/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.Entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.Entity;
import ma.bservices.beans.Chantier;

/**
 *
 * @author BARAKA
 */
@Entity
public class Visite implements Serializable{
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(name = "DOCTEUR")
    private String docteur;
    
    @Column(name = "TYPE")
    private String type; 
    
    @Column(name = "DATE_VISITE")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateVisite;  

    @Column(name = "CREEPAR")
    private String creePar;
    
    @Column(name = "ETAT")
    private String etat; 
    
    @Column(name = "AU_OG")
    private String auditionOG; 
    
    @Column(name = "AU_OD")
    private String auditionOD; 
    
    @Column(name = "VI_OG")
    private String visionOG; 
    
    @Column(name = "VI_OD")
    private String visionOD; 
    
    @Column(name = "OPTIQUE")
    private boolean optique=false; 
    
    @Column(name = "DUREE_OPTIQUE")
    private String dureeOptique; 
    
    @Column(name = "TAILLE")
    private String taille; 
    
    @Column(name = "POIDS")
    private String poids; 
    
    @Column(name = "PER_THOR")
    private String perThor; 
    
    @Column(name = "CAR_RESP")
    private String carResp; 
    
    @Column(name = "PEAU")
    private String peauPhanere; 
    
    @Column(name = "LOCOMOTO")
    private String locoMoteur;
    
    @Column(name = "CONTROLE")
    private String controle;  
    
    @Column(name = "EXAM_RADIO")
    private String examenRadio; 
    
    @Column(name = "ECG")
    private String ecg; 
    
    @Column(name = "POULS")
    private String pauls; 
    
    @Column(name = "VAISSEUX")
    private String vaisseaux; 
    
    @Column(name = "VARICES")
    private String varices;
    
    @Column(name = "GANGLIONS")
    private String ganglions;
    
    @Column(name = "BOUCHE")
    private String bouche;  
    
    @Column(name = "ABDOMEN")
    private String abdomen;
    
    @Column(name = "FOIE")
    private String foie;  
    
    @Column(name = "RATE")
    private String rate; 
    
    @Column(name = "AMYGDALES")
    private String amygdales; 
    
    @Column(name = "HERNIES")
    private String hernies;  
    
    @Column(name = "CICATRICE")
    private String cicatrice; 
    
    @Column(name = "REGLE")
    private String regle; 
    
    @Column(name = "BLENNO")
    private String blenno; 
    
    @Column(name = "URINE")
    private String urine;
    
    @Column(name = "ENDOCRIN")
    private String endocrinien;  
    
    @Column(name = "NERVOTISME")
    private String nervotisme; 
    
    @Column(name = "TRAMBLEMENT")
    private String tramblement; 
    
    @Column(name = "EQUILIBRE")
    private String equilibre; 
    
    @Column(name = "REFLEXE")
    private String reflexeOc; 
    
    @Column(name = "REFLEXE_TEND")
    private String reflexeTend; 
    
    @Column(name = "RAMBERG")
    private String remberg; 
    
    @Column(name = "EEG")
    private String eeg; 
    
    @Column(name = "SUCRE")
    private String sucre; 
    
    @Column(name = "SANG")
    private String sang; 
    
    @Column(name = "ALBUMINE")
    private String albumine; 
    
    @Column(name = "CRACHAT")
    private String crachat; 
    
    @Column(name = "SELLES")
    private String selles; 
    
    @Column(name = "APTE")
    private String apte; 
    
    @Column(name = "SURVILLER")
    private int surviller=1; 
    
    @Column(name = "ITT_GLOBALE")
    private int ittGlobale=0;  
    
    @Column(name = "PAUX_IPP")
    private int ippTaux=0; 
    
    @Column(name = "DYNAMOMETRE")
    private String dynamometre; 
    
    @Column(name = "TA")
    private String ta; 
    
    @Column(name = "REAMENAGEMENT")
    private String reamenagement; 
    
    @Column(name = "POSTE")
    private String poste; 
    
    @Column(name = "DUREE")
    private int duree=0; 
    
    @Column(name = "RXPULMONAIRE")
    private String rxPulmonaire;
    
    @Column(name = "DATE_RXPULMO")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateRxpulmonaire; 
   
    @Column(name = "DATE_AT")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date date_AT;
    
