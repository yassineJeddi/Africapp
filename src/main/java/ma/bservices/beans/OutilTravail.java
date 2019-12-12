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
import javax.persistence.Transient;

@Entity
@Table(name = "OUTILTRAVAIL")
public class OutilTravail implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -1996857210705543192L;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    private CategorieOutilTravail categorieOutilTravail;

    @Column(name = "REFERENCE")
    private String reference;

    @Column(name = "DATEAFFECTATION")
    @Temporal(TemporalType.DATE)
    private Date dateAffectation;

    @Column(name = "COMMENTAIRE")
    private String commentaire;

    @ManyToOne
    private Salarie salarie;

    @Transient
    private String chaineDateAffectation;

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
     * @return the categorieOutilTravail
     */
    public CategorieOutilTravail getCategorieOutilTravail() {
        return categorieOutilTravail;
    }

    /**
     * @param categorieOutilTravail the categorieOutilTravail to set
     */
    public void setCategorieOutilTravail(CategorieOutilTravail categorieOutilTravail) {
        this.categorieOutilTravail = categorieOutilTravail;
    }

    /**
     * @return the reference
     */
    public String getReference() {
        return reference;
    }

    /**
     * @param reference the reference to set
     */
    public void setReference(String reference) {
        this.reference = reference;
    }

    /**
     * @return the dateAffectation
     */
    public Date getDateAffectation() {
        return dateAffectation;
    }

    /**
     * @param dateAffectation the dateAffectation to set
     */
    public void setDateAffectation(Date dateAffectation) {
        this.dateAffectation = dateAffectation;
    }

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

    /**
     * @return the salarie
     */
    public Salarie getSalarie() {
        return salarie;
    }

    /**
     * @param salarie the salarie to set
     */
    public void setSalarie(Salarie salarie) {
        this.salarie = salarie;
    }

    /**
     * @return the chaineDateAffectation
     */
    public String getChaineDateAffectation() {
        return chaineDateAffectation;
    }

    /**
     * @param chaineDateAffectation the chaineDateAffectation to set
     */
    public void setChaineDateAffectation(String chaineDateAffectation) {
        this.chaineDateAffectation = chaineDateAffectation;
    }

}
