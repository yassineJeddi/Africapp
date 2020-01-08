/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.services;

import java.io.Serializable;
import java.util.List;
import ma.bservices.beans.AccidentTravail;
import ma.bservices.beans.QuittanceAt;
import ma.bservices.dao.IQuittanceAtDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author yassine
 */
@Service("quittanceAtService")
public class QuittanceAtServiceImp   implements Serializable,IQuittanceAtService {
    
    @Autowired
    IQuittanceAtDao quittanceAtDao;

    public IQuittanceAtDao getQuittanceAtDao() {
        return quittanceAtDao;
    }

    public void setQuittanceAtDao(IQuittanceAtDao quittanceAtDao) {
        this.quittanceAtDao = quittanceAtDao;
    }
    
    

    @Override
    public void addQuittanceAt(QuittanceAt d) {
        quittanceAtDao.addQuittanceAt(d);
    }

    @Override
    public void editQuittanceAt(QuittanceAt d) {
        quittanceAtDao.editQuittanceAt(d);
    }

    @Override
    public void remouvQuittanceAt(QuittanceAt d) {
        quittanceAtDao.remouvQuittanceAt(d);
    }

    @Override
    public QuittanceAt quittanceAtById(Integer id) {
        return quittanceAtDao.quittanceAtById(id);
    }

    @Override
    public List<QuittanceAt> allQuittanceAtByAt(AccidentTravail a) {
        return quittanceAtDao.allQuittanceAtByAt(a);
    }
    
}
