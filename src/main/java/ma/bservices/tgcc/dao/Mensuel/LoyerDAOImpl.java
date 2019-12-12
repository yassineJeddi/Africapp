/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.dao.Mensuel;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import ma.bservices.tgcc.Entity.BonCaisse;
import ma.bservices.beans.Chantier;
import ma.bservices.beans.Proprietaire;
import ma.bservices.tgcc.Entity.FournisseurLoyer;
import ma.bservices.tgcc.Entity.Loyer;
import ma.bservices.tgcc.Entity.LoyerChantier;
import ma.bservices.tgcc.Entity.LoyerSalarie;
import ma.bservices.tgcc.Entity.Mensuel;
import ma.bservices.tgcc.utilitaire.MbHibernateDaoSupport;
import ma.bservices.tgcc.utilitaire.SearchU;
import org.hibernate.FlushMode;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author a.wattah
 */
@Repository("loyerDAO")
@Transactional
public class LoyerDAOImpl extends MbHibernateDaoSupport implements LoyerDAO, Serializable {

    private final static String HQL_instance = "m";
    private static final Logger looger = Logger.getLogger(LoyerDAOImpl.class.getName());

    @Override
    public List<Mensuel> findAll() {
        return this.getHibernateTemplate().loadAll(Mensuel.class);
    }

