/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.services;

import java.util.List;
import ma.bservices.beans.Marque;
import ma.bservices.dao.IMarqueDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author yassine.jeddi
 */
@Service("marqueService")
public class MarqueServiceImp implements IMarqueService{

    @Autowired
    private IMarqueDAO marqueDAO;

    public IMarqueDAO getMarqueDAO() {
        return marqueDAO;
    }

    public void setMarqueDAO(IMarqueDAO marqueDAO) {
        this.marqueDAO = marqueDAO;
    }


    @Override
    public void addMarque(Marque m) {
        marqueDAO.addMarque(m);
    }

    @Override
    public List<Marque> allMarque() {
        return marqueDAO.allMarque();
    }
    
    
    @Override
    public List<Marque> allMarqueByType(String type){
         return marqueDAO.allMarqueByType(type);
    }
}
