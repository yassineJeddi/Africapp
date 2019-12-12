/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.mb.services;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import ma.bservice.tgcc.Constante.Message;
import ma.bservices.beans.Chantier;
import ma.bservices.beans.Document;
import ma.bservices.beans.Fonction;
import ma.bservices.tgcc.Entity.CarteGasoil;
import ma.bservices.tgcc.Entity.Citerne;
import ma.bservices.tgcc.Entity.Engin;
import ma.bservices.tgcc.Entity.Mensuel;
import ma.bservices.tgcc.Entity.Modem3G;
import ma.bservices.tgcc.Entity.Ordinateur;
import ma.bservices.tgcc.Entity.Telephone;
import ma.bservices.tgcc.Entity.Voiture;
import ma.bservices.tgcc.service.Engin.ChantierService;
import ma.bservices.tgcc.service.Engin.CiterneService;
import ma.bservices.tgcc.service.Engin.EnginService;
import ma.bservices.tgcc.service.Mensuel.CarteGasoilService;
import ma.bservices.tgcc.service.Mensuel.DocumentService;
import ma.bservices.tgcc.service.Mensuel.FonctionService;
import ma.bservices.tgcc.service.Mensuel.MensuelService;
import ma.bservices.tgcc.service.Mensuel.Modem3gService;
import ma.bservices.tgcc.service.Mensuel.OrdinateurService;
import ma.bservices.tgcc.service.Mensuel.TelephoneService;
import ma.bservices.tgcc.service.Mensuel.VoitureServices;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

/**
 *
 * @author iraamane
 */
@Component
@ManagedBean(name = "uploadAll_ServMb")
@ApplicationScoped
public class upload_tout_servMb implements Serializable {


    @ManagedProperty(value = "#{citerneService}")
    private CiterneService citerneService;

    @ManagedProperty(value = "#{documentService}")
    private DocumentService documentService;

    @ManagedProperty(value = "#{fonctionService}")
    private FonctionService fonctionService;

    @ManagedProperty(value = "#{chantierService}")
    private ChantierService chantierService;

    @ManagedProperty(value = "#{mensuelService}")
    private MensuelService mensuelService;

    @ManagedProperty(value = "#{voitureService}")
    private VoitureServices voitureService;


    @ManagedProperty(value = "#{telephoneService}")
    private TelephoneService telephoneService;

    @ManagedProperty(value = "#{ordinateurService}")
    private OrdinateurService ordinateurService;

    @ManagedProperty(value = "#{carteGasoilService}")
    private CarteGasoilService cartegsService;

    @ManagedProperty(value = "#{modem3gService}")
    private Modem3gService modem3gService;

    @ManagedProperty(value = "#{enginService}")
    private EnginService enginService;

    List<Document> documents = new LinkedList<>();
    List<CarteGasoil> cartes = new LinkedList<>();
    List<Chantier> chantiers = new LinkedList<>();
    List<Mensuel> mensuels = new LinkedList<>();
    List<Engin> engins = new LinkedList<>();
    List<Voiture> voitures = new LinkedList<>();
    List<Modem3G> modems = new LinkedList<>();
    List<Ordinateur> ordinateurs = new LinkedList<>();
    List<Telephone> telephones = new LinkedList<>();
    List<Fonction> fonctions = new LinkedList<>();
    List<Citerne> citernes = new LinkedList<>();


    public CiterneService getCiterneService() {
        return citerneService;
    }

    public void setCiterneService(CiterneService citerneService) {
        this.citerneService = citerneService;
    }

    public DocumentService getDocumentService() {
        return documentService;
    }

    public void setDocumentService(DocumentService documentService) {
        this.documentService = documentService;
    }

    public FonctionService getFonctionService() {
        return fonctionService;
    }

    public void setFonctionService(FonctionService fonctionService) {
        this.fonctionService = fonctionService;
    }

    public ChantierService getChantierService() {
        return chantierService;
    }

    public void setChantierService(ChantierService chantierService) {
        this.chantierService = chantierService;
    }

    public MensuelService getMensuelService() {
        return mensuelService;
    }

    public void setMensuelService(MensuelService mensuelService) {
        this.mensuelService = mensuelService;
    }

    public VoitureServices getVoitureService() {
        return voitureService;
    }

    public void setVoitureService(VoitureServices voitureService) {
        this.voitureService = voitureService;
    }


    public TelephoneService getTelephoneService() {
        return telephoneService;
    }

    public void setTelephoneService(TelephoneService telephoneService) {
        this.telephoneService = telephoneService;
    }

    public OrdinateurService getOrdinateurService() {
        return ordinateurService;
    }

    public void setOrdinateurService(OrdinateurService ordinateurService) {
        this.ordinateurService = ordinateurService;
    }

    public CarteGasoilService getCartegsService() {
        return cartegsService;
    }

    public void setCartegsService(CarteGasoilService cartegsService) {
        this.cartegsService = cartegsService;
    }

    public Modem3gService getModem3gService() {
        return modem3gService;
    }

    public void setModem3gService(Modem3gService modem3gService) {
        this.modem3gService = modem3gService;
    }

    public EnginService getEnginService() {
        return enginService;
    }

    public void setEnginService(EnginService enginService) {
        this.enginService = enginService;
    }

