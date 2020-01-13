/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.dao;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.Resource;
import ma.bservice.tgcc.Constante.Constante;
import ma.bservices.beans.AffectationSalarieSupp;
import ma.bservices.beans.Salarie;
import ma.bservices.mb.services.MbHibernateDaoSupport;
import ma.bservices.tgcc.Entity.Mensuel;
import static org.atmosphere.cpr.Universe.sessionFactory;
import org.hibernate.FlushMode;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author j.allali
 */
@Repository("salarieDao")
@Transactional
public class SalarieDaoImpl extends MbHibernateDaoSupport implements SalarieDao {

    @Resource(name = "sessionFactory")
    private SessionFactory sessionFactory;

    /**
     * Affecter un supp slarie à un salarie
     *
     * @param s
     * @param supp
     * @return
     */
    @Override
    public Boolean affecterSupp(Salarie s, Salarie supp) {
        Transaction tx = null;
        Session session = getSessionFactory().openSession();
        try {
            session.setFlushMode(FlushMode.AUTO);
            tx = session.beginTransaction();
            s.setSalarieSupp(supp);
//            session.update(s);
            session.save(s);
            tx.commit();
            session.close();
        } catch (Exception exp) {
            tx.rollback();
            session.close();
            return false;
        }
        return true;
    }

    /**
     * Supprimer le supp salarie du salarie specifie
     *
     * @param s
     * @return
     */
    @Override
    public Boolean deleteSupp(Salarie s) {
        Transaction tx = null;
        Session session = getSessionFactory().openSession();
        try {
            session.setFlushMode(FlushMode.AUTO);
            tx = session.beginTransaction();
            s.setSalarieSupp(null);
            session.update(s);
            tx.commit();
            session.close();
        } catch (Exception exp) {
            tx.rollback();
            session.close();
            return false;
        }
        return true;
    }

    /**
     * lise des salaries non afféctés
     *
     * @return
     */
    @Override
    public List<Salarie> listNotAffected() {
        List l = this.getHibernateTemplate().find("SELECT s FROM Salarie s where Dtype='Salarie' and superieur_id is NULL ");
        if (l.size() > 0) {
            return l;
        }

        return null;
    }

    /**
     * liste des salaries supp
     *
     * @return
     */
    @Override
    public List<Salarie> listSupp() {
        List l = this.getHibernateTemplate().find("SELECT s FROM Salarie s where Dtype='Salarie' AND superieur_id is NOT NULL ");
        if (l.size() > 0) {
            return l;
        }

        return null;
    }
    @Override
    public List<Salarie> listSalarieByChantierId(int idChantier) {
        /*
        Session session = sessionFactory.getCurrentSession();
        List<Salarie> l = new ArrayList<Salarie>(); 
        
        if(idChantier>0 ){
            try {   
                 String req="SELECT s.* FROM Salarie s where  s.id in (SELECT SALARIE_ID FROM CHANTIER_SALARIE WHERE CHANTIER_ID ="+idChantier+")  ";
                 System.out.println("DAO =======> req : "+req);
                // l = (List<Salarie>) this.getHibernateTemplate().find(req);  
                 List li =  session.createSQLQuery(req).list();
                 System.out.println("nombre de salarier par chantier est : "+li.size());
            } catch (Exception e) {
                logger.error("Erreur de récupération des salarier par chantier "+e.getMessage());
            }  
        }
        return l;
        */
        List<Salarie> l = new ArrayList<Salarie>(); 
        
        if(idChantier>0 ){ 
            try {   
                 String req=" FROM Salarie s where s.id in (SELECT SALARIE_ID FROM CHANTIER_SALARIE WHERE CHANTIER_ID ="+idChantier+")  ";
                 l = (List<Salarie>) this.getHibernateTemplate().find(req);   
            
            } catch (Exception e) {
                logger.error("Erreur de récupération des salarier par chantier "+e.getMessage());
            }  
        }
        return l;
    }

