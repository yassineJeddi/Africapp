/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.utilitaire;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import ma.bservices.beans.Article;
import ma.bservices.beans.Chantier;
import ma.bservices.beans.Type;
import ma.bservices.mb.services.Module;
import ma.bservices.services.MailConfigService;
import ma.bservices.tgcc.Entity.ChantierArticleQ;
import ma.bservices.tgcc.Entity.MailConfigBean;
import ma.bservices.tgcc.Entity.Mensuel;
import ma.bservices.tgcc.Entity.Voiture;
import ma.bservices.tgcc.service.Engin.ChantierService;
import ma.bservices.tgcc.service.Mensuel.FonctionService;
import ma.bservices.tgcc.service.Mensuel.MensuelService;
import ma.bservices.tgcc.service.Mensuel.VoitureServices;
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
@Component("mailScheduler")
public class MailTaskScheduler {

    @Autowired
    private VoitureServices voitureService;

    @Autowired
    ChantierArticleQService chantierArticleQService;

    @Autowired
    MailConfigService mailConfigService;

    @Autowired
    ArticleService articleService;

    @Autowired
    ChantierService chantierService;

    @Autowired
    MensuelService mensuelService;

    @Autowired
    FonctionService fonctionService;

    private static final String CRON_EXPR = "0 0 8-17 * * MON-FRI";    
    private static final String FROM_MAIL = "tgccbtp@gmail.com";
    private static final String CRON_EXPRV = "0 0 8 * * MON";

    public List<String> getRecipientsByModule(String module) {
        List<String> destinataires = new LinkedList<>();
        MailConfigBean listDestinatairesMail = mailConfigService.findByModule(module).get(0);
        String[] email_strings = listDestinatairesMail.getMail_to().split(";");
        for (String s : email_strings) {

            destinataires.add(s);
        }

        return destinataires;
    }

//    @Scheduled(cron = CRON_EXPR)
    public void printMessage() {
        // ass.printStuff();
        System.out.println("I am called by Spring mail task scheduler");
    }

//    @Scheduled(cron = CRON_EXPRV)
    public void sendMailVoiture() {
        int visite_month, visite_year, assurance_month, assurance_year;

        /**
         * RECUPERATION DE LA DATE DU JOUR*
         */
        Date today = new Date();
        Calendar current_calendar = Calendar.getInstance();
        current_calendar.setTime(today);
        int current_year = current_calendar.get(Calendar.YEAR);
        int current_month = current_calendar.get(Calendar.MONTH) + 1;

        /**
         * RECUPERATION DE LA LISTE DES DATES DES VOITURES *
         */
        List<Voiture> l_voitures = voitureService.findAll();
        if (l_voitures != null && !l_voitures.isEmpty()) {
            for (Voiture v : l_voitures) {

                if (v.getDateprochainetech() == null && v.getDatefincontrat() == null) {
                    break;
                }
                System.out.println("PROCHAINE VISITE TECHNIQUE : " + v.getDateprochainetech());

                /**
                 * VISITE TECHNIQUE *
                 */
                if (v.getDateprochainetech() != null) {

                    Calendar cal = Calendar.getInstance();
                    cal.setTime(v.getDateprochainetech());
                    visite_month = cal.get(Calendar.MONTH) + 1;
                    visite_year = cal.get(Calendar.YEAR);

                    if (current_year == visite_year) {

                        /**
                         * ENVOI DE MAIL UN MOIS AVANT LA DATE LIMITE*
                         */
                        if ((visite_month != 1 && current_month == visite_month - 1) || (visite_month == 1 && current_month == 12 && current_year == visite_year - 1)) {

                            String msg = generateMsgVisiteTech(v.getMatriculevoiture());
                            ApplicationContext context = new ClassPathXmlApplicationContext("mail.xml");
                            SendEmail sm = (SendEmail) context.getBean("sendEmail");

                            if (getRecipientsByModule("VOITURE_VISITE_TECHNIQUE") != null) {
                                for (String recipient : getRecipientsByModule("VOITURE_VISITE_TECHNIQUE")) {
                                    sm.sendMail(FROM_MAIL, recipient, "Vérification vehicule", msg);
                                }
                            }
                        }

                    }
                }

                /**
                 * ASSURANCE *
                 */
                if (v.getDatefincontrat() != null) {
                    Calendar cal_assurance = Calendar.getInstance();
                    cal_assurance.setTime(v.getDatefincontrat());
                    assurance_month = cal_assurance.get(Calendar.MONTH) + 1;
                    assurance_year = cal_assurance.get(Calendar.YEAR);

                    if (current_year == assurance_year) {

                        /**
                         * ENVOI DE MAIL UN MOIS AVANT LA DATE LIMITE*
                         */
                        if ((assurance_month != 1 && current_month == assurance_month - 1) || (assurance_month == 1 && current_month == 12 && current_year == assurance_year - 1)) {
                            String msg = generateMsgAssurance(v.getMatriculevoiture());
                            ApplicationContext context = new ClassPathXmlApplicationContext("mail.xml");
                            SendEmail sm = (SendEmail) context.getBean("sendEmail");

                            if (getRecipientsByModule("VOITURE_ASSURANCE") != null) {
                                for (String recipient : getRecipientsByModule("VOITURE_ASSURANCE")) {
                                    sm.sendMail(FROM_MAIL, recipient, "Vérification vehicule", msg);
                                }
                            }

                        }
                    }
                }
            }
        }

        // voitureService.sendMailVisiteTechnique();    
    }

//    @Scheduled(cron = CRON_EXPRV)
    public void sendMailVignette() {

        /**
         * RECUPERATION DE LA DATE DU JOUR*
         */
        Date today = new Date();
        Calendar current_calendar = Calendar.getInstance();
        current_calendar.setTime(today);
        int current_year = current_calendar.get(Calendar.YEAR);
        int current_month = current_calendar.get(Calendar.MONTH) + 1;
        if (current_month == 1) {
            List<Voiture> l_voitures = voitureService.findAll();
            if (l_voitures != null && !l_voitures.isEmpty()) {
                String msg = generateMsgVignette();
                ApplicationContext context = new ClassPathXmlApplicationContext("mail.xml");
                SendEmail sm = (SendEmail) context.getBean("sendEmail");

                if (getRecipientsByModule("VOITURE_VIGNETTE") != null) {
                    for (String recipient : getRecipientsByModule("VOITURE_VIGNETTE")) {
                        sm.sendMail(FROM_MAIL, recipient, "Vérification vignette", msg);
                    }
                }

            }
        }
    }

