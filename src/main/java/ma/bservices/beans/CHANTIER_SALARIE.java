/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.beans;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author yassine
 */
@Entity
public class CHANTIER_SALARIE implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    
    
    @Column(name = "CHANTIER_ID")
    private Integer CHANTIER_ID;
    
    
    @Column(name = "SALARIE_ID")
    private Integer SALARIE_ID;

    public Integer getCHANTIER_ID() {
        return CHANTIER_ID;
    }

    public void setCHANTIER_ID(Integer CHANTIER_ID) {
        this.CHANTIER_ID = CHANTIER_ID;
    }

    public Integer getSALARIE_ID() {
        return SALARIE_ID;
    }

    public void setSALARIE_ID(Integer SALARIE_ID) {
        this.SALARIE_ID = SALARIE_ID;
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
        if (!(object instanceof CHANTIER_SALARIE)) {
            return false;
        }
        CHANTIER_SALARIE other = (CHANTIER_SALARIE) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ma.bservices.beans.CHANTIER_SALARIE[ id=" + id + " ]";
    }
    
}
