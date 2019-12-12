/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.dao;

import java.util.List;
import ma.bservices.beans.Chantier;
import ma.bservices.beans.Salarie;
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

    public List<Zone> findBySalarie(int salarie);

    public Boolean affectZoneToSalarie(Zone zone, Salarie salarie);

    /**
     * resoudre le problème de lazy par recupérer la liste des zones d'un
     * salarie
     *
     * @param s salarie
     */
    public void zonesOf(Salarie s);

    public List<Zone> findBySupAndChantier(Salarie sup, Chantier c);
}
