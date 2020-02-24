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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import ma.bservices.beans.Article;
import ma.bservices.beans.EtatDA;
import ma.bservices.beans.Utilisateur;

/**
 *
 * @author admin
 */
@Entity
@Table(name = "DemandeDetail")
@XmlRootElement
public class DemandeDetail implements Serializable {
    
    @Id
    @Column(name="ID")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;
    
    @Column(name = "REF")
    private String refArticle;
        
    @ManyToOne
    @JoinColumn(name="Id_Demande", referencedColumnName="ID")
    private DemandeEntete demandeEntete;
    
    @ManyToOne
    private Article artcile;
    
    @Column(name="Longeur")
    private float longeur;
    
    @Column(name="Largeur")
    private float largeur;
    
    @Column(name="Hauteur")
    private float hauteur;
    
    @Column(name="Autre")
    private String autre;
    
    @Column(name="Unite")
    private String unite; 
    
    @Column(name="Qte")
    private Float qte;
  
    @Column(name="Date_Souhaite")
    private Date dateSouhaite;
    
    @Column(name="Observation")
    private String observation;
    
    @Column(name="Designation_Demandeur")
    private String designationDemandeur;
    
    @Column(name="File_Import")
    private String fileImport;
     
    @Column(name="Quantite_article")
    private Float quantiteArticle;
        
    @Column(name="Quantite_prepare")
    private Float quantitePrepare;
    
    @ManyToOne
    private EtatDA etatDA;

    @Column(name="Date_Max_utilisation")
    private Date dateMaxUtilisation;
        
    @ManyToOne
    private Engin engin;
        
    public long getId() {
        return id;
    }

    public DemandeEntete getDemandeEntete() {
        return demandeEntete;
    }

    public Article getArtcile() {
        return artcile;
    }

    public float getLongeur() {
        return longeur;
    }

    public float getLargeur() {
        return largeur;
    }

    public float getHauteur() {
        return hauteur;
    }



    public Float getQte() {
        return qte;
    }

    public Date getDateSouhaite() {
        return dateSouhaite;
    }

    public String getObservation() {
        return observation;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setDemandeEntete(DemandeEntete demandeEntete) {
        this.demandeEntete = demandeEntete;
    }

    public void setArtcile(Article artcile) {
        this.artcile = artcile;
    }

    public void setLongeur(float longeur) {
        this.longeur = longeur;
    }

    public void setLargeur(float largeur) {
        this.largeur = largeur;
    }

    public void setHauteur(float hauteur) {
        this.hauteur = hauteur;
    }




    public void setDateSouhaite(Date dateSouhaite) {
        this.dateSouhaite = dateSouhaite;
    }


    public void setObservation(String observation) {
        this.observation = observation;
    }

    public String getRefArticle() {
        return refArticle;
    }

    public void setRefArticle(String refArticle) {
        this.refArticle = refArticle;
    }

    public void setQte(Float qte) {
        this.qte = qte;
    }

    public String getDesignationDemandeur() {
        return designationDemandeur;
    }

    public void setDesignationDemandeur(String designationDemandeur) {
        this.designationDemandeur = designationDemandeur;
    }

    public String getFileImport() {
        return fileImport;
    }

    public void setFileImport(String fileImport) {
        this.fileImport = fileImport;
    }

    public Float getQuantiteArticle() {
        return quantiteArticle;
    }

    public void setQuantiteArticle(Float quantiteArticle) {
        this.quantiteArticle = quantiteArticle;
    }

    public Float getQuantitePrepare() {
        return quantitePrepare;
    }

    public void setQuantitePrepare(Float quantitePrepare) {
        this.quantitePrepare = quantitePrepare;
    }


    public String getUnite() {
        return unite;
    }

    public void setUnite(String unite) {
        this.unite = unite;
    }

    public EtatDA getEtatDA() {
        return etatDA;
    }

    public void setEtatDA(EtatDA etatDA) {
        this.etatDA = etatDA;
    }

    @Override
    public String toString() {
        return "DemandeDetail{" + "id=" + id + ", refArticle=" + refArticle + ", demandeEntete=" + demandeEntete + ", artcile=" + artcile + ", longeur=" + longeur + ", largeur=" + largeur + ", hauteur=" + hauteur + ", autre=" + autre + ", unite=" + unite + ", qte=" + qte + ", dateSouhaite=" + dateSouhaite + ", observation=" + observation + ", designationDemandeur=" + designationDemandeur + ", fileImport=" + fileImport + ", quantiteArticle=" + quantiteArticle + ", quantitePrepare=" + quantitePrepare + ", etatDA=" + etatDA + '}';
    }

    public String getAutre() {
        return autre;
    }

    public void setAutre(String autre) {
        this.autre = autre;
    }

    public Date getDateMaxUtilisation() {
        return dateMaxUtilisation;
    }

    public void setDateMaxUtilisation(Date dateMaxUtilisation) {
        this.dateMaxUtilisation = dateMaxUtilisation;
    }

    public Engin getEngin() {
        return engin;
    }

    public void setEngin(Engin engin) {
        this.engin = engin;
    }

  

    
    
    
    


}
