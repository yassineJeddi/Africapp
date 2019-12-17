/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import ma.bservices.beans.DetailAT;
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
@Repository("detailATDao")
@Transactional
public class DetailATDaoImp  extends MbHibernateDaoSupport implements IDetailATDao, Serializable {

    @Override
    public void addDetailAT(DetailAT d) {
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
    public void editDetailAT(DetailAT d) {
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
    public void remouveDetailAT(DetailAT d) {
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
    public List<DetailAT> allDetailAT() {
         List<DetailAT>  l = new ArrayList<DetailAT>();
        try {
             l =  this.getHibernateTemplate().loadAll(DetailAT.class);
        } catch (Exception e) {
            System.out.println("Erreur de récupération liste des Accidents de Travail car "+e.getMessage());
        }
        return l;
    }

    @Override
    public DetailAT detailATById(Long id) {
          DetailAT  l = new DetailAT();
        try {
             l =  this.getHibernateTemplate().load(DetailAT.class, id);
        } catch (Exception e) {
            System.out.println("Erreur de récupération  DetailAT car "+e.getMessage());
        }
        return l;
    }

    @Override
    public List<DetailAT> allDetailATByAtId(Long id) {
        List<DetailAT>  l = new ArrayList<DetailAT>();
        try {
             l =  (List<DetailAT>) this.getHibernateTemplate().find("From DetailAT d where d.accidentTravail.id="+id);
        } catch (Exception e) {
            System.out.println("Erreur de récupération  DetailAT car "+e.getMessage());
        }
        return l;        
    }
    
}
