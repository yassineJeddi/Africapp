package ma.bservices.beans;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import ma.bservices.tgcc.Entity.Citerne;
import ma.bservices.tgcc.Entity.Engin;
import ma.bservices.tgcc.Entity.LoyerChantier;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "PRJAP")
public class Chantier implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -8842763861226050388L;

    @Id
    @Column(name = "PRJAP_ID")
    //@GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    @Column(name = "AFFAIRE", columnDefinition = "char(8)")
    private String codeNovapaie;

    @Column(name = "LIB80", columnDefinition = "char(80)")
    private String code;

    @Column(name = "UP_REGION", columnDefinition = "char(8)")
    private String region;

    @Column(name = "DOS")
    private Integer dos;

    @Column(name = "HEUREENTREE", columnDefinition = "char(10)")
    private String heureEntree;

    @Column(name = "HEURESORTIE", columnDefinition = "char(10)")
    private String heureSortie;

    @Column(name = "NOMBREHEURES", columnDefinition = "numeric(2,0)")
    private Integer nombreHeures;

    @Column(name = "NOMBREMINUTES", columnDefinition = "numeric(2,0)")
    private Integer nombreMinutes;
    
    @Column(name = "ADRESSE")
    private String adresse;
    
    @Column(name = "EMAIL")
    private String email;

    @ManyToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JoinTable(name = "CHANTIER_SALARIE", joinColumns = {
    @JoinColumn(name = "CHANTIER_ID")}, inverseJoinColumns = {
    @JoinColumn(name = "SALARIE_ID")})
    private List<Salarie> salaries;

    @ManyToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "chantiers")
    private Set<Utilisateur> utilisateurs;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "chantiers", targetEntity = Lot.class)
    private List<Lot> lots;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "idChantier", cascade = CascadeType.ALL)
    private List<Zone> zones;

    @OneToMany(mappedBy = "prjapId", fetch = FetchType.LAZY)
    private List<Engin> enginList;

//    @OneToOne
//    private Organigrame organigram;
    

    @ManyToMany(mappedBy = "chantiers", fetch = FetchType.LAZY)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<LoyerChantier> loyerChantier;

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "affiniteChantier")
    private List<Chantier> affiniteChantierReverse;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "CHANTIER_AFFINITE", joinColumns = {
        @JoinColumn(name = "CHANTIER_ID")}, inverseJoinColumns = {
        @JoinColumn(name = "CHANTIER_AFFINITE_ID")})
    private List<Chantier> affiniteChantier;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Citerne> citernes;

    @Transient
    private String liste_Nom_Affinites;

    @Transient
    private Integer[] idChantiers;

    @Transient
    private Boolean display;

    @Transient
    private Boolean display_chantier_Principal;

    @Transient
    private String[] chantier_str;

    /**
     * la liste des zones d'un chéf équipe
     */
    @Transient
    private List<Zone> zoneOfChefEquipe = new LinkedList<>();

