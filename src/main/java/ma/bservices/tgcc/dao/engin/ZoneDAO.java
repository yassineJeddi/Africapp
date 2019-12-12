/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.dao.engin;

import java.util.List;
import ma.bservices.beans.Zone;

/**
 *
 * @author j.allali
 */
public interface ZoneDAO {

    public List<Zone> findAll();
    public Zone findById(int id);
    public List<Zone> findByChantierID(int chantier_id);
    public Boolean addZone(Zone zone);
    public Boolean deleteZone(int id);
    public Boolean uppdate(Zone zone);
     public void delete_Test_Unitaire(Zone zd);
}
