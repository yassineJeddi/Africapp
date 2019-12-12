/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.Entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import ma.bservices.beans.Article;
import ma.bservices.beans.Chantier;

/**
 *
 * @author IRAAMANE
 */
@Entity
@Table(name = "STOCK_CHANTIER_Q")
@XmlRootElement

public class ChantierArticleQ implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer codeChantierStockQ;

    @Column
    private Double quantiteChantierStock;

    @Column
    private Integer idVentilation;

    @OneToOne(fetch = FetchType.LAZY)
    private Chantier chantierId;

    @OneToOne
    private Article articleId;

    @Column(name = "FAM1")
    private String fam;

    @Column(name = "FAM2")
    private String sfam;

    @Column(name = "FAM3")
    private String ssfam;

    @Column(name = "PRORATA")
    private String prorata;

    @Column(name = "TYPCONSO")
    private String typConso;
    
    @Column(name = "NATURE")
    private String nature;

    @Column(name = "ENCOURS")
    private boolean estEnCoursDeTransfert;

    public Integer getIdVentilation() {
        return idVentilation;
    }

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    
    public void setIdVentilation(Integer idVentilation) {
        this.idVentilation = idVentilation;
    }

    public Integer getCodeChantierStockQ() {
        return codeChantierStockQ;
    }

    public void setCodeChantierStockQ(Integer codeChantierStockQ) {
        this.codeChantierStockQ = codeChantierStockQ;
    }

    public Double getQuantiteChantierStock() {
        return quantiteChantierStock;
    }

    public void setQuantiteChantierStock(Double quantiteChantierStock) {
        this.quantiteChantierStock = quantiteChantierStock;
    }

    public Chantier getChantierId() {
        return chantierId;
    }

    public String getFam() {
        return fam;
    }

    public void setFam(String fam) {
        this.fam = fam;
    }

    public String getSfam() {
        return sfam;
    }

    public void setSfam(String sfam) {
        this.sfam = sfam;
    }

    public String getSsfam() {
        return ssfam;
    }

    public void setSsfam(String ssfam) {
        this.ssfam = ssfam;
    }

    public String getProrata() {
        return prorata;
    }

    public void setProrata(String prorata) {
        this.prorata = prorata;
    }

    public String getTypConso() {
        return typConso;
    }

    public void setTypConso(String typConso) {
        this.typConso = typConso;
    }
    
    

    public boolean isEstEnCoursDeTransfert() {
        return estEnCoursDeTransfert;
    }

    public void setEstEnCoursDeTransfert(boolean estEnCoursDeTransfert) {
        this.estEnCoursDeTransfert = estEnCoursDeTransfert;
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

    public ChantierArticleQ() {
    }

}
