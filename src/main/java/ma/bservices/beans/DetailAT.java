/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.beans;

import java.io.Serializable;
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
public class DetailAT implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    
    @ManyToOne
    private AccidentTravail accidentTravail;
    
    /*** CAUSE AT**/
    private Boolean chuteEnHaut =Boolean.FALSE ;
    private Boolean chuteDObjet =Boolean.FALSE ;
    private Boolean trebuchement =Boolean.FALSE ;
    private Boolean manutentionManuelle =Boolean.FALSE ;
    private Boolean manutentionMecanique =Boolean.FALSE ;
    private String  causeProbable = "";
    
    /** Lieu AT**/
    private Boolean interieurLieuTravail =Boolean.FALSE ;
    private Boolean mission =Boolean.FALSE ;
    private Boolean trajet =Boolean.FALSE ;
    private String  LieuPrecis = "";
        
    /**Description AT **/
    private String description = "";
    private String decriptionArabe = "";
    
    /**Nature de la blessure**/
    private Boolean plaieSimple =Boolean.FALSE ;
    private Boolean plaieGrave =Boolean.FALSE ;
    private Boolean contusion =Boolean.FALSE ;
    private Boolean piqure =Boolean.FALSE ;
    private Boolean entorse =Boolean.FALSE ;
    private Boolean fracture =Boolean.FALSE ;
    private Boolean douleur =Boolean.FALSE ;
    private Boolean luxation =Boolean.FALSE ;
    private Boolean bruleurSimple =Boolean.FALSE ;
    private Boolean bruleurGrave =Boolean.FALSE ;
    private Boolean ecrasement =Boolean.FALSE ;
    private Boolean sectionnement =Boolean.FALSE ;
    private Boolean penetrationCorpEtranger =Boolean.FALSE ;
    private String autreNature = "";

    
    /**Siège de la blessure**/
    private Boolean yeuxD =Boolean.FALSE ;
    private Boolean yeuxG =Boolean.FALSE ;
    private Boolean mainD =Boolean.FALSE ;
    private Boolean mainG =Boolean.FALSE ;
    private Boolean brasD =Boolean.FALSE ;
    private Boolean brasG =Boolean.FALSE ;
    private Boolean cuisseD =Boolean.FALSE ;
    private Boolean cuisseG =Boolean.FALSE ;
    private Boolean piedD =Boolean.FALSE ;
    private Boolean piedG =Boolean.FALSE ;
    private Boolean jambeD =Boolean.FALSE ;
    private Boolean jambeG =Boolean.FALSE ;
    private Boolean tete =Boolean.FALSE ;
    private Boolean tronc =Boolean.FALSE ;
    private String autreSiege = "";
    
    
    /**Mesures prises immédiatement**/
    private Boolean secourInterne =Boolean.FALSE ;
    private Boolean secourExterne =Boolean.FALSE ;
    
    
    /**Suite**/
    private Boolean hospitalisation =Boolean.FALSE ;
    private Boolean atSansArret =Boolean.FALSE ;
    private Boolean atAvecArret =Boolean.FALSE ;
    private Boolean deces =Boolean.FALSE ;
    private Integer nbrJour=0;
    
    /***  CAUSE AT**/
    public AccidentTravail getAccidentTravail() {
        return accidentTravail;
    }

    public void setAccidentTravail(AccidentTravail accidentTravail) {
        this.accidentTravail = accidentTravail;
    }

    public Boolean getChuteEnHaut() {
        return chuteEnHaut;
    }

    public void setChuteEnHaut(Boolean chuteEnHaut) {
        this.chuteEnHaut = chuteEnHaut;
    }

    public Boolean getChuteDObjet() {
        return chuteDObjet;
    }

    public void setChuteDObjet(Boolean chuteDObjet) {
        this.chuteDObjet = chuteDObjet;
    }

    public Boolean getTrebuchement() {
        return trebuchement;
    }

    public void setTrebuchement(Boolean trebuchement) {
        this.trebuchement = trebuchement;
    }

    public Boolean getManutentionManuelle() {
        return manutentionManuelle;
    }

    public void setManutentionManuelle(Boolean manutentionManuelle) {
        this.manutentionManuelle = manutentionManuelle;
    }

    public Boolean getManutentionMecanique() {
        return manutentionMecanique;
    }

    public void setManutentionMecanique(Boolean manutentionMecanique) {
        this.manutentionMecanique = manutentionMecanique;
    }

    public String getCauseProbable() {
        return causeProbable;
    }

    public void setCauseProbable(String causeProbable) {
        this.causeProbable = causeProbable;
    }

    /**Description AT **/
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDecriptionArabe() {
        return decriptionArabe;
    }

    public void setDecriptionArabe(String decriptionArabe) {
        this.decriptionArabe = decriptionArabe;
    }

    /** Lieu AT**/
    public Boolean getInterieurLieuTravail() {
        return interieurLieuTravail;
    }

    public void setInterieurLieuTravail(Boolean interieurLieuTravail) {
        this.interieurLieuTravail = interieurLieuTravail;
    }

    public Boolean getMission() {
        return mission;
    }

    public void setMission(Boolean mission) {
        this.mission = mission;
    }

    public Boolean getTrajet() {
        return trajet;
    }

    public void setTrajet(Boolean trajet) {
        this.trajet = trajet;
    }

    public String getLieuPrecis() {
        return LieuPrecis;
    }

    public void setLieuPrecis(String LieuPrecis) {
        this.LieuPrecis = LieuPrecis;
    }

    /**Nature de la blessure**/
    public Boolean getPlaieSimple() {
        return plaieSimple;
    }

    public void setPlaieSimple(Boolean plaieSimple) {
        this.plaieSimple = plaieSimple;
    }

    public Boolean getPlaieGrave() {
        return plaieGrave;
    }

    public void setPlaieGrave(Boolean plaieGrave) {
        this.plaieGrave = plaieGrave;
    }

    public Boolean getContusion() {
        return contusion;
    }

    public void setContusion(Boolean contusion) {
        this.contusion = contusion;
    }

    public Boolean getPiqure() {
        return piqure;
    }

    public void setPiqure(Boolean piqure) {
        this.piqure = piqure;
    }

    public Boolean getEntorse() {
        return entorse;
    }

    public void setEntorse(Boolean entorse) {
        this.entorse = entorse;
    }

    public Boolean getFracture() {
        return fracture;
    }

    public void setFracture(Boolean fracture) {
        this.fracture = fracture;
    }

    public Boolean getDouleur() {
        return douleur;
    }

    public void setDouleur(Boolean douleur) {
        this.douleur = douleur;
    }

    public Boolean getLuxation() {
        return luxation;
    }

    public void setLuxation(Boolean luxation) {
        this.luxation = luxation;
    }

    public Boolean getBruleurSimple() {
        return bruleurSimple;
    }

    public void setBruleurSimple(Boolean bruleurSimple) {
        this.bruleurSimple = bruleurSimple;
    }

    public Boolean getBruleurGrave() {
        return bruleurGrave;
    }

    public void setBruleurGrave(Boolean bruleurGrave) {
        this.bruleurGrave = bruleurGrave;
    }

    public Boolean getEcrasement() {
        return ecrasement;
    }

    public void setEcrasement(Boolean ecrasement) {
        this.ecrasement = ecrasement;
    }

    public Boolean getSectionnement() {
        return sectionnement;
    }

    public void setSectionnement(Boolean sectionnement) {
        this.sectionnement = sectionnement;
    }

    public Boolean getPenetrationCorpEtranger() {
        return penetrationCorpEtranger;
    }

    public void setPenetrationCorpEtranger(Boolean penetrationCorpEtranger) {
        this.penetrationCorpEtranger = penetrationCorpEtranger;
    }

    public String getAutreNature() {
        return autreNature;
    }

    public void setAutreNature(String autreNature) {
        this.autreNature = autreNature;
    }
    
    /**Siège de la blessure**/
    
    
    public Boolean getYeuxD() {    
        return yeuxD;
    }

    public void setYeuxD(Boolean yeuxD) {
        this.yeuxD = yeuxD;
    }

    public Boolean getYeuxG() {
        return yeuxG;
    }

    public void setYeuxG(Boolean yeuxG) {
        this.yeuxG = yeuxG;
    }

    public Boolean getMainD() {
        return mainD;
    }

    public void setMainD(Boolean mainD) {
        this.mainD = mainD;
    }

    public Boolean getMainG() {
        return mainG;
    }

    public void setMainG(Boolean mainG) {
        this.mainG = mainG;
    }

    public Boolean getCuisseD() {
        return cuisseD;
    }

    public void setCuisseD(Boolean cuisseD) {
        this.cuisseD = cuisseD;
    }

    public Boolean getCuisseG() {
        return cuisseG;
    }

    public void setCuisseG(Boolean cuisseG) {
        this.cuisseG = cuisseG;
    }

    public Boolean getPiedD() {
        return piedD;
    }

    public void setPiedD(Boolean piedD) {
        this.piedD = piedD;
    }

    public Boolean getPiedG() {
        return piedG;
    }

    public void setPiedG(Boolean piedG) {
        this.piedG = piedG;
    }

    public Boolean getTete() {
        return tete;
    }

    public void setTete(Boolean tete) {
        this.tete = tete;
    }

    public Boolean getTronc() {
        return tronc;
    }

    public void setTronc(Boolean tronc) {
        this.tronc = tronc;
    }

    public String getAutreSiege() {
        return autreSiege;
    }

    public void setAutreSiege(String autreSiege) {
        this.autreSiege = autreSiege;
    }

    public Boolean getBrasD() {
        return brasD;
    }

    public void setBrasD(Boolean brasD) {
        this.brasD = brasD;
    }

    public Boolean getBrasG() {
        return brasG;
    }

    public void setBrasG(Boolean brasG) {
        this.brasG = brasG;
    }

    public Boolean getJambeD() {
        return jambeD;
    }

    public void setJambeD(Boolean jambeD) {
        this.jambeD = jambeD;
    }

    public Boolean getJambeG() {
        return jambeG;
    }

    public void setJambeG(Boolean jambeG) {
        this.jambeG = jambeG;
    }
    

    /**Suite**/
    public Boolean getSecourInterne() {
        return secourInterne;
    }

    public void setSecourInterne(Boolean secourInterne) {
        this.secourInterne = secourInterne;
    }

    public Boolean getSecourExterne() {
        return secourExterne;
    }

    public void setSecourExterne(Boolean secourExterne) {
        this.secourExterne = secourExterne;
    }

    public Boolean getHospitalisation() {
        return hospitalisation;
    }

    public void setHospitalisation(Boolean hospitalisation) {
        this.hospitalisation = hospitalisation;
    }

    public Boolean getAtSansArret() {
        return atSansArret;
    }

    public void setAtSansArret(Boolean atSansArret) {
        this.atSansArret = atSansArret;
    }

    public Boolean getAtAvecArret() {
        return atAvecArret;
    }

    public void setAtAvecArret(Boolean atAvecArret) {
        this.atAvecArret = atAvecArret;
    }

    public Boolean getDeces() {
        return deces;
    }

    public void setDeces(Boolean deces) {
        this.deces = deces;
    }

    public Integer getNbrJour() {
        return nbrJour;
    }

    public void setNbrJour(Integer nbrJour) {
        this.nbrJour = nbrJour;
    }
    
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        if (!(object instanceof DetailAT)) {
            return false;
        }
        DetailAT other = (DetailAT) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ma.bservices.beans.DetailAT[ id=" + id + " ]";
    }
    
}
