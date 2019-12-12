/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.mb;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPage;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.ArrayDataModel;
import javax.faces.model.DataModel;
import ma.bservice.tgcc.Constante.Constante;
import ma.bservices.beans.AffectationSalarieSupp;
import ma.bservices.beans.Chantier;
import ma.bservices.beans.Salarie;
import ma.bservices.beans.Zone;
import ma.bservices.beans.FichePointageLot;
import ma.bservices.beans.Fonction;
import ma.bservices.beans.Lot;
import ma.bservices.constantes.Constantes;
import ma.bservices.mb.services.ConstanteMb;
import ma.bservices.mb.services.Evol_FonctionMb;
import ma.bservices.mb.services.Module;
import ma.bservices.mb.services.SalarieServMb;
import ma.bservices.services.AffectationSSuppService;
import ma.bservices.services.ChantierService;
import ma.bservices.services.DocumentService;
import ma.bservices.services.Evol_LotService;
import ma.bservices.services.ParametrageService;
import ma.bservices.services.PresenceService;
import ma.bservices.services.SalarieService;
import ma.bservices.services.SalarieServiceIn;
import ma.bservices.services.ZoneServices;
import ma.bservices.services.FichePLService;
import ma.bservices.services.OrganigrameService;
import ma.bservices.tgcc.Entity.Mensuel;
import ma.bservices.tgcc.Entity.Organigrame;
import ma.bservices.tgcc.authentification.Authentification;
import ma.bservices.tgcc.service.Mensuel.MensuelService;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import static org.castor.mapping.AbstractMappingLoaderFactory.LOG;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.data.PageEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.springframework.stereotype.Component;

/**
 *
 * @author j.allali
 */
@Component
@ManagedBean(name = "affectSalarieMb")
@ViewScoped
public class AffectSalarieSupp implements Serializable {

    @ManagedProperty(value = "#{salarieService}")
    private SalarieService salarieService;

    @ManagedProperty(value = "#{salarieServiceIn}")
    private SalarieServiceIn salarieServiceIn;

    @ManagedProperty(value = "#{mensuleServiceIn}")
    private MensuelService mensuelService;

    @ManagedProperty(value = "#{organigrameService}")
    private OrganigrameService organigramService;

    @ManagedProperty(value = "#{affectationSSuppService}")
    private AffectationSSuppService affectationSSuppService;

    @ManagedProperty(value = "#{chantierServiceEvol}")
    private ChantierService chantieService;
    private List<Chantier> nonAffectChantier;

    @ManagedProperty(value = "#{parametrageService}")
    private ParametrageService parametrageService;

    @ManagedProperty(value = "#{zoneService}")
    private ZoneServices zoneServices;

    @ManagedProperty(value = "#{presenceService}")
    private PresenceService presenceService;

    @ManagedProperty(value = "#{documentServiceEvol}")
    private DocumentService documentService;

    @ManagedProperty(value = "#{lotServiceEvol}")
    private Evol_LotService lotService;

    @ManagedProperty(value = "#{fichePLService}")
    private FichePLService fichePLService;

    private Boolean disable = true;
    private List<Salarie> salarieToAffect = new ArrayList<>();

    private List<Salarie> salarieChefByChantier = new ArrayList<>();

    private List<Salarie> salarieChefByChantierAffect = new ArrayList<>();

    private List<Salarie> selectSlarieToAffect = new ArrayList<>();

    private List<AffectationSalarieSupp> salarieAffected = new ArrayList<>();

    private UploadedFile importFile;

    private Salarie findSalarie = new Salarie();
    private List<Fonction> fonctions = new LinkedList<>();
    private Integer codeChantier, page, i, var, idSalarieSupp, idSalarieSuppUpdate, etat, idType, idFonction;

    private Chantier c = new Chantier();

    private String chefSup;

    private AffectationSalarieSupp salarieToUpdate = new AffectationSalarieSupp();

    private DataModel<AffectationSalarieSupp> salarieList;
//    private List result = new LinkedList<>();
    private String chantier = "";
    int nbr = 0;
    int a = 0;
    List<Chantier> chantierBySalarie = new ArrayList<>();
    int rowIndex;
    private Boolean hasSup;
    private Integer idChantierSearch;
    private Integer idSuppSearch;
    private Date dateSearch, dateSearchEnd;
    private Date date = new Date();
    private SalarieServMb salarieMb;
    private Evol_FonctionMb fonctionMb;
    private String idStatut;

    private StreamedContent file_Excel;
    /**
     * chemin de la fiche du pointage après la génération de la fiche
     */
    private String cheminFiche;

    public List<Fonction> getFonctions() {
        return fonctions;
    }

    public MensuelService getMensuelService() {
        return mensuelService;
    }

    public void setMensuelService(MensuelService mensuelService) {
        this.mensuelService = mensuelService;
    }

    public void setFonctions(List<Fonction> fonctions) {
        this.fonctions = fonctions;
    }

    public String getIdStatut() {
        return idStatut;
    }

    public void setIdStatut(String idStatut) {
        this.idStatut = idStatut;
    }

    public StreamedContent getFile_Excel() {
        return file_Excel;
    }

    public void setFile_Excel(StreamedContent file_Excel) {
        this.file_Excel = file_Excel;
    }

    public Boolean getDisable() {
        return disable;
    }

    public void setDisable(Boolean disable) {
        this.disable = disable;
    }

