/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.service.stock;

import java.io.Serializable;
import java.util.List;
import ma.bservices.beans.Article;
import ma.bservices.beans.Chantier;
import ma.bservices.tgcc.Entity.ChantierArticleQ;
import ma.bservices.tgcc.dao.engin.ChantierDAO;
import ma.bservices.tgcc.dao.stock.ArticleDAO;
import ma.bservices.tgcc.dao.stock.ChantierArticleQDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author IRAAMANE
 */
@Service("chantierArticleQService")
public class ChantierArticleQServiceImpl implements ChantierArticleQService, Serializable {

    @Autowired
    ChantierArticleQDAO chantierArticleQDAO;

    @Autowired
    ArticleDAO articleDAO;

    @Autowired
    ChantierDAO chantierDAO;

    public ChantierArticleQDAO getChantierArticleQDAO() {
        return chantierArticleQDAO;
    }

    public void setChantierArticleQDAO(ChantierArticleQDAO chantierArticleQDAO) {
        this.chantierArticleQDAO = chantierArticleQDAO;
    }

    public ArticleDAO getArticleDAO() {
        return articleDAO;
    }

    public void setArticleDAO(ArticleDAO articleDAO) {
        this.articleDAO = articleDAO;
    }

    public ChantierDAO getChantierDAO() {
        return chantierDAO;
    }

    public void setChantierDAO(ChantierDAO chantierDAO) {
        this.chantierDAO = chantierDAO;
    }

    @Override
    public List<ChantierArticleQ> findAll() {
        return chantierArticleQDAO.findAll();
    }

    @Override
    public List<ChantierArticleQ> findByArticleId(int article_id) {
        return chantierArticleQDAO.findByArticleId(article_id);
    }

    @Override
    public List<ChantierArticleQ> findByChantierId(int chantier_id) {
        return chantierArticleQDAO.findByChantierId(chantier_id);
    }

    @Override
    public ChantierArticleQ findByChantierArticle(int article_id, int chantier_id) {
        return chantierArticleQDAO.findByChantierArticle(article_id, chantier_id);
    }

    @Override
    public ChantierArticleQ findByRefArticle(String ref, int chantier_id) {
        System.out.println("CHAQDIVA FROM SERVICE ==== " + ref);
        return chantierArticleQDAO.findByRefArticle(ref, chantier_id);
    }

    @Override
    public void addChantierArticleQ(ChantierArticleQ chantierArticleQ, int article_id, int chantier_id, Double quantite, String fam, String fam2, String fam3, String prorata, String typConso) {
        //get article
        Article article = articleDAO.findOneByCode(article_id);

        //get chantier
        Chantier chantier = chantierDAO.findById(chantier_id);

        chantierArticleQ.setArticleId(article);
        chantierArticleQ.setChantierId(chantier);
        chantierArticleQ.setQuantiteChantierStock(quantite);
        chantierArticleQ.setFam(fam);
        chantierArticleQ.setSfam(fam2);
        chantierArticleQ.setSsfam(fam3);
        chantierArticleQ.setProrata(prorata);
        chantierArticleQ.setTypConso(typConso);
        

        //save chantierAricleQ
        chantierArticleQDAO.addChantierArticleQ(chantierArticleQ);
    }

    @Override
    public void updateChantierArticleQ(ChantierArticleQ chantierArticleQ, Double quantite) {
        chantierArticleQ.setQuantiteChantierStock(quantite);
        chantierArticleQDAO.updateChantierArticleQ(chantierArticleQ);
    }

    @Override
    public void removeChantierArticleQ(ChantierArticleQ chnatierArticleQ) {
        chantierArticleQDAO.removeChantierArticleQ(chnatierArticleQ);
    }

    @Override
    public List<ChantierArticleQ> findByCode(String code, int chantier_id) {
        return chantierArticleQDAO.findByCode(code, chantier_id);
    }

    @Override
    public ChantierArticleQ findLastIventilation() {
        return chantierArticleQDAO.findLastIventilation();
    }

    @Override
    public List<ChantierArticleQ> findByFilters(String ref, String des, String fam, String sfam, String ssfam, Integer idChan) {
        return chantierArticleQDAO.findByFilter(ref, des, fam, sfam, ssfam, idChan);
    }

}
