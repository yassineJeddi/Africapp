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
import ma.bservices.tgcc.Entity.ConsommationStock;
import ma.bservices.tgcc.utilitaire.MbHibernateDaoSupport;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author IRAAMANE
 */
@Repository("consommationStockDAO")
@Transactional
public class ConsommationStockDAOImpl extends MbHibernateDaoSupport implements ConsommationStockDAO, Serializable {

    @Override
    public List<ConsommationStock> findAll() {
        return this.getHibernateTemplate().loadAll(ConsommationStock.class);
    }

    @Override
    public void addConsommationStock(ConsommationStock consommationStock) {
        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);
        session.beginTransaction();
        session.save(consommationStock);
        session.getTransaction().commit();
        session.close();
    }
    
     @Override
    public void updateConsommationStock(ConsommationStock consommationStock) {
        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);
        session.beginTransaction();
        session.update(consommationStock);
        session.getTransaction().commit();
        session.close();
    }
    
      @Override
    public void removeConsommationStock(ConsommationStock consommationStock) {
        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);
        session.beginTransaction();
        session.delete(consommationStock);
        session.getTransaction().commit();
        session.close();
    }
    
    
       @Override
    public List<ConsommationStock> findByIntervalDate(int chantier_id, Date dateFrom, Date dateTo) {
        String dateFromString, dateToString;
         DateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
         dateFromString = formatDate.format(dateFrom);
         dateToString = formatDate.format(dateTo);
          System.out.println(formatDate.format(dateFrom));          
          System.out.println(formatDate.format(dateTo));
        //  return this.getHibernateTemplate().loadAll(AffectationStock.class);
        List l = this.getHibernateTemplate().find("SELECT s FROM ConsommationStock s WHERE s.dateConsoStock >= '" + dateFromString + "' AND s.dateConsoStock <= '" + dateToString + "' AND s.chantierId.id = '" + chantier_id + "'");
        return l.size() > 0 ? l : null;
    }

    @Override
    public ConsommationStock findById(int consommation_id) {
        List l = this.getHibernateTemplate().find("SELECT c FROM ConsommationStock c WHERE c.codeConsommationStock = '" + consommation_id + "'");
        return l.size() > 0 ? (ConsommationStock) l.get(0) : null;
    }

    @Override
    public List<ConsommationStock> findByZone(int zone_id) {
        List l = this.getHibernateTemplate().find("SELECT c FROM ConsommationStock c WHERE c.zoneId.idZone = '" + zone_id + "'");
        return l.size() > 0 ? l : null;
    }
    
      @Override
    public List<ConsommationStock> findByChantier(int chantier_id) {
        List l = this.getHibernateTemplate().find("SELECT c FROM ConsommationStock c WHERE c.chantierId.id = '" + chantier_id + "'");
        return l.size() > 0 ? l : null;
    }

}
