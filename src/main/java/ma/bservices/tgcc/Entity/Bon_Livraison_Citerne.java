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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author a.wattah
 */
@Entity
@Table(name = "BON_LIVRAISON_CITERNE")
public class Bon_Livraison_Citerne implements Serializable {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "DATE")
    @Temporal(TemporalType.DATE)
    private Date date;
    
    @Column(name = "DATEOPERATION")
    @Temporal(TemporalType.DATE)
    private Date dateOperation;

    @Column(name = "CHEMIN_FICHIER")
    private String chemin_fichier;

    @Column(name = "VOLUME_ACTUEL")
    private Double volume_actuel;

    @Column(name = "VOLUME")
    private Double volume;

    @OneToOne
    @JoinColumn(name = "CITERNE_ID", referencedColumnName = "ID")
    private Citerne citerne;

    @OneToOne(optional = true)
    private Engin engin;

    @OneToOne
    private Mensuel mensuel;

    @Column(name = "KILOMETRAGE")
    private String kilometrage;

    @Column(name = "HEURE_ENGIN")
    private String heure;

    @Column(name = "LAST_KILOMETRAGE")
    private String lastKilometrage;

    @Column(name = "LAST_HEURE_ENGIN")
    private String lastHeure;

    @Column(name = "ACTION")
    private String action;

    @Column(name = "NUMERO_Livraison")
    private String numero_Livraison;

    @Column(name = "NUMERO_COMMANDE")
    private String numero_commande;

    @Column(name = "COMMENTAIRE")
    private String commentaire;
    
    @Column(name = "NUM_BON")
    private String numBon;

    @Column(name = "FOURNISSEURBL")
    private String fournisseurBL;
    /**
     * add construct par default
     */
    public Bon_Livraison_Citerne() {
    }

    public Bon_Livraison_Citerne(Integer id) {
        this.id = id;

    }

    /**
     * getters and setters
     *
     * @return
     */
    public String getNumero_Livraison() {
        return numero_Livraison;
    }

    public void setNumero_Livraison(String numero_Livraison) {
        this.numero_Livraison = numero_Livraison;
    }

    public String getHeure() {
        return heure;
    }

    public void setHeure(String heure) {
        this.heure = heure;
    }

    public Mensuel getMensuel() {
        return mensuel;
    }

    public void setMensuel(Mensuel mensuel) {
        this.mensuel = mensuel;
    }

    public String getNumero_commande() {
        return numero_commande;
    }

    public void setNumero_commande(String numero_commande) {
        this.numero_commande = numero_commande;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Engin getEngin() {
        return engin;
    }

    public void setEngin(Engin engin) {
        this.engin = engin;
    }

    public String getKilometrage() {
        return kilometrage;
    }

    public void setKilometrage(String kilometrage) {
        this.kilometrage = kilometrage;
    }

    public Double getVolume_actuel() {
        return volume_actuel;
    }

    public void setVolume_actuel(Double volume_actuel) {
        this.volume_actuel = volume_actuel;
    }

    public Double getVolume() {
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }

    public String getChemin_fichier() {
        return chemin_fichier;
    }

    public void setChemin_fichier(String chemin_fichier) {
        this.chemin_fichier = chemin_fichier;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Citerne getCiterne() {
        return citerne;
    }

    public void setCiterne(Citerne citerne) {
        this.citerne = citerne;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public String getNumBon() {
        return numBon;
    }

    public void setNumBon(String numBon) {
        this.numBon = numBon;
    }

    public Date getDateOperation() {
        return dateOperation;
    }

    public void setDateOperation(Date dateOperation) {
        this.dateOperation = dateOperation;
    }

    public String getLastKilometrage() {
        return lastKilometrage;
    }

    public void setLastKilometrage(String lastKilometrage) {
        this.lastKilometrage = lastKilometrage;
    }

    public String getLastHeure() {
        return lastHeure;
    }

    public void setLastHeure(String lastHeure) {
        this.lastHeure = lastHeure;
    }

    public String getFournisseurBL() {
        return fournisseurBL;
    }

    public void setFournisseurBL(String fournisseurBL) {
        this.fournisseurBL = fournisseurBL;
    }
    
    
    

    /**
     * end getters and setters
     */

    
    
    @Override
    public String toString() {
        return "Bon_Livraison_Citerne{" + "id=" + id + ", date=" + date + ", chemin_fichier=" + chemin_fichier + ", volume_actuel=" + volume_actuel + ", volume=" + volume + ", kilometrage=" + kilometrage + ", heure=" + heure + ", action=" + action + ", numero_Livraison=" + numero_Livraison + ", numero_commande=" + numero_commande + ", commentaire=" + commentaire + ", numBon=" + numBon + '}';
    }
    
    
    
    
}
