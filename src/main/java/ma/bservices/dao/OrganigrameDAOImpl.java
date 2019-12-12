/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.dao;

import java.io.Serializable;
import java.util.List;
import ma.bservices.beans.Chantier;
import ma.bservices.beans.NiveauFonction;
import ma.bservices.beans.Salarie;
import ma.bservices.mb.services.MbHibernateDaoSupport;
import ma.bservices.tgcc.Entity.Organigrame;
import org.hibernate.FlushMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author airaamane
 */
@Repository("organigrameDAO")
@Transactional
public class OrganigrameDAOImpl extends MbHibernateDaoSupport implements OrganigrameDAO, Serializable {

    @Override
    public List<Organigrame> findByChantier(Chantier chantier) {
        List l = this.getHibernateTemplate().find("SELECT org FROM Organigrame org WHERE org.chantier.id= " + chantier.getId());
        if (l.size() > 0) {
            return l;
        }
        return null;
    }
    
    
     @Override
    public List<Organigrame> findByChantierChef(Chantier chantier) {
        List l = this.getHibernateTemplate().find("SELECT org FROM Organigrame org WHERE org.chantier.id= " + chantier.getId() +  " AND org.isChef = 'true'");
        if (l.size() > 0) {
            return l;
        }
        return null;
    }
    
    
      @Override
    public List<Organigrame> findDistinctNiveauByChantier(Chantier chantier) {
        List l = this.getHibernateTemplate().find("SELECT DISTINCT org.niveau FROM Organigrame org WHERE org.chantier.id= " + chantier.getId());
        if (l.size() > 0) {
            return l;
        }
        return null;
    }

    @Override
    public void save(Organigrame org) {
        System.out.println("SAVING ...");
       // System.out.println("SALARIES : " + org.getSalaries().size());

         Transaction tx = null;
        Session session = getSessionFactory().openSession();
        try {
            session.setFlushMode(FlushMode.AUTO);
            tx = session.beginTransaction();
            session.save(session.merge(org));
            tx.commit();
            System.out.println("SAVED!");
            session.close();
        } catch (Exception exp) {
            System.out.println("ROLLED SAVE! EXCEPTION : " + exp.getMessage());
            tx.rollback();
            session.close();
        }

    }

    @Override
    public void update(Organigrame org) {

        Transaction tx = null;
        Session session = getSessionFactory().openSession();
        try {
            session.setFlushMode(FlushMode.AUTO);
            tx = session.beginTransaction();
            session.update(session.merge(org));
            tx.commit();
            System.out.println("UPDATED!");
            session.close();
        } catch (Exception exp) {
            System.out.println("ROLLED UPDATE!" +  exp.getMessage());
            tx.rollback();
            session.close();
        }
    }
    
    
     @Override
    public void delete(Organigrame org) {
        Session s = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
        Query query = s.createQuery("DELETE FROM Organigrame org WHERE org.id= " + org.getId());
       // query.setParameter("id", id);
        int deleted = query.executeUpdate();
        System.out.println("value_____" + deleted);
     

    }

    @Override
    public List<Integer> findByChantierNiveau(Chantier chantier, NiveauFonction niveau) {
        List l = this.getHibernateTemplate().find("SELECT org.salarie.id FROM Organigrame org WHERE org.chantier.id= " + chantier.getId() + " AND org.niveau.id = " + niveau.getId());
        if (l.size() > 0) {
            return  l;
        }
        return null;
    }
    
     @Override
    public List<Organigrame> findOrgsByChantierNiveau(Chantier chantier, NiveauFonction niveau) {
        List l = this.getHibernateTemplate().find("SELECT org FROM Organigrame org WHERE org.chantier.id= " + chantier.getId() + " AND org.niveau.id = " + niveau.getId());
        if (l.size() > 0) {
            return  l;
        }
        return null;
    }
    
    
     @Override
    public boolean deleteOrgsByChantierNiveau(Chantier chantier, NiveauFonction niveau) {
      //  List l = this.getHibernateTemplate().find("DELETE FROM Organigrame org WHERE org.chantier.id= " + chantier.getId() + " AND org.niveau.id = " + niveau.getId());
        Session s = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
        Query query = s.createQuery("DELETE FROM Organigrame org WHERE org.chantier.id= " + chantier.getId() + " AND org.niveau.id = " + niveau.getId());
       // query.setParameter("id", id);
        int deleted = query.executeUpdate();
        System.out.println("value_____" + deleted);
        return true;
    }
    
      @Override
    public List<Organigrame> findByChantierNiveauChef(Chantier chantier, NiveauFonction niveau) {
        List l = this.getHibernateTemplate().find("SELECT org FROM Organigrame org WHERE org.chantier.id= " + chantier.getId() + " AND org.niveau.id = " + niveau.getId() + " AND org.isChef = 'true'");
        if (l.size() > 0) {
            return  l;
        }
        return null;
    }
    
    @Override
    public Organigrame findByChantierNiveauSalarie(Chantier chantier, NiveauFonction niveau, Salarie salarie) {
        List l = this.getHibernateTemplate().find("SELECT org FROM Organigrame org WHERE org.chantier.id= " + chantier.getId() + " AND org.niveau.id = " + niveau.getId() + " AND org.salarie.id = " + salarie.getId());
        if (l.size() > 0) {
            return  (Organigrame) l.get(0);
        }
        return null;
    }

}
