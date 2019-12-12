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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.print.attribute.standard.Severity;
import ma.bservice.tgcc.Constante.Message;
import ma.bservices.beans.Chantier;
import ma.bservices.beans.Document;
import ma.bservices.beans.HistoriqueVoiture;
import ma.bservices.beans.Salarie;
import ma.bservices.mb.services.Module;
import ma.bservices.services.ChantierService;
import ma.bservices.tgcc.Entity.Historique_Voiture;
import ma.bservices.tgcc.Entity.Historique_VoitureChantier;
import ma.bservices.tgcc.Entity.Voiture;
import ma.bservices.tgcc.service.Mensuel.CarteGasoilService;
import ma.bservices.tgcc.service.Mensuel.DocumentService;
import ma.bservices.tgcc.service.Mensuel.HistoriqueService;
import ma.bservices.tgcc.service.Mensuel.MensuelService;
import ma.bservices.tgcc.service.Mensuel.VoitureServices;
import ma.bservices.tgcc.utilitaire.Outils;
import org.primefaces.event.RowEditEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

/**
 *
 * @author a.wattah
 */
@Component
@ManagedBean(name = "gestion_voitureMb")
@ViewScoped
public class GestionVoitureMb implements Serializable {

    private static final Logger looger = Logger.getLogger(GestionVoitureMb.class.getName());

    ////////////////////////////////////////////////////////////////////
    /////////////////////// Variables /////////////////////////////////
    //////////////////////////////////////////////////////////////////
    @ManagedProperty(value = "#{voitureService}")
    private VoitureServices voitureService;

    @ManagedProperty(value = "#{historiqueService}")
    private HistoriqueService hService;

    @ManagedProperty(value = "#{carteGasoilService}")
    private CarteGasoilService carteGasoilService;

    @ManagedProperty(value = "#{documentService}")
    private DocumentService documentService;

    @ManagedProperty(value = "#{mensuelService}")
    private MensuelService mensuelService;
    
    @ManagedProperty(value = "#{chantierServiceEvol}")
    private ChantierService chantierService;

    private List<Document> listDoc = new LinkedList();
    private List<String> Marque_Voitures;
    private List<Voiture> voitures;
    private List<Salarie> salaries = new ArrayList<Salarie>();
    private List<Chantier> chantiers = new ArrayList<Chantier>();
    private List<HistoriqueVoiture> historiqueVoitures = new ArrayList<HistoriqueVoiture>();
    private List<Historique_Voiture> voituresH = new LinkedList<>();
    private List<Historique_VoitureChantier> voituresH2 = new LinkedList<>();

    private Voiture voitureToAdd;
    private Voiture voiture = new Voiture();
    private Salarie salarie = new Salarie();
    private Chantier chantier = new Chantier();
    private Date dateFromS, dateFromC, dateToS, dateToC, datedebu, datefin, dateAfect, datedesafct;
    private String marque;
    private long duree;
    private Voiture voitureToShow = new Voiture();
    private Voiture voitureSelected = new Voiture();

    //////////////////////////////////////////////////////////////////
    ////////////////////Geters et Seters /////////////////////////////
    //////////////////////////////////////////////////////////////////
    public Voiture getVoitureSelected() {
        return voitureSelected;
    }

    public void setVoitureSelected(Voiture voitureSelected) {
        this.voitureSelected = voitureSelected;
    }

    public List<Historique_VoitureChantier> getVoituresH2() {
        return voituresH2;
    }

    public void setVoituresH2(List<Historique_VoitureChantier> voituresH2) {
        this.voituresH2 = voituresH2;
    }

    public DocumentService getDocumentService() {
        return documentService;
    }

    public void setDocumentService(DocumentService documentService) {
        this.documentService = documentService;
    }

    public List<String> getMarque_Voitures() {
        return Marque_Voitures;
    }

    public void setMarque_Voitures(List<String> Marque_Voitures) {
        this.Marque_Voitures = Marque_Voitures;
    }

    public List<Document> getListDoc() {
        return listDoc;
    }

