/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.dao.engin;

import java.io.Serializable;
import java.util.List;
import ma.bservices.tgcc.Entity.DocumentEngin;
import ma.bservices.tgcc.utilitaire.MbHibernateDaoSupport;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author a.wattah
 */
@Repository("documentEnginDAO")
@Transactional
public class DocumentEnginDaoImpl extends MbHibernateDaoSupport implements DocumentEnginDAO, Serializable {

    @Override
    public void save(DocumentEngin documentEngin) {

        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);

        session.beginTransaction();
        session.save(documentEngin);

        session.getTransaction().commit();

        session.close();
    }

    @Override
    public List<DocumentEngin> getListDocumentEngins(int id) {

        List l = this.getHibernateTemplate().find("From DocumentEngin d where d.engin.iDEngin = " + id);

        return l;
    }

    @Override
    public void delete(DocumentEngin documentEngin) {
        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);

        session.beginTransaction();
        session.delete(documentEngin);

        session.getTransaction().commit();

        session.close();
    }

    @Override
    public void update(DocumentEngin documentEngin) {
        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);

        session.beginTransaction();
        session.update(documentEngin);

        session.getTransaction().commit();

        session.close();
    }

}