    @Override
    public List<Mensuel> search(int id, String matriculeB, String nomB, String dateDebut) {

        String filtre = createSearchFiltre(id, matriculeB, nomB, dateDebut);

        System.out.println("les filtre : " + filtre);
        List l = null;

        l = this.getHibernateTemplate().find("From Mensuel " + HQL_instance + " " + filtre);

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
    static String createSearchFiltre(int id, String matriculeB, String nomB, String dateDebut) {

        String filtre = "";
        Boolean filtre_bool_where = false;
        String filtre_one;
        //creation du filre pour le code

        filtre_one = SearchU.createOneFilre("id", "" + id, filtre_bool_where, HQL_instance);
        if (filtre_one != null) {
            filtre += filtre_one;
            filtre_bool_where = true;
        }

        filtre_one = SearchU.createOneFilre("matricule", matriculeB, filtre_bool_where, HQL_instance);
        if (filtre_one != null) {
            filtre += filtre_one;
            filtre_bool_where = true;
        }
        filtre_one = SearchU.createOneFilre("nom", nomB, filtre_bool_where, HQL_instance);
        if (filtre_one != null) {
            filtre += filtre_one;
            filtre_bool_where = true;
        }

        filtre_one = SearchU.createOneFilre("datecreation", dateDebut, filtre_bool_where, HQL_instance);
        if (filtre_one != null) {
            filtre += filtre_one;
            filtre_bool_where = true;
        }
        return filtre;

    }

    @Override
    public List<Mensuel> Consult(int id) {

        List<Mensuel> mensules = null;

        Session s = this.getHibernateTemplate().getSessionFactory().openSession();
        try {
            //demarrage de la session
            s.getTransaction().begin();
            String hql = "select m from Mensuel m , in(m.loyerSalaries) l where m.id =" + id;
//            select p from ModPm p join p.modScopeTypes type where type.scopeTypeId = 1
//            String hql="select m from Mensuel m inner join m.loyerSalaries l where l.id ="+id;

            Query query = s.createQuery(hql);

            mensules = query.list();

            s.getTransaction().commit();
            s.close();

        } catch (Exception e) {

            s.getTransaction().rollback();
            s.close();
        }

        return mensules;
    }

    /**
     * * methode qui recupere la liste de loyer salarie a partir id de mensuel
     *
     * @param id
     * @return
     */
    @Override
    public List<LoyerSalarie> getListLoyerSalarieByIdMensuel(int id) {

        List<LoyerSalarie> loyersSalaries = null;

        Session s = this.getHibernateTemplate().getSessionFactory().openSession();
        try {
            //demarrage de la session
            s.getTransaction().begin();
            String hql = "select m from LoyerSalarie m , in(m.mensuels) l where m.id =" + id;
//            select p from ModPm p join p.modScopeTypes type where type.scopeTypeId = 1
//            String hql="select m from Mensuel m inner join m.loyerSalaries l where l.id ="+id;

            Query query = s.createQuery(hql);

            loyersSalaries = query.list();

            s.getTransaction().commit();

        } catch (Exception e) {

            s.getTransaction().rollback();
        }

        return loyersSalaries;
    }

    @Override
    public List<Mensuel> rechercherMensuel(String matricule, String nom, String prenom, String fonction, String dateDebut) {
        String filtre = createSearchFiltre(matricule, nom, prenom, fonction, dateDebut);

        System.out.println("les filtre : " + filtre);
        List l = null;

        l = this.getHibernateTemplate().find("From Mensuel " + HQL_instance + " " + filtre);

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
    static String createSearchFiltre(String matricule, String nom, String prenom, String fonction, String dateDebut) {

        String filtre = "";
        Boolean filtre_bool_where = false;
        String filtre_one;
        //creation du filre pour le code

        filtre_one = SearchU.createOneFilre("matricule", "" + matricule, filtre_bool_where, HQL_instance);
        if (filtre_one != null) {
            filtre += filtre_one;
            filtre_bool_where = true;
        }

        filtre_one = SearchU.createOneFilre("nom", "" + nom, filtre_bool_where, HQL_instance);
        if (filtre_one != null) {
            filtre += filtre_one;
            filtre_bool_where = true;
        }
        filtre_one = SearchU.createOneFilre("prenom", "" + prenom, filtre_bool_where, HQL_instance);
        if (filtre_one != null) {
            filtre += filtre_one;
            filtre_bool_where = true;
        }

        filtre_one = SearchU.createOneFilre("iDFonction.libelleFonction", "" + fonction, filtre_bool_where, HQL_instance);
        if (filtre_one != null) {
            filtre += filtre_one;
            filtre_bool_where = true;
        }
        filtre_one = SearchU.createOneFilre("datecreation", "" + dateDebut, filtre_bool_where, HQL_instance);
        if (filtre_one != null) {
            filtre += filtre_one;
            filtre_bool_where = true;
        }

        return filtre;

    }

    @Override
    public List<Mensuel> findALLl() {

        List<Mensuel> mensules = null;

        Session s = this.getHibernateTemplate().getSessionFactory().openSession();
        try {
            //demarrage de la session
            s.getTransaction().begin();
//            String hql="select m from Mensuel m , in(m.loyerSalaries) l where m.id ="+id;
//            select p from ModPm p join p.modScopeTypes type where type.scopeTypeId = 1
            String hql = "select m from Mensuel m join m.loyerSalaries l ";

            Query query = s.createQuery(hql);

            mensules = query.list();

            s.getTransaction().commit();
            s.close();
        } catch (Exception e) {

            s.getTransaction().rollback();
        }

        return mensules;
    }

    @Override
    public List<Mensuel> findALLlBy_Id_Loyer(Long id) {

        List<Mensuel> mensules = null;

        Session s = this.getHibernateTemplate().getSessionFactory().openSession();
        try {
            //demarrage de la session
            s.getTransaction().begin();
//            String hql="select m from Mensuel m , in(m.loyerSalaries) l where m.id ="+id;
//            select p from ModPm p join p.modScopeTypes type where type.scopeTypeId = 1
            String hql = "select m from Mensuel m join m.loyerSalaries l where l.id =" + id;

            Query query = s.createQuery(hql);

            mensules = query.list();
            
            
            System.out.println("LIST OF MENSUEL GOT DAO ::: " + mensules);

            s.getTransaction().commit();
            s.close();
        } catch (Exception e) {

            s.getTransaction().rollback();
        }

        return mensules;
    }

    @Override
    public List<Mensuel> AfficherListMensuelByidLoyer(Long id) {
        List<Mensuel> mensules = null;

        Session s = this.getHibernateTemplate().getSessionFactory().openSession();
        try {
            //demarrage de la session
            s.getTransaction().begin();
            String hql = "select m from Mensuel m join m.loyerSalaries l where l.mensuel_Principal.id !=" + id;
//            String hql = "select m from Mensuel m , in(m.loyerSalaries) l where m.id !=" + id;
//            select p from ModPm p join p.modScopeTypes type where type.scopeTypeId = 1
//            String hql="select m from Mensuel m inner join m.loyerSalaries l where l.id ="+id;

            Query query = s.createQuery(hql);

            mensules = query.list();

            s.getTransaction().commit();
            s.close();
        } catch (Exception e) {

            s.getTransaction().rollback();
        }

        return mensules;
    }

    @Override
    public List<BonCaisse> findAllBonCaiss() {
        return this.getHibernateTemplate().loadAll(BonCaisse.class);
    }

    @Override
    public BonCaisse findAllBonCaisseByIdMensuel(int id) {
        List l;
        l = this.getHibernateTemplate().find("From b BonCaisse b where b.ls.id" + id);
        if (l.size() > 0) {
            return (BonCaisse) l.get(0);
        }
        return null;
    }

    @Override
    public void saveAffectation(LoyerChantier loyer) {
        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);

        session.beginTransaction();
        session.save(loyer);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List<Chantier> ConsultChantier(int id) {
        List<Chantier> chantiers = null;

        Session s = this.getHibernateTemplate().getSessionFactory().openSession();
        try {
            //demarrage de la session
            s.getTransaction().begin();
            String hql = "select m from Chantier m , in(m.loyerChantier) l where m.prjapId =" + id;
//            select p from ModPm p join p.modScopeTypes type where type.scopeTypeId = 1
//            String hql="select m from Mensuel m join m.loyerSalaries l where l.id ="+id;

            Query query = s.createQuery(hql);

            chantiers = query.list();

            s.getTransaction().commit();
            s.close();

        } catch (Exception e) {

            s.getTransaction().rollback();
        }

        return chantiers;
    }

    @Override
    public void saveAffectation(LoyerSalarie loyer) {
        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);

        session.beginTransaction();
        session.save(session.merge(loyer));
        session.getTransaction().commit();
        session.close();
        
        System.out.println("IN DAO === MENSUELS : " + loyer.getMensuels());

    }

    /*
     * suprimer loyer chantier 
     */
    @Override
    public void deleteLoyerChantier(LoyerChantier loyer) {
        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);

        session.beginTransaction();
        session.delete(loyer);
        session.getTransaction().commit();
        session.close();
    }

//    
//     @Override
//   public void saveAffectation(LoyerSalarie loyer) {
//       Session session = getSessionFactory().openSession();
//       session.setFlushMode(FlushMode.AUTO);
//
//       session.beginTransaction();
//       session.save(loyer);   
//       session.getTransaction().commit();
//       session.close();
//   }
    @Override
    public void saveLoyerSalarie(LoyerSalarie loyer) {
        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);
        loyer.setEstArchive(Boolean.FALSE);
        session.beginTransaction();
        session.save(loyer);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void deleteLoyerSalarie(LoyerSalarie loyer) {
        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);
        session.beginTransaction();
        session.delete(loyer);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List<LoyerChantier> loyerChantiers() {
        List l = this.getHibernateTemplate().find("FROM LoyerChantier m ORDER BY m.datedebut DESC");
        return l;
    }

