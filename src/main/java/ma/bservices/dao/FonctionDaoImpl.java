/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.dao;

import java.io.Serializable;
import java.util.List;
import ma.bservices.beans.Fonction;
import ma.bservices.beans.FonctionLocal;
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
@Repository("fonctionDao")
@Transactional
public class FonctionDaoImpl extends MbHibernateDaoSupport implements FonctionDao, Serializable {

    @Override
    public boolean Add(FonctionLocal f) {
        Transaction tx = null;
        Session session = getSessionFactory().openSession();
        try {
            session.setFlushMode(FlushMode.AUTO);
            tx = session.beginTransaction();
            session.save(f);
            tx.commit();
            session.close();
        } catch (Exception exp) {
            System.out.println("exception : " + exp.getMessage());
            tx.rollback();
            session.close();
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(FonctionLocal f) {
        Transaction tx = null;
        Session session = getSessionFactory().openSession();
        try {
            session.setFlushMode(FlushMode.AUTO);
            tx = session.beginTransaction();
            session.delete(f);
            tx.commit();
            session.close();
        } catch (Exception exp) {
            System.out.println("exception : " + exp.getMessage());
            tx.rollback();
            session.close();
            return false;
        }
        return true;
    }

    @Override
    public List<FonctionLocal> getAll() {
        return getHibernateTemplate().loadAll(FonctionLocal.class);
    }

    @Override
    public boolean update(FonctionLocal f) {
        Transaction tx = null;
        Session session = getSessionFactory().openSession();
        try {
            session.setFlushMode(FlushMode.AUTO);
            tx = session.beginTransaction();
            session.update(f);
            tx.commit();
            session.close();
        } catch (Exception exp) {
            System.out.println("exception : " + exp.getMessage());
            tx.rollback();
            session.close();
            return false;
        }
        return true;
    }

    @Override
    public List<Fonction> NotHaveNiveau() {
        return (List<Fonction>) getHibernateTemplate().find("select f from Fonction f where not f.id in (select fl.fonction.id from FonctionLocal fl)");
    }

    @Override
    public FonctionLocal getById(Integer idFonction) {
        return getHibernateTemplate().get(FonctionLocal.class, idFonction);
    }

}
