/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.dao.Mensuel;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import ma.bservices.tgcc.Entity.Affectation;
import ma.bservices.tgcc.Entity.Mensuel;
import ma.bservices.tgcc.Entity.SousAffectation;
import ma.bservices.tgcc.utilitaire.MbHibernateDaoSupport;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author zakaria.dem
 */
@Repository("affectDAO")
@Transactional
public class AffectationDAOImpl extends MbHibernateDaoSupport implements AffectationDAO, Serializable {

    @Override
    public void saveAffectation(List<Affectation> affect) {

        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);

        session.beginTransaction();
        for (Affectation f : affect) {
            session.save(f);
        }
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List<Date> getDatesOfAffectation(Mensuel _m) {

        List l = this.getHibernateTemplate().find("select distinct a.dateeffect "
                + "FROM Affectation a "
                + "where a.mensuel.id = " + _m.getId()
                + " and a.archived = " + Boolean.FALSE
                + "order by a.dateeffect desc ");
        return l;

    }

    @Override
    public List<Affectation> getAffectationOfMensuel(Mensuel _m, Date date) {

        if (date == null) {
            return null;
        }

        List l_aff = this.getHibernateTemplate().find("FROM Affectation a "
                + "where a.mensuel.id = " + _m.getId()
                + " and a.dateeffect = '" + date.toString() + "'"
                + " and a.archived = " + Boolean.FALSE);
        return l_aff;

    }

    /*
     * test Unitaire findAll des affectations
     */
    @Override
    public List<Affectation> Find_All() {

        List l = this.getHibernateTemplate().find("SELECT c FROM Affectation c "
                + "WHERE c.archived = " + Boolean.FALSE);
        if (l.size() > 0) {

            return l;
        }

        return null;
    }

    /*
     * test unitaire suprimer la liste des affectations
     */
    @Override
    public void deleteAffectation(List<Affectation> affect) {

        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);

        session.beginTransaction();
        for (Affectation f : affect) {
            f.setArchived(Boolean.TRUE);
            f.setDateArchivage(new Date());

            session.update(f);
        }
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List<Date> list_Affectaion_BetweenTo_Date(Date date_debut, Date date_fin, Mensuel m) {

        if (date_debut != null && date_fin != null) {

            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date date_to = date_debut;
            Date date_From = date_fin;
            String date_To_String = df.format(date_to);
            String date_From_String = df.format(date_From);

            List l_aff_m = this.getHibernateTemplate().find("SELECT  distinct b.dateeffect "
                    + " FROM Affectation b "
                    + "where b.mensuel.id= " + m.getId() + " "
                    + " and b.archived = " + Boolean.FALSE
                    + " and b.dateeffect  between  ' " + date_To_String + " ' and '" + date_From_String + "'");

            return l_aff_m;

        }

        return null;

    }

    @Override
    public List<Affectation> list_Affectaion_BetweenTo_Date_(Date date_debut, Date date_fin, Mensuel m) {

        if (date_debut != null && date_fin != null) {

            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date date_to = date_debut;
            Date date_From = date_fin;
            String date_To_String = df.format(date_to);
            String date_From_String = df.format(date_From);

            List l_aff_m = this.getHibernateTemplate().find("SELECT b "
                    + " FROM Affectation b "
                    + "where b.mensuel.id= " + m.getId() + ""
                    + " and b.archived = " + Boolean.FALSE
                    + " and b.dateeffect  between  ' " + date_To_String + " ' and '" + date_From_String + "'");

            return l_aff_m;
        }
        return null;
    }

    /**
     *
     * @param date_debut
     * @param date_fin
     * @param m
     * @return
     */
    @Override
    public List<Affectation> list_Affectaions_inWeek(Mensuel m, Date date_fin) {

        if (date_fin != null) {

            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String date_to_String = df.format(date_fin);

            List l_aff_m = this.getHibernateTemplate().find("SELECT a "
                    + " FROM Affectation a "
                    + "where a.mensuel.id= " + m.getId()
                    + " and a.dateeffect  <=  ' " + date_to_String + "' "
                    + " and a.archived = " + Boolean.FALSE
                    + " ORDER BY a.dateeffect ASC");

            return l_aff_m;
        }
        return null;
    }

    @Override
    public void save(SousAffectation sousAffectation) {

        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);

        session.beginTransaction();
        session.save(sousAffectation);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void deleteSA(SousAffectation sousAffectation) {

        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);

