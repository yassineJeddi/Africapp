package ma.bservices.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import ma.bservices.beans.Groupe;
import ma.bservices.beans.Permission;
import ma.bservices.beans.Utilisateur;
import ma.bservices.constantes.Constantes;

import org.apache.log4j.Logger;
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
@Service("administrationService")
@Transactional
public class AdministrationService {

    protected static Logger logger = Logger.getLogger("service");
    @Resource(name = "sessionFactory")
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    //------------ PERMISSIONS ---------------
    /**
     * Récupérer la liste des permissions associés à un sous module
     *
     * @param sousModule le nom du sous module
     *
     * @return liste des Permissions
     */
    public List<Permission> listePermissions(String sousModule) {
        logger.debug("liste des permissions");

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM Permission p WHERE p.sousModule=?");
        query.setParameter(0, sousModule, StandardBasicTypes.STRING);

        //		  List<Permission> permissionList=new ArrayList<Permission>();
        //		  Permission permission=null;
        //		  
        //		  for(int i=0;i<query.list().size(); i++){
        //		    	
        ////		    	Object[] o=(Object[]) query.list().get(i);
        //		    	permission = new Permission();
        ////		    	permission.setId((Integer) o[0]);
        ////		    	permission.setModule((String)o[1]);
        ////		    	permission.setSousModule((String)o[2]);
        //		    	permission.setPermission((String)query.list().get(i));
        //		    	
        //		    	permissionList.add(permission);	
        //		    }
        return query.list();

    }

    /**
     * Récupérer les modules de l'application
     *
     * @return permissions -> modules
     */
    public List<Permission> listeModules() {
        logger.debug("liste des modules");

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createSQLQuery("SELECT DISTINCT MODULE FROM PERMISSION");

        List<Permission> modulesList = new ArrayList<Permission>();
        Permission permission = null;

        for (int i = 0; i < query.list().size(); i++) {

            //		    	Object[] o=(Object[]) query.list().get(i);
            permission = new Permission();
            //		    	permission.setId((Integer)query.list().get(1));
            permission.setModule((String) query.list().get(i));
            //		    	permission.setSousModule((String)o[2]);
            //		    	permission.setPermission((String)o[3]);

            modulesList.add(permission);
        }

        return modulesList;

    }

    /**
     * Récupérer les Sous module d'un module
     *
     * @param module le nom du module
     * @return Permissions -> Sous Modules
     */
    public List<Permission> listeSousModules(String module) {
        logger.debug("liste des sous modules");

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createSQLQuery("SELECT DISTINCT SOUSMODULE FROM PERMISSION p WHERE p.MODULE=?");
        query.setParameter(0, module, StandardBasicTypes.STRING);

        List<Permission> sousModulesList = new ArrayList<Permission>();
        Permission permission = null;

        for (int i = 0; i < query.list().size(); i++) {

            //		    	Object[] o=(Object[]) query.list().get(i);
            permission = new Permission();
            //		    	permission.setId((Integer) o[0]);
            //		    	permission.setModule((String)o[1]);
            permission.setSousModule((String) query.list().get(i));
            //		    	permission.setPermission((String)o[3]);

            sousModulesList.add(permission);
        }

        return sousModulesList;

    }

    /**
     * Récupérer une Permission
     *
     * @param id l'identifiant du permission
     *
     * @return Permission
     */
    public Permission getPermission(Integer id) {

        logger.debug("récupérer Permission par id");

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM Permission WHERE id=?");

        query.setParameter(0, id, StandardBasicTypes.INTEGER);
        Permission permission = (Permission) query.uniqueResult();

        return permission;
    }

    /**
     * Récupérer la Permission via le Nom
     *
     * @param permissionName le nom du permission
     * @return Permission
     */
    public Permission getPermissionByName(String permissionName) {

        logger.debug("récupérer Permission par nom");

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM Permission WHERE permission=?");

        query.setParameter(0, permissionName, StandardBasicTypes.STRING);
        Permission permission = (Permission) query.uniqueResult();
        if(permission == null){
            System.out.println("HELLO, YOUR PERMISSION DOESN'T EXIST");
        }

        return permission;
    }

