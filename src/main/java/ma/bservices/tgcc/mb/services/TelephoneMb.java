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
import ma.bservices.tgcc.Entity.Telephone;
import ma.bservices.tgcc.service.Mensuel.OrdinateurService;
import ma.bservices.tgcc.service.Mensuel.TelephoneService;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

/**
 *
 * @author a.wattah
 */
@Component
@ManagedBean(name = "telephone_ServMb")
@ApplicationScoped
public class TelephoneMb implements Serializable {

    @ManagedProperty(value = "#{telephoneService}")
    private TelephoneService telephoneService;

    private List<Telephone> l_telephoneNon_Affecter;

    private List<Telephone> l_telephones;

    private List<Telephone> l_telephonesFindAll;

    /**
     * getters and setters
     */
    public TelephoneService getTelephoneService() {
        return telephoneService;
    }

    public void setTelephoneService(TelephoneService telephoneService) {
        this.telephoneService = telephoneService;
    }

    public List<Telephone> getL_telephoneNon_Affecter() {
        return l_telephoneNon_Affecter;
    }

    public void setL_telephoneNon_Affecter(List<Telephone> l_telephoneNon_Affecter) {
        this.l_telephoneNon_Affecter = l_telephoneNon_Affecter;
    }

    public List<Telephone> getL_telephones() {
        return l_telephones;
    }

    public void setL_telephones(List<Telephone> l_telephones) {
        this.l_telephones = l_telephones;
    }

    public List<Telephone> getL_telephonesFindAll() {
        return l_telephonesFindAll;
    }

    public void setL_telephonesFindAll(List<Telephone> l_telephonesFindAll) {
        this.l_telephonesFindAll = l_telephonesFindAll;
    }

    /**
     * end geters and setters
     */
    @PostConstruct
    public void init() {
        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        telephoneService = ctx.getBean(TelephoneService.class);
        l_telephoneNon_Affecter = telephoneService.listTelephone_Non_Affecter();
        l_telephones = telephoneService.listTelephophone_Affecter();
        l_telephonesFindAll = telephoneService.findAll();

    }

    /**
     * en cas re charges les donneee
     */
    public void reload() {
        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        telephoneService = ctx.getBean(TelephoneService.class);

        if (l_telephoneNon_Affecter != null) {
            l_telephoneNon_Affecter.clear();
        }

        l_telephoneNon_Affecter = telephoneService.listTelephone_Non_Affecter();
        
              //  FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, Message.UPLOAD_SUCCESS, Message.TELEPHONE_UPLOAD));


    }

    /**
     * en cas re charges les donneee
     */
    public void reloadTelephoneAffecter() {
        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        telephoneService = ctx.getBean(TelephoneService.class);

        if (l_telephones != null) {
            l_telephones.clear();
        }

        l_telephones = telephoneService.listTelephophone_Affecter();

    }

    /**
     *
     */
    public void reloadTelephoneFindAll() {
        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        telephoneService = ctx.getBean(TelephoneService.class);

        if (l_telephonesFindAll != null) {
            l_telephonesFindAll.clear();
        }

        l_telephonesFindAll = telephoneService.findAll();

    }

}
