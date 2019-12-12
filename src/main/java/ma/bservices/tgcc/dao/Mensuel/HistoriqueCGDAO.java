/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.dao.Mensuel;

import java.util.Date;
import java.util.List;
import ma.bservices.tgcc.Entity.Historique_CG;

/**
 *
 * @author admin
 */
public interface HistoriqueCGDAO {
    public List<Historique_CG> findAll();
    public void addrecord(Historique_CG record);
    public List<Historique_CG> findByDateRange(Date from, Date to);
}
