/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.Entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import ma.bservices.beans.Chantier;

/**
 *
 * @author zakaria.dem
 */
@Entity
@Table(name = "POINTAGE")
@XmlRootElement
public class Pointage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "ID_Salarie", referencedColumnName = "ID")
    private Mensuel mensuel;

    @ManyToOne
    @JoinColumn(name = "ID_CRENAU", referencedColumnName = "ID")
    private Crenau crenau;

    @ManyToOne
    @JoinColumn(name = "ID_CHANTIER", referencedColumnName = "PRJAP_ID")
    private Chantier chantier;

    @Column(name = "AUTRE")
    private String autre;

    @Column(name = "AUTRE_TYPE")
    private String autreType;
    
    @Column(name = "CODE_FICHE")
    private String codeFiche;

    @Column(name = "DAY")
    @Temporal(TemporalType.DATE)
    private Date day;

    @Column(name = "DEFINITIVE")
    private int definitive;

    @Column(name = "ALL_DAY")
    private Boolean allDay;

    public Integer getId() {
        return id;
    }

    public Mensuel getMensuel() {
        return mensuel;
    }

    public void setMensuel(Mensuel mensuel) {
        this.mensuel = mensuel;
    }

    public Crenau getCrenau() {
        return crenau;
    }

    public void setCrenau(Crenau crenau) {
        this.crenau = crenau;
    }

    public Chantier getChantier() {
        return chantier;
    }

    public void setChantier(Chantier chantier) {
        this.chantier = chantier;
    }

    public String getAutre() {
        return autre;
    }

    public void setAutre(String autre) {
        this.autre = autre;
    }

    public String getAutreType() {
        return autreType;
    }

    public void setAutreType(String autreType) {
        this.autreType = autreType;
    }

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    public int getDefinitive() {
        return definitive;
    }

    public void setDefinitive(int definitive) {
        
        this.definitive = definitive;
    }

    public Boolean getAllDay() {
        return allDay;
    }

    public void setAllDay(Boolean allDay) {
        this.allDay = allDay;
    }

    public String getCodeFiche() {
        return codeFiche;
    }

    public void setCodeFiche(String codeFiche) {
        this.codeFiche = codeFiche;
    }
    
    

}
