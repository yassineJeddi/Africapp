/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.utilitaire;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.faces.context.FacesContext;
import ma.bservices.mb.services.ConstanteMb;

/**
 *
 * @author iraamane
 */
public class TgccFileManager {

    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMM dd HH:mm:ss");
    static Date tDate = new Date();
    static String chemin;

    
    public static String getCheminAttestation(String callingMethod) {
        Calendar calendar = new GregorianCalendar();
        chemin = ConstanteMb.getRepertoire()+"/files/resources/" + callingMethod + "/" + calendar.get(Calendar.YEAR) + "/" + String.valueOf(calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.DAY_OF_MONTH);
        return chemin != null ? chemin : "";
    }
    
    public static String getCheminFichier(String callingMethod) {
        System.out.println("DATE:" + sdf.format(tDate));
        Calendar calendar = new GregorianCalendar();
        System.out.println("\nchemin parametrer \n ");
        chemin = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/files/resources/" + callingMethod + "/" + calendar.get(Calendar.YEAR) + "/" + String.valueOf(calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.DAY_OF_MONTH) + "/" + calendar.get(Calendar.HOUR_OF_DAY) + "/" + calendar.get(Calendar.MINUTE));
        System.out.println("chemin genere " + chemin);
        return chemin != null ? chemin : "";
    }
    public static String getArboFichier(String callingMethod) {
        System.out.println("DATE:" + sdf.format(tDate));
        Calendar calendar = new GregorianCalendar();
        System.out.println("\nchemin parametrer \n ");
        chemin = ConstanteMb.getRepertoire()+"/files/resources/" + callingMethod + "/" + calendar.get(Calendar.YEAR) + "/" + String.valueOf(calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.DAY_OF_MONTH) + "/" + calendar.get(Calendar.HOUR_OF_DAY) + "/" + calendar.get(Calendar.MINUTE);
        System.out.println("chemin genere 2 " + chemin);
        return chemin != null ? chemin : "";
    }
   public static String getArboFichier(String callingMethod, Date tdate) {
        System.out.println("DATE:" + sdf.format(tdate));
        Calendar calendar = new GregorianCalendar();
        System.out.println("\nchemin parametrer \n ");
        chemin = ConstanteMb.getRepertoire()+"/files/resources/" + callingMethod + "/" + calendar.get(Calendar.YEAR) + "/" + String.valueOf(calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.DAY_OF_MONTH) + "/" + calendar.get(Calendar.HOUR_OF_DAY) + "/" + calendar.get(Calendar.MINUTE);
        System.out.println("chemin genere 2 " + chemin);
        return chemin != null ? chemin : "";
    }

    public static String getCheminFichier(String callingMethod, Date tdate) {
        System.out.println("DATE:" + sdf.format(tdate));
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(tdate);
        chemin = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/" + "files/resources/" + callingMethod + "/" + calendar.get(Calendar.YEAR) + "/" + String.valueOf(calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.DAY_OF_MONTH) + "/" + calendar.get(Calendar.HOUR_OF_DAY) + "/" + calendar.get(Calendar.MINUTE));
        return chemin;
    }

}
