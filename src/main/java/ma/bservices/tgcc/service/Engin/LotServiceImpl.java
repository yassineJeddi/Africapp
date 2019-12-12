/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.service.Engin;

import java.io.Serializable;
import java.util.List;
import ma.bservices.beans.Lot;
import ma.bservices.tgcc.dao.engin.LotDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author j.allali
 */
@Service("lotService")
public class LotServiceImpl implements LotService, Serializable {

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
    public Lot findByLib(String lib) {
        return lotDAO.findOneByLabel(lib);
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
    public Lot findByIb(int id) {
        return lotDAO.findById(id);
    }
}
