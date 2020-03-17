/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.dao.engin;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.Resource;
import ma.bservices.beans.Salarie;
import ma.bservices.tgcc.Entity.ECHEANCIER_VIDANGE;
import ma.bservices.tgcc.Entity.Engin; 
import ma.bservices.tgcc.Entity.InterventionMaintenance;
import ma.bservices.tgcc.Entity.Panne;
import ma.bservices.tgcc.Entity.ReferentielEngin;
import ma.bservices.tgcc.utilitaire.MbHibernateDaoSupport;
import ma.bservices.tgcc.utilitaire.SearchU;
import org.hibernate.FlushMode;
import org.hibernate.Query; 
import org.hibernate.Session; 
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author zakaria.dem
 */
@Repository("enginDAO")
@Transactional(rollbackFor = Exception.class)
public class EnginDAOImpl extends MbHibernateDaoSupport implements EnginDAO, Serializable {

    private static final Logger looger = Logger.getLogger(EnginDAOImpl.class.getName());

    
    @Resource(name = "sessionFactory")
    private SessionFactory sessionFactory;
    
        
    private final static String HQL_instance = "e";

    @Override
    public List<Engin> findAll() {

        return this.getHibernateTemplate().loadAll(Engin.class);

    }

    @Override
    public List<Engin> enginsArchive() {
        List<Engin> l = new ArrayList<Engin>();
        try {
            
         l = (List<Engin>) this.getHibernateTemplate().find("SELECT e FROM Engin e WHERE e.archive = '" + Boolean.TRUE + "'");
        } catch (Exception e) {
            looger.warning("Erreur de récuperation des engins Archivé car : "+e.getMessage());
        }
        return l;
    }
    @Override
    public List<Engin> enginsActif() {
        List<Engin> l = new ArrayList<Engin>();
        try {
              List ll =  this.getHibernateTemplate().find("SELECT e FROM Engin e WHERE (e.archive = 'false' or e.archive is null)  AND (e.reforme =  'false' or e.reforme is null)");
                    l = (List<Engin>)ll;
        } catch (Exception e) {
            looger.warning("Erreur de récuperation des engins Archivé car : "+e.getMessage());
        }
        return l;
    }
    @Override
    public List<Engin>  enginsEnPanne(){
        List<Engin> l = new ArrayList<Engin>();
        try {
            l = (List<Engin>) this.getHibernateTemplate().find("SELECT e FROM Engin e WHERE e.etat = 'EN_PANNE'  ");
            
        } catch (Exception e) {
            looger.warning("Erreur de récuperation des engins EN PANNE car : "+e.getMessage());
        }
        return l;
    }

    @Override
    public List<Engin>  enginsActifNoPanne(){
        List<Engin> l = new ArrayList<Engin>();
        try {
            l = (List<Engin>) this.getHibernateTemplate().find("SELECT e FROM Engin e WHERE e.etat = 'EN_PANNE' and (e.archive = 'false' or e.archive is null)  AND (e.reforme =  'false' or e.reforme is null) ");
            
        } catch (Exception e) {
            looger.warning("Erreur de récuperation des engins EN PANNE car : "+e.getMessage());
        }
        return l;
    }

    @Override
    public List<Engin> enginsReforme() {
        List<Engin> l = new ArrayList<Engin>();
        try {
         l = (List<Engin>) this.getHibernateTemplate().find("SELECT e FROM Engin e WHERE e.archive = '" + Boolean.FALSE + "' AND e.reforme = '" + Boolean.TRUE + "'");
        } catch (Exception e) {
            looger.warning("Erreur de récuperation des engins Reformé car : "+e.getMessage());
        }
        return l;
    }

    @Override
    public Engin findOneById(Integer id) {
        List l = this.getHibernateTemplate().find("SELECT e FROM Engin e WHERE e.iDEngin = '" + id + "'");

        if (l.size() > 0) {
            return (Engin) l.get(0);
        }

        return null;
    }

    @Override
    public List<Panne> lastPanneByEnginPanne(){
        List<Panne> l = new ArrayList<Panne>();
        
        try {
            l = (List<Panne>)this.getHibernateTemplate().find("SELECT p FROM Panne p "
                    + " WHERE p.id in (SELECT MAX(pp.id) FROM Panne pp group by pp.engin )"
                    + " AND p.engin.etat ='EN_PANNE' ");
        } catch (Exception e) {
            System.out.println("Erreur de recuperation last panne by engin en panne car "+e.getMessage());
        }
        return l;
        
        
    }

