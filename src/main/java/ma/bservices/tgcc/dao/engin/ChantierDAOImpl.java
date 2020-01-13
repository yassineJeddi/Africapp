/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.dao.engin;

import java.io.Serializable;
import java.util.List;
import javax.annotation.Resource;
import ma.bservice.tgcc.Constante.Constante;
import ma.bservices.beans.CHANTIER_SALARIE;
import ma.bservices.beans.Chantier;
import ma.bservices.beans.Document;
import ma.bservices.beans.Salarie;
import ma.bservices.beans.Zone;
import ma.bservices.tgcc.Entity.Voiture;
import ma.bservices.tgcc.utilitaire.MbHibernateDaoSupport;
import ma.bservices.tgcc.utilitaire.SearchU;
import org.hibernate.FlushMode;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author j.allali
 */
@Repository("chantierDAO")
@Transactional
public class ChantierDAOImpl extends MbHibernateDaoSupport implements ChantierDAO, Serializable {

    private final static String HQL_instance = "m";
    
    

    @Override
    public List<Chantier> findAll() {
        return this.getHibernateTemplate().loadAll(Chantier.class);
    }

    @Override
    public Chantier findById(int id) {
        List l = this.getHibernateTemplate().find("SELECT c FROM Chantier c WHERE c.id = " + id);
        if (l.size() > 0) {
            return (Chantier) l.get(0);
        }

        return null;

    }

    @Override
    public Chantier get_depot() {
        List l = this.getHibernateTemplate().find("SELECT c FROM Chantier c WHERE c.codeNovapaie = '" + Constante.CODE_CHANTIER_DEPOT + "'");
        if (l.size() > 0) {
            return (Chantier) l.get(0);
        }
        return null;
    }

    @Override
    public List<Chantier> findBy_Different_Id_(int id) {

        List l = this.getHibernateTemplate().find("SELECT c FROM Chantier c WHERE c.id != " + id);

        return l;

    }

    @Override
    public Boolean addChantier(Chantier chantier) {

        Transaction tx = null;
        Session session = getSessionFactory().openSession();
        try {

            session.setFlushMode(FlushMode.AUTO);
            tx = session.beginTransaction();

            session.save(chantier);

            tx.commit();
            session.close();
        } catch (Exception exp) {
            tx.rollback();

            session.close();
        }
        return true;
    }

    @Override
    public Boolean updateChantier(Chantier chantier) {

//       Session session;
//        if(!getSessionFactory().isClosed()){
//            session = getSessionFactory().getCurrentSession();
//            System.out.println("___ Open");
//        }
//        else{
//            session=getSessionFactory().openSession();
//            System.out.println("________ closed");
//        }
//        session.setFlushMode(FlushMode.AUTO);
//        if(session.getTransaction().isActive()==true){
//            System.out.println("_______Transaction Active");
//            session.getTransaction();
//            
//        }else{
//            System.out.println("_______Transaction not Active");
//            session.beginTransaction();
//            
//        }
//        System.out.println("_____ Update 3 ________");
//        session.update(chantier);
//        System.out.println("_____ Update 4 ________");
//        session.getTransaction().commit();
//        System.out.println("chantier Updated successfully.....!!");
//        session.close();
//        return true;
        Transaction tx = null;
//        Session session = getSessionFactory().getCurrentSession();
        Session session = getSessionFactory().openSession();
        try {
            session.setFlushMode(FlushMode.AUTO);
            tx = session.beginTransaction();
            // System.out.println("les nombre de chantier" + chantier.getAffiniteChantier().size());
            session.update(chantier);
            //System.out.println("UPDATED");
            tx.commit();
        } catch (Exception exp) {
            tx.rollback();

            session.close();
        }
        return true;

    }

    @Override
    public Chantier findByName(String name) {
        List l = this.getHibernateTemplate().find("SELECT c FROM Chantier c WHERE c.code = '" + name + "'");
//        List l = this.getHibernateTemplate().findByNamedQuery("Chantier.findByPrjapId", new Object[] {id});
        if (l.size() > 0) {
            return (Chantier) l.get(0);
        }

        return null;
    }

