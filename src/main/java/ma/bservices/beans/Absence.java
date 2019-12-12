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
@Table(name = "ABSENCE")
public class Absence implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 249304588662226951L;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "DATEDEBUT")
    @Temporal(TemporalType.DATE)
    private Date dateDebut;

    @Column(name = "HEUREDEBUT")
    private String heureDebut;

    @Column(name = "DATEFIN")
    @Temporal(TemporalType.DATE)
    private Date dateFin;

    @Column(name = "HEUREFIN")
    private String heureFin;

    @Column(name = "COMMENTAIRE")
    private String commentaire;

    @Column(name = "LONGDATETIMEDEBUT")
    private Long longDateTimeDebut;

    @Column(name = "LONGDATETIMEFIN")
    private Long longDateTimeFin;

    @ManyToOne
    private Salarie salarie;

    @ManyToOne
    private TypeAbsence typeAbsence;

    @Transient
    private String chaineDateHeureDebut;

    @Transient
    private String chaineDateHeureFin;
    
    @Transient
    private Integer idtypeAbs; 
    
    @Transient
    private int heuredeb; 
    
    @Transient
    private int mindeb; 
    
    @Transient
    private int heureF; 
    
    @Transient
    private int minF; 
    
    

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
     * @return the dateDebut
     */
    public Date getDateDebut() {
        return dateDebut;
    }

    /**
     * @param dateDebut the dateDebut to set
     */
    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    /**
     * @return the heureDebut
     */
    public String getHeureDebut() {
        return heureDebut;
    }

    /**
     * @param heureDebut the heureDebut to set
     */
    public void setHeureDebut(String heureDebut) {
        this.heureDebut = heureDebut;
    }

    /**
     * @return the dateFin
     */
    public Date getDateFin() {
        return dateFin;
    }

    /**
     * @param dateFin the dateFin to set
     */
    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    /**
     * @return the heureFin
     */
    public String getHeureFin() {
        return heureFin;
    }

    /**
     * @param heureFin the heureFin to set
     */
    public void setHeureFin(String heureFin) {
        this.heureFin = heureFin;
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
     * @return the longDateTimeDebut
     */
    public Long getLongDateTimeDebut() {
        return longDateTimeDebut;
    }

    /**
     * @param longDateTimeDebut the longDateTimeDebut to set
     */
    public void setLongDateTimeDebut(Long longDateTimeDebut) {
        this.longDateTimeDebut = longDateTimeDebut;
    }

    /**
     * @return the longDateTimeFin
     */
    public Long getLongDateTimeFin() {
        return longDateTimeFin;
    }

    /**
     * @param longDateTimeFin the longDateTimeFin to set
     */
    public void setLongDateTimeFin(Long longDateTimeFin) {
        this.longDateTimeFin = longDateTimeFin;
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
     * @return the typeAbsence
     */
    public TypeAbsence getTypeAbsence() {
        return typeAbsence;
    }

    /**
     * @param typeAbsence the typeAbsence to set
     */
    public void setTypeAbsence(TypeAbsence typeAbsence) {
        this.typeAbsence = typeAbsence;
    }

    public String getChaineDateHeureDebut() {
        return chaineDateHeureDebut;
    }

    public void setChaineDateHeureDebut(String chaineDateHeureDebut) {
        this.chaineDateHeureDebut = chaineDateHeureDebut;
    }

    public String getChaineDateHeureFin() {
        return chaineDateHeureFin;
    }

    public void setChaineDateHeureFin(String chaineDateHeureFin) {
        this.chaineDateHeureFin = chaineDateHeureFin;
    }


    public int getHeuredeb() {
        return heuredeb;
    }

    public void setHeuredeb(int heuredeb) {
        this.heuredeb = heuredeb;
    }

    public int getMindeb() {
        return mindeb;
    }

    public void setMindeb(int mindeb) {
        this.mindeb = mindeb;
    }

    public int getHeureF() {
        return heureF;
    }

    public void setHeureF(int heureF) {
        this.heureF = heureF;
    }

    public int getMinF() {
        return minF;
    }

    public void setMinF(int minF) {
        this.minF = minF;
    }

    public Integer getIdtypeAbs() {
        return idtypeAbs;
    }

    public void setIdtypeAbs(Integer idtypeAbs) {
        this.idtypeAbs = idtypeAbs;
    }
    
    

    
    
}
