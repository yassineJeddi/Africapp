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
import javax.persistence.Table;

/**
 *
 * @author yassine
 */
@Entity
@Table(name = "ECHEANCIER_VIDANGE")
public class ECHEANCIER_VIDANGE implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    
    @Column(name = "CODE")
    private String CODE;
    @Column(name = "DATE_PR_VID")
    private Date DATE_PR_VID;
    @Column(name = "CP_PR_VID")
    private Long CP_PR_VID;
    @Column(name = "TYPE_PR_VID")
    private Long TYPE_PR_VID;

    public String getCODE() {
        return CODE;
    }

    public void setCODE(String CODE) {
        this.CODE = CODE;
    }

    public Date getDATE_PR_VID() {
        return DATE_PR_VID;
    }

    public void setDATE_PR_VID(Date DATE_PR_VID) {
        this.DATE_PR_VID = DATE_PR_VID;
    }

    public Long getCP_PR_VID() {
        return CP_PR_VID;
    }

    public void setCP_PR_VID(Long CP_PR_VID) {
        this.CP_PR_VID = CP_PR_VID;
    }

    public Long getTYPE_PR_VID() {
        return TYPE_PR_VID;
    }

    public void setTYPE_PR_VID(Long TYPE_PR_VID) {
        this.TYPE_PR_VID = TYPE_PR_VID;
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
        if (!(object instanceof ECHEANCIER_VIDANGE)) {
            return false;
        }
        ECHEANCIER_VIDANGE other = (ECHEANCIER_VIDANGE) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ECHEANCIER_VIDANGE{" + "id=" + id + ", CODE=" + CODE + ", DATE_PR_VID=" + DATE_PR_VID + ", CP_PR_VID=" + CP_PR_VID + ", TYPE_PR_VID=" + TYPE_PR_VID + '}';
    }
 
}
