/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.services;

import java.io.Serializable;
import java.util.List;
import ma.bservices.dao.ITraceUser;
import ma.bservices.tgcc.Entity.TraceUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author yassine
 */
@Service("traceUserService")
public class TraceUserServiceImp implements Serializable,ITraceUserService{

    
    @Autowired
    private ITraceUser traceUserDao;

    public ITraceUser getTraceUserDao() {
        return traceUserDao;
    }

    public void setTraceUserDao(ITraceUser traceUserDao) {
        this.traceUserDao = traceUserDao;
    }
 

    public TraceUserServiceImp() {
    }

  
    
    @Override
    public void addTraceUser(TraceUser t) {
        traceUserDao.addTraceUser(t);
    }

    @Override
    public List<TraceUser> allTraceUser() {
        return traceUserDao.allTraceUser();
    }
    
}
