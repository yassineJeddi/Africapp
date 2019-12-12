/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.dao;

import java.util.List;
import ma.bservices.beans.Chantier;
import ma.bservices.beans.ChantierAffinite;

/**
 *
 * @author airaamane
 */
public interface ChantierAffiniteDAO {

    public List<ChantierAffinite> findAll();
    
    public List<ChantierAffinite> findByChantier(Integer c);

    public void save(ChantierAffinite c);

    public void update(ChantierAffinite c);

}
