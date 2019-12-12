/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.dao.Mensuel;

import java.io.Serializable;
import java.util.List;
import ma.bservices.beans.Chantier;
import ma.bservices.tgcc.Entity.PointageMensuelQuinzinier;
import ma.bservices.tgcc.utilitaire.MbHibernateDaoSupport;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author a.wattah
 */
@Repository("pointageMensuelQuinzinierDAO")
@Transactional
public class PointageMensuelQuinzinierDAOImpl extends MbHibernateDaoSupport implements PointageMensuelQuinzinierDAO, Serializable{

    @Override
    public Boolean save(PointageMensuelQuinzinier pointageMensuelQuinzinier) {
         Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);

        session.beginTransaction();
        session.save(pointageMensuelQuinzinier);

        session.getTransaction().commit();

        session.close();
        return true;
    }

    @Override
    public Chantier getChantierByLib(String lib) {
        List l = this.getHibernateTemplate().find("SELECT c FROM Chantier c WHERE c.code = '" + lib+ "'");
        if (l.size() > 0) {
            return (Chantier) l.get(0);
        }
        return null;
    }
    
    
    /*
    * test Unitaire suprimer pointage mensuel
    */
    
     @Override
    public Boolean delete(PointageMensuelQuinzinier pointageMensuelQuinzinier) {
         Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);

        session.beginTransaction();
        session.delete(pointageMensuelQuinzinier);

        session.getTransaction().commit();

        session.close();
        return true;
    }
    
    
}
