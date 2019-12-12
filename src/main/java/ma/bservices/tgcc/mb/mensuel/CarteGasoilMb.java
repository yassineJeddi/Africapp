/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.mb.mensuel;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import ma.bservice.tgcc.Constante.Message;
import ma.bservices.beans.Chantier;
import ma.bservices.beans.Document;
import ma.bservices.beans.Salarie;
import ma.bservices.services.ChantierService;
import ma.bservices.tgcc.Entity.CarteGasoil;
import ma.bservices.tgcc.Entity.HistoriqueCarteGazoile;
import ma.bservices.tgcc.Entity.Voiture;
import ma.bservices.tgcc.service.Mensuel.CarteGasoilService;
import ma.bservices.tgcc.service.Mensuel.DocumentService;
import ma.bservices.tgcc.service.Mensuel.MensuelService;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

@Component
@ManagedBean(name = "cartesGasoilMb")
@ViewScoped
public class CarteGasoilMb implements Serializable {

    private static final Logger looger = Logger.getLogger(CarteGasoilMb.class.getName());

    @ManagedProperty(value = "#{carteGasoilService}")
    private CarteGasoilService carteGasoilService;

    @ManagedProperty(value = "#{documentService}")
    private DocumentService documentService;

    @ManagedProperty(value = "#{mensuelService}")
    private MensuelService mensuelService;

    @ManagedProperty(value = "#{chantierServiceEvol}")
    private ChantierService chantierService;

    /**
     * ****************************************
     *********** Déclaration des listes *******
     *****************************************
     */
    private List<Salarie> salaries = new ArrayList<Salarie>();
    private List<Chantier> chantiers = new ArrayList<Chantier>();
    private List<CarteGasoil> carteGazoiles = new ArrayList<CarteGasoil>();
    private List<HistoriqueCarteGazoile> historiqueCarteGazoiles = new ArrayList<HistoriqueCarteGazoile>();
    private List<Document> listDoc = new LinkedList();
    /**
     * ****************************************
     *********** Déclaration des variables ****
     *****************************************
     */
    Salarie salarie = new Salarie();
    Chantier chantier = new Chantier();
    CarteGasoil carteGazoile = new CarteGasoil();
    CarteGasoil carteGazoileToAdd = new CarteGasoil();
    CarteGasoil carteGazoileToEdit = new CarteGasoil();
    CarteGasoil carteGazoileToShow = new CarteGasoil();
    HistoriqueCarteGazoile historiqueCarteGazoile = new HistoriqueCarteGazoile();
    String sufix, prefix;
    Date dateAfect = new Date();
    Boolean activeCarteCH = Boolean.FALSE, activeCarteSL = Boolean.FALSE, activeCarte = Boolean.FALSE, activeCarteHCH = Boolean.FALSE, activeCarteHSL = Boolean.FALSE;

    /**
     * ****************************************
     *********** Geters & Seters **************
     *****************************************
     */
    public List<Document> getListDoc() {
        return listDoc;
    }

    public void setListDoc(List<Document> listDoc) {
        this.listDoc = listDoc;
    }

    public CarteGasoil getCarteGazoileToShow() {
        return carteGazoileToShow;
    }

    public void setCarteGazoileToShow(CarteGasoil carteGazoileToShow) {
        this.carteGazoileToShow = carteGazoileToShow;
    }

    public List<HistoriqueCarteGazoile> getHistoriqueCarteGazoiles() {
        return historiqueCarteGazoiles;
    }

    public void setHistoriqueCarteGazoiles(List<HistoriqueCarteGazoile> historiqueCarteGazoiles) {
        this.historiqueCarteGazoiles = historiqueCarteGazoiles;
    }

    public Boolean getActiveCarteCH() {
        return activeCarteCH;
    }

    public void setActiveCarteCH(Boolean activeCarteCH) {
        this.activeCarteCH = activeCarteCH;
    }

    public Boolean getActiveCarteSL() {
        return activeCarteSL;
    }

    public void setActiveCarteSL(Boolean activeCarteSL) {
        this.activeCarteSL = activeCarteSL;
    }

    public Boolean getActiveCarte() {
        return activeCarte;
    }

    public void setActiveCarte(Boolean activeCarte) {
        this.activeCarte = activeCarte;
    }

    public Boolean getActiveCarteHCH() {
        return activeCarteHCH;
    }

