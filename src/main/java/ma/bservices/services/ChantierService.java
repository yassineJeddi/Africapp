package ma.bservices.services;

import com.sun.javafx.scene.control.skin.VirtualFlow;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import ma.bservices.beans.Chantier;
import ma.bservices.beans.Salarie;
import ma.bservices.beans.Utilisateur;
import ma.bservices.constantes.Constantes;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author bservices
 *
 */
@Service("chantierServiceEvol")
@Transactional
public class ChantierService {

    protected static Logger logger = Logger.getLogger("service");
    @Resource(name = "sessionFactory")
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Récupérer le nombre des chantiers (affecté ou non à un salarié) selon les
     * critères de recherche passés en paramètres
     *
     * @param idSalarie l'identifiant du salarié
     * @param affecte prend comme valeur 0 ou 1. si 0 la méthode renvoie le
     * nombre des chantiers affectés au salarié
     * @param querySearch mots clés pour effectuer la recherche sur le nom du
     * chantier
     *
     * @return nombre des chantiers
     */
    public Object nombreCodesChantiers(Integer idSalarie, int affecte, String querySearch, int[] dos) {

        Session session = sessionFactory.getCurrentSession();
//        try {
//
//            session.connection().setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
//
//        } catch (HibernateException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (SQLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
        Query query = null;

        if (affecte == 1 && idSalarie != 0) {

            query = session.createSQLQuery("SELECT count(*) FROM PRJAP CH INNER JOIN CHANTIER_SALARIE CS ON CH.PRJAP_ID=CS.CHANTIER_ID WHERE CS.SALARIE_ID=? AND LOWER(CH.LIB80) LIKE '%" + StringEscapeUtils.escapeSql(querySearch).toLowerCase() + "%'");
            query.setParameter(0, idSalarie, StandardBasicTypes.INTEGER);

            return query.uniqueResult();
        } else if (affecte == 0 && idSalarie != 0) {

            query = session.createSQLQuery("SELECT CS.CHANTIER_ID FROM CHANTIER_SALARIE CS WHERE CS.SALARIE_ID=?");
            query.setParameter(0, idSalarie, StandardBasicTypes.INTEGER);

            List listeObjetChantiersAffectes = query.list();
            List<Chantier> listeChantiersAffectes = new ArrayList<Chantier>();

            Chantier ch = null;

            for (int i = 0; i < listeObjetChantiersAffectes.size(); i++) {

                ch = new Chantier();
                ch.setId(((Integer) listeObjetChantiersAffectes.get(i)));

                listeChantiersAffectes.add(ch);
            }

            if (listeChantiersAffectes.size() != 0) {

                query = session.createQuery("SELECT count(*) FROM Chantier c where (c.dos=? OR c.dos=?) AND c not in(:liste) AND lower(c.code) LIKE '%" + StringEscapeUtils.escapeSql(querySearch).toLowerCase() + "%'");
                query.setParameter(0, dos[0], StandardBasicTypes.INTEGER);
                query.setParameter(1, dos[1], StandardBasicTypes.INTEGER);
                query.setParameterList("liste", listeChantiersAffectes);

                return query.uniqueResult();

            } else {
                query = session.createQuery("SELECT count(*) FROM Chantier WHERE (dos=? OR dos=?)  and lower(code) LIKE '%" + StringEscapeUtils.escapeSql(querySearch).toLowerCase() + "%'");
                query.setParameter(0, dos[0], StandardBasicTypes.INTEGER);
                query.setParameter(1, dos[1], StandardBasicTypes.INTEGER);
                return query.uniqueResult();
            }

        }

        return 0;

    }

    /**
     * Récupérer la liste des chantiers selon les critères de pagination (start
     * et limit) et de recherche passés en paramètres
     *
     * @param start pour la pagination, c'est un nombre entier qui représente
     * l'index du premier élément à afficher
     * @param limit pour la pagination, c'est un nombre entier qui représente
     * l'index du dernier élément à afficher
     * @param idSalarie l'identifiant du salarié dans la base de données
     * @param affecte prend comme valeur 0 ou 1. si 0 la méthode renvoie le
     * nombre des chantiers affectés au salarié
     * @param querySearch mots clés pour effectuer la recherche sur le nom du
     * chantier
     *
     * @return la liste des Chantiers
     */
    public List<Chantier> listeCodesChantiers(int start, int limit, Integer idSalarie, int affecte, String querySearch, int[] dos) {
        logger.debug("liste des codes chantiers affectés ou non");

        Session session = sessionFactory.getCurrentSession();
//        try {
//
//            session.connection().setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
//
//        } catch (HibernateException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (SQLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
        if (affecte == 1 && idSalarie != 0) {

            Query query = session.createSQLQuery("SELECT CH.PRJAP_ID,CAST(CH.LIB80 as VARCHAR(255)),CAST(CH.UP_REGION as VARCHAR(255)) FROM PRJAP CH INNER JOIN CHANTIER_SALARIE CS ON CH.PRJAP_ID=CS.CHANTIER_ID WHERE CS.SALARIE_ID=? AND LOWER(CH.LIB80) LIKE '%" + StringEscapeUtils.escapeSql(querySearch).toLowerCase() + "%' ORDER BY CH.LIB80");
            query.setParameter(0, idSalarie, StandardBasicTypes.INTEGER);

            query.setFirstResult(start);
            query.setMaxResults(limit);

            List<Chantier> chantiersAffectesList = new ArrayList<Chantier>();
            List liste = query.list();
            Chantier chantier = null;

            for (int i = 0; i < liste.size(); i++) {

                Object[] o = (Object[]) liste.get(i);
                chantier = new Chantier();
                chantier.setId(((Integer) o[0]));
                chantier.setCode((String) o[1]);
                chantier.setRegion((String) o[2]);

                chantiersAffectesList.add(chantier);
            }

            //				query.setParameter(0, id, Hibernate.INTEGER);
            return chantiersAffectesList;
        } else if (affecte == 0 && idSalarie != 0) {

            Query query = session.createSQLQuery("SELECT CS.CHANTIER_ID FROM CHANTIER_SALARIE CS WHERE CS.SALARIE_ID=?");
            query.setParameter(0, idSalarie, StandardBasicTypes.INTEGER);

            List listeObjetChantiersAffectes = query.list();
            List<Chantier> listeChantiersAffectes = new ArrayList<Chantier>();
            Chantier ch = null;

            for (int i = 0; i < listeObjetChantiersAffectes.size(); i++) {

                ch = new Chantier();
                ch.setId(((Integer) listeObjetChantiersAffectes.get(i)));

                listeChantiersAffectes.add(ch);
            }

            if (listeChantiersAffectes.size() != 0) {

                query = session.createQuery("SELECT c.id, c.code,c.region FROM Chantier c where (c.dos=? OR c.dos=?)  and c not in(:liste) AND lower(c.code) like '%" + StringEscapeUtils.escapeSql(querySearch).toLowerCase() + "%' order by c.code");
                query.setParameter(0, dos[0], StandardBasicTypes.INTEGER);
                query.setParameter(1, dos[1], StandardBasicTypes.INTEGER);
                query.setParameterList("liste", listeChantiersAffectes);
                query.setFirstResult(start);
                query.setMaxResults(limit);

                List listeObjetChantiersNonAffectes = query.list();
                List<Chantier> listeChantiersNonAffectes = new ArrayList<Chantier>();
                Chantier c = null;

                for (int i = 0; i < listeObjetChantiersNonAffectes.size(); i++) {

                    Object[] o = (Object[]) listeObjetChantiersNonAffectes.get(i);
                    c = new Chantier();
                    c.setId(((Integer) o[0]));
                    c.setCode((String) o[1]);
                    c.setRegion((String) o[2]);

                    listeChantiersNonAffectes.add(c);

                }

                return listeChantiersNonAffectes;

            } else {

                query = session.createSQLQuery("SELECT PRJAP_ID,CAST(LIB80 as VARCHAR(255)),CAST(CH.UP_REGION as VARCHAR(255)) FROM PRJAP CH WHERE DOS=700 AND LOWER(CH.LIB80) LIKE '%" + StringEscapeUtils.escapeSql(querySearch).toLowerCase() + "%' order by CH.LIB80");
                query.setFirstResult(start);
                query.setMaxResults(limit);

                List listeObjetChantiersNonAffectes = query.list();
                List<Chantier> listeChantiersNonAffectes = new ArrayList<Chantier>();
                Chantier c = null;

                for (int i = 0; i < listeObjetChantiersNonAffectes.size(); i++) {

                    Object[] o = (Object[]) listeObjetChantiersNonAffectes.get(i);
                    c = new Chantier();
                    c.setId(((Integer) o[0]));
                    c.setCode((String) o[1]);
                    c.setRegion((String) o[2]);

                    listeChantiersNonAffectes.add(c);

                }

                return listeChantiersNonAffectes;

            }

        }

        return new ArrayList<Chantier>();

    }

    /**
     * Récupérer le nombre des chantiers affectés ou non à un utilisateur
     *
     * @param idUser l'identifiant de l'utilisateur dans la base de données
     * @param adminAlfresco True si l'utilisateur connecté est un administrateur
     * elle renvoie le nombre total des chantiers sinon elle renvoie le nombre
     * des chantiers affecté à l'utilisateur connecté
     * @param querySearch mots clés pour effectuer la recherche sur le nom du
     * chantier
     *
     * @return le nombre des chantiers
     */
    public Object nombreChantiers(Integer idUser, boolean adminAlfresco, String querySearch, int[] dos) {

        Session session = sessionFactory.getCurrentSession();
        Query query = null;
        if (adminAlfresco) {
            query = session.createQuery("SELECT count(*) FROM Chantier WHERE (dos=? OR dos=?) AND lower(code) LIKE '%" + StringEscapeUtils.escapeSql(querySearch).toLowerCase() + "%'");
            query.setParameter(0, dos[0], StandardBasicTypes.INTEGER);
            query.setParameter(1, dos[1], StandardBasicTypes.INTEGER);
        } else {
            query = session.createSQLQuery("SELECT count(*) FROM PRJAP CH, CHANTIER_UTILISATEUR CU WHERE CH.PRJAP_ID=CU.CHANTIER_ID AND CU.UTILISATEUR_ID=? AND LOWER(CH.LIB80) LIKE '%" + StringEscapeUtils.escapeSql(querySearch).toLowerCase() + "%'");
            query.setParameter(0, idUser, StandardBasicTypes.INTEGER);
        }

        return query.uniqueResult();

    }

