/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.mb.stock;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import ma.bservices.beans.Article;
import ma.bservices.beans.Lot;
import ma.bservices.tgcc.Entity.LotArticleXls;
import ma.bservices.tgcc.service.Engin.LotService;
import ma.bservices.tgcc.service.stock.ArticleService;
import ma.bservices.tgcc.service.stock.LotArticleXlsService;
import ma.bservices.tgcc.utilitaire.ExcelParser;
import org.primefaces.model.UploadedFile;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

/**
 *
 * @author airaamane
 */
@Component
@ManagedBean(name = "parseLotArticle")
@ViewScoped
public class ParseLotArticleMb implements Serializable {

    /* Services */
    /**
     * -- lotArticleXls Service -- *
     */
    @ManagedProperty(value = "#{lotArticleXlsService}")
    private LotArticleXlsService laxService;

    /**
     * -- lot Service -- *
     */
    @ManagedProperty(value = "#{lotService}")
    private LotService lotService;

    /**
     * -- article Service -- *
     */
    @ManagedProperty(value = "#{articleService}")
    private ArticleService artService;

    /**
     * -- variables -- *
     */
    LotArticleXls lax = new LotArticleXls();
    UploadedFile uploadedLotParArticle;
    List<LotArticleXls> listOfLotsParArticle = new LinkedList<>();

    /**
     * -- Getters & Setters -- *
     */
    public UploadedFile getUploadedLotParArticle() {
        return uploadedLotParArticle;
    }

    public void setUploadedLotParArticle(UploadedFile uploadedLotParArticle) {
        this.uploadedLotParArticle = uploadedLotParArticle;
    }

    public LotArticleXlsService getLaxService() {
        return laxService;
    }

    public void setLaxService(LotArticleXlsService laxService) {
        this.laxService = laxService;
    }

    public LotService getLotService() {
        return lotService;
    }

    public void setLotService(LotService lotService) {
        this.lotService = lotService;
    }

    public ArticleService getArtService() {
        return artService;
    }

    public void setArtService(ArticleService artService) {
        this.artService = artService;
    }

    public LotArticleXls getLax() {
        return lax;
    }

    public void setLax(LotArticleXls lax) {
        this.lax = lax;
    }

    public List<LotArticleXls> getListOfLotsParArticle() {
        return listOfLotsParArticle;
    }

    public void setListOfLotsParArticle(List<LotArticleXls> listOfLotsParArticle) {
        this.listOfLotsParArticle = listOfLotsParArticle;
    }
    
    

    @PostConstruct
    public void init() {
        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        laxService = ctx.getBean(LotArticleXlsService.class);
        lotService = ctx.getBean(LotService.class);
        artService = ctx.getBean(ArticleService.class);
        
        
        listOfLotsParArticle = laxService.findAll();

    }
    
    
    public void processUpload(){
        System.out.println("FILE UPLOADED");
    }
    
    /*
    public void processUploadedExcelFile(){
    
    String extension = FilenameUtils.getExtension(uploadedLotParArticle.getFileName());
    
            if ("xls".equals(extension) || "xlsx".equals(extension)) {

                try (InputStream input = uploadedLotParArticle.getInputstream()) {

                    //On recupère le fichier excel de la vue et on crée les présences
                    Map<String, String[]> data = presenceService.recupererPresencesFichier(input, extension, this.mounth, this.year, desaffectationChantiers, verificationContrat, ecraserPointagesExistants, avecReglage, this.chantierSearch);
                    if (data != null) {
                        //on crée le document rectifié pour le téléchargement
                        ByteArrayOutputStream output = presenceService.creerDocument(data);
                        byte[] bytes = output.toByteArray();

                        //Enregidtrement du document
                        try (InputStream inputEx = new ByteArrayInputStream(bytes)) {

                            String chemin = TgccFileManager.getArboFichier("presenceSalarie");
                            Path folder = Paths.get(chemin);
                            Files.createDirectories(folder);

                            Path file = Files.createTempFile(folder, FilenameUtils.getBaseName(uploadedLotParArticle.getFileName()), ".xls");

                            Files.copy(inputEx, file, StandardCopyOption.REPLACE_EXISTING);

                        }
                        try (InputStream inputEx2 = new ByteArrayInputStream(bytes)){
                        
                             file_Excel = new DefaultStreamedContent(inputEx2,"application/vnd.ms-excel",FilenameUtils.getBaseName(uploadedLotParArticle.getFileName()) + ".xls");
                       
                        }
                        //                    //on enregistre le document sur le repertoire
                        //                    
                        //                    
                        //                    Path folder = Paths.get(chemin);
                        //                    Files.createDirectories(folder);
                        //                    
                        //                    String filename = FilenameUtils.getBaseName(event.getFile().getFileName());
                    }

                } catch (Exception e) {
                   // System.out.println("erreur _ " + e.getMessage() + " __ " + e.getLocalizedMessage());
                    Module.message(2, "Erreur", "importation echouée");
//                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Erreur", "L'importation du fichier a échouée"));

                }

            } else {
                Module.message(2, "Veuillez choisir un fichier excel (xls,xlsx) ", "");
//                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Erreur", "Veuillez choisir un fichier excel (xls,xlsx)"));

            }
    
    
    }*/

    public void parse() {

        Map<String, List<String>> mapArtLotFromXls = new HashMap<>();
        System.out.println("PARSING ....");
        mapArtLotFromXls = ExcelParser.parseExcelFile("C:\\Users\\admin\\Documents\\NetBeansProjects\\TGCC-Integration-3\\src\\main\\webapp\\resources\\canva.xlsx");

        for (Map.Entry<String, List<String>> entry : mapArtLotFromXls.entrySet()) {
            System.out.println("ARTICLE : " + entry.getKey());
            for (String s : entry.getValue()) {
                
                try {
                    Article art = artService.findByRef(entry.getKey());
                    Lot lot = lotService.findByLib(s.trim());
                    lax.setArticleId(art);
                    lax.setLotId(lot);
                      laxService.save(lax);
                } catch (Exception e) {
                    System.out.println("cannot find article and/or Lot : " + e.getMessage());
                }
                
               
                
               // System.out.println("LOT : " + lot.getLibelle());
                
              
                System.out.println("LOT : " + s);
            }
            System.out.println("");
        }
        System.out.println("DONE PARSING.");

    }

}
