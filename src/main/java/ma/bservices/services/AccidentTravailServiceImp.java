/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.services;

import java.io.Serializable;
import java.util.List;
import ma.bservices.beans.AccidentTravail;
import ma.bservices.beans.Chantier;
import ma.bservices.beans.Salarie;
import ma.bservices.dao.IAccidentTravailDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author yassine
 */
@Service("accidentTravailService")
public class AccidentTravailServiceImp implements Serializable,IAccidentTravailService {
   
    @Autowired
    IAccidentTravailDao accidentTravailDao;

    public IAccidentTravailDao getAccidentTravailDao() {
        return accidentTravailDao;
    }

    public void setAccidentTravailDao(IAccidentTravailDao accidentTravailDao) {
        this.accidentTravailDao = accidentTravailDao;
    }

    @Override
    public Long addAccidentTravail(AccidentTravail a) {
        return accidentTravailDao.addAccidentTravail(a);
    }

    @Override
    public void editAccidentTravail(AccidentTravail a) {
       accidentTravailDao.editAccidentTravail(a);
    }

    @Override
    public void remouveAccidentTravail(AccidentTravail a) {
        accidentTravailDao.remouveAccidentTravail(a);
    }

    @Override
    public List<AccidentTravail> allAccidentTravail() {
        return accidentTravailDao.allAccidentTravail();
    }

    @Override
    public AccidentTravail allAccidentTravailById(Long id) {
       return accidentTravailDao.allAccidentTravailById(id);
    }

    @Override
    public List<AccidentTravail> allAccidentTravailByChantier(Chantier c) {
        return accidentTravailDao.allAccidentTravailByChantier(c);
    }

    @Override
    public List<AccidentTravail> allAccidentTravailBySalarie(Salarie s) {
        return accidentTravailDao.allAccidentTravailBySalarie(s);
    }

    @Override
    public List<AccidentTravail> allAccidentTravailByListChantier(List<Chantier> c) {
       return accidentTravailDao.allAccidentTravailByListChantier(c);
    }
    
}
