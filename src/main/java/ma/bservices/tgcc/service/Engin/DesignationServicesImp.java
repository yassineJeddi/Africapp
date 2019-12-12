/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.service.Engin;

import java.util.List;
import ma.bservices.beans.Designation;
import ma.bservices.tgcc.dao.engin.IDesignationDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author yassine.jeddi
 */
@Service("designiationService")
public class DesignationServicesImp implements IDesignationServices {

    @Autowired
    IDesignationDAO designiationDAO;

    public IDesignationDAO getDesigniationDAO() {
        return designiationDAO;
    }

    public void setDesigniationDAO(IDesignationDAO designiationDAO) {
        this.designiationDAO = designiationDAO;
    }
    
    
    
    @Override
    public void addDesignation(Designation d) {
        designiationDAO.addDesignation(d);
    }

    @Override
    public List<Designation> allDesignation() {
        return designiationDAO.allDesignation();
    }

    @Override
    public Designation designationById(Integer id) {
        return designiationDAO.designationById(id);
    }

    @Override
    public List<Designation> allDesignationByFamille(String famille) {
        return designiationDAO.allDesignationByFamille(famille);
    }
    
}
