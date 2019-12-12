package ma.bservices.services;

import java.util.List;

import javax.annotation.Resource;
import ma.bservices.beans.Enfant;

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
@Service("enfantService")
@Transactional
public class EnfantService {

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
     * Récupérer le nombre d'enfants d'un salarié
     *
     * @param idSalarie l'identifiant du salarié dans la base de données
     * @return le nombre d'enfants
     */
    public Object nombreEnfantsSalarie(Integer idSalarie) {
        logger.debug("Nombre des enfants du salarie " + idSalarie);

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("SELECT count(*) FROM Enfant WHERE salarie.id=?");
        query.setParameter(0, idSalarie, StandardBasicTypes.INTEGER);
        return query.uniqueResult();

    }

    /**
     * Récupérer la liste des enfants d'un salarié
     *
     * @param idSalarie l'identifiant du salarié
     * @param start pour la pagination, c'est un nombre entier qui représente
     * l'index du premier élément à afficher
     * @param limit pour la pagination, c'est un nombre entier qui représente
     * l'index du dernier élément à afficher
     *
     * @return la liste des Enfants
     */
    public List<Enfant> listeEnfantsSalarie(Integer idSalarie, int start, int limit) {
        logger.debug("Liste des enfants du salarie " + idSalarie);

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM Enfant WHERE salarie.id=? ORDER BY dateNaissance DESC, id DESC");
        query.setParameter(0, idSalarie, StandardBasicTypes.INTEGER);
        query.setFirstResult(start);
        query.setMaxResults(limit);

        List<Enfant> listeEnfants = query.list();

        for (int i = 0; i < listeEnfants.size(); i++) {

            String chaineDateNaissance = listeEnfants.get(i).getDateNaissance().toString();
            chaineDateNaissance = chaineDateNaissance.substring(8, 10) + "/" + chaineDateNaissance.substring(5, 7) + "/" + chaineDateNaissance.substring(0, 4);
            listeEnfants.get(i).setChaineDateNaissance(chaineDateNaissance);

        }

        return listeEnfants;

    }

    /**
     * Créer un enfant d'un salarié
     *
     * @param enfant instance de l'objet de Enfant
     *
     * @return id enfant
     */
    public Integer ajouterEnfant(Enfant enfant) {
        logger.debug("Ajouter un enfant");

        Session session = sessionFactory.getCurrentSession();

        Integer id = (Integer) session.save(enfant);

        return id;
    }

    /**
     * Récupérer les informations d'un enfant
     *
     * @param idEnfant l'identifiant de l'enfant dans la base de donnée
     *
     * @return Enfant
     */
    public Enfant getEnfantById(Integer idEnfant) {
        logger.debug("Récupération d'un Enfant par id");

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM Enfant WHERE id=:id");
        query.setParameter("id", idEnfant, StandardBasicTypes.INTEGER);

        Enfant enfant = (Enfant) query.uniqueResult();

        return enfant;

    }

    /**
     * Modifier les informations d'un enfant
     *
     * @param enfant
     */
    public void modifierEnfant(Enfant enfant) {
        logger.debug("Modifier enfant");

        Session session = sessionFactory.getCurrentSession();

        session.update(enfant);

    }

    /**
     * Supprimer un enfant
     *
     * @param enfant
     */
    public void supprimerEnfant(Enfant enfant) {
        logger.debug("Supprimer Enfant ");

        Session session = sessionFactory.getCurrentSession();
        session.delete(enfant);
    }

}
