/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.dao.Mensuel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import ma.bservices.tgcc.Entity.MvmtCaisse;
import ma.bservices.tgcc.utilitaire.MbHibernateDaoSupport;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author yassine.jeddi
 */
@Repository("mvmtCaisseDAOImpl")
@Transactional
public class MvmtCaisseDAOImpl extends MbHibernateDaoSupport implements IMvmtCaisseDAO,Serializable{

    
    protected static Logger logger = Logger.getLogger(MvmtCaisseDAOImpl.class); 
    
    @Override
    public void addMvmtCaisse(MvmtCaisse m) {
          try { 
            m.setArchiver(Boolean.FALSE);
            this.getHibernateTemplate().save(m);
            getHibernateTemplate().flush(); 
        } catch (HibernateException e) {
            logger.error("Erreur d'insertion mouvement car "+e.getMessage());
        }
    }

    @Override
    public void editMvmtCaisse(MvmtCaisse m) {
         try {
            this.getHibernateTemplate().update(m);
            getHibernateTemplate().flush(); 
        } catch (Exception e) {
            logger.error("Erreur de supression de mouvement car "+e.getMessage());
        }
    }

    @Override
    public void remouvMvmtCaisse(MvmtCaisse m) {
        try {
            m.setArchiver(Boolean.TRUE);
            this.getHibernateTemplate().update(m);
            getHibernateTemplate().flush(); 
        } catch (DataAccessException e) {
            logger.error("Erreur de supression de mouvement car "+e.getMessage());
        }
    }

    @Override
    public MvmtCaisse mvmtCaisseByID(int id) {
        MvmtCaisse m = new MvmtCaisse();
        try {
            m=this.getHibernateTemplate().get(MvmtCaisse.class, id);
        } catch (DataAccessException e) {
            logger.error("Erreur de récupération de voiture par id car "+e.getMessage());
        }
        return m;
    }

    @Override
    public List<MvmtCaisse> allMvmtCaisse() {
        List<MvmtCaisse> l = new ArrayList<MvmtCaisse>();
        try {
            l = (List<MvmtCaisse>) this.getHibernateTemplate().find("From MvmtCaisse m "
                    + " where m.archiver = '" + Boolean.FALSE + "'");
        } catch (DataAccessException e) {
         logger.error("Erreur de récupération liste les Mvmt Caisse car "+e.getMessage());
        }
        return l;
    }

    @Override
    public List<MvmtCaisse> allMvmtCaisseByIdCaisse(int idCaisse) {
        List<MvmtCaisse> l = new ArrayList<MvmtCaisse>();
        try {
            l = (List<MvmtCaisse>) this.getHibernateTemplate().find("From MvmtCaisse m "
                    + " where m.archiver = '" + Boolean.FALSE + "'"
                    + " AND m.caisse.id="+idCaisse);
        } catch (DataAccessException e) {
         logger.error("Erreur de récupération liste les Mvmt Caisse car "+e.getMessage());
        }
        return l;
    }
    @Override
    public String soldeCaisse(int idCaisse) {
         Double mtn=0.0;
        try {
            List l =  this.getHibernateTemplate().find("SELECT SUM(m.mtn) From MvmtCaisse m "
                    + " where m.archiver = '" + Boolean.FALSE + "'"
                    + " AND m.caisse.id="+idCaisse);
            mtn =  (Double) l.get(0);
            System.err.println("=================> mtn : "+mtn);
        } catch (DataAccessException e) {
         logger.error("Erreur de récupération liste les Mvmt Caisse car "+e.getMessage());
        }
        return mtn.toString();
    }
    
}
