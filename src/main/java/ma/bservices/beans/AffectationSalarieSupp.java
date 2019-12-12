/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.beans;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;

/**
 *
 * @author j.allali
 */
@Entity
@Table(name = "AFFECTATION_SALRIE_SUPP")
public class AffectationSalarieSupp implements Serializable {

    @Id
    @Column(name = "AFFECTATION_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateAffectatio;
    
    /**
     * quand ce paramètre est true => C'est la dernière affectation
     * quand il est à False => On a une plus récente affection et donc celle-ci est archivé
     */
    @Column
    private Boolean currentSupp;

    @ManyToOne
    private Salarie salaries;

    @ManyToOne
    private Salarie chefEquipe;

    @ManyToOne
    private Chantier chantier;

    @Transient
    private String chefEquipeString;

    public String getChefEquipeString() {
        chefEquipeString = (this.chefEquipe != null) ? this.chefEquipe.getNom() + " " + this.chefEquipe.getPrenom() : "";
        return chefEquipeString;
    }

    public void setChefEquipeString(String ChefEquipe) {
        this.chefEquipeString = ChefEquipe;
    }

    public Chantier getChantier() {
        return chantier;
    }

    public void setChantier(Chantier chantier) {
        this.chantier = chantier;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Salarie getSalaries() {
        return salaries;
    }

    public Salarie getChefEquipe() {
        return chefEquipe;
    }

    public void setChefEquipe(Salarie chefEquipe) {
        this.chefEquipe = chefEquipe;
    }

    public void setSalaries(Salarie salaries) {
        this.salaries = salaries;
    }

    public Date getDateAffectatio() {
        return dateAffectatio;
    }

    public void setDateAffectatio(Date dateAffectatio) {
        this.dateAffectatio = dateAffectatio;
    }

    public Boolean getCurrentSupp() {
        return currentSupp;
    }

    public void setCurrentSupp(Boolean currentSupp) {
        this.currentSupp = currentSupp;
    }

}
