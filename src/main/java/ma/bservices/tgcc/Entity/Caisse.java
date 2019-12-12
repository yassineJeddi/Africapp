/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.Entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import ma.bservices.beans.Chantier;
import ma.bservices.beans.Salarie;

/**
 *
 * @author yassine.jeddi
 */
@Entity
public class Caisse implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(name = "NOM")
    private String nom;
    
    @Column(name = "TYPE")
    private String type;
    
    @Column(name = "SOLDE")
    private Float solde;
    
    @Column(name = "COMMENT")
    private String comment;
    
    @Column(name = "ARCHIVE")
    private Boolean archive;
    
        
    @ManyToOne
    private Salarie salarie;

    @ManyToOne
    private Chantier chantier;

    public Boolean getArchive() {
        return archive;
    }

    public void setArchive(Boolean archive) {
        this.archive = archive;
    }
    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Salarie getSalarie() {
        return salarie;
    }

    public void setSalarie(Salarie salarie) {
        this.salarie = salarie;
    }

    public Chantier getChantier() {
        return chantier;
    }

    public void setChantier(Chantier chantier) {
        this.chantier = chantier;
    }

    public Float getSolde() {
        return solde;
    }

    public void setSolde(Float solde) {
        this.solde = solde;
    }

    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    public Caisse() {
    }

    @Override
    public String toString() {
        return "Caisse{" + "id=" + id + ", nom=" + nom + ", type=" + type + ", comment=" + comment + ", salarie=" + salarie + ", chantier=" + chantier + '}';
    }

    
}