    public SalarieServMb getSalarieMb() {
        return salarieMb;
    }

    public void setSalarieMb(SalarieServMb salarieMb) {
        this.salarieMb = salarieMb;
    }

    public String getCheminFiche() {
        return cheminFiche;
    }

    public void setCheminFiche(String cheminFiche) {
        this.cheminFiche = cheminFiche;
    }

    public Evol_LotService getLotService() {
        return lotService;
    }

    public void setLotService(Evol_LotService lotService) {
        this.lotService = lotService;
    }

    public OrganigrameService getOrganigramService() {
        return organigramService;
    }

    public void setOrganigramService(OrganigrameService organigramService) {
        this.organigramService = organigramService;
    }

    public Integer getIdType() {
        return idType;
    }

    public void setIdType(Integer idType) {
        this.idType = idType;
    }

    public Integer getIdFonction() {
        return idFonction;
    }

    public void setIdFonction(Integer idFonction) {
        this.idFonction = idFonction;
    }

    public UploadedFile getImportFile() {
        return importFile;
    }

    public void setImportFile(UploadedFile importFile) {
        this.importFile = importFile;
    }

    public AffectationSSuppService getAffectationSSuppService() {
        return affectationSSuppService;
    }

    public void setAffectationSSuppService(AffectationSSuppService affectationSSuppService) {
        this.affectationSSuppService = affectationSSuppService;
    }

    public FichePLService getFichePLService() {
        return fichePLService;
    }

    public void setFichePLService(FichePLService fichePLService) {
        this.fichePLService = fichePLService;
    }

    public PresenceService getPresenceService() {
        return presenceService;
    }

    public void setPresenceService(PresenceService presenceService) {
        this.presenceService = presenceService;
    }

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public DataModel<AffectationSalarieSupp> getSalarieList() {
        return salarieList;
    }

    public void setSalarieList(DataModel<AffectationSalarieSupp> salarieList) {
        this.salarieList = salarieList;
    }

    public AffectationSalarieSupp getSalarieToUpdate() {
        return salarieToUpdate;
    }

    public void setSalarieToUpdate(AffectationSalarieSupp salarieToUpdate) {
        this.salarieToUpdate = salarieToUpdate;
    }

    public String getChefSup() {
        return chefSup;
    }

