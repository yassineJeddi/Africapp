/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.dao.Mensuel;

import java.util.List;
import ma.bservices.beans.Fonction;

/**
 *
 * @author j.allali
 */
public interface FonctionDAO {

    public List<Fonction> findAll();

    public void delete(Fonction element);

    public void save(Fonction element);
    
    public void update(Fonction func);
    
    public Fonction findByCode(String code);

    public List<Fonction> l_FonctionsByTypeDoc(int id);

    public Fonction getFonctionById(int id);
    
    public void importFonctionDiva();
}
