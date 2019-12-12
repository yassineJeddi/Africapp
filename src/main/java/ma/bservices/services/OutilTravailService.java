package ma.bservices.services;

import java.util.List;

import javax.annotation.Resource;
import ma.bservices.beans.OutilTravail;
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
@Service("outilTravailService")
@Transactional
public class OutilTravailService {

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
     * Récupérer le nombre des Outils de travail
     *
     * @param idSalarie l'identifiant du salarié dans la base de données
     *
     * @return le nombre des outils de travail
     */
    public Object nombreOutilsTravail(Integer idSalarie) {
        logger.debug("Nombre des OutilsTravail");

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("SELECT count(*) FROM OutilTravail WHERE salarie.id=?");
        query.setParameter(0, idSalarie, StandardBasicTypes.INTEGER);
        return query.uniqueResult();

    }

    /**
     * Récupérer la liste des outils de travail
     *
     * @param idSalarie
     * @param start
     * @param limit
     *
     * @return Outils de travail
     */
    public List<OutilTravail> listeOutilsTravail(Integer idSalarie, int start, int limit) {
        logger.debug("Liste des OutilsTravail");

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM OutilTravail WHERE salarie.id=? ORDER BY dateAffectation DESC, id DESC");
        query.setParameter(0, idSalarie, StandardBasicTypes.INTEGER);
        query.setFirstResult(start);
        query.setMaxResults(limit);

        return query.list();

    }

    /**
     * Ajouter un nouvel outil de travail
     *
     * @param outilTravail
     *
     * @return Id outil
     */
    public Integer ajouterOutilTravail(OutilTravail outilTravail) {
        logger.debug("Ajouter une outilTravail");

        Session session = sessionFactory.getCurrentSession();

        Integer id = (Integer) session.save(outilTravail);

        return id;
    }

    /**
     * Récupérer un Outil de travail
     *
     * @param idOutilTravail
     *
     * @return Outil de Travail
     */
    public OutilTravail getOutilTravailById(Integer idOutilTravail) {
        logger.debug("Récupération d'un OutilTravail par id");

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM OutilTravail WHERE id=:id");
        query.setParameter("id", idOutilTravail, StandardBasicTypes.INTEGER);

        OutilTravail outilTravail = (OutilTravail) query.uniqueResult();

        return outilTravail;

    }

    /**
     * Récupérer les informations d'un Outil de travail
     *
     * @param outilTravail
     */
    public void modifierOutilTravail(OutilTravail outilTravail) {
        logger.debug("Modifier OutilTravail");

        Session session = sessionFactory.getCurrentSession();

        session.update(outilTravail);

    }

    /**
     * Supprimer un outil de travail
     *
     * @param ot
     */
    public void supprimerOutilTravail(OutilTravail ot) {
        logger.debug("Supprimer outilTravail ");

        Session session = sessionFactory.getCurrentSession();
        session.delete(ot);
    }

}
