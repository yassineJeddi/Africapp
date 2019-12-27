
package ma.bservices.mb;
 
 
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException; 
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;  
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;  
import java.io.File;
 
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption; 
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays; 
import java.util.Calendar;
import java.util.Date;
import java.util.List;  
import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped; 
import javax.faces.context.FacesContext; 
import ma.bservice.tgcc.Constante.Message;
import ma.bservices.beans.Chantier;
import ma.bservices.beans.Salarie;
import ma.bservices.mb.services.ConstanteMb;
import ma.bservices.mb.services.Evol_ChantierMb; 
import ma.bservices.mb.services.Module;
import ma.bservices.services.DossierMedicalService;
import ma.bservices.services.SalarieServiceIn;
import ma.bservices.services.VisiteService; 
import ma.bservices.tgcc.Entity.Antecedent;
import ma.bservices.tgcc.Entity.AntecedentProfessionnel;
import ma.bservices.tgcc.Entity.DocumentDossierMedical;
import ma.bservices.tgcc.Entity.DossierMedical;
import ma.bservices.tgcc.Entity.Visite;
import ma.bservices.tgcc.service.Engin.ChantierService; 
import org.apache.commons.io.FilenameUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;
import org.primefaces.context.RequestContext;  
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author BARAKA
 */
@Component
@ManagedBean(name = "dossMb")
@ViewScoped
public class DossierMedicalMB implements Serializable{
    
    @ManagedProperty(value = "#{chantierService}")
    private ChantierService chantierService;
    
    @ManagedProperty(value = "#{salarieServiceIn}")
    private SalarieServiceIn salarieService;
    
    @ManagedProperty(value = "#{dossierMedicalService}")
    private DossierMedicalService dossierMedicalService;
    
    @ManagedProperty(value = "#{visiteService}")
    private VisiteService visiteService;
    
    private List<Chantier> chantiers;
    private Chantier chantier = new Chantier();
    private int idChantier = 0;
    private int idStatut   = 0;
    private List<DossierMedical> dossiersMedicaux = new ArrayList<>();  
    
    private List<Salarie> salaries;
    private List<Visite> listaVisite;
    
    private Visite visiteToAdd;
    private Visite visiteToShow;
    
    private boolean isAdmin=false;
    private String  codeIds = null;
    
    private DossierMedical dosMedToAdd  = new DossierMedical();
    private DossierMedical dosMedToshow = new DossierMedical();
    private Salarie salarieToAdd = new Salarie();
    private Integer idSalarie = 0;
    
    private List<String> RISQUE = new ArrayList<>();
    private List<String> PERE = new ArrayList<>();
    private List<String> MERE = new ArrayList<>();
    private List<String> ENFANTS = new ArrayList<>();
    private List<String> FRERES = new ArrayList<>();
    private List<String> SOEURS = new ArrayList<>();
    private List<String> CONJOINT = new ArrayList<>();
    private List<String> ATCDMD = new ArrayList<>();
    private List<String> ATCDCH = new ArrayList<>();
    
    private List<AntecedentProfessionnel> antecedentProfessionnelHorsTGCC = new ArrayList<>();
    private List<Antecedent> antecedentRisque = new ArrayList<>();
    private List<Antecedent> antecedentAutre = new ArrayList<>();
    private List<Antecedent> antecedentATCDCH = new ArrayList<>();
    private List<Antecedent> antecedentATCDMD = new ArrayList<>();
    private List<Antecedent> listeTypeFichiers = new ArrayList<>();

    public List<Antecedent> getListeTypeFichiers() {
        return listeTypeFichiers;
    }

    public void setListeTypeFichiers(List<Antecedent> listeTypeFichiers) {
        this.listeTypeFichiers = listeTypeFichiers;
    }

    public List<Antecedent> getAntecedentATCDCH() {
        return antecedentATCDCH;
    }

    public void setAntecedentATCDCH(List<Antecedent> antecedentATCDCH) {
        this.antecedentATCDCH = antecedentATCDCH;
    }

    public List<Antecedent> getAntecedentATCDMD() {
        return antecedentATCDMD;
    }

    public void setAntecedentATCDMD(List<Antecedent> antecedentATCDMD) {
        this.antecedentATCDMD = antecedentATCDMD;
    } 
    
    private Antecedent antecedentToAdd = new Antecedent();

    public Antecedent getAntecedentToAdd() {
        return antecedentToAdd;
    }

    public void setAntecedentToAdd(Antecedent antecedentToAdd) {
        this.antecedentToAdd = antecedentToAdd;
    } 
    
    public List<AntecedentProfessionnel> getAntecedentProfessionnelHorsTGCC() {
        return antecedentProfessionnelHorsTGCC;
    }

    public void setAntecedentProfessionnelHorsTGCC(List<AntecedentProfessionnel> antecedentProfessionnelHorsTGCC) {
        this.antecedentProfessionnelHorsTGCC = antecedentProfessionnelHorsTGCC;
    }

    public List<Antecedent> getAntecedentRisque() {
        return antecedentRisque;
    }

    public void setAntecedentRisque(List<Antecedent> antecedentRisque) {
        this.antecedentRisque = antecedentRisque;
    }

    public List<Antecedent> getAntecedentAutre() {
        return antecedentAutre;
    }

    public void setAntecedentAutre(List<Antecedent> antecedentAutre) {
        this.antecedentAutre = antecedentAutre;
    }
     
    public List<String> getATCDMD() {
        return ATCDMD;
    }

    public void setATCDMD(List<String> ATCDMD) {
        this.ATCDMD = ATCDMD;
    }

    public List<String> getATCDCH() {
        return ATCDCH;
    }

    public void setATCDCH(List<String> ATCDCH) {
        this.ATCDCH = ATCDCH;
    }
    
    private List<DocumentDossierMedical> listaFichiers=null; 
    
    public List<String> getRISQUE() {
        return RISQUE;
    }

    public void setRISQUE(List<String> RISQUE) {
        this.RISQUE = RISQUE;
    } 
    public List<String> getPERE() {
        return PERE;
    }

    public void setPERE(List<String> PERE) {
        this.PERE = PERE;
    }

    public List<String> getMERE() {
        return MERE;
    }

    public void setMERE(List<String> MERE) {
        this.MERE = MERE;
    }

    public List<String> getENFANTS() {
        return ENFANTS;
    }

    public void setENFANTS(List<String> ENFANTS) {
        this.ENFANTS = ENFANTS;
    }

    public List<String> getFRERES() {
        return FRERES;
    }

    public void setFRERES(List<String> FRERES) {
        this.FRERES = FRERES;
    }

    public List<String> getSOEURS() {
        return SOEURS;
    }

    public void setSOEURS(List<String> SOEURS) {
        this.SOEURS = SOEURS;
    }

    public List<String> getCONJOINT() {
        return CONJOINT;
    }

