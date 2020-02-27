/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.mb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import ma.bservices.beans.Presence;
import ma.bservices.mb.services.Module;
import ma.bservices.services.IPresenceService;
import ma.bservices.services.PresenceService;
import ma.bservices.services.SalarieService;
import org.springframework.stereotype.Component;

/**
 *
 * @author Mahdi
 */
@Component
@ManagedBean(name = "PresenceSMb")
@ViewScoped
public class PresenceSalarieMb implements Serializable {

    //Présences
    @ManagedProperty(value = "#{presenceService}")
    private PresenceService presenceService;
    @ManagedProperty(value = "#{presenceServicei}")
    private IPresenceService presenceServicei;
    @ManagedProperty(value = "#{salarieService}")
    private SalarieService salarieService;
    
    
    private List<Presence> presences;
    private Date dateDe, dateA;
    private Integer idChantier;

    public PresenceService getPresenceService() {
        return presenceService;
    }

    public void setPresenceService(PresenceService presenceService) {
        this.presenceService = presenceService;
    }

    public SalarieService getSalarieService() {
        return salarieService;
    }

    public void setSalarieService(SalarieService salarieService) {
        this.salarieService = salarieService;
    }

    public List<Presence> getPresences() {
        return presences;
    }

    public void setPresences(List<Presence> presences) {
        this.presences = presences;
    }

    public Date getDateDe() {
        return dateDe;
    }

    public void setDateDe(Date dateDe) {
        this.dateDe = dateDe;
    }

    public Date getDateA() {
        return dateA;
    }

    public void setDateA(Date dateA) {
        this.dateA = dateA;
    }

    public Integer getIdChantier() {
        return idChantier;
    }

    public void setIdChantier(Integer idChantier) {
        this.idChantier = idChantier;
    }

    public IPresenceService getPresenceServicei() {
        return presenceServicei;
    }

    public void setPresenceServicei(IPresenceService presenceServicei) {
        this.presenceServicei = presenceServicei;
    }
    
    

    //methode
    @PostConstruct
    public void init() {
        presenceService = Module.ctx.getBean(PresenceService.class);
    }


    public void findPresenceSalarie() {
        
        presences = new ArrayList<Presence>();
       if ("".equals(idChantier) || idChantier == null || idChantier<=0) {
            Module.message(2, "Veuillez sélectionner le chantier", "");
            return;
        } 
       if ("".equals(dateDe) || dateDe == null) {
            Module.message(2, "Veuillez sélectionner date début", "");
            return;
        }  
        try { 
            presences = presenceServicei.allPresenceByChantierAndDate(idChantier, dateDe, dateA);
        } catch (Exception e) {
            System.out.println(" Erreur de récupération list des presences car "+e.getMessage());
        }
       
    }








}
