/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import ma.bservices.beans.DocumentDetailAt;
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
@Repository("documentDetailATDao")
@Transactional
public class DocumentDetailAtDaoImp  extends MbHibernateDaoSupport implements IDocumentDetailAtDao, Serializable  {

    @Override
    public void addDocumentDetailAt(DocumentDetailAt d) {
        System.out.println("DAO--------------> addDocumentDetailAt "+d.toString());
          Transaction tx = null;
        Session session = getSessionFactory().openSession();
        try {
            session.setFlushMode(FlushMode.AUTO);
            tx = session.beginTransaction();
            session.save(d);
            System.out.println("DAO--------------> addDocumentDetailAt ok");
            tx.commit();
            session.close();
        } catch (Exception exp) {
            System.out.println("DAO--------------> addDocumentDetailAt error ");
            System.out.println("exception : " + exp.getMessage());
            tx.rollback();
            session.close();            
        }       
    }

    @Override
    public void editDocumentDetailAt(DocumentDetailAt d) {
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
    public void remouveDocumentDetailAt(DocumentDetailAt d) {
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
    public DocumentDetailAt infoDocumentDetailAtById(Long id) {
        DocumentDetailAt d = new DocumentDetailAt();
        try {
             d =  this.getHibernateTemplate().load(DocumentDetailAt.class, id);
        } catch (Exception e) {
            System.out.println("Erreur de récupération liste des documents Accidents de Travail car "+e.getMessage());
        }
        return d;
    }

    @Override
    public List<DocumentDetailAt> allDocumentDetailAt() {
        List<DocumentDetailAt>  l = new ArrayList<DocumentDetailAt>();
        try {
             l =  this.getHibernateTemplate().loadAll(DocumentDetailAt.class);
        } catch (Exception e) {
            System.out.println("Erreur de récupération liste des documents Accidents de Travail car "+e.getMessage());
        }
        return l;
    }

    @Override
    public List<DocumentDetailAt> allDocumentDetailAtByIdDetailAt(Long id) {
        List<DocumentDetailAt>  l = new ArrayList<DocumentDetailAt>();
            System.out.println("DAO-----------> 1 From DocumentDetailAt d where d.detailAT.id="+id);
        try {
            System.out.println("DAO-----------> 2 From DocumentDetailAt d where d.detailAT.id="+id);
             l =  (List<DocumentDetailAt>) this.getHibernateTemplate().find("SELECT d From DocumentDetailAt d where d.detailAT.id="+id);
        } catch (Exception e) {
            System.out.println("Erreur de récupération  documents car "+e.getMessage());
        }
        return l;
    }
    
}
