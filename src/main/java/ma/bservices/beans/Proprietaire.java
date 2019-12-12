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
public class Proprietaire implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    
    @Column(name = "NOMPROPRIETAIRE")
    private String nomproprietaire;
    
    @Column(name = "PRENOMPROPRIETAIRE")
    private String prenomproprietaire;
    
    @Column(name = "NUMPROPRIETAIRE")
    private String numproprietaire;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomproprietaire() {
        return nomproprietaire;
    }

    public void setNomproprietaire(String nomproprietaire) {
        this.nomproprietaire = nomproprietaire;
    }

    public String getPrenomproprietaire() {
        return prenomproprietaire;
    }

    public void setPrenomproprietaire(String prenomproprietaire) {
        this.prenomproprietaire = prenomproprietaire;
    }

    public String getNumproprietaire() {
        return numproprietaire;
    }

    public void setNumproprietaire(String numproprietaire) {
        this.numproprietaire = numproprietaire;
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
        if (!(object instanceof Proprietaire)) {
            return false;
        }
        Proprietaire other = (Proprietaire) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ma.bservices.beans.Proprietaire[ id=" + id + " ]";
    }
    
}
