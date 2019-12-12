/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.service.Engin;

import java.util.List;
import ma.bservices.tgcc.Entity.FamilleEngin;

/**
 *
 * @author yassine.jeddi
 */
public interface IFamilleEnginService {
    
    public void addFamilleEngin(FamilleEngin f);
    public FamilleEngin familleEnginByID(Integer id);
    public List<FamilleEngin> allFamilleEngin();
}
