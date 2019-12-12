/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.paginate;

import java.util.Date;
import java.util.List;
import ma.bservices.beans.DemandeApprovisionnement;
import ma.bservices.constantes.Constantes;
import ma.bservices.mb.services.Module;
import ma.bservices.services.AchatService;

/**
 *
 * @author Mahdi
 */
public class DmdPaginate {

    private static AchatService achatService;
    private static int nbr;

    static {
        achatService = Module.ctx.getBean(AchatService.class);
    }

    public static List<DemandeApprovisionnement> page(Integer index, String numeroDA, String chantier, String etat,
            String demandeur, Date dateAjoutDe, Date dateAjoutA, Date dateLivraisonSouhaiteeDe, Date dateLivraisonSouhaiteeA) {
        nbr = Integer.parseInt(
                achatService.nombreDemandesAppro(
                        numeroDA != null ? numeroDA : "",
                        chantier != null ? chantier : "",
                        etat != null ? etat : "",
                        demandeur != null ? demandeur : "",
                        dateAjoutDe, dateAjoutA, dateLivraisonSouhaiteeDe, dateLivraisonSouhaiteeA, Constantes.isAdmin()).toString());
        int p = (index > 0) ? (index - 1) * 10 : 0;
        return achatService.listeDemandesAppro(numeroDA != null ? numeroDA : "",
                chantier != null ? chantier : "",
                etat != null ? etat : "",
                demandeur != null ? demandeur : "",
                dateAjoutDe, dateAjoutA, dateLivraisonSouhaiteeDe, dateLivraisonSouhaiteeA, p, 10, Constantes.isAdmin());
    }

    public static List<DemandeApprovisionnement> fisrt(String numeroDA, String chantier, String etat,
            String demandeur, Date dateAjoutDe, Date dateAjoutA, Date dateLivraisonSouhaiteeDe, Date dateLivraisonSouhaiteeA) {
        return achatService.listeDemandesAppro(numeroDA != null ? numeroDA : "",
                chantier != null ? chantier : "",
                etat != null ? etat : "",
                demandeur != null ? demandeur : "", dateAjoutDe, dateAjoutA, dateLivraisonSouhaiteeDe, dateLivraisonSouhaiteeA, 0, 10, Constantes.isAdmin());
    }

    public static List<DemandeApprovisionnement> last(String numeroDA, String chantier, String etat,
            String demandeur, Date dateAjoutDe, Date dateAjoutA, Date dateLivraisonSouhaiteeDe, Date dateLivraisonSouhaiteeA) {
        nbr = Integer.parseInt(achatService.nombreDemandesAppro(numeroDA != null ? numeroDA : "",
                chantier != null ? chantier : "",
                etat != null ? etat : "",
                demandeur != null ? demandeur : "", dateAjoutDe, dateAjoutA, dateLivraisonSouhaiteeDe, dateLivraisonSouhaiteeA, true).toString());
        int p = nbr / 10;
        return achatService.listeDemandesAppro(numeroDA != null ? numeroDA : "",
                chantier != null ? chantier : "",
                etat != null ? etat : "",
                demandeur != null ? demandeur : "",
                dateAjoutDe, dateAjoutA, dateLivraisonSouhaiteeDe, dateLivraisonSouhaiteeA, p * 10, 10, Constantes.isAdmin());
    }
}
