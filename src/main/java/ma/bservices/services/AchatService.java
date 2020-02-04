package ma.bservices.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.xml.ws.Holder;

//import ma.bservices.authentication.CmaUsernamePasswordAuthenticationToken;
import ma.bservices.beans.Article;
import ma.bservices.beans.ArticleBL;
import ma.bservices.beans.ArticleDA;
import ma.bservices.beans.BonLivraison;
import ma.bservices.beans.Chantier;
import ma.bservices.beans.DemandeApprovisionnement;
import ma.bservices.beans.EtatDA;
import ma.bservices.beans.Utilisateur;
import ma.bservices.constantes.Constantes;
import ma.bservices.webservice.Commande;
import ma.bservices.webservice.Commande.Mouvement;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Projections;
import org.hibernate.type.DateType;
import org.hibernate.type.StringType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
//import com.rivetlogic.core.cma.repo.Ticket;

import ma.bservices.tgcc.webService.DivaltoService;
import ma.bservices.tgcc.webService.DivaltoServiceSoap;
import javax.faces.bean.ManagedProperty;
import ma.bservices.beans.Permission;
import ma.bservices.tgcc.service.Engin.UtilisateurService;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author bservices
 *
 */
@Service("achatService")
@Transactional
public class AchatService {

    protected static Logger logger = Logger.getLogger("service");
    @Resource(name = "sessionFactory")
    private SessionFactory sessionFactory;
    private int nbRepGetCommandeWS = 0;
    private int nbRepValidationBLWS = 0;
    private int nbRepDemandeApproWS = 0;

    @Resource(name = "administrationService")
    private AdministrationService administrationService;
    
    @Autowired
    private UtilisateurService utilisateurService;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public AdministrationService getAdministrationService() {
        return administrationService;
    }

    public void setAdministrationService(AdministrationService administrationService) {
        this.administrationService = administrationService;
    }

    // --- DEMANDES APPROVISIONNEMENT ---
    /**
     * Créer une nouvelle Demande d'Approvisionnement
     *
     * @param demandeAprovisionnement : DemandeApprovisionnement
     *
     * @return id Demande Appro
     */
    public Integer ajouterDemandeAppro(DemandeApprovisionnement demandeAprovisionnement) {

        logger.debug("ajouter une nouvelle demande approvisionnement");

        Session session = sessionFactory.getCurrentSession();

        return (Integer) session.save(demandeAprovisionnement);

    }

    /**
     * Récupérer le nombre des demandes d'Approvisionnement selon les critères
     * de recherche passés en paramètres. Si un paramètre de recherche est vide,
     * la recherche par celui ci sera ignorée
     *
     * @param numeroDA numéro de la demande d'approvisionnement
     * @param chantier le nom du chantier
     * @param etat etat de la demande d'appro
     * @param demandeur le nom du demandeur
     * @param dateAjoutDe date de début (correspond à la date de création de la
     * demande d'appro) pour effectuer la recherche sur des demandes créées dans
     * une période (format: yyyy/mm/jj)
     * @param dateAjoutA date de fin (correspond à la date de création de la
     * demande d'appro) pour effectuer la recherche sur des demandes créées dans
     * une période (format: yyyy/mm/jj)
     * @param dateLivraisonSouhaiteeDe date de début (correspond à la date de
     * livraison souhaitée) pour effectuer la recherche dans une période de
     * dates de livraison souhaitée (format: yyyy/mm/jj)
     * @param dateLivraisonSouhaiteeA date de fin (correspond à la date de
     * livraison souhaitée) pour effectuer la recherche dans une période de
     * dates de livraison souhaitée (format: yyyy/mm/jj)
     *
     * @return nombre des demandes d'approvisionnement
     */
    public Object nombreDemandesAppro(String numeroDA, String chantier, String etat, String demandeur, Date dateAjoutDe, Date dateAjoutA, Date dateLivraisonSouhaiteeDe, Date dateLivraisonSouhaiteeA, boolean userIsAdmin) {

        logger.debug("liste des demandes appro");

        Session session = sessionFactory.getCurrentSession();
        String queryRecherche = "";
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Utilisateur User = utilisateurService.getUsersByLogin(auth.getPrincipal().toString());
        
         Permission persmission = administrationService.getPermissionByName("admin");
        Boolean bool = administrationService.hasPermission(User, persmission);
        
        
        Integer idUser = User.getId();
        

        if (!bool) {
//            Ticket ticket = ((CmaUsernamePasswordAuthenticationToken) SecurityContextHolder
//                    .getContext().getAuthentication()).getTicket();
            Query q = session.createSQLQuery("SELECT CE.CHANTIER_ID FROM CHANTIER_UTILISATEUR CE WHERE CE.UTILISATEUR_ID=?");
            q.setParameter(0, idUser, StandardBasicTypes.INTEGER);
            String chantiersAffectes = "";
            List liste = q.list();
            for (int i = 0; i < liste.size(); i++) {
                //			c = new Chantier();
                //			c.setId(((Integer) liste.get(i)));
                chantiersAffectes = chantiersAffectes + liste.get(i).toString() + ",";
            }
            chantiersAffectes = chantiersAffectes.substring(0, chantiersAffectes.lastIndexOf(','));
            queryRecherche = " AND DA.chantier.id in (" + chantiersAffectes + ")";
        }

        if (numeroDA != "") {
            queryRecherche += " AND DA.numeroDA='" + numeroDA + "' ";
        }

        if (chantier != "") {
            queryRecherche += " AND DA.chantier.code='" + StringEscapeUtils.escapeSql(chantier) + "' ";
        }

        if (etat != "") {
            queryRecherche += " AND DA.etatDA.etat='" + StringEscapeUtils.escapeSql(etat) + "' ";
        }

        if (dateAjoutDe != null) {

            if (dateAjoutA == null) {

                queryRecherche += " AND DA.dateAjout=:dateAjoutDe ";
            } else {
                queryRecherche += " AND DA.dateAjout BETWEEN :dateAjoutDe AND :dateAjoutA ";
            }

        }

        if (dateLivraisonSouhaiteeDe != null) {

            if (dateLivraisonSouhaiteeA == null) {

                queryRecherche += " AND DA.dateLivraisonSouhaitee=:dateLivraisonSouhaiteeDe ";

            } else {
                queryRecherche += " AND DA.dateLivraisonSouhaitee BETWEEN :dateLivraisonSouhaiteeDe AND :dateLivraisonSouhaiteeA ";
            }

        }

        queryRecherche = queryRecherche.replaceFirst("AND", "WHERE");
        Query query = session.createQuery("SELECT count(*) FROM DemandeApprovisionnement DA " + queryRecherche);

//		query.setParameter("chantiersAffectes", chantiersAffectes);
        if (dateAjoutDe != null) {

            if (dateAjoutA == null) {

                query.setParameter("dateAjoutDe", dateAjoutDe);

            } else {

                query.setParameter("dateAjoutDe", dateAjoutDe);
                query.setParameter("dateAjoutA", dateAjoutA);
            }

        }

        if (dateLivraisonSouhaiteeDe != null) {

            if (dateLivraisonSouhaiteeA == null) {

                query.setParameter("dateLivraisonSouhaiteeDe", dateLivraisonSouhaiteeDe);

            } else {

                query.setParameter("dateLivraisonSouhaiteeDe", dateLivraisonSouhaiteeDe);
                query.setParameter("dateLivraisonSouhaiteeA", dateLivraisonSouhaiteeA);

            }

        }

        return query.uniqueResult();
    }

