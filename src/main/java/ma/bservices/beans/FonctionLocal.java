/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.beans;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 *
 * @author Mahdi
 */
@Entity
@Table
public class FonctionLocal implements Serializable {

    @Id
    @PrimaryKeyJoinColumn
    private Integer id;

    @OneToOne(fetch = FetchType.EAGER)
    private Fonction fonction;

    @ManyToOne(fetch = FetchType.EAGER)
    private NiveauFonction niveauFonction;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Fonction getFonction() {
        return fonction;
    }

    public void setFonction(Fonction fonction) {
        this.fonction = fonction;
    }

    public NiveauFonction getNiveauFonction() {
        return niveauFonction;
    }

    public void setNiveauFonction(NiveauFonction niveauFonction) {
        this.niveauFonction = niveauFonction;
    }

}
