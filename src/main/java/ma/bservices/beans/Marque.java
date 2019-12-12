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
import javax.persistence.Table;

/**
 *
 * @author yassine.jeddi
 */

@Entity
@Table(name = "MARQUE")
public class Marque implements Serializable {
     
    
    private static final long serialVersionUID = 1L; 
    
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    
    
    @Column(name = "NOM")
    private String nom;
    
    @Column(name = "TYPE")
    private String type;
    
    @Column(name = "FAMILLE")
    private String famille;

    public Integer getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getType() {
        return type;
    }

    public String getFamille() {
        return famille;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setFamille(String famille) {
        this.famille = famille;
    }

    public Marque() {
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
        if (!(object instanceof Marque)) {
            return false;
        }
        Marque other = (Marque) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    @Override
    public String toString() {
        return "Marque{" + "id=" + id + ", nom=" + nom + ", type=" + type + ", famille=" + famille + '}';
    }
    
    
}
