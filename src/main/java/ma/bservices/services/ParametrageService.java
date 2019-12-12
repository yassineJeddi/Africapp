package ma.bservices.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import ma.bservices.beans.Article;
import ma.bservices.beans.CategorieOutilTravail;
import ma.bservices.beans.Civilite;
import ma.bservices.beans.CouleurCasque;
import ma.bservices.beans.CouleurGilet;
import ma.bservices.beans.Duree;
import ma.bservices.beans.Etat;
import ma.bservices.beans.EtatContrat;
import ma.bservices.beans.EtatDA;
import ma.bservices.beans.EtatHeuresSupplementaires;
import ma.bservices.beans.Fonction;
import ma.bservices.beans.ModelContrat;
import ma.bservices.beans.Motif;
import ma.bservices.beans.Nationalite;
import ma.bservices.beans.Pays;
import ma.bservices.beans.SituationFamiliale;
import ma.bservices.beans.Statut;
import ma.bservices.beans.ModePaiement;
import ma.bservices.beans.PeriodeEssai;
import ma.bservices.beans.Pointure;
import ma.bservices.beans.Preavis;
import ma.bservices.beans.TailleHabillement;
import ma.bservices.beans.Type;
import ma.bservices.beans.TypeAbsence;
import ma.bservices.beans.TypeContrat;
import ma.bservices.beans.TypeDocument;
import ma.bservices.constantes.Constantes;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
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
@Service("parametrageService")
@Transactional
public class ParametrageService {

    protected static Logger logger = Logger.getLogger("service");
    @Resource(name = "sessionFactory")
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    // ----- PARAMETRAGE RESSSOURCES HUMAINES ----- 
    /**
     * ***************************************************
     */
    /**
     * **************** Statut Salarie *****************
     */
    /**
     * ***************************************************
     */
    /**
     * Récupérer le nombre des statuts qui existent
     *
     * @param querySearch
     * @return Object
     */
    public Object nombreStatuts(String querySearch) {

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("SELECT count(*) FROM Statut WHERE statut LIKE '%" + StringEscapeUtils.escapeSql(querySearch) + "%'");

        return query.uniqueResult();

    }

    /**
     * Récupérer la liste des Statuts qui existent
     *
     * @param start
     * @param limit
     * @param querySearch
     *
     * @return Statuts
     */
    public List<Statut> listeStatuts(int start, int limit, String querySearch) {
        logger.debug("liste des statuts");

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM Statut WHERE statut LIKE '%" + StringEscapeUtils.escapeSql(querySearch) + "%' order by statut");
        query.setFirstResult(start);
        query.setMaxResults(limit);

        return query.list();

    }

    /**
     * Récupérer un Statut par id
     *
     * @param idStatut
     *
     * @return Statut
     */
    public Statut getStatut(Integer idStatut) {
        logger.debug("Statut par id");

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM Statut WHERE id=?");

        query.setParameter(0, idStatut, StandardBasicTypes.INTEGER);
        Statut statut = (Statut) query.uniqueResult();

        return statut;

    }

    /**
     * Récupérer un Statut par nom
     *
     * @param nomStatut
     * @return Statut
     */
    public Statut getStatut(String nomStatut) {
        logger.debug("Statut par nom");

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM Statut WHERE statut like '" + nomStatut + "%'");

        //query.setParameter(0, nomStatut, Hibernate.STRING);
        Statut statut = (Statut) query.uniqueResult();

        return statut;

    }

    /**
     * ***************************************************
     */
    /**
     * **************** Fonction Salarie *****************
     */
    /**
     * ***************************************************
     */
    /**
     * Récupérer le nombre des fonctions qui existent
     *
     * @param querySearch
     * @return Object
     */
    public Object nombreFonctions(String querySearch) {

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("SELECT count(*) FROM Fonction WHERE fonction LIKE '%" + StringEscapeUtils.escapeSql(querySearch) + "%'");

        return query.uniqueResult();

    }

    /**
     * Récupérer la liste des Fonctions qui existent
     */
    public List<Fonction> listeAllFonctions(){
        List<Fonction> l = new ArrayList<Fonction> ();
        try {
            Session session = sessionFactory.getCurrentSession();
            Query query = session.createQuery("FROM Fonction order by fonction");
             l = query.list();
        } catch (Exception e) {
            System.out.println("Erreur de récupération liste des fonction car "+e.getMessage());
        }

        return l;
        
    }
    /**
     * Récupérer la liste des Fonctions qui existent
     *
     * @param start
     * @param limit
     * @param querySearch
     *
     * @return Fonctions
     */
    public List<Fonction> listeFonctions(int start, int limit, String querySearch) {
        logger.debug("liste des fonctions");
        List<Fonction> l = new ArrayList<Fonction> ();
        try {
            Session session = sessionFactory.getCurrentSession();
        System.out.println("FROM Fonction WHERE fonction LIKE '%" + StringEscapeUtils.escapeSql(querySearch) + "%' order by fonction");
        Query query = session.createQuery("FROM Fonction WHERE fonction LIKE '%" + StringEscapeUtils.escapeSql(querySearch) + "%' order by fonction");

        query.setFirstResult(start);
        query.setMaxResults(limit);

        l = query.list();
        } catch (Exception e) {
            System.out.println("Erreur de récupération liste des fonction car "+e.getMessage());
        }

        return l;

    }

    /**
     * ************************ Liste des fonctions selon le statut
     * **************************
     */
    /**
     * Récupérer le nombre des fonctions par statut
     *
     * @param querySearch
     * @param idStatut
     *
     * @return Object
     */
    public Object nombreFonctionsParStatut(String querySearch, Integer idStatut) {

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("SELECT count(*) FROM Fonction WHERE fonction LIKE '%" + StringEscapeUtils.escapeSql(querySearch) + "%' AND statut.id=?");
        query.setParameter(0, idStatut, StandardBasicTypes.INTEGER);

        return query.uniqueResult();

    }

    /**
     * Récupérer la liste des fonctions par statut
     *
     * @param start
     * @param limit
     * @param querySearch
     * @param idStatut
     *
     * @return Fonctions
     */
    public List<Fonction> listeFonctionsParStatut(int start, int limit, String querySearch, Integer idStatut) {
        logger.debug("liste des fonctions selon l'id du statut");

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM Fonction WHERE fonction LIKE '%" + StringEscapeUtils.escapeSql(querySearch) + "%' AND statut.id=? order by fonction");
        query.setParameter(0, idStatut, StandardBasicTypes.INTEGER);

        query.setFirstResult(start);
        query.setMaxResults(limit);

        return query.list();

    }

    /**
     * Récupérer le nombre des fonctions par Statut
     *
     * @param querySearch
     * @param statut
     *
     * @return Object
     */
    public Object nombreFonctionsParStatut(String querySearch, String statut, String dos, int nbreCaracteres) {

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("SELECT count(*) FROM Fonction WHERE " + dos + " AND length(codeDiva)=" + nbreCaracteres + " AND  fonction LIKE '%" + StringEscapeUtils.escapeSql(querySearch) + "%' AND codeDiva like '" + statut + "%'");
        //query.setParameter(0, statut, Hibernate.STRING);

        return query.uniqueResult();

    }

