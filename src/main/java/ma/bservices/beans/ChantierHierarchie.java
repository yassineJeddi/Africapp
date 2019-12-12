/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.beans;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author admin
 */
@Entity
@Table
public class ChantierHierarchie implements Serializable {

    @EmbeddedId
    @Id
    private SalarieChantier id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Salarie superieur;

    public SalarieChantier getId() {
        return id;
    }

    public void setId(SalarieChantier id) {
        this.id = id;
    }

    public Salarie getSuperieur() {
        return superieur;
    }

    public void setSuperieur(Salarie superieur) {
        this.superieur = superieur;
    }

}
