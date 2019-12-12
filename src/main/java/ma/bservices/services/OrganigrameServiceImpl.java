/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.services;

import java.io.Serializable;
import java.util.List;
import ma.bservices.beans.Chantier;
import ma.bservices.beans.NiveauFonction;
import ma.bservices.beans.Salarie;
import ma.bservices.dao.OrganigrameDAO;
import ma.bservices.tgcc.Entity.Organigrame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author airaamane
 */
@Service("organigrameService")
public class OrganigrameServiceImpl implements Serializable, OrganigrameService {

    @Autowired
    OrganigrameDAO organigrameDAO;

    public OrganigrameDAO getOrgDAO() {
        return organigrameDAO;
    }

    public void setOrganigrameDAO(OrganigrameDAO organigrameDAO) {
        this.organigrameDAO = organigrameDAO;
    }

    @Override
    public List<Organigrame> findByChantier(Chantier chantier) {
        return organigrameDAO.findByChantier(chantier);
    }
    
     @Override
    public List<Organigrame> findByChantierChef(Chantier chantier) {
        return organigrameDAO.findByChantierChef(chantier);
    }

    @Override
    public void save(Organigrame org) {
        organigrameDAO.save(org);
    }

    @Override
    public void update(Organigrame org) {
        organigrameDAO.update(org);
    }
    
     @Override
    public void delete(Organigrame org) {
        organigrameDAO.delete(org);
    }

    @Override
    public List<Integer> findByChantierNiveau(Chantier chantier, NiveauFonction niveau) {
        return organigrameDAO.findByChantierNiveau(chantier, niveau);
    }
    
       @Override
    public List<Organigrame> findOrgsByChantierNiveau(Chantier chantier, NiveauFonction niveau) {
        return organigrameDAO.findOrgsByChantierNiveau(chantier, niveau);
    }
    
      @Override
    public boolean deleteOrgsByChantierNiveau(Chantier chantier, NiveauFonction niveau) {
        return organigrameDAO.deleteOrgsByChantierNiveau(chantier, niveau);
    }
    
     @Override
    public List<Organigrame> findByChantierNiveauChef(Chantier chantier, NiveauFonction niveau) {
        return organigrameDAO.findByChantierNiveauChef(chantier, niveau);
    }

    @Override
    public Organigrame findByChantierNiveauSalarie(Chantier chantier, NiveauFonction niveau, Salarie salarie) {
        return organigrameDAO.findByChantierNiveauSalarie(chantier, niveau, salarie);
    }
    
    @Override
    public List<Organigrame> findDistinctNiveauByChantier(Chantier chantier) {
        return organigrameDAO.findDistinctNiveauByChantier(chantier);
    }

}
