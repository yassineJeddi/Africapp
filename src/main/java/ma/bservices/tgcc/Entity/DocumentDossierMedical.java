/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.Entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author BARAKA
 */
@Entity
@Table(name = "DOCUMENT_DOSSIERMED")
public class DocumentDossierMedical implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "CHEMIN")
    private String chemin;

    @ManyToOne
    private DossierMedical dossierMedical;

    @Column(name = "TITRE")
    private String titre;

    @Column(name = "CREEPAR")
    private String creePar;
    
    @Column(name = "DATECREATION")
    private Date dateCreation = new Date(); 

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getChemin() {
        return chemin;
    }

    public void setChemin(String chemin) {
        this.chemin = chemin;
    }

    public DossierMedical getDossierMedical() {
        return dossierMedical;
    }

    public void setDossierMedical(DossierMedical dossierMedical) {
        this.dossierMedical = dossierMedical;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
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

    public DocumentDossierMedical() {
    }

    public DocumentDossierMedical(String chemin, DossierMedical dossierMedical, String titre, String creePar) {
        this.chemin = chemin;
        this.dossierMedical = dossierMedical;
        this.titre = titre;
        this.creePar = creePar;
    }
    
}
