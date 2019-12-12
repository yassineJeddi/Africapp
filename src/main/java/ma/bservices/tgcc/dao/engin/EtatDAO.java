/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.dao.engin;

import java.util.List;
import ma.bservices.beans.Etat;

/**
 *
 * @author a.wattah
 */
public interface EtatDAO {

    public List<Etat> findAllEtat();

    public Etat findOneByLabel(String label);
    
    /**
     * Test Unitaire
     */
    public void ajouterEngin(Etat etat);
    public void deleteEtat(Etat etat); 
}
