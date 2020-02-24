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

/**
 *
 * @author yassine
 */
@Entity
public class ReferentielEngin implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    
    
    
    @Column(name = "TYPE_VID")
    private String typeVid;
    
    @Column(name = "DES")
    private String des;
    
    @Column(name = "REFERENCE")
    private String reference;
    
    @Column(name = "MARQUE")
    private String marque;
    
    @Column(name = "QUANTITE")
    private String quantite;
    
    @Column(name = "UNITE") 
    private String unite;
    
    
    @Column(name = "USERADD") 
    private String userAdd;
    @Column(name = "DATEADD") 
    private Date dateAdd;
    
    @Column(name = "USEREDIT") 
    private String userEdit;
    @Column(name = "DATEEDIT") 
    private Date dateEdit;
    
    @ManyToOne
    private Engin engin;
    
    
    
    

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

    public String getTypeVid() {
        return typeVid;
    }

    public void setTypeVid(String typeVid) {
        this.typeVid = typeVid;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public String getQuantite() {
        return quantite;
    }

    public void setQuantite(String quantite) {
        this.quantite = quantite;
    }

    public String getUnite() {
        return unite;
    }

    public void setUnite(String unite) {
        this.unite = unite;
    }

    public Engin getEngin() {
        return engin;
    }

    public void setEngin(Engin engin) {
        this.engin = engin;
    }

    public String getUserAdd() {
        return userAdd;
    }

    public void setUserAdd(String userAdd) {
        this.userAdd = userAdd;
    }

    public Date getDateAdd() {
        return dateAdd;
    }

    public void setDateAdd(Date dateAdd) {
        this.dateAdd = dateAdd;
    }

    public String getUserEdit() {
        return userEdit;
    }

    public void setUserEdit(String userEdit) {
        this.userEdit = userEdit;
    }

    public Date getDateEdit() {
        return dateEdit;
    }

    public void setDateEdit(Date dateEdit) {
        this.dateEdit = dateEdit;
    }

    @Override
    public String toString() {
        return "ReferentielEngin{" + "id=" + id + ", typeVid=" + typeVid + ", des=" + des + ", reference=" + reference + ", marque=" + marque + ", quantite=" + quantite + ", unite=" + unite + ", userAdd=" + userAdd + ", dateAdd=" + dateAdd + ", userEdit=" + userEdit + ", dateEdit=" + dateEdit + ", engin=" + engin + '}';
    }

    
    
    
 
}
