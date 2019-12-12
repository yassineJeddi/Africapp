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
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import ma.bservice.tgcc.Constante.Message;
import ma.bservices.tgcc.Entity.Voiture;
import ma.bservices.tgcc.service.Mensuel.VoitureServices;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

/**
 *
 * @author wattah
 */
@Component
@ManagedBean(name = "voiture_ServMb")
@ApplicationScoped
public class VoitureMb implements Serializable {

    @ManagedProperty(value = "#{voitureService}")
    private VoitureServices voitureService;

    private List<Voiture> l_voitures;

    private List<Voiture> l_voituresNonaffecter;

    private List<String> marque_voi;

    private List<String> numchassis_voi;

    private Voiture voiture;

    /**
     * getters and setters
     *
     * @return
     */
    public List<Voiture> getL_voituresNonaffecter() {
        return l_voituresNonaffecter;
    }

    public void setL_voituresNonaffecter(List<Voiture> l_voituresNonaffecter) {
        this.l_voituresNonaffecter = l_voituresNonaffecter;
    }

    public VoitureServices getVoitureService() {
        return voitureService;
    }

    public void setVoitureService(VoitureServices voitureService) {
        this.voitureService = voitureService;
    }

    public List<Voiture> getL_voitures() {
        return l_voitures;
    }

    public void setL_voitures(List<Voiture> l_voitures) {
        this.l_voitures = l_voitures;
    }

    public Voiture getVoiture() {
        return voiture;
    }

    public void setVoiture(Voiture voiture) {
        this.voiture = voiture;
    }

    public List<String> getMarque_voi() {
        return marque_voi;
    }

    public void setMarque_voi(List<String> marque_voi) {
        this.marque_voi = marque_voi;
    }

    public List<String> getNumchassis_voi() {
        return numchassis_voi;
    }

    public void setNumchassis_voi(List<String> numchassis_voi) {
        this.numchassis_voi = numchassis_voi;
    }

    /**
     * end getters and setters
     */
    @PostConstruct
    public void init() {
        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        voitureService = ctx.getBean(VoitureServices.class);

        voiture = new Voiture();
        l_voitures = voitureService.findAll();
       // marque_voi = voitureService.getListe_MarqueVoiture();

        //numchassis_voi = voitureService.getListe_numChassisVoiture();

        l_voituresNonaffecter = voitureService.getListeVoituresNonAffecter();

    }

    /**
     *
     */
    public List<String> marque(String query) {
        List<String> toReturn = new LinkedList<>();

        for (int i = 0; i < this.marque_voi.size(); i++) {
            if (marque_voi.get(i).toLowerCase().startsWith(query.toLowerCase())) {
                toReturn.add(marque_voi.get(i));
            }
        }

        return toReturn;
    }

    public List<String> numChassis(String query) {
        List<String> toReturn = new LinkedList<>();

        for (int i = 0; i < this.numchassis_voi.size(); i++) {
            if (numchassis_voi.get(i).toLowerCase().startsWith(query.toLowerCase())) {
                toReturn.add(numchassis_voi.get(i));
            }
        }

        return toReturn;
    }

    /**
     * en cas re charges les donneee
     */
    public void reload() {
        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        voitureService = ctx.getBean(VoitureServices.class);

        if (l_voituresNonaffecter != null) {
            l_voituresNonaffecter.clear();
        }

        l_voituresNonaffecter = voitureService.getListeVoituresNonAffecter();
        
             //   FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, Message.UPLOAD_SUCCESS, Message.VOITURE_UPLOAD));


    }

    /**
     * en cas re charges les donneee
     */
    public void reloadVoitureFindAll() {
        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        voitureService = ctx.getBean(VoitureServices.class);

        if (l_voitures != null) {
            l_voitures.clear();
        }

        l_voitures = voitureService.findAll();
        
        System.out.println("entre : " + l_voitures.size());

    }

}