    @Override
    public List<LoyerSalarie> loyerSalaries() {
        List l = this.getHibernateTemplate().find("FROM LoyerSalarie m ORDER BY m.datedebut DESC");
        return l;
    }

    @Override
    public List<LoyerSalarie> loyerSalariesByDateDeb(Date datedeb) {

        DateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
        String ss = formatDate.format(datedeb);
        System.out.println("DAAAAAAAAAAo ==== +++++ DATE : " + ss);
        List l = this.getHibernateTemplate().find("FROM LoyerSalarie m WHERE m.datedebut >= '" + ss + "'  ORDER BY m.datedebut DESC");
        return l;
    }

    @Override
    public List<LoyerChantier> loyerChantierByDateDeb(Date datedeb) {

        DateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
        String ss = formatDate.format(datedeb);
        System.out.println("DAAAAAAAAAAo ==== +++++ DATE : " + ss);
        List l = this.getHibernateTemplate().find("FROM LoyerChantier m WHERE m.datedebut >= '" + ss + "'  ORDER BY m.datedebut DESC");
        return l;
    }

    @Override
    public List<LoyerChantier> rechercher_Loyer_Chantier_ByIdentifient_Date_Debut(Long identifiant, String date_Debut) {

//        List l = this.getHibernateTemplate().find("SELECT c FROM LoyerChantier c WHERE c.id = '" + identifiant + "' OR CONVERT(VARCHAR,c.datedebut,103) = '" + date_Debut + "'");
//        if (l.size() > 0) {
//
//            return l;
//        }
//
//        return null;
        String filtre = createSearchFiltre(identifiant, date_Debut);
        // List l = this.getHibernateTemplate().find("FROM LoyerChantier c WHERE c. );
//        if (l.size() > 0) {
//
//            return l;
//        }

        return null;
    }

