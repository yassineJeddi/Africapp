/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.dao.Mensuel;

import java.util.ArrayList;
import java.util.List;
import ma.bservices.tgcc.Entity.Caisse;
import ma.bservices.tgcc.utilitaire.MbHibernateDaoSupport;
import org.apache.log4j.Logger;
import org.hibernate.FlushMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author yassine.jeddi
 */
@Repository("caisseDAO")
@Transactional
public class CaisseDAOImp extends MbHibernateDaoSupport implements ICaisseDAO{
    
       protected static Logger logger = Logger.getLogger(CaisseDAOImp.class); 

    @Override
    public void addCaisse(Caisse c) {
        try { 
            c.setArchive(Boolean.FALSE); 
            this.getHibernateTemplate().save(c);
            getHibernateTemplate().flush(); 
        } catch (HibernateException e) {
            logger.error("Erreur d'insertion la caisse car "+e.getMessage());
        }
    }

    @Override
    public void editCaisse(Caisse c) {
       Transaction tx = null;
        Session session = getSessionFactory().openSession();
        try {
            session.setFlushMode(FlushMode.AUTO);
            tx = session.beginTransaction();
            session.update(c);
            tx.commit();
            session.close(); 
        } catch (HibernateException e) {
            tx.rollback();
            session.close();
            logger.error("Erreur de Modification de la caisse car "+e.getMessage()); 
        }
    }

    @Override
    public void remouuvCaisse(Caisse c) {
         try {
            c.setArchive(Boolean.TRUE);
            this.getHibernateTemplate().saveOrUpdate(c);
            getHibernateTemplate().flush(); 
        } catch (Exception e) {
            logger.error("Erreur de supression de la caisse car "+e.getMessage());
        }
    }

    @Override
    public List<Caisse> allCaisse() {
        List<Caisse> l =new ArrayList<Caisse>();
        try {
        l=(List<Caisse>) this.getHibernateTemplate().find("From Caisse c "
                    + " where c.archive = '" + Boolean.FALSE+"'" );
        } catch (Exception e) {
                logger.warn("Erreur de récuperation liste des caisse car : "+e.getMessage());
        }
        System.out.println("ma.bservices.tgcc.dao.Mensuel.CaisseDAOImp.allCaisse()");
        return l;
    }

    @Override
    public Caisse caisseByID(Long idCaisse) {
        Caisse c =new Caisse();
        try {
        List l=(List<Caisse>) this.getHibernateTemplate().find("From Caisse c "
                    + " where c.archive = '" + Boolean.FALSE+"'" );
        c = (Caisse) l.get(0);
        } catch (Exception e) {
                logger.warn("Erreur de récuperation la caisse car : "+e.getMessage());
        }
        return c;
    }
    
}
