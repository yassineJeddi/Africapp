/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.mb.stock;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.lowagie.text.pdf.PdfCell;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import ma.bservice.tgcc.Constante.Message;
import ma.bservices.beans.Article;
import ma.bservices.beans.Chantier;
import ma.bservices.beans.Zone;
import ma.bservices.mb.services.ConstanteMb;
import ma.bservices.mb.services.Module;
import ma.bservices.mb.testMensuel;
import ma.bservices.tgcc.Entity.AffectationStock;
import ma.bservices.tgcc.Entity.ChantierArticleQ;
import ma.bservices.tgcc.Entity.ConsommationStock;
import ma.bservices.tgcc.Entity.RetourStock;
import ma.bservices.tgcc.Entity.TransferStock;
import ma.bservices.tgcc.Entity.TransferStockManager;
import ma.bservices.tgcc.Entity.ZoneArticleQ;
import ma.bservices.tgcc.service.Engin.ChantierService;
import ma.bservices.tgcc.service.Engin.ZoneServices;
import ma.bservices.tgcc.service.SendEmail;
import ma.bservices.tgcc.service.stock.AffectationStockService;
import ma.bservices.tgcc.service.stock.ArticleService;
import ma.bservices.tgcc.service.stock.ChantierArticleQService;
import ma.bservices.tgcc.service.stock.ConsommationStockService;
import ma.bservices.tgcc.service.stock.RetourStockService;
import ma.bservices.tgcc.service.stock.TransferStockManagerService;
import ma.bservices.tgcc.service.stock.TransferStockService;
import ma.bservices.tgcc.service.stock.ZoneArticleQService;
import ma.bservices.tgcc.utilitaire.ExcelParser;
import ma.bservices.tgcc.utilitaire.TGCCMailSender;
import ma.bservices.tgcc.utilitaire.TgccFileManager;
import ma.bservices.tgcc.webService.FonctionsWS;
import ma.bservices.tgcc.webService.MensuelWSCallManager;
import org.json.JSONArray;
import org.json.JSONObject;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FlowEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TabChangeEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

/**
 *
 * @author airaamane
 */
@Component
@ManagedBean(name = "operationStock")
@ViewScoped
public class OperationStockMb implements Serializable {

    /* Services */
    @ManagedProperty(value = "#{transferStockService}")
    private TransferStockService transferStockService;

    @ManagedProperty(value = "#{chantierArticleQService}")
    private ChantierArticleQService chantierArticleQService;

    @ManagedProperty(value = "#{affectationStockService}")
    private AffectationStockService affectationStockService;

    @ManagedProperty(value = "#{articleService}")
    private ArticleService articleService;

    @ManagedProperty(value = "#{zoneArticleQService}")
    private ZoneArticleQService zoneArticleQService;

    @ManagedProperty(value = "#{zoneServices}")
    private ZoneServices zoneServices;

    @ManagedProperty(value = "#{consommationStockService}")
    private ConsommationStockService consommationService;

    @ManagedProperty(value = "#{chantierService}")
    private ChantierService chantierService;

    @ManagedProperty(value = "#{retourService}")
    private RetourStockService retourService;

    @ManagedProperty(value = "#{transferStockManagerService}")
    private TransferStockManagerService transferStockManagerService;

    @ManagedProperty(value = "#{chantieService}")
    private ma.bservices.services.ChantierService chantieService;

    /**
     * *******************VARIABLES DE TRANSFERT***********************
     */
    TransferStock transferStock = new TransferStock();
    List<TransferStock> transfersSelectedBySearch = new LinkedList<>();
    List<TransferStock> transBySearch = new LinkedList<>();

    private int chantierEmetteurId;
    private int chantierRecepteurId;
    private Integer transferSelectedBySearchId;
    private Double quantiteToTransfer;
    private Double quantiteTransferToReturn;
    private Double quantiteRec;
    private List<TransferStock> transfers;
    private List<TransferStock> receptions;
    private List<TransferStock> retoursTransfer;
    
    List<Chantier> chantiersTrait = new LinkedList<>();

    private ChantierArticleQ articleIter;
    private ChantierArticleQ searchArt;

    private String searchCode = "", searchFam = "", searchSFam = "", searchSSFam = "", searchDes = "";

    public ChantierArticleQ getSearchArt() {
        return searchArt;
    }

    public void setSearchArt(ChantierArticleQ searchArt) {
        this.searchArt = searchArt;
    }

    /**
     * ********************************************************
     */
    /**
     * *******************VARIABLES D'AFFECTATION***********************
     */
    private int zoneAffectId;
    private int lotAffectId;
    private Double quantiteToAffect;
    private Double quantiteToReturn;
    private Double quantiteToConsomme;
    AffectationStock affectationStock = new AffectationStock();
    ConsommationStock consommationStock = new ConsommationStock();
    RetourStock retourStock = new RetourStock();
    private List<AffectationStock> affectations;
    private String qtePopup;

    /**
     * -- variables de gestion de l'écart * de quantités stock ** * entre
     * divalto et upsit *
     */
    ChantierArticleQ articleToCheck = new ChantierArticleQ();
    Double qteDivaStock;

    public Double getQteDivaStock() {
        return qteDivaStock;
    }

    public void setQteDivaStock(Double qteDivaStock) {
        this.qteDivaStock = qteDivaStock;
    }

    public ChantierArticleQ getArticleToCheck() {
        return articleToCheck;
    }

    public void setArticleToCheck(ChantierArticleQ articleToCheck) {
        this.articleToCheck = articleToCheck;
    }

    /**
     * END OF *
     */
    /**
     * *********************************************************
     */
    List<TransferStockManager> transfersToProcess = new LinkedList<>();
    private Date dateFrom, dateTo;
    String reasonForRetour;
    Article article = new Article();
    String operation = "";
    String operationTransfer = "";
    String operationTransferRet = "";

    String uniteToSearch = "";

    private int chantierToReaffect;

    TransferStock transferSelected = new TransferStock();
    AffectationStock affectationSelected = new AffectationStock();

    private Integer quantiteInter;
    private String operationsHeader;
    private String designationToSearch;
    private String ch;
    private Integer codeDerniereOperation;
    private boolean isBonGenerated = false;
    private boolean hasRetourValue;

    List<Zone> zonesByChantier;

    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    Date date = new Date();

    private int chantierTransferId;
    private int lotConsommation;
    private int lotReturn;
    private int articleToConsomme;
    private int chantierSearch2;
    private int zomeToConsommeFrom;
    private int chantierGlobal;
    private int numberOfArticlesToTransfer;
    private int chantierTransferMulti;
    private int chantierRecepteurMultipleId;
    private Date dateTransferMulti;
    private int codeTransfertStock;

    private String activeTabTitle = "Affectations";

    Document doc = new Document();

    private String designationToSearch2;

    private String radioProcess;

    public String getSearchCode() {
        return searchCode;
    }

    public void setSearchCode(String searchCode) {
        this.searchCode = searchCode;
    }

    public String getSearchFam() {
        return searchFam;
    }

    public void setSearchFam(String searchFam) {
        this.searchFam = searchFam;
    }

    public String getSearchSFam() {
        return searchSFam;
    }

    public void setSearchSFam(String searchSFam) {
        this.searchSFam = searchSFam;
    }

    public String getSearchSSFam() {
        return searchSSFam;
    }

    public void setSearchSSFam(String searchSSFam) {
        this.searchSSFam = searchSSFam;
    }

    public String getSearchDes() {
        return searchDes;
    }

    public void setSearchDes(String searchDes) {
        this.searchDes = searchDes;
    }

    private boolean isDetailable;
    private Date dateOfToday;

    private Date dateReceptionTransfert;

    private boolean isStockTableRendered = true;
    private boolean isConsoTablerendered = false;
    private boolean isRetourTableRendered = false;
    private boolean isReceptionTableRendered = false;
    private boolean isTansferTableRendered = true;
    private boolean isRetourTransTableRendered = false;
    private boolean isInputDisabled = false;
    private boolean isOKshown = false;
    private boolean isCommandShown = true;
    private boolean isRecYet = true;
    private boolean isReadyToTransfer = false;
    private boolean noChantierSelectedYet = true;

    private List<ChantierArticleQ> articlesInChantier;
    private List<ChantierArticleQ> filteredArtsInChan;
    private List<ChantierArticleQ> articlesInChantierf;
    List<Chantier> chantiers = new LinkedList<>();

    private Map<Integer, Integer> mapOfQuantiteByZone;
    private List<?> listToDisplay = new LinkedList<>();
    private List<String> operations = new LinkedList<>();
    private List<ZoneArticleQ> zoneArticleQs = new LinkedList<>();

    Date dateAffect = new Date();
    Date dateTransfert = new Date();
    Date dateConsommation = new Date();
    Date dateRetour = new Date();

    private List<AffectationStock> listOfAffectationsByArticleId;
    private ZoneArticleQ zoneArticleQ = new ZoneArticleQ();
    private ZoneArticleQ zoneArticleQSelected = new ZoneArticleQ();
    private ChantierArticleQ chantierArticleQ = new ChantierArticleQ();
    private ChantierArticleQ chantierArticleQSelected = new ChantierArticleQ();

    List<TransferStock> listOfArticlesInTransfer = new LinkedList<>();

    private List<Article> selectedArticles = new LinkedList<>();
    private List<Article> articles;
    private List<RetourStock> retours = new LinkedList<>();
    private List<ConsommationStock> consommations = new LinkedList<>();
    private List<String> selectedQuantites;

    //transfert multi article variables
    Article articleToTransferM = new Article();
    ChantierArticleQ chantierArticleQToTransferM = new ChantierArticleQ();
    private List<ChantierArticleQ> listOfArticlesToTransferM = new LinkedList<>();
    private List<Integer> listOfQuantitiesToTransferM = new LinkedList<>();
    private Double quantiteToTransferM;

    ZoneArticleQ zaq = new ZoneArticleQ();
    ChantierArticleQ chaq = new ChantierArticleQ();

    String codeCurrent;

    public int codebon;

    boolean isRetShown = false;

    public String getRadioProcess() {
        return radioProcess;
    }

    public void setRadioProcess(String radioProcess) {
        this.radioProcess = radioProcess;
    }

    AffectationStock affectationToDelete;
    ConsommationStock consoToDelete;
    RetourStock retourToDelete;

    private Article articleToAdd = new Article();
    private int zoneToAdd;
    private int chantierToAdd;
    private int familleToAdd;
    private int sfamilleToAdd;
    private int lotToAdd;
    private int typologieToAdd;

    ArticleQuantite articleTRM2 = new ArticleQuantite();
    private int quantiteToReturnTr;
    TransferStock transferToDelete = new TransferStock();
    int codeAff;
    int referenceTrGlobal;
    List<ArticleQuantite> listOfArticleQuantiteGlobal = new LinkedList<>();

    public int getReferenceTrGlobal() {
        return referenceTrGlobal;
    }

    public void setReferenceTrGlobal(int referenceTrGlobal) {
        this.referenceTrGlobal = referenceTrGlobal;
    }

    public List<ArticleQuantite> getListOfArticleQuantiteGlobal() {
        return listOfArticleQuantiteGlobal;
    }

    public void setListOfArticleQuantiteGlobal(List<ArticleQuantite> listOfArticleQuantiteGlobal) {
        this.listOfArticleQuantiteGlobal = listOfArticleQuantiteGlobal;
    }

    public String getUniteToSearch() {
        return uniteToSearch;
    }

    public void setUniteToSearch(String uniteToSearch) {
        this.uniteToSearch = uniteToSearch;
    }

    public int getCodeAff() {
        return codeAff;
    }

    public List<ChantierArticleQ> getFilteredArtsInChan() {
        return filteredArtsInChan;
    }

    public void setFilteredArtsInChan(List<ChantierArticleQ> filteredArtsInChan) {
        this.filteredArtsInChan = filteredArtsInChan;
    }

    public TransferStockManagerService getTransferStockManagerService() {
        return transferStockManagerService;
    }

    public List<Chantier> getChantiersTrait() {
        return chantiersTrait;
    }

    public void setChantiersTrait(List<Chantier> chantiersTrait) {
        this.chantiersTrait = chantiersTrait;
    }
    
    

    public void setTransferStockManagerService(TransferStockManagerService transferStockManagerService) {
        this.transferStockManagerService = transferStockManagerService;
    }

    public void setCodeAff(int codeAff) {
        this.codeAff = codeAff;
    }

    public int getQuantiteToReturnTr() {
        return quantiteToReturnTr;
    }

    public void setQuantiteToReturnTr(int quantiteToReturnTr) {
        this.quantiteToReturnTr = quantiteToReturnTr;
    }

    public String getQtePopup() {
        return qtePopup;
    }

    public void setQtePopup(String qtePopup) {
        this.qtePopup = qtePopup;
    }

    public TransferStock getTransferToDelete() {
        return transferToDelete;
    }

    public ChantierArticleQ getArticleIter() {
        return articleIter;
    }

    public void setArticleIter(ChantierArticleQ articleIter) {
        this.articleIter = articleIter;
    }

    public ma.bservices.services.ChantierService getChantieService() {
        return chantieService;
    }

    public void setChantieService(ma.bservices.services.ChantierService chantieService) {
        this.chantieService = chantieService;
    }

    public List<TransferStockManager> getTransfersToProcess() {
        return transfersToProcess;
    }

    public void setTransfersToProcess(List<TransferStockManager> transfersToProcess) {
        this.transfersToProcess = transfersToProcess;
    }

    public void setTransferToDelete(TransferStock transferToDelete) {
        this.transferToDelete = transferToDelete;
    }

    public ArticleQuantite getArticleTRM2() {
        return articleTRM2;
    }

    public void setArticleTRM2(ArticleQuantite articleTRM) {
        this.articleTRM2 = articleTRM;
    }

    public RetourStock getRetourToDelete() {
        return retourToDelete;
    }

    public void setRetourToDelete(RetourStock retourToDelete) {
        this.retourToDelete = retourToDelete;
    }

    public ConsommationStock getConsoToDelete() {
        return consoToDelete;
    }

    public void setConsoToDelete(ConsommationStock consoToDelete) {
        this.consoToDelete = consoToDelete;
    }

    public AffectationStock getAffectationToDelete() {
        return affectationToDelete;
    }

    public void setAffectationToDelete(AffectationStock affectationToDelete) {
        this.affectationToDelete = affectationToDelete;
    }

    public int getChantierToReaffect() {
        return chantierToReaffect;
    }

    public void setChantierToReaffect(int chantierToReaffect) {
        this.chantierToReaffect = chantierToReaffect;
    }

    public boolean isIsRetShown() {
        return isRetShown;
    }

    public void setIsRetShown(boolean isRetShown) {
        this.isRetShown = isRetShown;
    }

    public int getCodebon() {
        return codebon;
    }

    public void setCodebon(int codebon) {
        this.codebon = codebon;
    }

    public String getCodeCurrent() {
        return codeCurrent;
    }

    public ZoneArticleQ getZaq() {
        return zaq;
    }

    public String getActiveTabTitle() {
        return activeTabTitle;
    }

    public void setActiveTabTitle(String activeTabTitle) {
        this.activeTabTitle = activeTabTitle;
    }

    public void setZaq(ZoneArticleQ zaq) {
        this.zaq = zaq;
    }

    public ChantierArticleQ getChaq() {
        return chaq;
    }

    public void setChaq(ChantierArticleQ chaq) {
        this.chaq = chaq;
    }

    public void setCodeCurrent(String codeCurrent) {
        this.codeCurrent = codeCurrent;
    }

    //getters and setters
    public Article getArticleToTransferM() {
        return articleToTransferM;
    }

    public void setArticleToTransferM(Article articleToTransferM) {
        this.articleToTransferM = articleToTransferM;
    }

    public Integer getCodeDerniereOperation() {
        return codeDerniereOperation;
    }

