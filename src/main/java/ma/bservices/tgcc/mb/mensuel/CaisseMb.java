/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.mb.mensuel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import ma.bservices.beans.Chantier;
import ma.bservices.beans.Salarie;
import ma.bservices.mb.services.Module;
import ma.bservices.services.ChantierService;
import ma.bservices.tgcc.Entity.Caisse;
import ma.bservices.tgcc.Entity.MvmtCaisse;
import ma.bservices.tgcc.service.Mensuel.ICaisseService;
import ma.bservices.tgcc.service.Mensuel.IMvmtCaisseService;
import ma.bservices.tgcc.service.Mensuel.MensuelService;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

/**
 *
 * @author yassine.jeddi
 */
@Component
@ManagedBean(name = "caisseMb")
@ViewScoped
public class CaisseMb implements Serializable {

    private static final Logger looger = Logger.getLogger(CaisseMb.class.getName());
    
    
    ////////////////////////////////////////////////////////////////////
    /////////////////////// Variables /////////////////////////////////
    //////////////////////////////////////////////////////////////////
    @ManagedProperty(value = "#{caisseService}")
    private ICaisseService caisseService;
    
    @ManagedProperty(value = "#{mvmtCaisseServiceImpl}")
    private IMvmtCaisseService mvmtCaisseService;
    
    @ManagedProperty(value = "#{mensuelService}")
    private MensuelService mensuelService;
    
    @ManagedProperty(value = "#{chantierServiceEvol}")
    private ChantierService chantierService;
    
    private List<Chantier> chantiers = new ArrayList<Chantier>();
    private List<Salarie> salaries = new ArrayList<Salarie>();
    private List<Caisse> caisses = new ArrayList<Caisse>();
    private List<MvmtCaisse> mvmtCaisses = new ArrayList<MvmtCaisse>();
    
    private Salarie salarie = new Salarie();
    private Chantier chantier = new Chantier();
    private Caisse caisse = new Caisse();
    private MvmtCaisse mvmtCaisse = new MvmtCaisse();
    
    private Integer idSalarie,idChantier;
    private String solde;
    
    ////////////////////////////////////////////////////////////////////
    /////////////////////// GETTERS SETTERS ///////////////////////////
    //////////////////////////////////////////////////////////////////

    public ICaisseService getCaisseService() {
        return caisseService;
    }

    public void setCaisseService(ICaisseService caisseService) {
        this.caisseService = caisseService;
    }

    public MensuelService getMensuelService() {
        return mensuelService;
    }

    public void setMensuelService(MensuelService mensuelService) {
        this.mensuelService = mensuelService;
    }

    public ChantierService getChantierService() {
        return chantierService;
    }

    public void setChantierService(ChantierService chantierService) {
        this.chantierService = chantierService;
    }

    public List<Chantier> getChantiers() {
        return chantiers;
    }

    public void setChantiers(List<Chantier> chantiers) {
        this.chantiers = chantiers;
    }

    public List<Salarie> getSalaries() {
        return salaries;
    }

    public void setSalaries(List<Salarie> salaries) {
        this.salaries = salaries;
    }

    public List<Caisse> getCaisses() {
        return caisses;
    }

    public void setCaisses(List<Caisse> caisses) {
        this.caisses = caisses;
    }

    public Salarie getSalarie() {
        return salarie;
    }

    public void setSalarie(Salarie salarie) {
        this.salarie = salarie;
    }

    public Chantier getChantier() {
        return chantier;
    }

    public void setChantier(Chantier chantier) {
        this.chantier = chantier;
    }

    public Integer getIdSalarie() {
        return idSalarie;
    }

    public void setIdSalarie(Integer idSalarie) {
        this.idSalarie = idSalarie;
    }

    public Integer getIdChantier() {
        return idChantier;
    }

    public void setIdChantier(Integer idChantier) {
        this.idChantier = idChantier;
    }

    public Caisse getCaisse() {
        return caisse;
    }

    public void setCaisse(Caisse caisse) {
        this.caisse = caisse;
    }

    public IMvmtCaisseService getMvmtCaisseService() {
        return mvmtCaisseService;
    }

    public void setMvmtCaisseService(IMvmtCaisseService mvmtCaisseService) {
        this.mvmtCaisseService = mvmtCaisseService;
    }

    public List<MvmtCaisse> getMvmtCaisses() {
        return mvmtCaisses;
    }

    public void setMvmtCaisses(List<MvmtCaisse> mvmtCaisses) {
        this.mvmtCaisses = mvmtCaisses;
    }

    public MvmtCaisse getMvmtCaisse() {
        return mvmtCaisse;
    }

    public void setMvmtCaisse(MvmtCaisse mvmtCaisse) {
        this.mvmtCaisse = mvmtCaisse;
    }

    public String getSolde() {
        return solde;
    }