    public void setCONJOINT(List<String> CONJOINT) {
        this.CONJOINT = CONJOINT;
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

    public Chantier getChantier() {
        return chantier;
    }

    public void setChantier(Chantier chantier) {
        this.chantier = chantier;
    }

    public int getIdChantier() {
        return idChantier;
    }

    public void setIdChantier(int idChantier) {
        this.idChantier = idChantier;
    }

    public int getIdStatut() {
        return idStatut;
    }

    public void setIdStatut(int idStatut) {
        this.idStatut = idStatut;
    }

    public List<DossierMedical> getDossiersMedicaux() {
        return dossiersMedicaux;
    }

    public void setDossiersMedicaux(List<DossierMedical> dossiersMedicaux) {
        this.dossiersMedicaux = dossiersMedicaux;
    }

    public SalarieServiceIn getSalarieService() {
        return salarieService;
    }

    public void setSalarieService(SalarieServiceIn salarieService) {
        this.salarieService = salarieService;
    }

    public List<Salarie> getSalaries() {
        return salaries;
    }

    public void setSalaries(List<Salarie> salaries) {
        this.salaries = salaries;
    }

    public DossierMedicalService getDossierMedicalService() {
        return dossierMedicalService;
    }

    public void setDossierMedicalService(DossierMedicalService dossierMedicalService) {
        this.dossierMedicalService = dossierMedicalService;
    }

    public boolean isIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getCodeIds() {
        return codeIds;
    }

    public void setCodeIds(String codeIds) {
        this.codeIds = codeIds;
    }

    public DossierMedical getDosMedToAdd() {
        return dosMedToAdd;
    }

    public void setDosMedToAdd(DossierMedical dosMedToAdd) {
        this.dosMedToAdd = dosMedToAdd;
    }

    public DossierMedical getDosMedToshow() {
        return dosMedToshow;
    }

    public void setDosMedToshow(DossierMedical dosMedToshow) {
        this.dosMedToshow = dosMedToshow;
    }

    public Salarie getSalarieToAdd() {
        return salarieToAdd;
    }

    public void setSalarieToAdd(Salarie salarieToAdd) {
        this.salarieToAdd = salarieToAdd;
    }

    public Integer getIdSalarie() {
        return idSalarie;
    }

    public void setIdSalarie(Integer idSalarie) {
        this.idSalarie = idSalarie;
    } 
  
    @PostConstruct
    public void init() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("________ User Connected ________" + auth.getPrincipal().toString());
        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        chantierService = ctx.getBean(ChantierService.class);
        salarieService = Module.ctx.getBean(SalarieServiceIn.class);
        // salarieServicenv = Module.ctx.getBean(SalarieService.class);
        
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        Evol_ChantierMb evol_ChantierMb = (Evol_ChantierMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "evol_chantierMb");
        isAdmin = false;
         
        for (GrantedAuthority grantedAuthority : auth.getAuthorities()) {
            if ("admin".equals(grantedAuthority.getAuthority())) {  
                chantiers = evol_ChantierMb.getChantiers();
               // salaries  =salarieService.listSalarieAdmin(); 
                isAdmin = true;
                break;
            }
        }
        if (!isAdmin) {
            chantiers = chantierService.searchByUser(auth.getPrincipal().toString()); 
            String listChantiers = "";
            for(int i=0;i<chantiers.size();i++){
                if(chantiers.get(i).getId()!=null) listChantiers=listChantiers+chantiers.get(i).getId()+",";
            } 
        }
        antecedentRisque = new ArrayList<>();
        antecedentRisque= dossierMedicalService.findAntecedentByType("RISQUE");
        antecedentAutre = new ArrayList<>();
        antecedentAutre= dossierMedicalService.findAntecedentByType("AUTRE"); 
        antecedentATCDMD = new ArrayList<>();
        antecedentATCDMD= dossierMedicalService.findAntecedentByType("ATCD MD"); 
        antecedentATCDCH = new ArrayList<>();
        antecedentATCDCH= dossierMedicalService.findAntecedentByType("ATCD CH"); 
        listeTypeFichiers = new ArrayList<>();
        listeTypeFichiers= dossierMedicalService.findAntecedentByType("FICHIER"); 
    }
 
    public void addAntecedent(){
        try {
            AntecedentProfessionnel anteced = new AntecedentProfessionnel();
            anteced.setDossierMedical(dosMedToshow);
            antecedentProfessionnelHorsTGCC.add(anteced);
        } catch (Exception e) {
            ExceptionText("addAntecedent", e);
        }
    }
    public void onRowEdit(RowEditEvent event) {
        try {
            String message ="Opération à été effectuée.";
            FacesContext context = FacesContext.getCurrentInstance(); 
            context.addMessage(null, new FacesMessage("Infos:",   message) );
            AntecedentProfessionnel anteced = (AntecedentProfessionnel) event.getObject();
            anteced.setDossierMedical(dosMedToshow);
            if(anteced.getId()==null){
                dossierMedicalService.addAntecedentProfessionnel(anteced);
            }else{
                dossierMedicalService.updateAntecedentProfessionnel(anteced);
            }
            
        } catch (Exception e) {
            ExceptionText("onRowEdit", e);
        }
    }
    public void saveAntecedent(){
        try {
//            long id = dossierMedicalService.addAntecedent(antecedentToAdd);
            
            if(dossierMedicalService.addAntecedent(antecedentToAdd) != null) {
                System.out.println("ID: " +antecedentToAdd.getId());
                if(antecedentToAdd.getType().equals("RISQUE")){
                    antecedentRisque.add(antecedentToAdd);
                }else if(antecedentToAdd.getType().equals("ATCD MD")){
                    antecedentATCDMD.add(antecedentToAdd);
                }else if(antecedentToAdd.getType().equals("ATCD CH")){
                    antecedentATCDCH.add(antecedentToAdd);
                }else if(antecedentToAdd.getType().equals("FICHIER")){
                    listeTypeFichiers.add(antecedentToAdd);
                }else {
                    antecedentAutre.add(antecedentToAdd);
                }
                RequestContext.getCurrentInstance().execute("PF('addAntecedent').hide();");
                String message ="Modification est terminé avec succès.";
                FacesContext context = FacesContext.getCurrentInstance(); 
                context.addMessage(null, new FacesMessage("Infos:",   message) );
                antecedentToAdd = new Antecedent();
            }else{
                System.out.println("FAILED");
            }
        } catch (Exception e) {
            System.out.println("ID: " +e.getMessage());
        }
    } 
    public void onRowCancel(RowEditEvent  event) {
        try {
            String message ="Opération est annulée.";
            FacesContext context = FacesContext.getCurrentInstance(); 
            context.addMessage(null, new FacesMessage("Infos:",   message) );
        } catch (Exception e) {
            ExceptionText("onRowCancel", e);
        }
    }
    
    public void afficherVisite(Visite vis){
        try {
            System.out.println("===============> afficherVisite IN  >> " +visiteToShow.getType());
            visiteToShow = new Visite();
            visiteToShow = vis;
            if(visiteToShow.getType()!=null){
                if("V.E.".equals(visiteToShow.getType())){
                    RequestContext.getCurrentInstance().execute("PF('dlg5').show();");
                }
                else if("V.A.T.".equals(visiteToShow.getType())){
                    RequestContext.getCurrentInstance().execute("PF('dlg50').show();");
                }
                else{
                    RequestContext.getCurrentInstance().execute("PF('dlg500').show();");
                } 
            }
        } catch (Exception e) {
            ExceptionText("afficherVisite", e);
        }
    }
    public String userConnected(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String au = auth.getPrincipal().toString();
        String utili = au.replace(".", " ").toUpperCase();
        return utili;
    }
    
