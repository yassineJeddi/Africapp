/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.service.stock;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import ma.bservices.beans.Article;
import ma.bservices.beans.Chantier;
import ma.bservices.beans.Lot;
import ma.bservices.beans.Zone;
import ma.bservices.tgcc.Entity.AffectationStock;
import ma.bservices.tgcc.dao.engin.ChantierDAO;
import ma.bservices.tgcc.dao.engin.LotDAO;
import ma.bservices.tgcc.dao.engin.ZoneDAO;
import ma.bservices.tgcc.dao.stock.AffectationStockDAO;
import ma.bservices.tgcc.dao.stock.ArticleDAO;
import ma.bservices.tgcc.dao.stock.ZoneArticleQDAO;
import ma.bservices.tgcc.utilitaire.TGCCMailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author IRAAMANE
 */
@Service("affectationStockService")
public class AffectationStockServiceImpl implements AffectationStockService, Serializable {

    @Autowired
    private AffectationStockDAO affectationStockDAO;

    @Autowired
    private ArticleDAO articleDAO;

    @Autowired
    private ZoneDAO zoneDAO;

    @Autowired
    private LotDAO lotDAO;
    
    @Autowired
    private ChantierDAO chantierDAO;
    
     @Autowired
    private ZoneArticleQDAO zaqDAO;

    public AffectationStockDAO getAffectationStockDAO() {
        return affectationStockDAO;
    }

    public void setAffectationStockDAO(AffectationStockDAO affectationStockDAO) {
        this.affectationStockDAO = affectationStockDAO;
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
    
    

    public ZoneDAO getZoneDAO() {
        return zoneDAO;
    }

    public void setZoneDAO(ZoneDAO zoneDAO) {
        this.zoneDAO = zoneDAO;
    }

    public LotDAO getLotDAO() {
        return lotDAO;
    }

    public void setLotDAO(LotDAO lotDAO) {
        this.lotDAO = lotDAO;
    }

    public ZoneArticleQDAO getZaqDAO() {
        return zaqDAO;
    }

    public void setZaqDAO(ZoneArticleQDAO zaqDAO) {
        this.zaqDAO = zaqDAO;
    }
    
    
    

    @Override
    public List<AffectationStock> findAll() {
        return affectationStockDAO.findAll();
    }
    
      @Override
    public void removeAff(AffectationStock aff) {
        affectationStockDAO.deleteAff(aff);
    }
    
    @Override
    public List<AffectationStock> findAllInChantier(int id) {
        return affectationStockDAO.findAllInChantier(id);
    }

    @Override
    public void saveAffectationStock(AffectationStock affectStock, int article_id, int zone_id, int lot_id, Double quantite, Date dateAffect, int ch) {

        // get article
        Article article = articleDAO.findOneByCode(article_id);
        //get lot
        Lot lot = lotDAO.findById(lot_id);
        //get zone 
        Zone zone = zoneDAO.findById(zone_id);
        //get assocaed zaq
        Chantier chantier = chantierDAO.findById(ch);
       

        //set article, zone, lot, quantité de l'affectation
        affectStock.setArticleId(article);
        affectStock.setZoneId(zone);
        affectStock.setLotId(lot);
        affectStock.setQuantite(quantite);
        affectStock.setDateeffectStock(dateAffect);
        affectStock.setChantierId(chantier);

        //save to database
        affectationStockDAO.saveAffectationStock(affectStock);

        System.out.println("=====================AFFECTATION ADDED SUCCESSFULLY =================");

    }

    @Override
    public List<AffectationStock> findAllByArticle(int codeArticle) {
        return affectationStockDAO.findAllAffectationsByArticle(codeArticle);
    }
    
      @Override
    public List<AffectationStock> findByIntervalDate(int chantier_id, Date dateFrom, Date dateTo) {
        return affectationStockDAO.findByIntervalDate(chantier_id, dateFrom, dateTo);
    }

    @Override
    public AffectationStock findById(int id) {
        return affectationStockDAO.findById(id);
    }

    @Override
    public List<AffectationStock> findByZone(int zone_id) {
       return affectationStockDAO.findByZone(zone_id);
    }
    
    @Override
    public void printStuff(){    
        System.out.println("THIS IS PRINTING FROM SERVICE CALLED BY SPRING SCHEDULER");
        TGCCMailSender.sendMail();
    }

    @Override
    public void updateAffectationStock(AffectationStock affectStock, int zone_id, int lot_id, Double quantite) {
          //get lot
        Lot lot = lotDAO.findById(lot_id);
        //get zone 
        Zone zone = zoneDAO.findById(zone_id);

        //set article, zone, lot, quantité de l'affectation      
        affectStock.setZoneId(zone);
        affectStock.setLotId(lot);
        affectStock.setQuantite(quantite);

        //save to database
        affectationStockDAO.updateAffectationStock(affectStock);
    }

}
