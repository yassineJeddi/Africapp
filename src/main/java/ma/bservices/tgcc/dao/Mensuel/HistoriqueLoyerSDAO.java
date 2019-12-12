/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.dao.Mensuel;

import java.util.Date;
import java.util.List;
import ma.bservices.tgcc.Entity.Historique_LoyerS;
import ma.bservices.tgcc.Entity.Historique_Modem3G;

/**
 *
 * @author j.allali
 */
public interface HistoriqueLoyerSDAO {
     public List<Historique_LoyerS> findAll();
     public void addrecord(Historique_LoyerS record);
     public List<Historique_LoyerS> findByDateRange(Date from, Date to);
}
