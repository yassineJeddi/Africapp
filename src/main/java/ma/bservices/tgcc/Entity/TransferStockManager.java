/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.Entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author airaamane
 */

@Entity
@Table(name = "STOCK_TRANSFER_MANAGER")
public class TransferStockManager implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CODE_TRANSFERT_STOCk")
    private int codeTransferManager;
    
    
    private String entryComment;
    
    
    @OneToOne
    private TransferStock transferToManage;
    
    
    private boolean isProcessed;
    

    public boolean isIsProcessed() {
        return isProcessed;
    }

    public void setIsProcessed(boolean isProcessed) {
        this.isProcessed = isProcessed;
    }

    public int getCodeTransferManager() {
        return codeTransferManager;
    }

    public void setCodeTransferManager(int codeTransferManager) {
        this.codeTransferManager = codeTransferManager;
    }

    public String getEntryComment() {
        return entryComment;
    }

    public void setEntryComment(String entryComment) {
        this.entryComment = entryComment;
    }

    public TransferStock getTransferToManage() {
        return transferToManage;
    }

    public void setTransferToManage(TransferStock transferToManage) {
        this.transferToManage = transferToManage;
    } 
    
}
