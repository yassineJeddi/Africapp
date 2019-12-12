/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.dao.Mensuel;

import java.util.Date;
import java.util.List;
import ma.bservices.tgcc.Entity.Historique_Voiture;

/**
 *
 * @author admin
 */
public interface HistoriqueVoitureDAO {
    
    public List<Historique_Voiture> findAll();
    public void addRecordVoitureHistorique(Historique_Voiture recordVH);
    public List<Historique_Voiture> findByDateRange(Date dateFrom, Date dateTo);
    
}
