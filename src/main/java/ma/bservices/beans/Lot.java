/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import ma.bservices.tgcc.Entity.AffectationStock;
import ma.bservices.tgcc.Entity.PointageEngin;
import ma.bservices.tgcc.Entity.ZoneArticleQ;

/**
 *
 * @author Mahdi
 */
@Entity
@Table
public class Lot implements Serializable {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "LIBELLE_LOT")
    private String libelle;
    
    @Column(name = "ABR_LOT")
    private String abreviation;

    @Size(max = 255)
    @Column(name = "cheminIcon")
    private String cheminIcon;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = Fonction.class)
    @JoinTable(name = "lot_fonction", joinColumns = {
        @JoinColumn(name = "LOT_ID", nullable = false, updatable = false)},
            inverseJoinColumns = {
                @JoinColumn(name = "PPTEMP_ID",
                        nullable = false, updatable = false)})
    private List<Fonction> fonctions;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = Chantier.class)
    @JoinTable(name = "lot_chantier",
            joinColumns = @JoinColumn(name = "LOT_ID", nullable = false, updatable = false),
            inverseJoinColumns = @JoinColumn(name = "PRJAP_ID", nullable = false, updatable = false))
    private List<Chantier> chantiers;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "lots")
    private List<PointageLot> pointageLots;

    @OneToMany(mappedBy = "idLot")
    private List<PointageEngin> pointageEnginList;
    
    
      @OneToMany(mappedBy = "zoneId")
    private List<AffectationStock> affectationsInLot;
      
       @OneToMany(mappedBy = "lotId")
    private List<ZoneArticleQ> affectationsEntries;

    public Lot() {
        this.chantiers = new ArrayList<>();
        this.fonctions = new ArrayList<>();
    }

    public String getCheminIcon() {
        return cheminIcon;
    }

    public void setCheminIcon(String cheminIcon) {
        this.cheminIcon = cheminIcon;
    }

    public List<ZoneArticleQ> getAffectationsEntries() {
        return affectationsEntries;
    }

    public void setAffectationsEntries(List<ZoneArticleQ> affectationsEntries) {
        this.affectationsEntries = affectationsEntries;
    }
    
    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public List<Fonction> getFonctions() {
        return fonctions;
    }

    public String getAbreviation() {
        return abreviation;
    }

    public void setAbreviation(String abreviation) {
        this.abreviation = abreviation;
    }
    
    

    public void setFonctions(List<Fonction> fonctions) {
        this.fonctions = fonctions;
    }

    public List<Chantier> getChantiers() {
        return chantiers;
    }

    public void setChantiers(List<Chantier> chantiers) {
        this.chantiers = chantiers;
    }

    public List<PointageLot> getPointageLots() {
        return pointageLots;
    }

    public void setPointageLots(List<PointageLot> pointageLots) {
        this.pointageLots = pointageLots;
    }

    public List<PointageEngin> getPointageEnginList() {
        return pointageEnginList;
    }

    public void setPointageEnginList(List<PointageEngin> pointageEnginList) {
        this.pointageEnginList = pointageEnginList;
    }

    public List<AffectationStock> getAffectationsInLot() {
        return affectationsInLot;
    }

    public void setAffectationsInLot(List<AffectationStock> affectationsInLot) {
        this.affectationsInLot = affectationsInLot;
    }
    
    

}
