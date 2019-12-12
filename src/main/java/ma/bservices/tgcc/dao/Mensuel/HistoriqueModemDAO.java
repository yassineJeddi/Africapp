/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.dao.Mensuel;

import java.util.Date;
import java.util.List;
import ma.bservices.tgcc.Entity.Historique_Modem3G;

/**
 *
 * @author admin
 */
public interface HistoriqueModemDAO {
    public List<Historique_Modem3G> findAll();
    public void addrecord(Historique_Modem3G record);
     public List<Historique_Modem3G> findByDateRange(Date from, Date to);
}