    @ManyToOne
    private DossierMedical dossierMedical; 
    
    @ManyToOne
    private Chantier chantier; 
    
    @Column(name = "PROCH_VIS")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date prochaineVisite;
    
    @Column(name = "DATECREATION")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateCreation;

    @Column(name = "MODIFIEPAR")
    private String modifiePar;

    @Column(name = "COMMENTAIRE")
    private String commentaire;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getDateVisite() {
        return dateVisite;
    }

    public void setDateVisite(Date dateVisite) {
        this.dateVisite = dateVisite;
    }

    public String getCreePar() {
        return creePar;
    }

    public void setCreePar(String creePar) {
        this.creePar = creePar;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public String getAuditionOG() {
        return auditionOG;
    }

    public void setAuditionOG(String auditionOG) {
        this.auditionOG = auditionOG;
    }

    public String getAuditionOD() {
        return auditionOD;
    }

    public void setAuditionOD(String auditionOD) {
        this.auditionOD = auditionOD;
    }

    public String getVisionOG() {
        return visionOG;
    }

    public void setVisionOG(String visionOG) {
        this.visionOG = visionOG;
    }

    public String getVisionOD() {
        return visionOD;
    }

    public void setVisionOD(String visionOD) {
        this.visionOD = visionOD;
    }

    public String getTaille() {
        return taille;
    }

    public void setTaille(String taille) {
        this.taille = taille;
    }

    public String getPoids() {
        return poids;
    }

    public void setPoids(String poids) {
        this.poids = poids;
    }

    public String getPerThor() {
        return perThor;
    }

    public void setPerThor(String perThor) {
        this.perThor = perThor;
    }

    public String getCarResp() {
        return carResp;
    }

    public void setCarResp(String carResp) {
        this.carResp = carResp;
    }

    public String getPeauPhanere() {
        return peauPhanere;
    }

    public void setPeauPhanere(String peauPhanere) {
        this.peauPhanere = peauPhanere;
    }

    public String getLocoMoteur() {
        return locoMoteur;
    }

    public void setLocoMoteur(String locoMoteur) {
        this.locoMoteur = locoMoteur;
    }

    public String getControle() {
        return controle;
    }

    public void setControle(String controle) {
        this.controle = controle;
    }

    public String getExamenRadio() {
        return examenRadio;
    }

    public void setExamenRadio(String examenRadio) {
        this.examenRadio = examenRadio;
    }

    public String getEcg() {
        return ecg;
    }

    public void setEcg(String ecg) {
        this.ecg = ecg;
    }

    public String getPauls() {
        return pauls;
    }

    public void setPauls(String pauls) {
        this.pauls = pauls;
    }

    public String getVaisseaux() {
        return vaisseaux;
    }

    public void setVaisseaux(String vaisseaux) {
        this.vaisseaux = vaisseaux;
    }

    public String getVarices() {
        return varices;
    }

    public void setVarices(String varices) {
        this.varices = varices;
    }

    public String getGanglions() {
        return ganglions;
    }

    public void setGanglions(String ganglions) {
        this.ganglions = ganglions;
    }

    public String getBouche() {
        return bouche;
    }

    public void setBouche(String bouche) {
        this.bouche = bouche;
    }

    public String getAbdomen() {
        return abdomen;
    }

    public void setAbdomen(String abdomen) {
        this.abdomen = abdomen;
    }

    public String getFoie() {
        return foie;
    }

    public void setFoie(String foie) {
        this.foie = foie;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getAmygdales() {
        return amygdales;
    }

    public void setAmygdales(String amygdales) {
        this.amygdales = amygdales;
    }

    public String getHernies() {
        return hernies;
    }

    public void setHernies(String hernies) {
        this.hernies = hernies;
    }

    public String getCicatrice() {
        return cicatrice;
    }

    public void setCicatrice(String cicatrice) {
        this.cicatrice = cicatrice;
    }

    public String getRegle() {
        return regle;
    }

    public void setRegle(String regle) {
        this.regle = regle;
    }

    public String getBlenno() {
        return blenno;
    }

    public void setBlenno(String blenno) {
        this.blenno = blenno;
    }

    public String getUrine() {
        return urine;
    }

    public void setUrine(String urine) {
        this.urine = urine;
    }

    public String getEndocrinien() {
        return endocrinien;
    }

    public void setEndocrinien(String endocrinien) {
        this.endocrinien = endocrinien;
    }

    public String getNervotisme() {
        return nervotisme;
    }

    public void setNervotisme(String nervotisme) {
        this.nervotisme = nervotisme;
    }

    public String getTramblement() {
        return tramblement;
    }

    public void setTramblement(String tramblement) {
        this.tramblement = tramblement;
    }

    public String getEquilibre() {
        return equilibre;
    }

    public void setEquilibre(String equilibre) {
        this.equilibre = equilibre;
    } 
    public String getRemberg() {
        return remberg;
    }

    public void setRemberg(String remberg) {
        this.remberg = remberg;
    }

    public String getEeg() {
        return eeg;
    }

    public void setEeg(String eeg) {
        this.eeg = eeg;
    } 

    public String getCrachat() {
        return crachat;
    }

    public void setCrachat(String crachat) {
        this.crachat = crachat;
    }

    public String getSelles() {
        return selles;
    }

    public void setSelles(String selles) {
        this.selles = selles;
    }

    public String getApte() {
        return apte;
    }

    public void setApte(String apte) {
        this.apte = apte;
    } 

    public int getSurviller() {
        return surviller;
    }

    public void setSurviller(int surviller) {
        this.surviller = surviller;
    }

    public Date getProchaineVisite() {
        return prochaineVisite;
    }

    public void setProchaineVisite(Date prochaineVisite) {
        this.prochaineVisite = prochaineVisite;
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

    public DossierMedical getDossierMedical() {
        return dossierMedical;
    }

    public void setDossierMedical(DossierMedical dossierMedical) {
        this.dossierMedical = dossierMedical;
    }

    public Visite() {
    }

    public String getDynamometre() {
        return dynamometre;
    }

    public void setDynamometre(String dynamometre) {
        this.dynamometre = dynamometre;
    }

    public String getTa() {
        return ta;
    }

    public void setTa(String ta) {
        this.ta = ta;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public String getSucre() {
        return sucre;
    }

    public void setSucre(String sucre) {
        this.sucre = sucre;
    }

    public String getSang() {
        return sang;
    }

    public void setSang(String sang) {
        this.sang = sang;
    }

    public String getAlbumine() {
        return albumine;
    }

    public void setAlbumine(String albumine) {
        this.albumine = albumine;
    }

    public String getReamenagement() {
        return reamenagement;
    }

    public void setReamenagement(String reamenagement) {
        this.reamenagement = reamenagement;
    }

    public String getPoste() {
        return poste;
    }

    public void setPoste(String poste) {
        this.poste = poste;
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public String getRxPulmonaire() {
        return rxPulmonaire;
    }

    public void setRxPulmonaire(String rxPulmonaire) {
        this.rxPulmonaire = rxPulmonaire;
    }

    public Date getDateRxpulmonaire() {
        return dateRxpulmonaire;
    }

    public void setDateRxpulmonaire(Date dateRxpulmonaire) {
        this.dateRxpulmonaire = dateRxpulmonaire;
    }

    public int getIttGlobale() {
        return ittGlobale;
    }

    public void setIttGlobale(int ittGlobale) {
        this.ittGlobale = ittGlobale;
    }

    public int getIppTaux() {
        return ippTaux;
    }

    public void setIppTaux(int ippTaux) {
        this.ippTaux = ippTaux;
    }

    public String getReflexeOc() {
        return reflexeOc;
    }

    public void setReflexeOc(String reflexeOc) {
        this.reflexeOc = reflexeOc;
    }

    public String getReflexeTend() {
        return reflexeTend;
    }

    public void setReflexeTend(String reflexeTend) {
        this.reflexeTend = reflexeTend;
    }

    public Date getDate_AT() {
        return date_AT;
    }

    public void setDate_AT(Date date_AT) {
        this.date_AT = date_AT;
    }

    public boolean isOptique() {
        return optique;
    }

    public void setOptique(boolean optique) {
        this.optique = optique;
    }

    public String getDureeOptique() {
        return dureeOptique;
    }

    public void setDureeOptique(String dureeOptique) {
        this.dureeOptique = dureeOptique;
    }

    public Chantier getChantier() {
        return chantier;
    }

    public void setChantier(Chantier chantier) {
        this.chantier = chantier;
    }

     

    
}
