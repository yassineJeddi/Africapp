/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.mb.services;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

/**
 *
 * @author Mahdi
 */
public class Module {

    public static WebApplicationContext ctx;
    public static int[] dos = new int[2];
    //dos => dossier 700 parametrage spécifique du TGCC
    //999 paramétrage génerique de divalto

    static {
        if (ctx == null) {
            System.out.println("module config");
            if (FacesContext.getCurrentInstance() != null) {
                ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
                if (ctx == null) {
                    System.out.println("ctx null");
                } else {
                    System.out.println("ctx not null " + ctx.toString());
                }
            } else {
//                ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
            }
        }
        
        dos[0] = 700;
        dos[1] = 999;
        
    }

    public static void message(Integer i, String titre, String m) {
        
        FacesMessage.Severity tab[] = {FacesMessage.SEVERITY_INFO, FacesMessage.SEVERITY_WARN, FacesMessage.SEVERITY_ERROR, FacesMessage.SEVERITY_FATAL};
        FacesMessage msg = new FacesMessage(tab[i], titre, m);
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public static void message(String titre, String m) {
        FacesMessage msg = new FacesMessage(titre, m);
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public static Boolean checkDate(Date dateMin, Date dateMax, Date dateTest) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String minDate = (dateMin != null) ? sdf.format(dateMin) : "",
                maxDate = (dateMax != null) ? sdf.format(dateMax) : "",
                testDate = (dateTest != null) ? sdf.format(dateTest) : "";
        dateMin = !"".equals(minDate) ? new Date(minDate) : null;
        dateMax = !"".equals(maxDate) ? new Date(maxDate) : null;
        dateTest = !"".equals(testDate) ? new Date(testDate) : null;
        if (dateTest == null) {
            return false;
        }
        if (dateMin != null && dateMax != null) {
            Integer minTest = dateTest.compareTo(dateMin), maxTest = dateMax.compareTo(dateTest);
            return minTest >= 0 && maxTest >= 0;
        } else if (dateMin  != null && dateMax == null) {
            return (dateMin.compareTo(dateTest) <= 0);
        } else if (dateMin == null && dateMax != null) {
            return (dateMax.compareTo(dateTest) >= 0);
        }
        return false;
    }
}
