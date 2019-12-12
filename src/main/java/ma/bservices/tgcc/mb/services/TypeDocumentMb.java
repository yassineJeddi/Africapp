/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.mb.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import ma.bservices.beans.TypeDocument;
import ma.bservices.tgcc.service.Mensuel.TypeDocumentService;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

/**
 *
 * @author a.wattah
 */
@Component("type_documentServMb_")
@ManagedBean(name = "type_documentServMb_")
@ApplicationScoped
public class TypeDocumentMb implements Serializable {

    @ManagedProperty(value = "#{TypedocumentService}")
    private TypeDocumentService typeDocumentService;

    private List<TypeDocument> l_type_docs = new ArrayList<>();

    private List<TypeDocument> l_Type_documents = new ArrayList<>();

    /**
     * getters and setters
     */
    public TypeDocumentService getTypeDocumentService() {
        return typeDocumentService;
    }

    public void setTypeDocumentService(TypeDocumentService typeDocumentService) {
        this.typeDocumentService = typeDocumentService;
    }

    public List<TypeDocument> getL_type_docs() {
        return l_type_docs;
    }

    public void setL_type_docs(List<TypeDocument> l_type_docs) {
        this.l_type_docs = l_type_docs;
    }

    public List<TypeDocument> getL_Type_documents() {
        return l_Type_documents;
    }

    public void setL_Type_documents(List<TypeDocument> l_Type_documents) {
        this.l_Type_documents = l_Type_documents;
    }

    /**
     * end getters and setters
     */
    @PostConstruct
    public void init() {
        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        typeDocumentService = ctx.getBean(TypeDocumentService.class);
        this.l_type_docs = typeDocumentService.l_docs_Non_Obligatoir();

        this.l_Type_documents = typeDocumentService.findAll();
    }
}
