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
import ma.bservices.tgcc.Entity.Historique_Ordinateur;
import ma.bservices.tgcc.Entity.Mensuel;
import ma.bservices.tgcc.Entity.Ordinateur;
import ma.bservices.tgcc.service.Mensuel.HistoriqueService;
import ma.bservices.tgcc.service.Mensuel.MensuelService;
import ma.bservices.tgcc.service.Mensuel.OrdinateurService;
import ma.bservices.tgcc.utilitaire.Outils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

/**
 *
 * @author j.allali
 */
@Component
@ManagedBean(name = "ordinateurMb")
@ViewScoped
public class OrdinateurMb implements Serializable {
//ordinateurService

    @ManagedProperty(value = "#{ordinateurService}")
    private OrdinateurService ordinateurService;

    @ManagedProperty(value = "#{mensuelService}")
    private MensuelService mensuelService;

    @ManagedProperty(value = "#{historiqueService}")
    private HistoriqueService hService;

    private List<Ordinateur> ordinateurs;

    private Date dateDebutOrdi;
    private Mensuel mensuel = new Mensuel();
    private Mensuel mensuelAffect = new Mensuel();
    private Ordinateur ordA = new Ordinateur();

    private Ordinateur searchOrdin = new Ordinateur();

    private List<Historique_Ordinateur> ordisH = new LinkedList<>();

    private Mensuel searchMesn;
    private Mensuel mensuelToSearch;
    private List<Mensuel> mensuels;
    private Date dateRendu;
    private Ordinateur ordASelected = new Ordinateur();

    private Date dateAfec;

    private Date dateFrom, dateTo;

    public Ordinateur getOrdASelected() {
        return ordASelected;
    }

    public void setOrdASelected(Ordinateur ordASelected) {
        this.ordASelected = ordASelected;
    }

    public Date getDateRendu() {
        return dateRendu;
    }

    public void setDateRendu(Date dateRendu) {
        this.dateRendu = dateRendu;
    }

    /**
     * cette variable pour filtre ordinateur Affecter
     */
    private List<Ordinateur> listFilterAffecteToOrdinateur = new ArrayList<>();

    /**
     * variable pour affecter Ordinateur
     *
     * @return
     */
    private Integer numero_serie_ordinateurTo_affect;

    private Ordinateur serachOrdianteur = new Ordinateur();
    private Ordinateur ordinateurChoisi = new Ordinateur();
    private List<Ordinateur> listOrdianByMarque;
    private Date dateDebutOrdinateur = new Date();

    public Ordinateur getOrdinateurChoisi() {
        return ordinateurChoisi;
    }

    public void setOrdinateurChoisi(Ordinateur ordinateurChoisi) {
        this.ordinateurChoisi = ordinateurChoisi;
    }

    public List<Ordinateur> getListOrdianByMarque() {
        return listOrdianByMarque;
    }

    public HistoriqueService gethService() {
        return hService;
    }

    public void sethService(HistoriqueService hService) {
        this.hService = hService;
    }

    public void setListOrdianByMarque(List<Ordinateur> listOrdianByMarque) {
        this.listOrdianByMarque = listOrdianByMarque;
    }

    public Ordinateur getSerachOrdianteur() {
        return serachOrdianteur;
    }

    public void setSerachOrdianteur(Ordinateur serachOrdianteur) {
        this.serachOrdianteur = serachOrdianteur;
    }

    public List<Ordinateur> getListFilterAffecteToOrdinateur() {
        return listFilterAffecteToOrdinateur;
    }

    public void setListFilterAffecteToOrdinateur(List<Ordinateur> listFilterAffecteToOrdinateur) {
        this.listFilterAffecteToOrdinateur = listFilterAffecteToOrdinateur;
    }

    public Integer getNumero_serie_ordinateurTo_affect() {
        return numero_serie_ordinateurTo_affect;
    }

