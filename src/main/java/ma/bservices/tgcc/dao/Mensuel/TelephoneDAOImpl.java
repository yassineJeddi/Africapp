/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.dao.Mensuel;

import java.io.Serializable;
import java.util.List;
import ma.bservices.tgcc.Entity.MarqueTelephone;
import ma.bservices.tgcc.Entity.Mensuel;
import ma.bservices.tgcc.Entity.Telephone;
import ma.bservices.tgcc.utilitaire.MbHibernateDaoSupport;
import ma.bservices.tgcc.utilitaire.SearchU;
import org.hibernate.FlushMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author a.wattah
 */
@Repository("telephoneDAO")
@Transactional
public class TelephoneDAOImpl extends MbHibernateDaoSupport implements TelephoneDAO, Serializable {

    private final static String HQL_instance = "m";

    @Override
    public Boolean save(Telephone telephone) {

        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);

        session.beginTransaction();

        telephone.setArchiver(Boolean.FALSE);

        session.save(telephone);

        session.getTransaction().commit();

        session.close();

        return true;
    }

    @Override
    public List<Telephone> findAll() {

        List l = this.getHibernateTemplate().find("From Telephone t where t.archiver = '" + Boolean.FALSE + "'");

        return l;
//        return this.getHibernateTemplate().loadAll(Telephone.class);
    }

    @Override
    public List<Mensuel> rechercherMensuel(String matricule, String nom, String prenom, String cin) {
        String filtre = createSearchFiltre(matricule, nom, prenom, cin);

        System.out.println("les filtre : " + filtre);
        List l = null;

        l = this.getHibernateTemplate().find("From Mensuel " + HQL_instance + " " + filtre);
        System.out.println("la liste des mensuels @@@@@@@@@@@@@@@@@@@@@@@@@@" + l.size());
        return l;
    }

    /**
     *
     * retourne les filre SQL pour la requte de la recherche des Pointage EX :
     * WHERE p.code = 123 and p.designation = DES
     *
     * @param code
     * @param Designation
     * @return String
     */
    static String createSearchFiltre(String matricule, String nom, String prenom, String cin) {

        String filtre = "";
        Boolean filtre_bool_where = false;
        String filtre_one;
        //creation du filre pour le code

        filtre_one = SearchU.createOneFilre("matricule", matricule, filtre_bool_where, HQL_instance);
        if (filtre_one != null) {
            filtre += filtre_one;
            filtre_bool_where = true;
        }

        filtre_one = SearchU.createOneFilre("nom", nom, filtre_bool_where, HQL_instance);
        if (filtre_one != null) {
            filtre += filtre_one;
            filtre_bool_where = true;
        }
        filtre_one = SearchU.createOneFilre("prenom", prenom, filtre_bool_where, HQL_instance);
        if (filtre_one != null) {
            filtre += filtre_one;
            filtre_bool_where = true;
        }

        filtre_one = SearchU.createOneFilre("cin", cin, filtre_bool_where, HQL_instance);
        if (filtre_one != null) {
            filtre += filtre_one;
            filtre_bool_where = true;
        }
        return filtre;

    }

    @Override
    public List<Mensuel> findAllM() {
        return this.getHibernateTemplate().loadAll(Mensuel.class);
    }

    @Override
    public List<Telephone> ConsultOrdinateur(int id) {

        List l = null;

        l = this.getHibernateTemplate().find("select t From  Telephone t "
                + " where t.archiver = '" + Boolean.FALSE + "' and  t.mensuel.id = " + id);
        if (l.size() > 0) {
            return l;
        }
        return null;
    }

    static String createSearchFiltre1(String matricule, String nom, String prenom, String cin) {

        String filtre = "";
        Boolean filtre_bool_where = false;
        String filtre_one;
        //creation du filre pour le code

        filtre_one = SearchU.createOneFilre("mensuel.matricule", matricule, filtre_bool_where, HQL_instance);
        if (filtre_one != null) {
            filtre += filtre_one;
            filtre_bool_where = true;
        }

        filtre_one = SearchU.createOneFilre("mensuel.nom", nom, filtre_bool_where, HQL_instance);
        if (filtre_one != null) {
            filtre += filtre_one;
            filtre_bool_where = true;
        }
        filtre_one = SearchU.createOneFilre("mensuel.prenom", prenom, filtre_bool_where, HQL_instance);
        if (filtre_one != null) {
            filtre += filtre_one;
            filtre_bool_where = true;
        }

        filtre_one = SearchU.createOneFilre("mensuel.cin", "" + cin, filtre_bool_where, HQL_instance);
        if (filtre_one != null) {
            filtre += filtre_one;
            filtre_bool_where = true;
        }

        return filtre;

    }

    @Override
    public List<MarqueTelephone> getMarqueByLib(String lib) {
        List<MarqueTelephone> marques = null;

        Session s = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
        try {
            //demarrage de la session
            s.getTransaction().begin();
            String hql = "select m from MarqueTelephone m ";

            Query query = s.createQuery(hql);

            marques = query.list();
            s.getTransaction().commit();

        } catch (Exception e) {

            s.getTransaction().rollback();
        }

        return marques;
    }

    @Override
    public Boolean save(MarqueTelephone marqueTelephone) {
        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);

        session.beginTransaction();

        session.save(marqueTelephone);

        session.getTransaction().commit();

        session.close();
        return true;
    }

    /**
     * test unitaire suprimer
     *
     * @param telephone
     * @return
     */
    @Override
    public Boolean delete(Telephone telephone) {

        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);

        session.beginTransaction();

        telephone.setArchiver(Boolean.TRUE);

        session.update(telephone);

        session.getTransaction().commit();

        session.close();

        return true;
    }

    /**
     * cette qui permet de recupere list des telephone Non Affecter
     *
     * @return
     */
    @Override
    public List<Telephone> listTelephone_Non_Affecter() {

        List l = null;

        l = this.getHibernateTemplate().find("select t From  Telephone t where "
                + " t.archiver = '" + Boolean.FALSE + "' and  t.mensuel is null ");
        if (l.size() > 0) {
            return l;
        }
        return null;
    }

    @Override
    public Boolean update(Telephone telephone) {
        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);

        session.beginTransaction();
        telephone.setArchiver(Boolean.FALSE);

        session.update(session.merge(telephone));

        session.getTransaction().commit();

        session.close();

        return true;
    }

    /**
     * cette qui permet de recupere list des telephones affecter
     *
     * @return
     */
    @Override
    public List<Telephone> listTelephophone_Affecter() {
        List l = null;

        l = this.getHibernateTemplate().find("select t From  Telephone t where "
                + " t.archiver = '" + Boolean.FALSE + "' and t.mensuel.id is not null");
        if (l.size() > 0) {
            return l;
        }
        return null;
    }

    /**
     * methode qui permet de desaffecter telephone a un mensuel
     *
     * @param telephone
     */
    @Override
    public void desaffecter_telephone_mensuel(Telephone telephone) {

        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);

        session.beginTransaction();

        telephone.setMensuel(null);

        telephone.setArchiver(Boolean.FALSE);

        session.update(telephone);

        session.getTransaction().commit();

        session.close();

    }

    @Override
    public List<Telephone> getList_telephone_NonAffecter(String num, String marque, String model) {

        List l = null;

        String hql = "";

        if (!num.equals("")) {
            hql = " and t.numSerieTel = '" + num + "'";
        }

        if (!marque.equals("")) {
            hql = hql + "and t.marque =  '" + marque + "'";
        }

        if (!model.equals("")) {
            hql = hql + "and t.modele = '" + model + "'";
        }

        l = this.getHibernateTemplate().find("select t From Telephone t where  "
                + "t.archiver = '" + Boolean.FALSE + "' and t.mensuel is null " + hql);

        return l;
    }

    @Override
    public List<Telephone> getListTelephoneDifferentId(int id) {

        List l = null;

        l = this.getHibernateTemplate().find("From Telephone t where t.archiver = '" + Boolean.FALSE + "' and t.id != " + id);

        return l;
    }

}
