/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.paginate;

import java.util.Date;
import java.util.List;
import ma.bservices.beans.BonLivraison;
import ma.bservices.constantes.Constantes;

import ma.bservices.mb.services.Module;
import ma.bservices.services.AchatService;

/**
 *
 * @author Mahdi
 */
public class BlPaginate {

    private static final AchatService achatService;
    public static Integer nbr;

    static {
        achatService = Module.ctx.getBean(AchatService.class);
        nbr = Integer.parseInt(achatService.nombreBonsLivraison("", "", null, null, null, true).toString());
    }

    public static List<BonLivraison> page(Integer index) {
        int p = (index - 1) * 10;
        return achatService.listeBonsLivraison(p, 10, "", "", null, null, null, true);
    }

    public static List<BonLivraison> page(Integer index, String numeroBL, String numeroBC, Date dateLivraison, Integer chantier, Integer responsable) {
        boolean userIsAdmin = false;
        if (Constantes.getRoleAuth().equals("admin") || Constantes.getRoleAuth().equals("EMAIL_CONTRIBUTORS")) {
            userIsAdmin = true;
        }
        nbr = Integer.parseInt(achatService.nombreBonsLivraison(
                (numeroBL == null) ? "" : numeroBL, (numeroBC == null) ? "" : numeroBC,
                dateLivraison, chantier, responsable, userIsAdmin).toString());
        int p = (index > 0) ? (index - 1) * 10 : 0;
        return achatService.listeBonsLivraison(p, 10, (numeroBL == null) ? "" : numeroBL, (numeroBC == null) ? "" : numeroBC, dateLivraison, chantier, responsable, userIsAdmin);
    }
}
