/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.dao.stock;

import java.io.Serializable;
import java.util.List;
import ma.bservices.tgcc.Entity.LotArticleXls;
import ma.bservices.tgcc.utilitaire.MbHibernateDaoSupport;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author admin
 */
@Repository("lotArticleXlsDAO")
@Transactional
public class LotArticleXlsDAOImpl extends MbHibernateDaoSupport implements Serializable, LotArticleXlsDAO {

    @Override
    public List<LotArticleXls> findAll() {
        List l = this.getHibernateTemplate().find("SELECT l FROM LotArticleXls l");
        return l.size() > 0 ? l : null;
    }

    @Override
    public List<LotArticleXls> findByArticleId(Integer article_id) {
        List l = this.getHibernateTemplate().find("SELECT c FROM LotArticleXls c WHERE c.article.id = '" + article_id + "'");
        return l.size() > 0 ? l : null;
    }

    @Override
    public LotArticleXls findByArticleLot(Integer articleId, Integer lotId) {
        List l = this.getHibernateTemplate().find("SELECT c FROM LotArticleXls c WHERE c.article.id = '" + articleId + "' AND c.lot.id = '" + lotId + "' ");
        if (l.size() > 0) {
            return (LotArticleXls) l.get(0);
        } else {
            return null;
        }
    }

    @Override
    public void save(LotArticleXls entry) {
        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);
        session.beginTransaction();
        session.save(entry);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void update(LotArticleXls entry) {
        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);
        session.beginTransaction();
        session.update(entry);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void delete(LotArticleXls entry) {
        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);
        session.beginTransaction();
        session.delete(entry);
        session.getTransaction().commit();
        session.close();
    }

}
