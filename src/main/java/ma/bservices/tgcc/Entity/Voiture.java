/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.Entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import ma.bservices.beans.Chantier;
import ma.bservices.beans.Document;
import ma.bservices.beans.Salarie;


@Entity
@Table(name = "VOITURE")
public class Voiture  implements Serializable {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  
 
    @Column(name = "Marque")
    private String marque;

    @Column(name = "Model")
    private String model;
    
    @Column(name = "PUISSANCEFISCAL")
    private String puissance_fiscal;
    
    @Column(name = "TYPECARBURANT")
    private String type_carburant;
    
    @Column(name = "MATRICULEVOITUREINTERIEUR")
    private String matricule_voiture_nouveau;
    
    @Column(name = "MATRICULEVOITURE")
    private String matriculevoiture;
    
    @Column(name = "NUMEROCHASSIS")
    private String num_chassis;
    
    @Column(name = "NUMCARTEGRISE")
    private String numcartegrise;
    
    @Column(name = "NUMCONTRAT")
    private String numcontrat;
    
    @Column(name = "STATUT")
    private String statut;
    
    @Column(name = "DATE_STATUT")
    private Date dateStatut;
    
    @Column(name = "PROPRIETAIRE")
    private String proprietaire;
    
    @Column(name = "AFFECT")
    private Boolean affect;

    @Column(name = "ARCHIVER")
    private Boolean archiver;
    
    @Column(name = "COUTLOCATION")
    private Integer coutlocation;
    
    @Column(name = "DATERENDU")
    private Date dateRendu;

    @Column(name = "DATECHANGEMENTMATRICULE")
    private Date date_changement_matricule;
    
    @Column(name = "DATEACQUISITION")
    private Date dateacquisition;
    
    @Column(name = "DATEAFFECTATION")
    private Date dateaffectation;
    
    @Column(name = "DATEDEBUTCONTRAT")
    private Date datedebutcontrat;
    
    @Column(name = "DATEFINCONTRAT")
    private Date datefincontrat;
    
    @Column(name = "DATEPROCHAINETECH")
    private Date dateprochainetech;
    
    @Column(name = "MC_MAROC")
    private Date mc_maroc;
    
    @Column(name = "PREMIERMISEENCIRCULATION")
    private Date premier_mise_circulation;
    
