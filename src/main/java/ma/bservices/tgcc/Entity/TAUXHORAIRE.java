/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.Entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author yassine.jeddi
 */
@Entity
@Table(name = "TAUXHORAIRE")
public class TAUXHORAIRE implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "EMPLOICOD")
    String EMPLOICOD;
    
    @Column(name = "Etiquettes_de_lignes")
    String Etiquettes_de_lignes;
     
    @Column(name = "TAUX_HORAIRE")
    Float TAUX_HORAIRE;

    public TAUXHORAIRE() {
    }

    public TAUXHORAIRE(Long id, String EMPLOICOD, String Etiquettes_de_lignes, Float TAUX_HORAIRE) {
        this.id = id;
        this.EMPLOICOD = EMPLOICOD;
        this.Etiquettes_de_lignes = Etiquettes_de_lignes;
        this.TAUX_HORAIRE = TAUX_HORAIRE;
    }

    public Long getId() {
        return id;
    }

    public String getEMPLOICOD() {
        return EMPLOICOD;
    }

    public String getEtiquettes_de_lignes() {
        return Etiquettes_de_lignes;
    }

    public Float getTAUX_HORAIRE() {
        return TAUX_HORAIRE;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setEMPLOICOD(String EMPLOICOD) {
        this.EMPLOICOD = EMPLOICOD;
    }

    public void setEtiquettes_de_lignes(String Etiquettes_de_lignes) {
        this.Etiquettes_de_lignes = Etiquettes_de_lignes;
    }

    public void setTAUX_HORAIRE(Float TAUX_HORAIRE) {
        this.TAUX_HORAIRE = TAUX_HORAIRE;
    }
    
}
