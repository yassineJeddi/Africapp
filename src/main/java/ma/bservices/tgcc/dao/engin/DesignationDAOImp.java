/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.dao.engin;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import ma.bservices.beans.Designation;
import ma.bservices.tgcc.utilitaire.MbHibernateDaoSupport;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author yassine.jeddi
 */
@Repository("designiationDAO")
@Transactional(rollbackFor = Exception.class)
public class DesignationDAOImp extends MbHibernateDaoSupport implements IDesignationDAO {

    private static final Logger looger = Logger.getLogger(DesignationDAOImp.class.getName());
    

    @Override
    public void addDesignation(Designation d) {
         try {
            Session session = getSessionFactory().openSession();
            session.setFlushMode(FlushMode.AUTO);
            session.beginTransaction();
            session.saveOrUpdate(d);
            session.getTransaction().commit();
            session.close();            
        } catch (Exception e) {
            looger.warning("Erreur d'ajouter une marque car " + e.getMessage());
        }
    }

    @Override
    public List<Designation> allDesignation() {
        
        return this.getHibernateTemplate().loadAll(Designation.class);
    }

    @Override
    public Designation designationById(Integer id) {
        Designation d = new Designation();
        try {
            d = (Designation) this.getHibernateTemplate().find("FROM Designation d WHERE d.id = " + id).get(0);
        } catch (Exception e) {
            looger.warning("Erreur de récuperation de Designation par ID car "+e.getMessage());
        }
        return d;
    }

    @Override
    public List<Designation> allDesignationByFamille(String famille) {
        List<Designation> l = new ArrayList<Designation>();
        try {
            l = (List<Designation>) this.getHibernateTemplate().find("FROM Designation d WHERE d.familleEngin = '" + famille.toUpperCase()+"' ");
        } catch (Exception e) {
            looger.warning("Erreur de récuperation liste des Designations  par famille car "+e.getMessage());
        }
        if(l.size()<1){
            l = this.getHibernateTemplate().loadAll(Designation.class);
        }
        return l;
    }
    
}
