/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.service.stock;

import java.io.Serializable;
import java.util.List;
import ma.bservices.beans.Chantier;
import ma.bservices.tgcc.Entity.TransferStock;
import ma.bservices.tgcc.Entity.TransferStockManager;
import ma.bservices.tgcc.dao.stock.TransferStockManagerDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author airaamane
 */


@Service("transferStockManagerService")
public class TransferStockManagerServiceImpl implements Serializable, TransferStockManagerService {
    
    @Autowired
    TransferStockManagerDAO transferStockManagerDAO;

    public TransferStockManagerDAO getTransferStockManagerDAO() {
        return transferStockManagerDAO;
    }

    public void setTransferStockManagerDAO(TransferStockManagerDAO transferStockManagerDAO) {
        this.transferStockManagerDAO = transferStockManagerDAO;
    }
   

    @Override
    public List<TransferStockManager> findAll(Chantier ch) {
        return transferStockManagerDAO.findAll(ch);
    }

    @Override
    public void addEntry(TransferStockManager entry, TransferStock tranferToManage, String comment, boolean isProcessed) {
        
        entry.setTransferToManage(tranferToManage);
        entry.setEntryComment(comment);
        entry.setIsProcessed(isProcessed);
        
        transferStockManagerDAO.addEntry(entry);
    }

    @Override
    public void updateEntry(TransferStockManager entry) {
        transferStockManagerDAO.updateEntry(entry);
    }
    
    
    
}