    /**
     * Récupérer la liste des chantiers affectés ou non à un utilisateur
     *
     * @param idUser l'identifiant de l'utilisateur dans la base de données
     * @param adminAlfresco True si l'utilisateur connecté est un administrateur
     * elle renvoie la liste de tous de les chantiers sinon elle renvoie la
     * liste des chantiers affecté à l'utilisateur connecté
     * @param start pour la pagination, c'est un nombre entier qui représente
     * l'index du premier élément à afficher
     * @param limit pour la pagination, c'est un nombre entier qui représente
     * l'index du dernier élément à afficher
     * @param querySearch mots clés pour effectuer la recherche sur le nom du
     * chantier
     *
     * @return la liste des Chantiers
     */
    public List<Chantier> listeChantiers(Integer idUser, boolean adminAlfreso, int start, int limit, String querySearch, int[] dos) {
        logger.debug("liste des chantiers");

        Session session = sessionFactory.getCurrentSession();
        Query query = null;

        if (adminAlfreso) {
            query = session.createQuery("SELECT id,code,heureEntree,heureSortie FROM Chantier where (dos=? OR dos=?) and lower(code) LIKE '%" + StringEscapeUtils.escapeSql(querySearch).toLowerCase() + "%' order by code");
            query.setParameter(0, dos[0], StandardBasicTypes.INTEGER);
            query.setParameter(1, dos[1], StandardBasicTypes.INTEGER);
        } else {
            query = session.createSQLQuery("SELECT CH.PRJAP_ID,CAST(CH.LIB80 as VARCHAR(255))FROM PRJAP CH, CHANTIER_UTILISATEUR CU WHERE CH.PRJAP_ID=CU.CHANTIER_ID AND CU.UTILISATEUR_ID=? AND LOWER(CH.LIB80) LIKE '%" + StringEscapeUtils.escapeSql(querySearch).toLowerCase() + "%'");
            query.setParameter(0, idUser, StandardBasicTypes.INTEGER);
        }
        query.setFirstResult(start);
        query.setMaxResults(limit);

        List liste = query.list();

        List<Chantier> listeChantiers = new ArrayList<Chantier>();
        Chantier chantier = null;

        for (int i = 0; i < liste.size(); i++) {

            Object[] o = (Object[]) liste.get(i);
            chantier = new Chantier();
            chantier.setId((Integer) o[0]);
            chantier.setCode((String) o[1]);
            chantier.setHeureEntree(this.getChantierPresences((Integer) o[0]).getHeureEntree().trim());
            chantier.setHeureSortie(this.getChantierPresences((Integer) o[0]).getHeureSortie().trim());
            listeChantiers.add(chantier);
        }

        return listeChantiers;

    }

    /**
     * Récupérer le nombre des chantiers affectés ou non à un utilisateur
     *
     * @param idUser l'identifiant de l'utilisateur dans la base de données
     * @param adminAlfresco True si l'utilisateur connecté est un administrateur
     * elle renvoie le nombre total des chantiers sinon elle renvoie le nombre
     * des chantiers affecté à l'utilisateur connecté
     * @param querySearch mots clés pour effectuer la recherche sur le nom du
     * chantier
     * @param dos tableau des numéros de dossiers sur divalto
     *
     * @return le nombre des chantiers
     */
    public Object nombreChantiersActifs(Integer idUser, boolean adminAlfresco, String querySearch, int[] dos) {

        Session session = sessionFactory.getCurrentSession();
        Query query = null;
        if (adminAlfresco) {
            query = session.createSQLQuery("SELECT count(*) FROM PRJAP WHERE (DOS=? OR DOS=?) AND CE2<=3 AND lower(LIB80) LIKE '%" + StringEscapeUtils.escapeSql(querySearch).toLowerCase() + "%'");
            query.setParameter(0, dos[0], StandardBasicTypes.INTEGER);
            query.setParameter(1, dos[1], StandardBasicTypes.INTEGER);
        } else {
            query = session.createSQLQuery("SELECT count(*) FROM PRJAP CH, CHANTIER_UTILISATEUR CU WHERE CH.PRJAP_ID=CU.CHANTIER_ID AND CU.UTILISATEUR_ID=? AND CE2<=3 AND LOWER(CH.LIB80) LIKE '%" + StringEscapeUtils.escapeSql(querySearch).toLowerCase() + "%'");
            query.setParameter(0, idUser, StandardBasicTypes.INTEGER);
        }

        return query.uniqueResult();

    }

    /**
     * Récupérer la liste des chantiers affectés ou non à un utilisateur
     *
     * @param idUser l'identifiant de l'utilisateur dans la base de données
     * @param adminAlfresco True si l'utilisateur connecté est un administrateur
     * elle renvoie la liste de tous de les chantiers sinon elle renvoie la
     * liste des chantiers affecté à l'utilisateur connecté
     * @param start pour la pagination, c'est un nombre entier qui représente
     * l'index du premier élément à afficher
     * @param limit pour la pagination, c'est un nombre entier qui représente
     * l'index du dernier élément à afficher
     * @param querySearch mots clés pour effectuer la recherche sur le nom du
     * chantier
     *
     * @return la liste des Chantiers
     */
    public List<Chantier> listeChantiersActifs(Integer idUser, boolean adminAlfreso, int start, int limit, String querySearch, int[] dos) {
        logger.debug("liste des chantiers");

        Session session = sessionFactory.getCurrentSession();
        Query query = null;

        if (adminAlfreso) {
            query = session.createSQLQuery("SELECT PRJAP_ID,CAST(LIB80 as NVARCHAR(255)),CAST(HEUREENTREE as VARCHAR(10)),CAST(HEURESORTIE as NVARCHAR(10)) FROM PRJAP where (DOS=? OR DOS=?) AND CE2<=3 and lower(LIB80) LIKE '%" + StringEscapeUtils.escapeSql(querySearch).toLowerCase() + "%' order by LIB80");
            query.setParameter(0, dos[0], StandardBasicTypes.INTEGER);
            query.setParameter(1, dos[1], StandardBasicTypes.INTEGER);
        } else {
            query = session.createSQLQuery("SELECT CH.PRJAP_ID,CAST(CH.LIB80 as VARCHAR(255)),CAST(HEUREENTREE as NVARCHAR(10)),CAST(HEURESORTIE as NVARCHAR(10)) FROM PRJAP CH, CHANTIER_UTILISATEUR CU WHERE CH.PRJAP_ID=CU.CHANTIER_ID AND CU.UTILISATEUR_ID=? AND CE2<=3 AND LOWER(CH.LIB80) LIKE '%" + StringEscapeUtils.escapeSql(querySearch).toLowerCase() + "%'");
            query.setParameter(0, idUser, StandardBasicTypes.INTEGER);
        }
        query.setFirstResult(start);
        query.setMaxResults(limit);

        List liste = query.list();

        List<Chantier> listeChantiers = new ArrayList<Chantier>();
        Chantier chantier = null;

        for (int i = 0; i < liste.size(); i++) {

            Object[] o = (Object[]) liste.get(i);
            chantier = new Chantier();
            chantier.setId((Integer) o[0]);
            chantier.setCode((String) o[1]);
            chantier.setHeureEntree(this.getChantierPresences((Integer) o[0]).getHeureEntree().trim());
            chantier.setHeureSortie(this.getChantierPresences((Integer) o[0]).getHeureSortie().trim());
            listeChantiers.add(chantier);
        }

        return listeChantiers;

    }

    /**
     * Récupérer un chantier à partir de la base de données
     *
     * @param idChantier l'identifiant du chantier dans la base de données
     *
     * @return Chantier
     */
    public Chantier getChantier(Integer idChantier) {
        logger.debug("Chantier par id");

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM Chantier WHERE id=?");

        query.setParameter(0, idChantier, StandardBasicTypes.INTEGER);
        Chantier chantier = (Chantier) query.uniqueResult();

        return chantier;

    }

    public Chantier getChantierPresences(Integer idChantier) {
        logger.debug("Chantier par id");

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("SELECT id ,heureEntree, heureSortie FROM Chantier WHERE id=?");
        query.setParameter(0, idChantier, StandardBasicTypes.INTEGER);

        List liste = query.list();
        Chantier c = null;

        if (liste.size() == 0) {

            return null;
        } else {

            for (int i = 0; i < liste.size(); i++) {

                Object[] o = (Object[]) liste.get(i);
                c = new Chantier();
                c.setId(((Integer) o[0]));
                c.setHeureEntree((String) o[1]);
                c.setHeureSortie((String) o[2]);

            }

        }

        return c;

    }

    public Integer getChantierByAffaire(String affaire) {
        logger.debug("Chantier par affaire");
        String dos = "";
        dos = Constantes.getInstance().numeroDossierDivalto;
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createSQLQuery("SELECT PRJAP_ID FROM PRJAP WHERE AFFAIRE=? AND DOS=?");
        query.setParameter(0, affaire, StandardBasicTypes.STRING);
        query.setParameter(1, dos, StandardBasicTypes.STRING);
        Integer idChantier = (Integer) query.uniqueResult();

        return idChantier;

    }

