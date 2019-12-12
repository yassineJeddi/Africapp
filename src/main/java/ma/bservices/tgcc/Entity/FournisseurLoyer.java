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
import javax.persistence.Table;

/**
 *
 * @author yassine
 */
@Entity
@Table(name = "FOU")
public class FournisseurLoyer implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "FOU_ID")
    private String fou_id;
    @Column(name = "TIERS")
    private String tiers;
    @Column(name = "NOM")
    private String nom;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFou_id() {
        return fou_id;
    }

    public void setFou_id(String fou_id) {
        this.fou_id = fou_id;
    }

    public String getTiers() {
        return tiers;
    }

    public void setTiers(String tiers) {
        this.tiers = tiers;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public String toString() {
        return "FournisseurLoyer{" + "id=" + id + ", fou_id=" + fou_id + ", tiers=" + tiers + ", nom=" + nom + '}';
    }
    
    
    
    
    
    
     
    
}