//    @Column(name = "DATEDebut")
//    @Temporal(TemporalType.DATE)
//    private Date DateDebu;
    @OneToMany(mappedBy = "id.chantier")
    private List<ChantierHierarchie> superieurs;

    public List<ChantierHierarchie> getSuperieurs() {
        return superieurs;
    }

    public void setSuperieurs(List<ChantierHierarchie> superieurs) {
        this.superieurs = superieurs;
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

//    public Organigrame getOrganigram() {
//        return organigram;
//    }
//
//    public void setOrganigram(Organigrame organigram) {
//        this.organigram = organigram;
//    }

    /**
     * @return the codeNovapaie
     */
    public String getCodeNovapaie() {
        return codeNovapaie;
    }

    /**
     * @param codeNovapaie the codeNovapaie to set
     */
    public void setCodeNovapaie(String codeNovapaie) {
        this.codeNovapaie = codeNovapaie;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

//    /**
//     * @return the ville
//     */
//    public String getVille() {
//        return ville;
//    }
//
//    /**
//     * @param ville the ville to set
//     */
//    public void setVille(String ville) {
//        this.ville = ville;
//    }
//    /**
//     * @return the adresse
//     */
//    public String getAdresse() {
//        return adresse;
//    }
//
//    /**
//     * @param adresse the adresse to set
//     */
//    public void setAdresse(String adresse) {
//        this.adresse = adresse;
//    }
    /**
     * @return the heureEntree
     */
    public String getHeureEntree() {
        return heureEntree;
    }

    /**
     * @param heureEntree the heureEntree to set
     */
    public void setHeureEntree(String heureEntree) {
        this.heureEntree = heureEntree;
    }

    /**
     * @return the heureSortie
     */
    public String getHeureSortie() {
        return heureSortie;
    }

    /**
     * @param heureSortie the heureSortie to set
     */
    public void setHeureSortie(String heureSortie) {
        this.heureSortie = heureSortie;
    }

    /**
     * @return the nombreHeures
     */
    public Integer getNombreHeures() {
        return nombreHeures;
    }

    /**
     * @param nombreHeures the nombreHeures to set
     */
    public void setNombreHeures(Integer nombreHeures) {
        this.nombreHeures = nombreHeures;
    }

    /**
     * @return the nombreMinutes
     */
    public Integer getNombreMinutes() {
        return nombreMinutes;
    }

    /**
     * @param nombreMinutes the nombreMinutes to set
     */
    public void setNombreMinutes(Integer nombreMinutes) {
        this.nombreMinutes = nombreMinutes;
    }

    /**
     * @return the salaries
     */
    public List<Salarie> getSalaries() {
        return salaries;
    }

    /**
     * @param salaries the salaries to set
     */
    public void setSalaries(List<Salarie> salaries) {
        this.salaries = salaries;
    }

    /**
     * @return the utilisateurs
     */
    public Set<Utilisateur> getUtilisateurs() {
        return utilisateurs;
    }

    /**
     * @param utilisateurs the utilisateurs to set
     */
    public void setUtilisateurs(Set<Utilisateur> utilisateurs) {
        this.utilisateurs = utilisateurs;
    }

    /**
     * @return the region
     */
    public String getRegion() {
        return region;
    }

    /**
     * @param region the region to set
     */
    public void setRegion(String region) {
        this.region = region;
    }

    /**
     * @return the dos
     */
    public Integer getDos() {
        return dos;
    }

    /**
     * @param dos the dos to set
     */
    public void setDos(Integer dos) {
        this.dos = dos;
    }

    public List<Lot> getLots() {
        return lots;
    }

    public void setLots(List<Lot> lots) {
        this.lots = lots;
    }

    public List<Zone> getZones() {
        return zones;
    }

    public void setZones(List<Zone> zones) {
        this.zones = zones;
    }

    public List<Engin> getEnginList() {
        return enginList;
    }

    public void setEnginList(List<Engin> enginList) {
        this.enginList = enginList;
    }

//    public List<ChantierEngin> getChantierEnginList() {
//        return chantierEnginList;
//    }
//
//    public void setChantierEnginList(List<ChantierEngin> chantierEnginList) {
//        this.chantierEnginList = chantierEnginList;
//    }
    public List<LoyerChantier> getLoyerChantier() {
        return loyerChantier;
    }

    public void setLoyerChantier(List<LoyerChantier> loyerChantier) {
        this.loyerChantier = loyerChantier;
    }

    public List<Citerne> getCiternes() {
        return citernes;
    }

    public void setCiternes(List<Citerne> citernes) {
        this.citernes = citernes;
    }

    public String getListe_Nom_Affinites() {
        return liste_Nom_Affinites;
    }

    public void setListe_Nom_Affinites(String liste_Nom_Affinites) {
        this.liste_Nom_Affinites = liste_Nom_Affinites;
    }

    public Integer[] getIdChantiers() {
        return idChantiers;
    }

    public void setIdChantiers(Integer[] idChantiers) {
        this.idChantiers = idChantiers;
    }

    public Boolean getDisplay() {
        return display;
    }

    public void setDisplay(Boolean display) {
        this.display = display;
    }

    public List<Chantier> getAffiniteChantierReverse() {
        return affiniteChantierReverse;
    }

    public void setAffiniteChantierReverse(List<Chantier> affiniteChantierReverse) {
        this.affiniteChantierReverse = affiniteChantierReverse;
    }

    public Boolean getDisplay_chantier_Principal() {
        return display_chantier_Principal;
    }

    public void setDisplay_chantier_Principal(Boolean display_chantier_Principal) {
        this.display_chantier_Principal = display_chantier_Principal;
    }

    public String[] getChantier_str() {
        return chantier_str;
    }

    public void setChantier_str(String[] chantier_str) {
        this.chantier_str = chantier_str;
    }

    public List<Chantier> getAffiniteChantier() {
        return affiniteChantier;
    }

    public void setAffiniteChantier(List<Chantier> affiniteChantier) {
        this.affiniteChantier = affiniteChantier;
    }

    public List<Zone> getZoneOfChefEquipe() {
        return zoneOfChefEquipe;
    }

    public void setZoneOfChefEquipe(List<Zone> zoneOfChefEquipe) {
        this.zoneOfChefEquipe = zoneOfChefEquipe;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Chantier other = (Chantier) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Chantier{" + "id=" + id + ", codeNovapaie=" + codeNovapaie + ", code=" + code + ", region=" + region + ", dos=" + dos + ", heureEntree=" + heureEntree + ", heureSortie=" + heureSortie + ", nombreHeures=" + nombreHeures + ", nombreMinutes=" + nombreMinutes + ", adresse=" + adresse + ", email=" + email + ", display=" + display + ", display_chantier_Principal=" + display_chantier_Principal + '}';
    }
    
    

}
