/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.dao.engin;

import java.util.List;
import ma.bservices.beans.Lot;

/**
 *
 * @author j.allali
 */
public interface LotDAO {
    
    public List<Lot> findAll();
    public Lot findById(int id);
    public Lot findOneByLabel(String label);
    public Boolean updateLot(Lot upLot);
    public Boolean deleteLot(int id);
    public Boolean deleteLot(Lot l);
    public Boolean insertLot(Lot insLot);
}