    @Override
    public Salarie addSalarie(Salarie s) {
        Transaction tx = null;
        Session session = getSessionFactory().openSession();
        try {
            session.setFlushMode(FlushMode.AUTO);
            tx = session.beginTransaction();
            session.save(s);
            tx.commit();
            session.close();
        } catch (Exception exp) {
            tx.rollback();
            session.close();
            return null;
        }
        return s;
    }

    /**
     * afficher la liste des salaries affécté à un supp spécifié
     *
     * @param s
     * @return
     */
    @Override
    public List<Salarie> listSalarieBySupp(Salarie s) {
        List l = this.getHibernateTemplate().find("SELECT s FROM Salarie s where Dtype='Salarie' AND s.salarieSupp.id = '" + s.getId() + "'");
        if (l.size() > 0) {
            return l;
        }

        return null;
    }

    @Override
    public Salarie deleteSalarie(Salarie s) {
        Transaction tx = null;
        Session session = getSessionFactory().openSession();
        try {
            session.setFlushMode(FlushMode.AUTO);
            tx = session.beginTransaction();
            session.delete(s);
            tx.commit();
            session.close();
        } catch (Exception exp) {
            tx.rollback();
            session.close();
            return null;
        }
        return s;
    }

    @Override
    public List<Salarie> getSalarieChefEquipe() {
//        List l = this.getHibernateTemplate().find("FROM Salarie s where and s.fonction = " + Constante.FONCTION_ID_CHEF_EQUIPE + " and s.etat.id in (" + Constante.Etat_ID_ACTIF + "," + Constante.Etat_ID_ACTIF_PROVISOIR + ")");
        List l = this.getHibernateTemplate().find("FROM Salarie s where Dtype='Salarie' and s.fonction.id = " + Constante.FONCTION_ID_CHEF_EQUIPE + " and s.etat.id in (" + Constante.Etat_ID_ACTIF + "," + Constante.Etat_ID_ACTIF_PROVISOIR + ")");
        System.out.println("liste CHEF Equipe: " + l.size());
        if (l.size() > 0) {
            return l;
        }
        return new LinkedList<>();
    }

    @Override
    public List<Mensuel> getMensuelsChefEquipe() {
        List l = this.getHibernateTemplate().find("SELECT s FROM Salarie s "
                + "where Dtype='Mensuel' "
                + "and s.fonction = " + Constante.FONCTION_ID_CHEF_EQUIPE);
        if (l.size() > 0) {
            return l;
        }
        return new ArrayList<>();
    }

    /**
     * Retourne la liste des mensuels chef d'équipe dans un chantier envoyé en
     * paramètre
     *
     * @param idChantier
     * @return List<Mensuel>
     */
    @Override
    public List<Mensuel> getMensuelsChefEquipeInChantier(int idChantier) {
        List l = this.getHibernateTemplate().find("SELECT s FROM Mensuel s ,"
                + "in(s.chantiers) c "
                + "where Dtype='Mensuel' "
                + "and s.fonction = " + Constante.FONCTION_ID_CHEF_EQUIPE
                + "AND s.statut = 1 "
                + "AND c.id =" + idChantier
        );
        if (l.size() > 0) {
            return l;
        }
        return new ArrayList<>();
    }

//    @Override
//    public List<Salarie> getSalarieChefEquipeByChantier(int idChantier) {
//        List l = this.getHibernateTemplate().find("SELECT s FROM Salarie s , in(s.chantiers) c  where s.fonction = 268 and c.CHANTIER_ID = "+idChantier
//        );
//        if (l.size() > 0) {
//            return  l;
//        }
//
//        return null;
//    }
    @Override
    public List<Salarie> listSalarieBySupp() {
        List l = this.getHibernateTemplate().find("SELECT s FROM Salarie s where Dtype='Salarie' AND s.salarieSupp.id !=0");
        if (l.size() > 0) {
            System.out.println(" xxxxxxxxxxxxxxxx SALARIE CHANTIER xxxxxxxxxxx " + l.size());
            return l;
        }

        return null;
    }