    /**
     * Récupérer la liste des demandes d'approvisionnement selon les paramètres
     * de pagination et de recherche. Si un paramètre de recherche est vide, la
     * recherche par celui ci sera ignorée
     *
     * @param numeroDA numéro de la demande d'approvisionnement
     * @param chantier le nom du chantier
     * @param etat etat de la demande d'appro
     * @param demandeur le nom du demandeur
     * @param dateAjoutDe date de début (correspond à la date de création de la
     * demande d'appro) pour effectuer la recherche sur des demandes créées dans
     * une période (format: yyyy/mm/jj)
     * @param dateAjoutA date de fin (correspond à la date de création de la
     * demande d'appro) pour effectuer la recherche sur des demandes créées dans
     * une période (format: yyyy/mm/jj)
     * @param dateLivraisonSouhaiteeDe date de début (correspond à la date de
     * livraison souhaitée) pour effectuer la recherche dans une période de
     * dates de livraison souhaitée (format: yyyy/mm/jj)
     * @param dateLivraisonSouhaiteeA date de fin (correspond à la date de
     * livraison souhaitée) pour effectuer la recherche dans une période de
     * dates de livraison souhaitée (format: yyyy/mm/jj)
     * @param start pour la pagination, c'est un nombre entier qui représente
     * l'index du premier élément à afficher
     * @param limit pour la pagination, c'est un nombre entier qui représente
     * l'index du dernier élément à afficher
     * @param userIsAdmin
     *
     * @return demandes d'approvisionnement
     */
    public List<DemandeApprovisionnement> listeDemandesAppro(String numeroDA, String chantier, String etat, String demandeur, Date dateAjoutDe, Date dateAjoutA, Date dateLivraisonSouhaiteeDe, Date dateLivraisonSouhaiteeA, int start, int limit, boolean userIsAdmin) {

        logger.debug("liste des demandes appro");

        Session session = sessionFactory.getCurrentSession();
        String queryRecherche = "";
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Utilisateur User = utilisateurService.getUsersByLogin(auth.getPrincipal().toString());

        Permission persmission = administrationService.getPermissionByName("admin");
        Boolean bool = administrationService.hasPermission(User, persmission);

        Integer idUser = User.getId();
        if (!bool) {
            Query q = session.createSQLQuery("SELECT CE.CHANTIER_ID FROM CHANTIER_UTILISATEUR CE WHERE CE.UTILISATEUR_ID=?");
            q.setParameter(0, idUser, StandardBasicTypes.INTEGER);
           
            String chantiersAffectes = "";
            List liste = q.list();
            //		Chantier c=null;
            for (int i = 0; i < liste.size(); i++) {
                //			c = new Chantier();
                //			c.setId(((Integer) liste.get(i)));
                chantiersAffectes = chantiersAffectes + liste.get(i).toString() + ",";
            }
            if (chantiersAffectes != "") {
                chantiersAffectes = chantiersAffectes.substring(0, chantiersAffectes.lastIndexOf(','));
            }
            queryRecherche = " AND DA.chantier.id in (" + chantiersAffectes + ")";
        }

        if (numeroDA != "") {
            queryRecherche += " AND DA.numeroDA='" + numeroDA + "' ";
        }

        if (chantier != "") {
            queryRecherche += " AND DA.chantier.code='" + StringEscapeUtils.escapeSql(chantier) + "' ";
        }

        if (etat != "") {
            queryRecherche += " AND DA.etatDA.etat='" + StringEscapeUtils.escapeSql(etat) + "' ";
        }

        if (dateAjoutDe != null) {

            if (dateAjoutA == null) {

                queryRecherche += " AND DA.dateAjout=:dateAjoutDe ";
            } else {
                queryRecherche += " AND DA.dateAjout BETWEEN :dateAjoutDe AND :dateAjoutA ";
            }

        }

        if (dateLivraisonSouhaiteeDe != null) {

            if (dateLivraisonSouhaiteeA == null) {

                queryRecherche += " AND DA.dateLivraisonSouhaitee=:dateLivraisonSouhaiteeDe ";

            } else {
                queryRecherche += " AND DA.dateLivraisonSouhaitee BETWEEN :dateLivraisonSouhaiteeDe AND :dateLivraisonSouhaiteeA ";
            }

        }

        queryRecherche = queryRecherche.replaceFirst("AND", "WHERE");

        Query query = session.createQuery("SELECT id, numeroDA, chantier.code, dateAjout, dateLivraisonSouhaitee, demandeur.nom, etatDA.etat FROM DemandeApprovisionnement DA " + queryRecherche + " order by dateAjout desc, dateLivraisonSouhaitee desc");

//		query.setParameter("chantiersAffectes", chantiersAffectes);
        if (dateAjoutDe != null) {

            if (dateAjoutA == null) {

                query.setParameter("dateAjoutDe", dateAjoutDe);

            } else {

                query.setParameter("dateAjoutDe", dateAjoutDe);
                query.setParameter("dateAjoutA", dateAjoutA);
            }

        }

        if (dateLivraisonSouhaiteeDe != null) {

            if (dateLivraisonSouhaiteeA == null) {

                query.setParameter("dateLivraisonSouhaiteeDe", dateLivraisonSouhaiteeDe);

            } else {

                query.setParameter("dateLivraisonSouhaiteeDe", dateLivraisonSouhaiteeDe);
                query.setParameter("dateLivraisonSouhaiteeA", dateLivraisonSouhaiteeA);

            }

        }

        query.setFirstResult(start);
        query.setMaxResults(limit);

        List listeObjetsDemandesApprovisionnement = query.list();
        List<DemandeApprovisionnement> listeDemandesApprovisionnement = new ArrayList<DemandeApprovisionnement>();

        DemandeApprovisionnement objetDemandeAprovisionnement = null;
        Chantier objetChantier = null;
        Utilisateur objetUtilisateur = null; //demandeur du demande d'approvisionnement
        EtatDA objetEtatDA = null;

        for (int i = 0; i < listeObjetsDemandesApprovisionnement.size(); i++) {

            Object[] o = (Object[]) listeObjetsDemandesApprovisionnement.get(i);

            objetDemandeAprovisionnement = new DemandeApprovisionnement();
            objetDemandeAprovisionnement.setId((Integer) o[0]);
            objetDemandeAprovisionnement.setNumeroDA((String) o[1]);

            objetChantier = new Chantier();
            objetChantier.setCode((String) o[2]);
            objetDemandeAprovisionnement.setChantier(objetChantier);

            String chaineDateAjout = ((Date) o[3]).toString();
            chaineDateAjout = chaineDateAjout.substring(0, 4) + "/" + chaineDateAjout.substring(5, 7) + "/" + chaineDateAjout.substring(8);
            objetDemandeAprovisionnement.setDateAjout(new Date(chaineDateAjout));

            String chaineDA = chaineDateAjout.substring(8, 10) + "/" + chaineDateAjout.substring(5, 7) + "/" + chaineDateAjout.substring(0, 4);
            objetDemandeAprovisionnement.setChaineDateAjout(chaineDA);

            String chaineDateLivraisonSouhaitee = ((Date) o[4]).toString();
            chaineDateLivraisonSouhaitee = chaineDateLivraisonSouhaitee.substring(0, 4) + "/" + chaineDateLivraisonSouhaitee.substring(5, 7) + "/" + chaineDateLivraisonSouhaitee.substring(8);
            objetDemandeAprovisionnement.setDateLivraisonSouhaitee(new Date(chaineDateLivraisonSouhaitee));

            String chaineDLS = chaineDateLivraisonSouhaitee.substring(8, 10) + "/" + chaineDateLivraisonSouhaitee.substring(5, 7) + "/" + chaineDateLivraisonSouhaitee.substring(0, 4);
            objetDemandeAprovisionnement.setChaineDateLivraisonSouhaitee(chaineDLS);

            objetUtilisateur = new Utilisateur();
            objetUtilisateur.setNom((String) o[5]);
            objetDemandeAprovisionnement.setDemandeur(objetUtilisateur);

            objetEtatDA = new EtatDA();
            objetEtatDA.setEtat((String) o[6]);
            objetDemandeAprovisionnement.setEtatDA(objetEtatDA);

            listeDemandesApprovisionnement.add(objetDemandeAprovisionnement);
            //		        	id, numeroDA, chantier.code, dateAjout, dateLivraisonSouhaitee, demandeur.nom, etatDA.etat

        }

        return listeDemandesApprovisionnement;
    }