    public void setListDoc(List<Document> listDoc) {
        this.listDoc = listDoc;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public CarteGasoilService getCarteGasoilService() {
        return carteGasoilService;
    }

    public Voiture getVoitureToShow() {
        return voitureToShow;
    }

    public void setVoitureToShow(Voiture voitureToShow) {
        this.voitureToShow = voitureToShow;
    }

    public void setCarteGasoilService(CarteGasoilService carteGasoilService) {
        this.carteGasoilService = carteGasoilService;
    }

    public long getDuree() {
        return duree;
    }

    public void setDuree(long duree) {
        this.duree = duree;
    }

    public Date getDatedebu() {
        return datedebu;
    }

    public void setDatedebu(Date datedebu) {
        this.datedebu = datedebu;
    }

    public Date getDatefin() {
        return datefin;
    }

    public List<Historique_Voiture> getVoituresH() {
        return voituresH;
    }

    public void setVoituresH(List<Historique_Voiture> voituresH) {
        this.voituresH = voituresH;
    }

    public HistoriqueService gethService() {
        return hService;
    }

    public void sethService(HistoriqueService hService) {
        this.hService = hService;
    }

    public void setDatefin(Date datefin) {
        this.datefin = datefin;
    }

    public List<Voiture> getVoitures() {
        return voitures;
    }

    public void setVoitures(List<Voiture> voitures) {
        this.voitures = voitures;
    }

    public Date getDateFromS() {
        return dateFromS;
    }

    public void setDateFromS(Date dateFromS) {
        this.dateFromS = dateFromS;
    }

    public Date getDateFromC() {
        return dateFromC;
    }

    public void setDateFromC(Date dateFromC) {
        this.dateFromC = dateFromC;
    }

    public Date getDateToS() {
        return dateToS;
    }

    public void setDateToS(Date dateToS) {
        this.dateToS = dateToS;
    }

    public Date getDateToC() {
        return dateToC;
    }

    public void setDateToC(Date dateToC) {
        this.dateToC = dateToC;
    }

    public void setVoitureToAdd(Voiture voitureToAdd) {
        this.voitureToAdd = voitureToAdd;
    }

    public Voiture getVoitureToAdd() {
        return voitureToAdd;
    }

    public VoitureServices getVoitureService() {
        return voitureService;
    }

    public void setVoitureService(VoitureServices voitureService) {
        this.voitureService = voitureService;
    }

    public Voiture getVoiture() {
        return voiture;
    }

    public void setVoiture(Voiture voiture) {
        this.voiture = voiture;
    }

    public void setMensuelService(MensuelService mensuelService) {
        this.mensuelService = mensuelService;
    }

    public void setSalaries(List<Salarie> salaries) {
        this.salaries = salaries;
    }

    public void setSalarie(Salarie salarie) {
        this.salarie = salarie;
    }

    public void setChantier(Chantier chantier) {
        this.chantier = chantier;
    }

    public MensuelService getMensuelService() {
        return mensuelService;
    }

    public List<Salarie> getSalaries() {
        return salaries;
    }

    public Salarie getSalarie() {
        return salarie;
    }

    public Chantier getChantier() {
        return chantier;
    }

    public Date getDateAfect() {
        return dateAfect;
    }

    public void setDateAfect(Date dateAfect) {
        this.dateAfect = dateAfect;
    }
    
    public List<Chantier> getChantiers() {
        return chantiers;
    }

    public void setChantiers(List<Chantier> chantiers) {
        this.chantiers = chantiers;
    }

    public ChantierService getChantierService() {
        return chantierService;
    }

    public void setChantierService(ChantierService chantierService) {
        this.chantierService = chantierService;
    }

    public Date getDatedesafct() {
        return datedesafct;
    }

    public void setDatedesafct(Date datedesafct) {
        this.datedesafct = datedesafct;
    }

    public void setHistoriqueVoitures(List<HistoriqueVoiture> historiqueVoitures) {
        this.historiqueVoitures = historiqueVoitures;
    }

    public List<HistoriqueVoiture> getHistoriqueVoitures() {
        return historiqueVoitures;
    }

    
    /**
     * end etters and getters
     */
    //////////////////////////////////////////////////////////////////
    ////////////////////Methodes ////////////////////////////////////
    ////////////////////////////////////////////////////////////////
    public void searchByDateS() {
        voituresH = hService.findByDateRangeVS(dateFromS, dateToS);
    }

    public void reinitSearchS() {
        voituresH = hService.findAllVoituresSalarie();
        dateFromS = null;
        dateToS = null;
    }

    public void searchByDateC() {
        voituresH2 = hService.findByDateRangeVC(dateFromC, dateToC);

    }

    public void reinitSearchC() {
        voituresH2 = hService.findAllVoituresChantier();
        dateFromC = null;
        dateToC = null;
    }

    @PostConstruct
    public void init() {
        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        voitureService = ctx.getBean(VoitureServices.class);
        documentService = ctx.getBean(DocumentService.class);
        chantierService = Module.ctx.getBean(ma.bservices.services.ChantierService.class);
        hService = ctx.getBean(HistoriqueService.class);
        voitureToAdd = new Voiture();
        voiture = new Voiture();
        
    }

    public void initVoitureToShowDocs(Voiture voiture) {
        System.out.println("voiture to show docs" + voiture.getMatriculevoiture());
        voitureToShow = voiture;
        listDoc = documentService.getDocumentByVoiture(voitureToShow.getId());
    }

    public void removeGest(Document a) throws IOException {
        documentService.deleteDocument(a);
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("" + Message.DELETE_DOCUMENT, Message.DELETE_DOCUMENT));
        listDoc = documentService.getDocumentByVoiture(voitureToShow.getId());

//        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
//        ec.redirect(ec.getRequestContextPath() + "/mensuel/docVoiture.xhtml");
    }

