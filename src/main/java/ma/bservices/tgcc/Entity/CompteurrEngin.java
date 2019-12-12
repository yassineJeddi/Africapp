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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author yassine
 */
@Entity
@Table(name = "COMPTEUR_ENGIN")
public class CompteurrEngin implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    
    @Column(name = "OLDCPTH_ENG")
    private Float oldCptHEng;
    
    @Column(name = "OLDCPTK_ENG")
    private Float oldCptKEng;
    
    
    @Column(name = "OLDCPTH_REEL")
    private Float oldCptHReel;
    
    @Column(name = "OLDCPTK_REEL")
    private Float oldCptKReel;
    
    
    @Column(name = "CPTH_REL")
    private Float cptHReel;
    
    @Column(name = "CPTK_REL")
    private Float cptKReel;
    
    @Column(name = "Date_CREATION")
    @Temporal(TemporalType.DATE)
    private Date dateCreation;
    
    @Column(name = "DATE_CHANGEMENT")
    @Temporal(TemporalType.DATE)
    private Date dateChangement;
    
    @Column(name = "COMMENTAIRE")
    private String commentaire;
    
    @Column(name = "UTILISATEUR")
    private String utilisateur;
    
    @ManyToOne
    private Engin engin;
    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getOldCptHEng() {
        return oldCptHEng;
    }

    public void setOldCptHEng(Float oldCptHEng) {
        this.oldCptHEng = oldCptHEng;
    }

    public Float getOldCptKEng() {
        return oldCptKEng;
    }

    public void setOldCptKEng(Float oldCptKEng) {
        this.oldCptKEng = oldCptKEng;
    }

    public Float getOldCptHReel() {
        return oldCptHReel;
    }

    public void setOldCptHReel(Float oldCptHReel) {
        this.oldCptHReel = oldCptHReel;
    }

    public Float getOldCptKReel() {
        return oldCptKReel;
    }

    public void setOldCptKReel(Float oldCptKReel) {
        this.oldCptKReel = oldCptKReel;
    }

    public Float getCptHReel() {
        return cptHReel;
    }

    public void setCptHReel(Float cptHReel) {
        this.cptHReel = cptHReel;
    }

    public Float getCptKReel() {
        return cptKReel;
    }

    public void setCptKReel(Float cptKReel) {
        this.cptKReel = cptKReel;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Date getDateChangement() {
        return dateChangement;
    }

    public void setDateChangement(Date dateChangement) {
        this.dateChangement = dateChangement;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public String getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(String utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Engin getEngin() {
        return engin;
    }

    public void setEngin(Engin engin) {
        this.engin = engin;
    }

    public CompteurrEngin() {
    }

    public CompteurrEngin(Long id, Float oldCptHEng, Float oldCptKEng, Float oldCptHReel, Float oldCptKReel, Float cptHReel, Float cptKReel, Date dateCreation, Date dateChangement, String commentaire, String utilisateur) {
        this.id = id;
        this.oldCptHEng = oldCptHEng;
        this.oldCptKEng = oldCptKEng;
        this.oldCptHReel = oldCptHReel;
        this.oldCptKReel = oldCptKReel;
        this.cptHReel = cptHReel;
        this.cptKReel = cptKReel;
        this.dateCreation = dateCreation;
        this.dateChangement = dateChangement;
        this.commentaire = commentaire;
        this.utilisateur = utilisateur;
    }

    @Override
    public String toString() {
        return "CompteurrEngin{" + "id=" + id + ", oldCptHEng=" + oldCptHEng + ", oldCptKEng=" + oldCptKEng + ", oldCptHReel=" + oldCptHReel + ", oldCptKReel=" + oldCptKReel + ", cptHReel=" + cptHReel + ", cptKReel=" + cptKReel + ", dateCreation=" + dateCreation + ", dateChangement=" + dateChangement + ", commentaire=" + commentaire + ", utilisateur=" + utilisateur + '}';
    }

    

}
