/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.dao.stock;

import java.io.Serializable;
import java.util.List;
import ma.bservices.beans.Chantier;
import ma.bservices.tgcc.Entity.TransferStockManager;
import ma.bservices.tgcc.utilitaire.MbHibernateDaoSupport;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author airaamane
 */

@Repository("transferStockManagerDAO")
@Transactional
public class TransferStockManagerDAOImpl  extends MbHibernateDaoSupport implements TransferStockManagerDAO, Serializable {

     @Override
    public List<TransferStockManager> findAll(Chantier chantier) {
        List l = this.getHibernateTemplate().find("SELECT t FROM TransferStockManager t WHERE t.transferToManage.chantierEmetteurId.id = '" + chantier.getId() + "'");
       return l;
    }

    
    
    
    @Override
    public void addEntry(TransferStockManager entry) {
        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);
        session.beginTransaction();
        session.save(session.merge(entry));
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void updateEntry(TransferStockManager entry) {
        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);
        session.beginTransaction();
        session.update(session.merge(entry));
        session.getTransaction().commit();
        session.close();
    }
    
    
}
