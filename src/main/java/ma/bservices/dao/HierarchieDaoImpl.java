/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.dao;

import java.io.Serializable;
import java.util.List;
import ma.bservices.beans.ChantierHierarchie;
import ma.bservices.beans.SalarieChantier;
import ma.bservices.mb.services.MbHibernateDaoSupport;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Mahdi
 */
@Repository("HierarchieDao")
@Transactional
public class HierarchieDaoImpl extends MbHibernateDaoSupport implements Serializable, HierarchieDao {

    @Override
    public boolean ajouter(ChantierHierarchie ch) {
        Transaction tx = null;
        Session session = getSessionFactory().openSession();
        try {
            session.setFlushMode(FlushMode.AUTO);
            tx = session.beginTransaction();
            session.save(session.merge(ch));
            tx.commit();
            session.close();
        } catch (Exception exp) {
            tx.rollback();
            session.close();
            System.out.println("erreur " + exp.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean supprimer(ChantierHierarchie ch) {
        Transaction tx = null;
        Session session = getSessionFactory().openSession();
        try {
            session.setFlushMode(FlushMode.AUTO);
            tx = session.beginTransaction();
            session.delete(ch);
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
    public List<ChantierHierarchie> gets() {
        return getHibernateTemplate().loadAll(ChantierHierarchie.class);
    }

    @Override
    public ChantierHierarchie get(Integer idHierarchie) {
        return getHibernateTemplate().get(ChantierHierarchie.class, idHierarchie);
    }

    @Override
    public ChantierHierarchie get(SalarieChantier sc) {
        return getHibernateTemplate().get(ChantierHierarchie.class, sc);
    }

}
