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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import ma.bservices.beans.Article;
import ma.bservices.beans.Chantier;
import ma.bservices.beans.Lot;
import ma.bservices.beans.Zone;

/**
 *
 * @author IRAAMANE
 */
@Entity
@Table(name = "STOCK_CONSOMMATION")
@XmlRootElement
public class ConsommationStock implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CODE_CONSOMMATION_STOCK")
    private Integer codeConsommationStock;

    @Column(name = "DATE_CONSOMMATION_STOCK")
    @Temporal(value = TemporalType.DATE)
    private Date dateConsoStock;
    
     @Column(name = "DATE_CONSOMMATION_REELE")
    @Temporal(value = TemporalType.DATE)
    private Date dateReeleConso;

    @Column(name = "DATE_MODIFICATION_STOCK")
    @Temporal(value = TemporalType.DATE)
    private Date dateModifConsoStock;

    @Column(name = "QUANTITE_CONSOMMATION_STOCK")
    private Double quantite;

    @OneToOne
    private Zone zoneId;

    @OneToOne(fetch = FetchType.LAZY)
    private Chantier chantierId;

    @OneToOne
    private Lot lotId;

    @OneToOne
    private Article articleId;

    public Integer getCodeConsommationStock() {
        return codeConsommationStock;
    }

    public void setCodeConsommationStock(Integer codeConsommationStock) {
        this.codeConsommationStock = codeConsommationStock;
    }

    public Date getDateConsoStock() {
        return dateConsoStock;
    }

    public void setDateConsoStock(Date dateConsoStock) {
        this.dateConsoStock = dateConsoStock;
    }

    public Double getQuantite() {
        return quantite;
    }

    public void setQuantite(Double quantite) {
        this.quantite = quantite;
    }

    public Zone getZoneId() {
        return zoneId;
    }

    public void setZoneId(Zone zoneId) {
        this.zoneId = zoneId;
    }

    public Date getDateReeleConso() {
        return dateReeleConso;
    }

    public void setDateReeleConso(Date dateReeleConso) {
        this.dateReeleConso = dateReeleConso;
    }
    
    

    public Lot getLotId() {
        return lotId;
    }

    public void setLotId(Lot lotId) {
        this.lotId = lotId;
    }

    public Chantier getChantierId() {
        return chantierId;
    }

    public void setChantierId(Chantier chantierId) {
        this.chantierId = chantierId;
    }

    public Article getArticleId() {
        return articleId;
    }

    public void setArticleId(Article articleId) {
        this.articleId = articleId;
    }

    public Date getDateModifConsoStock() {
        return dateModifConsoStock;
    }

    public void setDateModifConsoStock(Date dateModifConsoStock) {
        this.dateModifConsoStock = dateModifConsoStock;
    }

    public ConsommationStock() {
    }

}
