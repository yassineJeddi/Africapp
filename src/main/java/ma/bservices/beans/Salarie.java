package ma.bservices.beans;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.CascadeType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import ma.bservices.tgcc.Entity.Organigrame;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "SALARIE")
public class Salarie implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -5686009246073688470L;
    public static final String ETAT_ACTIF = "Actif";

// Identifiants
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "MATRICULE")
    private String matricule;

    @Column(name = "MATRICULEDIVALTO")
    private String matriculeDivalto;

    @ManyToOne
    private Civilite civilite;

    @OneToOne
    private Organigrame organigrame;

    @Column(name = "NOM")
    private String nom;

    @Column(name = "NOMARABE")
    private String nomArabe;

    @Column(name = "PRENOM")
    private String prenom;

    @Column(name = "PRENOMARABE")
    private String prenomArabe;

    @Column(name = "CIN")
    private String cin;

    @Column(name = "DATENAISSANCE")
    @Temporal(TemporalType.DATE)
    private Date dateNaissance;

    @Column(name = "LIEUNAISSANCE")
    private String lieuNaissance;

    @Column(name = "CiviliteDiva")
    private String civiliteDiva;

    @Transient
    private String sup;

    @ManyToOne
    @JoinColumn(name = "NATIONALITEDIVALTO")
    private Nationalite nationalite;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "salarie")
    private List<Document> documents;
// Adresse	
    @Column(name = "ADRESSE")
    private String adresse;

    @Column(name = "ADRESSEARABE")
    private String adresseArabe;

    @Column(name = "VILLE")
    private String ville;

    @ManyToOne
    @JoinColumn(name = "PAYSDIVALTO")
    private Pays pays;

    @Column(name = "TELEPHONE")
    private String telephone;

    @Column(name = "GSM")
    private String gsm;

    @Column(name = "PHOTO")
    private String cheminPhoto;

    @Column(name = "EMAIL")
    private String email;

// Identifiants
    @ManyToOne
    private Fonction fonction;

    @ManyToOne
    private Type type;

    @Column(name = "CNSS")
    private String cnss;

    @Column(name = "RIB")
    private String rib;

    @Column(name = "NOMBANQUE")
    private String nomBanque;

    @Column(name = "NOMAGENCE")
    private String nomAgence;

    @ManyToOne
    private ModePaiement modePaiement;

// Autres	
    @ManyToOne
    private Pointure pointure;

    @ManyToOne
    private TailleHabillement tailleHabillement;

    @ManyToOne
    private CouleurGilet couleurGilet;

    @ManyToOne
    private CouleurCasque couleurCasque;

//
//    @OneToOne(fetch = FetchType.EAGER)
//    private DossierSalarie dossierSalarie;
    @ManyToOne
    private Etat etat;

    @ManyToOne
    private SituationFamiliale situationFamiliale;

    @Column(name = "NOMBREENFANTS")
    private Integer nombreEnfants;

    @Column(name = "CREEPAR")
    private String creePar;

    @Column(name = "DATECREATION")
    @Temporal(TemporalType.DATE)
    private Date dateCreation;

    @Column(name = "MODIFIEPAR")
    private String modifiePar;

    @Column(name = "DATEMODIFICATION")
    @Temporal(TemporalType.DATE)
    private Date dateModification;

    @ManyToOne
    private Salarie salarieSupp;
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "salaries")
    private List<AffectationSalarieSupp> lAffectationSalarieSupp;

