/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.mb.services;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import ma.bservice.tgcc.Constante.Message;
import ma.bservices.tgcc.Entity.Citerne;
import ma.bservices.tgcc.service.Engin.CiterneService;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

/**
 *
 * @author a.wattah
 */
@Component
@ManagedBean(name = "citerne_ServMb")
@ApplicationScoped
public class CiterneMb implements Serializable {

    @ManagedProperty(value = "#{citerneService}")
    private CiterneService citerneService;

    private List<Citerne> l_citernes;
    private List<Citerne> citernes_nom_Archive;

    /**
     * getters and setters
     *
     * @return
     */
    public CiterneService getCiterneService() {
        return citerneService;
    }

    public void setCiterneService(CiterneService citerneService) {
        this.citerneService = citerneService;
    }

    public List<Citerne> getL_citernes() {
        return l_citernes;
    }

    public void setL_citernes(List<Citerne> l_citernes) {
        this.l_citernes = l_citernes;
    }

    public List<Citerne> getCiternes_nom_Archive() {
        return citernes_nom_Archive;
    }

    public void setCiternes_nom_Archive(List<Citerne> citernes_nom_Archive) {
        this.citernes_nom_Archive = citernes_nom_Archive;
    }

    /**
     * end getters and setters
     */
    @PostConstruct
    public void init() {

        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        citerneService = ctx.getBean(CiterneService.class);
        citernes_nom_Archive = citerneService.find_allCiterneNon_archiver();
        l_citernes = citerneService.find_all_Citerne();

    }

    /**
     * methode permet de re charge la liste
     */
    public void reload() {

        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        citerneService = ctx.getBean(CiterneService.class);

        if (l_citernes != null) {
            l_citernes.clear();
        }

        l_citernes = citerneService.find_all_Citerne();

        // FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, Message.UPLOAD_SUCCESS, Message.CITERNE_UPLOAD));
    }

    public void reloadCiterneNonArchiver() {

        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        citerneService = ctx.getBean(CiterneService.class);

        if (citernes_nom_Archive != null) {
            citernes_nom_Archive.clear();
        }

        citernes_nom_Archive = citerneService.find_allCiterneNon_archiver();
    }

}