    public void setActiveCarteHCH(Boolean activeCarteHCH) {
        this.activeCarteHCH = activeCarteHCH;
    }

    public Boolean getActiveCarteHSL() {
        return activeCarteHSL;
    }

    public HistoriqueCarteGazoile getHistoriqueCarteGazoile() {
        return historiqueCarteGazoile;
    }

    public void setHistoriqueCarteGazoile(HistoriqueCarteGazoile historiqueCarteGazoile) {
        this.historiqueCarteGazoile = historiqueCarteGazoile;
    }

    public void setActiveCarteHSL(Boolean activeCarteHSL) {
        this.activeCarteHSL = activeCarteHSL;
    }

    public void setDateAfect(Date dateAfect) {
        this.dateAfect = dateAfect;
    }

    public Date getDateAfect() {
        return dateAfect;
    }

    public String getPrefix() {
        return prefix;
    }

    public DocumentService getDocumentService() {
        return documentService;
    }

    public MensuelService getMensuelService() {
        return mensuelService;
    }

    public ma.bservices.services.ChantierService getChantierService() {
        return chantierService;
    }

    public List<Salarie> getSalaries() {
        return salaries;
    }

    public List<Chantier> getChantiers() {
        return chantiers;
    }

    public List<CarteGasoil> getCarteGazoiles() {
        return carteGazoiles;
    }

    public Salarie getSalarie() {
        return salarie;
    }

    public Chantier getChantier() {
        return chantier;
    }

    public CarteGasoil getCarteGazoile() {
        return carteGazoile;
    }

    public CarteGasoil getCarteGazoileToAdd() {
        return carteGazoileToAdd;
    }

    public CarteGasoil getCarteGazoileToEdit() {
        return carteGazoileToEdit;
    }

    public String getSufix() {
        return sufix;
    }

    public void setDocumentService(DocumentService documentService) {
        this.documentService = documentService;
    }

    public void setMensuelService(MensuelService mensuelService) {
        this.mensuelService = mensuelService;
    }

    public void setChantierService(ma.bservices.services.ChantierService chantierService) {
        this.chantierService = chantierService;
    }

    public void setSalaries(List<Salarie> salaries) {
        this.salaries = salaries;
    }

    public void setChantiers(List<Chantier> chantiers) {
        this.chantiers = chantiers;
    }

    public void setCarteGazoiles(List<CarteGasoil> carteGazoiles) {
        this.carteGazoiles = carteGazoiles;
    }

    public void setSalarie(Salarie salarie) {
        this.salarie = salarie;
    }

    public void setChantier(Chantier chantier) {
        this.chantier = chantier;
    }

    public void setCarteGazoile(CarteGasoil carteGazoile) {
        this.carteGazoile = carteGazoile;
    }

    public void setCarteGazoileToAdd(CarteGasoil carteGazoileToAdd) {
        this.carteGazoileToAdd = carteGazoileToAdd;
    }

    public void setCarteGazoileToEdit(CarteGasoil carteGazoileToEdit) {
        this.carteGazoileToEdit = carteGazoileToEdit;
    }

