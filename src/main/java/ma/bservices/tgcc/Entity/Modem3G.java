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
@DiscriminatorValue("Modem3g")
public class Modem3G extends Element{
    
     @Column (name = "NUEMRO")
     private String num3g;
     
     
     @Column (name = "IMEI")
     private String imei;
     
     @Column (name = "SERIENUMERO")
     private String serie_numero;
     
     
     
      @Column (name = "NUMERO3G")
     private String numero;
      
      
       @Column (name = "OPERATEUR")
     private String operateur;
     
     

    public String getNum3g() {
        return num3g;
    }

    public void setNum3g(String num3g) {
        this.num3g = num3g;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getSerie_numero() {
        return serie_numero;
    }

    public void setSerie_numero(String serie_numero) {
        this.serie_numero = serie_numero;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getOperateur() {
        return operateur;
    }

    public void setOperateur(String operateur) {
        this.operateur = operateur;
    }
     
     
    
    
     
    
}
