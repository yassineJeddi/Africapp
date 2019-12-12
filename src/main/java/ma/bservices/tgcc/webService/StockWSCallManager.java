/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.webService;

import javax.xml.ws.Holder;
import org.apache.commons.lang.StringEscapeUtils;

/**
 *
 * @author admin
 */
public class StockWSCallManager {
    
    
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
            divaltoServiceSoap.webServiceDiva("<ACTION>TESTWS", "<Task>Get_Article<Chantier>" + chantier + "<Date_modif>" + date_modif, hI, hS);
            String chaine = StringEscapeUtils.unescapeHtml(hS.value).trim();
            System.out.println("requete Get_Article WS: " + "<Task>Get_Article<Chantier>" + chantier + "<Date_modif>" + date_modif);
            System.out.println("reponse Get_Article WS: " + chaine);
            System.out.println("code: " + hI.value);
            System.out.println(chaine);
            return chaine;
        } catch (Exception e) {
            System.out.println("Erreur " + e.getLocalizedMessage());
            return e.getMessage();
        }
    }

    
    /**
     *
     * @param Ref_art
     * @param Chantier_Emett
     * @param Chantier_Recep
     * @param Date_Transf
     * @return code de transfere divalto
     */
    public static String transfertWS(String Ref_art, String Chantier_Emett,
            String Chantier_Recep, String Date_Transf) {
        try {
            DivaltoService divaltoService = new DivaltoService();
            DivaltoServiceSoap divaltoServiceSoap = divaltoService.getDivaltoServiceSoap();
            Holder<Integer> hI = new Holder<>();
            Holder<String> hS = new Holder<>();
            divaltoServiceSoap.webServiceDiva("<ACTION>WSTGCC", "<Task>Creer_Transfert<Ref_art>" + Ref_art
                    + "<Chantier_Emett>" + Chantier_Emett + "<Chantier_Recep>"
                    + Chantier_Recep + "<Date_Transf>" + Date_Transf, hI, hS);
            String chaine = StringEscapeUtils.unescapeHtml(hS.value).trim();
            System.out.println("requete Creer_Transfert WS: " + "<Task>Creer_Transfert<Ref_art>" + Ref_art
                    + "<Chantier_Emett>" + Chantier_Emett + "<Chantier_Recep>"
                    + Chantier_Recep + "<Date_Transf>" + Date_Transf);
            System.out.println("reponse Creer_Transfert WS: " + chaine);
            System.out.println("code: " + hI.value);
            System.out.println(chaine);
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
    
    
    
    
    
    
}
