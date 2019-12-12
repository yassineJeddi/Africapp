/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.mb.mensuel;

import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import ma.bservice.tgcc.Constante.Message;
import ma.bservices.mb.services.Module;
import ma.bservices.tgcc.Entity.Historique_Telephone;
import ma.bservices.tgcc.Entity.Mensuel;
import ma.bservices.tgcc.Entity.Telephone;
import ma.bservices.tgcc.service.Mensuel.HistoriqueService;
import ma.bservices.tgcc.service.Mensuel.MensuelService;
import ma.bservices.tgcc.service.Mensuel.TelephoneService;
import ma.bservices.tgcc.utilitaire.Outils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

/**
 *
 * @author a.wattah
 */
@Component
@ManagedBean(name = "telephoneMb")
@ViewScoped
public class TelephoneMb implements Serializable {

    @ManagedProperty(value = "#{telephoneService}")
    private TelephoneService elementService;

    @ManagedProperty(value = "#{mensuelService}")
    private MensuelService mensuelService;
    
      @ManagedProperty(value = "#{historiqueService}")
    private HistoriqueService hService;

      Date dateFrom, dateTo;
    private List<Telephone> telephones;
    private Telephone telephoneSelected = new Telephone();
    private List<Telephone> telephonesList = new ArrayList<>();
    private List<Historique_Telephone> telephonesH = new LinkedList<>();
    
    
    private Date dateDebutTelephone = new Date();

    private Telephone telephone = new Telephone();
    private Telephone telephoneToAdd = new Telephone();

    private Mensuel mensuel = new Mensuel();

    private Mensuel telephoneSearch = new Mensuel();
    private Mensuel telephoneObject = new Mensuel();
    private Mensuel telephoneToSearch = new Mensuel();

    private Date dateOfToday;
    
    private Date dateAffectation;

    private Telephone telephoneValider;

    private List<Mensuel> searchMensuelList = new ArrayList<Mensuel>();
    private List<Mensuel> mensuels = new ArrayList<Mensuel>();

    private Telephone telephone_to_search = new Telephone();

    private List<Telephone> l_tel_ByNum_Marque;

    /**
     * cette liste pour recupere listNonAffecte a mensuel
     */
    private List<Telephone> listFilterAffecteTo_Telephone = new ArrayList<>();

    private Telephone telephone_to_affect;
    private Date dateRendu;
    private Telephone teleSelected = new Telephone();

    /**
     * variable pour affecter Telephone to Mensuel
     *
     * @return
     */
    private Integer telephoneTo_Affect_Mensuel;

    /*
     * getters and setters 
     */
    public Telephone getTelephoneSelected() {
        return telephoneSelected;
    }

    public List<Historique_Telephone> getTelephonesH() {
        return telephonesH;
    }

    public void setTelephonesH(List<Historique_Telephone> telephonesH) {
        this.telephonesH = telephonesH;
    }
    
    public void setTelephoneSelected(Telephone telephoneSelected) {
        this.telephoneSelected = telephoneSelected;
    }

    public Date getDateRendu() {
        return dateRendu;
    }

    public Date getDateDebutTelephone() {
        return dateDebutTelephone;
    }

    public void setDateDebutTelephone(Date dateDebutTelephone) {
        this.dateDebutTelephone = dateDebutTelephone;
    }
    
    
    public void setDateRendu(Date dateRendu) {
        this.dateRendu = dateRendu;
    }

    public Telephone getTeleSelected() {
        return teleSelected;
    }

    public void setTeleSelected(Telephone teleSelected) {
        this.teleSelected = teleSelected;
    }

    public Telephone getTelephone_to_affect() {
        return telephone_to_affect;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }
    
    

    public void setTelephone_to_affect(Telephone telephone_to_affect) {
        this.telephone_to_affect = telephone_to_affect;
    }

    public List<Telephone> getL_tel_ByNum_Marque() {
        return l_tel_ByNum_Marque;
    }

    public Date getDateOfToday() {
        return dateOfToday;
    }

    public HistoriqueService gethService() {
        return hService;
    }

    public void sethService(HistoriqueService hService) {
        this.hService = hService;
    }    
    

    public void setDateOfToday(Date dateOfToday) {
        this.dateOfToday = dateOfToday;
    }

