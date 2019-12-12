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
import ma.bservices.tgcc.Entity.ConsommationStock;
import ma.bservices.tgcc.dao.engin.ChantierDAO;
import ma.bservices.tgcc.dao.engin.LotDAO;
import ma.bservices.tgcc.dao.engin.ZoneDAO;
import ma.bservices.tgcc.dao.stock.ArticleDAO;
import ma.bservices.tgcc.dao.stock.ConsommationStockDAO;
import ma.bservices.tgcc.dao.stock.ZoneArticleQDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author IRAAMANE
 */
@Service("consommationStockService")
public class ConsommationStockServiceImpl implements ConsommationStockService, Serializable {

    @Autowired
    ConsommationStockDAO consommationDAO;

    @Autowired
    ArticleDAO articleDAO;
    
     @Autowired
    ChantierDAO chantierDAO;

    @Autowired
    ZoneDAO zoneDAO;

    @Autowired
    LotDAO lotDAO;
    
    @Autowired
    private ZoneArticleQDAO zaqDAO;

    public ZoneArticleQDAO getZaqDAO() {
        return zaqDAO;
    }

    public void setZaqDAO(ZoneArticleQDAO zaqDAO) {
        this.zaqDAO = zaqDAO;
    }

    public ConsommationStockDAO getConsommationDAO() {
        return consommationDAO;
    }

    public ChantierDAO getChantierDAO() {
        return chantierDAO;
    }

    public void setChantierDAO(ChantierDAO chantierDAO) {
        this.chantierDAO = chantierDAO;
    }
    
    

    public void setConsommationDAO(ConsommationStockDAO consommationDAO) {
        this.consommationDAO = consommationDAO;
    }

    public ArticleDAO getArticleDAO() {
        return articleDAO;
    }

    public void setArticleDAO(ArticleDAO articleDAO) {
        this.articleDAO = articleDAO;
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

    @Override
    public List<ConsommationStock> findAll() {
        return consommationDAO.findAll();
    }
    
       @Override
    public List<ConsommationStock> findByIntervalDate(int chantier_id, Date date_from, Date date_to) {
        return consommationDAO.findByIntervalDate(chantier_id, date_from, date_to);
    }

    @Override
    public void addConsommationStock(ConsommationStock consomationStock, int article_id, int zone_id, int lot_id, Double quantite, Date dateConsommation, int chantier_id) {
        // get article
        Article article = articleDAO.findOneByCode(article_id);
        //get lot
        Lot lot = lotDAO.findById(lot_id);
        //get zone 
        Zone zone = zoneDAO.findById(zone_id);
        //get chantier
        Chantier chantier = chantierDAO.findById(chantier_id);
       

        //set article, zone, lot, quantité, date de consommation
        consomationStock.setArticleId(article);
        consomationStock.setZoneId(zone);
        consomationStock.setLotId(lot);
        consomationStock.setQuantite(quantite);
        consomationStock.setDateConsoStock(dateConsommation);
        consomationStock.setChantierId(chantier);
        

        //save to database
        consommationDAO.addConsommationStock(consomationStock);

        System.out.println("=====================CONSOMMATION ADDED SUCCESSFULLY =================");

    }

    @Override
    public ConsommationStock findById(int id) {
        return consommationDAO.findById(id);
    }

    @Override
    public List<ConsommationStock> findByZone(int zone_id) {
        return consommationDAO.findByZone(zone_id);
    }
    
     @Override
    public List<ConsommationStock> findByChantier(int chantier_id) {
        return consommationDAO.findByChantier(chantier_id);
    }
    
        @Override
    public void removeConsommationStock(ConsommationStock conso) {
        consommationDAO.removeConsommationStock(conso);
    }
    
     @Override
    public void updateConsommationStock(ConsommationStock affectStock, int zone_id, int lot_id, Double quantite) {
          //get lot
        Lot lot = lotDAO.findById(lot_id);
        //get zone 
        Zone zone = zoneDAO.findById(zone_id);

        //set article, zone, lot, quantité de l'affectation      
        affectStock.setZoneId(zone);
        affectStock.setLotId(lot);
        affectStock.setQuantite(quantite);

        //save to database
        consommationDAO.updateConsommationStock(affectStock);
    }

}
