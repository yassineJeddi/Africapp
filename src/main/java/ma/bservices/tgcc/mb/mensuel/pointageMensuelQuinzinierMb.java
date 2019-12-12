/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.mb.mensuel;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import ma.bservice.tgcc.Constante.Message;
import ma.bservices.beans.Chantier;
import ma.bservices.tgcc.Entity.Mensuel;
import ma.bservices.tgcc.Entity.PointageMensuelQuinzinier;
import ma.bservices.tgcc.service.Engin.ChantierService;
import ma.bservices.tgcc.service.Mensuel.MensuelService;
import ma.bservices.tgcc.service.Mensuel.PointageMensuelQuinzinierService;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

/**
 *
 * @author a.wattah
 */
@Component
@ManagedBean(name = "pointageMensuelQuinzinierMb")
@ViewScoped
public class pointageMensuelQuinzinierMb implements Serializable {

    @ManagedProperty(value = "#{pointageMensuelQuinzinierService}")
    private PointageMensuelQuinzinierService pointageMensuelQuinzinierService;

    @ManagedProperty(value = "#{chantierService}")
    private ChantierService chantierService;

    @ManagedProperty(value = "#{mensuelService}")
    private MensuelService mensuelService;

    private Mensuel mensuelPointage = new Mensuel();
    private PointageMensuelQuinzinier mensuelQuinzinier = new PointageMensuelQuinzinier();
    private String chantier;
    private String pointeEntree;
    private List<Chantier> chantierslist;
    private List<Mensuel> mensuels;
    private Date date = new Date();
    private String heure;
    private String minute;
    private Chantier chantierObject;
    private Mensuel mensuelsa = new Mensuel();

    /*
     * getters and setters
     */
    public PointageMensuelQuinzinierService getPointageMensuelQuinzinierService() {
        return pointageMensuelQuinzinierService;
    }

    public void setPointageMensuelQuinzinierService(PointageMensuelQuinzinierService pointageMensuelQuinzinierService) {
        this.pointageMensuelQuinzinierService = pointageMensuelQuinzinierService;
    }

    public Mensuel getMensuelPointage() {
        return mensuelPointage;
    }

    public void setMensuelPointage(Mensuel mensuelPointage) {
        this.mensuelPointage = mensuelPointage;
    }

    public PointageMensuelQuinzinier getMensuelQuinzinier() {
        return mensuelQuinzinier;
    }

    public void setMensuelQuinzinier(PointageMensuelQuinzinier mensuelQuinzinier) {
        this.mensuelQuinzinier = mensuelQuinzinier;
    }

    public String getChantier() {
        return chantier;
    }

    public void setChantier(String chantier) {
        this.chantier = chantier;
    }

    public ChantierService getChantierService() {
        return chantierService;
    }

    public void setChantierService(ChantierService chantierService) {
        this.chantierService = chantierService;
    }

    public List<Chantier> getChantiers() {
        return chantierslist;
    }

    public void setChantiers(List<Chantier> chantiers) {
        this.chantierslist = chantiers;
    }

    public String getPointeEntree() {
        return pointeEntree;
    }

    public void setPointeEntree(String pointeEntree) {
        this.pointeEntree = pointeEntree;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getHeure() {
        return heure;
    }

    public void setHeure(String heure) {
        this.heure = heure;
    }

    public String getMinute() {
        return minute;
    }

    public void setMinute(String minute) {
        this.minute = minute;
    }

    public Chantier getChantierObject() {
        return chantierObject;
    }

    public void setChantierObject(Chantier chantierObject) {
        this.chantierObject = chantierObject;
    }

    public MensuelService getMensuelService() {
        return mensuelService;
    }

    public void setMensuelService(MensuelService mensuelService) {
        this.mensuelService = mensuelService;
    }

    public List<Chantier> getChantierslist() {
        return chantierslist;
    }

    public void setChantierslist(List<Chantier> chantierslist) {
        this.chantierslist = chantierslist;
    }

    public Mensuel getMensuelsa() {
        return mensuelsa;
    }

    public void setMensuelsa(Mensuel mensuelsa) {
        this.mensuelsa = mensuelsa;
    }

    public List<Mensuel> getMensuels() {
        return mensuels;
    }

    public void setMensuels(List<Mensuel> mensuels) {
        this.mensuels = mensuels;
    }

    /*
     * end getters and setters
     */
    public pointageMensuelQuinzinierMb() {
//        this.mensuelPointage=new Mensuel();
    }

    @PostConstruct
    public void init() {
        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        pointageMensuelQuinzinierService = ctx.getBean(PointageMensuelQuinzinierService.class);
        chantierService = ctx.getBean(ChantierService.class);
        mensuelService = ctx.getBean(MensuelService.class);
        chantierslist = chantierService.findAll();

    }

    /*
     * pointer
     */
    public void pointer() {

        mensuelQuinzinier.setDate(date);
        chantierObject = pointageMensuelQuinzinierService.getChantierByLib(chantier);
        mensuelQuinzinier.setChantier(chantierObject);
        mensuelQuinzinier.setTypePointage(pointeEntree);
        mensuelQuinzinier.setHeureEntree(heure);
        mensuelQuinzinier.setMensuel(mensuelPointage);
        pointageMensuelQuinzinierService.save(mensuelQuinzinier);
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("" + Message.MENSUEL_POINTE, ""));

    }

    /*
     * rechercher mensuel 
     */
    public void rechercher() {
        mensuels = mensuelService.search(mensuelsa.getMatricule(), mensuelsa.getNom(), mensuelsa.getPrenom(), "", mensuelsa.getCin());
    }

}