    public void calculateDateProcVisite1(){
        try { 
            System.out.println("visiteToAdd.getSurviller() " + visiteToAdd.getSurviller());
            if(visiteToAdd.getSurviller()!=0){
                Calendar cal = Calendar.getInstance(); 
                if (visiteToAdd.getDateVisite()!= null){
                    cal.setTime(visiteToAdd.getDateVisite());
                }
                else {
                    visiteToAdd.setDateVisite(new Date());
                    cal.setTime(visiteToShow.getDateVisite());
                }
                if(0!= visiteToAdd.getSurviller())cal.add(Calendar.MONTH, visiteToAdd.getSurviller());
                else cal.add(Calendar.MONTH, 1); 
                visiteToAdd.setProchaineVisite(cal.getTime());
                System.out.println("visiteToAdd.getProchaineVisite() " + visiteToAdd.getProchaineVisite());  
            } 
        } catch (NumberFormatException e) {
            ExceptionText("calculateDateProcVisite", e);
        }
    }
    public void calculateDateProcVisite(){
        try {   
            Calendar cal = Calendar.getInstance();
            cal.setTime(visiteToShow.getDateVisite());
            cal.add(Calendar.MONTH, visiteToShow.getSurviller());
            visiteToShow.setProchaineVisite(cal.getTime());
            
        } catch (NumberFormatException e) {
            ExceptionText("calculateDateProcVisite", e);
        }
    }
    
    public void listenerAjoutVisite(String type){
        try {
            System.out.println("IN listenerAjoutVisite");
            visiteToAdd = new Visite(); 
            visiteToAdd.setDateVisite(new Date());
            visiteToAdd.setCreePar(userConnected());
            visiteToAdd.setDocteur(userConnected());
            visiteToAdd.setDateCreation(new Date());
            calculateDateProcVisite1();
            switch (type) {
                case "EM":
                    visiteToAdd.setType("V.E.");
                    RequestContext.getCurrentInstance().execute("PF('dlg4').show();");
                    break;
                case "AT":
                    visiteToAdd.setType("V.A.T.");
                    RequestContext.getCurrentInstance().execute("PF('dlg40').show();");
                    break;
                default:
                    RequestContext.getCurrentInstance().execute("PF('dlg400').show();");
                    break;
            }
            
        } catch (Exception e) {
            ExceptionText("listenerAjoutVisite", e);
        }
    }
    public void listenerShowVisite(Visite vis){
        try {
            System.out.println("IN SHOWING"); 
            visiteToShow =vis;
            if(vis.getType().equals("V.E.")){ 
                RequestContext.getCurrentInstance().execute("PF('dlg5').show();");
            }
            else if(vis.getType().equals("V.A.T.")){ 
                RequestContext.getCurrentInstance().execute("PF('dlg50').show();");
            }
            else RequestContext.getCurrentInstance().execute("PF('dlg500').show();");
            
        } catch (Exception e) {
            ExceptionText("SHOWING", e);
        }
    }
//    private String selectedDoc="/opt/files/docDossierMedicaux/test.txt";
    private String selectedDoc="";
    public void visualiser(String chemin) {
        System.out.println("chemin1 : " + chemin);
        System.out.println("chemin2 : " + chemin.indexOf("files"));
        System.out.println("chemin3 : " + chemin.substring(chemin.indexOf("files")));
        selectedDoc = chemin.substring(chemin.indexOf("files")); 
    }
     
    private UploadedFile uploadedFile;
    public void loadingFile(DossierMedical dos){
        dosMedToshow = new DossierMedical();
        dosMedToshow =dos;
    }
    public String titre=null;
    public void uploader() throws IOException { 
        // String chemin = TgccFileManager.getCheminFichier("Documents Engin");
        String chemin = "/opt/files/docDossierMedicaux";
        Path folder = Paths.get(chemin);
        Files.createDirectories(folder);
        if (uploadedFile == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, Message.STRING_CHARGE_DOCUMENT, Message.STRING_CHARGE_DOCUMENT));

        } else if ("".equals(uploadedFile.getFileName())) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, Message.STRING_CHARGE_DOCUMENT, Message.STRING_CHARGE_DOCUMENT));
        } else {
//            String filename  = FilenameUtils.getBaseName(uploadedFile.getFileName());
            String extension = FilenameUtils.getExtension(uploadedFile.getFileName());

            if (!"pdf".equals(extension)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, Message.STRING_CHARGE_DOCUMENT_PDF, Message.STRING_CHARGE_DOCUMENT_PDF));
            } else {

                Path file = Files.createTempFile(folder, dosMedToshow.getSalarie().getCin(), "."+extension);

                 
                try (InputStream input = uploadedFile.getInputstream()) {
                    Files.copy(input, file, StandardCopyOption.REPLACE_EXISTING);   
                    DocumentDossierMedical document = new DocumentDossierMedical();
                    document.setTitre(titre);
                    document.setChemin(chemin+ "/" + file.getFileName());
                    document.setCreePar(userConnected());
                    document.setDossierMedical(dosMedToshow); 
                    Integer id = dossierMedicalService.insertDocument(document); 
                    titre=null;
                    listaFichiers.add(document);
                    RequestContext.getCurrentInstance().execute("PF('tableDoc').show();");
                    System.out.println(id);
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, Message.STRING_CHARGE_DOCUMENT_DONE, Message.STRING_CHARGE_DOCUMENT_DONE));

