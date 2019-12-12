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
import ma.bservices.tgcc.Entity.ConsommationStock;
import ma.bservices.tgcc.Entity.RetourStock;
import ma.bservices.tgcc.utilitaire.MbHibernateDaoSupport;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author IRAAMANE
 */
@Repository("retourStockDAO")
@Transactional
public class RetourStockDAOImpl extends MbHibernateDaoSupport implements RetourStockDAO, Serializable {

    @Override
    public List<RetourStock> findAll() {
        return this.getHibernateTemplate().loadAll(RetourStock.class);
    }

    @Override
    public void addRetourStock(RetourStock retourStock) {
        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);
        session.beginTransaction();
        session.save(retourStock);
        session.getTransaction().commit();
        session.close();
    }
    
       @Override
    public void removeRetourStock(RetourStock retourStock) {
        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);
        session.beginTransaction();
        session.delete(retourStock);
        session.getTransaction().commit();
        session.close();
    }
    
     @Override
    public void updateRetourStock(RetourStock retourStock) {
        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);
        session.beginTransaction();
        session.update(retourStock);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public RetourStock findById(int retour_id) {
        List l = this.getHibernateTemplate().find("SELECT r FROM RetourStock r WHERE r.codeRetourStock = '" + retour_id + "'");
        return l.size() > 0 ? (RetourStock) l.get(0) : null;
    }

    @Override
    public List<RetourStock> findByZone(int zone_id) {

        List l = this.getHibernateTemplate().find("SELECT r FROM RetourStock r WHERE c.zoneId.idZone = '" + zone_id + "'");
        return l.size() > 0 ? l : null;

    }
    
    @Override
    public List<RetourStock> findByChantier(int chantier_id) {

        List l = this.getHibernateTemplate().find("SELECT r FROM RetourStock r WHERE r.chantierId.id = '" + chantier_id + "'");
        return l.size() > 0 ? l : null;

    }
    
       @Override
    public List<RetourStock> findByIntervalDate(int chantier_id, Date dateFrom, Date dateTo) {
        String dateFromString, dateToString;
         DateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
         dateFromString = formatDate.format(dateFrom);
         dateToString = formatDate.format(dateTo);
          System.out.println(formatDate.format(dateFrom));          
          System.out.println(formatDate.format(dateTo));
        //  return this.getHibernateTemplate().loadAll(AffectationStock.class);
        List l = this.getHibernateTemplate().find("SELECT s FROM RetourStock s WHERE s.dateRetourStock >= '" + dateFromString + "' AND s.dateRetourStock <= '" + dateToString + "' AND s.chantierId.id = '" + chantier_id + "'");
        return l.size() > 0 ? l : null;
    }

}
