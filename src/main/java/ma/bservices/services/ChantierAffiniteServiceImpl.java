/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.services;

import java.io.Serializable;
import java.util.List;
import ma.bservices.beans.Chantier;
import ma.bservices.beans.ChantierAffinite;
import ma.bservices.dao.ChantierAffiniteDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author airaamane
 */

@Service("chantierAffiniteService")
public class ChantierAffiniteServiceImpl implements Serializable, ChantierAffiniteService {

    @Autowired
    ChantierAffiniteDAO chantierAffiniteDAO;

    public ChantierAffiniteDAO getChantierAffiniteDAO() {
        return chantierAffiniteDAO;
    }

    public void setChantierAffiniteDAO(ChantierAffiniteDAO chantierAffiniteDAO) {
        this.chantierAffiniteDAO = chantierAffiniteDAO;
    }  
    
    @Override
    public List<ChantierAffinite> findAll() {
       return chantierAffiniteDAO.findAll();
    }
    
      @Override
    public List<ChantierAffinite> findByChantier(Integer c) {
       return chantierAffiniteDAO.findByChantier(c);
    }

    @Override
    public void save(ChantierAffinite c) {
        chantierAffiniteDAO.save(c);
    }

    @Override
    public void update(ChantierAffinite c) {
        chantierAffiniteDAO.update(c);
    }
    
    
    
}
