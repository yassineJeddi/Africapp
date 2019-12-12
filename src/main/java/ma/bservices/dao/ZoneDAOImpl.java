/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.dao;

import java.io.Serializable;
import java.util.List;
import ma.bservices.beans.Chantier;
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
 * @author j.allali
 */
@Repository("zoneDAOEvol")
@Transactional
public class ZoneDAOImpl extends MbHibernateDaoSupport implements ZoneDAO, Serializable {

    @Override
    public List<Zone> findAll() {
        return this.getHibernateTemplate().loadAll(Zone.class);
    }

    @Override
    public Zone findById(int id) {
        List l = this.getHibernateTemplate().find("SELECT z FROM Zone z WHERE z.idZone = " + id);
        if (l.size() > 0) {
            return (Zone) l.get(0);
        }

        return null;
    }

    @Override
    public List<Zone> findByChantierID(int chantier_id) {
        List l = null;
        l = this.getHibernateTemplate().find("SELECT z FROM Zone z WHERE z.idChantier = " + chantier_id);
        return l;
    }

    @Override
    public Boolean addZone(Zone zone) {

        Transaction tx = null;
        Session session = getSessionFactory().openSession();
        try {

            session.setFlushMode(FlushMode.AUTO);
            tx = session.beginTransaction();

            session.save(zone);

            tx.commit();
            session.close();
        } catch (Exception exp) {
            tx.rollback();
            session.close();
        }
        return true;
    }

    @Override
    public Boolean deleteZone(int id) {

        Session sess = getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = sess.beginTransaction();
            Query query = sess.createQuery("delete from Zone where id=:id");
            query.setParameter("id", id);
            int deleted = query.executeUpdate();
            System.out.println("Deleted: " + deleted + " user(s)");
            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e; // or display error message
        } finally {
            sess.close();
        }
        return true;
    }

//        System.out.println("rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr");
//        Session s = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
//        System.out.println("dddddddddddd :" + id);
//
//        Query query = s.createQuery("delete from Zone where id=:id");
//        query.setParameter("id", id);
//        int deleted = query.executeUpdate();
//        System.out.println("Deleted: " + deleted + " user(s)");
//        System.out.println("zone  ------------" + id);
//        return true;
    @Override
    public Boolean uppdate(Zone zone) {

//        Session session = getSessionFactory().getCurrentSession();
        Session session = getSessionFactory().openSession();

        session.setFlushMode(FlushMode.AUTO);

        Transaction tx = session.beginTransaction();
        session.update(zone);
        tx.commit();

        System.out.println("Object Updated successfully.....!!");
        session.close();

        return true;

    }

    /**
     * methode test unitaire delete zone
     *
     * @param zd
     */
    @Override
    public void delete_Test_Unitaire(Zone zd) {
        Session session = getSessionFactory().openSession();

        session.setFlushMode(FlushMode.AUTO);

        Transaction tx = session.beginTransaction();
        session.delete(zd);
        tx.commit();

        System.out.println("Object Updated successfully.....!!");
        session.close();

    }

    @Override
    public List<Zone> findBySalarie(int salarie) {
        List<Zone> zone = null;

        Session s = this.getHibernateTemplate().getSessionFactory().openSession();
        try {
            //demarrage de la session
            s.getTransaction().begin();
            String hql = "select m from Zone m , in(m.salaries) l where l.id =" + salarie;
//            select p from ModPm p join p.modScopeTypes type where type.scopeTypeId = 1
//            String hql="select m from Mensuel m inner join m.loyerSalaries l where l.id ="+id;

            Query query = s.createQuery(hql);

            zone = query.list();

            s.getTransaction().commit();
            s.close();

        } catch (Exception e) {

            s.getTransaction().rollback();
            s.close();
        }

        return zone;
    }

    @Override
    public Boolean affectZoneToSalarie(Zone zone, Salarie salarie) {
        System.out.println(" **** Affect Zone 1 **** ");
        Transaction tx;
        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.COMMIT);
        tx = session.beginTransaction();
        System.out.println(" @@@@@@@@@@@@@@@@@ /////////// " + zone.getLibeleZone());

        session.saveOrUpdate(session.merge(salarie));
        System.out.println(" **** Affect Zone Fin **** ");
        tx.commit();
        session.close();
        return true;
    }

    @Override
    public void zonesOf(Salarie s) {
        Session session = getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        List<Zone> list = (List<Zone>) session.createQuery("select z from Salarie s join s.zones z where Dtype='Salarie' and s.id=:id").setInteger("id", s.getId()).list();
        System.out.println("list zones " + list.size());
        s.setZones(list);
        tx.commit();
        session.close();
    }

    @Override
    public List<Zone> findBySupAndChantier(Salarie sup, Chantier c) {
        Session session = getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        List<Zone> list = (List<Zone>) session.createQuery("select z from Salarie s left join s.zones z "
                + "where Dtype='Salarie' "
                + "and s.id=:idSalarie "
                + "and z.idChantier.id=:idChantier")
                .setInteger("idSalarie", sup.getId())
                .setInteger("idChantier", c.getId()).list();
        System.out.println("list zones " + list.size());
        tx.commit();
        session.close();
        return list;
    }
}
