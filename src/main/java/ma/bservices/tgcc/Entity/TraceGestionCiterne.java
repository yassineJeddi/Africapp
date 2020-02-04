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

/**
 *
 * @author yassine
 */
@Entity
public class TraceGestionCiterne implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    
    @Column(name = "IDCITERNE")
    private String idCiterne;
    
    @Column(name = "LIBELLE_CITERNE")
    private String libelle_citerne;

    @Column(name = "TYPE_CITERNE")
    private String type_citerne;

    @Column(name = "CAPACITE")
    private Integer capacite;

    @Column(name = "LOCALITE")
    private String localite;

    @Column(name = "VOLUME_ACTUEL")
    private Double volume_actuel;
    
    @Column(name = "VOLUME_ACTUEL_D")
    private Double volume_actuel_;

    @Column(name = "ARCHIVER")
    private Boolean archiver;

    @Column(name = "CHANTIER")
    private String chantier_Principal;

    @Column(name = "DATE")
    private Date dateOperation;

    @Column(name = "ACTION")
    private String action;
    
    @Column(name = "UTILISATEUR")
    private String utilisateur;
    
    
    
    
    
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

    public String getLibelle_citerne() {
        return libelle_citerne;
    }

    public void setLibelle_citerne(String libelle_citerne) {
        this.libelle_citerne = libelle_citerne;
    }

    public String getType_citerne() {
        return type_citerne;
    }

    public void setType_citerne(String type_citerne) {
        this.type_citerne = type_citerne;
    }

    public Integer getCapacite() {
        return capacite;
    }

    public void setCapacite(Integer capacite) {
        this.capacite = capacite;
    }

    public String getLocalite() {
        return localite;
    }

    public void setLocalite(String localite) {
        this.localite = localite;
    }

    public Double getVolume_actuel() {
        return volume_actuel;
    }

    public void setVolume_actuel(Double volume_actuel) {
        this.volume_actuel = volume_actuel;
    }

    public Double getVolume_actuel_() {
        return volume_actuel_;
    }

    public void setVolume_actuel_(Double volume_actuel_) {
        this.volume_actuel_ = volume_actuel_;
    }

    public Boolean getArchiver() {
        return archiver;
    }

    public void setArchiver(Boolean archiver) {
        this.archiver = archiver;
    }

    public String getChantier_Principal() {
        return chantier_Principal;
    }

    public void setChantier_Principal(String chantier_Principal) {
        this.chantier_Principal = chantier_Principal;
    }

    public Date getDateOperation() {
        return dateOperation;
    }

    public void setDateOperation(Date dateOperation) {
        this.dateOperation = dateOperation;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(String utilisateur) {
        this.utilisateur = utilisateur;
    }

    public String getIdCiterne() {
        return idCiterne;
    }

    public void setIdCiterne(String idCiterne) {
        this.idCiterne = idCiterne;
    }

    
    
    
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TraceGestionCiterne)) {
            return false;
        }
        TraceGestionCiterne other = (TraceGestionCiterne) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ma.bservices.tgcc.Entity.TraceGestionCiterne[ id=" + id + " ]";
    }
    
}