    public String generateMsgVisiteTech(String matricule) {
        return "Bonjour, prière de faire la visite technique pour le véhicule " + matricule + ". Merci";
    }

    public String generateMsgAssurance(String matricule) {
        return "Bonjour, prière de renouveller l'assurance pour le véhicule " + matricule + ". Merci";
    }

    public String generateMsgVignette() {
        return "Bonjour, prière de renouveller la vignette pour les véhicules . Merci";
    }

//    @Scheduled(cron = CRON_EXPR)
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
//    @Scheduled(cron = CRON_EXPR)
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

                //detects status change Actif -> Inactif
                if (testExist.getStatut().compareToIgnoreCase("1") == 0 && status == 0) {
                    //mail de notification changement d'état
                    ApplicationContext context = new ClassPathXmlApplicationContext("mail.xml");
                    SendEmail sm = (SendEmail) context.getBean("sendEmail");

                    try {
                        String msg1 = "Bonjour, le mensuel " + testExist.getMatricule() + " " + testExist.getNom() + " " + testExist.getPrenom() + " est inactif, prière de lui désaffecter l'ensemble des éléments";

                        if (getRecipientsByModule("MENSUEL_INACTIF") != null) {
                            for (String recipient : getRecipientsByModule("MENSUEL_INACTIF")) {
                                sm.sendMail("tgccbtp@gmail.com", recipient, "MENSUEL INACTIF", msg1);
                            }
                        }

                    } catch (Exception ex) {
                        System.out.println(" ====    EXCEPTION WS MENSUEL CHANGEMENT ETAT : " + ex.getMessage());
                    }
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

    public VoitureServices getVoitureService() {
        return voitureService;
    }

    public void setVoitureService(VoitureServices voitureService) {
        this.voitureService = voitureService;
    }

    public MailConfigService getMailConfigService() {
        return mailConfigService;
    }

    public void setMailConfigService(MailConfigService mailConfigService) {
        this.mailConfigService = mailConfigService;
    }

    public MensuelService getMensuelService() {
        return mensuelService;
    }

    public void setMensuelService(MensuelService mensuelService) {
        this.mensuelService = mensuelService;
    }

    public FonctionService getFonctionService() {
        return fonctionService;
    }

    public void setFonctionService(FonctionService fonctionService) {
        this.fonctionService = fonctionService;
    }

}
