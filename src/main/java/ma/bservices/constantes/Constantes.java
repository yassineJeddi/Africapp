package ma.bservices.constantes;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import org.springframework.security.core.Authentication;
//import org.alfresco.service.cmr.repository.NodeRef;

//import org.alfresco.service.cmr.repository.NodeRef;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
//Singleton 

public class Constantes {

    private static Constantes instance = new Constantes();

//    public static final String APPLICATION_CONTEXT = "classpath*:core/cma-core-context.xml";
    public String url = "";
//    public NodeRef nodeRefDocumentsUtilisateurs = null;
//    public NodeRef nodeRefThumbnails = null;
//    public NodeRef nodeRefFichierInconvertible = null;
//    public NodeRef nodeRefPhotosTemporaires = null;
//    public NodeRef nodeRefPhotoSalarie = null;
//    public NodeRef nodeRefTemp = null;
//    public NodeRef nodeRefBonsLivraison = null;

    //Design des rapports
    public String bdUrl = "";
    public String nomUtilisateur = "";
    public String motDePasse = "";

    public String jrxmlRapportPresencesSalaries = null;
    public String jrxmlRapportPresences = null;
//    public NodeRef nodeRefRapportsTemporaires = null;

    // numeros dossiers DIVALTO
    public String numeroDossierDivalto;
    public String numeroDossierCommunDivalto;

    // nbre de répétitions web service en cas de dépassement de licences
    public int nbreRepWS;

    // chemin des presences après appel du web service des temps
    public String cheminPresences;

    public static final ArrayList<GrantedAuthority> AUTHORITIES = new ArrayList<>();

    /**
     * Constructeur avec modificateur d'accés "private" pour ne pas avoir
     * plusieurs instances de cette classe
     */
    private Constantes() {
        super();

        PropertiesLoader pl = new PropertiesLoader();
//        String nodeRefDocumentsUtilisateursProp = pl.getProp("bservices.nodeRefDocumentsUtilisateurs");
//        String nodeRefThumbnailsProp = pl.getProp("bservices.nodeRefThumbnails");
//        String nodeRefFichierInconvertibleProp = pl.getProp("bservices.nodeRefFichierInconvertible");
//        String nodeRefPhotosTemporairesProp = pl.getProp("bservices.nodeRefPhotosTemporaires");
//        String nodeRefPhotoSalarieProp = pl.getProp("bservices.nodeRefPhotoSalarie");
//        String nodeRefBonsLivraisonProp = pl.getProp("bservices.nodeRefBonsLivraison");
//        String nodeRefTempProp = pl.getProp("bservices.nodeRefTemp");

        url = pl.getProp("bservices.url");
//        nodeRefDocumentsUtilisateurs = new NodeRef(nodeRefDocumentsUtilisateursProp);
//        nodeRefThumbnails = new NodeRef(nodeRefThumbnailsProp);
//        nodeRefFichierInconvertible = new NodeRef(nodeRefFichierInconvertibleProp);
//        nodeRefPhotosTemporaires = new NodeRef(nodeRefPhotosTemporairesProp);
//        nodeRefPhotoSalarie = new NodeRef(nodeRefPhotoSalarieProp);
//        nodeRefBonsLivraison = new NodeRef(nodeRefBonsLivraisonProp);
//        nodeRefTemp = new NodeRef(nodeRefTempProp);

        //Rapports
        bdUrl = pl.getProp("bservices.rapports.bd.url");
        nomUtilisateur = pl.getProp("bservices.rapports.bd.nomUtilisateur");
        motDePasse = pl.getProp("bservices.rapports.bd.motDePasse");

        String nodeRefRapportsTemporairesProp = pl.getProp("bservices.rapports.nodeRefRapportsTemporaires");
        jrxmlRapportPresencesSalaries = pl.getProp("bservices.rapports.jrxmlRapportPresencesSalaries");
        jrxmlRapportPresences = pl.getProp("bservices.rapports.jrxmlRapportPresences");
//        nodeRefRapportsTemporaires = new NodeRef(nodeRefRapportsTemporairesProp);

        //Dossiers Divalto
        numeroDossierDivalto = pl.getProp("bservices.numeroDossierDivalto");
        numeroDossierCommunDivalto = pl.getProp("bservices.numeroDossierCommunDivalto");

        //nombre de répiétition WS
        nbreRepWS = Integer.parseInt(pl.getProp("bservices.nombreRepetitionWS"));

        // chemin des extraction des présences
        cheminPresences = pl.getProp("bservices.cheminExtractionPresences");

    }

    public static Constantes getInstance() {

        return instance;
    }

    public static String dateFrancaisVersAnglais(String dateFrancais) {

        String dateAnglais = "";
        String[] dateF = dateFrancais.split("/");
        dateAnglais = dateF[1] + "/" + dateF[0] + "/" + dateF[2];
        DateFormat dateFormat = DateFormat.getDateInstance(1, Locale.ENGLISH);
        String formatDateAnglais = dateFormat.format(new Date(dateAnglais));
        String[] dateA = formatDateAnglais.split(" ");
        dateAnglais = dateF[0] + " " + dateA[0] + " " + dateF[2];

        return dateAnglais;

    }

    public static String dateAnglaisVersFrancais(Date dateAnglais) {

        String dateAng = dateAnglais.toString();

        String[] dateA = dateAng.split(" ");
        String annee = dateA[5];

        DateFormat dateFormat = DateFormat.getDateInstance(3, Locale.FRANCE);
        String dateFrancais = dateFormat.format(dateAnglais);
        String[] dateF = dateFrancais.split("/");

        dateFrancais = dateF[0] + "/" + dateF[1] + "/" + annee;

        return dateFrancais;

    }

