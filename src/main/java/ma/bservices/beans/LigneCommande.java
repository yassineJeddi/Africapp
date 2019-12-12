/**
 *
 */
package ma.bservices.beans;

import java.io.Serializable;

import javax.persistence.ManyToOne;

/**
 * @author root
 *
 */
//@Entity
//@Table(name = "LIGNECOMMANDE")
public class LigneCommande implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -4864370811710824608L;

//	@Id
//  @Column(name="ID")
//  @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

//	@Column(name = "QUANTITECOMMANDEE")
    private Integer quantiteCommandee;

//	@Column(name = "QUANTITEDEJALIVREE")
    private Integer quantiteDejaLivree;

//	@Column(name = "QUANTITERESTANTE")
    private Integer quantiteRestante;

//	@ManyToOne
    private Article article;

//	@ManyToOne
    private Commande commande;

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
     * @return the quantiteCommandee
     */
    public Integer getQuantiteCommandee() {
        return quantiteCommandee;
    }

    /**
     * @param quantiteCommandee the quantiteCommandee to set
     */
    public void setQuantiteCommandee(Integer quantiteCommandee) {
        this.quantiteCommandee = quantiteCommandee;
    }

    /**
     * @return the quantiteDejaLivree
     */
    public Integer getQuantiteDejaLivree() {
        return quantiteDejaLivree;
    }

    /**
     * @param quantiteDejaLivree the quantiteDejaLivree to set
     */
    public void setQuantiteDejaLivree(Integer quantiteDejaLivree) {
        this.quantiteDejaLivree = quantiteDejaLivree;
    }

    /**
     * @return the quantiteRestante
     */
    public Integer getQuantiteRestante() {
        return quantiteRestante;
    }

    /**
     * @param quantiteRestante the quantiteRestante to set
     */
    public void setQuantiteRestante(Integer quantiteRestante) {
        this.quantiteRestante = quantiteRestante;
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
     * @return the commande
     */
    public Commande getCommande() {
        return commande;
    }

    /**
     * @param commande the commande to set
     */
    public void setCommande(Commande commande) {
        this.commande = commande;
    }

}