    /**
     * Modifier une demande d'approvisionnement
     *
     * @param demandeApprovisionnement instance de l'objet
     * DemandeApprovisionnement. il doit récupérée au prélable à partir de la
     * base de données puis passée en paramètres
     */
    public void modifierDemandeAppro(DemandeApprovisionnement demandeApprovisionnement) {

        logger.debug("modifier demande approvisionnement");

        Session session = sessionFactory.getCurrentSession();
        session.update(session.merge(demandeApprovisionnement));

    }

    // Faire de la projection de colonne de la table DemandeApprovisionnement pour ne pas avoir un fetch EAGER
    /**
     * Récupérer une demande d'Approvisionnement via id
     *
     * @param idDA l'identifiant de la demande d'approvisionnement dans la base
     * de données
     * @return Demande d'approvisionnement
     */
    public DemandeApprovisionnement getDemandeAppro(Integer idDA) {
        logger.debug("Demande d'approvisionnement par id");

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM DemandeApprovisionnement WHERE id=?");

        query.setParameter(0, idDA, StandardBasicTypes.INTEGER);

        DemandeApprovisionnement objetDemandeApprovisionnement = (DemandeApprovisionnement) query.uniqueResult();
        return objetDemandeApprovisionnement;

    }

    /**
     * Supprimer une demande d'approvisionnement
     *
     * @param da instance de l'objet DemandeApprovisionnement. elle doit être
     * récupérée à partir de la base de données au préalable puis passée en
     * paramètres
     */
    public void supprimerDA(DemandeApprovisionnement da) {
        logger.debug("Supprimer une DA");

        Session session = sessionFactory.getCurrentSession();
        session.delete(da);
    }

    // --- FIN DEMANDES APPROVISIONNEMENT ---
    // --- ARTICLES --- 
    // --- FIN ARTICLES ---
    // --- ARTICLEDA ---
    //	public Object nombreArticles(String querySearch){
    //
    //		Session session=sessionFactory.getCurrentSession();
    //		
    //		Query query=session.createQuery("SELECT count(*) FROM Article WHERE codeArticle LIKE '%"+StringEscapeUtils.escapeSql(querySearch)+"%'");
    //		 
    //		return query.uniqueResult();
    //		
    //	}
    //	
    //	public List<Article> listeArticles(int start, int limit,String querySearch) {
    //		  logger.debug("liste des codes articles");	
    //		   
    //		  Session session = sessionFactory.getCurrentSession();
    //		  
    //		  Query query=session.createQuery("FROM Article WHERE codeArticle LIKE '%"+StringEscapeUtils.escapeSql(querySearch)+"%' order by codeArticle"); 
    //			query.setFirstResult(start);
    //			query.setMaxResults(limit);
    //			
    //		  return  query.list();
    //		  
    //	}
    //	
    //	public Article getArticle(Integer idArticle){
    //		logger.debug("Article par id");
    //	
    //		Session session = sessionFactory.getCurrentSession();
    //		  		
    //		Query query=session.createQuery("FROM Article WHERE id=?");
    //		
    //		query.setParameter(0, idArticle, Hibernate.INTEGER);
    //		Article article= (Article)query.uniqueResult();
    //		  			
    //	
    //	return article;
    //	
    //}
    /**
     * Créer un nouveau article dans une demande d'approvisionnement
     *
     * @param articleDA instance de l'objet ArticleDA qui correspond à un
     * article de demande d'approvisionnement. elle doit être récupéré à partir
     * de la base de données au préalable puis passée en paramètre.
     *
     * @return l'identifiant de l'article créé
     */
    public Integer ajouterArticleDA(ArticleDA articleDA) {

        logger.debug("ajouter ArticleDA");

        Session session = sessionFactory.getCurrentSession();
        return (Integer) session.save(articleDA);

    }

    // --- FIN ARTICLEDA ---
    // --- BONS DE LIVRAISON ---
    /**
     * Créer un nouveau bon de livraison
     *
     * @param bonLivraison instance de bon de livraison. doit au préalable
     * recevoir les informations du nouveau bon de livraison via les setters
     *
     * @return id du bon de livraison créé
     */
    public Integer ajouterBonLivraison(BonLivraison bonLivraison) {

        logger.debug("ajouter un nouveau BL");

        Session session = sessionFactory.getCurrentSession();
        bonLivraison.setDateCreation(new Date());
        return (Integer) session.save(bonLivraison);

    }

    /**
     * Récupérer un Bon de livraison via id
     *
     * @param idBL l'identifiant du bon de livraison dans la base de données
     * @return Bon de livraison
     */
    public BonLivraison getBonLivraison(Integer idBL) {
        logger.debug("Bon de Livraison par id");

        Session session = sessionFactory.getCurrentSession();

        System.out.println("id bl: " + idBL);
        Query query = session.createQuery("SELECT id, numeroBL, dateLivraison, commentaire, numeroBC, numeroBCSecondaire, nodeRefDocument, fournisseur FROM BonLivraison WHERE id=?");

        query.setParameter(0, idBL, StandardBasicTypes.INTEGER);

        BonLivraison objetBonLivraison = new BonLivraison();

        //System.out.println("obj: "+query.list().get(0));
        Object[] o = (Object[]) query.uniqueResult();

        System.out.println("obj: " + o[0]);

        objetBonLivraison.setId((Integer) o[0]);

        if (o[1] != null) {
            objetBonLivraison.setNumeroBL(StringEscapeUtils.escapeJavaScript((String) o[1]));
        }
        if (o[2] != null) {
            String chaineDateLivraison = ((Date) o[2]).toString();
            chaineDateLivraison = chaineDateLivraison.substring(0, 4) + "/" + chaineDateLivraison.substring(5, 7) + "/" + chaineDateLivraison.substring(8);
            objetBonLivraison.setDateLivraison(new Date(chaineDateLivraison));
        }
        if (o[3] != null) {
            objetBonLivraison.setCommentaire(StringEscapeUtils.escapeJavaScript((String) o[3]));
        }
        if (o[4] != null) {
            objetBonLivraison.setNumeroBC((String) o[4]);
        }
        if (o[5] != null) {
            objetBonLivraison.setNumeroBCSecondaire((String) o[5]);
        }
        if (o[6] != null) {
            objetBonLivraison.setNodeRefDocument((String) o[6]);
        }
        if (o[7] != null) {

            objetBonLivraison.setFournisseur(StringEscapeUtils.escapeJavaScript((String) o[7]));
        }

//		Chantier objetChantier=new Chantier();
//		if(o[8]!=null && o[9]!=null){
//			objetChantier.setId((Integer) o[8]);
//			objetChantier.setCode((String) o[9]);
//			objetBonLivraison.setChantier(objetChantier);
//		}
//
//		Utilisateur objetUtilisateur = new Utilisateur();
//		
//		if(o[10]!=null && o[11]!=null && o[12]!=null){
//			objetUtilisateur.setId((Integer) o[10]);
//			objetUtilisateur.setNom((String) o[11]);
//			objetUtilisateur.setPrenom((String) o[12]);
//			objetBonLivraison.setResponsable(objetUtilisateur);
//		}
        return objetBonLivraison;

    }

