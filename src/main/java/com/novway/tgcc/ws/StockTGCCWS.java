/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.novway.tgcc.ws;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import ma.bservices.beans.Article;
import ma.bservices.beans.Chantier;
import ma.bservices.beans.Type;
import ma.bservices.mb.services.Module;
import ma.bservices.tgcc.Entity.ChantierArticleQ;
import ma.bservices.tgcc.Entity.Mensuel;
import ma.bservices.tgcc.service.Engin.ChantierService;
import ma.bservices.tgcc.service.Mensuel.FonctionService;
import ma.bservices.tgcc.service.Mensuel.MensuelService;
import ma.bservices.tgcc.service.SendEmail;
import ma.bservices.tgcc.service.stock.ArticleService;
import ma.bservices.tgcc.service.stock.ChantierArticleQService;
import ma.bservices.tgcc.webService.FonctionsWS;
import ma.bservices.tgcc.webService.MensuelWSCallManager;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 *
 * @author airaamane
 */
@Configuration
@EnableScheduling
@Component("wsStockScheduler")
public class StockTGCCWS {

    @Autowired
    ChantierArticleQService chantierArticleQService;

    @Autowired
    ArticleService articleService;

    @Autowired
    ChantierService chantierService;

    @Autowired
    MensuelService mensuelService;

    @Autowired
    FonctionService fonctionService;

    private static final String CRON_EXPR = "0 0/48 15 * * WED";
    
    
    
    @Scheduled(cron = CRON_EXPR)
    public void print(){
        System.out.println("SCHEDULED");
    }

    @Scheduled(cron = CRON_EXPR)
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
                // chantieService = Module.ctx.getBean(ma.bservices.services.ChantierService.class);
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
     * WEBSERVICE DE SYNCHRONISATION DES MENSUELS *
     */
    
