/**
 * @author bservices
 *
 */
package ma.bservices.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import ma.bservices.beans.Chantier;
import ma.bservices.beans.EtatHeuresSupplementaires;
import ma.bservices.beans.Fonction;
import ma.bservices.beans.HeuresSupplementaires;
import ma.bservices.beans.Salarie;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("heuresSupplementairesService")
@Transactional
public class HeuresSupplementairesService {

    protected static Logger logger = Logger.getLogger("service");
    @Resource(name = "sessionFactory")
    private SessionFactory sessionFactory;

    @Resource(name = "administrationService")
    private AdministrationService administrationService;

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

    /**
     * Récupérer le nombre des heures supplémentaires avec ou sans filtres
     *
     * @param matricule le matricule du salarié
     * @param cin la CIN (le numéro de carte d'identité ationale)
     * @param cnss le numéro de CNSS
     * @param etat exemple: "En cours", "Accepté"...
     * @param chantier l'identifiant du chantier dans la base de données
     * @param date format: yyyy/mm/jj
     *
     * @return le nombre des heures supplémentaires
     */
    public Integer nombreHS(String matricule, String cin, String cnss, Integer etat,
            List<Integer> chantiers, Date date) {
        logger.debug("nombre des heures supplémentaires avec ou sans filtre");

        Session session = sessionFactory.getCurrentSession();
        String queryRecherche = "";

        if (matricule == "" && cin == "" && cnss == ""
                && date == null && (chantiers == null) && etat == null) {

            queryRecherche = "SELECT count(*) FROM HeuresSupplementaires";

        } else {

            queryRecherche = "SELECT count(*) FROM HeuresSupplementaires WHERE";

            if (matricule != "") {
                queryRecherche += " AND salarie.matricule='" + StringEscapeUtils.escapeSql(matricule) + "' ";
            }

            if (cin != "") {
                queryRecherche += " AND salarie.cin='" + StringEscapeUtils.escapeSql(cin) + "' ";
            }

            if (cnss != "") {
                queryRecherche += " AND salarie.cnss='" + StringEscapeUtils.escapeSql(cnss) + "' ";
            }

            if (chantiers != null) {

                if (chantiers.size() > 1) {
                    String InValues = "(";

                    for (int i = 0; i < chantiers.size(); i++) {

                        if (i == chantiers.size() - 1) {
                            InValues += " " + chantiers.get(i) + " )";
                        } else {
                            InValues += " " + chantiers.get(i) + ", ";
                        }

                    }

                    queryRecherche += " AND chantier.id IN " + InValues + " ";
                } else if (chantiers.size() > 0) {
                    queryRecherche += " AND chantier.id='" + chantiers.get(0) + "' ";
                }
            }

            if (etat != null) {
                queryRecherche += " AND etat.id='" + etat + "' ";
            }
            if (date != null) {
                queryRecherche += " AND date=:date";
            }

        }
        queryRecherche = queryRecherche.replaceAll("WHERE AND", "WHERE");

        Query query = session.createQuery(queryRecherche);

        if (date != null) {

            query.setParameter("date", date);

        }

        return (int) (long) query.uniqueResult();

    }

    /**
     * Valider (modifier l'état) des Heures Supplementaires
     *
     * @param hs
     */
    public void validerHeuresSupplementaires(HeuresSupplementaires hs) {

        Session session = sessionFactory.getCurrentSession();
        session.update(hs);

    }

    /**
     * Récupérer heures supplémentaires
     *
     * @param id
     * @return HeuresSupplementaires
     */
    public HeuresSupplementaires getHS(Integer id) {

        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("FROM HeuresSupplementaires WHERE ID=?");
        query.setParameter(0, id, StandardBasicTypes.INTEGER);
        return (HeuresSupplementaires) query.uniqueResult();

    }

