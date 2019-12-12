/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.service.Engin;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import ma.bservices.tgcc.Entity.CompteurrEngin;
import ma.bservices.tgcc.dao.engin.ICompteurEnginDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author yassine
 */
@Service("compteurEnginServiceImp")
public class CompteurEnginServiceImp implements ICompteurEnginService, Serializable{

    @Autowired
    ICompteurEnginDAO compteurEnginDAO ;

    public ICompteurEnginDAO getCompteurEnginDAO() {
        return compteurEnginDAO;
    }

    public void setCompteurEnginDAO(ICompteurEnginDAO compteurEnginDAO) {
        this.compteurEnginDAO = compteurEnginDAO;
    }
    
    @Override
    public List<CompteurrEngin> allCompteurrEngin() {
        return compteurEnginDAO.allCompteurrEngin();
    }

    @Override
    public List<CompteurrEngin> allCompteurrEnginByIdEngin(Integer id_engin) {
        return compteurEnginDAO.allCompteurrEnginByIdEngin(id_engin);
    }

    @Override
    public CompteurrEngin findCompteurrEnginById(Integer id) {
        return compteurEnginDAO.findCompteurrEnginById(id);
    }

    @Override
    public void saveCompteurrEngin(CompteurrEngin c) {
        compteurEnginDAO.saveCompteurrEngin(c);
    }

    @Override
    public void editCompteurrEngin(CompteurrEngin c) {
        compteurEnginDAO.editCompteurrEngin(c);
    }

    @Override
    public void remouveCompteurrEngin(CompteurrEngin c) {
        compteurEnginDAO.remouveCompteurrEngin(c);
    }

    @Override
    public CompteurrEngin findCompteurrEnginByDate(Date d) {
        return compteurEnginDAO.findCompteurrEnginByDate(d);
    }
    
}
