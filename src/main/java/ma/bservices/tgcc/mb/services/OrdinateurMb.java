/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.mb.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import ma.bservice.tgcc.Constante.Message;
import ma.bservices.tgcc.Entity.Ordinateur;
import ma.bservices.tgcc.service.Mensuel.OrdinateurService;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

/**
 *
 * @author a.wattah
 */
@Component
@ManagedBean(name = "ordinateur_ServMb")
@ApplicationScoped
public class OrdinateurMb implements Serializable {

    @ManagedProperty(value = "#{ordinateurService}")
    private OrdinateurService ordinateurService;

    private List<Ordinateur> l_ordinateur_NonAffecter = new ArrayList<>();

    private List<Ordinateur> l_ordinateursAffecter;

    private List<Ordinateur> l_ordinateurs;

    /**
     * getters and setters
     */
    public OrdinateurService getOrdinateurService() {
        return ordinateurService;
    }

    public void setOrdinateurService(OrdinateurService ordinateurService) {
        this.ordinateurService = ordinateurService;
    }

    public List<Ordinateur> getL_ordinateur_NonAffecter() {
        return l_ordinateur_NonAffecter;
    }

    public void setL_ordinateur_NonAffecter(List<Ordinateur> l_ordinateur_NonAffecter) {
        this.l_ordinateur_NonAffecter = l_ordinateur_NonAffecter;
    }

    public List<Ordinateur> getL_ordinateursAffecter() {
        return l_ordinateursAffecter;
    }

    public void setL_ordinateursAffecter(List<Ordinateur> l_ordinateursAffecter) {
        this.l_ordinateursAffecter = l_ordinateursAffecter;
    }

    public List<Ordinateur> getL_ordinateurs() {
        return l_ordinateurs;
    }

    public void setL_ordinateurs(List<Ordinateur> l_ordinateurs) {
        this.l_ordinateurs = l_ordinateurs;
    }

    /**
     * getters and setters
     */
    @PostConstruct
    public void init() {

        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        ordinateurService = ctx.getBean(OrdinateurService.class);
        l_ordinateur_NonAffecter = ordinateurService.listOrdinateur_NonAffecter();
        l_ordinateurs = ordinateurService.findAll();
        l_ordinateursAffecter = ordinateurService.listOrdinateur_Affecte();
    }

    /**
     * en cas re charges les donneee
     */
    public void reload() {
        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        ordinateurService = ctx.getBean(OrdinateurService.class);

        if (l_ordinateur_NonAffecter != null) {

            l_ordinateur_NonAffecter.clear();
        }

        l_ordinateur_NonAffecter = ordinateurService.listOrdinateur_NonAffecter();
        
              //  FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, Message.UPLOAD_SUCCESS, Message.ORDINATEUR_UPLOAD));


    }

    public void reloadAffecter() {
        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        ordinateurService = ctx.getBean(OrdinateurService.class);

        if (l_ordinateursAffecter != null) {

            l_ordinateursAffecter.clear();
        }

        l_ordinateursAffecter = ordinateurService.listOrdinateur_Affecte();

    }

    public void reloadOrdinateurFidAll() {
        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        ordinateurService = ctx.getBean(OrdinateurService.class);
        
        

        if (l_ordinateurs != null) {

            l_ordinateurs.clear();
        }

        l_ordinateurs = ordinateurService.findAll();

    }
}
