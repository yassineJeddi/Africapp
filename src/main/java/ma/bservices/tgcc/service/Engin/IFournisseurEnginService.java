/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.service.Engin;

import java.util.List;
import ma.bservices.tgcc.Entity.FournisseurEngin;

/**
 *
 * @author yassine
 */
public interface IFournisseurEnginService {
    public void addFournisseurEngin(FournisseurEngin e);
    public List<FournisseurEngin> allFournisseurEngin();
    
}