    @Override
    public List<Engin> rechercherEngin(String code, String designation, String marque, String etat, int chantier_id) {

        String filtre = createSearchFiltre(code, designation, marque, etat, chantier_id);

        System.out.println("les filtre : " + filtre);
        List l = null;

        l = this.getHibernateTemplate().find("From Engin " + HQL_instance + " " + filtre);

        return l;
    }

    /**
     *
     * retourne les filre SQL pour la requte de la recherche des Pointage EX :
     * WHERE p.code = 123 and p.designation = DES
     *
     * @param code
     * @param Designation
     * @return String
     */
    static String createSearchFiltre(String code, String Designation, String marque, String etat, int chantier_id) {

        String filtre = "";
        Boolean filtre_bool_where = false;
        String filtre_one;
        //creation du filre pour le code

        filtre_one = SearchU.createOneFilre("code", code, filtre_bool_where, HQL_instance);
        if (filtre_one != null) {
            filtre += filtre_one;
            filtre_bool_where = true;
        }

        filtre_one = SearchU.createOneFilre("designation", Designation, filtre_bool_where, HQL_instance);
        if (filtre_one != null) {
            filtre += filtre_one;
            filtre_bool_where = true;
        }
        filtre_one = SearchU.createOneFilre("marque", marque, filtre_bool_where, HQL_instance);
        if (filtre_one != null) {
            filtre += filtre_one;
            filtre_bool_where = true;
        }

        filtre_one = SearchU.createOneFilre("prjapId.id", "" + chantier_id, filtre_bool_where, HQL_instance);
        if (filtre_one != null) {
            filtre += filtre_one;
            filtre_bool_where = true;
        }

        filtre_one = SearchU.createOneFilre("etat", etat, filtre_bool_where, HQL_instance);
        if (filtre_one != null) {
            filtre += filtre_one;
            filtre_bool_where = true;
        }


        return filtre;

    }

    @Override
    @Transactional
    public void ajouterEngin(Engin engin) {
        try {
            Session session = getSessionFactory().openSession();
            session.setFlushMode(FlushMode.AUTO);

            session.beginTransaction();
            session.saveOrUpdate(engin);

            session.getTransaction().commit();

            session.close();
        } catch (Exception e) {
            System.out.println("Erreur d'enregistrement Engin car "+e.getMessage());
        }

//        try {
//            this.getHibernateTemplate().save(engin);
//            this.getHibernateTemplate().flush();
//        } catch (Exception e) {
//        }
    }

//    @Override
//    public void affecterEngin(Engin engin) {
//
//        this.getHibernateTemplate().save(engin);
//
//    }
    @Override
    @Transactional
    public void affecterEngin(Engin engin) {
        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);
        session.beginTransaction();
        // session.update(session.merge(engin));
        session.update(engin);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void affecterEnginTestunit(Engin engin) {

        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);
        session.beginTransaction();
        session.update(engin);
        session.getTransaction().commit(); 
        session.close();

    }
    @Override
    @Transactional
    public void  updateListEngin(List<Engin> engins){
        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);
        session.beginTransaction();
        try {
            for(Engin p : engins){
                if(p != null){
                    session.update(session.merge(p));
                }
            }
        } catch (Exception e) {
            System.out.println("Erreur de modification d'engin car "+e.getMessage());
        }
        session.getTransaction().commit();
        session.close(); 
        
    }
    @Override
    @Transactional
    public void updateEngin(Engin engin) { 
        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);
        session.beginTransaction();
        session.update(session.merge(engin));
        session.getTransaction().commit();
        session.close(); 
        
    }

    @Override
