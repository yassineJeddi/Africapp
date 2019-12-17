/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.services;

import java.io.Serializable;
import java.util.List;
import ma.bservices.beans.DetailAT;
import ma.bservices.dao.IDetailATDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author yassine
 */
@Service("detailATService")
public class DetailATServiceImp  implements Serializable,IDetailATService {
    
    
    @Autowired
    IDetailATDao detailATDao;

    public IDetailATDao getDetailATDao() {
        return detailATDao;
    }

    public void setDetailATDao(IDetailATDao detailATDao) {
        this.detailATDao = detailATDao;
    }

    @Override
    public void addDetailAT(DetailAT d) {
        detailATDao.addDetailAT(d);
    }

    @Override
    public void editDetailAT(DetailAT d) {
        detailATDao.editDetailAT(d);
    }

    @Override
    public void remouveDetailAT(DetailAT d) {
        detailATDao.remouveDetailAT(d);
    }

    @Override
    public List<DetailAT> allDetailAT() {
        return detailATDao.allDetailAT();
    }

    @Override
    public DetailAT detailATById(Long id) {
        return detailATDao.detailATById(id);
    }

    @Override
    public List<DetailAT> allDetailATByAtId(Long id) {
        return detailATDao.allDetailATByAtId(id);
    }
    
    
    
    
}
