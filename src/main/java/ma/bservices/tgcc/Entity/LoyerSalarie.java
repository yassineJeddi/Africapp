/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.Entity;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 *
 * @author a.wattah
 */
@Entity
@DiscriminatorValue("LoyerSalarie") 
public class LoyerSalarie extends Loyer {
    

    public LoyerSalarie() {
    }
    
    

    @OneToMany(mappedBy = "loyerSalarie")
    private List<BonCaisse> boncaisse;
    
    @JoinTable(name = "LOYER_SALARIE_MENSUEL", joinColumns = {
        @JoinColumn(name = "ID_LSSALARIE", referencedColumnName = "ID")}, inverseJoinColumns = {
        @JoinColumn(name = "ID_MENSUEL", referencedColumnName = "ID")})
    @ManyToMany(cascade = CascadeType.ALL)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Mensuel> mensuels;

    public List<Mensuel> getMensuels() {
        return mensuels;
    }

    public void setMensuels(List<Mensuel> mensuels) {
        this.mensuels = mensuels;
    }

    public List<BonCaisse> getBoncaisse() {
        return boncaisse;
    }

    public void setBoncaisse(List<BonCaisse> boncaisse) {
        this.boncaisse = boncaisse;
    }
    



    
}