    @Scheduled(cron = CRON_EXPR)
    public void callWSmensuel() {
        Mensuel m = new Mensuel();
        System.out.println("LAUCHING WS ...");
        String jsonRes = MensuelWSCallManager.mensuelWS("11/12/2000");

        JSONObject obj = new JSONObject(jsonRes);
        JSONArray arr = obj.getJSONArray("Mensuels");
        System.out.println("nobre mensuels" + arr.length());
        for (int i = 0; i < arr.length(); i++) {
            String id = arr.getJSONObject(i).getString("id_divalto");
            String datec = arr.getJSONObject(i).getString("date_creation");
            String dated = arr.getJSONObject(i).getString("date_debut");
            String matricule = arr.getJSONObject(i).getString("matricule").replaceAll("\\s", "");
            String nom = arr.getJSONObject(i).getString("nom");
            String prenom = arr.getJSONObject(i).getString("prenom");
            String fonctionDiva = arr.getJSONObject(i).getString("fonction");
            int status = arr.getJSONObject(i).getInt("status");
            String cin = arr.getJSONObject(i).getString("cin");
            String cnss = arr.getJSONObject(i).getString("cnss");
            String civilite = arr.getJSONObject(i).getString("Civilité");
            String etablissement = arr.getJSONObject(i).getString("établissement");
            String estQuinzainier = arr.getJSONObject(i).getString("Quinzainier");
            String uniMulti;
            try {
                uniMulti = arr.getJSONObject(i).getString("Nature"); //uni multi chantier
            } catch (Exception e) {
                uniMulti = "";
            }
            String typeC;
            try {
                typeC = arr.getJSONObject(i).getString("Type Contrat");
            } catch (Exception e) {
                typeC = "";
            }
//String uniMulti = arr.getJSONObject(i).getString("Nature"); //uni multi chantier

            System.out.println(id);
            System.out.println("date creation" + datec);
            System.out.println("date debut" + dated);
            System.out.println(matricule);
            System.out.println(nom);
            System.out.println(prenom);
            System.out.println(fonctionDiva);
            System.out.println(status);
            System.out.println(cin);
            System.out.println(cnss);
            System.out.println(uniMulti);
            System.out.println("type contrat " + typeC);
            System.out.println("end of one");
            try {
                matricule = Integer.parseInt(matricule.substring(0, matricule.length() - 1)) + "";
            } catch (Exception e) {
                System.out.println("EXCEPTION");
            }

            System.out.println(" ==== = === = === matricule " + matricule);
            Mensuel testExist = mensuelService.getByMatricule(matricule);
            if (testExist != null) {
                System.out.println("mensuel avec matricule : " + matricule + "already exists");
                try {
                    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                    testExist.setDateCreation(formatter.parse(datec));
                    testExist.setDateDebut(formatter.parse(dated));
                } catch (Exception e) {
                    Module.message(1, "Erreur Parse Date Création Mensuel ", e.getMessage());
                    System.out.println(" @@@@@@@@  exist  __ #### "
                            + "erruer parse date contrat " + datec + " date debut " + dated);
                }

                testExist.setCin(cin);
                testExist.setCnss(cnss);
                testExist.setFonction(fonctionService.findByCode(fonctionDiva));
                testExist.setNom(nom);
                testExist.setCiviliteDiva(civilite);
                testExist.setEtablissement(etablissement);
                testExist.setPrenom(prenom);
                testExist.setTypeFonction(estQuinzainier.compareToIgnoreCase("Non") == 0 ? "Mensuel" : "Mensuel Type Quinzainier");

                testExist.setStatut(String.valueOf(status));

                testExist.setCiviliteDiva(civilite);
                testExist.setTypeAffectationDiva(uniMulti);
                testExist.setTypeAffectation(uniMulti.compareToIgnoreCase("Uni Chantier") != 0 ? true : false);
                Type type = new Type();
                type.setId(2);
                testExist.setType(type);
                testExist.setTypeContrat(typeC);
                mensuelService.updateMensuel(testExist);

                //detects status change Actif -> Inactif
                if (testExist.getStatut().compareToIgnoreCase("1") == 0 && status == 0) {
                    //mail de notification changement d'état
                    ApplicationContext context = new ClassPathXmlApplicationContext("mail.xml");
                    SendEmail sm = (SendEmail) context.getBean("sendEmail");

                    try {
                        sm.sendMail("tgccbtp@gmail.com", "iraamane.abdellah@gmail.com", "MENSUEL INACTIF", "Bonjour, le mensuel " + testExist.getMatricule() + " " + testExist.getNom() + " " + testExist.getPrenom() + " est inactif, prière de lui désaffecter l'ensemble des éléments");
                    } catch (Exception ex) {
                        System.out.println(" ====    EXCEPTION WS MENSUEL CHANGEMENT ETAT : " + ex.getMessage());
                    }
                }

            } else {
                m = new Mensuel();
                try {
                    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                    m.setDateCreation(formatter.parse(datec));
                    m.setDateDebut(formatter.parse(dated));
                } catch (Exception e) {
                    Module.message(1, "Erreur Parse Date Création Mensuel ", e.getMessage());
                    System.out.println("erruer parse date contrat " + datec + " date debut " + dated);
                }
                try {
                    m.setId(Integer.parseInt(id));
                } catch (Exception e) {
                    System.out.println("EXCEPTIOn");
                }
                m.setCin(cin);
                m.setCnss(cnss);
                m.setMatricule(matricule);
                m.setFonction(fonctionService.findByCode(fonctionDiva));
                m.setNom(nom);
                m.setCiviliteDiva(civilite);
                m.setPrenom(prenom);
                m.setTypeFonction(estQuinzainier.compareToIgnoreCase("Non") == 0 ? "Mensuel" : "Mensuel Type Quinzainier");
                m.setStatut(String.valueOf(status));
                m.setTypeAffectationDiva(uniMulti);
                m.setTypeAffectation(uniMulti.compareToIgnoreCase("Uni Chantier") != 0 ? true : false);
                Type type = new Type();
                type.setId(2);
                m.setType(type);
                m.setTypeContrat(typeC);
                mensuelService.saveMensuel(m);
            }
        }

        // elContext = FacesContext.getCurrentInstance().getELContext();
        // mensuelServMb = (ma.bservices.tgcc.mb.services.MensuelMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "mensuelServMb");
        // mensuelServMb.reload_Mensuel();
        // l_mensuels = mensuelServMb.l_mensuels;
        //mensuelServMb = (ma.bservices.tgcc.mb.services.MensuelMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "mensuelServMb");
        // l_mensuels = mensuelService.findAll();
        System.out.println("WS CALL DONE ...");
    }

    public ChantierArticleQService getChantierArticleQService() {
        return chantierArticleQService;
    }

    public void setChantierArticleQService(ChantierArticleQService chantierArticleQService) {
        this.chantierArticleQService = chantierArticleQService;
    }

    public ArticleService getArticleService() {
        return articleService;
    }

    public void setArticleService(ArticleService articleService) {
        this.articleService = articleService;
    }

    public ChantierService getChantierService() {
        return chantierService;
    }

    public void setChantierService(ChantierService chantierService) {
        this.chantierService = chantierService;
    }

}