    /**
     * Récupérer la liste des heures supplémentaires
     *
     * @param start pour la pagination, c'est un nombre entier qui représente
     * l'index du premier élément à afficher
     * @param limit pour la pagination, c'est un nombre entier qui représente
     * l'index du dernier élément à afficher
     * @param matricule le matricule du salarié
     * @param cin la CIN (numéro de la carte d'identité nationale) du salarié
     * @param cnss numéro du cnss
     * @param etat l'identifiant de l'état des heures sup dans la base de
     * données
     * @param chantier l'identifiant du chantier dans la base de données
     * @param date
     *
     * @return Heures Supplémentaires
     */
    public List<HeuresSupplementaires> listeHeuresSupplementaires(int start, int limit, String matricule, String cin, String cnss, Integer etat,
            List<Integer> chantiers, Date date) {
        logger.debug("liste des heures supplémentaires");

        Session session = sessionFactory.getCurrentSession();
        String queryRecherche = "";
        
        

        if (matricule == "" && cin == "" && cnss == "" /*&& nom == "" && prenom == "" */
                && date == null && (chantiers == null ) && etat == null) {

            queryRecherche = "FROM HeuresSupplementaires";

        } else {

            queryRecherche = "FROM HeuresSupplementaires WHERE";

            if (matricule != "") {
                queryRecherche += " AND salarie.matricule='" + StringEscapeUtils.escapeSql(matricule) + "' ";
            }

            if (cin != "") {
                queryRecherche += " AND salarie.cin='" + StringEscapeUtils.escapeSql(cin) + "' ";
            }

            if (cnss != "") {
                queryRecherche += " AND salarie.cnss='" + StringEscapeUtils.escapeSql(cnss) + "' ";
            }

            if (chantiers != null) {

                if (chantiers.size() > 1) {
                    String InValues = "(";

                    for (int i = 0; i < chantiers.size(); i++) {

                        if (i == chantiers.size() - 1) {
                            InValues += " " + chantiers.get(i) + " )";
                        } else {
                            InValues += " " + chantiers.get(i) + ", ";
                        }

                    }

                    queryRecherche += " AND chantier.id IN " + InValues + " ";
                } else if (chantiers.size() > 0) {
                    queryRecherche += " AND chantier.id=" + chantiers.get(0) + " ";
                }
            }

            if (etat != null) {
                queryRecherche += " AND etat.id=" + etat + "";
            }
            if (date != null) {
                queryRecherche += " AND date=:date";
            }
        }

        queryRecherche = queryRecherche.replaceAll("WHERE AND", "WHERE");

        Query query = session.createQuery(queryRecherche + " ORDER BY date desc, id desc");

        if (date != null) {

            query.setParameter("date", date);

        }

        query.setFirstResult(start);
        query.setMaxResults(limit);
        List<HeuresSupplementaires> listeHS = (List<HeuresSupplementaires>) query.list();
        System.out.println("________ Liste SizeHS @@@@@@@@@@@ " + listeHS.size());
        return listeHS;

    }

    /**
     * Créer nouveaux heures supplémentaires
     *
     * @param hs heures supplémentaires à ajouter
     * @return id HS
     */
    public Integer ajouterHS(HeuresSupplementaires hs) {

        logger.debug("Ajouter heures supplémentaires ");

        Session session = sessionFactory.getCurrentSession();

        Integer idHS = (Integer) session.save(hs);
        return idHS;
    }

    /**
     * Supprimer heures supplémentaires
     *
     * @param hs
     */
    public void supprimerHS(HeuresSupplementaires hs) {

        logger.debug("Supprimer heures supplémentaires ");

        Session session = sessionFactory.getCurrentSession();
        session.delete(hs);
    }

    /**
     * modifier l'heures supplémentaires dans la base de données
     *
     * @param hs l'ojet HeuresSupplementaires, il doit, avant de lui passer en
     * paramètres, recevoir les éléments via les setters
     *
     * @return true si la modification est effectuée avec succès
     */
    public boolean modifierHS(HeuresSupplementaires hs) {

        logger.debug("Modifier heures supplémentaires ");

        Session session = sessionFactory.getCurrentSession();
        session.update(hs);
        return true;

    }
    /**
     * 
     * @param idSalarie
     * @param dateHS
     * @param heureDebut format : 2016/09/01
     * @return 
     */
    public boolean heuresSupExist(Integer idSalarie, String dateHS, String heureDebut) {
        Session session = sessionFactory.getCurrentSession();
        Query query = null;

        dateHS = dateHS.replaceAll("-", "/");
        Long dateTimeDebut = new Date(dateHS + " " + heureDebut).getTime();
        query = session.createQuery("SELECT count(*) FROM HeuresSupplementaires WHERE salarie.id=? AND ? Between longDateTimeDebutHS and longDateTimeFinHS");

        query.setParameter(0, idSalarie, StandardBasicTypes.INTEGER);
        query.setParameter(1, dateTimeDebut, StandardBasicTypes.LONG);

        int n = (int) (long) (Long) query.uniqueResult();
        if (n > 0) {
            return true;
        }

        return false;
    }
    
    
     /**
     * 
     * @param idSalarie
     * @param dateHS
     * @param heureDebut format : 2016/09/01
     * @return 
     */
    public boolean heuresSupValidWithEtatExist(Integer idSalarie, String dateHS, String heureDebut, Integer etat_id) {
        Session session = sessionFactory.getCurrentSession();
        Query query = null;

        dateHS = dateHS.replaceAll("-", "/");
        Long dateTimeDebut = new Date(dateHS + " " + heureDebut).getTime();
        query = session.createQuery("SELECT count(*) FROM HeuresSupplementaires WHERE salarie.id=? AND ? Between longDateTimeDebutHS and longDateTimeFinHS AND etat.id=?");

        query.setParameter(0, idSalarie, StandardBasicTypes.INTEGER);
        query.setParameter(1, dateTimeDebut, StandardBasicTypes.LONG);
        query.setParameter(2, etat_id, StandardBasicTypes.INTEGER);

        int n = (int) (long) (Long) query.uniqueResult();
        if (n > 0) {
            return true;
        }

        return false;
    }
    
   

}
