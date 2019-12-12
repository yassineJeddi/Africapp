/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.paginate;

import java.util.Date;
import java.util.List;
import ma.bservices.beans.HeuresSupplementaires;
import ma.bservices.beans.Salarie;
import ma.bservices.mb.services.Module;
import ma.bservices.services.HeuresSupplementairesService;
import ma.bservices.services.SalarieService;
import ma.bservices.beans.Absence;
import ma.bservices.services.AbsenceService;


/**
 *
 * @author Mahdi
 */
public class AbsensePagination {

    private static AbsenceService absenseService;
    
    private static int i;

    public int getI() {
        return i;
    }

    public void setI(int i) {
        AbsensePagination.i = i;
    }

    static {
        if (absenseService == null) {
            absenseService = Module.ctx.getBean(AbsenceService.class);
        }
    }

    /**
     * Retourne la liste des absenses, les premiers 10 absences
     * 
     * @param matricule
     * @param cin
     * @param cnss
     * @param typeAbsence
     * @return 
     */
    public static List<Absence> first(String matricule, String cin, String cnss, Integer typeAbsence) {
        
        i =  absenseService.nombreAbsences((matricule == null) ? "" : matricule, (cin == null) ? "" : cin, (cnss == null) ? "" : cnss, typeAbsence);

        return absenseService.listeAbsences(0, 10, (matricule == null) ? "" : matricule, (cin == null) ? "" : cin, (cnss == null) ? "" : cnss, typeAbsence);
        
    }

    /**
     * liste des absences suivant
     *
     * @param matricule
     * @param cin
     * @param cnss
     * @param etat
     * @param chantier
     * @param date
     * @return
     */
    public static List<Absence> last(String matricule, String cin, String cnss, Integer typeAbsence) {

        i =  absenseService.nombreAbsences((matricule == null) ? "" : matricule, (cin == null) ? "" : cin, (cnss == null) ? "" : cnss, typeAbsence);          
        int p = i / 10;

        return absenseService.listeAbsences(p * 10, 10, (matricule == null) ? "" : matricule, (cin == null) ? "" : cin, (cnss == null) ? "" : cnss, typeAbsence);
      
    }

    
    /**
     * retourne la liste des absenses par page
     * 
     * @param index
     * @param matricule
     * @param cin
     * @param cnss
     * @param typeAbsence
     * @return 
     */
    public static List<Absence> page(Integer index, String matricule, String cin, String cnss, Integer typeAbsence) {
        i =  absenseService.nombreAbsences((matricule == null) ? "" : matricule, (cin == null) ? "" : cin, (cnss == null) ? "" : cnss, typeAbsence);
        int p = (index > 0) ? (index - 1) * 10 : 0;

        return absenseService.listeAbsences(p, 10, (matricule == null) ? "" : matricule, (cin == null) ? "" : cin, (cnss == null) ? "" : cnss, typeAbsence);
    }
}




