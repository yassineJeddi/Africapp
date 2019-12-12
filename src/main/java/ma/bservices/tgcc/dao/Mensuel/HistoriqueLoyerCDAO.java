/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.dao.Mensuel;

import java.util.Date;
import java.util.List;
import ma.bservices.tgcc.Entity.Historique_LoyerC;

/**
 *
 * @author j.allali
 */
public interface HistoriqueLoyerCDAO {
    
     public List<Historique_LoyerC> findAll();
     public void addrecord(Historique_LoyerC record);
     public List<Historique_LoyerC> findByDateRange(Date from, Date to);
    
}
