/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.beans;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author admin
 */
@Entity
@Table(name = "PRJAP_AFFINITE")
public class ChantierAffinite implements Serializable {

    @Id
    @Column(name = "PRJAP_AFFINITE")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    private Chantier chantier;

    @OneToOne(fetch = FetchType.LAZY)
    private Chantier chantierAffinite;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Chantier getChantier() {
        return chantier;
    }

    public void setChantier(Chantier chantier) {
        this.chantier = chantier;
    }

    public Chantier getChantierAffinite() {
        return chantierAffinite;
    }

    public void setChantierAffinite(Chantier chantierAffinite) {
        this.chantierAffinite = chantierAffinite;
    }
    
    
    
}
