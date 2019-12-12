/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.Entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import ma.bservices.beans.Chantier;
import ma.bservices.beans.Document;
import ma.bservices.beans.Salarie;

/**
 *
 * @author yassine.jeddi
 */
@Entity
public class MvmtCaisse implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(name = "TYPE")
    private String type;
    
    @Column(name = "MTN")
    private Float mtn;
    
    @Column(name = "NATURE")
    private String nature;
    
    @Column(name = "STATUS")
    private String status;
    
    @Column(name = "COMMENT")
    private String comment;
    
    @Column(name = "CATEGORIE")
    private String categorie;
    
    @Column(name = "DESIGN")
    private String designation;
    
    @Column(name = "ARCHIVER")
    private Boolean archiver;
    
    @Column(name = "DATE_mvmt")
    private Date dateMvmt;
    
    @Column(name = "DATE_pay")
    private Date datePay;
    
    @Column(name = "DATE_db")
    private Date dateDb;
    
    @Column(name = "DATE_df")
    private Date dateDf;
    
    @Column(name = "DATE_OPERATION")
    private Date dateOperation;
    
    
    @ManyToOne
    private Caisse caisse;
    
    @ManyToOne
    private Salarie salarie;

    @ManyToOne
    private Chantier chantier;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDateMvmt() {
        return dateMvmt;
    }

    public void setDateMvmt(Date dateMvmt) {
        this.dateMvmt = dateMvmt;
    }

    public Date getDateOperation() {
        return dateOperation;
    }

    public void setDateOperation(Date dateOperation) {
        this.dateOperation = dateOperation;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public Caisse getCaisse() {
        return caisse;
    }

    public void setCaisse(Caisse caisse) {
        this.caisse = caisse;
    }

    public Salarie getSalarie() {
        return salarie;
    }

    public void setSalarie(Salarie salarie) {
        this.salarie = salarie;
    }

    public Chantier getChantier() {
        return chantier;
    }

    public void setChantier(Chantier chantier) {
        this.chantier = chantier;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getArchiver() {
        return archiver;
    }

    public void setArchiver(Boolean archiver) {
        this.archiver = archiver;
    }

    public Float getMtn() {
        return mtn;
    }

    public void setMtn(Float mtn) {
        this.mtn = mtn;
    }

    public Date getDatePay() {
        return datePay;
    }

    public void setDatePay(Date datePay) {
        this.datePay = datePay;
    }

    public Date getDateDb() {
        return dateDb;
    }

    public void setDateDb(Date dateDb) {
        this.dateDb = dateDb;
    }

    public Date getDateDf() {
        return dateDf;
    }

    public void setDateDf(Date dateDf) {
        this.dateDf = dateDf;
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
        if (!(object instanceof MvmtCaisse)) {
            return false;
        }
        MvmtCaisse other = (MvmtCaisse) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    public MvmtCaisse() {
    }

    
    
}
