/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.dao.stock;

import java.io.Serializable;
import java.util.List;
import ma.bservices.tgcc.Entity.ZoneArticleQ;
import ma.bservices.tgcc.utilitaire.MbHibernateDaoSupport;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author IRAAMANE
 */

@Repository("zoneArticleQDAO")
@Transactional

public class ZoneArticleQDAOImpl extends MbHibernateDaoSupport implements ZoneArticleQDAO, Serializable{

    @Override
    public List<ZoneArticleQ> findAll() {
         return this.getHibernateTemplate().loadAll(ZoneArticleQ.class);
    }
    
    @Override
    public List<ZoneArticleQ> findByArticleId(int article_id, int chantier_id) {
        List l = this.getHibernateTemplate().find("SELECT z FROM ZoneArticleQ z WHERE z.articleId.id = '" + article_id + "' AND z.chantierId.id = '" +chantier_id+ "'");
        return l.size() > 0 ? l : null;
    }
    
      @Override
    public List<ZoneArticleQ> findByChantier(int id) {
        List l = this.getHibernateTemplate().find("SELECT z FROM ZoneArticleQ z WHERE z.chantierId.id = '" + id + "'");
        return l.size() > 0 ? l : null;
    }
    
    @Override
    public ZoneArticleQ findByZoneArticle(int article_id, int zone_id, int lot_id) {
        List l = this.getHibernateTemplate().find("SELECT z FROM ZoneArticleQ z WHERE z.articleId.id = '" + article_id + "' AND z.zoneId.idZone = '" + zone_id +"' AND z.lotId.id = '" + lot_id + "'");
        if(l.size() > 0)
            return (ZoneArticleQ) l.get(0);
        else
            return null;
    }

    @Override
    public void addZoneArticleQ(ZoneArticleQ zoneArticleQ) {
        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);
        session.beginTransaction();
        session.save(zoneArticleQ);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void removeZoneArticleQ(ZoneArticleQ zoneArticleQ) {
         Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);
        session.beginTransaction();
        session.delete(zoneArticleQ);
        session.getTransaction().commit();
        session.close();
         System.out.println("removed!");
    }

    @Override
    public void updateZoneArticleQ(ZoneArticleQ zoneArticleQ) {
         Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);
        session.beginTransaction();
        session.update(zoneArticleQ);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public ZoneArticleQ findById(int id) {
        
         List l = this.getHibernateTemplate().find("SELECT z FROM ZoneArticleQ z WHERE z.codeZoneStockQ = '" + id + "'");
        if(l.size() > 0)
            return (ZoneArticleQ) l.get(0);
        else
            return null;
    }
    
}
