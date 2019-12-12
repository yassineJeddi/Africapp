/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.Entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import ma.bservices.beans.Chantier;
import ma.bservices.beans.Lot;
import ma.bservices.beans.Utilisateur;
import ma.bservices.beans.Zone;

/**
 *
 * @author j.allali
 */
@Entity
@Table(name = "POINTAGE_ENGIN")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PointageEngin.findAll", query = "SELECT p FROM PointageEngin p"),
    @NamedQuery(name = "PointageEngin.findByIdPoinEng", query = "SELECT p FROM PointageEngin p WHERE p.idPoinEng = :idPoinEng"),
    @NamedQuery(name = "PointageEngin.findByCode", query = "SELECT p FROM PointageEngin p WHERE p.code = :code"),
    @NamedQuery(name = "PointageEngin.findByDateCreation", query = "SELECT p FROM PointageEngin p WHERE p.dateCreation = :dateCreation"),
    @NamedQuery(name = "PointageEngin.findByDesignation", query = "SELECT p FROM PointageEngin p WHERE p.designation = :designation"),
    @NamedQuery(name = "PointageEngin.findByPointePar", query = "SELECT p FROM PointageEngin p WHERE p.pointePar = :pointePar"),
    @NamedQuery(name = "PointageEngin.findByReference", query = "SELECT p FROM PointageEngin p WHERE p.reference = :reference")})
public class PointageEngin implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_POIN_ENG")
    private Integer idPoinEng;

    @Column(name = "CODE")
    private String code;

    @Column(name = "DATE_CREATION")
    @Temporal(TemporalType.DATE)
    private Date dateCreation;

    @Column(name = "DESIGNATION")
    private String designation;

    @Column(name = "POINTE_PAR")
    private String pointePar;

    @ManyToOne(cascade = CascadeType.ALL)
    private EtatEngin idEtat;

    @Column(name = "REFERENCE")
    private String reference;

    @Transient
    private Integer idChantierAffinite;
    
   
    private String LibLot;

    @ManyToOne(cascade = CascadeType.ALL)
    private Utilisateur user;

    @JoinTable(name = "Pointage_ZONE", joinColumns = {
        @JoinColumn(name = "ID_POIN_ENG", referencedColumnName = "ID_POIN_ENG")}, inverseJoinColumns = {
        @JoinColumn(name = "ID_ZONE", referencedColumnName = "ID_ZONE")})
    @ManyToMany(cascade = CascadeType.ALL)
    private List<Zone> zoneList;

    @JoinColumn(name = "ID_LOT", referencedColumnName = "ID")
    @ManyToOne(cascade = CascadeType.ALL)
    private Lot idLot;

    @JoinColumn(name = "ID_Engin", referencedColumnName = "ID_Engin")
    @ManyToOne
    private Engin iDEngin;

