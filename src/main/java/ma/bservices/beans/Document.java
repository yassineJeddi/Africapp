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
import ma.bservices.tgcc.Entity.CarteGasoil;
import ma.bservices.tgcc.Entity.Loyer;
import ma.bservices.tgcc.Entity.Voiture;

@Entity
@Table(name = "DOCUMENT")
public class Document implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 8737240602038201108L;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "NODEREF")
    private String nodeRef;

    @Column(name = "CREEPAR")
    private String creePar;

    @Column(name = "DATECREATION")
    @Temporal(TemporalType.DATE)
    private Date dateCreation;

    @Column(name = "MODIFIEPAR")
    protected String modifiePar;

    @Column(name = "DATEMODIFICATION")
    @Temporal(TemporalType.DATE)
    private Date dateModification;

    @ManyToOne
    private TypeDocument typeDocument;

    @ManyToOne
    private Salarie salarie;

    @ManyToOne
    private Voiture voiture;

    @ManyToOne
    private Chantier chantier;
    
    @ManyToOne
    private CarteGasoil carteGasoil;
    
    @ManyToOne
    private Loyer loyer;
    
    @Column(name = "TITRE")
    private String titre;

    @Column(name = "COMMENTAIRE")
    private String commentaire;

    @Column(name = "CheminDocument")
    private String chemin;

    @Transient
    private String chaineDateCreation;

    public String getChemin() {
        return chemin;
    }

    public void setChemin(String chemin) {
        this.chemin = chemin;
    }

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
     * @return the nodeRef
     */
    public String getNodeRef() {
        return nodeRef;
    }

    /**
     * @param nodeRef the nodeRef to set
     */
    public void setNodeRef(String nodeRef) {
        this.nodeRef = nodeRef;
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

    /**
     * @return the typeDocument
     */
    public TypeDocument getTypeDocument() {
        return typeDocument;
    }

    /**
     * @param typeDocument the typeDocument to set
     */
    public void setTypeDocument(TypeDocument typeDocument) {
        this.typeDocument = typeDocument;
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
     * @return the chaineDateCreation
     */
    public String getChaineDateCreation() {
        return chaineDateCreation;
    }

    /**
     * @param chaineDateCreation the chaineDateCreation to set
     */
    public void setChaineDateCreation(String chaineDateCreation) {
        this.chaineDateCreation = chaineDateCreation;
    }

    /**
     * @return the titre
     */
    public String getTitre() {
        return titre;
    }

    /**
     * @param titre the titre to set
     */
    public void setTitre(String titre) {
        this.titre = titre;
    }

    public Voiture getVoiture() {
        return voiture;
    }

    public void setVoiture(Voiture voiture) {
        this.voiture = voiture;
    }

    public Chantier getChantier() {
        return chantier;
    }

    public void setChantier(Chantier chantier) {
        this.chantier = chantier;
    }

    public CarteGasoil getCarteGasoil() {
        return carteGasoil;
    }

    public void setCarteGasoil(CarteGasoil carteGasoil) {
        this.carteGasoil = carteGasoil;
    }

    public Loyer getLoyer() {
        return loyer;
    }

    public void setLoyer(Loyer loyer) {
        this.loyer = loyer;
    }

    @Override
    public String toString() {
        return "Document{" + "id=" + id + ", nodeRef=" + nodeRef + ", creePar=" + creePar + ", dateCreation=" + dateCreation + ", modifiePar=" + modifiePar + ", dateModification=" + dateModification + ", typeDocument=" + typeDocument + ", salarie=" + salarie + ", voiture=" + voiture + ", chantier=" + chantier + ", carteGasoil=" + carteGasoil + ", loyer=" + loyer + ", titre=" + titre + ", commentaire=" + commentaire + ", chemin=" + chemin + ", chaineDateCreation=" + chaineDateCreation + '}';
    }
 
    
    
    

}
