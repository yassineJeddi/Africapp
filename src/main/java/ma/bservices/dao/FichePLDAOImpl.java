/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.dao;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import ma.bservices.beans.FichePointageLot;
import ma.bservices.mb.services.MbHibernateDaoSupport;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author j.allali
 */
@Repository("fichePLDAO")
@Transactional
public class FichePLDAOImpl extends MbHibernateDaoSupport implements FichePLDAO, Serializable {

    @Override
    public FichePointageLot saveFiche(FichePointageLot fiche) {
        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);

        session.beginTransaction();
        session.save(session.merge(fiche));

        session.getTransaction().commit();
        System.out.println("save Fiche PL __________");
        session.close();

        return fiche;
    }

    @Override
    public List<FichePointageLot> findAll() {

        List l = null;
        l = this.getHibernateTemplate().find("select c FROM FichePointageLot c order by c._date desc");
        if (l.size() > 0) {
            return l;
        }
        return null;
    }
    
    /**
     * Retourne les fichePointageLot pointée dans la date x dans le chantier c avec le chef c
     * @param idChantier
     * @param idChef
     * @param datePointage
     * @return 
     */
    @Override
    public List<FichePointageLot> findFicheByChefDateChantier(Integer idChantier, Integer idChef, Date datePointage) {
        List l = null;
        
        String date_String = "";
        if (datePointage != null) {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            date_String = df.format(datePointage);
        }
        
        
        l = this.getHibernateTemplate().find("SELECT f FROM FichePointageLot f "
                + "WHERE f.chantier.id = " + idChantier + " "
                + "AND f.salarieSupp.id = " + idChef + " "
                + "AND f._date = '" + date_String + "' "
                + "order by f._date desc");
        
        if (l.size() > 0) {
            return l;
        }
        return null;
    }

    @Override
    public FichePointageLot getById(Long id) {
        return this.getHibernateTemplate().get(FichePointageLot.class, id);
    }

    @Override
    public boolean updateFiche(FichePointageLot fiche) {

        Transaction tx = null;
        Session session = getSessionFactory().openSession();
        try {
            session.setFlushMode(FlushMode.AUTO);
            tx = session.beginTransaction();
            session.update(session.merge(fiche));
            tx.commit();
            session.close();
        } catch (Exception exp) {
            System.out.println("exception : " + exp.getMessage());
            tx.rollback();
            session.close();
            return false;
        }
        return true;
    }

}