    public void setL_tel_ByNum_Marque(List<Telephone> l_tel_ByNum_Marque) {
        this.l_tel_ByNum_Marque = l_tel_ByNum_Marque;
    }

    public Telephone getTelephone_to_search() {
        return telephone_to_search;
    }

    public void setTelephone_to_search(Telephone telephone_to_search) {
        this.telephone_to_search = telephone_to_search;
    }

    public List<Telephone> getListFilterAffecteTo_Telephone() {
        return listFilterAffecteTo_Telephone;
    }

    public void setListFilterAffecteTo_Telephone(List<Telephone> listFilterAffecteTo_Telephone) {
        this.listFilterAffecteTo_Telephone = listFilterAffecteTo_Telephone;
    }

    public Integer getTelephoneTo_Affect_Mensuel() {
        return telephoneTo_Affect_Mensuel;
    }

    public void setTelephoneTo_Affect_Mensuel(Integer telephoneTo_Affect_Mensuel) {
        this.telephoneTo_Affect_Mensuel = telephoneTo_Affect_Mensuel;
    }

    public TelephoneService getElementService() {
        return elementService;
    }

    public void setElementService(TelephoneService elementService) {
        this.elementService = elementService;
    }

    public List<Telephone> getTelephones() {
        return telephones;
    }

    public void setTelephones(List<Telephone> telephones) {
        this.telephones = telephones;
    }

    public Telephone getTelephone() {
        return telephone;
    }

    public void setTelephone(Telephone telephone) {
        this.telephone = telephone;
    }

    public Mensuel getMensuel() {
        return mensuel;
    }

    public void setMensuel(Mensuel mensuel) {
        this.mensuel = mensuel;
    }

    public Telephone getTelephoneValider() {
        return telephoneValider;
    }

    public void setTelephoneValider(Telephone telephoneValider) {
        this.telephoneValider = telephoneValider;
    }

    public Mensuel getTelephoneSearch() {
        return telephoneSearch;
    }

    public void setTelephoneSearch(Mensuel telephoneSearch) {
        this.telephoneSearch = telephoneSearch;
    }

    public List<Mensuel> getSearchMensuelList() {
        return searchMensuelList;
    }

    public void setSearchMensuelList(List<Mensuel> searchMensuelList) {
        this.searchMensuelList = searchMensuelList;
    }

    public List<Mensuel> getMensuels() {
        return mensuels;
    }

    public void setMensuels(List<Mensuel> mensuels) {
        this.mensuels = mensuels;
    }

    public Mensuel getTelephoneToSearch() {
        return telephoneToSearch;
    }

    public void setTelephoneToSearch(Mensuel telephoneToSearch) {
        this.telephoneToSearch = telephoneToSearch;
    }

    public MensuelService getMensuelService() {
        return mensuelService;
    }

    public void setMensuelService(MensuelService mensuelService) {
        this.mensuelService = mensuelService;
    }

    public Mensuel getTelephoneObject() {
        return telephoneObject;
    }

    public void setTelephoneObject(Mensuel telephoneObject) {
        this.telephoneObject = telephoneObject;
    }

    public Telephone getTelephoneToAdd() {
        return telephoneToAdd;
    }

    public void setTelephoneToAdd(Telephone telephoneToAdd) {
        this.telephoneToAdd = telephoneToAdd;
    }

    public List<Telephone> getTelephonesList() {
        return telephonesList;
    }

    public void setTelephonesList(List<Telephone> telephonesList) {
        this.telephonesList = telephonesList;
    }

    public Date getDateAffectation() {
        return dateAffectation;
    }

    public void setDateAffectation(Date dateAffectation) {
        this.dateAffectation = dateAffectation;
    }
    
    