//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pointageEngin")
//    private List<Poee> poeeList;
    @OneToOne(cascade = CascadeType.ALL)
    private EtatEngin etatEngin;

    @OneToOne(cascade = CascadeType.ALL)
    private Chantier chantierAffinite;

    @ManyToOne
    private Chantier chantierPointage;

    /**
     * Cet attribut est utilisé pour récuprére la liste des zones du pointage
     */
    @Transient
    private String[] zones_str;

    @Column(name = "NBR_HEURES")
    private Float nbrHeures;

    @Column(name = "NBR_KM")
    private Float nbrKm;

    @Column(name = "NBRJ_HEURES")
    private Float nbrJHeures;

    @Column(name = "NBRJ_KM")
    private Float nbrJKm;

    public Float getNbrJHeures() {
        return nbrJHeures;
    }

    public void setNbrJHeures(Float nbrJHeures) {
        this.nbrJHeures = nbrJHeures;
    }

    public Float getNbrJKm() {
        return nbrJKm;
    }

    public void setNbrJKm(Float nbrJKm) {
        this.nbrJKm = nbrJKm;
    }

    
    public Chantier getChantierPointage() {
        return chantierPointage;
    }

    public void setChantierPointage(Chantier chantierPointage) {
        this.chantierPointage = chantierPointage;
    }

    public Integer getIdChantierAffinite() {
        return idChantierAffinite;
    }

    public void setIdChantierAffinite(Integer idChantierAffinite) {
        this.idChantierAffinite = idChantierAffinite;
    }

    public Chantier getChantierAffinite() {
        return chantierAffinite;
    }

    public void setChantierAffinite(Chantier chantierAffinite) {
        this.chantierAffinite = chantierAffinite;
    }

    public Utilisateur getUser() {
        return user;
    }

    public String getLibLot() {
        return LibLot;
    }

    public void setLibLot(String LibLot) {
        this.LibLot = LibLot;
    }

    
    public void setUser(Utilisateur user) {
        this.user = user;
    }

    public String[] getZones_str() {
        return zones_str;
    }

    public void setZones_str(String[] zones_str) {
        this.zones_str = zones_str;
    }

    public EtatEngin getIdEtat() {
        return idEtat;
    }

    public void setIdEtat(EtatEngin idEtat) {
        this.idEtat = idEtat;
    }

    public PointageEngin() {
    }

    public PointageEngin(Integer idPoinEng) {
        this.idPoinEng = idPoinEng;
    }

    public Integer getIdPoinEng() {
        return idPoinEng;
    }

    public void setIdPoinEng(Integer idPoinEng) {
        this.idPoinEng = idPoinEng;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getPointePar() {
        return pointePar;
    }

    public void setPointePar(String pointePar) {
        this.pointePar = pointePar;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    @XmlTransient
    public List<Zone> getZoneList() {
        return zoneList;
    }

    public void setZoneList(List<Zone> zoneList) {
        this.zoneList = zoneList;
    }

    public Lot getIdLot() {
        return idLot;
    }

    public void setIdLot(Lot idLot) {
        this.idLot = idLot;
    }

    public Engin getIDEngin() {
        return iDEngin;
    }

    public void setIDEngin(Engin iDEngin) {
        this.iDEngin = iDEngin;
    }

//    @XmlTransient
//    public List<Poee> getPoeeList() {
//        return poeeList;
//    }
//
//    public void setPoeeList(List<Poee> poeeList) {
//        this.poeeList = poeeList;
//    }
    public EtatEngin getEtatEngin() {
        return etatEngin;
    }

    public void setEtatEngin(EtatEngin etatEngin) {
        this.etatEngin = etatEngin;
    }

    public Engin getiDEngin() {
        return iDEngin;
    }

    public void setiDEngin(Engin iDEngin) {
        this.iDEngin = iDEngin;
    }

    public Float getNbrHeures() {
        return nbrHeures;
    }

    public void setNbrHeures(Float nbrHeures) {
        this.nbrHeures = nbrHeures;
    }

    public Float getNbrKm() {
        return nbrKm;
    }

    public void setNbrKm(Float nbrKm) {
        this.nbrKm = nbrKm;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPoinEng != null ? idPoinEng.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PointageEngin)) {
            return false;
        }

        if (this.getIDEngin().equals(((PointageEngin) object).getIDEngin())) {
            return true;
        }

        return false;
    }

    @Override
    public String toString() {
        return "PointageEngin{" + "idPoinEng=" + idPoinEng + ", code=" + code + ", dateCreation=" + dateCreation + ", designation=" + designation + ", pointePar=" + pointePar + ", idEtat=" + idEtat + ", reference=" + reference + ", idChantierAffinite=" + idChantierAffinite + ", LibLot=" + LibLot + ", user=" + user + ", idLot=" + idLot + ", iDEngin=" + iDEngin + ", chantierAffinite=" + chantierAffinite + ", chantierPointage=" + chantierPointage + ", nbrHeures=" + nbrHeures + ", nbrKm=" + nbrKm + '}';
    }

    public PointageEngin(Date dateCreation, Engin iDEngin) {
        this.dateCreation = dateCreation;
        this.iDEngin = iDEngin;
    }

}
