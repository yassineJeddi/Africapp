/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.dao.engin;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import ma.bservices.beans.Chantier;
import ma.bservices.beans.Zone;
import ma.bservices.tgcc.Entity.Engin;
import ma.bservices.tgcc.Entity.PointageEngin;
import ma.bservices.tgcc.utilitaire.MbHibernateDaoSupport;
import ma.bservices.tgcc.utilitaire.SearchU;
import org.hibernate.FlushMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

/**
 *
 * @author j.allali
 */
@Repository("pointageEnginDAO")
public class PointageEnginDAOImpl extends MbHibernateDaoSupport implements PointageEnginDAO, Serializable {

    private final static String HQL_instance = "p";

    @Override
    public List<PointageEngin> findAll() {
        return this.getHibernateTemplate().loadAll(PointageEngin.class);
    }

    /**
     *
     * Retourne le résultat de la recherche sur la liste des engins
     *
     * @param code
     * @param Designation
     * @return List<PointageEngin>
     */
    @Override
    public List<PointageEngin> RechercheEngin(String code, String Designation, String marque, Date date_from, Date date_to, int chantier) {
        /* 
         Query to get pointageEngin par code,designation,date,marque
         */

        String filtre = createSearchFiltre(code, Designation, marque, date_from, date_to, chantier);

        System.out.println("les filtre : " + filtre);
        List<PointageEngin>  l = new ArrayList<PointageEngin>();
        try {
             l = (List<PointageEngin>) this.getHibernateTemplate().find("From PointageEngin " + HQL_instance + " " + filtre);
            } catch (Exception e) {
                System.out.println("Erreur de récupération de la listes des engin car : "+e.getMessage());
        }
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
    static String createSearchFiltre(String code, String Designation, String marque, Date date_from, Date date_to, int idChantier) {

        String filtre = "";
        Boolean filtre_bool_where = false;
        String filtre_one;
        //creation du filre pour le code

        filtre_one = SearchU.createOneFilre("iDEngin.code", code, filtre_bool_where, HQL_instance);
        if (filtre_one != null) {
            filtre += filtre_one;
            filtre_bool_where = true;
        }

        filtre_one = SearchU.createOneFilre("iDEngin.designation", Designation, filtre_bool_where, HQL_instance);
        if (filtre_one != null) {
            filtre += filtre_one;
            filtre_bool_where = true;
        }

        filtre_one = SearchU.createOneFilre("iDEngin.marque", marque, filtre_bool_where, HQL_instance);
        if (filtre_one != null) {
            filtre += filtre_one;
            filtre_bool_where = true;
        }

        filtre_one = SearchU.createOneFilre("chantierPointage.id", "" + idChantier, filtre_bool_where, HQL_instance);
        if (filtre_one != null) {
            filtre += filtre_one;
            filtre_bool_where = true;
        }
        
        String dateFromString = "";
        String dateToString = "";
        if (date_from != null && date_to != null) {
            DateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");

            dateFromString = formatDate.format(date_from);
            dateToString = formatDate.format(date_to);
        }

        filtre_one = SearchU.createOneFilre_betweenTwoDates("dateCreation", dateFromString, dateToString, filtre_bool_where, HQL_instance);
        if (filtre_one != null) {
            filtre += filtre_one;
            filtre_bool_where = true;
        }

        return filtre;

    }

    @Override
    public void saveListPointage(List<PointageEngin> pe){
        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);
        session.beginTransaction();
        try {
            for(PointageEngin p : pe){
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
    public Boolean save(PointageEngin pe) {

        System.out.println("in the save method");
//        Session session = getSessionFactory().getCurrentSession();
        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.COMMIT);

        session.beginTransaction();
        session.save(session.merge(pe));
        session.getTransaction().commit();

        session.close();
        return true;
    }

    @Override
    public List<PointageEngin> getPointageEnginByDateAndChantierId(int chantier_id, String day, String day_tom) {

        List l = new ArrayList<PointageEngin>();
        try {
            l = this.getHibernateTemplate().find("From PointageEngin p WHERE p.chantierPointage.id = " + chantier_id + " and p.dateCreation >= '" + day + "' and p.dateCreation < '" + day_tom + "'");
        } catch (Exception e) {
           System.out.println("Erreur de récuperation de getPointageEnginByDateAndChantierId car : "+e.getLocalizedMessage());
        }
       
            return l;
    }

    @Override
    public List<Zone> getZoneList(Integer id_pointage) {

        List l = null;
        l = this.getHibernateTemplate().find("SELECT z From PointageEngin p join p.zoneList z WHERE p.idPoinEng = " + id_pointage + "");

        if (l.size() > 0) {
            return l;
        }

        return null;
    }

    /**
     * retourne le dernier pointage lié à un chantier C'est pour autoriser de
     * pointer un jour dans une date supérieur à la date du dernier pointage
     *
     * @param chantier_id
     * @return
     */
    @Override
    public PointageEngin getLastPointageEnginByChantierId(int chantier_id) {
        PointageEngin p = new PointageEngin();
        try {
        
        List l = null;
        l = this.getHibernateTemplate().find("SELECT p From PointageEngin p "
                        + " WHERE  p.chantierPointage.id = " + chantier_id + " "
                        + " AND p.dateCreation =( SELECT MAX(pp.dateCreation) "
                        + " FROM PointageEngin pp WHERE pp.chantierPointage.id = " + chantier_id + " )");
               // + "ORDER BY p.dateCreation DESC)");

        if (l.size() > 0) {
            p = (PointageEngin) l.get(0);
        }
    
        } catch (Exception e) {
            System.out.println("Erreur de recuperation du getLastPointageEnginByChantierId car ");
        }
        return p;
    }

    @Override
    public void updateEtat(Engin peUpdate) {
        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);

        session.beginTransaction();
        session.update(peUpdate);

        session.getTransaction().commit();

        session.close();
    }

    @Override
    public Engin addEngin(String code) {
        Engin engin = new Engin();

//        Session s = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
        Session s = this.getHibernateTemplate().getSessionFactory().openSession();
        try {
            //demarrage de la session
            s.getTransaction().begin();
            Query query = s.createQuery("From Engin e WHERE e.code = :code");
            query.setParameter("code", code);
            engin = (Engin) query.list().get(0);
            //execution de la requete
            s.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            s.getTransaction().rollback();
        }
        return engin;
    }

    @Override
    public Chantier addChantier(String chantier) {
        Chantier chantierr = new Chantier();

//        Session s = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
        Session s = this.getHibernateTemplate().getSessionFactory().openSession();
        try {
            //demarrage de la session
            s.getTransaction().begin();
            Query query = s.createQuery("From Chantier c WHERE c.code = :chantier");
            query.setParameter("chantier", chantier);
            chantierr = (Chantier) query.list().get(0);
            //execution de la requete
            s.getTransaction().commit();
            s.close();
        } catch (Exception e) {
            e.printStackTrace();
            s.getTransaction().rollback();
        }
        return chantierr;
    }

    @Override
    public List<Engin> findAllPanne() {
        List<Engin> pointageEngin = new ArrayList<>();

//        Session s = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
        Session s = this.getHibernateTemplate().getSessionFactory().openSession();
        try {
            //demarrage de la session
            s.getTransaction().begin();
            Query query = s.createQuery("From Engin p WHERE p.etat = :etat");
            query.setParameter("etat", "Panne");
            pointageEngin = query.list();
            //execution de la requete
            s.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            s.getTransaction().rollback();
        }
        return pointageEngin;
    }

    @Override
    public List<Engin> getCode(String code) {
        List<Engin> PE = null;

//        Session s = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
        Session s = this.getHibernateTemplate().getSessionFactory().openSession();
        try {
            //demarrage de la session
            s.getTransaction().begin();
            Query query = s.createQuery("FROM Engin p WHERE p.code= :code");
            query.setParameter("code", code);
            PE = query.list();
            //execution de la requete
            s.getTransaction().commit();
            s.close();
        } catch (Exception e) {
            e.printStackTrace();
            s.getTransaction().rollback();
        }
        return PE;
    }

    @Override
    public void addEnginPanne(Engin poiE) {
        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);

        session.beginTransaction();
        session.save(poiE);

        session.getTransaction().commit();

        session.close();

    }

