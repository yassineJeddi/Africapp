/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.service.Engin;

import java.util.List;
import ma.bservices.beans.Zone;

/**
 *
 * @author j.allali
 */
public interface ZoneServices {

    public List<Zone> findAll();
    public List<Zone> findByChantierID(int chantier_id);
    public Boolean addZone(Zone zone, int chantier_id); 
    public Boolean delete(int id);
    public Boolean update(Zone zone, int chantier_id);
}
