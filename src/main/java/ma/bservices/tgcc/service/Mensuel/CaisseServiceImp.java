/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.service.Mensuel;

import java.io.Serializable;
import java.util.List;
import ma.bservices.tgcc.Entity.Caisse;
import ma.bservices.tgcc.dao.Mensuel.ICaisseDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author yassine.jeddi
 */
@Service("caisseService")
public class CaisseServiceImp implements Serializable,ICaisseService{

    @Autowired
    ICaisseDAO caisseDAO;

    public ICaisseDAO getCaisseDAO() {
        return caisseDAO;
    }

    public void setCaisseDAO(ICaisseDAO caisseDAO) {
        this.caisseDAO = caisseDAO;
    }
    
    
    @Override
    public void addCaisse(Caisse c) {
        caisseDAO.addCaisse(c);
    }

    @Override
    public void editCaisse(Caisse c) {
        caisseDAO.editCaisse(c);
    }

    @Override
    public void remouuvCaisse(Caisse c) {
        caisseDAO.remouuvCaisse(c);
    }

    @Override
    public List<Caisse> allCaisse() {
        return caisseDAO.allCaisse();
    }

    @Override
    public Caisse caisseByID(Long idCaisse) {
        return caisseDAO.caisseByID(idCaisse);
    }
    
}
