/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.service.stock;

import java.util.List;
import ma.bservices.beans.Chantier;
import ma.bservices.tgcc.Entity.TransferStock;
import ma.bservices.tgcc.Entity.TransferStockManager;

/**
 *
 * @author airaamane
 */
public interface TransferStockManagerService {
    
    
    public List<TransferStockManager> findAll(Chantier ch);
    
    public void addEntry(TransferStockManager entry, TransferStock tranferToManage, String comment, boolean isProcessed);
    
    public void updateEntry(TransferStockManager entry);
    
    
}
