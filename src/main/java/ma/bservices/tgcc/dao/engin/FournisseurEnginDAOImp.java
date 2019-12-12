/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.dao.engin;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import ma.bservices.mb.services.MbHibernateDaoSupport;
import ma.bservices.tgcc.Entity.FournisseurEngin;
import org.hibernate.FlushMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author yassine
 */
@Repository("fournisseurEnginDAO")
@Transactional(rollbackFor = Exception.class)
public class FournisseurEnginDAOImp extends MbHibernateDaoSupport implements Serializable, IFournisseurEnginDAO{

    private static final Logger looger = Logger.getLogger(TypeEnginDAOImp.class.getName());
    
    @Override
    @Transactional
    public void addFournisseurEngin(FournisseurEngin e) {
          try {
                Session session = getSessionFactory().openSession();
                session.setFlushMode(FlushMode.AUTO);
                session.beginTransaction();
                session.saveOrUpdate(e);
                session.getTransaction().commit();
                session.close();
        } catch (HibernateException em) {
            looger.log(Level.WARNING, "Erreur d''enregistrement de Type Engin car : {0}", em.getMessage());
        }
    }

    @Override
    public List<FournisseurEngin> allFournisseurEngin() {
        return this.getHibernateTemplate().loadAll(FournisseurEngin.class);
    }
    
}
