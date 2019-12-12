/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.mb.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import ma.bservices.beans.Chantier;
import ma.bservices.tgcc.service.Engin.ChantierService;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

/**
 *
 * @author Mahdi
 */
@Component("chantierServMb")
@ManagedBean(name = "chantierServMb")
@ApplicationScoped
public class ChantierMb implements Serializable {

    @ManagedProperty(value = "#{chantierService}")
    private ChantierService chantierService;

    private List<String> l_code_chantier;
    private List<String> l_nom_chantier;
    private List<String> l_region;

    private List<Chantier> chantiers = new ArrayList<>();

    public ChantierMb() {
    }

    public ChantierService getChantierService() {
        return chantierService;
    }

    public void setChantierService(ChantierService chantierService) {
        this.chantierService = chantierService;
    }

    public List<Chantier> getChantiers() {
        return chantiers;
    }

    public void setChantiers(List<Chantier> chantiers) {
        this.chantiers = chantiers;
    }

    public List<String> getL_code_chantier() {
        return l_code_chantier;
    }

    public void setL_code_chantier(List<String> l_code_chantier) {
        this.l_code_chantier = l_code_chantier;
    }

    public List<String> getL_nom_chantier() {
        return l_nom_chantier;
    }

    public void setL_nom_chantier(List<String> l_nom_chantier) {
        this.l_nom_chantier = l_nom_chantier;
    }

    public List<String> getL_region() {
        return l_region;
    }

    public void setL_region(List<String> l_region) {
        this.l_region = l_region;
    }

    @PostConstruct
    public void init() {
        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        chantierService = ctx.getBean(ChantierService.class);

        chantiers = chantierService.findAll();

        this.l_code_chantier = chantierService.get_allChantiersCodes();
        this.l_nom_chantier = chantierService.get_allChantiersNames();
        this.l_region = chantierService.get_allChantiersRegins();

    }

    /**
     * the list of codes to display for the auto-complete
     * @param query
     * @return
     */
    public List<String> codes_get(String query) {
        List<String> toReturn = new LinkedList<>();

        for (int i = 0; i < this.l_code_chantier.size(); i++) {
            if (l_code_chantier.get(i).toLowerCase().startsWith(query.toLowerCase())) {
                toReturn.add(l_code_chantier.get(i));
            }
        }

        return toReturn;
    }

    /**
     * the liste of name to display for the auto-complete
     *
     * @param query
     * @return
     */
    public List<String> names_get(String query) {
        List<String> toReturn = new LinkedList<>();

        for (int i = 0; i < this.l_nom_chantier.size(); i++) {
            if (l_nom_chantier.get(i).toLowerCase().startsWith(query.toLowerCase())) {
                toReturn.add(l_nom_chantier.get(i));
            }
        }

        return toReturn;
    }

    /**
     * the list of region to display for the auto-complete
     *
     * @param query
     * @return
     */
    public List<String> regions_get(String query) {
        List<String> toReturn = new LinkedList<>();

        for (int i = 0; i < this.l_region.size(); i++) {
            if (l_region.get(i).toLowerCase().startsWith(query.toLowerCase())) {
                toReturn.add(l_region.get(i));
            }
        }

        return toReturn;
    }

    /**
     * methode reload la liste des mensuels
     */
    public void reload_Chantier() {

        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        chantierService = ctx.getBean(ChantierService.class);

        if (chantiers != null) {
            chantiers.clear();
        }

        chantiers = chantierService.findAll();

       // FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, Message.UPLOAD_SUCCESS, Message.CHANTIER_UPLOAD));


    }

}
