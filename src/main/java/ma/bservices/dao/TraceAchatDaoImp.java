/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.dao;

import java.io.Serializable;
import java.util.List;
import ma.bservices.beans.TraceAchat;
import ma.bservices.mb.services.MbHibernateDaoSupport;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

/**
 *
 * @author yassine
 */
@Repository("traceAchatDao")
public class TraceAchatDaoImp   extends MbHibernateDaoSupport implements ITraceAchatDao,Serializable {

    @Override
    public void addTraceAchat(TraceAchat t) {
        try {
                Session session = getSessionFactory().openSession();
                session.setFlushMode(FlushMode.AUTO); 
                session.beginTransaction();
                session.save(t); 
                session.getTransaction().commit(); 
                session.close();
        } catch (Exception e) {
            System.out.println("Erreur d'enregistrement TraceUser car "+e.getMessage());
        }
    }

    @Override
    public List<TraceAchat> allTraceAchat() {
        
         return this.getHibernateTemplate().loadAll(TraceAchat.class);
    }
    
}
