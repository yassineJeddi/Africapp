package ma.bservices.mb.services;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import ma.bservices.beans.Zone;
import ma.bservices.services.ZoneServices;
import org.springframework.stereotype.Component;

/**
 *
 * @author Mahdi
 */
@Component
@ManagedBean
@ApplicationScoped
public class ZoneMb implements Serializable {

    @ManagedProperty(value = "#{zoneService}")
    private ZoneServices zoneServices;
    private List<Zone> zones;

    @PostConstruct
    public void init() {
        zoneServices = Module.ctx.getBean(ZoneServices.class);
        zones = zoneServices.findAll();
    }

    //getter & setter
    public ZoneServices getZoneServices() {
        return zoneServices;
    }

    public void setZoneServices(ZoneServices zoneServices) {
        this.zoneServices = zoneServices;
    }

    public List<Zone> getZones() {
        return zones;
    }

    public void setZones(List<Zone> zones) {
        this.zones = zones;
    }
}
