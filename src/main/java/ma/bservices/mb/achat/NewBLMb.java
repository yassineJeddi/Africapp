/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.mb.achat;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import ma.bservices.beans.Article;
import ma.bservices.beans.ArticleBL;
import ma.bservices.beans.BonLivraison;
import ma.bservices.beans.Chantier;
import ma.bservices.beans.Document;
import ma.bservices.beans.Utilisateur;
import ma.bservices.constantes.Constantes;
import ma.bservices.mb.services.Module;
import ma.bservices.services.AchatService;
import ma.bservices.services.AdministrationService;
import ma.bservices.services.ChantierService;
import ma.bservices.services.DocumentService;
import ma.bservices.services.ParametrageService;
import ma.bservices.tgcc.authentification.Authentification;
import ma.bservices.tgcc.utilitaire.TgccFileManager;
import org.apache.commons.io.FilenameUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.stereotype.Component;

/**
 *
 * @author Mahdi
 */
@Component
@ManagedBean
@ViewScoped
public class NewBLMb implements Serializable {

    @ManagedProperty(value = "#{achatService}")
    private AchatService achatService;

    @ManagedProperty(value = "#{documentServiceEvol}")
    private DocumentService documentService;

    @ManagedProperty(value = "#{parametrageService}")
    private ParametrageService parametrageService;

    private ChantierService chantierService;
    private AdministrationService administrationService;
    private BonLivraison newbl = new BonLivraison();
    private UploadedFile uploadedFile;
    private Document document;
    private String chemin;
    private String codeChantier;
    private Integer idbl;
    private String listeArticlesBL, articlesQuantites, numeroBL = "", fournisseur, commentaire = "";
    private Date dateLivr;

    private boolean validerdisabled = true;

    public boolean isValiderdisabled() {

        return validerdisabled;
    }

    public void setValiderdisabled(boolean validerdisabled) {
        this.validerdisabled = validerdisabled;
    }

    public void setLibChantier(String libChantier) {
        this.libChantier = libChantier;
    }
    private String libChantier;

