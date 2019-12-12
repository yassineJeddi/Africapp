/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.dao.Mensuel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import ma.bservices.tgcc.Entity.TAUXHORAIRE;
import ma.bservices.tgcc.utilitaire.MbHibernateDaoSupport;
import org.hibernate.FlushMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author yassine.jeddi
 */

@Repository("tauxHoraireDAO")
@Transactional
public class TauxHoraireDAOImp  extends MbHibernateDaoSupport  implements TauxHoraireDAO, Serializable {

    @Override
    public List<TAUXHORAIRE> findAll() {
        List<TAUXHORAIRE> l = new ArrayList<TAUXHORAIRE>();
        try {
            l=this.getHibernateTemplate().loadAll(TAUXHORAIRE.class);
        } catch (Exception e) {
            System.out.println("Erreur de recuperation de la liste "+e.getMessage());
        }
        return l;
    }

    @Override
    public void addTaux(TAUXHORAIRE t) {
        
        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);

        session.beginTransaction();
        session.save(t);

        session.getTransaction().commit();

        session.close();
    }

    @Override
    public void updateTaux(TAUXHORAIRE t) {
        
        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);

        session.beginTransaction();
        session.update(session.merge(t));

        session.getTransaction().commit();

        session.close();
    }

    @Override
    public void removeTaux(TAUXHORAIRE t) {
        
        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);

        session.beginTransaction();
        session.delete(t);

        session.getTransaction().commit();

        session.close();
    }

    @Override
    public TAUXHORAIRE findByCode(String code) {
        
        TAUXHORAIRE t = new TAUXHORAIRE();

        Session s = this.getHibernateTemplate().getSessionFactory().openSession();
        try {

            s.getTransaction().begin();

            String hql = "select t From TAUXHORAIRE t  where t.EMPLOICOD = '"+code+"'";

            Query query = s.createQuery(hql);

           if(query.list().size()>0){
               t=(TAUXHORAIRE) query.list().get(0);
           }
            s.getTransaction().commit();
            s.close();
        } catch (Exception e) { 
            System.out.println("Erreur de recuperation de TAUXHORAIRE par Code car: "+e.getMessage());
        }
        return t;

    }

    @Override
    public TAUXHORAIRE findById(int id) {
         
        TAUXHORAIRE t = new TAUXHORAIRE();

        Session s = this.getHibernateTemplate().getSessionFactory().openSession();
        try {

            s.getTransaction().begin();

            String hql = "select t From TAUXHORAIRE t  where t.ID = " + id;

            Query query = s.createQuery(hql);

           if(query.list().size()>0){
               t=(TAUXHORAIRE) query.list().get(0);
           }
            s.getTransaction().commit();
            s.close();
        } catch (Exception e) { 
            System.out.println("Erreur de recuperation de TAUXHORAIRE par ID  car: "+e.getMessage());
        }
        return t;
    }
    
}
