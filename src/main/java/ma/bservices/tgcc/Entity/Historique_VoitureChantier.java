package ma.bservices.tgcc.Entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import ma.bservices.beans.Chantier;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author admin
 */
@Entity
@Table(name = "HISTORIQUE_VOITURE_CHANTIER")
@XmlRootElement
public class Historique_VoitureChantier implements Serializable {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Chantier chantier;

    @OneToOne
    private Voiture voitureChantier;

    @Temporal(value = TemporalType.DATE)
    private Date dateAffectation;

    @Temporal(value = TemporalType.DATE)
    private Date dateDesaffectation;

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

 

    public Voiture getVoitureChantier() {
        return voitureChantier;
    }

    public void setVoitureChantier(Voiture voitureChantier) {
        this.voitureChantier = voitureChantier;
    }


    public Date getDateAffectation() {
        return dateAffectation;
    }

    public void setDateAffectation(Date dateAffectation) {
        this.dateAffectation = dateAffectation;
    }

    public Date getDateDesaffectation() {
        return dateDesaffectation;
    }

    public void setDateDesaffectation(Date dateDesaffectation) {
        this.dateDesaffectation = dateDesaffectation;
    }
    
    
    
    
}