    //------------ GROUPES ---------------
    /**
     * Récupérer la liste des Groupes selon les paramètres de pagination start
     * et limit
     *
     * @param start pour la pagination, c'est un nombre entier qui représente
     * l'index du premier élément à afficher
     * @param limit pour la pagination, c'est un nombre entier qui représente
     * l'index du dernier élément à afficher
     *
     * @return la liste des Groupes
     */
    public List<Groupe> listeGroupes(int start, int limit) {

        logger.debug("Liste des Groupes");

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM Groupe");

        query.setFirstResult(start);
        query.setMaxResults(limit);

        return query.list();
    }

    /**
     * Récupérer le nombre des Groupes existants
     *
     * @return nombre des groupes
     */
    public Object nombreGroupes() {

        logger.debug("nombre des Groupes");

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("SELECT count(*) FROM Groupe");

        return query.uniqueResult();
    }

    /**
     * Récupérer un Groupe via id
     *
     * @param id l'identifiant du groupe dans la base de données
     *
     * @return Groupe
     */
    public Groupe getGroupe(Integer id) {

        logger.debug("récupérer Groupe par id");

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM Groupe WHERE id=?");

        query.setParameter(0, id, StandardBasicTypes.INTEGER);
        Groupe g = (Groupe) query.uniqueResult();

        return g;
    }

    /**
     * Récupérer un Groupe via nom
     *
     * @param groupe le nom du groupe
     *
     * @return Groupe
     */
    public Groupe getGroupe(String groupe) {

        logger.debug("récupérer Groupe par nom");

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM Groupe WHERE groupe=?");

        query.setParameter(0, groupe, StandardBasicTypes.STRING);
        Groupe g = (Groupe) query.uniqueResult();

        return g;
    }

    /**
     * Récupérer un Groupe via nodeRef
     *
     * @param nodeRefGroupe le nodeRef du groupe
     *
     * @return Groupe
     */
    public Groupe getGroupeByNodeRef(String nodeRefGroupe) {

        logger.debug("récupérer Groupe par nodeRef");

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM Groupe WHERE nodeRef=?");

        query.setParameter(0, nodeRefGroupe, StandardBasicTypes.STRING);
        Groupe g = (Groupe) query.uniqueResult();

        return g;
    }

    /**
     * Créer un nouveau Groupe
     *
     * @param groupe le nom du Groupe
     */
    public void ajouterGroupe(Groupe groupe) {

        logger.debug("ajouter un nouveau groupe");

        Session session = sessionFactory.getCurrentSession();

        session.save(groupe);

    }

    /**
     * Attribuer une Permission à un Groupe
     *
     * @param groupe le nom du Groupe
     * @param permission le nom du permission à attribuer au groupe
     */
    public void attribuerPermissionGroupe(Groupe groupe, Permission permission) {
        logger.debug("attribuer une permission a un groupe");

        Session session = sessionFactory.getCurrentSession();

        groupe.getPermissions().add(permission);

        session.update(groupe);

    }

    /**
     * Désattribuer une Permission d'un Groupe
     *
     * @param groupe le nom du groupe
     * @param permission le nom de permission
     */
    public void desattribuerPermission(Groupe groupe, Permission permission) {
        logger.debug("desattribuer une permission a un groupe");

        Session session = sessionFactory.getCurrentSession();

        groupe.getPermissions().remove(permission);

        session.update(groupe);

    }

    /**
     * Désattribuer une Permission d'un Groupe
     *
     * @param groupe le nom du groupe
     * @param permission instance de l'obejt Permission. il doit être au
     * préalable récupéré à partir de la base de données puis passé en
     * paramètres
     */
    public void desattribuerPermissionGroupe(Groupe groupe, Permission permission) {
        logger.debug("desattribuer une permission a un groupe");

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createSQLQuery("DELETE FROM GROUPE_PERMISSION WHERE GROUPE_ID = ? AND PERMISSION_ID=?");
        query.setParameter(0, groupe.getId(), StandardBasicTypes.INTEGER);
        query.setParameter(1, permission.getId(), StandardBasicTypes.INTEGER);
        query.executeUpdate();

    }

    /**
     * Récupérer la liste des Permissions d'un Groupe
     *
     * @param groupe le nom du groupe
     * @return Permissions la liste des permissions
     */
    public List<Permission> permissionsGroupe(Groupe groupe) {
        logger.debug("récupérer les permissions d'un groupe");

        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("SELECT gr.permissions  FROM Groupe gr WHERE gr.id=?");
        query.setParameter(0, groupe.getId(), StandardBasicTypes.INTEGER);

        return query.list();

    }