    public void setSufix(String sufix) {
        this.sufix = sufix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public CarteGasoilService getCarteGasoilService() {
        return carteGasoilService;
    }

    public void setCarteGasoilService(CarteGasoilService carteGasoilService) {
        this.carteGasoilService = carteGasoilService;
    }

    /**
     * ****************************************
     *********** INIT *************************
     *****************************************
     */
    @PostConstruct
    public void init() {

        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        carteGasoilService = ctx.getBean(CarteGasoilService.class);

    }

    public void chargerListGazoil() {
        carteGazoiles = carteGasoilService.findAll();
    }

    public void chargerListSalarie() {
        salaries = carteGasoilService.findAllSalaries();
    }

    public void listCarteGasoilNonAffecte() {
        System.out.println("=============> listCarteGasoilNonAffecte");
        carteGazoiles = carteGasoilService.listCarteGasoilNonAffecte();
        System.out.println("=============> listCarteGasoilNonAffecte : " + carteGazoiles.size());
        salaries = carteGasoilService.findAllSalaries();
        System.out.println("=============> findAllSalaries " + salaries.size());

    }

    public void preparAddCarteGazoil() {
        try {
            CarteGasoil cg = carteGasoilService.lastCarteGasoil();
            sufix = cg.getNumcartegasoil().substring(0, 13);

        } catch (Exception e) {
        }
        System.out.println("==========> sufix : " + sufix);
        prefix = "";
        carteGazoile = new CarteGasoil();
    }

    public void preparEditCarteGazoil() {
        sufix = carteGazoile.getNumcartegasoil().substring(0, 13);
        prefix = carteGazoile.getNumcartegasoil().substring(13, 18);;
    }

    public void addCarteGazoil() {
        System.out.println("==========> addCarteGazoil");
        String numCarte = sufix.toString() + prefix.toString();
        try {
            carteGazoile.setNumcartegasoil(numCarte);
        } catch (Exception e) {
            looger.warning("Erreur de numero de la carte gazoile au moment d'ajout car " + e.getMessage());
        }
        carteGasoilService.save(carteGazoile);
        carteGazoile = new CarteGasoil();
        chargerListGazoil();
    }

    public void editCarteGazoil() {
        String numCarte = sufix.toString() + prefix.toString();
        try {
            carteGazoile.setNumcartegasoil(numCarte);
        } catch (Exception e) {
            looger.warning("Erreur de numero de la carte gazoile au moment de modification car " + e.getMessage());
        }
        carteGasoilService.update(carteGazoile);
        chargerListGazoil();
    }

    public void remouveCarteGazoil(CarteGasoil c) {

        carteGasoilService.delete(c);
        try {
            CarteGasoil cg = carteGasoilService.lastCarteGasoil();
            sufix = cg.getNumcartegasoil().substring(0, 13);

        } catch (Exception e) {
        }
        prefix = "";
        carteGazoile = new CarteGasoil();
        chargerListGazoil();
    }

    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////Gestion d'affectation //////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    public void affectCarteSalarier() {
        carteGazoile.setSalarie(salarie);
        carteGazoile.setAffect(Boolean.TRUE);
        carteGazoile.setDateafectation(dateAfect);
        carteGasoilService.update(carteGazoile);

        try {
            historiqueCarteGazoile = new HistoriqueCarteGazoile();
            historiqueCarteGazoile.setCarteGasoil(carteGazoile);
            historiqueCarteGazoile.setDateAfect(dateAfect);
            historiqueCarteGazoile.setSalarie(salarie);
            carteGasoilService.ajoutHistorique(historiqueCarteGazoile);
        } catch (Exception e) {
            looger.warning("Erreur d'enregistrement historique carte car : " + e.getMessage());
        }
        salarie = new Salarie();
        carteGazoile = new CarteGasoil();
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Affectation effectué avec succées", ""));

    }

    public void desAffectCarte(CarteGasoil c) {

        c.setSalarie(null);
        c.setChantier(null);
        c.setAffect(Boolean.FALSE);
        c.setDateafectation(null);
        carteGasoilService.update(c);
        try {
            Date d = new Date();
            historiqueCarteGazoile = new HistoriqueCarteGazoile();
            historiqueCarteGazoile.setCarteGasoil(c);
            historiqueCarteGazoile.setDateAfect(dateAfect);
            historiqueCarteGazoile.setDateDesafect(d);
            carteGasoilService.ajoutHistorique(historiqueCarteGazoile);
        } catch (Exception e) {
            looger.warning("Erreur d'enregistrement historique carte car : " + e.getMessage());
        }

        salarie = new Salarie();
        chantier = new Chantier();
        carteGazoile = new CarteGasoil();
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Affectation effectué avec succées", ""));
        if (activeCarteCH) {
            chargerListGazoilChantier();
        } else if (activeCarteSL) {
            chargerListGazoilSalary();
        } else if (activeCarte) {
            chargerListCarteAfecter();
        } else if (activeCarteHCH) {
            chargerListCarteHC();
        } else if (activeCarteHSL) {
            chargerListCarteHSL();
        }
    }

