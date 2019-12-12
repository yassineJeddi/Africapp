/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.dao;

import java.io.Serializable;
import java.util.List;
import ma.bservices.beans.Docs;
import ma.bservices.mb.services.MbHibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author air
 */

@Repository("docsDAO")
@Transactional
public class DocsDAOImpl extends MbHibernateDaoSupport implements DocsDAO, Serializable {

    @Override
    public List<Docs> findAll() {
        return this.getHibernateTemplate().loadAll(Docs.class);
    }

    @Override
    public Docs findByNodeRef(String nodeRef) {
        
         List l = this.getHibernateTemplate().find("SELECT d FROM Docs d WHERE d.nodeRef = '" + nodeRef + "'");
        if (l.size() > 0) {
            return (Docs) l.get(0);
        }
        return null;
        
    }
    
}
