/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import ma.bservices.dao.VisiteDAO;
import ma.bservices.tgcc.Entity.DossierMedical;
import ma.bservices.tgcc.Entity.Visite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author BARAKA
 */
@Service("visiteService")
@Transactional
public class VisiteServiceIplm implements VisiteService, Serializable{
    
    @Autowired
    VisiteDAO visitedao;
    
    @Override
    public Long addVisite(Visite visite) {
        return visitedao.addVisite(visite);
    }

    @Override
    public Long updateVisite(Visite visite) {
        return visitedao.updateVisite(visite);
    }

    @Override
    public boolean deleteVisite(Visite visite) {
        return visitedao.deleteVisite(visite);
    }

    @Override
    public List<Visite> findAllVisite() {
        return visitedao.findAllVisite();
    }

    @Override
    public List<Visite> findVisiteByType(String type) {
        return visitedao.findVisiteByType(type);
    }

    @Override
    public List<Visite> findVisitesByDossierMedical(DossierMedical dos) {
        return visitedao.findVisitesByDossierMedical(dos);
    }

    @Override
    public Date lastDateVisiteByDossier(DossierMedical dos) {
        return visitedao.lastDateVisiteByDossier(dos);
    }

    @Override
    public String docteurLastVisiteByDossier(DossierMedical dos) {
        return visitedao.docteurLastVisiteByDossier(dos);
    }
    
}
