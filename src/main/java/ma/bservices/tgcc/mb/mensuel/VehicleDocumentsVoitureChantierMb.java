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
//import ma.bservices.tgcc.Entity.VoitureChantier;
import ma.bservices.tgcc.service.Mensuel.DocumentService;
//import ma.bservices.tgcc.service.Mensuel.VoitureChantierService;
import static org.apache.catalina.connector.InputBuffer.DEFAULT_BUFFER_SIZE;
import org.apache.commons.io.FilenameUtils;
import org.primefaces.model.UploadedFile;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

/**
 *
 * @author a.wattah
 */
@Component
@ManagedBean(name = "vehiculeDocVoitureChantierMb")
@ViewScoped
public class VehicleDocumentsVoitureChantierMb implements Serializable {

    @ManagedProperty(value = "#{documentService}")
    private DocumentService documentService;

   // @ManagedProperty(value = "#{voitureChantierService}")
   // private VoitureChantierService voitureChantierService;

    ELContext elContext = FacesContext.getCurrentInstance().getELContext();
//    VoitureChantierMb VoitureChantierBean = (VoitureChantierMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "voitureChantierMb");

    private Document documentVoitureChantierToAdd;

    private String commentaire_doc_voitureChantier;
    private String titre_doc_voitureChantier;
    private UploadedFile uploadedFile_doc_voitureChantier;

    private List<Document> lis_document_Voiture_Chantiers;
    private String selectedDoc;

    private Long selectedVehicule;

   //private VoitureChantier selectedVoitureChantier;

    /**
     * getter and settes
     *
     * @return
     */
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
/*
    public VoitureChantierMb getVoitureChantierBean() {
        return VoitureChantierBean;
    }

    public void setVoitureChantierBean(VoitureChantierMb VoitureChantierBean) {
        this.VoitureChantierBean = VoitureChantierBean;
    }

    public VoitureChantierService getVoitureChantierService() {
        return voitureChantierService;
    }

    public void setVoitureChantierService(VoitureChantierService voitureChantierService) {
        this.voitureChantierService = voitureChantierService;
    }
*/
    public Long getSelectedVehicule() {
        return selectedVehicule;
    }

    public void setSelectedVehicule(Long selectedVehicule) {
        this.selectedVehicule = selectedVehicule;
    }

    public Document getDocumentVoitureChantierToAdd() {
        return documentVoitureChantierToAdd;
    }

    public void setDocumentVoitureChantierToAdd(Document documentVoitureChantierToAdd) {
        this.documentVoitureChantierToAdd = documentVoitureChantierToAdd;
    }

    public String getCommentaire_doc_voitureChantier() {
        return commentaire_doc_voitureChantier;
    }
/*
    public VoitureChantier getSelectedVoitureChantier() {
        return selectedVoitureChantier;
    }

    public void setSelectedVoitureChantier(VoitureChantier selectedVoitureChantier) {
        this.selectedVoitureChantier = selectedVoitureChantier;
    }
*/
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

    public void setUploadedFile_doc_voitureChantier(UploadedFile uploadedFile_doc_voitureChantier) {
        this.uploadedFile_doc_voitureChantier = uploadedFile_doc_voitureChantier;
    }

    public List<Document> getLis_document_Voiture_Chantiers() {
        return lis_document_Voiture_Chantiers;
    }

    public void setLis_document_Voiture_Chantiers(List<Document> lis_document_Voiture_Chantiers) {
        this.lis_document_Voiture_Chantiers = lis_document_Voiture_Chantiers;
    }

    public String getSelectedDoc() {
        return selectedDoc;
    }

    public void setSelectedDoc(String selectedDoc) {
        this.selectedDoc = selectedDoc;
    }

    /**
     * end getter and settes
     *
     *
     */
    @PostConstruct
    public void init() {
        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        documentService = ctx.getBean(DocumentService.class);

        //get la voitureSalarie ID
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        Map<String, String> requestParameters = externalContext.getRequestParameterMap();
        if (requestParameters.containsKey("voitureId")) {
            this.selectedVehicule = Long.valueOf(requestParameters.get("voitureId"));
           // this.selectedVoitureChantier = voitureChantierService.get_byId(this.selectedVehicule);

            lis_document_Voiture_Chantiers = documentService.getDocumentByVoiture(this.selectedVehicule);

        }

    }

    /**
     * enregistre document voiture affecter a chantier
     *
     * @param a
     */
    public void saveDocumentChantier(Long vehiculeId) throws IOException {
        
        //this.selectedVoitureChantier = voitureChantierService.get_byId(vehiculeId);

        
        if (titre_doc_voitureChantier.equals("")) {

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, Message.TITRE_OBLIGATOIRE, Message.TITRE_OBLIGATOIRE));
        } else if (uploadedFile_doc_voitureChantier == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, Message.FILE_OBLIGATOIRE, Message.FILE_OBLIGATOIRE));
        } else if (uploadedFile_doc_voitureChantier.getFileName().equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, Message.STRING_CHARGE_DOCUMENT, Message.STRING_CHARGE_DOCUMENT));
        } else {

            documentVoitureChantierToAdd = new Document();
            /*deprectated : saves to target, deleted after application restarts*/
            // String chemin_Document_chantier = TgccFileManager.getCheminFichier("Voiture Chantier");

            /* new code : saves to /opt root */
            String chemin_Document_chantier = ConstanteMb.getRepertoire() + "/files/documentsVoitureChantier";

            Path folder = Paths.get(chemin_Document_chantier);
            Files.createDirectories(folder);
            String filename = FilenameUtils.getBaseName(uploadedFile_doc_voitureChantier.getFileName());
            String extension = FilenameUtils.getExtension(uploadedFile_doc_voitureChantier.getFileName());
            Path file = Files.createTempFile(folder, filename + "-", "." + extension);
            try (InputStream input = uploadedFile_doc_voitureChantier.getInputstream()) {
                Files.copy(input, file, StandardCopyOption.REPLACE_EXISTING);

                documentVoitureChantierToAdd.setChemin(chemin_Document_chantier + "/" + file.getFileName());

             //   documentVoitureChantierToAdd.setVoiture(this.selectedVoitureChantier);
                documentVoitureChantierToAdd.setTitre(titre_doc_voitureChantier);
                documentVoitureChantierToAdd.setCommentaire(commentaire_doc_voitureChantier);

                documentService.save(documentVoitureChantierToAdd);
                titre_doc_voitureChantier = "";
            }

            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            //ec.redirect(ec.getRequestContextPath() + "/mensuel/DocumentVoitureChantier.xhtml?voitureId=" + this.selectedVoitureChantier.getId());
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

        //lis_document_Voiture_Chantiers = documentService.getDocumentByVoiture(VoitureChantierBean.getVoitureChantier_().getId());

//        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
//        ec.redirect(ec.getRequestContextPath() + "/mensuel/DocumentVoitureChantier.xhtml");
    }

    public void visualiser(String chemin) {
        System.out.println("chemin : " + chemin);
        selectedDoc = "";
        selectedDoc = chemin.substring(chemin.indexOf("files"), chemin.length());
        System.out.println("s : " + selectedDoc);
    }

}
