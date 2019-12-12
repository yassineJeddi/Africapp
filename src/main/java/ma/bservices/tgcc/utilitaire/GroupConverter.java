/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.utilitaire;

/**
 *
 * @author a.iraamane
 */

import javax.faces.convert.EnumConverter;
import javax.faces.convert.FacesConverter;
import ma.bservices.beans.Groupe;
        

@FacesConverter(value="grpConverter")
public class GroupConverter extends EnumConverter {

    public GroupConverter() {
        super(Groupe.class);
    }

}
