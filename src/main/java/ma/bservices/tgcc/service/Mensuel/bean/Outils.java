/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.service.Mensuel.bean;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author a.wattah
 */
public class Outils implements Serializable {

    public Boolean comparTo_List_Object(List list, String object) {

        Boolean exist = false;

        if (list != null) {
            if (!list.isEmpty()) {

                for (Object l_ob : list) {

                    if (l_ob.equals(object)) {

                    }

                }

            }
        }

        return exist;

    }

}
