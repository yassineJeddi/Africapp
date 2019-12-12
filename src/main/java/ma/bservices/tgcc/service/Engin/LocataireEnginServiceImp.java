/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.service.Engin;

import java.io.Serializable;
import java.util.List;
import ma.bservices.tgcc.Entity.LocataireEngin;
import ma.bservices.tgcc.dao.engin.ILocataireEnginDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author yassine
 */
@Service("locataireEnginService")
public class LocataireEnginServiceImp implements ILocataireEnginService, Serializable {

    
    @Autowired
    ILocataireEnginDAO locatairEnginDao;

    public ILocataireEnginDAO getLocatairEnginDao() {
        return locatairEnginDao;
    }

    public void setLocatairEnginDao(ILocataireEnginDAO locatairEnginDao) {
        this.locatairEnginDao = locatairEnginDao;
    }
     
    @Override
    public void addLocatEngin(LocataireEngin s) {
        locatairEnginDao.addSstEngin(s);
    }

    @Override
    public List<LocataireEngin> allLocatEngin() {
        return  locatairEnginDao.allSstEngin();
    }
    
}