    @Override
    public Boolean delete(Engin g) {
        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);

        session.beginTransaction();
        session.delete(g);

        session.getTransaction().commit();

        session.close();
        return true;
    }

    @Override
    public Boolean deletePE(int id) {
        Session sess = getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = sess.beginTransaction();
            Query query = sess.createQuery("delete from PointageEngin where iDEngin.iDEngin=:id");
            query.setParameter("id", id);
            int deleted = query.executeUpdate();
            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e; // or display error message
        } finally {
            sess.close();
        }
        return true;
    }

    /*
     * save pointage engin test unitaire 
     */
    @Override
    public Boolean saveTestUnitaire(PointageEngin pe) {

//        Session session = getSessionFactory().getCurrentSession();
        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.COMMIT);

        session.beginTransaction();
        session.save(pe);
        session.getTransaction().commit();

        session.close();
        return true;
    }

    @Override
    public List<PointageEngin> getPointageEnginByDate_Diff_Depo(int idEngin) {
        List l = new ArrayList<PointageEngin>();
        try {
            System.out.println("From PointageEngin p WHERE p.chantierPointage.id NOT like '%DEPOT%' and p.iDEngin.iDEngin = " + idEngin + " order by p.dateCreation desc");
            l = this.getHibernateTemplate().find("From PointageEngin p WHERE p.chantierPointage.id NOT like '%DEPOT%' and p.iDEngin.iDEngin = " + idEngin + " order by p.dateCreation desc");

        } catch (Exception e) {
            System.out.println("Erreur de recuperation List PointageEngin car : "+e.getMessage());
        }

        return l;
    }

    @Override
    public PointageEngin lastPointageEngin(Integer idChantier, Integer idEngin) {
        List<PointageEngin> l = (List<PointageEngin>) this.getHibernateTemplate().find("From PointageEngin p WHERE p.chantierPointage.id = " + idChantier + " and p.iDEngin.iDEngin = " + idEngin + " order by p.dateCreation desc");

       /* List<PointageEngin> l = (List<PointageEngin>) this.getHibernateTemplate().find("select p From POINTAGE_ENGIN p " +
                                                                                        "WHERE p.chantierPointage.id=" + idChantier + " and p.iDEngin.iDEngin = " + idEngin  +
                                                                                        "and p.DATE_CREATION=(select MAX(DATE_CREATION) "+ 
                                                                                        "from POINTAGE_ENGIN WHERE p.chantierPointage.id=" + idChantier + " and p.iDEngin.iDEngin = " + idEngin + ")");*/
        if (l.size() > 0) {
            return l.get(0);
        }

        return null;
    }
}