    /**
     * Récupérer la liste des fonctions par statuts
     *
     * @param start
     * @param limit
     * @param querySearch
     * @param statut
     *
     * @return Fonctions
     */
    public List<Fonction> listeFonctionsParStatut(int start, int limit, String querySearch, String statut, String dos, int nbreCaracteres) {
        logger.debug("liste des fonctions selon le nom du statut");

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM Fonction WHERE " + dos + "  AND length(codeDiva)=" + nbreCaracteres + " AND  fonction LIKE '%" + StringEscapeUtils.escapeSql(querySearch) + "%' AND codeDiva like '" + statut + "%' order by fonction");
        //query.setParameter(0, statut, Hibernate.STRING);

        query.setFirstResult(start);
        query.setMaxResults(limit);

        return query.list();

    }

    /**
     * *****************************************************************************
     */
    /**
     * Récupérer une Fonction
     *
     * @param idFonction
     *
     * @return Fonction
     */
    public Fonction getFonction(Integer idFonction) {
        logger.debug("Fonction par id");

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM Fonction WHERE id=?");

        query.setParameter(0, idFonction, StandardBasicTypes.INTEGER);
        Fonction fonction = (Fonction) query.uniqueResult();
        return fonction;

    }

    /**
     * Récupérer une Fonction via Nom
     *
     * @param nomFonction
     *
     * @return Fonction
     */
    public Fonction getFonction(String nomFonction) {
        logger.debug("Fonction par nom");

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM Fonction WHERE fonction=? AND length(codeDiva)=3");

        query.setParameter(0, nomFonction, StandardBasicTypes.STRING);
        Fonction fonction = (Fonction) query.uniqueResult();

        return fonction;

    }

    /**
     * ***************************************************
     */
    /**
     * **************** Civilite Salarie *****************
     */
    /**
     * ***************************************************
     */
    /**
     * Récupérer le nombre des Civilités qui existent
     *
     * @param querySearch
     *
     * @return Object
     */
    public Object nombreCivilites(String querySearch) {

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("SELECT count(*) FROM Civilite WHERE civilite LIKE '%" + StringEscapeUtils.escapeSql(querySearch) + "%'");

        return query.uniqueResult();

    }

    /**
     * Récupérer la liste des civilités
     *
     * @param start
     * @param limit
     * @param querySearch
     *
     * @return Civilités
     */
    public List<Civilite> listeCivilites(int start, int limit, String querySearch) {
        logger.debug("liste des civilites");

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM Civilite WHERE civilite LIKE '%" + StringEscapeUtils.escapeSql(querySearch) + "%' order by civilite");
        query.setFirstResult(start);
        query.setMaxResults(limit);

        return query.list();

    }

    /**
     * Récupérer une Civilité par id
     *
     * @param idCivilite
     * @return Civilité
     */
    public Civilite getCivilite(Integer idCivilite) {
        logger.debug("Civilite par id");

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM Civilite WHERE id=?");

        query.setParameter(0, idCivilite, StandardBasicTypes.INTEGER);
        Civilite civilite = (Civilite) query.uniqueResult();

        return civilite;

    }

    /**
     * Récupérer une civilité par nom
     *
     * @param nomCivilite
     *
     * @return Civilité
     */
    public Civilite getCivilite(String nomCivilite) {
        logger.debug("Civilite par nom");

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM Civilite WHERE civilite=?");

        query.setParameter(0, nomCivilite, StandardBasicTypes.STRING);
        Civilite civilite = (Civilite) query.uniqueResult();

        return civilite;

    }

    /**
     * ***************************************************
     */
    /**
     * **************** Nationalite Salarie *****************
     */
    /**
     * ***************************************************
     */
    /**
     * Récupérer le nombre des Nationalités
     *
     * @param querySearch
     *
     * @return Object
     */
    public Object nombreNationalites(String querySearch) {

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("SELECT count(*) FROM Nationalite WHERE nationalite LIKE '%" + StringEscapeUtils.escapeSql(querySearch) + "%'");

        return query.uniqueResult();

    }

    /**
     * Récupérer la liste des nationalités
     *
     * @param start
     * @param limit
     * @param querySearch
     *
     * @return Nationalités
     */
    public List<Nationalite> listeNationalites(int start, int limit, String querySearch) {
        logger.debug("liste des nationalites");

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM Nationalite WHERE nationalite LIKE '%" + StringEscapeUtils.escapeSql(querySearch) + "%' order by nationalite");
        query.setFirstResult(start);
        query.setMaxResults(limit);

        return query.list();

    }

    /**
     * Récupérer une Nationalité par id
     *
     * @param idNationalite
     *
     * @return Nationalité
     */
    public Nationalite getNationalite(Integer idNationalite) {
        logger.debug("Nationalite par id");

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM Nationalite WHERE id=?");

        query.setParameter(0, idNationalite, StandardBasicTypes.INTEGER);
        Nationalite nationalite = (Nationalite) query.uniqueResult();

        return nationalite;

    }

    /**
     * Récupérer une Nationalté par Nom
     *
     * @param nomNationalite
     * @return Nationalté
     */
    public Nationalite getNationalite(String nomNationalite) {
        logger.debug("Nationalite par nom");

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM Nationalite WHERE nationalite=?");

        query.setParameter(0, nomNationalite, StandardBasicTypes.STRING);
        Nationalite nationalite = (Nationalite) query.uniqueResult();

        return nationalite;

    }

    /**
     * ***************************************************
     */
    /**
     * **************** Pays Salarie *****************
     */
    /**
     * ***************************************************
     */
    /**
     * Récupérer le nombre des pays
     *
     * @param querySearch
     * @return Object
     */
    public Object nombrePays(String querySearch) {

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("SELECT count(*) FROM Pays WHERE pays LIKE '%" + StringEscapeUtils.escapeSql(querySearch) + "%'");

        return query.uniqueResult();

    }

    /**
     * Récupérer la liste des Pays
     *
     * @param start
     * @param limit
     * @param querySearch
     *
     * @return Pays
     */
    public List<Pays> listePays(int start, int limit, String querySearch) {
        logger.debug("liste des pays");

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM Pays WHERE pays LIKE '%" + StringEscapeUtils.escapeSql(querySearch) + "%' order by pays");
        query.setFirstResult(start);
        query.setMaxResults(limit);

        return query.list();

    }

    /**
     * Récupérer un pays
     *
     * @param idPays
     * @return Pays
     */
    public Pays getPays(Integer idPays) {
        logger.debug("Pays par id");

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM Pays WHERE id=?");

        query.setParameter(0, idPays, StandardBasicTypes.INTEGER);
        Pays pays = (Pays) query.uniqueResult();

        return pays;

    }

    /**
     * Récupérer un Pays par Nom
     *
     * @param nomPays
     *
     * @return Pays
     */
    public Pays getPays(String nomPays) {
        logger.debug("Pays par nom");

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM Pays WHERE pays=?");

        query.setParameter(0, nomPays, StandardBasicTypes.STRING);
        Pays pays = (Pays) query.uniqueResult();

        return pays;

    }

    /**
     * ***************************************************
     */
    /**
     * **************** Situation familiale Salarie *****************
     */
    /**
     * ***************************************************
     */
    /**
     * Récupérer le nombre des situations Familiale
     *
     * @param querySearch
     * @return
     */
    public Object nombreSituationsFamiliale(String querySearch) {

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("SELECT count(*) FROM SituationFamiliale WHERE situationFamiliale LIKE '%" + StringEscapeUtils.escapeSql(querySearch) + "%'");

        return query.uniqueResult();

    }

