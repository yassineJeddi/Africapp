/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.dao.Mensuel;

import java.io.Serializable;
import java.util.List;
import ma.bservices.tgcc.Entity.Modem3G;
import ma.bservices.tgcc.utilitaire.MbHibernateDaoSupport;
import org.hibernate.FlushMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author a.wattah
 */
@Repository("modem3gDAO")
@Transactional
public class Modem3GDAOImpl extends MbHibernateDaoSupport implements Modem3GDAO, Serializable {

    private final static String HQL_instance = "m";

    @Override
    public List<Modem3G> findAll() {

        List l = this.getHibernateTemplate().find("From Modem3G m where m.archiver = '" + Boolean.FALSE + "'");

        return l;
    }

    @Override
    public Boolean save(Modem3G modem3G) {

        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);

        session.beginTransaction();

        modem3G.setArchiver(Boolean.FALSE);

        session.save(modem3G);

        session.getTransaction().commit();

        session.close();
        return true;
    }

    @Override
    public List<Modem3G> Consult3g(int id) {

        List l = null;

        l = this.getHibernateTemplate().find("select t From  Modem3G t where t.archiver = '"
                + Boolean.FALSE + "' and  t.mensuel.id = " + id);
        if (l.size() > 0) {
            return l;
        }
        return null;
    }

    /**
     * cette qui permet de recupere liste des 3g Non Affecter
     *
     * @return
     */
    @Override
    public List<Modem3G> list3G_Non_Affecter() {

        List l = null;

        l = this.getHibernateTemplate().find("select t From  Modem3G t where "
                + " t.archiver = '" + Boolean.FALSE + "' and  t.mensuel.id is null ");
        if (l.size() > 0) {
            return l;
        }
        return null;
    }

    @Override
    public List<Modem3G> listModem3GById(String id) {

        List l = null;

        l = this.getHibernateTemplate().find("select t From  Modem3G t where "
                + "t.archiver = '" + Boolean.FALSE + "' and t.id = " + "'id'");
        if (l.size() > 0) {
            return l;
        }
        return null;
    }

    @Override
    public Boolean update(Modem3G modem3G) {

        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);

        session.beginTransaction();
        session.update(session.merge(modem3G));

        session.getTransaction().commit();

        session.close();
        return true;
    }

    @Override
    public Boolean delete(Modem3G modem3G) {

        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);

        session.beginTransaction();
        modem3G.setArchiver(Boolean.TRUE);

        session.update(modem3G);

        session.getTransaction().commit();

        session.close();

        return true;
    }

    /**
     * methode qui permet de desaffacter 3g un salarie
     *
     * @param modem3G
     */
    @Override
    public void desaffecter_3g_salarie(Modem3G modem3G) {

        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);

        session.beginTransaction();
        modem3G.setMensuel(null);
        session.update(modem3G);

        session.getTransaction().commit();

        session.close();
    }

    /**
     * cette qui permet de recupere liste des 3G affecter
     *
     * @return
     */
    @Override
    public List<Modem3G> list3G_Affeceter() {

        List l = null;

        l = this.getHibernateTemplate().find("select t From  Modem3G t where t.mensuel.id is not null"
                + " and t.archiver = '" + Boolean.FALSE + "'");
        if (l.size() > 0) {
            return l;
        }
        return null;
    }

    @Override
    public List<Modem3G> getListe_3gByImeiandNumero(String imei, String numero, String operateur, String numTel) {

        List l = null;

        Session session = getSessionFactory().openSession();

        session.setFlushMode(FlushMode.AUTO);

        session.beginTransaction();

        Query query;

        String hql = "";

        if (!imei.equals("")) {

            hql = " and m.imei = '" + imei + "'";

        }

        if (!numero.equals("")) {
            hql = hql + " and  m.serie_numero = '" + numero + "'";
        }

        if (!operateur.equals("")) {
            hql = hql + " and m.operateur = '" + operateur + "'";
        }

        if (!numTel.equals("")) {
            hql = hql + " and m.numero = '" + numTel + "'";
        }

        query = session.createQuery("select m From Modem3G m where m.mensuel is null "
                + "and m.archiver =  '" + Boolean.FALSE + "'"
                + hql);

        l = query.list();
        session.getTransaction().commit();
        session.close();

        return l;

    }

    @Override
    public List<Modem3G> getListe3gDifferentId(int id) {

        List l = null;

        l = this.getHibernateTemplate().find("From Modem3G m where m.archiver = '" + Boolean.FALSE + "' and m.id != " + id);
        return l;

    }

}