    //---------------- USERS -----------------
    /**
     * Récupérer un Utilisateur à partir de la base de données
     *
     * @param id l'identifiant dans la base de données
     *
     * @return Utilisateur
     */
    public Utilisateur getUtilisateur(Integer id) {

        logger.debug("récupérer Utilisateur par id");

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM Utilisateur WHERE id=?");

        query.setParameter(0, id, StandardBasicTypes.INTEGER);
        Utilisateur user = (Utilisateur) query.uniqueResult();

        return user;

    }

    /**
     * Récupérer un Utilisateur avec ses dépendances à partir de la base de
     * données
     *
     * @param id l'identifiant dans la base de données
     *
     * @return Utilisateur
     */
    public Utilisateur getUtilisateurAvecDependances(Integer id) {

        logger.debug("récupérer Utilisateur avec ses dépendances par id");

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM Utilisateur WHERE id=?");

        query.setParameter(0, id, StandardBasicTypes.INTEGER);
        Utilisateur user = (Utilisateur) query.uniqueResult();

        return user;

    }

    /**
     * Récupérer un Utilisateur en indiquant son login
     *
     * @param login l'identifiant de l'utilisateur
     *
     * @return Utilisateur
     */
    public Utilisateur getUtilisateurByLogin(String login) {

        logger.debug("récupérer Utilisateur par login");

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM Utilisateur WHERE login=?");

        query.setParameter(0, login, StandardBasicTypes.STRING);
        List users = query.list();
        Utilisateur user = null;
        if (users.size() > 0) {
            user = (Utilisateur) users.get(0);
        } else {
            user = null;
        }

        return user;

    }

    /**
     * Récupérer les informatons d'un Utilisateur via son nodeRef
     *
     * @param nodeRef nodeRef de l'utilisateur
     *
     * @return Informations de l'utilisateur
     */
    public List<String> getInformationsUtilisateurByNodeRef(String nodeRef) {
        logger.debug("récupérer les informations d'un utilisateur par nodeRef");

        List<String> listeInformations = new ArrayList<String>();

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM Utilisateur WHERE nodeRef=?");
        query.setParameter(0, nodeRef, StandardBasicTypes.STRING);

        Utilisateur user = (Utilisateur) query.uniqueResult();

        listeInformations.add(user.getLogin());
        listeInformations.add(user.getPrenom());
        listeInformations.add(user.getNom());
        listeInformations.add(user.getMobile());
        listeInformations.add(user.getEmail());

        return listeInformations;
    }

    /**
     * Récupérer le nombre des utilisateurs appartenant à Groupe selon les
     * paramètres de pagination et de recherche
     *
     * @param start pour la pagination, c'est un nombre entier qui représente
     * l'index du premier élément à afficher
     * @param limit pour la pagination, c'est un nombre entier qui représente
     * l'index du dernier élément à afficher
     * @param idGroupe l'identifiant du groupe dans la base de données
     * @param cin la CIN (le numéro de carte d'identité Nationale) de
     * l'utilisateur
     * @param nom le nom de l'utilisateur
     * @param prenom le prénom de l'utilisateur
     * @param login le login de l'utilisateur
     *
     * @return le nombre des utilisateurs d'un groupe
     */
    public Object nombreUsersGroupe(int start, int limit, Integer idGroupe, String cin, String nom, String prenom, String login) {
        logger.debug("liste des utilisateurs d un groupe");

        Session session = sessionFactory.getCurrentSession();

        String queryRecherche = "";

        if (cin != "") {
            queryRecherche += " AND UPPER(U.cin)='" + cin.toUpperCase() + "' ";
        }

        if (nom != "") {
            queryRecherche += " AND UPPER(U.nom)='" + nom.toUpperCase() + "' ";
        }
        if (prenom != "") {
            queryRecherche += " AND UPPER(U.prenom)='" + prenom.toUpperCase() + "' ";
        }
        if (login != "") {
            queryRecherche += " AND UPPER(U.login)='" + login.toUpperCase() + "' ";
        }

        //queryRecherche = queryRecherche.replaceFirst("and","where");  
        Query query = session.createSQLQuery("SELECT count(*) FROM UTILISATEUR U, UTILISATEUR_GROUPE UG WHERE U.ID=UG.UTILISATEUR_ID AND UG.GROUPE_ID=?" + queryRecherche);
        query.setParameter(0, idGroupe, StandardBasicTypes.INTEGER);

        return query.uniqueResult();

    }