    public void ajouterVoiture() {

        /* Boolean existMatricule = false;
        Boolean existMatriculeProvisoire = false;
        Boolean existNumChassis = false;
        Boolean existNumeroCartegrise = false;
        Boolean existNumeroAssurance = false;

        if (datedebu != null && datefin != null) {
            duree = datedebu.getTime() - datefin.getTime();
        }*/
 /* ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        ma.bservices.tgcc.mb.services.VoitureMb voitureServMb = (ma.bservices.tgcc.mb.services.VoitureMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "voiture_ServMb");
        voitureServMb.reloadVoitureFindAll();
        List<Voiture> l_voitures_ = voitureServMb.getL_voitures();

        if (l_voitures_ != null) {
            if (!l_voitures_.isEmpty()) {

                for (Voiture v__ : l_voitures_) {

                    if (voitureToAdd.getNum_chassis() != null) {

                        String numChanssis = voitureToAdd.getNum_chassis().replaceAll(" ", "");
                        if (v__.getNum_chassis().equals(numChanssis)) {
                            existNumChassis = true;
                        }
                    }

                    if (voitureToAdd.getMatricule_voiture_nouveau() != null) {

                        String matriculeProv = voitureToAdd.getMatricule_voiture_nouveau().replaceAll(" ", "");

                        if (v__.getMatricule_voiture_nouveau().equals(matriculeProv)) {

                            existMatriculeProvisoire = true;
                        }
                    }
                    if (voitureToAdd.getNumcontrat() != null) {

                        String numContrat = voitureToAdd.getNumcontrat().replaceAll(" ", "");

                        if (v__.getNumcontrat().equals(numContrat)) {
                            existNumeroAssurance = true;
                        }
                    }

                    if (voitureToAdd.getNumcartegrise() != null) {

                        String numCartGrise = voitureToAdd.getNumcartegrise().replaceAll(" ", "");

                        if (v__.getNumcartegrise().equals(numCartGrise)) {
                            existNumeroCartegrise = true;
                        }
                    }

                    if (voitureToAdd.getMatriculevoiture() != null) {

                        String matricule = voitureToAdd.getMatriculevoiture().replaceAll(" ", "");

                        if (v__.getMatriculevoiture().equals(matricule)) {
                            existMatricule = true;
                        }
                    }

                }

            }
        }
         */
        System.out.println("voitureToAdd =======> "+voitureToAdd.toString());
        Boolean existe = voitureService.existeVoiture(voitureToAdd);

        if (existe) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, Message.EXISTE_VOITUR_WARNING, Message.EXISTE_VOITUR_WARNING));
        } else {
            //voitureToAdd.setDurecontratassu(String.valueOf(duree));
            voitureService.ajouter_voiture(voitureToAdd);
            voitures = voitureService.findAll();
            voitureToAdd = new Voiture();
            datefin = null;
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("" + Message.ADD_VOITURE_SUCCESS, ""));

        }

    }

    public void delete(Voiture v) {

        if (v.getSalarie() != null || v.getChantier() != null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                    Message.DELETE_VOITURE_FALSE, Message.DELETE_VOITURE_FALSE));

            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(Severity.WARNING + Message.DELETE_VOITURE_FALSE, ""));

        } else {

            voitureService.delete(v);
            voitures = voitureService.findAll();
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("" + Message.DELETE_VOITURE_SUCCESS, ""));
        }

    }

    public void onRowEdit(RowEditEvent event) {

        Voiture voiture_To_Update = (Voiture) event.getObject();
//        VoitureSalarie v = new VoitureSalarie();
//        VoitureChantier voiture_To_Cast = new VoitureChantier();
//
//        if (voiture_To_Update instanceof VoitureSalarie) {
//            v = (VoitureSalarie) voiture_To_Update;
//        }
//
//        if (voiture_To_Update instanceof VoitureChantier) {
//            voiture_To_Cast = (VoitureChantier) voiture_To_Update;
//        }
//
//        if (v.getMensuel() != null || voiture_To_Cast.getChantier() != null) {
//            if (v.getMensuel() != null) {
//
//                if (v.getMensuel().getId() != null) {
//                    FacesContext context = FacesContext.getCurrentInstance();
//                    String message = " Ce Voiture est déjà afféctée impossible de Modifié ";
//                    context.addMessage(null, new FacesMessage(Severity.WARNING + message, ""));
//                     voitures = voitureService.findAll();
//                }
//
//            }
//
//            if (voiture_To_Cast.getChantier() != null) {
//
//                if (voiture_To_Cast.getChantier().getPrjapId() != null) {
//                    FacesContext context = FacesContext.getCurrentInstance();
//                    String message = " Ce Voiture est déjà afféctée impossible de Modifié ";
//                    context.addMessage(null, new FacesMessage(Severity.WARNING + message, ""));
//                     voitures = voitureService.findAll();
//                }
//            }
//
//        } else {

        Boolean update = voitureService.update(voiture_To_Update);

        if (update == true) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("" + Message.UPDATE_VOITURE_SUCCESS, ""));
            voitures = voitureService.findAll();
        }
