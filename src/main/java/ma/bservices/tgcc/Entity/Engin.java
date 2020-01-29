/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.Entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import ma.bservices.beans.Chantier;
import ma.bservices.beans.Salarie;

/**
 *
 * @author zakaria.dem
 */
@Entity
@Table(name = "ENGIN")
@XmlRootElement
public class Engin implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_Engin")
    private Integer iDEngin;

    @Column(name = "TYPE_COMPTEUR")
    private String typeCompteur;

    @Column(name = "COMPTEURHoraire")
    private Float comteurHoraire;

    @Column(name = "COMPTEURKILO")
    private Float compteurKilometrique;

    @Column(name = "NBRHEUERS")
    private Float nbrHeures;

    @Column(name = "NBRKILOMTRAGE")
    private Float nbrKilometrage;

    @Column(name = "NBRHEUERSPOINTE")
    private Float nbrHeuresPointe;

    @Column(name = "NBRKILOMTRAGEPOINTE")
    private Float nbrKilometragePointe;

    @Column(name = "NUMCHASSIS")
    private String numchassis;

    @Column(name = "NUMSERIE")
    private String numSerie;

    @Column(name = "CODEDESIGNATION")
    private String codeDesignation;

    @Column(name = "NUMIMMATRICULATION")
    private String num_immatriculation;

    @Column(name = "ANNEEFABRICATION")
    private String anneeFabrication;

    @Column(name = "FOURNISSEUR")
    private String fournisseur;

    @Column(name = "chantier_lib")
    private String chantierLib;
    
    @Column(name = "typePointageDept")
    private String typePointageDept;

    @Column(name = "DATEAQUISITION")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateAquisition;

    @Column(name = "DATEMISEMARCHE")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateMiseMarche;

    @Column(name = "DATLASTPANNE")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateLastPanne;

    @JoinColumn(name = "PRJAP_ID", referencedColumnName = "PRJAP_ID")
    @ManyToOne
    private Chantier prjapId;

    @OneToMany(mappedBy = "iDEngin", fetch = FetchType.LAZY)
    private List<PointageEngin> pointageEnginList;

//    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "engin", optional = false)
//    private ChantierEngin chantierEngin;
    @Column(name = "Date_AFFECTATION")
    @Temporal(TemporalType.DATE)
    private Date dateAFFECTATION;

    @Column(name = "Date_CREATION")
    @Temporal(TemporalType.DATE)
    private Date dateCREATION;

    @Column(name = "Date_DER_VID")
    @Temporal(TemporalType.DATE)
    private Date dateDERVID;

    @Column(name = "Date_DER_VIS")
    @Temporal(TemporalType.DATE)
    private Date dateDERVIS;

    @Column(name = "Date_MISE_SERVICE")
    @Temporal(TemporalType.DATE)
    private Date dateMISESERVICE;

    @Column(name = "Date_MODIFICATION")
    @Temporal(TemporalType.DATE)
    private Date dateMODIFICATION;

    @Column(name = "ACTIF")
    private Short actif;

    @Column(name = "ANNEE_CONST")
    private String anneeConst;

    @Column(name = "LIEU_REPARATION_TYPE")
    private String lieuReparationType;

    @Column(name = "LIEU_REPARATION_CHANTIER_ID")
    private String lieuReparation_chantier_id;

    @Column(name = "ANNEE_MES")
    private String anneeMes;

    @Column(name = "CODE")
    private String code;

    @Column(name = "COMPTEUR")
    private String compteur;

    @Column(name = "CREE_PAR")
    private String creePar;

    @Column(name = "DESIGNATION")
    private String designation;

    @Column(name = "ETAT")
    private String etat;

    @Column(name = "ETATRANSFERT")
    private Boolean etatTransfert;

    @Column(name = "MARQUE")
    private String marque;

    @Column(name = "MODIFIER_PAR")
    private String modifierPar;

    @Column(name = "REF_ARTICLE")
    private String refArticle;

    @Column(name = "REFERENCE")
    private String reference;

    @Column(name = "Comm")
    private String comm;

