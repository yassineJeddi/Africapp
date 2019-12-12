/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.service.Mensuel;

import java.io.Serializable;
import ma.bservices.beans.Chantier;
import ma.bservices.tgcc.Entity.PointageMensuelQuinzinier;
import ma.bservices.tgcc.dao.Mensuel.PointageMensuelQuinzinierDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author a.wattah
 */
@Service("pointageMensuelQuinzinierService")
public class PointageMensuelQuinzinierServiceImpl implements PointageMensuelQuinzinierService , Serializable{
    
    
    @Autowired
    private PointageMensuelQuinzinierDAO pointageMensuelQuinzinierDAO;

    public PointageMensuelQuinzinierDAO getPointageMensuelQuinzinierDAO() {
        return pointageMensuelQuinzinierDAO;
    }

    public void setPointageMensuelQuinzinierDAO(PointageMensuelQuinzinierDAO pointageMensuelQuinzinierDAO) {
        this.pointageMensuelQuinzinierDAO = pointageMensuelQuinzinierDAO;
    }

    @Override
    public Boolean save(PointageMensuelQuinzinier mensuelQuinzinier) {
        return this.pointageMensuelQuinzinierDAO.save(mensuelQuinzinier);
    }

    @Override
    public Chantier getChantierByLib(String lib) {
        return this.pointageMensuelQuinzinierDAO.getChantierByLib(lib);
    }
    
    
    @Override
    public Boolean delete(PointageMensuelQuinzinier pointageMensuelQuinzinier) {
        return this.pointageMensuelQuinzinierDAO.delete(pointageMensuelQuinzinier);
    }
}
