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
import ma.bservices.tgcc.Entity.ZoneArticleQ;
import ma.bservices.tgcc.dao.engin.ChantierDAO;
import ma.bservices.tgcc.dao.engin.LotDAO;
import ma.bservices.tgcc.dao.engin.ZoneDAO;
import ma.bservices.tgcc.dao.stock.ArticleDAO;
import ma.bservices.tgcc.dao.stock.ZoneArticleQDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author IRAAMANE
 */
@Service("zoneArticleQService")
public class ZoneArticleQServiceImpl implements ZoneArticleQService, Serializable {

    @Autowired
    ZoneArticleQDAO zoneArticleQDAO;

    @Autowired
    ArticleDAO articleDAO;
    
    @Autowired
    ChantierDAO chantierDAO;

    @Autowired
    ZoneDAO zoneDAO;

    @Autowired
    LotDAO lotDAO;

    public ZoneArticleQDAO getZoneArticleQDAO() {
        return zoneArticleQDAO;
    }

    public ChantierDAO getChantierDAO() {
        return chantierDAO;
    }

    public void setChantierDAO(ChantierDAO chantierDAO) {
        this.chantierDAO = chantierDAO;
    }
    
    

    public void setZoneArticleQDAO(ZoneArticleQDAO zoneArticleQDAO) {
        this.zoneArticleQDAO = zoneArticleQDAO;
    }

    public ZoneArticleQDAO getZoneArticleDAO() {
        return zoneArticleQDAO;
    }

    public void setZoneArticleDAO(ZoneArticleQDAO zoneArticleDAO) {
        this.zoneArticleQDAO = zoneArticleDAO;
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
    public List<ZoneArticleQ> findAll() {
        return zoneArticleQDAO.findAll();
    }

    @Override
    public List<ZoneArticleQ> findByArticleId(int article_id, int chantier_id) {
        return zoneArticleQDAO.findByArticleId(article_id, chantier_id);
    }

    @Override
    public ZoneArticleQ findByZoneArticle(int article_id, int zone_id, int lot_id) {
        return zoneArticleQDAO.findByZoneArticle(article_id, zone_id, lot_id);
    }

     @Override
    public void addZoneArticleQ(ZoneArticleQ zoneArticleQ, int article_id, int zone_id, int lot_id, Double quantite, int chantier_id) {
        //get article
        Article article = articleDAO.findOneByCode(article_id);

        //get zone
        Zone zone = zoneDAO.findById(zone_id);
        
        //get chantier
        Chantier chantier = chantierDAO.findById(chantier_id);

        //get lot
        Lot lot = lotDAO.findById(lot_id);

        zoneArticleQ.setArticleId(article);
        zoneArticleQ.setZoneId(zone);
        zoneArticleQ.setLotId(lot);
        zoneArticleQ.setQuantiteZoneStock(quantite);
        zoneArticleQ.setChantierId(chantier);

        //save zoneArticleQ
        zoneArticleQDAO.addZoneArticleQ(zoneArticleQ);
    }
    
    @Override
    public void addZoneArticleQQ(ZoneArticleQ zoneArticleQ, int article_id, int zone_id, int lot_id, Double quantite, int chantier_id, Date dateaffect) {
        //get article
        Article article = articleDAO.findOneByCode(article_id);

        //get zone
        Zone zone = zoneDAO.findById(zone_id);
        
        //get chantier
        Chantier chantier = chantierDAO.findById(chantier_id);

        //get lot
        Lot lot = lotDAO.findById(lot_id);

        zoneArticleQ.setArticleId(article);
        zoneArticleQ.setZoneId(zone);
        zoneArticleQ.setLotId(lot);
        zoneArticleQ.setQuantiteZoneStock(quantite);
        zoneArticleQ.setChantierId(chantier);
        zoneArticleQ.setDateeffectStock(dateaffect);

        //save zoneArticleQ
        zoneArticleQDAO.addZoneArticleQ(zoneArticleQ);
    }

    @Override
    public void updateZoneArticleQQ(ZoneArticleQ zoneArticleQ, Double quantite, Date dateaffect) {
        zoneArticleQ.setQuantiteZoneStock(quantite);
        
        zoneArticleQ.setDateeffectStock(dateaffect);
        
        zoneArticleQDAO.updateZoneArticleQ(zoneArticleQ);
    }
    
       @Override
    public void updateZoneArticleQ(ZoneArticleQ zoneArticleQ, Double quantite) {
        zoneArticleQ.setQuantiteZoneStock(quantite);
        
        zoneArticleQDAO.updateZoneArticleQ(zoneArticleQ);
    }

    @Override
    public void removeZoneArticleQ(ZoneArticleQ zoneArticleQ) {
        zoneArticleQDAO.removeZoneArticleQ(zoneArticleQ);
    }

    @Override
    public ZoneArticleQ findById(int id) {
        return zoneArticleQDAO.findById(id);
    }

     @Override
    public List<ZoneArticleQ> findByChantier(int id) {
        return zoneArticleQDAO.findByChantier(id);
    }

    
    @Override
    public void updateZoneArticleQAll(ZoneArticleQ zoneArticleQ, int zone_id, int lot_id, Double quantite) {
        
        //get zone
        Zone zone = zoneDAO.findById(zone_id);
        //get lot
        Lot lot = lotDAO.findById(lot_id);
        
        zoneArticleQ.setZoneId(zone);
        zoneArticleQ.setLotId(lot);
        zoneArticleQ.setQuantiteZoneStock(quantite);
        
        zoneArticleQDAO.updateZoneArticleQ(zoneArticleQ);
        
    }

}