    /**
     * Récupérer la liste des situations familiales
     *
     * @param start
     * @param limit
     * @param querySearch
     *
     * @return Situations Familales
     */
    public List<SituationFamiliale> listeSituationsFamiliale(int start, int limit, String querySearch) {
        logger.debug("liste des situations familiale");

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM SituationFamiliale WHERE situationFamiliale LIKE '%" + StringEscapeUtils.escapeSql(querySearch) + "%' order by situationFamiliale");
        query.setFirstResult(start);
        query.setMaxResults(limit);

        return query.list();

    }

    /**
     * Récupérer une situation Familiale par id
     *
     * @param idSituationFamiliale
     *
     * @return Situation Familiale
     */
    public SituationFamiliale getSituationFamiliale(Integer idSituationFamiliale) {
        logger.debug("SituationFamiliale par id");

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM SituationFamiliale WHERE id=?");

        query.setParameter(0, idSituationFamiliale, StandardBasicTypes.INTEGER);
        SituationFamiliale situationFamiliale = (SituationFamiliale) query.uniqueResult();

        return situationFamiliale;

    }

    /**
     * Récupérer une Situation Familiale par nom
     *
     * @param nomSituationFamiliale
     *
     * @return Situation Familiale
     */
    public SituationFamiliale getSituationFamiliale(String nomSituationFamiliale) {
        logger.debug("SituationFamiliale par nom");

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM SituationFamiliale WHERE situationFamiliale=?");

        query.setParameter(0, nomSituationFamiliale, StandardBasicTypes.STRING);
        SituationFamiliale situationFamiliale = (SituationFamiliale) query.uniqueResult();

        return situationFamiliale;

    }

    /**
     * ***************************************************
     */
    /**
     * **************** Type Salarie *****************
     */
    /**
     * ***************************************************
     */
    /**
     * Récupérer le nombre des Types
     *
     * @param querySearch
     *
     * @return Object
     */
    public Object nombreTypes(String querySearch) {

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("SELECT count(*) FROM Type WHERE type LIKE '%" + StringEscapeUtils.escapeSql(querySearch) + "%'");

        return query.uniqueResult();

    }

    /**
     * Récupérer la liste des Types
     *
     * @param start
     * @param limit
     * @param querySearch
     *
     * @return Types
     */
    public List<Type> listeTypes(int start, int limit, String querySearch) {
        logger.debug("liste des types");

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM Type WHERE type LIKE '%" + StringEscapeUtils.escapeSql(querySearch) + "%' order by type");
        query.setFirstResult(start);
        query.setMaxResults(limit);

        return query.list();

    }

    /**
     * Récupérer un Type par id
     *
     * @param idType
     *
     * @return Type
     */
    public Type getType(Integer idType) {
        logger.debug("Type par id");

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM Type WHERE id=?");

        query.setParameter(0, idType, StandardBasicTypes.INTEGER);
        Type type = (Type) query.uniqueResult();

        return type;

    }

    /**
     * Récupérer un type par nom
     *
     * @param nomType
     *
     * @return Type
     */
    public Type getType(String nomType) {
        logger.debug("Type par nom");

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM Type WHERE type=?");

        query.setParameter(0, nomType, StandardBasicTypes.STRING);
        Type type = (Type) query.uniqueResult();

        return type;

    }

    /**
     * ***************************************************
     */
    /**
     * **************** Etat Salarie *****************
     */
    /**
     * ***************************************************
     */
    /**
     * Récupérer le nombre des états
     *
     * @param querySearch
     *
     * @return Object
     */
    public Object nombreEtats(String querySearch) {

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("SELECT count(*) FROM Etat WHERE etat LIKE '%" + StringEscapeUtils.escapeSql(querySearch) + "%'");

        return query.uniqueResult();

    }

    /**
     * Récupérer la liste des états
     *
     * @param start
     * @param limit
     * @param querySearch
     *
     * @return états
     */
    public List<Etat> listeEtats(int start, int limit, String querySearch) {
        logger.debug("liste des etats");

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM Etat WHERE etat LIKE '%" + StringEscapeUtils.escapeSql(querySearch) + "%' order by etat");
        query.setFirstResult(start);
        query.setMaxResults(limit);

        return query.list();

    }
    public List<Etat> listeAllEtats() {
        List<Etat> l = new ArrayList<Etat>();
        try {
            Session session = sessionFactory.getCurrentSession();
            Query query = session.createQuery("FROM Etat order by etat");
            l= query.list();
        } catch (Exception e) {
            logger.debug("Erreur de recuperation liste des etats car : "+e.getMessage());
        }
        return l;
    }

    /**
     * Récupérer un état par id
     *
     * @param idEtat
     *
     * @return Etat
     */
    public Etat getEtat(Integer idEtat) {
        logger.debug("Etat par id");

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM Etat WHERE id=?");
        query.setParameter(0, idEtat, StandardBasicTypes.INTEGER);

        Etat etat = (Etat) query.uniqueResult();

        return etat;

    }

    /**
     * Récupérer un état par nom
     *
     * @param nomEtat
     *
     * @return Etat
     */
    public Etat getEtat(String nomEtat) {
        logger.debug("Etat par nom");

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM Etat WHERE etat=?");
        query.setParameter(0, nomEtat, StandardBasicTypes.STRING);

        Etat etat = (Etat) query.uniqueResult();
        System.out.println("etat : " + etat);
        return etat;

    }

    /**
     * ***************************************************
     */
    /**
     * **************** Pointure Salarie *****************
     */
    /**
     * ***************************************************
     */
    /**
     * Récupérer le nombre des pointures
     *
     * @param querySearch
     *
     * @return Object
     */
    public Object nombrePointures(String querySearch) {

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("SELECT count(*) FROM Pointure WHERE pointure LIKE '%" + StringEscapeUtils.escapeSql(querySearch) + "%'");

        return query.uniqueResult();

    }

    /**
     * Récupérer la liste des pointures
     *
     * @param start
     * @param limit
     * @param querySearch
     *
     * @return Pointures
     */
    public List<Pointure> listePointures(int start, int limit, String querySearch) {
        logger.debug("liste des pointures");

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM Pointure WHERE pointure LIKE '%" + StringEscapeUtils.escapeSql(querySearch) + "%' order by pointure");
        query.setFirstResult(start);
        query.setMaxResults(limit);

        return query.list();

    }

    /**
     * Récupérer une pointure par id
     *
     * @param idPointure
     *
     * @return Pointure
     */
    public Pointure getPointure(Integer idPointure) {
        logger.debug("Pointure par id");

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM Pointure WHERE id=?");

        query.setParameter(0, idPointure, StandardBasicTypes.INTEGER);
        Pointure pointure = (Pointure) query.uniqueResult();

        return pointure;

    }

    /**
     * Récupérer une Pointure
     *
     * @param nomPointure
     *
     * @return Pointure
     */
    public Pointure getPointure(String nomPointure) {
        logger.debug("Pointure par nom");

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM Pointure WHERE pointure=?");

        query.setParameter(0, nomPointure, StandardBasicTypes.STRING);
        Pointure pointure = (Pointure) query.uniqueResult();

        return pointure;

    }

    /**
     * ***************************************************
     */
    /**
     * **************** TailleHabillement Salarie *********
     */
    /**
     * ***************************************************
     */
    /**
     * Récupérer le nombre des Tailles d'habillement
     *
     * @param querySearch
     * @return Object
     */
    public Object nombreTailleHabillements(String querySearch) {

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("SELECT count(*) FROM TailleHabillement WHERE tailleHabillement LIKE '%" + StringEscapeUtils.escapeSql(querySearch) + "%'");

        return query.uniqueResult();

    }

