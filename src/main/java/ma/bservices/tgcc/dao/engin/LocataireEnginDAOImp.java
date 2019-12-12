/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.dao.engin;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import ma.bservices.mb.services.MbHibernateDaoSupport;
import ma.bservices.tgcc.Entity.LocataireEngin;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author yassine
 */
@Repository("locatairEnginDao")
@Transactional
public class LocataireEnginDAOImp  extends MbHibernateDaoSupport implements ILocataireEnginDAO , Serializable{
   
    

    @Override
    public void addSstEngin(LocataireEngin s) {
           
        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);
        session.beginTransaction();
        session.saveOrUpdate(s);
        session.getTransaction().commit();
        session.close();
        
    }

    @Override
    public List<LocataireEngin> allSstEngin() {
         List<LocataireEngin> l = new ArrayList<LocataireEngin>();
        try {
            l = this.getHibernateTemplate().loadAll(LocataireEngin.class);
        } catch (Exception e) {
            System.out.println("Erreur de récuperation des SSTengins car : "+e.getMessage());
        }
        return l;
    }
    
}
