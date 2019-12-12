/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.dao.Mensuel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import ma.bservices.beans.Fonction;
import ma.bservices.tgcc.utilitaire.MbHibernateDaoSupport;
import org.hibernate.FlushMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author j.allali
 */
@Repository("fonctionDAO")
@Transactional
public class FonctionDAOImpl extends MbHibernateDaoSupport implements FonctionDAO, Serializable {

    @Override
    public List<Fonction> findAll() {
         List<Fonction>  l = new ArrayList<Fonction> ();
         try {
              l= this.getHibernateTemplate().loadAll(Fonction.class);
        } catch (Exception e) {
             System.out.println("Erreur de recuperation touts les fonction car "+e.getMessage());
        }
         return l;
    }

    @Override
    public void delete(Fonction element) {

        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);

        session.beginTransaction();
        session.delete(element);

        session.getTransaction().commit();

        session.close();

    }

    @Override
    public void save(Fonction element) {

        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);

        session.beginTransaction();
        session.save(element);

        session.getTransaction().commit();

        session.close();

    }

    @Override
    public void update(Fonction func) {

        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);

        session.beginTransaction();
        session.update(session.merge(func));

        session.getTransaction().commit();

        session.close();

    }

    /**
     * methode qui permet de returne list des focntions ayant docObligatoire
     *
     * @param id
     * @return
     */
    @Override
    public List<Fonction> l_FonctionsByTypeDoc(int id) {

        List l = null;

        Session s = this.getHibernateTemplate().getSessionFactory().openSession();
        try {

            s.getTransaction().begin();

            String hql = "select f From Fonction f join f.typeDocuments t where t.id = " + id;

            Query query = s.createQuery(hql);

            l = query.list();

            s.getTransaction().commit();
            s.close();
        } catch (Exception e) {

            s.getTransaction().rollback();
        }

        return l;

    }

    @Override
    public Fonction getFonctionById(int id) {

        List l = null;

        l = this.getHibernateTemplate().find("From Fonction f where f = " + id);

        if (l.size() > 0) {
            return (Fonction) l.get(0);
        }
        return null;
    }

    @Override
    public Fonction findByCode(String code) {

        List l = null;

        l = this.getHibernateTemplate().find("SELECT f From Fonction f where f.codeDiva = '" + code + "'");

        if (l.size() > 0) {
            return (Fonction) l.get(0);
        }
        return null;
    }

    @Override
    public void importFonctionDiva() {
        
        Session s = this.getHibernateTemplate().getSessionFactory().openSession();
        try {

            s.getTransaction().begin();

            String hql = "call PS_importFonctionDiva()";

            Query query = s.createQuery(hql);

            int i = query.executeUpdate();
            System.out.println("::::::::::::::::> i : "+i);
            s.getTransaction().commit();
            s.close();
        } catch (Exception e) {
            System.out.println("Erreur d'execution la procedure stoke d'importation des fonctions car "+e.getMessage());
        }
 
    }

}