    //	public Chantier getInformationsChantier(Integer idChantier){
    //		logger.debug("Informations chantier par id");
    //	
    //		Session session = sessionFactory.getCurrentSession();
    //		  		
    //		Query query = session.createSQLQuery("SELECT CODE, VILLE, ADRESSE, HEUREENTREE, HEURESORTIE, NOMBREHEURES, NOMBREMINUTES FROM CHANTIER C WHERE C.ID=?");
    //		query.setParameter(0, idChantier, Hibernate.INTEGER);
    //		
    //		System.out.println("sise " + query.list().size());
    //		
    //		Chantier chantier= null;
    //		  			
    //	
    //	return chantier;
    //	
    //}
    /**
     * Récupérer l'identifiant d'un chantier dans la base de données
     *
     * @param codeChantier le nom du chantier
     * @return id chantier
     */
    public Integer getIdChantierByCode(String codeChantier) {
        logger.debug("Chantier par code");

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("SELECT c.id FROM Chantier c WHERE c.code=?");

        query.setParameter(0, codeChantier, StandardBasicTypes.STRING);

        List list = query.list();

        //		Object[] o=(Object[]) list.get(0); 
        //		Integer idChantier= (Integer) o[0];
        return (Integer) list.get(0);

    }

    /**
     * Récupérer le nombre des chantiers (affecté ou non à un salarié) selon les
     * critères de recherche (code, ville...) passés en paramètres
     *
     * @param idSalarie l'identifiant du salarié
     * @param affecte prend comme valeur 0 ou 1. si 0 la méthode renvoie le
     * nombre des chantiers affectés au salarié
     * @param start pour la pagination, c'est un nombre entier qui représente
     * l'index du premier élément à afficher
     * @param limit pour la pagination, c'est un nombre entier qui représente
     * l'index du dernier élément à afficher
     * @param code le nom du chantier
     * @param ville la ville du chantier
     *
     * @return le nombre des chantiers
     */
    public Object nombreChantiers(Integer idSalarie, int affecte, int start, int limit, String code, String ville, int[] dos) {
        logger.debug("liste des chantiers affectés ou non");

        Session session = sessionFactory.getCurrentSession();
//        try {
//
//            session.connection().setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
//
//        } catch (HibernateException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (SQLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
        Query query = null;

        String queryRecherche = "";

        if (affecte == 1 && idSalarie != 0) {

            if (code != "") {
                queryRecherche += " AND LOWER(CH.LIB80) like '%" + StringEscapeUtils.escapeSql(code).toLowerCase() + "%' ";
            }

            if (ville != "") {
                queryRecherche += " AND LOWER(CH.UP_REGION) like '%" + StringEscapeUtils.escapeSql(ville).toLowerCase() + "%' ";
            }

            query = session.createSQLQuery("SELECT count(*) FROM PRJAP CH INNER JOIN CHANTIER_SALARIE CS ON CH.PRJAP_ID=CS.CHANTIER_ID WHERE CS.SALARIE_ID=?"
                    + queryRecherche);
            query.setParameter(0, idSalarie, StandardBasicTypes.INTEGER);

        } else if (affecte == 0 && idSalarie != 0) {

            query = session.createSQLQuery("SELECT CS.CHANTIER_ID FROM CHANTIER_SALARIE CS WHERE CS.SALARIE_ID=? ");
            query.setParameter(0, idSalarie, StandardBasicTypes.INTEGER);

            List listeObjetChantiersAffectes = query.list();
            List<Chantier> listeChantiersAffectes = new ArrayList<Chantier>();

            Chantier c = null;

            for (int i = 0; i < listeObjetChantiersAffectes.size(); i++) {

                c = new Chantier();
                c.setId(((Integer) listeObjetChantiersAffectes.get(i)));

                listeChantiersAffectes.add(c);
            }

            if (code != "") {
                queryRecherche += " and lower(c.code) like '%" + StringEscapeUtils.escapeSql(code).toLowerCase() + "%' ";
            }

            if (ville != "") {
                queryRecherche += " and lower(c.region) like '" + StringEscapeUtils.escapeSql(ville).toLowerCase() + "' ";
            }

            if (listeChantiersAffectes.size() != 0) {

                query = session.createQuery("SELECT count(*) FROM Chantier c where (c.dos=? OR c.dos=?) and c not in(:liste) " + queryRecherche);
                query.setParameter(0, dos[0], StandardBasicTypes.INTEGER);
                query.setParameter(1, dos[1], StandardBasicTypes.INTEGER);
                query.setParameterList("liste", listeChantiersAffectes);

            } else {

                //queryRecherche = queryRecherche.replaceFirst("and","where");
                query = session.createQuery("SELECT count(*) FROM Chantier c where (c.dos=? OR c.dos=?) " + queryRecherche);
                query.setParameter(0, dos[0], StandardBasicTypes.INTEGER);
                query.setParameter(1, dos[1], StandardBasicTypes.INTEGER);

            }

        }
        return query.uniqueResult();

    }

