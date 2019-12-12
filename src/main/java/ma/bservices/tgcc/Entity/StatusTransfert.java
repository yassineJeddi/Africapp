/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.Entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author IRAAMANE
 */

@Entity
@Table(name = "StatusTransfert")
@XmlRootElement
public class StatusTransfert implements Serializable{
    
      @Id
    @Column(name = "ID_STATUS_TRANSFERT")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idStatusTransfert;
      
    @Column(name = "STAT_LIB")
    private String libStatusTransfer;

    @OneToMany(mappedBy = "statusTransferId")
    private List<TransferStock> transferList;

    public Integer getIdStatusTransfert() {
        return idStatusTransfert;
    }

    public void setIdStatusTransfert(Integer idStatusTransfert) {
        this.idStatusTransfert = idStatusTransfert;
    }

    public String getLibStatusTransfer() {
        return libStatusTransfer;
    }

    public void setLibStatusTransfer(String libStatusTransfer) {
        this.libStatusTransfer = libStatusTransfer;
    }

    public List<TransferStock> getTransferList() {
        return transferList;
    }

    public void setTransferList(List<TransferStock> transferList) {
        this.transferList = transferList;
    }

    public StatusTransfert(String libStatusTransfer) {
        this.libStatusTransfer = libStatusTransfer;
    }

    public StatusTransfert() {
    }
    
    
    
    
    
    
    
}
