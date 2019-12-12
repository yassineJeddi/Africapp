/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.service.Engin;

import java.io.Serializable;
import java.util.List;
import ma.bservices.beans.Chantier;
import ma.bservices.beans.Zone;
import ma.bservices.tgcc.dao.engin.ChantierDAO;
import ma.bservices.tgcc.dao.engin.ZoneDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author j.allali
 */
@Service("zoneServices")
public class ZoneServicesImpl implements ZoneServices, Serializable {

    @Autowired
    private ZoneDAO zoneDAO;

    @Autowired
    private ChantierDAO chantierDAO;

    public ChantierDAO getChantierDAO() {
        return chantierDAO;
    }

    public void setChantierDAO(ChantierDAO chantierDAO) {
        this.chantierDAO = chantierDAO;
    }

    public ZoneDAO getZoneDAO() {
        return zoneDAO;
    }

    public void setZoneDAO(ZoneDAO zoneDAO) {
        this.zoneDAO = zoneDAO;
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
        Chantier _c = chantierDAO.findById(chantier_id);
        zone.setIdChantier(_c);
        zone.setLibeleZone(chantierDAO.libelleZoneToInsert(chantier_id));
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

        Chantier _c = chantierDAO.findById(chantier_id);
        zone.setIdChantier(_c);
        this.zoneDAO.uppdate(zone);
        return true;
    }
}
