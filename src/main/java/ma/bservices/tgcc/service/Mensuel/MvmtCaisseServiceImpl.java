/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.service.Mensuel;

import java.io.Serializable;
import java.util.List;
import ma.bservices.tgcc.Entity.MvmtCaisse;
import ma.bservices.tgcc.dao.Mensuel.IMvmtCaisseDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author yassine.jeddi
 */
@Service("mvmtCaisseServiceImpl")
public class MvmtCaisseServiceImpl implements IMvmtCaisseService, Serializable{

     @Autowired
    private IMvmtCaisseDAO mvmtCaisseDAO;

    public IMvmtCaisseDAO getMvmtCaisseDAO() {
        return mvmtCaisseDAO;
    }

    public void setMvmtCaisseDAO(IMvmtCaisseDAO mvmtCaisseDAO) {
        this.mvmtCaisseDAO = mvmtCaisseDAO;
    }
     
     
    
    @Override
    public void addMvmtCaisse(MvmtCaisse m) {
        mvmtCaisseDAO.addMvmtCaisse(m);
    }

    @Override
    public void editMvmtCaisse(MvmtCaisse m) {
        mvmtCaisseDAO.editMvmtCaisse(m);
    }

    @Override
    public void remouvMvmtCaisse(MvmtCaisse m) {
        mvmtCaisseDAO.remouvMvmtCaisse(m);
    }

    @Override
    public MvmtCaisse mvmtCaisseByID(int id) {
            return mvmtCaisseDAO.mvmtCaisseByID(id);
    }

    @Override
    public List<MvmtCaisse> allMvmtCaisse() {
        return mvmtCaisseDAO.allMvmtCaisse();
    }

    @Override
    public List<MvmtCaisse> allMvmtCaisseByIdCaisse(int idCaisse) {
        return mvmtCaisseDAO.allMvmtCaisseByIdCaisse(idCaisse);
    }

    @Override
    public String soldeCaisse(int idCaisse) {
        return mvmtCaisseDAO.soldeCaisse(idCaisse);
    }
    
}
