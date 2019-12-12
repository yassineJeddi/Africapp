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
import javax.persistence.Table;

/**
 *
 * @author yassine
 */
@Entity
@Table(name = "HISTORIQUE_PANNE")
public class HistoriquePanne implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(name = "COMMENTAIRE_PANNE")
    String COMMENTAIRE_PANNE;
    @Column(name = "COMPTEUR_HORAIRE")
    Integer COMPTEUR_HORAIRE;
    @Column(name = "DATE_PANNE")
    Date DATE_PANNE;
    @Column(name = "DATE_MARCHE")
    Date DATE_MARCHE;
    @Column(name = "engin_ID_Engin")
    Integer engin_ID_Engin;
    @Column(name = "user_MEMarche_ID")
    Integer user_MEMarche_ID;
    @Column(name = "user_MEPanne_ID")
    Integer user_MEPanne_ID;
    @Column(name = "CHANTIER_PANNE")
    String CHANTIER_PANNE; 

    public String getCOMMENTAIRE_PANNE() {
        return COMMENTAIRE_PANNE;
    }

    public void setCOMMENTAIRE_PANNE(String COMMENTAIRE_PANNE) {
        this.COMMENTAIRE_PANNE = COMMENTAIRE_PANNE;
    }

    public Integer getCOMPTEUR_HORAIRE() {
        return COMPTEUR_HORAIRE;
    }

    public void setCOMPTEUR_HORAIRE(Integer COMPTEUR_HORAIRE) {
        this.COMPTEUR_HORAIRE = COMPTEUR_HORAIRE;
    }

    public Date getDATE_PANNE() {
        return DATE_PANNE;
    }

    public void setDATE_PANNE(Date DATE_PANNE) {
        this.DATE_PANNE = DATE_PANNE;
    }

    public Date getDATE_MARCHE() {
        return DATE_MARCHE;
    }

    public void setDATE_MARCHE(Date DATE_MARCHE) {
        this.DATE_MARCHE = DATE_MARCHE;
    }

    public Integer getEngin_ID_Engin() {
        return engin_ID_Engin;
    }

    public void setEngin_ID_Engin(Integer engin_ID_Engin) {
        this.engin_ID_Engin = engin_ID_Engin;
    }

    public Integer getUser_MEMarche_ID() {
        return user_MEMarche_ID;
    }

    public void setUser_MEMarche_ID(Integer user_MEMarche_ID) {
        this.user_MEMarche_ID = user_MEMarche_ID;
    }

    public Integer getUser_MEPanne_ID() {
        return user_MEPanne_ID;
    }

    public void setUser_MEPanne_ID(Integer user_MEPanne_ID) {
        this.user_MEPanne_ID = user_MEPanne_ID;
    }

    public String getCHANTIER_PANNE() {
        return CHANTIER_PANNE;
    }

    public void setCHANTIER_PANNE(String CHANTIER_PANNE) {
        this.CHANTIER_PANNE = CHANTIER_PANNE;
    }
    
    
    
    
    
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HistoriquePanne)) {
            return false;
        }
        HistoriquePanne other = (HistoriquePanne) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "HistoriquePanne{" + "id=" + id + ", COMMENTAIRE_PANNE=" + COMMENTAIRE_PANNE + ", COMPTEUR_HORAIRE=" + COMPTEUR_HORAIRE + ", DATE_PANNE=" + DATE_PANNE + ", DATE_MARCHE=" + DATE_MARCHE + ", engin_ID_Engin=" + engin_ID_Engin + ", user_MEMarche_ID=" + user_MEMarche_ID + ", user_MEPanne_ID=" + user_MEPanne_ID + ", CHANTIER_PANNE=" + CHANTIER_PANNE + '}';
    }

     
    
}