//    @OneToMany(mappedBy = "iDEngin")
//    private List<PointageEngin> pointageEnginList;
    @Column(name = "TYPEENGIN")
    private String typeEngin;

    @Column(name = "FAMILLENGIN")
    private String familleEngin;

    @Column(name = "DATEDERNIEREVISITESECURITY")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateDerniereVisiteSecurity;

    @Column(name = "ARCHIVE")
    private Boolean archive;
    
    @Column(name = "REFORMER")
    private Boolean reforme;
    
    @Column(name = "NATURE")
    private String nature;
    
    @Column(name = "NOM_SST")
    private String nomSST;
    
    @Column(name = "NOM_LOCATAIRE")
    private String nomLocataire;
    
    
    
    
    @Column(name = "numSerieMoteur")
    private String numSerieMoteur;
    
    @Column(name = "numSerieboiteVitesse")
    private String numSerieboiteVitesse;
    
    @Column(name = "numSeriePont")
    private String numSeriePont;
    
    @Column(name = "marqueMoteur")
    private String marqueMoteur;
    
    @Column(name = "marqueboiteVitesse")
    private String marqueboiteVitesse;
    
    @Column(name = "marqueSeriePont")
    private String marqueSeriePont;
    
    @Column(name = "typeMoteur")
    private String typeMoteur;
    
    @Column(name = "typeboiteVitesse")
    private String typeboiteVitesse;
    
    @Column(name = "typeSeriePont")
    private String typeSeriePont;
    
    @Column(name = "qteReservoir")
    private Integer qteReservoir;
    
    @Column(name = "consoMoyReel")
    private String consoMoyReel;
    
    @Column(name = "consoMinConstructeur")
    private String consoMinConstructeur;
    
    @Column(name = "consoMaxConstructeur")
    private String consoMaxConstructeur;
    
    @Column(name = "numImmobilisationCompta")
    private String numImmobilisationCompta;
    
    @Column(name = "numContratLising")
    private String numContratLising;
    
    @Column(name = "coutLocation")
    private String coutLocation;
    
    @Column(name = "uniteLocation")
    private String uniteLocation;
     
    
    
    
    
    
    

    @OneToOne
    private Panne hist_panne_id;
    
    @ManyToOne
    private Salarie conducteur;

    //GETTERS & SETTERS
    
    public Float getNbrHeuresPointe() {
        return nbrHeuresPointe;
    }

    public void setNbrHeuresPointe(Float nbrHeuresPointe) {
        this.nbrHeuresPointe = nbrHeuresPointe;
    }

    public Float getNbrKilometragePointe() {
        return nbrKilometragePointe;
    }

    public void setNbrKilometragePointe(Float nbrKilometragePointe) {
        this.nbrKilometragePointe = nbrKilometragePointe;
    }

    public Date getDateMiseMarche() {
        return dateMiseMarche;
    }

    public String getChantierLib() {
        return chantierLib;
    }

    public void setChantierLib(String chantierLib) {
        this.chantierLib = chantierLib;
    }

    public Date getDateAquisition() {
        return dateAquisition;
    }

    public void setDateAquisition(Date dateAquisition) {
        this.dateAquisition = dateAquisition;
    }

    public Boolean getEtatTransfert() {
        return etatTransfert;
    }

    public void setEtatTransfert(Boolean etatTransfert) {
        this.etatTransfert = etatTransfert;
    }

    public String getTypeEngin() {
        return typeEngin;
    }

    public String getNumSerie() {
        return numSerie;
    }

    public void setNumSerie(String numSerie) {
        this.numSerie = numSerie;
    }

    public String getCodeDesignation() {
        return codeDesignation;
    }

    public void setCodeDesignation(String codeDesignation) {
        this.codeDesignation = codeDesignation;
    }

    public String getNum_immatriculation() {
        return num_immatriculation;
    }

    public void setNum_immatriculation(String num_immatriculation) {
        this.num_immatriculation = num_immatriculation;
    }

    public String getAnneeFabrication() {
        return anneeFabrication;
    }

    public void setDateMiseMarche(Date dateMiseMarche) {
        this.dateMiseMarche = dateMiseMarche;
    }

    public void setAnneeFabrication(String anneeFabrication) {
        this.anneeFabrication = anneeFabrication;
    }

    public String getFournisseur() {
        return fournisseur;
    }

    public void setFournisseur(String fournisseur) {
        this.fournisseur = fournisseur;
    }

    public Integer getiDEngin() {
        return iDEngin;
    }

    public void setiDEngin(Integer iDEngin) {
        this.iDEngin = iDEngin;
    }

    public void setTypeEngin(String typeEngin) {
        this.typeEngin = typeEngin;
    }

    public String getFamilleEngin() {
        return familleEngin;
    }

    public void setFamilleEngin(String familleEngin) {
        this.familleEngin = familleEngin;
    }

    public Boolean getArchive() {
        return archive;
    }

    public void setArchive(Boolean archive) {
        this.archive = archive;
    }

    public Boolean getReforme() {
        return reforme;
    }

    public void setReforme(Boolean reforme) {
        this.reforme = reforme;
    }
    
    public Engin() {
    }

    public Engin(Integer iDEngin) {
        this.iDEngin = iDEngin;
    }

    public Integer getIDEngin() {
        return iDEngin;
    }

    public void setIDEngin(Integer iDEngin) {
        this.iDEngin = iDEngin;
    }

    public Date getDateAFFECTATION() {
        return dateAFFECTATION;
    }

    public void setDateAFFECTATION(Date dateAFFECTATION) {
        this.dateAFFECTATION = dateAFFECTATION;
    }

    public Date getDateCREATION() {
        return dateCREATION;
    }

    public void setDateCREATION(Date dateCREATION) {
        this.dateCREATION = dateCREATION;
    }

    public String getLieuReparationType() {
        return lieuReparationType;
    }

    public void setLieuReparationType(String lieuReparationType) {
        this.lieuReparationType = lieuReparationType;
    }

    public String getLieuReparation_chantier_id() {
        return lieuReparation_chantier_id;
    }

    public void setLieuReparation_chantier_id(String lieuReparation_chantier_id) {
        this.lieuReparation_chantier_id = lieuReparation_chantier_id;
    }

    public String getComm() {
        return comm;
    }

    public void setComm(String comm) {
        this.comm = comm;
    }

    public Date getDateDERVID() {
        return dateDERVID;
    }

    public void setDateDERVID(Date dateDERVID) {
        this.dateDERVID = dateDERVID;
    }

    public Panne getHist_panne_id() {
        return hist_panne_id;
    }

    public void setHist_panne_id(Panne hist_panne_id) {
        this.hist_panne_id = hist_panne_id;
    }

    public Date getDateDERVIS() {
        return dateDERVIS;
    }

    public void setDateDERVIS(Date dateDERVIS) {
        this.dateDERVIS = dateDERVIS;
    }

    public Date getDateMISESERVICE() {
        return dateMISESERVICE;
    }

    public void setDateMISESERVICE(Date dateMISESERVICE) {
        this.dateMISESERVICE = dateMISESERVICE;
    }

    public Date getDateMODIFICATION() {
        return dateMODIFICATION;
    }

    public void setDateMODIFICATION(Date dateMODIFICATION) {
        this.dateMODIFICATION = dateMODIFICATION;
    }

    public Short getActif() {
        return actif;
    }

    public void setActif(Short actif) {
        this.actif = actif;
    }

    public String getAnneeConst() {
        return anneeConst;
    }

    public void setAnneeConst(String anneeConst) {
        this.anneeConst = anneeConst;
    }

    public String getAnneeMes() {
        return anneeMes;
    }

    public void setAnneeMes(String anneeMes) {
        this.anneeMes = anneeMes;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCompteur() {
        return compteur;
    }

    public void setCompteur(String compteur) {
        this.compteur = compteur;
    }

    public String getCreePar() {
        return creePar;
    }

    public void setCreePar(String creePar) {
        this.creePar = creePar;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public String getModifierPar() {
        return modifierPar;
    }

    public void setModifierPar(String modifierPar) {
        this.modifierPar = modifierPar;
    }

    public String getRefArticle() {
        return refArticle;
    }

    public void setRefArticle(String refArticle) {
        this.refArticle = refArticle;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

//    @XmlTransient
//    public List<PointageEngin> getPointageEnginList() {
//        return pointageEnginList;
//    }
//
//    public void setPointageEnginList(List<PointageEngin> pointageEnginList) {
//        this.pointageEnginList = pointageEnginList;
//    }
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDEngin != null ? iDEngin.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Engin)) {
            return false;
        }
        Engin other = (Engin) object;
        if ((this.iDEngin == null && other.iDEngin != null) || (this.iDEngin != null && !this.iDEngin.equals(other.iDEngin))) {
            return false;
        }
        return true;
    }

   

     

    public String getTypeCompteur() {
        return typeCompteur;
    }

    public void setTypeCompteur(String typeCompteur) {
        this.typeCompteur = typeCompteur;
    }

    public String getNumchassis() {
        return numchassis;
    }

    public void setNumchassis(String numchassis) {
        this.numchassis = numchassis;
    }

    public Chantier getPrjapId() {
        return prjapId;
    }

    public void setPrjapId(Chantier prjapId) {
        this.prjapId = prjapId;
    }

    @XmlTransient
    public List<PointageEngin> getPointageEnginList() {
        return pointageEnginList;
    }

    public void setPointageEnginList(List<PointageEngin> pointageEnginList) {
        this.pointageEnginList = pointageEnginList;
    }

    public Float getComteurHoraire() {
        return comteurHoraire;
    }

    public void setComteurHoraire(Float comteurHoraire) {
        this.comteurHoraire = comteurHoraire;
    }

    public Float getCompteurKilometrique() {
        return compteurKilometrique;
    }

    public void setCompteurKilometrique(Float compteurKilometrique) {
        this.compteurKilometrique = compteurKilometrique;
    }

