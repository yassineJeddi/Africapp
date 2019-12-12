package ma.bservices.mb;

import static com.sun.faces.el.FacesCompositeELResolver.ELResolverChainType.Faces;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.StandardCopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import org.apache.commons.io.FilenameUtils;

import javax.annotation.PostConstruct;
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
import ma.bservices.tgcc.utilitaire.TgccFileManager;
import org.primefaces.event.FileUploadEvent;

import org.springframework.stereotype.Component;

@Component
@ManagedBean
@SessionScoped
public class DossierSalarieDocMb implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    //@Resource(name = "salarieService")
    //@Autowired
    @ManagedProperty(value = "#{salarieService}")
    private SalarieService salarieService;

    @ManagedProperty(value = "#{parametrageService}")
    private ParametrageService parametrageService;

    @ManagedProperty(value = "#{documentServiceEvol}")
    private DocumentService documentService;

    /**
     * dossier Salarie
     */
    private Salarie salarie = new Salarie();
    private Document document;
    private Document documentcnss;
    private List<TypeDocument> typeDocuments = new LinkedList<>();
    private Integer idTypeDoc;
    private String displayFile;
    private List<TypeDocument> allTypeDocuments;

    public void setAllTypeDocuments(List<TypeDocument> allTypeDocuments) {
        this.allTypeDocuments = allTypeDocuments;
    }

    public List<TypeDocument> getAllTypeDocuments() {
        return allTypeDocuments;
    }

  


    //getter & setter
    public String getDisplayFile() {
        return displayFile;
    }

    public void setDisplayFile(String displayFile) {
        this.displayFile = displayFile;
    }

    public Integer getIdTypeDoc() {
        return idTypeDoc;
    }

    public void setIdTypeDoc(Integer idTypeDoc) {
        this.idTypeDoc = idTypeDoc;
    }

    public SalarieService getSalarieService() {
        return salarieService;
    }

    public void setSalarieService(SalarieService salarieService) {
        this.salarieService = salarieService;
    }

    public List<TypeDocument> getTypeDocuments() {
        return typeDocuments;
    }

    public void setTypeDocuments(List<TypeDocument> typeDocuments) {
        this.typeDocuments = typeDocuments;
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

    public Salarie getSalarie() {
        return salarie;
    }

    public void setSalarie(Salarie salarie) {
        this.salarie = salarie;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
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
        allTypeDocuments=parametrageService.listeTypesDocuments(0,10,"");
        
    }

    public String documentSalarie(Integer idSalarie) {
        System.out.println("matricule salarie " + idSalarie);
        salarie = salarieService.getSalarie(idSalarie);
        typeDocuments = salarieService.listeDocumentNonAjoutes(salarie);
       //FacesContext.getCurrentInstance().getExternalContext().getFlash().put("salarie", salarie);
        //FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("idSalarie", idSalarie);

        return "completerdossier.xhtml?faces-redirect=true&salarieId = " + idSalarie;
    }
   
    public void save(FileUploadEvent event) throws IOException {
        /**
         * Partie CIN /resources/document salarie/
         */
        System.out.println("___________ before upload__________ " + idTypeDoc);
        document = new Document();
        String chemin = TgccFileManager.getCheminFichier("DocumentSalarie");
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
            cheminCIN =  cheminCIN.substring(cheminCIN.indexOf("files"));
            document.setChemin(cheminCIN);
            document.setTitre("CIN");
            document.setDateCreation((new Date()));
            document.setDateModification((new Date()));
            document.setCommentaire("");
            document.setSalarie(salarie);
            TypeDocument typedoc = parametrageService.getTypeDocument("");
            document.setTypeDocument(typedoc);
            documentService.ajouterDocument(document);
            Module.message(0, "Document " + document.getTypeDocument().getTypeDocument() + " ajoutée avec succés", "");
        } catch (IOException ex) {
            System.out.println("@@@erreur d'ajouter document " + ex.getMessage());
            Module.message(3, "echec d'ajout du document", "");
        } catch (Exception ex) {
            System.out.println("@@@erreur d'ajouter document " + ex.getMessage());
            Module.message(3, "echec d'ajout du document", "");
        }

        salarie = new Salarie();
    }

    public void supprimerDocument(Document doc) {
        documentService.supprimerDocument(doc);
        displayFile = "none";
    }

    public String resultCreate() {
        try {
            finalize();
        } catch (Throwable ex) {
            System.out.println("### erreur finalise => " + ex.getMessage());
        }
        return "salaries.xhtml?faces-redirect=true";
    }

}
