/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.services;

import java.io.Serializable;
import java.util.List;
import ma.bservices.beans.Fonction;
import ma.bservices.beans.FonctionLocal;
import ma.bservices.dao.FonctionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Mahdi
 */
@Service("fonctionServiceEvol")
public class FonctionServiceImpl implements FonctionService, Serializable {

    @Autowired
    FonctionDao fonctionDao;

    public FonctionDao getFonctionDao() {
        return fonctionDao;
    }

    public void setFonctionDao(FonctionDao fonctionDao) {
        this.fonctionDao = fonctionDao;
    }

    @Override
    public boolean ajouterFonction(FonctionLocal f) {
        return (f != null) ? fonctionDao.Add(f) : false;
    }

    @Override
    public boolean modifierFonction(FonctionLocal f) {
        return (f != null) ? fonctionDao.update(f) : false;
    }

    @Override
    public boolean supprimerFonction(FonctionLocal f) {
        return (f != null) ? fonctionDao.delete(f) : false;
    }

    @Override
    public List<FonctionLocal> loadAll() {
        return fonctionDao.getAll();
    }

    @Override
    public List<Fonction> nonAffecter() {
        return fonctionDao.NotHaveNiveau();
    }

    @Override
    public FonctionLocal getById(Integer idFonction) {
        return (idFonction != null) ? fonctionDao.getById(idFonction) : null;
    }

}
