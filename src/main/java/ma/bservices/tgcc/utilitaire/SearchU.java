/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.utilitaire;

import java.util.Date;

/**
 *
 * @author zakaria.dem
 */
public class SearchU {

    /**
     *
     * Cree un filtre SQL EX WHERE p.code = 123 ou bien : and p.code = 123
     *
     * @param label
     * @param item
     * @param boolean_filtre
     * @param HQL_inst "FROM Chantier c : le parametre HQL_inst est "c"
     * @return String
     */
    public static String createOneFilre(String label, String item, Boolean boolean_filtre, String HQL_inst) {

        String filtre = "";

        if (item != null && item.compareTo("") != 0 && item.compareTo("-1") != 0 ) {

            if (boolean_filtre == false) {
                filtre += "WHERE ";
                filtre += HQL_inst + "." + label + " LIKE '%" + item + "%'";
            } else {
                filtre += " AND " + HQL_inst + "." + label + " LIKE '%" + item + "%'";
            }
            return filtre;
        }

        return null;

    }

    /**
     * Génère une requete Date between deux autres date en en convertissant
     * l'objet String à un objet date.
     *
     * @param label
     * @param date_from
     * @param date_to
     * @param boolean_filtre
     * @param HQL_inst
     * @return
     */
    public static String createOneFilre_betweenTwoDates(String label, String date_from, String date_to, Boolean boolean_filtre, String HQL_inst) {

        String filtre = "";
        
//        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/YYYY");
//        Date date_to1=formatter.parse(date_to);
//        Date date_from1=formatter.parse(date_from);
//        
        if (date_from != null && date_from.compareTo("") != 0 && date_to != null && date_to.compareTo("") != 0) {
            if (!boolean_filtre) {
                filtre += "WHERE ";
                filtre += "" + HQL_inst + "."  + label + " >= '" + date_from + "'  and " + HQL_inst + "."  + label + " <= '" + date_to + "'";
            } else {
                filtre += " AND ";
                filtre += "" + HQL_inst + "."  + label + " >= '" + date_from + "'  and " + HQL_inst + "."  + label + " <= '" + date_to + "'";
            }
            return filtre;
        }
        return null;
        
    }

    
    public static String createOneFilre_betweenTwoDates(String label, Date date_from, Date date_to, Boolean boolean_filtre, String HQL_inst) {

        String filtre = "";
        
//        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/YYYY");
//        Date date_to1=formatter.parse(date_to);
//        Date date_from1=formatter.parse(date_from);
//        
        if (date_from != null && date_to != null) {
            if (!boolean_filtre) {
                filtre += "WHERE ";
                filtre += "" + HQL_inst + "."  + label + " between '" + date_from + "' and '"  + date_to + "'";
            } else {
                filtre += " AND ";
                System.out.println("filtre ---- " + filtre);
                filtre += "" + HQL_inst + "."  + label + " between '" + date_from + "' and '"  + date_to + "'";
            }
            return filtre;
        }
        return null;
        
    }
    
    
}
