package ma.bservices.services;

import com.itextpdf.text.log.SysoLogger;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;

import javax.annotation.Resource;

import ma.bservices.beans.Contrat;
import ma.bservices.beans.EtatContrat;
import ma.bservices.beans.HistoriqueContrat;

import org.apache.log4j.Logger;
import org.hibernate.FlushMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author bservices
 *
 */
@Service("contratService")
@Transactional
public class ContratService {

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
     * Récupérer le nombre des contrats d'un salarié
     *
     * @param idSalarie l'identifiant du salarié
     * @return le nombre des contrats du salarié
     */
    public Object nombreContratsSalarie(Integer idSalarie) {

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("SELECT count(*) FROM Contrat WHERE salarie.id=? ");
        query.setParameter(0, idSalarie, StandardBasicTypes.INTEGER);

        return query.uniqueResult();

    }
    
    
    
    /** modifier contrat salarie sur la base de données
     * @param c le contrat en question **/
    public void editContrat(Contrat c){
       Transaction tx = null;
        Session session = getSessionFactory().openSession();
        try {

            session.setFlushMode(FlushMode.AUTO);
            tx = session.beginTransaction();

            session.update(c);

            tx.commit();
            session.close();
        } catch (Exception exp) {
            tx.rollback();
            session.close();
        }
    }

    /**
     * Récupérer la liste des contrats d'un salarié selon les critères de
     * pagination (start et liit)
     *
     * @param start pour la pagination, c'est un nombre entier qui représente
     * l'index du premier élément à afficher
     * @param limit pour la pagination, c'est un nombre entier qui représente
     * l'index du dernier élément à afficher
     * @param idSalarie l'identifiant du salarié dans la base de données
     * @return la liste des Contrats
     */
    public List<Contrat> listeContratsSalarie(int start, int limit, Integer idSalarie) {
        logger.debug("liste des contrats d'un salarié");

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM Contrat WHERE salarie.id=? order by dateEmbauche desc");
        query.setParameter(0, idSalarie, StandardBasicTypes.INTEGER);
        query.setFirstResult(start);
        query.setMaxResults(limit);

        List<Contrat> listeContrats = query.list();

        for (int i = 0; i < listeContrats.size(); i++) {

            String chaineDateEmbauche = listeContrats.get(i).getDateEmbauche().toString();
            chaineDateEmbauche = chaineDateEmbauche.substring(8, 10) + "/" + chaineDateEmbauche.substring(5, 7) + "/" + chaineDateEmbauche.substring(0, 4);
            listeContrats.get(i).setChaineDateEmbauche(chaineDateEmbauche);

        }

        return listeContrats;

    }

    /**
     * Récupérer un Contrat via id
     *
     * @param idContrat l'identifiant du contrat
     * @return Contrat
     */
    public Contrat getContrat(Integer idContrat) {
        logger.debug("getContrat par id");

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM Contrat WHERE id=?");

        query.setParameter(0, idContrat, StandardBasicTypes.INTEGER);
        Contrat contrat = (Contrat) query.uniqueResult();

        return contrat;

    }
    
      /**
     * Récupérer tous les contrats non légalisés
     *
     *
     * @return listContrat
     */
    public List<Contrat> findAllContratsNonL() {
        logger.debug("getContrat par id");

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM Contrat WHERE nodeRefNonLegalise is not null AND nodeRefNonLegalise not like 'files%'" );

        
        List<Contrat> lcontratsNL = query.list();

        return lcontratsNL;

    }
    /**
     
     suppression des contrat non égaliser
     */
    public void deleteContratsNonL(Contrat c) {
        System.out.println("============>  deleteContratsNonL() <==========="+c.getId());
        try { 
                Session session = getSessionFactory().openSession();
                session.setFlushMode(FlushMode.AUTO);
                session.beginTransaction();
                session.delete(c);
                session.getTransaction().commit();
                session.close();
        } catch (Exception exp) {  
            System.out.println("Erreur de suppression du contrat car : "+exp.getMessage());
        }
         

    }
    
    
      public List<Contrat> findAllContratsL() {

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM Contrat WHERE nodeRefLegalise is not null AND nodeRefLegalise not like 'files%'" );

          //System.out.println("FROM SERVICE: FOUND :: " + ((Contrat)query.uniqueResult()).getNodeRefLegalise());
        List<Contrat> lcontratsNL = query.list();

        return lcontratsNL;

    }