    public void setSolde(String solde) {
        this.solde = solde;
    }
    
    
    
    
    ////////////////////////////////////////////////////////////////////
    /////////////////////// Fonctions  ////////////////////////////////
    //////////////////////////////////////////////////////////////////
    @PostConstruct
    public void init() {
        
        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        caisseService = ctx.getBean(ICaisseService.class);
        mensuelService = ctx.getBean(MensuelService.class);
        chantierService = Module.ctx.getBean(ChantierService.class);
        
        chantiers = chantierService.listeChantiers();
        salaries  = mensuelService.findAllActifs();
        caisse = new Caisse();
        try {
        caisse = caisseService.allCaisse().get(0);
            System.out.println(":::::::::> caisse : "+caisse.toString());
        } catch (Exception e) {
            System.out.println("Erreur de récuperation de la caisse car "+e.getMessage());
        }
    }
    
    public void prepareMAJCaisse(){
        salarie = new Salarie();
        chantier= new Chantier();
        chantiers = chantierService.listeChantiers();
        salaries  = mensuelService.findAllActifs();
    }
    
    public void addCaisse(){
       /*
        if(idChantier !=0){
            chantier = chantierService.getChantierById(idChantier);
        }else{
            chantier=new Chantier();
        }
        if(idSalarie !=0){
            salarie  = mensuelService.getById(idSalarie);
        }else{
            salarie= new Salarie();
        }
        caisse.setChantier(chantier);
        caisse.setSalarie(salarie);
*/
        System.out.println("ma.bservices.tgcc.mb.mensuel.CaisseMb.addCaisse() :::::::> "+caisse.toString());
        try {
            caisseService.addCaisse(caisse);
        } catch (Exception e) {
            looger.warning("Erreur d'enregistrement de la caisse !! car : "+e.getMessage());
        }
    }
    
    public void editCaisse(){
        chantier = chantierService.getChantierById(idChantier);
        salarie  = mensuelService.getById(idSalarie);
        caisse.setChantier(chantier);
        caisse.setSalarie(salarie);
        caisseService.editCaisse(caisse);
    }
    
    public void remouveCaisse(Caisse c){
        caisseService.remouuvCaisse(c);
    }
    
    public void chargerCaisse(){
        caisses=caisseService.allCaisse();
    }
    
      //////////////////////////////////////////////////////////////////
     ////////////////////// Mouvement Caisse  /////////////////////////
    //////////////////////////////////////////////////////////////////
    
    public void chargerMvmt(){
        mvmtCaisses=mvmtCaisseService.allMvmtCaisse();
        solde=mvmtCaisseService.soldeCaisse(caisse.getId().intValue());
    }
    public void chargerMvmtCaisse(){
        mvmtCaisses=mvmtCaisseService.allMvmtCaisseByIdCaisse(caisse.getId().intValue());
        solde=mvmtCaisseService.soldeCaisse(caisse.getId().intValue());
    }
    public void preparAddMvmt(){
        mvmtCaisse=new MvmtCaisse();
        mvmtCaisse.setCaisse(caisse);
        idChantier = 0;
        idSalarie  = 0;
    }
    public void addMvmtCaisse(){
        if(idChantier !=0){
            chantier = chantierService.getChantierById(idChantier);
        }else{
            chantier=null;
        }
        if(idSalarie !=0){
            salarie  = mensuelService.getById(idSalarie);
        }else{
            salarie= null;
        }
        mvmtCaisse.setChantier(chantier);
        mvmtCaisse.setSalarie(salarie);
        System.out.println(":::::::> mvmtCaisse : "+mvmtCaisse.toString());
        mvmtCaisseService.addMvmtCaisse(mvmtCaisse);
        mvmtCaisses=mvmtCaisseService.allMvmtCaisseByIdCaisse(caisse.getId().intValue());
        solde=mvmtCaisseService.soldeCaisse(caisse.getId().intValue());
    }
    public void editMvmtCaisse(){
        Chantier c = chantierService.getChantierById(idChantier);
        try {
            Salarie s = mensuelService.findById(idSalarie.toString());
            mvmtCaisse.setSalarie(s);
        } catch (Exception e) {
            System.out.println("Erreur de chargement le salarie car : "+e.getMessage());
        }
        mvmtCaisse.setChantier(c);
        mvmtCaisseService.editMvmtCaisse(mvmtCaisse);
        mvmtCaisses=mvmtCaisseService.allMvmtCaisseByIdCaisse(caisse.getId().intValue());
        solde=mvmtCaisseService.soldeCaisse(caisse.getId().intValue());
    }
    public void remouveMvmtCaisse(MvmtCaisse m){
        mvmtCaisseService.remouvMvmtCaisse(m);
        mvmtCaisses=mvmtCaisseService.allMvmtCaisseByIdCaisse(caisse.getId().intValue());
        solde=mvmtCaisseService.soldeCaisse(caisse.getId().intValue());
    }
    
}
