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
import ma.bservices.beans.Chantier;
import ma.bservices.beans.Salarie;
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

@Repository("accidentTravailDao")
@Transactional
public class AccidentTravailDaoImp extends MbHibernateDaoSupport implements IAccidentTravailDao, Serializable{


    @Override
    public void addAccidentTravail(AccidentTravail a) {
        Transaction tx = null;
        Session session = getSessionFactory().openSession();
        try {
            session.setFlushMode(FlushMode.AUTO);
            tx = session.beginTransaction();
            session.save(a);
            tx.commit();
            session.close();
        } catch (Exception exp) {
            System.out.println("exception : " + exp.getMessage());
            tx.rollback();
            session.close();            
        }       
    }

    @Override
    public void editAccidentTravail(AccidentTravail a) {
        Transaction tx = null;
        Session session = getSessionFactory().openSession();
        try {
            session.setFlushMode(FlushMode.AUTO);
            tx = session.beginTransaction();
            session.merge(a);
            tx.commit();
            session.close();
        } catch (Exception exp) {
            System.out.println("exception : " + exp.getMessage());
            tx.rollback();
            session.close();            
        } 
    }

    @Override
    public void remouveAccidentTravail(AccidentTravail a) {
        Transaction tx = null;
        Session session = getSessionFactory().openSession();
        try {
            session.setFlushMode(FlushMode.AUTO);
            tx = session.beginTransaction();
            session.delete(a);
            tx.commit();
            session.close();
        } catch (Exception exp) {
            System.out.println("exception : " + exp.getMessage());
            tx.rollback();
            session.close();            
        } 
    }

    @Override
    public AccidentTravail allAccidentTravailById(Long id) {
         AccidentTravail  l = new AccidentTravail();
        try {
             l =  this.getHibernateTemplate().load(AccidentTravail.class, id);
        } catch (Exception e) {
            System.out.println("Erreur de récupération  Accident de Travail car "+e.getMessage());
        }
        return l;
    }

    @Override
    public List<AccidentTravail> allAccidentTravail() {
        List<AccidentTravail>  l = new ArrayList<AccidentTravail>();
        try {
             l =  this.getHibernateTemplate().loadAll(AccidentTravail.class);
        } catch (Exception e) {
            System.out.println("Erreur de récupération liste des Accidents de Travail car "+e.getMessage());
        }
        return l;
    }


    @Override
    public List<AccidentTravail> allAccidentTravailByChantier(Chantier c) {
        List<AccidentTravail>  l = new ArrayList<AccidentTravail>();
        try {
             l = (List<AccidentTravail>) this.getHibernateTemplate().find(" FROM AccidentTravail a where a.chantier.id = " + c.getId());
        } catch (Exception e) {
            System.out.println("Erreur de récupération liste des Accidents de Travail par chantier car "+e.getMessage());
        }
        return l;
    }

    @Override
    public List<AccidentTravail> allAccidentTravailBySalarie(Salarie s) {
        List<AccidentTravail>  l = new ArrayList<AccidentTravail>();
        try {
             l = (List<AccidentTravail>) this.getHibernateTemplate().find(" FROM AccidentTravail a where a.salarie.id = " + s.getId());
        } catch (Exception e) {
            System.out.println("Erreur de récupération liste des Accidents de Travail par salarie car "+e.getMessage());
        }
        return l;
    }
    
}
