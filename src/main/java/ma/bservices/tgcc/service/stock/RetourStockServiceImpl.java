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
import ma.bservices.tgcc.Entity.RetourStock;
import ma.bservices.tgcc.dao.engin.ChantierDAO;
import ma.bservices.tgcc.dao.engin.LotDAO;
import ma.bservices.tgcc.dao.engin.ZoneDAO;
import ma.bservices.tgcc.dao.stock.ArticleDAO;
import ma.bservices.tgcc.dao.stock.RetourStockDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author IRAAMANE
 */
@Service("retourStockService")
public class RetourStockServiceImpl implements RetourStockService, Serializable {

    @Autowired
    RetourStockDAO retourStockDAO;

    @Autowired
    ArticleDAO articleDAO;

    @Autowired
    ZoneDAO zoneDAO;
    
    @Autowired
    ChantierDAO chantierDAO;

    @Autowired
    LotDAO lotDAO;

    public ChantierDAO getChantierDAO() {
        return chantierDAO;
    }

    public void setChantierDAO(ChantierDAO chantierDAO) {
        this.chantierDAO = chantierDAO;
    }
    
    
    

    public RetourStockDAO getRetourStockDAO() {
        return retourStockDAO;
    }

    public void setRetourStockDAO(RetourStockDAO retourStockDAO) {
        this.retourStockDAO = retourStockDAO;
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
    public List<RetourStock> findAll() {
        return retourStockDAO.findAll();
    }
    
        @Override
    public List<RetourStock> findByIntervalDate(int chantier_id, Date date_from, Date date_to) {
        return retourStockDAO.findByIntervalDate(chantier_id, date_from, date_to);
    }

    @Override
    public void addRetourStock(RetourStock retourStock, int article_id, int zone_id, int lot_id, Double quantite, Date dateRetour, int chantier_id) {
        // get article
        Article article = articleDAO.findOneByCode(article_id);
        //get lot
        Lot lot = lotDAO.findById(lot_id);
        //get zone 
        Zone zone = zoneDAO.findById(zone_id);
        //get chantier
        Chantier chantier = chantierDAO.findById(chantier_id);

        //set article, zone, lot, quantité, date de retour
        retourStock.setArticleId(article);
        retourStock.setZoneId(zone);
        retourStock.setLotId(lot);
        retourStock.setQuantite(quantite);
        retourStock.setDateRetourStock(dateRetour);
        retourStock.setChantierId(chantier);

        //save to database
        retourStockDAO.addRetourStock(retourStock);

        System.out.println("=====================RETOUR ADDED SUCCESSFULLY =================");

    }

    @Override
    public RetourStock findById(int id) {
        return retourStockDAO.findById(id);
    }
    
     @Override
    public void removeRetourStock(RetourStock retourStock) {
      retourStockDAO.removeRetourStock(retourStock);
    }

    @Override
    public List<RetourStock> findbyZone(int zone_id) {
        return retourStockDAO.findByZone(zone_id);
    }

    @Override
    public List<RetourStock> findByChantier(int id) {
        return retourStockDAO.findByChantier(id);
    }
    
    

}
