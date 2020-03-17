/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.mb.Engin;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import ma.bservice.tgcc.Constante.Message;
import ma.bservices.mb.services.ConstanteMb;
import ma.bservices.tgcc.Entity.CompteurrEngin;
import ma.bservices.tgcc.Entity.ECHEANCIER_VIDANGE;
import ma.bservices.tgcc.Entity.Engin;
import ma.bservices.tgcc.Entity.InterventionMaintenance;
import ma.bservices.tgcc.service.Engin.DocumentEnginService;
import ma.bservices.tgcc.service.Engin.EnginService;
import ma.bservices.tgcc.service.Engin.ICompteurEnginService;
import org.apache.commons.io.FilenameUtils;
import org.primefaces.model.UploadedFile;
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
@ManagedBean(name = "interventionEnginMb")
@ViewScoped
public class InterventionEnginMb {
    
    @ManagedProperty(value = "#{documentEnginService}")
    private DocumentEnginService documentEnginService;

    @ManagedProperty(value = "#{enginService}")
    private EnginService enginSerive;

    @ManagedProperty(value = "#{compteurEnginServiceImp}")
    private ICompteurEnginService compteurEnginService;
    
    
    private String selectedDoc;
    private Integer prijAp_id=0;
    
    private UploadedFile uploadedFile;
    private InterventionMaintenance interventionMaintenance;
    private Engin engin = new Engin();
    private ECHEANCIER_VIDANGE ev = new ECHEANCIER_VIDANGE();
    
    private List<InterventionMaintenance> listPr = new ArrayList<InterventionMaintenance>(); 
    private List<InterventionMaintenance> listCr = new ArrayList<InterventionMaintenance>();
    private List<CompteurrEngin> compteurrEngins = new ArrayList<CompteurrEngin>();
    
    
    

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
    public void prepInterventionMaintenance(){
        interventionMaintenance = new InterventionMaintenance();
        interventionMaintenance.setID_ENGIN(engin.getIDEngin());
        interventionMaintenance.setTYPE_INTERV("P");
        
    }

    public void addInterventionMaintenance() throws IOException{
        
            System.out.println("::::::::::::::::::::::> prijAp_id : "+prijAp_id);
        if(prijAp_id>0){
            String chemin = ConstanteMb.getRepertoire() + "/files/engin/maintenance_preventive";
        Path folder = Paths.get(chemin);
        Files.createDirectories(folder);
        if (uploadedFile == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, Message.STRING_CHARGE_DOCUMENT, Message.STRING_CHARGE_DOCUMENT));
        } else if (uploadedFile.getFileName().equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, Message.STRING_CHARGE_DOCUMENT, Message.STRING_CHARGE_DOCUMENT));
        } else {
            String filename = FilenameUtils.getBaseName(uploadedFile.getFileName());
            String extension = FilenameUtils.getExtension(uploadedFile.getFileName());
            
            if (!"pdf".equals(extension)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, Message.STRING_CHARGE_DOCUMENT_PDF, Message.STRING_CHARGE_DOCUMENT_PDF));
            } else {
                Path file = Files.createTempFile(folder, filename, "." + extension);
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                try (InputStream input = uploadedFile.getInputstream()) {
                Files.copy(input, file, StandardCopyOption.REPLACE_EXISTING); 
                interventionMaintenance.setPRJAP_ID_INTER(prijAp_id);
                interventionMaintenance.setCreepar(auth.getPrincipal().toString());
                interventionMaintenance.setDATE_CREATE_REV(new Date()); 
                interventionMaintenance.setFichier(chemin + "/" +file.getFileName());
                enginSerive.addInterventionMaintenancePr(interventionMaintenance);
                listPr= enginSerive.listIntervPr(engin.getIDEngin()); 
                }
            }
        }
        }
        
    }
    
    public void visualiser(String chemin) {
        FacesContext context = FacesContext.getCurrentInstance();
         try {
                System.out.println("chemin : " + chemin);
                selectedDoc = chemin.substring(chemin.indexOf("files")); 
                System.out.println("selectedDoc : " + selectedDoc);
            } catch (Exception e) {
                System.out.println("Ereur de telechargement du fichier "+e.getMessage());
                context.addMessage(null, new FacesMessage("Ereur de visualiser du fichier car"+e.getMessage(), ""));
         }
    }
    
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

    public InterventionMaintenance getInterventionMaintenance() {
        return interventionMaintenance;
    }

    public void setInterventionMaintenance(InterventionMaintenance interventionMaintenance) {
        this.interventionMaintenance = interventionMaintenance;
    }

    public Integer getPrijAp_id() {
        return prijAp_id;
    }

    public void setPrijAp_id(Integer prijAp_id) {
        this.prijAp_id = prijAp_id;
    }

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public String getSelectedDoc() {
        return selectedDoc;
    }

    public void setSelectedDoc(String selectedDoc) {
        this.selectedDoc = selectedDoc;
    }
    
    
    
    /****
     * Fin Geters et Setters
     * @return 
     */
    
}