//    @JoinTable(name = "Salarie_ZONE", joinColumns = {
//        @JoinColumn(name = "salaries_ID", referencedColumnName = "ID")}, inverseJoinColumns = {
//        @JoinColumn(name = "zones_ID_ZONE", referencedColumnName = "ID_ZONE")}
//    , uniqueConstraints =
//    @UniqueConstraint(name = "ID_SZONE",
//    columnNames = {"salaries_ID", "zones_ID_ZONE"}))
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Zone> zones = new LinkedList<>();

    @Transient
    private String[] zones_str;

    @ManyToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "salaries")
    private List<Chantier> chantiers;

    @OneToOne
    private Contrat contrat;
    /**
     * la liste des lots pointé a un salarié (ma.bservice.mb.pointageLotMb = >
     * methode selectedLot
     */
    @Transient
    private boolean[] check;
    /**
     * la liste des lots autoriser a pointé pour une fonction d'un salarié
     * ma.bservice.mb.pointageLotMb = > methode lotAutoriser
     */
    @Transient
    private boolean[] lotAutoriser;
    /**
     * recuperer les zone dans la quel un salarie pointé dans une journée
     */
    @Transient
    private List<String> idZone;
    @Transient
    private String zonePointeConsult;

    @OneToMany(mappedBy = "salarie")
    private List<PointageLot> pointageLots;

    @OneToMany(mappedBy = "id.salarie")
    private List<ChantierHierarchie> superieurs;

    @ManyToMany(mappedBy = "salaries", cascade = CascadeType.ALL)
    private List<FichePointageLot> fichePointageLots = new LinkedList<>();

    @Transient
    private Boolean hasSup;

    @Transient
    private String ChefEquipe;

    public String getSup() {
        return sup;
    }

    public void setSup(String sup) {
        this.sup = sup;
    }

    public String getChefEquipe() {
        return ChefEquipe;
    }

    public void setChefEquipe(String ChefEquipe) {
        this.ChefEquipe = ChefEquipe;
    }

    public List<ChantierHierarchie> getSuperieurs() {
        return superieurs;
    }

    public void setSuperieurs(List<ChantierHierarchie> superieurs) {
        this.superieurs = superieurs;
    }

    public Salarie(Integer id) {
        this.id = id;
    }

    public String getCiviliteDiva() {
        return civiliteDiva;
    }

    public void setCiviliteDiva(String civiliteDiva) {
        this.civiliteDiva = civiliteDiva;
    }

    public Salarie() {
    }

    public Salarie getSalarieSupp() {
        return salarieSupp;
    }

    public void setSalarieSupp(Salarie salarieSupp) {
        this.salarieSupp = salarieSupp;
    }

    public List<Chantier> getChantiers() {
        return chantiers;
    }

    public void setChantiers(List<Chantier> chantiers) {
        this.chantiers = chantiers;
    }

    public String[] getZones_str() {
        return zones_str;
    }

    public void setZones_str(String[] zones_str) {
        this.zones_str = zones_str;
    }

    public List<Zone> getZones() {
        return zones;
    }

    public void setZones(List<Zone> zones) {
        this.zones = zones;
    }

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
     * @return the matricule
     */
    public String getMatricule() {
        return matricule;
    }

    /**
     * @param matricule the matricule to set
     */
    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    /**
     * @return the matriculeDivalto
     */
    public String getMatriculeDivalto() {
        return matriculeDivalto;
    }

    /**
     * @param matriculeDivalto the matriculeDivalto to set
     */
    public void setMatriculeDivalto(String matriculeDivalto) {
        this.matriculeDivalto = matriculeDivalto;
    }

    /**
     * @return the civilite
     */
    public Civilite getCivilite() {
        return civilite;
    }

    /**
     * @param civilite the civilite to set
     */
    public void setCivilite(Civilite civilite) {
        this.civilite = civilite;
    }

    public String getCheminPhoto() {
        return cheminPhoto;
    }

    public Organigrame getOrganigrame() {
        return organigrame;
    }

    public void setOrganigrame(Organigrame organigrame) {
        this.organigrame = organigrame;
    }

    public void setCheminPhoto(String cheminPhoto) {
        this.cheminPhoto = cheminPhoto;
    }

    /**
     * @return the nom
     */
    public String getNom() {
        return nom;
    }

    /**
     * @param nom the nom to set
     */
    public void setNom(String nom) {
        this.nom = nom.toUpperCase();
    }

    /**
     * @return the nomArabe
     */
    public String getNomArabe() {
        return nomArabe;
    }

    /**
     * @param nomArabe the nomArabe to set
     */
    public void setNomArabe(String nomArabe) {
        this.nomArabe = nomArabe;
    }

    /**
     * @return the prenom
     */
    public String getPrenom() {
        return prenom;
    }

    /**
     * @param prenom the prenom to set
     */
    public void setPrenom(String prenom) {
        if (prenom != null) {
            this.prenom = (!"".equals(prenom)) ? prenom.substring(0, 1).toUpperCase() + prenom.substring(1) : "";
        } else {
            this.prenom = "";
        }
    }

    /**
     * @return the prenomArabe
     */
    public String getPrenomArabe() {
        return prenomArabe;
    }

    /**
     * @param prenomArabe the prenomArabe to set
     */
    public void setPrenomArabe(String prenomArabe) {
        this.prenomArabe = prenomArabe;
    }

    /**
     * @return the cin
     */
    public String getCin() {
        return cin;
    }

    /**
     * @param cin the cin to set
     */
    public void setCin(String cin) {
        this.cin = cin;
    }

    /**
     * @return the dateNaissance
     */
    public Date getDateNaissance() {
        return dateNaissance;
    }

    public List<AffectationSalarieSupp> getlAffectationSalarieSupp() {
        return lAffectationSalarieSupp;
    }

    public void setlAffectationSalarieSupp(List<AffectationSalarieSupp> lAffectationSalarieSupp) {
        this.lAffectationSalarieSupp = lAffectationSalarieSupp;
    }
    
    

    /**
     * @param dateNaissance the dateNaissance to set
     */
    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    /**
     * @return the lieuNaissance
     */
    public String getLieuNaissance() {
        return lieuNaissance;
    }

    /**
     * @param lieuNaissance the lieuNaissance to set
     */
    public void setLieuNaissance(String lieuNaissance) {
        this.lieuNaissance = lieuNaissance;
    }

    /**
     * @return the nationalite
     */
    public Nationalite getNationalite() {
        return nationalite;
    }

    /**
     * @param nationalite the nationalite to set
     */
    public void setNationalite(Nationalite nationalite) {
        this.nationalite = nationalite;
    }

    /**
     * @return the adresse
     */
    public String getAdresse() {
        return adresse;
    }

    /**
     * @param adresse the adresse to set
     */
    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    /**
     * @return the adresseArabe
     */
    public String getAdresseArabe() {
        return adresseArabe;
    }

    /**
     * @param adresseArabe the adresseArabe to set
     */
    public void setAdresseArabe(String adresseArabe) {
        this.adresseArabe = adresseArabe;
    }

    /**
     * @return the ville
     */
    public String getVille() {
        return ville;
    }

    /**
     * @param ville the ville to set
     */
    public void setVille(String ville) {
        this.ville = ville;
    }

    /**
     * @return the pays
     */
    public Pays getPays() {
        return pays;
    }

    /**
     * @param pays the pays to set
     */
    public void setPays(Pays pays) {
        this.pays = pays;
    }

    /**
     * @return the telephone
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * @param telephone the telephone to set
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /**
     * @return the gsm
     */
    public String getGsm() {
        return gsm;
    }

    /**
     * @param gsm the gsm to set
     */
    public void setGsm(String gsm) {
        this.gsm = gsm;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the fonction
     */
    public Fonction getFonction() {
        return fonction;
    }

    /**
     * @param fonction the fonction to set
     */
    public void setFonction(Fonction fonction) {
        this.fonction = fonction;
    }

    /**
     * @return the type
     */
    public Type getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(Type type) {
        this.type = type;
    }

    /**
     * @return the cnss
     */
    public String getCnss() {
        return cnss;
    }

    /**
     * @param cnss the cnss to set
     */
    public void setCnss(String cnss) {
        this.cnss = cnss;
    }

    /**
     * @return the rib
     */
    public String getRib() {
        return rib;
    }

    /**
     * @param rib the rib to set
     */
    public void setRib(String rib) {
        this.rib = rib;
    }

    /**
     * @return the nomBanque
     */
    public String getNomBanque() {
        return nomBanque;
    }

    /**
     * @param nomBanque the nomBanque to set
     */
    public void setNomBanque(String nomBanque) {
        this.nomBanque = nomBanque;
    }

    /**
     * @return the nomAgence
     */
    public String getNomAgence() {
        return nomAgence;
    }

    /**
     * @param nomAgence the nomAgence to set
     */
    public void setNomAgence(String nomAgence) {
        this.nomAgence = nomAgence;
    }

    /**
     * @return the modePaiement
     */
    public ModePaiement getModePaiement() {
        return modePaiement;
    }

    /**
     * @param modePaiement the modePaiement to set
     */
    public void setModePaiement(ModePaiement modePaiement) {
        this.modePaiement = modePaiement;
    }

    /**
     * @return the pointure
     */
    public Pointure getPointure() {
        return pointure;
    }

    /**
     * @param pointure the pointure to set
     */
    public void setPointure(Pointure pointure) {
        this.pointure = pointure;
    }

    /**
     * @return the tailleHabillement
     */
    public TailleHabillement getTailleHabillement() {
        return tailleHabillement;
    }

    /**
     * @param tailleHabillement the tailleHabillement to set
     */
    public void setTailleHabillement(TailleHabillement tailleHabillement) {
        this.tailleHabillement = tailleHabillement;
    }

    /**
     * @return the couleurGilet
     */
    public CouleurGilet getCouleurGilet() {
        return couleurGilet;
    }

    /**
     * @param couleurGilet the couleurGilet to set
     */
    public void setCouleurGilet(CouleurGilet couleurGilet) {
        this.couleurGilet = couleurGilet;
    }

    /**
     * @return the couleurCasque
     */
    public CouleurCasque getCouleurCasque() {
        return couleurCasque;
    }

    /**
     * @param couleurCasque the couleurCasque to set
     */
    public void setCouleurCasque(CouleurCasque couleurCasque) {
        this.couleurCasque = couleurCasque;
    }

    /**
     * @return the etat
     */
    public Etat getEtat() {
        return etat;
    }

    /**
     * @param etat the etat to set
     */
    public void setEtat(Etat etat) {
        this.etat = etat;
    }

    /**
     * @return the situationFamiliale
     */
    public SituationFamiliale getSituationFamiliale() {
        return situationFamiliale;
    }

    /**
     * @param situationFamiliale the situationFamiliale to set
     */
    public void setSituationFamiliale(SituationFamiliale situationFamiliale) {
        this.situationFamiliale = situationFamiliale;
    }

    /**
     * @return the nombreEnfants
     */
    public Integer getNombreEnfants() {
        return nombreEnfants;
    }

    /**
     * @param nombreEnfants the nombreEnfants to set
     */
    public void setNombreEnfants(Integer nombreEnfants) {
        this.nombreEnfants = nombreEnfants;
    }

    /**
     * @return the creePar
     */
    public String getCreePar() {
        return creePar;
    }

    /**
     * @param creePar the creePar to set
     */
    public void setCreePar(String creePar) {
        this.creePar = creePar;
    }

    /**
     * @return the dateCreation
     */
    public Date getDateCreation() {
        return dateCreation;
    }

    /**
     * @param dateCreation the dateCreation to set
     */
    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    /**
     * @return the modifiePar
     */
    public String getModifiePar() {
        return modifiePar;
    }

    /**
     * @param modifiePar the modifiePar to set
     */
    public void setModifiePar(String modifiePar) {
        this.modifiePar = modifiePar;
    }

    /**
     * @return the dateModification
     */
    public Date getDateModification() {
        return dateModification;
    }

    /**
     * @param dateModification the dateModification to set
     */
    public void setDateModification(Date dateModification) {
        this.dateModification = dateModification;
    }

    public boolean[] getCheck() {
        return check;
    }

    public void setCheck(boolean[] check) {
        this.check = check;
    }

    public List<PointageLot> getPointageLots() {
        return pointageLots;
    }

    public void setPointageLots(List<PointageLot> pointageLots) {
        this.pointageLots = pointageLots;
    }

    public List<String> getIdZone() {
        return idZone;
    }

    public void setIdZone(List<String> idZone) {
        this.idZone = idZone;
    }

    public Contrat getContrat() {
        return contrat;
    }

    public void setContrat(Contrat contrat) {
        this.contrat = contrat;
    }

    public List<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }

    public Boolean getHasSup() {
        return hasSup;
    }

    public void setHasSup(Boolean hasSup) {
        this.hasSup = hasSup;
    }

    public boolean[] getLotAutoriser() {
        return lotAutoriser;
    }

    public void setLotAutoriser(boolean[] lotAutoriser) {
        this.lotAutoriser = lotAutoriser;
    }

    public String getZonePointeConsult() {
        return zonePointeConsult;
    }

    public void setZonePointeConsult(String zonePointeConsult) {
        this.zonePointeConsult = zonePointeConsult;
    }

    public List<FichePointageLot> getFichePointageLots() {
        return fichePointageLots;
    }

    public void setFichePointageLots(List<FichePointageLot> fichePointageLots) {
        this.fichePointageLots = fichePointageLots;
    }

}
