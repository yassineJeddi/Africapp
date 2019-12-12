package ma.bservices.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "PPTEMP")
public class Fonction implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -1864157529411012769L;

    @Id
    @Column(name = "PPTEMP_ID")
    //@GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

//	@Column(name = "CODENOVAPAIE")
//	private String codeNovapaie;
    @Column(name = "LIB", columnDefinition = "char(40)")
    private String fonction;

//	@Column(name = "LIBARABE",columnDefinition="char(40)")
//	private String fonctionArabe;
    @Column(name = "DOS", columnDefinition = "numeric(3,0)")
    private Integer dos;

  //  @ManyToOne
  //  private Statut statut;

//	@ManyToOne
//	private ModelContrat modelContrat;
    @ManyToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JoinTable(name = "FONCTION_TYPEDOCUMENT", joinColumns = {
        @JoinColumn(name = "PPTEMP_ID")}, inverseJoinColumns = {
        @JoinColumn(name = "TYPEDOCUMENT_ID")})
    private List<TypeDocument> typesDocument;

    @Column(name = "EMPLOICOD", columnDefinition = "char(20)")
    private String codeDiva;

    @OneToMany(mappedBy = "fonction", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Where(clause = "DTYPE ='Salarie'")
    private List<Salarie> salaries;

    @OneToOne(mappedBy = "fonction", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private FonctionLocal fonctionLocal;

    @ManyToMany(mappedBy = "fonctions", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Lot> lots;

    @Column(name = "UP_FONCT_QUAINZ")
    private Integer typeFonction;

    public Fonction() {
        this.lots = new ArrayList<>();
        this.salaries = new ArrayList<>();
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

    public Integer getTypeFonction() {
        return typeFonction;
    }

    public void setTypeFonction(Integer typeFonction) {
        this.typeFonction = typeFonction;
    }

//	/**
//	 * @return the codeNovapaie
//	 */
//	public String getCodeNovapaie() {
//		return codeNovapaie;
//	}
//
//
//	/**
//	 * @param codeNovapaie the codeNovapaie to set
//	 */
//	public void setCodeNovapaie(String codeNovapaie) {
//		this.codeNovapaie = codeNovapaie;
//	}
    /**
     * @return the fonction
     */
    public String getFonction() {
        return fonction;
    }

    /**
     * @param fonction the fonction to set
     */
    public void setFonction(String fonction) {
        this.fonction = fonction;
    }

//    public Statut getStatut() {
//        return statut;
//    }

//	/**
//	 * @return the fonctionArabe
//	 */
//	public String getFonctionArabe() {
//		return fonctionArabe;
//	}
//
//
//	/**
//	 * @param fonctionArabe the fonctionArabe to set
//	 */
//	public void setFonctionArabe(String fonctionArabe) {
//		this.fonctionArabe = fonctionArabe;
//	}
//
//
//    public void setStatut(Statut statut) {
//        this.statut = statut;
//    }

//	public ModelContrat getModelContrat() {
//		return modelContrat;
//	}
//
//
//	public void setModelContrat(ModelContrat modelContrat) {
//		this.modelContrat = modelContrat;
//	}
    /**
     * @return the typesDocument
     */
    public List<TypeDocument> getTypesDocument() {
        return typesDocument;
    }

    /**
     * @param typesDocument the typesDocument to set
     */
    public void setTypesDocument(List<TypeDocument> typesDocument) {
        this.typesDocument = typesDocument;
    }

    /**
     * @return the codeDiva
     */
    public String getCodeDiva() {
        return codeDiva;
    }

    /**
     * @param codeDiva the codeDiva to set
     */
    public void setCodeDiva(String codeDiva) {
        this.codeDiva = codeDiva;
    }

    public Integer getDos() {
        return dos;
    }

    public void setDos(Integer dos) {
        this.dos = dos;
    }

    public List<Salarie> getSalaries() {
        return salaries;
    }

    public void setSalaries(List<Salarie> salaries) {
        this.salaries = salaries;
    }

    public FonctionLocal getFonctionLocal() {
        return fonctionLocal;
    }

    public void setFonctionLocal(FonctionLocal fonctionLocal) {
        this.fonctionLocal = fonctionLocal;
    }

    public List<Lot> getLots() {
        return lots;
    }

    public void setLots(List<Lot> lots) {
        this.lots = lots;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Fonction other = (Fonction) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public String toString() {
        return "Fonction{" + "id=" + id + ", fonction=" + fonction + ", dos=" + dos + ", codeDiva=" + codeDiva + ", fonctionLocal=" + fonctionLocal + ", typeFonction=" + typeFonction + '}';
    }
    

}
