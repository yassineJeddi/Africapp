package ma.bservices.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import ma.bservices.beans.Contrat;
import ma.bservices.beans.Document;
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
@Service("documentServiceEvol")
@Transactional
public class DocumentService {

    protected static Logger logger = Logger.getLogger("service");

    @Resource(name = "sessionFactory")
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public ContratService getContratService() {
        return contratService;
    }

    public void setContratService(ContratService contratService) {
        this.contratService = contratService;
    }

    public ParametrageService getParametrageService() {
        return parametrageService;
    }

    public void setParametrageService(ParametrageService parametrageService) {
        this.parametrageService = parametrageService;
    }

    @Resource(name = "contratService")
    private ContratService contratService;

    @Resource(name = "parametrageService")
    private ParametrageService parametrageService;

    /**
     * Récupérer un Document à partir de la base de données
     *
     * @param idDocument l'identifiant du document dans la base de données
     * @return Document
     */
    public Document getDocumentById(Integer idDocument) {
        logger.debug("Document par id");

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM Document  WHERE id=?");
        query.setParameter(0, idDocument, StandardBasicTypes.INTEGER);

        Document document = (Document) query.uniqueResult();

        return document;

    }

    /**
     * Récupérer un Document d'un salarié
     *
     * @param idSalarie l'identifiant du salarié dans la base de données
     * @param idTypeDocument l'identifiant du type de document dans la base de
     * données
     *
     * @return Document
     */
    public Document getDocument(Integer idSalarie, Integer idTypeDocument) {
        logger.debug("Document par idSalarie et idTypeDocument");

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM Document as doc WHERE doc.salarie.id=? AND doc.typeDocument.id=?");
        query.setParameter(0, idSalarie, StandardBasicTypes.INTEGER);
        query.setParameter(1, idTypeDocument, StandardBasicTypes.INTEGER);

        Document document = (Document) query.uniqueResult();

        return document;

    }

    /**
     * ************* Documents Salarie **********************
     */
    /**
     * Récupérer le nombre des documents d'un salarié
     *
     * @param idSalarie l'identifiant du salarié dans la base de données
     * @return le nombre des documents du salarié
     */
    public Object nombreDocumentsSalarie(Integer idSalarie) {

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("SELECT count(*) FROM Document WHERE salarie.id=? ");
        query.setParameter(0, idSalarie, StandardBasicTypes.INTEGER);

        return query.uniqueResult();

    }

    /**
     * Récupérer la liste des document d'un salarié
     *
     * @param idSalarie l'identifiant du salarié dans la base de données
     * @param start pour la pagination, c'est un nombre entier qui représente
     * l'index du premier élément à afficher
     * @param limit pour la pagination, c'est un nombre entier qui représente
     * l'index du dernier élément à afficher
     *
     * @return la liste des Documents
     */
    public List<Document> listeDocumentsSalarie(Integer idSalarie, int start, int limit) {
        logger.debug("liste des documents des salaries");

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM Document WHERE salarie.id=? ORDER BY dateCreation DESC, id DESC");
        query.setParameter(0, idSalarie, StandardBasicTypes.INTEGER);

//        query.setFirstResult(start);
//        query.setMaxResults(limit);

        List<Document> listeDocuments = query.list();

        for (int i = 0; i < listeDocuments.size(); i++) {

            String chaineDateCreation = listeDocuments.get(i).getDateCreation().toString();
            chaineDateCreation = chaineDateCreation.substring(8, 10) + "/" + chaineDateCreation.substring(5, 7) + "/" + chaineDateCreation.substring(0, 4);
            listeDocuments.get(i).setChaineDateCreation(chaineDateCreation);
            
        }

        return listeDocuments;

    }

    /**
     * Récupérer les Informations d'un document d'un salarié
     *
     * @param nodeRefDocument nodeRef de document
     *
     * @return Informations du document
     */
    public Map<String, Object> mapInformationsDocumentSalarie(String nodeRefDocument) {

        Map<String, Object> mapInformationsDocumentSalarie = new HashMap<String, Object>();

        Document document = this.getDocumentByNodeRef(nodeRefDocument);

        mapInformationsDocumentSalarie.put("idDocument", document.getId());
        mapInformationsDocumentSalarie.put("typeDocument", document.getTypeDocument().getTypeDocument());
        mapInformationsDocumentSalarie.put("nodeRefDocument", document.getNodeRef());
        mapInformationsDocumentSalarie.put("creeParDocument", document.getCreePar());
        mapInformationsDocumentSalarie.put("dateCreationDocument", document.getDateCreation());
        mapInformationsDocumentSalarie.put("modifieParDocument", document.getModifiePar());
        mapInformationsDocumentSalarie.put("dateModificationDocument", document.getDateModification());
        mapInformationsDocumentSalarie.put("commentaireDocument", document.getCommentaire());
        mapInformationsDocumentSalarie.put("titreDocument", document.getTitre());

        return mapInformationsDocumentSalarie;

    }

