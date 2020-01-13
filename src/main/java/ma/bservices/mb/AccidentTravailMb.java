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
import java.util.Calendar;
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
import ma.bservices.beans.CertificatAt;
import ma.bservices.beans.Chantier;
import ma.bservices.beans.Contrat;
import ma.bservices.beans.DetailAT;
import ma.bservices.beans.DocumentAt;
import ma.bservices.beans.DocumentDetailAt;
import ma.bservices.beans.DocumentImprimeDetailAt;
import ma.bservices.beans.QuittanceAt;
import ma.bservices.beans.Salarie;
import ma.bservices.beans.Utilisateur;
import ma.bservices.mb.services.ConstanteMb;
import ma.bservices.mb.services.Evol_ChantierMb;
import ma.bservices.mb.services.Module;
import ma.bservices.services.ContratService;
import ma.bservices.services.IAccidentTravailService;
import ma.bservices.services.ICertificatAtService;
import ma.bservices.services.IDetailATService;
import ma.bservices.services.IDocumentAtService;
import ma.bservices.services.IDocumentDetailAtService;
import ma.bservices.services.IDocumentImprimeDetailAtService;
import ma.bservices.services.IQuittanceAtService;
import ma.bservices.services.SalarieServiceIn;
import ma.bservices.tgcc.pdf.GenRapportAT;
import ma.bservices.tgcc.service.Engin.ChantierService;
import ma.bservices.tgcc.service.Engin.UtilisateurService;
import ma.bservices.tgcc.service.SendEmail;
import static org.apache.catalina.connector.InputBuffer.DEFAULT_BUFFER_SIZE;
import org.apache.commons.io.FilenameUtils;
import org.primefaces.model.UploadedFile;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
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
    
    @ManagedProperty(value = "#{documentImprimeDetailATService}")
    private IDocumentImprimeDetailAtService documentImprimeDetailAtService;
    
    @ManagedProperty(value = "#{documentAtService}")
    private IDocumentAtService documentAtService;
    
    @ManagedProperty(value = "#{certificatAtService}")
    private ICertificatAtService certificatAtService;
    
    @ManagedProperty(value = "#{quittanceAtService}")
    private IQuittanceAtService quittanceAtService;
    
    @ManagedProperty(value = "#{utilisateurService}")
    private UtilisateurService utilisateurService;
    
    @ManagedProperty(value = "#{contratService}")
    private ContratService contratService;
    
    boolean isAdmin = false, valideRH = false, valideQhse = false, isRH = false, isQhse = false 
            ,selectGuerison = false,selectConsolidation = false ,selectReprise = false  ;
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
    private UploadedFile uploadedFileAt;
    private UploadedFile uploadedFileCertificat;
    private UploadedFile uploadedFileQuitance;
    private String selectedDoc;
    private Date verifDateAt = new Date();
    private Contrat lastContratSal = new Contrat();
    
    private QuittanceAt quittanceAt;
    private List<QuittanceAt> quittanceAts;
    
    private CertificatAt certificatAt;
    private List<CertificatAt> certificatAts;
    
    private DocumentAt documentAt;
    private List<DocumentAt> documentAts;
    
    private Utilisateur utilisateur;
    

    
    
    private List<AccidentTravail> accidentTravails = new ArrayList<AccidentTravail>();
    private List<Chantier> chantiers = new ArrayList<Chantier>();
    private List<Salarie> salaries = new ArrayList<Salarie>();
    private List<DetailAT> detailATs = new ArrayList<DetailAT>();
    private List<Contrat> contrats = new ArrayList<Contrat>();
    private List<DocumentDetailAt> documentDetailAts = new ArrayList<DocumentDetailAt>();
    private List<DocumentImprimeDetailAt> documentImprimeDetailAts = new ArrayList<DocumentImprimeDetailAt>();

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

    public IDocumentImprimeDetailAtService getDocumentImprimeDetailAtService() {
        return documentImprimeDetailAtService;
    }

    public void setDocumentImprimeDetailAtService(IDocumentImprimeDetailAtService documentImprimeDetailAtService) {
        this.documentImprimeDetailAtService = documentImprimeDetailAtService;
    }

    public List<DocumentImprimeDetailAt> getDocumentImprimeDetailAts() {
        return documentImprimeDetailAts;
    }

    public void setDocumentImprimeDetailAts(List<DocumentImprimeDetailAt> documentImprimeDetailAts) {
        this.documentImprimeDetailAts = documentImprimeDetailAts;
    }

    public QuittanceAt getQuittanceAt() {
        return quittanceAt;
    }

    public void setQuittanceAt(QuittanceAt quittanceAt) {
        this.quittanceAt = quittanceAt;
    }

    public List<QuittanceAt> getQuittanceAts() {
        return quittanceAts;
    }

    public void setQuittanceAts(List<QuittanceAt> quittanceAts) {
        this.quittanceAts = quittanceAts;
    }

    public CertificatAt getCertificatAt() {
        return certificatAt;
    }

    public void setCertificatAt(CertificatAt certificatAt) {
        this.certificatAt = certificatAt;
    }

    public List<CertificatAt> getCertificatAts() {
        return certificatAts;
    }

    public void setCertificatAts(List<CertificatAt> certificatAts) {
        this.certificatAts = certificatAts;
    }

    public DocumentAt getDocumentAt() {
        return documentAt;
    }

    public void setDocumentAt(DocumentAt documentAt) {
        this.documentAt = documentAt;
    }

    public List<DocumentAt> getDocumentAts() {
        return documentAts;
    }

    public void setDocumentAts(List<DocumentAt> documentAts) {
        this.documentAts = documentAts;
    }

    public IDocumentAtService getDocumentAtService() {
        return documentAtService;
    }

    public void setDocumentAtService(IDocumentAtService documentAtService) {
        this.documentAtService = documentAtService;
    }

    public ICertificatAtService getCertificatAtService() {
        return certificatAtService;
    }

    public void setCertificatAtService(ICertificatAtService certificatAtService) {
        this.certificatAtService = certificatAtService;
    }

    public IQuittanceAtService getQuittanceAtService() {
        return quittanceAtService;
    }

    public void setQuittanceAtService(IQuittanceAtService quittanceAtService) {
        this.quittanceAtService = quittanceAtService;
    }

    public UploadedFile getUploadedFileAt() {
        return uploadedFileAt;
    }

    public void setUploadedFileAt(UploadedFile uploadedFileAt) {
        this.uploadedFileAt = uploadedFileAt;
    }

    public UploadedFile getUploadedFileCertificat() {
        return uploadedFileCertificat;
    }

    public void setUploadedFileCertificat(UploadedFile uploadedFileCertificat) {
        this.uploadedFileCertificat = uploadedFileCertificat;
    }

    public UploadedFile getUploadedFileQuitance() {
        return uploadedFileQuitance;
    }

    public void setUploadedFileQuitance(UploadedFile uploadedFileQuitance) {
        this.uploadedFileQuitance = uploadedFileQuitance;
    }

    public boolean isValideRH() {
        return valideRH;
    }

    public void setValideRH(boolean valideRH) {
        this.valideRH = valideRH;
    }

    public boolean isValideQhse() {
        return valideQhse;
    }

    public void setValideQhse(boolean valideQhse) {
        this.valideQhse = valideQhse;
    }

    public Date getVerifDateAt() {
        return verifDateAt;
    }

    public void setVerifDateAt(Date verifDateAt) {
        this.verifDateAt = verifDateAt;
    }

    public UtilisateurService getUtilisateurService() {
        return utilisateurService;
    }

    public void setUtilisateurService(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    public boolean isIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public boolean isIsRH() {
        return isRH;
    }

    public void setIsRH(boolean isRH) {
        this.isRH = isRH;
    }

    public boolean isIsQhse() {
        return isQhse;
    }

    public void setIsQhse(boolean isQhse) {
        this.isQhse = isQhse;
    }

    public boolean isSelectGuerison() {
        return selectGuerison;
    }

    public void setSelectGuerison(boolean selectGuerison) {
        this.selectGuerison = selectGuerison;
    }

    public Contrat getLastContratSal() {
        return lastContratSal;
    }

    public void setLastContratSal(Contrat lastContratSal) {
        this.lastContratSal = lastContratSal;
    }

    public ContratService getContratService() {
        return contratService;
    }

    public void setContratService(ContratService contratService) {
        this.contratService = contratService;
    }

    public List<Contrat> getContrats() {
        return contrats;
    }

    public void setContrats(List<Contrat> contrats) {
        this.contrats = contrats;
    }

    public boolean isSelectConsolidation() {
        return selectConsolidation;
    }

    public void setSelectConsolidation(boolean selectConsolidation) {
        this.selectConsolidation = selectConsolidation;
    }

    public boolean isSelectReprise() {
        return selectReprise;
    }

    public void setSelectReprise(boolean selectReprise) {
        this.selectReprise = selectReprise;
    }

    
    
    
    
    
    
    
    
    
    @PostConstruct
    public void init() {
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication(); 
        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        
        utilisateurService = ctx.getBean(UtilisateurService.class);
        chantierService = ctx.getBean(ChantierService.class);
        salarieService  = ctx.getBean(SalarieServiceIn.class);
        accidentTravailService = ctx.getBean(IAccidentTravailService.class);   
        detailATService = ctx.getBean(IDetailATService.class);        
        documentDetailAtService = ctx.getBean(IDocumentDetailAtService.class);  
        detailATService = ctx.getBean(IDetailATService.class);  
        certificatAtService = ctx.getBean(ICertificatAtService.class);  
        quittanceAtService = ctx.getBean(IQuittanceAtService.class);  
        contratService = Module.ctx.getBean(ContratService.class);   
        
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        Evol_ChantierMb evol_ChantierMb = (Evol_ChantierMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "evol_chantierMb");
        
        viderVar();
        
        utilisateur = utilisateurService.getUsersByLogin(auth.getPrincipal().toString().trim());
        
        for (GrantedAuthority grantedAuthority : auth.getAuthorities()) {
            if ("admin".equals(grantedAuthority.getAuthority())) { 
                chantiers = evol_ChantierMb.getChantiers();
                isAdmin = true;
                break;
            }
        }
        for (GrantedAuthority grantedAuthority : auth.getAuthorities()) {
            
            if ("valider at rh".equals(grantedAuthority.getAuthority())) { 
                isRH=Boolean.TRUE;
                break;
            }
        }
        for (GrantedAuthority grantedAuthority : auth.getAuthorities()) {
            if ("valider at qhse".equals(grantedAuthority.getAuthority())) { 
                isQhse=Boolean.TRUE;
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
            }else{
                try {
                    if(accidentTravail != null){
                        detailAT = new DetailAT();
                        detailAT.setAccidentTravail(accidentTravail);
                        detailATService.addDetailAT(detailAT);
                        detailATs = detailATService.allDetailATByAtId(id);
                        detailAT = detailATs.get(0);
                    }
                } catch (Exception e) {
                    System.out.println("Erreur de creation detail At car "+e.getMessage());
                }
            }
            try {
                if((detailAT.getLieuPrecis().trim().length()<1 )|| (detailAT.getLieuPrecis()== null)){
                    detailAT.setLieuPrecis(accidentTravail.getLieu());
                }
            } catch (Exception e) {
                    System.out.println("Erreur d'initiel de detail At car "+e.getMessage());
            }
            try {
                    valideRH = (detailAT.getValideRH()!=null)?detailAT.getValideRH():false;
                } catch (Exception e) {
                    System.out.println("Erreur de charger valideRH car "+e.getMessage());
                }
                try {
                    valideQhse = (detailAT.getValideQhse() !=null)?detailAT.getValideQhse():false;
                } catch (Exception e) {
                    System.out.println("Erreur de charger valideQhse car "+e.getMessage());
                }
            if(accidentTravail.getSalarie()!=null){
                contrats = contratService.listeContratsSalarie(0, Integer.parseInt(contratService.nombreContratsSalarie(accidentTravail.getSalarie().getId()).toString()), accidentTravail.getSalarie().getId());
                if(contrats.size()>0){
                    lastContratSal = contrats.get(0);
                }
            }
            
        }
        
    }
    
    public void listSalarieByChantier(){
        if(idChantier>0){
            salaries = salarieService.listSalarieActifByChantierId(idChantier);
        }
    }
    public void selectSalarieByChantier(){
        if (idSalarie>0) {
            salarie = salarieService.getSalarieByID(idSalarie);
            accidentTravail.setTelContacter(salarie.getGsm());
        } 
    }
    public void addAT(){
        
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            if(idChantier>0){
                chantier=chantierService.findById(idChantier);
            }
            accidentTravail.setChantier(chantier);
            accidentTravail.setSalarie(salarie);
            accidentTravail.setCreePar(utilisateur);
            accidentTravail.setDateCreation(new Date());
            Long id_At = accidentTravailService.addAccidentTravail(accidentTravail);
            
            if(id_At>0){
            //email
            ApplicationContext ctx = new ClassPathXmlApplicationContext("mail.xml");
            String dateAccidentEmail = new SimpleDateFormat("yyyy-MM-dd").format(accidentTravail.getDateAccident());
            SendEmail sm = (SendEmail) ctx.getBean("sendEmail");
            String objetMail="Accident de travail "+((accidentTravail.getMortel())?"(Mortel)":"")+" / "+chantier.getCode().trim().toUpperCase();
            String corpMail="Bonjour,\nNous vous informons là survenu d'accident de travail à M."+ salarie.getNom().trim()+" "+salarie.getPrenom().trim()
                    +" |CIN: "+salarie.getCin().trim()+" |MATRICULE: "+salarie.getMatricule().trim()
                    +" / "+salarie.getFonction().getFonction().trim() +" au niveau chantier "+chantier.getCode().trim().toUpperCase()
                    +" le "+dateAccidentEmail+".\n"
                    +"Circonstances : "+accidentTravail.getDescription().trim()
                    +"\nCordialement,";
            List<String> listDestinatairesMail = new ArrayList<String>();
            //listDestinatairesMail.add("at_notification@tgcc.ma");
            if(chantier.getEmail() != null){
                listDestinatairesMail.add(chantier.getEmail().trim());
            }
            listDestinatairesMail.add("yassine.jeddi@tgcc.ma");
            //listDestinatairesMail.add("hanane.noam@tgcc.ma");
            
            
            if (listDestinatairesMail != null && !listDestinatairesMail.isEmpty()) {
                for (String m : listDestinatairesMail) {
                    sm.sendMail("notification@tgcc.ma", m, objetMail, corpMail);
                }
            }
            context.addMessage(null, new FacesMessage("AT enregistré", ""));
            
        }else{
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Erreur!","AT non enregistré"));
                
            }
           
        } catch (Exception e) { 
            System.out.println("::::::::::>ERREUR  accidentTravail "+e.getMessage());
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Erreur!","AT non enregistré"));
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
        if(isAdmin){
            accidentTravails = accidentTravailService.allAccidentTravail(); 
        }else{
            accidentTravails = accidentTravailService.allAccidentTravailByListChantier(chantiers);
        }
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
    
    public void imprimer(){
        GenRapportAT rp = new GenRapportAT();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date(); 
        try {
            String nomFichier = detailAT.getId()+"_"+dateFormat.format(date);
            String chemin = rp.generationRapportAt("/opt/files/docsDetAT/", ""+nomFichier, detailAT);
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();

            DocumentImprimeDetailAt d = new DocumentImprimeDetailAt();
            d.setChemin(chemin);
            d.setDetailAT(detailAT);
            d.setCreePar(auth.getPrincipal().toString());
            d.setDateCreation(new Date());
            
            documentImprimeDetailAtService.addDocumentDetailAt(d);
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, Message.STRING_CHARGE_DOCUMENT_DONE, Message.STRING_CHARGE_DOCUMENT_DONE));
        } catch (Exception e) {
            System.out.println("Erreur de generation rapport AT car "+e.getMessage());
        }
        documentImprimeDetailAts = documentImprimeDetailAtService.allDocumentDetailAtByIdDetailAt(detailAT.getId());
        
        // documentImprimeDetailAtService 
    }
    
    public void prepAddDocumentDeatilAt(){
        documentDetailAt = new DocumentDetailAt();
    }
    
    public void uploader() throws IOException {

        // String chemin = TgccFileManager.getCheminFichier("Documents Engin");
        String chemin = ConstanteMb.getRepertoire() + "/files/docsDetAT";
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
                    documentDetailAt.setChemin(chemin + "/" +file.getFileName());
                    documentDetailAt.setCreePar(auth.getPrincipal().toString());
                    documentDetailAt.setDateCreation(new Date());
                    documentDetailAt.setDetailAT(detailAT);
                    documentDetailAtService.addDocumentDetailAt(documentDetailAt);
                    documentDetailAts = documentDetailAtService.allDocumentDetailAtByIdDetailAt(detailAT.getId()); 
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, Message.STRING_CHARGE_DOCUMENT_DONE, Message.STRING_CHARGE_DOCUMENT_DONE));
                }
            }
        }
    }
    
    public void delete(DocumentDetailAt de) throws IOException {

        FacesContext context = FacesContext.getCurrentInstance();
        try {
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
    public void chargerDocumentDetailAt(){
        if(detailAT.getId()>0){
                    try {
                        documentDetailAts = documentDetailAtService.allDocumentDetailAtByIdDetailAt(detailAT.getId());
                    } catch (Exception e) {
                        System.out.println("MB---> Erreur de chargement les document  car "+e.getMessage());
                    }
                }  
    }
    public void chargerDocumentImprimerDetailAt(){
        try {
            
        if(detailAT.getId()>0){
                    try {
                        documentImprimeDetailAts = documentImprimeDetailAtService.allDocumentDetailAtByIdDetailAt(detailAT.getId());
                    } catch (Exception e) {
                        System.out.println("MB---> Erreur de chargement les document  car "+e.getMessage());
                    }

                } 
        } catch (Exception e) {
            System.out.println("Erreur de chargement liste des document imprimer car "+e.getMessage());
        } 
    }
    
    
    
    /**********
     * Gestion Quittance
     */
    
    public void prepQuitance(){
        quittanceAt = new QuittanceAt();
        quittanceAt.setMtnPaye(0);
        quittanceAt.setNbjPaye(0);
        quittanceAt.setAt(accidentTravail);
    }
    public void chargeQuitance(){
        quittanceAts=quittanceAtService.allQuittanceAtByAt(accidentTravail);
    }
    public void addQuitance() throws IOException{
        
        // String chemin = TgccFileManager.getCheminFichier("Documents Engin");
        String chemin = ConstanteMb.getRepertoire() + "/files/docsDetAT/Quittances";
        Path folder = Paths.get(chemin);
        Files.createDirectories(folder);
        if (uploadedFileQuitance == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, Message.STRING_CHARGE_DOCUMENT, Message.STRING_CHARGE_DOCUMENT));
        } else if (uploadedFileQuitance.getFileName().equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, Message.STRING_CHARGE_DOCUMENT, Message.STRING_CHARGE_DOCUMENT));
        } else {
            String filename = FilenameUtils.getBaseName(uploadedFileQuitance.getFileName());
            String extension = FilenameUtils.getExtension(uploadedFileQuitance.getFileName());
            
            if (!"pdf".equals(extension)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, Message.STRING_CHARGE_DOCUMENT_PDF, Message.STRING_CHARGE_DOCUMENT_PDF));
            } else {
                Path file = Files.createTempFile(folder, filename, "." + extension);
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                try (InputStream input = uploadedFileQuitance.getInputstream()) {
                        Files.copy(input, file, StandardCopyOption.REPLACE_EXISTING);
                        quittanceAt.setCreePar(auth.getPrincipal().toString());
                        quittanceAt.setDateCreation(new Date());
                        quittanceAt.setChemin(chemin + "/" +file.getFileName());
                        quittanceAtService.addQuittanceAt(quittanceAt);
                        chargeQuitance();
                         if(quittanceAt.getNbjPaye()>0){
                            try {
                                if(accidentTravail.getNbjPaye()==null){
                                    accidentTravail.setNbjPaye(0);
                                }
                                accidentTravail.setNbjPaye(accidentTravail.getNbjPaye()+quittanceAt.getNbjPaye());
                                accidentTravailService.editAccidentTravail(accidentTravail);
                            } catch (Exception e) {
                                System.out.println("Erreur d'ajouter nombre des jours Paye au AT car "+e.getMessage());
                            }
                        }
                        if(quittanceAt.getMtnPaye() >0){
                            try {
                                if(accidentTravail.getMtnPaye() ==null){
                                    accidentTravail.setMtnPaye(0.0);
                                }
                                accidentTravail.setMtnPaye(accidentTravail.getMtnPaye()+quittanceAt.getMtnPaye());
                                accidentTravailService.editAccidentTravail(accidentTravail);
                                
                            } catch (Exception e) {
                                System.out.println("Erreur d'ajouter  MTN au AT car "+e.getMessage());
                            }
                        }
                    }
            }
        }
    }
    public void editQuitance() throws IOException{
       
        // String chemin = TgccFileManager.getCheminFichier("Documents Engin");
        String chemin = ConstanteMb.getRepertoire() + "/files/docsDetAT/Quittances";
        Path folder = Paths.get(chemin);
        Files.createDirectories(folder);
        if (uploadedFileQuitance == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, Message.STRING_CHARGE_DOCUMENT, Message.STRING_CHARGE_DOCUMENT));
        } else if (uploadedFileQuitance.getFileName().equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, Message.STRING_CHARGE_DOCUMENT, Message.STRING_CHARGE_DOCUMENT));
        } else {
            String filename = FilenameUtils.getBaseName(uploadedFileQuitance.getFileName());
            String extension = FilenameUtils.getExtension(uploadedFileQuitance.getFileName());
            
            if (!"pdf".equals(extension)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, Message.STRING_CHARGE_DOCUMENT_PDF, Message.STRING_CHARGE_DOCUMENT_PDF));
            } else {
                Path file = Files.createTempFile(folder, filename, "." + extension);
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                try (InputStream input = uploadedFileQuitance.getInputstream()) {
                        Files.copy(input, file, StandardCopyOption.REPLACE_EXISTING);
                        quittanceAt.setCreePar(auth.getPrincipal().toString());
                        quittanceAt.setDateCreation(new Date());
                        quittanceAt.setChemin(chemin + "/" +file.getFileName());
                        quittanceAtService.editQuittanceAt(quittanceAt);
                        chargeQuitance();
                    }
            }
        }
    }
    public void remouveQuitance( QuittanceAt q){
        if(q!=null){
            quittanceAt =q;
            try {
                quittanceAtService.remouvQuittanceAt(quittanceAt);
                chargeQuitance();
                try {
                        accidentTravail.setNbjPaye(accidentTravail.getNbjPaye()-quittanceAt.getNbjPaye());
                        accidentTravail.setMtnPaye(accidentTravail.getMtnPaye()-quittanceAt.getMtnPaye());
                        accidentTravailService.editAccidentTravail(accidentTravail);
                } catch (Exception e) {
                    System.out.println("Erreur d'ajouter nombre des jours Paye au AT car "+e.getMessage());
                }
            } catch (Exception e) {
                System.out.println("Erreur du suppression Quitance car "+e.getMessage());
            }
        }
    }
    
    
    public void ChargerAccidentForGestion(AccidentTravail a){
        accidentTravail=a;
        certificatAts = certificatAtService.allcertificatAtByAt(accidentTravail);
    }
    /**********
     * Gestion certificat
     */
    
    public void prepCertificat(){
        System.out.println("ma.bservices.mb.AccidentTravailMb.prepCertificat()");
        certificatAt = new CertificatAt();
        certificatAt.setNbjArret(0);
        certificatAt.setAt(accidentTravail);
    }
    public void chargeCertificat(){
        System.out.println("ma.bservices.mb.AccidentTravailMb.chargeCertificat()");
        certificatAts = certificatAtService.allcertificatAtByAt(accidentTravail);
    }
    public void addCertificat() throws IOException{
        // String chemin = TgccFileManager.getCheminFichier("Documents Engin");
        String chemin = ConstanteMb.getRepertoire() + "/files/docsDetAT/Certificats";
        Path folder = Paths.get(chemin);
        Files.createDirectories(folder);
        if (uploadedFileCertificat == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, Message.STRING_CHARGE_DOCUMENT, Message.STRING_CHARGE_DOCUMENT));
        } else if (uploadedFileCertificat.getFileName().equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, Message.STRING_CHARGE_DOCUMENT, Message.STRING_CHARGE_DOCUMENT));
        } else {
            String filename = FilenameUtils.getBaseName(uploadedFileCertificat.getFileName());
            String extension = FilenameUtils.getExtension(uploadedFileCertificat.getFileName());
            
            if (!"pdf".equals(extension)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, Message.STRING_CHARGE_DOCUMENT_PDF, Message.STRING_CHARGE_DOCUMENT_PDF));
            } else {
                Path file = Files.createTempFile(folder, filename, "." + extension);
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                try (InputStream input = uploadedFileCertificat.getInputstream()) {
                        Files.copy(input, file, StandardCopyOption.REPLACE_EXISTING);
                        certificatAt.setCreePar(auth.getPrincipal().toString());
                        certificatAt.setDateCreation(new Date());
                        certificatAt.setChemin(chemin + "/" +file.getFileName());
                        certificatAtService.addCertificatAt(certificatAt);
                        chargeCertificat();
                        if(certificatAt.getNbjArret()>0){
                            try {
                                if(accidentTravail.getNbjArret()==null){
                                    accidentTravail.setNbjArret(0);
                                }
                                accidentTravail.setNbjArret(accidentTravail.getNbjArret()+certificatAt.getNbjArret());
                                
                                Calendar calendar = Calendar.getInstance();
                                if(accidentTravail.getDateRetour() != null){
                                    calendar.setTime(accidentTravail.getDateRetour());
                                }else{
                                    calendar.setTime(accidentTravail.getDateAccident());
                                }
                                calendar.add(Calendar.DATE, certificatAt.getNbjArret());
                                accidentTravail.setDateRetour(calendar.getTime());
                                if("Initial".equals(certificatAt.getTypeCertificat())){
                                    accidentTravail.setCertificatInitial(Boolean.TRUE);
                                }
                                accidentTravailService.editAccidentTravail(accidentTravail);
                                
                            } catch (Exception e) {
                                System.out.println("Erreur d'ajouter nombre des jours arreter au AT car "+e.getMessage());
                            }
                        }
                        if("Reprise de travail".equals(certificatAt.getTypeCertificat())){
                            System.out.println("ma.bservices.mb.AccidentTravailMb.addCertificat()======> Reprise de travail");
                               accidentTravail.setDateRetourReel(certificatAt.getDateReprise());
                               accidentTravailService.editAccidentTravail(accidentTravail);
                           }
                    }
            }
        }
    }
    public void editCertificat() throws IOException{
        
        // String chemin = TgccFileManager.getCheminFichier("Documents Engin");
        String chemin = ConstanteMb.getRepertoire() + "/files/docsDetAT/Certificats";
        Path folder = Paths.get(chemin);
        Files.createDirectories(folder);
        if (uploadedFileCertificat == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, Message.STRING_CHARGE_DOCUMENT, Message.STRING_CHARGE_DOCUMENT));
        } else if (uploadedFileCertificat.getFileName().equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, Message.STRING_CHARGE_DOCUMENT, Message.STRING_CHARGE_DOCUMENT));
        } else {
            String filename = FilenameUtils.getBaseName(uploadedFileCertificat.getFileName());
            String extension = FilenameUtils.getExtension(uploadedFileCertificat.getFileName());
            
            if (!"pdf".equals(extension)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, Message.STRING_CHARGE_DOCUMENT_PDF, Message.STRING_CHARGE_DOCUMENT_PDF));
            } else {
                Path file = Files.createTempFile(folder, filename, "." + extension);
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                try (InputStream input = uploadedFileCertificat.getInputstream()) {
                        Files.copy(input, file, StandardCopyOption.REPLACE_EXISTING);
                        certificatAt.setCreePar(auth.getPrincipal().toString());
                        certificatAt.setDateCreation(new Date());
                        certificatAt.setChemin(chemin + "/" +file.getFileName());
                        certificatAtService.editCertificatAt(certificatAt);
                        chargeCertificat();
                    }
            }
        }
    }
    public void remouveCertificat(CertificatAt c ){
        if(c!=null){
            certificatAt = c;
            try {
                if(certificatAt.getNbjArret()>0){
                    try {
                        if(accidentTravail.getNbjArret()!=null ){
                            accidentTravail.setNbjArret(accidentTravail.getNbjArret()-certificatAt.getNbjArret());
                        }
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(accidentTravail.getDateRetour());
                        calendar.add(Calendar.DATE,(-1)*certificatAt.getNbjArret());
                        accidentTravail.setDateRetour(calendar.getTime());
                        if("Initial".equals(certificatAt.getTypeCertificat())){
                                accidentTravail.setCertificatInitial(Boolean.FALSE);
                            }
                        if("Reprise de travail".equals(certificatAt.getTypeCertificat())){
                               accidentTravail.setDateRetourReel(null);
                           }
                        accidentTravailService.editAccidentTravail(accidentTravail);
                    } catch (Exception e) {
                        System.out.println("Erreur d'ajouter nombre des jours arreter au AT car "+e.getMessage());
                    }
                }
                
                certificatAtService.remouvCertificatAt(certificatAt);
                chargeCertificat();
            } catch (Exception e) {
                System.out.println("Erreur de suppression certificat car "+e.getMessage());
            }
        }
    }
    public void recuOriginalCertificat(){
        certificatAt.setRecuOriginal(Boolean.TRUE);
        certificatAtService.editCertificatAt(certificatAt);
        chargeCertificat();
    }
    
    /**********
     * Gestion Document AT
     */
    
    public void prepDocumentAt(){
        documentAt = new DocumentAt();
        documentAt.setAt(accidentTravail);
    }
    public void chargeDocumentAt(){
        documentAts = documentAtService.allDocumentAtByAt(accidentTravail);
    }
    public void addDocumentAt() throws IOException{
        // String chemin = TgccFileManager.getCheminFichier("Documents Engin");
        String chemin = ConstanteMb.getRepertoire() + "/files/docsDetAT/DocumentAt";
        Path folder = Paths.get(chemin);
        Files.createDirectories(folder);
        if (uploadedFileAt == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, Message.STRING_CHARGE_DOCUMENT, Message.STRING_CHARGE_DOCUMENT));
        } else if (uploadedFileAt.getFileName().equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, Message.STRING_CHARGE_DOCUMENT, Message.STRING_CHARGE_DOCUMENT));
        } else {
            String filename = FilenameUtils.getBaseName(uploadedFileAt.getFileName());
            String extension = FilenameUtils.getExtension(uploadedFileAt.getFileName());
            
            if (!"pdf".equals(extension)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, Message.STRING_CHARGE_DOCUMENT_PDF, Message.STRING_CHARGE_DOCUMENT_PDF));
            } else {
                Path file = Files.createTempFile(folder, filename, "." + extension);
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                try (InputStream input = uploadedFileAt.getInputstream()) {
                documentAt.setCreePar(auth.getPrincipal().toString());
                documentAt.setDateCreation(new Date());
                documentAt.setChemin(chemin + "/" +file.getFileName());
                documentAtService.addDocumentAt(documentAt);
                chargeDocumentAt();
                }
            }
        }
    }
    public void editDocumentAt(){
        documentAtService.editDocumentAt(documentAt);
        chargeDocumentAt();
    }
    public void remouveDocumentAt(){
        documentAtService.remouvDocumentAt(documentAt);
        chargeDocumentAt();
    }
    
     public void downLoadFichier(String s) throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();
         try {
                if (s != null) {
                        telecharger_fichier(s);
                }
         } catch (Exception e) {
            System.out.println("Ereur de telechargement du fichier "+e.getMessage());
            context.addMessage(null, new FacesMessage("Ereur de télèchargement du fichier car"+e.getMessage(), ""));
         }

    }
    public void recuCertificatOriginal(CertificatAt c){
        c.setRecuOriginal(Boolean.TRUE);
        certificatAtService.editCertificatAt(c);
    }
    public void validQhse(){
        FacesContext context = FacesContext.getCurrentInstance();
        chargerDocumentDetailAt();
        if(documentDetailAts.size()>0){
            detailAT.setValideQhse(Boolean.TRUE);
            detailAT.setUserValidQHSE(utilisateur);
            detailAT.setDateValidQhse(new Date());
            valideQhse= Boolean.TRUE;
            detailATService.editDetailAT(detailAT);
        }else{
            context.addMessage(null, new FacesMessage("Validation non effectuer, Merci d'ajouter un document(Photos /ou croquis)", ""));
        }
    }
    public void validRH(){
        detailAT.setValideRH(Boolean.TRUE);
        detailAT.setUserValidRH(utilisateur);
        detailAT.setDateValidRh(new Date());
        valideRH=Boolean.TRUE;
        detailATService.editDetailAT(detailAT);
    }
    public void changeSuiteDece(){
        if(detailAT.getDeces()){
            detailAT.setAtAvecArret(Boolean.FALSE);
            detailAT.setAtSansArret(Boolean.FALSE);
            detailAT.setPresqueAccident(Boolean.FALSE);
        }
    }
    public void changeSuiteAtArret(){
        if(detailAT.getAtAvecArret()){
            detailAT.setAtSansArret(Boolean.FALSE);
            detailAT.setPresqueAccident(Boolean.FALSE);
            detailAT.setDeces(Boolean.FALSE);
        }
    }
    public void changeSuiteAtSnArret(){
        if(detailAT.getAtSansArret()){
            detailAT.setAtAvecArret(Boolean.FALSE);
            detailAT.setPresqueAccident(Boolean.FALSE);
            detailAT.setDeces(Boolean.FALSE);
        }
    }
    public void changeSuitePresqAt(){
        if(detailAT.getPresqueAccident()){
            detailAT.setAtAvecArret(Boolean.FALSE);
            detailAT.setAtSansArret(Boolean.FALSE);
            detailAT.setDeces(Boolean.FALSE);
        }
    }
    public void changeTypeCertificat(){
        if("Guerison".equals(certificatAt.getTypeCertificat())){
            selectGuerison = true;
            selectConsolidation = false;
            selectReprise=false;
        }else if("Consolidation".equals(certificatAt.getTypeCertificat())){
            selectGuerison = false;
            selectConsolidation = true;
            selectReprise=false;
        }else if("Reprise de travail".equals(certificatAt.getTypeCertificat())){
            selectGuerison = false;
            selectConsolidation = false;
            selectReprise=true;
            
        }else {
            selectGuerison = false;
            selectConsolidation = false;
            selectReprise=false;
        }
    }
}
