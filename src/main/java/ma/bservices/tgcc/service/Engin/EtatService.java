/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.service.Engin;

import java.util.List;
import ma.bservices.beans.Etat;
import ma.bservices.tgcc.Entity.EtatEngin;

/**
 *
 * @author a.wattah
 */
public interface EtatService {
    
    public List<Etat> findAllEtat();
    /**
     * Etat Engin
     */
    public List<EtatEngin> findAllEtatEngin();
}
