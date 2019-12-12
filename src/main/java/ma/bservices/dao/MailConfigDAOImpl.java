/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.dao;

import java.io.Serializable;
import java.util.List;
import ma.bservices.mb.services.MbHibernateDaoSupport;
import ma.bservices.tgcc.Entity.MailConfigBean;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author airaamane
 */
@Repository("mailConfigDAO")
@Transactional
public class MailConfigDAOImpl extends MbHibernateDaoSupport implements MailConfigDAO, Serializable {

    @Override
    public List<MailConfigBean> findByModule(String module) {
        List l = this.getHibernateTemplate().find("SELECT m FROM MailConfigBean m WHERE m.module = '" + module + "'");
        if (l.size() > 0) {
            return l;
        }
        return null;
    }

    @Override
    public void update(MailConfigBean bean) {
        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);

        session.beginTransaction();
        session.update(bean);

        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List<MailConfigBean> findAll() {
        List l = this.getHibernateTemplate().find("SELECT m FROM MailConfigBean m");
        if (l.size() > 0) {
            return l;
        }
        return null;
    }

}
