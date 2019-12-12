/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.dao.engin;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import ma.bservices.tgcc.Entity.CompteurrEngin;
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
 * @author yassine
 */
@Repository("compteurEnginDAO")
@Transactional(rollbackFor = Exception.class)
public class CompteurEnginDAOImp   extends HibernateDaoSupport implements ICompteurEnginDAO, Serializable {

    
    @Autowired
    public CompteurEnginDAOImp(SessionFactory sessionFactory){
        setSessionFactory(sessionFactory);
    }
    
    
    @Override
    public List<CompteurrEngin> allCompteurrEngin() {
         return this.getHibernateTemplate().loadAll(CompteurrEngin.class);
    }

    @Override
    public List<CompteurrEngin> allCompteurrEnginByIdEngin(Integer id_engin) {
       List<CompteurrEngin> lc  = new ArrayList<CompteurrEngin>();
        try {
           lc = (List<CompteurrEngin>) this.getHibernateTemplate().find("FROM CompteurrEngin c where c.engin.iDEngin="+id_engin);         
        } catch (DataAccessException e) {
            logger.warn("Erreur de récuperation du CompteurrEngin  par ID engin car :"+e.getMessage());
        }
        return lc;
    }

    @Override
    public CompteurrEngin findCompteurrEnginById(Integer id) {
        CompteurrEngin c  = new CompteurrEngin();
        try {
           c=this.getHibernateTemplate().load(CompteurrEngin.class, id);         
        } catch (DataAccessException e) {
            logger.warn("Erreur de récuperation CompteurrEngin car :"+e.getMessage());
        }
        return c;
    }

    @Override
    @Transactional
    public void saveCompteurrEngin(CompteurrEngin c) {
        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);
        session.beginTransaction();
        session.save(c);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    @Transactional
    public void editCompteurrEngin(CompteurrEngin c) {
        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);
        session.beginTransaction();
        session.update(c);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    @Transactional
    public void remouveCompteurrEngin(CompteurrEngin c) {
        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);
        session.beginTransaction();
        session.delete(c);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public CompteurrEngin findCompteurrEnginByDate(Date d) {
         CompteurrEngin c  = new CompteurrEngin();
        try {
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                String date_format = df.format(d);
                System.out.println("==================================> date_format : "+date_format);
                System.out.println("==================================> CompteurrEngin : "+c.toString());
                List l = this.getHibernateTemplate().find("FROM CompteurrEngin c where c.dateChangement='"+date_format+"'");                 
                System.out.println("==================================> L : "+l);
                if(l.size()>0){  c=   (CompteurrEngin) l.get(0);}
                System.out.println("==================================> CompteurrEngin : "+c.toString());
        } catch (DataAccessException e) {
            logger.warn("Erreur de récuperation CompteurrEngin car :"+e.getMessage());
        }
        return c;
    }
    
}