    public void setCodeDerniereOperation(Integer codeDerniereOperation) {
        this.codeDerniereOperation = codeDerniereOperation;
    }

    public boolean isIsBonGenerated() {
        return isBonGenerated;
    }

    public void setIsBonGenerated(boolean isBonGenerated) {
        this.isBonGenerated = isBonGenerated;
    }

    public String getDesignationToSearch() {
        return designationToSearch;
    }

    public void setDesignationToSearch(String designationToSearch) {
        this.designationToSearch = designationToSearch;
    }

    public String getDesignationToSearch2() {
        return designationToSearch2;
    }

    public Document getDoc() {
        return doc;
    }

    public void setDoc(Document doc) {
        this.doc = doc;
    }

    public void setDesignationToSearch2(String designationToSearch2) {
        this.designationToSearch2 = designationToSearch2;
    }

    public ChantierArticleQ getChantierArticleQToTransferM() {
        return chantierArticleQToTransferM;
    }

    public void setChantierArticleQToTransferM(ChantierArticleQ chantierArticleQToTransferM) {
        this.chantierArticleQToTransferM = chantierArticleQToTransferM;
    }

    public String getCh() {
        return ch;
    }

    public void setCh(String ch) {
        this.ch = ch;
    }

    public List<TransferStock> getListOfArticlesInTransfer() {
        return listOfArticlesInTransfer;
    }

    public void setListOfArticlesInTransfer(List<TransferStock> listOfArticlesInTransfer) {
        this.listOfArticlesInTransfer = listOfArticlesInTransfer;
    }

    public boolean isHasRetourValue() {
        return hasRetourValue;
    }

    public void setHasRetourValue(boolean hasRetourValue) {
        this.hasRetourValue = hasRetourValue;
    }

    public String getOperationTransferRet() {
        return operationTransferRet;
    }

    public List<TransferStock> getTransfersSelectedBySearch() {
        return transfersSelectedBySearch;
    }

    public void setTransfersSelectedBySearch(List<TransferStock> transfersSelectedBySearch) {
        this.transfersSelectedBySearch = transfersSelectedBySearch;
    }

    public void setOperationTransferRet(String operationTransferRet) {
        this.operationTransferRet = operationTransferRet;
    }

    public boolean isNoChantierSelectedYet() {
        return noChantierSelectedYet;
    }

    public void setNoChantierSelectedYet(boolean noChantierSelectedYet) {
        this.noChantierSelectedYet = noChantierSelectedYet;
    }

    public boolean isIsRecYet() {
        return isRecYet;
    }

    public List<TransferStock> getTransBySearch() {
        return transBySearch;
    }

    public void setTransBySearch(List<TransferStock> transBySearch) {
        this.transBySearch = transBySearch;
    }

    public List<Chantier> getChantiers() {
        return chantiers;
    }

    public void setChantiers(List<Chantier> chantiers) {
        this.chantiers = chantiers;
    }

    public List<Zone> getZonesByChantier() {
        return zonesByChantier;
    }

    public void setZonesByChantier(List<Zone> zonesByChantier) {
        this.zonesByChantier = zonesByChantier;
    }

    public void setIsRecYet(boolean isRecYet) {
        this.isRecYet = isRecYet;
    }

    public int getCodeTransfertStock() {
        return codeTransfertStock;
    }

    public void setCodeTransfertStock(int codeTransfertStock) {
        this.codeTransfertStock = codeTransfertStock;
    }

    public List<ChantierArticleQ> getArticlesInChantierf() {
        return articlesInChantierf;
    }

    public void setArticlesInChantierf(List<ChantierArticleQ> articlesInChantierf) {
        this.articlesInChantierf = articlesInChantierf;
    }

    public List<ChantierArticleQ> getListOfArticlesToTransferM() {
        return listOfArticlesToTransferM;
    }

    public void setListOfArticlesToTransferM(List<ChantierArticleQ> listOfArticlesToTransferM) {
        this.listOfArticlesToTransferM = listOfArticlesToTransferM;
    }

    public List<Integer> getListOfQuantitiesToTransferM() {
        return listOfQuantitiesToTransferM;
    }

    public void setListOfQuantitiesToTransferM(List<Integer> listOfQuantitiesToTransferM) {
        this.listOfQuantitiesToTransferM = listOfQuantitiesToTransferM;
    }

    public Double getQuantiteToTransferM() {
        return quantiteToTransferM;
    }

    public boolean isIsReadyToTransfer() {
        return isReadyToTransfer;
    }

    public void setIsReadyToTransfer(boolean isReadyToTransfer) {
        this.isReadyToTransfer = isReadyToTransfer;
    }

    public void setQuantiteToTransferM(Double quantiteToTransferM) {
        this.quantiteToTransferM = quantiteToTransferM;
    }

    // end of gtters and setters
    public TransferStockService getTransferStockService() {
        return transferStockService;
    }

    public void setTransferStockService(TransferStockService transferStockService) {
        this.transferStockService = transferStockService;
    }