    /**
     * Récupérer la liste des utilisateurs appartenant à Groupe selon les
     * paramètres de pagination et de recherche
     *
     * @param start pour la pagination, c'est un nombre entier qui représente
     * l'index du premier élément à afficher
     * @param limit pour la pagination, c'est un nombre entier qui représente
     * l'index du dernier élément à afficher
     * @param idGroupe l'identifiant du groupe dans la base de données
     * @param cin la CIN (le numéro de carte d'identité Nationale) de
     * l'utilisateur
     * @param nom le nom de l'utilisateur
     * @param prenom le prénom de l'utilisateur
     * @param login le login de l'utilisateur
     *
     * @return liste des Utilisateurs
     */
    public List<Utilisateur> listeUtilisateursGroupe(Integer idGroupe, String cin, String nom, String prenom, String login) {
        logger.debug("liste des utilisateurs d un groupe");

        Session session = sessionFactory.getCurrentSession();

        String queryRecherche = "";

        if (cin != "") {
            queryRecherche += " AND UPPER(U.CIN)='" + cin.toUpperCase() + "' ";
        }

        if (nom != "") {
            queryRecherche += " AND UPPER(U.NOM)='" + nom.toUpperCase() + "' ";
        }
        if (prenom != "") {
            queryRecherche += " AND UPPER(U.PRENOM)='" + prenom.toUpperCase() + "' ";
        }
        if (login != "") {
            queryRecherche += " AND UPPER(U.LOGIN)='" + login.toUpperCase() + "' ";
        }

        //queryRecherche = queryRecherche.replaceFirst("and","where");
        Query query = session.createSQLQuery("SELECT U.ID,U.CIN,U.LOGIN,U.NOM,U.PRENOM,U.EMAIL,U.MOBILE,U.NODEREF,U.ISACTIVE FROM UTILISATEUR U, UTILISATEUR_GROUPE UG WHERE U.ID=UG.UTILISATEUR_ID AND UG.GROUPE_ID=?" + queryRecherche + " ORDER BY U.NOM");
        query.setParameter(0, idGroupe, StandardBasicTypes.INTEGER);

       

        List<Utilisateur> usersList = new ArrayList<Utilisateur>();
        List liste = query.list();
        Utilisateur user = null;

        for (int i = 0; i < liste.size(); i++) {

            Object[] o = (Object[]) liste.get(i);
            user = new Utilisateur();
            user.setId(((Integer) o[0]));
            user.setCin(((String) o[1]));
            user.setLogin((String) o[2]);
            user.setNom((String) o[3]);
            user.setPrenom((String) o[4]);
            user.setEmail((String) o[5]);
            user.setMobile((String) o[6]);
            user.setNodeRef((String) o[7]);
            user.setIsActive((boolean) o[8]);
            usersList.add(user);
        }

        return usersList;
    }

    /**
     * Récupérer le nombre des utilisateurs
     *
     * @param start pour la pagination, c'est un nombre entier qui représente
     * l'index du premier élément à afficher
     * @param limit pour la pagination, c'est un nombre entier qui représente
     * l'index du dernier élément à afficher
     * @param idGroupe l'identifiant du groupe dans la base de données
     * @param cin la CIN (le numéro de carte d'identité Nationale) de
     * l'utilisateur
     * @param nom le nom de l'utilisateur
     * @param prenom le prénom de l'utilisateur
     * @param login le login de l'utilisateur
     *
     * @return nombre des utilisateurs
     */
    public Object nombreUsers(int start, int limit, Integer idGroupe, String cin, String nom, String prenom, String login) {
        logger.debug("liste des utilisateurs d un groupe");

        Session session = sessionFactory.getCurrentSession();

        String queryRecherche = "";

        if (cin != "") {
            queryRecherche += " AND UPPER(U.cin)='" + cin.toUpperCase() + "' ";
        }

        if (nom != "") {
            queryRecherche += " AND UPPER(U.nom)='" + nom.toUpperCase() + "' ";
        }
        if (prenom != "") {
            queryRecherche += " AND UPPER(U.prenom)='" + prenom.toUpperCase() + "' ";
        }
        if (login != "") {
            queryRecherche += " AND UPPER(U.login)='" + login.toUpperCase() + "' ";
        }
        String admin = "admin";
        queryRecherche += " AND U.login!='" + admin + "' ";

        queryRecherche = queryRecherche.replaceFirst("AND", "WHERE");
        Query query = session.createQuery("SELECT count(*) FROM Utilisateur U " + queryRecherche);

        return query.uniqueResult();

    }

