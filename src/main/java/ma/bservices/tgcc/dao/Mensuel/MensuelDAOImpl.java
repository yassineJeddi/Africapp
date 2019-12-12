/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.dao.Mensuel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import ma.bservices.tgcc.Entity.Affectation;
import ma.bservices.tgcc.Entity.Mensuel;
import ma.bservices.tgcc.utilitaire.MbHibernateDaoSupport;
import ma.bservices.tgcc.utilitaire.SearchU;
import org.hibernate.FlushMode;
import java.util.Date;
import ma.bservice.tgcc.Constante.Constante;
import ma.bservices.beans.Chantier;
import ma.bservices.beans.Salarie;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author j.allali
 */
@Repository("mensuelDAO")
@Transactional
public class MensuelDAOImpl extends MbHibernateDaoSupport implements MensuelDAO, Serializable {

    private final static String HQL_instance = "m";

    @Override
    public List<Mensuel> findAll() {
        List<Mensuel> l = new ArrayList<Mensuel>();
        try {
           //l=(List<Mensuel>) this.getHibernateTemplate().find("from Mensuel where statut =1 order by cast(matricule as int)");
           l=(List<Mensuel>) this.getHibernateTemplate().find("from Mensuel where statut =1 ");
        } catch (Exception e) {
            System.out.println(" EREUR de chargement la liste des salarier :"+e.getMessage());
        }
        return l;
    }

    @Override
    public List<Mensuel> inActiffindAll() {
        return (List<Mensuel>) this.getHibernateTemplate().find("from Mensuel where statut = 0");
    }

    @Override
    public List<Mensuel> rechercherMensuel(String matricule, String nom, String prenom, String fonction, String cin) {
        String filtre = createSearchFiltre(matricule, nom, prenom, fonction, cin);

        System.out.println("les filtre : " + filtre);
        List l = null;

        l = this.getHibernateTemplate().find("From Mensuel " + HQL_instance + " " + filtre);
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
    static String createSearchFiltreByStatus(String matricule, String nom, String prenom, String fonction, String status) {

        String filtre = "";
        Boolean filtre_bool_where = false;
        String filtre_one;
        //creation du filre pour le code

        String fon = "";

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

        if (fonction != null) {
            fon = fonction.replace("'", "''");
        }

        filtre_one = SearchU.createOneFilre("fonction.fonction", "" + fon, filtre_bool_where, HQL_instance);
        if (filtre_one != null) {
            filtre += filtre_one;
            filtre_bool_where = true;
        }

        filtre_one = SearchU.createOneFilre("statut", status.equals("inactif") ? "0" : "1", filtre_bool_where, HQL_instance);
        if (filtre_one != null) {
            filtre += filtre_one;
            filtre_bool_where = true;
        }

        return filtre;

    }

    @Override
    public List<Chantier> getChantierAffect(int id) {
        List l = this.getHibernateTemplate().find("SELECT c FROM Chantier c WHERE c.id != '" + id + "'");
        if (l.size() > 0) {

            return l;
        }

        return null;
    }

    @Override
    public List<Mensuel> getMensuelDifferentId(int id) {
        List l = this.getHibernateTemplate().find("SELECT c FROM Mensuel c WHERE c.id !='" + id + "'");
        if (l.size() > 0) {

            return l;
        }

        return null;
    }

    @Override
    public List<Affectation> findAllAffectation() {
        return this.getHibernateTemplate().loadAll(Affectation.class);
    }

    @Override
    public Chantier ChantierByID(int id) {
        List l = this.getHibernateTemplate().find("SELECT c FROM Chantier c WHERE c.id = '" + id + "'");
        if (l.size() > 0) {

            return (Chantier) l.get(0);
        }

        return null;
    }

    @Override
    public Mensuel findById(String ID) {

        List l = this.getHibernateTemplate().find("SELECT m FROM Mensuel m WHERE m.id= '" + ID + "'");

        if (l.size() > 0) {
            Hibernate.initialize(((Mensuel) l.get(0)).getDocuments());
            return (Mensuel) l.get(0);
        }

        return null;
    }

    @Override
    public Mensuel findByMatricule(String matricule) {

        List l = this.getHibernateTemplate().find("SELECT m FROM Mensuel m WHERE m.matricule= '" + matricule + "'");
        System.out.println("============= DAO RESPONSE FOR MATRICULE : " + matricule);
        if (l.size() > 0) {
            //Hibernate.initialize(((Mensuel) l.get(0)).getDocuments());
            return (Mensuel) l.get(0);
        } else {
            return null;
        }
    }

    @Override
    public Mensuel findById_integer(int ID) {
        List l = this.getHibernateTemplate().find("SELECT m FROM Mensuel m WHERE m.id= " + ID);
        if (l.size() > 0) {

            return (Mensuel) l.get(0);
        }

        return null;
    }

    @Override
    public void update(Mensuel m) {
        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);

        session.beginTransaction();
        session.update(m);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public Chantier getIdChantierByLib(String lib) {
        List l = this.getHibernateTemplate().find("FROM Chantier c WHERE c.code = '" + lib + "'");

//        List l = this.getHibernateTemplate().findByNamedQuery("Chantier.findByPrjapId", new Object[] {id});
        if (l.size() > 0) {

            return (Chantier) l.get(0);
        }

        return null;
    }

    @Override
    public List<Mensuel> rechercherMensuelByfonction(String matricule, String nom, String prenom, String fonction, String status) {
        String filtre = createSearchFiltreByStatus(matricule, nom, prenom, fonction, status);

        System.out.println("les filtre : " + filtre);
        List l = null;

        l = this.getHibernateTemplate().find("From Mensuel " + HQL_instance + " " + filtre);

        return l;
    }

    @Override
    public List<Mensuel> rechercherMensuelByDateCreation(String matricule, String nom, String prenom, String date_creation) {
        String filtre = createSearchFiltreByDateCreation(matricule, nom, prenom, date_creation);

        System.out.println("les filtre : " + filtre);
        List l = null;

        l = this.getHibernateTemplate().find("From Mensuel " + HQL_instance + " " + filtre);

        return l;
    }

    static String createSearchFiltreByDateCreation(String matricule, String nom, String prenom, String date_creation) {

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

        filtre_one = SearchU.createOneFilre("dateCreation", "" + Constante.getDateFrFromString(date_creation), filtre_bool_where, HQL_instance);
        if (filtre_one != null) {
            filtre += filtre_one;
            filtre_bool_where = true;
        }

        return filtre;

    }

    static String createSearchFiltre(String matricule, String nom, String prenom, String fonction, String cin) {

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

        filtre_one = SearchU.createOneFilre("fonction.fonction", "" + fonction, filtre_bool_where, HQL_instance);
        if (filtre_one != null) {
            filtre += filtre_one;
            filtre_bool_where = true;
        }
        filtre_one = SearchU.createOneFilre("cin", "" + cin, filtre_bool_where, HQL_instance);
        if (filtre_one != null) {
            filtre += filtre_one;
            filtre_bool_where = true;
        }
        return filtre;

    }

    @Override
    public List<Mensuel> listMensuel(int id) {
        List l = null;
        l = this.getHibernateTemplate().find("select c FROM Mensuel c WHERE c.id = " + id);
        if (l.size() > 0) {
            return l;

        }
        return null;
    }

    @Override
    public void saveAffectation(List<Affectation> affect) {
        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);

        session.beginTransaction();
        for (Affectation f : affect) {

            f.setDateeffect(new Date());
            session.save(f);
        }
        session.getTransaction().commit();
        session.close();
    }

