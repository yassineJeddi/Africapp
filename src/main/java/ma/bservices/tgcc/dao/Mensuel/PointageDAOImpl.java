/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.dao.Mensuel;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import ma.bservices.tgcc.Entity.Crenau;
import ma.bservices.tgcc.Entity.Mensuel;
import ma.bservices.tgcc.Entity.Pointage;
import ma.bservices.tgcc.utilitaire.MbHibernateDaoSupport;
import org.hibernate.FlushMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author zakaria.dem
 */
@Repository("pointageDAO")
@Transactional
public class PointageDAOImpl extends MbHibernateDaoSupport implements PointageDAO, Serializable {

    @Override
    public void save(Pointage _p) {

        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);

        session.beginTransaction();
        session.saveOrUpdate(_p);
        session.getTransaction().commit();

        session.close();

    }

    @Override
    /**
     * Retourne la liste des pointages entre deux date pour un mensuel
     */
    public List<Pointage> getPointageByMensuelBetweenTwoDates(Mensuel mensuel, Date start, Date end) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        List l = this.getHibernateTemplate().find("SELECT p FROM Pointage p "
                + "WHERE p.mensuel.id = " + mensuel.getId() + " "
                + "AND p.day >= '" + sdf.format(start) + "' "
                + "AND p.day <= '" + sdf.format(end) + "'");

        return l;
    }

    @Override
    public Pointage exist(Mensuel _m, Date date, Crenau _c) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List l = this.getHibernateTemplate().find("SELECT p FROM Pointage p "
                + "WHERE p.mensuel.id = " + _m.getId() + " "
                + "AND p.day = '" + sdf.format(date) + "' "
                + "AND p.crenau.id = " + _c.getId());

        if (l.size() > 0) {
            return (Pointage) l.get(0);
        }
        return null;
    }

    /**
     * retourne le dernier pointage definitif effectué par le mensuel
     *
     * @param mensuel_id
     * @return
     */
    @Override
    public Pointage get_Last_Pointage(int mensuel_id) {

        Session session = getSessionFactory().openSession();
        Query _q = session.createQuery("SELECT p FROM Pointage p "
                + "WHERE p.mensuel.id = " + mensuel_id + " "
                + "AND p.definitive = 1 "
                + "ORDER BY p.day DESC").setMaxResults(1);

        List<Pointage> l_result = _q.list();

        if (l_result != null && l_result.size() > 0) {
            return l_result.get(0);
        }
        return null;
    }

    @Override
    public void delete(Pointage _p) {
        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);

        session.beginTransaction();
        session.delete(_p);
        session.getTransaction().commit();

        session.close();
    }

    /**
     * retourne la liste des codes des chantiers
     *
     * @return
     */
    @Override
    public List<String> distinct_fiche_mensuels() {
        List l = null;
        l = this.getHibernateTemplate().find("select distinct p.codeFiche FROM Pointage p where p.codeFiche != null");
        if (l.size() > 0) {
            return l;
        }
        return null;
    }

    /**
     * retourne la liste des mensuels qui ont pointé un jour sur le chantier
     * donnée en paramètere Cette fonction est utilisé pour le service RH pour
     * facilité la recherche des fiches de pointage
     *
     * @param chantier_id
     * @return
     */
    public List<Mensuel> distinct_mensuels_by_chantier(int chantier_id) {
        List l = null;
        l = this.getHibernateTemplate().find("select distinct p.mensuel FROM Pointage p where p.chantier.id =" + chantier_id);
        if (l.size() > 0) {
            return l;
        }

        return null;
    }
    
    /**
     * 
     * selectionne les code des fiches de pointage d'un mensuel donnée en paramètre dans un chantier donnée aussi en paramètre
     * 
     * @param selected_chantier
     * @param selected_mensuel
     * @return List<String>
     */
    public List<String> get_fiches_Bymensuel_inChantier(Integer selected_chantier, int selected_mensuel) {
        List l = null;
        String str_req_chantier = "";


        l = this.getHibernateTemplate().find("select distinct p.codeFiche FROM Pointage p where p.mensuel.id = " + selected_mensuel + " and p.codeFiche != null");

        if (l.size() > 0) {
            return l;
        }
        return null;
    }

//    public void get_mensuee_whoes_have_pointed
}
