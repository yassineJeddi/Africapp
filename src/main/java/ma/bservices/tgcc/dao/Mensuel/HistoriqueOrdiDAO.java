/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.dao.Mensuel;

import java.util.Date;
import java.util.List;
import ma.bservices.tgcc.Entity.Historique_Ordinateur;

/**
 *
 * @author admin
 */

public interface HistoriqueOrdiDAO {
    public List<Historique_Ordinateur> findAll();
    public void addrecord(Historique_Ordinateur record);
    
    public List<Historique_Ordinateur> findByDateRange(Date from, Date to);
}
