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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author airaamane
 */

@Entity
@Table(name = "HISTORIQUE_LoyerS")
@XmlRootElement
public class Historique_LoyerS implements Serializable{
    
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

     @OneToOne
    private Mensuel mensuel;
     
     @OneToOne
    private LoyerSalarie loyerS;
     
    @Temporal(value = TemporalType.DATE)
    private Date dateAffectation;

    @Temporal(value = TemporalType.DATE)
    private Date dateDesaffectation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Mensuel getMensuel() {
        return mensuel;
    }

    public void setMensuel(Mensuel mensuel) {
        this.mensuel = mensuel;
    }

    public LoyerSalarie getLoyerS() {
        return loyerS;
    }

    public void setLoyerS(LoyerSalarie loyerS) {
        this.loyerS = loyerS;
    }

    public Date getDateAffectation() {
        return dateAffectation;
    }

    public void setDateAffectation(Date dateAffectation) {
        this.dateAffectation = dateAffectation;
    }

    public Date getDateDesaffectation() {
        return dateDesaffectation;
    }

    public void setDateDesaffectation(Date dateDesaffectation) {
        this.dateDesaffectation = dateDesaffectation;
    }
     

     
     
     
}
