package ma.bservices.beans;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ARTICLEBL")
public class ArticleBL implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -6792379048055777494L;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    private BonLivraison bonLivraison;

    @Column(name = "REF")
    private String refArticle;

    @ManyToOne
    private Article article;

    @Column(name = "QUANTITEINITIALE")
    private Float quantiteInitiale;

    @Column(name = "QUANTITEVALIDEE")
    private Float quantiteValidee;

    @Column(name = "QUANTITELIVREE")
    private Float quantiteLivree;

    @Column(name = "RESTE")
    private Float reste;

    @Column(name = "NUMERODIVALTO")
    private String numeroDivalto;

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the bonLivraison
     */
    public BonLivraison getBonLivraison() {
        return bonLivraison;
    }

    /**
     * @param bonLivraison the bonLivraison to set
     */
    public void setBonLivraison(BonLivraison bonLivraison) {
        this.bonLivraison = bonLivraison;
    }

    /**
     * @return the article
     */
    public Article getArticle() {
        return article;
    }

    /**
     * @param article the article to set
     */
    public void setArticle(Article article) {
        this.article = article;
    }

    /**
     * @return the quantiteInitiale
     */
    public Float getQuantiteInitiale() {
        return quantiteInitiale;
    }

    /**
     * @param quantiteInitiale the quantiteInitiale to set
     */
    public void setQuantiteInitiale(Float quantiteInitiale) {
        this.quantiteInitiale = quantiteInitiale;
    }

    /**
     * @return the quantiteValidee
     */
    public Float getQuantiteValidee() {
        return quantiteValidee;
    }

    /**
     * @param quantiteValidee the quantiteValidee to set
     */
    public void setQuantiteValidee(Float quantiteValidee) {
        this.quantiteValidee = quantiteValidee;
    }

    /**
     * @return the quantiteLivree
     */
    public Float getQuantiteLivree() {
        return quantiteLivree;
    }

    /**
     * @param quantiteLivree the quantiteLivree to set
     */
    public void setQuantiteLivree(Float quantiteLivree) {
        this.quantiteLivree = quantiteLivree;
    }

    /**
     * @return the reste
     */
    public Float getReste() {
        return reste;
    }

    /**
     * @param reste the reste to set
     */
    public void setReste(Float reste) {
        this.reste = reste;
    }

    /**
     * @return the numeroDivalto
     */
    public String getNumeroDivalto() {
        return numeroDivalto;
    }

    /**
     * @param numeroDivalto the numeroDivalto to set
     */
    public void setNumeroDivalto(String numeroDivalto) {
        this.numeroDivalto = numeroDivalto;
    }

    /**
     * @return the refArticle
     */
    public String getRefArticle() {
        return refArticle;
    }

    /**
     * @param refArticle the refArticle to set
     */
    public void setRefArticle(String refArticle) {
        this.refArticle = refArticle;
    }

}
