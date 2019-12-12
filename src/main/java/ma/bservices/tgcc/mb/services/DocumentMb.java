/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.mb.services;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import ma.bservices.beans.Document;
import ma.bservices.tgcc.service.Mensuel.DocumentService;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

/**
 *
 * @author a.wattah
 */
@Component("document_ServMb")
@ManagedBean(name = "document_ServMb")
@ApplicationScoped
public class DocumentMb implements Serializable {

    @ManagedProperty(value = "#{documentService}")
    private DocumentService documentService;

    private List<Document> l_documents;

    /**
     * getters and setters
     */
    public DocumentService getDocumentService() {
        return documentService;
    }

    public void setDocumentService(DocumentService documentService) {
        this.documentService = documentService;
    }

    public List<Document> getL_documents() {
        return l_documents;
    }

    public void setL_documents(List<Document> l_documents) {
        this.l_documents = l_documents;
    }

    /**
     * end getters and setters
     */
    @PostConstruct
    public void init() {
        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        documentService = ctx.getBean(DocumentService.class);

        l_documents = documentService.findAll();

    }

    public void reload() {

        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        documentService = ctx.getBean(DocumentService.class);

        if (l_documents != null) {
            l_documents.clear();

        }

        l_documents = documentService.findAll();
        
              //  FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, Message.UPLOAD_SUCCESS, Message.DOCUMENT_UPLOAD));

    }

}
