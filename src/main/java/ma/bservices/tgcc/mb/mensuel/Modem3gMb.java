
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.mb.mensuel;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import ma.bservices.beans.Chantier;
import ma.bservices.mb.services.Module;
import ma.bservices.tgcc.Entity.Historique_Modem3G;
import ma.bservices.tgcc.Entity.Mensuel;
import ma.bservices.tgcc.Entity.Modem3G;
import ma.bservices.tgcc.service.Engin.ChantierService;
import ma.bservices.tgcc.service.Mensuel.HistoriqueService;
import ma.bservices.tgcc.service.Mensuel.MensuelService;
import ma.bservices.tgcc.service.Mensuel.Modem3gService;
import ma.bservices.tgcc.utilitaire.Outils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

/**
 *
 * @author a.wattah
 */
@Component
@ManagedBean(name = "modem3gMb")
@ViewScoped
public class Modem3gMb implements Serializable {

    @ManagedProperty(value = "#{modem3gService}")
    private Modem3gService modem3gService;

    @ManagedProperty(value = "#{historiqueService}")
    private HistoriqueService hService;

    @ManagedProperty(value = "#{chantierService}")
    private ChantierService chantierService;

    @ManagedProperty(value = "#{mensuelService}")
    private MensuelService mensuelService;

    private Modem3G modem3G = new Modem3G();
    private List<Modem3G> modem3Gs;
    private Mensuel mensuelToSearch = new Mensuel();
    private Mensuel mensuelToSearchConsul3g = new Mensuel();
    private List<Chantier> chantiers;
    private List<Mensuel> mensuels;
    List<Modem3G> modemList = new ArrayList<>();

    private Mensuel mensuel = new Mensuel();
    private Mensuel mensuelto = new Mensuel();

    private List<Historique_Modem3G> modemsH = new LinkedList<>();

    private Modem3G mg = new Modem3G();

    private String imei_To_search;

    private String numSerieTosearch;
    private String operateur_to_search;

    private String numeroTel_toSearch;

    private List<Modem3G> listFilterAffecte = new ArrayList<>();

    private List<Modem3G> l_3g_toAffectes;
    /**
     * variable affecte Modem3g
     */
    private Integer imeiToAffecter;

    private Date dateAffect;

    private Date dateRendu;
    private Date dateFrom, dateTo; // recherche historique
    private Modem3G modem3GSelected = new Modem3G();
    private Date dateDebutModem;

    public Date getDateRendu() {
        return dateRendu;
    }

    public void setDateRendu(Date dateRendu) {
        this.dateRendu = dateRendu;
    }

    public List<Historique_Modem3G> getModemsH() {
        return modemsH;
    }

    public void setModemsH(List<Historique_Modem3G> modemsH) {
        this.modemsH = modemsH;
    }

    public Date getDateDebutModem() {
        return dateDebutModem;
    }

