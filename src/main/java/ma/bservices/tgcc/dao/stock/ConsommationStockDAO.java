/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.dao.stock;

import java.util.Date;
import java.util.List;
import ma.bservices.tgcc.Entity.ConsommationStock;

/**
 *
 * @author IRAAMANE
 */
public interface ConsommationStockDAO {

    public List<ConsommationStock> findAll();

    public void addConsommationStock(ConsommationStock consommationStock);
    
     public List<ConsommationStock> findByIntervalDate(int chantier_id, Date date_from, Date date_to);

    public void removeConsommationStock(ConsommationStock consommationStock);

    public void updateConsommationStock(ConsommationStock consommationStock);

    public ConsommationStock findById(int consommation_id);

    public List<ConsommationStock> findByZone(int zone_id);

    public List<ConsommationStock> findByChantier(int chantier_id);
}
