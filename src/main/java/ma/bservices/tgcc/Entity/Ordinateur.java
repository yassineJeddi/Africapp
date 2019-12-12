/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.Entity;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author j.allali
 */
@Entity
@DiscriminatorValue("Ordinateur")
public class Ordinateur extends Element {

    @Column(name = "NUMERO_SERIE_ORDI")
    private String NumeroSerieOrd;

    @Column(name = "MARQUE")
    private String marque;

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public String getNumeroSerieOrd() {
        return NumeroSerieOrd;
    }

    public void setNumeroSerieOrd(String NumeroSerieOrd) {
        this.NumeroSerieOrd = NumeroSerieOrd;
    }

    public Ordinateur() {
        super();
    }

}
