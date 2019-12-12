/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.dao;

import java.io.Serializable;
import java.util.List; 
import ma.bservices.tgcc.Entity.TraceUser;
import ma.bservices.tgcc.utilitaire.MbHibernateDaoSupport;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.springframework.stereotype.Repository; 

/**
 *
 * @author yassine
 */
@Repository("traceUserDao")
public class TraceUserDaoImp  extends MbHibernateDaoSupport implements ITraceUser,Serializable{
 

       
    @Override
    public void addTraceUser(TraceUser t) {
        try {
                Session session = getSessionFactory().openSession();
                session.setFlushMode(FlushMode.AUTO); 
                session.beginTransaction();
                session.saveOrUpdate(t); 
                session.getTransaction().commit(); 
                session.close();
        } catch (Exception e) {
            System.out.println("Erreur d'enregistrement TraceUser car "+e.getMessage());
        }
    }

    @Override
    public List<TraceUser> allTraceUser() { 
         return this.getHibernateTemplate().loadAll(TraceUser.class);
    }
    
}
