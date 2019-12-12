/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.dao.engin;

import java.io.Serializable;
import java.util.List;
import ma.bservices.tgcc.Entity.EtatEngin;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author a.wattah
 */
@Repository("etatEnginDAO")
@Transactional
public class EtatEnginDAOImpl extends HibernateDaoSupport implements EtatEnginDAO, Serializable {

    @Override
    public EtatEngin findOneByLabel(String label) {
        List l = this.getHibernateTemplate().find("SELECT e FROM EtatEngin e WHERE e.libelleEtat = '" + label + "'");

        if (l.size() > 0) {
            System.out.println("________ /////// SIZE ETAT ENGIN --------______"+l.size());
            return (EtatEngin) l.get(0);
        }

        return null;
    }
    
     @Override
    @Transactional
    public Boolean ajouterEtatE(EtatEngin ee) {

        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);

        session.beginTransaction();
        session.save(ee);

        session.getTransaction().commit();

        session.close();
        return true;
}
    
     @Override
    @Transactional
    public Boolean deleteEtatE(EtatEngin ee) {

        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);

        session.beginTransaction();
        session.delete(ee);

        session.getTransaction().commit();

        session.close();
        return true;
}

    @Override
    public List<EtatEngin> findAllEtatEngin() {
        return this.getHibernateTemplate().loadAll(EtatEngin.class);
    }

}
