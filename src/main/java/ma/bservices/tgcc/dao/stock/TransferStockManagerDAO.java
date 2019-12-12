/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.dao.stock;

import java.util.List;
import ma.bservices.beans.Chantier;
import ma.bservices.tgcc.Entity.TransferStockManager;

/**
 *
 * @author airaamane
 */
public interface TransferStockManagerDAO {
    
    
    public List<TransferStockManager> findAll(Chantier chantier);
    
    public void addEntry(TransferStockManager entry);
    
    public void updateEntry(TransferStockManager entry);
    
    
}
