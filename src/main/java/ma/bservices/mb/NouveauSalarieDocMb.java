package ma.bservices.mb;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.StandardCopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import org.apache.commons.io.FilenameUtils;

import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import ma.bservices.beans.Document;

import ma.bservices.beans.Salarie;
import ma.bservices.beans.TypeDocument;
import ma.bservices.mb.services.Module;

import ma.bservices.services.DocumentService;
import ma.bservices.services.ParametrageService;
import ma.bservices.services.SalarieService;
import ma.bservices.tgcc.authentification.Authentification;
import ma.bservices.tgcc.utilitaire.TgccFileManager;
import org.primefaces.event.FileUploadEvent;

import org.springframework.stereotype.Component;

@Component
@ManagedBean
@SessionScoped
public class NouveauSalarieDocMb implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @ManagedProperty(value = "#{salarieService}")
    private SalarieService salarieService;

    @ManagedProperty(value = "#{parametrageService}")
    private ParametrageService parametrageService;

    @ManagedProperty(value = "#{documentServiceEvol}")
    private DocumentService documentService;

    /**
     * NouveauSalarie
     */
    private Salarie addSalarie = new Salarie();
    private String cheminPhotoSalarie;
    // private int ;
    private Boolean disable;
    private Document document;
    private String chemin;
    private Document documentcnss;
    private String chemincnss;
    private String displayFile, displayFileCnss;

    public String getDisplayFile() {
        return displayFile;
    }

    public void setDisplayFile(String displayFile) {
        this.displayFile = displayFile;
    }

    public String getDisplayFileCnss() {
        return displayFileCnss;
    }

    public void setDisplayFileCnss(String displayFileCnss) {
        this.displayFileCnss = displayFileCnss;
    }

    public SalarieService getSalarieService() {
        return salarieService;
    }

    public void setSalarieService(SalarieService salarieService) {
        this.salarieService = salarieService;
    }

    public ParametrageService getParametrageService() {
        return parametrageService;
    }

    public void setParametrageService(ParametrageService parametrageService) {
        this.parametrageService = parametrageService;
    }

    public DocumentService getDocumentService() {
        return documentService;
    }

    public void setDocumentService(DocumentService documentService) {
        this.documentService = documentService;
    }

    public void setAddSalarie(Salarie addSalarie) {
        this.addSalarie = addSalarie;
    }

    public Salarie getAddSalarie() {
        return addSalarie;
    }

    public String getCheminPhotoSalarie() {
        return cheminPhotoSalarie;
    }

    public void setCheminPhotoSalarie(String cheminPhotoSalarie) {
        this.cheminPhotoSalarie = cheminPhotoSalarie;
    }

    public Boolean getDisable() {
        return disable;
    }

    public void setDisable(Boolean disable) {
        this.disable = disable;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public String getChemin() {
        return chemin;
    }

    public void setChemin(String chemin) {
        this.chemin = chemin;
    }

    public String getChemincnss() {
        return chemincnss;
    }

    public void setChemincnss(String chemincnss) {
        this.chemincnss = chemincnss;
    }

    public Document getDocumentcnss() {
        return documentcnss;
    }

    public void setDocumentcnss(Document documentcnss) {
        this.documentcnss = documentcnss;
    }

    /**
     * *********
     * Methodes
     */
    @PostConstruct
    public void init() {
        System.out.println("___faces ___ " + FacesContext.getCurrentInstance().toString());
        salarieService = Module.ctx.getBean(SalarieService.class);
        documentService = Module.ctx.getBean(DocumentService.class);
    }

    public String documentSalarie(Integer idSalarie) {
        System.out.println("matricule salarie " + idSalarie);
        addSalarie = salarieService.getSalarie(idSalarie);
        cheminPhotoSalarie = "../resources/imageUser/user.png";
        chemincnss = "";
        chemin = "";
        displayFile = "none";
        displayFileCnss = "none";
        return "nouveausalariedocx.xhtml?faces-redirect=true";
    }

    public void savePhoto(FileUploadEvent event) throws IOException {
        /**
         * upload photo salarie
         */
        cheminPhotoSalarie = TgccFileManager.getArboFichier("PhotosSalarie");

        Path folder = Paths.get(cheminPhotoSalarie);
        Files.createDirectories(folder);
        String filename = FilenameUtils.getBaseName(event.getFile().getFileName());
        String extension = FilenameUtils.getExtension(event.getFile().getFileName());
        if (!"PNG".equals(extension.toUpperCase())
                && !"JPEG".equals(extension.toUpperCase())
                && !"JPG".equals(extension.toUpperCase())) {
            Module.message(2, "Veuillez choisir une image de type ", "(jpeg, jpg, png)");
            return;
        }
        Path file = Files.createTempFile(folder, filename + "-", "." + extension);
        try (InputStream input = event.getFile().getInputstream()) {
            Files.copy(input, file, StandardCopyOption.REPLACE_EXISTING);

            cheminPhotoSalarie = cheminPhotoSalarie + "/" + file.getFileName();
            cheminPhotoSalarie = cheminPhotoSalarie.substring(cheminPhotoSalarie.indexOf("files"));
            System.out.println("photo salarie " + cheminPhotoSalarie);

            addSalarie.setCheminPhoto(cheminPhotoSalarie);
            salarieService.modifierInformationsSalarie(addSalarie);
        }

    }

    public String resultCreate() {
        Integer idSalarie = addSalarie.getId();
       
        
        try {
            finalize();
        } catch (Throwable ex) {
            System.out.println("### erreur finalise => " + ex.getMessage());
        }
        return "/evol/detail.xhtml?faces-redirect=true&salarieId=" + idSalarie;
    }
    
        public String resultCreate(int idSalarie) {         
        try {
            finalize();
        } catch (Throwable ex) {
            System.out.println("### erreur finalise => " + ex.getMessage());
        }
        return "/evol/detail.xhtml?faces-redirect=true&salarieId=" + idSalarie;
    }

    public void saveCin(FileUploadEvent event) throws IOException {
        /**
         * Partie CIN /resources/document salarie/
         */
        System.out.println("___________ Test 1 __________ ");
        document = new Document();
        chemin = TgccFileManager.getArboFichier("DocumentSalarie");
        if (chemin.equals("")) {
            Module.message(3, "echec d'enregistrement de document ", "");
            return;
        }
        Path folder = Paths.get(chemin);
        Files.createDirectories(folder);
        String filename = FilenameUtils.getBaseName(event.getFile().getFileName());
        String extension = FilenameUtils.getExtension(event.getFile().getFileName());
        if (!"PDF".equals(extension.toUpperCase())) {
            Module.message(3, "type fichier doit être un pdf", "");
            return;
        }
        Path file = Files.createTempFile(folder, filename + "-", "." + extension);
        try (InputStream input = event.getFile().getInputstream()) {
            Files.copy(input, file, StandardCopyOption.REPLACE_EXISTING);
            String cheminCIN = chemin + "/" + file.getFileName();
            cheminCIN = cheminCIN.substring(cheminCIN.indexOf("files"));
            chemin = cheminCIN;
            ELContext elContext = FacesContext.getCurrentInstance().getELContext();
            Authentification authentification = (Authentification) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "authentification");
            String demand = authentification.get_connected_user().getLogin();
            document.setChemin(cheminCIN);
            document.setTitre("CIN");
            document.setDateCreation((new Date()));
            document.setCommentaire("");
            document.setSalarie(addSalarie);
            document.setCreePar(demand);
            TypeDocument typedoc = parametrageService.getTypeDocument("Cin");
            document.setTypeDocument(typedoc);
            documentService.ajouterDocument(document);
            displayFile = "block";
        } catch (IOException ex) {
            System.out.println("@@@erreur d'ajouter document " + ex.getMessage());
            Module.message(3, "echec d'ajout du document", "");
        } catch (Exception ex) {
            System.out.println("@@@erreur d'ajouter document " + ex.getMessage());
            Module.message(3, "echec d'ajout du document", "");
        }
    }

    public void saveCnss(FileUploadEvent event) throws IOException {
        /**
         * Partie CNSS
         */
        documentcnss = new Document();
        chemincnss = TgccFileManager.getArboFichier("DocumentSalarie");
        Path folder1 = Paths.get(chemincnss);
        Files.createDirectories(folder1);
        String filename1 = FilenameUtils.getBaseName(event.getFile().getFileName());
        String extension1 = FilenameUtils.getExtension(event.getFile().getFileName());
        if (!"PDF".equals(extension1.toUpperCase())) {
            Module.message(3, "type fichier doit être un pdf", "");
            return;
        }
        Path file1 = Files.createTempFile(folder1, filename1 + "-", "." + extension1);
        try (InputStream input1 = event.getFile().getInputstream()) {
            Files.copy(input1, file1, StandardCopyOption.REPLACE_EXISTING);
            String cheminCNSS = chemincnss + "/" + file1.getFileName();
            cheminCNSS = cheminCNSS.substring(cheminCNSS.indexOf("files"));
            chemincnss = cheminCNSS;
            documentcnss.setChemin(cheminCNSS);
            documentcnss.setTitre("CNSS");
            documentcnss.setDateCreation((new Date()));
            documentcnss.setDateModification((new Date()));
            documentcnss.setCommentaire("");
            documentcnss.setSalarie(addSalarie);
            ELContext elContext = FacesContext.getCurrentInstance().getELContext();
            Authentification authentification = (Authentification) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "authentification");
            String demand = authentification.get_connected_user().getLogin();
            documentcnss.setCreePar(demand);
            TypeDocument typedoc1 = parametrageService.getTypeDocument("Cnss");
            documentcnss.setTypeDocument(typedoc1);
            documentService.ajouterDocument(documentcnss);
            displayFileCnss = "block";
        } catch (IOException ex) {
            System.out.println("@@@erreur d'ajouter document " + ex.getMessage());
            Module.message(3, "echec d'ajout du document", "");
        } catch (Exception ex) {
            System.out.println("@@@erreur d'ajouter document " + ex.getMessage());
            Module.message(3, "echec d'ajout du document", "");
        }
    }

    public void supprimerDocumentCin() {
        documentService.supprimerDocument(document);
        chemin = "";
        displayFile = "none";
    }

    public void supprimerDocumentCnss() {
        documentService.supprimerDocument(documentcnss);
        displayFileCnss = "none";
        chemincnss = "";
    }
}
