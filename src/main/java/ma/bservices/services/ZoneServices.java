/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.services;

import java.util.List;
import ma.bservices.beans.Chantier;
import ma.bservices.beans.Salarie;
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
    
    public Boolean update(Zone zone);

    public List<Zone> findBySalarie(int salarie);

    public Boolean affectZoneToSalarie(Zone zone, Salarie salarie);

    public Zone getbyId(Integer idZone);

    /**
     * recuperer la liste des zones d'un salarie dans un chantier
     *
     * @param sup salarie superieur
     * @param c chantier
     * @return liste des zones
     */
    public List<Zone> zoneBy(Salarie sup, Chantier c);
}