    @Override
    public Chantier getByAffaire(String affaire) {
        List l = this.getHibernateTemplate().find("SELECT c FROM Chantier c WHERE c.codeNovapaie = '" + affaire + "'");
        if (l.size() > 0) {
            return (Chantier) l.get(0);
        }

        return null;
    }

    @Override
    public List<Chantier> search(String code, String nom, String region) {
        String filtre = createSearchFiltre(code, nom, region);

        System.out.println("les filtre : " + filtre);
        List l = null;

        l = this.getHibernateTemplate().find("From Chantier " + HQL_instance + " " + filtre);

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
    static String createSearchFiltre(String code, String nom, String region) {

        String filtre = "";
        Boolean filtre_bool_where = false;
        String filtre_one;
        //creation du filre pour le code

        filtre_one = SearchU.createOneFilre("codeNovapaie", code, filtre_bool_where, HQL_instance);
        if (filtre_one != null) {
            filtre += filtre_one;
            filtre_bool_where = true;
        }

        filtre_one = SearchU.createOneFilre("code", nom, filtre_bool_where, HQL_instance);
        if (filtre_one != null) {
            filtre += filtre_one;
            filtre_bool_where = true;
        }
        filtre_one = SearchU.createOneFilre("region", region, filtre_bool_where, HQL_instance);
        if (filtre_one != null) {
            filtre += filtre_one;
            filtre_bool_where = true;
        }

        return filtre;

    }

    @Override
    public Voiture saveVoiture(Voiture voiture) {

        Transaction tx = null;

//        Session session = getSessionFactory().getCurrentSession();
        Session session = getSessionFactory().openSession();
        try {
            session.setFlushMode(FlushMode.AUTO);
            tx = session.beginTransaction();

            session.save(voiture);
            tx.commit();
        } catch (Exception exp) {
            tx.rollback();

            session.close();
        }
        return voiture;
    }

    @Override
    public List<Voiture> findAllVoiture() {

        return this.getHibernateTemplate().loadAll(Voiture.class);

    }

    @Override
    public List<Voiture> searchVoitureBy(String matricule) {

        String filtre = createSearchFiltre(matricule);

        System.out.println("les filtre : " + filtre);
        List l = null;

        l = this.getHibernateTemplate().find("From Voiture " + HQL_instance + " " + filtre);
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
    static String createSearchFiltre(String matricule) {

        String filtre = "";
        Boolean filtre_bool_where = false;
        String filtre_one;
        //creation du filre pour le code

        filtre_one = SearchU.createOneFilre("matriculevoiture", matricule, filtre_bool_where, HQL_instance);
        if (filtre_one != null) {
            filtre += filtre_one;
            filtre_bool_where = true;
        }

        return filtre;

    }

    @Override
    public List<Chantier> findAllInChantier(int chantier_id) {
        List l = this.getHibernateTemplate().find("SELECT c FROM Chantier c WHERE c.id = " + chantier_id);
        if (l.size() > 0) {
            return l;
        }
        return null;
    }

    @Override
    public List<Chantier> findAllInChantierByAffaire(String chantier_id) {
        List l = this.getHibernateTemplate().find("SELECT c FROM Chantier c WHERE c.codeNovapaie = '" + chantier_id + "'");
        if (l.size() > 0) {
            return l;
        }
        return null;
    }

    @Override
    public List<Voiture> getVoiture(int id) {
        List l = this.getHibernateTemplate().find("SELECT c FROM Voiture c WHERE c.chantier.id = " + id);
        if (l.size() > 0) {

            return l;
        }

        return null;
    }

    @Override
    public List<Chantier> findByAffaire(String affaire) {

        List l = this.getHibernateTemplate().find("SELECT c FROM Chantier c WHERE c.codeNovapaie = '" + affaire + "'");
        if (l.size() > 0) {
            return l;
        }
        return null;
    }

    /**
     *
     * @param id
     * @return
     */
    @Override
    public List<Document> getDocumentVoiture(Long id) {
        List l = this.getHibernateTemplate().find("SELECT c FROM Document c WHERE c.voiture.id = " + id);
        if (l.size() > 0) {

            return l;
        }

        return null;
    }

    /*
     * suprimer chantier test 
     */
    @Override
    public Boolean deleteChantierTestUnitaire(Chantier chantier) {

        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);

        session.beginTransaction();
        session.delete(chantier);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public Document saveDocument(Document d) {

        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);

        session.beginTransaction();
        session.save(d);

        session.getTransaction().commit();

        session.close();
        return d;
    }

    @Override
    public Boolean deleteVoiture(Long id) {
        Session sess = getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = sess.beginTransaction();
            Query query = sess.createQuery("delete from Voiture where id=:id");
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

    @Override
    public Boolean deleteChantierByquery(int id) {

        Session sess = getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = sess.beginTransaction();
            Query query = sess.createQuery("delete from Chantier where id=:id");
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

    @Override
    public List<Chantier> searchByUser(String user) {
        List<Chantier> chantiers = null;

        Session s = this.getHibernateTemplate().getSessionFactory().openSession();
        try {
            //demarrage de la session
            s.getTransaction().begin();
            String hql = "select m from Chantier m , in(m.utilisateurs) l where l.login ='" + user + "'";
//            select p from ModPm p join p.modScopeTypes type where type.scopeTypeId = 1
//            String hql="select m from Mensuel m inner join m.loyerSalaries l where l.id ="+id;

            Query query = s.createQuery(hql);

            chantiers = query.list();

            s.getTransaction().commit();

        } catch (Exception e) {

            s.getTransaction().rollback();
        }

        return chantiers;
    }

    @Override
    public Boolean deleteChantier(Chantier chantier) {

        Transaction tx = null;
        Session session;
//        if (getSessionFactory().isClosed()) 
//             {
//            session = getSessionFactory().openSession();
//             } else {
//            session = getSessionFactory().getCurrentSession();
//             }
//        try {
//            
        session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);
        System.out.println("___________ Start");
//            if (session.getTransaction().isActive()) {
//                tx = session.getTransaction();
//            } else {
        tx = session.beginTransaction();
//            }
        System.out.println("___________ 1");
        if (chantier.getZones() != null) {
            for (int i = 0; i < chantier.getZones().size(); i++) {
                session.delete(chantier.getZones().get(i));
            }
        }
        System.out.println("___________ 2");
        session.delete(chantier);
        System.out.println("___________ 3");
        tx.commit();
        System.out.println("___________ 4");
        session.close();
//        } catch (Exception exp) {
//            tx.rollback();
//            System.out.println("_____ Exception ______" + exp.getMessage());
////            session.close();
//        }
        return true;
    }

    @Override
    public void update_Chantier_Test_Unitaire(Chantier chantier) {
        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);

        session.beginTransaction();
        session.update(chantier);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public Chantier add_Chantier_Test_Unitaire(Chantier chantier) {
        Session session = getSessionFactory().openSession();
        Transaction trans = session.beginTransaction();
        try {
            session.save(chantier);
            trans.commit();
            session.close();
        } catch (HibernateException he) {

        }
        return chantier;
    }

    /**
     * retourne la liste des codes des chantiers pour l'autocomplete
     *
     * @return
     */
    @Override
    public List<String> get_allChantiersCodes() {
        List l = this.getHibernateTemplate().find("SELECT DISTINCT c.codeNovapaie FROM Chantier c WHERE c.codeNovapaie is not null");
        if (l.size() > 0) {
            return l;
        }
        return null;
    }

    /**
     * retourne la liste des noms des chantiers pour l'autocomplete
     *
     * @return
     */
    @Override
    public List<String> get_allChantiersNames() {
        List l = this.getHibernateTemplate().find("SELECT DISTINCT c.code FROM Chantier c WHERE c.code is not null");
        if (l.size() > 0) {
            return l;
        }
        return null;
    }

    /**
     * retourne la liste des refions pour l'autocomplete
     *
     * @return
     */
    @Override
    public List<String> get_allChantiersRegins() {
        List l = this.getHibernateTemplate().find("SELECT DISTINCT c.region FROM Chantier c WHERE c.region is not null ");
        if (l.size() > 0) {
            return l;
        }
        return null;
    }

    @Override
    public Chantier findById(String id) {
        List l = this.getHibernateTemplate().find("SELECT c FROM Chantier c WHERE c.id = '" + id + "'");
//        List l = this.getHibernateTemplate().findByNamedQuery("Chantier.findByPrjapId", new Object[] {id});
        if (l.size() > 0) {
            return (Chantier) l.get(0);
        }

        return null;
    }

    @Override
    public Boolean saveAffiniteChantier(Chantier chantier) {
        Transaction tx = null;
        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.COMMIT);
        tx = session.beginTransaction();

        session.saveOrUpdate(session.merge(chantier));
        tx.commit();
        session.close();
        return true;
    }

    @Override
    public List<Chantier> findBy_ChantierAff_Id_(int id) {
        List l = this.getHibernateTemplate().find("SELECT c FROM Chantier c WHERE c.id = " + id);
        if (l.size() > 0) {
            return l;
        }

        return null;
    }

    @Override
    public List<Chantier> getListChantierBy(String code, String nom, String region) {

        List l = null;

        l = this.getHibernateTemplate().find("From Chantier c where c.codeNovapaie= '" + code + "' or c.code = '" + nom
                + "' or c.region = '" + region + "'");

        return l;
    }

    @Override
    public List<String> getListChantierDistinctRegion() {

        List l = this.getHibernateTemplate().find("SELECT DISTINCT c.region FROM Chantier c ");
        if (l.size() > 0) {
            return l;
        }
        return null;

    }

    @Override
    public List<String> getListChantierDistinctRegion(String code) {

        List l = this.getHibernateTemplate().find("SELECT DISTINCT c.region FROM Chantier c WHERE c.region is not null and c.codeNovapaie = '" + code + "'");
        if (l.size() > 0) {
            return l;
        }
        return null;

    }

    @Override
    public List<String> getListChantierDistinctRegionBy(String nom, String code) {

        List l = this.getHibernateTemplate().find("SELECT DISTINCT c.region FROM "
                + "Chantier c WHERE c.region is not null and  c.code = '" + nom
                + "' and c.codeNovapaie = '" + code + "'");
        if (l.size() > 0) {
            return l;
        }
        return null;
    }

    @Override
    public String libelleZoneToInsert(Integer idChantier) {
        Session session = this.getSessionFactory().getCurrentSession();

        //Zone z  = this.getHibernateTemplate().find("From Zone where ");
        Query query = session.createQuery("From Zone where idChantier.id=:idC order by libeleZone desc").setParameter("idC", idChantier);
        query.setMaxResults(1);
        Zone z = (Zone) query.uniqueResult();
        if (z != null) {
            String last = z.getLibeleZone().substring(z.getLibeleZone().length() - 1);
            Integer index;
            try {
                index = Integer.parseInt(last);
            } catch (Exception e) {
                index = 0;
            }
            System.out.println("index" + 1);
            String s = "zone_" + (index + 1);
            System.out.println("new zone " + s);
            return s;
        } else {
            return "Zone_1";
        }
    }

    @Override
    public void affectSalarieChatierFinance(Salarie s, Chantier c) {
        
        CHANTIER_SALARIE chantierSalarie = new CHANTIER_SALARIE();
        chantierSalarie.setCHANTIER_ID(c.getId());
        chantierSalarie.setSALARIE_ID(s.getId());
                
        try {
                Session session = getSessionFactory().openSession();
                session.setFlushMode(FlushMode.AUTO);
                session.beginTransaction();
                session.merge(chantierSalarie);
                session.getTransaction().commit();
                session.close();
        } catch (Exception e) {
                System.out.println("Erreur d'affecter Salarie Chatier Finance car "+e.getMessage());
        }

    }

    @Override
    public void   deleteAffectSalarieToutChatierFinance(Salarie s) {
        
                
        try {
                Session session = getSessionFactory().openSession();
                session.setFlushMode(FlushMode.AUTO);
                session.beginTransaction(); 
                Query query = session.createSQLQuery("DELETE FROM CHANTIER_SALARIE WHERE SALARIE_ID="+s.getId());
                query.executeUpdate();
                session.getTransaction().commit();
                session.close();
        } catch (Exception e) {
                System.out.println("Erreur de supprimer Salarie Chatier Finance car "+e.getMessage());
        }

    }

}