    /*
     * and setters and getters
     */
    @PostConstruct
    public void init() {
        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        elementService = ctx.getBean(TelephoneService.class);
        
       hService = ctx.getBean(HistoriqueService.class);
       
       telephonesH = hService.findAllTels();

        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        ma.bservices.tgcc.mb.services.TelephoneMb telephoneServMb = (ma.bservices.tgcc.mb.services.TelephoneMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "telephone_ServMb");

//        telephones = elementService.findAll();
//         ELContext elContext = FacesContext.getCurrentInstance().getELContext();
//        ma.bservices.tgcc.mb.services.TelephoneMb telephone_ServMb = (ma.bservices.tgcc.mb.services.TelephoneMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "telephone_ServMb");
      
        telephoneServMb.reloadTelephoneAffecter();
        this.telephones = telephoneServMb.getL_telephones();
        dateOfToday = new Date();
        
        dateAffectation = new Date();

//        telephones = elementService.listTelephophone_Affecter();
        telephone_to_affect = new Telephone();

    }

    public void redirect_to_affecte(Telephone selected) {

        telephone_to_affect = selected;
      

    }
    
    
    
    /*rechercher dans l'historique des telephones par interval de dates*/
    
    public void searchByDate(){
    telephonesH = hService.findByDateRange(dateFrom, dateTo);
    }
    
    
    public void reinitSearch(){
    telephonesH = hService.findAllTels();
    dateFrom = null;
    dateTo = null;
    }

    /**
     * cette methode pour recupere liste Filtre Non Affecte a un Mensuel
     */
    public void redirectMensuel(Mensuel selected) {

        mensuel = selected;

    }