    public void setChefSup(String chefSup) {
        this.chefSup = chefSup;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    public Salarie getFindSalarie() {
        return findSalarie;
    }

    public void setFindSalarie(Salarie findSalarie) {
        this.findSalarie = findSalarie;
    }

    public Integer getIdChantierSearch() {
        return idChantierSearch;
    }

    public void setIdChantierSearch(Integer idChantierSearch) {
        this.idChantierSearch = idChantierSearch;
    }

    public Integer getIdSuppSearch() {
        return idSuppSearch;
    }

    public void setIdSuppSearch(Integer idSuppSearch) {
        this.idSuppSearch = idSuppSearch;
    }

    public Date getDateSearch() {
        return dateSearch;
    }

    public void setDateSearch(Date dateSearch) {
        this.dateSearch = dateSearch;
    }

    public List<Chantier> getChantierBySalarie() {
        return chantierBySalarie;
    }

    public void setChantierBySalarie(List<Chantier> chantierBySalarie) {
        this.chantierBySalarie = chantierBySalarie;
    }

    public List<Salarie> getSalarieChefByChantierAffect() {
        return salarieChefByChantierAffect;
    }

    public void setSalarieChefByChantierAffect(List<Salarie> salarieChefByChantierAffect) {
        this.salarieChefByChantierAffect = salarieChefByChantierAffect;
    }

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public int getNbr() {
        return nbr;
    }

    public void setNbr(int nbr) {
        this.nbr = nbr;
    }

    public Integer getIdSalarieSuppUpdate() {
        return idSalarieSuppUpdate;
    }

    public void setIdSalarieSuppUpdate(Integer idSalarieSuppUpdate) {
        this.idSalarieSuppUpdate = idSalarieSuppUpdate;
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

    public List<AffectationSalarieSupp> getSalarieAffected() {
        return salarieAffected;
    }

    public void setSalarieAffected(List<AffectationSalarieSupp> salarieAffected) {
        this.salarieAffected = salarieAffected;
    }

    public Integer getIdSalarieSupp() {
        return idSalarieSupp;
    }

    public void setIdSalarieSupp(Integer idSalarieSupp) {
        this.idSalarieSupp = idSalarieSupp;
    }

    public List<Salarie> getSelectSlarieToAffect() {
        return selectSlarieToAffect;
    }

    public void setSelectSlarieToAffect(List<Salarie> selectSlarieToAffect) {
        this.selectSlarieToAffect = selectSlarieToAffect;
    }

    public Chantier getC() {
        return c;
    }

    public void setC(Chantier c) {
        this.c = c;
    }

    public List<Salarie> getSalarieChefByChantier() {
        return salarieChefByChantier;
    }

    public void setSalarieChefByChantier(List<Salarie> salarieChefByChantier) {
        this.salarieChefByChantier = salarieChefByChantier;
    }

    public Integer getVar() {
        return var;
    }

    public void setVar(Integer var) {
        this.var = var;
    }

    public Integer getCodeChantier() {
        return codeChantier;
    }

    public void setCodeChantier(Integer codeChantier) {
        this.codeChantier = codeChantier;
    }

    public List<Salarie> getSalarieToAffect() {
        return salarieToAffect;
    }

    public void setSalarieToAffect(List<Salarie> salarieToAffect) {
        this.salarieToAffect = salarieToAffect;
    }

    public SalarieService getSalarieService() {
        return salarieService;
    }

    public void setSalarieService(SalarieService salarieService) {
        this.salarieService = salarieService;
    }

    public SalarieServiceIn getSalarieServiceIn() {
        return salarieServiceIn;
    }

    public void setSalarieServiceIn(SalarieServiceIn salarieServiceIn) {
        this.salarieServiceIn = salarieServiceIn;
    }

    public ChantierService getChantieService() {
        return chantieService;
    }

    public void setChantieService(ChantierService chantieService) {
        this.chantieService = chantieService;
    }

    public List<Chantier> getNonAffectChantier() {
        return nonAffectChantier;
    }

    public void setNonAffectChantier(List<Chantier> nonAffectChantier) {
        this.nonAffectChantier = nonAffectChantier;
    }

    public ParametrageService getParametrageService() {
        return parametrageService;
    }

    public void setParametrageService(ParametrageService parametrageService) {
        this.parametrageService = parametrageService;
    }

    public DocumentService getDocumentService() {
        return documentService;
    }

    public void setDocumentService(DocumentService documentService) {
        this.documentService = documentService;
    }

    public ZoneServices getZoneServices() {
        return zoneServices;
    }

    public void setZoneServices(ZoneServices zoneServices) {
        this.zoneServices = zoneServices;
    }

    public Boolean getHasSup() {
        return hasSup;
    }

    public void setHasSup(Boolean hasSup) {
        this.hasSup = hasSup;
    }

    public Date getDateSearchEnd() {
        return dateSearchEnd;
    }

    public void setDateSearchEnd(Date dateSearchEnd) {
        this.dateSearchEnd = dateSearchEnd;
    }

    public Evol_FonctionMb getFonctionMb() {
        return fonctionMb;
    }

    public void setFonctionMb(Evol_FonctionMb fonctionMb) {
        this.fonctionMb = fonctionMb;
    }

    @PostConstruct
    public void init() {
        page = 1;
        chantieService = Module.ctx.getBean(ChantierService.class);
        salarieServiceIn = Module.ctx.getBean(SalarieServiceIn.class);
        mensuelService = Module.ctx.getBean(MensuelService.class);
        affectationSSuppService = Module.ctx.getBean(AffectationSSuppService.class);

        /*
        salarieAffected = affectationSSuppService.listSalarieBySupp();
        for (AffectationSalarieSupp sal : salarieAffected) {
            if (sal.getSalaries() != null && sal.getChefEquipe() != null) {
                sal.getSalaries().setChefEquipe(sal.getChefEquipe().getNom() + " " + sal.getChefEquipe().getPrenom());
            }
        }
        if (salarieAffected != null) {
            salarieList = new ArrayDataModel(salarieAffected.toArray());
        }
         */
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        salarieMb = (SalarieServMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "salarieServMb");
        //salarieChefByChantierAffect = salarieMb.getListCE();
        //  salarieChefByChantierAffect = salarieService.listeSalaries(0, 10, "", null, Constante.FONCTION_ID_CHEF_EQUIPE, null, "", "", "", "", codeChantier.toString(), "");

        /*
        fonctionMb = (Evol_FonctionMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "evol_fonctionMb");
        fonctions = fonctionMb.getFonctions();
         */
    }

    public void fonctionByStatut() {
        if (idStatut != null) {
            fonctions = fonctionMb.foncByStatut(idStatut);
        } else {
            fonctions = fonctionMb.getFonctions();
        }
    }

    public void update(PageEvent event) {
        //int var = event.getPage();

        //nbr = selectSlarieToAffect.size() - a;
        if (!selectSlarieToAffect.isEmpty()) {
            Module.message(1, "Vous avez choisi : " + selectSlarieToAffect.size() + " Salarie pour les affecter ", "");
        }
    }

    /**
     * recuperer le dernier superieur d'un salarie
     *
     * @param sal
     */
    public void superieurOf(Salarie sal) {
        AffectationSalarieSupp sup = affectationSSuppService.getLastSuperiorBySalarieAndChantier(sal, codeChantier);

        sal.setHasSup(sup != null);
        sal.setChefEquipe((sup != null && sup.getChefEquipe() != null) ? sup.getChefEquipe().getNom() + " " + sup.getChefEquipe().getPrenom() : "---- ----");
    }

    public void desaffecter(AffectationSalarieSupp sal) {
        try {
            sal.setCurrentSupp(Boolean.FALSE);
            affectationSSuppService.updateSalarie(sal);
            salarieAffected = affectationSSuppService.listSalarieBySupp();
            for (AffectationSalarieSupp sale : salarieAffected) {
                sale.getSalaries().setChefEquipe(sale.getChefEquipe().getNom() + " " + sale.getChefEquipe().getPrenom());
            }
            if (salarieAffected != null) {
                salarieList = new ArrayDataModel(salarieAffected.toArray());
            }
            Module.message(0, "Désaffectation effectuée", "");
        } catch (Exception ex) {
            Module.message(2, "Erreur désaffectation", "");
            System.out.println("Erreur désaffectation salarié supérieur : " + ex.getMessage());
        }
    }

    /**
     * Creates a new instance of AffectSalarieSupp
     */
    public AffectSalarieSupp() {
    }

    /**
     * methode pour recupere list des chefs d'equipe par chantier
     */
    public void getListeChefEquipeByChantier() {
        if (idChantierSearch != null) {
//            int j = Integer.parseInt(salarieService.nombreSalaries("", null, Constante.FONCTION_ID_CHEF_EQUIPE, null, "", "", "", "", idChantierSearch.toString(), "") + "");
//            List<Salarie> listActChef = salarieService.listeSalaries(0, j, "", null, Constante.FONCTION_ID_CHEF_EQUIPE, Constante.Etat_ID_ACTIF, "", "", "", "", idChantierSearch.toString(), "");
//            List<Salarie> listAPChef = salarieService.listeSalaries(0, j, "", null, Constante.FONCTION_ID_CHEF_EQUIPE, Constante.Etat_ID_ACTIF_PROVISOIR, "", "", "", "", idChantierSearch.toString(), "");
//            salarieChefByChantierAffect = new LinkedList<>();
//            salarieChefByChantierAffect.addAll(listActChef);
//            salarieChefByChantierAffect.addAll(listAPChef);
//            //salarieChefByChantierAffect = result;

            // traitement organigrame :: ajoute les salaries marqués chefs d'équipe au niveau de l'organigrame a la liste des chefs d'équipe
            Chantier chantierOrg = chantieService.getChantier(idChantierSearch);
            List<Organigrame> orgs = organigramService.findByChantierChef(chantierOrg);
            salarieChefByChantierAffect = new LinkedList<>();
            if (orgs == null) {
                salarieChefByChantierAffect = new LinkedList<>();
            } else {
                for (Organigrame org : orgs) {
                    System.out.println("CHANTIER : " + chantierOrg.getCode());
                    System.out.println("CHEF : " + org.getSalarie().getNom());
                    if (!salarieChefByChantierAffect.contains(org.getSalarie())) {
                        salarieChefByChantierAffect.add(org.getSalarie());
                    }
                }
            }
        }

        disable = false;

    }

    public void salarieByChantiersAffect() {
        /**
         * List Salarie Actif + AP+En cours 
         */

        if (salarieToAffect != null) {
            salarieToAffect.clear();
        }

        salarieToAffect = salarieServiceIn.getActifSalarie(codeChantier);
        salarieToAffect.addAll(mensuelService.actifMensuelByChantier(codeChantier));
        //idType
        System.out.println("=============>"+salarieServiceIn.getSalarieHearchChef(codeChantier, null));
        //  for (Salarie sal : salarieToAffect) {
        //    superieurOf(sal);
        // }
       
        /**
         * List Chef d'equipe On a passer id de la fonction chef d'equipe '268'
         * et le chantier choisi ont un staut Actif + AP+En cours
         */
     
        List<Salarie> listActChef = salarieService.listeAllSalaries(0, -1, "", Constante.FONCTION_ID_CHEF_EQUIPE, Constante.Etat_ID_ACTIF, "", "", "", "", codeChantier.toString(), "");
        //System.out.println("List Act Chef :" + listActChef.size());

        List<Salarie> listAPChef = salarieService.listeAllSalaries(0, -1, "", Constante.FONCTION_ID_CHEF_EQUIPE, Constante.Etat_ID_ACTIF_PROVISOIR, "", "", "", "", codeChantier.toString(), "");
       // System.out.println("List AP Chef :" + listAPChef.size());

        List<Mensuel> lMensuelChefEquipe = salarieServiceIn.getMensuelChefEquipeInChantier(codeChantier);

        salarieChefByChantierAffect = new LinkedList<>();

        salarieChefByChantierAffect.addAll(listActChef);
        salarieChefByChantierAffect.addAll(listAPChef);
        salarieChefByChantierAffect.addAll(lMensuelChefEquipe);

        // traitement organigrame :: ajoute les salaries marqués chefs d'équipe au niveau de l'organigrame a la liste des chefs d'équipe
        Chantier chantierOrg = chantieService.getChantier(codeChantier);
        List<Organigrame> orgs = organigramService.findByChantierChef(chantierOrg);
        if (orgs != null) {
            for (Organigrame org : orgs) {
                //System.out.println("CHANTIER : " + chantierOrg.getCode());
                //System.out.println("CHEF : " + org.getSalarie().getNom());
                if (!salarieChefByChantierAffect.contains(org.getSalarie())) {
                    salarieChefByChantierAffect.add(org.getSalarie());
                }
            }
        }
        disable = false;
    }
    

    public void salarieByChantiersAffect_old() {
        /**
         * List Salarie Actif + AP+En cours 
         */

        //i = Integer.parseInt(salarieService.nombreSalaries("", null, null, null, "", "", "", "", codeChantier.toString(), "") + "");
        //List<Salarie> listAct = salarieServiceIn.getSalarieNotChef(codeChantier, Constante.Etat_ID_ACTIF);
        //List<Salarie> listAP = salarieServiceIn.getSalarieNotChef(codeChantier, Constante.Etat_ID_ACTIF_PROVISOIR);
        //List<Salarie> listEC = salarieServiceIn.getSalarieNotChef(codeChantier, Constante.Etat_ID_En_Cours);
        if (salarieToAffect != null) {
            salarieToAffect.clear();
        }

        salarieToAffect = salarieServiceIn.getActifSalarie(codeChantier);
        salarieToAffect.addAll(mensuelService.actifMensuelByChantier(codeChantier));
        /*
        if (listAct != null) {
            salarieToAffect.addAll(listAct);
        }
        if (listAP != null) {
            salarieToAffect.addAll(listAP);
        }
        
        if (listEC != null) {
            salarieToAffect.addAll(listEC);
        }
         */

        //  for (Salarie sal : salarieToAffect) {
        //    superieurOf(sal);
        //  }
        /**
         * List Chef d'equipe On a passer id de la fonction chef d'equipe '268'
         * et le chantier choisi ont un staut Actif + AP+En cours
         */
        //    int j = Integer.parseInt(salarieService.nombreAllSalaries("", Constante.FONCTION_ID_CHEF_EQUIPE, null, "", "", "", "", codeChantier.toString(), "") + "");
        List<Salarie> listActChef = salarieService.listeAllSalaries(0, -1, "", Constante.FONCTION_ID_CHEF_EQUIPE, Constante.Etat_ID_ACTIF, "", "", "", "", codeChantier.toString(), "");
        //System.out.println("List Act Chef :" + listActChef.size());

        List<Salarie> listAPChef = salarieService.listeAllSalaries(0, -1, "", Constante.FONCTION_ID_CHEF_EQUIPE, Constante.Etat_ID_ACTIF_PROVISOIR, "", "", "", "", codeChantier.toString(), "");
        //System.out.println("List AP Chef :" + listAPChef.size());

        List<Mensuel> lMensuelChefEquipe = salarieServiceIn.getMensuelChefEquipeInChantier(codeChantier);

        salarieChefByChantierAffect = new LinkedList<>();

        salarieChefByChantierAffect.addAll(listActChef);
        salarieChefByChantierAffect.addAll(listAPChef);
        salarieChefByChantierAffect.addAll(lMensuelChefEquipe);

        // traitement organigrame :: ajoute les salaries marqués chefs d'équipe au niveau de l'organigrame a la liste des chefs d'équipe
        Chantier chantierOrg = chantieService.getChantier(codeChantier);
        List<Organigrame> orgs = organigramService.findByChantierChef(chantierOrg);
        //    salarieChefByChantierAffect = new LinkedList<>();
        if (orgs != null) {
            //    salarieChefByChantierAffect = new LinkedList<>();
            for (Organigrame org : orgs) {
                //System.out.println("CHANTIER : " + chantierOrg.getCode());
                //System.out.println("CHEF : " + org.getSalarie().getNom());
                if (!salarieChefByChantierAffect.contains(org.getSalarie())) {
                    salarieChefByChantierAffect.add(org.getSalarie());
                }
            }
        }
        disable = false;
    }

    public void createExcel(String s) {
        try {
            String filename = "C:/ErreurFichier.xls";
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("Erreur");

            HSSFRow rowhead = sheet.createRow((short) 0);
            rowhead.createCell(0).setCellValue("Erreur");

            HSSFRow row = sheet.createRow((short) 1);
            row.createCell(0).setCellValue(s);

            ByteArrayOutputStream output = new ByteArrayOutputStream();
            workbook.write(output);
            byte[] bytes = output.toByteArray();
            InputStream inputEx2 = new ByteArrayInputStream(bytes);
            file_Excel = new DefaultStreamedContent(inputEx2, "application/vnd.ms-excel", "Erreur_Affectation.xls");

            System.out.println("Fichier d'erreur genéré");

        } catch (Exception ex) {
            System.out.println(ex);
        }

    }

    /**
     * Pour l'impres de la fiche pointage lot on doit choisir un chantier et un
     * chef de projet
     */
    public void salarieByChantiersAffectFiche() {

        i = Integer.parseInt(salarieService.nombreSalaries("", null, null, null, "", "", "", "", codeChantier.toString(), "") + "");
        List<Salarie> listAct = salarieServiceIn.getSalarieNotChef(codeChantier, 1);
        List<Salarie> listAP = salarieServiceIn.getSalarieNotChef(codeChantier, 4);
        List<Salarie> listEC = salarieServiceIn.getSalarieNotChef(codeChantier, 5);

        if (listAct != null) {
            salarieToAffect.addAll(listAct);
        }
        if (listAP != null) {
            salarieToAffect.addAll(listAP);
        }
        if (listEC != null) {
            salarieToAffect.addAll(listEC);
        }
        /**
         * List Chef d'equipe On a passer id de la fonction chef d'equipe '268'
         * et le chantier choisi
         */
//        int j = Integer.parseInt(salarieService.nombreSalaries("", null, Constante.FONCTION_ID_CHEF_EQUIPE, null, "", "", "", "", codeChantier.toString(), "") + "");
//        salarieChefByChantierAffect = salarieService.listeSalaries(0, j, "", null, Constante.FONCTION_ID_CHEF_EQUIPE, null, "", "", "", "", codeChantier.toString(), "");
//        for (Salarie s : salarieChefByChantierAffect) {
//            System.out.println("SALARIE CHEF DE CHANTIER : " + s.getNom());
//        }

        // traitement organigrame :: ajoute les salaries marqués chefs d'équipe au niveau de l'organigrame a la liste des chefs d'équipe
        Chantier chantierOrg = chantieService.getChantier(codeChantier);
        List<Organigrame> orgs = organigramService.findByChantierChef(chantierOrg);
        salarieChefByChantierAffect = new LinkedList<>();
        if (salarieChefByChantierAffect == null) {
            salarieChefByChantierAffect = new LinkedList<>();
        } else if (orgs != null) {
            for (Organigrame org : orgs) {
                //System.out.println("CHANTIER : " + chantierOrg.getCode());
                //System.out.println("CHEF : " + org.getSalarie().getNom());
                if (!salarieChefByChantierAffect.contains(org.getSalarie())) {
                    salarieChefByChantierAffect.add(org.getSalarie());
                }
            }
        }

        disable = false;
    }

    public void affectSalarieToSupp() {

        Chantier _chantier = chantieService.getChantierById(codeChantier);
        Salarie salarieSupp = salarieService.getSalarie(idSalarieSupp);

        if (selectSlarieToAffect.isEmpty()) {

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "", "il faut choisir au moins une salarie"));

        } else {
            int nbr = 0;
            for (Salarie s : selectSlarieToAffect) {
                AffectationSalarieSupp a1 = affectationSSuppService.listSalarieByChantier(idSalarieSupp, s.getId(), codeChantier);
                if (a1 != null) {
                    System.out.println("affectation existe");
                } else {
                    AffectationSalarieSupp a2 = affectationSSuppService.listSalarieByChantier(null, s.getId(), codeChantier);
                    if (a2 != null) {
                        a2.setCurrentSupp(false);
                        affectationSSuppService.updateSalarie(a2);
                        System.out.println("affectation existe with different chef");
                    }
                    AffectationSalarieSupp affec = new AffectationSalarieSupp();
                    affec.setChefEquipe(salarieSupp);
                    affec.setSalaries(s);
                    affec.setChantier(_chantier);
                    affec.setDateAffectatio(new Date());
                    affec.setCurrentSupp(true);
                    affectationSSuppService.affecterSupp(affec);
                    nbr++;
                    superieurOf(s);
                }
            }

            /**
             * Affichage du nombres des salaries sÃ©lÃ©ctionnÃ©s
             */
//            FacesMessage msg = new FacesMessage("Vous avez affecté :" + selectSlarieToAffect.size() + " salarié(s) au chef d'équipe :" + idSalarieSupp, "");
//            FacesContext.getCurrentInstance().addMessage(null, msg);
            Module.message(0, "Vous avez affecté :" + nbr + " salarié(s) au chef d'équipe :" + salarieSupp.getNom() + " " + salarieSupp.getPrenom(), "Matricule " + salarieSupp.getMatricule() + " - " + (selectSlarieToAffect.size() - nbr) + " déjà affectés");
            selectSlarieToAffect.clear();
        }
    }

