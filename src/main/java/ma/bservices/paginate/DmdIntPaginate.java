/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.paginate;

import java.util.Date;
import java.util.List;
import ma.bservices.constantes.Constantes;
import ma.bservices.mb.services.Module;
import ma.bservices.services.DemandeService;
import ma.bservices.tgcc.Entity.DemandeEntete;

/**
 *
 * @author admin
 */
public class DmdIntPaginate {
     private static DemandeService demandeService;
    private static int nbr;

    static {
        demandeService = Module.ctx.getBean(DemandeService.class);
    }

    public static List<DemandeEntete> page(Integer index, String numeroDA, String chantier,String atelier, String etat,
            String demandeur, Date dateAjoutDe, Date dateAjoutA, Date dateLivraisonSouhaiteeDe, Date dateLivraisonSouhaiteeA) {
        nbr = Integer.parseInt(
                demandeService.nombreDemandesInterne(
                        numeroDA != null ? numeroDA : "",
                        chantier != null ? chantier : "", atelier != null ? atelier : "",
                        etat != null ? etat : "",
                        demandeur != null ? demandeur : "",
                        dateAjoutDe, dateAjoutA, dateLivraisonSouhaiteeDe, dateLivraisonSouhaiteeA, Constantes.isAdmin()).toString());
        int p = (index > 0) ? (index - 1) * 10 : 0;
        return demandeService.listeDemandesInterne(numeroDA != null ? numeroDA : "",
                chantier != null ? chantier : "",atelier != null ? atelier : "",
                etat != null ? etat : "",
                demandeur != null ? demandeur : "",
                dateAjoutDe, dateAjoutA, dateLivraisonSouhaiteeDe, dateLivraisonSouhaiteeA, p, 10, Constantes.isAdmin());
    }

    public static List<DemandeEntete> fisrt(String numeroDA, String chantier,String atelier, String etat,
            String demandeur, Date dateAjoutDe, Date dateAjoutA, Date dateLivraisonSouhaiteeDe, Date dateLivraisonSouhaiteeA) {
        return demandeService.listeDemandesInterne(numeroDA != null ? numeroDA : "",
                chantier != null ? chantier : "",atelier != null ? atelier : "",
                etat != null ? etat : "",
                demandeur != null ? demandeur : "", dateAjoutDe, dateAjoutA, dateLivraisonSouhaiteeDe, dateLivraisonSouhaiteeA, 0, 10, Constantes.isAdmin());
    }

    public static List<DemandeEntete> last(String numeroDA, String chantier,String atelier, String etat,
            String demandeur, Date dateAjoutDe, Date dateAjoutA, Date dateLivraisonSouhaiteeDe, Date dateLivraisonSouhaiteeA) {
        nbr = Integer.parseInt(demandeService.nombreDemandesInterne(numeroDA != null ? numeroDA : "",
                chantier != null ? chantier : "", atelier != null ? atelier : "",
                etat != null ? etat : "",
                demandeur != null ? demandeur : "", dateAjoutDe, dateAjoutA, dateLivraisonSouhaiteeDe, dateLivraisonSouhaiteeA, true).toString());
        int p = nbr / 10;
        return demandeService.listeDemandesInterne(numeroDA != null ? numeroDA : "",
                chantier != null ? chantier : "",atelier != null ? atelier : "",
                etat != null ? etat : "",
                demandeur != null ? demandeur : "",
                dateAjoutDe, dateAjoutA, dateLivraisonSouhaiteeDe, dateLivraisonSouhaiteeA, p * 10, 10, Constantes.isAdmin());
    }

}
 

