/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import ma.bservices.beans.Chantier;
import ma.bservices.beans.Salarie;
import ma.bservices.beans.Zone;
import ma.bservices.dao.ZoneDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author j.allali
 */
@Service("zoneService")
@Transactional
public class ZoneServicesImpl implements ZoneServices, Serializable {

    @Autowired
//    @Qualifier(value = "zoneDAOEvol")
    private ZoneDAO zoneDAO;

    @Autowired
    ChantierService chantierDAO;

    public ZoneDAO getZoneDAO() {
        return zoneDAO;
    }

    public void setZoneDAO(ZoneDAO zoneDAO) {
        this.zoneDAO = zoneDAO;
    }

    public ChantierService getChantierDAO() {
        return chantierDAO;
    }

    public void setChantierDAO(ChantierService chantierDAO) {
        this.chantierDAO = chantierDAO;
    }

    @Override
    public List<Zone> findAll() {
        return zoneDAO.findAll();
    }

    @Override
    public List<Zone> findByChantierID(int chantier_id) {
        return zoneDAO.findByChantierID(chantier_id);
    }

    @Override
    public Boolean addZone(Zone zone, int chantier_id) {
        Chantier _c = chantierDAO.getChantier(chantier_id);
        zone.setIdChantier(_c);
        this.zoneDAO.addZone(zone);
        return true;
    }

    @Override
    public Boolean delete(int id) {

        this.zoneDAO.deleteZone(id);
        return true;

    }

    @Override
    public Boolean update(Zone zone, int chantier_id) {

        Chantier _c = chantierDAO.getChantier(chantier_id);
        zone.setIdChantier(_c);
        this.zoneDAO.uppdate(zone);
        return true;
    }

    @Override
    public Boolean update(Zone zone) {
        return (zone != null) ? this.zoneDAO.uppdate(zone) : false;
    }

    @Override
    public List<Zone> findBySalarie(int salarie) {
        return zoneDAO.findBySalarie(salarie);
    }

    @Override
    public Boolean affectZoneToSalarie(Zone zone, Salarie salarie) {
        System.out.println("affect Zone To Salarie -> Zone Service //////////////////// @@@@@@@@@@@@@@ ");

        try {
            if (salarie != null) {
//         if(zone!=null){
                String[] array_zones = salarie.getZones_str();
                for (String array_zone : array_zones) {
                    System.out.println(" <<<<<<<<<<<< 1 ");
                    Zone zone_to_add = zoneDAO.findById(Integer.parseInt(array_zone));
                    System.out.println(" <<<<<<<<<<<< 2 " + zone_to_add.getLibeleZone());
                    if (salarie.getZones() == null) {
                        salarie.setZones(new LinkedList<Zone>());
                    }
                    System.out.println(" <<<<<<<<<<<< 3 ");
//                    Hibernate.initialize(salarie.getZones());
                    zoneDAO.zonesOf(salarie);
                    salarie.getZones().add(zone_to_add);
                    return zoneDAO.affectZoneToSalarie(zone_to_add, salarie);
                }
//         }   
            }
            return false;
        } catch (Exception e) {
            System.out.println("Erreur " + e.getLocalizedMessage());
            return false;
        }

    }

    @Override
    public Zone getbyId(Integer idZone) {
        return (idZone != null) ? zoneDAO.findById(idZone) : null;
    }

    @Override
    public List<Zone> zoneBy(Salarie sup, Chantier c) {
        return (sup != null && c != null) ? zoneDAO.findBySupAndChantier(sup, c) : new ArrayList<Zone>();
    }
}