    public void rechercheSalarie() {
        System.out.println("xxxxxx MATRICULE xxxxxxxxx " + findSalarie.getMatricule());
        salarieService = Module.ctx.getBean(SalarieService.class);
        if (idFonction != Constante.FONCTION_ID_CHEF_EQUIPE) {
            i = Integer.parseInt(salarieService.nombreAllSalaries(findSalarie.getMatricule(), idFonction, etat, findSalarie.getCin(), findSalarie.getNom(), findSalarie.getPrenom(), findSalarie.getCnss(), codeChantier.toString(), findSalarie.getMatriculeDivalto()) + "");
            List<Salarie> result = salarieService.listeAllSalaries(0, i, findSalarie.getMatricule(), idFonction, etat, findSalarie.getCin(), findSalarie.getNom(), findSalarie.getPrenom(), findSalarie.getCnss(), codeChantier.toString(), findSalarie.getMatriculeDivalto());
            salarieToAffect.clear();
            for (Salarie sal : result) {
                if (sal.getFonction().getId() != Constante.FONCTION_ID_CHEF_EQUIPE) {
                    superieurOf(sal);
                    salarieToAffect.add(sal);
                }
            }
        } else {
            Module.message(1, "vous ne pouvre pas filtrer par Chef d'équipe", "");
        }
    }

