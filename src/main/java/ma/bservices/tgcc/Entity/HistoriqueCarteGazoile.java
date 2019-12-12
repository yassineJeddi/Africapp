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
import ma.bservices.beans.Chantier;
import ma.bservices.beans.Salarie;

/**
 *
 * @author yassine.jeddi
 */
@Entity
@Table(name = "HISTORIQUECARTEGAZOILE")
public class HistoriqueCarteGazoile implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    
    @Column(name = "DATE_HISTORIQUE")
    private Date datehistorique;
    
    
    @Column(name = "DATE_AFECT")
    private Date dateAfect;
            
    
    @Column(name = "DATE_DESAFECT")
    private Date dateDesafect;
            
    @ManyToOne
    private CarteGasoil carteGasoil;
    
    @ManyToOne
    private Salarie salarie;
            
    @ManyToOne
    private Chantier chantier;

    public void setDatehistorique(Date datehistorique) {
        this.datehistorique = datehistorique;
    }

    public void setDateAfect(Date dateAfect) {
        this.dateAfect = dateAfect;
    }

    public void setDateDesafect(Date dateDesafect) {
        this.dateDesafect = dateDesafect;
    }

    

    public void setSalarie(Salarie salarie) {
        this.salarie = salarie;
    }

    public void setChantier(Chantier chantier) {
        this.chantier = chantier;
    }

    public Date getDatehistorique() {
        return datehistorique;
    }

    public Date getDateAfect() {
        return dateAfect;
    }

    public Date getDateDesafect() {
        return dateDesafect;
    }

    public void setCarteGasoil(CarteGasoil carteGasoil) {
        this.carteGasoil = carteGasoil;
    }

    public CarteGasoil getCarteGasoil() {
        return carteGasoil;
    }


    public Salarie getSalarie() {
        return salarie;
    }

    public Chantier getChantier() {
        return chantier;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HistoriqueCarteGazoile)) {
            return false;
        }
        HistoriqueCarteGazoile other = (HistoriqueCarteGazoile) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "HistoriqueCarteGazoile{" + "id=" + id + ", datehistorique=" + datehistorique + ", dateAfect=" + dateAfect + ", dateDesafect=" + dateDesafect + ", salarie=" + salarie + ", chantier=" + chantier + '}';
    }

    public HistoriqueCarteGazoile() {
    }

    
    
}
