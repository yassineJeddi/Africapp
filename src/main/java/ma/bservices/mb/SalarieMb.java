package ma.bservices.mb;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.servlet.http.HttpServletRequest;
import ma.bservice.tgcc.Constante.Constante;
import ma.bservices.beans.Chantier;
import ma.bservices.beans.Document;
import ma.bservices.beans.Fonction;
import ma.bservices.beans.ModePaiement;

import ma.bservices.beans.Salarie;
import ma.bservices.beans.Zone;
import ma.bservices.constantes.Constantes;
import ma.bservices.mb.services.Evol_ChantierMb;
import ma.bservices.mb.services.Evol_FonctionMb;
import ma.bservices.mb.services.Module;
//import ma.bservices.metier.SalarieManager;
//import ma.bservices.metier.SalarieManagerImpl;
import ma.bservices.paginate.SalariePagination;
import ma.bservices.services.ChantierService;
import ma.bservices.services.DocumentService;
import ma.bservices.services.ParametrageService;
import ma.bservices.services.SalarieService;
import ma.bservices.services.SalarieServiceIn;
import ma.bservices.services.ZoneServices;
import ma.bservices.tgcc.service.Mensuel.MensuelService;
import ma.bservices.tgcc.utilitaire.LazySalarieDataModel;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.UploadedFile;

import org.springframework.stereotype.Component;