    /**
     * Récupérer la liste des chantiers (affecté ou non à un salarié) selon les
     * critères de recherche (code, ville...) passés en paramètres
     *
     * @param idSalarie l'identifiant du salarié
     * @param affecte prend comme valeur 0 ou 1. si 0 la méthode renvoie le
     * nombre des chantiers affectés au salarié
     * @param start pour la pagination, c'est un nombre entier qui représente
     * l'index du premier élément à afficher
     * @param limit pour la pagination, c'est un nombre entier qui représente
     * l'index du dernier élément à afficher
     * @param code le nom du chantier
     * @param ville la ville du chantier
     *
     * @return Chantiers
     */
    public List<Chantier> listeChantiersAffectes(Integer idSalarie, int affecte, int start, int limit, String code, String ville, int[] dos, boolean isAdmin, Integer IdUser) {
        logger.debug("liste des chantiers affectés ou non");

        Session session = sessionFactory.getCurrentSession();
//        try {
//
//            session.connection().setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
//
//        } catch (HibernateException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (SQLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }

        String queryRecherche = "";

        if (affecte == 1 && idSalarie != 0) {

            List<Chantier> chantiersAffectesAtUser = new LinkedList<>();
            String join = "";
            if (!isAdmin) {
                join += " INNER JOIN CHANTIER_UTILISATEUR CU ON CH.PRJAP_ID=CU.CHANTIER_ID WHERE CU.UTILISATEUR_ID=:idUser ";
            }
            if (code != "") {
                queryRecherche += " AND LOWER(CH.LIB80) like '%" + StringEscapeUtils.escapeSql(code).toLowerCase() + "%' ";
            }

            if (ville != "") {
                queryRecherche += " AND LOWER (CH.UP_REGION) like '%" + StringEscapeUtils.escapeSql(ville).toLowerCase() + "%' ";
            }
            Query query = null;
if (!isAdmin) {
            query = session.createSQLQuery("SELECT CS.CHANTIER_ID,CS.SALARIE_ID FROM PRJAP CH INNER JOIN CHANTIER_SALARIE CS ON CH.PRJAP_ID=CS.CHANTIER_ID INNER JOIN CHANTIER_UTILISATEUR CU ON CH.PRJAP_ID=CU.CHANTIER_ID WHERE CU.UTILISATEUR_ID=?  AND CS.SALARIE_ID=?" + queryRecherche + " ORDER BY CH.LIB80");
            query.setParameter(0, IdUser, StandardBasicTypes.INTEGER);
            query.setParameter(1, idSalarie, StandardBasicTypes.INTEGER);
         
}
else {      query = session.createSQLQuery("SELECT CS.CHANTIER_ID,CS.SALARIE_ID FROM PRJAP CH INNER JOIN CHANTIER_SALARIE CS ON CH.PRJAP_ID=CS.CHANTIER_ID  WHERE CS.SALARIE_ID=?" + queryRecherche + " ORDER BY CH.LIB80");
            query.setParameter(0, idSalarie, StandardBasicTypes.INTEGER);
}
           
            query.setFirstResult(start);
            query.setMaxResults(limit);

            List<Chantier> chantiersAffectesList = new ArrayList<Chantier>();
            List liste = query.list();
            Chantier chantier = null;

            for (int i = 0; i < liste.size(); i++) {

                Object[] o = (Object[]) query.list().get(i);
                chantier = new Chantier();
                chantier = getChantierById((Integer) o[0]);

//				chantier.setId(((Integer) o[0]));
//				System.out.println("lib: "+o[1]);
//				
//				chantier.setCode((String) o[1]);
//				chantier.setRegion((String) o[2]);
//				System.out.println("reg: "+o[2]);
                chantiersAffectesList.add(chantier);
            }

            //				query.setParameter(0, id, Hibernate.INTEGER);
            return chantiersAffectesList;
        } else if (affecte == 0 && idSalarie != 0) {

            Query query = session.createSQLQuery("SELECT CS.CHANTIER_ID FROM CHANTIER_SALARIE CS WHERE CS.SALARIE_ID=?"); /** les Chantiers d'un salarie */
            query.setParameter(0, idSalarie, StandardBasicTypes.INTEGER);

            List<Chantier> chantiersAffectesList = new ArrayList<>();
            List liste = query.list();
            Chantier chantier = null;

            for (int i = 0; i < liste.size(); i++) {

                chantier = new Chantier();
                chantier.setId(((Integer) liste.get(i))); 

                chantiersAffectesList.add(chantier);  /** les Chantiers d'un salarie */
            }
            List<Chantier> chantiersAffectesAtUser = new LinkedList<>();
            if (!isAdmin) {
                Query queryUser = session.createSQLQuery("SELECT CS.CHANTIER_ID FROM CHANTIER_UTILISATEUR CS WHERE CS.UTILISATEUR_ID=?");
                queryUser.setParameter(0, IdUser, StandardBasicTypes.INTEGER);
                List listeUser = queryUser.list();
                if (listeUser.isEmpty()) {
                    return new LinkedList<>();
                }
                Chantier chantierToUser = null;
                for (int i = 0; i < listeUser.size(); i++) {
                    chantierToUser = new Chantier();
                    chantierToUser.setId(((Integer) listeUser.get(i)));
                    chantiersAffectesAtUser.add(chantierToUser); /** chantiers affectes aux utilisateurs */
                }
                queryRecherche += " and c in (:listeUsers) ";
            }

            if (code != "") {
                queryRecherche += " and lower (c.code) like  '%" + StringEscapeUtils.escapeSql(code).toLowerCase() + "%' ";
            }

            if (ville != "") {
                queryRecherche += " and lower (c.region) like '%" + StringEscapeUtils.escapeSql(ville).toLowerCase() + "%' ";
            }

            if (chantiersAffectesList.size() != 0) {

                query = session.createQuery("SELECT c.id, c.code, c.region FROM Chantier c where (c.dos=? OR c.dos=?) and c not in(:liste)" + queryRecherche + " order by c.code");
                query.setParameter(0, dos[0], StandardBasicTypes.INTEGER);
                query.setParameter(1, dos[1], StandardBasicTypes.INTEGER);
                query.setParameterList("liste", chantiersAffectesList);
                if (!isAdmin) {
                    query.setParameterList("listeUsers", chantiersAffectesAtUser);
                }
                query.setFirstResult(start);
                query.setMaxResults(limit);

                List<Chantier> chantiersNonAffectesList = new ArrayList<Chantier>();
                //					 chantiersNonAffectesList=query.list();
                List listeObjetchantiersNonAffectes = query.list();
                Chantier c = null;

                for (int i = 0; i < listeObjetchantiersNonAffectes.size(); i++) {

                    Object[] o = (Object[]) listeObjetchantiersNonAffectes.get(i);
                    c = new Chantier();
                    c.setId(((Integer) o[0]));
                    c.setCode((String) o[1]);
                    c.setRegion((String) o[2]);
                    chantiersNonAffectesList.add(c);
                }

                return chantiersNonAffectesList;

            } else {

                //queryRecherche = queryRecherche.replaceFirst("and", "where");
                query = session.createQuery("SELECT c.id, c.code, c.region FROM Chantier c where (c.dos=? OR c.dos=?) " + queryRecherche + " order by c.code");
                query.setParameter(0, dos[0], StandardBasicTypes.INTEGER);
                query.setParameter(1, dos[1], StandardBasicTypes.INTEGER);
                if (!isAdmin) {
                    query.setParameterList("listeUsers", chantiersAffectesAtUser);
                }
                //					 query=session.createSQLQuery("SELECT ID,CODE,VILLE,ADRESSE FROM CHANTIER CH"+queryRech+" order by CH.CODE");	
                query.setFirstResult(start);
                query.setMaxResults(limit);

                List<Chantier> chantiersNonAffectesList = new ArrayList<Chantier>();
                //					 chantiersNonAffectesList=query.list();
                List listeObjetchantiersNonAffectes = query.list();
                Chantier c = null;

                for (int i = 0; i < listeObjetchantiersNonAffectes.size(); i++) {

                    Object[] o = (Object[]) listeObjetchantiersNonAffectes.get(i);
                    c = new Chantier();
                    c.setId(((Integer) o[0]));
                    c.setCode((String) o[1]);
                    c.setRegion((String) o[2]);
                    chantiersNonAffectesList.add(c);
                }

                return chantiersNonAffectesList;

            }

        }

        return new ArrayList<Chantier>();

    }
    public List<Chantier> listeChantiersAffectes2(Integer idSalarie, int affecte, int start, int limit, String code, String ville, int[] dos, boolean isAdmin, Integer IdUser) {
        logger.debug("liste des chantiers affectés ou non");

        Session session = sessionFactory.getCurrentSession();
//        try {
//
//            session.connection().setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
//
//        } catch (HibernateException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (SQLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }

        String queryRecherche = "";
/*** Liste des Chaniters Affectés à un salarié, le resultat est different en fonction des droits de l'utilsateur, un utilisateur admin a acces à l'ensemble des chantiers
 * un utlisateur non admin n'a acces qu'aux chantiers aux quels il est affecté)
 *  */
        if (affecte == 1 && idSalarie != 0) {

            List<Chantier> chantiersAffectesAtUser = new LinkedList<>();
            String join = "";
            if (!isAdmin) {
                join += " INNER JOIN CHANTIER_UTILISATEUR CU ON CH.PRJAP_ID=CU.CHANTIER_ID WHERE CU.UTILISATEUR_ID=:idUser ";
            }
            if (code != "") {
                queryRecherche += " AND LOWER(CH.LIB80) like '%" + StringEscapeUtils.escapeSql(code).toLowerCase() + "%' ";
            }

            if (ville != "") {
                queryRecherche += " AND LOWER (CH.UP_REGION) like '%" + StringEscapeUtils.escapeSql(ville).toLowerCase() + "%' ";
            }
            Query query = null;
if (!isAdmin) {
    
            query = session.createSQLQuery("SELECT CS.CHANTIER_ID,CS.SALARIE_ID FROM PRJAP CH INNER JOIN CHANTIER_SALARIE CS ON CH.PRJAP_ID=CS.CHANTIER_ID " + join + " AND CS.SALARIE_ID=?" + queryRecherche + " ORDER BY CH.LIB80");
}
else {      query = session.createSQLQuery("SELECT CS.CHANTIER_ID,CS.SALARIE_ID FROM PRJAP CH INNER JOIN CHANTIER_SALARIE CS ON CH.PRJAP_ID=CS.CHANTIER_ID  WHERE CS.SALARIE_ID=?" + queryRecherche + " ORDER BY CH.LIB80");
}
            query.setParameter(0, idSalarie, StandardBasicTypes.INTEGER);
            if (!isAdmin) {
                query.setParameter("idUser", IdUser, StandardBasicTypes.INTEGER);
            }
            query.setFirstResult(start);
            query.setMaxResults(limit);

            List<Chantier> chantiersAffectesList = new ArrayList<Chantier>();
            List liste = query.list();
            Chantier chantier = null;

            for (int i = 0; i < liste.size(); i++) {

                Object[] o = (Object[]) query.list().get(i);
                chantier = new Chantier();
                chantier = getChantierById((Integer) o[0]);

//				chantier.setId(((Integer) o[0]));
//				System.out.println("lib: "+o[1]);
//				
//				chantier.setCode((String) o[1]);
//				chantier.setRegion((String) o[2]);
//				System.out.println("reg: "+o[2]);
                chantiersAffectesList.add(chantier);
            }

            //				query.setParameter(0, id, Hibernate.INTEGER);
            return chantiersAffectesList;
            
            
            /** */
        } else if (affecte == 0 && idSalarie != 0) {

            Query query = session.createSQLQuery("SELECT CS.CHANTIER_ID FROM CHANTIER_SALARIE CS WHERE CS.SALARIE_ID=?");
            query.setParameter(0, idSalarie, StandardBasicTypes.INTEGER);

            List<Chantier> chantiersAffectesList = new ArrayList<>();
            List liste = query.list();
            Chantier chantier = null;

            for (int i = 0; i < liste.size(); i++) {

                chantier = new Chantier();
                chantier.setId(((Integer) liste.get(i)));

                chantiersAffectesList.add(chantier);                 /** @chantiersAffectesListles chantiers affectés aux Salaries  */

            }
            List<Chantier> chantiersAffectesAtUser = new LinkedList<>();
            if (!isAdmin) {
                Query queryUser = session.createSQLQuery("SELECT CS.CHANTIER_ID FROM CHANTIER_UTILISATEUR CS WHERE CS.UTILISATEUR_ID=?");
                queryUser.setParameter(0, IdUser, StandardBasicTypes.INTEGER);
                List listeUser = query.list();
                if (listeUser.isEmpty()) {
                    return new LinkedList<>();
                }
                Chantier chantierToUser = null;
                for (int i = 0; i < listeUser.size(); i++) {
                    chantierToUser = new Chantier();
                    chantierToUser.setId(((Integer) liste.get(i)));
                    chantiersAffectesAtUser.add(chantierToUser);   /** @chantiersAffectesAtUser affectés aux Utilisateurs  */
                }
                queryRecherche += " and c in (:listeUsers) ";
            }

            if (code != "") {
                queryRecherche += " and lower (c.code) like  '%" + StringEscapeUtils.escapeSql(code).toLowerCase() + "%' ";
            }

            if (ville != "") {
                queryRecherche += " and lower (c.region) like '%" + StringEscapeUtils.escapeSql(ville).toLowerCase() + "%' ";
            }

            if (chantiersAffectesList.size() != 0) {

                query = session.createQuery("SELECT c.id, c.code, c.region FROM Chantier c where (c.dos=? OR c.dos=?) and c not in(:liste)" + queryRecherche + " order by c.code");
                query.setParameter(0, dos[0], StandardBasicTypes.INTEGER);
                query.setParameter(1, dos[1], StandardBasicTypes.INTEGER);
                query.setParameterList("liste", chantiersAffectesList);
                if (!isAdmin) {
                    query.setParameterList("listeUsers", chantiersAffectesAtUser);
                }
                query.setFirstResult(start);
                query.setMaxResults(limit);

                List<Chantier> chantiersNonAffectesList = new ArrayList<Chantier>();
                
                //					 chantiersNonAffectesList=query.list();
                List listeObjetchantiersNonAffectes = query.list();
                Chantier c = null;

                for (int i = 0; i < listeObjetchantiersNonAffectes.size(); i++) {

                    Object[] o = (Object[]) listeObjetchantiersNonAffectes.get(i);
                    c = new Chantier();
                    c.setId(((Integer) o[0]));
                    c.setCode((String) o[1]);
                    c.setRegion((String) o[2]);
                    chantiersNonAffectesList.add(c);
                }

                return chantiersNonAffectesList;

            } else {

                //queryRecherche = queryRecherche.replaceFirst("and", "where");
                query = session.createQuery("SELECT c.id, c.code, c.region FROM Chantier c where (c.dos=? OR c.dos=?) " + queryRecherche + " order by c.code");
                query.setParameter(0, dos[0], StandardBasicTypes.INTEGER);
                query.setParameter(1, dos[1], StandardBasicTypes.INTEGER);
                if (!isAdmin) {
                    query.setParameterList("listeUsers", chantiersAffectesAtUser);
                }
                //					 query=session.createSQLQuery("SELECT ID,CODE,VILLE,ADRESSE FROM CHANTIER CH"+queryRech+" order by CH.CODE");	
                query.setFirstResult(start);
                query.setMaxResults(limit);

                List<Chantier> chantiersNonAffectesList = new ArrayList<Chantier>();
                //					 chantiersNonAffectesList=query.list();
                List listeObjetchantiersNonAffectes = query.list();
                Chantier c = null;

                for (int i = 0; i < listeObjetchantiersNonAffectes.size(); i++) {

                    Object[] o = (Object[]) listeObjetchantiersNonAffectes.get(i);
                    c = new Chantier();
                    c.setId(((Integer) o[0]));
                    c.setCode((String) o[1]);
                    c.setRegion((String) o[2]);
                    chantiersNonAffectesList.add(c);
                }

                return chantiersNonAffectesList;

            }

        }

        return new ArrayList<Chantier>();

    }


