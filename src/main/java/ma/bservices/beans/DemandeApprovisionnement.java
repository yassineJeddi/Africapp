/**
 *
 */
package ma.bservices.beans;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.CascadeType;

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

/**
 * @author root
 *
 */
@Entity
@Table(name = "DEMANDEAPPROVISIONNEMENT")
public class DemandeApprovisionnement implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 2689228383039251630L;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "NUMERODA")
    private String numeroDA;

    @Column(name = "DATEAJOUT")
    @Temporal(TemporalType.DATE)
    private Date dateAjout;

    @Column(name = "DATELIVRAISONSOUHAITEE")
    @Temporal(TemporalType.DATE)
    private Date dateLivraisonSouhaitee;

    @Column(name = "COMMENTAIRE")
    private String commentaire;

    @Column(name = "DESTINATIONDA")
    private String destinationDA;

    @ManyToOne
    private Chantier chantier;

    @ManyToOne
    private Utilisateur demandeur;

    @ManyToOne
    private EtatDA etatDA;

    @Transient
    private String chaineDateAjout;

    @Transient
    private String chaineDateLivraisonSouhaitee;

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
     * @return the numeroDA
     */
    public String getNumeroDA() {
        return numeroDA;
    }

    /**
     * @param numeroDA the numeroDA to set
     */
    public void setNumeroDA(String numeroDA) {
        this.numeroDA = numeroDA;
    }

    /**
     * @return the dateAjout
     */
    public Date getDateAjout() {
        return dateAjout;
    }

    /**
     * @param dateAjout the dateAjout to set
     */
    public void setDateAjout(Date dateAjout) {
        this.dateAjout = dateAjout;
    }

    /**
     * @return the dateLivraisonSouhaitee
     */
    public Date getDateLivraisonSouhaitee() {
        return dateLivraisonSouhaitee;
    }

    /**
     * @param dateLivraisonSouhaitee the dateLivraisonSouhaitee to set
     */
    public void setDateLivraisonSouhaitee(Date dateLivraisonSouhaitee) {
        this.dateLivraisonSouhaitee = dateLivraisonSouhaitee;
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
     * @return the chantier
     */
    public Chantier getChantier() {
        return chantier;
    }

    /**
     * @param chantier the chantier to set
     */
    public void setChantier(Chantier chantier) {
        this.chantier = chantier;
    }

    /**
     * @return the demandeur
     */
    public Utilisateur getDemandeur() {
        return demandeur;
    }

    /**
     * @param demandeur the demandeur to set
     */
    public void setDemandeur(Utilisateur demandeur) {
        this.demandeur = demandeur;
    }

    /**
     * @return the etatDA
     */
    public EtatDA getEtatDA() {
        return etatDA;
    }

    /**
     * @param etatDA the etatDA to set
     */
    public void setEtatDA(EtatDA etatDA) {
        this.etatDA = etatDA;
    }

    /**
     * @return the chaineDateAjout
     */
    public String getChaineDateAjout() {
        return chaineDateAjout;
    }

    /**
     * @param chaineDateAjout the chaineDateAjout to set
     */
    public void setChaineDateAjout(String chaineDateAjout) {
        this.chaineDateAjout = chaineDateAjout;
    }

    /**
     * @return the chaineDateLivraisonSouhaitee
     */
    public String getChaineDateLivraisonSouhaitee() {
        return chaineDateLivraisonSouhaitee;
    }

    /**
     * @param chaineDateLivraisonSouhaitee the chaineDateLivraisonSouhaitee to
     * set
     */
    public void setChaineDateLivraisonSouhaitee(String chaineDateLivraisonSouhaitee) {
        this.chaineDateLivraisonSouhaitee = chaineDateLivraisonSouhaitee;
    }

    /**
     * @return the destinationDA
     */
    public String getDestinationDA() {
        return destinationDA;
    }

    /**
     * @param destinationDA the destinationDA to set
     */
    public void setDestinationDA(String destinationDA) {
        this.destinationDA = destinationDA;
    }

}
