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
import ma.bservices.tgcc.Entity.TypeEngin;
import org.hibernate.FlushMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author yassine.jeddi
 */
@Repository("typeEnginDAO")
@Transactional(rollbackFor = Exception.class)
public class TypeEnginDAOImp extends MbHibernateDaoSupport implements ITypeEnginDAO, Serializable{

    
    private static final Logger looger = Logger.getLogger(TypeEnginDAOImp.class.getName());
    
    @Override
    @Transactional
    public void addTypeEngin(TypeEngin t) {
        try {
                Session session = getSessionFactory().openSession();
                session.setFlushMode(FlushMode.AUTO);
                session.beginTransaction();
                session.saveOrUpdate(t);
                session.getTransaction().commit();
                session.close();
        } catch (HibernateException e) {
            looger.log(Level.WARNING, "Erreur d''enregistrement de Type Engin car : {0}", e.getMessage());
        }
    }

    @Override
    public List<TypeEngin> allTypeEngin() {
        return this.getHibernateTemplate().loadAll(TypeEngin.class);
    }
    
}