    /*
     * test Unitaire ajouter et suprimer un mensuel
     */
    @Override
    public void saveMensuel(Mensuel m) {
        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);
        session.beginTransaction();
        session.save(m);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void updateMensuel(Mensuel m) {
        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);
        session.beginTransaction();
        session.update(session.merge(m));
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void deleteMensuel(Mensuel m) {
        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);
        session.beginTransaction();
        session.delete(m);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List<String> distinct_mensuel_name() {
        List l = null;
        l = this.getHibernateTemplate().find("select distinct c.nom FROM Mensuel c");
        if (l.size() > 0) {
            return l;
        }
        return null;
    }

    @Override
    public List<String> distinct_mensuel_firstName() {
        List l = null;
        l = this.getHibernateTemplate().find("select distinct c.prenom FROM Mensuel c");
        if (l.size() > 0) {
            return l;
        }
        return null;
    }

    @Override
    public List<String> distinct_mensuel_matricule() {
        List l = null;
        l = this.getHibernateTemplate().find("select distinct c.matricule FROM Mensuel c");
        if (l.size() > 0) {
            return l;
        }
        return null;
    }

    @Override
    public List<String> distinct_mensuel_cin() {
        List l = null;
        l = this.getHibernateTemplate().find("select distinct c.cin FROM Mensuel c");
        if (l.size() > 0) {
            return l;
        }
        return null;
    }

    @Override
    public List<Mensuel> getListMensuelBy(String matricule, String nom, String prenom, String cin) {

        List l = null;

        l = this.getHibernateTemplate().find("From Mensuel m where m.matricule = '" + matricule
                + "' or m.nom = '" + nom + "' or m.prenom  = '" + prenom
                + "' or m.cin = '" + cin + "'");

        return l;

    }

    @Override
    public List<Salarie> findAllActif() {
          List l = null;
        l = this.getHibernateTemplate().find("select s FROM Mensuel s WHERE s.statut = 1 ");
        if (l.size() > 0) {
            return l;
        }
        return null;
    }
    
    /**
     * retourne la liste des mensuel actif dans un chantier
     * @param idChantier
     * @return 
     */
    @Override
    public List<Mensuel> actifMensuelByChantier(int idChantier) {

        List l = this.getHibernateTemplate().find("SELECT s FROM Mensuel s ,"
                + "in(s.chantiers) c "
                + "where Dtype='Mensuel' "
                + "AND s.statut = 1 "
                + "AND c.id =" + idChantier
        );
        if (l.size() > 0) {
            return l;
        }
        return new ArrayList<>();
        
    }
}
