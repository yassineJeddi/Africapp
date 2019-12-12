/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.service.stock;

import java.io.Serializable;
import java.util.List;
import ma.bservices.beans.Article;
import ma.bservices.tgcc.dao.stock.ArticleDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author IRAAMANE
 */
@Service("articleService")
public class ArticleServiceImpl implements ArticleService, Serializable {

    @Autowired
    private ArticleDAO articleDAO;

    private TransferStockService transferService;

    public TransferStockService getTransferService() {
        return transferService;
    }

    public void setTransferService(TransferStockService transferService) {
        this.transferService = transferService;
    }

    public ArticleDAO getArticleDAO() {
        return articleDAO;
    }

    public void setArticleDAO(ArticleDAO articleDAO) {
        this.articleDAO = articleDAO;
    }

    @Override
    public List<Article> findAll() {
        return articleDAO.findAll();
    }

    @Override
    public void addArticle(Article article, String ref, String des, String fam, String sfam, String ssfam, String typConso, String nature, String unite, Double qteTotale, Double qteDispo, Double qteConso) {

        article.setCodeArticle(ref);
        article.setDos(700);
        article.setFam1(fam);
        article.setFam2(sfam);
        article.setFam3(ssfam);
        article.setTypo(typConso);
        article.setDesignation(des);
        article.setUnite(unite);
        article.setNatureArticle(nature);      

        articleDAO.ajouterArticle(article);
    }

    @Override
    public Article findOneByCode(int codeArticle) {
        return articleDAO.findOneByCode(codeArticle);
    }

    @Override
    public Article findByRef(String ref) {
        return articleDAO.findByRef(ref);
    }
    
     @Override
    public Article findByDesignation(String ref) {
        return articleDAO.findByDesignation(ref);
    }

    @Override
    public void removeArticle(Article article) {
        articleDAO.removeArticle(article);
    }

    @Override
    public void updateArticle(Article article, Double qteTotale, Double qteDispo, Double qteConso) {
       
        articleDAO.updateArticle(article);
    }

}
