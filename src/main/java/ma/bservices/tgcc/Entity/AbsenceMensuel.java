/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.Entity;

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
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author j.allali
 */
@Entity(name = "AbsenceMensuel")
@Table(name = "ABSENCE_MENSUEL")
public class AbsenceMensuel implements Serializable {

    private static final long serialVersionUID = 249304588662226951L;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "DATEDEBUT")
    @Temporal(TemporalType.TIMESTAMP) 
    
    private Date dateDebut;

    
    
    
    @Column(name = "DATEFIN")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateFin;

    @Column(name = "DATEAJOUT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateAjout;

    @Column(name = "COMMENTAIRE")
    private String commentaire;

    /**
     * voir si l'absence est validé ou annulé NULL : pas encore checké, 1 :
     * validé 0 : annulé
     */
    @Column(name = "CHECKED")
    private Integer checked;

    @ManyToOne
    private Mensuel salarie;

    @Column(name = "TYPEABSENCE")
    private String typeAbsence;

    @Transient
    private String chaineDateHeureDebut;

    @Transient
    private String chaineDateHeureFin;

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

    public Date getDateAjout() {
        return dateAjout;
    }

    public void setDateAjout(Date dateAjout) {
        this.dateAjout = dateAjout;
    }

    /**
     * @param dateDebut the dateDebut to set
     */
    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
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

    public Mensuel getSalarie() {
        return salarie;
    }

    public void setSalarie(Mensuel salarie) {
        this.salarie = salarie;
    }

    public String getTypeAbsence() {
        return typeAbsence;
    }
    
  

    public void setTypeAbsence(String typeAbsence) {
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

    public Integer getChecked() {
        return checked;
    }

    public void setChecked(Integer checked) {
        this.checked = checked;
    }

}
