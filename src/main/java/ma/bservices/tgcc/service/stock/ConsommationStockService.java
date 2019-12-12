/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.service.stock;

import java.util.Date;
import java.util.List;
import ma.bservices.tgcc.Entity.ConsommationStock;

/**
 *
 * @author IRAAMANE
 */
public interface ConsommationStockService {

    public List<ConsommationStock> findAll();

    public List<ConsommationStock> findByZone(int zone_id);
    
    public List<ConsommationStock> findByIntervalDate(int chantier_id, Date dateFrom, Date dateTo);
    
    public void removeConsommationStock(ConsommationStock consomationStock);
    
    public List<ConsommationStock> findByChantier(int chantier_id);

    public void addConsommationStock(ConsommationStock consomationStock, int article_id, int zone_id, int lot_id, Double quantite, Date dateConsommation, int chantier_id );

    public void updateConsommationStock(ConsommationStock consomationStock, int zone_id, int lot_id, Double quantite);

    public ConsommationStock findById(int id);

}
