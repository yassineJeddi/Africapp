/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.service.Engin;

import java.io.Serializable;
import java.util.List;
import ma.bservices.beans.Etat;
import ma.bservices.tgcc.Entity.EtatEngin;
import ma.bservices.tgcc.dao.engin.EtatDAO;
import ma.bservices.tgcc.dao.engin.EtatEnginDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author a.wattah
 */

@Service("etatService")
public class EtatServiceImpl implements EtatService ,Serializable{
    
      @Autowired
    private EtatDAO etatDAO;
      
      @Autowired
    private EtatEnginDAO etatEnginDAO;

    public EtatEnginDAO getEtatEnginDAO() {
        return etatEnginDAO;
    }

    public void setEtatEnginDAO(EtatEnginDAO etatEnginDAO) {
        this.etatEnginDAO = etatEnginDAO;
    }

    public EtatDAO getEtatDAO() {
        return etatDAO;
    }

    public void setEtatDAO(EtatDAO etatDAO) {
        this.etatDAO = etatDAO;
    }

    @Override
    public List<Etat> findAllEtat() {
        return etatDAO.findAllEtat();
    }

    @Override
    public List<EtatEngin> findAllEtatEngin() {
        return etatEnginDAO.findAllEtatEngin();
    }
    
}
