/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.dao.stock;

import java.io.Serializable;
import java.util.List;
import ma.bservices.tgcc.Entity.ChantierArticleQ;
import ma.bservices.tgcc.utilitaire.MbHibernateDaoSupport;
import ma.bservices.tgcc.utilitaire.SearchU;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author IRAAMANE
 */
@Repository("chantierArticleQDAO")
@Transactional
public class ChantierArticleQDAOImpl extends MbHibernateDaoSupport implements ChantierArticleQDAO, Serializable {

    private final static String HQL_instance = "s";

    @Override
    public List<ChantierArticleQ> findAll() {
        List l = this.getHibernateTemplate().find("SELECT c FROM ChantierArticleQ c");
        return l.size() > 0 ? l : null;
    }

    @Override
    public List<ChantierArticleQ> findByArticleId(int article_id) {
        List l = this.getHibernateTemplate().find("SELECT c FROM ChantierArticleQ c WHERE c.articleId.id = '" + article_id + "'");
        return l.size() > 0 ? l : null;
    }

    @Override
    public List<ChantierArticleQ> findByChantierId(int chantier_id) {
        List l = this.getHibernateTemplate().find("SELECT c FROM ChantierArticleQ c WHERE c.chantierId.id = '" + chantier_id + "'");
        return l.size() > 0 ? l : null;
    }

    @Override
    public ChantierArticleQ findByChantierArticle(int article_id, int chantier_id) {
        List l = this.getHibernateTemplate().find("SELECT c FROM ChantierArticleQ c WHERE c.articleId.id = '" + article_id + "' AND c.chantierId.id = '" + chantier_id + "' ");
        if (l.size() > 0) {
            return (ChantierArticleQ) l.get(0);
        } else {
            return null;
        }
    }

    @Override
    public void addChantierArticleQ(ChantierArticleQ chantierArticleQ) {
        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);
        session.beginTransaction();
        session.saveOrUpdate(chantierArticleQ);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void removeChantierArticleQ(ChantierArticleQ chantierArticleQ) {
        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);
        session.beginTransaction();
        session.delete(chantierArticleQ);
        session.getTransaction().commit();
        session.close();
        System.out.println("removed!");
    }

    @Override
    public void updateChantierArticleQ(ChantierArticleQ chantierArticleQ) {
        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);
        session.beginTransaction();
        session.update(session.merge(chantierArticleQ));
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List<ChantierArticleQ> findByCode(String code, int chantier_id) {
        List l = this.getHibernateTemplate().find("SELECT c FROM ChantierArticleQ c WHERE c.chantierId.id = '" + chantier_id + "' AND c.articleId.designation LIKE '" + code + "'");
        if (l.size() > 0) {
            return l;
        } else {
            return null;
        }
    }

    @Override
    public ChantierArticleQ findByRefArticle(String ref, int chantier_id) {
        System.out.println("CHAQDIVA FROM DAO" + ref);
        List l = this.getHibernateTemplate().find("SELECT c FROM ChantierArticleQ c WHERE c.articleId.codeArticle = '" + ref + "' AND c.chantierId.id = '" + chantier_id + "' ");
        if (l.size() > 0) {
            System.out.println("FOUND ===========IN CHANTIER " + chantier_id + "========================== " + ((ChantierArticleQ) l.get(0)).getArticleId().getCodeArticle());
            return (ChantierArticleQ) l.get(0);

        } else {
            System.out.println("CHAQDIVA NUUUUUUUUUUUUUUUUUULLLLLLLLLLLLLLLLLLL" + ref);
            return null;
        }
    }

    @Override
    public ChantierArticleQ findLastIventilation() {
        Session session = getSessionFactory().openSession();
        List l = session.createQuery("FROM ChantierArticleQ c order by c.idVentilation desc")
                .setMaxResults(1).list();
        session.close();
        if (l != null && !l.isEmpty()) {
            return (ChantierArticleQ) l.get(0);
        }
        return null;
    }

    @Override
    public List<ChantierArticleQ> findByFilter(String ref, String des, String fam, String sfam, String ssfam, Integer id) {
        String filtre = createFilters(ref, des, fam, sfam, ssfam, id);

        System.out.println("les filtre : " + filtre);
        List l = null;

        l = this.getHibernateTemplate().find("From ChantierArticleQ " + HQL_instance + " " + filtre);

        return l;
    }

    public String createFilters(String ref, String des, String fam, String sfam, String ssfam, Integer idChan) {

        String filtre = "";
        Boolean filtre_bool_where = false;
        String filtre_one;
        //creation du filre pour le code

        filtre_one = SearchU.createOneFilre("articleId.codeArticle", ref, filtre_bool_where, HQL_instance);
        if (filtre_one != null) {
            filtre += filtre_one;
            filtre_bool_where = true;
        }

        filtre_one = SearchU.createOneFilre("articleId.designation", des, filtre_bool_where, HQL_instance);
        if (filtre_one != null) {
            filtre += filtre_one;
            filtre_bool_where = true;
        }

        filtre_one = SearchU.createOneFilre("fam", fam, filtre_bool_where, HQL_instance);
        if (filtre_one != null) {
            filtre += filtre_one;
            filtre_bool_where = true;
        }

        filtre_one = SearchU.createOneFilre("sfam", sfam, filtre_bool_where, HQL_instance);
        if (filtre_one != null) {
            filtre += filtre_one;
            filtre_bool_where = true;
        }

        filtre_one = SearchU.createOneFilre("ssfam", ssfam, filtre_bool_where, HQL_instance);
        if (filtre_one != null) {
            filtre += filtre_one;
            filtre_bool_where = true;
        }
        
          filtre_one = SearchU.createOneFilre("chantierId.id", "" + idChan, filtre_bool_where, HQL_instance);
        if (filtre_one != null) {
            filtre += filtre_one;
            filtre_bool_where = true;
        }

        return filtre;
    }
}
