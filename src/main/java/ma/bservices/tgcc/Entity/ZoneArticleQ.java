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
import javax.persistence.OneToMany;
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
@Table(name = "STOCK_ARTICLE_Q")
@XmlRootElement
public class ZoneArticleQ implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer codeZoneStockQ;
    
    @Column(name = "DATE_AFFECTATION_STOCK")
    @Temporal(value = TemporalType.DATE)
    private Date dateeffectStock;
    
    @Column
    private Double quantiteZoneStock;

    @ManyToOne
    private Zone zoneId;
    
    @OneToOne
    private Chantier chantierId;
    
    @ManyToOne
    private Lot lotId;

    @OneToOne
    private Article articleId;

    public Integer getCodeZoneStockQ() {
        return codeZoneStockQ;
    }

    public void setCodeZoneStockQ(Integer codeZoneStockQ) {
        this.codeZoneStockQ = codeZoneStockQ;
    }

    public Double getQuantiteZoneStock() {
        return quantiteZoneStock;
    }

    public Date getDateeffectStock() {
        return dateeffectStock;
    }

    public Chantier getChantierId() {
        return chantierId;
    }

    public void setChantierId(Chantier chantierId) {
        this.chantierId = chantierId;
    }
    
    

    public void setDateeffectStock(Date dateeffectStock) {
        this.dateeffectStock = dateeffectStock;
    }

    public void setQuantiteZoneStock(Double quantiteZoneStock) {
        this.quantiteZoneStock = quantiteZoneStock;
    }

    public Zone getZoneId() {
        return zoneId;
    }

    public void setZoneId(Zone zoneId) {
        this.zoneId = zoneId;
    }

    public Article getArticleId() {
        return articleId;
    }

    public void setArticleId(Article articleId) {
        this.articleId = articleId;
    }

    public Lot getLotId() {
        return lotId;
    }

    public void setLotId(Lot lotId) {
        this.lotId = lotId;
    }
    
    

    public ZoneArticleQ() {
    }
    
    
    
    
    
    
}
