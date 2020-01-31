package ma.bservices.beans;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name = "CONTRAT")
public class Contrat implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -5580003229760857633L;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "DATEEMBAUCHE")
    @Temporal(TemporalType.DATE)
    private Date dateEmbauche;

    @Column(name = "DATEFIN")
    @Temporal(TemporalType.DATE)
    private Date dateFin;

    @ManyToOne
    private Duree duree;

    @ManyToOne
    private Preavis preavis;

    @ManyToOne
    private PeriodeEssai periodeEssai;

    @ManyToOne
    private EtatContrat etatContrat;

    @ManyToOne
    private TypeContrat typeContrat;

    @ManyToOne
    private Fonction fonction;

    @OneToOne(fetch = FetchType.EAGER)
    private Salarie salarie;

    @OneToOne(fetch = FetchType.EAGER)
    private ModelContrat modelContrat;

    @Column(name = "TAUXHORAIRE")
    private Float tauxHoraire;

//	@Column(name = "SALAIRECHIFFRES")
//	private String salaireChiffres;
//	
//	@Column(name = "SALAIRELETTRES")
//	private String salaireLettres;
    @Column(name = "NODEREFNONLEGALISE")
    private String nodeRefNonLegalise;

    @Column(name = "NODEREFLEGALISE")
    private String nodeRefLegalise;

    @Transient
    private String chaineDateEmbauche;

    @Column(name = "REFCONTRATDIVA")
    private String refContratDiva;

    @ManyToOne
    private Motif motif;

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
     * @return the dateEmbauche
     */
    public Date getDateEmbauche() {
        //System.out.println("la data d'embauche = " + dateEmbauche);
        
        return dateEmbauche;
    }
    public String getDateEmbaucheString()
    {
    return dateEmbauche.toString();
    }

    /**
     * @param dateEmbauche the dateEmbauche to set
     */
    public void setDateEmbauche(Date dateEmbauche) {
        this.dateEmbauche = dateEmbauche;
    }

    /**
     * @return the dateFin
     */
    public Date getDateFin() {
        return dateFin;
    }

    /**
     * @param dateFin the dateFin to set
     */
    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    /**
     * @return the duree
     */
    public Duree getDuree() {
        return duree;
    }

    /**
     * @param duree the duree to set
     */
    public void setDuree(Duree duree) {
        this.duree = duree;
    }

    /**
     * @return the preavis
     */
    public Preavis getPreavis() {
        return preavis;
    }

    /**
     * @param preavis the preavis to set
     */
    public void setPreavis(Preavis preavis) {
        this.preavis = preavis;
    }

    /**
     * @return the periodeEssai
     */
    public PeriodeEssai getPeriodeEssai() {
        return periodeEssai;
    }

    /**
     * @param periodeEssai the periodeEssai to set
     */
    public void setPeriodeEssai(PeriodeEssai periodeEssai) {
        this.periodeEssai = periodeEssai;
    }

    /**
     * @return the etatContrat
     */
    public EtatContrat getEtatContrat() {
        return etatContrat;
    }

    /**
     * @param etatContrat the etatContrat to set
     */
    public void setEtatContrat(EtatContrat etatContrat) {
        this.etatContrat = etatContrat;
    }

    /**
     * @return the typeContrat
     */
    public TypeContrat getTypeContrat() {
        return typeContrat;
    }

    /**
     * @param typeContrat the typeContrat to set
     */
    public void setTypeContrat(TypeContrat typeContrat) {
        this.typeContrat = typeContrat;
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
     * @return the salarie
     */
    public Salarie getSalarie() {
        return salarie;
    }

    /**
     * @param salarie the salarie to set
     */
    public void setSalarie(Salarie salarie) {
        this.salarie = salarie;
    }

    /**
     * @return the chaineDateEmbauche
     */
    public String getChaineDateEmbauche() {
        return chaineDateEmbauche;
    }

    /**
     * @param chaineDateEmbauche the chaineDateEmbauche to set
     */
    public void setChaineDateEmbauche(String chaineDateEmbauche) {
        this.chaineDateEmbauche = chaineDateEmbauche;
    }

    public ModelContrat getModelContrat() {
        return modelContrat;
    }

    public void setModelContrat(ModelContrat modelContrat) {
        this.modelContrat = modelContrat;
    }

//	public String getSalaireChiffres() {
//		return salaireChiffres;
//	}
//
//	public void setSalaireChiffres(String salaireChiffres) {
//		this.salaireChiffres = salaireChiffres;
//	}
//
//	public String getSalaireLettres() {
//		return salaireLettres;
//	}
//
//	public void setSalaireLettres(String salaireLettres) {
//		this.salaireLettres = salaireLettres;
//	}
    /**
     * @return the tauxHoraire
     */
    public Float getTauxHoraire() {
        return tauxHoraire;
    }

    /**
     * @param tauxHoraire the tauxHoraire to set
     */
    public void setTauxHoraire(Float tauxHoraire) {
        this.tauxHoraire = tauxHoraire;
    }

    public String getNodeRefNonLegalise() {
        return nodeRefNonLegalise;
    }

    public void setNodeRefNonLegalise(String nodeRefNonLegalise) {
        this.nodeRefNonLegalise = nodeRefNonLegalise;
    }

    public String getNodeRefLegalise() {
        return nodeRefLegalise;
    }

    public void setNodeRefLegalise(String nodeRefLegalise) {
        this.nodeRefLegalise = nodeRefLegalise;
    }

    /**
     * @return the refContratDiva
     */
    public String getRefContratDiva() {
        return refContratDiva;
    }

    /**
     * @param refContratDiva the refContratDiva to set
     */
    public void setRefContratDiva(String refContratDiva) {
        this.refContratDiva = refContratDiva;
    }

    /**
     * @return the motif
     */
    public Motif getMotif() {
        return motif;
    }

    /**
     * @param motif the motif to set
     */
    public void setMotif(Motif motif) {
        this.motif = motif;
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

    @Override
    public String toString() {
        return "Contrat{" + "id=" + id + ", dateEmbauche=" + dateEmbauche + ", dateFin=" + dateFin + ", duree=" + duree + ", etatContrat=" + etatContrat.getEtatContrat() + ", typeContrat=" + typeContrat + ", tauxHoraire=" + tauxHoraire + ", nodeRefNonLegalise=" + nodeRefNonLegalise + ", nodeRefLegalise=" + nodeRefLegalise + ", chaineDateEmbauche=" + chaineDateEmbauche + ", refContratDiva=" + refContratDiva + ", motif=" + motif + ", creePar=" + creePar + ", dateCreation=" + dateCreation + ", modifiePar=" + modifiePar + ", dateModification=" + dateModification + '}';
    }
    
    

}
