/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.service.Engin;

import java.util.List;
import ma.bservices.beans.Lot;

/**
 *
 * @author j.allali
 */
public interface LotService {
    public List<Lot> findAll();
    public Lot findByIb(int id);
    public Lot findByLib(String lib);
    public void updateLot(Lot upLot);
    public Boolean deleteLot(int id);
    public Boolean insertLot(Lot insLot);
}