    public void setDateDebutModem(Date dateDebutModem) {
        this.dateDebutModem = dateDebutModem;
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

    public Modem3G getModem3GSelected() {
        return modem3GSelected;
    }

    public void setModem3GSelected(Modem3G modem3GSelected) {
        this.modem3GSelected = modem3GSelected;
    }

    /*
     * getters and setters 
     */
    public Date getDateAffect() {
        return dateAffect;
    }

    public void setDateAffect(Date dateAffect) {
        this.dateAffect = dateAffect;
    }

    public List<Modem3G> getL_3g_toAffectes() {
        return l_3g_toAffectes;
    }

    public void setL_3g_toAffectes(List<Modem3G> l_3g_toAffectes) {
        this.l_3g_toAffectes = l_3g_toAffectes;
    }

    public String getImei_To_search() {
        return imei_To_search;
    }

    public void setImei_To_search(String imei_To_search) {
        this.imei_To_search = imei_To_search;
    }

    public String getNumSerieTosearch() {
        return numSerieTosearch;
    }

    public void setNumSerieTosearch(String numSerieTosearch) {
        this.numSerieTosearch = numSerieTosearch;
    }

    public List<Modem3G> getListFilterAffecte() {
        return listFilterAffecte;
    }

    public HistoriqueService gethService() {
        return hService;
    }

    public void sethService(HistoriqueService hService) {
        this.hService = hService;
    }

    public void setListFilterAffecte(List<Modem3G> listFilterAffecte) {
        this.listFilterAffecte = listFilterAffecte;
    }

    public Integer getImeiToAffecter() {
        return imeiToAffecter;
    }

    public void setImeiToAffecter(Integer imeiToAffecter) {
        this.imeiToAffecter = imeiToAffecter;
    }

    public Modem3gService getModem3gService() {
        return modem3gService;
    }

    public void setModem3gService(Modem3gService modem3gService) {
        this.modem3gService = modem3gService;
    }

    public ChantierService getChantierService() {
        return chantierService;
    }

    public void setChantierService(ChantierService chantierService) {
        this.chantierService = chantierService;
    }

    public Modem3G getModem3G() {
        return modem3G;
    }

    public void setModem3G(Modem3G modem3G) {
        this.modem3G = modem3G;
    }

    public List<Modem3G> getModem3Gs() {
        return modem3Gs;
    }

    public void setModem3Gs(List<Modem3G> modem3Gs) {
        this.modem3Gs = modem3Gs;
    }

    public List<Chantier> getChantiers() {
        return chantiers;
    }

    public void setChantiers(List<Chantier> chantiers) {
        this.chantiers = chantiers;
    }

    public MensuelService getMensuelService() {
        return mensuelService;
    }

    public void setMensuelService(MensuelService mensuelService) {
        this.mensuelService = mensuelService;
    }

    public Mensuel getMensuelToSearch() {
        return mensuelToSearch;
    }

    public void setMensuelToSearch(Mensuel mensuelToSearch) {
        this.mensuelToSearch = mensuelToSearch;
    }

    public List<Mensuel> getMensuels() {
        return mensuels;
    }

    public void setMensuel(Mensuel mensuel) {
        this.mensuel = mensuel;
    }

    public Modem3G getMg() {
        return mg;
    }

    public void setMg(Modem3G mg) {
        this.mg = mg;
    }

    public Mensuel getMensuelto() {
        return mensuelto;
    }

    public void setMensuelto(Mensuel mensuelto) {
        this.mensuelto = mensuelto;
    }

    public Mensuel getMensuel() {
        return mensuel;
    }

    public void setMensuels(List<Mensuel> mensuels) {
        this.mensuels = mensuels;
    }

    public Mensuel getMensuelToSearchConsul3g() {
        return mensuelToSearchConsul3g;
    }

    public void setMensuelToSearchConsul3g(Mensuel mensuelToSearchConsul3g) {
        this.mensuelToSearchConsul3g = mensuelToSearchConsul3g;
    }

    public List<Modem3G> getModemList() {
        return modemList;
    }

    public void setModemList(List<Modem3G> modemList) {
        this.modemList = modemList;
    }

    public String getOperateur_to_search() {
        return operateur_to_search;
    }

    public void setOperateur_to_search(String operateur_to_search) {
        this.operateur_to_search = operateur_to_search;
    }

    public String getNumeroTel_toSearch() {
        return numeroTel_toSearch;
    }

    public void setNumeroTel_toSearch(String numeroTel_toSearch) {
        this.numeroTel_toSearch = numeroTel_toSearch;
    }

    /*
     * end getters and setters
     */
    // recherche dans l'historique par interval de dates
    public void searchByDate() {
        modemsH = hService.findByDateRange3G(dateFrom, dateTo);
    }

//re    ialiser la recherche
    public void reinitSearch() {
        modemsH = hService.findAllModems();
        dateFrom = null;
        dateTo = null;

    }

    @PostConstruct
    public void init() {
        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        modem3gService = ctx.getBean(Modem3gService.class);
        chantierService = ctx.getBean(ChantierService.class);
        mensuelService = ctx.getBean(MensuelService.class);
        hService = ctx.getBean(HistoriqueService.class);
        mensuel = new Mensuel();
        modemsH = hService.findAllModems();

//        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
//        ma.bservices.tgcc.mb.services.Modem3gMb modem3gbean = (ma.bservices.tgcc.mb.services.Modem3gMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "modem3g_ServMb");
        //   modemList = modem3gService.findAll();
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        ma.bservices.tgcc.mb.services.Modem3gMb modemServMb = (ma.bservices.tgcc.mb.services.Modem3gMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "modem3g_ServMb");
        modemServMb.reload3gAffecter();
        modemServMb.reloadList3gNonAffecter();
        this.modemList = modemServMb.getL_modem3gAffecter();

        dateAffect = new Date();
        //modemList = modem3gService.list3G_Affeceter();
        modem3Gs = modem3gService.findAll();

//        modemList = modem3gService.list3G_Affeceter();
//        mensuels=mensuelService.findAll();
//        mensuelService.DeleteMensuelAndTsElements(1);
    }

    public void rechercher() {
        System.out.println("entreee ");
        if (mensuelToSearch.getMatricule().compareTo("") == 0 && mensuelToSearch.getNom().compareTo("") == 0 && mensuelToSearch.getPrenom().compareTo("") == 0 && mensuelToSearch.getCin().compareTo("") == 0) {

            ELContext elContext = FacesContext.getCurrentInstance().getELContext();
            ma.bservices.tgcc.mb.services.MensuelMb mensuelServMb = (ma.bservices.tgcc.mb.services.MensuelMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "mensuelServMb");

            this.mensuels = mensuelServMb.l_mensuels;
        } else {
            mensuels = mensuelService.search(mensuelToSearch.getMatricule(), mensuelToSearch.getNom(), mensuelToSearch.getPrenom(), "", mensuelToSearch.getCin());
        }
    }

    /**
     * methode pour redirect vers Object mensuel
     */
    public void redirectMensuel(Mensuel selected) {
        mensuel = selected;

//        if (listFilterAffecte != null) {
//            listFilterAffecte.clear();
//        }
//        listFilterAffecte = modem3gService.list3G_Non_Affecter();
    }

    public void save() {

        if (mg.getDateRendu() != null && !Module.checkDate(mg.getDateRendu(), null, dateDebutModem)) {
            Module.message(2, "date affectation doit etre supperieure a la derniere date de rendu", "");
            return;
        }
        if (!Module.checkDate(dateDebutModem, null, new Date())) {
            Module.message(2, "date affectation doit etre inférieure a la date d'aujourd'hui", "");
            return;
        }

        if (mensuel.getId() == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, Message.AFFECT_CARTE_GASOIL_WARNING, Message.AFFECT_CARTE_GASOIL_WARNING));

        } else {

            mg.setMensuel(mensuel);
            mg.setDateDebut(dateDebutModem);
            System.out.println("EXPECTED DATE DEBUT : " + dateDebutModem);
            System.out.println("ACTUAL DATE DEBUT : " + mg.getDateDebut());

            modem3gService.update(mg);

            ELContext elContext = FacesContext.getCurrentInstance().getELContext();
            ma.bservices.tgcc.mb.services.Modem3gMb modem3gbean = (ma.bservices.tgcc.mb.services.Modem3gMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "modem3g_ServMb");
            modem3gbean.reload3gAffecter();
            modem3gbean.reloadList3gNonAffecter();

            FacesContext context = FacesContext.getCurrentInstance();
            String message = Message.AFFECT_3G_SUCCESS + mensuel.getNom();
            context.addMessage(null, new FacesMessage("" + message, ""));

            mg = new Modem3G();
            dateDebutModem = new Date();

            mensuel = new Mensuel();
        }

    }

    public String toFrenchDate(Date d) {

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        return sdf.format(d);

    }

    public void consultation(Mensuel selected) {

        modem3Gs = new ArrayList<Modem3G>();

        modemList = new ArrayList<>();
        mensuel = selected;

        if (mensuel != null) {
            modem3Gs = this.modem3gService.Consult3g(mensuel.getId());

//            System.out.println("entre :" + modem3Gs.size());
        }
        if (modem3Gs != null) {
            if (!modem3Gs.isEmpty()) {
                modemList = modem3Gs;

            }
        } else {

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, Message.MENSUEL_NO_3G, ""));

        }

    }

    /**
     * methode qui permet de desaffecter a un salarie
     *
     * @param modem3G
     */
    public void desaffecter_Modem3g(Modem3G modem3G) {

        this.modem3gService.desaffecter_3g_salarie(modem3G);

        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        ma.bservices.tgcc.mb.services.Modem3gMb modemServMb = (ma.bservices.tgcc.mb.services.Modem3gMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "modem3g_ServMb");
        modemServMb.reload3gAffecter();
        modemServMb.reloadList3gNonAffecter();

        this.modemList = modemServMb.getL_modem3gAffecter();

        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("" + Message.ANNULERAFFECTATION_3G, Message.ANNULERAFFECTATION_3G));

    }

    public void addDateRendu() throws ParseException {

        if (!Module.checkDate(modem3GSelected.getDateDebut(), null, dateRendu)) {
            Module.message(2, "date affectation doit etre supperieure a la derniere date de rendu", "");
            return;
        }
        
        if (!Module.checkDate(dateRendu, null, new Date())) {
            Module.message(2, "date affectation doit etre inférieure a la date d'aujourd'hui", "");
            return;
        }

        hService.addRecordModem(new Historique_Modem3G(), modem3GSelected, modem3GSelected.getMensuel(), modem3GSelected.getDateDebut(), dateRendu);

        modem3GSelected.setDateRendu(dateRendu);
        modem3gService.update(modem3GSelected);
        System.out.println("EXPECTED DATE RENDU : " + dateRendu);
        System.out.println("ACTUAL DATE RENDU : " + modem3GSelected.getDateRendu());
        this.modem3gService.desaffecter_3g_salarie(modem3GSelected);

        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        ma.bservices.tgcc.mb.services.Modem3gMb modemServMb = (ma.bservices.tgcc.mb.services.Modem3gMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "modem3g_ServMb");
        modemServMb.reload3gAffecter();
        modemServMb.reloadList3gNonAffecter();
        this.modemList = modemServMb.getL_modem3gAffecter();

        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(" " + Message.DESAFFECTED_3G, Message.DESAFFECTED_3G));

        dateRendu = null;
    }

    public void recherch_3g() {

        l_3g_toAffectes = new ArrayList<>();

        if (this.imei_To_search.equals("") && this.numSerieTosearch.equals("") && this.operateur_to_search.equals("") && this.numeroTel_toSearch.equals("")) {

            ELContext elContext = FacesContext.getCurrentInstance().getELContext();
            ma.bservices.tgcc.mb.services.Modem3gMb modem3gSerBean = (ma.bservices.tgcc.mb.services.Modem3gMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "modem3g_ServMb");

            modem3gSerBean.reload();

            l_3g_toAffectes = modem3gSerBean.getL_modem3g_Non_Affecter();

        } else {

            l_3g_toAffectes = this.modem3gService.getListe_3gByImeiandNumero(imei_To_search, numSerieTosearch, operateur_to_search, numeroTel_toSearch);

        }
    }

    public void redirect_to_affecte(Modem3G selected) {
        System.out.println("SETTING MODEM 3G");
        mg = selected;
        dateDebutModem = new Date();
        System.out.println("MODEM 3G SET DATE DEBUT : " + mg.getDateDebut());
        System.out.println("WANTED DATE DEBUT : " + dateDebutModem);

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

    public void afficherModem3gAffecte() {

        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        ma.bservices.tgcc.mb.services.Modem3gMb modemServMb = (ma.bservices.tgcc.mb.services.Modem3gMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "modem3g_ServMb");
        this.modemList = modemServMb.getL_modem3gAffecter();
    }

    public void afficher3gRechercher() {
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        ma.bservices.tgcc.mb.services.Modem3gMb modem3g_ServMb = (ma.bservices.tgcc.mb.services.Modem3gMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "modem3g_ServMb");
        this.l_3g_toAffectes = modem3g_ServMb.getL_modem3g_Non_Affecter();
    }

    /*
     *
     */
    public String format(String str) {
        Outils outils = new Outils();
        return outils.format(str);
    }
}
