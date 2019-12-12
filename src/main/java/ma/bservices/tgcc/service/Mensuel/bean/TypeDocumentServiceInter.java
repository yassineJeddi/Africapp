/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.service.Mensuel.bean;

import java.util.ArrayList;
import java.util.List;
import ma.bservices.beans.TypeDocument;

/**
 *
 * @author a.wattah
 */
public class TypeDocumentServiceInter {

    private List<TypeDocument> l_type_Document;

    /**
     * construct par defautl
     */
    public TypeDocumentServiceInter() {
    }

    /**
     * getters and setters
     *
     * @return
     */
    public List<TypeDocument> getL_type_Document() {
        return l_type_Document;
    }

    public void setL_type_Document(List<TypeDocument> l_type_Document) {
        this.l_type_Document = l_type_Document;
    }

    /**
     * end getters and setters
     */
    /**
     * get list autocomplete sasair par utilisateur
     *
     * @param l_type
     * @return
     */
    public List<String> getList_type_doc(List<String> l_type) {

        System.out.println("entree :: " + l_type.size());

        List<String> results = new ArrayList<>();

        if (l_type != null) {

            for (int i = 0; i < l_type.size(); i++) {
                if (l_type.get(i) != null) {
                    results.add(l_type.get(i));
                }
            }
            return results;

        }

        return results;

    }
}
