/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.dao.Mensuel;

import java.util.Date;
import java.util.List;
import ma.bservices.tgcc.Entity.Historique_VoitureChantier;

/**
 *
 * @author admin
 */
public interface HistoriqueVoitureChDAO {
    
    public List<Historique_VoitureChantier> findAll();
    public void addRecordVoitureChHistorique(Historique_VoitureChantier recordVH);
    public List<Historique_VoitureChantier> findByDateRange(Date from, Date to);
    
}
