/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import ma.bservices.beans.FichePointageLot;
import ma.bservices.dao.FichePLDAO;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author j.allali
 */
@Service("fichePLService")
public class FichePLServiceImpl implements FichePLService, Serializable {

    protected static Logger logger = Logger.getLogger(FichePLServiceImpl.class);
    @Autowired
    private FichePLDAO ficheplDAO;

    public FichePLDAO getFicheplDAO() {
        return ficheplDAO;
    }

    public void setFicheplDAO(FichePLDAO ficheplDAO) {
        this.ficheplDAO = ficheplDAO;
    }

    @Override
    public FichePointageLot saveFiche(FichePointageLot fiche) {
        return ficheplDAO.saveFiche(fiche);
    }

    @Override
    public List<FichePointageLot> findFicheByChefDateChantier(Integer IdChantier, Integer idChef, Date datePointage) {
        
        List<FichePointageLot> result = ficheplDAO.findFicheByChefDateChantier(IdChantier, idChef, datePointage);
        return result;
        
    }

    @Override
    public List<FichePointageLot> findAll() {
        return ficheplDAO.findAll();
    }

    @Override
    public FichePointageLot getByCode(Long code) {
        try {
            return (code != null) ? ficheplDAO.getById(code) : null;
        } catch (Exception e) {
            logger.error("Erreur de recuperation de FichePointageLot par code car : "+e.getMessage());
            return null;
        }
    }

    @Override
    public boolean update(FichePointageLot p) {
        return (p != null) ? ficheplDAO.updateFiche(p) : false;
    }

}
