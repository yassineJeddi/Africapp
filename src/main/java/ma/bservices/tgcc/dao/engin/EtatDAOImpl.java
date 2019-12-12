/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.dao.engin;

import java.io.Serializable;
import java.util.List;
import ma.bservices.beans.Etat;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author a.wattah
 */
@Repository("etatDAO")
@Transactional
public class EtatDAOImpl extends HibernateDaoSupport implements EtatDAO, Serializable {

    @Override
    public List<Etat> findAllEtat() {
        return this.getHibernateTemplate().loadAll(Etat.class);
    }

    @Override
    public Etat findOneByLabel(String label) {

        List l = this.getHibernateTemplate().find("SELECT e FROM Etat e WHERE e.etat = '" + label +"'");

        if (l.size() > 0) {
            return (Etat) l.get(0);
        }

        return null;
    }

    @Override
    @Transactional
    public void ajouterEngin(Etat etat) {

        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);

        session.beginTransaction();
        session.save(etat);

        session.getTransaction().commit();

        session.close();

//        try {
//            this.getHibernateTemplate().save(engin);
//            this.getHibernateTemplate().flush();
//        } catch (Exception e) {
//        }
    }
    
    @Override
    @Transactional
    public void deleteEtat(Etat etat) {

        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);

        session.beginTransaction();
        session.delete(etat);

        session.getTransaction().commit();

        session.close();

//        try {
//            this.getHibernateTemplate().save(engin);
//            this.getHibernateTemplate().flush();
//        } catch (Exception e) {
//        }
    }
    
}
