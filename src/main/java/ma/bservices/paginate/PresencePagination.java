/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.paginate;

import java.util.Date;
import java.util.List;
import ma.bservices.beans.Absence;
import ma.bservices.beans.Presence;
import ma.bservices.mb.services.Module;
import ma.bservices.services.AbsenceService;
import ma.bservices.services.PresenceService;

/**
 *
 * @author admin
 */
public class PresencePagination {
    private static PresenceService presenceService;
    
    private static int i;

    public int getI() {
        return i;
    }

    public void setI(int i) {
        PresencePagination.i = i;
    }

    static {
        if (presenceService == null) {
            presenceService = Module.ctx.getBean(PresenceService.class);
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
    public static List<Presence> first(String matricule, Integer idChantier) {
        
        i =  presenceService.nombrePresencesSalarie(matricule, idChantier, null, null);

        return presenceService.listePresencesSalarie(0,10, matricule, idChantier, null, null);
       // return presenceService.listeAbsences(0, 10, (matricule == null) ? "" : matricule, (cin == null) ? "" : cin, (cnss == null) ? "" : cnss, typeAbsence);
        
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
    public static List<Presence> last(String matricule, Integer idChantier, Date de, Date a) {

        i =  presenceService.nombrePresencesSalarie(matricule, idChantier, null, null);
        int p = i / 10;
            return presenceService.listePresencesSalarie(p * 10, 10,matricule, idChantier, de, a);
        //return absenseService.listeAbsences(p * 10, 10, (matricule == null) ? "" : matricule, (cin == null) ? "" : cin, (cnss == null) ? "" : cnss, typeAbsence);
      
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
    public static List<Presence> page(Integer index, String matricule, Integer idChantier, Date de, Date a) {
        i =  presenceService.nombrePresencesSalarie((matricule == null) ? "" : matricule, idChantier, de, a);
        int p = (index > 0) ? (index - 1) * 10 : 0;

        return presenceService.listePresencesSalarie(p, 10, (matricule == null) ? "" : matricule, idChantier, de, a);
    }
}