    public static String dateAnglaisVersFrancaisDetail(Date dateAnglais) {

        if (dateAnglais != null) {
            String dateFrancais = Constantes.dateAnglaisVersFrancais(dateAnglais);
            String[] dateF = dateFrancais.split("/");
            String jour = dateF[0];
            String mois = dateF[1];
            String annee = dateF[2];
            DateFormat dateFormat = DateFormat.getDateInstance(1, Locale.FRANCE);
            dateFrancais = dateFormat.format(new Date(mois + "/" + jour + "/" + annee));

            return dateFrancais;
        }
        return null;

    }

    public static String dateEtHeureAnglaisVersFrancais(Date dateAnglais) {

        String dateAng = dateAnglais.toString();

        String[] dateA = dateAng.split(" ");
        String annee = dateA[5];

        DateFormat dateFormat = DateFormat.getDateInstance(3, Locale.FRANCE);
        String dateEtHeureFrancais = dateFormat.format(dateAnglais);
        String[] dateF = dateEtHeureFrancais.split("/");

        dateEtHeureFrancais = dateF[0] + "/" + dateF[1] + "/" + annee + " " + dateA[3];

        return dateEtHeureFrancais;

    }

    public static boolean isValide(String extension) {
        extension = extension.toLowerCase();
        ArrayList<String> listeExtensions = new ArrayList<String>();

        listeExtensions.add(".pdf");
        listeExtensions.add(".doc");
        listeExtensions.add(".docx");
        listeExtensions.add(".html");
        listeExtensions.add(".txt");
        listeExtensions.add(".htm");
        listeExtensions.add(".odf");

        return listeExtensions.contains(extension);

    }

    public static String getRoleAuth() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        String role = "";
        
        OUTER:
        for (GrantedAuthority grantedAuthority : auth.getAuthorities()) {
             // System.out.println(grantedAuthority.getAuthority());
            switch (grantedAuthority.getAuthority()) {
               case "pointage_declaratif":
                    role = "pointage_declaratif";
                    break OUTER;
                case "admin":
                    role = "admin";
                    break OUTER;
                case "pointage_des_engins":
                    role = "pointage_des_engins";
                    break OUTER;
                case "achat":
                    role = "achat";
                    break OUTER;
                case "EMAIL_CONTRIBUTORS,":
                    role = "EMAIL_CONTRIBUTORS";
                    break OUTER;
                case "ALFRESCO_ADMINISTRATORS,":
                    role = "ALFRESCO_ADMINISTRATORS";
                    break OUTER;
            }
        }
        return role;

    }

    public static int validationRib(String rib) {
        String ribPart1;
        if (rib.substring(0, 1) == "0") {
            ribPart1 = rib.substring(1, 6);
        }
        else {
            ribPart1 = rib.substring(0, 6);
        }

        String ribPart2 = rib.substring(6, 12);
        String ribPart3 = rib.substring(12, 18);
        String ribPart4 = rib.substring(18, 22) + "00";

        int a = Integer.parseInt(ribPart1) % 97;
        int b = Integer.parseInt((a + "" + ribPart2)) % 97;
        int c = Integer.parseInt((b + "" + ribPart3)) % 97;
        int d = Integer.parseInt((c + "" + ribPart4)) % 97;

        int cle = 97 - d;
        if (cle == Integer.parseInt(rib.substring(22, 24))) {
            return 1;
        } else {
            return 0;
        }

    }

    public static int validationCnss(String cnss) {

        String ch = cnss.substring(1, 8);
        int sommePairs = 0;
        int sommeImpairs = 0;
        int nbp = 0;
        int nbi = 0;
        int i = 0;
        int j = 1;
        for (; i < 8;) {
            nbp = Integer.parseInt(ch.substring(i, i + 1));
            sommePairs = sommePairs + nbp;
            i = i + 2;
        }

        for (; j < 7;) {
            nbi = Integer.parseInt(ch.substring(j, j + 1));
            sommeImpairs = sommeImpairs + nbi;
            j = j + 2;
        }
        int cle = 10 - (((sommePairs * 2) + sommeImpairs) % 10);
        if (cle == 10) {
            cle = 0;
        }
        if (cle == Integer.parseInt(cnss.substring(8, 9))) {
            System.out.println("@@@@cnss valide " + cnss);
            return 1;
        } else if ((9 - cle) == Integer.parseInt(cnss.substring(8, 9))) {
            System.out.println("@@@@cnss valide " + cnss);
            return 1;
        } else {
            System.out.println("@@@@cnss invalide ");
            return 0;
        }
    }

    public static String generate(int length) {
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ ";
        String mot = "";
        for (int x = 0; x < length; x++) {
            int i = (int) Math.floor(Math.random() * 53);
            mot += chars.charAt(i);
        }
        // System.out.println(mot);
        return mot;
    }

    public static String generateInt(int length) {
        String chars = "0123456789";
        String mot = "";
        for (int x = 0; x < length; x++) {
            int i = (int) Math.floor(Math.random() * 10);
            mot += chars.charAt(i);
        }
        
        System.out.println(mot);
        return mot;
    }
    
     public static boolean isAdmin(){
        boolean admin;
        if ("admin".equals(Constantes.getRoleAuth())) 
        { admin=true;}
           else {admin=false;}
            return admin;
    }


}