//    @Transactional
    public Engin findOneByCode(String code) {

        List l = this.getHibernateTemplate().find("SELECT p FROM Engin p WHERE p.code = '" + code + "'");
        if (l.size() > 0) {
            return (Engin) l.get(0);
        }
        return null;
    }

    @Override
    public List<Engin> findAllInChantier(Integer chantier_id) {
        List l = this.getHibernateTemplate().find("SELECT p FROM Engin p WHERE p.prjapId = '" + chantier_id + "'");
        if (l.size() > 0) {
            return l;
        }
        return null;
    }

    @Override
    public List<Engin> findAllEnginByChantierId(Integer chantier_id) {
        List<Engin> l = new ArrayList<Engin>();
        try {
              List ll =  this.getHibernateTemplate().find("SELECT e FROM Engin e WHERE (e.archive = 'false' or e.archive is null)  "
                      + "AND (e.reforme =  'false' or e.reforme is null) "
                      + "AND e.prjapId = '" + chantier_id + "'");
                    l = (List<Engin>)ll;
        } catch (Exception e) {
            looger.warning("Erreur de récuperation des engins par chantier car : "+e.getMessage());
        }
        return l;
    }

    @Override
    public List<Engin> findAllInChantierArchive(int chantier_id) {
        List l = this.getHibernateTemplate().find("SELECT p FROM Engin p WHERE p.prjapId = '" + chantier_id + "' and p.archive = false");
        if (l.size() > 0) {
            return l;
        }
        return null;
    }

    /**
     * Ajouter lors du test unitaire
     */
    @Override
    public Boolean delete(Engin e) {
        try {

            Session session = getSessionFactory().openSession();
            session.setFlushMode(FlushMode.AUTO);
            session.beginTransaction();
            session.delete(e);
            session.getTransaction().commit();
            session.close();
            return true;
        } catch (Exception exp) {
            System.out.println("Erreur de suppression Engin car "+exp.getMessage());
            return false;
        }
    }

    @Override
    public List<Engin> findOneByArchive() {
        Boolean archive = false;
        List l = this.getHibernateTemplate().find("SELECT p FROM Engin p WHERE p.archive = '" + archive + "'");
        if (l.size() > 0) {

            return l;
        }

        return null;
    }

    @Override
    public List<Engin> rechercherEnginByFa(String code, String designation, String marque, String etat, int chantier_id, String typeE, String familleE) {
        String filtre = createSearchFiltre(code, designation, marque, etat, chantier_id, typeE, familleE);

        System.out.println("les filtre : " + filtre);
        List l = null;
        if(filtre.length()>1){
        System.out.println("les filtre :filtre.length()>1: " + "From Engin " + HQL_instance + " " + filtre+" AND e.archive=FALSE AND e.reforme=FALSE ");
            l = this.getHibernateTemplate().find("From Engin " + HQL_instance + " " + filtre+" AND e.archive=FALSE AND e.reforme=FALSE ");
        }else{
        System.out.println("les filtre :filtre.length()<=1: " + "From Engin " + HQL_instance + " WHERE e.archive=FALSE AND e.reforme=FALSE ");
            l = this.getHibernateTemplate().find("From Engin " + HQL_instance + " WHERE e.archive=FALSE AND e.reforme=FALSE ");
        }
        return l;
    }

    static String createSearchFiltre(String code, String Designation, String marque, String etat, int chantier_id, String typeE, String familleE) {

        String filtre = "";
        Boolean filtre_bool_where = false;
        String filtre_one;
        //creation du filre pour le code

        filtre_one = SearchU.createOneFilre("code", code, filtre_bool_where, HQL_instance);
        if (filtre_one != null) {
            filtre += filtre_one;
            filtre_bool_where = true;
        }

        filtre_one = SearchU.createOneFilre("designation", Designation, filtre_bool_where, HQL_instance);
        if (filtre_one != null) {
            filtre += filtre_one;
            filtre_bool_where = true;
        }
        filtre_one = SearchU.createOneFilre("marque", marque, filtre_bool_where, HQL_instance);
        if (filtre_one != null) {
            filtre += filtre_one;
            filtre_bool_where = true;
        }

        filtre_one = SearchU.createOneFilre("prjapId.id", "" + chantier_id, filtre_bool_where, HQL_instance);
        if (filtre_one != null) {
            filtre += filtre_one;
            filtre_bool_where = true;
        }

        filtre_one = SearchU.createOneFilre("etat", etat, filtre_bool_where, HQL_instance);
        if (filtre_one != null) {
            filtre += filtre_one;
            filtre_bool_where = true;
        }

        filtre_one = SearchU.createOneFilre("typeEngin", typeE, filtre_bool_where, HQL_instance);
        if (filtre_one != null) {
            filtre += filtre_one;
            filtre_bool_where = true;
        }

        filtre_one = SearchU.createOneFilre("familleEngin", familleE, filtre_bool_where, HQL_instance);
        if (filtre_one != null) {
            filtre += filtre_one;
            filtre_bool_where = true;
        }

        return filtre;

    }

    @Override
    public List<Engin> findAllChantierArchivePanne(int chantier_id) {
        // modification du 22/11/2018
        // List l = this.getHibernateTemplate().find("SELECT p FROM Engin p WHERE p.prjapId = '" + chantier_id + "' and p.archive = false and p.etat !='panne' ");
        
        List l = this.getHibernateTemplate().find("SELECT p FROM Engin p WHERE p.prjapId = '" + chantier_id + "' and p.archive = false and p.etat !='panne' and (p.actif is null or p.actif=0)) ");
        
        if (l.size() > 0) {
            return l;
        }
        return null;
    }
    @Override
    public List<Engin> findAllEnginPointageAutoParChantier(int chantier_id) {
        // modification du 22/11/2018
        // List l = this.getHibernateTemplate().find("SELECT p FROM Engin p WHERE p.prjapId = '" + chantier_id + "' and p.archive = false and p.etat !='panne' ");
        
        List l = this.getHibernateTemplate().find("SELECT p FROM Engin p WHERE p.prjapId = '" + chantier_id + "' and p.archive = false and p.etat !='panne' and  p.actif=1 ");
        
        if (l.size() > 0) {
            return l;
        }
        return null;
    }

    @Override
    public List<Engin> findAllEnginPointageAutoDept(int chantier_id) {
        // modification du 22/11/2018
        // List l = this.getHibernateTemplate().find("SELECT p FROM Engin p WHERE p.prjapId = '" + chantier_id + "' and p.archive = false and p.etat !='panne' ");
        
        List l = this.getHibernateTemplate().find("SELECT p FROM Engin p WHERE p.prjapId = '" + chantier_id + "' and p.archive = false and p.etat !='panne' and  p.typePointageDept='automatique' ");
        
        if (l.size() > 0) {
            return l;
        }
        return null;
    }
    @Override
    public List<Engin> findAllEnginPointageManuelDept(int chantier_id) {
        // modification du 22/11/2018
        // List l = this.getHibernateTemplate().find("SELECT p FROM Engin p WHERE p.prjapId = '" + chantier_id + "' and p.archive = false and p.etat !='panne' ");
        
        List l = this.getHibernateTemplate().find("SELECT p FROM Engin p WHERE p.prjapId = '" + chantier_id + "' and p.archive = false and p.etat !='panne' and  p.typePointageDept='manuel' ");
        
        if (l.size() > 0) {
            return l;
        }
        return null;
    }

    @Override
    public List<String> findAllMarque() {
        List<String> engin = new ArrayList<>();

//        Session s = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
        Session s = this.getHibernateTemplate().getSessionFactory().openSession();
        try {
            //demarrage de la session
            s.getTransaction().begin();
            Query query = s.createQuery("select distinct(e.marque) from Engin e");
            engin = query.list();
            //execution de la requete
            s.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            s.getTransaction().rollback();
        }
        return engin;
    }

    @Override
    public List<Salarie> allConducteur() {
        List<Salarie> l = new ArrayList<Salarie>();
        try {
           /* 
            List f = this.getHibernateTemplate().find("SELECT f FROM FONCTION f WHERE lower(f.fonction) like '%chauffeur%'  ");
            System.out.println("====================> liste des salarie de fonction chaufeur :"+f.size() );
            List e = this.getHibernateTemplate().find("FROM Etat f WHERE f.id in(1,5) ");
            System.out.println("====================> liste des etat de fonction chaufeur:"+f.size() );
            System.out.println(e.size() );
*/
            l = (List<Salarie>) this.getHibernateTemplate().find("FROM Salarie s  where s.etat.id in (1,5) and lower(s.fonction.fonction) like '%chauffeur%' ");
        } catch (Exception e) {
            looger.warning("Erreur de récuperation des Conducteur car :"+e.getMessage());
        }
        return l;
    }
    
    @Override
    public String nextCod(String designiation,String codeD){
        String code="";
        try {
            System.out.println("======> SELECT MAX(e.codeDesignation) FROM Engin e WHERE upper(e.designation) = '" + designiation.toUpperCase()+ "'");
            code =  (String) this.getHibernateTemplate().find("SELECT MAX(e.codeDesignation) FROM Engin e WHERE upper(e.designation) = '" + designiation.toUpperCase()+ "'").get(0);
            
            System.out.println("======> code : "+code);
            if(!(code == null)){
                try {
                        code= designiation+(Integer.parseInt(code.replace(designiation, ""))+1);
                } catch (Exception e) {
                    looger.warning("Erreur d'incrimentation de code car "+e.getMessage());
                }
            }else{
                if(codeD != null){
                    code=codeD+1;
                }
            }
            
            System.out.println("======> code : "+code);
            System.out.println("======> codeD : "+codeD);
        } catch (Exception e) {
            looger.warning("Erreur de récuperation du code car : "+e.getMessage());
        }
        return code;
    }

    @Override
    public List<InterventionMaintenance> listIntervPr(int idEngin) {
        List<InterventionMaintenance> l = new ArrayList<InterventionMaintenance>(); 
        try {
           l = (List<InterventionMaintenance>) this.getHibernateTemplate().find("FROM InterventionMaintenance i where i.ID_ENGIN="+idEngin);
            System.out.println("FROM InterventionMaintenance i where i.ID_ENGIN="+idEngin);
        } catch (Exception e) {
            System.out.println("Erreur de récupération liste nterventions maintenance préventive car "+e.getMessage());
        } 
        return l;
    }

    @Override
    public List<InterventionMaintenance> listIntervCr(int idEngin) {
        List<InterventionMaintenance> l = new ArrayList<InterventionMaintenance>(); 
       Session s = sessionFactory.openSession();
        try {
           l = (List<InterventionMaintenance>) this.getHibernateTemplate().find("FROM InterventionMaintenance i where i.panne.engin.iDEngin="+idEngin);
           
        } catch (Exception e) {
            System.out.println("Erreur de récupération de liste nterventions maintenance préventive car "+e.getMessage());
        } 
        return l;
    }

    @Override
    public ECHEANCIER_VIDANGE lastEcheancierVidangeByCodeEngin(String code) {
        
        ECHEANCIER_VIDANGE ev = new ECHEANCIER_VIDANGE();
        try {
             List l  =  this.getHibernateTemplate().find("FROM ECHEANCIER_VIDANGE i where i.CODE='"+code+"')");
           ev=(ECHEANCIER_VIDANGE) l.get(0);
        } catch (Exception e) {
            System.out.println("Erreur de récupération de liste nterventions maintenance préventive car "+e.getMessage());
        }  
        return ev;
    }

    @Override
    public Panne lastPanneByEngin(Engin engin) {
        Panne panne = new Panne();
        try {
             List l  =  this.getHibernateTemplate().find("FROM Panne i where  i.engin.iDEngin ="+engin.getiDEngin()+" AND i.date=(SELECT MAX(date) FROM Panne where engin.iDEngin ="+engin.getiDEngin()+" ) ");
           panne=(Panne) l.get(0);
        } catch (Exception e) {
            System.out.println("Erreur de recuperation de last panne car "+e.getMessage());
        }
        return panne;
    }

    @Override
    public void addReferentielEngin(ReferentielEngin r) {
         try {
            Session session = getSessionFactory().openSession();
            session.setFlushMode(FlushMode.AUTO);
            session.beginTransaction();
            session.save(r);
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            System.out.println("Erreur d'enregistrement ReferentielEngin car "+e.getMessage());
        }
    }

    @Override
    public void editReferentielEngin(ReferentielEngin r) {
        try {
            Session session = getSessionFactory().openSession();
            session.setFlushMode(FlushMode.AUTO);
            session.beginTransaction();
            session.update(r);
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            System.out.println("Erreur de modifier ReferentielEngin car "+e.getMessage());
        }
    }

    @Override
    public void remouvReferentielEngin(ReferentielEngin r) {
         try {
            Session session = getSessionFactory().openSession();
            session.setFlushMode(FlushMode.AUTO);
            session.beginTransaction();
            session.delete(r);
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            System.out.println("Erreur de suppression ReferentielEngin car "+e.getMessage());
        }
    }

    @Override
    public List<ReferentielEngin> allReferentielEnginByEngin(Engin e) {
         List<ReferentielEngin> l = new ArrayList<ReferentielEngin>();
       try {
             l  =  (List<ReferentielEngin>) this.getHibernateTemplate().find("FROM ReferentielEngin r where  r.engin.iDEngin ="+e.getiDEngin());
          
        } catch (Exception exp) {
            System.out.println("Erreur de recuperation de last panne car "+exp.getMessage());
        }
       return l;
    }

    @Override
    public void addInterventionMaintenancePr(InterventionMaintenance i) {
        try {
            Session session = getSessionFactory().openSession();
            session.setFlushMode(FlushMode.AUTO);
            session.beginTransaction();
            session.save(i);
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            System.out.println("Erreur d'enregistrement InterventionMaintenance car "+e.getMessage());
        }
    }

    @Override
    public void editInterventionMaintenancePr(InterventionMaintenance i) {
        try {
            Session session = getSessionFactory().openSession();
            session.setFlushMode(FlushMode.AUTO);
            session.beginTransaction();
            session.update(i);
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            System.out.println("Erreur de modifier InterventionMaintenance car "+e.getMessage());
        }
    }
    
    
    
    

}
