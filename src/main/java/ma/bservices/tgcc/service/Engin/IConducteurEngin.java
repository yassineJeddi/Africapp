/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.service.Engin;

import java.util.List;
import ma.bservices.tgcc.Entity.ConducteurEngin;

/**
 *
 * @author yassine.jeddi
 */

public interface IConducteurEngin {
    
    public List<ConducteurEngin> allConducteurEngins ();
    public ConducteurEngin conducteurEnginById(Integer iDConducteurEngin);
    public ConducteurEngin lastConducteurEnginByEngin(Integer id_engin);
    public void addConducteurEngin(ConducteurEngin c);
    public void editConducteurEngin(ConducteurEngin c);
    public void remouveConducteurEngin(ConducteurEngin c);
}
