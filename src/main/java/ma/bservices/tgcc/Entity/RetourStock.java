/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.Entity;

import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "STOCK_RETOUR")
@XmlRootElement
public class RetourStock {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CODE_RETOUR_STOCK")
    private Integer codeRetourStock;

    @Column(name = "DATE_RETOUR_STOCK")
    @Temporal(value = TemporalType.DATE)
    private Date dateRetourStock;

    @Column(name = "DATE_MODFRETOUR_STOCK")
    @Temporal(value = TemporalType.DATE)
    private Date dateModifRetourStock;

    @Column(name = "QUANTITE_RETOUR_STOCK")
    private Double quantite;

    @OneToOne
    private Zone zoneId;

    @OneToOne
    private Lot lotId;

    @OneToOne
    private Article articleId;

    @OneToOne
    private Chantier chantierId;

    public Chantier getChantierId() {
        return chantierId;
    }

    public void setChantierId(Chantier chantierId) {
        this.chantierId = chantierId;
    }

    public Integer getCodeRetourStock() {
        return codeRetourStock;
    }

    public void setCodeRetourStock(Integer codeRetourStock) {
        this.codeRetourStock = codeRetourStock;
    }

    public Date getDateRetourStock() {
        return dateRetourStock;
    }

    public void setDateRetourStock(Date dateRetourStock) {
        this.dateRetourStock = dateRetourStock;
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

    public Lot getLotId() {
        return lotId;
    }

    public void setLotId(Lot lotId) {
        this.lotId = lotId;
    }

    public Article getArticleId() {
        return articleId;
    }

    public void setArticleId(Article articleId) {
        this.articleId = articleId;
    }

    public Date getDateModifRetourStock() {
        return dateModifRetourStock;
    }

    public void setDateModifRetourStock(Date dateModifRetourStock) {
        this.dateModifRetourStock = dateModifRetourStock;
    }

    public RetourStock() {
    }

}