    public List<Historique_Ordinateur> getOrdisH() {
        return ordisH;
    }

    public void setOrdisH(List<Historique_Ordinateur> ordisH) {
        this.ordisH = ordisH;
    }

    public Date getDateDebutOrdi() {
        return dateDebutOrdi;
    }

    public void setDateDebutOrdi(Date dateDebutOrdi) {
        this.dateDebutOrdi = dateDebutOrdi;
    }

    public Date getDateDebutOrdinateur() {
        return dateDebutOrdinateur;
    }

    public void setDateDebutOrdinateur(Date dateDebutOrdinateur) {
        this.dateDebutOrdinateur = dateDebutOrdinateur;
    }

    public void setNumero_serie_ordinateurTo_affect(Integer numero_serie_ordinateurTo_affect) {
        this.numero_serie_ordinateurTo_affect = numero_serie_ordinateurTo_affect;
    }

    public Mensuel getMensuelAffect() {
        return mensuelAffect;
    }

    public void setMensuelAffect(Mensuel mensuelAffect) {
        this.mensuelAffect = mensuelAffect;
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

    public void setMensuels(List<Mensuel> mensuels) {
        this.mensuels = mensuels;
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

    List<Mensuel> searchMensuelList = new ArrayList<Mensuel>();

    public MensuelService getMensuelService() {
        return mensuelService;
    }

    public void setMensuelService(MensuelService mensuelService) {
        this.mensuelService = mensuelService;
    }

    public List<Mensuel> getSearchMensuelList() {
        return searchMensuelList;
    }

    public void setSearchMensuelList(List<Mensuel> searchMensuelList) {
        this.searchMensuelList = searchMensuelList;
    }

    public Mensuel getSearchMesn() {
        return searchMesn;
    }

    public void setSearchMesn(Mensuel searchMesn) {
        this.searchMesn = searchMesn;
    }

    public Ordinateur getSearchOrdin() {
        return searchOrdin;
    }

    public void setSearchOrdin(Ordinateur searchOrdin) {
        this.searchOrdin = searchOrdin;
    }

    public Ordinateur getOrdA() {
        return ordA;
    }

    public void setOrdA(Ordinateur ordA) {
        this.ordA = ordA;
    }

    public Mensuel getMensuel() {
        return mensuel;
    }

    public void setMensuel(Mensuel mensuel) {
        this.mensuel = mensuel;
    }

    public List<Ordinateur> getOrdinateurs() {
        return ordinateurs;
    }

    public void setOrdinateurs(List<Ordinateur> ordinateurs) {
        this.ordinateurs = ordinateurs;
    }

    public OrdinateurService getOrdinateurService() {
        return ordinateurService;
    }

    public void setOrdinateurService(OrdinateurService ordinateurService) {
        this.ordinateurService = ordinateurService;
    }

    public Date getDateAfec() {
        return dateAfec;
    }

    public void setDateAfec(Date dateAfec) {
        this.dateAfec = dateAfec;
    }

    /**
     * Creates a new instance of OrdinateurMb
     */
    public OrdinateurMb() {
    }

    @PostConstruct
    public void init() {
        mensuelToSearch = new Mensuel();
        searchMesn = new Mensuel();
        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        ordinateurService = ctx.getBean(OrdinateurService.class);
        hService = ctx.getBean(HistoriqueService.class);

        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        ma.bservices.tgcc.mb.services.OrdinateurMb ordinateurServMb = (ma.bservices.tgcc.mb.services.OrdinateurMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "ordinateur_ServMb");
        ordinateurServMb.reloadAffecter();
        //  ordinateurs = ordinateurService.findAll();
        this.ordinateurs = ordinateurServMb.getL_ordinateursAffecter();

        dateAfec = new Date();
        ordisH = hService.findAllOrdis();

//        mensuels = mensuelService.findAll();
    }

    /**
     * methode pour redirect vers Object mensuel
     */
    public void redirectMensuel(Mensuel selected) {

        mensuel = selected;

//        System.out.println("entrrreeeeeeeeeeeeeeeee");
//
//        if (listFilterAffecteToOrdinateur != null) {
//            listFilterAffecteToOrdinateur.clear();
//        }
//        listFilterAffecteToOrdinateur = ordinateurService.listOrdinateur_NonAffecter();
    }

    /**
     * Methode pour affecter Ordinateur a Un mensuel
     */
    public void validateAffectOrd() {

        if (ordinateurChoisi.getDateRendu() != null && !Module.checkDate(ordinateurChoisi.getDateRendu(), null, dateDebutOrdinateur)) {
            Module.message(2, "date affectation doit etre supperieure a la derniere date de rendu", "");
            return;
        }
        if (!Module.checkDate(dateDebutOrdinateur, null, new Date())) {
            Module.message(2, "date affectation doit etre inférieure a la date d'aujourd'hui", "");
            return;
        }
        if (mensuel.getId() == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, Message.AFFECT_CARTE_GASOIL_WARNING, Message.AFFECT_CARTE_GASOIL_WARNING));

        } else {

            ordinateurChoisi.setMensuel(mensuel);
            ordinateurChoisi.setDateDebut(dateDebutOrdinateur);
            System.out.println("DATE DEBUT ORDI : " + dateDebutOrdinateur);
            System.out.println("DATE DEBUT ORDI V2 : " + ordinateurChoisi.getDateDebut());

            ordinateurService.update(ordinateurChoisi);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("" + Message.AFFECT_ORDINATEUR, ""));

            ELContext elContext = FacesContext.getCurrentInstance().getELContext();
            ma.bservices.tgcc.mb.services.OrdinateurMb ordinateurServMb = (ma.bservices.tgcc.mb.services.OrdinateurMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "ordinateur_ServMb");
            ordinateurServMb.reloadAffecter();
            ordinateurServMb.reload();

            ordinateurChoisi = new Ordinateur();
            mensuel = new Mensuel();
            dateDebutOrdinateur = new Date();

        }

    }

    public void rechercherMensuel() {
        System.out.println("afficher nom :" + mensuelToSearch.getNom());
        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        mensuelService
                = ctx.getBean(MensuelService.class
                );
        if (mensuelToSearch.getMatricule().compareTo("") == 0 && mensuelToSearch.getNom().compareTo("") == 0 && mensuelToSearch.getPrenom().compareTo("") == 0 && mensuelToSearch.getCin().compareTo("") == 0) {

            ELContext elContext = FacesContext.getCurrentInstance().getELContext();
            ma.bservices.tgcc.mb.services.MensuelMb mensuelServMb = (ma.bservices.tgcc.mb.services.MensuelMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "mensuelServMb");

            this.mensuels = mensuelServMb.l_mensuels;
        } else {
            this.searchMensuelList = mensuelService.search(mensuelToSearch.getMatricule(), mensuelToSearch.getNom(), mensuelToSearch.getPrenom(), "", mensuelToSearch.getCin());
            this.mensuels = searchMensuelList;
        }

    }

    public void setDateD() {
        ordinateurChoisi.setDateDebut(new Date());
    }

    /*historique par date */
    public void searchByDate() {

        ordisH = hService.findByDateRangeOrdi(dateFrom, dateTo);
    }

