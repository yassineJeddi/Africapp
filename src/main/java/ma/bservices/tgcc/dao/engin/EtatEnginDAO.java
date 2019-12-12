/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.dao.engin;

import java.util.List;
import ma.bservices.tgcc.Entity.EtatEngin;

/**
 *
 * @author a.wattah
 */
public interface EtatEnginDAO {

    public EtatEngin findOneByLabel(String label);
    /**
     * Test unit
     */
    public Boolean ajouterEtatE(EtatEngin ee);
    public Boolean deleteEtatE(EtatEngin ee);
    
    public List<EtatEngin> findAllEtatEngin();
}
