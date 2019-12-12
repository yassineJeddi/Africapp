/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.dao.Mensuel;

import java.util.List;
import ma.bservices.tgcc.Entity.Caisse;

/**
 *
 * @author yassine.jeddi
 */
public interface ICaisseDAO {
    
    public void addCaisse(Caisse c);
    public void editCaisse(Caisse c);
    public void remouuvCaisse(Caisse c);
    public List<Caisse> allCaisse();
    public Caisse caisseByID(Long idCaisse);
    
    
}