    /**
     * Affecter un salarié à un chantier
     *
     * @param salarie instance de l'objet de Salarie, il doit être récupéré à
     * partir de la base de données puis passé en paramètres
     * @param chantier instance de l'objet de Chantier, il doit être récupéré à
     * partir de la base de données puis passé en paramètres
     */
    public void affecterSalarieChantier(Salarie salarie, Chantier chantier) {
        logger.debug("affecter un salarie à un chantier");

        Session session = sessionFactory.getCurrentSession();
//        try {
//
//            session.connection().setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
//
//        } catch (HibernateException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (SQLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//		chantier.getSalaries().add(salarie);		
//
//		session.update(chantier);
//		
        Query query = session.createSQLQuery("INSERT INTO CHANTIER_SALARIE(CHANTIER_ID,SALARIE_ID) VALUES(?,?)");
        query.setParameter(0, chantier.getId(), StandardBasicTypes.INTEGER);
        query.setParameter(1, salarie.getId(), StandardBasicTypes.INTEGER);
        query.executeUpdate();

    }

    /**
     * Désaffecter un salarié d'un chantier
     *
     * @param salarie instance de l'objet de Salarie, il doit être récupéré à
     * partir de la base de données puis passé en paramètres
     * @param chantier instance de l'objet de Chantier, il doit être récupéré à
     * partir de la base de données puis passé en paramètres
     */
    public void desaffecterSalarieChantier(Salarie salarie, Chantier chantier) {
        logger.debug("désaffecter un salarie d'un chantier");

        Session session = sessionFactory.getCurrentSession();
//        try {
//
//            session.connection().setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
//
//        } catch (HibernateException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (SQLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        try {
//
//            session.connection().setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
//
//        } catch (HibernateException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (SQLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
        Query query = session.createSQLQuery("DELETE FROM CHANTIER_SALARIE WHERE CHANTIER_ID = ? AND SALARIE_ID=?");
        query.setParameter(0, chantier.getId(), StandardBasicTypes.INTEGER);
        query.setParameter(1, salarie.getId(), StandardBasicTypes.INTEGER);
        query.executeUpdate();

        //		chantier.getSalaries().remove(salarie);		
        //		session.update(chantier);
    }

    /**
     * Désaffecter le salarié de tous les chantiers
     *
     * @param salarieId
     */
    public void desaffecterSalarieTousChantiers(Integer salarieId) {
        logger.debug("désaffecter un salarie de tous les chantiers");

        Session session = sessionFactory.getCurrentSession();
//        try {
//
//            session.connection().setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
//
//        } catch (HibernateException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (SQLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
        Query query = session.createSQLQuery("DELETE FROM CHANTIER_SALARIE WHERE SALARIE_ID=?");
        query.setParameter(0, salarieId, StandardBasicTypes.INTEGER);
        query.executeUpdate();

    }

    //Vérification de l'affectation du salarié à un chantier
    /**
     * Vérifier si un salarié est affecté à un chantier
     *
     * @param idChantier l'identifiant du chantier dans la base de données
     * @param idSalarie l'identifiant du salarié dans la base de données
     * @return Renvoie True si le salarié est affecté au chantier sinon False
     */
    public boolean verifierAffectationSalarieChantier(Integer idChantier, Integer idSalarie) {
        logger.debug("Vérifier l'affectation du salarié à un chantier");

        Session session = sessionFactory.getCurrentSession();
//        try {
//
//            session.connection().setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
//
//        } catch (HibernateException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (SQLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
        Query query = session.createSQLQuery("SELECT count(*) FROM CHANTIER_SALARIE WHERE CHANTIER_ID = ? AND SALARIE_ID=?");
        query.setParameter(0, idChantier, StandardBasicTypes.INTEGER);
        query.setParameter(1, idSalarie, StandardBasicTypes.INTEGER);

        Object nombre = query.uniqueResult();

        if (((Integer) nombre).intValue() != 0) {
            return true;
        }

        return false;
    }

    //---------------------------------------- 
    /**
     * Récupérer le nombre des chantiers selon les critères de recherche passés
     * en paramètres (nom et/ou ville)
     *
     * @param nom le nom du chantier
     * @param ville la ville du chantier
     * @return le nombre des chantiers
     */
    public Object nombreChantiers(String nom, String ville, int[] dos) {

        Session session = sessionFactory.getCurrentSession();

        String queryRecherche = "";

        if (nom != "") {
            queryRecherche += " AND lower(code) like '%" + StringEscapeUtils.escapeSql(nom).toLowerCase() + "%' ";
        }
        if (ville != "") {
            queryRecherche += " AND lower(region) like '%" + StringEscapeUtils.escapeSql(ville).toLowerCase() + "%' ";
        }

        //queryRecherche = queryRecherche.replaceFirst("AND", " WHERE");
        Query query = session.createQuery("SELECT count(*) FROM Chantier where (dos=? OR dos=?) " + queryRecherche);
        query.setParameter(0, dos[0], StandardBasicTypes.INTEGER);
        query.setParameter(1, dos[1], StandardBasicTypes.INTEGER);

        return query.uniqueResult();

    }

    /**
     * Récupérer la liste des chantiers selon les critères de pagination(start
     * et limit) et recherche passés en paramètres (nom et/ou ville)
     *
     * @param start pour la pagination, c'est un nombre entier qui représente
     * l'index du premier élément à afficher
     * @param limit pour la pagination, c'est un nombre entier qui représente
     * l'index du dernier élément à afficher
     * @param nom le nom du chantier
     * @param ville la ville du chantier
     *
     * @return la liste des Chantiers
     */
    public List<Chantier> chantiersList(int start, int limit, String nom, String ville, int[] dos) {

        logger.debug("liste des chantiers");

        Session session = sessionFactory.getCurrentSession();
        String queryRecherche = "";

        if (nom != "") {
            queryRecherche += " AND lower(code) like '%" + StringEscapeUtils.escapeSql(nom).toLowerCase() + "%' ";
        }
        if (ville != "") {
            queryRecherche += " AND lower(region) like '%" + StringEscapeUtils.escapeSql(ville).toLowerCase() + "%' ";
        }

//		queryRecherche = queryRecherche.replaceFirst("AND", " WHERE");
System.out.println("SELECT id,code,region, heureEntree, heureSortie, nombreHeures, nombreMinutes FROM Chantier where (dos=? OR dos=?) " + queryRecherche + " order by code");
        Query query = session.createQuery("SELECT id,code,region, heureEntree, heureSortie, nombreHeures, nombreMinutes FROM Chantier where (dos=? OR dos=?) " + queryRecherche + " order by code");
        query.setParameter(0, dos[0], StandardBasicTypes.INTEGER);
        query.setParameter(1, dos[1], StandardBasicTypes.INTEGER);
        query.setFirstResult(start);
        query.setMaxResults(limit);

        List liste = query.list();

        List<Chantier> listeChantiers = new ArrayList<Chantier>();
        Chantier chantier = null;

        for (int i = 0; i < liste.size(); i++) {

            Object[] o = (Object[]) liste.get(i);
            chantier = new Chantier();
            chantier.setId((Integer) o[0]);
            chantier.setCode((String) o[1]);
            chantier.setRegion((String) o[2]);
//			chantier.setAdresse((String) o[3]);
            chantier.setHeureEntree((String) o[3]);
            chantier.setHeureSortie((String) o[4]);
            chantier.setNombreHeures((Integer) o[5]);
            chantier.setNombreMinutes((Integer) o[6]);

            listeChantiers.add(chantier);
        }

        return listeChantiers;

    }

    /**
     * Créer un nouveau chantier sur la base de données
     *
     * @param chantier
     */
    public void ajouterChantier(Chantier chantier) {

        logger.debug("ajouter un nouveau chantier");

        Session session = sessionFactory.getCurrentSession();
        session.save(chantier);

    }

