/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import ma.bservices.beans.Chantier;
import ma.bservices.beans.ChantierAffinite;
import ma.bservices.mb.services.MbHibernateDaoSupport;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author airaamane
 */

@Repository("ChantierAffiniteDAO")
@Transactional

public class ChantierAffiniteDAOImpl extends MbHibernateDaoSupport implements ChantierAffiniteDAO, Serializable {

    @Override
    public List<ChantierAffinite> findAll() {
        return this.getHibernateTemplate().loadAll(ChantierAffinite.class);
    }

    @Override
    public void save(ChantierAffinite c) {
        Transaction tx = null;
        Session session = getSessionFactory().openSession();
        try {
            session.setFlushMode(FlushMode.AUTO);
            tx = session.beginTransaction();
            session.save(c);
            tx.commit();
            session.close();
        } catch (Exception exp) {
            System.out.println("exception : " + exp.getMessage());
            tx.rollback();
            session.close();            
        }       
    }

    @Override
    public void update(ChantierAffinite c) {
         Transaction tx = null;
        Session session = getSessionFactory().openSession();
        try {
            session.setFlushMode(FlushMode.AUTO);
            tx = session.beginTransaction();
            session.update(c);
            tx.commit();
            session.close();
        } catch (Exception exp) {
            System.out.println("exception : " + exp.getMessage());
            tx.rollback();
            session.close();            
        }       
    }

    @Override
    public List<ChantierAffinite> findByChantier(Integer c) {
       
        List<ChantierAffinite> l = new ArrayList<ChantierAffinite>();
        try { 
            l = (List<ChantierAffinite>) this.getHibernateTemplate().find("SELECT a FROM ChantierAffinite a where a.chantier.id = " + c);
        } catch (Exception e) {
            System.out.println("Erreur de récupération du ChantierAffinite car : "+e.getMessage());
        }
        return l;
    }
    
}
