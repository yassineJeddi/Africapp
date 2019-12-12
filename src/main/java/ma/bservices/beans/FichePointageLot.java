/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author j.allali
 */
@Entity
@Table(name = "FICHEPL")
public class FichePointageLot implements Serializable, Comparable<FichePointageLot> {

    @Id
    @Column(name = "FICHE_ID")
    //@GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @Column(name = "DatePointage")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date _date;

    @Column(name = "CHEMIN")
    private String chemin;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Salarie> salaries = new ArrayList<>();

    @ManyToOne
    private Chantier chantier;

    @ManyToOne
    private Salarie salarieSupp;

    @OneToMany(mappedBy = "fiche")
    private List<PointageLot> pointage;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<PointageLot> getPointage() {
        return pointage;
    }

    public void setPointage(List<PointageLot> pointage) {
        this.pointage = pointage;
    }

    public Date getDate() {
        return _date;
    }

    public void setDate(Date date) {
        this._date = date;
    }

    public String getChemin() {
        return chemin;
    }

    public void setChemin(String chemin) {
        this.chemin = chemin;
    }

    public List<Salarie> getSalaries() {
        return salaries;
    }

    public void setSalaries(List<Salarie> salaries) {
        this.salaries = salaries;
    }

    public Chantier getChantier() {
        return chantier;
    }

    public void setChantier(Chantier chantier) {
        this.chantier = chantier;
    }

    public Salarie getSalarieSupp() {
        return salarieSupp;
    }

    public void setSalarieSupp(Salarie salarieSupp) {
        this.salarieSupp = salarieSupp;
    }

    @Override
    public int compareTo(FichePointageLot o) {
        return this.getDate().compareTo(o.getDate());
    }

    @Override
    public String toString() {
        return "FichePointageLot{" + "id=" + id + ", _date=" + _date + ", chemin=" + chemin + ", chantier=" + chantier + '}';
    }

}
