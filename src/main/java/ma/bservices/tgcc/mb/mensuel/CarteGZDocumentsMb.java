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
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import ma.bservice.tgcc.Constante.Message;
import ma.bservices.beans.Document;
import ma.bservices.mb.services.ConstanteMb;
import ma.bservices.tgcc.Entity.CarteGasoil;
import ma.bservices.tgcc.service.Mensuel.DocumentService;
import static org.apache.catalina.connector.InputBuffer.DEFAULT_BUFFER_SIZE;
import org.apache.commons.io.FilenameUtils;
import org.primefaces.model.UploadedFile;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

/**
 *
 * @author yassine.jeddi
 */

@Component
@ManagedBean(name = "carteGZDocMb")
@ViewScoped
public class CarteGZDocumentsMb implements Serializable{
    
    @ManagedProperty(value = "#{documentService}")
    private DocumentService documentService;


    ELContext elContext = FacesContext.getCurrentInstance().getELContext();
    //VoitureSalarieMb voitureSalarieBean = (VoitureSalarieMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "voituresMb");
    //VoitureChantierMb VoitureChantierBean = (VoitureChantierMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "voitureChantierMb");
    GestionVoitureMb gestionVoitureBean = (GestionVoitureMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "GestionVoitureMb");

    private Document document;
    private Document documentCarteToAdd;
    private static String chemin;
    private UploadedFile uploadedFile;
    private List<Document> listDoc;
    private List<Document> listDoc2;
    private String commentaire;
    private String titre;
    private String selectedDoc;
    private Long selectedCarte;
    
    
    /**
     * methode pour enregistre les information des document de Carte gazoile affecte a
     * chantier
     */
    
    /******************************************
     *********** Déclaration des variables ****
     ******************************************/
    private String commentaire_doc_carte; //commentaire_doc_voitureChantier
    private String titre_doc_carte;//titre_doc_voitureChantier
    private UploadedFile uploadedFile_doc_carte; //uploadedFile_doc_voitureChantier
    private List<Document> lis_document_carte;//lis_document_Voiture_Chantiers

    /******************************************
     *********** Geters & Seters **************
     ******************************************/
    
    private CarteGasoil carteShow = new CarteGasoil();

    public DocumentService getDocumentService() {
        return documentService;
    }

    public void setDocumentService(DocumentService documentService) {
        this.documentService = documentService;
    }

    public ELContext getElContext() {
        return elContext;
    }

    public void setElContext(ELContext elContext) {
        this.elContext = elContext;
    }

    public GestionVoitureMb getGestionVoitureBean() {
        return gestionVoitureBean;
    }

    public void setGestionVoitureBean(GestionVoitureMb gestionVoitureBean) {
        this.gestionVoitureBean = gestionVoitureBean;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public Document getDocumentCarteToAdd() {
        return documentCarteToAdd;
    }

    public void setDocumentCarteToAdd(Document documentCarteToAdd) {
        this.documentCarteToAdd = documentCarteToAdd;
    }

    public static String getChemin() {
        return chemin;
    }

    public static void setChemin(String chemin) {
        CarteGZDocumentsMb.chemin = chemin;
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

    public List<Document> getListDoc2() {
        return listDoc2;
    }

    public void setListDoc2(List<Document> listDoc2) {
        this.listDoc2 = listDoc2;
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

    public String getSelectedDoc() {
        return selectedDoc;
    }

    public void setSelectedDoc(String selectedDoc) {
        this.selectedDoc = selectedDoc;
    }

    public Long getSelectedCarte() {
        return selectedCarte;
    }

    public void setSelectedCarte(Long selectedCarte) {
        this.selectedCarte = selectedCarte;
    }

    public String getCommentaire_doc_carte() {
        return commentaire_doc_carte;
    }

    public void setCommentaire_doc_carte(String commentaire_doc_carte) {
        this.commentaire_doc_carte = commentaire_doc_carte;
    }

    public String getTitre_doc_carte() {
        return titre_doc_carte;
    }

    public void setTitre_doc_carte(String titre_doc_carte) {
        this.titre_doc_carte = titre_doc_carte;
    }

    public UploadedFile getUploadedFile_doc_carte() {
        return uploadedFile_doc_carte;
    }

    public void setUploadedFile_doc_carte(UploadedFile uploadedFile_doc_carte) {
        this.uploadedFile_doc_carte = uploadedFile_doc_carte;
    }

    public List<Document> getLis_document_carte() {
        return lis_document_carte;
    }

    public void setLis_document_carte(List<Document> lis_document_carte) {
        this.lis_document_carte = lis_document_carte;
    }

    public CarteGasoil getCarteShow() {
        return carteShow;
    }

    public void setCarteShow(CarteGasoil carteShow) {
        this.carteShow = carteShow;
    }
    
    
    
    
    @PostConstruct
    public void init() {
        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        documentService = ctx.getBean(DocumentService.class);
        //get la voitureSalarie ID
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        Map<String, String> requestParameters = externalContext.getRequestParameterMap();
        
        if (requestParameters.containsKey("carteGZID")) {
            this.selectedCarte = Long.valueOf(requestParameters.get("carteGZID"));
            listDoc = documentService.getDocumentByVoiture(this.selectedCarte);  
        }
    }
    
    
    public void saveDocumentCarte() throws IOException {

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
                String chemin = ConstanteMb.getRepertoire() + "/files/documentsCarteGazoile";

                Path folder = Paths.get(chemin);
                Files.createDirectories(folder);

                Path file = Files.createTempFile(folder, filename + "-", "." + extension);
                try (InputStream input = uploadedFile.getInputstream()) {
                    Files.copy(input, file, StandardCopyOption.REPLACE_EXISTING);
                    document.setChemin(chemin + "/" + file.getFileName());
                    document.setCarteGasoil(carteShow);
                    document.setCommentaire(commentaire);
                    document.setTitre(titre);
                    documentService.save(document);
                    titre = "";
                }

                ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
                ec.redirect(ec.getRequestContextPath() + "/mensuel/cartesGazoile.xhtml");
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
     public void removeGest(Document a) throws IOException {
        documentService.deleteDocument(a);
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("" + Message.DELETE_DOCUMENT, Message.DELETE_DOCUMENT));
        listDoc = documentService.getDocumentByCarteGZ(carteShow.getId());

    }
     
    public void visualiser(String chemin) {
        System.out.println("chemin : " + chemin);
        selectedDoc = "";
        selectedDoc = chemin.substring(chemin.indexOf("files"), chemin.length());
        System.out.println("s : " + selectedDoc);
    }

}