    public void affectCarteChantier() {

        carteGazoile.setChantier(chantier);
        carteGazoile.setAffect(Boolean.TRUE);
        carteGazoile.setDateafectation(dateAfect);
        carteGasoilService.update(carteGazoile);

        try {
            historiqueCarteGazoile = new HistoriqueCarteGazoile();
            historiqueCarteGazoile.setCarteGasoil(carteGazoile);
            historiqueCarteGazoile.setDateAfect(dateAfect);
            historiqueCarteGazoile.setChantier(chantier);
            carteGasoilService.ajoutHistorique(historiqueCarteGazoile);
        } catch (Exception e) {
            looger.warning("Erreur d'enregistrement historique carte car : " + e.getMessage());
        }
        chantier = new Chantier();
        carteGazoile = new CarteGasoil();
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("" + Message.DESAFCT_VOITURE_SUCCESS, ""));
    }

    public void chargerChantier() {
        try {
            chantiers = chantierService.listeChantiers();
        } catch (Exception e) {
            looger.warning("Erreur de chargement de la liste car " + e.getMessage());
        }
    }

    public void chargerListGazoilSalary() {
        activeCarteCH = Boolean.FALSE;
        activeCarteSL = Boolean.TRUE;
        activeCarte = Boolean.FALSE;
        activeCarteHCH = Boolean.FALSE;
        activeCarteHSL = Boolean.FALSE;
        carteGazoiles = carteGasoilService.listCarteGasoilSalarie();
    }

    public void chargerListGazoilChantier() {
        activeCarteCH = Boolean.TRUE;
        activeCarteSL = Boolean.FALSE;
        activeCarte = Boolean.FALSE;
        activeCarteHCH = Boolean.FALSE;
        activeCarteHSL = Boolean.FALSE;
        carteGazoiles = carteGasoilService.listCarteGasoilChantier();
    }

    public void chargerListCarteAfecter() {
        activeCarteCH = Boolean.FALSE;
        activeCarteSL = Boolean.FALSE;
        activeCarte = Boolean.TRUE;
        activeCarteHCH = Boolean.FALSE;
        activeCarteHSL = Boolean.FALSE;
        carteGazoiles = carteGasoilService.listCarteGasoilAffecte();
    }

    public void chargerListCarteHC() {
        activeCarteCH = Boolean.FALSE;
        activeCarteSL = Boolean.FALSE;
        activeCarte = Boolean.FALSE;
        activeCarteHCH = Boolean.TRUE;
        activeCarteHSL = Boolean.FALSE;
        historiqueCarteGazoiles = carteGasoilService.getAllHistoriqueCarteGazoileChantier();
    }

    public void chargerListCarteH() {
        activeCarteCH = Boolean.FALSE;
        activeCarteSL = Boolean.FALSE;
        activeCarte = Boolean.FALSE;
        activeCarteHCH = Boolean.TRUE;
        activeCarteHSL = Boolean.FALSE;
        historiqueCarteGazoiles = carteGasoilService.getAllHistoriqueCarteGazoile();
    }

    public void chargerListCarteHSL() {
        activeCarteCH = Boolean.FALSE;
        activeCarteSL = Boolean.FALSE;
        activeCarte = Boolean.FALSE;
        activeCarteHCH = Boolean.FALSE;
        activeCarteHSL = Boolean.TRUE;
        historiqueCarteGazoiles = carteGasoilService.getAllHistoriqueCarteGazoileSalarier();
    }

    public void chargerListCarte() {
        activeCarteCH = Boolean.FALSE;
        activeCarteSL = Boolean.FALSE;
        activeCarte = Boolean.TRUE;
        activeCarteHCH = Boolean.FALSE;
        activeCarteHSL = Boolean.FALSE;
        carteGazoiles = carteGasoilService.listCarteGasoil();
    }

    public String voitureBySalary(Long id) {
        Voiture v = new Voiture();
        String s="";
        try {
                v = carteGasoilService.voitureBySalary(id);
                s= v.getMatriculevoiture().toString()+" | "+v.getModel().toString();
        } catch (Exception e) {
            looger.warning("Erreur de récuperation de la voiture car "+e.getMessage());
        }
        return s;
    }
    public String voitureByChantier(Long id) {
        Voiture v = new Voiture();
        String s="";
        try {
                v = carteGasoilService.voitureByChantier(id);
                s= v.getMatriculevoiture().toString()+" | "+v.getModel().toString();
        } catch (Exception e) {
            looger.warning("Erreur de récuperation de la voiture car "+e.getMessage());
        }
        return s;
    }
    public void initCarteToShowDocs(CarteGasoil carte) {
        carteGazoileToShow = carte;
        listDoc = documentService.getDocumentByCarteGZ(carteGazoileToShow.getId());
    }

    public void removeGest(Document a) throws IOException {
        documentService.deleteDocument(a);
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("" + Message.DELETE_DOCUMENT, Message.DELETE_DOCUMENT));
        listDoc = documentService.getDocumentByCarteGZ(carteGazoileToShow.getId());
    }

}