    /**
     * Récupérer le nombre des bons de livraison selon les critères de recherche
     * passés en paramètres. Si un paramètre de recherche est vide, la recherche
     * par celui ci sera ignorée
     *
     * @param numeroBL numéro du bon de livraison dans la base de données
     * @param numeroBC numéro de bon de commande
     * @param dateLivraison date de livraison
     * @param chantier l'identifiant du chantier dans la base de données
     * @param responsable l'identifiant du chantier dans la base de données
     *
     * @return le nombre des bon de livraison
     */
    public Object nombreBonsLivraison(String numeroBL, String numeroBC, Date dateLivraison, Integer chantier, Integer responsable, boolean userIsAdmin) {

        logger.debug("nombre des BL");

        Session session = sessionFactory.getCurrentSession();

        String queryRecherche = "";
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Utilisateur User = utilisateurService.getUsersByLogin(auth.getPrincipal().toString());
        Integer idUser = User.getId();

        if (!userIsAdmin) {
//            Ticket ticket = ((CmaUsernamePasswordAuthenticationToken) SecurityContextHolder
//                    .getContext().getAuthentication()).getTicket();
            Query q = session.createSQLQuery("SELECT CE.CHANTIER_ID FROM CHANTIER_UTILISATEUR CE WHERE CE.UTILISATEUR_ID=?");
            q.setParameter(0, idUser, StandardBasicTypes.INTEGER);
            String chantiersAffectes = "";
            List liste = q.list();
            //		Chantier c=null;
            for (int i = 0; i < liste.size(); i++) {
                //			c = new Chantier();
                //			c.setId(((Integer) liste.get(i)));
                chantiersAffectes = chantiersAffectes + liste.get(i).toString() + ",";
            }
            chantiersAffectes = chantiersAffectes.substring(0, chantiersAffectes.lastIndexOf(','));
            queryRecherche = " AND BL.chantier.id in (" + chantiersAffectes + ")";
        }

        if (numeroBL != "") {
            queryRecherche += " AND BL.numeroBL='" + numeroBL + "' ";
        }

        if (numeroBC != "") {
            queryRecherche += " AND BL.numeroBC='" + numeroBC + "' ";
        }
        if (dateLivraison != null) {
            queryRecherche += " AND BL.dateLivraison=? ";
        }

        if (responsable != null) {
            queryRecherche += " AND BL.responsable.id='" + responsable + "' ";
        }
        if (chantier != null) {
            queryRecherche += " AND BL.chantier.id='" + chantier + "' ";
        }

        Query query = session.createQuery("SELECT count(*) FROM BonLivraison BL  WHERE BL.etatBL=1 " + queryRecherche);
        if (dateLivraison != null) {
            query.setParameter(0, dateLivraison, StandardBasicTypes.DATE);
        }

        return query.uniqueResult();
    }

    /**
     * Récuperer les bons de livraison pour effectuer la migration depuis
     * alfresco
     *
     * @return
     */
    public List<BonLivraison> findAllBL() {

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM BonLivraison WHERE nodeRefDocument is not NULL AND nodeRefDocument not like 'files%'");

        //System.out.println("FROM SERVICE: FOUND :: " + ((Contrat)query.uniqueResult()).getNodeRefLegalise());
        List<BonLivraison> lBL = query.list();

        return lBL;

    }

    /**
     * Récupérer la liste des bons de livraison selon les critères de recherche
     * et de pagination passés en paramètres. Si un paramètre de recherche est
     * vide, la recherche par celui ci sera ignorée
     *
     * @param start pour la pagination, c'est un nombre entier qui représente
     * l'index du premier élément à afficher
     * @param limit pour la pagination, c'est un nombre entier qui représente
     * l'index du dernier élément à afficher
     * @param numeroBL numéro du bon de livraison
     * @param numeroBC numéro du bon de commande
     * @param dateLivraison date de livraison (format: yyyy/mm/jj)
     * @param chantier l'identifiant du chantier dans la base de données
     * @param responsable l'identifiant duresponsable (qui a créé le bon de
     * livraison)
     *
     * @return la liste des Bons de livraison
     */
    public List<BonLivraison> listeBonsLivraison(int start, int limit, String numeroBL, String numeroBC, Date dateLivraison, Integer chantier, Integer responsable, boolean userIsAdmin) {

        logger.debug("liste des BL");

        Session session = sessionFactory.getCurrentSession();

        String queryRecherche = "";
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Utilisateur User = utilisateurService.getUsersByLogin(auth.getPrincipal().toString());
        Integer idUser = User.getId();

        if (!userIsAdmin) {
//            Ticket ticket = ((CmaUsernamePasswordAuthenticationToken) SecurityContextHolder
//                    .getContext().getAuthentication()).getTicket();
            Query q = session.createSQLQuery("SELECT CE.CHANTIER_ID FROM CHANTIER_UTILISATEUR CE WHERE CE.UTILISATEUR_ID=?");
            q.setParameter(0, idUser, StandardBasicTypes.INTEGER);
            String chantiersAffectes = "";
            List liste = q.list();
            //		Chantier c=null;
            for (int i = 0; i < liste.size(); i++) {
                //			c = new Chantier();
                //			c.setId(((Integer) liste.get(i)));
                chantiersAffectes = chantiersAffectes + liste.get(i).toString() + ",";
            }
            chantiersAffectes = chantiersAffectes.substring(0, chantiersAffectes.lastIndexOf(','));
            queryRecherche = " AND BL.chantier.id in (" + chantiersAffectes + ")";
        }

        if (numeroBL != "") {
            queryRecherche += " AND BL.numeroBL='" + numeroBL + "' ";
        }

        if (numeroBC != "") {
            queryRecherche += " AND BL.numeroBC='" + numeroBC + "' ";
        }
        if (dateLivraison != null) {
            queryRecherche += " AND BL.dateLivraison=? ";
        }

        if (responsable != null) {
            queryRecherche += " AND BL.responsable.id='" + responsable + "' ";
        }
        if (chantier != null) {
            queryRecherche += " AND BL.chantier.id='" + chantier + "' ";
        }

        Query query = session.createQuery("FROM BonLivraison BL WHERE BL.etatBL=1 " + queryRecherche);
        if (dateLivraison != null) {
            query.setParameter(0, dateLivraison, StandardBasicTypes.DATE);
        }
        query.setFirstResult(start);
        query.setMaxResults(limit);
        List<BonLivraison> listeBonsLivraison = (List<BonLivraison>) query.list();
        return listeBonsLivraison;
    }

    /**
     * Modifier un Bon de livraison
     *
     * @param instance de l'objet BonLivraison. elle doit être récupéré au
     * préalabale à partir de la base de données puis passée en paramètres
     */
    public void modifierBonLivraison(BonLivraison bonLivraison) {

        logger.debug("modifier BL");

        Session session = sessionFactory.getCurrentSession();
        session.update(bonLivraison);

    }