    @Override
    public Salarie updateSalarie(Salarie s) {
        Transaction tx = null;
        Session session = getSessionFactory().openSession();
        try {
            session.setFlushMode(FlushMode.AUTO);
            tx = session.beginTransaction();
            session.update(s);
            tx.commit();
            session.close();
        } catch (Exception exp) {
            tx.rollback();
            session.close();
            return null;
        }
        return s;
    }

    /**
     *
     * Recupère de la base de données la liste des salarié avec le type de
     * pointage : TYPE_FONCTION_POINTAGE_UPSIT dans une chantier en paramètere,
     * qui ne sont pas chef d'équipe et qui sont en état Actif ou Actif
     * provisoire.
     *
     * @param idc Id du chantier
     * @return List<Salarie>
     */
    @Override
    public List<Salarie> getActifSalarie(Integer idc) {
        String typeFonction = String.valueOf(Constante.Type_Fonction_Pointage_Upsit[0]);
        List l = this.getHibernateTemplate().find(
                "SELECT s FROM Salarie s  "
                + "INNER JOIN s.chantiers c "
                + "WHERE s.fonction.typeFonction in (" + typeFonction + ") "
                + "AND ( s.etat.id = " + Constante.Etat_ID_ACTIF + " OR s.etat.id = " + Constante.Etat_ID_ACTIF_PROVISOIR + ") "
                + "AND c.id =" + idc
        );

        if (l.size() > 0) {
            return l;
        }

        return null;
    }

    /**
     *
     * Recupère de la base de données la liste des salarié avec le type de
     * pointage : TYPE_FONCTION_POINTAGE_UPSIT dans une chantier en paramètere,
     * qui ne sont pas chef d'équipe et qui sont en état Actif ou Actif
     * provisoire.
     *
     * @param idc Id du chantier
     * @return List<Salarie>
     */
    @Override
    public List<Salarie> getActifSalarieWithSup(Integer idc) {

        Session session = sessionFactory.getCurrentSession();
        String typeFonction = String.valueOf(Constante.Type_Fonction_Pointage_Upsit[0]);
        //List<Object[]> results = this.getHibernateTemp    late().find
        Query q = session.createQuery(          
                "SELECT s, ss FROM Salarie s ,"
                + "in(s.chantiers) c "
                + "LEFT JOIN s.lAffectationSalarieSupp ss ON ss.currentSupp=true AND ss.chantier.id =" + idc + " "
                + "WHERE s.fonction.typeFonction = " + typeFonction + " "
                + "AND ( s.etat.id = " + Constante.Etat_ID_ACTIF + " OR s.etat.id = " + Constante.Etat_ID_ACTIF_PROVISOIR + ") "
                + "AND c.id =" + idc
        );

        List<Object[]> results = q.list();
        List<Salarie> lSalaries = new LinkedList<>();
        
        for (int i = 0; i < results.size(); i++) {
            Object salarieObj = results.get(i)[0];
        
            if (salarieObj instanceof Salarie) {
                Object affectationSalarieSupObj = results.get(i)[1];
            
                if (affectationSalarieSupObj instanceof AffectationSalarieSupp && (((AffectationSalarieSupp) affectationSalarieSupObj).getChefEquipe() != null )) {
                    ((Salarie) salarieObj).setChefEquipe(((AffectationSalarieSupp) affectationSalarieSupObj).getChefEquipe().getNom() + " " + ((AffectationSalarieSupp) affectationSalarieSupObj).getChefEquipe().getPrenom() );
                }
                
                lSalaries.add((Salarie) salarieObj);
            }

        }
        
        if(lSalaries.size() > 0){
            return lSalaries;
        }

        return null;
    }

