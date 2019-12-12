/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.service.Engin;

import java.io.Serializable;
import java.util.List;
import ma.bservices.tgcc.Entity.ConducteurEngin;
import ma.bservices.tgcc.dao.engin.IConducteurEnginDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author yassine.jeddi
 */
@Service("conducteurEnginServiceImpl")
public class ConducteurEnginServiceImpl implements IConducteurEngin, Serializable {

    @Autowired
    IConducteurEnginDAO conducteurEnginDAO ;
    
    @Override
    public List<ConducteurEngin> allConducteurEngins() {
       return conducteurEnginDAO.allConducteurEngins();
    }

    @Override
    public ConducteurEngin conducteurEnginById(Integer iDConducteurEngin) {
        return conducteurEnginDAO.conducteurEnginById(iDConducteurEngin);
    }

    @Override
    public ConducteurEngin lastConducteurEnginByEngin(Integer id_engin) {
        return conducteurEnginDAO.lastConducteurEnginByEngin(id_engin);
    }

    @Override
    public void addConducteurEngin(ConducteurEngin c) {
        conducteurEnginDAO.addConducteurEngin(c);
    }

    @Override
    public void editConducteurEngin(ConducteurEngin c) {
        conducteurEnginDAO.editConducteurEngin(c);
    }

    @Override
    public void remouveConducteurEngin(ConducteurEngin c) {
        conducteurEnginDAO.remouveConducteurEngin(c);
    }

    public IConducteurEnginDAO getConducteurEnginDAO() {
        return conducteurEnginDAO;
    }

    public void setConducteurEnginDAO(IConducteurEnginDAO conducteurEnginDAO) {
        this.conducteurEnginDAO = conducteurEnginDAO;
    }
    
    
}
