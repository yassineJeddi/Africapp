/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.dao.stock;

import java.io.Serializable;
import java.util.List;
import ma.bservices.tgcc.Entity.StatusTransfert;
import ma.bservices.tgcc.utilitaire.MbHibernateDaoSupport;
import org.hibernate.FlushMode;
import org.hibernate.Session;

/**
 *
 * @author IRAAMANE
 */
public class StatusTransferDAOImpl extends MbHibernateDaoSupport implements StatusTransferDAO, Serializable {

    @Override
    public List<StatusTransfert> findAll() {
        return this.getHibernateTemplate().loadAll(StatusTransfert.class);
    }

    @Override
    public StatusTransfert findById(int id) {

        List l = this.getHibernateTemplate().find("SELECT l FROM StatusTransfert l WHERE l.idStatusTransfert = " + id);
        if (l.size() > 0) {
            return (StatusTransfert) l.get(0);
        }
        return null;
    }

    @Override
    public void addStatus(StatusTransfert status) {
        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);

        session.beginTransaction();
        session.save(status);

        session.getTransaction().commit();
        session.close();
    }

}
