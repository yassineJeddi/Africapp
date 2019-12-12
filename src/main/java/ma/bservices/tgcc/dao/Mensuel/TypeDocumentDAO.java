/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.dao.Mensuel;

import java.util.List;
import ma.bservices.beans.TypeDocument;

/**
 *
 * @author a.wattah
 */
public interface TypeDocumentDAO {

    /**
     * recupere liste docs obligatoire
     *
     * @return
     */
    public List<TypeDocument> l_docs_Obligatoire();

    public List<TypeDocument> findDocsByFonction(Integer id_fonction);

    public TypeDocument type_doc(String type);

    /**
     * methode recupere listes des docuemnts
     *
     * @return
     */
    public List<TypeDocument> find_all();

    /**
     * methode pour recupere la liste des type document
     *
     * @return
     */
    public List<String> l_type_docs();

    /**
     * methode qui permet affiche la list docs qui ne pas obligatoir
     *
     * @return
     */
    public List<TypeDocument> l_docs_Non_Obligatoir();

    public void update(TypeDocument typeDoc);

}