    @Override
    public List<Salarie> getSalarieNotChef(Integer idc, Integer etatID) {
        int tab[] = Constante.Type_Fonction_Pointage_Upsit;
        String s = "";
        for (int i = 0; i < tab.length; i++) {
            s += tab[i] + ",";
        }
        s = s.substring(0, s.length() - 1);
        List l = this.getHibernateTemplate().find("SELECT s FROM Salarie s , in(s.chantiers) c where s.fonction.typeFonction in (" + s + ") AND s.fonction.id !=" + Constante.FONCTION_ID_CHEF_EQUIPE + " and s.etat.id = " + etatID + " and c.id =" + idc);
        if (l.size() > 0) {
            return l;
        }

        return null;
    }

    @Override
    public List<String> getListVilleDistinct() {
        List l = this.getHibernateTemplate().find("select distinct c.ville FROM Salarie c");
        if (l.size() > 0) {
            return l;
        }
        return null;
    }

    @Override
    public List<Salarie> getSalariesbyOrganigram(Integer id) {
        List l = this.getHibernateTemplate().find("select s FROM Salarie s WHERE s.organigrame.id = " + id);
        if (l.size() > 0) {
            return l;
        }
        return null;
    }

    @Override
    public List<Object> getSalarieHearchChef(Integer idChantier, Integer idChef) { 
        List<Object> SalarieHearchChefs = new ArrayList<Object>();
        String req ="";
        if(idChantier != null){
            req = "SELECT MATRICULE,LIB,NOM,PRENOM,chef,CHANTIER_ID,chefEquipe_ID  FROM V$_SALARIE_AVEC_CHEF WHERE CHANTIER_ID ="+idChantier;
            if(idChef != null){
                req +=" AND ass.chefEquipe_ID="+idChef;
            }
        } 
        if(req.length()>0 ){
            try {   
                Session session = sessionFactory.getCurrentSession();
                Query query = session.createQuery(req); 
                SalarieHearchChefs =  query.list(); 
            } catch (Exception e) {
                logger.error("Erreur de récupération des salarier avec leurs chef car "+e.getMessage());
            }
        } 
        return SalarieHearchChefs ;
    }

    @Override
    public Salarie getSalarieByID(Integer idc) {
        
        return this.getHibernateTemplate().load(Salarie.class, idc);
    }
    
    @Override
    public List<Salarie> listSalarieBlackListSorti() {
        List l = this.getHibernateTemplate().find( "SELECT s FROM Salarie s WHERE s.etat.id  in (2,3) AND s.type.id=2 " );

        if (l.size() > 0) {
            return l;
        }

        return null;
    }
    @Override
    public List<Salarie> listSalarieByListChantier(String listChantiers) { 
        List<Salarie> l = new ArrayList<Salarie>();  
        try {   
             String req=" FROM Salarie s where s.id in (SELECT SALARIE_ID FROM CHANTIER_SALARIE WHERE CHANTIER_ID in ("+listChantiers+"))";
             l = (List<Salarie>) this.getHibernateTemplate().find(req);    
        } catch (Exception e) {
            logger.error("Erreur de récupération des salarier par chantier "+e.getMessage());
        }   
        return l;
    }

    @Override
    public List<Salarie> listSalarieActifByChantierId(int idChantier) {
        List<Salarie> l = new ArrayList<Salarie>(); 
        
        if(idChantier>0 ){ 
            try {   
                 String req=" FROM Salarie s where s.etat.etat in('Actif','Actif provisoire') and s.id in (SELECT SALARIE_ID FROM CHANTIER_SALARIE WHERE CHANTIER_ID ="+idChantier+")  ";
                 l = (List<Salarie>) this.getHibernateTemplate().find(req);   
            
            } catch (Exception e) {
                logger.error("Erreur de récupération des salarier par chantier "+e.getMessage());
            }  
        }
        return l;
    }

}