    public String getLibChantier() {
        return libChantier;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public String getFournisseur() {
        return fournisseur;
    }

    public void setFournisseur(String fournisseur) {
        this.fournisseur = fournisseur;
    }

    public String getNumeroBL() {
        return numeroBL;
    }

    public void setNumeroBL(String numeroBL) {
        this.numeroBL = numeroBL;
    }

    public Date getDateLivr() {
        return dateLivr;
    }

    public void setDateLivr(Date dateLivr) {
        this.dateLivr = dateLivr;
    }

    public String getCodeChantier() {
        return codeChantier;
    }

    public void setCodeChantier(String codeChantier) {
        this.codeChantier = codeChantier;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public String getChemin() {
        return this.chemin;
    }

    public void setChemin(String chemin) {
        this.chemin = chemin;
    }

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public ChantierService getChantierService() {
        return chantierService;
    }

    public void setChantierService(ChantierService chantierService) {
        this.chantierService = chantierService;
    }

    public AchatService getAchatService() {
        return achatService;
    }

    public void setAchatService(AchatService achatService) {
        this.achatService = achatService;
    }

    public BonLivraison getNewbl() {
        return newbl;
    }

    public void setNewbl(BonLivraison newbl) {
        this.newbl = newbl;
    }

    public DocumentService getDocumentService() {
        return documentService;
    }

    public void setDocumentService(DocumentService documentService) {
        this.documentService = documentService;
    }

    public ParametrageService getParametrageService() {
        return parametrageService;
    }

    public void setParametrageService(ParametrageService parametrageService) {
        this.parametrageService = parametrageService;
    }

    public AdministrationService getAdministrationService() {
        return administrationService;
    }

    public void setAdministrationService(AdministrationService administrationService) {
        this.administrationService = administrationService;
    }

    public Integer getIdbl() {
        return idbl;
    }

    public void setIdbl(Integer idbl) {
        this.idbl = idbl;
    }
    private List<ArticleBL> articleBLs;

    public List<ArticleBL> getArticleBLs() {
        return articleBLs;
    }

    public void setArticleBLs(List<ArticleBL> articleBLs) {
        this.articleBLs = articleBLs;
    }

    public String getListeArticlesBL() {
        return listeArticlesBL;
    }

    public void setListeArticlesBL(String listeArticlesBL) {
        this.listeArticlesBL = listeArticlesBL;
    }

    public String getArticlesQuantites() {
        return articlesQuantites;
    }

    public void setArticlesQuantites(String articlesQuantites) {
        this.articlesQuantites = articlesQuantites;
    }

    @PostConstruct
    public void init() {
        achatService = Module.ctx.getBean(AchatService.class);
        chantierService = Module.ctx.getBean(ChantierService.class);
        documentService = Module.ctx.getBean(DocumentService.class);
        parametrageService = Module.ctx.getBean(ParametrageService.class);
//        newbl.setDateLivraison(new Date());
        dateLivr = new Date();
    }

    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        } catch (NullPointerException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
        }
                

    public void found() {
        if (!isInteger(newbl.getNumeroBC())) {
            Module.message(1, "Attention N° Commande", "Le numero de BC doit etre un nombre");
        } else {
            
            Map<String, String> infosCommande;
            
            //authetification
            ELContext elContext = FacesContext.getCurrentInstance().getELContext();
            Authentification authentification = (Authentification) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "authentification");
            String demand = authentification.get_connected_user().getLogin();
            Utilisateur connectedUser = authentification.get_connected_user();
            //authentification end
            
            if (!newbl.getNumeroBC().equals("") && newbl.getNumeroBC() != null) {
                infosCommande = achatService.getInfosCommande(newbl.getNumeroBC());
                libChantier = infosCommande.get("nomChantier");
                if (infosCommande.get("commandeExiste").equals("oui") && infosCommande.get("livraisonTotale").equals("non")) {
                    newbl.setEtatBL(false);
                    newbl.setFournisseur(infosCommande.get("nomFournisseur"));
                    newbl.setDateLivraison(new Date());
                    newbl.setDateCreation(new Date());
                    newbl.setChantier(chantierService.getChantier(infosCommande.get("codeChantier")));
                    newbl.setCreePar(demand);
                    newbl.setResponsable(connectedUser);
                    idbl = achatService.ajouterBonLivraison(newbl);
                } else {//y.ouayach@casanet.net
                    Module.message(1, "Attention N° Commande", "Code 2103 : Erreur lors de la récupération de la commande,\nVérifiez SVP le numéro de commande ou contactez votre Administrateur");
                }
                if (infosCommande.get("commandeExiste").equals("oui")) {
                    boolean utilisateurAffecteAuChantier = false;
                    Chantier chantier = chantierService.getChantier(infosCommande.get("codeChantier"));
                    codeChantier = infosCommande.get("codeChantier");
                    if (chantier != null) {
                        utilisateurAffecteAuChantier = chantierService.verifierAffectationUserChantier(chantier.getId(), connectedUser.getId());
                    }
                    if (utilisateurAffecteAuChantier || Constantes.getRoleAuth().equals("admin")
                            || Constantes.getRoleAuth().equals("EMAIL_CONTRIBUTORS")) {
                        if (infosCommande.get("livraisonTotale").equals("oui")) {
                            Module.message(1, "Code 2102 : Les articles de cette commande ont été totalement validés", "");
                        } else {

                            articleBLs = achatService.getArticlesBL(idbl);
                            listeArticlesBL = "";
                            articlesQuantites = "";
                        }
                    } else {
                        Module.message(2, "Code 2101 : Vous n'êtes pas autorisés à accéder à cette commande", "");
                    }
                }
            }
        }
        }

