/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.beans;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author yassine.jeddi
 */
@Entity
public class Designation implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(name = "CODE_DESIGNATION")
    private String codeDesignation;
    
    @Column(name = "NOM_DESIGNATION")
    private String nomDesignation;
    
    @Column(name = "TYPE_COMPTEUR")
    private String typeCompteur;
    
    @Column(name = "TYPE_POINTAGE")
    private String typePointage;
    
    @Column(name = "FAMILLE_ENGIN")
    private String familleEngin;
    
    @Column(name="MATRICULE_BLIG")
    Boolean matriculeOblig;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodeDesignation() {
        return codeDesignation;
    }


    public String getTypeCompteur() {
        return typeCompteur;
    }

    public void setCodeDesignation(String codeDesignation) {
        this.codeDesignation = codeDesignation;
    }

    public void setTypePointage(String typePointage) {
        this.typePointage = typePointage;
    }

    public String getTypePointage() {
        return typePointage;
    }


    public void setTypeCompteur(String typeCompteur) {
        this.typeCompteur = typeCompteur;
    }

    public void setNomDesignation(String nomDesignation) {
        this.nomDesignation = nomDesignation;
    }

    public String getNomDesignation() {
        return nomDesignation;
    }

    public String getFamilleEngin() {
        return familleEngin;
    }

    public void setFamilleEngin(String familleEngin) {
        this.familleEngin = familleEngin;
    }

    public Boolean getMatriculeOblig() {
        return matriculeOblig;
    }

    public void setMatriculeOblig(Boolean matriculeOblig) {
        this.matriculeOblig = matriculeOblig;
    }

    
    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }



    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Designation)) {
            return false;
        }
        Designation other = (Designation) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Designation{" + "id=" + id + ", codeDesignation=" + codeDesignation + ", nomDesignation=" + nomDesignation + ", typeCompteur=" + typeCompteur + ", typePointage=" + typePointage + ", familleEngin=" + familleEngin + ", matriculeOblig=" + matriculeOblig + '}';
    }


   

    public Designation() {
    }
    
    
}
