/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.mb;

import java.util.LinkedList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import ma.bservice.tgcc.Constante.Constante;
import ma.bservices.beans.Chantier;
import ma.bservices.beans.Salarie;
import ma.bservices.beans.Zone;
import ma.bservices.mb.services.Module;
import ma.bservices.mb.services.SalarieServMb;
import ma.bservices.mb.services.ZoneMb;
import ma.bservices.services.SalarieService;
import ma.bservices.services.ZoneServices;
import org.springframework.stereotype.Component;

/**
 *
 * @author Mahdi
 */
@Component
@ManagedBean
@ViewScoped
public class ConsulterZone {

    @ManagedProperty(value = "#{zoneService}")
    private ZoneServices zoneServices;
    @ManagedProperty(value = "#{salarieService}")
    private SalarieService salarieService;
    private List<Zone> zones;
    private Integer idChantier, idZone;
    private List<Salarie> salaries;
    private ZoneMb zoneMb;
    private SalarieServMb salarieMb;
    private Salarie selectedSalarie;
    private Zone selectedZone;
    private List<Chantier> chantierWithZone;

    @PostConstruct
    public void init() {
        zoneServices = Module.ctx.getBean(ZoneServices.class);
        salarieService = Module.ctx.getBean(SalarieService.class);
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        zoneMb = (ZoneMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "zoneMb");
        salarieMb = (SalarieServMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "salarieServMb");
        salaries = salarieMb.getListCE();
        zones = zoneMb.getZones();
    }

    public void zoneByChantier() {
        zones = (idChantier != null) ? zoneServices.findByChantierID(idChantier) : zoneMb.getZones();
        salaries = salarieService.listeSalaries(0, salarieMb.getNbrCE(), "", null, Constante.FONCTION_ID_CHEF_EQUIPE, null, "", "", "", "", idChantier + "", "");
    }

    public void salarieByZone() {
        Zone zone = zoneServices.getbyId(selectedZone.getIdZone());
        salaries = zone.getSalaries();
    }

    public void desaffecter(Zone z) {
        Salarie s = salarieService.getSalarie(selectedSalarie.getId());
        boolean remove = s.getZones().remove(z);
        try {
            if (remove) {
                salarieService.modifierInformationsSalarie(s);
                zoneBySalarie(s);
                Module.message(0, "désaffectation effectuée", "");
            }
        } catch (Exception ex) {
            Module.message(3, "Oups ! erreur", "");
        }
    }

    public void zoneBySalarie(Salarie sal) {
        //zones = new LinkedList<>();
        Salarie s = salarieService.getSalarie(sal.getId());
        selectedSalarie = s;
        chantierWithZone = s.getChantiers();
        for (Chantier c : chantierWithZone) {
            //zones.clear();
            for (Zone z : zoneServices.findByChantierID(c.getId())) {
                Zone zone = zoneServices.getbyId(z.getIdZone());
                for (Salarie s1 : zone.getSalaries()) {
                    if (s1.getId().equals(sal.getId())) {
                        c.getZoneOfChefEquipe().add(zone);
                        break;
                    }
                }
            }
        }
        System.out.println("Salarie Chef With Chantier ");
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

    public List<Salarie> getSalaries() {
        return salaries;
    }

    public void setSalaries(List<Salarie> salaries) {
        this.salaries = salaries;
    }

    public Integer getIdChantier() {
        return idChantier;
    }

    public void setIdChantier(Integer idChantier) {
        this.idChantier = idChantier;
    }

    public Integer getIdZone() {
        return idZone;
    }

    public void setIdZone(Integer idZone) {
        this.idZone = idZone;
    }

    public Salarie getSelectedSalarie() {
        return selectedSalarie;
    }

    public void setSelectedSalarie(Salarie selectedSalarie) {
        this.selectedSalarie = selectedSalarie;
    }

    public Zone getSelectedZone() {
        return selectedZone;
    }

    public void setSelectedZone(Zone selectedZone) {
        this.selectedZone = selectedZone;
    }

    public SalarieService getSalarieService() {
        return salarieService;
    }

    public void setSalarieService(SalarieService salarieService) {
        this.salarieService = salarieService;
    }

    public ZoneMb getZoneMb() {
        return zoneMb;
    }

    public void setZoneMb(ZoneMb zoneMb) {
        this.zoneMb = zoneMb;
    }

    public SalarieServMb getSalarieMb() {
        return salarieMb;
    }

    public void setSalarieMb(SalarieServMb salarieMb) {
        this.salarieMb = salarieMb;
    }

    public List<Chantier> getChantierWithZone() {
        return chantierWithZone;
    }

    public void setChantierWithZone(List<Chantier> chantierWithZone) {
        this.chantierWithZone = chantierWithZone;
    }

}