    public ZoneServices getZoneServices() {
        return zoneServices;
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

    public boolean isIsRetourTransTableRendered() {
        return isRetourTransTableRendered;
    }

    public void setIsRetourTransTableRendered(boolean isRetourTransTableRendered) {
        this.isRetourTransTableRendered = isRetourTransTableRendered;
    }

    public void setZoneServices(ZoneServices zoneServices) {
        this.zoneServices = zoneServices;
    }

    public boolean isIsCommandShown() {
        return isCommandShown;
    }

    public void setIsCommandShown(boolean isCommandShown) {
        this.isCommandShown = isCommandShown;
    }

    public String getReasonForRetour() {
        return reasonForRetour;
    }

    public void setReasonForRetour(String reasonForRetour) {
        this.reasonForRetour = reasonForRetour;
    }

    public List<TransferStock> getReceptions() {
        return receptions;
    }

    public DateFormat getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(DateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isIsOKshown() {
        return isOKshown;
    }

    public void setIsOKshown(boolean isOKshown) {
        this.isOKshown = isOKshown;
    }

    public Double getQuantiteRec() {
        return quantiteRec;
    }

    public void setQuantiteRec(Double quantiteRec) {
        this.quantiteRec = quantiteRec;
    }

    public void setReceptions(List<TransferStock> receptions) {
        this.receptions = receptions;
    }

    public Date getDateOfToday() {
        return dateOfToday;
    }

    public Date getDateReceptionTransfert() {
        return dateReceptionTransfert;
    }

    public void setDateReceptionTransfert(Date dateReceptionTransfert) {
        this.dateReceptionTransfert = dateReceptionTransfert;
    }

    public boolean isIsInputDisabled() {
        return isInputDisabled;
    }

    public void setIsInputDisabled(boolean isInputDisabled) {
        this.isInputDisabled = isInputDisabled;
    }

    public void setDateOfToday(Date dateOfToday) {
        this.dateOfToday = dateOfToday;
    }

    public List<TransferStock> getRetoursTransfer() {
        return retoursTransfer;
    }

    public void setRetoursTransfer(List<TransferStock> retoursTransfer) {
        this.retoursTransfer = retoursTransfer;
    }

    public int getNumberOfArticlesToTransfer() {
        return numberOfArticlesToTransfer;
    }

    public void setNumberOfArticlesToTransfer(int numberOfArticlesToTransfer) {
        this.numberOfArticlesToTransfer = numberOfArticlesToTransfer;
    }

    public ChantierArticleQ getChantierArticleQSelected() {
        return chantierArticleQSelected;
    }

    public void setChantierArticleQSelected(ChantierArticleQ chantierArticleQSelected) {
        this.chantierArticleQSelected = chantierArticleQSelected;
    }

    public ChantierArticleQService getChantierArticleQService() {
        return chantierArticleQService;
    }

    public void setChantierArticleQService(ChantierArticleQService chantierArticleQService) {
        this.chantierArticleQService = chantierArticleQService;
    }

    public ChantierArticleQ getChantierArticleQ() {
        return chantierArticleQ;
    }

    public void setChantierArticleQ(ChantierArticleQ chantierArticleQ) {
        this.chantierArticleQ = chantierArticleQ;
    }

    public List<ChantierArticleQ> getArticlesInChantier() {
        return articlesInChantier;
    }

    public void setArticlesInChantier(List<ChantierArticleQ> articlesInChantier) {
        this.articlesInChantier = articlesInChantier;
    }

    public int getChantierTransferMulti() {
        return chantierTransferMulti;
    }

    public void setChantierTransferMulti(int chantierTransferMulti) {
        this.chantierTransferMulti = chantierTransferMulti;
    }

    public int getChantierRecepteurMultipleId() {
        return chantierRecepteurMultipleId;
    }

    public void setChantierRecepteurMultipleId(int chantierRecepteurMultipleId) {
        this.chantierRecepteurMultipleId = chantierRecepteurMultipleId;
    }

    public Date getDateTransferMulti() {
        return dateTransferMulti;
    }

    public void setDateTransferMulti(Date dateTransferMulti) {
        this.dateTransferMulti = dateTransferMulti;
    }

    public int getLotReturn() {
        return lotReturn;
    }

    public void setLotReturn(int lotReturn) {
        this.lotReturn = lotReturn;
    }

    public String getOperationsHeader() {
        return operationsHeader;
    }

    public String getOperationTransfer() {
        return operationTransfer;
    }

    public ZoneArticleQ getZoneArticleQSelected() {
        return zoneArticleQSelected;
    }

    public void setZoneArticleQSelected(ZoneArticleQ zoneArticleQSelected) {
        this.zoneArticleQSelected = zoneArticleQSelected;
    }

    public List<ZoneArticleQ> getZoneArticleQs() {
        return zoneArticleQs;
    }

    public void setZoneArticleQs(List<ZoneArticleQ> zoneArticleQs) {
        this.zoneArticleQs = zoneArticleQs;
    }

    public int getChantierGlobal() {
        return chantierGlobal;
    }

    public void setChantierGlobal(int chantierGlobal) {
        this.chantierGlobal = chantierGlobal;
    }

    public void setOperationTransfer(String operationTransfer) {
        this.operationTransfer = operationTransfer;
    }

    public void setOperationsHeader(String operationsHeader) {
        this.operationsHeader = operationsHeader;
    }

    public ZoneArticleQService getZoneArticleQService() {
        return zoneArticleQService;
    }

    public void setZoneArticleQService(ZoneArticleQService zoneArticleQService) {
        this.zoneArticleQService = zoneArticleQService;
    }

    public ArticleService getArticleService() {
        return articleService;
    }

    public void setArticleService(ArticleService articleService) {
        this.articleService = articleService;
    }

    public AffectationStockService getAffectationStockService() {
        return affectationStockService;
    }

    public void setAffectationStockService(AffectationStockService affectationStockService) {
        this.affectationStockService = affectationStockService;
    }

    public ZoneArticleQ getZoneArticleQ() {
        return zoneArticleQ;
    }

    public void setZoneArticleQ(ZoneArticleQ zoneArticleQ) {
        this.zoneArticleQ = zoneArticleQ;
    }

    public boolean isIsStockTableRendered() {
        return isStockTableRendered;
    }

    public void setIsStockTableRendered(boolean isStockTableRendered) {
        this.isStockTableRendered = isStockTableRendered;
    }

    public boolean isIsConsoTablerendered() {
        return isConsoTablerendered;
    }

    public void setIsConsoTablerendered(boolean isConsoTablerendered) {
        this.isConsoTablerendered = isConsoTablerendered;
    }

    public boolean isIsRetourTableRendered() {
        return isRetourTableRendered;
    }

    public boolean isIsReceptionTableRendered() {
        return isReceptionTableRendered;
    }

    public void setIsReceptionTableRendered(boolean isReceptionTableRendered) {
        this.isReceptionTableRendered = isReceptionTableRendered;
    }

    public List<Article> getListOfAlreadyTransferedArticles() {
        return listOfAlreadyTransferedArticles;
    }

    public void setListOfAlreadyTransferedArticles(List<Article> listOfAlreadyTransferedArticles) {
        this.listOfAlreadyTransferedArticles = listOfAlreadyTransferedArticles;
    }

    public boolean isIsTansferTableRendered() {
        return isTansferTableRendered;
    }

    public void setIsTansferTableRendered(boolean isTansferTableRendered) {
        this.isTansferTableRendered = isTansferTableRendered;
    }

    public void setIsRetourTableRendered(boolean isRetourTableRendered) {
        this.isRetourTableRendered = isRetourTableRendered;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public RetourStockService getRetourService() {
        return retourService;
    }

    public void setRetourService(RetourStockService retourService) {
        this.retourService = retourService;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public List<RetourStock> getRetours() {
        return retours;
    }

    public void setRetours(List<RetourStock> retours) {
        this.retours = retours;
    }

    public List<ConsommationStock> getConsommations() {
        return consommations;
    }

    public void setConsommations(List<ConsommationStock> consommations) {
        this.consommations = consommations;
    }

    public TransferStock getTransferStock() {
        return transferStock;
    }

    public void setTransferStock(TransferStock transferStock) {
        this.transferStock = transferStock;
    }

    public AffectationStock getAffectationStock() {
        return affectationStock;
    }

    public void setAffectationStock(AffectationStock affectationStock) {
        this.affectationStock = affectationStock;
    }

    public int getChantierEmetteurId() {
        return chantierEmetteurId;
    }

    public void setChantierEmetteurId(int chantierEmetteurId) {
        this.chantierEmetteurId = chantierEmetteurId;
    }

    public int getChantierRecepteurId() {
        return chantierRecepteurId;
    }

    public void setChantierRecepteurId(int chantierRecepteurId) {
        this.chantierRecepteurId = chantierRecepteurId;
    }

    public RetourStock getRetourStock() {
        return retourStock;
    }

    public void setRetourStock(RetourStock retourStock) {
        this.retourStock = retourStock;
    }

    public AffectationStock getAffectationSelected() {
        return affectationSelected;
    }

    public ChantierService getChantierService() {
        return chantierService;
    }

    public void setChantierService(ChantierService chantierService) {
        this.chantierService = chantierService;
    }

    public void setAffectationSelected(AffectationStock affectationSelected) {
        this.affectationSelected = affectationSelected;
    }

    public TransferStock getTransferSelected() {
        return transferSelected;
    }

    public void setTransferSelected(TransferStock transferSelected) {
        this.transferSelected = transferSelected;
    }

    public Double getQuantiteToTransfer() {
        return quantiteToTransfer;
    }

    public void setQuantiteToTransfer(Double quantiteToTransfer) {
        this.quantiteToTransfer = quantiteToTransfer;
    }

    public ConsommationStockService getConsommationService() {
        return consommationService;
    }

    public void setConsommationService(ConsommationStockService consommationService) {
        this.consommationService = consommationService;
    }

    public ConsommationStock getConsommationStock() {
        return consommationStock;
    }

    public void setConsommationStock(ConsommationStock consommationStock) {
        this.consommationStock = consommationStock;
    }

    public int getArticleToConsomme() {
        return articleToConsomme;
    }

    public void setArticleToConsomme(int articleToConsomme) {
        this.articleToConsomme = articleToConsomme;
    }

    public Integer getQuantiteInter() {
        return quantiteInter;
    }

    public void setQuantiteInter(Integer quantiteInter) {
        this.quantiteInter = quantiteInter;
    }

    public Double getQuantiteToConsomme() {
        return quantiteToConsomme;
    }

    public void setQuantiteToConsomme(Double quantiteToConsomme) {
        this.quantiteToConsomme = quantiteToConsomme;
    }

    public Double getQuantiteTransferToReturn() {
        return quantiteTransferToReturn;
    }

    public void setQuantiteTransferToReturn(Double quantiteTransferToReturn) {
        this.quantiteTransferToReturn = quantiteTransferToReturn;
    }

    public Double getQuantiteToReturn() {
        return quantiteToReturn;
    }

    public void setQuantiteToReturn(Double quantiteToReturn) {
        this.quantiteToReturn = quantiteToReturn;
    }

    public Double getQuantiteToAffect() {
        return quantiteToAffect;
    }

    public void setQuantiteToAffect(Double quantiteToAffect) {
        this.quantiteToAffect = quantiteToAffect;
    }

    public List<?> getListToDisplay() {
        return listToDisplay;
    }

    public void setListToDisplay(List<?> listToDisplay) {
        this.listToDisplay = listToDisplay;
    }

    public int getZoneAffectId() {
        return zoneAffectId;
    }

    public int getChantierToAdd() {
        return chantierToAdd;
    }

    public void setChantierToAdd(int chantierToAdd) {
        this.chantierToAdd = chantierToAdd;
    }

    public int getZoneToAdd() {
        return zoneToAdd;
    }

    public void setZoneToAdd(int zoneToAdd) {
        this.zoneToAdd = zoneToAdd;
    }

    public int getLotToAdd() {
        return lotToAdd;
    }

    public void setLotToAdd(int lotToAdd) {
        this.lotToAdd = lotToAdd;
    }

    public int getFamilleToAdd() {
        return familleToAdd;
    }

    public void setFamilleToAdd(int familleToAdd) {
        this.familleToAdd = familleToAdd;
    }

    public int getSfamilleToAdd() {
        return sfamilleToAdd;
    }

    public void setSfamilleToAdd(int sfamilleToAdd) {
        this.sfamilleToAdd = sfamilleToAdd;
    }

    public int getTypologieToAdd() {
        return typologieToAdd;
    }

    public void setTypologieToAdd(int typologieToAdd) {
        this.typologieToAdd = typologieToAdd;
    }

    public Article getArticleToAdd() {
        return articleToAdd;
    }

    public void setArticleToAdd(Article articleToAdd) {
        this.articleToAdd = articleToAdd;
    }

    public void setZoneAffectId(int zoneAffectId) {
        this.zoneAffectId = zoneAffectId;
    }

    public int getZomeToConsommeFrom() {
        return zomeToConsommeFrom;
    }

    public void setZomeToConsommeFrom(int zomeToConsommeFrom) {
        this.zomeToConsommeFrom = zomeToConsommeFrom;
    }

    public int getLotConsommation() {
        return lotConsommation;
    }

    public void setLotConsommation(int lotConsommation) {
        this.lotConsommation = lotConsommation;
    }

    public int getLotAffectId() {
        return lotAffectId;
    }

    public int getChantierSearch2() {
        return chantierSearch2;
    }

    public void setChantierSearch2(int chantierSearch2) {
        this.chantierSearch2 = chantierSearch2;
    }

    public void setLotAffectId(int lotAffectId) {
        this.lotAffectId = lotAffectId;
    }

    public int getChantierTransferId() {
        return chantierTransferId;
    }

    public void setChantierTransferId(int chantierTransferId) {
        this.chantierTransferId = chantierTransferId;
    }

    public boolean isIsDetailable() {
        return isDetailable;
    }

    public void setIsDetailable(boolean isDetailable) {
        this.isDetailable = isDetailable;
    }

    public List<TransferStock> getTransfers() {
        return transfers;
    }

    public void setTransfers(List<TransferStock> transfers) {
        this.transfers = transfers;
    }

    public List<AffectationStock> getAffectations() {
        return affectations;
    }

    public void setAffectations(List<AffectationStock> affectations) {
        this.affectations = affectations;
    }

    public List<String> getOperations() {
        return operations;
    }

    public Integer getTransferSelectedBySearchId() {
        return transferSelectedBySearchId;
    }

    public void setTransferSelectedBySearchId(Integer transferSelectedBySearchId) {
        this.transferSelectedBySearchId = transferSelectedBySearchId;
    }

    public void setOperations(List<String> operations) {
        this.operations = operations;
    }

    public List<String> getSelectedQuantites() {
        return selectedQuantites;
    }

    public void setSelectedQuantites(List<String> selectedQuantites) {
        this.selectedQuantites = selectedQuantites;
    }

    public List<AffectationStock> getListOfAffectationsByArticleId() {
        return listOfAffectationsByArticleId;
    }

    public void setListOfAffectationsByArticleId(List<AffectationStock> listOfAffectationsByArticleId) {
        this.listOfAffectationsByArticleId = listOfAffectationsByArticleId;
    }

    public List<Article> getSelectedArticles() {
        return selectedArticles;
    }

    public void setSelectedArticles(List<Article> selectedArticles) {
        this.selectedArticles = selectedArticles;
    }

    public Map<Integer, Integer> getMapOfQuantiteByZone() {
        return mapOfQuantiteByZone;
    }

    public void setMapOfQuantiteByZone(Map<Integer, Integer> mapOfQuantiteByZone) {
        this.mapOfQuantiteByZone = mapOfQuantiteByZone;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public Date getDateTransfert() {
        return dateTransfert;
    }

    public void setDateTransfert(Date dateTransfert) {
        this.dateTransfert = dateTransfert;
    }

    public Date getDateAffect() {
        return dateAffect;
    }

    public void setDateAffect(Date dateAffect) {
        this.dateAffect = dateAffect;
    }

    public Date getDateConsommation() {
        return dateConsommation;
    }

    public void setDateConsommation(Date dateConsommation) {
        this.dateConsommation = dateConsommation;
    }

    public Date getDateRetour() {
        return dateRetour;
    }

    public void setDateRetour(Date dateRetour) {
        this.dateRetour = dateRetour;
    }

    /**
     * ******************* ADD TRANSFER STOCK *****************************
     */
    public void initBonTr(int refTr) {
        codeCurrent = "/files/documentsStock/bonTransfert" + refTr + ".pdf";
    }

    public void initCodeAff(int refAf) {
        codeCurrent = "/files/documentsStock/bonAffectation" + refAf + ".pdf";
    }

    public void initBonRetour() throws IOException, DocumentException {
        downLoad_Recu_Retour(transferSelected);
        codeCurrent = "/files/documentsStock/bonRetour" + transferSelected.getCodeTransfertStock() + ".pdf";
    }

    public void addTransferStock() throws IOException, DocumentException {

        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();

        cal1.setTime(dateTransfert);
        cal2.setTime(new Date());

        if (cal1.after(cal2)) {
            //System.out.println("Date1 is after Date2");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "La date du transfert n'est pas valide!"));
            return;
        }

        String refQteWSTransfer = "";
        StringBuilder strBuilder;
        int index;
        String qteInWSStringFormat = "";
        DateFormat formatDate = new SimpleDateFormat("dd-MM-yyyy");

        if (chantierRecepteurId == chantierGlobal) {
            infoExistsCh();
        } else {
            Integer referenceTr = 0;
            if (transferStockService.findAll() == null) {
                System.out.println("first transfer to add");
                referenceTr = 1;
            } else {
                System.out.println("last transfer code was " + transferStockService.findAll().get(transferStockService.findAll().size() - 1).getReferenceTransfer());
                referenceTr = (transferStockService.findAll().get(transferStockService.findAll().size() - 1).getReferenceTransfer()) + 1;
            }
            // transfert de selected articles à selected chantier 
            for (ArticleQuantite ar : listOfArticleQuantite) {
                transferStock = new TransferStock();
                quantiteToTransfer = ar.quantite;
                strBuilder = new StringBuilder(quantiteToTransfer.toString());
                index = strBuilder.indexOf(".");
                strBuilder.setCharAt(index, ',');
                qteInWSStringFormat = strBuilder.toString();

                //add le transfert à l'historique des transfers
                refQteWSTransfer = refQteWSTransfer.concat(ar.article.getCodeArticle()).concat(";").concat(qteInWSStringFormat).concat("|");
                System.out.println("ADDING TRANSFER ===== ");
                transferStockService.addTransferStock(transferStock, ar.article.getId(), chantierGlobal, chantierRecepteurId, quantiteToTransfer, 0d, 2, dateTransfert, false, false, false, referenceTr);
                System.out.println("TRANSFER ADDED FROM MANAGED BEAN ==== ");
                ChantierArticleQ chantierArticleQFoundInit = chantierArticleQService.findByChantierArticle(ar.article.getId(), chantierGlobal);
                chantierArticleQFoundInit.setEstEnCoursDeTransfert(true);
                chantierArticleQService.updateChantierArticleQ(chantierArticleQFoundInit, chantierArticleQFoundInit.getQuantiteChantierStock());
            }

            StringBuilder strBuilder2 = new StringBuilder(refQteWSTransfer);
            strBuilder2.deleteCharAt(strBuilder2.lastIndexOf("|"));
            refQteWSTransfer = strBuilder2.toString();
            System.out.println("WEBSERVICE STATEMENT TRANSFER ::::  ***** **** *** :: " + refQteWSTransfer);
            referenceTrGlobal = referenceTr;
            listOfArticleQuantiteGlobal = listOfArticleQuantite;
            //  String s = FonctionsWS.transfertWS(refQteWSTransfer, chantierService.findById(chantierGlobal).getCodeNovapaie(), chantierService.findById(chantierRecepteurId).getCodeNovapaie(), formatDate.format(dateTransfert).concat(" 00:00:00"));
            articlesInChantier = chantierArticleQService.findByChantierId(chantierGlobal);

            System.out.println("DISABLING THE BUTTON SHOWING BON ==== ");
            isNotReadyToShowBon = false;
            System.out.println(" === SHOWING BON ==== ");
            downLoad_Recu(referenceTrGlobal, listOfArticleQuantiteGlobal);
            initBonTr(referenceTrGlobal);
            System.out.println(codeCurrent);
            // downLoad_Recu(referenceTrGlobal, listOfArticleQuantiteGlobal);
            infoTR();
            // FacesContext context = FacesContext.getCurrentInstance();
            //ee  PF('detailz2_dialog').hide();

            RequestContext rc = RequestContext.getCurrentInstance();
            rc.execute("PF('detailz2_dialog').hide()");
            //  disableInputs();
        }
    }

    public void bonTransfert() throws IOException, DocumentException {
//        RequestContext rc = RequestContext.getCurrentInstance();
//        rc.execute("PF('transferMV_dialog').hide()");

        downLoad_Recu(referenceTrGlobal, listOfArticleQuantiteGlobal);
    }

    public void switchi() {
        isNotReadyToShowBon = true;

    }

    public void downLoad_Recu(int reference, List<ArticleQuantite> listOfArticles) throws IOException, DocumentException {
        System.out.println("entree");

        //  String chemin = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/files/resources/documentsStock/");
        String chemin = ConstanteMb.getRepertoire() + "/files/documentsStock";
        Path folder = Paths.get(chemin);
        Files.createDirectories(folder);

        String nomFichier = "/bonTransfert" + reference + ".pdf";
        File file = new File(chemin + nomFichier);

        Document document = new Document();

        PdfWriter.getInstance(document, new FileOutputStream(file));

        document.open();

        String chemin_Image = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/img/logo.png");
        Image image1 = Image.getInstance(chemin_Image);
        document.add(image1);
        Font f = new Font(FontFamily.HELVETICA, 16.0f, Font.BOLD, BaseColor.BLACK);
        Font ff = new Font(FontFamily.HELVETICA, 12.0f, Font.BOLD, BaseColor.BLACK);
        Paragraph paragraph = new Paragraph("Bon de Transfert ", f);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        document.add(paragraph);

        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        PdfPTable tx = new PdfPTable(2);
        tx.setWidthPercentage(100);

        PdfPCell cellFive = new PdfPCell(makePara("Date : ", dateFormat.format(transferStock.getDateTransferStock())));
        PdfPCell cellTree = new PdfPCell(makePara("Code Transfert : ", String.valueOf(reference)));

        cellTree.setBorder(Rectangle.NO_BORDER);
        cellFive.setBorder(Rectangle.NO_BORDER);

        tx.addCell(cellFive);
        tx.addCell(cellTree);

        tx.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        tx.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        tx.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
        tx.getDefaultCell().setFixedHeight(100);
        document.add(tx);

        document.add(new Paragraph(" "));

        PdfPTable txx = new PdfPTable(2);
        txx.setWidthPercentage(100);
        PdfPCell cellFivex = new PdfPCell(makePara("Chantier Expéditeur : ", transferStock.getChantierEmetteurId().getCode()));
        PdfPCell cellTreex = new PdfPCell(makePara("Chantier Destinataire: ", transferStock.getChantierRecepteurId().getCode()));

        cellTreex.setBorder(Rectangle.NO_BORDER);
        cellFivex.setBorder(Rectangle.NO_BORDER);

        txx.addCell(cellFivex);
        txx.addCell(cellTreex);

        txx.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        txx.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        txx.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
        txx.getDefaultCell().setFixedHeight(100);
        document.add(txx);

        document.add(new Paragraph(" "));
        document.add(new Paragraph("Articles : ", ff));
        document.add(new Paragraph(" "));
        PdfPTable arts = new PdfPTable(3);
        arts.setWidthPercentage(100);
        PdfPCell code = new PdfPCell(new Phrase("Code Article", ff));
        PdfPCell des = new PdfPCell(new Phrase("Designation", ff));
        PdfPCell qte = new PdfPCell(new Phrase("Quantité", ff));

        arts.addCell(code);
        arts.addCell(des);
        arts.addCell(qte);

        for (ArticleQuantite ar : listOfArticles) {

            arts.addCell(new PdfPCell(new Phrase(ar.article.getCodeArticle())));
            arts.addCell(new Phrase(ar.article.getDesignation()));
            arts.addCell(new PdfPCell(new Phrase(ar.quantite + "")));

        }

        arts.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
        arts.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
        arts.getDefaultCell().setFixedHeight(100);

        document.add(arts);

        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));

        PdfPTable signs = new PdfPTable(3);
        signs.setWidthPercentage(100);
        PdfPCell eme = new PdfPCell(new Phrase("Signature de l'emetteur: "));
        PdfPCell chauf = new PdfPCell(new Phrase("Signature du Chauffeur: "));
        PdfPCell rec = new PdfPCell(new Phrase("Signature du récepteur: "));

        signs.addCell(eme);
        signs.addCell(chauf);
        signs.addCell(rec);

        PdfPCell eme_empty = new PdfPCell(new Phrase(""));
        PdfPCell chauf_empty = new PdfPCell(new Phrase(" "));
        PdfPCell rec_empty = new PdfPCell(new Phrase(" "));

        eme_empty.setFixedHeight(80);
        chauf_empty.setFixedHeight(80);
        rec_empty.setFixedHeight(80);

        signs.addCell(eme_empty);
        signs.addCell(chauf_empty);
        signs.addCell(rec_empty);

        arts.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
        arts.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
        arts.getDefaultCell().setFixedHeight(100);

        document.add(signs);

        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));

        document.close();

        codeCurrent = "/files/documentsStock" + nomFichier;

        System.out.println(codeCurrent);
        System.out.println("sortie");

    }

    private boolean isNotReadyToShowBon = true;

    public boolean isIsNotReadyToShowBon() {
        return isNotReadyToShowBon;
    }

    public void setIsNotReadyToShowBon(boolean isNotReadyToShowBon) {
        this.isNotReadyToShowBon = isNotReadyToShowBon;
    }

    public void processTransfer() {
        System.out.println("TRANSFER PROCESS TO CHANTIER : " + chantierToReaffect);

        //  Chantier chantierRea = chantierService.findById(chantierToReaffect);
        Article ar = processSelected.getTransferToManage().getArticleId();
        Double difQuan = processSelected.getTransferToManage().getQuantite() - processSelected.getTransferToManage().getQuantiteReception();
        System.out.println("dif Quan : " + difQuan);

//        if (chantierArticleQService.findByChantierArticle(ar.getId(), chantierRea.getId()) == null) {
//            System.out.println("l'article n'existe pas sur ce chantier");
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention!", "Cet article n'existe pas sur le chantier choisi."));
//
//        } else {
        //  ChantierArticleQ chantierArticleQFound = chantierArticleQService.findByChantierArticle(ar.getId(), chantierRea.getId());
        //   chantierArticleQService.updateChantierArticleQ(chantierArticleQFound, chantierArticleQFound.getQuantiteChantierStock() - difQuan);
        processSelected.setIsProcessed(true);
        transferStockManagerService.updateEntry(processSelected);
        transfersToProcess = transferStockManagerService.findAll(chantierService.findById(chantierGlobal));
        //  System.out.println("LA QUANTITE DE : " + difQuan + "WILL BE SOUTRAITE DU CHANTIER : " + chantierRea.getCode());
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Succès!", "ce Transfert à été régularisé avec succès."));

        //    }
    }

    public void updateArticles() {

        articlesInChantier = chantierArticleQService.findByChantierId(chantierGlobal);

    }

    /**
     * *************END OF ADD TRANSFER STOCK ****************
     */
    /**
     * *****************ADD AFFECTATION STOCK ************************
     */
    public void addAffectationStock() throws IOException, DocumentException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date1 = sdf.format(dateAffect);
        String date2 = sdf.format(new Date());

        if (date1.compareTo(date2) > 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "La date d'affectation n'est pas valide!"));
            return;
        } else if (date1.compareTo(date2) < 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "La date d'affectation n'est pas valide!"));
            return;
        }

        //save zoneArticleQ if not exists
        if (zoneArticleQService.findByZoneArticle(article.getId(), zoneAffectId, lotAffectId) == null) {
            zoneArticleQService.addZoneArticleQ(zoneArticleQ, article.getId(), zoneAffectId, lotAffectId, quantiteToAffect, chantierGlobal);
            //sauvegarde de l'affectation
            affectationStockService.saveAffectationStock(affectationStock, article.getId(), zoneAffectId, lotAffectId, quantiteToAffect, dateAffect, chantierGlobal);
        } else {
            ZoneArticleQ zoneArticleQFound = zoneArticleQService.findByZoneArticle(article.getId(), zoneAffectId, lotAffectId);
            zoneArticleQService.updateZoneArticleQ(zoneArticleQFound, zoneArticleQFound.getQuantiteZoneStock() + quantiteToAffect);
            affectationStockService.saveAffectationStock(affectationStock, article.getId(), zoneAffectId, lotAffectId, quantiteToAffect, dateAffect, chantierGlobal);

        }

        //update article en question
        ChantierArticleQ chantierArticleQFoundInit = chantierArticleQService.findByChantierArticle(article.getId(), chantierGlobal);
        chantierArticleQService.updateChantierArticleQ(chantierArticleQFoundInit, chantierArticleQFoundInit.getQuantiteChantierStock() - quantiteToAffect);
        if (chantierArticleQFoundInit.getQuantiteChantierStock() == 0) {
            System.out.println("stock épuisé");
        }

        // articlesInChantier = chantierArticleQService.findByFilters(searchCode.toUpperCase(), searchDes.toUpperCase(), searchFam.toUpperCase(), searchSFam.toUpperCase(), searchSSFam.toUpperCase(), chantierGlobal);
        rechercherArticles();
        //mettre a jour les articles sur chantier
        //articlesInChantier = chantierArticleQService.findByChantierId(chantierGlobal);
        info(article.getId(), zoneAffectId, lotAffectId);
        disableInputs();
        codeAff = affectationStock.getCodeAffectationStock();
        downLoad_Recu_Affectation(affectationStockService.findById(affectationStock.getCodeAffectationStock()));
        codeCurrent = "/files/documentsStock/bonAffectation" + codeAff + ".pdf";
        System.out.println("bon created pour affectation " + codeCurrent);

        // articlesInChantier = chantierArticleQService.findByChantierId(chantierGlobal);
    }

    public void showBon() throws IOException, DocumentException {
        //downLoad_Recu_Affectation(affectationStockService.findById(codeAff));
        codeCurrent = "/files/documentsStock/bonAffectation" + codeAff + ".pdf";
        System.out.println("bon created pour affectation " + codeCurrent);
        isBonGenerated = true;

    }

    public void disableInputs() {
        isInputDisabled = true;
        isCommandShown = false;
        isOKshown = true;
    }

    public void updateTableZQ() {
        articlesInChantier = chantierArticleQService.findByChantierId(chantierGlobal);
        zoneArticleQs = zoneArticleQService.findByArticleId(zoneArticleQSelected.getArticleId().getId(), chantierGlobal);
    }

    /**
     * *************END OF ADD AFFECTATION STOCK ****************
     */
    /**
     * *******************ADD CONSOMMATION STOCK*****************
     */
    public void addConsommationStock() {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date1 = sdf.format(dateConsommation);
        String date2 = sdf.format(new Date());

        if (date1.compareTo(date2) > 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "La date de consommation n'est pas valide!"));
            return;
        } else if (date1.compareTo(date2) < 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "La date de consommation n'est pas valide!"));
            return;
        }

        Article articleSelected = zoneArticleQSelected.getArticleId();
        ChantierArticleQ chaqq = chantierArticleQService.findByChantierArticle(articleSelected.getId(), chantierGlobal);

        //save consommation stock
        consommationStock.setDateReeleConso(new Date());
        consommationService.addConsommationStock(consommationStock, zoneArticleQSelected.getArticleId().getId(), zoneArticleQSelected.getZoneId().getIdZone(), zoneArticleQSelected.getLotId().getId(), quantiteToConsomme, dateConsommation, chantierGlobal);
        FonctionsWS.consommationWS(zoneArticleQSelected.getArticleId().getCodeArticle(), quantiteToConsomme.toString(), "12-02-2015 00:00:00", chantierService.findById(chantierGlobal).getCodeNovapaie());
        //update zoneArticleQ
        zoneArticleQService.updateZoneArticleQ(zoneArticleQSelected, zoneArticleQSelected.getQuantiteZoneStock() - quantiteToConsomme);

        if (zoneArticleQSelected.getQuantiteZoneStock() == 0) {
            System.out.println("quantité epuisée!");
            zoneArticleQService.removeZoneArticleQ(zoneArticleQSelected);
            if (zoneArticleQService.findByArticleId(articleSelected.getId(), chantierGlobal) == null && chaqq.getQuantiteChantierStock() == 0) {
                System.out.println("NO ZAQ EXISTS HERE");
                chantierArticleQService.removeChantierArticleQ(chaqq);
            }
        }
        //articlesInChantier = chantierArticleQService.findByChantierId(chantierGlobal);
        infoConsommation();
        disableInputs();
        rechercherArticles();
        // articlesInChantier = chantierArticleQService.findByChantierId(chantierGlobal);
        zoneArticleQs = zoneArticleQService.findByArticleId(zoneArticleQSelected.getArticleId().getId(), chantierGlobal);

    }

    /**
     * ***********END OF ADD CONSOMMATION
     * STOCK***************************************
     */
    /**
     * ***** RETOUR STOCK ************************************
     */
    public void returnStock() {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date1 = sdf.format(dateRetour);
        String date2 = sdf.format(new Date());

        if (date1.compareTo(date2) > 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "La date de retour n'est pas valide!"));
            return;
        } else if (date1.compareTo(date2) < 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "La date de retour n'est pas valide!"));
            return;
        }

        ChantierArticleQ chantierArticleQFoundInit = chantierArticleQService.findByChantierArticle(zoneArticleQSelected.getArticleId().getId(), chantierGlobal);
        chantierArticleQService.updateChantierArticleQ(chantierArticleQFoundInit, chantierArticleQFoundInit.getQuantiteChantierStock() + quantiteToReturn);

        //update zoneArticleQ
        zoneArticleQService.updateZoneArticleQ(zoneArticleQSelected, zoneArticleQSelected.getQuantiteZoneStock() - quantiteToReturn);

        // save retour stock
        retourService.addRetourStock(retourStock, zoneArticleQSelected.getArticleId().getId(), zoneArticleQSelected.getZoneId().getIdZone(), zoneArticleQSelected.getLotId().getId(), quantiteToReturn, dateRetour, chantierGlobal);

        if (zoneArticleQSelected.getQuantiteZoneStock() == 0) {
            System.out.println("quantité epuisée!");
            zoneArticleQService.removeZoneArticleQ(zoneArticleQSelected);
        }

        //update liste articles
        rechercherArticles();
        //  articlesInChantier = chantierArticleQService.findByFilters(searchCode.toUpperCase(), searchDes.toUpperCase(), searchFam.toUpperCase(), searchSFam.toUpperCase(), searchSSFam.toUpperCase(), chantierGlobal);

        //update list zoneAticleQ
        zoneArticleQs = zoneArticleQService.findByArticleId(zoneArticleQSelected.getArticleId().getId(), chantierGlobal);

        infoRetour();
        disableInputs();

    }

    /**
     * ********END OF RETOUR ARTICLE*************
     */
    public void recepStock() {

        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        Calendar cal3 = Calendar.getInstance();

        cal1.setTime(dateReceptionTransfert);
        cal2.setTime(new Date());
        cal3.setTime(transferSelected.getDateTransferStock());

        if (cal1.after(cal2) || cal1.before(cal3)) {
            //System.out.println("Date1 is after Date2");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "La date du reception n'est pas valide!"));
            return;
        }

        if (transferSelected.isIsRetour() == false) {

            /* CAS : QTE REC > QTE ENV */
            if (transferSelected.getQuantite() < quantiteRec) {

                transferStockService.receptTransfertStock(transferSelected, 1, true, quantiteRec, dateReceptionTransfert);

                /* MISE A JOUR DES QTE */
 /* CAS : ARTICLE N'EXISTE PAS SUR CHANTIER RECEPTEUR */
                if (chantierArticleQService.findByChantierArticle(transferSelected.getArticleId().getId(), transferSelected.getChantierRecepteurId().getId()) == null) {

                    /* ON CREE UN NOUVEAU ARTICLE SUR CHANTIER RECEPTEUR */
                    chantierArticleQService.addChantierArticleQ(chantierArticleQ, transferSelected.getArticleId().getId(), transferSelected.getChantierRecepteurId().getId(), quantiteRec, "", "", "", "", "");

                    /* DECREMENTE QTE ARTICLE CHANTIER EMETTEUR | PEUT ENTRAINER DES QTE NEGATIVES */
                    ChantierArticleQ chantierArticleQFound = chantierArticleQService.findByChantierArticle(transferSelected.getArticleId().getId(), transferSelected.getChantierEmetteurId().getId());
                    chantierArticleQService.updateChantierArticleQ(chantierArticleQFound, chantierArticleQFound.getQuantiteChantierStock() - quantiteRec);
                    chantierArticleQFound.setEstEnCoursDeTransfert(false);

                    /* REGULARISATION DU TRANSFERT :: SUR QTE */
                    transferStockManagerService.addEntry(new TransferStockManager(), transferSelected, "sur quantité", false);

                } /* CAS : ARTICLE EXISTE SUR CHANTIER RECEPTEUR */ else {


                    /* RECUPERATION ARTICLE SUR CHANTIER RECEPTEUR */
                    ChantierArticleQ chantierArticleQFound = chantierArticleQService.findByChantierArticle(transferSelected.getArticleId().getId(), transferSelected.getChantierRecepteurId().getId());

                    /* MISE A JOUR QTE SUR CHANTIER RECEPTEUR  */
                    chantierArticleQService.updateChantierArticleQ(chantierArticleQFound, chantierArticleQFound.getQuantiteChantierStock() + quantiteRec);

                    /* MISE A JOUR QTE ARTICLE SUR CHANTIER EMETTEUR */
                    ChantierArticleQ chantierArticleQFoundInitial = chantierArticleQService.findByChantierArticle(transferSelected.getArticleId().getId(), transferSelected.getChantierEmetteurId().getId());
                    chantierArticleQService.updateChantierArticleQ(chantierArticleQFoundInitial, chantierArticleQFoundInitial.getQuantiteChantierStock() - quantiteRec);
                    chantierArticleQFoundInitial.setEstEnCoursDeTransfert(false);

                    /* REGULARISATION TRANSFERT :: SUR QTE */
                    transferStockManagerService.addEntry(new TransferStockManager(), transferSelected, "sur quantité", false);
                }

                /* ENVOYER LA REQUETE DU WS TRANSFERT */
                DateFormat formatDate = new SimpleDateFormat("dd-MM-yyyy");
                String refQteWSTransfer = "";
                StringBuilder strBuilder = new StringBuilder(quantiteRec.toString());
                int index = strBuilder.indexOf(".");
                strBuilder.setCharAt(index, ',');
                String qteInWSStringFormat = strBuilder.toString();
                refQteWSTransfer = refQteWSTransfer.concat(transferSelected.getArticleId().getCodeArticle()).concat(";").concat(qteInWSStringFormat).concat("|");
                FonctionsWS.transfertWS(refQteWSTransfer, transferSelected.getChantierEmetteurId().getCodeNovapaie(), transferSelected.getChantierRecepteurId().getCodeNovapaie(), formatDate.format(transferSelected.getDateTransferStock()).concat(" 00:00:00"));
            } /* END OF QTE REC > QTE ENV */ /* CAS : QTE REC > QTE ENV */ else if (transferSelected.getQuantite() > quantiteRec) {

                transferStockService.receptTransfertStock(transferSelected, 1, true, quantiteRec, dateReceptionTransfert);

                /* ARTICLE N'EXISTE PAS SUR CHANTIER RECEPTEUR */
                if (chantierArticleQService.findByChantierArticle(transferSelected.getArticleId().getId(), transferSelected.getChantierRecepteurId().getId()) == null) {

                    /* CREATION DE L'ARTICLE SUR CHANTIER RECPTEUR */
                    chantierArticleQService.addChantierArticleQ(chantierArticleQ, transferSelected.getArticleId().getId(), transferSelected.getChantierRecepteurId().getId(), quantiteRec, "", "", "", "", "");

                    /* RECUPERATION DE l'ARTICLE SUR CHANTIER EMETTEUR */
                    ChantierArticleQ chantierArticleQFound = chantierArticleQService.findByChantierArticle(transferSelected.getArticleId().getId(), transferSelected.getChantierEmetteurId().getId());

                    /* MISE A JOUR DE LA QTE DE L'ARTICLE SUR CHANTIER EMETTEUR */
                    chantierArticleQService.updateChantierArticleQ(chantierArticleQFound, chantierArticleQFound.getQuantiteChantierStock() - quantiteRec);
                    chantierArticleQFound.setEstEnCoursDeTransfert(false);

                    transferStockManagerService.addEntry(new TransferStockManager(), transferSelected, "sous quantité", false);

                } /* ARTICLE EXISTE SUR CHANTIER RECEPTEUR */ else {

                    /* RECUPERATION ARTICLE CHANTIER RECEPTEUR */
                    ChantierArticleQ chantierArticleQFound = chantierArticleQService.findByChantierArticle(transferSelected.getArticleId().getId(), transferSelected.getChantierRecepteurId().getId());

                    /* MISE A JOUR QTE ARTICLE CHANTIER RECEPTEUR */
                    chantierArticleQService.updateChantierArticleQ(chantierArticleQFound, chantierArticleQFound.getQuantiteChantierStock() + quantiteRec);

                    /* RECUPERATION ARTICLE CHANTIER EMETTEUR */
                    ChantierArticleQ chantierArticleQFoundInitial = chantierArticleQService.findByChantierArticle(transferSelected.getArticleId().getId(), transferSelected.getChantierEmetteurId().getId());

                    /* MISE A JOUR QTE ARTICLE CHANTIER EMETTEUR */
                    chantierArticleQService.updateChantierArticleQ(chantierArticleQFoundInitial, chantierArticleQFoundInitial.getQuantiteChantierStock() - quantiteRec);
                    chantierArticleQFoundInitial.setEstEnCoursDeTransfert(false);

                    /* REGULARISATION STOCK :: SOUS QTE */
                    transferStockManagerService.addEntry(new TransferStockManager(), transferSelected, "sous quantité", false);

                }

                /* LANCEMENT WS TRANSFERT */
                DateFormat formatDate = new SimpleDateFormat("dd-MM-yyyy");
                String refQteWSTransfer = "";
                StringBuilder strBuilder = new StringBuilder(quantiteRec.toString());
                int index = strBuilder.indexOf(".");
                strBuilder.setCharAt(index, ',');
                String qteInWSStringFormat = strBuilder.toString();
                refQteWSTransfer = refQteWSTransfer.concat(transferSelected.getArticleId().getCodeArticle()).concat(";").concat(qteInWSStringFormat).concat("|");
                FonctionsWS.transfertWS(refQteWSTransfer, transferSelected.getChantierEmetteurId().getCodeNovapaie(), transferSelected.getChantierRecepteurId().getCodeNovapaie(), formatDate.format(transferSelected.getDateTransferStock()).concat(" 00:00:00"));

            } /* CAS QTE ENV == QTE REC */ else if (Objects.equals(transferSelected.getQuantite(), quantiteRec)) {

                transferStockService.receptTransfertStock(transferSelected, 1, true, quantiteRec, dateReceptionTransfert);

                /* ARTICLE N'EXISTE PAS SUR CHANTIER RECEPTEUR */
                if (chantierArticleQService.findByChantierArticle(transferSelected.getArticleId().getId(), transferSelected.getChantierRecepteurId().getId()) == null) {

                    /* CREATION DE L'ARTICLE SUR CHANTIER RECPTEUR */
                    chantierArticleQService.addChantierArticleQ(chantierArticleQ, transferSelected.getArticleId().getId(), transferSelected.getChantierRecepteurId().getId(), quantiteRec, "", "", "", "", "");

                    /* RECUPERATION DE l'ARTICLE SUR CHANTIER EMETTEUR */
                    ChantierArticleQ chantierArticleQFound = chantierArticleQService.findByChantierArticle(transferSelected.getArticleId().getId(), transferSelected.getChantierEmetteurId().getId());

                    /* MISE A JOUR DE LA QTE DE L'ARTICLE SUR CHANTIER EMETTEUR */
                    chantierArticleQService.updateChantierArticleQ(chantierArticleQFound, chantierArticleQFound.getQuantiteChantierStock() - quantiteRec);
                    chantierArticleQFound.setEstEnCoursDeTransfert(false);

                } /* ARTICLE EXISTE SUR CHANTIER RECEPTEUR */ else {

                    /* RECUPERATION ARTICLE CHANTIER RECEPTEUR */
                    ChantierArticleQ chantierArticleQFound = chantierArticleQService.findByChantierArticle(transferSelected.getArticleId().getId(), transferSelected.getChantierRecepteurId().getId());

                    /* MISE A JOUR QTE ARTICLE CHANTIER RECEPTEUR */
                    chantierArticleQService.updateChantierArticleQ(chantierArticleQFound, chantierArticleQFound.getQuantiteChantierStock() + quantiteRec);

                    /* RECUPERATION ARTICLE CHANTIER EMETTEUR */
                    ChantierArticleQ chantierArticleQFoundInitial = chantierArticleQService.findByChantierArticle(transferSelected.getArticleId().getId(), transferSelected.getChantierEmetteurId().getId());

                    /* MISE A JOUR QTE ARTICLE CHANTIER EMETTEUR */
                    chantierArticleQService.updateChantierArticleQ(chantierArticleQFoundInitial, chantierArticleQFoundInitial.getQuantiteChantierStock() - quantiteRec);
                    chantierArticleQFoundInitial.setEstEnCoursDeTransfert(false);
                }

                /* LANCEMENT WS TRANSFERT */
                DateFormat formatDate = new SimpleDateFormat("dd-MM-yyyy");
                String refQteWSTransfer = "";
                StringBuilder strBuilder = new StringBuilder(quantiteRec.toString());
                int index = strBuilder.indexOf(".");
                strBuilder.setCharAt(index, ',');
                String qteInWSStringFormat = strBuilder.toString();
                refQteWSTransfer = refQteWSTransfer.concat(transferSelected.getArticleId().getCodeArticle()).concat(";").concat(qteInWSStringFormat).concat("|");
                FonctionsWS.transfertWS(refQteWSTransfer, transferSelected.getChantierEmetteurId().getCodeNovapaie(), transferSelected.getChantierRecepteurId().getCodeNovapaie(), formatDate.format(transferSelected.getDateTransferStock()).concat(" 00:00:00"));

            }

        } else {

            transferStockService.receptTransfertStock(transferSelected, 1, true, transferSelected.getQuantite(), dateReceptionTransfert);
            //save if not exists chantierArticleQ
            if (chantierArticleQService.findByChantierArticle(transferSelected.getArticleId().getId(), transferSelected.getChantierRecepteurId().getId()) == null) {
                chantierArticleQService.addChantierArticleQ(chantierArticleQ, transferSelected.getArticleId().getId(), transferSelected.getChantierRecepteurId().getId(), transferSelected.getQuantite(), "", "", "", "", "");
            } else {
                ChantierArticleQ chantierArticleQFound = chantierArticleQService.findByChantierArticle(transferSelected.getArticleId().getId(), transferSelected.getChantierRecepteurId().getId());
                chantierArticleQService.updateChantierArticleQ(chantierArticleQFound, chantierArticleQFound.getQuantiteChantierStock() + transferSelected.getQuantite());
            }

        }

        receptions = transferStockService.findByChantierRecepteur(chantierGlobal);
        isOKshown = true;
        isCommandShown = false;
        infoRet();
    }

    TransferStockManager processSelected = new TransferStockManager();

    public TransferStockManager getProcessSelected() {
        return processSelected;
    }

    public void setProcessSelected(TransferStockManager processSelected) {
        this.processSelected = processSelected;
    }

    public void trait(TransferStockManager tr) {
        chantiersTrait.clear();
        processSelected = tr;
        chantiersTrait.add(tr.getTransferToManage().getChantierEmetteurId());
        chantiersTrait.add(tr.getTransferToManage().getChantierRecepteurId());
    }

    public void inVars(TransferStock transferToRec) {
        transferSelected = transferToRec;
        isOKshown = false;
        isCommandShown = true;
        transferSelectedBySearchId = null;
        quantiteRec = null;
        dateReceptionTransfert = new Date();
        transfersSelectedBySearch = new LinkedList<>();
        codeCurrent = convertToDoubleDecimalsPopupTr(transferSelected.getQuantite());
    }

    public void returnTransfer() throws IOException, DocumentException {

        System.out.println("le transfert to return est : " + transferSelected.getArticleId().getDesignation());
        System.out.println("raison to return is : " + reasonForRetour);
        transferStock = new TransferStock();

        int referenceTr = 0;
        if (transferStockService.findAll() == null) {
            System.out.println("first transfer to add");
            referenceTr = 1;
        } else {
            System.out.println("last transfer code was " + transferStockService.findAll().get(transferStockService.findAll().size() - 1).getReferenceTransfer());
            referenceTr = (transferStockService.findAll().get(transferStockService.findAll().size() - 1).getReferenceTransfer()) + 1;
        }

        transferStockService.retourTransfertStock(transferSelected, 3, transferSelected.getQuantite() - transferSelected.getQuantiteReception(), dateRetour);
        //transferStockService.addTransferStock(transferStock, transferSelected.getArticleId().getCodeArticle(), transferSelected.getChantierRecepteurId().getId(), transferSelected.getChantierEmetteurId().getId(), quantiteTransferToReturn, transferSelected.getQuantiteReception(), 2, dateReceptionTransfert, true, true, false, referenceTr);

        //save if not exists chantierArticleQ
        if (chantierArticleQService.findByChantierArticle(transferSelected.getArticleId().getId(), transferSelected.getChantierEmetteurId().getId()) == null) {

            chantierArticleQService.addChantierArticleQ(chantierArticleQ, transferSelected.getArticleId().getId(), transferSelected.getChantierEmetteurId().getId(), transferSelected.getQuantite() - transferSelected.getQuantiteReception(), "", "", "", "", "");

            ChantierArticleQ chantierArticleQFoundR = chantierArticleQService.findByChantierArticle(transferSelected.getArticleId().getId(), transferSelected.getChantierRecepteurId().getId());

        } else {

            ChantierArticleQ chantierArticleQFound = chantierArticleQService.findByChantierArticle(transferSelected.getArticleId().getId(), transferSelected.getChantierEmetteurId().getId());
            chantierArticleQService.updateChantierArticleQ(chantierArticleQFound, chantierArticleQFound.getQuantiteChantierStock() + transferSelected.getQuantite() - transferSelected.getQuantiteReception());
        }

        receptions = transferStockService.findByChantierRecepteur(chantierGlobal);
        isOKshown = true;
        isCommandShown = false;
        infoRetour();
        downLoad_Recu_Retour(transferSelected);
        if (reasonForRetour.compareToIgnoreCase("sous") == 0) {
            // downloadBonRet(transferSelected);

        }

        if (reasonForRetour.compareToIgnoreCase("sur") == 0) {
            System.out.println("sur quantité");
        }

        codeCurrent = "/files/documentsStock/bonRetour" + transferSelected.getCodeTransfertStock() + ".pdf";
//        articlesInChantier = chantierArticleQService.findByChantierId(chantierGlobal);
        //   retoursTransfer = transferStockService.findRByChantierEmetteur(chantierGlobal);
        // transfers = transferStockService.findByChantierEmetteur(chantierGlobal);
        //   receptions = transferStockService.findByChantierRecepteur(chantierGlobal);
//           Thread.sleep(100);
//           downLoad_Recu_Retour(transferSelected);

    }

    public void initTransferM() {
        if (listOfAlreadyTransferedArticles.contains(chantierArticleQToTransferM.getArticleId())) {
            System.out.println("ARTICLE ALREADY ADDED");
            chantierArticleQToTransferM = new ChantierArticleQ();
            infoExists();

        } else {
            articleToTransferM = chantierArticleQToTransferM.getArticleId();
            codeCurrent = convertToDoubleDecimalsPopupTr(chantierArticleQToTransferM.getQuantiteChantierStock());
            designationToSearch2 = articleToTransferM.getDesignation();
            uniteToSearch = articleToTransferM.getUnite();
        }

    }

    public void initAddVars() {

        designationToSearch2 = "";
        codeCurrent = "";
        chantierArticleQToTransferM = new ChantierArticleQ();
        quantiteToTransferM = null;
        uniteToSearch = "";

    }

    public String toFrDa(Date d) {

        return dateFormat.format(d);

    }

    public void initTransferMList() {
        System.out.println("full:" + listOfArticleQuantite);
        listOfArticleQuantite = new LinkedList<>();
        listOfAlreadyTransferedArticles = new LinkedList<>();
        isReadyToTransfer = false;
        System.out.println("emptied:" + listOfArticleQuantite);
    }

    List<ArticleQuantite> listOfArticleQuantite = new LinkedList();
    ArticleQuantite articleQuantiteToTRansferM = new ArticleQuantite();

    public ArticleQuantite getArticleQuantiteToTRansferM() {
        return articleQuantiteToTRansferM;
    }

    public void setArticleQuantiteToTRansferM(ArticleQuantite articleQuantiteToTRansferM) {
        this.articleQuantiteToTRansferM = articleQuantiteToTRansferM;
    }

    public List<ArticleQuantite> getListOfArticleQuantite() {
        return listOfArticleQuantite;
    }

    public void setListOfArticleQuantite(List<ArticleQuantite> listOfArticleQuantite) {
        this.listOfArticleQuantite = listOfArticleQuantite;
    }

    List<Article> listOfAlreadyTransferedArticles = new LinkedList<>();

    public void addToListOfTransferM() {
//     listOfArticlesToTransferM.add(chantierArticleQToTransferM);
//     listOfQuantitiesToTransferM.add(quantiteToTransferM);
        articleQuantiteToTRansferM = new ArticleQuantite(quantiteToTransferM, chantierArticleQToTransferM.getArticleId(), chantierArticleQToTransferM.getQuantiteChantierStock());
        listOfArticleQuantite.add(articleQuantiteToTRansferM);
        listOfAlreadyTransferedArticles.add(chantierArticleQToTransferM.getArticleId());
        qtePopup = convertToDoubleDecimals(chantierArticleQToTransferM.getQuantiteChantierStock());
        // chantierArticleQToTransferM = new ChantierArticleQ();
        isReadyToTransfer = true;
        initAddVars();
        infoAddedTRM();

    }

    public void addToListOfTransferMAfterEdit() {

        //int index = listOfArticleQuantite.indexOf(articleTRM2);
        listOfArticleQuantite.remove(articleTRM2);
        articleQuantiteToTRansferM = new ArticleQuantite(quantiteToTransferM, articleTRM2.article, chantierArticleQToTransferM.getQuantiteChantierStock());
        listOfArticleQuantite.add(articleQuantiteToTRansferM);
        infoAddedTRM();

    }

    public void loadArticlesByDes() {

    }

    public void initTransferMVariables() {
        System.out.println("list initialized!");
        listOfArticleQuantite = new LinkedList();
        System.out.println("list initialized!");
    }

    public void initDetailsTransfer(TransferStock transfer) {
        System.out.println("REFERENCE : " + transfer.getReferenceTransfer());
        listOfArticlesInTransfer = transferStockService.findByRef(transfer.getReferenceTransfer());

    }

    public void cancelTransfer(TransferStock trm) {
        transferToDelete = trm;
        List<TransferStock> listOfTransfersByRef = new LinkedList<>();
        listOfTransfersByRef = transferStockService.findByRef(transferToDelete.getReferenceTransfer());
        System.out.println("TRANSFERS BY REF:" + listOfTransfersByRef);
        for (TransferStock transferto : listOfTransfersByRef) {

            chaq = chantierArticleQService.findByChantierArticle(transferto.getArticleId().getId(), transferto.getChantierEmetteurId().getId());
            System.out.println(transferto.getQuantite());
            System.out.println(chaq.getQuantiteChantierStock());
            System.out.println(chaq.getQuantiteChantierStock() + transferto.getQuantite());
            chantierArticleQService.updateChantierArticleQ(chaq, chaq.getQuantiteChantierStock() + transferto.getQuantite());

            if (transferto.getStatusTransferId().getIdStatusTransfert() != 1) {
                chaq = chantierArticleQService.findByChantierArticle(transferto.getArticleId().getId(), transferto.getChantierRecepteurId().getId());
                chantierArticleQService.updateChantierArticleQ(chaq, chaq.getQuantiteChantierStock() - transferto.getQuantite());

            }
            transferStockService.removeTransferStock(transferto);
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, Message.STRING_TRANSFERT_SUCCESS, "Transfert Annulé"));

        // articlesInChantier = chantierArticleQService.findByChantierId(chantierGlobal);
        //mettre a jour la liste des transferts
        transfers = transferStockService.findByChantierEmetteur(chantierGlobal);
        //mettre a jour la liste des receptions
        // receptions = transferStockService.findByChantierRecepteur(chantierGlobal);
        //System.out.println(listOfTransfersByRef);

    }

    public void cancelTRM(TransferStock tr) {
        transferToDelete = tr;
        codebon = tr.getReferenceTransfer();
        ch = "code: " + codebon + ".";
        System.out.println(ch);
    }

    public void cancelTRMRec(TransferStock tr) {
        transferToDelete = tr;

    }

    public void initRetourTr(TransferStock transferSelectedb) {
        transferSelected = transferSelectedb;
    }

    //bon retour
    public void downLoad_Recu_Retour(TransferStock transferStock) throws IOException, DocumentException {
        System.out.println("entree");

        //  String chemin = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/document/");
        String chemin = ConstanteMb.getRepertoire() + "/files/documentsStock";
        // String chemin = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/files/resources/documentsStock/");
        Path folder = Paths.get(chemin);
        Files.createDirectories(folder);
        String nomFichier = "/bonRetour" + transferStock.getCodeTransfertStock() + ".pdf";
        File file = new File(chemin + nomFichier);
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(file));
        document.open();
        String chemin_Image = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/img/logo.png");
        Image image1 = Image.getInstance(chemin_Image);
        document.add(image1);
        Font f = new Font(FontFamily.TIMES_ROMAN, 20.0f, Font.UNDERLINE, BaseColor.BLACK);
        Paragraph paragraph = new Paragraph("Bon de Retour de Transfèrt N° " + transferStock.getCodeTransfertStock(), f);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        document.add(paragraph);

        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));

        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));

        document.add(new Paragraph(" Code Transfert Initial : " + transferStock.getReferenceTransfer()));
        document.add(new Paragraph(" "));
        document.add(new Paragraph(" Code Article : " + transferStock.getArticleId().getId()));
        document.add(new Paragraph(" "));
        document.add(new Paragraph(" Designation Article : " + transferStock.getArticleId().getDesignation()));
        document.add(new Paragraph(" "));
        document.add(new Paragraph(" Chantier Emetteur : " + transferStock.getChantierRecepteurId().getCode()));
        document.add(new Paragraph(" "));
        document.add(new Paragraph(" Chantier Client  : " + transferStock.getChantierEmetteurId().getCode()));
        document.add(new Paragraph(" "));
        document.add(new Paragraph(" Quantité Transférée Initiale : " + transferStock.getQuantite()));
        document.add(new Paragraph(" "));
        document.add(new Paragraph(" Quantité Retournée : " + transferStock.getQuantiteRetour()));

        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));
        if (reasonForRetour.compareTo("sous") == 0) {
            document.add(new Paragraph(" Commentaires : La quantité transférée réelement est inférieure à la quantité demandée (indiquée sur le bon de transfèrt)"));
        } else if (reasonForRetour.compareTo("sur") == 0) {
            document.add(new Paragraph(" Commentaires : La quantité transférée (indiquée sur le bon de transfèrt) est supérieure à la quantité demandée"));
        } else if (reasonForRetour.compareTo("non") == 0) {
            document.add(new Paragraph(" Commentaires : l'article transféré n'est pas conforme à l'article initialement demandé!"));
        }
        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        document.add(new Paragraph(" Transfert le : " + dateFormat.format(transferStock.getDateTransferStock())));
        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));

        document.add(new Paragraph("Signature  récépteur :"));
        Paragraph paragraph_emetteur = new Paragraph("Signature de l'émetteur :");
        paragraph_emetteur.setAlignment(Element.ALIGN_RIGHT);
        document.add(paragraph_emetteur);
        document.close();

        infoRetour();

    }

    // bon d'affectation
    public void downLoad_Recu_Affectation(AffectationStock affectationStock) throws IOException, DocumentException {
        System.out.println("entree");

        String chemin = ConstanteMb.getRepertoire() + "/files/documentsStock";
        //String chemin = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/document/");
        // String chemin = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/files/resources/documentsStock/");
        Path folder = Paths.get(chemin);
        Files.createDirectories(folder);
        String nomFichier = "/bonAffectation" + affectationStock.getCodeAffectationStock() + ".pdf";
        File file = new File(chemin + nomFichier);

        Document document = new Document();

        PdfWriter.getInstance(document, new FileOutputStream(file));

        /* START OF NEW */
 /* START OF OLD */
        document.open();
        String chemin_Image = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/img/logo.png");
        Image image1 = Image.getInstance(chemin_Image);
        document.add(image1);
        Font f = new Font(FontFamily.HELVETICA, 18.0f, Font.BOLD, BaseColor.BLACK);
        Font ff = new Font(FontFamily.HELVETICA, 12.0f, Font.BOLD, BaseColor.BLACK);
        Font fb = new Font(FontFamily.HELVETICA, 12.0f, Font.NORMAL, BaseColor.BLACK);
        Paragraph paragraph = new Paragraph("Bon d'affectation", f);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        document.add(new Paragraph(" "));

        document.add(paragraph);

        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        PdfPTable tx = new PdfPTable(2);
        tx.setWidthPercentage(100);
        Phrase ph = new Phrase("Date: ", ff);
        Phrase ph2 = new Phrase(dateFormat.format(affectationStock.getDateeffectStock()));
        Paragraph p = new Paragraph();
        p.add(ph);
        p.add(ph2);
        PdfPCell cellFive = new PdfPCell();

        cellFive.addElement(p);

        PdfPCell cellTree = new PdfPCell(makePara("N° Bon : ", affectationStock.getCodeAffectationStock().toString()));

        cellTree.setBorder(Rectangle.NO_BORDER);
        cellFive.setBorder(Rectangle.NO_BORDER);

        tx.addCell(cellFive);
        tx.addCell(cellTree);

        tx.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        tx.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        tx.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
        tx.getDefaultCell().setFixedHeight(70);
        document.add(tx);

//        PdfPTable t = new PdfPTable(5);
//        t.setWidthPercentage(100);
//
//        PdfPCell cellFive = new PdfPCell(new Phrase("Date: ", ff));
//        PdfPCell cellFiveDate = new PdfPCell(new Phrase(dateFormat.format(affectationStock.getDateeffectStock())));
//
//        PdfPCell spacer = new PdfPCell(new Paragraph(""));
//        PdfPCell cellTree = new PdfPCell(new Phrase("Numero de Bon: ", ff));
//        PdfPCell cellFiveNum = new PdfPCell(new Phrase(affectationStock.getCodeAffectationStock()));
//
//        cellTree.setBorder(Rectangle.NO_BORDER);
//        spacer.setBorder(Rectangle.NO_BORDER);
//        cellFive.setBorder(Rectangle.NO_BORDER);
//
//        cellFiveDate.setBorder(Rectangle.NO_BORDER);
//        cellFiveNum.setBorder(Rectangle.NO_BORDER);
//
//        t.addCell(cellFive);
//        t.addCell(cellFiveDate);
//        t.addCell(spacer);
//        t.addCell(cellTree);
//        t.addCell(cellFiveNum);
//
//        t.getDefaultCell().setBorder(Rectangle.NO_BORDER);
//        //t.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
//        // t.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
//        t.getDefaultCell().setFixedHeight(80);
//
//        document.add(t);
        document.add(new Paragraph(" "));

        PdfPTable txx = new PdfPTable(2);
        txx.setWidthPercentage(100);
        PdfPCell cellFivex = new PdfPCell(makePara("Chantier: ", chantierService.findById(chantierGlobal).getCode()));
        PdfPCell cellTreex = new PdfPCell(makePara("Zone : ", affectationStock.getZoneId().getLibeleZone()));

        cellTreex.setBorder(Rectangle.NO_BORDER);
        cellFivex.setBorder(Rectangle.NO_BORDER);

        txx.addCell(cellFivex);
        txx.addCell(cellTreex);

        txx.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        txx.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        txx.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
        txx.getDefaultCell().setFixedHeight(70);
        document.add(txx);
//
//        PdfPTable tt = new PdfPTable(5);
//        tt.setWidthPercentage(100);
//        PdfPCell cellFivet = new PdfPCell(new Phrase("Chantier: ", ff));
//        PdfPCell cellCh = new PdfPCell(new Phrase(chantierService.findById(chantierGlobal).getCode()));
//
//        PdfPCell cellTreet = new PdfPCell(new Phrase("Zone: "));
//        PdfPCell cellZone = new PdfPCell(new Phrase(affectationStock.getZoneId().getLibeleZone()));
//
//        cellTreet.setBorder(Rectangle.NO_BORDER);
//        cellFivet.setBorder(Rectangle.NO_BORDER);
//        cellCh.setBorder(Rectangle.NO_BORDER);
//        cellZone.setBorder(Rectangle.NO_BORDER);
//        tt.addCell(cellFivet);
//        tt.addCell(cellCh);
//        tt.addCell(spacer);
//        tt.addCell(cellTreet);
//        tt.addCell(cellZone);
//
//        tt.getDefaultCell().setBorder(Rectangle.NO_BORDER);
//        // tt.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
//        tt.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
//        tt.getDefaultCell().setFixedHeight(120);
//        tt.getDefaultCell().setLeading(50, 2);
//        document.add(tt);
        document.add(new Paragraph(" "));

        document.add(makePara(" Lot: ", affectationStock.getLotId().getLibelle()));

        document.add(new Paragraph(" "));

        PdfPTable txxx = new PdfPTable(2);
        txxx.setWidthPercentage(100);
        PdfPCell cellFivexx = new PdfPCell(makePara("Article: ", affectationStock.getArticleId().getDesignation()));
        PdfPCell cellTreexx = new PdfPCell(makePara("Quantité : ", affectationStock.getQuantite().toString() + " " + affectationStock.getArticleId().getUnite()));

        cellTreexx.setBorder(Rectangle.NO_BORDER);
        cellFivexx.setBorder(Rectangle.NO_BORDER);

        txxx.addCell(cellFivexx);
        txxx.addCell(cellTreexx);

        txxx.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        txxx.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        txxx.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
        txxx.getDefaultCell().setFixedHeight(70);
        document.add(txxx);

//        PdfPTable r = new PdfPTable(3);
//        r.setWidthPercentage(100);
//        PdfPCell cellFivettr = new PdfPCell(new Phrase("Article: " + affectationStock.getArticleId().getDesignation()));
//        PdfPCell cellFivettrr = new PdfPCell(new Phrase("Quantité : " + affectationStock.getQuantite()));
//
//        cellFivettr.setBorder(Rectangle.NO_BORDER);
//        cellFivettrr.setBorder(Rectangle.NO_BORDER);
//        r.addCell(cellFivettr);
//       // r.addCell(spacer);
//        r.addCell(cellFivettrr);
//
//        r.getDefaultCell().setBorder(Rectangle.NO_BORDER);
//        //r.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
//        r.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
//        r.getDefaultCell().setFixedHeight(80);
//        document.add(r);
        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));

        PdfPTable t4s = new PdfPTable(2);
        t4s.setWidthPercentage(100);
        PdfPCell cellFives = new PdfPCell(new Phrase("Signature de l'emetteur: "));
        PdfPCell cellTrees = new PdfPCell(new Phrase("Signature du récepteur: "));

        cellTrees.setBorder(Rectangle.NO_BORDER);
        cellFives.setBorder(Rectangle.NO_BORDER);

        t4s.addCell(cellFives);
        t4s.addCell(cellTrees);

        t4s.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        t4s.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        t4s.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
        t4s.getDefaultCell().setFixedHeight(70);
        document.add(t4s);

