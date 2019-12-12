/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.mb.services;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import ma.bservices.beans.Chantier;
import ma.bservices.tgcc.Entity.Engin;
import ma.bservices.tgcc.service.Engin.EnginService;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

/**
 *
 * @author zakaria.dem
 */
@Component("enginServMb")
@ManagedBean(name = "enginServMb")
@ApplicationScoped
public class EnginMb implements Serializable {

    @ManagedProperty(value = "#{enginService}")
    private EnginService enginService;

    private List<Engin> l_engins;

    private List<Engin> l_engins_in_chantier;

    private List<Engin> l_engins_non_archives;

    public EnginMb() {
    }

    //getters and setters fin
    @PostConstruct
    public void init() {

        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        enginService = ctx.getBean(EnginService.class);
        l_engins = enginService.findAll();
        l_engins_non_archives = enginService.findOneByArchive();

    }

    /**
     * retourne la liste des engins d'un chantier
     *
     * @param chantier
     * @return
     */
    public List<Engin> get_chantiers_byEngin(Chantier chantier) {
        if (chantier != null) {

            this.l_engins_in_chantier = new LinkedList<>();

            for (int i = 0; i < l_engins.size(); i++) {

                if (l_engins.get(i).getPrjapId().equals(chantier)) {
                    l_engins_in_chantier.add(l_engins.get(i));
                }

            }


        }
        return l_engins_in_chantier;
    }

    /**
     * re-charge les données des engins en cas de modifications
     */
    public void reload() {
        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        enginService = ctx.getBean(EnginService.class);

        this.l_engins.clear();
        this.l_engins = enginService.findAll();
        this.l_engins_non_archives = enginService.findOneByArchive();

        // FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, Message.UPLOAD_SUCCESS, Message.ENGIN_UPLOAD));
    }

    /**
     * *****************************************************************************************************************************
     *******************************************************************************************************************************
     *******************************************************************************************************************************
     * GETTERS AND SETTERS
     * *******************************************************************************************************************************
     * *******************************************************************************************************************************
     */
    public EnginService getEnginService() {
        return enginService;
    }

    public void setEnginService(EnginService enginService) {
        this.enginService = enginService;
    }

    public List<Engin> getL_engins() {
        return l_engins;
    }

    public void setL_engins(List<Engin> l_engins) {
        this.l_engins = l_engins;
    }

    public List<Engin> getL_engins_in_chantier() {
        return l_engins_in_chantier;
    }

    public void setL_engins_in_chantier(List<Engin> l_engins_in_chantier) {
        this.l_engins_in_chantier = l_engins_in_chantier;
    }

    public List<Engin> getL_engins_non_archives() {
        return l_engins_non_archives;
    }

    public void setL_engins_non_archives(List<Engin> l_engins_non_archives) {
        this.l_engins_non_archives = l_engins_non_archives;
    }

}
