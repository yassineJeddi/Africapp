/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservice.tgcc.Constante;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author zakaria.dem
 */
public class Constante {

    public static final String CODE_CHANTIER_DEPOT = "CHAN0223";

    public static final String CODE_ETAT_ENGIN_PANNE = "EN_PANNE";
    public static final String CODE_ETAT_ENGIN_MARCHE = "EN_MARCHE";

    public static final String FIRST_DAY_IN_POINTAGE = "01-02-2016";
    public static final String GROUPE_MENSUELS = "mensuel";
    public static final String GROUPE_POINTEURS = "admin_chantier";
    public static final String GROUPE_ADMINS = "admin";
    public static final String GROUPE_RHS = "rh";
    public static final String GROUPE_ACHAT = "achat";

    //pointage par Lot
    public static final String FIRST_DAY_IN_POINTAGE_LOT = "18-04-2016";
    public static final String FIRST_DAY_IN_POINTAGE_ENGIN = "25-05-2016";
    public static final int FONCTION_ID_CHEF_EQUIPE = 268;
    public static final int Etat_ID_ACTIF = 1;
    public static final int Etat_ID_Contrat_Inactif = 3;
    public static final int Etat_ID_Sortie = 3;
    public static final int Etat_ID_ACTIF_PROVISOIR = 5;
    public static final int Etat_ID_En_Cours = 4;
    public static final int ID_CIVILITE_MONSIEUR = 1;
    public static final int ID_NATIONALITE_MAROCAINE = 1;
    public static final int ID_Situation_Cilibataire = 1;
    public static final int ID_Pays_Maroc = 1;
    public static final int ID_Mode_Paiement = 3;
    public static final int ID_Type_Quinzainier = 1;
    public static final int ID_Model_Arabe = 1;
    public static final int ID_Type_Contrat_CDD = 2;
    public static final int ID_Duree_Contrat_1an = 2;
    public static final int ID_Periode_Essai = 2;
    public static final int ID_Periode_Preavis = 1;
    public static final int[] Type_Fonction_Pointage_Upsit = {1};
    public static final int[] Type_Fonction_Pointage_Declaratif = {0};
    public static final String chemin_physique = "/var/lib/tomcat7/webapps/files";

    public static Date getMinDateNaissance() {
        Date minDateNaissance = new Date();
        minDateNaissance.setYear((new Date()).getYear() - 18);
        return minDateNaissance;
    }

    /**
     * the first day of pointage in the system
     *
     * @return Date
     * @throws ParseException
     */
    public static Date get_first_day_in_pointage_forAllApplication() throws ParseException {

        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        return df.parse(Constante.FIRST_DAY_IN_POINTAGE);

    }

    /**
     * the first day of pointage by lot in the system
     *
     * @return Date
     * @throws ParseException
     */
    public static Date get_first_day_in_pointage_lot_forAllApplication() throws ParseException {
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        return df.parse(Constante.FIRST_DAY_IN_POINTAGE_LOT);
    }

    /**
     * the first day of pointage by engin in the system
     *
     * @return Date
     * @throws ParseException
     */
    public static Date get_first_day_in_pointage_Engin_forAllApplication() throws ParseException {
        
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        try {
            return df.parse(Constante.FIRST_DAY_IN_POINTAGE_ENGIN);
        } catch (Exception e) {
            System.out.println("Erreur de get_first_day_in_pointage_Engin_forAllApplication car "+e.getMessage());
            return null;
        }
    }

    /**
     * get date from string
     *
     * @param dateString
     * @return Date
     */
    public static Date getDateFrFromString(String dateString) {
        try {
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            return df.parse(dateString);
        } catch (ParseException e) {
            System.out.println("@@ erreur parsing date " + e.getMessage());
            return null;
        }

    }

    public static Date getDateFrFromDate(Date date) {
        try {
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            String dateString = df.format(date);
            return df.parse(dateString);
        } catch (ParseException e) {
            System.out.println("@@ erreur parsing date " + e.getMessage());
            return null;
        }

    }

}
