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
@Table(name = "HEURESSUPPLEMENTAIRES")
public class HeuresSupplementaires implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -3119548160979804344L;
    

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "DATE")
    @Temporal(TemporalType.DATE)
    private Date date;

    @Transient
    private String chaineDate;

    @Column(name = "HEUREDEBUT")
    private String heureDebut;

    @Column(name = "HEUREFIN")
    private String heureFin;

    @Column(name = "NOMBREHEURES")
    private Integer nombreHeures;

    @Column(name = "NOMBREMINUTES")
    private Integer nombreMinutes;

    @ManyToOne
    private EtatHeuresSupplementaires etat;

    @ManyToOne
    private Salarie salarie;

    @ManyToOne
    private Chantier chantier;

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

    @Column(name = "VALIDEPAR")
    private String validePar;

    @Column(name = "DATEVALIDATION")
    @Temporal(TemporalType.DATE)
    private Date dateValidation;

    @Column(name = "LONGDATETIMEDEBUTHS")
    private Long longDateTimeDebutHS;

    @Column(name = "LONGDATETIMEFINHS")
    private Long longDateTimeFinHS;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getHeureDebut() {
        return heureDebut;
    }

    public void setHeureDebut(String heureDebut) {
        this.heureDebut = heureDebut;
    }

    public String getHeureFin() {
        return heureFin;
    }

    public void setHeureFin(String heureFin) {
        this.heureFin = heureFin;
    }

    /**
     * @return the nombreHeures
     */
    public Integer getNombreHeures() {
        return nombreHeures;
    }

    /**
     * @param nombreHeures the nombreHeures to set
     */
    public void setNombreHeures(Integer nombreHeures) {
        this.nombreHeures = nombreHeures;
    }

    /**
     * @return the nombreMinutes
     */
    public Integer getNombreMinutes() {
        return nombreMinutes;
    }

    /**
     * @param nombreMinutes the nombreMinutes to set
     */
    public void setNombreMinutes(Integer nombreMinutes) {
        this.nombreMinutes = nombreMinutes;
    }

    public EtatHeuresSupplementaires getEtat() {
        return etat;
    }

    public void setEtat(EtatHeuresSupplementaires etat) {
        this.etat = etat;
    }

    public Salarie getSalarie() {
        return salarie;
    }

    public void setSalarie(Salarie salarie) {
        this.salarie = salarie;
    }

    public Chantier getChantier() {
        return chantier;
    }

    public void setChantier(Chantier chantier) {
        this.chantier = chantier;
    }

    public HeuresSupplementaires() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @return the chaineDate
     */
    public String getChaineDate() {
        return chaineDate;
    }

    /**
     * @param chaineDate the chaineDate to set
     */
    public void setChaineDate(String chaineDate) {
        this.chaineDate = chaineDate;
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
     * @return the validePar
     */
    public String getValidePar() {
        return validePar;
    }

    /**
     * @param validePar the validePar to set
     */
    public void setValidePar(String validePar) {
        this.validePar = validePar;
    }

    /**
     * @return the dateValidation
     */
    public Date getDateValidation() {
        return dateValidation;
    }

    /**
     * @param dateValidation the dateValidation to set
     */
    public void setDateValidation(Date dateValidation) {
        this.dateValidation = dateValidation;
    }

    /**
     * @return the longDateTimeDebutHS
     */
    public Long getLongDateTimeDebutHS() {
        return longDateTimeDebutHS;
    }

    /**
     * @param longDateTimeDebutHS the longDateTimeDebutHS to set
     */
    public void setLongDateTimeDebutHS(Long longDateTimeDebutHS) {
        this.longDateTimeDebutHS = longDateTimeDebutHS;
    }

    /**
     * @return the longDateTimeFinHS
     */
    public Long getLongDateTimeFinHS() {
        return longDateTimeFinHS;
    }

    /**
     * @param longDateTimeFinHS the longDateTimeFinHS to set
     */
    public void setLongDateTimeFinHS(Long longDateTimeFinHS) {
        this.longDateTimeFinHS = longDateTimeFinHS;
    }

}
