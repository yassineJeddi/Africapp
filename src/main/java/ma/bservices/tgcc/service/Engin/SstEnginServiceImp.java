/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.service.Engin;

import java.io.Serializable;
import java.util.List;
import ma.bservices.tgcc.Entity.SstEngin;
import ma.bservices.tgcc.dao.engin.ISstEnginDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author yassine
 */
@Service("sstEnginServiceImp")
public class SstEnginServiceImp implements ISstEnginService, Serializable {
    
    
    @Autowired
    ISstEnginDAO sstEnginDAO;

    public ISstEnginDAO getSstEnginDAO() {
        return sstEnginDAO;
    }

    public void setSstEnginDAO(ISstEnginDAO sstEnginDAO) {
        this.sstEnginDAO = sstEnginDAO;
    }

    
    @Override
    public void addSstEngin(SstEngin s) {
        sstEnginDAO.addSstEngin(s);
    }

    @Override
    public List<SstEngin> allSstEngin() {
        return sstEnginDAO.allSstEngin();
    }
}
