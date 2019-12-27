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

/**
 *
 * @author yassine
 */
@Entity
public class DocumentImprimeDetailAt implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
  
    
    @Column(name = "CHEMIN")
    private String chemin;

    @ManyToOne
    private DetailAT detailAT;

    @Column(name = "TITRE")
    private String titre;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "CREEPAR")
    private String creePar;
    
    @Column(name = "DATECREATION")
    private Date dateCreation;
    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChemin() {
        return chemin;
    }

    public void setChemin(String chemin) {
        this.chemin = chemin;
    }

    public DetailAT getDetailAT() {
        return detailAT;
    }

    public void setDetailAT(DetailAT detailAT) {
        this.detailAT = detailAT;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreePar() {
        return creePar;
    }

    public void setCreePar(String creePar) {
        this.creePar = creePar;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

   
    @Override
    public String toString() {
        return "DocumentDetailAt{" + "id=" + id + ", chemin=" + chemin + ", detailAT=" + detailAT + ", titre=" + titre + ", description=" + description + ", creePar=" + creePar + ", dateCreation=" + dateCreation +  '}';
    }

    

    
}
