/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import ma.bservices.beans.Fonction;
import ma.bservices.beans.Lot;
import ma.bservices.dao.LotDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author j.allali
 */
@Service("lotServiceEvol")
public class Evol_LotServiceImpl implements Evol_LotService, Serializable {

    @Autowired
    private LotDAO lotDAO;

    public LotDAO getLotDAO() {
        return lotDAO;
    }

    public void setLotDAO(LotDAO lotDAO) {
        this.lotDAO = lotDAO;
    }

    @Override
    public List<Lot> findAll() {
        return lotDAO.findAll();
    }

    @Override
    public void updateLot(Lot upLot) {
        this.lotDAO.updateLot(upLot);

    }

    @Override
    public Boolean deleteLot(int id) {
        this.lotDAO.deleteLot(id);
        return true;
    }

    @Override
    public Boolean insertLot(Lot insLot) {
        this.lotDAO.insertLot(insLot);
        return true;
    }

    @Override
    public List<Lot> findNotHaveFonction(Integer idFonction) {
        List<Lot> result = new ArrayList<>();
        List<Lot> lots = lotDAO.findAll();
        for (Lot l : lots) {
            List<Fonction> fonctions = l.getFonctions();
            boolean d = false;
            for (Fonction f : fonctions) {
                if (f.getId().equals(idFonction)) {
                    d = true;
                }
            }
            if (!d) {
                result.add(l);
            }
        }
        return result;
    }

    @Override
    public List<Lot> lotByFonction(Integer idFonction) {
        List<Lot> result = new ArrayList<>();
        for (Lot l : lotDAO.findAll()) {
            for (Fonction f : l.getFonctions()) {
                if (f.getId().equals(idFonction)) {
                    result.add(l);
                }
            }
        }
        return result;
    }

    @Override
    public Lot findIconByLabelLot(String label) {
        return lotDAO.findIconByLabelLot(label);
    }
}
