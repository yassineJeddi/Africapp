/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.Entity;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 *
 * @author a.wattah
 */
@Entity
@DiscriminatorValue("Telephone") 
public  class Telephone extends Element{
    
    @Column (name = "NUMSERIETEL")
    private String numSerieTel;
    
    @Column (name = "NUMLIGNE", unique = true)
    private String numLigneTel;
    
    @Column (name = "MONTANTTEL")
    private Double montantTel;
    
     @Column (name = "MARQUE")
     private String marque;
//    @OneToMany(mappedBy = "telephone" ,cascade=CascadeType.ALL)
//    private List<MarqueTelephone> marque;
   

    public String getNumSerieTel() {
        return numSerieTel;
    }

    public String getNumLigneTel() {
        return numLigneTel;
    }

    public void setNumLigneTel(String numLigneTel) {
        this.numLigneTel = numLigneTel;
    }

    public Double getMontantTel() {
        return montantTel;
    }

    public void setMontantTel(Double montantTel) {
        this.montantTel = montantTel;
    }
    
    public void setNumSerieTel(String numSerieTel) {
        this.numSerieTel = numSerieTel;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    

    

    public Telephone() {
        super();
    }
    
    
    
    
}
