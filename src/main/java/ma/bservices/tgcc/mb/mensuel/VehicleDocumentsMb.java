/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.mb.mensuel;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import ma.bservice.tgcc.Constante.Message;
import ma.bservices.beans.Document;
import ma.bservices.mb.services.ConstanteMb;
import ma.bservices.tgcc.Entity.Loyer;
import ma.bservices.tgcc.Entity.Mensuel;
import ma.bservices.tgcc.Entity.Voiture;
import ma.bservices.tgcc.service.Mensuel.DocumentService;
import ma.bservices.tgcc.utilitaire.TgccFileManager;
import static org.apache.catalina.connector.InputBuffer.DEFAULT_BUFFER_SIZE;
import org.apache.commons.io.FilenameUtils;
import org.primefaces.model.UploadedFile;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

/**
 *
 * @author zakaria.dem
 */
@Component
@ManagedBean(name = "vehiculeDocMb")
@ViewScoped
public class VehicleDocumentsMb implements Serializable {

    @ManagedProperty(value = "#{documentService}")
    private DocumentService documentService;


    ELContext elContext = FacesContext.getCurrentInstance().getELContext();
    //VoitureSalarieMb voitureSalarieBean = (VoitureSalarieMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "voituresMb");
    //VoitureChantierMb VoitureChantierBean = (VoitureChantierMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "voitureChantierMb");
    GestionVoitureMb gestionVoitureBean = (GestionVoitureMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "GestionVoitureMb");

    private Document document;
    private Document documentVoitureChantierToAdd;
    private static String chemin;
    private UploadedFile uploadedFile;
    private List<Document> listDoc;
    private List<Document> listDoc2;
    private String commentaire;
    private String titre;
    private String selectedDoc;
    private Long selectedVehicule;

    /**
     * methode pour enregistre les information des document de voiture affecte a
     * chantier
     */
    private String commentaire_doc_voitureChantier;
    private String titre_doc_voitureChantier;
    private UploadedFile uploadedFile_doc_voitureChantier;
    private List<Document> lis_document_Voiture_Chantiers;

    private Voiture voitureShow = new Voiture();

    /**
     * getter and settes
     *
     * @return
     */
    public String getCommentaire_doc_voitureChantier() {
        return commentaire_doc_voitureChantier;
    }

    public String getSelectedDoc() {
        return selectedDoc;
    }

    public void setSelectedDoc(String selectedDoc) {
        this.selectedDoc = selectedDoc;
    }

    public void setCommentaire_doc_voitureChantier(String commentaire_doc_voitureChantier) {
        this.commentaire_doc_voitureChantier = commentaire_doc_voitureChantier;
    }

    public String getTitre_doc_voitureChantier() {
        return titre_doc_voitureChantier;
    }

    public void setTitre_doc_voitureChantier(String titre_doc_voitureChantier) {
        this.titre_doc_voitureChantier = titre_doc_voitureChantier;
    }

    public UploadedFile getUploadedFile_doc_voitureChantier() {
        return uploadedFile_doc_voitureChantier;
    }

    public GestionVoitureMb getGestionVoitureBean() {
        return gestionVoitureBean;
    }

    public Long getSelectedVehicule() {
        return selectedVehicule;
    }

    public void setSelectedVehicule(Long selectedVehicule) {
        this.selectedVehicule = selectedVehicule;
    }

    
    
    public void setGestionVoitureBean(GestionVoitureMb gestionVoitureBean) {
        this.gestionVoitureBean = gestionVoitureBean;
    }

    public void setUploadedFile_doc_voitureChantier(UploadedFile uploadedFile_doc_voitureChantier) {
        this.uploadedFile_doc_voitureChantier = uploadedFile_doc_voitureChantier;
    }

    public List<Document> getLis_document_Voiture_Chantiers() {
        return lis_document_Voiture_Chantiers;
    }

    public Voiture getVoitureShow() {
        return voitureShow;
    }

    public void setVoitureShow(Voiture voitureShow) {
        this.voitureShow = voitureShow;
    }

    public void setLis_document_Voiture_Chantiers(List<Document> lis_document_Voiture_Chantiers) {
        this.lis_document_Voiture_Chantiers = lis_document_Voiture_Chantiers;
    }
/*
    public VoitureChantierMb getVoitureChantierBean() {
        return VoitureChantierBean;
    }

    public void setVoitureChantierBean(VoitureChantierMb VoitureChantierBean) {
        this.VoitureChantierBean = VoitureChantierBean;
    }
*/
    public Document getDocumentVoitureChantierToAdd() {
        return documentVoitureChantierToAdd;
    }

