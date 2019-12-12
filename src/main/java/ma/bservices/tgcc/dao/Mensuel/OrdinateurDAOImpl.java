/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.dao.Mensuel;

import java.io.Serializable;
import java.util.List;
import ma.bservices.tgcc.Entity.Ordinateur;
import ma.bservices.tgcc.utilitaire.MbHibernateDaoSupport;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

/**
 *
 * @author j.allali
 */
@Repository("ordinateurDAO")
//@Transactional
public class OrdinateurDAOImpl extends MbHibernateDaoSupport implements OrdinateurDAO, Serializable {

    private final static String HQL_instance = "m";

    @Override
    public Boolean saveOrdinateur(Ordinateur ordi) {
        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);

        session.beginTransaction();

        ordi.setArchiver(Boolean.FALSE);

        session.save(ordi);

        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public List<Ordinateur> ConsultOrdinateur(int id) {
        List l = this.getHibernateTemplate().find("SELECT p FROM Ordinateur p "
                + "WHERE p.archiver = '" + Boolean.FALSE + "' and  p.mensuel.id = " + id);

        if (l.size() > 0) {

            return l;

        }

        return null;
    }

    /**
     * methode qui permet de recupere list des ordinateur Non affecter
     *
     * @param id
     * @return
     */
    @Override
    public List<Ordinateur> listOrdinateur_NonAffecter() {

        List l = null;

        l = this.getHibernateTemplate().find("select t From  Ordinateur t "
                + "where t.archiver = '" + Boolean.FALSE + "' and  t.mensuel.id is null");
        System.out.println("entree");
        if (l.size() > 0) {
            return l;
        }
        return null;
    }

    @Override
    public List<Ordinateur> findAll() {

        List l = this.getHibernateTemplate().find("select o From Ordinateur o where o.archiver = '" + Boolean.FALSE + "'");

        return l;

//        return this.getHibernateTemplate().loadAll(Ordinateur.class);
    }

    /**
     * test uniataire suprimer
     */
    @Override
    public void delete(Ordinateur ordinateur) {

        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);

        session.beginTransaction();

        ordinateur.setArchiver(Boolean.TRUE);

        session.update(ordinateur);
        session.getTransaction().commit();

        session.close();

    }

    @Override
    public Boolean update(Ordinateur ordinateur) {

        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);

        session.beginTransaction();
        ordinateur.setArchiver(Boolean.FALSE);

        session.update(ordinateur);
        session.getTransaction().commit();

        session.close();
        return true;
    }

    @Override
    public List<String> getList_ordinateur_distinct() {
        List l = this.getHibernateTemplate().find(" select distinct(marque) From Ordinateur ");

        return l;
    }

    /**
     * methode qui permet de recupere list des ordianteur Affecte
     *
     * @return
     */
    @Override
    public List<Ordinateur> listOrdinateur_Affecte() {
        List l = null;

        l = this.getHibernateTemplate().find("select t From  Ordinateur t "
                + "where t.archiver = '" + Boolean.FALSE + "' and  t.mensuel.id is not null");
        if (l.size() > 0) {
            return l;
        }
        return null;
    }

    /**
     * methode qui permet de desaffcter odrdinateur a un mensuel
     *
     * @param ordinateur
     */
    @Override
    public void desaffecter_ordianteur_mensuel(Ordinateur ordinateur) {

        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);

        session.beginTransaction();
        ordinateur.setMensuel(null);

        ordinateur.setArchiver(Boolean.FALSE);

        session.update(ordinateur);
        session.getTransaction().commit();

        session.close();
    }

    @Override
    public List<Ordinateur> findByMArque(String marque, String numSerie) {
        List l = null;

        String hql = "";

        if (!marque.equals("")) {

            hql = " and t.marque = '" + marque + "'";

        }

        if (!numSerie.equals("")) {
            hql = hql + " and  t.NumeroSerieOrd = '" + numSerie + "'";
        }

        l = this.getHibernateTemplate().find("select t From  Ordinateur t where "
                + " t.archiver = '" + Boolean.FALSE + "' and t.mensuel is null " + hql);
        if (l.size() > 0) {
            return l;
        }
        return null;
    }

    @Override
    public List<Ordinateur> getListeOrdinateurDifferentId(int id) {

        List l = null;
        l = this.getHibernateTemplate().find("From Ordinateur o where o.archiver = '" + Boolean.FALSE + "' and o.id != " + id);

        return l;
    }

}
