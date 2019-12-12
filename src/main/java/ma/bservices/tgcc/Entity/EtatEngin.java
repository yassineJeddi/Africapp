/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.Entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author j.allali
 */
@Entity
@Table(name = "ETAT_ENGIN")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EtatEngin.findAll", query = "SELECT e FROM EtatEngin e"),
    @NamedQuery(name = "EtatEngin.findByIDEtat", query = "SELECT e FROM EtatEngin e WHERE e.iDEtat = :iDEtat"),
    @NamedQuery(name = "EtatEngin.findByLibelleEtat", query = "SELECT e FROM EtatEngin e WHERE e.libelleEtat = :libelleEtat")})
public class EtatEngin implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_Etat")
    private Integer iDEtat;
    @Column(name = "LIBELLE_ETAT")
    private String libelleEtat;
//    @OneToOne(cascade = CascadeType.ALL, mappedBy = "etatEngin")
//    private Poee poee;
//    @JoinColumn(name = "ID_Etat", referencedColumnName = "ID_POIN_ENG")
//    @OneToOne
//    private PointageEngin pointageEngin;
//    @OneToMany(mappedBy = "idEtat")
//    private Collection<PointageEngin> pointageEnginCollection;

    public EtatEngin() {
    }

    public EtatEngin(Integer iDEtat) {
        this.iDEtat = iDEtat;
    }

    public Integer getIDEtat() {
        return iDEtat;
    }

//    public Collection<PointageEngin> getPointageEnginCollection() {
//        return pointageEnginCollection;
//    }
//
//    public void setPointageEnginCollection(Collection<PointageEngin> pointageEnginCollection) {
//        this.pointageEnginCollection = pointageEnginCollection;
//    }
    
    
    public void setIDEtat(Integer iDEtat) {
        this.iDEtat = iDEtat;
    }

    public String getLibelleEtat() {
        return libelleEtat;
    }

    public void setLibelleEtat(String libelleEtat) {
        this.libelleEtat = libelleEtat;
    }

//    public Poee getPoee() {
//        return poee;
//    }
//
//    public void setPoee(Poee poee) {
//        this.poee = poee;
//    }

//    public PointageEngin getPointageEngin() {
//        return pointageEngin;
//    }
//
//    public void setPointageEngin(PointageEngin pointageEngin) {
//        this.pointageEngin = pointageEngin;
//    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDEtat != null ? iDEtat.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EtatEngin)) {
            return false;
        }
        EtatEngin other = (EtatEngin) object;
        if ((this.iDEtat == null && other.iDEtat != null) || (this.iDEtat != null && !this.iDEtat.equals(other.iDEtat))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ma.bservices.tgcc.Entity.EtatEngin[ iDEtat=" + iDEtat + " ]";
    }

    public EtatEngin(String libelleEtat) {
        this.libelleEtat = libelleEtat;
    }

    public EtatEngin(Integer iDEtat, String libelleEtat, PointageEngin pointageEngin) {
        this.iDEtat = iDEtat;
        this.libelleEtat = libelleEtat;
//        this.pointageEngin = pointageEngin;
    }

    public EtatEngin(Integer iDEtat, String libelleEtat) {
        this.iDEtat = iDEtat;
        this.libelleEtat = libelleEtat;
    }

  
    
}
