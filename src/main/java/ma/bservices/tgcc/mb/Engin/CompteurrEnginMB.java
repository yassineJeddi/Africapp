/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.mb.Engin;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import ma.bservices.tgcc.Entity.CompteurrEngin;
import ma.bservices.tgcc.Entity.Engin;
import ma.bservices.tgcc.Entity.ReferentielEngin;
import ma.bservices.tgcc.authentification.Authentification;
import ma.bservices.tgcc.service.Engin.EnginService;
import ma.bservices.tgcc.service.Engin.ICompteurEnginService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

/**
 *
 * @author yassine
 */
@Component
@ManagedBean(name = "compteurrEnginMB")
@ViewScoped
public class CompteurrEnginMB {
    
    
    @ManagedProperty(value = "#{enginService}")
    private EnginService enginSerive;
    
    @ManagedProperty(value = "#{compteurEnginServiceImp}")
    private ICompteurEnginService compteurEnginService;
    
    private CompteurrEngin compteurEngin = new CompteurrEngin();
    private Engin engin = new Engin();
    private Integer idEngin;
    private Date dateChangement;
    
    private ReferentielEngin referentielEngin;
    private List<ReferentielEngin> listeReferentielEngin;
    
    private List<CompteurrEngin> compteurrEngins = new ArrayList<CompteurrEngin>();
    
    
    @PostConstruct
    public void init() {
        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        compteurEnginService = ctx.getBean(ICompteurEnginService.class);
        enginSerive = ctx.getBean(EnginService.class);
        dateChangement=new Date();
    }
    public void prepareRefEngins(Engin e){
        referentielEngin = new ReferentielEngin();
        engin =e;
        listeReferentielEngin = enginSerive.allReferentielEnginByEngin(engin); 
    }
    public void prepReferentiel(ReferentielEngin r){
        referentielEngin= r;        
    }
    public void addReferentiel(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication(); 
        referentielEngin.setUserAdd(auth.getPrincipal().toString());
        referentielEngin.setDateAdd(new Date());
        referentielEngin.setEngin(engin);
        enginSerive.addReferentielEngin(referentielEngin);
        referentielEngin = new ReferentielEngin();
        listeReferentielEngin = enginSerive.allReferentielEnginByEngin(engin);
    }
    public void editReferentiel(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication(); 
        referentielEngin.setUserEdit(auth.getPrincipal().toString());
        referentielEngin.setDateEdit(new Date());
        enginSerive.editReferentielEngin(referentielEngin);
        referentielEngin = new ReferentielEngin();
        listeReferentielEngin = enginSerive.allReferentielEnginByEngin(engin);
    }
    public void remouveReferentiel(ReferentielEngin r){
        enginSerive.remouvReferentielEngin(r);
        referentielEngin = new ReferentielEngin();
        listeReferentielEngin = enginSerive.allReferentielEnginByEngin(engin);
    }
    
    
    public void preparCompteurEngin(Engin e){
        
        compteurEngin= new CompteurrEngin();
        engin=e;
        compteurEngin.setOldCptHEng(e.getComteurHoraire());
        compteurEngin.setOldCptKEng(e.getCompteurKilometrique());
        compteurEngin.setOldCptHReel(e.getComteurHoraire());
        compteurEngin.setOldCptKReel(e.getCompteurKilometrique());
        compteurEngin.setDateChangement(new Date());
        compteurEngin.setEngin(engin);
    }

    public void addCompteurrEngin(){
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        Authentification authentification = (Authentification) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "authentification");
        String demand = authentification.get_connected_user().getLogin();
        compteurEngin.setUtilisateur(demand);
        compteurEngin.setDateCreation(new Date());
        compteurEnginService.saveCompteurrEngin(compteurEngin);
        engin.setCompteurKilometrique(compteurEngin.getCptKReel());
        engin.setComteurHoraire(compteurEngin.getCptHReel());
        enginSerive.updateEngin(engin);
    }
    
    public void allCompteurEngin(){
        compteurrEngins = compteurEnginService.allCompteurrEngin();
    }
    
    public void allCompteurEnginByIdEngin(Integer id){
        compteurrEngins = compteurEnginService.allCompteurrEnginByIdEngin(id);
    }
    
    public void vider(){
        CompteurrEngin compteurEngin;
        compteurrEngins = new ArrayList<CompteurrEngin>();
        engin = new Engin();
    }
    
    public ICompteurEnginService getCompteurEnginService() {
        return compteurEnginService;
    }

    public void setCompteurEnginService(ICompteurEnginService compteurEnginService) {
        this.compteurEnginService = compteurEnginService;
    }

    public CompteurrEngin getCompteurEngin() {
        return compteurEngin;
    }

    public void setCompteurEngin(CompteurrEngin compteurEngin) {
        this.compteurEngin = compteurEngin;
    }

    public List<CompteurrEngin> getCompteurrEngins() {
        return compteurrEngins;
    }

    public void setCompteurrEngins(List<CompteurrEngin> compteurrEngins) {
        this.compteurrEngins = compteurrEngins;
    }

    public EnginService getEnginSerive() {
        return enginSerive;
    }

    public void setEnginSerive(EnginService enginSerive) {
        this.enginSerive = enginSerive;
    }

    public Engin getEngin() {
        return engin;
    }

    public void setEngin(Engin engin) {
        this.engin = engin;
    }

    public Integer getIdEngin() {
        return idEngin;
    }

    public void setIdEngin(Integer idEngin) {
        this.idEngin = idEngin;
    }

    public Date getDateChangement() {
        return dateChangement;
    }

    public void setDateChangement(Date dateChangement) {
        this.dateChangement = dateChangement;
    }

    public ReferentielEngin getReferentielEngin() {
        return referentielEngin;
    }

    public void setReferentielEngin(ReferentielEngin referentielEngin) {
        this.referentielEngin = referentielEngin;
    }

    public List<ReferentielEngin> getListeReferentielEngin() {
        return listeReferentielEngin;
    }

    public void setListeReferentielEngin(List<ReferentielEngin> listeReferentielEngin) {
        this.listeReferentielEngin = listeReferentielEngin;
    }
    

}
