/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import ma.bservices.tgcc.Entity.Organigrame;
import ma.bservices.tgcc.Entity.SalariesNiveau;

/**
 *
 * @author mahdi
 */
@Entity
@Table(name = "niveauFonction")
public class NiveauFonction implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "niveau")
    private String niveau;
    
    @Column(name="priorite")
    private Integer priorite;
   
    @OneToMany(mappedBy = "niveauFonction", 
            cascade = CascadeType.ALL, 
            fetch = FetchType.LAZY)
    private List<FonctionLocal> fls;
    
    @OneToOne
    private Organigrame niveauOrganigram;
    
   
    
     

    public NiveauFonction() {
        this.fls = new ArrayList<>();
    }

    public Organigrame getNiveauOrganigram() {
        return niveauOrganigram;
    }

    public void setNiveauOrganigram(Organigrame niveauOrganigram) {
        this.niveauOrganigram = niveauOrganigram;
    }
    

    public Integer getId() {
        return id;
    }

    public Integer getPriorite() {
        return priorite;
    }

    public void setPriorite(Integer priorite) {
        this.priorite = priorite;
    }
    
    
    

    
    

    public void setId(Integer id) {
        this.id = id;
    }

  
    

    public List<FonctionLocal> getFls() {
        return fls;
    }

    public void setFls(List<FonctionLocal> fls) {
        this.fls = fls;
    }

    public String getNiveau() {
        return niveau;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
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
        final NiveauFonction other = (NiveauFonction) obj;
        if (!Objects.equals(this.niveau, other.niveau)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
    
    
}
