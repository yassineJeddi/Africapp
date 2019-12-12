/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.dao.Mensuel;

import java.io.Serializable;
import java.util.List;
import ma.bservices.tgcc.Entity.Element;
import ma.bservices.tgcc.utilitaire.MbHibernateDaoSupport;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author a.wattah
 */
@Repository("elementDao")
@Transactional
public class ElementDAOImpl extends  MbHibernateDaoSupport implements ElementDAO , Serializable{
    
    
    
    @Override
    public void update(Element element) {
        
        
        
         Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);

        session.beginTransaction();
        session.update(element);

        session.getTransaction().commit();

        session.close();
        
    }

    @Override
    public List<Element> getElementByIdMensuel(int id) {
      
         List l=null;
         l=this.getHibernateTemplate().find("select c FROM Element c WHERE c.mensuel.id = " + id);
         if(l.size()>0){
             return l;
         }
        return null;
         
    
    }
    
    
    /**
     * test unitaire 
     */
    
    @Override
    public void save(Element element) {
        
        
        
         Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);

        session.beginTransaction();
        session.save(element);

        session.getTransaction().commit();

        session.close();
        
    }
    
    
     @Override
    public void delete(Element element) {
        
        
        
         Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);

        session.beginTransaction();
        session.delete(element);

        session.getTransaction().commit();

        session.close();
        
    }
}
