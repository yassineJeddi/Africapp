/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.Entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author yassine.jeddi
 */

@Entity
@Table(name = "CONDUCTEUR_ENGIN")
public class ConducteurEngin  implements Serializable {
    
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer iDConducteurEngin; 
    
    @Column(name = "ID_ENGIN")
    private Integer ID_ENGIN;
    
    @Column(name = "MATRICULE")
    private String MATRICULE;
    
    @Column(name = "DATE_DEB_AFECT")
    @Temporal(TemporalType.DATE)
    private Date DATE_DEB_AFECT;
       
    @Column(name = "DATE_FIN_AFECT")
    @Temporal(TemporalType.DATE)
    private Date DATE_FIN_AFECT;
    
    @Column(name = "ACTIF")
    private Short actif;

    public Integer getiDConducteurEngin() {
        return iDConducteurEngin;
    }

    public Integer getID_ENGIN() {
        return ID_ENGIN;
    }

    public String getMATRICULE() {
        return MATRICULE;
    }

    public Date getDATE_DEB_AFECT() {
        return DATE_DEB_AFECT;
    }

    public Date getDATE_FIN_AFECT() {
        return DATE_FIN_AFECT;
    }

    public Short getActif() {
        return actif;
    }

    public void setiDConducteurEngin(Integer iDConducteurEngin) {
        this.iDConducteurEngin = iDConducteurEngin;
    }

    public void setID_ENGIN(Integer ID_ENGIN) {
        this.ID_ENGIN = ID_ENGIN;
    }

    public void setMATRICULE(String MATRICULE) {
        this.MATRICULE = MATRICULE;
    }

    public void setDATE_DEB_AFECT(Date DATE_DEB_AFECT) {
        this.DATE_DEB_AFECT = DATE_DEB_AFECT;
    }

    public void setDATE_FIN_AFECT(Date DATE_FIN_AFECT) {
        this.DATE_FIN_AFECT = DATE_FIN_AFECT;
    }

    public void setActif(Short actif) {
        this.actif = actif;
    }

    public ConducteurEngin() {
    }

    public ConducteurEngin(Integer iDConducteurEngin, Integer ID_ENGIN, String MATRICULE, Date DATE_DEB_AFECT, Date DATE_FIN_AFECT, Short actif) {
        this.iDConducteurEngin = iDConducteurEngin;
        this.ID_ENGIN = ID_ENGIN;
        this.MATRICULE = MATRICULE;
        this.DATE_DEB_AFECT = DATE_DEB_AFECT;
        this.DATE_FIN_AFECT = DATE_FIN_AFECT;
        this.actif = actif;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ConducteurEngin other = (ConducteurEngin) obj;
        if (!Objects.equals(this.iDConducteurEngin, other.iDConducteurEngin)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ConducteurEngin{" + "iDConducteurEngin=" + iDConducteurEngin + ", ID_ENGIN=" + ID_ENGIN + ", MATRICULE=" + MATRICULE + ", DATE_DEB_AFECT=" + DATE_DEB_AFECT + ", DATE_FIN_AFECT=" + DATE_FIN_AFECT + ", actif=" + actif + '}';
    }
    
    
}
