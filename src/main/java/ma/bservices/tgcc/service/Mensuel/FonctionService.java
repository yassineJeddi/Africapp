/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.service.Mensuel;

import java.util.List;
import ma.bservices.beans.Fonction;
import ma.bservices.beans.TypeDocument;

/**
 *
 * @author j.allali
 */
public interface FonctionService {

    public List<Fonction> findAll();

    public List<Fonction> l_FonctionsByTypeDoc(int id);
    
    public Fonction findByCode(String code);
    
    public void setListTypeDocsObligatoires(Fonction f, List<TypeDocument> listOfRequiredDocTypes);
    
    public void importFonctionDiva();

}
