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
@Table(name = "DESIGNIATIONENGIN")
public class DesigniationEngin implements Serializable {
    
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "DESIGNIATION")
    private String designiation;
    
    @Column(name = "TYPE_COMPTEUR")
    private String typeCompteur;
    
    @Column(name = "CODE_DESIGNIATION")
    private String codeDesigniation;
    
    @Column(name = "TYPE_ENGIN")
    private String typeEngin;
    
    
}
