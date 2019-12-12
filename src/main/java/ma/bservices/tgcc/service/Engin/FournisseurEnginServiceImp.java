/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.service.Engin;

import java.io.Serializable;
import java.util.List;
import ma.bservices.tgcc.Entity.FournisseurEngin;
import ma.bservices.tgcc.dao.engin.IFournisseurEnginDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author yassine
 */

@Service("fournisseurEnginServiceImp")
public class FournisseurEnginServiceImp implements IFournisseurEnginService, Serializable{

    @Autowired
    private IFournisseurEnginDAO fournisseurEnginDAO;

    public IFournisseurEnginDAO getFournisseurEnginDAO() {
        return fournisseurEnginDAO;
    }

    public void setFournisseurEnginDAO(IFournisseurEnginDAO fournisseurEnginDAO) {
        this.fournisseurEnginDAO = fournisseurEnginDAO;
    }
    
    @Override
    public void addFournisseurEngin(FournisseurEngin e) {
        fournisseurEnginDAO.addFournisseurEngin(e);
    }

    @Override
    public List<FournisseurEngin> allFournisseurEngin() {
        return fournisseurEnginDAO.allFournisseurEngin();
    }
    
}
