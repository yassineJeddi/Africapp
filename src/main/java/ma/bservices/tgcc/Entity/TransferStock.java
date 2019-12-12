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
@Table(name = "STOCK_TRANSFER")
public class TransferStock implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CODE_TRANSFERT_STOCk")
    private int codeTransfertStock;

    @Column(name = "DATE_TRANSFER_STOCK")
    @Temporal(value = TemporalType.DATE)
    private Date dateTransferStock;

    @Column(name = "DATE_RECEPTION_STOCK")
    @Temporal(value = TemporalType.DATE)
    private Date dateReceptionStock;

    @Column(name = "DATE_RETOUR_STOCK")
    @Temporal(value = TemporalType.DATE)
    private Date dateRetourStock;

    @Column(name = "QUANTITE_TRANSFER_STOCK")
    private Double quantite;

    @Column(name = "QUANTITE_ENCOURS_STOCK")
    private Double quantiteEnCours;
    
    
    @Column(name = "QUANTITE_RECEPTION_STOCK")
    private Double quantiteReception;

    @Column(name = "QUANTITE_RETOUR_STOCK")
    private Double quantiteRetour;

    @Column
    private boolean isRetour;

    @Column
    private boolean isRec;

    @Column
    private boolean isRetourRec;

    @Column
    private Integer referenceTransfer;

    @OneToOne
    private Chantier chantierEmetteurId;

    @OneToOne
    private Chantier chantierRecepteurId;

    @OneToOne
    private Article articleId;

    @ManyToOne
    private StatusTransfert statusTransferId;

    public int getCodeTransfertStock() {
        return codeTransfertStock;
    }

    public void setCodeTransfertStock(int codeTransfertStock) {
        this.codeTransfertStock = codeTransfertStock;
    }

    public Date getDateTransferStock() {
        return dateTransferStock;
    }

    public void setDateTransferStock(Date dateTransferStock) {
        this.dateTransferStock = dateTransferStock;
    }

    public Integer getReferenceTransfer() {
        return referenceTransfer;
    }

    public boolean isIsRec() {
        return isRec;
    }

    public void setIsRec(boolean isRec) {
        this.isRec = isRec;
    }

    public void setReferenceTransfer(Integer referenceTransfer) {
        this.referenceTransfer = referenceTransfer;
    }

    public Double getQuantiteEnCours() {
        return quantiteEnCours;
    }

    public void setQuantiteEnCours(Double quantiteEnCours) {
        this.quantiteEnCours = quantiteEnCours;
    }   
    

    public Double getQuantite() {
        return quantite;
    }

    public void setQuantite(Double quantite) {
        this.quantite = quantite;
    }

    public boolean isIsRetourRec() {
        return isRetourRec;
    }

    public void setIsRetourRec(boolean isRetourRec) {
        this.isRetourRec = isRetourRec;
    }

    public Chantier getChantierEmetteurId() {
        return chantierEmetteurId;
    }

    public Article getArticleId() {
        return articleId;
    }

    public void setArticleId(Article articleId) {
        this.articleId = articleId;
    }

    public void setChantierEmetteurId(Chantier chantierEmetteurId) {
        this.chantierEmetteurId = chantierEmetteurId;
    }

    public Chantier getChantierRecepteurId() {
        return chantierRecepteurId;
    }

    public void setChantierRecepteurId(Chantier chantierRecepteurId) {
        this.chantierRecepteurId = chantierRecepteurId;
    }

    public StatusTransfert getStatusTransferId() {
        return statusTransferId;
    }

    public void setStatusTransferId(StatusTransfert statusTransferId) {
        this.statusTransferId = statusTransferId;
    }

    public boolean isIsRetour() {
        return isRetour;
    }

    public void setIsRetour(boolean isRetour) {
        this.isRetour = isRetour;
    }

    public Double getQuantiteReception() {
        return quantiteReception;
    }

    public void setQuantiteReception(Double quantiteReception) {
        this.quantiteReception = quantiteReception;
    }

    public Date getDateReceptionStock() {
        return dateReceptionStock;
    }

    public void setDateReceptionStock(Date dateReceptionStock) {
        this.dateReceptionStock = dateReceptionStock;
    }

    public Date getDateRetourStock() {
        return dateRetourStock;
    }

    public void setDateRetourStock(Date dateRetourStock) {
        this.dateRetourStock = dateRetourStock;
    }

    public Double getQuantiteRetour() {
        return quantiteRetour;
    }

    public void setQuantiteRetour(Double quantiteRetour) {
        this.quantiteRetour = quantiteRetour;
    }

    public TransferStock() {
    }

}