    // AFFECTATION UTILISATEUR AUX CHANTIERS
    /**
     * Récupérer le nombre des chantiers affectés ou non à un utilisateur
     *
     * @param idUser l'identifiant de l'utilisateur dans la base de données
     * @param affecte prend comme valeur 0 ou 1. si 0 la méthode renvoie le
     * nombre des chantiers affectés au salarié
     * @param querySearch mots clés pour effectuer la recherche sur le nom du
     * chantier
     *
     * @return le nombre des chantier
     */
    public Object nombreCodesChantiersUtilisateur(Integer idUser, int affecte, String querySearch, int[] dos) {

        Session session = sessionFactory.getCurrentSession();
        Query query = null;

        if (affecte == 1 && idUser != 0) {

            query = session.createSQLQuery("SELECT count(*) FROM PRJAP CH, CHANTIER_UTILISATEUR CU WHERE CH.PRJAP_ID=CU.CHANTIER_ID AND CU.UTILISATEUR_ID=? AND LOWER(CH.LIB80) LIKE '%" + StringEscapeUtils.escapeSql(querySearch).toLowerCase() + "%'");
            query.setParameter(0, idUser, StandardBasicTypes.INTEGER);

            return query.uniqueResult();
        } else if (affecte == 0 && idUser != 0) {

            query = session.createSQLQuery("SELECT CH.PRJAP_ID FROM PRJAP CH, CHANTIER_UTILISATEUR CU WHERE CH.PRJAP_ID=CU.CHANTIER_ID AND CU.UTILISATEUR_ID=? ");
            query.setParameter(0, idUser, StandardBasicTypes.INTEGER);

            List listeObjetChantiersAffectes = query.list();
            List<Chantier> listeChantiersAffectes = new ArrayList<Chantier>();

            Chantier ch = null;

            for (int i = 0; i < listeObjetChantiersAffectes.size(); i++) {

                ch = new Chantier();
                ch.setId(((Integer) listeObjetChantiersAffectes.get(i)));

                listeChantiersAffectes.add(ch);
            }

            if (listeChantiersAffectes.size() != 0) {
                System.out.println("coun2t");

                query = session.createQuery("SELECT count(*) FROM Chantier c where (c.dos=? OR c.dos=?) and c not in(:liste) AND lower(c.code) LIKE '%" + StringEscapeUtils.escapeSql(querySearch).toLowerCase() + "%'");
                query.setParameter(0, dos[0], StandardBasicTypes.INTEGER);
                query.setParameter(1, dos[1], StandardBasicTypes.INTEGER);
                query.setParameterList("liste", listeChantiersAffectes);

                return query.uniqueResult();

            } else {
                query = session.createQuery("SELECT count(*) FROM Chantier WHERE (dos=? OR dos=?) and lower(code) LIKE '%" + StringEscapeUtils.escapeSql(querySearch).toLowerCase() + "%'");
                query.setParameter(0, dos[0], StandardBasicTypes.INTEGER);
                query.setParameter(1, dos[1], StandardBasicTypes.INTEGER);
                return query.uniqueResult();
            }

        }

        return 0;

    }

    /**
     * Récupérer la liste des chantiers d'un utilisateur
     *
     * @param start pour la pagination, c'est un nombre entier qui représente
     * l'index du premier élément à afficher
     * @param limit pour la pagination, c'est un nombre entier qui représente
     * l'index du dernier élément à afficher
     * @param idUser l'identifiant de l'utilisateur dans la base de données
     * @param affecte prend comme valeur 0 ou 1. si 0 la méthode renvoie le
     * nombre des chantiers affectés à l'utilisateur
     * @param querySearch mots clés pour effectuer la recherche sur le nom du
     * chantier
     *
     * @return la liste des Chantiers
     */
    public List<Chantier> listeCodesChantiersUtilisateur(int start, int limit, Integer idUser, int affecte, String querySearch, int[] dos) {
        logger.debug("liste des codes chantiers affectés ou non");

        Session session = sessionFactory.getCurrentSession();

        if (affecte == 1 && idUser != 0) {

            Query query = session.createSQLQuery("SELECT CH.PRJAP_ID,CAST(CH.LIB80 as VARCHAR(255)) FROM PRJAP CH, CHANTIER_UTILISATEUR CU WHERE CH.PRJAP_ID=CU.CHANTIER_ID AND CU.UTILISATEUR_ID=?  AND LOWER(CH.LIB80) LIKE '%" + StringEscapeUtils.escapeSql(querySearch).toLowerCase() + "%' ORDER BY CH.LIB80");
            query.setParameter(0, idUser, StandardBasicTypes.INTEGER);

            query.setFirstResult(start);
            query.setMaxResults(limit);

            List<Chantier> chantiersAffectesList = new ArrayList<Chantier>();
            List liste = query.list();
            Chantier chantier = null;

            for (int i = 0; i < liste.size(); i++) {

                Object[] o = (Object[]) liste.get(i);
                chantier = new Chantier();
                chantier.setId(((Integer) o[0]));
                chantier.setCode((String) o[1]);

                chantiersAffectesList.add(chantier);
            }

            //				query.setParameter(0, id, Hibernate.INTEGER);
            return chantiersAffectesList;
        } else if (affecte == 0 && idUser != 0) {

            Query query = session.createSQLQuery("SELECT CH.PRJAP_ID FROM PRJAP CH, CHANTIER_UTILISATEUR CU WHERE CH.PRJAP_ID=CU.CHANTIER_ID AND CU.UTILISATEUR_ID=? ");
            query.setParameter(0, idUser, StandardBasicTypes.INTEGER);

            List listeObjetChantiersAffectes = query.list();
            List<Chantier> listeChantiersAffectes = new ArrayList<Chantier>();
            Chantier ch = null;

            for (int i = 0; i < listeObjetChantiersAffectes.size(); i++) {

                ch = new Chantier();
                ch.setId(((Integer) listeObjetChantiersAffectes.get(i)));

                listeChantiersAffectes.add(ch);
            }

            if (listeChantiersAffectes.size() != 0) {

                query = session.createQuery("SELECT c.id, c.code FROM Chantier c where (c.dos=? OR c.dos=?) and c not in(:liste) AND lower(c.code) like '%" + StringEscapeUtils.escapeSql(querySearch).toLowerCase() + "%' order by c.code");
                query.setParameter(0, dos[0], StandardBasicTypes.INTEGER);
                query.setParameter(1, dos[1], StandardBasicTypes.INTEGER);
                query.setParameterList("liste", listeChantiersAffectes);
                query.setFirstResult(start);
                query.setMaxResults(limit);

                List listeObjetChantiersNonAffectes = query.list();
                List<Chantier> listeChantiersNonAffectes = new ArrayList<Chantier>();
                Chantier c = null;

                for (int i = 0; i < listeObjetChantiersNonAffectes.size(); i++) {

                    Object[] o = (Object[]) listeObjetChantiersNonAffectes.get(i);
                    c = new Chantier();
                    c.setId(((Integer) o[0]));
                    c.setCode((String) o[1]);

                    listeChantiersNonAffectes.add(c);

                }

                return listeChantiersNonAffectes;

            } else {

                query = session.createSQLQuery("SELECT PRJAP_ID,CAST(CH.LIB80 as VARCHAR(255)) FROM PRJAP CH WHERE LOWER(CH.LIB80) LIKE '%" + StringEscapeUtils.escapeSql(querySearch).toLowerCase() + "%' order by CH.LIB80");
                query.setFirstResult(start);
                query.setMaxResults(limit);

                List listeObjetChantiersNonAffectes = query.list();
                List<Chantier> listeChantiersNonAffectes = new ArrayList<Chantier>();
                Chantier c = null;

                for (int i = 0; i < listeObjetChantiersNonAffectes.size(); i++) {

                    Object[] o = (Object[]) listeObjetChantiersNonAffectes.get(i);
                    c = new Chantier();
                    c.setId(((Integer) o[0]));
                    c.setCode((String) o[1]);

                    listeChantiersNonAffectes.add(c);

                }

                return listeChantiersNonAffectes;

            }

        }

        return new ArrayList<Chantier>();

    }