//    public ChantierEngin getChantierEngin() {
//        return chantierEngin;
//    }
//
//    public void setChantierEngin(ChantierEngin chantierEngin) {
//        this.chantierEngin = chantierEngin;
//    }
    public Engin(Chantier prjapId, String code, String etat, String marque) {
        this.prjapId = prjapId;
        this.code = code;
        this.etat = etat;
        this.marque = marque;
    }

    public Engin(Chantier prjapId, String code, String designation, String etat, String marque) {
        this.prjapId = prjapId;
        this.code = code;
        this.designation = designation;
        this.etat = etat;
        this.marque = marque;
    }

    public Engin(String etat) {
        this.etat = etat;
    }

    public Date getDateDerniereVisiteSecurity() {
        return dateDerniereVisiteSecurity;
    }

    public void setDateDerniereVisiteSecurity(Date dateDerniereVisiteSecurity) {
        this.dateDerniereVisiteSecurity = dateDerniereVisiteSecurity;
    }

    public Float getNbrHeures() {
        return nbrHeures;
    }

    public void setNbrHeures(Float nbrHeures) {
        this.nbrHeures = nbrHeures;
    }

    public Float getNbrKilometrage() {
        return nbrKilometrage;
    }

    public void setNbrKilometrage(Float nbrKilometrage) {
        this.nbrKilometrage = nbrKilometrage;
    }

    public void setConducteur(Salarie conducteur) {
        this.conducteur = conducteur;
    }

    public Salarie getConducteur() {
        return conducteur;
    }

    public Date getDateLastPanne() {
        return dateLastPanne;
    }

    public void setDateLastPanne(Date dateLastPanne) {
        this.dateLastPanne = dateLastPanne;
    }

    public String getTypePointageDept() {
        return typePointageDept;
    }

    public void setTypePointageDept(String typePointageDept) {
        this.typePointageDept = typePointageDept;
    }

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    public String getNomSST() {
        return nomSST;
    }

    public void setNomSST(String nomSST) {
        this.nomSST = nomSST;
    }

    public String getNomLocataire() {
        return nomLocataire;
    }

    public void setNomLocataire(String nomLocataire) {
        this.nomLocataire = nomLocataire;
    }

    public String getNumSerieMoteur() {
        return numSerieMoteur;
    }

    public void setNumSerieMoteur(String numSerieMoteur) {
        this.numSerieMoteur = numSerieMoteur;
    }

    public String getNumSerieboiteVitesse() {
        return numSerieboiteVitesse;
    }

    public void setNumSerieboiteVitesse(String numSerieboiteVitesse) {
        this.numSerieboiteVitesse = numSerieboiteVitesse;
    }

    public String getNumSeriePont() {
        return numSeriePont;
    }

    public void setNumSeriePont(String numSeriePont) {
        this.numSeriePont = numSeriePont;
    }

    public String getMarqueMoteur() {
        return marqueMoteur;
    }

    public void setMarqueMoteur(String marqueMoteur) {
        this.marqueMoteur = marqueMoteur;
    }

    public String getMarqueboiteVitesse() {
        return marqueboiteVitesse;
    }

    public void setMarqueboiteVitesse(String marqueboiteVitesse) {
        this.marqueboiteVitesse = marqueboiteVitesse;
    }

    public String getMarqueSeriePont() {
        return marqueSeriePont;
    }

    public void setMarqueSeriePont(String marqueSeriePont) {
        this.marqueSeriePont = marqueSeriePont;
    }

    public String getTypeMoteur() {
        return typeMoteur;
    }

    public void setTypeMoteur(String typeMoteur) {
        this.typeMoteur = typeMoteur;
    }

    public String getTypeboiteVitesse() {
        return typeboiteVitesse;
    }

    public void setTypeboiteVitesse(String typeboiteVitesse) {
        this.typeboiteVitesse = typeboiteVitesse;
    }

    public String getTypeSeriePont() {
        return typeSeriePont;
    }

    public void setTypeSeriePont(String typeSeriePont) {
        this.typeSeriePont = typeSeriePont;
    }

    public Integer getQteReservoir() {
        return qteReservoir;
    }

    public void setQteReservoir(Integer qteReservoir) {
        this.qteReservoir = qteReservoir;
    }

    public String getConsoMoyReel() {
        return consoMoyReel;
    }

    public void setConsoMoyReel(String consoMoyReel) {
        this.consoMoyReel = consoMoyReel;
    }

    public String getConsoMinConstructeur() {
        return consoMinConstructeur;
    }

    public void setConsoMinConstructeur(String consoMinConstructeur) {
        this.consoMinConstructeur = consoMinConstructeur;
    }

    public String getConsoMaxConstructeur() {
        return consoMaxConstructeur;
    }

    public void setConsoMaxConstructeur(String consoMaxConstructeur) {
        this.consoMaxConstructeur = consoMaxConstructeur;
    }

    public String getNumImmobilisationCompta() {
        return numImmobilisationCompta;
    }

    public void setNumImmobilisationCompta(String numImmobilisationCompta) {
        this.numImmobilisationCompta = numImmobilisationCompta;
    }

    public String getNumContratLising() {
        return numContratLising;
    }

    public void setNumContratLising(String numContratLising) {
        this.numContratLising = numContratLising;
    }

    public String getCoutLocation() {
        return coutLocation;
    }

    public void setCoutLocation(String coutLocation) {
        this.coutLocation = coutLocation;
    }

    public String getUniteLocation() {
        return uniteLocation;
    }

    public void setUniteLocation(String uniteLocation) {
        this.uniteLocation = uniteLocation;
    }

    @Override
    public String toString() {
        return "Engin{" + "iDEngin=" + iDEngin + ", typeCompteur=" + typeCompteur + ", comteurHoraire=" + comteurHoraire + ", compteurKilometrique=" + compteurKilometrique + ", nbrHeures=" + nbrHeures + ", nbrKilometrage=" + nbrKilometrage + ", nbrHeuresPointe=" + nbrHeuresPointe + ", nbrKilometragePointe=" + nbrKilometragePointe + ", numchassis=" + numchassis + ", numSerie=" + numSerie + ", codeDesignation=" + codeDesignation + ", num_immatriculation=" + num_immatriculation + ", anneeFabrication=" + anneeFabrication + ", fournisseur=" + fournisseur + ", chantierLib=" + chantierLib + ", typePointageDept=" + typePointageDept + ", dateAquisition=" + dateAquisition + ", dateMiseMarche=" + dateMiseMarche + ", dateLastPanne=" + dateLastPanne + ", dateAFFECTATION=" + dateAFFECTATION + ", dateCREATION=" + dateCREATION + ", dateDERVID=" + dateDERVID + ", dateDERVIS=" + dateDERVIS + ", dateMISESERVICE=" + dateMISESERVICE + ", dateMODIFICATION=" + dateMODIFICATION + ", actif=" + actif + ", anneeConst=" + anneeConst + ", lieuReparationType=" + lieuReparationType + ", lieuReparation_chantier_id=" + lieuReparation_chantier_id + ", anneeMes=" + anneeMes + ", code=" + code + ", compteur=" + compteur + ", creePar=" + creePar + ", designation=" + designation + ", etat=" + etat + ", etatTransfert=" + etatTransfert + ", marque=" + marque + ", modifierPar=" + modifierPar + ", refArticle=" + refArticle + ", reference=" + reference + ", comm=" + comm + ", typeEngin=" + typeEngin + ", familleEngin=" + familleEngin + ", dateDerniereVisiteSecurity=" + dateDerniereVisiteSecurity + ", archive=" + archive + ", reforme=" + reforme + ", nature=" + nature + ", nomSST=" + nomSST + ", nomLocataire=" + nomLocataire + ", numSerieMoteur=" + numSerieMoteur + ", numSerieboiteVitesse=" + numSerieboiteVitesse + ", numSeriePont=" + numSeriePont + ", marqueMoteur=" + marqueMoteur + ", marqueboiteVitesse=" + marqueboiteVitesse + ", marqueSeriePont=" + marqueSeriePont + ", typeMoteur=" + typeMoteur + ", typeboiteVitesse=" + typeboiteVitesse + ", typeSeriePont=" + typeSeriePont + ", qteReservoir=" + qteReservoir + ", consoMoyReel=" + consoMoyReel + ", consoMinConstructeur=" + consoMinConstructeur + ", consoMaxConstructeur=" + consoMaxConstructeur + ", numImmobilisationCompta=" + numImmobilisationCompta + ", numContratLising=" + numContratLising + ", coutLocation=" + coutLocation + ", uniteLocation=" + uniteLocation + ", hist_panne_id=" + hist_panne_id + '}';
    }

    
   
    
     
    
    
}
