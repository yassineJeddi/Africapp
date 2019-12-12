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
@Table(name = "StatusReception")
@XmlRootElement
public class StatusReception implements Serializable{
    
       @Id
    @Column(name = "ID_STATUS_REC")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idStatusReception;
      
    @Column(name = "STAT_LIB")
    private String libStatusRec;

    @OneToMany(mappedBy = "statusTransferId")
    private List<TransferStock> transferList;
    
}
