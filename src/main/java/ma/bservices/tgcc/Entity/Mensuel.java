/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.Entity;

import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import ma.bservices.beans.Salarie;
import ma.bservices.beans.TypeDocument;
import org.hibernate.annotations.DiscriminatorOptions;
import org.hibernate.annotations.Where;

/**
 *
 * @author a.wattah
 */
@Entity
@DiscriminatorValue("Mensuel")
@DiscriminatorOptions(force = true)
public class Mensuel extends Salarie {

    public static final String ETAT_ACTIF = "actif";
    public static final String ETAT_INACTIF = "inatif";

    @OneToMany
    @JoinColumn(name = "mensuel", insertable = false, updatable = false)
    List<Element> elements;

    @OneToMany
    @JoinColumn(name = "mensuel", insertable = false, updatable = false)
    @Where(clause = "dtype='Ordinateur'")
    List<Ordinateur> ordinateur;

    @ManyToMany(mappedBy = "mensuels", fetch = FetchType.LAZY)
    private List<LoyerSalarie> loyerSalaries;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "mensuel", cascade = CascadeType.ALL)
    private List<Affectation> affectations;

    /**
     * true : multichantier
     * false : uni chantier
     */
    @Column(name = "TYPEAFFECTATION")
    private Boolean typeAffectation;

    @Column
    private Date dateDebut;

    @Column(name = "type_contrat")
    private String typeContrat;

    @Column(name = "TYPEAFFECTATIONDIVA")
    private String typeAffectationDiva;

    @Column(name = "ETABLISSEMENT")
    private String etablissement;

    @Column(name = "Statut")
  private String statut;
    
    
     @Column(name = "TYPE_FUNC")
  private String typeFonction;
    
    


    //constructor
    public Mensuel() {
        super();
    }

    public Mensuel(Integer id) {
        super(id);
    }

    public String getTypeContrat() {
        return typeContrat;
    }

    public void setTypeContrat(String typeContrat) {
        this.typeContrat = typeContrat;
    }

    //getters and setters
    public List<Element> getElements() {
        return elements;
    }

    public void setElements(List<Element> elements) {
        this.elements = elements;
    }

    public String getTypeAffectationDiva() {
        return typeAffectationDiva;
    }

    public String getTypeFonction() {
        return typeFonction;
    }

    public void setTypeFonction(String typeFonction) {
        this.typeFonction = typeFonction;
    }
    
    

    public void setTypeAffectationDiva(String typeAffectationDiva) {
        this.typeAffectationDiva = typeAffectationDiva;
    }

    public List<Ordinateur> getOrdinateur() {
        return ordinateur;
    }

    public void setOrdinateur(List<Ordinateur> ordinateur) {
        this.ordinateur = ordinateur;
    }

    public List<LoyerSalarie> getLoyerSalaries() {
        return loyerSalaries;
    }

  
    
    

    public void setLoyerSalaries(List<LoyerSalarie> loyerSalaries) {
        this.loyerSalaries = loyerSalaries;
    }

    public List<Affectation> getAffectations() {
        return affectations;
    }

    public void setAffectations(List<Affectation> affectations) {
        this.affectations = affectations;
    }

    public void merge_to_list(List<TypeDocument> list_1, List<TypeDocument> list_2) {

    }

    public Boolean getTypeAffectation() {
        return typeAffectation;
    }

    public void setTypeAffectation(Boolean typeAffectation) {
        this.typeAffectation = typeAffectation;
    }

    public String getEtablissement() {
        return etablissement;
    }

    public void setEtablissement(String etablissement) {
        this.etablissement = etablissement;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    /**
     * methode qui returne mensuel qui n'as pas docoument obligatoir
     *
     * @param type_docs
     * @return
     */
//    public Boolean have_documents(List<Typedocument> type_docs) {
//        
//       Hibernate.initialize(this.getDocuments());
//        
//        Boolean trouve = false;
//
//        List<Document> l_doc = new ArrayList<>();
//
//      
//        if ((this.getDocuments() != null) && (this.getDocuments().size() > 0)) {
//
//            for (int i = 0; i < this.getDocuments().size(); i++) {
//                if (type_docs.contains(this.getDocuments().get(i).getTypeDocument())) {
//                    l_doc.add(this.getDocuments().get(i));
//
//                }
//            }
//        }
//
//        if (l_doc.size() == type_docs.size()) {
//            trouve = true;
//        }
//
//        return trouve;
//    }
}
