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
import ma.bservices.beans.Chantier;
import ma.bservices.beans.Salarie;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author j.allali
 */
@Entity
@Table(name = "CARTEGASOIL")
public class CarteGasoil implements Serializable {

    ////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////VARIABLES////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "NUM_CARTEGASOIL")
    private String numcartegasoil;
    
    @Column(name = "CODE_PINE")
    private String codePin;
    
    @Column(name = "PLAFOND_DOTATION")
    private String plafondotation;
    
    @Column(name = "DATE_VALIDATION")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @Temporal(TemporalType.DATE)
    private Date datefin;
    
    @Column(name = "DATE_AFECTATION")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @Temporal(TemporalType.DATE)
    private Date dateafectation;
    
    @ManyToOne
    private Salarie salarie;

    @ManyToOne
    private Chantier chantier;
    
    @Column(name = "AFFECT")
    private Boolean affect;
    
    @Column(name = "ARCHIVER")
    private Boolean archiver;

    ////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////GETERS ET SETERS/////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////

    public Date getDateafectation() {
        return dateafectation;
    }

    public void setDateafectation(Date dateafectation) {
        this.dateafectation = dateafectation;
    }
    
    public Long getId() {
        return id;
    }

    public String getNumcartegasoil() {
        return numcartegasoil;
    }

    public String getCodePin() {
        return codePin;
    }

    public String getPlafondotation() {
        return plafondotation;
    }

    public Date getDatefin() {
        return datefin;
    }

    public Salarie getSalarie() {
        return salarie;
    }

    public Chantier getChantier() {
        return chantier;
    }

    public Boolean getAffect() {
        return affect;
    }

    public Boolean getArchiver() {
        return archiver;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNumcartegasoil(String numcartegasoil) {
        this.numcartegasoil = numcartegasoil;
    }

    public void setCodePin(String codePin) {
        this.codePin = codePin;
    }

    public void setPlafondotation(String plafondotation) {
        this.plafondotation = plafondotation;
    }

    public void setDatefin(Date datefin) {
        this.datefin = datefin;
    }

    public void setSalarie(Salarie salarie) {
        this.salarie = salarie;
    }

    public void setChantier(Chantier chantier) {
        this.chantier = chantier;
    }

    public void setAffect(Boolean affect) {
        this.affect = affect;
    }

    public void setArchiver(Boolean archiver) {
        this.archiver = archiver;
    }

    ////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////CONSTRUCTEURS////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////
    public CarteGasoil() {
    }

    public CarteGasoil(String numcartegasoil, String codePin, String plafondotation, Date datefin, Salarie salarie, Chantier chantier, Boolean affect, Boolean archiver) {
        this.numcartegasoil = numcartegasoil;
        this.codePin = codePin;
        this.plafondotation = plafondotation;
        this.datefin = datefin;
        this.salarie = salarie;
        this.chantier = chantier;
        this.affect = affect;
        this.archiver = archiver;
    }

    @Override
    public String toString() {
        return "CarteGasoil{" + "id=" + id + ", numcartegasoil=" + numcartegasoil + ", codePin=" + codePin + ", plafondotation=" + plafondotation + ", datefin=" + datefin + ", salarie=" + salarie + ", chantier=" + chantier + ", affect=" + affect + ", archiver=" + archiver + '}';
    }
    
    
}