//        PdfPTable r5 = new PdfPTable(3);
//        r5.setWidthPercentage(100);
//        PdfPCell cellFivettr5 = new PdfPCell(new Phrase("Signature Emetteur"));
//        PdfPCell cellFivettr55 = new PdfPCell(new Phrase("Signature Récepteur"));
//
//        cellFivettr5.setBorder(Rectangle.NO_BORDER);
//        cellFivettr55.setBorder(Rectangle.NO_BORDER);
//        r5.addCell(cellFivettr5);
//        //  r5.addCell(spacer);
//        r5.addCell(cellFivettr55);
//
//        r5.getDefaultCell().setBorder(Rectangle.NO_BORDER);
//        //  r5.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
//        r5.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
//        r5.getDefaultCell().setFixedHeight(100);
//        document.add(r5);
//        
//
//        PdfPTable table = new PdfPTable(6);
//        // the cell object
//        PdfPCell cell;
//        // we add a cell with colspan 3
//        cell = new PdfPCell(new Phrase("Date: " + dateFormat.format(affectationStock.getDateeffectStock())));
//        cell.setColspan(2);
//        cell.setBorder(Rectangle.NO_BORDER);
//        table.addCell(cell);
//
//        cell = new PdfPCell(new Phrase(""));
//        cell.setColspan(2);
//        cell.setBorder(Rectangle.NO_BORDER);
//        table.addCell(cell);
//
//        cell = new PdfPCell(new Phrase("Numero de Bon: " + affectationStock.getCodeAffectationStock()));
//        cell.setColspan(2);
//        cell.setBorder(Rectangle.NO_BORDER);
//        table.addCell(cell);
//
//        cell = new PdfPCell(new Phrase(""));
//        cell.setColspan(6);
//        cell.setBorder(Rectangle.NO_BORDER);
//        table.addCell(cell);
//
//        cell = new PdfPCell(new Phrase("Chantier: " + chantierService.findById(chantierGlobal).getCode()));
//        cell.setColspan(2);
//        cell.setBorder(Rectangle.NO_BORDER);
//
//        table.addCell(cell);
//
//        cell = new PdfPCell(new Phrase(""));
//        cell.setColspan(2);
//        cell.setBorder(Rectangle.NO_BORDER);
//        table.addCell(cell);
//
//        cell = new PdfPCell(new Phrase("Zone: " + affectationStock.getZoneId().getLibeleZone()));
//        cell.setColspan(2);
//        cell.setBorder(Rectangle.NO_BORDER);
//        table.addCell(cell);
//
//        cell = new PdfPCell(new Phrase(""));
//        cell.setColspan(6);
//        cell.setBorder(Rectangle.NO_BORDER);
//        table.addCell(cell);
//
//        cell = new PdfPCell(new Phrase(" Lot: " + affectationStock.getLotId().getLibelle()));
//        cell.setColspan(2);
//        cell.setBorder(Rectangle.NO_BORDER);
//        table.addCell(cell);
//
//        cell = new PdfPCell(new Phrase(""));
//        cell.setColspan(2);
//        cell.setBorder(Rectangle.NO_BORDER);
//        table.addCell(cell);
//
//        cell = new PdfPCell(new Phrase("Article: " + affectationStock.getArticleId().getDesignation()));
//        cell.setColspan(2);
//        cell.setBorder(Rectangle.NO_BORDER);
//        table.addCell(cell);
//
//        cell = new PdfPCell(new Phrase(""));
//        cell.setColspan(6);
//        cell.setBorder(Rectangle.NO_BORDER);
//        table.addCell(cell);
//        cell = new PdfPCell(new Phrase(""));
//        cell.setColspan(6);
//        cell.setBorder(Rectangle.NO_BORDER);
//        table.addCell(cell);
//
//        cell = new PdfPCell(new Phrase(" Signature de l'emetteur: "));
//        cell.setColspan(2);
//        cell.setBorder(Rectangle.NO_BORDER);
//        table.addCell(cell);
//
//        cell = new PdfPCell(new Phrase(""));
//        cell.setColspan(2);
//        cell.setBorder(Rectangle.NO_BORDER);
//        table.addCell(cell);
//
//        cell = new PdfPCell(new Phrase("Signature du récepteur:  "));
//        cell.setColspan(2);
//        cell.setBorder(Rectangle.NO_BORDER);
//        table.addCell(cell);
//
//        document.add(table);
        document.add(new Paragraph(" "));

        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));

