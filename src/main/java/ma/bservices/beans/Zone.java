/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.beans;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import ma.bservices.tgcc.Entity.AffectationStock;
import ma.bservices.tgcc.Entity.PointageEngin;
import ma.bservices.tgcc.Entity.ZoneArticleQ;

/**
 *
 * @author j.allali
 */
@Entity
@Table(name = "ZONE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Zone.findAll", query = "SELECT z FROM Zone z"),
    @NamedQuery(name = "Zone.findByIdZone", query = "SELECT z FROM Zone z WHERE z.idZone = :idZone"),
    @NamedQuery(name = "Zone.findByLibeleZone", query = "SELECT z FROM Zone z WHERE z.libeleZone = :libeleZone")})
public class Zone implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @Column(name = "ID_ZONE")
    private Integer idZone;

    public Zone() {
    }

    @Column(name = "LIBELE_ZONE")
    private String libeleZone;

    @ManyToOne
    @JoinColumn(name = "ID_CHANTIER", referencedColumnName = "PRJAP_ID")
    private Chantier idChantier;

    @ManyToMany(mappedBy = "zones", fetch = FetchType.LAZY)
    private List<Salarie> salaries;

    @ManyToMany(mappedBy = "zones")
    private List<PointageLot> pointageLots;

    @OneToMany(mappedBy = "zoneId")
    private List<AffectationStock> affectationsInZone;
    
     @OneToMany(mappedBy = "zoneId")
    private List<ZoneArticleQ> affectationsEntries;

    @ManyToMany(mappedBy = "zoneList")
    private List<PointageEngin> pointageEnginList;

    public Zone(Integer idZone) {
        this.idZone = idZone;
    }

    public List<Salarie> getSalaries() {
        return salaries;
    }

    public void setSalaries(List<Salarie> salaries) {
        this.salaries = salaries;
    }

    public Integer getIdZone() {
        return idZone;
    }

    public void setIdZone(Integer idZone) {
        this.idZone = idZone;
    }

    public String getLibeleZone() {
        return libeleZone;
    }

    public void setLibeleZone(String libeleZone) {
        this.libeleZone = libeleZone;
    }

    public List<ZoneArticleQ> getAffectationsEntries() {
        return affectationsEntries;
    }

    public void setAffectationsEntries(List<ZoneArticleQ> affectationsEntries) {
        this.affectationsEntries = affectationsEntries;
    }
    
    

    public Chantier getIdChantier() {
        return idChantier;
    }

    public void setIdChantier(Chantier idChantier) {
        this.idChantier = idChantier;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idZone != null ? idZone.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Zone)) {
            return false;
        }
        Zone other = (Zone) object;
        if ((this.idZone == null && other.idZone != null) || (this.idZone != null && !this.idZone.equals(other.idZone))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ma.bservices.tgcc.Entity.Zone[ idZone=" + idZone + " ]";
    }

    public Zone(String libeleZone) {
        this.libeleZone = libeleZone;
    }

    public Zone(String libeleZone, Chantier idChantier) {
        this.libeleZone = libeleZone;
        this.idChantier = idChantier;
    }

    public List<PointageLot> getPointageLots() {
        return pointageLots;
    }

    public void setPointageLots(List<PointageLot> pointageLots) {
        this.pointageLots = pointageLots;
    }

    public List<AffectationStock> getAffectationsInZone() {
        return affectationsInZone;
    }

    public void setAffectationsInZone(List<AffectationStock> affectationsInZone) {
        this.affectationsInZone = affectationsInZone;
    }


    public List<PointageEngin> getPointageEnginList() {
        return pointageEnginList;
    }

    public void setPointageEnginList(List<PointageEngin> pointageEnginList) {
        this.pointageEnginList = pointageEnginList;
    }

}
