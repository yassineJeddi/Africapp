/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.service.Mensuel;

import java.io.Serializable;
import java.util.List;
import ma.bservices.tgcc.Entity.BonCaisse;
import ma.bservices.tgcc.Entity.LoyerSalarie;
import ma.bservices.tgcc.dao.Mensuel.BonCaisseDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author j.allali
 */
@Service("boncaisseservice")
public class BonCaisseServiceImpl implements BonCaisseService, Serializable{

    @Autowired
    private BonCaisseDAO boncaisseDAO;

    public BonCaisseDAO getBoncaisseDAO() {
        return boncaisseDAO;
    }

    public void setBoncaisseDAO(BonCaisseDAO boncaisseDAO) {
        this.boncaisseDAO = boncaisseDAO;
    }

   

    
    @Override
    public void saveBonCaisse(BonCaisse BC) {
        boncaisseDAO.saveBonCaisse(BC);
    }

    @Override
    public void updateBonCaisse(BonCaisse BC) {
        boncaisseDAO.updateBonCaisse(BC);
    }

    @Override
    public BonCaisse getbcById(Long id) {
        return boncaisseDAO.getbcById(id);
    }

    @Override
    public LoyerSalarie AfficherListMensuelByidLoyer(Long id) {
        return boncaisseDAO.AfficherListMensuelByidLoyer(id);
    }

    @Override
    public List<BonCaisse> findAll() {
        return this.boncaisseDAO.findAll();
    }

    @Override
    public List<BonCaisse> getbcByIdLoyerSalarie(Long id) {
        return this.boncaisseDAO.getbcByIdLoyerSalarie(id);
    }

    @Override
    public void removeBCChantierByLC(BonCaisse BC) {
        this.boncaisseDAO.removeBCChantierByLC(BC);
    }

    @Override
    public BonCaisse getbcByIdLoyerChantier(Long id) {
        return this.boncaisseDAO.getbcByIdLoyerChantier(id);
    }

    @Override
    public List<BonCaisse> getbcByIdLoyerChan(Long id) {
        return this.boncaisseDAO.getbcByIdLoyerChan(id);
    }
    
    @Override
    public BonCaisse getbcByIdBonCaisse(Long id) {
        return this.boncaisseDAO.getbcByIdBonCaisse(id);
    }
    
}