//        }

    }

    public void onRowCancel() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Warning " + Message.UPDATE_VOITURE_CANCEL, ""));
    }

    public List<String> completeText(String query) {
        List<String> results = new ArrayList<String>();
        for (int i = 0; i < Marque_Voitures.size(); i++) {
            if (Marque_Voitures.get(i) != null) {
                results.add(Marque_Voitures.get(i));
            }
        }

        return results;
    }

    public void modifierVoitures() {
        voitures = voitureService.findAll();
    }

    /**
     * Update le détail de la voiture
     */
    public void modifierVoiture() throws IOException {
//
//        Boolean existMatricule = false;
//        Integer existMatricule2 = 0;
//        Boolean existMatriculeProvisoire = false;
//        Integer existMatriculeProvisoire2 = 0;
//        Boolean existNumChassis = false;
//        Integer existNumChassis2 = 0;
//
//        Boolean existNumeroCartegrise = false;
//        Integer existNumeroCartegrise2 = 0;
//
//        Boolean existNumeroAssurance = false;

        //  Integer existNumeroAssurance2 = 0;
//        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
//        ma.bservices.tgcc.mb.services.VoitureMb voitureServMb = (ma.bservices.tgcc.mb.services.VoitureMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "voiture_ServMb");
//        voitureServMb.reloadVoitureFindAll();
        // List<Voiture> l_voitures_ = voitureService.findAll();
//        List<Voiture> l_voitures_ = voitureServMb.getL_voitures();

        /*if (l_voitures_ != null) {
            if (!l_voitures_.isEmpty()) {

                for (Voiture v__ : l_voitures_) {

                    if (voitureSelected.getNum_chassis() != null) {

                        String numChanssis = voitureSelected.getNum_chassis().replaceAll(" ", "");

                        if (!"".equals(v__.getNum_chassis())) {

                            if (v__.getNum_chassis().equals(numChanssis)) {
                                existMatricule2 += 1;
                                continue;

                            }
                        }

                    }

                    if (voitureSelected.getMatricule_voiture_nouveau() != null) {

                        String matriculeProv = voitureSelected.getMatricule_voiture_nouveau().replaceAll(" ", "");

                        if (!"".equals(v__.getMatricule_voiture_nouveau())) {

                            if (v__.getMatricule_voiture_nouveau().equals(matriculeProv)) {

                                existMatriculeProvisoire2 += 1;
                                continue;
                            }
                        }
                    }
                    if (voitureSelected.getNumcontrat() != null) {

                        String numContrat = voitureSelected.getNumcontrat().replaceAll(" ", "");

                        if (!"".equals(v__.getNumcontrat())) {

                            if (v__.getNumcontrat().equals(numContrat)) {
                                existNumeroAssurance2 += 1;
                                continue;
                            }
                        }
                    }

                    if (voitureSelected.getNumcartegrise() != null) {

                        String numCartGrise = voitureSelected.getNumcartegrise().replaceAll(" ", "");

                        if (!"".equals(v__.getNumcartegrise())) {

                            if (v__.getNumcartegrise().equals(numCartGrise)) {
                                existNumeroCartegrise2 += 1;
                                continue;
                            }
                        }
                    }

                    if (voitureSelected.getMatriculevoiture() != null) {

                        String matricule = voitureSelected.getMatriculevoiture().replaceAll(" ", "");

                        if (!"".equals(v__.getMatriculevoiture())) {

                            if (v__.getMatriculevoiture().equals(matricule)) {
                                existMatricule2 += 1;
                            }
                        }
                    }

                }

            }
        }

  //      if (existMatricule2 == 2) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, Message.MATRICULEVOITURE_EXIST, Message.MATRICULEVOITURE_EXIST));
//            voitureServMb.reload();
//            voitureServMb.reloadVoitureFindAll();
//            this.voitures = voitureServMb.getL_voitures();
            voitures = voitureService.findAll();

        } else if (existMatriculeProvisoire2 == 2) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, Message.NUMMATRICULATIONPROVISOIRE_EXIST, Message.NUMMATRICULATIONPROVISOIRE_EXIST));
//            voitureServMb.reload();
//            voitureServMb.reloadVoitureFindAll();
//            this.voitures = voitureServMb.getL_voitures();
             voitures = voitureService.findAll();

        } else if (existNumChassis2 == 2) {

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, Message.NUMCHASSIS_EXIST, Message.NUMCHASSIS_EXIST));
//            voitureServMb.reload();
//            voitureServMb.reloadVoitureFindAll();
//            this.voitures = voitureServMb.getL_voitures();
            voitures = voitureService.findAll();
        } else if (existNumeroAssurance2 == 2) {

//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, Message.NUMCONTRATASSURANCE_EXIST, Message.NUMCONTRATASSURANCE_EXIST));
//            voitureServMb.reload();
//
//            voitureServMb.reloadVoitureFindAll();
//            this.voitures = voitureServMb.getL_voitures();
               voitures = voitureService.findAll();

        } else if (existNumeroCartegrise2 == 2) {

//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, Message.NUMCARTEGRISE_EXIST, Message.NUMCARTEGRISE_EXIST));
//            voitureServMb.reload();
//
//            voitureServMb.reloadVoitureFindAll();
//            this.voitures = voitureServMb.getL_voitures();
            voitures = voitureService.findAll();

        } else {*/
        Boolean update = voitureService.update(voitureSelected);

        //  voitures = voitureService.findAll();
        if (update == true) {
            System.out.println("updated MB");
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            //ec.redirect(ec.getRequestContextPath() + "/mensuel/CreationVoiture.xhtml");

            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("" + Message.UPDATE_VOITURE_SUCCESS, ""));

            voitures = voitureService.findAll();
        }
        voitures = voitureService.findAll();
    }

    public String convertDateFormat(Date date) {

        Outils outils = new Outils();
        return outils.convertDate_To_string(date);
    }

    public void afficherVoiture() {
        voitures = voitureService.findAll();
    }

    public void afficherVoitureAffecterSalarie() {
        voitures = voitureService.getListeVoituresAffecterSalarie();
    }

    public void afficherVoitureAffecterChantier() {
        voitures = voitureService.getListeVoituresAffecterChantier();
    }

    public void afficherVoitureNonAffecter() {
        voitures = voitureService.getListeVoituresNonAffecter();
    }

    public void chargerSalarie() {
        salaries = mensuelService.findAllActifs();
    }
    
    public void chargerChantier(){
        try {
            chantiers=chantierService.listeChantiers();
        } catch (Exception e) {
            looger.warning("Erreur de chargement de la liste car "+e.getMessage());
        }
    }
    
    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////Gestion d'affectation //////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    
    public void affectVoitureSalarier() {
        Boolean verif = false;
        try {
            voiture.setSalarie(salarie);
            voiture.setAffect(Boolean.TRUE);
            voiture.setDateaffectation(dateAfect);
            voitureService.update(voiture);
            verif = true;
            
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("" + Message.AFFECTED_VOITURE_SUCCESS, ""));
            voiture= new Voiture();
            salarie = new Salarie();
        } catch (Exception e) {
            looger.log(Level.WARNING, "Erreur d''afectation de salarier car {0}", e.getMessage());
        }
        if (verif) {
            try {
                HistoriqueVoiture hv = new HistoriqueVoiture();
                hv.setDatehistorique(new Date());
                hv.setVoiture(voiture);
                hv.setSalarie(salarie);
                hv.setDateAfect(dateAfect);
                voitureService.ajoutHistorique(hv);
            } catch (Exception e) {
                looger.log(Level.WARNING, "Erreur d''enregistrer l''historique d''afection de salarier car {0}", e.getMessage());
            }
        }

    }

    public void affectVoitureChantier() {
        Boolean verif = false;
        try {
            voiture.setChantier(chantier);
            voiture.setAffect(Boolean.TRUE);
            voiture.setDateaffectation(dateAfect);
            voitureService.update(voiture);
            verif = true;
            
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("" + Message.AFFECTED_VOITURE_SUCCESS, ""));
            voiture= new Voiture();
            chantier = new Chantier();
        } catch (Exception e) {
            looger.log(Level.WARNING, "Erreur d''afectation de salarier car {0}", e.getMessage());
        }
        if (verif) {
            try {
                HistoriqueVoiture hv = new HistoriqueVoiture();
                hv.setDatehistorique(new Date());
                hv.setVoiture(voiture);
                hv.setChantier(chantier);
                hv.setDateAfect(dateAfect);
                voitureService.ajoutHistorique(hv);
            } catch (Exception e) {
                looger.log(Level.WARNING, "Erreur d''enregistrer l''historique d''afection de salarier car {0}", e.getMessage());
            }
        }

    }
    
    public void desaffectVoitureChantier() {
        
        Boolean verif = false;
        
        try {
            chantier = voiture.getChantier();
            voiture.setChantier(null);
            voiture.setAffect(Boolean.FALSE);
            voiture.setDateRendu(datedesafct);
            voitureService.update(voiture);
            verif = true;
            
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("" + Message.DESAFCT_VOITURE_SUCCESS, ""));
            afficherVoitureAffecterChantier();
        } catch (Exception e) {
            looger.log(Level.WARNING, "Erreur d'afectation de salarier car {0}", e.getMessage());
        }
        if (verif) {
            try {
                HistoriqueVoiture hv = new HistoriqueVoiture();
                hv.setDatehistorique(new Date());
                hv.setVoiture(voiture);
                hv.setChantier(chantier);
                hv.setDateDesafect(datedesafct);
                voitureService.ajoutHistorique(hv);
            } catch (Exception e) {
                looger.log(Level.WARNING, "Erreur d''enregistrer l''historique d''afection de salarier car {0}", e.getMessage());
            }
        }
            voiture= new Voiture();
            chantier = new Chantier();
            datedesafct= new Date();
    }
    public void desaffectVoitureSalarier() {
        Boolean verif = false;
        try {
            salarie=voiture.getSalarie();
            voiture.setSalarie(null);
            voiture.setAffect(Boolean.FALSE);
            voiture.setDateRendu(datedesafct);
            voitureService.update(voiture);
            verif = true;
            
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("" + Message.DESAFCT_VOITURE_SUCCESS, ""));
            afficherVoitureAffecterSalarie();
        } catch (Exception e) {
            looger.log(Level.WARNING, "Erreur d''afectation de salarier car {0}", e.getMessage());
        }
        if (verif) {
            try {
                HistoriqueVoiture hv = new HistoriqueVoiture();
                hv.setDatehistorique(new Date());
                hv.setVoiture(voiture);
                hv.setSalarie(salarie);
                hv.setDateDesafect(datedesafct);
                voitureService.ajoutHistorique(hv);
            } catch (Exception e) {
                looger.log(Level.WARNING, "Erreur d''enregistrer l''historique d''afection de salarier car {0}", e.getMessage());
            }
        }
            voiture= new Voiture();
            salarie = new Salarie();
            datedesafct= new Date();
            
    }
    
    ///////////////////////////////////////////////////////////////////////////
    /////////////////////// Gestion d'historique //////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    
    public void hitoriqueAffectationSalarier(){
        historiqueVoitures = voitureService.getAllHistoriqueVoitureSalarier();
    }
    
    public void hitoriqueAffectationChantier(){
        historiqueVoitures = voitureService.getAllHistoriqueVoitureChantier();
    }
    
    public void historiqueAffectionSalarierParDate(){
        historiqueVoitures = voitureService.getAllHistoriqueVoitureSalarierByDate(datedebu, datefin);
    }
    public void historiqueAffectationChantierDate(){
        historiqueVoitures = voitureService.getAllHistoriqueVoitureChantierByDate(datedebu, datefin);
    }
    public String chantierBySalaryID(Long id) {
        return voitureService.getChantierBySalaryID(id);
    }
}
