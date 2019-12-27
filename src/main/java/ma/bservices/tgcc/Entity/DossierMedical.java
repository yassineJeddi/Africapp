/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.Entity;

import java.io.Serializable; 
import java.util.Date; 
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity; 
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;  
import javax.persistence.OneToOne;
import javax.persistence.Temporal; 
import javax.persistence.Transient;
import ma.bservices.beans.Salarie;

/**
 *
 * @author BARAKA
 */
@Entity 
public class DossierMedical implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Transient
    private int triOrdre=1;
    
    @Column(name = "DOCTEUR")
    private String docteur;
    
    @Column(name = "ETAT")
    private String etat; 

    @Column(name = "CREEPAR")
    private String creePar;
    
    @Column(name = "DATECREATION")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateCreation;

    @Column(name = "MODIFIEPAR")
    private String modifiePar;

    @Column(name = "ACTIF")
    private boolean actif;

    @Column(name = "SERVICE")
    private String service;

    @Column(name = "POST_TRA")
    private String posteTravail;

    @Column(name = "RISQUE")
    private String risque;

    @Column(name = "ACT_PROF")
    private String activiteProfessionel;

    @Column(name = "MALADIE_PERS")
    private String maladiePersonnelle;

    @Column(name = "IPP")
    private String ipp;

    @Column(name = "FORMATION_PROF")
    private String formationProfessionel;

    @Column(name = "ACCIDNT")
    private String accident;

    @Column(name = "AVP")
    private String avp;

    @Column(name = "ATCDCH")
    private String atcdch;

    @Column(name = "ATCDMD")
    private String atcdmd;

    @Column(name = "INTOXICATION")
    private String intoxication;

    @Column(name = "BCG")
    private String bcg;

    @Column(name = "BCG2")
    private Date bcg2;

    @Column(name = "BCG3")
    private String bcg3;

    @Column(name = "TETANOS")
    private String tetanos;

    @Column(name = "AUTRES")
    private String autres;

    @Column(name = "PERE")
    private String pere;

    @Column(name = "MERE")
    private String mere;

    @Column(name = "SOEURS")
    private String soeurs;

    @Column(name = "FRERES")
    private String freres;

    @Column(name = "CONJOIN")
    private String conjoin;

    @Column(name = "ENFANTS")
    private String enfants;

    @Column(name = "PROCHAINE_VISITE")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date prochaineVisite;
    
    @Column(name = "TITULARISATION")
    private String titularisation; 
    
    @Column(name = "DERN_VISITE")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date lastVisite;
    
    @Column(name = "DATE_EMBAUCHE")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateEmbauche;
    
    @Column(name = "DATEMODIFICATION")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateModification;
    
    @OneToOne
    private Salarie salarie; 

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocteur() {
        return docteur;
    }

    public void setDocteur(String docteur) {
        this.docteur = docteur;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public String getCreePar() {
        return creePar;
    }

    public void setCreePar(String creePar) {
        this.creePar = creePar;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public String getModifiePar() {
        return modifiePar;
    }

    public void setModifiePar(String modifiePar) {
        this.modifiePar = modifiePar;
    }

    public Date getDateModification() {
        return dateModification;
    }

    public void setDateModification(Date dateModification) {
        this.dateModification = dateModification;
    }

    public Salarie getSalarie() {
        return salarie;
    }

    public void setSalarie(Salarie salarie) {
        this.salarie = salarie;
    } 
    
    public DossierMedical() {
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DossierMedical)) {
            return false;
        }
        DossierMedical other = (DossierMedical) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public String toString() {
        return "ma.bservices.tgcc.Entity.DossierMedical[ id=" + id + " ]";
    }

    public boolean isActif() {
        return actif;
    }

    public void setActif(boolean actif) {
        this.actif = actif;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getPosteTravail() {
        return posteTravail;
    }

    public void setPosteTravail(String posteTravail) {
        this.posteTravail = posteTravail;
    }

    public String getRisque() {
        return risque;
    }

    public void setRisque(String risque) {
        this.risque = risque;
    }

    public String getActiviteProfessionel() {
        return activiteProfessionel;
    }

    public void setActiviteProfessionel(String activiteProfessionel) {
        this.activiteProfessionel = activiteProfessionel;
    }

    public String getMaladiePersonnelle() {
        return maladiePersonnelle;
    }

    public void setMaladiePersonnelle(String maladiePersonnelle) {
        this.maladiePersonnelle = maladiePersonnelle;
    }

    public String getIpp() {
        return ipp;
    }

    public void setIpp(String ipp) {
        this.ipp = ipp;
    }

    public String getFormationProfessionel() {
        return formationProfessionel;
    }

    public void setFormationProfessionel(String formationProfessionel) {
        this.formationProfessionel = formationProfessionel;
    }

    public String getAccident() {
        return accident;
    }

    public void setAccident(String accident) {
        this.accident = accident;
    }

    public String getIntoxication() {
        return intoxication;
    }

    public void setIntoxication(String intoxication) {
        this.intoxication = intoxication;
    }

    public String getBcg() {
        return bcg;
    }

    public void setBcg(String bcg) {
        this.bcg = bcg;
    }

    public String getTetanos() {
        return tetanos;
    }

    public void setTetanos(String tetanos) {
        this.tetanos = tetanos;
    }

    public String getAutres() {
        return autres;
    }

    public void setAutres(String autres) {
        this.autres = autres;
    }

    public String getPere() {
        return pere;
    }

    public void setPere(String pere) {
        this.pere = pere;
    }

    public String getMere() {
        return mere;
    }

    public void setMere(String mere) {
        this.mere = mere;
    }

    public String getSoeurs() {
        return soeurs;
    }

    public void setSoeurs(String soeurs) {
        this.soeurs = soeurs;
    }

    public String getFreres() {
        return freres;
    }

    public void setFreres(String freres) {
        this.freres = freres;
    }

    public String getConjoin() {
        return conjoin;
    }

    public void setConjoin(String conjoin) {
        this.conjoin = conjoin;
    }

    public String getEnfants() {
        return enfants;
    }

    public void setEnfants(String enfants) {
        this.enfants = enfants;
    }
 

    public Date getLastVisite() {
        return lastVisite;
    }

    public void setLastVisite(Date lastVisite) {
        this.lastVisite = lastVisite;
    } 

    public Date getProchaineVisite() {
        return prochaineVisite;
    }

    public void setProchaineVisite(Date prochaineVisite) {
        this.prochaineVisite = prochaineVisite;
    }

    public String getTitularisation() {
        return titularisation;
    }

    public void setTitularisation(String titularisation) {
        this.titularisation = titularisation;
    }

    public Date getDateEmbauche() {
        return dateEmbauche;
    }

    public void setDateEmbauche(Date dateEmbauche) {
        this.dateEmbauche = dateEmbauche;
    } 

    public Date getBcg2() {
        return bcg2;
    }

    public void setBcg2(Date bcg2) {
        this.bcg2 = bcg2;
    }

    public String getBcg3() {
        return bcg3;
    }

    public void setBcg3(String bcg3) {
        this.bcg3 = bcg3;
    }

    public String getAvp() {
        return avp;
    }

    public void setAvp(String avp) {
        this.avp = avp;
    }

    public String getAtcdch() {
        return atcdch;
    }

    public void setAtcdch(String atcdch) {
        this.atcdch = atcdch;
    }

    public String getAtcdmd() {
        return atcdmd;
    }

    public void setAtcdmd(String atcdmd) {
        this.atcdmd = atcdmd;
    }

    public int getTriOrdre() {
        return triOrdre;
    }

    public void setTriOrdre(int triOrdre) {
        this.triOrdre = triOrdre;
    }
    
}
