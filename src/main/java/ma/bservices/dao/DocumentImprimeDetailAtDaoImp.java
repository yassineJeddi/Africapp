/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import ma.bservices.beans.DocumentImprimeDetailAt;
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
@Repository("documentImprimeDetailATDao")
@Transactional
public class DocumentImprimeDetailAtDaoImp   extends MbHibernateDaoSupport implements IDocumentImprimeDetailAtDao, Serializable {

    @Override
    public void addDocumentDetailAt(DocumentImprimeDetailAt d) {
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
    public void editDocumentDetailAt(DocumentImprimeDetailAt d) {
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
    public void remouveDocumentDetailAt(DocumentImprimeDetailAt d) {
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
    public DocumentImprimeDetailAt infoDocumentDetailAtById(Long id) {
         DocumentImprimeDetailAt d = new DocumentImprimeDetailAt();
        try {
             d =  this.getHibernateTemplate().load(DocumentImprimeDetailAt.class, id);
        } catch (Exception e) {
            System.out.println("Erreur de récupération liste des documents Accidents de Travail car "+e.getMessage());
        }
        return d;
    }

    @Override
    public List<DocumentImprimeDetailAt> allDocumentDetailAt() {
         List<DocumentImprimeDetailAt>  l = new ArrayList<DocumentImprimeDetailAt>();
        try {
             l =  this.getHibernateTemplate().loadAll(DocumentImprimeDetailAt.class);
        } catch (Exception e) {
            System.out.println("Erreur de récupération liste des documents Accidents de Travail car "+e.getMessage());
        }
        return l;
    }

    @Override
    public List<DocumentImprimeDetailAt> allDocumentDetailAtByIdDetailAt(Long id) {
        List<DocumentImprimeDetailAt>  l = new ArrayList<DocumentImprimeDetailAt>();
        try {
             l =  (List<DocumentImprimeDetailAt>) this.getHibernateTemplate().find("SELECT d From DocumentImprimeDetailAt d where d.detailAT.id="+id);
        } catch (Exception e) {
            System.out.println("Erreur de récupération  documents car "+e.getMessage());
        }
        return l;
    }
    
}