    /**
     * Récupérer le nombre des chantiers affectés ou non à un utilisateur
     *
     * @param idUser l'identifiant de l'utilisateur dans la base de données
     * @param affecte prend comme valeur 0 ou 1. si 0 la méthode renvoie le
     * nombre des chantiers affectés à l'utilisateur
     * @param start pour la pagination, c'est un nombre entier qui représente
     * l'index du premier élément à afficher
     * @param limit pour la pagination, c'est un nombre entier qui représente
     * l'index du dernier élément à afficher
     * @param code le nom du chantier
     * @param ville la ville du chantier
     *
     * @return le nombre des chantiers
     */
    public Object nombreChantiersUtilisateur(Integer idUser, int affecte, int start, int limit, String code, String ville, int[] dos) {
        logger.debug("liste des chantiers affectés ou non");

        Session session = sessionFactory.getCurrentSession();
        Query query = null;

        String queryRecherche = "";

        if (affecte == 1 && idUser != 0) {

            if (code != "") {
                queryRecherche += " AND LOWER(CH.LIB80) like '%" + StringEscapeUtils.escapeSql(code).toLowerCase() + "%' ";
            }

            if (ville != "") {
                queryRecherche += " AND LOWER(CH.UP_REGION) like '%" + StringEscapeUtils.escapeSql(ville).toLowerCase() + "%' ";
            }

            query = session.createSQLQuery("SELECT count(*) FROM PRJAP CH, CHANTIER_UTILISATEUR CU WHERE CH.PRJAP_ID=CU.CHANTIER_ID AND CU.UTILISATEUR_ID=?"
                    + queryRecherche);
            query.setParameter(0, idUser, StandardBasicTypes.INTEGER);

        } else if (affecte == 0 && idUser != 0) {

            query = session.createSQLQuery("SELECT CH.PRJAP_ID FROM PRJAP CH, CHANTIER_UTILISATEUR CU WHERE CH.PRJAP_ID=CU.CHANTIER_ID AND CU.UTILISATEUR_ID=?");
            query.setParameter(0, idUser, StandardBasicTypes.INTEGER);

            List listeObjetChantiersAffectes = query.list();
            List<Chantier> listeChantiersAffectes = new ArrayList<Chantier>();

            Chantier c = null;

            for (int i = 0; i < listeObjetChantiersAffectes.size(); i++) {

                c = new Chantier();
                c.setId(((Integer) listeObjetChantiersAffectes.get(i)));

                listeChantiersAffectes.add(c);
            }

            if (code != "") {
                queryRecherche += " and lower(c.code) like '%" + StringEscapeUtils.escapeSql(code).toLowerCase() + "%' ";
            }

            if (ville != "") {
                queryRecherche += " and lower(c.region) like '%" + StringEscapeUtils.escapeSql(ville).toLowerCase() + "%' ";
            }

            if (listeChantiersAffectes.size() != 0) {

                query = session.createQuery("SELECT count(*) FROM Chantier c where (c.dos=? OR c.dos=?) and c not in(:liste) " + queryRecherche);
                query.setParameter(0, dos[0], StandardBasicTypes.INTEGER);
                query.setParameter(1, dos[1], StandardBasicTypes.INTEGER);
                query.setParameterList("liste", listeChantiersAffectes);

            } else {

//				queryRecherche = queryRecherche.replaceFirst("and","where");
                query = session.createQuery("SELECT count(*) FROM Chantier c where (c.dos=? OR c.dos=?) " + queryRecherche);
                query.setParameter(0, dos[0], StandardBasicTypes.INTEGER);
                query.setParameter(1, dos[1], StandardBasicTypes.INTEGER);

            }

        }
        return query.uniqueResult();

    }

    /**
     * Récupérer la liste des chantiers d'un utilisateur
     *
     * @param idUser l'identifiant de l'utilisateur dans la base de données
     * @param affecte prend comme valeur 0 ou 1. si 0 la méthode renvoie le
     * nombre des chantiers affectés à l'utilisateur
     * @param start pour la pagination, c'est un nombre entier qui représente
     * l'index du premier élément à afficher
     * @param limit pour la pagination, c'est un nombre entier qui représente
     * l'index du dernier élément à afficher
     * @param code le nom du chantier
     * @param ville la ville du chantier
     *
     * @return la liste des Chantiers
     */
    public List<Chantier> chantiersUtilisateur(Integer idUser, int affecte, int start, int limit, String code, String ville, int[] dos) {
        logger.debug("liste des chantiers affectés ou non");

        Session session = sessionFactory.getCurrentSession();

        String queryRecherche = "";

        if (affecte == 1 && idUser != 0) {

            if (code != "") {
                queryRecherche += " AND LOWER(CH.LIB80) like '%" + StringEscapeUtils.escapeSql(code).toLowerCase() + "%' ";
            }

            if (ville != "") {
                queryRecherche += " AND LOWER(CH.UP_REGION) like '%" + StringEscapeUtils.escapeSql(ville).toLowerCase() + "%' ";
            }

            Query query = session.createSQLQuery("SELECT CH.PRJAP_ID,CAST(CH.LIB80 as VARCHAR(255)),CAST(CH.UP_REGION as VARCHAR(255)) FROM PRJAP CH, CHANTIER_UTILISATEUR CU WHERE CH.PRJAP_ID=CU.CHANTIER_ID AND CU.UTILISATEUR_ID=? " + queryRecherche + " ORDER BY CH.LIB80");
            query.setParameter(0, idUser, StandardBasicTypes.INTEGER);

            query.setFirstResult(start);
            query.setMaxResults(limit);

            List<Chantier> chantiersAffectesList = new ArrayList<Chantier>();
            List liste = query.list();
            Chantier chantier = null;

            for (int i = 0; i < liste.size(); i++) {

                Object[] o = (Object[]) query.list().get(i);
                chantier = new Chantier();
                chantier.setId(((Integer) o[0]));
                chantier.setCode((String) o[1]);
                chantier.setRegion((String) o[2]);
                chantiersAffectesList.add(chantier);
            }

            //				query.setParameter(0, id, Hibernate.INTEGER);
            return chantiersAffectesList;
        } else if (affecte == 0 && idUser != 0) {

            Query query = session.createSQLQuery("SELECT CH.PRJAP_ID FROM PRJAP CH, CHANTIER_UTILISATEUR CU WHERE CH.PRJAP_ID=CU.CHANTIER_ID AND CU.UTILISATEUR_ID=?");
            query.setParameter(0, idUser, StandardBasicTypes.INTEGER);

            List<Chantier> chantiersAffectesList = new ArrayList<Chantier>();
            List liste = query.list();
            Chantier chantier = null;

            for (int i = 0; i < liste.size(); i++) {

                chantier = new Chantier();
                chantier.setId(((Integer) liste.get(i)));

                chantiersAffectesList.add(chantier);
            }

            if (code != "") {
                queryRecherche += " and lower(c.code) like '%" + StringEscapeUtils.escapeSql(code).toLowerCase() + "%' ";
            }

            if (ville != "") {
                queryRecherche += " and lower(c.region) like '%" + StringEscapeUtils.escapeSql(ville).toLowerCase() + "%' ";
            }

            if (chantiersAffectesList.size() != 0) {

                query = session.createQuery("SELECT c.id, c.code, c.region FROM Chantier c where (c.dos=? OR c.dos=?) and c not in(:liste)" + queryRecherche + " order by c.code");
                query.setParameter(0, dos[0], StandardBasicTypes.INTEGER);
                query.setParameter(1, dos[1], StandardBasicTypes.INTEGER);
                query.setParameterList("liste", chantiersAffectesList);
                query.setFirstResult(start);
                query.setMaxResults(limit);

                List<Chantier> chantiersNonAffectesList = new ArrayList<Chantier>();
                //					 chantiersNonAffectesList=query.list();
                List listeObjetchantiersNonAffectes = query.list();
                Chantier c = null;

                for (int i = 0; i < listeObjetchantiersNonAffectes.size(); i++) {

                    Object[] o = (Object[]) listeObjetchantiersNonAffectes.get(i);
                    c = new Chantier();
                    c.setId(((Integer) o[0]));
                    c.setCode((String) o[1]);
                    c.setRegion((String) o[2]);
                    chantiersNonAffectesList.add(c);
                }

                return chantiersNonAffectesList;

            } else {

//				queryRecherche = queryRecherche.replaceFirst("and", "where");
                query = session.createQuery("SELECT c.id, c.code, c.region FROM Chantier c where (c.dos=? OR c.dos=?) " + queryRecherche + " order by c.code");
                query.setParameter(0, dos[0], StandardBasicTypes.INTEGER);
                query.setParameter(1, dos[1], StandardBasicTypes.INTEGER);
                //					 query=session.createSQLQuery("SELECT ID,CODE,VILLE,ADRESSE FROM CHANTIER CH"+queryRech+" order by CH.CODE");	
                query.setFirstResult(start);
                query.setMaxResults(limit);

                List<Chantier> chantiersNonAffectesList = new ArrayList<Chantier>();
                //					 chantiersNonAffectesList=query.list();
                List listeObjetchantiersNonAffectes = query.list();
                Chantier c = null;

                for (int i = 0; i < listeObjetchantiersNonAffectes.size(); i++) {

                    Object[] o = (Object[]) listeObjetchantiersNonAffectes.get(i);
                    c = new Chantier();
                    c.setId(((Integer) o[0]));
                    c.setCode((String) o[1]);
                    c.setRegion((String) o[2]);
                    chantiersNonAffectesList.add(c);
                }

                return chantiersNonAffectesList;

            }

        }

        return new ArrayList<Chantier>();

    }

    /**
     * Affecter un utilisateur à un chantier
     *
     * @param utilisateur instence de l'objet Utilisateur. il doit être récupéré
     * à partir de la base de données
     * @param chantier instance de l'objet de Chantier. il ddoit être récupéré à
     * partir de la base de données
     */
    public void affecterUserChantier(Utilisateur utilisateur, Chantier chantier) {
        logger.debug("affecter un utilisateur à un chantier");

        Session session = sessionFactory.getCurrentSession();

        chantier.getUtilisateurs().add(utilisateur);

        session.update(chantier);

    }

    /**
     * Affecter un utilisateur à tous les chantiers
     *
     * @param utilisateurID l'identifiant de l'utilisateur dans la base de
     * données
     */
    public void affecterUserTousChantiers(Integer utilisateurID) {
        logger.debug("affecter un utilisateur à tous les chantiers");

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createSQLQuery("insert into [Bpaie].[dbo].[CHANTIER_UTILISATEUR]([CHANTIER_ID],[UTILISATEUR_ID])"
                + "SELECT p.PRJAP_ID," + utilisateurID + " from [Bpaie].[dbo].[PRJAP] as p where p.DOS=700 ");
        query.executeUpdate();

    }

    /**
     * Désaffecter un utilisateur d'un chantier
     *
     * @param utilisateur instence de l'objet Utilisateur. il doit être récupéré
     * à partir de la base de données
     * @param chantier instance de l'objet de Chantier. il ddoit être récupéré à
     * partir de la base de données
     */
    public void desaffecterUserChantier(Utilisateur utilisateur, Chantier chantier) {
        logger.debug("désaffecter un utilisateur d'un chantier");

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createSQLQuery("DELETE FROM CHANTIER_UTILISATEUR WHERE CHANTIER_ID = ? AND UTILISATEUR_ID=?");
        query.setParameter(0, chantier.getId(), StandardBasicTypes.INTEGER);
        query.setParameter(1, utilisateur.getId(), StandardBasicTypes.INTEGER);
        query.executeUpdate();

    }