//        document.add(new Paragraph("Signature  récépteur :"));
//        Paragraph paragraph_emetteur = new Paragraph("Signature de l'émetteur :");
//        paragraph_emetteur.setAlignment(Element.ALIGN_RIGHT);
//        document.add(paragraph_emetteur);
        document.close();

        System.out.println("sortie");

    }

    public void sendJava() {
        TGCCMailSender.sendSSL();
    }

    public void sendMailTGCC() {

        // TGCCMailSender.sendMail();
        ApplicationContext context
                = new ClassPathXmlApplicationContext("mail.xml");

        SendEmail sm = (SendEmail) context.getBean("sendEmail");
        sm.sendMail("tgcc@gmail.com", "iraamane.abdellah@gmail.com", "test sujet", "les message de l'email");
    }
    
    public void retournerTransferNonConform(TransferStock transferToReturn) {
        System.out.println("retour de commande non conforme ====================== " + transferToReturn.getArticleId().getDesignation());
        transferStockService.retourTransfertStock(transferToReturn, 4, transferToReturn.getQuantite(), new Date());

        if (chantierArticleQService.findByChantierArticle(transferToReturn.getArticleId().getId(), transferToReturn.getChantierEmetteurId().getId()) == null) {

            chantierArticleQService.addChantierArticleQ(chantierArticleQ, transferToReturn.getArticleId().getId(), transferToReturn.getChantierEmetteurId().getId(), transferToReturn.getQuantite(), "", "", "", "", "");

            ChantierArticleQ chantierArticleQFoundR = chantierArticleQService.findByChantierArticle(transferToReturn.getArticleId().getId(), transferToReturn.getChantierRecepteurId().getId());

        } else {

            ChantierArticleQ chantierArticleQFound = chantierArticleQService.findByChantierArticle(transferToReturn.getArticleId().getId(), transferToReturn.getChantierEmetteurId().getId());
            chantierArticleQService.updateChantierArticleQ(chantierArticleQFound, chantierArticleQFound.getQuantiteChantierStock() + transferToReturn.getQuantite());
        }

    }

    public String onFlowProcess(FlowEvent event) {

        return event.getNewStep();

    }

    @PostConstruct
    public void init() {
        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        transferStockService = ctx.getBean(TransferStockService.class);
        affectationStockService = ctx.getBean(AffectationStockService.class);
        articleService = ctx.getBean(ArticleService.class);
        zoneServices = ctx.getBean(ZoneServices.class);
        consommationService = ctx.getBean(ConsommationStockService.class);
        chantierService = ctx.getBean(ChantierService.class);
        retourService = ctx.getBean(RetourStockService.class);
        zoneArticleQService = ctx.getBean(ZoneArticleQService.class);
        chantierArticleQService = ctx.getBean(ChantierArticleQService.class);
        transferStockManagerService = ctx.getBean(TransferStockManagerService.class);
        activeTabTitle = "Affectations";
        isDetailable = true;
        isOKshown = false;
        isCommandShown = true;
        dateOfToday = date;
        dateAffect = new Date();
        dateTransfert = new Date();
        dateConsommation = new Date();
        dateRetour = new Date();
    }

    public void info(int articleId, int zoneId, int lotId) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, Message.AFFECT_SUCCESS, "affectation effectuée avec succès"));
    }

    public void infoExists() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, Message.STRING_ATT, Message.STRING_AJOUT_ARTICLE));
    }

    public void infoExistsCh() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, Message.STRING_ATT, "Veuillez Choisir un chantier different"));
    }

    public void infoAddedTRM() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, Message.STRING_AJOUT, Message.STRING_ARTICLE_TRANSFERT));
    }

    public void infoTR() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, Message.STRING_TRANSFERT_SUCCESS, Message.TRANSFERT_ARTICLE_BON_GENE));
    }

    public void infoRet() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, Message.STRING_RECEPTION, " "));
    }

    public void infoConsommation() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, Message.STRING_SUCCESS_RE, Message.STRING_CONSOMMATION_SUCCESS));
    }

    public void infoRetour() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, Message.STRING_RETOUR, Message.RETOUR_ARTICLE_SUCCESS));
    }

    public void addMessage(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void addArticle() {
        //articleService.addArticle(articleToAdd, zoneToAdd, chantierToAdd, familleToAdd, lotToAdd, sfamilleToAdd, typologieToAdd);
        articles = articleService.findAll();
    }

    public void hasRetourProcess() {
        System.out.println("CLICKED CHECKBOX : " + hasRetourValue);
    }

    public Paragraph makePara(String p1, String p2) {
        Paragraph p = new Paragraph();
        Font ff = new Font(FontFamily.HELVETICA, 12.0f, Font.BOLD, BaseColor.BLACK);
        Font fb = new Font(FontFamily.HELVETICA, 12.0f, Font.NORMAL, BaseColor.BLACK);
        p.add(new Phrase(p1, ff));
        p.add(new Phrase(p2, fb));
        return p;
    }

    public void updateCurrentZoneArticleQ(ZoneArticleQ zoneArticleQ) {
        this.zoneArticleQSelected = zoneArticleQ;
        quantiteToConsomme = null;
        dateConsommation = new Date();
        dateRetour = new Date();
        quantiteToReturn = null;
        isOKshown = false;
        isCommandShown = true;
        isInputDisabled = false;
    }

    public void removeArticleTRM(ArticleQuantite articleTRM) {
        listOfArticleQuantite.remove(articleTRM);
        listOfAlreadyTransferedArticles.remove(articleTRM.article);

        if (listOfArticleQuantite.size() == 0) {
            isReadyToTransfer = false;
        }
    }

    public void initArticleTRMToEdit(ArticleQuantite articleTr) {
        articleTRM2 = new ArticleQuantite(articleTr.quantite, articleTr.article, articleTr.quantiteTotalDispo);
        System.out.println("qte: " + articleTr.quantite);
        System.out.println("qte tt: " + articleTr.quantiteTotalDispo);
        articleTRM2 = articleTr;
        quantiteToTransferM = null;
    }

    public void testWS() {
        System.out.println("TESTING ARTICLES WS ....");
        //articleWSLaunch();
        FonctionsWS.articleWS("112620");
        System.out.println("END OF ARTICLES WS");
        System.out.println("STARTING MENSUEL WS");
        MensuelWSCallManager.mensuelWS("12-12-2015 18:00:00");
        System.out.println("DONE TESTING MENSUEL ....");
        System.out.println("STARTING ABSENCES");
        // MensuelWSCallManager.absenceWS("997", "02-11-2015 10:00:00", "03-11-2015 18:00:00", "maladie");
        System.out.println("FINISHED ABSENCES");
        System.out.println("STARTING TRANSFER");
        //transfertWS("GMAMBECOMAAPD27-2.5M;3", chantierService.findById(chantierGlobal).getCodeNovapaie(), chantierService.findById(chantierRecepteurId).getCodeNovapaie(), formatDate.format(dateTransfert).concat(" 00:00:00"));
        System.out.println("END OF TRANSFER");

        System.out.println("STARTING CONOMMATION");
        FonctionsWS.consommationWS("GMAMBECOMAAPD27-2.5M", "2", "02-11-2015 10:00:00", "CHAN0001");
        System.out.println("END OF CONSOMMATION");

    }

    public void confirmSynchro() {
        System.out.println("Syncing ......");
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        String s = dateFormat.format(dateOfToday);
        System.out.println(s);
        //rticleWSLaunch(chantierService.findById(chantierarticleWSLaunchGlobal).getCodeNovapaie());

    }

    public void onRowCancelRet(RowEditEvent event) {
        FacesMessage msg = new FacesMessage(Message.BACK_CANCEL, ((RetourStock) event.getObject()).getCodeRetourStock().toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void switchOperations() {

        if (operation.compareToIgnoreCase("1") == 0) {
            System.out.println("affectations selected combo ============");
            isStockTableRendered = true;
            isConsoTablerendered = false;
            isRetourTableRendered = false;

        } else if (operation.compareToIgnoreCase("2") == 0) {
            System.out.println("consommations selected combo ============");
            isStockTableRendered = false;
            isConsoTablerendered = true;
            isRetourTableRendered = false;
        } else if (operation.compareToIgnoreCase("3") == 0) {
            System.out.println("retours selected combo ============");
            isStockTableRendered = false;
            isConsoTablerendered = false;
            isRetourTableRendered = true;
        }

    }

    public void checkStockDivalto(ChantierArticleQ stockToVerify) {
        articleToCheck = chantierArticleQService.findByChantierArticle(stockToVerify.getArticleId().getId(), stockToVerify.getChantierId().getId());
        String article = articleToCheck.getArticleId().getCodeArticle();
        String chantier = articleToCheck.getChantierId().getCodeNovapaie();
        System.out.println("Article : " + article);
        System.out.println("Chantier : " + chantier);
        System.out.println("Quantité : " + articleToCheck.getQuantiteChantierStock());

        String qteDivaltoString = FonctionsWS.ecartStockWS(chantier, article);
        System.out.println(qteDivaltoString);
        String qteDivaltoStr = qteDivaltoString.substring(12, qteDivaltoString.indexOf('"', 12));
        String qteDivaltoDouble = qteDivaltoStr.replace(',', '.');
        Double qteDivalto = Double.parseDouble(qteDivaltoDouble);
        qteDivaStock = qteDivalto;
        System.out.println("Quantité Divalto : " + qteDivaStock);

    }

    public void initAffToDelete(AffectationStock af1) {
        affectationToDelete = af1;
    }

    public void initConsoToDelete(ConsommationStock conso) {
        consoToDelete = conso;
    }

    public void initRetourToDelete(RetourStock ret) {
        retourToDelete = ret;
    }

    public Double findTotalQteUpsit(ChantierArticleQ article) {
        Double qte = article.getQuantiteChantierStock();
        try {
            List<ZoneArticleQ> lzaq = zoneArticleQService.findByArticleId(article.getArticleId().getId(), chantierGlobal);
            for (ZoneArticleQ zaq : lzaq) {
                qte += zaq.getQuantiteZoneStock();
            }
            return qte;
        } catch (Exception e) {
            System.out.println("EXCEPTION HERE");

        }

        return qte;
    }

    /**
     * annuler affectation (affectations.xhtml)
     *
     **
     * @param aff
     */
    public void cancelAff(AffectationStock aff) {
        affectationToDelete = aff;
        zaq = zoneArticleQService.findByZoneArticle(affectationToDelete.getArticleId().getId(), affectationToDelete.getZoneId().getIdZone(), affectationToDelete.getLotId().getId());
        if (zaq != null) {
            if (affectationToDelete.getQuantite() > zaq.getQuantiteZoneStock()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, Message.STRING_ATT, Message.STRING_DELETE_AFFECT));

            } else {
                affectationStockService.removeAff(affectationToDelete);
                zoneArticleQService.updateZoneArticleQ(zaq, zaq.getQuantiteZoneStock() - affectationToDelete.getQuantite());

                if (zaq.getQuantiteZoneStock() == 0) {
                    zoneArticleQService.removeZoneArticleQ(zaq);
                }
                chaq = chantierArticleQService.findByChantierArticle(affectationToDelete.getArticleId().getId(), chantierGlobal);
                chantierArticleQService.updateChantierArticleQ(chaq, chaq.getQuantiteChantierStock() + affectationToDelete.getQuantite());
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, Message.AFFECTATION_CANCEL, Message.STRING_DELETE_AFFECT_S));

            }
            affectations = affectationStockService.findAllInChantier(chantierGlobal);
            articlesInChantier = chantierArticleQService.findByChantierId(chantierGlobal);
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, Message.STRING_ATT, Message.STRING_DELETE_AFFECT));
        }

    }

    public void onTabChange(TabChangeEvent event) {
        dateFrom = null;
        dateTo = null;
        activeTabTitle = event.getTab().getTitle();
        System.out.println("active tab : " + activeTabTitle);
        switch (activeTabTitle) {
            case "Consommations":
                System.out.println("charging consommations for chantier : " + chantierGlobal);
                consommations = consommationService.findByChantier(chantierGlobal);
                break;
            case "Affectations":
                System.out.println("charging affectations for chantier : " + chantierGlobal);
                affectations = affectationStockService.findAllInChantier(chantierGlobal);
                break;
            case "Reintegrations":
                System.out.println("charging reintegrations for chantier : " + chantierGlobal);
                retours = retourService.findByChantier(chantierGlobal);
                break;
            case "Transferts":
                System.out.println("charging transfers for chantier : " + chantierGlobal);
                transfers = transferStockService.findByChantierEmetteur(chantierGlobal);
                break;
            case "Receptions":
                System.out.println("charging receptions for chantier : " + chantierGlobal);
                receptions = transferStockService.findByChantierRecepteur(chantierGlobal);
                break;
            default:
                affectations = affectationStockService.findAllInChantier(chantierGlobal);
                break;
        }
//        FacesMessage msg = new FacesMessage("Tab Changed", "Active Tab: " + event.getTab().getTitle());
//        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void cancelRec(TransferStock receptionToCancel) {

        ChantierArticleQ chantierArticleQFound = chantierArticleQService.findByChantierArticle(receptionToCancel.getArticleId().getId(), receptionToCancel.getChantierRecepteurId().getId());
        chantierArticleQService.updateChantierArticleQ(chantierArticleQFound, chantierArticleQFound.getQuantiteChantierStock() - receptionToCancel.getQuantiteReception());
        transferStockService.cancelRec(receptionToCancel, 2);
    }

    /**
     * annuler consommation d'affectation (affectations.xhtml)
     *
     **
     * @param conso
     */
    public void cancelConso(ConsommationStock conso) {

        consoToDelete = conso;
        zoneArticleQ = new ZoneArticleQ();
        zaq = zoneArticleQService.findByZoneArticle(consoToDelete.getArticleId().getId(), consoToDelete.getZoneId().getIdZone(), consoToDelete.getLotId().getId());

        if (zaq == null) {
            zoneArticleQService.addZoneArticleQ(zoneArticleQ, consoToDelete.getArticleId().getId(), consoToDelete.getZoneId().getIdZone(), consoToDelete.getLotId().getId(), consoToDelete.getQuantite(), chantierGlobal);
        } else {
            zoneArticleQService.updateZoneArticleQ(zaq, zaq.getQuantiteZoneStock() + consoToDelete.getQuantite());
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, Message.CONSOMMATION_CANCEL, "consommation annulée avec succès!"));

        consommationService.removeConsommationStock(consoToDelete);
        consommations = consommationService.findByChantier(chantierGlobal);