    /**
     * Vérifier si un contrat d'un salarié existe ou pas dans une date. elle
     * vérifie si la date de fin d'une contrat d'un salarié est nulle alors dans
     * ce cas elle renvoie true sinon (en cas de date de fin non nulle) elle
     * vérifie la date passée en paramètres est ce qu'elle apartient à la durée
     * entre date d'embauche et date de fin de contrat
     *
     * @param idSalarie l'identifiant du salarié
     * @param date pour vérifier si le contrat
     * @param idContrat l'identifiant du contrat dans la base de données
     * @return Renvoie True si le contrat existe sinon false
     */
    public boolean verifierExistanceContrat(Integer idSalarie, Date date, Integer idContrat) {
        logger.debug("verification d'existance d'un contrat via la date");

        Session session = sessionFactory.getCurrentSession();

        Contrat dernierContrat = this.dernierContrat(idSalarie);

            System.err.println("===============> idContrat : "+idContrat);
            if(idContrat == null){
                if (dernierContrat.getId() != null) {
                    /***
                     * Pour des raisons réglémentaire le contrat d'un salarie 
                     * s'enregistre qu'après un mois (exp : salarie quite 10/2018 date prochaine entrer 12/2018) 
                     * 
                     */

                    int a=0,b=0,ad=0,bd=0; 
                    DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    Date dateFinn;
                    try {
                        dateFinn = format.parse(dernierContrat.getDateFin().toString());
                        a=dateFinn.getYear(); 
                        b=dateFinn.getMonth();

                        if(b==11){ b=0; a=a+1; }
                        else{ b=b+1;}
                        ad=date.getYear();
                        bd=date.getMonth();

                    } catch (ParseException ex) {
                        java.util.logging.Logger.getLogger(ContratService.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    int  dateFin = (a *100)+ b;
                    int  dateAct = (ad *100)+ bd;
                    System.err.println("===============> dateAct : "+dateAct);
                    System.err.println("===============> dateFin : "+dateFin);
                    System.err.println("===============> compare : "+(dateFin >= dateAct));
                    if(dateFin >= dateAct){
                        return true;
                    } 
                }
            }
//		Date date15=new Date();
//		Calendar calendar = Calendar.getInstance();
//		calendar.setTime(date);
//		calendar.add(Calendar.DATE,-15);
//		date15=calendar.getTime();
        Query query = null;

            System.err.println("===============> idContrat : "+idContrat);
        if (idContrat == null) {
            //DATEFIN is null seulement(sinon on ajoute AND ? between DATEEMBAUCHE and ?+durée(si n'est pas null) ) : on pourra pas ajouté des anciens contrats CDD aprés un CDI
            //Ajout d'un autre message : on pourra pas ajouter un ancien contrat
//			query = session.createSQLQuery("SELECT * FROM CONTRAT WHERE salarie_ID=? AND ( DATEFIN is null OR (? BETWEEN DATEEMBAUCHE AND DATEFIN) OR DATEEMBAUCHE > ? " +
//											" OR ? < DATEFIN)");
            query = session.createSQLQuery("SELECT * FROM CONTRAT WHERE salarie_ID=? AND ( DATEFIN is null OR (? BETWEEN DATEEMBAUCHE AND DATEFIN) OR DATEEMBAUCHE > ? )");
            query.setParameter(0, idSalarie, StandardBasicTypes.INTEGER);
            query.setParameter(1, date, StandardBasicTypes.DATE);
            query.setParameter(2, date, StandardBasicTypes.DATE);
//			query.setParameter(3, date15, Hibernate.DATE);

        } else {
//			query = session.createSQLQuery("SELECT * FROM CONTRAT WHERE salarie_ID=? AND ID<>? AND ( DATEFIN is null OR (? BETWEEN DATEEMBAUCHE AND DATEFIN) OR DATEEMBAUCHE > ? " +
//					" OR ? < DATEFIN)");
            query = session.createSQLQuery("SELECT * FROM CONTRAT WHERE salarie_ID=? AND ID<>? AND ( DATEFIN is null OR (? BETWEEN DATEEMBAUCHE AND DATEFIN) OR DATEEMBAUCHE > ? )");
            query.setParameter(0, idSalarie, StandardBasicTypes.INTEGER);
            query.setParameter(1, idContrat, StandardBasicTypes.INTEGER);
            query.setParameter(2, date, StandardBasicTypes.DATE);
            query.setParameter(3, date, StandardBasicTypes.DATE);
//			query.setParameter(4, date15, Hibernate.DATE);
        }

            System.err.println("=======================> query : "+query.getQueryString());
        if (query.list().size() > 0) {
            return true;
        }

        return false;
    }

    /**
     * Créer un nouveau contrat
     *
     * @param contrat instance de l'objet de Contrat. il faut utiliser les
     * setters pour remplir les informations du contrat puis passer l'objet en
     * paramètres
     *
     * @return id du contrat
     */
    public Integer ajouterContrat(Contrat contrat) {
        logger.debug("Ajouter un nouveau contrat");

        Session session = sessionFactory.getCurrentSession();

        Integer id = (Integer) session.save(contrat);

        return id;

    }

    /**
     * Modifier les information d'un contrat
     *
     * @param contrat nstance de l'objet de Contrat. il doit être récupéré à
     * partir de la base de données puis passé en paramètres
     */
    public void modifierContrat(Contrat contrat) {
        logger.debug("Modifier un contrat");

        Session session = sessionFactory.getCurrentSession();
        System.out.println("================> contrat MODIF : "+contrat);
        session.update(contrat);
    }

    /**
     * vérifier si le salarié a un contrat dans la date passée en paramètres. la
     * vérification est faite au moment de pointage du salarié
     *
     * @param idSalarie l'identifiant du salarié dans la base de données
     * @param datePointage objet Date
     * @return return true si la le salarié a un contrat dans cette date sinon
     * false
     */
    public boolean verifierContratLegalise(Integer idSalarie, Date datePointage) {

        logger.debug("Vérifier si le salarié a une contrat légalisé dans la date passée en paramètres");

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("SELECT count(*) FROM Contrat WHERE salarie.id=? AND etatContrat.id=? AND ( (? BETWEEN dateEmbauche AND dateFin) OR ((dateFin is null) AND dateEmbauche<=?)) ");
        query.setParameter(0, idSalarie, StandardBasicTypes.INTEGER);
        query.setParameter(1, 2, StandardBasicTypes.INTEGER);
        query.setParameter(2, datePointage, StandardBasicTypes.DATE);
        query.setParameter(3, datePointage, StandardBasicTypes.DATE);

        Long nombre = (Long) query.uniqueResult();
        int n = (int) (long) nombre;
        System.out.println("controle contrat: " + n);

        if (n != 0) {
            return true;
        }

        return false;
    }

    // ---- Historique contrat ---------
    /**
     * Créer un historique du contrat
     *
     * @param historiqueContrat instance de l'objet HistoriqueContrat. doit
     * être, avant, modifié via les setters
     * @return l'identifiant de l'historique dans la base de données
     */
    public Integer ajouterHistoriqueContrat(HistoriqueContrat historiqueContrat) {
        logger.debug("Ajouter histprique du contrat");

        Session session = sessionFactory.getCurrentSession();

        Integer id = (Integer) session.save(historiqueContrat);

        return id;

    }

    /**
     * Récupérer l'historique d'un contrat si le taux et/ou la fonction est
     * modifié
     *
     * @param start pour la pagination, un nombre entier qui représente l'index
     * du premier élément à afficher
     * @param limit pour la pagination, un nombre entier qui représente l'index
     * du premier élément à afficher
     * @param idContrat l'identifiant du contrat dans la base données
     * @return l'historique du contrat sous forme de liste
     */
    public List<HistoriqueContrat> historiqueContrat(int start, int limit, Integer idContrat) {
        logger.debug("l'historique d'un contrat du salarié");

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM HistoriqueContrat WHERE idContrat=? order by date desc");
        query.setParameter(0, idContrat, StandardBasicTypes.INTEGER);
        query.setFirstResult(start);
        query.setMaxResults(limit);

        List<HistoriqueContrat> historiqueContrats = query.list();

        return historiqueContrats;

    }

    /**
     * Vérifier si le contrat passé en paramètre est le dernier contrat du
     * salarié
     *
     * @param idContrat l'identifiant du contrat dans la base de données
     * @param idSalarie l'identifiant du salarié dans la base de données
     * @return True si dernier contrat sinon False
     */
    public boolean dernierContrat(Integer idContrat, Integer idSalarie) {

        logger.debug("vérifier si ce contrat est le plus récent");

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createSQLQuery("Select MAX(ID) FROM CONTRAT as c where salarie_ID=?");
        query.setParameter(0, idSalarie, StandardBasicTypes.INTEGER);

        Integer maxId = (Integer) query.uniqueResult();

        if (idContrat.equals(maxId)) {

            return true;
        } else {
            return false;
        }

    }

    public Contrat dernierContrat(Integer idSalarie) {

        logger.debug("Récupérer le contrat le plus récent");

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createSQLQuery("Select MAX(ID) FROM CONTRAT as c where salarie_ID=?");
        query.setParameter(0, idSalarie, StandardBasicTypes.INTEGER);

        Integer idContrat = (Integer) query.uniqueResult();
        Contrat contrat = new Contrat();
        if (idContrat == null) {
            contrat.setTauxHoraire(null);
        } else {
            contrat = this.getContrat(idContrat);
        }
        return contrat;
    }

    /**
     * Retourne le nombre des contrats d'un salarie selon l'etat du contrat
     * passé en paramètres
     *
     * @param salarieId l'identifiant du salarié dans la base de données
     * @param etatContratId l'etat du contrat
     * @return entier: le nombre des contrats
     */
    public Integer nombreContratParEtat(Integer salarieId, Integer etatContratId) {

        logger.debug("Récupérer le nombre de contrats d'un salarié par etat");

        Session session = sessionFactory.getCurrentSession();
        Query query = session.createSQLQuery("Select count(*) from CONTRAT where salarie_ID=? and etatContrat_ID=?");
        query.setParameter(0, salarieId, StandardBasicTypes.INTEGER);
        query.setParameter(1, etatContratId, StandardBasicTypes.INTEGER);

        return (Integer) query.uniqueResult();

    }

    /**
     * Retourne le dernier contrat d'un salarie selon l'etat du contrat passé en
     * paramètres
     *
     * @param salarieId l'identifiant du salarié dans la base de données
     * @param etatContratId l'etat du contrat
     * @return Contrat
     */
    public Contrat dernierContratParEtat(Integer idSalarie, Integer etatContratId) {

        logger.debug("Récupérer le dernier contrat par etat contrat");

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createSQLQuery("Select MAX(ID) FROM CONTRAT as c where salarie_ID=? AND etatContrat_ID=?");
        query.setParameter(0, idSalarie, StandardBasicTypes.INTEGER);
        query.setParameter(1, etatContratId, StandardBasicTypes.INTEGER);

        Integer idContrat = (Integer) query.uniqueResult();
        Contrat contrat = new Contrat();
        if (idContrat == null) {
            contrat.setTauxHoraire(null);
        } else {
            contrat = this.getContrat(idContrat);
        }
        return contrat;
    }

    /**
     * Régler un contrat en modifiant la date de dortie
     *
     * @param contratId
     * @param DateReglage
     * @return
     */
    public Integer reglerContrat(Integer contratId, Date dateReglage, Integer idMotif) {

        logger.debug("Régler un contrat");
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createSQLQuery("UPDATE CONTRAT SET DATEFIN=?,etatContrat_ID=3,motif_PPTSOR_ID=? WHERE ID=?");
        query.setParameter(0, dateReglage, StandardBasicTypes.DATE);
        query.setParameter(1, idMotif, StandardBasicTypes.INTEGER);
        query.setParameter(2, contratId, StandardBasicTypes.INTEGER);
        Integer id = query.executeUpdate();
        return id;
    }
    
  public List<Contrat> findAllContratL() {
          logger.debug("Contrats");
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createSQLQuery("SELECT * FROM CONTRAT c WHERE c.NODEREFLEGALISE = 'fcd8e465-d7da-431c-ad3a-ee0c4ebec1d3'");             
       // Integer id = query.executeUpdate();
        List<Contrat> lContratsL = query.list();
        return lContratsL;
  }

}
