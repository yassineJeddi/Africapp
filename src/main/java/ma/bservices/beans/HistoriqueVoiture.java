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
import ma.bservices.tgcc.Entity.Voiture;

/**
 *
 * @author yassine.jeddi
 */

@Entity
@Table(name = "HISTORIQUEVOITURE")
public class HistoriqueVoiture implements Serializable {
    
    
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  
 
    @Column(name = "DATE_HISTORIQUE")
    private Date datehistorique;
    
    
    @Column(name = "DATE_AFECT")
    private Date dateAfect;
            
    
    @Column(name = "DATE_DESAFECT")
    private Date dateDesafect;
            
    @ManyToOne
    private Voiture voiture;
    
    @ManyToOne
    private Salarie salarie;
            
    @ManyToOne
    private Chantier chantier;

    public Long getId() {
        return id;
    }

    public Date getDatehistorique() {
        return datehistorique;
    }

    public Voiture getVoiture() {
        return voiture;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDatehistorique(Date datehistorique) {
        this.datehistorique = datehistorique;
    }

    public void setVoiture(Voiture voiture) {
        this.voiture = voiture;
    }

    public HistoriqueVoiture() {
    }

    public Date getDateAfect() {
        return dateAfect;
    }

    public Date getDateDesafect() {
        return dateDesafect;
    }

    public Salarie getSalarie() {
        return salarie;
    }

    public Chantier getChantier() {
        return chantier;
    }

    public void setDateAfect(Date dateAfect) {
        this.dateAfect = dateAfect;
    }

    public void setDateDesafect(Date dateDesafect) {
        this.dateDesafect = dateDesafect;
    }

    public void setSalarie(Salarie salarie) {
        this.salarie = salarie;
    }

    public void setChantier(Chantier chantier) {
        this.chantier = chantier;
    }
    

    @Override
    public String toString() {
        return "HistoriqueVoiture{" + "id=" + id + ", datehistorique=" + datehistorique + '}';
    }
    
    
    
}
