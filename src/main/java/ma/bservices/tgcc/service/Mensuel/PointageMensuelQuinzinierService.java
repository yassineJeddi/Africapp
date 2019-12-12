/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.service.Mensuel;

import ma.bservices.beans.Chantier;
import ma.bservices.tgcc.Entity.PointageMensuelQuinzinier;

/**
 *
 * @author a.wattah
 */
public interface PointageMensuelQuinzinierService {
    public Boolean save(PointageMensuelQuinzinier mensuelQuinzinier);
    public Chantier getChantierByLib(String lib);
    public Boolean delete(PointageMensuelQuinzinier pointageMensuelQuinzinier);
    
}