    //Pour la modification d'un chantier
    /**
     * Récupérer un chantier à partir de la base de données
     *
     * @param idChantier l'identifiant du chantier dans la base de données
     * @return Chantier
     */
    public Chantier getChantierById(Integer idChantier) {
        logger.debug("Récupération d'un chantier par id");

        Session session = sessionFactory.getCurrentSession();
//        try {
//
//            session.connection().setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
//
//        } catch (HibernateException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (SQLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }

        Query query = session.createQuery("SELECT ch.id, ch.code, ch.region, ch.heureEntree, ch.heureSortie, ch.nombreHeures, ch.nombreMinutes FROM Chantier ch WHERE ch.id = ? ");
        query.setParameter(0, idChantier, StandardBasicTypes.INTEGER);

        Object[] o = (Object[]) query.uniqueResult();

        Chantier chantier = new Chantier();

        chantier.setId(((Integer) o[0]));
        chantier.setCode((String) o[1]);
        chantier.setRegion((String) o[2]);
        chantier.setHeureEntree((String) o[3]);
        chantier.setHeureSortie((String) o[4]);
        chantier.setNombreHeures((Integer) o[5]);
        chantier.setNombreMinutes((Integer) o[6]);

        //		Query query=session.createQuery("SELECT ch.id, ch.code, ch.ville, ch.adresse, ch.heureEntree, ch.heureSortie, ch.nombreHeures, ch.nombreMinutes FROM Chantier ch WHERE ch.id = ? ");
        //		query.setParameter(0, idChantier, Hibernate.INTEGER);
        //				
        //		
        //		 List listeObjetsChantier = query.list();
        //		 
        //		 Object[] objetArray = (Object[]) query.uniqueResult();
        //		 Object objet = query.uniqueResult();
        //		 
        //		 System.out.println(objetArray[0]);
        //		 System.out.println(objet);
        //		 
        //			Chantier chantier =  new Chantier();
        //		   		    
        //		    for(int i=0;i<listeObjetsChantier.size(); i++){
        //		    	
        //		    	Object[] o=(Object[]) listeObjetsChantier.get(i);
        //		    	
        //		    	chantier.setId(((Integer)o[0]));
        //		    	chantier.setCode((String) o[1]);
        //		    	chantier.setVille((String) o[2]);
        //		    	chantier.setAdresse((String) o[3]);
        //		    	chantier.setHeureEntree((String) o[4]);
        //		    	chantier.setHeureSortie((String) o[5]);
        //		    	chantier.setNombreHeures((Integer) o[6]);
        //		    	chantier.setNombreMinutes((Integer) o[7]);
        //		
        //		    }
        //				
        return chantier;

    }

    /**
     * Modifier un chantier dans la base de données
     *
     * @param chantier instance de l'objet Chantiers qui doit être récupéré de
     * la base de données
     */
    public void modifierChantier(Chantier chantier) {
        logger.debug("Modifier un chantier");

        Session session = sessionFactory.getCurrentSession();

        session.update(chantier);

    }

    /**
     * Récupérer un chantier à partir de la base de données via son code
     *
     * @param codeChantier
     * @return chantier
     */
    public Chantier getChantier(String codeChantier) {
        logger.debug("Chantier par code");

        Session session = sessionFactory.getCurrentSession();
//        try {
//
//            session.connection().setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
//
//        } catch (HibernateException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (SQLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }

        String dos = "";
        dos = Constantes.getInstance().numeroDossierDivalto;

        Query query = session.createQuery("SELECT ch.id, ch.code, ch.region, ch.heureEntree, ch.heureSortie, ch.nombreHeures, ch.nombreMinutes FROM Chantier ch WHERE ch.codeNovapaie=? and ch.dos=?");

        query.setParameter(0, codeChantier, StandardBasicTypes.STRING);
        query.setParameter(1, dos, StandardBasicTypes.STRING);
//		Chantier chantier= (Chantier)query.uniqueResult();

        Object[] o = (Object[]) query.uniqueResult();

        Chantier chantier = new Chantier();

        chantier.setId(((Integer) o[0]));
        chantier.setCode((String) o[1]);
        chantier.setRegion((String) o[2]);
        chantier.setHeureEntree((String) o[3]);
        chantier.setHeureSortie((String) o[4]);
        chantier.setNombreHeures((Integer) o[5]);
        chantier.setNombreMinutes((Integer) o[6]);

        return chantier;

    }

    /**
     * Vérifier si un utilisateur est affecté à un chantier dont l'id est passé
     * en paramètres. la vérification se fait sur la table CHANTIER_UTILISATEUR
     *
     * @param idChantier l'identifiant du chantier dans la base de données
     * @param idUser l'identifiant de l'utilsateur dans la base de données
     * @return
     */
    public boolean verifierAffectationUserChantier(Integer idChantier, Integer idUser) {
        logger.debug("Vérifier l'affectation d'un utilisateur à un chantier");

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createSQLQuery("SELECT count(*) FROM CHANTIER_UTILISATEUR WHERE CHANTIER_ID = ? AND UTILISATEUR_ID=?");
        query.setParameter(0, idChantier, StandardBasicTypes.INTEGER);
        query.setParameter(1, idUser, StandardBasicTypes.INTEGER);

        Object nombre = query.uniqueResult();

        System.out.println(idChantier + " " + idUser + " " + nombre);
        if (((Integer) nombre).intValue() != 0) {
            return true;
        }

        return false;
    }

    /**
     * Vérifier si le salarié est affecté au chantier de l'utilisateur connecté
     * cette méthode est utile pour contôler la modiifaction de fiche salarié
     *
     * @param idSalarie l'identifiant de l'utilisateur dans la base de données
     * @param idUser l'identifiant de l'utilisateur dans la base de données
     * @return true si le salarié est effecté à l'un des chantiers de
     * l'utilisateur
     */
    public boolean verifierAffectation(Integer idSalarie, Integer idUser) {
        logger.debug("Vérifier l'affectation du salarie aux chantier de l'utilisateur");

        Session session = sessionFactory.getCurrentSession();

        Query queryChantiersUser = session.createSQLQuery("SELECT CHANTIER_ID FROM CHANTIER_UTILISATEUR WHERE UTILISATEUR_ID=?");
        queryChantiersUser.setParameter(0, idUser, StandardBasicTypes.INTEGER);
        List<Integer> listeChantiersUser = queryChantiersUser.list();

        Query queryChantiersSalarie = session.createSQLQuery("SELECT CHANTIER_ID FROM CHANTIER_SALARIE WHERE SALARIE_ID=?");
        queryChantiersSalarie.setParameter(0, idSalarie, StandardBasicTypes.INTEGER);
        List<Integer> listeChantiersSalarie = queryChantiersSalarie.list();

        if (listeChantiersUser.size() > 0 && listeChantiersSalarie.size() > 0) {
            for (int i = 0; i < listeChantiersUser.size(); i++) {
                if (listeChantiersSalarie.contains(listeChantiersUser.get(i))) {
                    return true;
                }
            }
            return false;
        } else {
            return false;
        }
    }
public List<Chantier> listeChantiers(){
     List<Chantier> l = new ArrayList<Chantier>();
     try { 
        Session session = sessionFactory.getCurrentSession();
        Query queryChantiers = session.createQuery("SELECT c FROM Chantier c ");
        l=queryChantiers.list();
    } catch (HibernateException e) {
        logger.error("Erreur de recuperation de la liste des chantiers car "+e.getMessage());
    }
     return l;
}


/***
 A modifier
 
 */

    public List<Chantier> ateliersList(int start, int limit, String nom, String ville, int[] dos,String ate) {

        //logger.debug("liste des chantiers");

        Session session = sessionFactory.getCurrentSession();
        String queryRecherche = "";

        if (nom != "") {
            queryRecherche += " AND lower(code) like '%" + StringEscapeUtils.escapeSql(nom).toLowerCase() + "%' ";
        }
        if (ville != "") {
            queryRecherche += " AND lower(region) like '%" + StringEscapeUtils.escapeSql(ville).toLowerCase() + "%' ";
        }

//		queryRecherche = queryRecherche.replaceFirst("AND", " WHERE");
       //ate = "%atelier%";
        //System.out.println("SELECT id,code,region, heureEntree, heureSortie, nombreHeures, nombreMinutes FROM Chantier where (code like :ate) and (dos=? OR dos=?) " + queryRecherche + " order by code");
        Query query = session.createQuery("SELECT id,code,region, heureEntree, heureSortie, nombreHeures, nombreMinutes FROM Chantier  where lower(code) like '%" + StringEscapeUtils.escapeSql(ate).toLowerCase() + "%' and (dos=? OR dos=?) " + queryRecherche + " order by code");
        query.setParameter(0, dos[0], StandardBasicTypes.INTEGER);
        query.setParameter(1, dos[1], StandardBasicTypes.INTEGER);
        //query.setParameter("ate", ate);
        query.setFirstResult(start);
        query.setMaxResults(limit);

        List liste = query.list();

        List<Chantier> listeChantiers = new ArrayList<Chantier>();
        Chantier chantier = null;

        for (int i = 0; i < liste.size(); i++) {

            Object[] o = (Object[]) liste.get(i);
            chantier = new Chantier();
            chantier.setId((Integer) o[0]);
            chantier.setCode((String) o[1]);
            chantier.setRegion((String) o[2]);
//			chantier.setAdresse((String) o[3]);
            chantier.setHeureEntree((String) o[3]);
            chantier.setHeureSortie((String) o[4]);
            chantier.setNombreHeures((Integer) o[5]);
            chantier.setNombreMinutes((Integer) o[6]);

            listeChantiers.add(chantier);
        }

        return listeChantiers;

    }




}
