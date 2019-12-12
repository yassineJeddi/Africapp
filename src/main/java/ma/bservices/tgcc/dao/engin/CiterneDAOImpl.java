/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.dao.engin;

import java.io.Serializable;
import java.util.ArrayList;
import static java.util.Calendar.DATE;
import java.util.Date;
import java.util.List;
import ma.bservices.tgcc.Entity.Bon_Livraison_Citerne;
import ma.bservices.tgcc.Entity.Citerne;
import ma.bservices.tgcc.Entity.Engin;
import ma.bservices.tgcc.Entity.TraceCiterne;
import ma.bservices.tgcc.utilitaire.MbHibernateDaoSupport;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author a.wattah
 */
@Repository("CiterneDAO")
@Transactional
public class CiterneDAOImpl extends MbHibernateDaoSupport implements CiterneDAO, Serializable {

    @Override
    public Citerne findCiternById(int id) {
         return this.getHibernateTemplate().get(Citerne.class, id);
    }
    
    @Override
    public List<Citerne> find_all_Citerne() {
        return this.getHibernateTemplate().loadAll(Citerne.class);
    }

    @Override
    public void save_citerne(Citerne citerne) {

        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);

        session.beginTransaction();
        session.save(session.merge(citerne));

        session.getTransaction().commit();
        session.close();

    }

    @Override
    public void update_citerne(Citerne citerne) {

        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);

        session.beginTransaction();
        session.update(citerne);

        session.getTransaction().commit();
        session.close();

    }

    @Override
    public void save_bon_caisse_citerne(Bon_Livraison_Citerne bon_Livraison_Citerne) {

        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);

        session.beginTransaction();
        session.save(bon_Livraison_Citerne);

        session.getTransaction().commit();
        session.close();

    }

    @Override
    public void delete(Citerne citerne) {

        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);

        session.beginTransaction();

        session.delete(citerne);

        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void merge_citerne(Citerne citerne) {

        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);

        session.beginTransaction();
        session.update(session.merge(citerne));

        session.getTransaction().commit();
        session.close();

    }

    @Override
    public List<Citerne> find_allCiterneNon_archiver() {

        List<Citerne>  l = new ArrayList<Citerne>();
        try {
            l = (List<Citerne>) this.getHibernateTemplate().find("From Citerne c where c.archiver is null  or c.archiver = false");
        } catch (Exception e) {
            System.out.println("Erreur de recuperation liste des citerne non Archive car "+e.getMessage());
        }
        
        return l;
    }

    @Override
    public List<Engin> getEnginByChantierId(int id) {

        List l = null;

        l = this.getHibernateTemplate().find("From Engin e where e.prjapId.id = " + id);

        return l;
    }

    /************ GESION TraceCiterne *********************/
    
    @Override
    public void addTraceCiterne(TraceCiterne t) {
        
        
        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);

        session.beginTransaction();
        t.setDateOperation(new Date());
        session.save( session.merge(t));

        session.getTransaction().commit();
        session.close(); 
    }

    @Override
    public void editTraceCiterne(TraceCiterne t) {
        
        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);

        session.beginTransaction();
        session.update(session.merge(t));

        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void remouvTraceCiterne(TraceCiterne t) {
        this.getHibernateTemplate().delete(t);
    }

    @Override
    public TraceCiterne findTraceCiterneById(int id) {
        return this.getHibernateTemplate().get(TraceCiterne.class, id);
    }

    @Override
    public List<TraceCiterne> findAllTraceCiterne() {
        List<TraceCiterne> l = new ArrayList<TraceCiterne>();
        try {
            l=this.getHibernateTemplate().loadAll(TraceCiterne.class);
        } catch (Exception e) {
            System.out.println("Erreur de chargement Liste des citernes car "+e.getMessage());
        }
        return l;
    }

    @Override
    public List<TraceCiterne> findAllTraceCiterneByCiterne(Citerne c) {
        List<TraceCiterne> l = new ArrayList<TraceCiterne>();
        try {
            l=(List<TraceCiterne>) this.getHibernateTemplate().find("From TraceCiterne t where t.citernSrc.id = " + c.getId());
        } catch (Exception e) {
            System.out.println("Erreur de chargement Liste des citernes car "+e.getMessage());
        }
        return l;
    }

    @Override
    public List<TraceCiterne> findAllTraceCiterneByCiterneDist(Citerne c) {
        List<TraceCiterne> l = new ArrayList<TraceCiterne>();
        try {
            l=(List<TraceCiterne>) this.getHibernateTemplate().find("From TraceCiterne t where t.citernDist.id = " + c.getId());
        } catch (Exception e) {
            System.out.println("Erreur de chargement Liste des citernes car "+e.getMessage());
        }
        return l;
    }


    
}
