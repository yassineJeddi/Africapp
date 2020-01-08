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
import ma.bservices.beans.DocumentAt;
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
@Repository("documentAtDao")
@Transactional
public class DocumentAtDaoImp   extends MbHibernateDaoSupport implements IDocumentAtDao, Serializable{

    @Override
    public void addDocumentAt(DocumentAt d) {
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
    public void editDocumentAt(DocumentAt d) {
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
    public void remouvDocumentAt(DocumentAt d) {
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
    public DocumentAt documentAtById(Integer id) {
        DocumentAt d = new DocumentAt();
        try {
             d =  this.getHibernateTemplate().load(DocumentAt.class, id);
        } catch (Exception e) {
            System.out.println("Erreur de récupération liste des documents Accidents de Travail car "+e.getMessage());
        }
        return d;
    }

    @Override
    public List<DocumentAt> allDocumentAtByAt(AccidentTravail a) {
        List<DocumentAt>  l = new ArrayList<DocumentAt>();
        try {
             l =  (List<DocumentAt>) this.getHibernateTemplate().find("SELECT d From DocumentAt d where d.at.id="+a.getId());
        } catch (Exception e) {
            System.out.println("Erreur de récupération  documents car "+e.getMessage());
        }
        return l;
    }
    
}
