/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.dao.stock;

import java.util.Date;
import java.util.List;
import ma.bservices.tgcc.Entity.RetourStock;

/**
 *
 * @author IRAAMANE
 */
public interface RetourStockDAO {

    public List<RetourStock> findAll();

    public void addRetourStock(RetourStock retourStock);
    
     public List<RetourStock> findByIntervalDate(int chantier_id, Date date_from, Date date_to);

    public void updateRetourStock(RetourStock retourStock);
    
    public void removeRetourStock(RetourStock retourStock);

    public RetourStock findById(int retour_id);

    public List<RetourStock> findByZone(int zone_id);
    
    public List<RetourStock> findByChantier(int chantier_id);
}