    /**
     * Récupérer la liste des tailles d'habillement
     *
     * @param start
     * @param limit
     * @param querySearch
     *
     * @return Tailles d'habillement
     */
    public List<TailleHabillement> listeTailleHabillements(int start, int limit, String querySearch) {
        logger.debug("liste des TailleHabillements");

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM TailleHabillement WHERE tailleHabillement LIKE '%" + StringEscapeUtils.escapeSql(querySearch) + "%' order by tailleHabillement");
        query.setFirstResult(start);
        query.setMaxResults(limit);

        return query.list();

    }

    /**
     * Récupérer une Taille d'habillement par id
     *
     * @param idTailleHabillement
     *
     * @return Taille d'habillement
     */
    public TailleHabillement getTailleHabillement(Integer idTailleHabillement) {
        logger.debug("TailleHabillement par id");

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM TailleHabillement WHERE id=?");

        query.setParameter(0, idTailleHabillement, StandardBasicTypes.INTEGER);
        TailleHabillement tailleHabillement = (TailleHabillement) query.uniqueResult();

        return tailleHabillement;

    }

    /**
     * Récupérer une Taille d'habillement par nom
     *
     * @param nomTailleHabillement
     *
     * @return Taille d'habillement
     */
    public TailleHabillement getTailleHabillement(String nomTailleHabillement) {
        logger.debug("TailleHabillement par nom");

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM TailleHabillement WHERE tailleHabillement=?");

        query.setParameter(0, nomTailleHabillement, StandardBasicTypes.STRING);
        TailleHabillement tailleHabillement = (TailleHabillement) query.uniqueResult();

        return tailleHabillement;

    }

    /**
     * ***************************************************
     */
    /**
     * **************** CouleurGilet Salarie *****************
     */
    /**
     * ***************************************************
     */
    /**
     * Récupérer le nombre des couleurs de Gilets
     *
     * @param querySearch
     * @return Object
     */
    public Object nombreCouleurGilets(String querySearch) {

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("SELECT count(*) FROM CouleurGilet WHERE couleurGilet LIKE '%" + StringEscapeUtils.escapeSql(querySearch) + "%'");

        return query.uniqueResult();

    }

    /**
     * Récupérer la liste des couleurs des gilets
     *
     * @param start
     * @param limit
     * @param querySearch
     * @return Couleurs de Gilet
     */
    public List<CouleurGilet> listeCouleurGilets(int start, int limit, String querySearch) {
        logger.debug("liste des TaillesHabillement");

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM CouleurGilet WHERE couleurGilet LIKE '%" + StringEscapeUtils.escapeSql(querySearch) + "%' order by couleurGilet");
        query.setFirstResult(start);
        query.setMaxResults(limit);

        return query.list();

    }

    /**
     * Récupérer un Couleur de Gilet par id
     *
     * @param idCouleurGilet
     *
     * @return Couleur de Gilet
     */
    public CouleurGilet getCouleurGilet(Integer idCouleurGilet) {
        logger.debug("CouleurGilet par id");

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM CouleurGilet WHERE id=?");

        query.setParameter(0, idCouleurGilet, StandardBasicTypes.INTEGER);
        CouleurGilet couleurGilet = (CouleurGilet) query.uniqueResult();

        return couleurGilet;

    }

    /**
     * Récupérer la couleur de gilet par nom
     *
     * @param nomCouleurGilet
     * @return Couleur de Gilet
     */
    public CouleurGilet getCouleurGilet(String nomCouleurGilet) {
        logger.debug("CouleurGilet par nom");

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM CouleurGilet WHERE couleurGilet=?");

        query.setParameter(0, nomCouleurGilet, StandardBasicTypes.STRING);
        CouleurGilet couleurGilet = (CouleurGilet) query.uniqueResult();

        return couleurGilet;

    }

    /**
     * ***************************************************
     */
    /**
     * **************** CouleurCasque Salarie *****************
     */
    /**
     * ***************************************************
     */
    /**
     * Récupérer le nombre des couleurs de Casques
     *
     * @param querySearch
     *
     * @return Object
     */
    public Object nombreCouleurCasques(String querySearch) {

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("SELECT count(*) FROM CouleurCasque WHERE couleurCasque LIKE '%" + StringEscapeUtils.escapeSql(querySearch) + "%'");

        return query.uniqueResult();

    }

    /**
     * Récupérer la liste des couleurs des casques
     *
     * @param start
     * @param limit
     * @param querySearch
     * @return Couleurs de Casques
     */
    public List<CouleurCasque> listeCouleurCasques(int start, int limit, String querySearch) {
        logger.debug("liste des TaillesHabillement");

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM CouleurCasque WHERE couleurCasque LIKE '%" + StringEscapeUtils.escapeSql(querySearch) + "%' order by couleurCasque");
        query.setFirstResult(start);
        query.setMaxResults(limit);

        return query.list();

    }

    /**
     * Récupérer les couleurs de Casques par id
     *
     * @param idCouleurCasque
     *
     * @return Couleur de Casque
     */
    public CouleurCasque getCouleurCasque(Integer idCouleurCasque) {
        logger.debug("CouleurCasque par id");

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM CouleurCasque WHERE id=?");

        query.setParameter(0, idCouleurCasque, StandardBasicTypes.INTEGER);
        CouleurCasque couleurCasque = (CouleurCasque) query.uniqueResult();

        return couleurCasque;

    }

    /**
     * Récupérer un Couleur de Casque par nom
     *
     * @param nomCouleurCasque
     * @return Couleur de Casque
     */
    public CouleurCasque getCouleurCasque(String nomCouleurCasque) {
        logger.debug("CouleurCasque par nom");

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM CouleurCasque WHERE couleurCasque=?");

        query.setParameter(0, nomCouleurCasque, StandardBasicTypes.STRING);
        CouleurCasque couleurCasque = (CouleurCasque) query.uniqueResult();

        return couleurCasque;

    }

    /**
     * *********************************************************************Document*******************************************************************
     */
    /**
     * ***************************************************
     */
    /**
     * **************** Type Document *****************
     */
    /**
     * ***************************************************
     */
    /**
     * Récupérer la liste des Types de document
     *
     * @param querySearch
     *
     * @return Object
     */
    public Object nombreTypesDocuments(String querySearch) {

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("SELECT count(*) FROM TypeDocument WHERE typeDocument LIKE '%" + StringEscapeUtils.escapeSql(querySearch) + "%'");

        return query.uniqueResult();

    }

    /**
     * Récupérer la liste des Types de document
     *
     * @param start
     * @param limit
     * @param querySearch
     *
     * @return Type de Document
     */
    public List<TypeDocument> listeTypesDocuments(int start, int limit, String querySearch) {
        logger.debug("liste des types des documents");

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM TypeDocument WHERE typeDocument LIKE '%" + StringEscapeUtils.escapeSql(querySearch) + "%' order by typeDocument");
        query.setFirstResult(start);
        query.setMaxResults(limit);

        return query.list();

    }

    /**
     * Récupérer un Type de Document
     *
     * @param idTypeDocument
     *
     * @return Type de Document
     */
    public TypeDocument getTypeDocument(Integer idTypeDocument) {
        logger.debug("TypeDocument par id");

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM TypeDocument WHERE id=?");

        query.setParameter(0, idTypeDocument, StandardBasicTypes.INTEGER);
        TypeDocument typeDocument = (TypeDocument) query.uniqueResult();

        return typeDocument;

    }

