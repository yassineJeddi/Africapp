/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.paginate;

import java.util.List;
import ma.bservices.beans.Chantier;
import ma.bservices.mb.services.Module;
import ma.bservices.services.ChantierService;

/**
 *
 * @author Mahdi
 */
public class ChantierPaginate {

    private static ChantierService chantierService;
    private static Integer nbr;

    static {
        if (chantierService == null) {
            chantierService = Module.ctx.getBean(ChantierService.class);
            nbr = Integer.parseInt(chantierService.nombreChantiers("", "", Module.dos).toString());
        }
    }

    public static List<Chantier> page(Integer index) {
        int p = (index - 1) * 10;
        return chantierService.chantiersList(p, 10, "", "", Module.dos);
    }
}
