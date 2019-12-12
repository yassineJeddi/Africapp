/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.dao.engin;

import java.io.Serializable;
import java.util.List;
import ma.bservices.beans.Lot;
import ma.bservices.tgcc.utilitaire.MbHibernateDaoSupport;
import org.hibernate.FlushMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author j.allali
 */
@Repository("lotDAO")
@Transactional
public class LotDAOImpl extends MbHibernateDaoSupport implements LotDAO, Serializable {

    @Override
    public List<Lot> findAll() {
        return this.getHibernateTemplate().loadAll(Lot.class);
    }

    @Override
    public Lot findById(int id) {
        List l = this.getHibernateTemplate().find("SELECT l FROM Lot l WHERE l.id = " + id);
        if (l.size() > 0) {
            return (Lot) l.get(0);
        }

        return null;
    }

    @Override
    public Lot findOneByLabel(String label) {
        List l = this.getHibernateTemplate().find("SELECT l FROM Lot l WHERE l.libelle LIKE '%" + label + "%'");
        if (l.size() > 0) {
            return (Lot) l.get(0);
        }

        return null;
    }

    @Override
    public Boolean updateLot(Lot upLot) {
        
        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);

        session.beginTransaction();
        session.update(upLot);

        session.getTransaction().commit();

        session.close();
        return true;
      
    
    }

    @Override
    public Boolean deleteLot(int id) {
    
        Session s = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
         Query query = s.createQuery("delete from Lot where id=:id");
        query.setParameter("id", id);
        int deleted = query.executeUpdate();
        System.out.println("value_____"+deleted);
        return true;
    }

    @Override
    public Boolean deleteLot(Lot l) {
    
        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);

        session.beginTransaction();
        session.delete(l);

        session.getTransaction().commit();
        session.close();
        
        return true;
    }
    
    @Override
    public Boolean insertLot(Lot insLot) {
        
        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);

        session.beginTransaction();
        session.save(insLot);

        session.getTransaction().commit();
        System.out.println("Ajouter __________");
        session.close();
        
        return true;
    
    
    }
    
}
