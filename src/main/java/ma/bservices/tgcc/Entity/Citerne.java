/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.Entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import ma.bservices.beans.Chantier;

/**
 *
 * @author a.wattah
 */
@Entity
@Table(name = "CITERNE")
public class Citerne implements Serializable {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "LIBELLE_CITERNE")
    private String libelle_citerne;

    @Column(name = "TYPE_CITERNE")
    private String type_citerne;

    @Column(name = "CAPACITE")
    private Integer capacite;

    @Column(name = "LOCALITE")
    private String localite;

    @Column(name = "VOLUME_ACTUEL")
    private Double volume_actuel;
    
    @Column(name = "VOLUME_ACTUEL_D")
    private Double volume_actuel_;

    @Column(name = "ARCHIVER")
    private Boolean archiver;

    @OneToOne
    private Chantier chantier_Principal;

    @JoinTable(name = "CITERNE_CHANTIER", joinColumns = {
        @JoinColumn(name = "ID_CITERNE", referencedColumnName = "ID")}, inverseJoinColumns = {
        @JoinColumn(name = "ID_CHANTIER", referencedColumnName = "PRJAP_ID")})
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Chantier> l_chantiers;

    /**
     * add consruct par default
     */
    public Citerne() {
    }

    public Citerne(Integer id, String libelle_citerne, String type_citerne, String capacite) {
        this.id = id;
        this.libelle_citerne = libelle_citerne;
        this.type_citerne = type_citerne;

    }

    /**
     * getter end setters
     *
     * @return
     */
    public Double getVolume_actuel() {
        return volume_actuel;
    }

    public void setVolume_actuel(Double volume_actuel) {
        this.volume_actuel = volume_actuel;
    }

    public Boolean getArchiver() {
        return archiver;
    }

    public Double getVolume_actuel_() {
        return volume_actuel_;
    }

    public void setVolume_actuel_(Double volume_actuel_) {
        this.volume_actuel_ = volume_actuel_;
    }
    
    

    public void setArchiver(Boolean archiver) {
        this.archiver = archiver;
    }

    public Chantier getChantier_Principal() {
        return chantier_Principal;
    }

    public void setChantier_Principal(Chantier chantier_Principal) {
        this.chantier_Principal = chantier_Principal;
    }

    public String getLocalite() {
        return localite;
    }

    public void setLocalite(String localite) {
        this.localite = localite;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLibelle_citerne() {
        return libelle_citerne;
    }

    public void setLibelle_citerne(String libelle_citerne) {
        this.libelle_citerne = libelle_citerne;
    }

    public String getType_citerne() {
        return type_citerne;
    }

    public void setType_citerne(String type_citerne) {
        this.type_citerne = type_citerne;
    }

    public Integer getCapacite() {
        return capacite;
    }

    public void setCapacite(Integer capacite) {
        this.capacite = capacite;
    }

    public List<Chantier> getL_chantiers() {

        return l_chantiers;
    }

    public void setL_chantiers(List<Chantier> l_chantiers) {
        this.l_chantiers = l_chantiers;
    }


    /**
     * end getters and setters
     */
    @Override
    public String toString() {
        return "Citerne{" + "id=" + id + ", libelle_citerne=" + libelle_citerne + ", type_citerne=" + type_citerne + ", capacite=" + capacite + ", localite=" + localite + ", volume_actuel=" + volume_actuel + ", volume_actuel_=" + volume_actuel_ + ", archiver=" + archiver + '}';
    }

    
    
    
}
