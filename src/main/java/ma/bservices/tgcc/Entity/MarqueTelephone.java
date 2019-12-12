/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.Entity;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


/**
 *
 * @author a.wattah
 */
@Entity
@Table (name = "MarqueTelephone") 
public class MarqueTelephone implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    
    @ManyToOne(cascade = CascadeType.ALL)
    private Telephone telephone;
    
     @Column(name = "MARQUE")
    private String marqueTel;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMarqueTel() {
        return marqueTel;
    }

    public void setMarqueTel(String marqueTel) {
        this.marqueTel = marqueTel;
    }

    public Telephone getTelephone() {
        return telephone;
    }

    public void setTelephone(Telephone telephone) {
        this.telephone = telephone;
    }
     
     
    
    
    
}