// reinit 
    public void reinitSearch() {
        ordisH = hService.findAllOrdis();
        dateFrom = null;
        dateTo = null;
    }

    /**
     * parie consultation des ordinateur
     */
    public void consultOrdina(Mensuel selected) {
        ordinateurs = new ArrayList<>();
        List<Ordinateur> ordinateurList = new ArrayList<>();
        mensuelAffect = selected;
        if (ordinateurs != null) {
            ordinateurs.clear();
        }
        ordinateurs = ordinateurService.ConsultOrdinateur(mensuelAffect.getId());
        if (ordinateurs != null) {
            ordinateurList = ordinateurs;
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, Message.MENSUEL_NO_ORDINATEUR, ""));
        }
    }

    /**
     * methode qui permet de desaffecter ordinatuer a un mensuel
     *
     * @param ordinateur
     */
    public void desaffecter_ordinateur_mensuel(Ordinateur ordinateur) {

        this.ordinateurService.desaffecter_ordianteur_mensuel(ordinateur);

        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        ma.bservices.tgcc.mb.services.OrdinateurMb ordiServMb = (ma.bservices.tgcc.mb.services.OrdinateurMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "ordinateur_ServMb");
        ordiServMb.reloadAffecter();
        ordiServMb.reload();

        this.ordinateurs = ordiServMb.getL_ordinateursAffecter();

        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("" + Message.ANNULERAFFECATIONORDINATEUR, Message.ANNULERAFFECATIONORDINATEUR));
    }

    /*
     **Recherche Ordianateur By Marque
     */

    public void rechercherOrdianteur() {

        listOrdianByMarque = new ArrayList<>();

        if (serachOrdianteur.getMarque().equals("") && serachOrdianteur.getNumeroSerieOrd().equals("")) {

            ELContext elContext = FacesContext.getCurrentInstance().getELContext();
            ma.bservices.tgcc.mb.services.OrdinateurMb ordinateurServBean = (ma.bservices.tgcc.mb.services.OrdinateurMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "ordinateur_ServMb");
            ordinateurServBean.reload();
            listOrdianByMarque = ordinateurServBean.getL_ordinateur_NonAffecter();

        } else {
            listOrdianByMarque = ordinateurService.findByMArque(serachOrdianteur.getMarque(), serachOrdianteur.getNumeroSerieOrd());

        }

    }

    public void addDateRendu() throws ParseException {

        
        if (!Module.checkDate(ordASelected.getDateDebut(), null, dateRendu)) {
            Module.message(2, "date affectation doit etre supperieure a la derniere date de rendu", "");
            return;
        }
        
        if (!Module.checkDate(dateRendu, null, new Date())) {
            Module.message(2, "date affectation doit etre inférieure a la date d'aujourd'hui", "");
            return;
        }
        
        
        hService.addRecordOrdinateur(new Historique_Ordinateur(), ordASelected, ordASelected.getMensuel(), ordASelected.getDateDebut(), dateRendu);

        ordASelected.setDateRendu(dateRendu);
        ordinateurService.update(ordASelected);

        this.ordinateurService.desaffecter_ordianteur_mensuel(ordASelected);

        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        ma.bservices.tgcc.mb.services.OrdinateurMb ordiServMb = (ma.bservices.tgcc.mb.services.OrdinateurMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "ordinateur_ServMb");
        ordiServMb.reloadAffecter();
        ordiServMb.reload();

        this.ordinateurs = ordiServMb.getL_ordinateursAffecter();

        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("" + Message.DESAFFECTED_ORDIANTEUR, Message.DESAFFECTED_ORDIANTEUR));

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

    public void afficherOrdinateurRechercher() {
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        ma.bservices.tgcc.mb.services.OrdinateurMb ordinateur_ServMb = (ma.bservices.tgcc.mb.services.OrdinateurMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "ordinateur_ServMb");
        ordinateur_ServMb.reload();
        this.listOrdianByMarque = ordinateur_ServMb.getL_ordinateur_NonAffecter();

    }

    public void afficherOrdinateurAffecter() {
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        ma.bservices.tgcc.mb.services.OrdinateurMb ordinateur_ServMb = (ma.bservices.tgcc.mb.services.OrdinateurMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "ordinateur_ServMb");

        this.ordinateurs = ordinateur_ServMb.getL_ordinateursAffecter();
    }

    /*
     *
     */
    public String format(String str) {
        Outils outils = new Outils();
        return outils.format(str);
    }
}
