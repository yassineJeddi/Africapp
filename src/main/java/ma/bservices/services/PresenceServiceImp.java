/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.services;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import ma.bservices.beans.Presence;
import ma.bservices.dao.IPresenceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author yassine
 */
@Service("presenceServicei")
public class PresenceServiceImp implements Serializable,IPresenceService{
    
    
    @Autowired
    IPresenceDao presenceDao;

    public IPresenceDao getPresenceDao() {
        return presenceDao;
    }

    public void setPresenceDao(IPresenceDao presenceDao) {
        this.presenceDao = presenceDao;
    }
    
    

    @Override
    public List<Presence> allPresenceByChantier(Integer chantierId) {
        return presenceDao.allPresenceByChantier(chantierId);
    }

    @Override
    public List<Presence> allPresenceByChantierAndDate(Integer chantierId, Date dd, Date df) {
        return presenceDao.allPresenceByChantierAndDate(chantierId, dd, df);
    }

    @Override
    public List<Presence> allPresenceBySalarie(Integer salarieId) {
        return presenceDao.allPresenceBySalarie(salarieId);
    }
    
}
