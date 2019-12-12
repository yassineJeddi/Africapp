/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.service.stock;

import java.util.Date;
import java.util.List;
import ma.bservices.tgcc.Entity.AffectationStock;

/**
 *
 * @author IRAAMANE
 */
public interface AffectationStockService {
    
    public void saveAffectationStock(AffectationStock affectStock, int article_id, int zone_id, int lot_id, Double quantite, Date dateAffect, int ch);
    
    public void updateAffectationStock(AffectationStock affectStock, int zone_id, int lot_id, Double quantite);

    public List<AffectationStock> findAll();
    
    public void removeAff(AffectationStock affStock);
    
    public List<AffectationStock> findByIntervalDate(int chantier_id, Date dateFrom, Date dateTo);
    
    public List<AffectationStock> findAllInChantier(int id);


    public List<AffectationStock> findAllByArticle(int codeArticle);

    public AffectationStock findById(int id);
    
    public List<AffectationStock> findByZone(int zone_id);
    
    
    public void printStuff();
  
    
    
    
}
