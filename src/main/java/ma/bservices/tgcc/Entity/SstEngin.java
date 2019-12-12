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
 * @author yassine
 */
@Entity
@Table(name = "SSTENGIN")
public class SstEngin implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    
    @Column(name = "NOMSST")
    private String nomSST;

    public String getNomSST() {
        return nomSST;
    }

    public void setNomSST(String nomSST) {
        this.nomSST = nomSST;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "SstEngin{" + "id=" + id + ", nomSST=" + nomSST + '}';
    }
 
    
 

    
}
