/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.mb.Engin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import ma.bservices.tgcc.Entity.CompteurrEngin;
import ma.bservices.tgcc.Entity.ECHEANCIER_VIDANGE;
import ma.bservices.tgcc.Entity.Engin;
import ma.bservices.tgcc.Entity.InterventionMaintenance;
import ma.bservices.tgcc.service.Engin.DocumentEnginService;
import ma.bservices.tgcc.service.Engin.EnginService;
import ma.bservices.tgcc.service.Engin.ICompteurEnginService;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

/**
 *
 * @author yassine
 */
@Component
@ManagedBean(name = "interventionEnginMb")
@ViewScoped
public class InterventionEnginMb {
    
    @ManagedProperty(value = "#{documentEnginService}")
    private DocumentEnginService documentEnginService;

    @ManagedProperty(value = "#{enginService}")
    private EnginService enginSerive;

    @ManagedProperty(value = "#{compteurEnginServiceImp}")
    private ICompteurEnginService compteurEnginService;
    
    private Engin engin = new Engin();
    private ECHEANCIER_VIDANGE ev = new ECHEANCIER_VIDANGE();
    
    private List<InterventionMaintenance> listPr = new ArrayList<InterventionMaintenance>(); 
    private List<InterventionMaintenance> listCr = new ArrayList<InterventionMaintenance>();
    private List<CompteurrEngin> compteurrEngins = new ArrayList<CompteurrEngin>();
    
    
    
    /****
     * Geters et Setters
     * @return 
     */
    
    
    
    public DocumentEnginService getDocumentEnginService() {
        return documentEnginService;
    }

    public void setDocumentEnginService(DocumentEnginService documentEnginService) {
        this.documentEnginService = documentEnginService;
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


    public List<InterventionMaintenance> getListCr() {
        return listCr;
    }

    public void setListCr(List<InterventionMaintenance> listCr) {
        this.listCr = listCr;
    }

    public List<InterventionMaintenance> getListPr() {
        return listPr;
    }

    public void setListPr(List<InterventionMaintenance> listPr) {
        this.listPr = listPr;
    }

    public ECHEANCIER_VIDANGE getEv() {
        return ev;
    }

    public void setEv(ECHEANCIER_VIDANGE ev) {
        this.ev = ev;
    }

    public ICompteurEnginService getCompteurEnginService() {
        return compteurEnginService;
    }

    public void setCompteurEnginService(ICompteurEnginService compteurEnginService) {
        this.compteurEnginService = compteurEnginService;
    }

    public List<CompteurrEngin> getCompteurrEngins() {
        return compteurrEngins;
    }

    public void setCompteurrEngins(List<CompteurrEngin> compteurrEngins) {
        this.compteurrEngins = compteurrEngins;
    }


    
    
    /****
     * Fin Geters et Setters
     * @return 
     */

    @PostConstruct
    public void init() {

        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        documentEnginService = ctx.getBean(DocumentEnginService.class);

        engin = new Engin();

        Integer id = -1;
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        Map<String, String> requestParameters = externalContext.getRequestParameterMap();
        if (requestParameters.containsKey("enginId")) {
            id = Integer.valueOf(requestParameters.get("enginId"));
            engin = enginSerive.findOneById(id);
            ev=enginSerive.lastEcheancierVidangeByCodeEngin(engin.getCode());
            listInterCr(id);
            listInterPr(id); 
            compteurrEngins=compteurEnginService.allCompteurrEnginByIdEngin(engin.getIDEngin());
        }

    }
    
    public List<InterventionMaintenance> listInterPr(int id){
        
         listPr = new ArrayList<InterventionMaintenance>();
        try {
                listPr= enginSerive.listIntervPr(id);
                System.out.println("ma.bservices.tgcc.mb.Engin.InterventionEnginMb.listInterPr()");
            } catch (Exception e) {
                System.out.println("Erreur de récupération liste nterventions maintenance préventive car "+e.getMessage());
        }
        return listPr;
    }
    public List<InterventionMaintenance> listInterCr(int id){
        
         listCr = new ArrayList<InterventionMaintenance>();
        try {
                listCr= enginSerive.listIntervCr(id);
            } catch (Exception e) {
                System.out.println("Erreur de récupération liste nterventions maintenance curative car "+e.getMessage());
        }
        return listCr;
    }

    
    
}
