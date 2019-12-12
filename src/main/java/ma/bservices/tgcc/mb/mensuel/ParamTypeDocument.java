/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.mb.mensuel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import ma.bservices.beans.Fonction;
import ma.bservices.beans.TypeDocument;
import ma.bservices.tgcc.service.Mensuel.FonctionService;
import ma.bservices.tgcc.service.Mensuel.TypeDocumentService;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

/**
 *
 * @author a.wattah
 */
@Component
@ManagedBean(name = "paramTypeDocsMb")
@ViewScoped
public class ParamTypeDocument implements Serializable {

    @ManagedProperty(value = "#{TypedocumentService}")
    private TypeDocumentService typeDocumentService;

    @ManagedProperty(value = "#{fonctionService}")
    private FonctionService fonctionService;

    private List<TypeDocument> l_Type_documents = new ArrayList<>();

    private List<Fonction> l_fonctionByDocs;

    private Integer[] t_intg_selected;

    private TypeDocument typeDocToAdd;

    /**
     * getters and setters
     *
     * @return
     */
    public TypeDocumentService getTypeDocumentService() {
        return typeDocumentService;
    }

    public void setTypeDocumentService(TypeDocumentService typeDocumentService) {
        this.typeDocumentService = typeDocumentService;
    }

    public List<TypeDocument> getL_Type_documents() {
        return l_Type_documents;
    }

    public void setL_Type_documents(List<TypeDocument> l_Type_documents) {
        this.l_Type_documents = l_Type_documents;
    }

    public FonctionService getFonctionService() {
        return fonctionService;
    }

    public void setFonctionService(FonctionService fonctionService) {
        this.fonctionService = fonctionService;
    }

    public List<Fonction> getL_fonctionByDocs() {
        return l_fonctionByDocs;
    }

    public void setL_fonctionByDocs(List<Fonction> l_fonctionByDocs) {
        this.l_fonctionByDocs = l_fonctionByDocs;
    }

    public Integer[] getT_intg_selected() {
        return t_intg_selected;
    }

    public void setT_intg_selected(Integer[] t_intg_selected) {
        this.t_intg_selected = t_intg_selected;
    }

    public TypeDocument getTypeDocToAdd() {
        return typeDocToAdd;
    }

    public void setTypeDocToAdd(TypeDocument typeDocToAdd) {
        this.typeDocToAdd = typeDocToAdd;
    }

    /**
     * end getters and setters
     */
    @PostConstruct
    public void init() {
        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        typeDocumentService = ctx.getBean(TypeDocumentService.class);
        fonctionService = ctx.getBean(FonctionService.class);

        ELContext elContext = FacesContext.getCurrentInstance().getELContext();

        ma.bservices.tgcc.mb.services.TypeDocumentMb typeDocs_bean = (ma.bservices.tgcc.mb.services.TypeDocumentMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "type_documentServMb_");

        this.l_Type_documents = typeDocs_bean.getL_Type_documents();

    }

    /**
     * methode qui permet de visualiser detail type document
     *
     * @param selected
     */
    public void visualiserDetail(TypeDocument selected) {

        l_fonctionByDocs = this.fonctionService.l_FonctionsByTypeDoc(selected.getId());

    }

    /**
     * methode qui permet de redirect vers dialog ajouter Fonction
     *
     * @param selected
     */
    public void redirectToAdd(TypeDocument selected) {

        typeDocToAdd = selected;

    }

    /**
     * methode qui permet de ajouter document a fonction
     */
    public void ajouterDocumentObligatoirFonction() {

        this.typeDocumentService.addDocs_fonction(typeDocToAdd, t_intg_selected);

    }

}
