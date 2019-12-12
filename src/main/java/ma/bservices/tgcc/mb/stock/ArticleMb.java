/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.mb.stock;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import ma.bservice.tgcc.Constante.Message;
import ma.bservices.beans.Article;
import ma.bservices.beans.Zone;
import ma.bservices.tgcc.Entity.AffectationStock;
import ma.bservices.tgcc.Entity.TransferStock;
import ma.bservices.tgcc.service.Engin.ZoneServices;
import ma.bservices.tgcc.service.stock.AffectationStockService;
import ma.bservices.tgcc.service.stock.ArticleService;
import ma.bservices.tgcc.service.stock.TransferStockService;
import org.primefaces.event.RowEditEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

/**
 *
 * @author IRAAMANE
 */
@Component
@ManagedBean(name = "article")
@ViewScoped
public class ArticleMb implements Serializable {

    @ManagedProperty(value = "#{articleService}")
    private ArticleService articleService;

    @ManagedProperty(value = "#{zoneService}")
    private ZoneServices zoneService;

    private Map<Zone, Double> affectationsDetails;

    public Map<Zone, Double> getAffectationsDetails() {
        return affectationsDetails;
    }

    public void setAffectationsDetails(Map<Zone, Double> affectationsDetails) {
        this.affectationsDetails = affectationsDetails;
    }

    private Integer quantiteTransfer;

    public Integer getQuantiteTransfer() {
        return quantiteTransfer;
    }

    public void setQuantiteTransfer(Integer quantiteTransfer) {
        this.quantiteTransfer = quantiteTransfer;
    }

//   ELContext elContext = FacesContext.getCurrentInstance().getELContext();
//   AffectationStockMb affectationStockBean = (AffectationStockMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "affectationStockMb");
//    public ELContext getElContext() {
//        return elContext;
//    }
//
//    public void setElContext(ELContext elContext) {
//        this.elContext = elContext;
//    }
//
//    public AffectationStockMb getAffectationStockBean() {
//        return affectationStockBean;
//    }
//
//    public void setAffectationStockBean(AffectationStockMb affectationStockBean) {
//        this.affectationStockBean = affectationStockBean;
//    }
    @ManagedProperty(value = "#{affectationStockService}")
    private AffectationStockService affectationStockService;

    @ManagedProperty(value = "#{transferStockService}")
    private TransferStockService transferStockService;

    public TransferStockService getTransferStockService() {
        return transferStockService;
    }

    public void setTransferStockService(TransferStockService transferStockService) {
        this.transferStockService = transferStockService;
    }

    public AffectationStockService getAffectationStockService() {
        return affectationStockService;
    }

    public void setAffectationStockService(AffectationStockService affectationStockService) {
        this.affectationStockService = affectationStockService;
    }

    private Integer quantite_affectation;

    public Integer getQuantite_affectation() {
        return quantite_affectation;
    }

    public void setQuantite_affectation(Integer quantite_affectation) {
        this.quantite_affectation = quantite_affectation;
    }

    public ZoneServices getZoneService() {

        return zoneService;
    }

    public void setZoneService(ZoneServices zoneService) {
        this.zoneService = zoneService;
    }

    private List<Article> articles;

    private int chantierSearch;
    private int zoneSearch;

    public int getZoneSearch() {
        return zoneSearch;
    }

    public void setZoneSearch(int zoneSearch) {
        this.zoneSearch = zoneSearch;
    }

    Article article = new Article();

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    private List<Article> filtredArticles;

    private int chantierAffectation;
    private int zoneAffectation;

    public int getZoneAffectation() {
        return zoneAffectation;
    }

    public void setZoneAffectation(int zoneAffectation) {
        this.zoneAffectation = zoneAffectation;
    }

    public int getChantierAffectation() {
        return chantierAffectation;
    }

    public void setChantierAffectation(int chantierAffectation) {
        this.chantierAffectation = chantierAffectation;
    }

    public void printStuff() {
        System.out.println("CHANGE DETECTED");
    }

    public List<Article> getFiltredArticles() {
        return filtredArticles;
    }

    public void setFiltredArticles(List<Article> filtredArticles) {
        this.filtredArticles = filtredArticles;
    }

    public int getChantierToAdd() {
        return chantierToAdd;
    }

    public void setChantierToAdd(int chantierToAdd) {
        this.chantierToAdd = chantierToAdd;
    }

    private int familleToAdd;

    public int getFamilleToAdd() {
        return familleToAdd;
    }

    public void setFamilleToAdd(int familleToAdd) {
        this.familleToAdd = familleToAdd;
    }

    private int chantierToAdd;

    private int zoneToAdd;
    private AffectationStock affectationStock;

    public AffectationStock getAffectationStock() {
        return affectationStock;
    }

    public void setAffectationStock(AffectationStock affectationStock) {
        this.affectationStock = affectationStock;
    }

    public int getZoneToAdd() {
        return zoneToAdd;
    }

    public void setZoneToAdd(int zoneToAdd) {
        this.zoneToAdd = zoneToAdd;
    }

    private int sfamilleToAdd;

    private int typologieToAdd;

    public int getTypologieToAdd() {
        return typologieToAdd;
    }

    public void setTypologieToAdd(int typologieToAdd) {
        this.typologieToAdd = typologieToAdd;
    }

    public int getSfamilleToAdd() {
        return sfamilleToAdd;
    }

    public void setSfamilleToAdd(int sfamilleToAdd) {
        this.sfamilleToAdd = sfamilleToAdd;
    }

    private int lotToAdd;

    public int getLotToAdd() {
        return lotToAdd;
    }

    public void setLotToAdd(int lotToAdd) {
        this.lotToAdd = lotToAdd;
    }

    Article articleToAdd = new Article();

    public Article getArticleToAdd() {
        return articleToAdd;
    }

    public void setArticleToAdd(Article articleToAdd) {
        this.articleToAdd = articleToAdd;
    }

    public ArticleService getArticleService() {
        return articleService;
    }

    public void setArticleService(ArticleService articleService) {
        this.articleService = articleService;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public int getChantierSearch() {
        return chantierSearch;
    }

    public void setChantierSearch(int chantierSearch) {
        this.chantierSearch = chantierSearch;
    }

    public ArticleMb() {

    }

    public void addArticle() {
        // articleService.addArticle(articleToAdd, zoneToAdd, chantierToAdd, familleToAdd, lotToAdd, sfamilleToAdd, typologieToAdd);
        articles = articleService.findAll();
    }

    TransferStock transfer = new TransferStock();

    public TransferStock getTransfer() {
        return transfer;
    }

    public void setTransfer(TransferStock transfer) {
        this.transfer = transfer;
    }

    public void affecterArticle() {
        //System.out.println(" ======== AFFECTATION \n ====== ARTICLE : " + article.getDesignation() + "\n ZONE : " + zoneAffectation + " \n QUANTITE : " + quantite_affectation);
        // articleService.affecterArticle(article, zoneAffectation, quantite_affectation);         
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("" + Message.AFFECT_ARTICLE, ""));
    }

    public void onRowEdit(RowEditEvent event) {
        // FacesMessage msg = new FacesMessage("La quantité & été modifiée avec succes!", ((Article) event.getObject()).getQuantite_Zone0().toString());
        //  articleService.updateArticle(((Article) event.getObject()),((Article) event.getObject()).getQuantite_Zone0()) ;
        //  FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    @PostConstruct
    public void init() {
        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        articleService = ctx.getBean(ArticleService.class);
        articles = articleService.findAll();
    }

}