    /**
     * Récupérer la liste des utilisateurs
     *
     * @param start pour la pagination, c'est un nombre entier qui représente
     * l'index du premier élément à afficher
     * @param limit pour la pagination, c'est un nombre entier qui représente
     * l'index du dernier élément à afficher
     * @param cin la CIN (le numéro de carte d'identité Nationale) de
     * l'utilisateur
     * @param nom le nom de l'utilisateur
     * @param prenom le prénom de l'utilisateur
     * @param login le login de l'utilisateur
     *
     * @return liste des Utilisateurs
     */
    public List<Utilisateur> listeUtilisateurs(int start, int limit, String cin, String nom, String prenom, String login) {
        logger.debug("liste des utilisateurs d un groupe");

        Session session = sessionFactory.getCurrentSession();

        String queryRecherche = "";

        if (cin != "") {
            queryRecherche += " AND U.CIN='" + cin + "' ";
        }

        if (nom != "") {
            queryRecherche += " AND U.NOM='" + nom + "' ";
        }
        if (prenom != "") {
            queryRecherche += " AND U.PRENOM='" + prenom + "' ";
        }
        if (login != "") {
            queryRecherche += " AND U.LOGIN='" + login + "' ";
        }
        String admin = "admin";
        queryRecherche += " AND U.LOGIN!='" + admin + "' ";

        queryRecherche = queryRecherche.replaceFirst("AND", "WHERE");

        Query query = session.createSQLQuery("SELECT U.ID,U.CIN,U.LOGIN,U.NOM,U.PRENOM,U.EMAIL,U.MOBILE,U.NODEREF FROM UTILISATEUR U" + queryRecherche + " ORDER BY U.NOM");

        query.setFirstResult(start);
        query.setMaxResults(limit);

        List<Utilisateur> usersList = new ArrayList<Utilisateur>();
        List liste = query.list();
        Utilisateur user = null;

        for (int i = 0; i < liste.size(); i++) {

            Object[] o = (Object[]) liste.get(i);
            user = new Utilisateur();
            user.setId(((Integer) o[0]));
            user.setCin(((String) o[1]));
            user.setLogin((String) o[2]);
            user.setNom((String) o[3]);
            user.setPrenom((String) o[4]);
            user.setEmail((String) o[5]);
            user.setMobile((String) o[6]);
            user.setNodeRef((String) o[7]);
            usersList.add(user);
        }

        return usersList;

    }

    /**
     * Récupérer les permissions par défaut (celles qu'il hérite de son Groupe)
     * attribuées à un utilisateur
     *
     * @param idUser l'identifiant de l'utilisateur dans la base de données
     *
     * @return les Permissions attribuées à l'utilisateur
     */
    public List<Permission> defaultUserPermissions(Integer idUser) {
        logger.debug("liste des permissions");

        Session session = sessionFactory.getCurrentSession();

        Utilisateur user = new Utilisateur();
        List<Groupe> GroupesUserSet = new LinkedList<Groupe>();
        List<Permission> listePermissionsUser = new ArrayList<Permission>();

        user = this.getUtilisateur(idUser);
        GroupesUserSet = user.getGroupes();

        Iterator<Groupe> i = GroupesUserSet.iterator();
        while (i.hasNext()) {
            listePermissionsUser.addAll(this.permissionsGroupe(i.next()));
        }

        return listePermissionsUser;

    }

    /**
     * Créer un nouvel Utilisateur dans la base de données
     *
     * @param user instance de Utilisateur. il doit être récupéré au préalabale
     * à partir de la base de données
     *
     * @return l'identifiant de l'Utilisateur
     */
    public Serializable ajouterUtilisateur(Utilisateur user) {

        logger.debug("ajouter un nouveau utilisateur");

        Session session = sessionFactory.getCurrentSession();

        return session.save(user);

    }

