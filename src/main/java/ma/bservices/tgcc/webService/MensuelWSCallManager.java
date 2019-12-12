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
public class MensuelWSCallManager {
    
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
            fr.divalto.webservice.DivaltoService divaltoService = new fr.divalto.webservice.DivaltoService();
            fr.divalto.webservice.DivaltoServiceSoap divaltoServiceSoap = divaltoService.getDivaltoServiceSoap();
            Holder<Integer> hI = new Holder<>();
            Holder<String> hS = new Holder<>();
            divaltoServiceSoap.webServiceDiva("<ACTION>WSTGCC", "<Task>Get_Mensuel_Chantier<Matricule>" + matricule + "<Date_modif>" + date_modif, hI, hS);
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
            fr.divalto.webservice.DivaltoService divaltoService = new fr.divalto.webservice.DivaltoService();
            fr.divalto.webservice.DivaltoServiceSoap divaltoServiceSoap = divaltoService.getDivaltoServiceSoap();
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
    
}
