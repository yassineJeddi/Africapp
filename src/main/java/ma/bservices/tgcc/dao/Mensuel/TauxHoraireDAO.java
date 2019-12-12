/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.dao.Mensuel;

import java.util.List;
import ma.bservices.tgcc.Entity.TAUXHORAIRE;

/**
 *
 * @author yassine.jeddi
 */
public interface TauxHoraireDAO {
    
    public List<TAUXHORAIRE> findAll();
    
    public void addTaux(TAUXHORAIRE t);
    
    public void updateTaux(TAUXHORAIRE t);
    
    public void removeTaux(TAUXHORAIRE t);
    
    public TAUXHORAIRE findByCode(String code);
    
    public TAUXHORAIRE findById(int id);
}