    static String createSearchFiltre(Long id, String dateD) {

        String filtre = "";
        Boolean filtre_bool_where = false;
        String filtre_one;
        //creation du filre pour le code

        filtre_one = SearchU.createOneFilre("id", "" + id, filtre_bool_where, HQL_instance);
        if (filtre_one != null) {
            filtre += filtre_one;
            filtre_bool_where = true;
        }

        filtre_one = SearchU.createOneFilre("datedebut", dateD, filtre_bool_where, HQL_instance);
        if (filtre_one != null) {
            filtre += filtre_one;
            filtre_bool_where = true;
        }

        return filtre;

    }

    @Override
    public List<LoyerSalarie> rechercher_Loyer_Salarier_ByIdentifient_Date_Debut(Long identifiant, String date_Debut) {

//        String hql = "";
//
//        if (identifiant != 0) {
//
//            hql = " and c.id =  '" + identifiant + "'";
//
//        }else
//            
//
//        if (date_Debut.equals("")) {
//
//            hql = " and CONVERT(VARCHAR,c.datedebut,103) =  '" + date_Debut + "'";
//
//        }
//
//        List l = this.getHibernateTemplate().find("SELECT c FROM LoyerSalarie c WHERE c.id = '" + identifiant + "' OR CONVERT(VARCHAR,c.datedebut,103) = '" + date_Debut + "'");
//        if (l.size() > 0) {
//
//            return l;
//        }
//        
        String filtre = createSearchFiltre(identifiant, date_Debut);
        System.out.println("DATE DEBUT FROM DAO : " + date_Debut);
        List l = this.getHibernateTemplate().find("FROM LoyerSalarie m WHERE m.datedebut >= '" + date_Debut + "' ORDER BY m.datedebut  ");
        if (l.size() > 0) {

            return l;
        }

        return null;

    }

    @Override
    public void deleteLoyerSalarie_Test_Unitaire(LoyerSalarie loyer) {
        this.getHibernateTemplate().delete(loyer);
        this.getHibernateTemplate().flush();
    }

    @Override
    public List<LoyerSalarie> getListLoyerSalarieByIdMensuelPrincipal(int id) {

        List<LoyerSalarie> loyers = null;

        Session s = this.getHibernateTemplate().getSessionFactory().openSession();
        try {
            //demarrage de la session
            s.getTransaction().begin();
            String hql = "select l from LoyerSalarie l where l.mensuel_Principal.id =" + id;
//            select p from ModPm p join p.modScopeTypes type where type.scopeTypeId = 1
//            String hql="select m from Mensuel m join m.loyerSalaries l where l.id ="+id;

            Query query = s.createQuery(hql);

            loyers = query.list();

            s.getTransaction().commit();
            s.close();

        } catch (Exception e) {

            s.getTransaction().rollback();
        }

        return loyers;
    }

