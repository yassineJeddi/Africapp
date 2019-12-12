package ma.bservices.webservice;


import javax.xml.ws.Holder;

import org.apache.commons.lang.StringEscapeUtils;
import com.google.gson.JsonSyntaxException;
import ma.bservices.tgcc.webService.DivaltoService;
import ma.bservices.tgcc.webService.DivaltoServiceSoap;
 
public class TestGetCommande {

    /**
     * @param args
     * @throws JsonSyntaxException
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

        DivaltoService divaltoService = new DivaltoService();

	DivaltoServiceSoap divaltoServiceSoap = divaltoService.getDivaltoServiceSoap();

        Holder<Integer> hI = new Holder<Integer>();
        Holder<String> hS = new Holder<String>();
        divaltoServiceSoap.webServiceDiva("<ACTION>WSTGCC", "<Task>Charge_fact<picod>4<ticod>C<pino>NULL<pidt>31/07/2018<factdt>14/09/2018<pnature>FACTURE<pchan>CHAN0411<ptiers>C0000741<pref>2<pinotiers>395TGCC18<txtent><marche><avenant><dtdtravx>20180701<dtftravx>20180731<EXOTVA>2<factid>13532<signe>1<mouv>#0_VENTE___0;Montant des travaux ;1;533987.55;SO;CHAN0411;;#0_VENTE_RG;RETENUE GARANTIE;1;-53398.75;;CHAN0411;;", hI, hS);
        
        System.out.println("code: " + hI.value);
        System.out.println("json: " + hS.value);
        
        //<Task>ajouterSalarie<matricule><civilite>01<nom>nomTest<prenom>prenomTest<cin>B5455<dateNaissance>19840522<lieuNaissance>Rabat<nationalite>MA<adresse>15, belvedere<ville>Casablanca<pays>MA<telephone><gsm>0655845487<email><situationFamiliale>1<statut><fonction><cnss>112785824<rib>0078000769400019111165<banque>hassan 2<modePaiement>
//		String chaine = "{\"piece\":" +
//		"{\"entete\":" +
//	     	"{\"code_Fou\":\"F0000001\",\"nom_Fou\":\"SUNCHEMICAL\",\"date\":\"20130206\",\"ref_ent\":\"tert\",\"piNo\":\"12090017\"," +
//	    	"\"codChantier\":\"TESTP\",\"chantier\":\"TESTPEGASE\"}" +
//		"}," +
//		"\"mouvements\":" +
//	    	"[" +
//	    	"{\"ref\":\"ALB0001\",\"quantite\":\"0,000\",\"libelle\":\"Album&#39;MonpetitLouvre&#39;fran&#231;ais&#47;anglaisGDEPROD&#45;2\"," +
//	    	"\"montant\":\"0,00\",\"enrNo\":\"13600\",\"piCod\":\"2\",\"qteInitial\":\"3,000\",\"qteValidee\":\"3,000\"}" +
//	    	"]" +
//	    	"}";

        String chaine = StringEscapeUtils.unescapeHtml(hS.value);

        //Utilisation de l'api Google-gson pour la conversion du json en objet java "Commande"
//		Gson gson = new Gson();
//		Commande commande = gson.fromJson(chaine, Commande.class);
//		
//		System.out.println("Resultat: "+commande.getResultat());
//		System.out.println("nombreArticlesCommandes: "+commande.getNombreArticlesCommandes());
//		System.out.println("numero commande: "+commande.getPiece().getEntete().getPino());
//		
//		String ch="{\"referenceBLDivalto\":\"255650\", \"nombreArticlesBLDivalto\":\"0\"}";
        int ind1 = chaine.indexOf(":") + 2;
        int ind2 = chaine.indexOf("\"", ind1);

        String referenceSalarieDiva = chaine.substring(ind1, ind2);

        System.out.println("refrence salarié diva: " + referenceSalarieDiva);

    }

}
