
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.mb.mensuel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import ma.bservices.tgcc.Entity.Mensuel;
import ma.bservices.tgcc.mb.services.MensuelMb;
import ma.bservices.tgcc.service.Mensuel.MensuelService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

/**
 *
 * @author Mahdi
 */
@Component
@Transactional
@ManagedBean(name = "inactifMensuelMb")
@ViewScoped
public class InactifMensuelMb implements Serializable {

    @ManagedProperty(value = "#{mensuelService}")
    private MensuelService mensuelService;
    private Mensuel mensuelToSearch = new Mensuel();
    private Mensuel mensuel = new Mensuel();
    private List<Mensuel> searchMensuelList = new ArrayList<>();
    private Mensuel mensuelSearch = new Mensuel();
    private String fonction;
    private List<Mensuel> l_mensuels;
    ELContext elContext;
    ma.bservices.tgcc.mb.services.MensuelMb mensuelServMb;

    public List<Mensuel> getL_mensuels() {
        return l_mensuels;
    }

    /*
     * getters and setters
     */
    public void setL_mensuels(List<Mensuel> l_mensuels) {
        this.l_mensuels = l_mensuels;
    }

    public MensuelService getMensuelService() {
        return mensuelService;
    }

    public void setMensuelService(MensuelService mensuelService) {
        this.mensuelService = mensuelService;
    }

    public Mensuel getMensuel() {
        return mensuel;
    }

    public void setMensuel(Mensuel mensuel) {
        this.mensuel = mensuel;
    }

    public Mensuel getMensuelSearch() {
        return mensuelSearch;
    }

    public void setMensuelSearch(Mensuel mensuelSearch) {
        this.mensuelSearch = mensuelSearch;
    }

    public Mensuel getMensuelToSearch() {
        return mensuelToSearch;
    }

    public void setMensuelToSearch(Mensuel mensuelToSearch) {
        this.mensuelToSearch = mensuelToSearch;
    }

    public List<Mensuel> getSearchMensuelList() {
        return searchMensuelList;
    }

    public void setSearchMensuelList(List<Mensuel> searchMensuelList) {
        this.searchMensuelList = searchMensuelList;
    }

    public String getFonction() {
        return fonction;
    }

    public void setFonction(String fonction) {
        this.fonction = fonction;
    }

    public ELContext getElContext() {
        return elContext;
    }

    public void setElContext(ELContext elContext) {
        this.elContext = elContext;
    }

    public MensuelMb getMensuelServMb() {
        return mensuelServMb;
    }

    public void setMensuelServMb(MensuelMb mensuelServMb) {
        this.mensuelServMb = mensuelServMb;
    }


    /*
     * end setters and getters
     */
    @PostConstruct
    public void init() {
        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        mensuelService = ctx.getBean(MensuelService.class);
        elContext = FacesContext.getCurrentInstance().getELContext();
        mensuelServMb = (ma.bservices.tgcc.mb.services.MensuelMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "mensuelServMb");
        mensuel = new Mensuel();
        l_mensuels = mensuelServMb.l_mensuels_inActifs;

    }

    /*
     * cette methode qui fait la recherche a partir les information d'un salarié
     */
    public void rechercherEngin() {

        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        mensuelService = ctx.getBean(MensuelService.class);

        this.searchMensuelList = mensuelService.search(mensuelToSearch.getMatricule(), mensuelToSearch.getNom(), mensuelToSearch.getPrenom(), fonction, mensuelToSearch.getCin());

    }

    /**
     * Creates a new instance of MensuelMb
     */
    public InactifMensuelMb() {

    }

    /*
     * cette methode qui fait la recherche a partir les information d'un salarié
     */
    public void rechercherMensuel() {
        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        mensuelService = ctx.getBean(MensuelService.class);

        this.searchMensuelList = mensuelService.search(mensuelToSearch.getMatricule(), mensuelToSearch.getNom(), mensuelToSearch.getPrenom(), "", mensuelToSearch.getCin());

    }

    /*
     * rechercher un mensuel inactif
     */
    public void rechercherMensuelByFon() {

        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        mensuelService = ctx.getBean(MensuelService.class);
        if (mensuelToSearch.getMatricule().compareTo("") == 0 && mensuelToSearch.getNom().compareTo("") == 0 && mensuelToSearch.getPrenom().compareTo("") == 0 && fonction.compareTo("") == 0 && mensuelToSearch.getStatut().compareTo("") == 0) {

            l_mensuels = mensuelServMb.l_mensuels;
        } else {
            this.searchMensuelList = mensuelService.rechercherMensuelByfonction(mensuelToSearch.getMatricule(), mensuelToSearch.getNom(), mensuelToSearch.getPrenom(), fonction.trim(), "inactif");
            l_mensuels = searchMensuelList;
        }

    }
}