//                    ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
//                    ec.redirect(ec.getRequestContextPath() + "/evol/dossiersMedicaux.xhtml");
                }

            }
        }

    }
    
    public void chargerFichier(DossierMedical dos){
        try {
            listaFichiers=null;
            dosMedToshow=dos;
            listaFichiers = dossierMedicalService.findDocumentDossierMedicalByDossier(dos);
            
        } catch (Exception e) {
        }
    }
    private StreamedContent image = new DefaultStreamedContent();
    public StreamedContent chargerPdf(DossierMedical dos){
        try {
            if(dos==null){
                System.out.println("phase is render response");
                return new DefaultStreamedContent();
            }
            else {
                InputStream stream = new FileInputStream( selectedDoc);
                return new DefaultStreamedContent(stream, URLConnection.guessContentTypeFromName(selectedDoc));
            }
        } catch (FileNotFoundException e) {
            System.out.print(e.getMessage() );
            return new DefaultStreamedContent();
        }
        
    }
    public void ajouterDocument(String chemin) throws IOException{ 
//        String chemin = "/opt/files/docDossierMedicaux"; 
//        creeFichier(chemin, visiteToShow, "GENERER");
        DocumentDossierMedical document = new DocumentDossierMedical();
        document.setTitre("GENERER");
        document.setChemin(chemin);
        document.setCreePar(userConnected());
        document.setDossierMedical(visiteToShow.getDossierMedical());
        Integer id = dossierMedicalService.insertDocument(document);
        System.out.println("Doc add id: "+id+ " chemin:" + chemin); 
        
//        return chemin; 
    }
    public void addFichier(Visite vis) throws  DocumentException, IOException{
        try { 
            visiteToShow = vis;  
//            creeFichier(path, visiteToShow, "GENERER");
//            String chemin = "/opt/files/BARAKA";
//            Path folder = Paths.get(chemin);
//            Files.createDirectories(folder);
//            Path file = Files.createTempFile(folder, dosMedToshow.getSalarie().getCin(),".pdf");
            String path =  editeFichier();
            System.out.println("path: " + path);
//            ObservatajouterDocument(path);
            visualiser(path);
             
        } catch (IOException e) {
            System.out.println("Erreur "+e.getMessage());
        }
    }
    public void creeFichier(String chemin,Visite visite, String type) throws DocumentException, IOException {
    try {
            String fileName =chemin;
            System.out.println("chemin : "+fileName);
            //instance de document
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(fileName)); 
	    document.open();
            document.add(new Paragraph("BARAKA"));
            document.close();
            System.out.println("Fin d'éditions !");
        } catch (DocumentException | FileNotFoundException e) {
            System.out.println("chemin : "+e.getMessage());
        }
		
    } 
     
    public void miseAjourTable(int id){
        try {
            System.out.println("IN miseAjourTable " + id);
            dossierMedicalService.miseAjourSalarie(id);
            chargerDonnees();
        } catch (Exception e) {
            ExceptionText("miseAjourTable", e);
        }
    }     
    public void enregistrerVisite(){
        System.out.println("IN EnregistrerVisite");
        try {
            visiteToAdd.setDossierMedical(dosMedToshow);
            Long  id = visiteService.addVisite(visiteToAdd);
            System.out.println("Id insert:" + id);
            if(id!=null){  
                listaVisite.add(visiteToAdd);
                RequestContext.getCurrentInstance().execute("PF('dlg4').hide();");
                RequestContext.getCurrentInstance().execute("PF('dlg40').hide();");
                RequestContext.getCurrentInstance().execute("PF('dlg400').hide();");
                System.out.println("GOOD");  
                System.out.println("IdosMedToshow.getSalarie().getId():" + dosMedToshow.getSalarie().getId());                
                miseAjourTable(dosMedToshow.getSalarie().getId()); 
            }else{
                System.out.println("FAILED"); 
            }
        } catch (Exception e) {
            ExceptionText("UpdateVisite", e);
        }
    }
    public void updateVisite(){
        System.out.println("IN UpdateVisite");
        try { 
            visiteToShow.setDossierMedical(dosMedToshow);
            Long  id = visiteService.updateVisite(visiteToShow);
            miseAjourTable(dosMedToshow.getSalarie().getId());
            System.out.println("Id update:" + id);
            if(id!=null){ 
                RequestContext.getCurrentInstance().execute("PF('dlg5').hide();");
                RequestContext.getCurrentInstance().execute("PF('dlg50').hide();");
                RequestContext.getCurrentInstance().execute("PF('dlg500').hide();");
                System.out.println("GOOD"); 
            }else{
                System.out.println("FAILED"); 
            }  
        } catch (Exception e) {
            ExceptionText("UpdateVisite", e);
        }
    }
    public void listenerDlgAdd(){
        try { 
            salaries = null;
            salaries  =salarieService.listSalarieBlackListSorti();
            RISQUE = new ArrayList<>();
            PERE = new ArrayList<>();
            MERE = new ArrayList<>();
            FRERES = new ArrayList<>();
            SOEURS = new ArrayList<>();
            ENFANTS = new ArrayList<>();
            CONJOINT = new ArrayList<>(); 
            ATCDMD = new ArrayList<>(); 
            ATCDCH = new ArrayList<>(); 

            System.out.println("IN listenerDlgAdd");
            dosMedToAdd  = new DossierMedical();
            dosMedToshow = new DossierMedical();
            salarieToAdd = new Salarie();
            idSalarie = 0;
            RequestContext.getCurrentInstance().execute("PF('dlg2').show();");
             
        } catch (Exception e) {
            ExceptionText("listenerDlgAdd", e);
        }
    }
    public void ListVisitesByDossier(DossierMedical dos){
        try {
            System.out.println("IN ListVisitesByDossier");
            listaVisite = null;
            listaVisite = visiteService.findVisitesByDossierMedical(dos); 
//            RequestContext.getCurrentInstance().execute("PF('dlg3').show();");
        } catch (Exception e) {
            ExceptionText("afficherDossier", e);
        }
    } 
    public void afficherDossier(DossierMedical dos){
        try { 
            System.out.println("Affichage Dossier IN");  
            RISQUE = new ArrayList<>();
            PERE = new ArrayList<>();
            MERE = new ArrayList<>();
            FRERES = new ArrayList<>();
            SOEURS = new ArrayList<>();
            ENFANTS = new ArrayList<>();
            CONJOINT = new ArrayList<>(); 
            ATCDMD = new ArrayList<>(); 
            ATCDCH = new ArrayList<>(); 
            
            dosMedToshow = new DossierMedical();
            dosMedToshow = dos; 
            
              
            antecedentProfessionnelHorsTGCC = new ArrayList<>();
            antecedentProfessionnelHorsTGCC = dossierMedicalService.findAntecedentsProfessionels(dosMedToshow); 
            
            salaries = null;
            salaries  =salarieService.listSalarieByListChantier(idChantier+"");
            if(dosMedToshow.getRisque()!=null){
                String t = dosMedToshow.getRisque().substring(1,dosMedToshow.getRisque().length()-1); 
                String str[] = t.split(", "); 
                RISQUE.addAll(Arrays.asList(str));
            }
            if(dosMedToshow.getPere()!=null){
                String t = dosMedToshow.getPere().substring(1,dosMedToshow.getPere().length()-1); 
                String per[] = t.split(", "); 
                PERE.addAll(Arrays.asList(per));
            }
            if(dosMedToshow.getMere()!=null){
                String t = dosMedToshow.getMere().substring(1,dosMedToshow.getMere().length()-1); 
                String mer[] = t.split(", "); 
                MERE.addAll(Arrays.asList(mer));
            }
            if(dosMedToshow.getFreres()!=null){
                String t = dosMedToshow.getFreres().substring(1,dosMedToshow.getFreres().length()-1); 
                String fre[] = t.split(", "); 
                FRERES.addAll(Arrays.asList(fre));
            }
            if(dosMedToshow.getSoeurs()!=null){
                String t = dosMedToshow.getSoeurs().substring(1,dosMedToshow.getSoeurs().length()-1); 
                String soeur[] = t.split(", "); 
                SOEURS.addAll(Arrays.asList(soeur));
            }
            if(dosMedToshow.getEnfants()!=null){
                String t = dosMedToshow.getEnfants().substring(1,dosMedToshow.getEnfants().length()-1); 
                String enf[] = t.split(", "); 
                ENFANTS.addAll(Arrays.asList(enf));
            }
            if(dosMedToshow.getConjoin()!=null){
                String t = dosMedToshow.getConjoin().substring(1,dosMedToshow.getConjoin().length()-1); 
                String conj[] = t.split(", "); 
                CONJOINT.addAll(Arrays.asList(conj));
            }
            if(dosMedToshow.getAtcdmd()!=null){
                String t = dosMedToshow.getAtcdmd().substring(1,dosMedToshow.getAtcdmd().length()-1); 
                String md[] = t.split(", "); 
                ATCDMD.addAll(Arrays.asList(md));
            }
            if(dosMedToshow.getAtcdch()!=null){
                String t = dosMedToshow.getAtcdch().substring(1,dosMedToshow.getAtcdch().length()-1); 
                String ch[] = t.split(", "); 
                ATCDCH.addAll(Arrays.asList(ch));
            }
             
            if(dos.getSalarie()!=null)idSalarie = dos.getSalarie().getId();
            else idSalarie = 0;
            ListVisitesByDossier(dosMedToshow);
            RequestContext.getCurrentInstance().execute("PF('dlg1').show();");
        } catch (Exception e) {
            ExceptionText("afficherDossier", e);
        }
    }
    public void creeDossier(Salarie s){
         try { 
            salaries = null;
            if(idChantier!=0) {salaries  =salarieService.listSalarieByListChantier(idChantier+"");}
            else salaries  =salarieService.listSalarieBlackListSorti();
            RISQUE = new ArrayList<>();
            PERE = new ArrayList<>();
            MERE = new ArrayList<>();
            FRERES = new ArrayList<>();
            SOEURS = new ArrayList<>();
            ENFANTS = new ArrayList<>();
            CONJOINT = new ArrayList<>(); 
            ATCDMD = new ArrayList<>();
            ATCDCH = new ArrayList<>();
            
            
            System.out.println("IN listenerDlgAdd");
            dosMedToAdd  = new DossierMedical();
            dosMedToshow = new DossierMedical();
            salarieToAdd = s;
            idSalarie = s.getId();
            listenrSalarieAdd();
            RequestContext.getCurrentInstance().execute("PF('dlg2').show();");
              
        } catch (Exception e) {
            ExceptionText("listenerDlgAdd", e);
        }
    }
    public void enregistrerDossier(){
        try {
            if(dosMedToAdd.getSalarie().getId()!=null){
            System.out.println("Creation Dossier IN" + dosMedToAdd.getSalarie().getId()); 
             
            if(!RISQUE.isEmpty())dosMedToAdd.setRisque(RISQUE.toString()); 
            else dosMedToAdd.setRisque(null);
            if(!PERE.isEmpty())dosMedToAdd.setPere(PERE.toString()); 
            else dosMedToAdd.setPere(null);
            if(!MERE.isEmpty())dosMedToAdd.setMere(MERE.toString()); 
            else dosMedToAdd.setMere(null);
            if(!FRERES.isEmpty())dosMedToAdd.setFreres(FRERES.toString()); 
            else dosMedToAdd.setFreres(null);
            if(!SOEURS.isEmpty())dosMedToAdd.setSoeurs(SOEURS.toString());  
            else dosMedToAdd.setSoeurs(null);
            if(!ENFANTS.isEmpty())dosMedToAdd.setEnfants(ENFANTS.toString()); 
            else dosMedToAdd.setEnfants(null);
            if(!CONJOINT.isEmpty())dosMedToAdd.setConjoin(CONJOINT.toString()); 
            else dosMedToAdd.setConjoin(null);
            if(!ATCDMD.isEmpty())dosMedToAdd.setAtcdmd(ATCDMD.toString()); 
            else dosMedToAdd.setAtcdmd(null);
            if(!ATCDCH.isEmpty())dosMedToAdd.setAtcdch(ATCDCH.toString()); 
            else dosMedToAdd.setAtcdch(null);
            
            
            Long id = dossierMedicalService.addDossierMedical(dosMedToAdd);
            miseAjourTable(dosMedToAdd.getSalarie().getId());
            if(id!=null){
                System.out.println("Good");
                dossiersMedicaux.add(dosMedToAdd);
                salarieToAdd =new Salarie();
                dosMedToAdd = new DossierMedical();
                RISQUE = new ArrayList<>();
                PERE = new ArrayList<>();
                MERE = new ArrayList<>();
                FRERES = new ArrayList<>();
                SOEURS = new ArrayList<>();
                ENFANTS = new ArrayList<>();
                CONJOINT = new ArrayList<>(); 
                ATCDMD = new ArrayList<>();
                ATCDCH = new ArrayList<>(); 
                RequestContext.getCurrentInstance().execute("PF('dlg2').hide();");
                String message ="Création du dossier est terminé.";
                FacesContext context = FacesContext.getCurrentInstance(); 
                context.addMessage(null, new FacesMessage("Infos:",   message) ); 
                
            }else{
                System.out.println("Failed");
                String message ="Erreur de création.";
                FacesContext context = FacesContext.getCurrentInstance(); 
                context.addMessage(null, new FacesMessage("Attention:",   message) );
            }
            }else{
                System.out.println("Failed");
                String message ="Erreur de récupération du salarie merci de réessayer.";
                FacesContext context = FacesContext.getCurrentInstance(); 
                context.addMessage(null, new FacesMessage("Attention:",   message) );
            }
        } catch (Exception e) {
            ExceptionText("enregistrerDossier", e);
        }
    }
    public void modifierDossier(){
        try {
            System.out.println("IN modifierDossier"); 
//            dosMedToshow.setSalarie(salarieToAdd); 
            
            if(!RISQUE.isEmpty())dosMedToshow.setRisque(RISQUE.toString()); 
            else dosMedToshow.setRisque(null);
            if(!PERE.isEmpty())dosMedToshow.setPere(PERE.toString()); 
            else dosMedToshow.setPere(null);
            if(!MERE.isEmpty())dosMedToshow.setMere(MERE.toString()); 
            else dosMedToshow.setMere(null);
            if(!FRERES.isEmpty())dosMedToshow.setFreres(FRERES.toString()); 
            else dosMedToshow.setFreres(null);
            if(!SOEURS.isEmpty())dosMedToshow.setSoeurs(SOEURS.toString());  
            else dosMedToshow.setSoeurs(null);
            if(!ENFANTS.isEmpty())dosMedToshow.setEnfants(ENFANTS.toString()); 
            else dosMedToshow.setEnfants(null);
            if(!CONJOINT.isEmpty())dosMedToshow.setConjoin(CONJOINT.toString()); 
            else dosMedToshow.setConjoin(null);
            if(!ATCDMD.isEmpty())dosMedToshow.setAtcdmd(ATCDMD.toString()); 
            else dosMedToshow.setAtcdmd(null);
            if(!ATCDCH.isEmpty())dosMedToshow.setAtcdch(ATCDCH.toString()); 
            else dosMedToshow.setAtcdch(null);
            
            
            Long id = dossierMedicalService.updateDossierMedical(dosMedToshow); 
            miseAjourTable(dosMedToshow.getSalarie().getId()); 
            if(id!=null){
                System.out.println("Good"); 
                salarieToAdd =new Salarie();
                dosMedToshow = new DossierMedical();
                RequestContext.getCurrentInstance().execute("PF('dlg1').hide();"); 
                String message ="Modification est terminé avec succès.";
                FacesContext context = FacesContext.getCurrentInstance(); 
                context.addMessage(null, new FacesMessage("Infos:",   message) );
            }else{
                System.out.println("Failed");
                String message ="Erreur de modification.";
                FacesContext context = FacesContext.getCurrentInstance(); 
                context.addMessage(null, new FacesMessage("Attention:",   message) );
            }
        } catch (Exception e) {
            ExceptionText("ModifierDossier", e);
        }
    }
    public void listenrSalarieShow(){
        try {
            if(idSalarie!=0){
                salarieToAdd =new Salarie();
                salarieToAdd = salarieService.getSalarieByID(idSalarie); 
                dosMedToshow.setSalarie(salarieToAdd);
            }
            else{
                salarieToAdd =new Salarie();
                dosMedToshow.setSalarie(salarieToAdd);
            }
        } catch (Exception e) {
            System.out.println(e.getCause());
        }
    }
    public void listenrSalarieAdd(){
        try {
            System.out.println("idSalarie " + idSalarie);
            if(idSalarie!=0){
                salarieToAdd =new Salarie();
                salarieToAdd = salarieService.getSalarieByID(idSalarie);
                if(salarieToAdd.getEtat().getId()==2 || salarieToAdd.getEtat().getId()==3){
                    System.out.println("ARCHIVE");
                    dosMedToAdd.setActif(false);
                }else{
                    System.out.println("ACTIFS");
                    dosMedToAdd.setActif(true);
                }
                dosMedToAdd.setSalarie(salarieToAdd);
                System.out.println("salarieToAdd " +  dosMedToAdd.getSalarie().getId());
            }
            else{
                salarieToAdd =new Salarie();
                dosMedToAdd.setSalarie(salarieToAdd);
            }
        } catch (Exception e) {
            System.out.println(e.getCause());
        }
    }
    public String ExceptionText(String methode, Exception e){ 
        return "**** Exception **** Methode: " + methode + " Message: " + e.getMessage() ;
    }
    public void listenerFilter(Integer actif){
        try {  
            dossiersMedicaux = null; 
            if(isAdmin){
                if(idChantier == 0){
                    System.out.println("===ADMIN ==> :"+ isAdmin + "=== AUCUN CHANTIER SELECTIONNER ==> :"+ idChantier + "=== Actif ==> :"+ actif);
                    if(actif==null)dossiersMedicaux = dossierMedicalService.findAllDossierMedical();
                    else dossiersMedicaux = dossierMedicalService.findAllDossierMedicalByStatut(actif);
                }else{
                    System.out.println("===ADMIN ==> :"+ isAdmin + "=== CHANTIER SELECTIONNE ==> :"+ idChantier + "=== Actif ==> :"+ actif);
                    if(actif==null)dossiersMedicaux = dossierMedicalService.findDossierMedicalByChantier(idChantier);
                    else dossiersMedicaux = dossierMedicalService.findDossierMedicalByChantierAndStatut(idChantier,actif);
                }
            }else{
               if(idChantier == 0){
                    System.out.println("===USER ==> :"+ isAdmin + "=== AUCUN CHANTIER SELECTIONNE ==> :"+ idChantier + "=== Actif ==> :"+ actif);
                    if(actif==null) dossiersMedicaux = dossierMedicalService.findDossierMedicalChantiers(codeIds);
                    else dossiersMedicaux = dossierMedicalService.findDossierMedicalChantiersAndStatut(codeIds,actif);
                }else{ 
                    System.out.println("===USER ==> :"+ isAdmin + "=== CHANTIER SELECTIONNE ==> :"+ idChantier + "=== Actif ==> :"+ actif);
                    if(actif==null)dossiersMedicaux = dossierMedicalService.findDossierMedicalByChantier(idChantier);
                    else dossiersMedicaux = dossierMedicalService.findDossierMedicalByChantierAndStatut(idChantier,actif);
                }
            }  
        } catch (Exception e) {
            ExceptionText("listenerFilter", e);
        }
    }
    public void chargerDonnees(){
        try {       
               salaries = null;
               dossiersMedicaux = new ArrayList<>();    
                
               if(idStatut==0 || idChantier==0 ){
                   salaries  =salarieService.listSalarieBlackListSorti(); 
               }else{
                   salaries  =salarieService.listSalarieByListChantier(idChantier+""); 
               }
                dossiersMedicaux = dossierMedicalService.findDossierMedicalByChantierAndStatut(idChantier,idStatut); 
                 
                for(int i=0;i<dossiersMedicaux.size();i++){
                    if(dossiersMedicaux.get(i).getTitularisation().equals("NON") && dossiersMedicaux.get(i).getProchaineVisite()== null){  
                        dossiersMedicaux.get(i).setTriOrdre(1);
                    }else if(dossiersMedicaux.get(i).getTitularisation().equals("OUI") && dossiersMedicaux.get(i).getProchaineVisite()== null){  
                        dossiersMedicaux.get(i).setTriOrdre(10);
                    }else {
                        dossiersMedicaux.get(i).setTriOrdre(100);
                    }
                    
                }
                for(int i=0;i<dossiersMedicaux.size();i++){
                    if(dossiersMedicaux.get(i).getProchaineVisite()== null){  
                        dossiersMedicaux.get(i).setProchaineVisite(dossiersMedicaux.get(i).getDateEmbauche());
                    } 
                }
                for(int i=0;i<dossiersMedicaux.size();i++){ 
                     
                    String dateForm = new SimpleDateFormat("yyyyMMdd").format(dossiersMedicaux.get(i).getProchaineVisite());
                    int conv = Integer.parseInt(dateForm);
                     
                    int total = conv * dossiersMedicaux.get(i).getTriOrdre(); 
//                    System.out.print(" Ordre(): "+ dossiersMedicaux.get(i).getTriOrdre()+ " conv: "+ conv+ " Matricule:"+ dossiersMedicaux.get(i).getSalarie().getMatricule() );
                    dossiersMedicaux.get(i).setTriOrdre(total); 
                }
                    
                String message ="chargement terminé.";
                FacesContext context = FacesContext.getCurrentInstance(); 
                context.addMessage(null, new FacesMessage("Infos:",   message) );
 
                  
        } catch (Exception e) {
            ExceptionText("CHARGEMENT ", e);
        }
    }
    public void changeChantier(){
        System.out.println("===============> idChantier:"+idChantier);
        dossiersMedicaux   = new ArrayList<>();   
         
    }
    public void afficherVisites(DossierMedical dos){ 
        try { 
            System.out.println("===============> afficherVisites IN  >> ");
            dosMedToshow = new DossierMedical();
            dosMedToshow = dos;
            
        } catch (Exception e) {
            ExceptionText("changeStatus", e);
        }
    }
    public void changeStatus(){ 
        try {  
            System.out.println("===============> changeStatus IN  >> "); 
        } catch (Exception e) {
            ExceptionText("changeStatus", e);
        }
    }
    public void listeDossiersStatut(){
        try { 
            int id = idChantier;
            if(idStatut==0){
                idChantier=0;  
            } else{
                idChantier=id;
            }
            dossiersMedicaux   = new ArrayList<>();    
        } catch (Exception e) {
            ExceptionText("listeDossiers", e);
        }
    }
    public void listeDossiers(){
        try { 
            if(idChantier!=0){
                idStatut=1;  
            } else{
                idStatut=0;
            }
            dossiersMedicaux   = new ArrayList<>();   
            
        } catch (Exception e) {
            ExceptionText("listeDossiers", e);
        }
    }
    public List<Visite> getListaVisite() {
        return listaVisite;
    }

    public void setListaVisite(List<Visite> listaVisite) {
        this.listaVisite = listaVisite;
    }

    public Visite getVisiteToAdd() {
        return visiteToAdd;
    }

    public void setVisiteToAdd(Visite visiteToAdd) {
        this.visiteToAdd = visiteToAdd;
    }

    public Visite getVisiteToShow() {
        return visiteToShow;
    }

    public void setVisiteToShow(Visite visiteToShow) {
        this.visiteToShow = visiteToShow;
    }

    public VisiteService getVisiteService() {
        return visiteService;
    }

    public void setVisiteService(VisiteService visiteService) {
        this.visiteService = visiteService;
    }

    public String getSelectedDoc() {
        return selectedDoc;
    }

    public void setSelectedDoc(String selectedDoc) {
        this.selectedDoc = selectedDoc;
    }

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public StreamedContent getImage() {
        return image;
    }

    public void setImage(StreamedContent image) {
        this.image = image;
    }

    public List<DocumentDossierMedical> getListaFichiers() {
        return listaFichiers;
    }

    public void setListaFichiers(List<DocumentDossierMedical> listaFichiers) {
        this.listaFichiers = listaFichiers;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }
    
    public String editeFichier()throws IOException, DocumentException {
             String cheminDoc="";
             Document document = new Document(); 
		try {   
                         
                    Font f =  new Font(Font.FontFamily.TIMES_ROMAN, 15.0f, Font.UNDERLINE, BaseColor.BLACK);
                    Font f1 = new Font(Font.FontFamily.HELVETICA, 11.0f, 0, BaseColor.BLACK);
                    Font f2 = new Font(Font.FontFamily.HELVETICA,  9.0f, 0, BaseColor.BLACK); 
                    
                    String chemin = ConstanteMb.getRepertoire() + "/files/docDossierMedicaux";
                    Path folder = Paths.get(chemin);
                    Files.createDirectories(folder);
                          
			//chemain de fichier générer
                    String nomFichier = visiteToShow.getId()+"_"+new SimpleDateFormat("yyyy-MM-dd hh:mm").format(new Date())+".pdf";
                    File file = new File(chemin + "/" + nomFichier);

                     
                    cheminDoc= chemin + "/" + nomFichier;
                    
                    PdfWriter.getInstance(document, new FileOutputStream(file));
                        
                    document.open(); 
                        
                    String visiteType ="";
                         
                    if(visiteToShow.getType()!=null){
                        if     (null == visiteToShow.getType()) visiteType ="";
                        else switch (visiteToShow.getType()) {
                            case "V.E.":
                                visiteType ="Visite d'embauche";
                                break;
                            case "V.A.":
                                visiteType ="Visite Annuelle";
                                break;
                            case "V.P.P.":
                                visiteType ="Visite P.P";
                                break;
                            case "V.A.T.":
                                visiteType ="Visite AT";
                                break;
                            case "V.D.":
                                visiteType ="Visite Départ";
                                break;
                            case "V.S.":
                                visiteType ="Visite Spontannée";
                                break;
                            default:
                                visiteType ="Visite";
                                break;
                            }
                        }
                     
                    String titre0 = "Fiche médicale d'aptitude physique";
                    String titre1 = "("+ visiteType +")";
                    String titre2 = "(Arrêté du ministre de l'emploi N° 2625-12(16 juillet 2012))";

                    Paragraph vide = new Paragraph(" "); 
                    PdfPTable tableImg = new PdfPTable(1);
                    PdfPTable tableT = new PdfPTable(3);

                    String NOM=""; String MAT=""; String CIN="";
                    String NAI=""; String EMB=""; String POS="";
                        
                    if(visiteToShow.getDossierMedical().getSalarie().getNom()!=null && visiteToShow.getDossierMedical().getSalarie().getPrenom()!=null) {
                        NOM=""+visiteToShow.getDossierMedical().getSalarie().getNom()+" "+ visiteToShow.getDossierMedical().getSalarie().getPrenom();
                    }
                    if(visiteToShow.getDossierMedical().getSalarie().getMatricule()!=null){
                        MAT = visiteToShow.getDossierMedical().getSalarie().getMatricule();
                    }
                    if(visiteToShow.getDossierMedical().getSalarie().getFonction().getFonction()!=null){
                        POS = visiteToShow.getDossierMedical().getSalarie().getFonction().getFonction();
                    }
                    if(visiteToShow.getDossierMedical().getSalarie().getDateNaissance()!=null){
                        NAI = new SimpleDateFormat("dd/MM/yyyy").format(visiteToShow.getDossierMedical().getSalarie().getDateNaissance());
                    }
                    if(visiteToShow.getDossierMedical().getSalarie().getCin()!=null){
                        CIN = visiteToShow.getDossierMedical().getSalarie().getCin();
                    }
                    if(visiteToShow.getDossierMedical().getDateEmbauche()!=null){
                        EMB = new SimpleDateFormat("dd/MM/yyyy").format(visiteToShow.getDossierMedical().getDateEmbauche());
                    } 
                        
                    PdfPCell cellFonction = new PdfPCell(new Paragraph("Fonction/Poste: " + POS,f1));
                    PdfPCell cellNom = new PdfPCell(new Paragraph("Nom/Prénom: "  + NOM,f1));
                    PdfPCell cellMatricule = new PdfPCell(new Paragraph("N° Matricule: " + MAT,f1)); 
                    PdfPCell cellNaissance = new PdfPCell(new Paragraph("Date de naissance: " + NAI,f1)); 
                    PdfPCell cellCin = new PdfPCell(new Paragraph("CIN N°: " + CIN,f1)); 
                    PdfPCell cellEmbauche = new PdfPCell(new Paragraph("Date d'embauche: " + EMB,f1)); 
                     
                    PdfPTable tableEntete = new PdfPTable(20);
                    tableEntete.setTotalWidth(document.getPageSize().getWidth() - 80);
                    tableEntete.setLockedWidth(true);
                    String imageTete  = "/opt/files/docDossierMedicaux/signature/tete.png";
                    Image tete = Image.getInstance(imageTete);    
                    PdfPCell cellImageTete = new PdfPCell(tete);   
                    PdfPCell cellDateVisite= new PdfPCell(new Paragraph("Date: " +new SimpleDateFormat("dd/MM/yyyy").format(visiteToShow.getDateVisite()),f1));
                    cellImageTete.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
                    cellImageTete.setBorder(PdfPCell.NO_BORDER);
                    cellImageTete.setColspan(10);
                    cellImageTete.setPaddingLeft(0);
                    cellImageTete.setPaddingTop(0);
                    tableEntete.addCell(cellImageTete);
                    
                    cellDateVisite.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                    cellDateVisite.setBorder(PdfPCell.NO_BORDER);
                    cellDateVisite.setColspan(10);
                    cellDateVisite.setPaddingTop(20);
                    tableEntete.addCell(cellDateVisite);
                    
                    document.add(tableEntete);
                        
                    PdfPCell cellvide = new PdfPCell(new Paragraph(""));
                    PdfPCell cellTitre = new PdfPCell(new Paragraph(titre0,f));
                    PdfPCell cellTitre2 = new PdfPCell(new Paragraph(titre2,f2));
                    PdfPCell cellTitre1 = new PdfPCell(new Paragraph(titre1,f1));
                    cellTitre.setPadding(0);
                    cellTitre.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                    cellTitre.setBorder(PdfPCell.NO_BORDER); 
                    cellTitre1.setPadding(0);
                    cellTitre1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                    cellTitre1.setBorder(PdfPCell.NO_BORDER); 
                    cellTitre2.setPadding(0);
                    cellTitre2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                    cellTitre2.setBorder(PdfPCell.NO_BORDER); 
                    cellvide.setPadding(0);
                    cellvide.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                    cellvide.setBorder(PdfPCell.NO_BORDER); 
                    tableImg.setTotalWidth(document.getPageSize().getWidth() - 80);
                    tableImg.setLockedWidth(true);
                    cellTitre.setFixedHeight(25f);
                    cellTitre1.setFixedHeight(25f);
                    cellTitre2.setFixedHeight(25f); 
                        
                        
                     
                    PdfPTable table = new PdfPTable(20);
                    table.setTotalWidth(document.getPageSize().getWidth() - 80);
                    table.setLockedWidth(true);

                    cellTitre.setColspan(20);
                    cellTitre.setFixedHeight(25f);
                    table.addCell(cellTitre);

                    cellTitre2.setColspan(20);
                    cellTitre2.setFixedHeight(20f);
                    table.addCell(cellTitre2);

                    cellTitre1.setColspan(20);
                    cellTitre1.setFixedHeight(25f);
                    table.addCell(cellTitre1);

                    cellNom.setColspan(10);
                    cellNom.setFixedHeight(25f);
//                        cellNom.setBorder(PdfPCell.NO_BORDER);
                    table.addCell(cellNom);


                    cellMatricule.setColspan(10);
                    cellMatricule.setFixedHeight(25f);
//                        cellMatricule.setBorder(PdfPCell.NO_BORDER);
                    table.addCell(cellMatricule);

                    cellNaissance.setColspan(10);
                    cellNaissance.setFixedHeight(25f);
//                        cellNaissance.setBorder(PdfPCell.NO_BORDER);
                    table.addCell(cellNaissance);

                    cellCin.setColspan(10);
                    cellCin.setFixedHeight(25f);
//                        cellCin.setBorder(PdfPCell.NO_BORDER);
                    table.addCell(cellCin);

                    cellEmbauche.setColspan(10);
                    cellEmbauche.setFixedHeight(25f);
//                        cellEmbauche.setBorder(PdfPCell.NO_BORDER);
                    table.addCell(cellEmbauche);

                    cellFonction.setColspan(10);
                    cellFonction.setFixedHeight(25f);
//                        cellFonction.setBorder(PdfPCell.NO_BORDER);
                    table.addCell(cellFonction);


                    document.add(table);

                    document.add(vide);
                    Paragraph preface = new Paragraph("Attestation Médicale",f1); 
                    PdfPCell pdfcellProjet = new PdfPCell(preface); 
                    pdfcellProjet.setPadding(0);
                    pdfcellProjet.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                    pdfcellProjet.setBorder(PdfPCell.NO_BORDER); 

                    tableT.setTotalWidth(document.getPageSize().getWidth() - 80);
                    tableT.setLockedWidth(true);
                    tableT.addCell(cellvide);
                    tableT.addCell(pdfcellProjet);
                    tableT.addCell(cellvide); 

                    document.add(tableT);

                    String etat=".......";
                    if(visiteToShow.getApte()!=null) etat=visiteToShow.getApte();
                    document.add(new Paragraph("Je soussigne, Docteur "+visiteToShow.getDocteur()+
                            ", médecin de travail, certifie avoir examiné \n  Ce jour Mr/Mme:  "+ NOM
                            +" . \n Et déclare après examen médical qu'il(elle) est: "+etat,f1));
                    document.add(vide); 
                    if (visiteToShow.getCommentaire()!=null && !"".equals(visiteToShow.getCommentaire())){
                        document.add(new Paragraph("Observation :"+visiteToShow.getCommentaire()+".",f1));  
                    }
                    document.add(vide);   
                    document.add(new Paragraph("Date prochaine visite le: "+new SimpleDateFormat("dd/MM/yyyy").format(visiteToShow.getProchaineVisite())+". \t \t \t \t Cachet et visa du médecin de travail.",f1));

                    PdfPTable tableI = new PdfPTable(2);
                    tableI.setTotalWidth(document.getPageSize().getWidth() - 60);
                    tableI.setLockedWidth(true);
                    String signature;
                    if(visiteToShow.getDocteur()==null){
                        signature="logo.png" ;
                    }else{
                        switch (visiteToShow.getDocteur().toUpperCase()) {
                                case "SAMIR ERRIDA":
                                    signature="1.png" ;
                                    break;
                                case "SAID LATIFDRISSI": 
                                    signature="2.png" ;
                                    break;
                                case "BRAHIM OUGNAOU":
                                    signature="3.png" ;
                                    break;
                                case "JAMILA TALBAOUI": 
                                    signature="4.png" ;
                                    break;
                                case "FADOUA ALOUKY":
                                    signature="5.png" ;
                                    break;
                                case "RABII LAGHRISSI": 
                                    signature="6.png" ;
                                    break;
                                case "ZAKARIA IAICHI": 
                                    signature="7.png" ;
                                    break;
                                default:
                                    signature="logo.png" ;
                                    break;
                            }
                    }
                    System.out.println("---------visiteToShow.getDocteur()----- "+visiteToShow.getDocteur()+ " ------> signature :" + signature);
                    String imageFile = "/opt/files/docDossierMedicaux/signature/"+signature;
                    String imageBas  = "/opt/files/docDossierMedicaux/signature/bas.png";
                    Image img = Image.getInstance(imageFile);  
                    Image bas = Image.getInstance(imageBas);  
                    PdfPCell cellImage = new PdfPCell(img, true); 
                    PdfPCell cellImBas = new PdfPCell(bas, true);

                    cellImBas.setPaddingLeft(0); 
                    cellImage.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                    cellImage.setBorder(PdfPCell.NO_BORDER);  
                    
                     
                    cellImBas.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
                    cellImBas.setBorder(PdfPCell.NO_BORDER);
                    tableI.addCell(cellvide);
                    tableI.addCell(cellImage);

                    tableI.addCell(cellImBas);
                    tableI.addCell(cellvide); 

                    document.add(tableI);




                    document.close();
                    System.out.println("Fin d'éditions !"); 
                    cheminDoc=chemin + "/" + nomFichier;
//                        selectedDoc = cheminContrat.substring(cheminContrat.indexOf("files"));


            } catch (Exception ex) {
                    System.out.println("Erreur d'édition l'affiche engin car "+ex.getCause());
                    document.close();
            } 
        return cheminDoc;
		
    } 
    
    public String systemDateFormat(Date date){
        if(date==null)return "../images/actif.png";
        else if(date.before(new Date())){
            return "../images/rouge.png";
        }else{
            return "../images/actif.png";
        } 
    }
}
