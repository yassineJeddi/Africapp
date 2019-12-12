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
import javax.xml.bind.annotation.XmlRootElement;
import ma.bservices.beans.Article;
import ma.bservices.beans.Chantier;
import ma.bservices.beans.Lot;
import ma.bservices.beans.Zone;

/*
 * @author IRAAMANE
 */
@Entity
@Table(name = "STOCK_AFFECTATION")
@XmlRootElement
public class AffectationStock implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CODE_AFFECTATION_STOCK")
    private Integer codeAffectationStock;

    @Column(name = "DATE_AFFECTATION_STOCK")
    @Temporal(value = TemporalType.DATE)
    private Date dateeffectStock;
    
    @Column(name = "DATE_MODIFCATION_STOCK")
    @Temporal(value = TemporalType.DATE)
    private Date dateModificationStock;

    @Column(name = "QUANTITE_AFFECTATION_STOCK")
    private Double quantite;

    @ManyToOne
    private Zone zoneId;

    @ManyToOne
    private Lot lotId;
    
    @OneToOne
    private Chantier chantierId;

    @OneToOne
    private Article articleId;

    public Chantier getChantierId() {
        return chantierId;
    }

    public void setChantierId(Chantier chantierId) {
        this.chantierId = chantierId;
    }
  

    public Integer getCodeAffectationStock() {
        return codeAffectationStock;
    }

    public void setCodeAffectationStock(Integer codeAffectationStock) {
        this.codeAffectationStock = codeAffectationStock;
    }

    public Date getDateeffectStock() {
        return dateeffectStock;
    }

    public void setDateeffectStock(Date dateeffectStock) {
        this.dateeffectStock = dateeffectStock;
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

    public AffectationStock() {
    }

    public Date getDateModificationStock() {
        return dateModificationStock;
    }

    public void setDateModificationStock(Date dateModificationStock) {
        this.dateModificationStock = dateModificationStock;
    }
    
    
    
    

}