    /**
     * Récupérer un Type de document
     *
     * @param nomTypeDocument
     *
     * @return Type de Document
     */
    public TypeDocument getTypeDocument(String nomTypeDocument) {
        logger.debug("TypeDocument par nom");

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM TypeDocument WHERE typeDocument=?");

        query.setParameter(0, nomTypeDocument, StandardBasicTypes.STRING);
        TypeDocument typeDocument = (TypeDocument) query.uniqueResult();

        return typeDocument;

    }

    //-------------------------- Etat heures supplementaires ------------------------------
    public EtatHeuresSupplementaires getEtatHS(String etat) {

        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("FROM EtatHeuresSupplementaires WHERE etatHeuresSupplementaires=:EtatHS");
        query.setParameter("EtatHS", etat, StandardBasicTypes.STRING);

        return (EtatHeuresSupplementaires) query.uniqueResult();
    }

    public List<EtatHeuresSupplementaires> listeEtatHS(int start, int limit, String querySearch) {
        logger.debug("liste des Etat heures Sup");

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM EtatHeuresSupplementaires WHERE etatHeuresSupplementaires LIKE '%" + StringEscapeUtils.escapeSql(querySearch) + "%' order by etatHeuresSupplementaires");
        query.setFirstResult(start);
        query.setMaxResults(limit);

        return query.list();

    }

    public Object nombreEtatsHS(String querySearch) {

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("SELECT count(*) FROM EtatHeuresSupplementaires WHERE etatHeuresSupplementaires LIKE '%" + StringEscapeUtils.escapeSql(querySearch) + "%'");

        return query.uniqueResult();

    }

    /**
     * ***************************************************
     */
    /**
     * **************** Type des absences *********
     */
    /**
     * ***************************************************
     */
    public Object nombreTypesAbsences(String querySearch) {

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("SELECT count(*) FROM TypeAbsence WHERE typeAbsence LIKE '%" + StringEscapeUtils.escapeSql(querySearch) + "%'");

        return query.uniqueResult();

    }

    public List<TypeAbsence> listeTypesAbsences(int start, int limit, String querySearch) {
        logger.debug("liste des types d'absences");

        Session session = sessionFactory.getCurrentSession();

        Query query = null;

        query = session.createQuery("FROM TypeAbsence WHERE typeAbsence LIKE '%" + StringEscapeUtils.escapeSql(querySearch) + "%' order by typeAbsence");

        query.setFirstResult(start);
        query.setMaxResults(limit);

        return query.list();

    }

    public TypeAbsence getTypeAbsence(Integer idTypeAbsence) {
        logger.debug("TypeAbsence par id");

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM TypeAbsence WHERE id=?");

        query.setParameter(0, idTypeAbsence, StandardBasicTypes.INTEGER);
        TypeAbsence typeAbsence = (TypeAbsence) query.uniqueResult();

        return typeAbsence;

    }

    public TypeAbsence getTypeAbsence(String nomTypeAbsence) {
        logger.debug("TypeAbsence par nom");

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM TypeAbsence WHERE typeAbsence=?");

        query.setParameter(0, nomTypeAbsence, StandardBasicTypes.STRING);
        TypeAbsence typeAbsence = (TypeAbsence) query.uniqueResult();

        return typeAbsence;

    }

    /**
     * ***************************************************
     */
    /**
     * ****************Models contrats *********
     */
    /**
     * ***************************************************
     */
    public Object nombreModelsContrats(String querySearch) {

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("SELECT count(*) FROM ModelContrat WHERE intitule LIKE '%" + StringEscapeUtils.escapeSql(querySearch) + "%'");

        return query.uniqueResult();

    }

    public List<ModelContrat> listeModelsContrats(int start, int limit, String querySearch) {
        logger.debug("liste des models contrats");

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM ModelContrat WHERE intitule LIKE '%" + StringEscapeUtils.escapeSql(querySearch) + "%' order by intitule");

        query.setFirstResult(start);
        query.setMaxResults(limit);

        return query.list();

    }

    public ModelContrat getModelContrat(Integer idModelContrat) {
        logger.debug("ModelContrat par id");

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM ModelContrat WHERE id=?");

        query.setParameter(0, idModelContrat, StandardBasicTypes.INTEGER);
        ModelContrat modelContrat = (ModelContrat) query.uniqueResult();

        return modelContrat;
    }

    public ModelContrat getModelContrat(String modelContratSalarie) {
        logger.debug("récupérer le Model Contrat par intitule");

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM ModelContrat WHERE intitule=?");

        query.setParameter(0, modelContratSalarie, StandardBasicTypes.STRING);
        ModelContrat modelContrat = (ModelContrat) query.uniqueResult();

        return modelContrat;

    }

    /**
     * ***************************************************
     */
    /**
     * **************** Etats des contrats*********
     */
    /**
     * ***************************************************
     */
    public Object nombreEtatsContrats(String querySearch) {

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("SELECT count(*) FROM EtatContrat WHERE etatContrat LIKE '%" + StringEscapeUtils.escapeSql(querySearch) + "%'");

        return query.uniqueResult();

    }

    public List<EtatContrat> listeEtatsContrats(int start, int limit, String querySearch) {
        logger.debug("liste des Ã©tats de contrats");

        Session session = sessionFactory.getCurrentSession();

        Query query = null;

        query = session.createQuery("FROM EtatContrat WHERE etatContrat LIKE '%" + StringEscapeUtils.escapeSql(querySearch) + "%' order by etatContrat desc");

        query.setFirstResult(start);
        query.setMaxResults(limit);

        return query.list();

    }

    public EtatContrat getEtatContrat(Integer idEtatContrat) {
        logger.debug("EtatContrat par id");

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM EtatContrat WHERE id=?");

        query.setParameter(0, idEtatContrat, StandardBasicTypes.INTEGER);
        EtatContrat etatContrat = (EtatContrat) query.uniqueResult();

        return etatContrat;

    }

    /**
     * Récupérer un état de contrat
     *
     * @param nomEtatContrat
     *
     * @return Etat du Contrat
     */
    public EtatContrat getEtatContrat(String nomEtatContrat) {
        logger.debug("EtatContrat par nom");

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM EtatContrat WHERE etatContrat=?");

        query.setParameter(0, nomEtatContrat, StandardBasicTypes.STRING);
        EtatContrat etatContrat = (EtatContrat) query.uniqueResult();

        return etatContrat;

    }

    /**
     * ***************************************************
     */
    /**
     * **************** Type des contrats*********
     */
    /**
     * ***************************************************
     */
    /**
     * Récupérer le nombre des types de Contrat
     *
     * @param querySearch
     *
     * @return Object
     */
    public Object nombreTypesContrats(String querySearch) {

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("SELECT count(*) FROM TypeContrat WHERE typeContrat LIKE '%" + StringEscapeUtils.escapeSql(querySearch) + "%'");

        return query.uniqueResult();

    }

    /**
     * Récupérer la liste des Types de Contrat
     *
     * @param start
     * @param limit
     * @param querySearch
     *
     * @return Type de Contrat
     */
    public List<TypeContrat> listeTypesContrats(int start, int limit, String querySearch) {
        logger.debug("liste des types de contrats");

        Session session = sessionFactory.getCurrentSession();

        Query query = null;

        query = session.createQuery("FROM TypeContrat WHERE typeContrat LIKE '%" + StringEscapeUtils.escapeSql(querySearch) + "%' order by typeContrat");

        query.setFirstResult(start);
        query.setMaxResults(limit);

        return query.list();

    }