        session.beginTransaction();
        sousAffectation.setArchived(Boolean.TRUE);
        sousAffectation.setDateArchivage(new Date());
        session.update(sousAffectation);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void save(Affectation affectation) {

        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);

        session.beginTransaction();
        session.saveOrUpdate(affectation);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void save_Affecatation(Affectation affectation) {

        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);

        session.beginTransaction();
        session.save(affectation);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List<SousAffectation> findAll_SousAffectation() {

        return this.getHibernateTemplate().loadAll(SousAffectation.class);

    }

    /**
     * retourne la liste des sous-affectations d'une affectation par l'id des
     * affectation.
     *
     * @param id
     * @return
     */
    @Override
    public List<SousAffectation> list_affectation_byid_Affectation(int id) {

        List l_aff_m = this.getHibernateTemplate().find("SELECT  af  from SousAffectation af "
                + "where af.affectation.iDAffectation = " + id
                + " and af.archived = " + Boolean.FALSE);
        return l_aff_m;

    }

    @Override
    public List<Affectation> list_affectation_byid_Mensuel(int id) {

        List l_aff_m = this.getHibernateTemplate().find("SELECT  af  from Affectation af "
                + "where af.mensuel.id = " + id + ""
                + " and af.archived = " + Boolean.FALSE
                + " ORDER BY af.dateeffect DESC");
        return l_aff_m;

    }

    @Override
    public void delete_to_SousAffectation(SousAffectation sousAffectation) {
        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);

        session.beginTransaction();
        session.delete(sousAffectation);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void delete_to_affe(Affectation affectation) {
        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);

        session.beginTransaction();
        affectation.setArchived(Boolean.TRUE);
        affectation.setDateArchivage(new Date());
        session.update(affectation);
        session.getTransaction().commit();
        session.close();
    }

    /**
     * retourne la dernière affectation d'un mensuel.
     *
     * @param id
     * @return
     */
    @Override
    public Affectation get_lastAffectation_byMensuelId(int id) {

        Affectation _aff_m = null;
        List l_aff_m = null;

        l_aff_m = this.getHibernateTemplate().find("SELECT  af  from Affectation af "
                + "where af.mensuel.id = " + id
                + " and af.archived = " + Boolean.FALSE
                + " ORDER BY af.dateeffect DESC , af.iDAffectation DESC  ");

        if (!l_aff_m.isEmpty()) {

            _aff_m = (Affectation) l_aff_m.get(0);

            return _aff_m;

        }
        return null;
    }

    /**
     * Dans le cas d'une modification de la dernière affectation nous devons
     * donner à l'utilisateur la possibilité de choisir une date supérieur à la
     * dernière affectation et donc son avant dernière affectation
     *
     * @param id_mensuel
     * @return
     */
    @Override
    public Affectation get_lastAffectationOfCurrent_byMensuelId(int id_mensuel) {
        Affectation _aff_m = null;
        List l_aff_m = null;
        System.out.println("into get affectation methode");
        l_aff_m = this.getHibernateTemplate().find("SELECT  af  from Affectation af "
                + "where af.mensuel.id = " + id_mensuel
                + " and af.archived = " + Boolean.FALSE
                + " ORDER BY af.dateeffect DESC");
        if (!l_aff_m.isEmpty() && l_aff_m.size() >= 2) {
            return (Affectation) l_aff_m.get(1); //pour avoir la date de l'avant dernière affectation
        }

        return null;
    }

    @Override
    public Affectation get_lastAffectation_byid_Mensuel(int id) {

        Affectation _aff_m = null;
        List l_aff_m = null;

        l_aff_m = this.getHibernateTemplate().find("SELECT  af  from Affectation af "
                + "where af.mensuel.id = '" + id + "'"
                + " and af.archived = " + Boolean.FALSE);

        if (!l_aff_m.isEmpty()) {

            _aff_m = (Affectation) l_aff_m.get(0);

            return _aff_m;

        }
        return null;

    }

    /**
     * la liste des sous affectaion non archivé
     *
     * @param _affec
     * @return
     */
    @Override
    public List<SousAffectation> get_sousAffectation(Affectation _affec) {
        List l_sousAffectation = null;
        l_sousAffectation = this.getHibernateTemplate().find("SELECT saf from SousAffectation saf "
                + "WHERE saf.affectation.iDAffectation = " + _affec.getIDAffectation() + ""
                + " and saf.archived = " + Boolean.FALSE);

        return l_sousAffectation;
    }

}
