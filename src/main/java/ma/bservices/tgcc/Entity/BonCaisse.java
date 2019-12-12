/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.Entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author j.allali
 */
@Entity
@Table(name = "BONCAISSE")
@XmlRootElement
public class BonCaisse implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;

    @Column(name = "MOIS")
    private String mois;

    @Column(name = "ANNEE")
    private String annee;

    @Column(name = "CHEMIN")
    private String chemin;
    
    
     @Column(name = "CHEMIN_RECU")
    private String cheminRecu;

    @ManyToOne(fetch = FetchType.LAZY)
    private LoyerSalarie loyerSalarie;

    @ManyToOne(fetch = FetchType.LAZY)
    private LoyerChantier LoyerChantier;

    public LoyerChantier getLoyerChantier() {
        return LoyerChantier;
    }

    public void setLoyerChantier(LoyerChantier LoyerChantier) {
        this.LoyerChantier = LoyerChantier;
    }

    public LoyerSalarie getLoyerSalarie() {
        return loyerSalarie;
    }

    public void setLoyerSalarie(LoyerSalarie loyerSalarie) {
        this.loyerSalarie = loyerSalarie;
    }

    public Long getId() {
        return id;
    }

    public String getCheminRecu() {
        return cheminRecu;
    }

    public void setCheminRecu(String cheminRecu) {
        this.cheminRecu = cheminRecu;
    }
    
    

    public void setId(Long id) {
        this.id = id;
    }

    public String getMois() {
        return mois;
    }

    public void setMois(String mois) {
        this.mois = mois;
    }

    public String getAnnee() {
        return annee;
    }

    public void setAnnee(String annee) {
        this.annee = annee;
    }

    public BonCaisse() {
    }

    public BonCaisse(Long id) {
        this.id = id;
    }

    public String getChemin() {
        return chemin;
    }

    public void setChemin(String chemin) {
        this.chemin = chemin;
    }
}
