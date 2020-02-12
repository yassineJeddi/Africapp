/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.services;

import java.io.Serializable;
import java.util.List;
import ma.bservices.beans.TraceAchat;
import ma.bservices.dao.ITraceAchatDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author yassine
 */
@Service("traceAchatService")
public class TraceAchatServiceImp implements ITraceAchatService, Serializable {
    
    @Autowired
    private ITraceAchatDao traceAchatDao ;

    public ITraceAchatDao getTraceAchatDao() {
        return traceAchatDao;
    }

    public void setTraceAchatDao(ITraceAchatDao traceAchatDao) {
        this.traceAchatDao = traceAchatDao;
    }

    @Override
    public void addTraceAchat(TraceAchat t) {
        traceAchatDao.addTraceAchat(t);
    }

    @Override
    public List<TraceAchat> allTraceAchat() {
        return traceAchatDao.allTraceAchat();
    }
    
}
