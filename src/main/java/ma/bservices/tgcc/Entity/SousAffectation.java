/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.Entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import ma.bservices.beans.Chantier;

/**
 *
 * @author a.wattah
 */
@Entity
@Table(name = "SOUSAFFECTATION_FINANCIERE")
public class SousAffectation implements Serializable {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "POURCENTAGE")
    private Integer pourcentage;

    @ManyToOne
    private Chantier chantier;

    @ManyToOne
    private Affectation affectation;

    @Column(name = "ARCHIVE")
    private Boolean archived = Boolean.FALSE;

    @Column(name = "DATE_ARCHIVAGE")
    @Temporal(TemporalType.DATE)
    private Date dateArchivage;

    public SousAffectation() {
    }

    /**
     * Constructor
     *
     * @param pourcentage
     * @param chantier
     */
    public SousAffectation(Integer pourcentage, Chantier chantier) {
        this.pourcentage = pourcentage;
        this.chantier = chantier;
    }

    public SousAffectation(SousAffectation sa) {
        this.pourcentage = sa.pourcentage;
        this.chantier = sa.chantier;
    }

    /**
     * Getters and setters
     */
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPourcentage() {
        return pourcentage;
    }

    public void setPourcentage(Integer pourcentage) {
        this.pourcentage = pourcentage;
    }

    public Chantier getChantier() {
        return chantier;
    }

    public void setChantier(Chantier chantier) {
        this.chantier = chantier;
    }

    public Affectation getAffectation() {
        return affectation;
    }

    public void setAffectation(Affectation affectation) {
        this.affectation = affectation;
    }

    public Boolean getArchived() {
        return archived;
    }

    public void setArchived(Boolean archived) {
        this.archived = archived;
    }

    public Date getDateArchivage() {
        return dateArchivage;
    }

    public void setDateArchivage(Date dateArchivage) {
        this.dateArchivage = dateArchivage;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + Objects.hashCode(this.id);
        hash = 17 * hash + Objects.hashCode(this.pourcentage);
        hash = 17 * hash + Objects.hashCode(this.chantier);
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
        final SousAffectation other = (SousAffectation) obj;
        if (!Objects.equals(this.pourcentage, other.pourcentage)) {
            return false;
        }
        if (!Objects.equals(this.chantier, other.chantier)) {
            return false;
        }
        return true;
    }

    /**
     * Methods
     */
}