//        zoneArticleQs = zoneArticleQService.findByChantier(chantierGlobal);

    }

    public void searchByDate() {
        DateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(formatDate.format(dateFrom));
        System.out.println(formatDate.format(dateTo));
        switch (activeTabTitle) {
            case "Consommations":
                System.out.println("charging consommations for chantier : " + chantierGlobal);
                consommations = consommationService.findByIntervalDate(chantierGlobal, dateFrom, dateTo);
                break;
            case "Affectations":
                System.out.println("charging affectations for chantier : " + chantierGlobal);
                affectations = affectationStockService.findByIntervalDate(chantierGlobal, dateFrom, dateTo);
                break;
            case "Reintegrations":
                System.out.println("charging reintegrations for chantier : " + chantierGlobal);
                retours = retourService.findByIntervalDate(chantierGlobal, dateFrom, dateTo);
                break;
            case "Transferts":
                System.out.println("charging transfers for chantier : " + chantierGlobal);
                transfers = transferStockService.findByIntervalDate(chantierGlobal, dateFrom, dateTo);
                break;
            default:
                affectations = affectationStockService.findAllInChantier(chantierGlobal);
                break;

        }

    }

    /**
     * annuler retour d'affectation (affectations.xhtml)
     *
     * @param ret retour a annulé
     */
    public void cancelRetour(RetourStock ret) {
        retourToDelete = ret;
        zaq = zoneArticleQService.findByZoneArticle(retourToDelete.getArticleId().getId(), retourToDelete.getZoneId().getIdZone(), retourToDelete.getLotId().getId());
        if (zaq == null) {
            zoneArticleQService.addZoneArticleQ(zoneArticleQ, retourToDelete.getArticleId().getId(), retourToDelete.getZoneId().getIdZone(), retourToDelete.getLotId().getId(), retourToDelete.getQuantite(), chantierGlobal);
        } else {
            zoneArticleQService.updateZoneArticleQ(zaq, zaq.getQuantiteZoneStock() + retourToDelete.getQuantite());
        }

        chaq = chantierArticleQService.findByChantierArticle(retourToDelete.getArticleId().getId(), chantierGlobal);

        if (chaq == null) {
            chantierArticleQ = new ChantierArticleQ();
            retourService.removeRetourStock(retourToDelete);
            chantierArticleQService.addChantierArticleQ(chantierArticleQ, retourToDelete.getArticleId().getId(), chantierGlobal, retourToDelete.getQuantite(), "", "", "", "", "");
        } else if (chaq.getQuantiteChantierStock() < retourToDelete.getQuantite()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, Message.STRING_ATT, Message.RETOUR_ERR_CANCEL));

        } else {
            chantierArticleQService.updateChantierArticleQ(chaq, chaq.getQuantiteChantierStock() - retourToDelete.getQuantite());
            retourService.removeRetourStock(retourToDelete);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, Message.RETOUR_ARTICLE_SUCCESS, "Retour annulé avec succès!"));

        }

