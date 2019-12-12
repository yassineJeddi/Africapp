/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.dao.engin;

import java.io.Serializable;
import java.util.List;
import ma.bservices.tgcc.Entity.ConducteurEngin;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author yassine.jeddi
 */
@Repository("conducteurEnginDAO")
@Transactional(rollbackFor = Exception.class)
public class ConducteurEnginDAOImpl  extends HibernateDaoSupport implements IConducteurEnginDAO, Serializable{

    public ConducteurEnginDAOImpl() {
    }

    
    @Autowired
    public ConducteurEnginDAOImpl(SessionFactory sessionFactory){
        setSessionFactory(sessionFactory);
    }
    
    
    @Override
    public List<ConducteurEngin> allConducteurEngins() {
        
        return this.getHibernateTemplate().loadAll(ConducteurEngin.class);
    }

    @Override
    public ConducteurEngin conducteurEnginById(Integer iDConducteurEngin) {
        ConducteurEngin c  = new ConducteurEngin();
        try {
           c=this.getHibernateTemplate().load(ConducteurEngin.class, iDConducteurEngin);         
        } catch (DataAccessException e) {
            logger.warn("Erreur de récuperation du Conducteur engin par ID car :"+e.getMessage());
        }
        return c;
    }

    @Override
    public ConducteurEngin lastConducteurEnginByEngin(Integer id_engin) {
         ConducteurEngin c  = new ConducteurEngin();
        try {
        List l = this.getHibernateTemplate().find("SELECT ce FROM ConducteurEngin ce WHERE "
                + " ce.DATE_DEB_AFECT = (SELECT MAX(c.DATE_DEB_AFECT) FROM ConducteurEngin c WHERE c.ID_ENGIN = '" + id_engin + "')"
                + " AND ce.ID_ENGIN = '" + id_engin );
        c =  (ConducteurEngin) l.get(0);            
        } catch (DataAccessException e) {
            logger.warn("Erreur de récuperation du Conducteur engin par ID car :"+e.getMessage());
        }
        return c;
    }

    @Override
    @Transactional
    public void addConducteurEngin(ConducteurEngin c) {
        
        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);
        session.beginTransaction();
        session.save(c);
        session.getTransaction().commit();
        session.close();

        
    }

    @Override
    @Transactional
    public void editConducteurEngin(ConducteurEngin c) {
        
        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);
        session.beginTransaction();
        session.update(c);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    @Transactional
    public void remouveConducteurEngin(ConducteurEngin c) {
        
        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);
        session.beginTransaction();
        session.delete(c);
        session.getTransaction().commit();
        session.close();
    }
    
}