    /**
     * Récupérer le nombre des articles d'une demande d'approvisionnement
     *
     * @param idDA l'identifiant de la demande d'approvisionnement dans la base
     * de données
     *
     * @return le nombre des demandes d'appro
     */
    public Object nombreArticlesDemandeApprovisionnement(Integer idDA) {
        logger.debug("liste des articles des demandes d'approvisionnement");

        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("SELECT count(*) FROM ArticleDA WHERE demandeApprovisionnement.id=?");
        query.setParameter(0, idDA, StandardBasicTypes.INTEGER);

        return query.uniqueResult();

    }

    /**
     * Récupérer la liste des articles d'une demande d'approvisionnement
     *
     * @param idDA l'identifiant de la demande d'appro
     *
     * @return la liste des articles
     */
    public List<ArticleDA> articlesDemandeApprovisionnement(Integer idDA) {
        logger.debug("liste des articles d'une demande d'approvisionnement");

        //			article,demandeApprovisionnement,quantiteArticle,dateLivraisonSouhaitee,commentaire
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("FROM ArticleDA WHERE demandeApprovisionnement.id=? order by refArticle");
        query.setParameter(0, idDA, StandardBasicTypes.INTEGER);

        // List listeObjetsArticlesDemandesApprovisionnement = query.list();
        List<ArticleDA> listeArticlesDemandesApprovisionnement = (List<ArticleDA>) query.list();
//        ArticleDA objetArticleDA = null;
//        Article objetArticle = null;
//        DemandeApprovisionnement da = null;
//
//        for (int i = 0; i < listeObjetsArticlesDemandesApprovisionnement.size(); i++) {
//
//            Object[] o = (Object[]) listeObjetsArticlesDemandesApprovisionnement.get(i);
//
//            objetArticleDA = new ArticleDA();
//            objetArticleDA.setId((Integer) o[0]);
//
//            objetArticle = new Article();
//            objetArticle.setId((Integer) o[1]);
//            objetArticle.setCodeArticle((String) o[2]);
//            objetArticle.setDesignation((String) o[3]);
//            objetArticle.setUnite((String) o[4]);
//
//            objetArticleDA.setArticle(objetArticle);
//
//            objetArticleDA.setQuantiteArticle((Float) o[5]);
//
//            da = new DemandeApprovisionnement();
//            da.setId((Integer) o[6]);
//            objetArticleDA.setDemandeApprovisionnement(da);
//
//            //				String chaineDateLivraisonSouhaitee = ((Date) o[5]).toString();
//            //		    	chaineDateLivraisonSouhaitee = chaineDateLivraisonSouhaitee.substring(0,4) + "/" + chaineDateLivraisonSouhaitee.substring(5,7) + "/" + chaineDateLivraisonSouhaitee.substring(8);
//            //		    	objetArticleDA.setDateLivraisonSouhaitee(new Date(chaineDateLivraisonSouhaitee));
//            listeArticlesDemandesApprovisionnement.add(objetArticleDA);
//
//        }
        return listeArticlesDemandesApprovisionnement;

    }

    /**
     * Récupérer un article d'une demande d'appro
     *
     * @param idArticleDA l'identifiant de l'ArticleDA dans la base de données
     * qui correspond à un article d'une demande d'appro
     * @return Article d'une demande d'appro
     */
    public ArticleDA getArticleDA(Integer idArticleDA) {
        logger.debug("récupérer Article demande approvisionnement par id");

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM ArticleDA WHERE id=?");

        query.setParameter(0, idArticleDA, StandardBasicTypes.INTEGER);
        ArticleDA articleDA = (ArticleDA) query.uniqueResult();

        return articleDA;

    }

    /**
     * Modifier un article d'une demande d'approvisionnnement
     *
     * @param articleDA
     */
    public void modifierArticleDA(ArticleDA articleDA) {

        logger.debug("modifier article demande appro");

        Session session = sessionFactory.getCurrentSession();
        session.update(articleDA);

    }

    /**
     * Supprimer un article d'une demande d'approvisionnement
     *
     * @param ada instance de l'objet ArticleDA. doit être récupéré au préalable
     * à partir de la base de données puis passé en paramètres
     */
    public void supprimerArticleDA(ArticleDA ada) {

        logger.debug("Supprimer article DA ");

        Session session = sessionFactory.getCurrentSession();
        session.delete(ada);
    }

    // --- FIN BONS DE LIVRAISON ---
    /**
     * Appel du web service pourRécupérer une commande via son numéro à partir
     * de l'ERP Divalto. le résultat en Json est mappé en Objet Commande en
     * utilisant l'api GSON
     *
     * @param numeroCommande numéro de la commande
     *
     * @return Commande
     */
    public Commande getCommande(String numeroCommande) {

        Commande commande = new Commande();
        try {
            DivaltoService divaltoService = new DivaltoService();

            DivaltoServiceSoap divaltoServiceSoap = divaltoService.getDivaltoServiceSoap();

            Holder<Integer> hI = new Holder<>();
            Holder<String> hS = new Holder<>();
            System.out.println("get Commande Ws ");
            divaltoServiceSoap.webServiceDiva("<ACTION>WSTGCC", "<Task>getcommande<pino>" + numeroCommande, hI, hS);
            //divaltoServiceSoap.webServiceDiva("<ACTION>TESTWS1", "<Task>getcommande<pino>" + numeroCommande, hI, hS);

            String chaine = StringEscapeUtils.unescapeHtml(hS.value);
            System.out.println("json: " + chaine);
//		if(hI.value==8212 && nbRepGetCommandeWS<=Constantes.getInstance().nbreRepWS){
//			Thread.sleep(100);
//			nbRepGetCommandeWS++;
//			getCommande(numeroCommande);
//		}
            if (chaine.startsWith("{") && chaine.endsWith("}")) {

                //Utilisation de l'api Google-gson pour la conversion du json en objet java "Commande"
                Gson gson = new Gson();
                commande = gson.fromJson(chaine, Commande.class);

            } else {
                commande = null;
            }

        } catch (Exception e) {
            //e.printStackTrace();
            //System.out.println("Failed to access the WSDL at: http://192.168.0.202:81/webservicediva.asmx?wsdl");
            System.out.println(e.getMessage());
            return null;
        }
        return commande;

    }