    public void reinitList() {

    }

    public List<Chantier> chatnierBySalarie(int idSalarie) {
        System.out.println(" &&&&&&&&&& ID SALARIE CONSULTATION &&&&&&&&& " + idSalarie);
        nbr = Integer.parseInt(chantieService.nombreChantiers("", "", Module.dos).toString());
        boolean isAdmin = false;
        if ("admin".equals(Constantes.getRoleAuth()) || "EMAIL_CONTRIBUTORS".equals(Constantes.getRoleAuth())) {
            isAdmin = true;
        }
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        Authentification authentification = (Authentification) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "authentification");
        Integer idUser = authentification.get_connected_user().getId();
        return chantieService.listeChantiersAffectes(idSalarie, 1, 0, nbr, "", "", Module.dos, isAdmin, idUser);
    }

    // recherche par chef d'équipe :: affectation salarie sup.xhtml
    public void searchByChef() {

    }

    public void updateSuppSalarie() {
        //System.out.println(" ggggggggggggg ID SALARIE ggggggggg " + salarieToUpdate.getId());
        //System.out.println(" hhhhhhhhhhhh ID SALARIE SUPP hhhhhhhhhhhhhh " + idSalarieSuppUpdate);
        //System.out.println(" cccccccccccc SALARIE TO UPDATE >>>>>>>>>>>> " + idSalarieSuppUpdate);
        if (!idSalarieSuppUpdate.equals(salarieToUpdate.getChefEquipe().getId())) {
            Salarie salarieToU = salarieService.getSalarie(idSalarieSuppUpdate);
            salarieToUpdate.setChefEquipe(salarieToU);
            affectationSSuppService.updateSalarie(salarieToUpdate);
            salarieAffected = affectationSSuppService.listSalarieBySupp();
            for (AffectationSalarieSupp sal : salarieAffected) {
                sal.getSalaries().setChefEquipe(sal.getChefEquipe().getNom() + " " + sal.getChefEquipe().getPrenom());
            }
            if (salarieAffected != null) {
                salarieList = new ArrayDataModel(salarieAffected.toArray());
            }
            Module.message(0, "Modification", "effectuée pour ce Salarié ");
        } else {
            Module.message(1, "Aucune modification ", "effectuée");
        }
    }

    public void onRowCancel(RowEditEvent event) {
        Module.message(1, "Annulation!", "Vous avez Annulé l'opération");
    }

    public List<Salarie> salarieBysuppAffected(Integer id) {
        int j = Integer.parseInt(salarieService.nombreSalaries("", null, Constante.FONCTION_ID_CHEF_EQUIPE, null, "", "", "", "", id.toString(), "") + "");
        salarieChefByChantier = salarieService.listeSalaries(0, j, "", null, Constante.FONCTION_ID_CHEF_EQUIPE, null, "", "", "", "", id.toString(), "");
        return salarieChefByChantier;
    }

    public class Rotate extends PdfPageEventHelper {

        @Override
        public void onStartPage(PdfWriter writer, Document document) {
            writer.addPageDictEntry(PdfName.ROTATE, PdfPage.PORTRAIT);
        }
    }

    public void onRowEditInit() {

        /**
         * Charger List Chantier Modifier By Salarie ID
         */
//        Salarie sla = salarieService.getSalarie(salarieToUpdate.getSalaries().getId());
        System.out.println("<<<<<<<<<>>>>>>>> TEST ENTRE 1 <<<<<<<<<< !!!!!!! >>>>>>>> " + salarieToUpdate.getId());
        idSalarieSuppUpdate = salarieToUpdate.getId();
        // Integer nbr1 = Integer.parseInt(chantieService.nombreChantiers("", "", Module.dos).toString());
        //chantierBySalarie = chantieService.listeChantiersAffectes(salarieToUpdate.getSalaries().getId(), 1, 0, nbr1, "", "", Module.dos);
        //List<Chantier> ch = chantieService.listeChantiersAffectes(salarieToUpdate.getChefEquipe().getId(), 1, 0, nbr1, "", "", Module.dos);
        System.out.println(" <<<<<!>>>>>> CHANTIER CONSULT <<<<<<<!>>>>>>> ");
        int j = Integer.parseInt(salarieService.nombreSalaries("", null, Constante.FONCTION_ID_CHEF_EQUIPE, null, "", "", "", "", salarieToUpdate.getChantier().getId().toString(), "") + "");
        salarieChefByChantier = salarieService.listeSalaries(0, j, "", null, Constante.FONCTION_ID_CHEF_EQUIPE, null, "", "", "", "", salarieToUpdate.getChantier().getId().toString(), "");
        System.out.println("<<<<<<<<<>>>>>>>> TEST ENTRE 2 FIN <<<<<<<<<< !!!!!!! >>>>>>>> " + salarieChefByChantier.size());
    }

    public void rechercheSalarieConsul() {

        System.out.println(" bbbbbbbbb FORMAT DATE bbbbbbbbb " + dateSearch);
        try {
            if (idSuppSearch != null || (dateSearch != null && dateSearchEnd != null) || idChantierSearch != null) {
                salarieAffected = affectationSSuppService.listSalarieByChantierDateSupp(idSuppSearch, dateSearch, dateSearchEnd, idChantierSearch);
                for (AffectationSalarieSupp sal : salarieAffected) {
                    sal.getSalaries().setChefEquipe(sal.getChefEquipe().getNom() + " " + sal.getChefEquipe().getPrenom());
                }
                salarieList = new ArrayDataModel(salarieAffected.toArray());
                System.out.println(" vvvvvvvvvv SIZE LIST SEARCH vvvvvvvvvv " + salarieAffected.size());
            } else {
                salarieAffected = affectationSSuppService.listSalarieBySupp();
                for (AffectationSalarieSupp sal : salarieAffected) {
                    sal.getSalaries().setChefEquipe(sal.getChefEquipe().getNom() + " " + sal.getChefEquipe().getPrenom());
                }
                if (salarieAffected != null) {
                    salarieList = new ArrayDataModel(salarieAffected.toArray());
                }
            }
        } catch (Exception ex) {
            System.out.println("Erreur recherche Salarie Consul : AffectSalarieSupp : " + ex.getMessage());
        }
    }

    public void Saveimporter() throws IOException {
//        String chemin = TgccFileManager.getCheminFichier("Fiche Pointage");
//        Path folder = Paths.get(chemin);
//        Files.createDirectories(folder);
//        String filename = FilenameUtils.getBaseName(importFile.getFileName());
        String extension = FilenameUtils.getExtension(importFile.getFileName());
        if (!"xls".equals(extension) && !"xlsx".equals(extension)) {
            Module.message(2, "Veuillez choisir un fichier excel (xls,xlsx) ", "");
            return;
        }
        //Path file = Files.createTempFile(folder, filename, "." + extension);
        int j = 0, er = 0;
        try (InputStream input = importFile.getInputstream()) {
            System.out.println("HELLO PARSING EXCEL");
            //Files.copy(input, file, StandardCopyOption.REPLACE_EXISTING);
            XSSFWorkbook workbook = new XSSFWorkbook(input);
            //Get first/desired sheet from the workbook
            XSSFSheet sheet = workbook.getSheetAt(0);
            //Iterate through each rows one by one
            Iterator<Row> rowIterator = sheet.iterator();
            boolean erreur;
            if (rowIterator.hasNext()) {
                rowIterator.next();
            }
            while (rowIterator.hasNext()) {
                erreur = false;
                Row row = rowIterator.next();
                //For each row, iterate through all the columns
                Iterator<Cell> cellIterator = row.cellIterator();
                AffectationSalarieSupp affect = new AffectationSalarieSupp();
                Chantier c1 = new Chantier();
                Salarie s1 = new Salarie();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
//Check the cell type and format accordingly
                    if ((cell.getColumnIndex() == 0)) {
                        try {
                            System.out.println("chantier " + cell.getStringCellValue());
                        } catch (Exception e) {
                            System.out.println("erreur chantier " + e.getMessage());
                            erreur = true;
                            break;
                        }
                        c1 = chantieService.getChantier(chantieService.getChantierByAffaire(cell.getStringCellValue()));
                        erreur = c1 == null;
                        if (erreur) {
                            System.out.println("erreur code Chantier n'éxiste pas : " + cell.getStringCellValue());
                            break;
                        } else {
                            affect.setChantier(c1);
                        }
                        continue;
                    }
                    if ((cell.getColumnIndex() == 5)) {
                        try {
                            System.out.println("salarie " + cell.getNumericCellValue());
                        } catch (Exception e) {
                            System.out.println("erreur salarie " + e.getMessage());
                            erreur = true;
                            break;
                        }
                        s1 = salarieService.getSalarie((int) cell.getNumericCellValue());
                        erreur = (s1 == null);
                        if (erreur) {
                            System.out.println("erreur id Salarie n'éxiste pas : " + cell.getNumericCellValue());
                            break;
                        } else if (chantieService.verifierAffectationSalarieChantier(c1.getId(), s1.getId())) {
                            affect.setSalaries(s1);
                        } else {
                            createExcel("le Salarie " + s1.getId() + " n'est pas affecté au chantier : " + c1.getCode() + " avec ID " + c1.getId());
                            System.out.println("le Salarie " + s1.getId() + " n'est pas affecté au chantier : " + c1.getCode() + " avec ID " + c1.getId());
                            Module.message(1, "Erreur", "le Salarie " + s1.getId() + " n'est pas affecté au chantier " + c1.getCode().trim());
                            break;
                        }
                        continue;
                    }
                    if ((cell.getColumnIndex() == 2)) {
                        try {
                            System.out.println("chef " + cell.getNumericCellValue());
                        } catch (Exception e) {
                            erreur = true;
                            System.out.println("erreur chef " + e.getMessage());
                            break;
                        }
                        Salarie ce1 = salarieService.getSalarie((int) cell.getNumericCellValue());
                        erreur = ce1 == null;
                        if (erreur) {
                            System.out.println("erreur id Salarie Chef Equipe n'éxiste pas : " + cell.getNumericCellValue());
                            break;
                        } else {
                            if (!ce1.getFonction().getId().equals(Constante.FONCTION_ID_CHEF_EQUIPE)) {
                                System.out.println("le salarié " + ce1.getNom() + " " + ce1.getPrenom() + " n'est pas un chef d'équipe");
                                Module.message(1, "Erreur", "salarie " + cell.getNumericCellValue() + " n'est pas un chef d'équipe");
                                break;
                            }
                            if (chantieService.verifierAffectationSalarieChantier(c1.getId(), ce1.getId())) {
                                AffectationSalarieSupp existe = affectationSSuppService.getLastSuperiorBySalarieAndChantier(s1, c1.getId());
                                if (existe != null) {
                                    System.out.println("salarie a déjà un supérieur");
                                    existe.setCurrentSupp(false);
                                    affectationSSuppService.updateSalarie(existe);
                                }
                                affect.setChefEquipe(ce1);
                                affect.setCurrentSupp(true);
                            } else {
                                System.out.println("le Chef Equipe " + ce1.getId() + " n'est pas affecté au chantier : " + c1.getCode() + " avec ID " + c1.getId());
                                Module.message(1, "Erreur", "le Chef Equipe n'est pas affecté au chantier " + c1.getCode().trim());
                                break;
                            }
                            continue;
                        }
                    }
                    if ((cell.getColumnIndex() == 9)) {
                        try {
                            System.out.println("date " + cell.getDateCellValue());
                            affect.setDateAffectatio(cell.getDateCellValue());
                        } catch (Exception e) {
                            erreur = true;
                            System.out.println("ereur date " + e.getMessage());
                        }

                    }
                }
                if (erreur) {
                    er++;
                } else {
                    affectationSSuppService.affecterSupp(affect);
                    j++;
                    System.out.println("importation réussie");
                }
            }
            salarieByChantiersAffect();
            Module.message(0, "Info", j + " Importation réussie et " + er + " echouée");
        } catch (Exception e) {
            System.out.println("erreur _ " + e.getMessage() + " __ " + e.getLocalizedMessage());
            Module.message(2, "Erreur", "importation echouée " + j + " importation effectuée");
        }
    }
}
