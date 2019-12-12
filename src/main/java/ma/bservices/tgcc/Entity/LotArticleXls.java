/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.Entity;

import java.io.Serializable;
import javax.persistence.Basic;
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
import ma.bservices.beans.Lot;

/**
 *
 * @author airaamane
 */
@Entity
@Table(name = "LOT_ARTICLE_XLS")
@XmlRootElement
public class LotArticleXls implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer lotArticleId;
    
    @OneToOne(fetch = FetchType.LAZY)
    private Lot lot;

    @OneToOne
    private Article article;

    public Integer getLotArticleId() {
        return lotArticleId;
    }

    public void setLotArticleId(Integer lotArticleId) {
        this.lotArticleId = lotArticleId;
    }

    public Lot getLotId() {
        return lot;
    }

    public void setLotId(Lot lotId) {
        this.lot = lotId;
    }

    public Article getArticleId() {
        return article;
    }

    public void setArticleId(Article articleId) {
        this.article = articleId;
    }
    
    
    
}
