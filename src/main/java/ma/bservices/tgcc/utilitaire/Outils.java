/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.utilitaire;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author a.wattah
 */
public class Outils {

    /**
     * methode qui permet de converti date to string
     *
     * @param date
     * @return
     */
    public String convertDate_To_string(Date date) {

        String date_String = "";

        if (date != null) {

            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

            date_String = sdf.format(date);
        }

        return date_String;

    }

    /**
     * methode format date de type string
     */
    public String format(String str) {
        String str_2 = "";
        if (str != null) {

            try {

                str_2 = str.substring(8, 10) + "-" + str.substring(5, 7) + "-" + str.substring(0, 4);
                return str_2;

            } catch (Exception e) {
                System.out.println("exception " + e.getMessage());
            }

        }
        return str_2;
    }

}
