/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.mb;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import ma.bservice.tgcc.Constante.Message;
import ma.bservices.beans.AccidentTravail;
import ma.bservices.beans.Chantier;
import ma.bservices.beans.Salarie;
import ma.bservices.mb.services.Evol_ChantierMb;
import ma.bservices.mb.services.Module;
import ma.bservices.services.IAccidentTravailService;
import ma.bservices.services.SalarieServiceIn;
import ma.bservices.tgcc.service.Engin.ChantierService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

/**
 *
 * @author yassine
 */
@Component
@ManagedBean(name = "accidentTravailMb")
@ViewScoped
public class AccidentTravailMb {
    
    
    
    @ManagedProperty(value = "#{accidentTravailService}")
    private IAccidentTravailService accidentTravailService;
    
    @ManagedProperty(value = "#{chantierService}")
    private ChantierService chantierService;
    
    @ManagedProperty(value = "#{salarieServiceIn}")
    private SalarieServiceIn salarieService;
    
    public Module module = new Module();
    public Integer idChantier;
    public Integer idSalarie;
    public String msg;
    public AccidentTravail accidentTravail;
    public Chantier chantier;
    public Salarie salarie;
    public List<AccidentTravail> accidentTravails = new ArrayList<AccidentTravail>();
    public List<Chantier> chantiers = new ArrayList<Chantier>();
    public List<Salarie> salaries = new ArrayList<Salarie>();

    public IAccidentTravailService getAccidentTravailService() {
        return accidentTravailService;
    }

    public void setAccidentTravailService(IAccidentTravailService accidentTravailService) {
        this.accidentTravailService = accidentTravailService;
    }

    public ChantierService getChantierService() {
        return chantierService;
    }

    public void setChantierService(ChantierService chantierService) {
        this.chantierService = chantierService;
    }

    public SalarieServiceIn getSalarieService() {
        return salarieService;
    }

    public void setSalarieService(SalarieServiceIn salarieService) {
        this.salarieService = salarieService;
    }

    public AccidentTravail getAccidentTravail() {
        return accidentTravail;
    }

    public void setAccidentTravail(AccidentTravail accidentTravail) {
        this.accidentTravail = accidentTravail;
    }

    public Chantier getChantier() {
        return chantier;
    }

    public void setChantier(Chantier chantier) {
        this.chantier = chantier;
    }

    public Salarie getSalarie() {
        return salarie;
    }

    public void setSalarie(Salarie salarie) {
        this.salarie = salarie;
    }

    public List<AccidentTravail> getAccidentTravails() {
        return accidentTravails;
    }

    public void setAccidentTravails(List<AccidentTravail> accidentTravails) {
        this.accidentTravails = accidentTravails;
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

    public Integer getIdChantier() {
        return idChantier;
    }

    public void setIdChantier(Integer idChantier) {
        this.idChantier = idChantier;
    }

    public Integer getIdSalarie() {
        return idSalarie;
    }

    public void setIdSalarie(Integer idSalarie) {
        this.idSalarie = idSalarie;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    
    
    
    
    
    @PostConstruct
    public void init() {
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication(); 
        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        chantierService = ctx.getBean(ChantierService.class);
        salarieService  = ctx.getBean(SalarieServiceIn.class);
        accidentTravailService = ctx.getBean(IAccidentTravailService.class);        
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        Evol_ChantierMb evol_ChantierMb = (Evol_ChantierMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "evol_chantierMb");
        
        viderVar();
        
        boolean isAdmin = false;
         for (GrantedAuthority grantedAuthority : auth.getAuthorities()) {
            if ("admin".equals(grantedAuthority.getAuthority())) { 
                chantiers = evol_ChantierMb.getChantiers();
                isAdmin = true;
                break;
            }
        }
        if (!isAdmin) {
            chantiers = chantierService.searchByUser(auth.getPrincipal().toString());
        }
        
    }
    
    public void listSalarieByChantier(){
        if(idChantier>0){
            salaries = salarieService.listSalarieByChantierId(idChantier);
        }
    }
    public void selectSalarieByChantier(){
        System.out.println("idSalarie " + idSalarie);
        if (idSalarie>0) {
            salarie = salarieService.getSalarieByID(idSalarie);
        }
        System.out.println("salarie " + salarie.toString());
    }
    public void addAT(){
        
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            if(idChantier>0){
                chantier=chantierService.findById(idChantier);
            }
            accidentTravail.setChantier(chantier);
            accidentTravail.setSalarie(salarie);
            accidentTravailService.addAccidentTravail(accidentTravail);
            context.addMessage(null, new FacesMessage("AT enregistré", ""));
           
        } catch (Exception e) { 
            System.out.println("::::::::::>ERREUR  accidentTravail "+e.getMessage());
            context.addMessage(null, new FacesMessage("AT non enregistré", ""));
        }
        viderVar();
        //accidentTravailService.addAccidentTravail(accidentTravail);
    }
    public void viderVar(){
        accidentTravail= new AccidentTravail();
        chantier = new Chantier();
        salarie = new Salarie();
        salaries = new ArrayList<Salarie>();
        idChantier=0;
        idSalarie=0;
    }
    
    public void chargerAccident(){
        accidentTravails = accidentTravailService.allAccidentTravail();
    }
    public void detailAccident(AccidentTravail a){
        accidentTravail = a;
    }
    
    
    
}
