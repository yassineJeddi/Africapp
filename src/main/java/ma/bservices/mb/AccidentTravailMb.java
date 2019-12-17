/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.mb;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import ma.bservice.tgcc.Constante.Message;
import ma.bservices.beans.AccidentTravail;
import ma.bservices.beans.Chantier;
import ma.bservices.beans.DetailAT;
import ma.bservices.beans.DocumentDetailAt;
import ma.bservices.beans.Salarie;
import ma.bservices.mb.services.ConstanteMb;
import ma.bservices.mb.services.Evol_ChantierMb;
import ma.bservices.mb.services.Module;
import ma.bservices.services.IAccidentTravailService;
import ma.bservices.services.IDetailATService;
import ma.bservices.services.IDocumentDetailAtService;
import ma.bservices.services.SalarieServiceIn;
import ma.bservices.tgcc.service.Engin.ChantierService;
import static org.apache.catalina.connector.InputBuffer.DEFAULT_BUFFER_SIZE;
import org.apache.commons.io.FilenameUtils;
import org.primefaces.model.UploadedFile;
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
    
    @ManagedProperty(value = "#{detailATService}")
    private IDetailATService detailATService;
    
    @ManagedProperty(value = "#{documentDetailATService}")
    private IDocumentDetailAtService documentDetailAtService;
    
    private Module module = new Module();
    private Integer idChantier;
    private Integer idSalarie;
    private String msg;
    private AccidentTravail accidentTravail;
    private Chantier chantier;
    private Salarie salarie;
    private DetailAT detailAT=new DetailAT();
    private DocumentDetailAt documentDetailAt;
    private UploadedFile uploadedFile;
    private String selectedDoc;

    
    
    private List<AccidentTravail> accidentTravails = new ArrayList<AccidentTravail>();
    private List<Chantier> chantiers = new ArrayList<Chantier>();
    private List<Salarie> salaries = new ArrayList<Salarie>();
    private List<DetailAT> detailATs = new ArrayList<DetailAT>();
    private List<DocumentDetailAt> documentDetailAts = new ArrayList<DocumentDetailAt>();

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

    public IDetailATService getDetailATService() {
        return detailATService;
    }

    public void setDetailATService(IDetailATService detailATService) {
        this.detailATService = detailATService;
    }

    public DetailAT getDetailAT() {
        return detailAT;
    }

    public void setDetailAT(DetailAT detailAT) {
        this.detailAT = detailAT;
    }

    public List<DetailAT> getDetailATs() {
        return detailATs;
    }

    public void setDetailATs(List<DetailAT> detailATs) {
        this.detailATs = detailATs;
    }

    public DocumentDetailAt getDocumentDetailAt() {
        return documentDetailAt;
    }

    public void setDocumentDetailAt(DocumentDetailAt documentDetailAt) {
        this.documentDetailAt = documentDetailAt;
    }

    public List<DocumentDetailAt> getDocumentDetailAts() {
        return documentDetailAts;
    }

    public void setDocumentDetailAts(List<DocumentDetailAt> documentDetailAts) {
        this.documentDetailAts = documentDetailAts;
    }

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public IDocumentDetailAtService getDocumentDetailAtService() {
        return documentDetailAtService;
    }

    public void setDocumentDetailAtService(IDocumentDetailAtService documentDetailAtService) {
        this.documentDetailAtService = documentDetailAtService;
    }

    public String getSelectedDoc() {
        return selectedDoc;
    }

    public void setSelectedDoc(String selectedDoc) {
        this.selectedDoc = selectedDoc;
    }

    
    
    
    
    
    
    
    @PostConstruct
    public void init() {
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication(); 
        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        chantierService = ctx.getBean(ChantierService.class);
        salarieService  = ctx.getBean(SalarieServiceIn.class);
        accidentTravailService = ctx.getBean(IAccidentTravailService.class);   
        detailATService = ctx.getBean(IDetailATService.class);        
        documentDetailAtService = ctx.getBean(IDocumentDetailAtService.class);        
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
        
        
        Long id =Long.valueOf(-1);
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        Map<String, String> requestParameters = externalContext.getRequestParameterMap();
        if (requestParameters.containsKey("atId")) {
            id = Long.valueOf(requestParameters.get("atId"));
            accidentTravail = accidentTravailService.allAccidentTravailById(id);
            detailATs = detailATService.allDetailATByAtId(id);
            if(detailATs.size()>0){
                detailAT = detailATs.get(0);
            }
            if(detailAT.getId()>0){
                try {
                    System.out.println("MB---------> ma.bservices.mb.AccidentTravailMb.init() : "+detailAT.toString());
                    documentDetailAts = documentDetailAtService.allDocumentDetailAtByIdDetailAt(detailAT.getId());
                    
                } catch (Exception e) {
                    System.out.println("MB---> Erreur de chargement les document  car "+e.getMessage());
                }
                    
            } 
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
    public void redirectEnginDetAT(AccidentTravail a) throws IOException {
        System.out.println("redirect");
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect(ec.getRequestContextPath() + "/at/detailAt.xhtml?faces-redirect=true&atId=" + a.getId());
        
    }  
    
    public void saveOrUpdateDetailAt(){
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            if(detailAT.getId() != null){
                detailATService.editDetailAT(detailAT);
            }else{
                detailAT.setAccidentTravail(accidentTravail);
                detailATService.editDetailAT(detailAT);
            }
            context.addMessage(null, new FacesMessage("Rapport enregistré", ""));
            
        } catch (Exception e) {
            System.out.println("Ereur d'enregistrement detail AT car "+e.getMessage());
            context.addMessage(null, new FacesMessage("Rapport non enregistré car"+e.getMessage(), ""));
        }
    }
    
    public void prepAddDocumentDeatilAt(){
        documentDetailAt = new DocumentDetailAt();
    }
    
    public void uploader() throws IOException {
                System.out.println("MB uploader -------> 1");

        // String chemin = TgccFileManager.getCheminFichier("Documents Engin");
        String chemin = ConstanteMb.getRepertoire() + "/files/docsDetAT";
        Path folder = Paths.get(chemin);
        Files.createDirectories(folder);
        if (uploadedFile == null) {
            System.out.println("MB uploader -------> 2");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, Message.STRING_CHARGE_DOCUMENT, Message.STRING_CHARGE_DOCUMENT));

        } else if (uploadedFile.getFileName().equals("")) {
                System.out.println("MB uploader -------> 3");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, Message.STRING_CHARGE_DOCUMENT, Message.STRING_CHARGE_DOCUMENT));
        } else {
                System.out.println("MB uploader -------> 4");
            String filename = FilenameUtils.getBaseName(uploadedFile.getFileName());
            String extension = FilenameUtils.getExtension(uploadedFile.getFileName());
            
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
                    Date date = new Date(); 
                    String nomFichier = detailAT.getId()+"_"+dateFormat.format(date);

            if (!"pdf".equals(extension)) {
                System.out.println("MB uploader -------> 5");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, Message.STRING_CHARGE_DOCUMENT_PDF, Message.STRING_CHARGE_DOCUMENT_PDF));
            } else {
                System.out.println("MB uploader -------> 6");
                Path file = Files.createTempFile(folder, filename, "." + extension);

                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                try (InputStream input = uploadedFile.getInputstream()) {
                System.out.println("MB uploader -------> 7");
                    Files.copy(input, file, StandardCopyOption.REPLACE_EXISTING);
                    documentDetailAt.setChemin(chemin + "/" +file.getFileName());
                    documentDetailAt.setCreePar(auth.getPrincipal().toString());
                    documentDetailAt.setDateCreation(new Date());
                    documentDetailAt.setDetailAT(detailAT);
                    documentDetailAtService.addDocumentDetailAt(documentDetailAt);
                    /*if(documentDetailAt.getNbrjour()>0){
                        System.out.println("MB uploader -------> 8");
                                if(detailAT.getNbrJour()!=null){
                        System.out.println("MB uploader -------> 9");
                                    detailAT.setNbrJour(detailAT.getNbrJour()+documentDetailAt.getNbrjour());
                                }
                                else{
                        System.out.println("MB uploader -------> 10");
                            detailAT.setNbrJour(documentDetailAt.getNbrjour());
                        }
                        detailATService.editDetailAT(detailAT);
                    }*/
                System.out.println("MB uploader -------> 8");
                    documentDetailAts = documentDetailAtService.allDocumentDetailAtByIdDetailAt(detailAT.getId()); 
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, Message.STRING_CHARGE_DOCUMENT_DONE, Message.STRING_CHARGE_DOCUMENT_DONE));
                }
            }
        }
    }
    
    public void delete(DocumentDetailAt de) throws IOException {

        FacesContext context = FacesContext.getCurrentInstance();
        try {
            if(de.getNbrjour()>0){
                        detailAT.setNbrJour(detailAT.getNbrJour()-de.getNbrjour());
                        detailATService.editDetailAT(detailAT);
                    }
            documentDetailAtService.remouveDocumentDetailAt(de);
            documentDetailAts = documentDetailAtService.allDocumentDetailAtByIdDetailAt(detailAT.getId()); 
            context.addMessage(null, new FacesMessage("" + Message.DELETE_DOCUMENT, Message.DELETE_DOCUMENT));
        } catch (Exception e) {
                context.addMessage(null, new FacesMessage("Ereur de suppression du fichier car"+e.getMessage(), ""));
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
     public void downLoad(DocumentDetailAt d) throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();
         try {
                if (d != null) {
                    if (d.getChemin() != null) {
                        telecharger_fichier(d.getChemin());
                    }
                }
         } catch (Exception e) {
            System.out.println("Ereur de telechargement du fichier "+e.getMessage());
            context.addMessage(null, new FacesMessage("Ereur de télèchargement du fichier car"+e.getMessage(), ""));
         }

    }
    public void telecharger_fichier(String chemin) throws FileNotFoundException, IOException {

        if (chemin != null && !"".equals(chemin)) {

            FacesContext context = FacesContext.getCurrentInstance();

            File file = new File(chemin);

            HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
            response.reset();
            response.setBufferSize(DEFAULT_BUFFER_SIZE);
            response.setContentType("application/pdf");
            response.setHeader("Content-Length", String.valueOf(file.length()));
            response.setHeader("Content-Disposition", "attachment;filename=\""
                    + file.getName() + "\"");
            BufferedInputStream input = null;
            BufferedOutputStream output = null;
            try {
                input = new BufferedInputStream(new FileInputStream(file),
                        DEFAULT_BUFFER_SIZE);
                output = new BufferedOutputStream(response.getOutputStream(),
                        DEFAULT_BUFFER_SIZE);
                byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
                int length;
                while ((length = input.read(buffer)) > 0) {
                    output.write(buffer, 0, length);
                }
            } finally {
                if (input != null) {
                    input.close();
                    output.close();
                }
            }
            context.responseComplete();
        }

    }
}
