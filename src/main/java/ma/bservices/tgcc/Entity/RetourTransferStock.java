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
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import ma.bservices.beans.Article;
import ma.bservices.beans.Chantier;

/**
 *
 * @author IRAAMANE
 */

@Entity
@Table(name = "STOCK_TRANSFER_RETOUR")
public class RetourTransferStock implements Serializable{
     private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CODE_TRANSFERT_RETOUR_STOCk")
    private int codeRetourTransfertStock;

    @Column
    @Temporal(value = TemporalType.DATE)
    private Date dateTransferRetourStock;
    
      @Column
    @Temporal(value = TemporalType.DATE)
    private Date dateReceptionRetourStock;
      
    @Column(name = "QUANTITE_RETOUR_STOCK")
    private Integer quantiteRetourTransfert;
    
   
    
   
    @OneToOne
    private Chantier chantierRetourneurId;

    @OneToOne
    private Chantier chantierInitialId;

    @OneToOne
    private Article articleId;

    @ManyToOne
    private StatusTransfert statusTransferId;

    public int getCodeRetourTransfertStock() {
        return codeRetourTransfertStock;
    }

    public void setCodeRetourTransfertStock(int codeRetourTransfertStock) {
        this.codeRetourTransfertStock = codeRetourTransfertStock;
    }

    public Date getDateTransferRetourStock() {
        return dateTransferRetourStock;
    }

    public void setDateTransferRetourStock(Date dateTransferRetourStock) {
        this.dateTransferRetourStock = dateTransferRetourStock;
    }

    public Date getDateReceptionRetourStock() {
        return dateReceptionRetourStock;
    }

    public void setDateReceptionRetourStock(Date dateReceptionRetourStock) {
        this.dateReceptionRetourStock = dateReceptionRetourStock;
    }

    public Integer getQuantiteRetourTransfert() {
        return quantiteRetourTransfert;
    }

    public void setQuantiteRetourTransfert(Integer quantiteRetourTransfert) {
        this.quantiteRetourTransfert = quantiteRetourTransfert;
    }

    public Chantier getChantierRetourneurId() {
        return chantierRetourneurId;
    }

    public void setChantierRetourneurId(Chantier chantierRetourneurId) {
        this.chantierRetourneurId = chantierRetourneurId;
    }

    public Chantier getChantierInitialId() {
        return chantierInitialId;
    }

    public void setChantierInitialId(Chantier chantierInitialId) {
        this.chantierInitialId = chantierInitialId;
    }

    public Article getArticleId() {
        return articleId;
    }

    public void setArticleId(Article articleId) {
        this.articleId = articleId;
    }

    public StatusTransfert getStatusTransferId() {
        return statusTransferId;
    }

    public void setStatusTransferId(StatusTransfert statusTransferId) {
        this.statusTransferId = statusTransferId;
    }
    
    
    

    
    
    
}
