/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.mb.services;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import ma.bservices.beans.Fonction;
import ma.bservices.tgcc.service.Mensuel.FonctionService;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

/**
 *fonctionServMb
 * @author Mahdi
 */
@Component("fonctionServMb")
@ManagedBean(name = "fonctionServMb")
@ApplicationScoped
public class FonctionMb implements Serializable {

    @ManagedProperty(value = "#{fonctionService}")
    private FonctionService fonctionService;

    private List<Fonction> fonctions;

    public FonctionService getFonctionService() {
        return fonctionService;
    }

    public void setFonctionService(FonctionService fonctionService) {
        this.fonctionService = fonctionService;
    }

    public List<Fonction> getFonctions() {
        return fonctions;
    }

    public void setFonctions(List<Fonction> fonctions) {
        this.fonctions = fonctions;
    }

    public FonctionMb() {
    }

    @PostConstruct
    public void init() {
        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        fonctionService = ctx.getBean(FonctionService.class);
        fonctions = fonctionService.findAll();
    }

    /**
     * methode reload la liste des mensuels
     */
    public void reload_Fonction() {


        if (fonctions != null) {
            fonctions.clear();
        }
        try {
            fonctionService.importFonctionDiva();
        } catch (Exception e) {
            System.out.println("Erreur d'execution de la procedure stoké car "+e.getMessage());
        }
        fonctions = fonctionService.findAll();

       // FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, Message.UPLOAD_SUCCESS, Message.FONCTION_UPLOAD));


    }

}
