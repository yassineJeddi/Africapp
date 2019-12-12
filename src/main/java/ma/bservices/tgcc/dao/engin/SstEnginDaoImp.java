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
import ma.bservices.tgcc.Entity.SstEngin;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author yassine
 */
@Repository("sstEnginDAO")
@Transactional
public class SstEnginDaoImp extends MbHibernateDaoSupport implements ISstEnginDAO , Serializable {
    

    @Override
    public void addSstEngin(SstEngin s) {
        
        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);
        session.beginTransaction();
        session.saveOrUpdate(s);
        session.getTransaction().commit();
        session.close();
        
    }

    @Override
    public List<SstEngin> allSstEngin() {
          List<SstEngin> l = new ArrayList<SstEngin>();
        try {
            l = this.getHibernateTemplate().loadAll(SstEngin.class);
        } catch (Exception e) {
            System.out.println("Erreur de récuperation des SSTengins car : "+e.getMessage());
        }
        return l;
    }
    
}
