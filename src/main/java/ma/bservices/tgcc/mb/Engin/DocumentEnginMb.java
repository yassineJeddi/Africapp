/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.mb.Engin;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import ma.bservice.tgcc.Constante.Message;
import ma.bservices.mb.services.ConstanteMb;
import ma.bservices.tgcc.Entity.DocumentEngin;
import ma.bservices.tgcc.Entity.Engin;
import ma.bservices.tgcc.Entity.Mensuel;
import ma.bservices.tgcc.pdf.FicheEngin;
import ma.bservices.tgcc.service.Engin.Bean.CiterneServiceBean;
import ma.bservices.tgcc.service.Engin.DocumentEnginService;
import ma.bservices.tgcc.service.Engin.EnginService;
import ma.bservices.tgcc.utilitaire.Outils;
import ma.bservices.tgcc.utilitaire.TgccFileManager;
import org.apache.commons.io.FilenameUtils;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

/**
 *
 * @author a.wattah
 */
@Component
@ManagedBean(name = "documentEnginMb")
@ViewScoped
public class DocumentEnginMb implements Serializable {

    @ManagedProperty(value = "#{documentEnginService}")
    private DocumentEnginService documentEnginService;

    @ManagedProperty(value = "#{enginService}")
    private EnginService enginSerive;

    private DocumentEngin documentEngin = new DocumentEngin();

    private Engin enginToaddDocument = new Engin();

    private UploadedFile uploadedFile;

    private List<DocumentEngin> l_DocumentEngins = new ArrayList<>();

    private String selectedDoc;

    /**
     * getters and setters
     */
    public DocumentEnginService getDocumentEnginService() {
        return documentEnginService;
    }

    public void setDocumentEnginService(DocumentEnginService documentEnginService) {
        this.documentEnginService = documentEnginService;
    }

    public DocumentEngin getDocumentEngin() {
        return documentEngin;
    }

    public void setDocumentEngin(DocumentEngin documentEngin) {
        this.documentEngin = documentEngin;
    }

    public Engin getEnginToaddDocument() {
        return enginToaddDocument;
    }

    public void setEnginToaddDocument(Engin enginToaddDocument) {
        this.enginToaddDocument = enginToaddDocument;
    }

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public List<DocumentEngin> getL_DocumentEngins() {
        return l_DocumentEngins;
    }

    public void setL_DocumentEngins(List<DocumentEngin> l_DocumentEngins) {
        this.l_DocumentEngins = l_DocumentEngins;
    }

    public EnginService getEnginSerive() {
        return enginSerive;
    }

    public void setEnginSerive(EnginService enginSerive) {
        this.enginSerive = enginSerive;
    }

    public String getSelectedDoc() {
        return selectedDoc;
    }

    public void setSelectedDoc(String selectedDoc) {
        this.selectedDoc = selectedDoc;
    }

    @PostConstruct
    public void init() {

        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        documentEnginService = ctx.getBean(DocumentEnginService.class);

        documentEngin = new DocumentEngin();
        enginToaddDocument = new Engin();

        Integer id = -1;
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        Map<String, String> requestParameters = externalContext.getRequestParameterMap();
        if (requestParameters.containsKey("enginId")) {
            id = Integer.valueOf(requestParameters.get("enginId"));
            enginToaddDocument = enginSerive.findOneById(id);
            if (enginToaddDocument instanceof Engin) {
                l_DocumentEngins = this.documentEnginService.getListDocumentEngins(enginToaddDocument.getiDEngin());
            }
        }

    }

    public void redirectEngin_Document(Engin selected) {
        enginToaddDocument = selected;
        l_DocumentEngins = this.documentEnginService.getListDocumentEngins(selected.getiDEngin());
    }

    /**
     * uploader file engin
     */
    public void uploader() throws IOException {

        // String chemin = TgccFileManager.getCheminFichier("Documents Engin");
        String chemin = ConstanteMb.getRepertoire() + "/files/docsEngin";
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

                Path file = Files.createTempFile(folder, filename + "-", "." + extension);

                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                try (InputStream input = uploadedFile.getInputstream()) {
                    Files.copy(input, file, StandardCopyOption.REPLACE_EXISTING);
                    documentEngin.setChemin(chemin + "/" + file.getFileName());
                    documentEngin.setCreePar(auth.getPrincipal().toString());
                    documentEngin.setDateCreation(new Date());
                    documentEngin.setEngin(enginToaddDocument);
                    documentEnginService.save(documentEngin);
                    l_DocumentEngins = documentEnginService.getListDocumentEngins(this.documentEngin.getEngin().getiDEngin());
                    System.out.println("CHEMIN : " + chemin);
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, Message.STRING_CHARGE_DOCUMENT_DONE, Message.STRING_CHARGE_DOCUMENT_DONE));

                    ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
                    ec.redirect(ec.getRequestContextPath() + "/engin/DocumentEngin.xhtml?faces-redirect=true&enginId=" + this.documentEngin.getEngin().getiDEngin());
                }

            }
        }

    }
    
    public void ficheEngin(Engin e){
        FicheEngin fengin = new FicheEngin();
        String chemin = ConstanteMb.getRepertoire() + "/files/docsEngin";
        Path folder = Paths.get(chemin);
        try {
            Files.createDirectories(folder);
        } catch (IOException ex) {
            Logger.getLogger(DocumentEnginMb.class.getName()).log(Level.SEVERE, null, ex);
        }
        fengin.editeFicheEngin("D:\\", e);
    }

    public void visualiser(String chemin) {
        System.out.println("chemin : " + chemin);
        selectedDoc = chemin.substring(chemin.indexOf("files"));
        // selectedDoc = chemin.substring(chemin.indexOf("resources"), chemin.length());
        // System.out.println("s : " + selectedDoc);
    }

    /**
     * methode qui permet de telecharger fichier
     *
     * @param d
     * @throws IOException
     */
    public void downLoad(DocumentEngin d) throws IOException {

        CiterneServiceBean citerneServiceBean = new CiterneServiceBean();
        if (d != null) {
            if (d.getChemin() != null) {
                citerneServiceBean.telecharger_fichier(d.getChemin());

            }
        }

    }

    /**
     * cette metthode qui permet de supprimer document d'un mensuel
     *
     * @param doc
     */
    public void delete(DocumentEngin de) throws IOException {

        documentEnginService.delete(de);

        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("" + Message.DELETE_DOCUMENT, Message.DELETE_DOCUMENT));

        l_DocumentEngins = documentEnginService.getListDocumentEngins(this.enginToaddDocument.getiDEngin());

    }

    public void onRowEdit(RowEditEvent event) {

        documentEnginService.update((DocumentEngin) event.getObject());

        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(" " + Message.UPDATE_TITRE, ""));
    }

    public void onRowCancel() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, Message.UPDATE_TITRE_CANCEL, ""));
    }

    public String format(String str) {
        Outils outils = new Outils();
        return outils.format(str);
    }

}
