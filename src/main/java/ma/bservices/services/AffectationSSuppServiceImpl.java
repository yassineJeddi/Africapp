/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import ma.bservices.beans.AffectationSalarieSupp;
import ma.bservices.beans.Chantier;
import ma.bservices.beans.Salarie;
import ma.bservices.dao.AffectationSSuppDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author j.allali
 */
@Service("affectationSSuppService")
public class AffectationSSuppServiceImpl implements Serializable, AffectationSSuppService {

    @Autowired
    AffectationSSuppDAO affectationDAO;

    public AffectationSSuppDAO getAffectationDAO() {
        return affectationDAO;
    }

    public void setAffectationDAO(AffectationSSuppDAO affectationDAO) {
        this.affectationDAO = affectationDAO;
    }

    @Override
    public Boolean affecterSupp(AffectationSalarieSupp affect) {
        return affectationDAO.affecterSupp(affect);
    }

    @Override
    public AffectationSalarieSupp updateSalarie(AffectationSalarieSupp affect) {
        return affectationDAO.updateSalarie(affect);
    }

    @Override
    public List<AffectationSalarieSupp> listSalarieBySupp() {
        return affectationDAO.listSalarieBySupp();
    }

    @Override
    public List<AffectationSalarieSupp> listSalarieBySupp(Integer idSupp) {
        return affectationDAO.listSalarieBySupp(idSupp);
    }

    @Override
    public AffectationSalarieSupp listSalarieByChantier(Integer idSupp, Integer idSalarie, Integer idChantier) {
        return affectationDAO.listSalarieByChantier(idSupp, idSalarie, idChantier);
    }

    @Override
    public List<AffectationSalarieSupp> listSalarieByChantierDateSupp(Integer idSupp, Date dateDe, Date dateA, Integer idChantier) {
        System.out.println("SERVICE METHOD =====");
        if (dateA != null && dateDe != null && dateA.compareTo(dateDe) == 1) {
            return affectationDAO.listSalarieByChantierDateSupp(idSupp, dateDe, dateA, idChantier);
        }
        return affectationDAO.listSalarieByChantierDateSupp(idSupp, dateA, dateDe, idChantier);
    }

    @Override
    public Boolean pointageEntreeSalarie(Integer idSalarie, Long longDateTimePointage, String datee) {
        return affectationDAO.pointageEntreeSalarie(idSalarie, longDateTimePointage, datee);
    }

    @Override
    public Boolean desaffectation(Chantier c, Salarie chef) {
        try {
                for (AffectationSalarieSupp af : affectationDAO.listAffectationByChantier(c.getId())) {
                if (af.getChefEquipe().getId().equals(chef.getId())) {
                    af.setCurrentSupp(Boolean.FALSE);
                    affectationDAO.updateSalarie(af);
                }
            }
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    @Override
    public List<Salarie> salarieByLastSuperieurAndChantier(Salarie chef, Chantier c) {
        try {
            //List<Salarie> salaries = new LinkedList<>();
            List<Salarie> salaries = affectationDAO.listAffectationByChantierAndChefEquipe(c.getId(), chef);
            /*
            for (AffectationSalarieSupp af : lAffectationSalarieSupp) {
                if (af.getChefEquipe().getId().equals(chef.getId())) {
                    salaries.add(af.getSalaries());
                }
            }
            */
                
            //System.out.println("result salaries" + salaries.size());
            return salaries;
        } catch (Exception e) {
            System.out.println("erreur " + e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public AffectationSalarieSupp getLastSuperiorBySalarie(Salarie s) {
        for (AffectationSalarieSupp a : affectationDAO.listSalarieBySupp()) {
            if (a.getSalaries().getId().equals(s.getId()) && a.getCurrentSupp()) {
                return a;
            }
        }
        return null;
    }

    @Override
    public AffectationSalarieSupp getLastSuperiorBySalarieAndChantier(Salarie s, Integer idChantier) {
        
        List<AffectationSalarieSupp> lAffectationSalarieSupp = affectationDAO.listAffectationByChantierAndSalarie(idChantier, s);
        if(lAffectationSalarieSupp.size() > 0){
            return lAffectationSalarieSupp.get(0);
        }
        /*
        for (AffectationSalarieSupp a : lAffectationSalarieSupp) {
            if (a.getSalaries().getId().equals(s.getId())) {
                return a;
            }
        }*/
        return null;
    }
}
