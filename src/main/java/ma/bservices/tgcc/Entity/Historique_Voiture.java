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
 * @author admin
 */

@Entity
@Table(name = "HISTORIQUE_VOITURE")
@XmlRootElement
public class Historique_Voiture implements Serializable {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Mensuel mensuel;

    @OneToOne
    private Voiture voiture;

    @Temporal(value = TemporalType.DATE)    
    private Date dateAffectationVoiture;

    @Temporal(value = TemporalType.DATE)
    private Date dateDesaffectationVoiture;

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

    public Voiture getVoiture() {
        return voiture;
    }

    public void setVoiture(Voiture voiture) {
        this.voiture = voiture;
    }

    public Date getDateAffectationVoiture() {
        return dateAffectationVoiture;
    }

    public void setDateAffectationVoiture(Date dateAffectationVoiture) {
        this.dateAffectationVoiture = dateAffectationVoiture;
    }

    public Date getDateDesaffectationVoiture() {
        return dateDesaffectationVoiture;
    }

    public void setDateDesaffectationVoiture(Date dateDesaffectationVoiture) {
        this.dateDesaffectationVoiture = dateDesaffectationVoiture;
    }
    
    
    
    

}
