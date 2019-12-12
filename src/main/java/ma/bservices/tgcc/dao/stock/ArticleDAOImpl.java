/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.dao.stock;

import java.io.Serializable;
import java.util.List;
import ma.bservices.beans.Article;
import ma.bservices.tgcc.utilitaire.MbHibernateDaoSupport;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author IRAAMANE
 */
@Repository("articleDAO")
@Transactional
public class ArticleDAOImpl extends MbHibernateDaoSupport implements ArticleDAO, Serializable {

    @Override
    public List<Article> findAll() {
        return this.getHibernateTemplate().loadAll(Article.class);
    }

    @Override
    @Transactional
    public void removeArticle(Article article) {
        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);
        session.beginTransaction();
        session.delete(article);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    @Transactional
    public void updateArticle(Article article) {
        try {
            Session session = getSessionFactory().openSession();
            session.setFlushMode(FlushMode.AUTO);
            session.beginTransaction();
            session.update(session.merge(article));
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            System.out.println("@@@@@ erreur " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public void ajouterArticle(Article article) {
        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);
        session.beginTransaction();
        session.saveOrUpdate(session.merge(article));
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public Article findOneByCode(int code) {
        System.out.println("______ Part 2.1 ________");
        List l = this.getHibernateTemplate().find("SELECT a FROM Article a WHERE a.id = '" + code + "'");
        if (l.size() > 0) {
            System.out.println("_____ Part 2.2 __________");
            return (Article) l.get(0);
        }
        return null;
    }
    
    

    @Override
    public Article findByRef(String ref) {
        List l = this.getHibernateTemplate().find("SELECT a FROM Article a WHERE a.codeArticle = '" + ref + "'");
        if (l.size() > 0) {
            return (Article) l.get(0);
        }
        return null;
    }
    
     @Override
    public Article findByDesignation(String ref) {
        List l = this.getHibernateTemplate().find("SELECT a FROM Article a WHERE a.designation = '" + ref + "'");
        if (l.size() > 0) {
            return (Article) l.get(0);
        }
        return null;
    }

}
