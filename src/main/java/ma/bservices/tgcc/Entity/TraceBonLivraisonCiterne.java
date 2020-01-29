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

/**
 *
 * @author yassine
 */
@Entity

public class TraceBonLivraisonCiterne  implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    
    @Column(name = "utilisateur")
    private String utilisateur;
    
    @Column(name = "dateAction") 
    private Date dateAction;
    
    @Column(name = "module")
    private String module;
    
    
    @Column(name = "idBonLivraisonCiterne")
    private String idBonLivraisonCiterne;
    
    @Column(name = "DATE") 
    private Date date;
    
    @Column(name = "DATEOPERATION") 
    private Date dateOperation;

    @Column(name = "CHEMIN_FICHIER")
    private String chemin_fichier;

    @Column(name = "VOLUME_ACTUEL")
    private Double volume_actuel;

    @Column(name = "VOLUME")
    private Double volume;

    @Column(name = "citerne") 
    private String citerne;

    @Column(name = "engin")
    private String engin;

    @Column(name = "mensuel")
    private String mensuel;

    @Column(name = "KILOMETRAGE")
    private String kilometrage;

    @Column(name = "HEURE_ENGIN")
    private String heure;

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

    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(String utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Date getDateAction() {
        return dateAction;
    }

    public void setDateAction(Date dateAction) {
        this.dateAction = dateAction;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDateOperation() {
        return dateOperation;
    }

    public void setDateOperation(Date dateOperation) {
        this.dateOperation = dateOperation;
    }

    public String getChemin_fichier() {
        return chemin_fichier;
    }

    public void setChemin_fichier(String chemin_fichier) {
        this.chemin_fichier = chemin_fichier;
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

    public String getCiterne() {
        return citerne;
    }

    public void setCiterne(String citerne) {
        this.citerne = citerne;
    }

    public String getEngin() {
        return engin;
    }

    public void setEngin(String engin) {
        this.engin = engin;
    }

    public String getMensuel() {
        return mensuel;
    }

    public void setMensuel(String mensuel) {
        this.mensuel = mensuel;
    }

    public String getKilometrage() {
        return kilometrage;
    }

    public void setKilometrage(String kilometrage) {
        this.kilometrage = kilometrage;
    }

    public String getHeure() {
        return heure;
    }

    public void setHeure(String heure) {
        this.heure = heure;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getNumero_Livraison() {
        return numero_Livraison;
    }

    public void setNumero_Livraison(String numero_Livraison) {
        this.numero_Livraison = numero_Livraison;
    }

    public String getNumero_commande() {
        return numero_commande;
    }

    public void setNumero_commande(String numero_commande) {
        this.numero_commande = numero_commande;
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

    public String getIdBonLivraisonCiterne() {
        return idBonLivraisonCiterne;
    }

    public void setIdBonLivraisonCiterne(String idBonLivraisonCiterne) {
        this.idBonLivraisonCiterne = idBonLivraisonCiterne;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
}
