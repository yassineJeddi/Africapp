/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.service.Engin;

import java.io.Serializable;
import java.util.List;
import ma.bservices.tgcc.Entity.FamilleEngin;
import ma.bservices.tgcc.dao.engin.IFamilleEnginDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author yassine.jeddi
 */
@Service("familleEnginServiceImp")
public class FamilleEnginServiceImp implements IFamilleEnginService, Serializable{

      @Autowired
    private IFamilleEnginDAO familleEnginDAO ;

    public IFamilleEnginDAO getFamilleEnginDAO() {
        return familleEnginDAO;
    }

    public void setFamilleEnginDAO(IFamilleEnginDAO familleEnginDAO) {
        this.familleEnginDAO = familleEnginDAO;
    }
    
      
    @Override
    public void addFamilleEngin(FamilleEngin f) {
        familleEnginDAO.addFamilleEngin(f);
    }

    @Override
    public List<FamilleEngin> allFamilleEngin() {
        return familleEnginDAO.allFamilleEngin();
    }

    @Override
    public FamilleEngin familleEnginByID(Integer id) {
        return familleEnginDAO.familleEnginByID(id);
    }
    
}
