/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.service.Mensuel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import ma.bservices.beans.Fonction;
import ma.bservices.beans.TypeDocument;
import ma.bservices.tgcc.dao.Mensuel.FonctionDAO;
import ma.bservices.tgcc.dao.Mensuel.TypeDocumentDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author a.wattah
 */
@Service("TypedocumentService")
public class TypeDocumentServiceImpl implements TypeDocumentService, Serializable {

    @Autowired
    private TypeDocumentDAO typeDocumentDAO;

    @Autowired
    FonctionDAO fonctionDAO;

    /**
     * getters and setters
     *
     * @return
     */
    public TypeDocumentDAO getTypeDocumentDAO() {
        return typeDocumentDAO;
    }

    public void setTypeDocumentDAO(TypeDocumentDAO typeDocumentDAO) {
        this.typeDocumentDAO = typeDocumentDAO;
    }

    public FonctionDAO getFonctionDAO() {
        return fonctionDAO;
    }

    public void setFonctionDAO(FonctionDAO fonctionDAO) {
        this.fonctionDAO = fonctionDAO;
    }

    /**
     * end setters and getters
     */
    /**
     * methode c pour recupere liste des types documents
     *
     * @return
     */
    @Override
    public List<String> l_type_docs() {
        return this.typeDocumentDAO.l_type_docs();
    }

    /**
     * get liste des types documents
     *
     * @return
     */
    @Override
    public List<TypeDocument> findAll() {
        return this.typeDocumentDAO.find_all();
    }

    @Override
    public List<TypeDocument> l_docs_Obligatoire() {

        return this.typeDocumentDAO.l_docs_Obligatoire();
    }

    @Override
    public TypeDocument type_doc(String type) {
        return this.typeDocumentDAO.type_doc(type);
    }

    @Override
    public List<TypeDocument> l_docs_Non_Obligatoir() {
        return this.typeDocumentDAO.l_docs_Non_Obligatoir();
    }

    @Override
    public List<TypeDocument> findDocsByFonction(Integer id) {
        return typeDocumentDAO.findDocsByFonction(id);
    }

    @Override
    public void addDocs_fonction(TypeDocument typeDoc, Integer[] t_intg) {

        List<Fonction> l_fonctions = new ArrayList<>();

        if (t_intg != null) {

            for (int i = 0; i < t_intg.length; i++) {
                Fonction fonction = this.fonctionDAO.getFonctionById(t_intg[i]);

                l_fonctions.add(fonction);
            }

        }

        typeDoc.setFonctions(l_fonctions);

        typeDocumentDAO.update(typeDoc);

    }

}