    /**
     * Récupérer un Type de Contrat
     *
     * @param idTypeContrat
     *
     * @return Tye de Contrat
     */
    public TypeContrat getTypeContrat(Integer idTypeContrat) {
        logger.debug("TypeContrat par id");

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM TypeContrat WHERE id=?");

        query.setParameter(0, idTypeContrat, StandardBasicTypes.INTEGER);
        TypeContrat typeContrat = (TypeContrat) query.uniqueResult();

        return typeContrat;

    }

    /**
     * Récupérer un Type de Contrat
     *
     * @param nomTypeContrat
     *
     * @return Type de Contrat
     */
    public TypeContrat getTypeContrat(String nomTypeContrat) {
        logger.debug("TypeContrat par nom");

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM TypeContrat WHERE typeContrat=?");

        query.setParameter(0, nomTypeContrat, StandardBasicTypes.STRING);
        TypeContrat typeContrat = (TypeContrat) query.uniqueResult();

        return typeContrat;

    }

    /**
     * ***************************************************
     */
    /**
     * **************** DurÃ©e des contrats*********
     */
    /**
     * ***************************************************
     */
    /**
     * Récupérer le nombre des durées de contrat
     *
     * @param querySearch
     *
     * @return Object
     */
    public Object nombreDureesContrats(String querySearch) {

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("SELECT count(*) FROM Duree WHERE dureeContrat LIKE '%" + StringEscapeUtils.escapeSql(querySearch) + "%'");

        return query.uniqueResult();

    }

    /**
     * Récupérer les Durées de Contrat
     *
     * @param start
     * @param limit
     * @param querySearch
     *
     * @return Durées
     */
    public List<Duree> listeDureesContrats(int start, int limit, String querySearch) {
        logger.debug("liste des durÃ©es de contrats");

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM Duree WHERE dureeContrat LIKE '%" + StringEscapeUtils.escapeSql(querySearch) + "%' order by dureeContrat");

        query.setFirstResult(start);
        query.setMaxResults(limit);

        return query.list();

    }

    /**
     * Récupérer une Durée de contrat
     *
     * @param idDuree
     *
     * @return Durée
     */
    public Duree getDuree(Integer idDuree) {
        logger.debug("Duree par id");

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM Duree WHERE id=?");

        query.setParameter(0, idDuree, StandardBasicTypes.INTEGER);
        Duree duree = (Duree) query.uniqueResult();

        return duree;

    }

    /**
     * Récupérer une Durée de Contrat
     *
     * @param nomDuree
     *
     * @return Durée
     */
    public Duree getDuree(String nomDuree) {
        logger.debug("Duree par nom");

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM Duree WHERE dureeContrat=?");

        query.setParameter(0, nomDuree, StandardBasicTypes.STRING);
        Duree duree = (Duree) query.uniqueResult();

        return duree;

    }

    /**
     * ***************************************************
     */
    /**
     * **************** Periode d'essai des contrats*********
     */
    /**
     * ***************************************************
     */
    /**
     * Récupérer le nombre des Périodes d'essai
     *
     * @param querySearch
     *
     * @return Object
     */
    public Object nombrePeriodesEssaiContrats(String querySearch) {

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("SELECT count(*) FROM PeriodeEssai WHERE periodeEssai LIKE '%" + StringEscapeUtils.escapeSql(querySearch) + "%'");

        return query.uniqueResult();

    }

    /**
     * @param start
     * @param limit
     * @param querySearch
     *
     * @return Récupérer la liste des Périodes d'essai
     */
    public List<PeriodeEssai> listePeriodesEssaiContrats(int start, int limit, String querySearch) {
        logger.debug("liste des pÃ©riodes d'essai de contrats");

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM PeriodeEssai WHERE periodeEssai LIKE '%" + StringEscapeUtils.escapeSql(querySearch) + "%' order by periodeEssai");

        query.setFirstResult(start);
        query.setMaxResults(limit);

        return query.list();

    }

    /**
     * Récupérer la période d'essai
     *
     * @param idPeriodeEssai
     *
     * @return Période d'essai
     */
    public PeriodeEssai getPeriodeEssai(Integer idPeriodeEssai) {
        logger.debug("PeriodeEssai par id");

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM PeriodeEssai WHERE id=?");

        query.setParameter(0, idPeriodeEssai, StandardBasicTypes.INTEGER);
        PeriodeEssai periodeEssai = (PeriodeEssai) query.uniqueResult();

        return periodeEssai;

    }

    /**
     * Récupérer une Période d'essai
     *
     * @param nomPeriodeEssai
     * @return Période d'essai
     */
    public PeriodeEssai getPeriodeEssai(String nomPeriodeEssai) {
        logger.debug("PeriodeEssai par nom");

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM PeriodeEssai WHERE periodeEssai=?");

        query.setParameter(0, nomPeriodeEssai, StandardBasicTypes.STRING);
        PeriodeEssai periodeEssai = (PeriodeEssai) query.uniqueResult();

        return periodeEssai;

    }

    /**
     * ***************************************************
     */
    /**
     * **************** PrÃ©avis des contrats*********
     */
    /**
     * ***************************************************
     */
    /**
     * Récupérer le nombre des prévis des contrats
     *
     * @param querySearch
     * @return Object
     */
    public Object nombrePreavisContrats(String querySearch) {

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("SELECT count(*) FROM Preavis WHERE preavis LIKE '%" + StringEscapeUtils.escapeSql(querySearch) + "%'");

        return query.uniqueResult();

    }

    /**
     * Récupérer la liste des préavis du Contrat
     *
     * @param start
     * @param limit
     * @param querySearch
     * @return Préavis
     */
    public List<Preavis> listePreavisContrats(int start, int limit, String querySearch) {
        logger.debug("liste des preavis de contrats");

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM Preavis WHERE preavis LIKE '%" + StringEscapeUtils.escapeSql(querySearch) + "%' order by preavis");

        query.setFirstResult(start);
        query.setMaxResults(limit);

        return query.list();

    }

    /**
     * Récupérer un Préavis par id
     *
     * @param idPreavis
     * @return Préavis
     */
    public Preavis getPreavis(Integer idPreavis) {
        logger.debug("Preavis par id");

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM Preavis WHERE id=?");

        query.setParameter(0, idPreavis, StandardBasicTypes.INTEGER);
        Preavis preavis = (Preavis) query.uniqueResult();

        return preavis;

    }

    /**
     * Récupérer un préavis Par nom
     *
     * @param nomPreavis
     * @return Prévis
     */
    public Preavis getPreavis(String nomPreavis) {
        logger.debug("Preavis par nom");

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM Preavis WHERE preavis=?");

        query.setParameter(0, nomPreavis, StandardBasicTypes.STRING);
        Preavis preavis = (Preavis) query.uniqueResult();

        return preavis;

    }

    /**
     * ***************************************************
     */
    /**
     * ****************ModesPaiement *********
     */
    /**
     * ***************************************************
     */
    /**
     * Récupérer le nombre des modes de paiement
     *
     * @param querySearch
     *
     * @return Obejct
     */
    public Object nombreModesPaiement(String querySearch) {

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("SELECT count(*) FROM ModePaiement WHERE modePaiement LIKE '%" + StringEscapeUtils.escapeSql(querySearch) + "%'");

        return query.uniqueResult();

    }

