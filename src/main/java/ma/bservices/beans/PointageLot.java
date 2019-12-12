/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.beans;

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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author Mahdi
 */
@Table
@Entity
public class PointageLot implements Serializable, Comparable<PointageLot> {

    @Id
    @Column(name = "ID_POINTAGE")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date datePointage;

    @ManyToOne
    private Salarie salarie;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(joinColumns = {
        @JoinColumn(name = "ID_POINTAGE")}, inverseJoinColumns = {
        @JoinColumn(name = "ID")})
    private List<Lot> lots;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(joinColumns = {
        @JoinColumn(name = "ID_POINTAGE")}, inverseJoinColumns = {
        @JoinColumn(name = "ID_ZONE")})
    private List<Zone> zones;

    @ManyToOne()
    private FichePointageLot fiche;

    public FichePointageLot getFiche() {
        return fiche;
    }

    public void setFiche(FichePointageLot fiche) {
        this.fiche = fiche;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDatePointage() {
        return datePointage;
    }

    public void setDatePointage(Date datePointage) {
        this.datePointage = datePointage;
    }

    public Salarie getSalarie() {
        return salarie;
    }

    public void setSalarie(Salarie salarie) {
        this.salarie = salarie;
    }

    public List<Lot> getLots() {
        return lots;
    }

    public void setLots(List<Lot> lots) {
        this.lots = lots;
    }

    public List<Zone> getZones() {
        return zones;
    }

    public void setZones(List<Zone> zones) {
        this.zones = zones;
    }

    @Override
    public int compareTo(PointageLot o) {
        return this.getDatePointage().compareTo(o.getDatePointage());
    }

    @Override
    public String toString() {
        return "PointageLot{" + "id=" + id + ", datePointage=" + datePointage + ", salarie=" + salarie + '}';
    }

}
