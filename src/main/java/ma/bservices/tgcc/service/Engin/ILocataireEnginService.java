/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.service.Engin;

import java.util.List;
import ma.bservices.tgcc.Entity.LocataireEngin;

/**
 *
 * @author yassine
 */
public interface ILocataireEnginService {
    
    public void addLocatEngin(LocataireEngin s);
    public List<LocataireEngin> allLocatEngin();
    
}
