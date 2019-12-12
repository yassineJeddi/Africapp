/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.service.stock;

import java.util.Date;
import java.util.List;
import ma.bservices.tgcc.Entity.RetourStock;

/**
 *
 * @author IRAAMANE
 */
public interface RetourStockService {

    public List<RetourStock> findAll();
    
    public void removeRetourStock(RetourStock retourStock);

    public List<RetourStock> findbyZone(int zone_id);
    
     public List<RetourStock> findByIntervalDate(int chantier_id, Date dateFrom, Date dateTo);

    public void addRetourStock(RetourStock retourStock, int article_id, int zone_id, int lot_id, Double quantite, Date dateRetour, int chantier_id);

    public RetourStock findById(int id);
    
    public List<RetourStock> findByChantier(int id);
}
