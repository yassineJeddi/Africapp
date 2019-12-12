/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.dao.Mensuel;

import java.util.Date;
import java.util.List;
import ma.bservices.tgcc.Entity.Historique_CG;
import ma.bservices.tgcc.Entity.Historique_Telephone;

/**
 *
 * @author admin
 */
public interface HistoriqueTelephoneDAO {
    public List<Historique_Telephone> findAll();
    public void addrecord(Historique_Telephone record);
    public List<Historique_Telephone> findByDateRange(Date dateDebut, Date dateFin);
}
