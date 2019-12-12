/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.dao.Mensuel;

import java.io.Serializable;
import java.util.List;
import ma.bservices.tgcc.Entity.BonCaisse;
import ma.bservices.tgcc.Entity.LoyerSalarie;
import ma.bservices.tgcc.utilitaire.MbHibernateDaoSupport;
import org.hibernate.FlushMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author j.allali
 */
@Repository("boncaisseDAO")
@Transactional
public class BonCaisseDAOImpl extends MbHibernateDaoSupport implements BonCaisseDAO, Serializable {

    @Override
    public void saveBonCaisse(BonCaisse BC) {

        Session session = getSessionFactory().openSession();

        session.setFlushMode(FlushMode.AUTO);

        session.beginTransaction();
        session.save(BC);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void updateBonCaisse(BonCaisse BC) {

        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.COMMIT);

        session.beginTransaction();
        session.update(session.merge(BC));
        session.getTransaction().commit();
        session.close();

    }

    @Override
    public BonCaisse getbcById(Long id) {
        BonCaisse bc = null;
        Session s = this.getHibernateTemplate().getSessionFactory().openSession();
        try {
            //demarrage de la session
            s.getTransaction().begin();
            Query l = s.createQuery("SELECT c FROM BonCaisse c WHERE c.loyerSalarie.id = " + id);
            if (l.list().size() > 0) {

                bc = (BonCaisse) l.list().get(0);
//            s.getTransaction().commit();
                s.close();
            }
        } catch (Exception e) {

            s.getTransaction().rollback();
        }
        return bc;
    }

    @Override
    public LoyerSalarie AfficherListMensuelByidLoyer(Long id) {
        LoyerSalarie ls = null;

        Session s = this.getHibernateTemplate().getSessionFactory().openSession();
        try {
            //demarrage de la session
            s.getTransaction().begin();
            String hql = "select m from LoyerSalarie m , in(m.mensuels) l where l.id =" + id;

            Query query = s.createQuery(hql);

            ls = (LoyerSalarie) (query.list()).get(0);

            s.getTransaction().commit();
            s.close();

        } catch (Exception e) {

            s.getTransaction().rollback();
        }

        return ls;
    }

    @Override
    public List<BonCaisse> findAll() {
        return this.getHibernateTemplate().loadAll(BonCaisse.class);
    }

    @Override
    public List<BonCaisse> getbcByIdLoyerSalarie(Long id) {
        List l = this.getHibernateTemplate().find("SELECT c FROM BonCaisse c WHERE c.loyerSalarie.id = " + id);
        if (l.size() > 0) {

            return l;
        }

        return null;
    }

    @Override
    public void removeBCChantierByLC(BonCaisse BC) {
        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);

        session.beginTransaction();
        BC.setLoyerChantier(null);
        BC.setLoyerSalarie(null);
        
        session.delete(BC);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public BonCaisse getbcByIdLoyerChantier(Long id) {
        List l = this.getHibernateTemplate().find("SELECT c FROM BonCaisse c WHERE c.LoyerChantier.id = " + id);
        if (l.size() > 0) {
            return (BonCaisse) l.get(0);
        }

        return null;
    }

    @Override
    public List<BonCaisse> getbcByIdLoyerChan(Long id) {
        List l = this.getHibernateTemplate().find("SELECT c FROM BonCaisse c WHERE c.LoyerChantier.id = " + id);
        if (l.size() > 0) {

            return l;
        }

        return null;
    }

    /*
     * test Unitaire 
     */
    @Override
    public BonCaisse getbcByIdBonCaisse(Long id) {
        List l = this.getHibernateTemplate().find("SELECT c FROM BonCaisse c WHERE c.id = " + id);
        if (l.size() > 0) {

            return (BonCaisse) l.get(0);
        }

        return null;
    }
}
