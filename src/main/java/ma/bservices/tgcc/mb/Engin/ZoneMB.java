/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.mb.Engin;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import ma.bservice.tgcc.Constante.Message;
import ma.bservices.beans.Zone;
import ma.bservices.tgcc.service.Engin.ZoneServices;
import org.primefaces.event.RowEditEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

/**
 *
 * @author a.wattah
 */
@Component
@ManagedBean(name = "zone")
@RequestScoped
public class ZoneMB implements Serializable {

    @ManagedProperty(value = "#{zoneServices}")
    private ZoneServices zoneService;

    private List<Zone> zones;
    private List<Zone> zonesInChantier;

    public List<Zone> getZonesInChantier() {
        return zonesInChantier;
    }

    public void setZonesInChantier(List<Zone> zonesInChantier) {
        this.zonesInChantier = zonesInChantier;
    }

    private Zone zone = new Zone();

    private Zone zoneAdd;

    private String libeleZone;

    private int chantierSearch;
    private int chantierAdd;

    /*
     * getters and setters 
     */
    public ZoneServices getZoneService() {
        return zoneService;
    }

    public void setZoneService(ZoneServices zoneService) {
        this.zoneService = zoneService;
    }

    public List<Zone> getZones() {
        return zones;
    }

    public void setZones(List<Zone> zones) {
        this.zones = zones;
    }

    public Zone getZone() {
        return zone;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
    }

    public int getChantierSearch() {
        return chantierSearch;
    }

    public void setChantierSearch(int chantierSearch) {
        this.chantierSearch = chantierSearch;
    }

    public int getChantierAdd() {
        return chantierAdd;
    }

    public void setChantierAdd(int chantierAdd) {
        this.chantierAdd = chantierAdd;
    }

    public Zone getZoneAdd() {
        return zoneAdd;
    }

    public void setZoneAdd(Zone zoneAdd) {
        this.zoneAdd = zoneAdd;
    }

    public String getLibeleZone() {
        return libeleZone;
    }

    public void setLibeleZone(String libeleZone) {
        this.libeleZone = libeleZone;
    }

    /*
     * end getters and setters
     */
    /*
     * methode initialiser class 
     */
    @PostConstruct
    public void init() {
        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        zoneService = ctx.getBean(ZoneServices.class);

        zoneAdd = new Zone();
        zone = new Zone();

        // findAll zone 
        zones = zoneService.findAll();
        zonesInChantier = zoneService.findByChantierID(1);       

    }
    
    
    public void findZonesInChantier(){
    this.zonesInChantier = zoneService.findByChantierID(1);
    }
    

    /*
     * Ajouter Zone 
     */
    public void addZone() {
        System.out.println("addddddd");
        FacesContext context = FacesContext.getCurrentInstance();

        Boolean b = zoneService.addZone(zoneAdd, chantierAdd);
        String messageTrue = Message.ADD_ZONE_SUCCESS + zoneAdd.getLibeleZone();

        String messagefalse = Message.ADD_ZONE_FALSE;
        if (b == true) {
            context.addMessage(null, new FacesMessage("" + messageTrue, ""));
        } else {

            context.addMessage(null, new FacesMessage("" + messagefalse, ""));
            System.out.println("fin");

        }
        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        zoneService = ctx.getBean(ZoneServices.class);
        zones = zoneService.findAll();
        
        zoneAdd=new Zone();
        chantierAdd=0;
    }

    /*
     * Rechercher Zone
     */
    public void rechercherZones() {

    }

    /*
     * supprimer zone 
     */
    public void deleteZone(Zone z) {

        FacesContext context = FacesContext.getCurrentInstance();

        Boolean b = this.zoneService.delete(z.getIdZone());
        if (b == true) {

            context.addMessage(null, new FacesMessage("" + Message.DELETE_ZONE_SUCCESS, ""));

        } else {
            context.addMessage(null, new FacesMessage("" + Message.DELETE_ZONE_FALSE, ""));
        }

        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        zoneService = ctx.getBean(ZoneServices.class);
        zones = zoneService.findAll();

    }

    /*
     * modifier Zone 
     */
    public void onRowEdit(RowEditEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        String messageTrue = Message.UPDATE_ZONE_SUCCESS + ((Zone) event.getObject()).getLibeleZone();
        String messagefalse = Message.UPDATE_ZONE_FALSE+ ((Zone) event.getObject()).getLibeleZone();

        Boolean b = zoneService.update((Zone) event.getObject(), chantierSearch);
        if (b == true) {
            context.addMessage(null, new FacesMessage("" + messageTrue, ""));
        } else {
            context.addMessage(null, new FacesMessage("" + messagefalse, ""));
        }

    }

    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage(Message.UPDATE_ZONE_CANCEL, ((Zone) event.getObject()).getLibeleZone());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void updateZone() {
        System.out.println("modifier ..." + zone.getLibeleZone());

        System.out.println("addddddd");

        zoneService.update(zone, chantierSearch);
        System.out.println("fin");

        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        zoneService = ctx.getBean(ZoneServices.class);
        zones = zoneService.findAll();
        
      

    }

}
