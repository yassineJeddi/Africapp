/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.dao;

import java.io.Serializable;
import java.util.List;
import ma.bservices.beans.NiveauFonction;
import ma.bservices.mb.services.MbHibernateDaoSupport;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author admin
 */
@Repository("niveauFonctionDao")
@Transactional
public class NiveauFonctionImpl extends MbHibernateDaoSupport implements NiveauFonctionDao, Serializable {

    @Override
    public List<NiveauFonction> findAll() {
        return this.getHibernateTemplate().loadAll(NiveauFonction.class);
    }

    @Override
    public NiveauFonction findById(Integer idniveau) {
        List l = this.getHibernateTemplate().find("SELECT nf FROM NiveauFonction nf WHERE nf.id= " + idniveau);
        if (l.size() > 0) {
            return (NiveauFonction) l.get(0);
        }
        return null;
    }

    @Override
    public NiveauFonction findByName(String niveau) {
        List l = this.getHibernateTemplate().find("SELECT nf FROM NiveauFonction nf WHERE nf.niveau like '" + niveau + "'");
        if (l.size() > 0) {
            return (NiveauFonction) l.get(0);
        }
        return null;
    }

    @Override
    public boolean addNiveau(NiveauFonction nf) {
        Transaction tx = null;
        Session session = getSessionFactory().openSession();
        try {
            session.setFlushMode(FlushMode.AUTO);
            tx = session.beginTransaction();
            session.save(nf);
            tx.commit();
            session.close();
        } catch (Exception exp) {
            tx.rollback();
            session.close();
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteNiveau(NiveauFonction nf) {
        Transaction tx = null;
        Session session = getSessionFactory().openSession();
        try {
            session.setFlushMode(FlushMode.AUTO);
            tx = session.beginTransaction();
            session.delete(nf);
            tx.commit();
            session.close();
        } catch (Exception exp) {
            tx.rollback();
            session.close();
            return false;
        }
        return true;
    }

    @Override
    public boolean updateNiveau(NiveauFonction nf) {
        Transaction tx = null;
        Session session = getSessionFactory().openSession();
        try {
            session.setFlushMode(FlushMode.AUTO);
            tx = session.beginTransaction();
            session.update(nf);
            tx.commit();
            session.close();
        } catch (Exception exp) {
            tx.rollback();
            session.close();
            return false;
        }
        return true;
    }

    @Override
    public List<NiveauFonction> findByPriority(Integer p) {
        List l = this.getHibernateTemplate().find("SELECT nf FROM NiveauFonction nf WHERE nf.priorite > " + p);
        if (l != null) {
            return l;
        }
        return null;
    }
}
