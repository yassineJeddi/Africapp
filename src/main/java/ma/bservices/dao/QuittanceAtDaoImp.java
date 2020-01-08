/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import ma.bservices.beans.AccidentTravail;
import ma.bservices.beans.QuittanceAt;
import ma.bservices.mb.services.MbHibernateDaoSupport;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author yassine
 */
@Repository("quittanceAtDao")
@Transactional
public class QuittanceAtDaoImp   extends MbHibernateDaoSupport implements IQuittanceAtDao, Serializable {

    @Override
    public void addQuittanceAt(QuittanceAt d) {
        Transaction tx = null;
        Session session = getSessionFactory().openSession();
        try {
            session.setFlushMode(FlushMode.AUTO);
            tx = session.beginTransaction();
            session.save(d);
            tx.commit();
            session.close();
        } catch (Exception exp) {
            System.out.println("exception : " + exp.getMessage());
            tx.rollback();
            session.close();            
        }
    }

    @Override
    public void editQuittanceAt(QuittanceAt d) {
        Transaction tx = null;
        Session session = getSessionFactory().openSession();
        try {
            session.setFlushMode(FlushMode.AUTO);
            tx = session.beginTransaction();
            session.merge(d);
            tx.commit();
            session.close();
        } catch (Exception exp) {
            System.out.println("exception : " + exp.getMessage());
            tx.rollback();
            session.close();            
        } 
    }

    @Override
    public void remouvQuittanceAt(QuittanceAt d) {
        Transaction tx = null;
        Session session = getSessionFactory().openSession();
        try {
            session.setFlushMode(FlushMode.AUTO);
            tx = session.beginTransaction();
            session.delete(d);
            tx.commit();
            session.close();
        } catch (Exception exp) {
            System.out.println("exception : " + exp.getMessage());
            tx.rollback();
            session.close();            
        }
    }

    @Override
    public QuittanceAt quittanceAtById(Integer id) {
        QuittanceAt d = new QuittanceAt();
        try {
             d =  this.getHibernateTemplate().load(QuittanceAt.class, id);
        } catch (Exception e) {
            System.out.println("Erreur de récupération liste des documents Accidents de Travail car "+e.getMessage());
        }
        return d;
    }

    @Override
    public List<QuittanceAt> allQuittanceAtByAt(AccidentTravail a) {
        List<QuittanceAt>  l = new ArrayList<QuittanceAt>();
        try {
             l =  (List<QuittanceAt>) this.getHibernateTemplate().find("SELECT d From QuittanceAt d where d.at.id="+a.getId());
        } catch (Exception e) {
            System.out.println("Erreur de récupération  QuittanceAt car "+e.getMessage());
        }
        return l;
    }
    
}