    public List<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }

    public List<CarteGasoil> getCartes() {
        return cartes;
    }

    public void setCartes(List<CarteGasoil> cartes) {
        this.cartes = cartes;
    }

    public List<Chantier> getChantiers() {
        return chantiers;
    }

    public void setChantiers(List<Chantier> chantiers) {
        this.chantiers = chantiers;
    }

    public List<Mensuel> getMensuels() {
        return mensuels;
    }

    public void setMensuels(List<Mensuel> mensuels) {
        this.mensuels = mensuels;
    }

    public List<Engin> getEngins() {
        return engins;
    }

    public void setEngins(List<Engin> engins) {
        this.engins = engins;
    }

    public List<Voiture> getVoitures() {
        return voitures;
    }

    public void setVoitures(List<Voiture> voitures) {
        this.voitures = voitures;
    }

    public List<Modem3G> getModems() {
        return modems;
    }

    public void setModems(List<Modem3G> modems) {
        this.modems = modems;
    }


    public List<Ordinateur> getOrdinateurs() {
        return ordinateurs;
    }

    public void setOrdinateurs(List<Ordinateur> ordinateurs) {
        this.ordinateurs = ordinateurs;
    }

    public List<Telephone> getTelephones() {
        return telephones;
    }

    public void setTelephones(List<Telephone> telephones) {
        this.telephones = telephones;
    }

    public List<Fonction> getFonctions() {
        return fonctions;
    }

    public void setFonctions(List<Fonction> fonctions) {
        this.fonctions = fonctions;
    }

    public List<Citerne> getCiternes() {
        return citernes;
    }

    public void setCiternes(List<Citerne> citernes) {
        this.citernes = citernes;
    }

    @PostConstruct
    public void init() {
        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());

        chantierService = ctx.getBean(ChantierService.class);
        documentService = ctx.getBean(DocumentService.class);
        enginService = ctx.getBean(EnginService.class);
        mensuelService = ctx.getBean(MensuelService.class);
        citerneService = ctx.getBean(CiterneService.class);
        voitureService = ctx.getBean(VoitureServices.class);
        fonctionService = ctx.getBean(FonctionService.class);
        telephoneService = ctx.getBean(TelephoneService.class);
        modem3gService = ctx.getBean(Modem3gService.class);
        cartegsService = ctx.getBean(CarteGasoilService.class);
        ordinateurService = ctx.getBean(OrdinateurService.class);

    }

    public void reloadAll() {

//        chantiers = chantierService.findAll();
//        documents = documentService.findAll();
//        engins = enginService.findAll();
//        mensuels = mensuelService.findAll();
//        citernes = citerneService.find_all_Citerne();
//        voitures = voitureService.findAll();
//        fonctions = fonctionService.findAll();
//        telephones = telephoneService.findAll();
//        modems = modem3gService.findAll();
//        voituresCh = voitureChantierService.findAllVoiture();
//        voituresSa = voitureSalarieService.findAll();
//        cartes = cartegsService.findAll();
//        ordinateurs = ordinateurService.findAll();

        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        ma.bservices.tgcc.mb.services.MensuelMb mensuelMb = (ma.bservices.tgcc.mb.services.MensuelMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "mensuelServMb");

        ma.bservices.tgcc.mb.services.EnginMb engin_bean = (ma.bservices.tgcc.mb.services.EnginMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "enginServMb");
        ma.bservices.tgcc.mb.services.ChantierMb chantierMbBean = (ma.bservices.tgcc.mb.services.ChantierMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "chantierServMb");
        ma.bservices.tgcc.mb.services.CiterneMb citerneMbBean = (ma.bservices.tgcc.mb.services.CiterneMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "citerne_ServMb");
        //ma.bservices.tgcc.mb.services.Modem3gMb modemMbBean = (ma.bservices.tgcc.mb.services.Modem3gMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "modem3g_ServMb");
        ma.bservices.tgcc.mb.services.OrdinateurMb ordiMbBean = (ma.bservices.tgcc.mb.services.OrdinateurMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "ordinateur_ServMb");
        ma.bservices.tgcc.mb.services.VoitureMb voitureMbBean = (ma.bservices.tgcc.mb.services.VoitureMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "voiture_ServMb");
        ma.bservices.tgcc.mb.services.DocumentMb documentMbBean = (ma.bservices.tgcc.mb.services.DocumentMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "document_ServMb");
    //    ma.bservices.tgcc.mb.services.VoitureSalarieMb voisalMbBean = (ma.bservices.tgcc.mb.services.VoitureSalarieMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "voiture_salarieServMb");
    //    ma.bservices.tgcc.mb.services.VoitureChantierMb voichMbBean = (ma.bservices.tgcc.mb.services.VoitureChantierMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "voiture_chantierServMb");
    //    ma.bservices.tgcc.mb.services.CarteGasoilMb cartegs = (ma.bservices.tgcc.mb.services.CarteGasoilMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "carteGasoil_ServMb");
        ma.bservices.tgcc.mb.services.FonctionMb fonctionMb = (ma.bservices.tgcc.mb.services.FonctionMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "fonctionServMb");

        mensuelMb.reload_Mensuel();
        engin_bean.reload();
        chantierMbBean.reload_Chantier();
        citerneMbBean.reload();
       // modemMbBean.reload();
        ordiMbBean.reload();
        voitureMbBean.reload();
        documentMbBean.reload();
    //    voisalMbBean.reload();
    //    voichMbBean.reload();
    //    cartegs.reloadFindAll();
        fonctionMb.reload_Fonction();
        
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, Message.UPLOAD_SUCCESS, Message.TOUT_UPLOAD));

        
        
       
        
    }

}