    @Column(name = "DURECONTRATASSU")
    private String durecontratassu;
    
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "voiture", cascade = CascadeType.ALL)   
    private List<Document> documents;
    
    @ManyToOne
    private Salarie salarie;

    @ManyToOne
    private Chantier chantier;
    
    ////////////////////////////////////////////

    public Long getId() {
        return id;
    }

    public String getMarque() {
        return marque;
    }

    public String getModel() {
        return model;
    }

    public String getPuissance_fiscal() {
        return puissance_fiscal;
    }

    public String getType_carburant() {
        return type_carburant;
    }

    public String getMatricule_voiture_nouveau() {
        return matricule_voiture_nouveau;
    }

    public String getMatriculevoiture() {
        return matriculevoiture;
    }

    public String getNum_chassis() {
        return num_chassis;
    }

    public String getNumcartegrise() {
        return numcartegrise;
    }

    public String getNumcontrat() {
        return numcontrat;
    }

    public String getStatut() {
        return statut;
    }

    public Date getDateStatut() {
        return dateStatut;
    }

    public String getProprietaire() {
        return proprietaire;
    }

    public Boolean getAffect() {
        return affect;
    }

    public Boolean getArchiver() {
        return archiver;
    }

    public Integer getCoutlocation() {
        return coutlocation;
    }

    public Date getDateRendu() {
        return dateRendu;
    }

    public Date getDate_changement_matricule() {
        return date_changement_matricule;
    }

    public Date getDateacquisition() {
        return dateacquisition;
    }

    public Date getDateaffectation() {
        return dateaffectation;
    }

    public Date getDatedebutcontrat() {
        return datedebutcontrat;
    }

    public Date getDatefincontrat() {
        return datefincontrat;
    }

    public Date getDateprochainetech() {
        return dateprochainetech;
    }

    public Date getMc_maroc() {
        return mc_maroc;
    }

    public Date getPremier_mise_circulation() {
        return premier_mise_circulation;
    }

    public String getDurecontratassu() {
        return durecontratassu;
    }

    public List<Document> getDocuments() {
        return documents;
    }

    public Salarie getSalarie() {
        return salarie;
    }

    public Chantier getChantier() {
        return chantier;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setPuissance_fiscal(String puissance_fiscal) {
        this.puissance_fiscal = puissance_fiscal;
    }

    public void setType_carburant(String type_carburant) {
        this.type_carburant = type_carburant;
    }

    public void setMatricule_voiture_nouveau(String matricule_voiture_nouveau) {
        this.matricule_voiture_nouveau = matricule_voiture_nouveau;
    }

    public void setMatriculevoiture(String matriculevoiture) {
        this.matriculevoiture = matriculevoiture;
    }

    public void setNum_chassis(String num_chassis) {
        this.num_chassis = num_chassis;
    }

    public void setNumcartegrise(String numcartegrise) {
        this.numcartegrise = numcartegrise;
    }

    public void setNumcontrat(String numcontrat) {
        this.numcontrat = numcontrat;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public void setDateStatut(Date dateStatut) {
        this.dateStatut = dateStatut;
    }

    public void setProprietaire(String proprietaire) {
        this.proprietaire = proprietaire;
    }

    public void setAffect(Boolean affect) {
        this.affect = affect;
    }

    public void setArchiver(Boolean archiver) {
        this.archiver = archiver;
    }

    public void setCoutlocation(Integer coutlocation) {
        this.coutlocation = coutlocation;
    }

    public void setDateRendu(Date dateRendu) {
        this.dateRendu = dateRendu;
    }

    public void setDate_changement_matricule(Date date_changement_matricule) {
        this.date_changement_matricule = date_changement_matricule;
    }

    public void setDateacquisition(Date dateacquisition) {
        this.dateacquisition = dateacquisition;
    }

    public void setDateaffectation(Date dateaffectation) {
        this.dateaffectation = dateaffectation;
    }

    public void setDatedebutcontrat(Date datedebutcontrat) {
        this.datedebutcontrat = datedebutcontrat;
    }

    public void setDatefincontrat(Date datefincontrat) {
        this.datefincontrat = datefincontrat;
    }

    public void setDateprochainetech(Date dateprochainetech) {
        this.dateprochainetech = dateprochainetech;
    }

    public void setMc_maroc(Date mc_maroc) {
        this.mc_maroc = mc_maroc;
    }

    public void setPremier_mise_circulation(Date premier_mise_circulation) {
        this.premier_mise_circulation = premier_mise_circulation;
    }

    public void setDurecontratassu(String durecontratassu) {
        this.durecontratassu = durecontratassu;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }

    public void setSalarie(Salarie salarie) {
        this.salarie = salarie;
    }

    public void setChantier(Chantier chantier) {
        this.chantier = chantier;
    }

    public Voiture() {
    }

    @Override
    public String toString() {
        return "Voiture{" + "id=" + id + ", marque=" + marque + ", model=" + model + ", puissance_fiscal=" + puissance_fiscal + ", type_carburant=" + type_carburant + ", matricule_voiture_nouveau=" + matricule_voiture_nouveau + ", matriculevoiture=" + matriculevoiture + ", num_chassis=" + num_chassis + ", numcartegrise=" + numcartegrise + ", numcontrat=" + numcontrat + ", statut=" + statut + ", dateStatut=" + dateStatut + ", proprietaire=" + proprietaire + ", affect=" + affect + ", archiver=" + archiver + ", coutlocation=" + coutlocation + ", dateRendu=" + dateRendu + ", date_changement_matricule=" + date_changement_matricule + ", dateacquisition=" + dateacquisition + ", dateaffectation=" + dateaffectation + ", datedebutcontrat=" + datedebutcontrat + ", datefincontrat=" + datefincontrat + ", dateprochainetech=" + dateprochainetech + ", mc_maroc=" + mc_maroc + ", premier_mise_circulation=" + premier_mise_circulation + ", durecontratassu=" + durecontratassu + ", salarie=" + salarie + ", chantier=" + chantier + '}';
    }
    

    
}
