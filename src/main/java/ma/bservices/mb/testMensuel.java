/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.mb;

import ma.bservices.tgcc.webService.DivaltoService;
import ma.bservices.tgcc.webService.DivaltoServiceSoap;
import java.util.Date;
import javax.xml.ws.Holder;
import ma.bservices.tgcc.webService.FonctionsWS;
import ma.bservices.tgcc.webService.MensuelWSCallManager;
import org.apache.commons.lang.StringEscapeUtils;

/**
 *
 * @author Mahdi
 */
public class testMensuel {

    public static void main(String[] args) {
//        AchatService achatService = new AchatService();
        System.out.println("Call");
        //Commande cmd = achatService.getCommande("1375476");
        //Get_Chantier_By_Mensuel("06/06/2016");
        System.out.println("calling ws ......... " + new Date());
       // Get_Chantier_By_Mensuel("11/12/2015");
//        absenceWS(1450 + "", "29-06-2016 10:00:00", "29-06-2016 12:00:00", "Maladie", (float) 0.25);
        System.out.println("finished ws!6......... " + new Date());
        System.out.println("End Call");
//        System.out.println("___________ WS Mensuel");
//        mensuelWS("11/12/2015");
//        System.out.println("\n\n___________ WS Absence");
//        absenceWS("997", "02-11-2015 10:00:00", "03-11-2015 18:00:00", "maladie");
//        System.out.println("\n\n___________ WS Consommation");
//        consommationWS("GMAMBECOMAAPD27-2.5M", "2", "02-11-2015 10:00:00", "CHAN0001");
//        System.out.println("\n\n___________ WS Transfert");
//        transfertWS("GMAMBECOMAAPD27-2.5M", "3", "CHAN0001", "CHAN0002", "18-01-2016 15:07:00");
//        System.out.println("\n\n___________ WS Article");
//        articleWS("CHAN0001", "17-01-2016 00:00:00");
    }

    /**
     * la liste des mensuels from Web Service
     *
     * @param date_modif format date dd/MM/yyyy
     * @return liste des mensuels format Json
     */
    public static String mensuelWS(String date_modif) {
        try {
            DivaltoService divaltoService = new DivaltoService();
            DivaltoServiceSoap divaltoServiceSoap = divaltoService.getDivaltoServiceSoap();
            Holder<Integer> hI = new Holder<>();
            Holder<String> hS = new Holder<>();
            divaltoServiceSoap.webServiceDiva("<ACTION>WSTGCC", "<Task>Get_Mensuel<Date_modif>" + date_modif, hI, hS);
            String chaine = StringEscapeUtils.unescapeHtml(hS.value).trim();
            System.out.println("requete Get_Mensuel WS: " + "<Task>Get_Mensuel<Date_modif>" + date_modif);
            System.out.println("reponse Get_Mensuel WS: " + chaine);
            System.out.println("code: " + hI.value);
            return chaine;
        } catch (Exception e) {
            System.out.println("Erreur " + e.getLocalizedMessage());
            return "";
        }
    }

    /**
     * récuperer le cahntier d'un mensuel via divalto
     *
     * @param date_modif
     * @param matricule
     * @return res web service
     */
    public static String Get_Chantier_By_Mensuel(String date_modif, String matricule) {
        try {
            DivaltoService divaltoService = new DivaltoService();
            DivaltoServiceSoap divaltoServiceSoap = divaltoService.getDivaltoServiceSoap();
            Holder<Integer> hI = new Holder<>();
            Holder<String> hS = new Holder<>();
            divaltoServiceSoap.webServiceDiva("<ACTION>WSTGCC", "<Task>Get_Mensuel_Chantier<date_modif>" + date_modif + "<Matricule>" + matricule, hI, hS);
            String chaine = StringEscapeUtils.unescapeHtml(hS.value).trim();
            System.out.println("requete Get_Mensuel WS: " + "<Task>Get_Mensuel_Chantier<date_modif>" + date_modif + "<Matricule>" + matricule);
            System.out.println("reponse Get_Mensuel WS: " + chaine);
            System.out.println("code: " + hI.value);
            return chaine;
        } catch (Exception e) {
            System.out.println("Erreur " + e.getLocalizedMessage());
            return "";
        }
    }

    /**
     * Ajouter Absence divalto
     *
     * @param matricule du salarie qui a obligatoirement un contrat
     * @param date_d date début format date dd-MM-yyyy hh:mm:ss ex: 17-01-2016
     * 00:00:00
     * @param date_f date fin format date dd-MM-yyyy hh:mm:ss ex: 17-01-2016
     * 00:00:00
     * @param type d'absence
     * @param NbHeures
     * @return code si 1 absence ajoutée 0 absence non ajoutée
     */
    public static String absenceWS(String matricule, String date_d, String date_f, String type, float NbHeures) {
        try {
            DivaltoService divaltoService = new DivaltoService();
            DivaltoServiceSoap divaltoServiceSoap = divaltoService.getDivaltoServiceSoap();
            Holder<Integer> hI = new Holder<>();
            Holder<String> hS = new Holder<>();

            divaltoServiceSoap.webServiceDiva("<ACTION>WSTGCC",
                    "<Task>Ajouter_Absence<matricule>" + matricule + "<Date_abs_Deb>"
                    + date_d + "<Date_abs_fin>" + date_f + "<Type>" + type
                    + "<NbHeures>" + NbHeures, hI, hS);
            String chaine = StringEscapeUtils.unescapeHtml(hS.value).trim();
            System.out.println("requete Ajouter_Absence WS: " + "<Task>Ajouter_Absence<matricule>" + matricule
                    + "<Date_abs_Deb>" + date_d + "<Date_abs_fin>" + date_f + "<Type>" + type + "<NbHeures>" + NbHeures);
            System.out.println("reponse Ajouter_Absence WS: " + chaine);
            System.out.println("code: " + hI.value);
            return chaine;
        } catch (Exception e) {
            System.out.println("Erreur " + e.getLocalizedMessage());
            return e.getMessage();
        }
    }

