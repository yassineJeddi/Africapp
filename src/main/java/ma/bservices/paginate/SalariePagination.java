/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.paginate;

import java.util.List;
import ma.bservices.beans.Salarie;
import ma.bservices.mb.services.Module;
import ma.bservices.services.SalarieService;

/**
 *
 * @author Mahdi
 */
public class SalariePagination {

    private static SalarieService salarieService;
    private static int i;

    public SalarieService getSalarieService() {
        return salarieService;
    }

    public void setSalarieService(SalarieService salarieService) {
        SalariePagination.salarieService = salarieService;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        SalariePagination.i = i;
    }

    static {
        if (salarieService == null) {
            salarieService = Module.ctx.getBean(SalarieService.class);
        }
    }

    /**
     * première liste des salarié à afficher
     *
     * @param matricule
     * @param statut
     * @param fonction
     * @param etat
     * @param cin
     * @param nom
     * @param prenom
     * @param cnss
     * @param codeChantier
     * @param matriculeNova
     * @return
     */
    public static List<Salarie> first(String matricule, Integer statut, Integer fonction, Integer etat,
            String cin, String nom, String prenom, String cnss, String codeChantier, String matriculeNova) {

        //le nombre de salariés avec ou sans un filtre
        i = Integer.parseInt(salarieService.nombreSalaries((matricule == null) ? "" : matricule, statut, fonction, etat,
                (cin == null) ? "" : cin, (nom == null) ? "" : nom, (prenom == null) ? "" : prenom, (cnss == null) ? "" : cnss,
                (codeChantier == null) ? "" : codeChantier, (matriculeNova == null) ? "" : matriculeNova) + "");

        return salarieService.listeSalaries(0, 10, (matricule == null) ? "" : matricule, statut, fonction, etat,
                (cin == null) ? "" : cin, (nom == null) ? "" : nom, (prenom == null) ? "" : prenom, (cnss == null) ? "" : cnss,
                (codeChantier == null) ? "" : codeChantier, (matriculeNova == null) ? "" : matriculeNova);
    }

    public static List<Salarie> last(String matricule, Integer statut, Integer fonction, Integer etat,
            String cin, String nom, String prenom, String cnss, String codeChantier, String matriculeNova) {
        i = Integer.parseInt(salarieService.nombreSalaries((matricule == null) ? "" : matricule, statut, fonction, etat,
                (cin == null) ? "" : cin, (nom == null) ? "" : nom, (prenom == null) ? "" : prenom, (cnss == null) ? "" : cnss,
                (codeChantier == null) ? "" : codeChantier, (matriculeNova == null) ? "" : matriculeNova) + "");
        int p = i / 10;
        return salarieService.listeSalaries(p * 10, 10, (matricule == null) ? "" : matricule, statut, fonction, etat,
                (cin == null) ? "" : cin, (nom == null) ? "" : nom, (prenom == null) ? "" : prenom, (cnss == null) ? "" : cnss,
                (codeChantier == null) ? "" : codeChantier, (matriculeNova == null) ? "" : matriculeNova);
    }

    public static List<Salarie> page(Integer index, String matricule, Integer statut, Integer fonction, Integer etat,
            String cin, String nom, String prenom, String cnss, String codeChantier, String matriculeNova) {
        i = Integer.parseInt(salarieService.nombreSalaries((matricule == null) ? "" : matricule, statut, fonction, etat,
                (cin == null) ? "" : cin, (nom == null) ? "" : nom, (prenom == null) ? "" : prenom, (cnss == null) ? "" : cnss,
                (codeChantier == null) ? "" : codeChantier, (matriculeNova == null) ? "" : matriculeNova) + "");
        int p = (index > 0) ? (index - 1) * 10 : 0;
        return salarieService.listeSalaries(p, 10, (matricule == null) ? "" : matricule, statut, fonction, etat,
                (cin == null) ? "" : cin, (nom == null) ? "" : nom, (prenom == null) ? "" : prenom, (cnss == null) ? "" : cnss,
                (codeChantier == null) ? "" : codeChantier, (matriculeNova == null) ? "" : matriculeNova);
    }
}