    /**
     * Récupérer la liste des Modes de Paiement
     *
     * @param start
     * @param limit
     * @param querySearch
     *
     * @return Modes de Paiement
     */
    public List<ModePaiement> listeModesPaiement(int start, int limit, String querySearch) {
        logger.debug("liste des modes de paiement");

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM ModePaiement WHERE modePaiement LIKE '%" + StringEscapeUtils.escapeSql(querySearch) + "%' order by modePaiement");

        query.setFirstResult(start);
        query.setMaxResults(limit);

        return query.list();

    }

    /**
     * Récupérer le Mode de Paiement
     *
     * @param modePaiementSalarie
     *
     * @return Mode de Paiement
     */
    public ModePaiement getModePaiement(String modePaiementSalarie) {
        logger.debug("recuperer le mode de paiement par nom");

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM ModePaiement WHERE modePaiement=?");

        query.setParameter(0, modePaiementSalarie, StandardBasicTypes.STRING);
        ModePaiement modePaiement = (ModePaiement) query.uniqueResult();

        return modePaiement;

    }

    public ModePaiement getModePaiement(Integer idModePaiement) {
        logger.debug("recuperer le mode de paiement par id");

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM ModePaiement WHERE id=?");

        query.setParameter(0, idModePaiement, StandardBasicTypes.INTEGER);
        ModePaiement modePaiement = (ModePaiement) query.uniqueResult();

        return modePaiement;

    }

    /**
     * ***************************************************
     */
    /**
     * ****************CategoriesOutilTravail *********
     */
    /**
     * ***************************************************
     */
    /**
     * Récupérer le nombre des Catégories des outils de travail
     *
     * @param querySearch
     * @return Object
     */
    public Object nombreCategoriesOutilTravail(String querySearch) {

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("SELECT count(*) FROM CategorieOutilTravail WHERE categorieOutilTravail LIKE '%" + StringEscapeUtils.escapeSql(querySearch) + "%'");

        return query.uniqueResult();

    }

    /**
     * Récupérer la liste des Catégories des outils de travail
     *
     * @param start
     * @param limit
     * @param querySearch
     *
     * @return Catégories de travail
     */
    public List<CategorieOutilTravail> listeCategoriesOutilTravail(int start, int limit, String querySearch) {
        logger.debug("liste des CategoriesOutilTravail");

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM CategorieOutilTravail WHERE categorieOutilTravail LIKE '%" + StringEscapeUtils.escapeSql(querySearch) + "%' order by categorieOutilTravail");

        query.setFirstResult(start);
        query.setMaxResults(limit);

        return query.list();

    }

    /**
     * Récupérer la Catgorie d'un outil de travail par id
     *
     * @param idCategorieOutilTravail
     *
     * @return Catégorie Outil de travail
     */
    public CategorieOutilTravail getCategorieOutilTravail(Integer idCategorieOutilTravail) {
        logger.debug("CategorieOutilTravail par id");

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM CategorieOutilTravail WHERE id=?");

        query.setParameter(0, idCategorieOutilTravail, StandardBasicTypes.INTEGER);
        CategorieOutilTravail categorieOutilTravail = (CategorieOutilTravail) query.uniqueResult();

        return categorieOutilTravail;

    }

    /**
     * Récupérer la Catgorie d'un outil de travail par nom
     *
     * @param categorieOutilTravailSalarie
     * @return Catégorie outil de travail
     */
    public CategorieOutilTravail getCategorieOutilTravail(String categorieOutilTravailSalarie) {
        logger.debug("récupérer  CategorieOutilTravail par nom");

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM CategorieOutilTravail WHERE categorieOutilTravail=?");

        query.setParameter(0, categorieOutilTravailSalarie, StandardBasicTypes.STRING);
        CategorieOutilTravail categorieOutilTravail = (CategorieOutilTravail) query.uniqueResult();

        return categorieOutilTravail;

    }

    // ----- FIN PARAMETRAGE RESSSOURCES HUMAINES ----- 
    // ----- PARAMETRAGE ACHATS ----- 
    /**
     * ***************************************************
     */
    /**
     * **************** Etats de la demande d'approvisionnement
     * *****************
     */
    /**
     * ***************************************************
     */
    /**
     * Récupérer le nombre des états de la demande d'approvisionnement
     *
     * @param querySearch
     * @return Object
     */
    public Object nombreEtatsDA(String querySearch) {

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("SELECT count(*) FROM EtatDA WHERE etat LIKE '%" + StringEscapeUtils.escapeSql(querySearch) + "%'");

        return query.uniqueResult();

    }

    /**
     * Récupérer la liste des états des demandes d'approvisionnement
     *
     * @param start
     * @param limit
     * @param querySearch
     *
     * @return Etat demande d'approvisionnement
     */
    public List<EtatDA> listeEtatsDA(int start, int limit, String querySearch) {
        logger.debug("liste des etatsDA");

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM EtatDA WHERE etat LIKE '%" + StringEscapeUtils.escapeSql(querySearch) + "%' order by etat");
        query.setFirstResult(start);
        query.setMaxResults(limit);

        return query.list();

    }

    /**
     * Récupérer l'état d'une demande d'Approvisionnement
     *
     * @param idEtatDA
     *
     * @return Etat demande d'appro
     */
    public EtatDA getEtatDA(Integer idEtatDA) {
        logger.debug("EtatDA par id");

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM EtatDA WHERE id=?");

        query.setParameter(0, idEtatDA, StandardBasicTypes.INTEGER);
        EtatDA etatDA = (EtatDA) query.uniqueResult();

        return etatDA;

    }

    /**
     * Récupérer une état d'approvisionnement par nom
     *
     * @param nomEtatDA
     *
     * @return Etat Demande d'approvisionnement
     */
    public EtatDA getEtatDA(String nomEtatDA) {
        logger.debug("EtatDA par nom");

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM EtatDA WHERE etat=?");

        query.setParameter(0, nomEtatDA, StandardBasicTypes.STRING);
        EtatDA etatDA = (EtatDA) query.uniqueResult();

        return etatDA;

    }

    /**
     * Récupérer le nombre des motifs de sortie qui existent
     *
     * @param querySearch
     *
     * @return Object
     */
    public Object nombreMotifsSortie(String querySearch) {

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("SELECT count(*) FROM Motif WHERE motif LIKE '%" + StringEscapeUtils.escapeSql(querySearch) + "%'");

        return query.uniqueResult();

    }

    /**
     * Récupérer la liste des motifs de sortie
     *
     * @param start
     * @param limit
     * @param querySearch
     *
     * @return Civilités
     */
    public List<Motif> listeMotifs(int start, int limit, String querySearch) {
        logger.debug("liste des motifs de sortie");

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM Motif WHERE motif LIKE '%" + StringEscapeUtils.escapeSql(querySearch) + "%' order by motif");
        query.setFirstResult(start);
        query.setMaxResults(limit);

        return query.list();

    }

    /**
     * Récupérer un Motif de sortie par id
     *
     * @param idMotif
     * @return Motif
     */
    public Motif getMotif(Integer idMotif) {
        logger.debug("Motif par id");

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM Motif WHERE id=?");

        query.setParameter(0, idMotif, StandardBasicTypes.INTEGER);
        Motif motif = (Motif) query.uniqueResult();

        return motif;

    }