    /**
     * Affecter un Utilisateur à un Groupe
     *
     * @param user instance de Utilisateur. il doit être récupéré au préalabale
     * à partir de la base de données
     * @param groupe instance de Groupe. il doit être récupéré au préalabale à
     * partir de la base de données
     */
    public void affecterGroupes(Utilisateur user, Groupe groupe) {
        logger.debug("affecter un utilisateur à un groupe");

        Session session = sessionFactory.getCurrentSession();

        user.getGroupes().add(groupe);

        session.update(user);

    }

    /**
     * Récupérer les Permissions d'un utilisateur
     *
     * @param id l'identifiant de la permission
     *
     * @return Liste des permissions
     */
    public List getIdsPermissionsUser(Integer id) {

        logger.debug("récupérer les ids des permissions");

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("SELECT u.permissions FROM Utilisateur u WHERE u.id=?");

        query.setParameter(0, id, StandardBasicTypes.INTEGER);

        return query.list();
    }

    /**
     * Attribuer des permissions à Utilisateur
     *
     * @param user instance de Utilisateur. il doit être récupéré au préalabale
     * à partir de la base de données
     * @param permissions la liste des permission à attribuer à l'utilisateur
     */
    public void attribuerPermissionUtilisateur(Utilisateur user, List<Permission> permissions) {
        logger.debug("attribuer permissions du groupe à l'utilisateur");

        Session session = sessionFactory.getCurrentSession();

        List<Permission> listPerm = new ArrayList<Permission>();
        List<Integer> listPermIds = new ArrayList<Integer>();
        listPerm = getIdsPermissionsUser(user.getId());

        for (int i = 0; i < listPerm.size(); i++) {

            listPermIds.add(listPerm.get(i).getId());

        }

        for (int i = 0; i < permissions.size(); i++) {

            if (!listPermIds.contains(permissions.get(i).getId())) {

                user.getPermissions().add(permissions.get(i));
            }

        }

        session.update(user);

    }

    /**
     * Récupérer la liste des permissions d'un utilisateur
     *
     * @param user instance de Utilisateur. il doit être récupéré au préalabale
     * à partir de la base de données
     *
     * @return la liste des Permissions
     */
    public List<Permission> permissionsUtilisateur(Utilisateur user) {
        logger.debug("récupérer les permissions d'un utilisateur");

        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("SELECT u.permissions  FROM Utilisateur u WHERE u.id=?");
        query.setParameter(0, user.getId(), StandardBasicTypes.INTEGER);

        return query.list();

    }

    /**
     * Attribuer une Permission à Utilisateur à partir de la base de données
     *
     * @param user instance de Utilisateur. il doit être récupéré au préalabale
     * à partir de la base de données
     * @param permission le nom de la permission à attribuer à l'utilisateur
     */
    public void attribuerPermissionUtilisateur(Utilisateur user, Permission permission) {
        logger.debug("attribuer une permission a un groupe");

        Session session = sessionFactory.getCurrentSession();

        user.getPermissions().add(permission);

        session.update(user);

    }

    /**
     * Désattribuer une Permission d'un Utilisateur
     *
     * @param user instance de Utilisateur. il doit être récupéré au préalabale
     * à partir de la base de données
     * @param permission le nom de la permission
     */
    public void desattribuerPermissionUtilisateur(Utilisateur user, Permission permission) {
        logger.debug("desattribuer une permission a un utilisateur");

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createSQLQuery("DELETE FROM UTILISATEUR_PERMISSION WHERE UTILISATEUR_ID = ? AND PERMISSION_ID=?");
        query.setParameter(0, user.getId(), StandardBasicTypes.INTEGER);
        query.setParameter(1, permission.getId(), StandardBasicTypes.INTEGER);
        query.executeUpdate();

    }

    /**
     * Modifier les informations d'un Utilisateur
     *
     * @param user l'utilisateur à modifier. doit etre récupérer à partir de la
     * base de données puis passé en paramètres
     */
    public void modifierUtilisateur(Utilisateur user) {
        logger.debug("attribuer une permission a un groupe");

        Session session = sessionFactory.getCurrentSession();

        session.update(user);

    }

