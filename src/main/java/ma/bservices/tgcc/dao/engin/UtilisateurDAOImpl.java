/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.dao.engin;

import java.io.Serializable;
import java.util.List;
import ma.bservices.beans.Chantier;
import ma.bservices.beans.Groupe;
import ma.bservices.beans.Permission;
import ma.bservices.beans.Utilisateur;
import ma.bservices.tgcc.utilitaire.MbHibernateDaoSupport;
import ma.bservices.tgcc.utilitaire.SearchU;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author a.wattah
 */
@Repository("utilisateurDAO")
@Transactional
public class UtilisateurDAOImpl extends MbHibernateDaoSupport implements UtilisateurDAO, Serializable {

    private final static String HQL_instance = "u";

    @Override
    public List<Utilisateur> usersAll() {

        return this.getHibernateTemplate().loadAll(Utilisateur.class);
    }

    @Override
    public Utilisateur UserByLogin(final String login) {

        Session s = this.getHibernateTemplate().getSessionFactory().getCurrentSession();

        Query query = s.createQuery("FROM Utilisateur AS u WHERE u.login = :login");
        query.setParameter("login", login);
        if (query.list().size() > 0) {
            Utilisateur u = (Utilisateur) query.list().get(0);
            return u;
        }

        return null;
    }

    @Override
    public Utilisateur getUserByLogin(final String login) {

        Utilisateur u = new Utilisateur();

//        Session s = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
        Session s = this.getHibernateTemplate().getSessionFactory().openSession();
        try {
            //demarrage de la session
            Query query = s.createQuery("FROM Utilisateur u WHERE u.login = '" + login + "'");

            if (query.list().size() > 0) {

                System.out.println("entre 2 ");
                u = (Utilisateur) query.list().get(0);

                System.out.println("entre 3 : " + u.getLogin());
            }

            s.close();
        } catch (Exception e) {
            e.printStackTrace();
            s.getTransaction().rollback();
            s.close();
        }
        return u;
    }

    @Override
    public List<Utilisateur> usersByLNPG(String login, String nom, String prenom, String groupe) {

        System.out.println("recherche DAO IMP");
        /* 
         Query to get pointageEngin par code,designation,date,marque
         */

        //groupe
        System.out.println("login " + login + " nom " + nom);

        String filtre = createSearchFiltre(login, nom, prenom);

        System.out.println("les filtre : " + filtre);
        List liste = null;

        liste = this.getHibernateTemplate().find("From Utilisateur " + HQL_instance + " " + filtre);

        return liste;
    }

    static String createSearchFiltre(String login, String nom, String prenom) {

        String filtre = "";
        Boolean filtre_bool_where = false;
        String filtre_one;
        //creation du filre pour le code

        filtre_one = SearchU.createOneFilre("login", login, filtre_bool_where, HQL_instance);
        if (filtre_one != null) {
            filtre += filtre_one;
            filtre_bool_where = true;
        }

        filtre_one = SearchU.createOneFilre("nom", nom, filtre_bool_where, HQL_instance);
        if (filtre_one != null) {
            filtre += filtre_one;
            filtre_bool_where = true;
        }
        filtre_one = SearchU.createOneFilre("prenom", prenom, filtre_bool_where, HQL_instance);
        if (filtre_one != null) {
            filtre += filtre_one;
            filtre_bool_where = true;
        }

//        filtre_one = SearchU.createOneFilre("idGroupe.groupe", groupe, filtre_bool_where, HQL_instance);
//        if (filtre_one != null) {
//            filtre += filtre_one;
//            filtre_bool_where = true;
//        }
        return filtre;

    }

    @Override
    public void delete(int id) {

        System.out.println("rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr");
        Session s = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
        System.out.println("dddddddddddd :" + id);

        Query query = s.createQuery("delete from Utilisateur where id=:id");
        query.setParameter("id", id);
        int deleted = query.executeUpdate();
        System.out.println("Deleted: " + deleted + " user(s)");
        System.out.println("utilisateur  ------------" + id);

        //This makes the pending delete to be done
    }

    @Override
    public void addUser(Utilisateur utilisateur) {

        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);

        session.beginTransaction();
        session.save(utilisateur);

        session.getTransaction().commit();

        session.close();
    }
    
       @Override
    public void updateUser(Utilisateur utilisateur) {

        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);

        session.beginTransaction();
        session.update(session.merge(utilisateur));

        session.getTransaction().commit();

        session.close();
    }

    /**
     * Je l'ai ajouter lors du test unitaire pour ajouter un groupe et le tester
     * sans toucher les donner de la BD
     *
     * @param id
     * @return
     */
    @Override
    public void addGroupe(Groupe group) {

        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);

        session.beginTransaction();
        session.save(group);

        session.getTransaction().commit();

        session.close();
    }

    @Override
    public Groupe groupeByid(String id) {
        Groupe g = new Groupe();
        Session s = this.getHibernateTemplate().getSessionFactory().getCurrentSession();

        Query query = s.createQuery("FROM Groupe AS u WHERE u.groupe = :id");

        query.setParameter("id", id);
        System.out.println("login----------------------------" + id);
        g = (Groupe) query.list().get(0);
        System.out.println("utili  :::::----------------------------" + g);

        return g;
    }

    @Override
    public Boolean delete(Utilisateur id) {
        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);
        session.beginTransaction();
        session.delete(id);
        session.getTransaction().commit();
        return true;
    }

    @Override
    public void addGroupeToUser(Utilisateur user) {
        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);
        session.beginTransaction();
        session.update(session.merge(user));
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List<Chantier> findChantiersByUser(Utilisateur u) {

        List<Chantier> listOfCh = null;
        
        Session s = this.getHibernateTemplate().getSessionFactory().openSession();
        try {
            //demarrage de la session
            s.getTransaction().begin();

            String hql = "select c from Chantier c join c.utilisateurs l where l.id = " + u.getId();

            Query query = s.createQuery(hql);

            listOfCh = query.list();

            System.out.println("LIST OF CHANTIER GOT DAO ::: " + listOfCh);

            s.getTransaction().commit();
            s.close();
        } catch (Exception e) {

            s.getTransaction().rollback();
        }

        return listOfCh;
    }

    @Override
    public List<Permission> findPermissionsByUser(Utilisateur u) {
       List<Permission> listOfCh = null;
        
        Session s = this.getHibernateTemplate().getSessionFactory().openSession();
        try {
            //demarrage de la session
            s.getTransaction().begin();

            String hql = "select p from Permission p join p.utilisateurs l where l.id =" + u.getId();

            Query query = s.createQuery(hql);

            listOfCh = query.list();

            System.out.println("LIST OF PERMISSIONS GOT DAO ::: " + listOfCh);

            s.getTransaction().commit();
            s.close();
        } catch (Exception e) {

            s.getTransaction().rollback();
        }

        return listOfCh;
    }

    @Override
    public List<Groupe> findGroupsByUser(Utilisateur u) {
       List<Groupe> listOfGrp = null;
        
        Session s = this.getHibernateTemplate().getSessionFactory().openSession();
        try {
            //demarrage de la session
            s.getTransaction().begin();

            String hql = "select g from Groupe g join g.utilisateurs l where l.id =" + u.getId();

            Query query = s.createQuery(hql);

            listOfGrp = query.list();

            System.out.println("LIST OF PERMISSIONS GOT DAO ::: " + listOfGrp);

            s.getTransaction().commit();
            s.close();
        } catch (Exception e) {

            s.getTransaction().rollback();
        }

        return listOfGrp;
    }

}
