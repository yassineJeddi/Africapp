package ma.bservices.services;

import javax.annotation.Resource;

import ma.bservices.beans.DossierSalarie;

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
@Service("dossierSalarieService")
@Transactional
public class DossierSalarieService {

    protected static Logger logger = Logger.getLogger("service");
    @Resource(name = "sessionFactory")
    private SessionFactory sessionFactory;

    /**
     * Créer un dossier d'un salarié
     *
     * @param dossierSalarie
     * @return id dossier
     */
    public Integer ajouterDossierSalarie(DossierSalarie dossierSalarie) {
        logger.debug("Ajouter un nouveau dossier d'un salarié");

        Session session = sessionFactory.getCurrentSession();

        Integer numeroDossierSalarie = (Integer) session.save(dossierSalarie);

        return numeroDossierSalarie;
    }

    /**
     * modifier un dossier d'un salarié
     *
     * @param dossierSalarie
     */
    public void modifierDossierSalarie(DossierSalarie dossierSalarie) {
        logger.debug("Modifier un dossier d'un salarié");

        Session session = sessionFactory.getCurrentSession();

        session.update(dossierSalarie);
    }

    /**
     * Récupérer un dossier salarié
     *
     * @param idDossierSalarie
     *
     * @return Dossier salarie
     */
    public DossierSalarie getDossierSalarie(Integer idDossierSalarie) {
        logger.debug("DossierSalarie par id");

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM DossierSalarie WHERE id=?");

        query.setParameter(0, idDossierSalarie, StandardBasicTypes.INTEGER);
        DossierSalarie dossierSalarie = (DossierSalarie) query.uniqueResult();

        return dossierSalarie;

    }

}
