/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.beans;

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
public class CertificatAt implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    
    
    @Column(name = "CHEMIN")
    private String chemin;

    @ManyToOne
    private AccidentTravail at;

    @Column(name = "TITRE")
    private String titre;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "CREEPAR")
    private String creePar;
    
    @Column(name = "DATECREATION")
    private Date dateCreation;
    
    @Column(name = "TYPECERTIFICAT")
    private String typeCertificat;
    
    @Column(name = "NBJARRET")
    private Integer nbjArret;
    
    @Column(name = "RECUORIGINAL")
    private Boolean recuOriginal =Boolean.FALSE;
    
    @Column(name = "IPP")
    private Boolean ipp =Boolean.FALSE;
    @Column(name = "DATEREPRISE")
    private Date dateReprise ;
    @Column(name = "DATEGERISON")
    private Date dateGerison ;
    @Column(name = "tauxIpp")
    private Double tauxIpp =0.0;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChemin() {
        return chemin;
    }

    public void setChemin(String chemin) {
        this.chemin = chemin;
    }

    public AccidentTravail getAt() {
        return at;
    }

    public void setAt(AccidentTravail at) {
        this.at = at;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreePar() {
        return creePar;
    }

    public void setCreePar(String creePar) {
        this.creePar = creePar;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public String getTypeCertificat() {
        return typeCertificat;
    }

    public void setTypeCertificat(String typeCertificat) {
        this.typeCertificat = typeCertificat;
    }

    public Integer getNbjArret() {
        return nbjArret;
    }

    public void setNbjArret(Integer nbjArret) {
        this.nbjArret = nbjArret;
    }

    public Boolean getRecuOriginal() {
        return recuOriginal;
    }

    public void setRecuOriginal(Boolean recuOriginal) {
        this.recuOriginal = recuOriginal;
    }

    public Boolean getIpp() {
        return ipp;
    }

    public void setIpp(Boolean ipp) {
        this.ipp = ipp;
    }

    public Date getDateReprise() {
        return dateReprise;
    }

    public void setDateReprise(Date dateReprise) {
        this.dateReprise = dateReprise;
    }

    public Date getDateGerison() {
        return dateGerison;
    }

    public void setDateGerison(Date dateGerison) {
        this.dateGerison = dateGerison;
    }

    public Double getTauxIpp() {
        return tauxIpp;
    }

    public void setTauxIpp(Double tauxIpp) {
        this.tauxIpp = tauxIpp;
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
        if (!(object instanceof CertificatAt)) {
            return false;
        }
        CertificatAt other = (CertificatAt) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "CertificatAt{" + "id=" + id + ", chemin=" + chemin + ", at=" + at + ", titre=" + titre + ", description=" + description + ", creePar=" + creePar + ", dateCreation=" + dateCreation + ", typeCertificat=" + typeCertificat + ", nbjArret=" + nbjArret + ", recuOriginal=" + recuOriginal + '}';
    }

    
}
