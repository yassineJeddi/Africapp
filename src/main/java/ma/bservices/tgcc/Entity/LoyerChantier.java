/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.Entity;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import ma.bservices.beans.Chantier;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 *
 * @author j.allali
 */
@Entity
@DiscriminatorValue("LOYERCHANTIER")
public class LoyerChantier extends Loyer{

    public LoyerChantier() {
    }
   
    @Column(name = "IDENTIFIANT")
    private String identifiant;
    
    @OneToMany(mappedBy = "LoyerChantier")
    private List<BonCaisse> boncaisse;
    
    @JoinTable(name = "LOYER_SALARIE_CHANTIER", joinColumns = {
        @JoinColumn(name = "ID_LSCHANTIER", referencedColumnName = "ID")}, inverseJoinColumns = {
        @JoinColumn(name = "ID_CHANTIER", referencedColumnName = "PRJAP_ID")})
    @ManyToMany
    private List<Chantier> chantiers;

  
    
    
    public String getIdentifiant() {
        return identifiant;
    }

    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
    }

    public List<Chantier> getChantiers() {
        return chantiers;
    }

    public void setChantiers(List<Chantier> chantiers) {
        this.chantiers = chantiers;
    }

    public List<BonCaisse> getBoncaisse() {
        return boncaisse;
    }

    public void setBoncaisse(List<BonCaisse> boncaisse) {
        this.boncaisse = boncaisse;
    }

    public List<Chantier> getChantier() {
        return chantiers;
    }

    public void setChantier(List<Chantier> chantiers) {
        this.chantiers = chantiers;
    }
    
    
}
