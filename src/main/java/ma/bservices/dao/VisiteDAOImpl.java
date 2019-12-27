/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import ma.bservices.mb.services.MbHibernateDaoSupport;
import ma.bservices.tgcc.Entity.DossierMedical;
import ma.bservices.tgcc.Entity.Visite;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author BARAKA
 */
@Repository("visiteDAO")
@Transactional
public class VisiteDAOImpl extends MbHibernateDaoSupport implements Serializable, VisiteDAO {

    @Override
    public Long addVisite(Visite visite) {
        Transaction tx = null;
        Session session = getSessionFactory().openSession();
        try {
            session.setFlushMode(FlushMode.AUTO);
            tx = session.beginTransaction();
            session.persist(visite);
            tx.commit();
            session.close();
        } catch (DataAccessException exp) {
            System.err.print("Exception " + exp.getMessage());
            tx.rollback();
            session.close();
            return null;
        }
        return visite.getId();
    }

    @Override
    public Long updateVisite(Visite visite) {
        Transaction tx = null;
        Session session = getSessionFactory().openSession();
        try {
            session.setFlushMode(FlushMode.AUTO);
            tx = session.beginTransaction();
            session.update(visite);
            tx.commit();
            session.close();
        } catch (DataAccessException exp) {
            System.err.print("Exception " + exp.getMessage());
            tx.rollback();
            session.close();
            return null;
        }
        return visite.getId();
    }

    @Override
    public boolean deleteVisite(Visite visite) {
        Transaction tx = null;
        Session session = getSessionFactory().openSession();
        try {
            session.setFlushMode(FlushMode.AUTO);
            tx = session.beginTransaction();
            session.delete(visite);
            tx.commit();
            session.close();
        } catch (DataAccessException exp) {
            System.err.print("Exception " + exp.getMessage());
            tx.rollback();
            session.close();
            return false;
        }
        return true;
    }

    @Override
    public List<Visite> findAllVisite() {
        try {
            List l = this.getHibernateTemplate().find("SELECT s FROM Visite s ");
            if (l.size() > 0) {
                return l;
        } 
        } catch (DataAccessException e) {
        System.err.print("Exception " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Visite> findVisiteByType(String type) {
        List<Visite> l = new ArrayList<>();  
        try {   
             String req=" FROM Visite v where v.type='"+type+"')";
             l = (List<Visite>) this.getHibernateTemplate().find(req);   

        } catch (DataAccessException e) {
            System.err.println("Erreur de récupération des salarier par Listechantiers "+e.getMessage());
        }   
        return l;
    }

    @Override
    public List<Visite> findVisitesByDossierMedical(DossierMedical dos) {
        List<Visite> l = new ArrayList<>();  
        try {   
             String req=" FROM Visite v where v.dossierMedical.id="+dos.getId();
             l = (List<Visite>) this.getHibernateTemplate().find(req);   

        } catch (DataAccessException e) {
            System.err.println("Erreur de récupération des salarier par Listechantiers "+e.getMessage());
        }   
        return l;
    }
    
    @Override
    public Date lastDateVisiteByDossier(DossierMedical dos) {
        Date l = null; 
        try {   
             String req="select vc.dateVisite from Visite vc where vc.id= (select MAX(v.id) FROM Visite v where v.dossierMedical.id="+dos.getId()+")";
             l = (Date) this.getHibernateTemplate().find(req);   
             System.out.println("lastDateVisiteByDossier ID: " + dos.getId() + " last date: " +l);
        } catch (DataAccessException e) {
            System.err.println("lastDateVisiteByDossier "+e.getMessage());
        }   
        return l;
    }
    
    @Override
    public String docteurLastVisiteByDossier(DossierMedical dos) {
        String l = null; 
        try {   
             String req="select vc.docteur from Visite vc where vc.id= (select MAX(v.id) FROM Visite v where v.dossierMedical.id="+dos.getId()+")";
             l = (String) this.getHibernateTemplate().find(req).toString();   
             System.out.println("docteurLastVisiteByDossier ID: " + dos.getId() + " Docteur: " +l);
        } catch (DataAccessException e) {
            System.err.println("lastDateVisiteByDossier "+e.getMessage());
        }   
        return l;
    }
}
