/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.service.Mensuel;

import java.util.List;
import ma.bservices.beans.TypeDocument;

/**
 *
 * @author a.wattah
 */
public interface TypeDocumentService {

    public TypeDocument type_doc(String type);

    public List<TypeDocument> findDocsByFonction(Integer id_fonction);

    /**
     * return list type docs obligatoire
     *
     * @return
     */
    public List<TypeDocument> l_docs_Obligatoire();

    /**
     * recupere lis des types documents
     *
     * @return
     */
    public List<TypeDocument> findAll();

    /**
     * methode c pour recupere distinct type list des types documents
     *
     * @return
     */
    public List<String> l_type_docs();
    
    
    

    /**
     * methode pour afficher liste type_docs qui ne pas obligatoire
     */
    public List<TypeDocument> l_docs_Non_Obligatoir();

    public void addDocs_fonction(TypeDocument typeDoc, Integer[] t_intg);

}