@Component
@ManagedBean(name = "salarieMb")
@ViewScoped
public class SalarieMb implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    //@Resource(name = "salarieService")
    //@Autowired
    @ManagedProperty(value = "#{salarieService}")
    private SalarieService salarieService;

    @ManagedProperty(value = "#{mensuelService}")
    private MensuelService mensuelService;

    @ManagedProperty(value = "#{salarieServiceIn}")
    private SalarieServiceIn salarieServiceIn;

    @ManagedProperty(value = "#{chantieService}")
    private ChantierService chantieService;
    private List<Chantier> nonAffectChantier;
    private Integer idchantier;

    @ManagedProperty(value = "#{parametrageService}")
    private ParametrageService parametrageService;

    @ManagedProperty(value = "#{documentServiceEvol}")
    private DocumentService documentService;

    @ManagedProperty(value = "#{zoneService}")
    private ZoneServices zoneServices;

    private Integer fonction;
    private Integer statut;
    private Integer etat;
    private Integer pointureID, tailleID, giletID, casqueID, typeID, civiliteID;
    private String chantier = "";
    private LazyDataModel<Salarie> lazyModel;
    private Salarie findSalarie = new Salarie();
    private Salarie salarie = new Salarie();
    private Boolean prev, next, last, first, pageId;
    /**
     * NouveauSalarie
     */
    private Salarie addSalarie = new Salarie();
    private UploadedFile userPhotoUploaded;
    private String cheminPhotoSalarie;
    private int modepaiement;
    private Boolean disable;
    private int nationalite;
    private int situationFa;
    private int pays;
    private UploadedFile uploadedFile;
    private UploadedFile uploadedFilecnss;
    private Document document;
    private static String chemin;
    private Document documentcnss;
    private static String chemincnss;
    private String imgUrl;
    private Integer var;
    /**
     * Partie Evols
     */
    private Evol_FonctionMb fonctionMb;
    private String idStatut;
    private List<Fonction> fonctions = new LinkedList<>();
    private List<Chantier> chantiersBySalarie = new LinkedList<>();
    private List<Salarie> salarieChefEquipe = new LinkedList<>();
    private List<Zone> zones = new ArrayList<>();
    private List<Chantier> chantiers = new LinkedList<>();
    private int idChantier;
    private int idZone;
    private Salarie salaroeToAffect = new Salarie();

    /**
     * -- partie organigrame -- *
     */
    List<Salarie> selectedSalaries = new LinkedList<>();

    public Evol_FonctionMb getFonctionMb() {
        return fonctionMb;
    }

    public void setFonctionMb(Evol_FonctionMb fonctionMb) {
        this.fonctionMb = fonctionMb;
    }

    public String getIdStatut() {
        return idStatut;
    }

    public void setIdStatut(String idStatut) {
        this.idStatut = idStatut;
    }

    public List<Fonction> getFonctions() {
        return fonctions;
    }

    /**
     * ***************************
     *
     * @param fonctions
     */
    public void setFonctions(List<Fonction> fonctions) {
        this.fonctions = fonctions;
    }

    public Salarie getSalaroeToAffect() {
        return salaroeToAffect;
    }

    public void setSalaroeToAffect(Salarie salaroeToAffect) {
        this.salaroeToAffect = salaroeToAffect;
    }

    public Integer getTypeID() {
        return typeID;
    }

    public MensuelService getMensuelService() {
        return mensuelService;
    }

    public void setMensuelService(MensuelService mensuelService) {
        this.mensuelService = mensuelService;
    }

    public List<Salarie> getSelectedSalaries() {
        return selectedSalaries;
    }

    public void setSelectedSalaries(List<Salarie> selectedSalaries) {
        this.selectedSalaries = selectedSalaries;
    }

    public void setTypeID(Integer typeID) {
        this.typeID = typeID;
    }

    public Integer getCiviliteID() {
        return civiliteID;
    }

    public void setCiviliteID(Integer civiliteID) {
        this.civiliteID = civiliteID;
    }

    public int getIdZone() {
        return idZone;
    }

    public void setIdZone(int idZone) {
        this.idZone = idZone;
    }

    public ZoneServices getZoneServices() {
        return zoneServices;
    }

    public void setZoneServices(ZoneServices zoneServices) {
        this.zoneServices = zoneServices;
    }

    public List<Zone> getZones() {
        return zones;
    }

    public void setZones(List<Zone> zones) {
        this.zones = zones;
    }

    public int getIdChantier() {
        return idChantier;
    }

    public void setIdChantier(int idChantier) {
        this.idChantier = idChantier;
    }

    public List<Chantier> getChantiersBySalarie() {
        return chantiersBySalarie;
    }

    public void setChantiersBySalarie(List<Chantier> chantiersBySalarie) {
        this.chantiersBySalarie = chantiersBySalarie;
    }

    public List<Chantier> getChantiers() {
        return chantiers;
    }

    public void setChantiers(List<Chantier> chantiers) {
        this.chantiers = chantiers;
    }

    public List<Salarie> getSalarieChefEquipe() {
        return salarieChefEquipe;
    }

    public void setSalarieChefEquipe(List<Salarie> salarieChefEquipe) {
        this.salarieChefEquipe = salarieChefEquipe;
    }

    public Integer getVar() {
        return var;
    }

    public SalarieServiceIn getSalarieServiceIn() {
        return salarieServiceIn;
    }

    public void setSalarieServiceIn(SalarieServiceIn salarieServiceIn) {
        this.salarieServiceIn = salarieServiceIn;
    }

    public Integer getFonction() {
        return fonction;
    }

    public void setFonction(Integer fonction) {
        this.fonction = fonction;
    }

    public String getCheminPhotoSalarie() {
        return cheminPhotoSalarie;
    }

    public void setCheminPhotoSalarie(String cheminPhotoSalarie) {
        this.cheminPhotoSalarie = cheminPhotoSalarie;
    }

    public void setVar(Integer var) {
        this.var = var;
    }

    public Salarie getSalarie() {
        return salarie;
    }

    public void setSalarie(Salarie salarie) {
        this.salarie = salarie;
    }

    public Integer getIdchantier() {
        return idchantier;
    }

    public void setIdchantier(Integer idchantier) {
        this.idchantier = idchantier;
    }

    public List<Chantier> getNonAffectChantier() {
        return nonAffectChantier;
    }

    public void setNonAffectChantier(List<Chantier> nonAffectChantier) {
        this.nonAffectChantier = nonAffectChantier;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public UploadedFile getUserPhotoUploaded() {
        return userPhotoUploaded;
    }

    public void setUserPhotoUploaded(UploadedFile userPhotoUploaded) {
        this.userPhotoUploaded = userPhotoUploaded;
    }

    public Document getDocumentcnss() {
        return documentcnss;
    }

    public void setDocumentcnss(Document documentcnss) {
        this.documentcnss = documentcnss;
    }

    public static String getChemincnss() {
        return chemincnss;
    }

    public static void setChemincnss(String chemincnss) {
        SalarieMb.chemincnss = chemincnss;
    }

    public UploadedFile getUploadedFilecnss() {
        return uploadedFilecnss;
    }

    public void setUploadedFilecnss(UploadedFile uploadedFilecnss) {
        this.uploadedFilecnss = uploadedFilecnss;
    }

    public DocumentService getDocumentService() {
        return documentService;
    }

    public void setDocumentService(DocumentService documentService) {
        this.documentService = documentService;
    }

    public static String getChemin() {
        return chemin;
    }

    public static void setChemin(String chemin) {
        SalarieMb.chemin = chemin;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public void setParametrageService(ParametrageService parametrageService) {
        this.parametrageService = parametrageService;
    }

    public int getNationalite() {
        return nationalite;
    }

    public void setNationalite(int nationalite) {
        this.nationalite = nationalite;
    }

    public int getSituationFa() {
        return situationFa;
    }

    public void setSituationFa(int situationFa) {
        this.situationFa = situationFa;
    }

    public int getPays() {
        return pays;
    }

    public void setPays(int pays) {
        this.pays = pays;
    }

    public Boolean getDisable() {
        return disable;
    }

    public void setDisable(Boolean disable) {
        this.disable = disable;
    }

    public int getModepaiement() {
        return modepaiement;
    }

    public void setModepaiement(int modepaiement) {
        this.modepaiement = modepaiement;
    }

    public Salarie getAddSalarie() {
        return addSalarie;
    }

    public void setAddSalarie(Salarie addSalarie) {
        this.addSalarie = addSalarie;
    }

    public Integer getPointureID() {
        return pointureID;
    }

    public void setPointureID(Integer pointureID) {
        this.pointureID = pointureID;
    }

    public Integer getTailleID() {
        return tailleID;
    }

    public void setTailleID(Integer tailleID) {
        this.tailleID = tailleID;
    }

    public Integer getGiletID() {
        return giletID;
    }

    public void setGiletID(Integer giletID) {
        this.giletID = giletID;
    }

    public Integer getCasqueID() {
        return casqueID;
    }

    public void setCasqueID(Integer casqueID) {
        this.casqueID = casqueID;
    }

    public Integer getStatut() {
        return statut;
    }

    public void setStatut(Integer statut) {
        this.statut = statut;
    }

    public Integer getEtat() {
        return etat;
    }

    public void setEtat(Integer etat) {
        this.etat = etat;
    }

    public String getChantier() {
        return chantier;
    }

    public void setChantier(String chantier) {
        this.chantier = chantier;
    }

    public ChantierService getChantieService() {
        return chantieService;
    }

    public void setChantieService(ChantierService chantieService) {
        this.chantieService = chantieService;
    }

    public Salarie getFindSalarie() {
        return findSalarie;
    }

    public void setFindSalarie(Salarie findSalarie) {
        this.findSalarie = findSalarie;
    }

    public SalarieService getSalarieService() {
        return salarieService;
    }

    public void setSalarieService(SalarieService salarieService) {
        this.salarieService = salarieService;
    }

    public LazyDataModel<Salarie> getLazyModel() {
        return lazyModel;
    }

    public void setLazyModel(LazyDataModel<Salarie> lazyModel) {
        this.lazyModel = lazyModel;
    }

    private List<Salarie> salaries;

    private List<Salarie> salaries_actif;

    Constantes constantes = Constantes.getInstance();

//    SalarieManager salarieManager = new SalarieManagerImpl();
    public List<Salarie> getSalaries() {
        return salaries;
    }

    public void setSalaries(List<Salarie> salaries) {
        this.salaries = salaries;
    }
    private Integer page;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getI() {
        return i;
    }

    public void setI(Integer i) {
        this.i = i;
    }

    public Boolean getPrev() {
        return prev;
    }

    public void setPrev(Boolean prev) {
        this.prev = prev;
    }

    public Boolean getNext() {
        return next;
    }

    public void setNext(Boolean next) {
        this.next = next;
    }

    public Boolean getLast() {
        return last;
    }

    public void setLast(Boolean last) {
        this.last = last;
    }

    public Boolean getFirst() {
        return first;
    }

    public List<Salarie> getSalaries_actif() {
        return salaries_actif;
    }

    public void setSalaries_actif(List<Salarie> salaries_actif) {
        this.salaries_actif = salaries_actif;
    }

    public void setFirst(Boolean first) {
        this.first = first;
    }

    public Boolean getPageId() {
        return pageId;
    }

    public void setPageId(Boolean pageId) {
        this.pageId = pageId;
    }

    /**
     * *********
     * Methodes
     */
    @PostConstruct
    public void init() {
        System.out.println("___faces ___ " + FacesContext.getCurrentInstance().toString());
        salarieService = Module.ctx.getBean(SalarieService.class);
        chantieService = Module.ctx.getBean(ChantierService.class);
        zoneServices = Module.ctx.getBean(ZoneServices.class);
        parametrageService = Module.ctx.getBean(ParametrageService.class);
        documentService = Module.ctx.getBean(DocumentService.class);
        mensuelService = Module.ctx.getBean(MensuelService.class);
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        Evol_ChantierMb evol_chantierMb = (Evol_ChantierMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "evol_chantierMb");
        chantiers = evol_chantierMb.getChantiers();
        /**
         * var le nombre de page ; i nombre de salarie , page => current page
         */
        page = 1;
        i = Integer.parseInt(salarieService.nombreSalaries("", null, null, null, "", "", "", "", "", "") + "");
        var = (i % 10 == 0) ? i / 10 : i / 10 + 1;
        if (Objects.equals(page, var)) {
            last = true;
            pageId = true;
            first = false;
            next = true;
            prev = false;
        } else {
            onFirst();
        }
        
        try {
             salaries = SalariePagination.first(findSalarie.getMatricule(), statut, fonction, etat, findSalarie.getCin(), findSalarie.getNom(), findSalarie.getPrenom(), findSalarie.getCnss(), chantier, findSalarie.getMatriculeDivalto());
       
        } catch (Exception e) {
            System.out.println("Erreur de chargement la liste Salarier car "+e.getMessage());
        }
        try {
            fonctionMb = (Evol_FonctionMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "evol_fonctionMb");
        
        } catch (Exception e) {
            System.out.println("Erreur de chargement la liste Evol_FonctionMb car "+e.getMessage());
        }
        try {
            fonctions = fonctionMb.getFonctions();
        } catch (Exception e) {
            System.out.println("Erreur de chargement la liste fonctions car "+e.getMessage());
        }
        
       

     

    }
    
    public void deleteSalarie(Salarie s){
        try {
            salarieService.supprimerSalarie(s);
        } catch (Exception e) {
        }
    }

    private Integer i;

    public void recherche() {
        System.out.println("fonction ID " + fonction);
        if ("".equals(findSalarie.getMatricule().trim()) && "".equals(findSalarie.getCin().trim()) && "".equals(findSalarie.getNom().trim()) && "".equals(findSalarie.getPrenom().trim())
                && "".equals(findSalarie.getCnss().trim()) && "".equals(findSalarie.getMatriculeDivalto().trim()) && statut == null && fonction == null
                && etat == null && chantier == null) {
            System.out.println("tt les controles Null pour la recherche : first page");
            i = Integer.parseInt(salarieService.nombreSalaries(findSalarie.getMatricule(), statut, fonction, etat, findSalarie.getCin(), findSalarie.getNom(), findSalarie.getPrenom(), findSalarie.getCnss(), chantier, findSalarie.getMatriculeDivalto()) + "");
        }
        salarieService = Module.ctx.getBean(SalarieService.class);
        i = Integer.parseInt(salarieService.nombreSalaries(findSalarie.getMatricule(), statut, fonction, etat, findSalarie.getCin(), findSalarie.getNom(), findSalarie.getPrenom(), findSalarie.getCnss(), chantier, findSalarie.getMatriculeDivalto()) + "");
        page = 1;
        var = (i % 10 == 0) ? i / 10 : i / 10 + 1;
        if (var == 0 || var == 1) {
            var = 1;
            last = true;
            pageId = true;
            first = true;
            next = true;
            prev = true;
        } else {
            last = false;
            pageId = false;
            first = true;
            next = false;
            prev = true;
        }
        //System.out.println("nombre salarie :  " + i);
        salaries = salarieService.listeSalaries(0, 10, findSalarie.getMatricule(), statut, fonction, etat, findSalarie.getCin(), findSalarie.getNom(), findSalarie.getPrenom(), findSalarie.getCnss(), chantier, findSalarie.getMatriculeDivalto());
        findSalarie = new Salarie();
//        etat = null;
//        statut = null;
//        fonction = null;
//        chantier = null;
        System.out.println("liste salarie recherche:  " + salaries.size());
    }

    public void fonctionByStatut() {
        if (idStatut != null) {
            fonctions = fonctionMb.foncByStatut(idStatut);
        } else {
            fonctions = fonctionMb.getFonctions();
        }
    }

    public void onPaginate() {
        salaries.clear();
        salaries = SalariePagination.page(page, findSalarie.getMatricule(), statut, fonction, etat, findSalarie.getCin(), findSalarie.getNom(), findSalarie.getPrenom(), findSalarie.getCnss(), chantier, findSalarie.getMatriculeDivalto());
        if (page == var) {
            last = true;
            pageId = true;
            first = false;
            next = true;
            prev = false;
        } else if (page == 1) {
            last = false;
            pageId = false;
            first = true;
            next = false;
            prev = true;
        } else {
            last = false;
            pageId = false;
            first = false;
            next = false;
            prev = false;
        }
    }

    public void onNext() {
        salaries.clear();
        page += 1;
        salaries = SalariePagination.page(page, findSalarie.getMatricule(), statut, fonction, etat, findSalarie.getCin(), findSalarie.getNom(), findSalarie.getPrenom(), findSalarie.getCnss(), chantier, findSalarie.getMatriculeDivalto());

        last = false;
        prev = false;
        next = false;
        first = false;

        if (page.equals(var)) {
            last = true;
            pageId = false;
            first = false;
            next = true;
            prev = false;
        }
    }

    public void onPrevious() {
        salaries.clear();
        page -= 1;

        salaries = SalariePagination.page(page, findSalarie.getMatricule(), statut, fonction, etat, findSalarie.getCin(), findSalarie.getNom(), findSalarie.getPrenom(), findSalarie.getCnss(), chantier, findSalarie.getMatriculeDivalto());

        if (page == 1) {
            last = false;
            pageId = false;
            first = true;
            next = false;
            prev = true;
        } else {
            last = false;
            pageId = false;
            first = false;
            next = false;
            prev = false;
        }
    }

    public void onFirst() {
        page = 1;
        salaries = SalariePagination.page(page, findSalarie.getMatricule(), statut, fonction, etat, findSalarie.getCin(), findSalarie.getNom(), findSalarie.getPrenom(), findSalarie.getCnss(), chantier, findSalarie.getMatriculeDivalto());

        last = false;
        pageId = false;
        first = true;
        next = false;
        prev = true;

    }

    public void onLast() {
        page = (i % 10 == 0) ? i / 10 : i / 10 + 1;
        salaries = SalariePagination.page(page, findSalarie.getMatricule(), statut, fonction, etat, findSalarie.getCin(), findSalarie.getNom(), findSalarie.getPrenom(), findSalarie.getCnss(), chantier, findSalarie.getMatriculeDivalto());
        last = true;
        pageId = true;
        first = false;
        next = true;
        prev = false;
    }

    public void changeModePaiment() {
        ModePaiement MP = parametrageService.getModePaiement(modepaiement);
        disable = !MP.getModePaiement().equals("Virement");
    }

    /**
     * Partie Evols
     */
    public void listeZoneByChantier() {
        zones = zoneServices.findByChantierID(idChantier);
    }

    public void affectSalarietoZone() {
        Zone z = new Zone();
        if (zoneServices.affectZoneToSalarie(z, salaroeToAffect)) {
            Module.message(0, "Info", "Affectation effectuée");
        } else {
            Module.message(2, "Erreur", "Echec d'affectation");
        }
    }

    public void rechercheSalarie() {
        i = Integer.parseInt(salarieService.nombreSalaries(findSalarie.getMatricule(), statut, Constante.FONCTION_ID_CHEF_EQUIPE, etat, findSalarie.getCin(), findSalarie.getNom(), findSalarie.getPrenom(), findSalarie.getCnss(), chantier, findSalarie.getMatriculeDivalto()) + "");
        if (page != 1 && page <= (i / 10)) {
            page *= 10;
        }
        salarieChefEquipe = salarieService.listeSalaries(page, page + 10, findSalarie.getMatricule(), statut, Constante.FONCTION_ID_CHEF_EQUIPE, etat, findSalarie.getCin(), findSalarie.getNom(), findSalarie.getPrenom(), findSalarie.getCnss(), chantier, findSalarie.getMatriculeDivalto());
    }

    /**
     * navigate to details salarie with view
     *
     * @param salarieId
     */
    public String navigateToDetailSalarie(int salarieId) {
        return "/evol/detail.xhtml?faces-redirect=true&salarieId=" + salarieId;
    }

//    public void chantiersOf() {
//        chantiersBySalarie = chantieService.listeChantiersAffectes(salaroeToAffect.getId(), 1, 0, Integer.parseInt(chantieService.nombreChantiers("", "", Module.dos).toString()), "", "", Module.dos);
//    }
}
