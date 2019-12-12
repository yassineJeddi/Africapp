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
import ma.bservices.tgcc.Entity.Modem3G;
import ma.bservices.tgcc.service.Mensuel.Modem3gService;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

/**
 *
 * @author a.wattah
 */
@Component
@ManagedBean(name = "modem3g_ServMb")
@ApplicationScoped
public class Modem3gMb implements Serializable {

    @ManagedProperty(value = "#{modem3gService}")
    private Modem3gService modem3gService;

    private List<Modem3G> l_modem3g_Non_Affecter;

    private List<Modem3G> l_modem3gAffecter;

    private List<Modem3G> l_modem3gs;

    /**
     * getters and setters
     */
    public Modem3gService getModem3gService() {
        return modem3gService;
    }

    public List<Modem3G> getL_modem3gAffecter() {
        return l_modem3gAffecter;
    }

    public void setL_modem3gAffecter(List<Modem3G> l_modem3gAffecter) {
        this.l_modem3gAffecter = l_modem3gAffecter;
    }

    public void setModem3gService(Modem3gService modem3gService) {
        this.modem3gService = modem3gService;
    }

    public List<Modem3G> getL_modem3g_Non_Affecter() {
        return l_modem3g_Non_Affecter;
    }

    public void setL_modem3g_Non_Affecter(List<Modem3G> l_modem3g_Non_Affecter) {
        this.l_modem3g_Non_Affecter = l_modem3g_Non_Affecter;
    }

    public List<Modem3G> getL_modem3gs() {
        return l_modem3gs;
    }

    public void setL_modem3gs(List<Modem3G> l_modem3gs) {
        this.l_modem3gs = l_modem3gs;
    }

    /**
     * getters and setters
     */
    @PostConstruct
    public void init() {
        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        modem3gService = ctx.getBean(Modem3gService.class);

        l_modem3g_Non_Affecter = modem3gService.list3G_Non_Affecter();

        l_modem3gAffecter = modem3gService.list3G_Affeceter();

        l_modem3gs = modem3gService.findAll();

    }

    /**
     * en cas re charges les donneee
     */
    public void reload() {
        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        modem3gService = ctx.getBean(Modem3gService.class);

        if (l_modem3g_Non_Affecter != null) {
            l_modem3g_Non_Affecter.clear();
        }

        l_modem3g_Non_Affecter = modem3gService.list3G_Non_Affecter();

    }

    /**
     *
     */
    public void reload3gAffecter() {
        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        modem3gService = ctx.getBean(Modem3gService.class);

        if (l_modem3gAffecter != null) {
            l_modem3gAffecter.clear();
        }

        l_modem3gAffecter = modem3gService.list3G_Affeceter();

    }

    /**
     *
     */
    public void reloadList3g() {
        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        modem3gService = ctx.getBean(Modem3gService.class);

        if (l_modem3gs != null) {
            l_modem3gs.clear();
        }

        l_modem3gs = modem3gService.findAll();

       // FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, Message.UPLOAD_SUCCESS, Message.MODEM_UPLOAD));
    }

    /**
     *
     */
    public void reloadList3gNonAffecter() {
        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        modem3gService = ctx.getBean(Modem3gService.class);

        if (l_modem3g_Non_Affecter != null) {
            l_modem3g_Non_Affecter.clear();
        }

        l_modem3g_Non_Affecter = modem3gService.list3G_Non_Affecter();
       // FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, Message.UPLOAD_SUCCESS, Message.MODEM_UPLOAD));

    }

}