    /**
     * Récupérer un Utilisateur à partir de la base de données via son nodeRef
     *
     * @param nodeRefUser nodeRef de l'utilisateur
     *
     * @return Utilisateur
     */
    public Utilisateur getUserByNodeRef(String nodeRefUser) {
        logger.debug("récupérer utilisateur moyennant le nodeRef");

        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("SELECT id, nodeRef, login, nom, prenom,cin, email, mobile FROM Utilisateur WHERE nodeRef=?");

        query.setParameter(0, nodeRefUser, StandardBasicTypes.STRING);
//		Utilisateur user= (Utilisateur)query.uniqueResult();

        List list = query.list();
        Utilisateur user = new Utilisateur();
        for (int i = 0; i < list.size(); i++) {

            Object[] o = (Object[]) list.get(i);
            user.setId((Integer) o[0]);
            user.setNodeRef((String) o[1]);
            user.setLogin((String) o[2]);
            user.setNom((String) o[3]);
            user.setPrenom((String) o[4]);
            user.setCin((String) o[5]);
            user.setEmail((String) o[6]);
            user.setMobile((String) o[7]);
        }

        return user;
    }

    /**
     * récupérer l'objet utilisateur avec dépendances(groupes et permissions)
     * par nodeRef
     *
     * @param nodeRefUser nodeRef de l'utilisateur
     *
     * @return Utilisateur
     */
    public Utilisateur getUtilisateurByNodeRef(String nodeRefUser) {
        logger.debug("récupérer l'objet utilisateur avec dépendances  par nodeRef");

        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("FROM Utilisateur WHERE nodeRef=?");

        query.setParameter(0, nodeRefUser, StandardBasicTypes.STRING);
        Utilisateur user = (Utilisateur) query.uniqueResult();

        return user;
    }

    /**
     * Supprimer un Groupe
     *
     * @param groupe instance de l'objet Groupe. doit être récupéré à partir de
     * la base de données puis passé en paramètre
     */
    public void supprimerGroupe(Groupe groupe) {
        logger.debug("Supprimer groupe");

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createSQLQuery("DELETE FROM UTILISATEUR_GROUPE WHERE GROUPE_ID = ?");
        query.setParameter(0, groupe.getId(), StandardBasicTypes.INTEGER);
        query.executeUpdate();

        query = session.createSQLQuery("DELETE FROM GROUPE_PERMISSION WHERE GROUPE_ID = ?");
        query.setParameter(0, groupe.getId(), StandardBasicTypes.INTEGER);
        query.executeUpdate();

        session.delete(groupe);

    }

    /**
     * Supprimer un Utilisateur
     *
     * @param user instance de l'objet Utilisateur. doit être récupéré à partir
     * de la base de données puis passé en paramètre
     */
    public void supprimerUtilisateur(Utilisateur user) {
        logger.debug("Supprimer utilisateur");

        Session session = sessionFactory.getCurrentSession();
        Query query = null;

        query = session.createSQLQuery("DELETE FROM UTILISATEUR_GROUPE WHERE UTILISATEUR_ID = ?");
        query.setParameter(0, user.getId(), StandardBasicTypes.INTEGER);
        query.executeUpdate();

        query = session.createSQLQuery("DELETE FROM UTILISATEUR_PERMISSION WHERE UTILISATEUR_ID = ?");
        query.setParameter(0, user.getId(), StandardBasicTypes.INTEGER);
        query.executeUpdate();

        query = session.createSQLQuery("DELETE FROM CHANTIER_UTILISATEUR WHERE UTILISATEUR_ID = ?");
        query.setParameter(0, user.getId(), StandardBasicTypes.INTEGER);
        query.executeUpdate();

        session.delete(user);
    }

    /**
     * Vérifier si un utilisateur a une permission
     *
     * @param utilisateur instance de l'objet Utilisateur. doit être récupéré à
     * partir de la base de données puis passé en paramètres
     * @param permission instance de l'objet Permission. doit être récupéré à
     * partir de la base de données puis passé en paramètres
     *
     * @return Renvoie True si la suppression est effectuée avec succès sinon
     * False
     */
    public boolean hasPermission(Utilisateur utilisateur, Permission permission) {

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createSQLQuery("SELECT * FROM UTILISATEUR_PERMISSION WHERE UTILISATEUR_ID = ? AND PERMISSION_ID=?");
        query.setParameter(0, utilisateur.getId(), StandardBasicTypes.INTEGER);
        query.setParameter(1, permission.getId(), StandardBasicTypes.INTEGER);

        if (Constantes.getInstance().getRoleAuth().equals("EMAIL_CONTRIBUTORS")) {
            return true;
        }
        if (Constantes.getInstance().getRoleAuth().equals("admin")) {
            return true;
        }
        if (query.list().size() > 0) {
            return true;
        } else {
            return false;
        }

    }

}