    @Override
    public List<LoyerChantier> getLoyerChantierByChantier(int id) {

        List<LoyerChantier> chantiers = null;

        Session s = this.getHibernateTemplate().getSessionFactory().openSession();
        try {
            //demarrage de la session
            s.getTransaction().begin();
            String hql = "select m from LoyerChantier m , in(m.chantiers) l where l.prjapId =" + id;
//            select p from ModPm p join p.modScopeTypes type where type.scopeTypeId = 1
//            String hql="select m from Mensuel m join m.loyerSalaries l where l.id ="+id;

            Query query = s.createQuery(hql);

            chantiers = query.list();

            s.getTransaction().commit();
            s.close();

        } catch (Exception e) {

            s.getTransaction().rollback();
        }

        return chantiers;
    }

    @Override
    public void desaffecterLoyerMensuel(Loyer loyer) {
      
        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);
        Transaction tx = null;
        try {
            tx = session.beginTransaction();

            Query query = session.createQuery("update Loyer set estArchive = 1"
                    + " where id = :idLoyer");
            query.setParameter("idLoyer", loyer.getId());
            query.executeUpdate();

            tx.commit();
        } catch (HibernateException e) {
            System.out.println("HIBERNATE EXCEPTION");
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }

    }

    @Override
    public List<Loyer> findByNumContrat(String numContrat) {
         List l = this.getHibernateTemplate().find("FROM Loyer m WHERE m.numcontrat = '" + numContrat + "'");
        if (l.size() > 0) {
            return l;
        }

        return null;
    }

    @Override
    public LoyerSalarie findOneLSById(Integer id) {
      

        LoyerSalarie ls = null;

        Session s = this.getHibernateTemplate().getSessionFactory().openSession();
        try {
           
            s.getTransaction().begin();

            String hql = "select ls from Loyer ls where ls.id =" + id;

            Query query = s.createQuery(hql);

            ls = (LoyerSalarie) query.uniqueResult();
            
            
            System.out.println("RETRIEVED FROM LOYER DAO LS ::: " +  ls.getNomproprietaire());

            s.getTransaction().commit();
            s.close();
        } catch (Exception e) {
            System.out.println("EXCEPTIOOOOON : " + e.getMessage());
            s.getTransaction().rollback();
        }

        return ls;
    

    }

    @Override
    public LoyerChantier findOneLCById(Integer id) {
          LoyerChantier lc = null;
        System.out.println("LOYER CHANTIER DAO GET ONE --- *** ");
        Session s = this.getHibernateTemplate().getSessionFactory().openSession();
        try {
           
            s.getTransaction().begin();

            String hql = "select lc from Loyer lc where lc.id =" + id;

            Query query = s.createQuery(hql);

            lc = (LoyerChantier) query.uniqueResult();
            
            
            System.out.println("RETRIEVED FROM LOYER DAO LC ::: ");

            s.getTransaction().commit();
            s.close();
        } catch (Exception e) {
            System.out.println("EXCEPTION LC : " + e.getMessage());
            s.getTransaction().rollback();
        }

        return lc;
    }

    @Override
    public void update(Loyer ls) {
        System.out.println("UPDATING LS ...");
        Transaction tx = null;
        Session session = getSessionFactory().openSession();
        try {
            session.setFlushMode(FlushMode.AUTO);
            tx = session.beginTransaction();
            session.update(ls);
            tx.commit();
            session.close();
        } catch (Exception exp) {
            System.out.println("EXCEPTION UPDATING LS: " + exp.getMessage());
            tx.rollback();
            session.close();
            
        }
        
    }
    
    @Override
    public List<Proprietaire> getListproPrietaireDistinct() {
        List l = this.getHibernateTemplate().find("FROM Proprietaire ");
        if (l.size() > 0) {
            return l;
        }

        return null;
    }

    @Override
    public List<FournisseurLoyer> listFournisseurLoyer() {
        List<FournisseurLoyer> l = new ArrayList<FournisseurLoyer>();
        try {
            l =  this.getHibernateTemplate().loadAll(FournisseurLoyer.class);
        } catch (Exception e) {
            System.out.println("Erreur de chargement liste des fournisseur car "+e.getMessage());
        } 
        return l;
    }
	
}
