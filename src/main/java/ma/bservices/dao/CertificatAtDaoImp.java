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
import ma.bservices.beans.CertificatAt;
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
@Repository("certificatAtDao")
@Transactional
public class CertificatAtDaoImp  extends MbHibernateDaoSupport implements ICertificatAtDao, Serializable {

    @Override
    public void addCertificatAt(CertificatAt d) {
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
    public void editCertificatAt(CertificatAt d) {
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
    public void remouvCertificatAt(CertificatAt d) {
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
    public CertificatAt certificatAtById(Integer id) {
        CertificatAt c = new CertificatAt();
        try {
             c =  this.getHibernateTemplate().load(CertificatAt.class, id);
        } catch (Exception e) {
            System.out.println("Erreur de récupération liste des documents Accidents de Travail car "+e.getMessage());
        }
        return c;
    }

    @Override
    public List<CertificatAt> allCertificatAtByAt(AccidentTravail a) {
        List<CertificatAt>  l = new ArrayList<CertificatAt>();
        try {
             l =  (List<CertificatAt>) this.getHibernateTemplate().find("SELECT d From CertificatAt d where d.at.id="+a.getId());
        } catch (Exception e) {
            System.out.println("Erreur de récupération  QuittanceAt car "+e.getMessage());
        }
        return l;
    }
    
}
