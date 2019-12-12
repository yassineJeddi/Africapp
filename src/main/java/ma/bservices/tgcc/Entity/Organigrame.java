/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.Entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import ma.bservices.beans.Chantier;
import ma.bservices.beans.NiveauFonction;
import ma.bservices.beans.Salarie;

/**
 *
 * @author airaamane
 */
@Entity
@Table(name = "ORGANIGRAM")
public class Organigrame implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "isChef")
    private boolean isChef;

    @OneToOne(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private Chantier chantier;

    @OneToOne(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private NiveauFonction niveau;

    @OneToOne(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private Salarie salarie;

    private String superieur;

//   @OneToMany(mappedBy = "salariesNiveau", 
//            cascade = CascadeType.ALL,
//            fetch = FetchType.EAGER
//            )
//    private List<Salarie> salaries;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Chantier getChantier() {
        return chantier;
    }

    public void setChantier(Chantier chantier) {
        this.chantier = chantier;
    }

    public NiveauFonction getNiveau() {
        return niveau;
    }

    public void setNiveau(NiveauFonction niveau) {
        this.niveau = niveau;
    }

    public Salarie getSalarie() {
        return salarie;
    }

    public void setSalarie(Salarie salarie) {
        this.salarie = salarie;
    }

    public String getSuperieur() {
        return superieur;
    }

    public void setSuperieur(String superieur) {
        this.superieur = superieur;
    }

    public boolean isIsChef() {
        return isChef;
    }

    public void setIsChef(boolean isChef) {
        this.isChef = isChef;
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
        if (!(object instanceof Organigrame)) {
            return false;
        }
        Organigrame other = (Organigrame) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ma.bservices.tgcc.Entity.Organigrame[ id=" + id + " ]";
    }

}