    /**
     * Récupérer un motif de sortie par nom
     *
     * @param nomMotif
     *
     * @return Motif
     */
    public Motif getMotif(String nomMotif) {
        logger.debug("Motif par nom");

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM Motif WHERE motif=?");

        query.setParameter(0, nomMotif, StandardBasicTypes.STRING);
        Motif motif = (Motif) query.uniqueResult();

        return motif;

    }

    /**
     * Récupérer un motif de sortie par code motif Divalto
     *
     * @param codeMotif code motif divalto (exemple: fcdd)
     *
     * @return Motif
     */
    public Motif getMotifByCode(String codeMotif) {
        logger.debug("Motif par nom");

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM Motif WHERE motifCode=?");

        query.setParameter(0, codeMotif, StandardBasicTypes.STRING);
        Motif motif = (Motif) query.uniqueResult();

        return motif;

    }

    /**
     * ***************************************************
     */
    /**
     * **************** Articles **************
     */
    /**
     * ***************************************************
     */
    /**
     * Récupérer le nombre des articles
     *
     * @param querySearch
     *
     * @return Object
     */
    public Object nombreArticles(String querySearch) {

        Session session = sessionFactory.getCurrentSession();
        String dos = Constantes.getInstance().numeroDossierDivalto;
        Query query = session.createQuery("SELECT count(*) FROM Article WHERE codeArticle LIKE '%" + StringEscapeUtils.escapeSql(querySearch) + "%'  and fam1!='' and fam2!='' and fam3!='' AND dos=?");

        query.setParameter(0, Integer.parseInt(dos), StandardBasicTypes.INTEGER);

        return query.uniqueResult();

    }

    /**
     * Récupérer les Articles
     *
     * @param start
     * @param limit
     * @param querySearch
     * @return Articles
     */
    public List<Article> listeArticles(int start, int limit, String querySearch) {
        logger.debug("liste des codes articles");

        Session session = sessionFactory.getCurrentSession();
        String dos = Constantes.getInstance().numeroDossierDivalto;

        Query query = session.createQuery("FROM Article WHERE codeArticle LIKE '%" + StringEscapeUtils.escapeSql(querySearch) + "%' AND dos=? and fam1!='' and fam2!='' and fam3!='' order by codeArticle");

        query.setParameter(0, Integer.parseInt(dos), StandardBasicTypes.INTEGER);

        query.setFirstResult(start);
        query.setMaxResults(limit);

        return query.list();

    }

    /**
     * Récupérer un Article par id
     *
     * @param idArticle
     *
     * @return Article
     */
    public Article getArticle(Integer idArticle) {
        logger.debug("Article par id");

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM Article WHERE id=?");

        query.setParameter(0, idArticle, StandardBasicTypes.INTEGER);
        Article article = (Article) query.uniqueResult();

        return article;

    }

    /**
     * Récupérer un Article par Code
     *
     * @param codeArticle
     * @return Article
     */
    public Article getArticleByCode(String codeArticle, String dos) {
        logger.debug("Article par code");

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM Article  WHERE dos=" + dos + " AND lower(codeArticle) =? AND (HSDT is null or HSDT>?)");

        query.setParameter(0, codeArticle.toLowerCase(), StandardBasicTypes.STRING);
        query.setParameter(1, new Date(), StandardBasicTypes.DATE);
        Article article = (Article) query.uniqueResult();

        return article;

    }

    public Object nombreFamilles(String querySearch, Integer famno, Integer nbCaracteres, String fam, String reqDos) {

        Session session = sessionFactory.getCurrentSession();
        Query query = null;
        if (nbCaracteres == 2) {
            query = session.createQuery("SELECT count(*) FROM FamilleArticle WHERE " + reqDos + " AND lower(fam) LIKE '%" + StringEscapeUtils.escapeSql(querySearch).toLowerCase() + "%' AND famno=? AND length(fam)=?");
            query.setParameter(0, famno, StandardBasicTypes.INTEGER);
            query.setParameter(1, nbCaracteres, StandardBasicTypes.INTEGER);
        } else if (nbCaracteres == 5) {
            query = session.createQuery("SELECT count(*) FROM FamilleArticle WHERE " + reqDos + " AND lower(fam) LIKE '%" + StringEscapeUtils.escapeSql(querySearch).toLowerCase() + "%' AND famno=? AND length(fam)=? AND upper(fam) LIKE '" + fam + "%'");
            query.setParameter(0, famno, StandardBasicTypes.INTEGER);
            query.setParameter(1, nbCaracteres, StandardBasicTypes.INTEGER);
        } else {
            query = session.createQuery("SELECT count(*) FROM FamilleArticle WHERE " + reqDos + " AND lower(fam) LIKE '%" + StringEscapeUtils.escapeSql(querySearch).toLowerCase() + "%' AND famno=? AND length(fam)=? AND upper(fam) LIKE '" + fam + "%'");
            query.setParameter(0, famno, StandardBasicTypes.INTEGER);
            query.setParameter(1, nbCaracteres, StandardBasicTypes.INTEGER);
//			query.setParameter(2, fam, Hibernate.STRING);
        }

        return query.uniqueResult();

    }

    public Object listeFamilles(int strat, int limit, Integer famno, Integer nbCaracteres, String fam, String reqDos) {

        Session session = sessionFactory.getCurrentSession();
        Query query = null;
        if (nbCaracteres == 3) {
            query = session.createQuery("Select id,famno,fam,lib,upcomplement,dos FROM FamilleArticle WHERE " + reqDos + " AND lower(fam) LIKE '[0-9][0-9][0-9]-%' AND famno=? order by upcomplement,fam");
            query.setParameter(0, famno, StandardBasicTypes.INTEGER);
        } else if (nbCaracteres == 5) {
            query = session.createQuery("SELECT id,famno,fam,lib,upcomplement,dos  FROM FamilleArticle WHERE " + reqDos + " AND lower(fam) LIKE '[0-9][0-9][0-9][0-9][0-9]-%' AND famno=? AND upper(fam) LIKE '" + fam + "%'  order by upcomplement,fam");
            query.setParameter(0, famno, StandardBasicTypes.INTEGER);
        } else {
            query = session.createQuery("SELECT id,famno,fam,lib,upcomplement,dos FROM FamilleArticle WHERE " + reqDos + " AND lower(fam) LIKE '[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]' AND famno=?  AND upper(fam) LIKE '" + fam + "%'  order by upcomplement,fam");
            query.setParameter(0, famno, StandardBasicTypes.INTEGER);
        }

        List listFamilles = query.list();
        List<FamilleArticle> liste = new ArrayList<FamilleArticle>();
        FamilleArticle familleArticle = null;

        for (int i = 0; i < listFamilles.size(); i++) {

            Object[] o = (Object[]) listFamilles.get(i);
            familleArticle = new FamilleArticle();

            familleArticle.setId((Integer) o[0]);
            familleArticle.setFamno((Integer) o[1]);
            familleArticle.setFam(((Character) o[4]) + (String) o[2]);
            familleArticle.setLib((String) o[3]);
            familleArticle.setUpcomplement((Character) o[4]);
            familleArticle.setDos((Integer) o[5]);
            familleArticle.setFamille((Character) o[4] + (String) o[2] + " -- " + (String) o[3]);

            liste.add(familleArticle);

        }

        return liste;

    }

	// ----- FIN PARAMETRAGE ACHATS ----- 
}
