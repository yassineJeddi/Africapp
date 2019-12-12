/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.mb.mensuel;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import ma.bservices.beans.Fonction;
import ma.bservices.tgcc.service.Mensuel.FonctionService;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

/**
 *
 * @author a.wattah
 */
@Component
@ManagedBean(name = "fonctionMb")
@RequestScoped
public class FonctionMb {

    @ManagedProperty(value = "#{fonctionService}")
    private FonctionService fonctionService;
    
    private List<Fonction> fonctions;
    /*
    Getters & Setters
    */
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
    
    @PostConstruct
    public void init() {
        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        fonctionService = ctx.getBean(FonctionService.class);

        fonctions = fonctionService.findAll();

        System.out.println("--------------------------- Liste des mensuels" + fonctions.size());
    }
    /**
     * Creates a new instance of FonctionMb
     */
    public FonctionMb() {
    }
    
}
