/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.Entity;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 *
 * @author j.allali
 */
@Entity
@Table(name = "AFFECTATION_FINANCIERE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Affectation.findAll", query = "SELECT a FROM Affectation a"),
    @NamedQuery(name = "Affectation.findByIDAffectation", query = "SELECT a FROM Affectation a WHERE a.iDAffectation = :iDAffectation"),
    @NamedQuery(name = "Affectation.findByAffectation", query = "SELECT a FROM Affectation a WHERE a.affectation = :affectation")})

public class Affectation implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_Affectation")
    private Integer iDAffectation;

    @Column(name = "Affectation")
    private Integer affectation;

    @Column(name = "DATEEFFECT")
    @Temporal(TemporalType.DATE)
    private Date dateeffect;

    @Column(name = "AFFECTPAR")
    private String affectpar;

    @Transient
    private String stringDate;
    
    @Column(name = "ARCHIVE")
    private Boolean archived = Boolean.FALSE;
    
    @Column(name = "DATE_ARCHIVAGE")
    @Temporal(TemporalType.DATE)
    private Date dateArchivage;
    
    

    public String getStringDate() {

        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        stringDate = formatter.format(this.getDateeffect());

        return stringDate;
    }

    public void setStringDate(String stringDate) {
        this.stringDate = stringDate;
    }

    @JoinColumn(name = "ID_Salarie", referencedColumnName = "ID")
    @ManyToOne
    private Mensuel mensuel;

//    @JoinColumn(name = "ID_Chantier", referencedColumnName = "PRJAP_ID")
//    @ManyToOne(fetch = FetchType.LAZY)
//    private Chantier iDChantier;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "affectation", cascade = CascadeType.ALL)
    @Fetch(value = FetchMode.SUBSELECT)
    List<SousAffectation> sousAffectation;

    @Column(name = "TypeAffectation")
    private String typeAffectation;

    public List<SousAffectation> getSousAffectation() {
        return sousAffectation;
    }

    public void setSousAffectation(List<SousAffectation> sousAffectation) {
        this.sousAffectation = sousAffectation;
    }

    public String getAffectpar() {
        return affectpar;
    }

    public void setAffectpar(String affectpar) {
        this.affectpar = affectpar;
    }

    public Integer getiDAffectation() {
        return iDAffectation;
    }

    public String getTypeAffectation() {
        return typeAffectation;
    }

    public void setTypeAffectation(String typeAffectation) {
        this.typeAffectation = typeAffectation;
    }

    public void setiDAffectation(Integer iDAffectation) {
        this.iDAffectation = iDAffectation;
    }

    public Date getDateeffect() {
        return dateeffect;
    }

    public void setDateeffect(Date dateeffect) {
        this.dateeffect = dateeffect;
    }

//    public Chantier getiDChantier() {
//        return iDChantier;
//    }
//
//    public void setiDChantier(Chantier iDChantier) {
//        this.iDChantier = iDChantier;
//    }
    public Affectation() {
    }

    public Affectation(Date dateeffect, Mensuel mensuel) {
        this.dateeffect = dateeffect;
        this.mensuel = mensuel;
    }

    public Affectation(Integer iDAffectation) {
        this.iDAffectation = iDAffectation;
    }

    public Integer getIDAffectation() {
        return iDAffectation;
    }

    public void setIDAffectation(Integer iDAffectation) {
        this.iDAffectation = iDAffectation;
    }

    public Integer getAffectation() {
        return affectation;
    }

    public void setAffectation(Integer affectation) {
        this.affectation = affectation;
    }

    public Mensuel getMensuel() {
        return mensuel;
    }

    public void setMensuel(Mensuel mensuel) {
        this.mensuel = mensuel;
    }

    public Boolean getArchived() {
        return archived;
    }

    public void setArchived(Boolean archived) {
        this.archived = archived;
    }

    public Date getDateArchivage() {
        return dateArchivage;
    }

    public void setDateArchivage(Date dateArchivage) {
        this.dateArchivage = dateArchivage;
    }
    
    

//    public Chantier getIDChantier() {
//        return iDChantier;
//    }
//
//    public void setIDChantier(Chantier iDChantier) {
//        this.iDChantier = iDChantier;
//    }
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDAffectation != null ? iDAffectation.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Affectation)) {
            return false;
        }
        Affectation other = (Affectation) object;
        if ((this.iDAffectation == null && other.iDAffectation != null) || (this.iDAffectation != null && !this.iDAffectation.equals(other.iDAffectation))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ma.bservices.tgcc.Entity.Affectation[ iDAffectation=" + iDAffectation + " ]";
    }

}
