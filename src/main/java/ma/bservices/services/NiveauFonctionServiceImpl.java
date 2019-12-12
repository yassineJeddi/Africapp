/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.services;

import java.io.Serializable;
import java.util.List;
import ma.bservices.beans.NiveauFonction;
import ma.bservices.dao.NiveauFonctionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author admin
 */
@Service("niveauFonctionService")
public class NiveauFonctionServiceImpl implements Serializable, NiveauFonctionService {

    @Autowired
    NiveauFonctionDao niveauFonctionDao;

    public NiveauFonctionDao getNiveauFonctionDao() {
        return niveauFonctionDao;
    }

    public void setNiveauFonctionDao(NiveauFonctionDao niveauFonctionDao) {
        this.niveauFonctionDao = niveauFonctionDao;
    }

    @Override
    public List<NiveauFonction> getAll() {
        return niveauFonctionDao.findAll();
    }

    @Override
    public NiveauFonction getById(Integer id) {
        if (id != null && id > 0) {
            return niveauFonctionDao.findById(id);
        }
        throw new UnsupportedOperationException("Veuillez Saisir un identifiant valide");
    }
    
      @Override
    public NiveauFonction getByString(String lib) {
       
            return niveauFonctionDao.findByName(lib);
  
    }

    @Override
    public boolean ajouter(NiveauFonction nf) {
        return (nf != null) ? niveauFonctionDao.addNiveau(nf) : false;
    }

    @Override
    public boolean modifier(NiveauFonction nf) {
        return (nf != null) ? niveauFonctionDao.updateNiveau(nf) : false;
    }

    @Override
    public boolean supprimer(NiveauFonction nf) {
        return (nf != null) ? niveauFonctionDao.deleteNiveau(nf) : false;
    }

    @Override
    public List<NiveauFonction> findByPriority(Integer base_priority) {
      return niveauFonctionDao.findByPriority(base_priority);
    }

}
