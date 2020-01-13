/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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

/**
 *
 * @author yassine
 */
@Entity
public class AccidentTravail implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(name = "DATE_ACCIDENT")
    Date dateAccident;
    @Column(name = "LIEU")
    String lieu;
    @Column(name = "DESCRIPTION")
    String description;
    @Column(name = "CHEFPROJET")
    String chefProjet ;
    @Column(name = "CHEFCHANTIER")
    String chefChantier ; 
    @Column(name = "NBJARRET")
    Integer nbjArret ; 
    @Column(name = "NBJPAYE")
    Integer nbjPaye ; 
    @Column(name = "DATERETOUR")
    Date dateRetour ; 
    @Column(name = "DATERETOURREEL")
    Date dateRetourReel ; 
    @Column(name = "MTNPAYE")
    Double mtnPaye ; 
    @Column(name = "MORTEL")
    Boolean mortel =Boolean.FALSE;
    @Column(name = "VALIDRH")
    Boolean valideRH =Boolean.FALSE;
    @Column(name = "VALIDQHSE")
    Boolean valideQhse =Boolean.FALSE;
    @Column(name = "TELCONTACTER")
    String telContacter ; 
    @Column(name = "DATECREATION")
    Date dateCreation ; 
    @Column(name = "CERTIFICATINITIAL")
    Boolean certificatInitial =Boolean.FALSE;
    
    
    @ManyToOne
    Chantier chantier;
    
    @ManyToOne
    Salarie salarie;
    
    @ManyToOne
    Utilisateur creePar;

    public String getChefProjet() {
        return chefProjet;
    }

    public void setChefProjet(String chefProjet) {
        this.chefProjet = chefProjet;
    }

    public String getChefChantier() {
        return chefChantier;
    }

    public void setChefChantier(String chefChantier) {
        this.chefChantier = chefChantier;
    }

    public Date getDateAccident() {
        return dateAccident;
    }

    public void setDateAccident(Date dateAccident) {
        this.dateAccident = dateAccident;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Integer getNbjArret() {
        return nbjArret;
    }

    public void setNbjArret(Integer nbjArret) {
        this.nbjArret = nbjArret;
    }

    public Integer getNbjPaye() {
        return nbjPaye;
    }

    public void setNbjPaye(Integer nbjPaye) {
        this.nbjPaye = nbjPaye;
    }

    public Date getDateRetour() {
        return dateRetour;
    }

    public void setDateRetour(Date dateRetour) {
        this.dateRetour = dateRetour;
    }

    public Double getMtnPaye() {
        return mtnPaye;
    }

    public void setMtnPaye(Double mtnPaye) {
        this.mtnPaye = mtnPaye;
    }

    public Boolean getValideRH() {
        return valideRH;
    }

    public void setValideRH(Boolean valideRH) {
        this.valideRH = valideRH;
    }

    public Boolean getValideQhse() {
        return valideQhse;
    }

    public void setValideQhse(Boolean valideQhse) {
        this.valideQhse = valideQhse;
    }

    public Date getDateRetourReel() {
        return dateRetourReel;
    }

    public void setDateRetourReel(Date dateRetourReel) {
        this.dateRetourReel = dateRetourReel;
    } 

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getMortel() {
        return mortel;
    }

    public void setMortel(Boolean mortel) {
        this.mortel = mortel;
    }

    public String getTelContacter() {
        return telContacter;
    }

    public void setTelContacter(String telContacter) {
        this.telContacter = telContacter;
    }

    public Utilisateur getCreePar() {
        return creePar;
    }

    public void setCreePar(Utilisateur creePar) {
        this.creePar = creePar;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Boolean getCertificatInitial() {
        return certificatInitial;
    }

    public void setCertificatInitial(Boolean certificatInitial) {
        this.certificatInitial = certificatInitial;
    }


    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AccidentTravail)) {
            return false;
        }
        AccidentTravail other = (AccidentTravail) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "AccidentTravail{" + "id=" + id + ", dateAccident=" + dateAccident + ", lieu=" + lieu + ", description=" + description + ", chantier=" + chantier + ", salarie=" + salarie + '}';
    }

    
    
}
