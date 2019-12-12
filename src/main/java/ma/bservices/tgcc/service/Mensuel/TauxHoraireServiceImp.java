/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.service.Mensuel;

import java.io.Serializable;
import java.util.List;
import ma.bservices.tgcc.Entity.TAUXHORAIRE;
import ma.bservices.tgcc.dao.Mensuel.TauxHoraireDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author yassine.jeddi
 */
@Service("tauxHoraireService")
public class TauxHoraireServiceImp implements TauxHoraireService, Serializable {
    
    
    @Autowired
    private TauxHoraireDAO tauxHoraireDAO;

    @Override
    public List<TAUXHORAIRE> findAll() {
       return tauxHoraireDAO.findAll();
    }

    @Override
    public void addTaux(TAUXHORAIRE t) {
        tauxHoraireDAO.addTaux(t);
    }

    @Override
    public void updateTaux(TAUXHORAIRE t) {
        tauxHoraireDAO.updateTaux(t);
    }

    @Override
    public void removeTaux(TAUXHORAIRE t) {
        tauxHoraireDAO.removeTaux(t);
    }

    @Override
    public TAUXHORAIRE findByCode(String code) {
        return tauxHoraireDAO.findByCode(code);
    }

    @Override
    public TAUXHORAIRE findById(int id) {
        return tauxHoraireDAO.findById(id);
    }
    
}
