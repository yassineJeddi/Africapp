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
public class TraceUtilisateur implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "utilisateur")
    private String utilisateur;
    @Column(name = "dateAction") 
    private Date dateAction;
    @Column(name = "module")
    private String module;
    @Column(name = "action")
    private String action;
    @Column(name = "objet")
    private String objet;
    
    private Bon_Livraison_Citerne bon_Livraison_Citerne;
    
    
     public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(String utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Date getDateAction() {
        return dateAction;
    }

    public void setDateAction(Date dateAction) {
        this.dateAction = dateAction;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getObjet() {
        return objet;
    }

    public void setObjet(String objet) {
        this.objet = objet;
    }

    public Bon_Livraison_Citerne getBon_Livraison_Citerne() {
        return bon_Livraison_Citerne;
    }

    public void setBon_Livraison_Citerne(Bon_Livraison_Citerne bon_Livraison_Citerne) {
        this.bon_Livraison_Citerne = bon_Livraison_Citerne;
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
        if (!(object instanceof TraceUtilisateur)) {
            return false;
        }
        TraceUtilisateur other = (TraceUtilisateur) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TraceUtilisateur{" + "id=" + id + ", utilisateur=" + utilisateur + ", dateAction=" + dateAction + ", module=" + module + ", action=" + action + ", objet=" + objet + ", bon_Livraison_Citerne=" + bon_Livraison_Citerne + '}';
    }
 
    

     
    
}
