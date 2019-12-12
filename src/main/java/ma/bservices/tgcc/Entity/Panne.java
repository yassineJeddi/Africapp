/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.Entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import ma.bservices.beans.Utilisateur;

/**
 *
 * @author a.wattah
 */
@Entity
@Table(name = "HISTORIQUE_PANNE")
public class Panne implements Serializable {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "DATE_PANNE")
    @Temporal(TemporalType.DATE)
    private Date date;

    @Column(name = "COMMENTAIRE_PANNE")
    private String commentaire;
    
    
     @Column(name = "CHANTIER_PANNE")
    private String chantierPanne;
    

    @Column(name = "COMPTEUR_HORAIRE")
    private Float compteurHoraire;

    @Column(name = "COMPTEUR_KILOMETRIQUE")
    private Float compteurKilometrique;

    @Column(name = "DATE_MARCHE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date_marche;

    @ManyToOne
    private Utilisateur user_MEPanne;
    
    @ManyToOne
    private Utilisateur user_MEMarche;

    @ManyToOne
    private Engin engin;

    /**
     * add consruct par default
     */
    public Panne() {
    }

    /**
     * getter and setters
     *
     */
    public Integer getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public Date getDate_marche() {
        return date_marche;
    }

    public void setDate_marche(Date date_marche) {
        this.date_marche = date_marche;
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

    public Engin getEngin() {
        return engin;
    }

    public String getChantierPanne() {
        return chantierPanne;
    }

    public void setChantierPanne(String chantierPanne) {
        this.chantierPanne = chantierPanne;
    }
    
    

    public Utilisateur getUser_MEPanne() {
        return user_MEPanne;
    }

    public void setUser_MEPanne(Utilisateur user_MEPanne) {
        this.user_MEPanne = user_MEPanne;
    }

    public Utilisateur getUser_MEMarche() {
        return user_MEMarche;
    }

    public void setUser_MEMarche(Utilisateur user_MEMarche) {
        this.user_MEMarche = user_MEMarche;
    }

    
    public void setEngin(Engin engin) {
        this.engin = engin;
    }

    public Float getCompteurHoraire() {
        return compteurHoraire;
    }

    public void setCompteurHoraire(Float compteurHoraire) {
        this.compteurHoraire = compteurHoraire;
    }

    public Float getCompteurKilometrique() {
        return compteurKilometrique;
    }

    public void setCompteurKilometrique(Float compteurKilometrique) {
        this.compteurKilometrique = compteurKilometrique;
    }

    /**
     * end getters and setters
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Panne other = (Panne) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Panne{" + "id=" + id + ", date=" + date + ", commentaire=" + commentaire + ", chantierPanne=" + chantierPanne + ", compteurHoraire=" + compteurHoraire + ", compteurKilometrique=" + compteurKilometrique + ", date_marche=" + date_marche + ", user_MEPanne=" + user_MEPanne + ", user_MEMarche=" + user_MEMarche + ", engin=" + engin + '}';
    }

    
}
