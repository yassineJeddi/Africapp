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
import javax.persistence.Transient;

/**
 * @author root
 *
 */
@Entity
@Table(name = "BONLIVRAISON")
public class BonLivraison implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 6336400504793831806L;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "NUMEROBL")
    private String numeroBL;

    @Column(name = "NUMEROBLDIVA")
    private String numeroBLDiva;

    @Column(name = "DATELIVRAISON")
    @Temporal(TemporalType.DATE)
    private Date dateLivraison;

    @Column(name = "COMMENTAIRE")
    private String commentaire;

    @Column(name = "NUMEROBC")
    private String numeroBC;

    @Column(name = "NUMEROBCSECONDAIRE")
    private String numeroBCSecondaire;

    @Column(name = "NODEREFDOCUMENT")
    private String nodeRefDocument;

    @Column(name = "FOURNISSEUR")
    private String fournisseur;

    @ManyToOne
    private Chantier chantier;

    @ManyToOne
    private Utilisateur responsable;

    @Column(name = "ETATBL")
    private boolean etatBL;

    @Transient
    private String chaineDateLivraison;

    @Column(name = "CREEPAR")
    private String creePar;

    @Column(name = "DATECREATION")
    @Temporal(TemporalType.DATE)
    private Date dateCreation;

    @Column(name = "MODIFIEPAR")
    private String modifiePar;

    @Column(name = "DATEMODIFICATION")
    @Temporal(TemporalType.DATE)
    private Date dateModification;

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
     * @return the numeroBL
     */
    public String getNumeroBL() {
        return numeroBL;
    }

    /**
     * @param numeroBL the numeroBL to set
     */
    public void setNumeroBL(String numeroBL) {
        this.numeroBL = numeroBL;
    }

    /**
     * @return the numeroBLDiva
     */
    public String getNumeroBLDiva() {
        return numeroBLDiva;
    }

    /**
     * @param numeroBLDiva the numeroBLDiva to set
     */
    public void setNumeroBLDiva(String numeroBLDiva) {
        this.numeroBLDiva = numeroBLDiva;
    }

    /**
     * @return the dateLivraison
     */
    public Date getDateLivraison() {
        return dateLivraison;
    }

    /**
     * @param dateLivraison the dateLivraison to set
     */
    public void setDateLivraison(Date dateLivraison) {
        this.dateLivraison = dateLivraison;
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
     * @return the numeroBC
     */
    public String getNumeroBC() {
        return numeroBC;
    }

    /**
     * @param numeroBC the numeroBC to set
     */
    public void setNumeroBC(String numeroBC) {
        this.numeroBC = numeroBC;
    }

    /**
     * @return the nodeRefDocument
     */
    public String getNodeRefDocument() {
        return nodeRefDocument;
    }

    /**
     * @param nodeRefDocument the nodeRefDocument to set
     */
    public void setNodeRefDocument(String nodeRefDocument) {
        this.nodeRefDocument = nodeRefDocument;
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
     * @return the responsable
     */
    public Utilisateur getResponsable() {
        return responsable;
    }

    /**
     * @param responsable the responsable to set
     */
    public void setResponsable(Utilisateur responsable) {
        this.responsable = responsable;
    }

    /**
     * @return the numeroBCSecondaire
     */
    public String getNumeroBCSecondaire() {
        return numeroBCSecondaire;
    }

    /**
     * @param numeroBCSecondaire the numeroBCSecondaire to set
     */
    public void setNumeroBCSecondaire(String numeroBCSecondaire) {
        this.numeroBCSecondaire = numeroBCSecondaire;
    }

    /**
     * @return the fournisseur
     */
    public String getFournisseur() {
        return fournisseur;
    }

    /**
     * @param fournisseur the fournisseur to set
     */
    public void setFournisseur(String fournisseur) {
        this.fournisseur = fournisseur;
    }

    /**
     * @return the etatBL
     */
    public boolean isEtatBL() {
        return etatBL;
    }

    /**
     * @param etatBL the etatBL to set
     */
    public void setEtatBL(boolean etatBL) {
        this.etatBL = etatBL;
    }

    /**
     * @return the chaineDateLivraison
     */
    public String getChaineDateLivraison() {
        return chaineDateLivraison;
    }

    /**
     * @param chaineDateLivraison the chaineDateLivraison to set
     */
    public void setChaineDateLivraison(String chaineDateLivraison) {
        this.chaineDateLivraison = chaineDateLivraison;
    }

    /**
     * @return the creePar
     */
    public String getCreePar() {
        return creePar;
    }

    /**
     * @param creePar the creePar to set
     */
    public void setCreePar(String creePar) {
        this.creePar = creePar;
    }

    /**
     * @return the dateCreation
     */
    public Date getDateCreation() {
        return dateCreation;
    }

    /**
     * @param dateCreation the dateCreation to set
     */
    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    /**
     * @return the modifiePar
     */
    public String getModifiePar() {
        return modifiePar;
    }

    /**
     * @param modifiePar the modifiePar to set
     */
    public void setModifiePar(String modifiePar) {
        this.modifiePar = modifiePar;
    }

    /**
     * @return the dateModification
     */
    public Date getDateModification() {
        return dateModification;
    }

    /**
     * @param dateModification the dateModification to set
     */
    public void setDateModification(Date dateModification) {
        this.dateModification = dateModification;
    }

}
