/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.dao;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import ma.bservices.beans.Chantier;
import ma.bservices.beans.PointageLot;
import ma.bservices.beans.Salarie;
import ma.bservices.beans.Zone;
import ma.bservices.mb.services.MbHibernateDaoSupport;
import org.hibernate.FlushMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Mahdi
 */
@Repository("pointageLotDao")
@Transactional
public class PointageLotDaoImpl extends MbHibernateDaoSupport implements PointageLotDao, Serializable {

    @Override
    public List<PointageLot> getAll() {
        return getHibernateTemplate().loadAll(PointageLot.class);
    }

    @Override
    public List<PointageLot> getByX(Date datepointage, Salarie salarie, Zone zone) {
        String recherche = "", joint = "";
        if (datepointage != null) {
            recherche += " and p.datepointage = " + datepointage;
        }
        if (salarie != null) {
            joint += " inner join p.salarie as s ";
            recherche += " and s.id= " + salarie.getId();
        }
        if (zone != null) {
            joint += " inner join p.zone as z ";
            recherche += " and z.id = " + zone.getIdZone();
        }

        recherche = recherche.replaceFirst("and", "where");
        return (List<PointageLot>) getHibernateTemplate().find("select p from PointageLot p " + joint + recherche);

    }

    @Override
    public boolean pointer(PointageLot p) {
        Transaction tx = null;
        Session session = getSessionFactory().openSession();
        try {
            session.setFlushMode(FlushMode.AUTO);
            tx = session.beginTransaction();
            session.save(session.merge(p));
            tx.commit();
            session.close();
            System.out.println("pointage effectuée");
        } catch (Exception exp) {
            tx.rollback();
            session.close();
            System.out.println("erreur Pointage " + exp.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean updatePointage(PointageLot pl) {
        Transaction tx = null;
        Session session = getSessionFactory().openSession();
        try {
            session.setFlushMode(FlushMode.AUTO);
            tx = session.beginTransaction();
            session.update(pl);
            tx.commit();
            session.close();
        } catch (Exception exp) {
            tx.rollback();
            session.close();
            return false;
        }
        return true;
    }

    @Override
    public PointageLot getLastPointage() {
        Session session = getSessionFactory().openSession();
        Query query = session.createQuery("from PointageLot p order by p.datePointage desc").setMaxResults(1);
        PointageLot result = (PointageLot) query.uniqueResult();
        System.out.println("last pointage " + result.toString());
        return result;
    }
    
    @Override
    public List<PointageLot> getListPointageParLotBySalarie(Date datepointage, Salarie s, Chantier c) {
        List<PointageLot> l = new ArrayList<PointageLot>();
        try {
        DateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
        String datePointageStr = formatDate.format(datepointage);
        
        l = (List<PointageLot>) this.getHibernateTemplate().find("SELECT p FROM PointageLot p"
                + " INNER JOIN p.zones z "
                + " WHERE p.salarie.id = " + s.getId()
                + " AND p.datePointage = '" + datePointageStr + "'"
                + " AND z.idChantier.id = " + c.getId()
                ) ;
        } catch (Exception e) {
            logger.error("Erreur de récuperation de liste pointage par salarier et date car : "+e.getMessage());
        }
        return l; 
    }

    @Override
    public PointageLot getLastPointage(Chantier c, Salarie chef) {
        Session session = getSessionFactory().openSession();
        Query query = session.createQuery("select p from PointageLot p left join p.zones z "
                + "where z.idChantier.id = :idChantier "
                + "and p.fiche.salarieSupp.id =:idChef "
                + "order by p.datePointage desc")
                .setParameter("idChantier", c.getId())
                .setParameter("idChef", chef.getId());
        List<PointageLot> result = (List<PointageLot>) query.list();
        System.out.println("last pointage " + result.size());
        if (!result.isEmpty()) {
            return result.get(0);
        }
        return null;
    }

}
