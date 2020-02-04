/**
 *
 */
package ma.bservices.beans;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author root
 *
 */
@Entity
@Table(name = "ARTICLEDA")
public class ArticleDA implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -821780792616864446L;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "REF")
    private String refArticle;

    @ManyToOne
    private Article article;

    @ManyToOne
    private DemandeApprovisionnement demandeApprovisionnement;

    @Column(name = "QUANTITEARTICLE")
    private Float quantiteArticle;

//	@Column(name = "QUANTITEDEJALIVREE")
//	private Float quantiteDejaLivree;
//	
//	@Column(name = "RESTE")
//	private Float reste;
//	@Column(name = "DATELIVRAISONSOUHAITEE")
//	@Temporal(TemporalType.DATE)
//	private Date dateLivraisonSouhaitee;
//	
	@Column(name = "COMMENTAIRE")
	private String commentaire;
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
     * @return the demandeApprovisionnement
     */
    public DemandeApprovisionnement getDemandeApprovisionnement() {
        return demandeApprovisionnement;
    }

    /**
     * @param demandeApprovisionnement the demandeApprovisionnement to set
     */
    public void setDemandeApprovisionnement(
            DemandeApprovisionnement demandeApprovisionnement) {
        this.demandeApprovisionnement = demandeApprovisionnement;
    }

    /**
     * @return the quantiteArticle
     */
    public Float getQuantiteArticle() {
        return quantiteArticle;
    }

    /**
     * @param quantiteArticle the quantiteArticle to set
     */
    public void setQuantiteArticle(Float quantiteArticle) {
        this.quantiteArticle = quantiteArticle;
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

//	/**
//	 * @return the dateLivraisonSouhaitee
//	 */
//	public Date getDateLivraisonSouhaitee() {
//		return dateLivraisonSouhaitee;
//	}
//
//	/**
//	 * @param dateLivraisonSouhaite the dateLivraisonSouhaitee to set
//	 */
//	public void setDateLivraisonSouhaitee(Date dateLivraisonSouhaitee) {
//		this.dateLivraisonSouhaitee = dateLivraisonSouhaitee;
//	}
//
	/**
	 * @return the commentaire
	 */
	public String getCommentaire() {
		return commentaire;
	}

	/**
	 * @param commentaire the commentaire to set
	 */
	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}
}