    /**
     * liste des articles from WS
     *
     * @param chantier le codeNova ou bien Affaire
     * @param date_modif date de modification
     * @return liste des articles format Json
     */
    public static String articleWS(String chantier, String date_modif) {
        try {
            DivaltoService divaltoService = new DivaltoService();
            DivaltoServiceSoap divaltoServiceSoap = divaltoService.getDivaltoServiceSoap();
            Holder<Integer> hI = new Holder<>();
            Holder<String> hS = new Holder<>();
            divaltoServiceSoap.webServiceDiva("<ACTION>WSTGCC", "<Task>Get_Article<Chantier>" + chantier + "<Date_modif>" + date_modif, hI, hS);
            String chaine = StringEscapeUtils.unescapeHtml(hS.value).trim();
            System.out.println("requete Get_Article WS: " + "<Task>Get_Article<Chantier>" + chantier + "<Date_modif>" + date_modif);
            System.out.println("reponse Get_Article WS: " + chaine);
            System.out.println("code: " + hI.value);
            return chaine;
        } catch (Exception e) {
            System.out.println("Erreur " + e.getLocalizedMessage());
            return e.getMessage();
        }
    }

    /**
     * effectuer consommation
     *
     * @param Ref_art reference article
     * @param Qte_consommee quantité
     * @param Date_consom date format date dd-MM-yyyy hh:mm:ss
     * @param chantier le code nova ( Affaire)
     * @return code de consommation divalto
     */
    public static String consommationWS(String Ref_art, String Qte_consommee, String Date_consom, String chantier) {
        try {
            DivaltoService divaltoService = new DivaltoService();
            DivaltoServiceSoap divaltoServiceSoap = divaltoService.getDivaltoServiceSoap();
            Holder<Integer> hI = new Holder<>();
            Holder<String> hS = new Holder<>();
            divaltoServiceSoap.webServiceDiva("<ACTION>WSTGCC", "<Task>Creer_Consommation<Ref_art>" + Ref_art
                    + "<Qte_consommee>" + Qte_consommee + "<Date_consom>" + Date_consom + "<Chantier>" + chantier, hI, hS);
            String chaine = StringEscapeUtils.unescapeHtml(hS.value).trim();
            System.out.println("requete Creer_Consommation WS: " + "<Task>Creer_Consommation<Ref_art>" + Ref_art
                    + "<Qte_consommee>" + Qte_consommee + "<Date_consom>" + Date_consom + "<Chantier>" + chantier);
            System.out.println("reponse Creer_Consommation WS: " + chaine);
            System.out.println("code: " + hI.value);
            return chaine;
        } catch (Exception e) {
            System.out.println("Erreur " + e.getLocalizedMessage());
            return e.getMessage();
        }
    }

    /**
     *
     * @param Ref_art
     * @param Qte_Transf
     * @param Chantier_Emett
     * @param Chantier_Recep
     * @param Date_Transf
     * @return code de transfere divalto
     */
    public static String transfertWS(String Ref_art, String Qte_Transf, String Chantier_Emett,
            String Chantier_Recep, String Date_Transf) {
        try {
            DivaltoService divaltoService = new DivaltoService();
            DivaltoServiceSoap divaltoServiceSoap = divaltoService.getDivaltoServiceSoap();
            Holder<Integer> hI = new Holder<>();
            Holder<String> hS = new Holder<>();
            divaltoServiceSoap.webServiceDiva("<ACTION>WSTGCC", "<Task>Creer_Transfert<Ref_art>" + Ref_art
                    + "<Qte_Transf>" + Qte_Transf + "<Chantier_Emett>" + Chantier_Emett + "<Chantier_Recep>"
                    + Chantier_Recep + "<Date_Transf>" + Date_Transf, hI, hS);
            String chaine = StringEscapeUtils.unescapeHtml(hS.value).trim();
            System.out.println("requete Creer_Transfert WS: " + "<Task>Creer_Transfert<Ref_art>" + Ref_art
                    + "<Qte_Transf>" + Qte_Transf + "<Chantier_Emett>" + Chantier_Emett + "<Chantier_Recep>"
                    + Chantier_Recep + "<Date_Transf>" + Date_Transf);
            System.out.println("reponse Creer_Transfert WS: " + chaine);
            System.out.println("code: " + hI.value);
            return chaine;
        } catch (Exception e) {
            System.out.println("Erreur " + e.getLocalizedMessage());
            return e.getMessage();
        }
    }
}