    /*
     * cette methode qui fait affectation des telephones 
     */
    public void validateAffect() {

        
         if (telephone_to_affect.getDateRendu() != null && !Module.checkDate(telephone_to_affect.getDateRendu(), null, dateDebutTelephone)) {
            Module.message(2, "date affectation doit etre supperieure a la derniere date de rendu", "");
            return;
        }
        if (!Module.checkDate(dateDebutTelephone, null, new Date())) {
            Module.message(2, "date affectation doit etre inférieure a la date d'aujourd'hui", "");
            return;
        }
        
        if (mensuel.getId() == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, Message.AFFECT_CARTE_GASOIL_WARNING, Message.AFFECT_CARTE_GASOIL_WARNING));

        } else {

            telephone_to_affect.setMensuel(mensuel);
            telephone_to_affect.setDateDebut(dateDebutTelephone);
            

            elementService.update(telephone_to_affect);

            ELContext elContext = FacesContext.getCurrentInstance().getELContext();
            ma.bservices.tgcc.mb.services.TelephoneMb telephoneServMb = (ma.bservices.tgcc.mb.services.TelephoneMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "telephone_ServMb");
            telephoneServMb.reloadTelephoneAffecter();
            telephoneServMb.reload();

            FacesContext context = FacesContext.getCurrentInstance();
            String message = Message.TELEPHONE_AFFECTED + mensuel.getNom();
            context.addMessage(null, new FacesMessage("" + message, ""));

            telephone_to_affect = new Telephone();
            mensuel = new Mensuel();
            dateDebutTelephone = new Date();

        }

    }

    public TelephoneMb() {
    }

    /*
     * methode qui fai la recherche et afficher dans un table 
     */
    public void rechercher() {
        System.out.println("nom  " + telephoneToSearch.getNom());
        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        mensuelService
                = ctx.getBean(MensuelService.class
                );
        if (telephoneToSearch.getMatricule().compareTo("") == 0 && telephoneToSearch.getNom().compareTo("") == 0 && telephoneToSearch.getPrenom().compareTo("") == 0 && telephoneToSearch.getCin().compareTo("") == 0) {

            ELContext elContext = FacesContext.getCurrentInstance().getELContext();
            ma.bservices.tgcc.mb.services.MensuelMb mensuelServMb = (ma.bservices.tgcc.mb.services.MensuelMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "mensuelServMb");

            this.mensuels = mensuelServMb.l_mensuels;
        } else {

            this.searchMensuelList = mensuelService.search(telephoneToSearch.getMatricule(), telephoneToSearch.getNom(), telephoneToSearch.getPrenom(), "", telephoneToSearch.getCin());
            this.mensuels = searchMensuelList;
        }

    }

    public void consultation(Mensuel selected) {
        telephoneSearch = selected;
        telephones = new ArrayList<Telephone>();

        telephones = this.elementService.ConsultOrdinateur(telephoneSearch.getId());
        if (telephones != null) {
            telephonesList = telephones;
        } else {
            telephonesList.clear();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, Message.MENSUEL_NO_TELEPHONE, ""));
        }

    }

    /**
     * methode qui permet de desaffecter telephone a un mensuel
     *
     * @param telephone
     */
    public void desaffecter_telephone_mensuel(Telephone telephone) {

        this.elementService.desaffecter_telephone_mensuel(telephone);

        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        ma.bservices.tgcc.mb.services.TelephoneMb telephone_ServMb = (ma.bservices.tgcc.mb.services.TelephoneMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "telephone_ServMb");
        telephone_ServMb.reloadTelephoneAffecter();
        telephone_ServMb.reload();
        this.telephones = telephone_ServMb.getL_telephones();

        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("" + Message.DESAFFECTED_TELEPHONE, ""));

    }

    /**
     * methode qui permet de rechercher telephone non affecter
     */
    public void rechercher_telephone_NonAffecter() {

        l_tel_ByNum_Marque = new ArrayList<>();

        if (this.telephone_to_search.getNumSerieTel().equals("") && this.telephone_to_search.getMarque().equals("") && this.telephone_to_search.getModele().equals("")) {

            ELContext elContext = FacesContext.getCurrentInstance().getELContext();
            ma.bservices.tgcc.mb.services.TelephoneMb telephoneServBean = (ma.bservices.tgcc.mb.services.TelephoneMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "telephone_ServMb");
            telephoneServBean.reload();
            l_tel_ByNum_Marque = telephoneServBean.getL_telephoneNon_Affecter();

        } else {
            l_tel_ByNum_Marque = this.elementService.getList_telephone_NonAffecter(this.telephone_to_search.getNumSerieTel(), this.telephone_to_search.getMarque(), this.telephone_to_search.getModele());

        }

    }

    public void addDateRendu() throws ParseException {
        
        
           if (!Module.checkDate(teleSelected.getDateDebut(), null, dateRendu)) {
            Module.message(2, "date affectation doit etre supperieure a la derniere date de rendu", "");
            return;
        }
        
        if (!Module.checkDate(dateRendu, null, new Date())) {
            Module.message(2, "date affectation doit etre inférieure a la date d'aujourd'hui", "");
            return;
        }
        
        hService.addRecordTelephone(new Historique_Telephone(), teleSelected, teleSelected.getMensuel(), teleSelected.getDateDebut(), dateRendu);
        

        teleSelected.setDateRendu(dateRendu);
        elementService.update(teleSelected);

        this.elementService.desaffecter_telephone_mensuel(teleSelected);

        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        ma.bservices.tgcc.mb.services.TelephoneMb telephone_ServMb = (ma.bservices.tgcc.mb.services.TelephoneMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "telephone_ServMb");
        telephone_ServMb.reloadTelephoneAffecter();
        telephone_ServMb.reload();
        this.telephones = telephone_ServMb.getL_telephones();

        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("" + Message.DESAFFECTE_TELEPHONE, Message.DESAFFECTE_TELEPHONE));

        dateRendu = null;

    }

    public String convertDateFormat(Date date) {

        Outils outils = new Outils();
        return outils.convertDate_To_string(date);
    }

    public void afficherMensuelRechercher() {
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        ma.bservices.tgcc.mb.services.MensuelMb mensuelServMb = (ma.bservices.tgcc.mb.services.MensuelMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "mensuelServMb");
        this.mensuels = mensuelServMb.getL_mensuels();
    }

    /**
     *
     */
    public void afficherTelephoneRechercher() {
//        
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        ma.bservices.tgcc.mb.services.TelephoneMb telephone_ServMb = (ma.bservices.tgcc.mb.services.TelephoneMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "telephone_ServMb");
        telephone_ServMb.reload();
        this.l_tel_ByNum_Marque = telephone_ServMb.getL_telephoneNon_Affecter();
    }

    /**
     *
     */
    public void afficherTelephoneaffecter() {
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        ma.bservices.tgcc.mb.services.TelephoneMb telephone_ServMb = (ma.bservices.tgcc.mb.services.TelephoneMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "telephone_ServMb");
        this.telephones = telephone_ServMb.getL_telephones();
    }
    
    
    
    /*
     *
     */
    public String format(String str) {
        Outils outils = new Outils();
        return outils.format(str);
    }
}
