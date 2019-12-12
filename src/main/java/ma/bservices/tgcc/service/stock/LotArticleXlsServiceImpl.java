/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.service.stock;

import java.io.Serializable;
import java.util.List;
import ma.bservices.tgcc.Entity.LotArticleXls;
import ma.bservices.tgcc.dao.engin.LotDAO;
import ma.bservices.tgcc.dao.stock.ArticleDAO;
import ma.bservices.tgcc.dao.stock.LotArticleXlsDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author airaamane
 */

@Service("lotArticleXlsService")
public class LotArticleXlsServiceImpl implements LotArticleXlsService, Serializable {
    
    
    @Autowired
    LotArticleXlsDAO lotArticleXlsDAO;
    
    @Autowired
    ArticleDAO articleDAO;
    
    @Autowired
    LotDAO lotDAO;

    public LotArticleXlsDAO getLaxDAO() {
        return lotArticleXlsDAO;
    }

    public void setLaxDAO(LotArticleXlsDAO lotArticleXlsDAO) {
        this.lotArticleXlsDAO = lotArticleXlsDAO;
    }

    public ArticleDAO getArticleDAO() {
        return articleDAO;
    }

    public void setArticleDAO(ArticleDAO articleDAO) {
        this.articleDAO = articleDAO;
    }

    public LotDAO getLotDAO() {
        return lotDAO;
    }

    public void setLotDAO(LotDAO lotDAO) {
        this.lotDAO = lotDAO;
    }

    @Override
    public List<LotArticleXls> findAll() {
        return lotArticleXlsDAO.findAll();
    }

    @Override
    public List<LotArticleXls> findByArticle(Integer articleId) {
        return lotArticleXlsDAO.findByArticleId(articleId);
    }

    @Override
    public LotArticleXls findByLotArticle(Integer articleId, Integer lotId) {
       return lotArticleXlsDAO.findByArticleLot(articleId, lotId);
    }

    @Override
    public void save(LotArticleXls entry) {
        lotArticleXlsDAO.save(entry);
    }

    @Override
    public void update(LotArticleXls entry) {
        lotArticleXlsDAO.update(entry);
    }

    @Override
    public void delete(LotArticleXls entry) {
        lotArticleXlsDAO.delete(entry);
    }
    
    
    
    
    
}