//        zoneArticleQs = zoneArticleQService.findByChantier(chantierGlobal);
        retours = retourService.findByChantier(chantierGlobal);
//        articlesInChantier = chantierArticleQService.findByChantierId(chantierGlobal);

    }

    /**
     * Selection transfers -> transferts | retours dropdown 'transferts.xhtml' *
     */
    public void switchOperationsTransferRet() {

        if (operationTransferRet.compareToIgnoreCase("1") == 0) {
            transfers = transferStockService.findByChantierEmetteur(chantierGlobal);
            isRetShown = false;

        } else if (operationTransferRet.compareToIgnoreCase("2") == 0) {
            isRetShown = true;
            transfers = transferStockService.findRByChantierEmetteur(chantierGlobal);
        }

    }

    /**
     * selection Transferts | Receptions | Retours (transferts.xhtml) *
     */
    public void switchOperationsTransfer() {

        if (operationTransfer.compareToIgnoreCase("1") == 0) {

            isReceptionTableRendered = false;
            isTansferTableRendered = true;
            isRetourTransTableRendered = false;

        } else if (operationTransfer.compareToIgnoreCase("2") == 0) {

            isReceptionTableRendered = true;
            isTansferTableRendered = false;
            isRetourTransTableRendered = false;
        } else if (operationTransfer.compareToIgnoreCase("3") == 0) {

            isReceptionTableRendered = false;
            isTansferTableRendered = false;
            isRetourTransTableRendered = true;
        }

    }

    public void loadRetourInfo() {
        isOKshown = true;
        transfersSelectedBySearch = transferStockService.findById(transferSelectedBySearchId, chantierGlobal);
    }

    /**
     * * @handles la table des réceptions si le transfert selected deja recep.
     * == griser button rec. sinon permettre de recept. *
     */
    public void onRowSelect(SelectEvent event) {
        if (((TransferStock) event.getObject()).getQuantiteReception() > 0) {
            isRecYet = true;
        } else {
            isRecYet = false;
        }
    }

    /**
     * * @handles click sur les buttons de l'ffectation et details des
     * affectations d'un article
     * @param articleIt l'article en question
     * @param op si == 1 il s'agit d'une consultation des affectations sinon une
     * opération d'affectation
     */
    public void updateCurrentArticle(ChantierArticleQ articleIt, int op) {

        isBonGenerated = false;
        System.out.println(articleIt.getArticleId().getDesignation());
        articleIter = articleIt;
        this.article = articleIt.getArticleId();
        zonesByChantier = zoneServices.findByChantierID(chantierGlobal);
        chantierArticleQSelected = articleIt;
        qtePopup = convertToDoubleDecimals(chantierArticleQSelected.getQuantiteChantierStock());
        quantiteToAffect = null;
        dateAffect = new Date();
        if (op == 1) {
            zoneArticleQs = zoneArticleQService.findByArticleId(article.getId(), chantierGlobal);
        }
        System.out.println(article.getDesignation());

        isInputDisabled = false;
        isCommandShown = true;
        isOKshown = false;
    }

    public void rechercherArticlesInit() {
        searchCode = "";
        searchDes = "";
        searchFam = "";
        searchSFam = "";
        searchSSFam = "";
        articlesInChantier = chantierArticleQService.findByFilters(searchCode.toUpperCase(), searchDes.toUpperCase(), searchFam.toUpperCase(), searchSFam.toUpperCase(), searchSSFam.toUpperCase(), chantierGlobal);

    }

    public void rechercherArticles() {

        System.out.println("SEARCHING BY : " + searchCode + searchDes + searchFam + searchSFam + searchSSFam);

        articlesInChantier = chantierArticleQService.findByFilters(searchCode.toUpperCase(), searchDes.toUpperCase(), searchFam.toUpperCase(), searchSFam.toUpperCase(), searchSSFam.toUpperCase(), chantierGlobal);

        System.out.println("SIZE : " + articlesInChantier.size());

    }

    /**
     * controle sur le chantier global
     *
     * @param page la page en cours stock | operations chantier | transferts
     */
    public void globalChantierManager(int page) {
        System.out.println("value of chantier global : " + chantierGlobal);
        System.out.println("value of active tab : " + activeTabTitle);

        dateFrom = null;
        dateTo = null;

        switch (page) {
            case 1:
                switch (activeTabTitle) {
                    case "Consommations":
                        System.out.println("charging consommations for chantier : " + chantierGlobal);
                        consommations = consommationService.findByChantier(chantierGlobal);
                        break;
                    case "Affectations":
                        System.out.println("charging affectations for chantier : " + chantierGlobal);
                        affectations = affectationStockService.findAllInChantier(chantierGlobal);
                        break;
                    case "Reintegrations":
                        System.out.println("charging reintegrations for chantier : " + chantierGlobal);
                        retours = retourService.findByChantier(chantierGlobal);
                        break;
                    case "Transferts":
                        System.out.println("charging transfers for chantier : " + chantierGlobal);
                        transfers = transferStockService.findByChantierEmetteur(chantierGlobal);
                        break;
                    case "Receptions":
                        System.out.println("charging receptions for chantier : " + chantierGlobal);
                        receptions = transferStockService.findByChantierRecepteur(chantierGlobal);
                        break;
                    default:
                        affectations = affectationStockService.findAllInChantier(chantierGlobal);
                        break;
                }

            case 5:
                receptions = transferStockService.findByChantierRecepteur(chantierGlobal);
                break;
            case 6:
                retoursTransfer = transferStockService.findRByChantierRecepteur(chantierGlobal);
                break;
            case 20:
                transfersToProcess = transferStockManagerService.findAll(chantierService.findById(chantierGlobal));
                break;
            case 8:
                zonesByChantier = zoneServices.findByChantierID(chantierGlobal);
                articlesInChantier = chantierArticleQService.findByChantierId(chantierGlobal);
                break;
            default:
                break;
        }

        if (chantierGlobal != -1) {
            noChantierSelectedYet = false;
        } else {
            noChantierSelectedYet = true;
        }
    }

    public String callEcartWS(ChantierArticleQ toVerify) {
        String qteDivaltoString = FonctionsWS.ecartStockWS(toVerify.getChantierId().getCodeNovapaie(), toVerify.getArticleId().getCodeArticle());
        String qteDivaltoStr = qteDivaltoString.substring(12, qteDivaltoString.indexOf('"', 12));
        String qteDivaltoDouble = qteDivaltoStr.replace(',', '.');
        Double qteDivalto = Double.parseDouble(qteDivaltoDouble);
        return qteDivaltoDouble;
        //  testMensuel.Get_Chantier_By_Mensuel("01/09/2016", "1034511");
    }

    public String convertToDoubleDecimals(Double d) {

        DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.FRANCE);
        DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();

        symbols.setGroupingSeparator(' ');

        formatter.setDecimalFormatSymbols(symbols);
        // System.out.println(formatter.format(d));

        String s = formatter.format(d);
        return s;
    }

    public String convertToDoubleDecimalsPopup() {

        DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.FRANCE);
        DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();

        symbols.setGroupingSeparator(' ');
        formatter.setDecimalFormatSymbols(symbols);
        // System.out.println(formatter.format(d));

        String s = formatter.format(articleToCheck.getQuantiteChantierStock());
        return s;

    }

    public String convertToDoubleDecimalsPopupTr(Double d) {

        DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.FRANCE);
        DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();

        symbols.setGroupingSeparator(' ');
        formatter.setDecimalFormatSymbols(symbols);
        // System.out.println(formatter.format(d));

        String s = formatter.format(d);
        return s;

    }

    /**
     * CALL web service liste des articles populate table article
     *
     * @param chanId chantier courant
     *
     */
    public void articleWSLaunch() {

        int lengthOfReturnedJSONArray = 0;

        int iterationWSCall = 0;

        System.out.println("*************  FINDING LAST VENTILATION_ID IN DATABASE .... ");
        ChantierArticleQ q = chantierArticleQService.findLastIventilation();

        Integer idVentilation = (q != null && q.getIdVentilation() != null) ? q.getIdVentilation() : 0;

        System.out.println("LAST VENTILATION ID FOUND SUCCESSFULLY .... id : " + idVentilation);

        String jsonRes = "";
        JSONObject obj;
        JSONArray arr;

        System.out.println("Start Traitement WS");

        do {
            System.out.println(" ==== ++++  WS ENTREE VENTILATION_ID : " + idVentilation);
            System.out.println("calling ws ......... " + new Date());
            jsonRes = FonctionsWS.articleWS(idVentilation + "");
            System.out.println("finished ws!");
            if ("".equals(jsonRes)) {
                break;
            }

            System.out.println("****************ENTERING TRYCATCH");
            obj = new JSONObject(jsonRes);
            arr = obj.getJSONArray("Ventilations");
            for (int i = 0; i < arr.length(); i++) {
                String id = arr.getJSONObject(i).getString("idArticleDivalto");
                String chan = arr.getJSONObject(i).getString("Chantier");
                // String type = arr.getJSONObject(i).getString("Type");
                String idVentilationInit2 = arr.getJSONObject(i).getString("IdVentilation");
                String fam = arr.getJSONObject(i).getString("Libellé Famille1");
                String sfam = arr.getJSONObject(i).getString("Libellé Famille2");
                String ssfam = arr.getJSONObject(i).getString("Libellé Famille3");
                //  String des = arr.getJSONObject(i).getString("designationArticle");
                String typ = arr.getJSONObject(i).getString("typologieConsommation");
                //  String nature = arr.getJSONObject(i).getString("natureArticle");
                String isProrata = arr.getJSONObject(i).getString("Prorata");
                // String unite = arr.getJSONObject(i).getString("uniteArticle");
                String qteTotale = arr.getJSONObject(i).getString("Quantité").replaceAll("\\s", "");
                qteTotale = qteTotale.replaceAll(",", ".");
                Article articleWs = articleService.findByRef(id);
                chantieService = Module.ctx.getBean(ma.bservices.services.ChantierService.class);
                Chantier chantierWs = chantierService.getByAffaire(chan);
                System.out.println("get chantier Ws");
                Integer idVentilationInit = Integer.parseInt(idVentilationInit2);
                System.out.println(" ==== ++++ FROM WS VENTILATION_ID : " + idVentilationInit);
                if (articleWs == null) {
                    System.out.println(" ====================== ** ARTICLE NOT FOUND EXCEPTION : ** =============================== ");
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention!", "Problème de synchronisation des articles : article envoyé n'existe pas sur la base des article de upsit."));

                    return;
                    // articleService.addArticle(new Article(), id, des, fam, sfam, ssfam, typ, nature, unite, Double.parseDouble(qteTotale), 0d, 0d);
//                    if (chantierWs != null) {
//                        System.out.println("chantier Ws not null ");
//                        chantierArticleQService.addChantierArticleQ(new ChantierArticleQ(), articleWs.getId(), chantierWs.getId(), Double.parseDouble(qteTotale));
                    //                  }
                } else {
                    System.out.println("article Not null ");
                    if (chantierWs != null) {
                        System.out.println("chantier Ws not null ");
                        ChantierArticleQ articleQ = chantierArticleQService.findByRefArticle(id, chantierWs.getId());
                        System.out.println("before update article ");
                        // articleService.updateArticle(articleWs, articleWs.getQuantiteTotale() + Double.parseDouble(qteTotale), 0d, 0d);
                        System.out.println("before if");
                        if (articleQ == null) {
                            System.out.println("article Q is null");
                            articleQ = new ChantierArticleQ();
                            articleQ.setIdVentilation(idVentilationInit);
                            chantierArticleQService.addChantierArticleQ(articleQ, articleWs.getId(), chantierWs.getId(), Double.parseDouble(qteTotale), fam, sfam, ssfam, isProrata, typ);
                        } else {
                            System.out.println("article Q is not null ");
                            articleQ.setIdVentilation(idVentilationInit);
                            chantierArticleQService.updateChantierArticleQ(articleQ, articleQ.getQuantiteChantierStock() + Double.parseDouble(qteTotale));
                        }
                    }
                }

                jsonRes = "";
            }

            String idVentilationString = arr.getJSONObject(arr.length() - 1).getString("IdVentilation");

            idVentilation = Integer.parseInt(idVentilationString);
            lengthOfReturnedJSONArray = arr.length();
            System.out.println("LAST idVentilation : " + idVentilation);
            System.out.println("number of ventilations returned : " + lengthOfReturnedJSONArray);
            System.out.println("Iteration : " + iterationWSCall);
            iterationWSCall++;

        } while (lengthOfReturnedJSONArray > 0);
        System.out.println("Finish WS ");

//            Article articleToDiva = new Article();
//            Article articleToAddDiva = new Article();
//            ChantierArticleQ chaqDiva = new ChantierArticleQ();
//
//            JSONArray arr = obj.getJSONArray("Ventilations");
//
//                System.out.println("Ventillation :" + idVentilation);
//                System.out.println("Chantier :" + chantierService.findByAffaire(chan));
//                System.out.println("designation article: " + des);
//                System.out.println("Quantité : =" + strBuilder);
//
//              //  articleService.addArticle(new Article(), id, des, fam, sfam, ssfam, typ, nature, unite, Double.parseDouble(sqte), 0d, 0d);
//
//            articleToDiva = articleService.findByRef(id);
//            System.out.println("finding chgaqdiva with" + id);
//            chaqDiva = chantierArticleQService.findByRefArticle(id, chantierGlobal);
//            if (articleToDiva == null) {
//                articleToDiva = new Article();
//                System.out.println("ARTICLE NOT FOUND CREATING IT ==============");
//            }
//            if (chaqDiva == null) {
//                System.out.println("chaqdiva returned null, creating a new one ==============");
//                chaqDiva = new ChantierArticleQ();
//            }
//            articleService.addArticle(articleToDiva, id, des, fam, sfam, ssfam, typ, nature, unite, Double.parseDouble(strBuilder.toString()), Double.parseDouble(strBuilder2.toString()), Double.parseDouble(strBuilder3.toString()));
//            articleToAddDiva = articleService.findOneByCode(articleService.findAll().size() - 1);
//            if (articleService.findOneByCode(articleToDiva.getId()) != null) {
//                chantierArticleQService.addChantierArticleQ(chaqDiva, articleToDiva.getId(), chantierGlobal, Double.parseDouble(strBuilder2.toString()));
//            } else {
//                chantierArticleQService.addChantierArticleQ(chaqDiva, articleToAddDiva.getId(), chantierGlobal, Double.parseDouble(strBuilder2.toString()));
//            }
//                System.out.println("article added!");
//            }
        //  articlesInChantier = chantierArticleQService.findByChantierId(chantierGlobal);
    }

    /**
     * @handles Transfert à plusieurs articles *
     */
    public class ArticleQuantite implements Serializable {

        Double quantite;
        Double quantiteTotalDispo;
        Article article;

        public Double getQuantiteTotalDispo() {
            return quantiteTotalDispo;
        }

        public void setQuantiteTotalDispo(Double quantiteTotalDispo) {
            this.quantiteTotalDispo = quantiteTotalDispo;
        }

        public Double getQuantite() {
            return quantite;
        }

        public void setQuantite(Double quantite) {
            this.quantite = quantite;
        }

        public Article getArticle() {
            return article;
        }

        public void setArticle(Article article) {
            this.article = article;
        }

        public ArticleQuantite() {
        }

        public ArticleQuantite(Double quantite, Article article, Double quantiteTotaleDispo) {
            this.article = article;
            this.quantite = quantite;
            this.quantiteTotalDispo = quantiteTotaleDispo;
        }
    }

    class BonThread extends Thread {

        public void run() {

        }

    }

}
