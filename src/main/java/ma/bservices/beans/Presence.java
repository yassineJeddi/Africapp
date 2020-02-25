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
@Table(name = "PRESENCE")
public class Presence implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -2141683080327219549L;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "PRESENCE_ID")
    private Integer presenceId;

    @Column(name = "DATE")
    @Temporal(TemporalType.DATE)
    private Date date;

    @Column(name = "HEUREENTREE")
    private String heureEntree;

    @Column(name = "HEURESORTIE")
    private String heureSortie;

    @Column(name = "NOMBREHEURES")
    private Integer nombreHeures;

    @Column(name = "NOMBREMINUTES")
    private Integer nombreMinutes;

    @Column(name = "LONGDATETIMEENTREE")
    private Long longDateTimeEntree;

    @Column(name = "LONGDATETIMESORTIE")
    private Long longDateTimeSortie;

    @ManyToOne
    private Chantier chantier;

    @ManyToOne
    private Salarie salarie;

    @Transient
    private String chaineDate;

    @Transient
    private boolean multiplePointage;

    @Column(name = "HEUREENTREEREELLE")
    private String heureEntreeReelle;

    @Column(name = "HEURESORTIEREELLE")
    private String heureSortieReelle;

    @Column(name = "DATESAISIEHEUREENTREE", columnDefinition = "datetime2(7)")
    private Date dateSaisieHeureEntree;

    @Column(name = "DATESAISIEHEURESORTIE", columnDefinition = "datetime2(7)")
    private Date dateSaisieHeureSortie;

    @Column(name = "FLAG")
    private boolean flag;

    @Column(name = "CREEPAR")
    private String creePar;

    @Column(name = "DATECREATION")
    @Temporal(TemporalType.DATE)
    private Date dateCreation;

    @Column(name = "CE1", columnDefinition = "char(1)")
    private String ce1;

    @Column(name = "CONF", columnDefinition = "char(5)")
    private String conf;

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

    public String getHeureEntree() {
        return heureEntree;
    }

    public void setHeureEntree(String heureEntree) {
        this.heureEntree = heureEntree;
    }

    public String getHeureSortie() {
        return heureSortie;
    }

    public void setHeureSortie(String heureSortie) {
        this.heureSortie = heureSortie;
    }

    public Chantier getChantier() {
        return chantier;
    }

    public void setChantier(Chantier chantier) {
        this.chantier = chantier;
    }

    public Salarie getSalarie() {
        return salarie;
    }

    public void setSalarie(Salarie salarie) {
        this.salarie = salarie;
    }

    public String getChaineDate() {
        return chaineDate;
    }

    public void setChaineDate(String chaineDate) {
        this.chaineDate = chaineDate;
    }

    public Integer getNombreHeures() {
        return nombreHeures;
    }

    public void setNombreHeures(Integer nombreHeures) {
        this.nombreHeures = nombreHeures;
    }

    public Integer getNombreMinutes() {
        return nombreMinutes;
    }

    public void setNombreMinutes(Integer nombreMinutes) {
        this.nombreMinutes = nombreMinutes;
    }

    public Long getLongDateTimeEntree() {
        return longDateTimeEntree;
    }

    public void setLongDateTimeEntree(Long longDateTimeEntree) {
        this.longDateTimeEntree = longDateTimeEntree;
    }

    public Long getLongDateTimeSortie() {
        return longDateTimeSortie;
    }

    public void setLongDateTimeSortie(Long longDateTimeSortie) {
        this.longDateTimeSortie = longDateTimeSortie;
    }

    public boolean isMultiplePointage() {
        return multiplePointage;
    }

    public void setMultiplePointage(boolean multiplePointage) {
        this.multiplePointage = multiplePointage;
    }

    /**
     * @return the heureEntreeReelle
     */
    public String getHeureEntreeReelle() {
        return heureEntreeReelle;
    }

    /**
     * @param heureEntreeReelle the heureEntreeReelle to set
     */
    public void setHeureEntreeReelle(String heureEntreeReelle) {
        this.heureEntreeReelle = heureEntreeReelle;
    }

    /**
     * @return the heureSortieReelle
     */
    public String getHeureSortieReelle() {
        return heureSortieReelle;
    }

    /**
     * @param heureSortieReelle the heureSortieReelle to set
     */
    public void setHeureSortieReelle(String heureSortieReelle) {
        this.heureSortieReelle = heureSortieReelle;
    }

    /**
     * @return the dateSaisieHeureEntree
     */
    public Date getDateSaisieHeureEntree() {
        return dateSaisieHeureEntree;
    }

    /**
     * @param dateSaisieHeureEntree the dateSaisieHeureEntree to set
     */
    public void setDateSaisieHeureEntree(Date dateSaisieHeureEntree) {
        this.dateSaisieHeureEntree = dateSaisieHeureEntree;
    }

    /**
     * @return the dateSaisieHeureSortie
     */
    public Date getDateSaisieHeureSortie() {
        return dateSaisieHeureSortie;
    }

    /**
     * @param dateSaisieHeureSortie the dateSaisieHeureSortie to set
     */
    public void setDateSaisieHeureSortie(Date dateSaisieHeureSortie) {
        this.dateSaisieHeureSortie = dateSaisieHeureSortie;
    }

    /**
     * @return the flag
     */
    public boolean isFlag() {
        return flag;
    }

    /**
     * @param flag the flag to set
     */
    public void setFlag(boolean flag) {
        this.flag = flag;
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
     * @return the presenceId
     */
    public Integer getPresenceId() {
        return presenceId;
    }

    /**
     * @param presenceId the presenceId to set
     */
    public void setPresenceId(Integer presenceId) {
        this.presenceId = presenceId;
    }

    /**
     * @return the ce1
     */
    public String getCe1() {
        return ce1;
    }

    /**
     * @param ce1 the ce1 to set
     */
    public void setCe1(String ce1) {
        this.ce1 = ce1;
    }

    /**
     * @return the conf
     */
    public String getConf() {
        return conf;
    }

    /**
     * @param conf the conf to set
     */
    public void setConf(String conf) {
        this.conf = conf;
    }

    @Override
    public String toString() {
        return "Presence{" + "id=" + id + ", presenceId=" + presenceId + ", date=" + date + ", heureEntree=" + heureEntree + ", heureSortie=" + heureSortie + ", nombreHeures=" + nombreHeures + ", nombreMinutes=" + nombreMinutes + ", longDateTimeEntree=" + longDateTimeEntree + ", longDateTimeSortie=" + longDateTimeSortie + ", chaineDate=" + chaineDate + ", multiplePointage=" + multiplePointage + ", heureEntreeReelle=" + heureEntreeReelle + ", heureSortieReelle=" + heureSortieReelle + ", dateSaisieHeureEntree=" + dateSaisieHeureEntree + ", dateSaisieHeureSortie=" + dateSaisieHeureSortie + ", flag=" + flag + ", creePar=" + creePar + ", dateCreation=" + dateCreation + ", ce1=" + ce1 + ", conf=" + conf + '}';
    }

    
}
