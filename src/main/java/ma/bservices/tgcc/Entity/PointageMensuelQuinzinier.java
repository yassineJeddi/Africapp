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
import javax.xml.bind.annotation.XmlRootElement;
import ma.bservices.beans.Chantier;
import ma.bservices.beans.Fonction;
import ma.bservices.beans.Chantier;
import ma.bservices.beans.Fonction;

/**
 *
 * @author a.wattah
 */

@Entity
@Table(name = "POINTAGEMENSULQUINZINIER")
@XmlRootElement
public class PointageMensuelQuinzinier implements Serializable{
    
    
    @Id
    @Column(name="ID")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
	
    @Column(name = "TYPEPOINTAGE")
    private String typePointage;

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
	
    @Column(name="LONGDATETIMEENTREE")
    private Long longDateTimeEntree;
	
    @Column(name="LONGDATETIMESORTIE")
    private Long longDateTimeSortie;

    @ManyToOne 
    private Chantier chantier;
	
    @ManyToOne
    private Mensuel mensuel;

   @ManyToOne
   private Fonction fonction;    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTypePointage() {
        return typePointage;
    }

    public void setTypePointage(String typePointage) {
        this.typePointage = typePointage;
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

    public Chantier getChantier() {
        return chantier;
    }

    public void setChantier(Chantier chantier) {
        this.chantier = chantier;
    }

    public Mensuel getMensuel() {
        return mensuel;
    }

    public void setMensuel(Mensuel mensuel) {
        this.mensuel = mensuel;
    }

    public Fonction getFonction() {
        return fonction;
    }

    public void setFonction(Fonction fonction) {
        this.fonction = fonction;
    }
    
    
   
   
   
   
}
