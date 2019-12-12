/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import ma.bservices.beans.Marque;
import ma.bservices.tgcc.utilitaire.MbHibernateDaoSupport;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author yassine.jeddi
 */
@Repository("marqueDAO")
@Transactional(rollbackFor = Exception.class)
public class MarqueDAOImp extends MbHibernateDaoSupport implements IMarqueDAO, Serializable {
    
    private static final Logger looger = Logger.getLogger(MarqueDAOImp.class.getName());
    
    @Override
    public void addMarque(Marque m) {
        try {
            Session session = getSessionFactory().openSession();
            session.setFlushMode(FlushMode.AUTO);
            session.beginTransaction();
            session.saveOrUpdate(m);
            session.getTransaction().commit();
            session.close();            
        } catch (Exception e) {
            looger.warning("Erreur d'ajouter une marque car " + e.getMessage());
        }
    }
    
    @Override
    public List<Marque> allMarque() {
        return this.getHibernateTemplate().loadAll(Marque.class);
    }
    
    @Override
    public List<Marque> allMarqueByType(String type){
        List<Marque> l = new ArrayList<Marque>();
        System.out.println("ma.bservices.dao.MarqueDAOImp.allMarqueByType() =======> type "+type);
        try {
            l = (List<Marque>) this.getHibernateTemplate().find("FROM Marque m where m.type= '"+type+"'");
        } catch (Exception e) {
            looger.warning("Erreur de recuperation lestes des marques par type car " + e.getMessage());
        }
        if(l.size()<1 || type == null){
            l=this.getHibernateTemplate().loadAll(Marque.class);
        }
        return l;
    }
    
}
