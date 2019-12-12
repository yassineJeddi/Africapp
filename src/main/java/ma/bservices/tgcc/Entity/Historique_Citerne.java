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
 * @author a.wattah
 */
@Entity
@Table(name = "HISTORIQUE_CITERNE")
public class Historique_Citerne implements Serializable {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "CODE_COMMANDE")
    private String code_commande;

    @Column(name = "DATE_AFFECTATION")
    @Temporal(TemporalType.DATE)
    private Date date_affectation_citerne;

    @ManyToOne
    private Citerne citerne;

    @ManyToOne
    private Engin engin;
    
    /**
     * add construct par default
     */

    public Historique_Citerne() {
    }

    public Historique_Citerne(Integer id, String code_commande, Date date_affectation_citerne) {
        this.id = id;
        this.code_commande = code_commande;
        this.date_affectation_citerne = date_affectation_citerne;
    }
    
    /**
     * getters and setters
     * @return 
     */

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode_commande() {
        return code_commande;
    }

    public void setCode_commande(String code_commande) {
        this.code_commande = code_commande;
    }

    public Date getDate_affectation_citerne() {
        return date_affectation_citerne;
    }

    public void setDate_affectation_citerne(Date date_affectation_citerne) {
        this.date_affectation_citerne = date_affectation_citerne;
    }

    public Citerne getCiterne() {
        return citerne;
    }

    public void setCiterne(Citerne citerne) {
        this.citerne = citerne;
    }

    public Engin getEngin() {
        return engin;
    }

    public void setEngin(Engin engin) {
        this.engin = engin;
    }
    
    
    
    
    /**
     * end getters and setters
     */
    
    
    
    
    
    
    
    
    
    
    
    

}
