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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import ma.bservices.beans.Chantier;
import ma.bservices.beans.EtatDA;
import ma.bservices.beans.Utilisateur;

/**
 *
 * @author admin
 */
@Entity(name="DemandeEntete")
@Table(name = "DemandeEntete")
@XmlRootElement
public class DemandeEntete implements Serializable {
    
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "Num_Demande")
    private String numDemande;
    
    @ManyToOne
    @JoinColumn(name="id_demandeur", referencedColumnName="ID")
    private Utilisateur demandeur;
    
    @Column(name = "Date_Demande")
    @Temporal(TemporalType.DATE)
    private Date dateAjout;
    
    @Column(name = "Date_Livraison_Souhaite")
    @Temporal(TemporalType.DATE)
    private Date dateLivraisonSouhaitee;
                 
    @ManyToOne
    @JoinColumn(name="Id_chantier", referencedColumnName="PRJAP_ID")
    private Chantier chantier;

    @ManyToOne
    @JoinColumn(name="Id_atelier", referencedColumnName="PRJAP_ID")
    private Chantier atelier;
    
    
    @Column(name = "Comantaire")
    private String Comantaire;
    
    @ManyToOne
    @JoinColumn(name="Id_Respensable", referencedColumnName="ID")
    private Utilisateur rRespensable;
    
    @ManyToOne
    private EtatDA etatDA;

    @Transient
    private String chaineDateAjout;

    @Transient
    private String chaineDateLivraisonSouhaitee;

    public Long getId() {
        return id;
    }

    public String getNumDemande() {
        return numDemande;
    }

    public Utilisateur getDemandeur() {
        return demandeur;
    }

    public Date getDateAjout() {
        return dateAjout;
    }

    public Date getDateLivraisonSouhaitee() {
        return dateLivraisonSouhaitee;
    }

    public Chantier getChantier() {
        return chantier;
    }

    public Chantier getAtelier() {
        return atelier;
    }

    public String getComantaire() {
        return Comantaire;
    }

    public Utilisateur getrRespensable() {
        return rRespensable;
    }

    public EtatDA getEtatDA() {
        return etatDA;
    }

    public String getChaineDateAjout() {
        return chaineDateAjout;
    }

    public String getChaineDateLivraisonSouhaitee() {
        return chaineDateLivraisonSouhaitee;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNumDemande(String numDemande) {
        this.numDemande = numDemande;
    }

    public void setDemandeur(Utilisateur demandeur) {
        this.demandeur = demandeur;
    }

    public void setDateAjout(Date dateAjout) {
        this.dateAjout = dateAjout;
    }

    public void setDateLivraisonSouhaitee(Date dateLivraisonSouhaitee) {
        this.dateLivraisonSouhaitee = dateLivraisonSouhaitee;
    }

    public void setChantier(Chantier chantier) {
        this.chantier = chantier;
    }

    public void setAtelier(Chantier atelier) {
        this.atelier = atelier;
    }

    public void setComantaire(String Comantaire) {
        this.Comantaire = Comantaire;
    }

    public void setrRespensable(Utilisateur rRespensable) {
        this.rRespensable = rRespensable;
    }

    public void setEtatDA(EtatDA etatDA) {
        this.etatDA = etatDA;
    }

    public void setChaineDateAjout(String chaineDateAjout) {
        this.chaineDateAjout = chaineDateAjout;
    }

    public void setChaineDateLivraisonSouhaitee(String chaineDateLivraisonSouhaitee) {
        this.chaineDateLivraisonSouhaitee = chaineDateLivraisonSouhaitee;
    }
    
    

}
