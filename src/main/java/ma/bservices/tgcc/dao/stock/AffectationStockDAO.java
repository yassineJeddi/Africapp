/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.dao.stock;

import java.util.Date;
import java.util.List;
import ma.bservices.tgcc.Entity.AffectationStock;

/**
 *
 * @author IRAAMANE
 */
public interface AffectationStockDAO {

    public void saveAffectationStock(AffectationStock affectStock);
    
    public void deleteAff(AffectationStock aff);
    
    public void updateAffectationStock(AffectationStock affectationStock);

    public List<AffectationStock> findAll();
    
    public List<AffectationStock> findByIntervalDate(int chantier_id, Date date_from, Date date_to);
    
    public List<AffectationStock> findAllInChantier(int chantier_id);

    public List<AffectationStock> findAllAffectationsByArticle(int id);

    public AffectationStock findById(int id);
    
    public List<AffectationStock> findByZone(int zone_id);
}