    /**
     * Récupérer les Informations d'un contrat d'un salarié
     *
     * @param idContrat l'identifiant du contrat dans la base de données
     *
     * @return Informations du contrat
     */
    public Map<String, Object> mapInformationsContratSalarie(Integer idContrat) {

        Map<String, Object> mapInformationsContratSalarie = new HashMap<String, Object>();

        Contrat contrat = contratService.getContrat(idContrat);

        mapInformationsContratSalarie.put("idContrat", contrat.getId());
        mapInformationsContratSalarie.put("etatContrat", contrat.getEtatContrat().getEtatContrat());
        mapInformationsContratSalarie.put("idEtatContrat", contrat.getEtatContrat().getId());
        mapInformationsContratSalarie.put("typeContrat", contrat.getTypeContrat().getTypeContrat());
        mapInformationsContratSalarie.put("statutContrat", parametrageService.getStatut(contrat.getFonction().getCodeDiva().substring(0, 1)).getStatut());
        mapInformationsContratSalarie.put("fonctionContrat", contrat.getFonction().getFonction());
        mapInformationsContratSalarie.put("dateEmbaucheContrat", contrat.getDateEmbauche() != null ? contrat.getDateEmbauche() : "");
        mapInformationsContratSalarie.put("dateFinContrat", contrat.getDateFin() != null ? contrat.getDateFin() : "");
        mapInformationsContratSalarie.put("dureeContrat", contrat.getDuree().getDureeContrat());
        mapInformationsContratSalarie.put("periodeEssaieContrat", contrat.getPeriodeEssai().getPeriodeEssai());
        mapInformationsContratSalarie.put("preavisContrat", contrat.getPreavis().getPreavis());
        mapInformationsContratSalarie.put("tauxHoraire", contrat.getTauxHoraire());

        mapInformationsContratSalarie.put("etatSalarie", contrat.getSalarie().getEtat().getId());
        mapInformationsContratSalarie.put("dernierContrat", contratService.dernierContrat(idContrat, contrat.getSalarie().getId()));

        return mapInformationsContratSalarie;

    }

    /**
     * Créer un nouveau document sur base de données
     *
     * @param document instance de l'Objet Document. il faut utiliser les
     * setters affecter les informations du document
     *
     * @return id du document
     */
    public Integer ajouterDocument(Document document) {
        logger.debug("Ajouter un nouveau document");

        Session session = sessionFactory.getCurrentSession();

        Integer idDocument = (Integer) session.save(document);

        return idDocument;
    }

    /**
     * Modifier un document
     *
     * @param document instance de l'Objet Document. il faut le récupére rà
     * partir de la base de données
     */
    public void modifierDocument(Document document) {
        logger.debug("Modifier un document");

        Session session = sessionFactory.getCurrentSession();

        session.update(document);
    }

    /**
     * Récupérer un document d'un salarié via la CIN et le type de document
     *
     * @param cin la CIN (le numéro de la carte d'identité Nationale) du salarié
     * @param idTypeDocument l'identifiant du type de document dans la base de
     * données
     * @return Document
     */
    public Document getDocumentAjoute(String cin, Integer idTypeDocument) {

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM Document as d WHERE d.salarie.cin=:cin AND d.typeDocument.id=:idTypeDocument");
        query.setParameter("cin", cin);
        query.setParameter("idTypeDocument", idTypeDocument);

        return (Document) query.uniqueResult();
    }

    /**
     * Supprimer un document
     *
     * @param document
     */
    public void supprimerDocument(Document document) {
        logger.debug("Supprimer document");

        Session session = sessionFactory.getCurrentSession();

        session.delete(document);
    }

    /**
     * Récupérer un document
     *
     * @param nodeRef
     * @return Document
     */
    public Document getDocumentByNodeRef(String nodeRef) {
        logger.debug("récupérer le document moyennant le nodeRef");

        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("FROM Document as d WHERE d.nodeRef=:nodeRef");
        query.setParameter("nodeRef", nodeRef);

        return (Document) query.uniqueResult();

    }

    /**
     * Récupérer le dernier document modifié par un utilisateur
     *
     * @param nomEtPrenomUtilisateur
     * @return
     */
    public Map<String, Object> getDernierNodeRefIdDocument(String nomEtPrenomUtilisateur) {

        Map<String, Object> map = new HashMap<String, Object>();

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createSQLQuery("SELECT max(id), nodeRef from DOCUMENT where MODIFIEPAR = ?");
        query.setParameter(0, nomEtPrenomUtilisateur, StandardBasicTypes.STRING);

        map.put("max", query.uniqueResult());

        return map;

    }

}
