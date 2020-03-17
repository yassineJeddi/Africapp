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
import javax.persistence.Table;
import ma.bservices.beans.Utilisateur;

/**
 *
 * @author yassine
 */
@Entity
@Table(name = "TRACECITERNE")
public class TraceCiterne implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @ManyToOne
    private Citerne citernSrc;
    @Column(name = "CITERNESRCLITRE")
    private Double citernSrcLitre;
    @ManyToOne
    private Citerne citernDist;
    @Column(name = "CITERNEDISTLITR")
    private Double citernDistLitre;
    @Column(name = "LITRETRANS")
    private Double litreTransf;
    @Column(name = "LITRERECEPTIONE")
    private Double litreReceptione;
    @Column(name = "DATETRANS")
    private Date dateTrans;
    @Column(name = "DATERECEP")
    private Date dateRecep;
    @Column(name = "DATEOPERATION")
    private Date dateOperation = new Date();
    @Column(name = "DATEOPERATIONRECEP")
    private Date dateOperationRecep = new Date();
    @Column(name = "NUMBON")
    private String numBon;
    @Column(name = "COMMENT")
    private String comment; 
    @Column(name = "VALIDE")
    private Boolean valide = Boolean.FALSE; 
    @Column(name = "FICHIER")
    private String fichier; 
    @ManyToOne
    private Utilisateur utilisateurExpedition;
    @ManyToOne
    private Utilisateur utilisateurReception;
    
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Citerne getCiternSrc() {
        return citernSrc;
    }

    public void setCiternSrc(Citerne citernSrc) {
        this.citernSrc = citernSrc;
    }

    public Double getCiternSrcLitre() {
        return citernSrcLitre;
    }

    public void setCiternSrcLitre(Double citernSrcLitre) {
        this.citernSrcLitre = citernSrcLitre;
    }

    public Citerne getCiternDist() {
        return citernDist;
    }

    public void setCiternDist(Citerne citernDist) {
        this.citernDist = citernDist;
    }

    public Double getCiternDistLitre() {
        return citernDistLitre;
    }

    public void setCiternDistLitre(Double citernDistLitre) {
        this.citernDistLitre = citernDistLitre;
    }

    public Double getLitreTransf() {
        return litreTransf;
    }

    public void setLitreTransf(Double litreTransf) {
        this.litreTransf = litreTransf;
    }

    public Date getDateTrans() {
        return dateTrans;
    }

    public void setDateTrans(Date dateTrans) {
        this.dateTrans = dateTrans;
    }

    public Date getDateOperation() {
        return dateOperation;
    }

    public void setDateOperation(Date dateOperation) {
        this.dateOperation = dateOperation;
    }

    public String getNumBon() {
        return numBon;
    }

    public void setNumBon(String numBon) {
        this.numBon = numBon;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Double getLitreReceptione() {
        return litreReceptione;
    }

    public void setLitreReceptione(Double litreReceptione) {
        this.litreReceptione = litreReceptione;
    }

    public Boolean getValide() {
        return valide;
    }

    public void setValide(Boolean valide) {
        this.valide = valide;
    }

    public Utilisateur getUtilisateurExpedition() {
        return utilisateurExpedition;
    }

    public void setUtilisateurExpedition(Utilisateur utilisateurExpedition) {
        this.utilisateurExpedition = utilisateurExpedition;
    }

    public Utilisateur getUtilisateurReception() {
        return utilisateurReception;
    }

    public void setUtilisateurReception(Utilisateur utilisateurReception) {
        this.utilisateurReception = utilisateurReception;
    }

    public Date getDateRecep() {
        return dateRecep;
    }

    public void setDateRecep(Date dateRecep) {
        this.dateRecep = dateRecep;
    }

    public Date getDateOperationRecep() {
        return dateOperationRecep;
    }

    public void setDateOperationRecep(Date dateOperationRecep) {
        this.dateOperationRecep = dateOperationRecep;
    }

    public String getFichier() {
        return fichier;
    }

    public void setFichier(String fichier) {
        this.fichier = fichier;
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
        if (!(object instanceof TraceCiterne)) {
            return false;
        }
        TraceCiterne other = (TraceCiterne) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    
    @Override
    public String toString() {
        return "TraceCiterne{" + "id=" + id + ", citernSrc=" + citernSrc.getId() + ", citernSrcLitre=" + citernSrcLitre + ", citernDist=" + citernDist.getId() + ", citernDistLitre=" + citernDistLitre + ", litreTransf=" + litreTransf + ", litreReceptione=" + litreReceptione + ", dateTrans=" + dateTrans + ", dateOperation=" + dateOperation + ", numBon=" + numBon + ", comment=" + comment + ", valide=" + valide + ", utilisateurExpedition=" + utilisateurExpedition + ", utilisateurReception=" + utilisateurReception + '}';
    }
 

    
    
}