    public void setDocumentVoitureChantierToAdd(Document documentVoitureChantierToAdd) {
        this.documentVoitureChantierToAdd = documentVoitureChantierToAdd;
    }

    public DocumentService getDocumentService() {
        return documentService;
    }

    public void setDocumentService(DocumentService documentService) {
        this.documentService = documentService;
    }

    public List<Document> getListDoc2() {
        return listDoc2;
    }

    public void setListDoc2(List<Document> listDoc2) {
        this.listDoc2 = listDoc2;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public static String getChemin() {
        return chemin;
    }

    public static void setChemin(String chemin) {
        VehicleDocumentsMb.chemin = chemin;
    }

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public List<Document> getListDoc() {
        return listDoc;
    }

    public void setListDoc(List<Document> listDoc) {
        this.listDoc = listDoc;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }
/*
    public VoitureSalarieMb getVoitureSalarieBean() {
        return voitureSalarieBean;
    }

    public void setVoitureSalarieBean(VoitureSalarieMb voitureSalarieBean) {
        this.voitureSalarieBean = voitureSalarieBean;
    }
*/
    public ELContext getElContext() {
        return elContext;
    }

    public void setElContext(ELContext elContext) {
        this.elContext = elContext;
    }

    /**
     * Creates a new instance of VehicleDocumentsMb
     */
    public VehicleDocumentsMb() {

    }

    @PostConstruct
    public void init() {
        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        documentService = ctx.getBean(DocumentService.class);

        //get la voitureSalarie ID
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        Map<String, String> requestParameters = externalContext.getRequestParameterMap();
        if (requestParameters.containsKey("voitureID")) {
            this.selectedVehicule = Long.valueOf(requestParameters.get("voitureID"));
                    
            listDoc = documentService.getDocumentByVoiture(this.selectedVehicule);
            
        }

        //get la voitureChantier ID
        //listDoc2 = documentService.getDocumentByVoiture(gestionVoitureBean.getVoitureToShow().getId());
        
    }

    /**
     * Partie Document enregistre document salarie
     */
    public void saveDocumentSalarie() throws IOException {

        if (titre.equals("")) {

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, Message.TITRE_OBLIGATOIRE, Message.TITRE_OBLIGATOIRE));

        } else if (uploadedFile == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, Message.FILE_OBLIGATOIRE, Message.FILE_OBLIGATOIRE));
        } else if (uploadedFile.getFileName().equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, Message.STRING_CHARGE_DOCUMENT, Message.STRING_CHARGE_DOCUMENT));
        } else {
            String filename = FilenameUtils.getBaseName(uploadedFile.getFileName());
            String extension = FilenameUtils.getExtension(uploadedFile.getFileName());

            if (!"pdf".equals(extension)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, Message.STRING_CHARGE_DOCUMENT_PDF, Message.STRING_CHARGE_DOCUMENT_PDF));
            } else {
                document = new Document();
                /*deprectated : saves to target, deleted after application restarts*/
                // chemin = TgccFileManager.getCheminFichier("Voiture Salarie");

                /* new code : saves to /opt root */
                String chemin = ConstanteMb.getRepertoire() + "/files/documentsVoitureSalarie";

                Path folder = Paths.get(chemin);
                Files.createDirectories(folder);

                Path file = Files.createTempFile(folder, filename + "-", "." + extension);
                try (InputStream input = uploadedFile.getInputstream()) {
                    Files.copy(input, file, StandardCopyOption.REPLACE_EXISTING);
                    document.setChemin(chemin + "/" + file.getFileName());
                    
                    document.setCommentaire(commentaire);
                    document.setTitre(titre);
                    documentService.save(document);
                    titre = "";
                }

                ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
                ec.redirect(ec.getRequestContextPath() + "/mensuel/docVoiture.xhtml?voitureID=" + this.selectedVehicule);
            }

        }
    }

    public void saveDocumentVoitureGestion() throws IOException {

        if (titre.equals("")) {

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, Message.TITRE_OBLIGATOIRE, Message.TITRE_OBLIGATOIRE));

        } else if (uploadedFile == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, Message.FILE_OBLIGATOIRE, Message.FILE_OBLIGATOIRE));
        } else if (uploadedFile.getFileName().equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, Message.STRING_CHARGE_DOCUMENT, Message.STRING_CHARGE_DOCUMENT));
        } else {
            String filename = FilenameUtils.getBaseName(uploadedFile.getFileName());
            String extension = FilenameUtils.getExtension(uploadedFile.getFileName());

            if (!"pdf".equals(extension)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, Message.STRING_CHARGE_DOCUMENT_PDF, Message.STRING_CHARGE_DOCUMENT_PDF));
            } else {
                document = new Document();
                /*deprecated : docs saved in /opt/files -- outside target */
                //chemin = TgccFileManager.getCheminFichier("Docs Voiture");
                /* new code, save in opt/files */
                chemin = ConstanteMb.getRepertoire() + "/files/documentsVoiture";
                Path folder = Paths.get(chemin);
                Files.createDirectories(folder);

                Path file = Files.createTempFile(folder, filename + "-", "." + extension);
                try (InputStream input = uploadedFile.getInputstream()) {
                    Files.copy(input, file, StandardCopyOption.REPLACE_EXISTING);
                    document.setChemin(chemin + "/" + file.getFileName());
                    System.out.println("===================================== ||||||| voiture : +++++ " + voitureShow.getMatriculevoiture());
                    document.setVoiture(voitureShow);
                    document.setCommentaire(commentaire);
                    document.setTitre(titre);
                    documentService.save(document);
                    titre = "";
                }

                ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
                ec.redirect(ec.getRequestContextPath() + "/mensuel/CreationVoiture.xhtml");
            }

        }
    }

    public void saveDocumentLoyer(Loyer l) throws IOException {
        System.out.println(":::::::::::> Loyer :"+l.toString());
        if (titre.equals("")) {

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, Message.TITRE_OBLIGATOIRE, Message.TITRE_OBLIGATOIRE));

        } else if (uploadedFile == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, Message.FILE_OBLIGATOIRE, Message.FILE_OBLIGATOIRE));
        } else if (uploadedFile.getFileName().equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, Message.STRING_CHARGE_DOCUMENT, Message.STRING_CHARGE_DOCUMENT));
        } else {
            String filename = FilenameUtils.getBaseName(uploadedFile.getFileName());
            String extension = FilenameUtils.getExtension(uploadedFile.getFileName());

            if (!"pdf".equals(extension)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, Message.STRING_CHARGE_DOCUMENT_PDF, Message.STRING_CHARGE_DOCUMENT_PDF));
            } else {
                document = new Document();
                /*deprecated : docs saved in /opt/files -- outside target */
                //chemin = TgccFileManager.getCheminFichier("Docs Voiture");
                /* new code, save in opt/files */
                chemin = ConstanteMb.getRepertoire() + "/files/documentsLoyer";
                Path folder = Paths.get(chemin);
                Files.createDirectories(folder);

                Path file = Files.createTempFile(folder, filename + "-", "." + extension);
                try (InputStream input = uploadedFile.getInputstream()) {
                    Files.copy(input, file, StandardCopyOption.REPLACE_EXISTING);
                    document.setChemin(chemin + "/" + file.getFileName());
                    document.setLoyer(l);
                    document.setCommentaire(commentaire);
                    document.setTitre(titre);
                    System.out.println(":::::>  Document : "+document.toString());
                    documentService.save(document);
                    titre = "";
                }
                catch(Exception e){
                    System.out.println("Erreur d'enregistrement du Document VMB car : "+e.getMessage());
                }
                        
/*
                ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
                ec.redirect(ec.getRequestContextPath() + "/mensuel/CreationVoiture.xhtml");
*/
            }

        }
    }

    public void downLoad(Document a) throws IOException {

        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletResponse response = (HttpServletResponse) context
                .getExternalContext().getResponse();
        File file = new File(a.getChemin());
        if (!file.exists()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        response.reset();
        response.setBufferSize(DEFAULT_BUFFER_SIZE);
        response.setContentType("application/octet-stream");
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
            input.close();
            output.close();
        }
        context.responseComplete();
    }

    public void remove(Document a) throws IOException {
        documentService.deleteDocument(a);

        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("" + Message.DELETE_DOCUMENT, Message.DELETE_DOCUMENT));


//        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
//        ec.redirect(ec.getRequestContextPath() + "/mensuel/docVoiture.xhtml");
    }

    public void removeGest(Document a) throws IOException {
        documentService.deleteDocument(a);

        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("" + Message.DELETE_DOCUMENT, Message.DELETE_DOCUMENT));

        listDoc = documentService.getDocumentByVoiture(voitureShow.getId());

//        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
//        ec.redirect(ec.getRequestContextPath() + "/mensuel/docVoiture.xhtml");
    }

    public void visualiser(String chemin) {
        System.out.println("chemin : " + chemin);
        selectedDoc = "";
        selectedDoc = chemin.substring(chemin.indexOf("files"), chemin.length());
        System.out.println("s : " + selectedDoc);
    }

}
