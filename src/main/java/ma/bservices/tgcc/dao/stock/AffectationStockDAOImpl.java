/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.dao.stock;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import ma.bservices.tgcc.Entity.AffectationStock;
import ma.bservices.tgcc.utilitaire.MbHibernateDaoSupport;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author IRAAMANE
 */
@Repository("affectationStockDAO")
@Transactional

public class AffectationStockDAOImpl extends MbHibernateDaoSupport implements AffectationStockDAO, Serializable {

    @Override
    public void saveAffectationStock(AffectationStock affectStock) {
        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);
        session.beginTransaction();
        session.save(affectStock);
        session.getTransaction().commit();
        session.close();
    }
    
     @Override
    public void deleteAff(AffectationStock affectStock) {
        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);
        session.beginTransaction();
        session.delete(affectStock);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List<AffectationStock> findAll() {
        //  return this.getHibernateTemplate().loadAll(AffectationStock.class);
        List l = this.getHibernateTemplate().find("SELECT s FROM AffectationStock s");
        return l.size() > 0 ? l : null;
    }
    
      @Override
    public List<AffectationStock> findByIntervalDate(int chantier_id, Date dateFrom, Date dateTo) {
        String dateFromString, dateToString;
         DateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
         dateFromString = formatDate.format(dateFrom);
         dateToString = formatDate.format(dateTo);
          System.out.println(formatDate.format(dateFrom));          
          System.out.println(formatDate.format(dateTo));
        //  return this.getHibernateTemplate().loadAll(AffectationStock.class);
        List l = this.getHibernateTemplate().find("SELECT s FROM AffectationStock s WHERE s.dateeffectStock >= '" + dateFromString + "' AND s.dateeffectStock <= '" + dateToString + "' AND s.chantierId.id = '" + chantier_id + "'");
        return l.size() > 0 ? l : null;
    }

    @Override
    public AffectationStock findById(int id) {
        List l = this.getHibernateTemplate().find("SELECT s FROM AffectationStock s WHERE s.codeAffectationStock = '" + id + "'");
        if (l.size() > 0) {
            return (AffectationStock) l.get(0);
        }
        return null;
    }
    
    
    @Override
    public List<AffectationStock> findAllInChantier(int id) {
        List l = this.getHibernateTemplate().find("SELECT s FROM AffectationStock s WHERE s.chantierId.id = '" + id + "'");
        return l.size() > 0 ? l : null;
    }
    
    
    

    @Override
    public List<AffectationStock> findAllAffectationsByArticle(int id) {
        List l = this.getHibernateTemplate().find("SELECT s FROM AffectationStock s WHERE s.articleId.id = '" + id + "'");
        return l.size() > 0 ? l : null;
    }

    @Override
    public List<AffectationStock> findByZone(int zone_id) {
        List l = this.getHibernateTemplate().find("SELECT s FROM AffectationStock s WHERE s.zoneId.idZone = '" + zone_id + "'");
        return l.size() > 0 ? l : null;
    }

    @Override
    public void updateAffectationStock(AffectationStock affectationStock) {
        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);
        session.beginTransaction();
        session.update(affectationStock);
        session.getTransaction().commit();
        session.close();
    }

}