    /**
     * Appel du web service pour valider un bon de livraison au niveau Divalto
     *
     * @param numeroBonCommande le numéro du bon de commande associée au bon de
     * livraison
     * @param numeroBonLivraison le numéro de bon de livraison du fournisseur
     * @param dateLiv date de livraison (format: aaaammjj)
     * @param commentaire information supplémentaires
     * @param articlesQuantites les références articles avec les quantités.
     * (exemple: 21254-10;25481-20)
     * @return réponse du web service sous forme de Map
     */
    public Map<String, String> validationBLWS(String numeroBonCommande,
            String numeroBonLivraison, String dateLiv,
            String commentaire, String articlesQuantites) {

        Map<String, String> mapValidationBLWS = new HashMap<String, String>();
        String referenceBLDiva = "";
        String nombreArticleBLDiva = "";
        try {
            DivaltoService divaltoService = new DivaltoService();

            DivaltoServiceSoap divaltoServiceSoap = divaltoService.getDivaltoServiceSoap();

            Holder<Integer> hI = new Holder<Integer>();
            Holder<String> hS = new Holder<String>();
            divaltoServiceSoap.webServiceDiva("<ACTION>WSTGCC", "<Task>validation_bl<pino>" + numeroBonCommande + "<num_piece_fou>" + numeroBonLivraison + "<date_livr>" + dateLiv + "<comment>" + commentaire + "<enrno_qte>" + articlesQuantites + "@@@", hI, hS);

            String chaine = StringEscapeUtils.unescapeHtml(hS.value);
            System.out.println("requete validationBL WS: <Task>validation_bl<pino>" + numeroBonCommande + "<num_piece_fou>" + numeroBonLivraison + "<date_livr>" + dateLiv + "<comment>" + commentaire + "<enrno_qte>" + articlesQuantites);
            System.out.println("reponse validationBL WS: " + chaine);

//		if(hI.value==8212 && nbRepValidationBLWS<=Constantes.getInstance().nbreRepWS){
//			Thread.sleep(100);
//			nbRepValidationBLWS++;
//			validationBLWS( numeroBonCommande,
//					   numeroBonLivraison,  dateLiv,
//					   commentaire,  articlesQuantites);
//		}
            if (chaine.trim().startsWith("{") && chaine.trim().endsWith("}")) {
                int ind1 = chaine.indexOf(":") + 2;
                int ind2 = chaine.indexOf("\"", ind1);
                referenceBLDiva = chaine.substring(ind1, ind2);

                int ind3 = chaine.lastIndexOf(":") + 2;
                int ind4 = chaine.lastIndexOf("\"");
                nombreArticleBLDiva = chaine.substring(ind3, ind4);

                mapValidationBLWS.put("referenceBLDiva", referenceBLDiva);
                mapValidationBLWS.put("nombreArticleBLDiva", nombreArticleBLDiva);
            } else {
                mapValidationBLWS.put("messageErreurDivalto", chaine);
                mapValidationBLWS.put("referenceBLDiva", "-4");
                mapValidationBLWS.put("nombreArticleBLDiva", "0");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            mapValidationBLWS.put("messageErreurDivalto", e.getMessage());
            mapValidationBLWS.put("referenceBLDiva", "-4");
        }
        return mapValidationBLWS;
    }

    /**
     * Récupérer les informations d'une commande
     *
     * @param numeroCommande numéro de commande
     *
     * @return Informations de la commande
     */
    public Map<String, String> getInfosCommande(String numeroCommande) {

        Map<String, String> infos = new HashMap<String, String>();

        Commande commande = new Commande();
        commande = getCommande(numeroCommande);

        if (commande != null && commande.getPiece().getEntete().getPino() != "-1") {

            infos.put("commandeExiste", "oui");
            infos.put("numeroCommande", commande.getPiece().getEntete().getPino());
            infos.put("nomFournisseur", commande.getPiece().getEntete().getNom_fou());
            infos.put("nomChantier", commande.getPiece().getEntete().getChantier());
            infos.put("codeChantier", commande.getPiece().getEntete().getCodchantier());

            if (commande.getMouvements().length == 0) {
                infos.put("livraisonTotale", "oui");
            } else {
                infos.put("livraisonTotale", "non");
            }

        } else {

            infos.put("commandeExiste", "non");

        }

        return infos;
    }

    /**
     * Récupérer les articles d'un bon de livraison
     *
     * @param idBL l'identifiant du bon de livraison
     *
     * @return Articles du Bon de livraison
     */
    public List<ArticleBL> getArticlesBL(Integer idBL) {

        List<ArticleBL> articlesBL = new ArrayList<ArticleBL>();
        Mouvement mouvements[];
        ArticleBL articleBL = null;
        Article article = null;
        BonLivraison bonLivraison = null;
        String dos = Constantes.getInstance().numeroDossierDivalto;

        bonLivraison = getBonLivraison(idBL);
        //bonLivraison = getBL(idBL);
        mouvements = getCommande(bonLivraison.getNumeroBC()).getMouvements();

        for (int i = 0; i < mouvements.length; i++) {

            article = getArticleByRef(mouvements[i].getRef(), Integer.parseInt(dos));
            articleBL = new ArticleBL();

            articleBL.setRefArticle(article.getCodeArticle());
            articleBL.setArticle(article);
            articleBL.setBonLivraison(bonLivraison);
            articleBL.setQuantiteInitiale(Float.valueOf(mouvements[i].getQteinitial().replace(",", ".")));
            articleBL.setQuantiteValidee(Float.valueOf(mouvements[i].getQtevalidee().replace(",", ".")));
            articleBL.setReste(Float.valueOf(mouvements[i].getQuantite().replace(",", ".")));
            articleBL.setNumeroDivalto(mouvements[i].getEnrNo());

            //Integer numeroArticleBL=creerArticleBL(articleBL);
            articlesBL.add(articleBL);
        }

        return articlesBL;
    }

    /**
     * Créer un article pour une bon de livraison
     *
     * @param articleBL instance de l'objet ArticleBL. il faut, dans un premier
     * lieu, utiliser les setters pour créer cet objet puis le passer en
     * paramètres
     *
     * @return id article BL
     */
    public Integer creerArticleBL(ArticleBL articleBL) {

        logger.debug("creer un nouveau bon de livraison");

        Session session = sessionFactory.getCurrentSession();

        return (Integer) session.save(articleBL);

    }

    /**
     * Récupérer un article via la référence
     *
     * @param refArticle la référence de l'Article
     *
     * @return Article
     */
    public Article getArticleByRef(String refArticle, Integer dos) {
        logger.debug("Article par reference");

        Session session = sessionFactory.getCurrentSession();

        //Query query=session.createQuery("FROM Article WHERE codeArticle=? AND dos=? AND fam1!=? AND fam2!=? and fam3!=? and fam3 not like ?");
        Query query = session.createQuery("FROM Article WHERE dos=" + dos + " AND codeArticle=?");

        query.setParameter(0, refArticle, StandardBasicTypes.STRING);

//		query.setParameter(1, 700, Hibernate.STRING);
//		query.setParameter(2, "", Hibernate.STRING);
//		query.setParameter(3, "", Hibernate.STRING);
//		query.setParameter(4, "", Hibernate.STRING);
//		query.setParameter(5, "_", Hibernate.STRING);
        Article article = (Article) query.uniqueResult();

        return article;
    }

    /**
     * Récupérer un article d'un Bon de livraison
     *
     * @param idArticleBL l'identifiant de ArticleBL dans la base de données. il
     * corresponds à un article associé à un bon de livraison
     *
     * @return Article BL
     */
    public ArticleBL getArticleBL(Integer idArticleBL) {
        logger.debug("Article bon de livraison");

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM ArticleBL WHERE id=?");

        query.setParameter(0, idArticleBL, StandardBasicTypes.INTEGER);
        ArticleBL articleBL = (ArticleBL) query.uniqueResult();

        return articleBL;
    }

    /**
     * Récupérer un bon de livraison
     *
     * @param idBL l'identifiant du bon de livraison dans la base de données
     *
     * @return Bon de livraison
     */
    public BonLivraison getBL(Integer idBL) {
        logger.debug("Bon de Livraison par id");

        Session session = sessionFactory.getCurrentSession();

        System.out.println("id bl: " + idBL);
        Query query = session.createQuery("FROM BonLivraison WHERE id=?");

        query.setParameter(0, idBL, StandardBasicTypes.INTEGER);

        return (BonLivraison) query.uniqueResult();

    }

    /**
     * Modifier un Article BL
     *
     * @param articleBL
     */
    public void modifierArticleBL(ArticleBL articleBL) {

        logger.debug("modifier article bon de livraison");

        Session session = sessionFactory.getCurrentSession();
        session.update(articleBL);

    }

    /**
     * Récupérer les articles d'un bon de livraison
     *
     * @param idBL l'identifiant du bon de livraison
     *
     * @return Article bon de livraison
     */
    public List<ArticleBL> getArticlesBLByIdBL(Integer idBL) {
        logger.debug("récupérer les arcticles d'un bon de livraison");

//		Session session = sessionFactory.getCurrentSession();
//
//		Query query=session.createQuery("FROM ArticleBL ABL WHERE ABL.bonLivraison.id=?");
//
//		query.setParameter(0, idBL, Hibernate.INTEGER);
//
//
//		//return (List<ArticleBL>) query.list();
        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM ArticleBL WHERE bonLivraison.id=?");

        query.setParameter(0, idBL, StandardBasicTypes.INTEGER);

        List<ArticleBL> listeArticlesBL = query.list();
        System.out.println("liste article BL " + listeArticlesBL.size());
        return listeArticlesBL;

    }

    /**
     * Appel au web services qui se charge de créer une demmande
     * d'approvisionnement sur Divalto (ERP)
     *
     * @param listeArticleQuantite la liste des articles à commander avec leurs
     * quantités (exemple:"") "
     * @param dateLivraisonSouhaitee date de livraison souhaitée format:yyyymmjj
     * @param codeChantier code du chantier
     * @param demandeur le nom du demandeur
     * @return référence de la demande d'approvisionnement créée sur Divalto
     * avec le nombre des artciles demandés
     */
    public Map<String, String> commandeInterne_trsDepot(String listeArticleQuantite,String dateLivraisonSouhaitee, String codeChantier, String demandeur, String commentaire) {

        Map<String, String> mapCmdInterne = new HashMap<String, String>();
        String referenceDADiva = "";
        String nombreArticleDADiva = "";
        System.out.println("commande interne");
        try {
            DivaltoService divaltoService = new DivaltoService();
            DivaltoServiceSoap divaltoServiceSoap = divaltoService.getDivaltoServiceSoap();
            System.out.println("try");
            Holder<Integer> hI = new Holder<>();
            Holder<String> hS = new Holder<>();
            System.out.println("requete DA WS avant exec: <Task>commandeInterne_trsDepot_v2<ref_qte>" + StringEscapeUtils.unescapeHtml(listeArticleQuantite) + "<dateS>" + dateLivraisonSouhaitee + "<chantier>" + codeChantier + "<demandeur>" + demandeur + "<commentaire>" + StringEscapeUtils.unescapeHtml(commentaire));

//            divaltoServiceSoap.webServiceDiva("<ACTION>WSTGCC", "<Task>commandeInterne_trsDepot<ref_qte>" + StringEscapeUtils.unescapeHtml(listeArticleQuantite) + "<dateS>" + dateLivraisonSouhaitee + "<chantier>" + codeChantier + "<demandeur>" + demandeur + "<commentaire>" + StringEscapeUtils.unescapeHtml(commentaire), hI, hS);
            divaltoServiceSoap.webServiceDiva("<ACTION>WSTGCC", "<Task>commandeInterne_trsDepot_v2<ref_qte>" + StringEscapeUtils.unescapeHtml(listeArticleQuantite) + "<dateS>" + dateLivraisonSouhaitee + "<chantier>" + codeChantier + "<demandeur>" + demandeur + "<commentaire>" + StringEscapeUtils.unescapeHtml(commentaire), hI, hS);
            String chaine = StringEscapeUtils.unescapeHtml(hS.value);

//			if(hI.value==8212 && nbRepDemandeApproWS<=Constantes.getInstance().nbreRepWS){
//				Thread.sleep(100);
//				nbRepDemandeApproWS++;
//				commandeInterne_trsDepot(listeArticleQuantite, dateLivraisonSouhaitee,  codeChantier, demandeur);
//			}
            System.out.println("requete cmd interne WS: <Task>commandeInterne_trsDepot_v2<ref_qte>" + StringEscapeUtils.unescapeHtml(listeArticleQuantite) + "<dateS>" + dateLivraisonSouhaitee + "<chantier>" + codeChantier + "<demandeur>" + demandeur + "<commentaire>" + StringEscapeUtils.unescapeHtml(commentaire));
            System.out.println("reponse cmd interne WS: " + chaine);

            // {"referenceDADivalto":"0", "nombreArticlesDADivalto":"0"}
            if (chaine.trim().startsWith("{") && chaine.trim().endsWith("}")) {
                System.out.println("reussit appel");
                int ind1 = chaine.indexOf(":") + 2;
                int ind2 = chaine.indexOf("\"", ind1);
                referenceDADiva = chaine.substring(ind1, ind2);

                int ind3 = chaine.lastIndexOf(":") + 2;
                int ind4 = chaine.indexOf("\"", ind3);
                nombreArticleDADiva = chaine.substring(ind3, ind4);

                mapCmdInterne.put("referenceDADiva", referenceDADiva);
                mapCmdInterne.put("nombreArticleDADiva", nombreArticleDADiva);
            } else {
                System.out.println("appel echoue");
                mapCmdInterne.put("referenceDADiva", "-1");
                mapCmdInterne.put("nombreArticleDADiva", "0");
            }
        } catch (Exception e) {
            System.out.println("erreur : " + e.getMessage());
            mapCmdInterne.put("referenceDADiva", "-1");
            mapCmdInterne.put("nombreArticleDADiva", "0");
        }

        return mapCmdInterne;
    }

    /**
     * Récupérer le nombre des articles après la recherche en utilisant les
     * critère passés en paramètres
     *
     * @param querySearch une partie(mot clé) ou la totalité de la référence de
     * l'article
     * @param fam1 famille 1 de l'article
     * @param fam2 famile 2 de l'article
     * @param fam3 famille 3 de l'article
     * @param reqDos requete de dossier(s) de recherche des articles
     * @param designation la designation de l'article
     * @return le nombre des article recherchés
     */
    public Object nombreRechercheArticle(String querySearch, String fam1, String fam2, String fam3, String reqDos, String designation) {

        logger.debug("nombre des BL");

        Session session = sessionFactory.getCurrentSession();
        String famille = "";
        if (!"".equals(fam3)) {
            famille = fam3;
        } else if ("".equals(fam3) && !"".equals(fam2)) {
            famille = fam2.substring(1, 6);
        } else if ("".equals(fam3) && "".equals(fam2) && !"".equals(fam1)) {
            famille = fam1.substring(1, 4);
        }
        System.out.println("famille " + famille);

        Criteria criteria = session.createCriteria(Article.class);
        criteria.setProjection(Projections.rowCount());
        criteria.add(
                Expression.sql("FAM_0001!=? ", "", new StringType())
        );
        criteria.add(
                Expression.sql(reqDos + " AND DES like ? collate SQL_Latin1_General_Cp437_CI_AI", "%" + designation + "%", new StringType())
        );
        criteria.add(
                Expression.sql(" upper(REF) like ? collate SQL_Latin1_General_Cp437_CI_AI", "%" + StringEscapeUtils.escapeSql(querySearch).toUpperCase() + "%", new StringType())
        );
        criteria.add(
                Expression.sql(" upper(FAM_0001) like '" + famille.toUpperCase() + "%' collate SQL_Latin1_General_Cp437_CI_AI")
        );
        criteria.add(
                Expression.sql(" (HSDT is null or HSDT>?)", new Date(), new DateType())
        );

        return criteria.uniqueResult();
//		return criteria.list();
//		
//		Query query=session.createQuery("SELECT count(* )FROM Article WHERE "+reqDos+" AND codeArticle LIKE '%"+StringEscapeUtils.escapeSql(querySearch)+"%' AND upper(designation) like '%"+designation.toUpperCase()+"%' AND fam3!=?  AND upper(REF) LIKE '"+famille.toUpperCase()+"%'");
//		query.setParameter(0, "", Hibernate.STRING);
//		
//		return query.uniqueResult();

    }

    /**
     * Récupérer la liste des articles après la recherche en utilisant les
     * critère passés en paramètres
     *
     * @param start pour la pagination, c'est un nombre entier qui représente
     * l'index du premier élément à afficher
     * @param limit pour la pagination, c'est un nombre entier qui représente
     * l'index du dernier élément à afficher
     * @param querySearch une partie(mot clé) ou la totalité de la référence de
     * l'article
     * @param fam1 famille 1 de l'article
     * @param fam2 famile 2 de l'article
     * @param fam3 famille 3 de l'article
     * @param reqDos requete de dossier(s) de recherche des articles
     * @param designation la designation de l'article
     * @return la liste des articles recherchés
     */
    public List<Article> listeRechercheArticle(int start, int limit, String querySearch, String fam1, String fam2, String fam3, String reqDos, String designation) {

        logger.debug("nombre des BL");

        Session session = sessionFactory.getCurrentSession();
        String famille = "";
        if (!"".equals(fam3)) {
            famille = fam3;
        } else if ("".equals(fam3) && !"".equals(fam2)) {
            famille = fam2.substring(1, 6);
        } else if ("".equals(fam3) && "".equals(fam2) && !"".equals(fam1)) {
            famille = fam1.substring(1, 4);
        }
        System.out.println("famille " + famille);

        Criteria criteria = session.createCriteria(Article.class);
        criteria.add(
                Expression.sql("FAM_0001!=? ", "", new StringType())
        );
        criteria.add(
                Expression.sql(reqDos + " AND DES like ? collate SQL_Latin1_General_Cp437_CI_AI", "%" + designation + "%", new StringType())
        );
        criteria.add(
                Expression.sql(" upper(REF) like ? collate SQL_Latin1_General_Cp437_CI_AI", "%" + StringEscapeUtils.escapeSql(querySearch).toUpperCase() + "%", new StringType())
        );
        criteria.add(
                Expression.sql(" upper(FAM_0001) like '" + famille.toUpperCase() + "%' collate SQL_Latin1_General_Cp437_CI_AI")
        );
        criteria.add(
                Expression.sql(" (HSDT is null or HSDT>?)", new Date(), new DateType())
        );
        criteria.setFirstResult(start);
       // criteria.setMaxResults(limit);
        return criteria.list();

//		Query query=session.createQuery("FROM Article WHERE "+reqDos+" AND upper(codeArticle) LIKE '%"+StringEscapeUtils.escapeSql(querySearch).toUpperCase()+"%' AND upper(designation) like '%"+designation.toUpperCase()+"%' AND fam3!=?  AND upper(REF) LIKE '"+famille.toUpperCase()+"%'");
//		query.setParameter(0, "", Hibernate.STRING);
//		
//		query.setFirstResult(start);
//		query.setMaxResults(limit);
//		
//		return query.list();
    }

    public boolean articleExiste(String codeArticle) {

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("SELECT count(*) FROM Article WHERE codeArticle LIKE '" + codeArticle + "%'");

        if ((int) (long) query.list().size() > 0) {
            return true;
        }

        return false;
    }

    public boolean articleDADejaExiste(String codeArticle, Integer da) {

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createSQLQuery("SELECT count(*) FROM ARTICLEDA WHERE REF LIKE '" + codeArticle + "%' and demandeApprovisionnement_id=" + da + "");
        //query.setParameter(0, da, Hibernate.INTEGER);
        System.out.println("article deja ajouté: " + (int) (Integer) query.uniqueResult());
        if ((int) (Integer) query.uniqueResult() > 0) {
            return true;
        }

        return false;
    }

    /**
     * vérifier si un bon de livraison existe déjà (c-a-d déjà validé)
     *
     * @param numeroBL numéro de bon de livraison
     * @return true si validé sinon False
     */
    public boolean verifierNumeroBonLivraison(String numeroBL, String fournisseur) {

        logger.debug("verifier l'existence du numero de BL");

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("SELECT id FROM BonLivraison WHERE numeroBL=? and fournisseur=? and etatBL=1");
        query.setParameter(0, numeroBL, StandardBasicTypes.STRING);
        query.setParameter(1, fournisseur, StandardBasicTypes.STRING);
        if (query.list().size() > 0) {
            return true;
        }

        return false;
    }

    public boolean blAndBcIsUnique(String numeroBL, String fournisseur, String numeroBC) {

        logger.debug("verifier l'existence du numero de BL");

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("SELECT id FROM BonLivraison WHERE numeroBL=? and fournisseur=? and numeroBC=? and etatBL=1");
        query.setParameter(0, numeroBL, StandardBasicTypes.STRING);
        query.setParameter(1, fournisseur, StandardBasicTypes.STRING);
        query.setParameter(2, numeroBC, StandardBasicTypes.STRING);
        if (query.list().size() > 0) {
            return true;
        }

        return false;
    }

    /**
     * @return le nombre des utilisateurs qui ont validé des bons de livrison
     */
    public Object nombreUtilisateursValidationBL() {

        logger.debug("nombre des utilisateurs responsables des bons de livraison");

        Session session = sessionFactory.getCurrentSession();
        Query query = session.createSQLQuery("SELECT count(distinct responsable_ID) FROM BONLIVRAISON ");

        return query.uniqueResult();

    }

    /**
     * Récupère la liste des utilisateurs qui ont validé des bon de livraison
     *
     * @param start
     * @param limit
     * @param query
     * @return liste des utilisateurs
     */
    public List<Utilisateur> listeUtilisateursValidationBL(int start, int limit,
            String querySearch) {

        logger.debug("nombre des utilisateurs responsables des bons de livraison");

        List<Utilisateur> listeUtilisateurs = new ArrayList<Utilisateur>();
        Utilisateur user = null;

        Session session = sessionFactory.getCurrentSession();
        Query query = session.createSQLQuery("SELECT ID,LOGIN FROM UTILISATEUR WHERE LOGIN LIKE '%" + StringEscapeUtils.escapeSql(querySearch).toLowerCase() + "%' and ID IN (SELECT DISTINCT(responsable_ID) FROM BONLIVRAISON) ");
        query.setFirstResult(start);
        query.setMaxResults(limit);
        List users = query.list();
        for (int i = 0; i < users.size(); i++) {

            Object[] o = (Object[]) users.get(i);

            user = new Utilisateur();

            user.setId((Integer) o[0]);
            user.setLogin((String) o[1]);
            listeUtilisateurs.add(user);
        }

        return listeUtilisateurs;

    }

    public boolean demandeApproValidee(Integer idDa) {

        logger.debug("vérifier si la demande d'appro est validée");

        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("SELECT numeroDA from DemandeApprovisionnement where id=?");
        query.setParameter(0, idDa, StandardBasicTypes.INTEGER);
        String numeroDA = (String) query.uniqueResult();
        if (numeroDA != "" && numeroDA != null) {
            return true;
        } else {
            return false;
        }
    }

}