    /**
     * validation du bon de livraison
     *
     * @return
     */
    public String validerBL() {
        if (Module.checkDate(null, new Date(), dateLivr)) {

            if (achatService.blAndBcIsUnique(numeroBL, newbl.getFournisseur(), newbl.getNumeroBC())) {
                Module.message(2, "Code 2114 : Erreur, Ce numero de BL existe deja pour ce numero de BC", "");
            } 
            else if(!isInteger(newbl.getNumeroBC())){
            Module.message(1, "Attention N° Commande", "Le numero de BC doit etre un nombre");
            }
            
            else {

                ELContext elContext = FacesContext.getCurrentInstance().getELContext();
                Authentification authentification = (Authentification) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "authentification");
                String demand = authentification.get_connected_user().getLogin();
                /**
                 * le demandeur*
                 */
                String dos = Constantes.getInstance().numeroDossierDivalto;
                /**
                 * Le Dossier
                 */
                Map<String, String> mapValidationBLWS = new HashMap<>();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                if (idbl != null) {
                    BonLivraison bl = achatService.getBL(idbl);
                    /* idbbl id bl ajouté */
                    bl.setNumeroBL(numeroBL);
                    bl.setDateLivraison(dateLivr);
                    bl.setCommentaire(commentaire);
                    bl.setResponsable(authentification.get_connected_user());
                    bl.setCreePar(demand);
                    bl.setChantier(chantierService.getChantier(codeChantier));

                    System.out.println("date livr" + dateLivr + " dans bl " + bl.getDateLivraison());
                    System.out.println("numeroBonCommande: " + numeroBL);
                    System.out.println("commentaire: " + commentaire);

                    String dateLivraison = sdf.format(dateLivr);

                    /**
                     * convertie la date mm/jj/aaaa
                     */
                    String dl = "";
                    if (!"".equals(dateLivraison)) {
                        dl = dateLivraison.substring(5, 7) + "/" + dateLivraison.substring(8, 10) + "/" + dateLivraison.substring(0, 4);
                    }
                    List<ArticleBL> articlesBL = new LinkedList<>();
                    String dateLiv = dateLivraison.substring(0, 4) + dateLivraison.substring(5, 7) + dateLivraison.substring(8, 10);

                    if (!"".equals(listeArticlesBL) && listeArticlesBL.lastIndexOf('-') == listeArticlesBL.length() - 1) {
                        listeArticlesBL = listeArticlesBL.substring(0, listeArticlesBL.length() - 1);
                    }

                    if (!"".equals(articlesQuantites) && articlesQuantites.lastIndexOf('-') == articlesQuantites.length() - 1) {
                        articlesQuantites = articlesQuantites.substring(0, articlesQuantites.length() - 1);
                    }

                    System.out.println("listeArticlesBL: " + listeArticlesBL);
                    System.out.println("numeroBonCommande: " + bl.getNumeroBC());
                    System.out.println("numeroBonLivraison: " + bl.getNumeroBL());
                    System.out.println("dateLiv: " + dateLiv);
                    System.out.println("commentaire: " + bl.getCommentaire());
                    System.out.println("articlesQuantites: " + articlesQuantites);

                    mapValidationBLWS = achatService.validationBLWS(bl.getNumeroBC(),
                            bl.getNumeroBL(), dateLiv,
                            bl.getCommentaire(), articlesQuantites);

                    String referenceBLDiva = mapValidationBLWS.get("referenceBLDiva");
                    String nombreArticleBLDiva = mapValidationBLWS.get("nombreArticleBLDiva");
                    System.out.println("nombre article BL diva" + nombreArticleBLDiva);

                    switch (referenceBLDiva) {
                        case "0":
                            Module.message(2, "Code WS006 : Le bon de livraison n'est pas validé, Veuillez SVP contacter votre Administrateur ", "");
                            break;

                        case "-1":
                            Module.message(2, "Code 2105 : Ajout de Bon de livraison echoué,\n Veuillez vérifier les champs obligatoires et la validation des quantités  ", "");
                            break;
                        case "-2":
                            Module.message(2, "Code WS006 : e bon de livraison n'est pas validé, Veuillez SVP contacter votre Administrateur ", "");
                            break;
                        case "-3":
                            Module.message(2, "Code 2107 : Erreur, ce bon de livraison est en cours de validation ", "");
                            break;
                        case "-4":
                            Module.message(2, "Code WS007 : Erreur, le bon de livraison n'est pas validé \n\n Détail:\n " + mapValidationBLWS.get("messageErreurDivalto") + "", "");
                            break;

                        default:

                            bl.setEtatBL(true);
                            bl.setNumeroBLDiva(referenceBLDiva);
                            achatService.modifierBonLivraison(bl);

//                    modelMap.put("referenceBLDiva", referenceBLDiva);
//                    modelMap.put("nombreArticleBLDiva", nombreArticleBLDiva);
                            //---- stocker les articles livrés pour des raisons de traçabilité -----------
                            ArticleBL articleBL;
                            String[] articlesQteDiva = listeArticlesBL.split("-");
                            for (String articlesQteDiva1 : articlesQteDiva) {
                                String[] validationBLDiva = articlesQteDiva1.split(";");
                                if (validationBLDiva.length < 8) {
                                    Module.message(3, "CODE : 2111 Une Erreur s'est produite, veuillez contacter votre Administrateur", "");
                                    return "";
                                }
                                articleBL = new ArticleBL();
                                Article article;
                                articleBL.setBonLivraison(bl);
                                articleBL.setNumeroDivalto(validationBLDiva[1].trim());
                                System.out.println(articleBL.getNumeroDivalto());
                                articleBL.setRefArticle(validationBLDiva[2].trim());

                                article = achatService.getArticleByRef(validationBLDiva[2], Integer.parseInt(dos));
                                if (article != null) {
                                    articleBL.setArticle(article);
                                }

                                articleBL.setQuantiteInitiale(Float.valueOf(validationBLDiva[4].trim()));
                                articleBL.setQuantiteValidee(Float.valueOf(validationBLDiva[5].trim()));
                                articleBL.setReste(Float.valueOf(validationBLDiva[6].trim()));
                                articleBL.setQuantiteLivree(Float.valueOf(validationBLDiva[7].trim()));
                                achatService.creerArticleBL(articleBL);
                            }

                            Module.message(0, "Validation BL avec succès", "");
                            listeArticlesBL = "";
                            articlesQuantites = "";

                            return "detailBL.xhtml?faces-redirect=true&idBL=" + idbl;

                    }
                    /* if (!referenceBLDiva.equals("-1") && !referenceBLDiva.equals("0") && !referenceBLDiva.equals("-2")) {

                    if (!referenceBLDiva.equals("-3")) {
                        bl.setEtatBL(true);
                        bl.setNumeroBLDiva(referenceBLDiva);
                        achatService.modifierBonLivraison(bl);

//                    modelMap.put("referenceBLDiva", referenceBLDiva);
//                    modelMap.put("nombreArticleBLDiva", nombreArticleBLDiva);
                        //---- stocker les articles livrés pour des raisons de traçabilité -----------
                        ArticleBL articleBL;
                        String[] articlesQteDiva = listeArticlesBL.split("-");
                        for (String articlesQteDiva1 : articlesQteDiva) {
                            String[] validationBLDiva = articlesQteDiva1.split(";");
                            articleBL = new ArticleBL();
                            Article article;
                            articleBL.setBonLivraison(bl);
                            articleBL.setNumeroDivalto(validationBLDiva[1].trim());
                            System.out.println(articleBL.getNumeroDivalto());
                            articleBL.setRefArticle(validationBLDiva[2].trim());

                            article = achatService.getArticleByRef(validationBLDiva[2], Integer.parseInt(dos));
                            if (article != null) {
                                articleBL.setArticle(article);
                            }
                            articleBL.setQuantiteInitiale(Float.valueOf(validationBLDiva[4].trim()));
                            articleBL.setQuantiteValidee(Float.valueOf(validationBLDiva[5].trim()));
                            articleBL.setReste(Float.valueOf(validationBLDiva[6].trim()));
                            articleBL.setQuantiteLivree(Float.valueOf(validationBLDiva[7].trim()));
                            achatService.creerArticleBL(articleBL);
                        }
                        if ("-4".equals(referenceBLDiva)) {
                            Module.message(2, "Erreur, le bon de livraison n'est pas validé \n\n Détail:\n " + mapValidationBLWS.get("messageErreurDivalto") + "", "");
                        } else {
                            Module.message(0, "Validation BL avec succès", "");
                            listeArticlesBL = "";
                            articlesQuantites = "";

                            return "detailBL.xhtml?faces-redirect=true&idBL=" + idbl;

                        }
                        //------
                    } else {
                        Module.message(2, "Erreur, ce bon de livraison est en cours de validation ", "");
                    }
                } else {
                    Module.message(2, "Le bon de livraison n'est pas validé, Veuillez SVP contacter votre Administrateur ", "");
                } **/
//                BonLivraisonMb Bl = (BonLivraisonMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(FacesContext.getCurrentInstance().getELContext(), null, "bonLivraisonMb");
//                Bl.detail(newbl.getId());
//            } else {
//                Module.message(3, "Validation Bon Livraison", "numero BL Deja Existant");
//            }
                }
            }
        } else {
            Module.message(2, "Date de Livraison doit être en passé", "");
        }
        return "";
    }

    public void save(FileUploadEvent event) throws IOException {
        /**
         * Partie CIN /resources/document
         */
        if (idbl != null) {
            System.out.println("___________ Test 1 __________ ");
//        document = new Document();
            chemin = TgccFileManager.getArboFichier("BL");
            System.out.println("chemin 1st " + chemin);
            Path folder = Paths.get(chemin);
            Files.createDirectories(folder);
            String filename = FilenameUtils.getBaseName(event.getFile().getFileName());
            String extension = FilenameUtils.getExtension(event.getFile().getFileName());
            System.out.println("extension " + extension + " upper " + extension.toUpperCase());
            if ("".equals(extension) || !"PDF".equals(extension.toUpperCase())) {
                Module.message(3, "type fichier doit être un pdf", "");
                return;
            }
            Path file = Files.createTempFile(folder, filename + "-", "." + extension);
            try (InputStream input = event.getFile().getInputstream()) {
                Files.copy(input, file, StandardCopyOption.REPLACE_EXISTING);
                chemin = chemin.substring(chemin.indexOf("files"), chemin.length());
                System.out.println("chemin 2nd " + chemin);
                chemin += "/" + file.getFileName();
                System.out.println("chemin 3rd " + chemin);
                newbl.setNodeRefDocument(chemin);
                achatService.modifierBonLivraison(newbl);
                Module.message(0, "Bon livraison chargé avec succès", "");
            }
        }

    }

    /**
     * après la saisie de la quantité
     *
     * @param event
     */
    public void onRowEdit(RowEditEvent event) {
        System.out.println("in Edit");
        ArticleBL abl = (ArticleBL) event.getObject(); //object en cours d'edition
        if ("".equals(newbl.getFournisseur()) || "".equals(newbl.getNumeroBC()) || newbl.getDateLivraison() == null) {
            System.out.println("La validation a échouée, veuillez vérifier les champs obligatoires");
            Module.message(2, "code 2110 : La validation a échouée, veuillez vérifier les champs obligatoires", "");
        } else if (!(abl.getReste() > 0)) {
            System.out.println("il reste " + abl.getReste());
            Module.message(2, "Code 2109 : La validation a échoué, la quantité commandée de cet article a été totalement livrée", "");
            System.out.println("La validation a échoué, la quantité commandée de cet article a été totalement livrée");
        } else {
            System.out.println("without Erreur");
            if (abl.getQuantiteLivree() > abl.getReste()) {
                Module.message(1, " Code 2108 : La quantité saisie est supérieure à la quantité commandée restante, "
                        + "\n\nVeuillez procéder à une demande d'approvisionnement de régularisation"
                        + " \nauprès du sevice d'achat", "");
                System.out.println("Qte Saisie Supérieur a Qte Reste");

                abl.setQuantiteLivree(abl.getReste());
            }
            if (articlesQuantites.contains(abl.getNumeroDivalto())) {
                System.out.println(" @@@@@@@ articles Qte " + articlesQuantites);
                articlesQuantites = articlesQuantites.replace("|", "&");
                System.out.println("#####articles Qte after replace" + articlesQuantites);
                String[] rows;
                /**
                 * modifier la quantité existant dans articlesQuantites
                 */
                rows = articlesQuantites.split("&");
                System.out.println("rows " + Arrays.toString(rows));
                List<String> artBL = new LinkedList<>();
                for (String row : rows) {
                    if (!row.equals("")) {
                        System.out.println("row " + row);
                        String[] cols = row.split(";");
                        if (cols[0].equals(abl.getNumeroDivalto())) {
                            System.out.println("old qte" + cols[1]);
                            cols[1] = abl.getQuantiteLivree() + "";
                        }
                        artBL.add(cols[0] + ";" + cols[1]);
                    }
                }
                articlesQuantites = "";
                for (String s : artBL) {
                    articlesQuantites += s + "|";
                }
                System.out.println("result article qte existe" + articlesQuantites);
                /**
                 * modifier la quantité exist dans list Article BL
                 */
                System.out.println(" @@@@@ List articles BL " + listeArticlesBL);
                listeArticlesBL = listeArticlesBL.replace("-", "&");
                System.out.println(" @@@@@ List articles BL after replace  " + listeArticlesBL);
                rows = listeArticlesBL.split("&");
                System.out.println("@@@@@@@@@ rows List " + Arrays.toString(rows));
                List<String> listArtBL = new LinkedList<>();
                for (String row : rows) {
                    if (!row.equals("")) {
                        System.out.println("row " + row);
                        String[] cols = row.split(";");
                        if (cols[1].equals(abl.getNumeroDivalto())) {
                            System.out.println("old qte" + cols[1]);
                            cols[7] = abl.getQuantiteLivree() + "";
                        }
                        listArtBL.add(cols[0] + ";" + cols[1] + ";" + cols[2]
                                + ";" + cols[3] + ";" + cols[4] + ";" + cols[5]
                                + ";" + cols[6] + ";" + cols[7]
                        );
                    }
                }
                listeArticlesBL = "";
                for (String s : listArtBL) {
                    listeArticlesBL += s + "-";
                }
            } else {
                listeArticlesBL += abl.getArticle().getCodeArticle() + ";" + abl.getNumeroDivalto() + ";"
                        + abl.getArticle().getCodeArticle() + ";"
                        + abl.getArticle().getDesignation().replace("-", " ") + ";"
                        + abl.getQuantiteInitiale() + ";"
                        + abl.getQuantiteValidee() + ";"
                        + abl.getReste() + ";" + abl.getQuantiteLivree() + "-";
                articlesQuantites += abl.getNumeroDivalto() + ";" + abl.getQuantiteLivree() + "|";
            }

            System.out.println("listeArticlesBL : " + listeArticlesBL);
            System.out.println("articlesQuantites : " + articlesQuantites);
        }
        setValiderdisabled(false);
    }

    public void onRowCancel(RowEditEvent event) {
        System.out.println("EVENT : " + ((ArticleBL) event.getObject()).getQuantiteLivree());
        ((ArticleBL) event.getObject()).setQuantiteLivree(null);
         setValiderdisabled(true);
        
        Module.message(1, "Modification Annulée", "");
    }
}
