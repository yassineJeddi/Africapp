package ma.bservices.services;
//Verification d'existance du salarie au moment d'ajout d'une absence
//Verification d'existance du salarie au moment d'ajout d'une absence

import java.util.List;

import javax.annotation.Resource;

import ma.bservices.beans.Absence;
import ma.bservices.beans.TypeAbsence;
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
@Service("absenceServiceEvol")
@Transactional
public class AbsenceService {

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
     * Récupérer le nombre des absences d'un salarié
     *
     * @param matricule le matricule du salarié
     * @param cin la CIN (numéro de la catre d'identité Nationale)
     * @param cnss la numéro de CNSS
     * @param typeAbsence type d'absence (exemple:"Congé"...)
     *
     * @return nombre des absences
     */
    public Integer nombreAbsences(String matricule, String cin, String cnss, Integer typeAbsence) {
        logger.debug("Nombre des absences");

        Session session = sessionFactory.getCurrentSession();

        String chaineQueryRecherche = "";

        if (matricule != "") {
            chaineQueryRecherche += " AND salarie.matricule='" + matricule + "' ";
        }

        if (cin != "") {
            chaineQueryRecherche += " AND salarie.cin='" + cin + "' ";
        }

        if (cnss != "") {
            chaineQueryRecherche += " AND salarie.cnss='" + cnss + "' ";
        }

        if (typeAbsence != null) {
            chaineQueryRecherche += " AND typeAbsence.id='" + typeAbsence + "' ";
        }

        chaineQueryRecherche = chaineQueryRecherche.replaceFirst("AND", " WHERE");

        Query query = session.createQuery("SELECT count(*) FROM Absence " + chaineQueryRecherche);

        return (int) (long) query.uniqueResult();

    }

    /**
     * Récupérer le nombre et la liste des absences selon les critères de
     * pagination et de recherche cin, cnss et le type d'absence
     *
     * @param start pour la pagination, c'est un nombre entier qui représente
     * l'index du premier élément à afficher
     * @param limit pour la pagination, c'est un nombre entier qui représente
     * l'index du dernier élément à afficher
     * @param matricule le matricule du salarié
     * @param cin la CIN (numéro de la catre d'identité Nationale)
     * @param cnss la numéro de CNSS
     * @param typeAbsence type d'absence (exemple:"Congé"...)
     *
     * @return nombre et liste des Absences
     */
    public List<Absence> listeAbsences(int start, int limit, String matricule, String cin, String cnss, Integer typeAbsence) {
        logger.debug("Liste des absences");

        Session session = sessionFactory.getCurrentSession();

        String chaineQueryRecherche = "";

        if (matricule != "") {
            chaineQueryRecherche += " AND salarie.matricule='" + matricule + "' ";
        }

        if (cin != "") {
            chaineQueryRecherche += " AND salarie.cin='" + cin + "' ";
        }

        if (cnss != "") {
            chaineQueryRecherche += " AND salarie.cnss='" + cnss + "' ";
        }

        if (typeAbsence != null) {
            chaineQueryRecherche += " AND typeAbsence.id='" + typeAbsence + "' ";
        }

        chaineQueryRecherche = chaineQueryRecherche.replaceFirst("AND", " WHERE");

        Query query = session.createQuery("FROM Absence " + chaineQueryRecherche + " ORDER BY dateDebut DESC, id DESC");
        query.setFirstResult(start);
        query.setMaxResults(limit);

        List<Absence> listeAbsences = query.list();

        for (int i = 0; i < listeAbsences.size(); i++) {

            if (listeAbsences.get(i) != null && listeAbsences.get(i).getDateDebut() != null && listeAbsences.get(i).getDateFin() != null ) {

                String chaineDateHeureDebut = listeAbsences.get(i).getDateDebut().toString();
                chaineDateHeureDebut = chaineDateHeureDebut.substring(8, 10) + "/" + chaineDateHeureDebut.substring(5, 7) + "/" + chaineDateHeureDebut.substring(0, 4) + "    " + listeAbsences.get(i).getHeureDebut();
                listeAbsences.get(i).setChaineDateHeureDebut(chaineDateHeureDebut);

                String chaineDateHeureFin = listeAbsences.get(i).getDateFin().toString();
                chaineDateHeureFin = chaineDateHeureFin.substring(8, 10) + "/" + chaineDateHeureFin.substring(5, 7) + "/" + chaineDateHeureFin.substring(0, 4) + "    " + listeAbsences.get(i).getHeureFin();
                listeAbsences.get(i).setChaineDateHeureFin(chaineDateHeureFin);

                //pour la modification on doit les heure et les minutes dans la valeur transient -- fin de l'initialisation des champs transient
                String[] debutArr = new String[2];
                if (listeAbsences.get(i).getHeureDebut() != null) {
                    debutArr = listeAbsences.get(i).getHeureDebut().split(":");
                    listeAbsences.get(i).setHeuredeb(Integer.valueOf(debutArr[0]));
                    listeAbsences.get(i).setMindeb(Integer.valueOf(debutArr[1]));
                }

                String[] finArr = new String[2];
                if (listeAbsences.get(i).getHeureFin() != null) {
                    finArr = listeAbsences.get(i).getHeureFin().split(":");
                    listeAbsences.get(i).setHeureF(Integer.valueOf(finArr[0]));
                    listeAbsences.get(i).setMinF(Integer.valueOf(finArr[1]));
                }

                if (listeAbsences.get(i).getTypeAbsence() != null) {
                    listeAbsences.get(i).setIdtypeAbs(listeAbsences.get(i).getTypeAbsence().getId());
                }
            }
        }

        return listeAbsences;

    }

    //Verifier existance d'une absence
    /**
     * Vérifier l'existance d'une absence dans la base de données au moment
     * d'ajout ou de la modification d'une absence si idAbsence == null --> au
     * momemnt d'ajout d'une absence Si idAbsence != null --> au momemnt de
     * modification d'une absence
     *
     * @param matricule le matricule du salarié
     * @param idAbsence l'identifiant de l'absence dans la base de données.
     * @param longDateTimeDebut date de début sous format numérique de type long
     * (exemple:"1358924400000")
     * @param longDateTimeFin date de fin sous format numérique de type long
     * (exemple:"1359046800000")
     *
     * @return Renvoie True si l'absence existe déjà sinon False
     */
    public boolean verifierExistanceAbsence(String matricule, Integer idAbsence, Long longDateTimeDebut, Long longDateTimeFin) {

        Session session = sessionFactory.getCurrentSession();
        Query query = null;

        //idAbsence == null : ajout d'une absence     ------  idAbsence != null : modification d'une absence
        if (idAbsence == null) {

            query = session.createQuery("FROM Absence a WHERE a.salarie.matricule=? AND "
                    + "(   ( ? BETWEEN a.longDateTimeDebut AND a.longDateTimeFin AND ?<>a.longDateTimeFin ) "
                    + " OR ( ? BETWEEN a.longDateTimeDebut AND a.longDateTimeFin AND ?<>a.longDateTimeDebut ) "
                    + " OR ( a.longDateTimeDebut BETWEEN ? AND ? AND a.longDateTimeDebut<>? ) "
                    + " OR ( a.longDateTimeFin BETWEEN ? AND ? AND a.longDateTimeFin<>? )  )");

            query.setParameter(0, matricule, StandardBasicTypes.STRING);

            query.setParameter(1, longDateTimeDebut, StandardBasicTypes.LONG);
            query.setParameter(2, longDateTimeDebut, StandardBasicTypes.LONG);

            query.setParameter(3, longDateTimeFin, StandardBasicTypes.LONG);
            query.setParameter(4, longDateTimeFin, StandardBasicTypes.LONG);

            query.setParameter(5, longDateTimeDebut, StandardBasicTypes.LONG);
            query.setParameter(6, longDateTimeFin, StandardBasicTypes.LONG);
            query.setParameter(7, longDateTimeFin, StandardBasicTypes.LONG);

            query.setParameter(8, longDateTimeDebut, StandardBasicTypes.LONG);
            query.setParameter(9, longDateTimeFin, StandardBasicTypes.LONG);
            query.setParameter(10, longDateTimeDebut, StandardBasicTypes.LONG);

        } else {

            query = session.createQuery("FROM Absence a WHERE a.salarie.matricule=? AND a.id<>? AND "
                    + "(   ( ? BETWEEN a.longDateTimeDebut AND a.longDateTimeFin AND ?<>a.longDateTimeFin ) "
                    + " OR ( ? BETWEEN a.longDateTimeDebut AND a.longDateTimeFin AND ?<>a.longDateTimeDebut ) "
                    + " OR ( a.longDateTimeDebut BETWEEN ? AND ? AND a.longDateTimeDebut<>? ) "
                    + " OR ( a.longDateTimeFin BETWEEN ? AND ? AND a.longDateTimeFin<>? )  )");

            query.setParameter(0, matricule, StandardBasicTypes.STRING);
            query.setParameter(1, idAbsence, StandardBasicTypes.INTEGER);

            query.setParameter(2, longDateTimeDebut, StandardBasicTypes.LONG);
            query.setParameter(3, longDateTimeDebut, StandardBasicTypes.LONG);

            query.setParameter(4, longDateTimeFin, StandardBasicTypes.LONG);
            query.setParameter(5, longDateTimeFin, StandardBasicTypes.LONG);

            query.setParameter(6, longDateTimeDebut, StandardBasicTypes.LONG);
            query.setParameter(7, longDateTimeFin, StandardBasicTypes.LONG);
            query.setParameter(8, longDateTimeFin, StandardBasicTypes.LONG);

            query.setParameter(9, longDateTimeDebut, StandardBasicTypes.LONG);
            query.setParameter(10, longDateTimeFin, StandardBasicTypes.LONG);
            query.setParameter(11, longDateTimeDebut, StandardBasicTypes.LONG);

        }

        if (query.list().size() > 0) {
            return true;
        }

        return false;

    }

    //	public boolean verifierValiditeAbsencePointage(Integer idSalarie, Date dateDebut, String heureMinuteDebut, Date dateFin, String heureMinuteFin){
    //		
    //		 Session session = sessionFactory.getCurrentSession();
    //		  Query query =  session.createQuery("SELECT p.date, p.heureEntree, p.heureSortie FROM Presence p WHERE p.salarie.id=? AND p.date BETWEEN ? AND ? ");
    //		  
    //		  query.setParameter(0, idSalarie, Hibernate.INTEGER);
    //		  query.setParameter(1, dateDebut, Hibernate.DATE);
    //		  query.setParameter(2, dateFin, Hibernate.DATE);
    //		  		  
    //		  
    //		  List listePresences = query.list();	  
    //		
    //		  int intHeureMinuteDebutAbsence = Integer.parseInt(heureMinuteDebut.replaceFirst(":", ""));
    //		
    //		  int intHeureMinuteFinAbsence = Integer.parseInt(heureMinuteFin.replaceFirst(":", ""));
    //		  
    //		  for(int i=0; i< listePresences.size(); i++){
    //			 
    //			  Object[] o=(Object[]) listePresences.get(i);
    //			  Date datePointage = (Date) o[0];
    //			  String heureMinutePE = (String) o[1];
    //			  String heureMinutePS = (String) o[2];			    
    //			 	
    //			 
    //			  
    //			  if(dateDebut.before(datePointage) && datePointage.before(dateFin))
    //				  return false;
    //			  
    //			 
    //			  
    //			  if(heureMinutePE!=null && heureMinutePS!=null){
    //				  
    //				  int intHeureMinutePE = Integer.parseInt(heureMinutePE.replaceFirst(":", ""));
    //				  int intHeureMinutePS = Integer.parseInt(heureMinutePS.replaceFirst(":", ""));
    //				  
    //				  if(datePointage.equals(dateDebut) && datePointage.equals(dateFin)
    //						  && ( (intHeureMinuteDebutAbsence<intHeureMinutePE && intHeureMinutePE<intHeureMinuteFinAbsence) 
    //								  || (intHeureMinuteDebutAbsence<intHeureMinutePE && intHeureMinutePE<intHeureMinuteFinAbsence)
    //								  || (intHeureMinuteDebutAbsence<intHeureMinutePE && intHeureMinutePE<intHeureMinuteFinAbsence)
    //								  || (intHeureMinuteDebutAbsence<intHeureMinutePE && intHeureMinutePE<intHeureMinuteFinAbsence)) ){
    //					  
    //					  return false;
    //					  
    //				  } 
    //				  
    //				  if(datePointage.equals(dateDebut) && !datePointage.equals(dateFin)
    //						  && (intHeureMinutePE>=intHeureMinuteDebutAbsence || intHeureMinutePS>intHeureMinuteFinAbsence) 
    //								   ){				  
    //					  
    //					  return false;
    //					  
    //				  } 
    //				  
    //				  
    //				  if(!datePointage.equals(dateDebut) && datePointage.equals(dateFin)
    //						  && (intHeureMinutePE<intHeureMinuteDebutAbsence || intHeureMinutePS<=intHeureMinuteFinAbsence) 
    //								   ){				  
    //					  
    //					  return false;
    //					  
    //				  } 
    //				  
    //				  
    //			  }
    //			  
    //			  
    //            if(heureMinutePE!=null && heureMinutePS==null){
    //				  
    //				  int intHeureMinutePE = Integer.parseInt(heureMinutePE.replaceFirst(":", ""));
    //				  
    //				  
    //				  if(datePointage.equals(dateDebut) && datePointage.equals(dateFin)
    //						  &&  intHeureMinuteDebutAbsence<=intHeureMinutePE && intHeureMinutePE<intHeureMinuteFinAbsence
    //								  ){
    //					  
    //					  return false;
    //					  
    //				  } 
    //				  
    //				  if(datePointage.equals(dateDebut) && !datePointage.equals(dateFin)
    //						  && intHeureMinutePE>=intHeureMinuteDebutAbsence 
    //								   ){				  
    //					  
    //					  return false;
    //					  
    //				  } 
    //				  
    //				  
    //				  if(!datePointage.equals(dateDebut) && datePointage.equals(dateFin)
    //						  && intHeureMinutePE<intHeureMinuteFinAbsence  
    //								   ){				  
    //					  
    //					  return false;
    //					  
    //				  } 
    //				  
    //				  
    //			  }
    //			  
    //			  
    //            if(heureMinutePE==null && heureMinutePS!=null){
    //				  
    //				  int intHeureMinutePS = Integer.parseInt(heureMinutePS.replaceFirst(":", ""));
    //				  
    //				  
    //				  if(datePointage.equals(dateDebut) && datePointage.equals(dateFin)
    //						  &&  intHeureMinuteDebutAbsence<intHeureMinutePS && intHeureMinutePS<=intHeureMinuteFinAbsence
    //								  ){
    //					  
    //					  return false;
    //					  
    //				  } 
    //				  
    //				  if(datePointage.equals(dateDebut) && !datePointage.equals(dateFin)
    //						  && intHeureMinutePS>intHeureMinuteDebutAbsence){				  
    //					  
    //					  return false;
    //					  
    //				  } 
    //				  
    //				  
    //				  if(!datePointage.equals(dateDebut) && datePointage.equals(dateFin)
    //						  && intHeureMinutePS<=intHeureMinuteFinAbsence  
    //								   ){				  
    //					  
    //					  return false;
    //					  
    //				  } 
    //				  
    //				  
    //			  }
    //			  
    //			  
    ////			  if((date.equals(dateDebut) && intHeureMinuteDebut<= intHeureMinutePointage && intHeureMinutePointage<=intHeureMinuteFin) || (datePointage.equals(dateFin) && intHeureMinuteDebut<= intHeureMinutePointage && intHeureMinutePointage<=intHeureMinuteFin) )
    ////			  	  return false;
    //				  
    //			 
    //			 
    //			  
    //			  
    //			  
    //		  }
    //		  
    //		  
    //		return true;
    //		
    //		
    //	}
    //	
    /**
     * Vérifier la validité de la date de début et la date de fin d'une absence
     * (pour éviter la superposition avec les présences exemple:marquer un
     * salarié absent sachant qu'il a été déjà marqué présent dans les mêmes
     * dates)
     *
     * @param idSalarie l'identifiant du salarié dans la base de données
     * @param longDateTimeDebut date de début sous format numérique de type long
     * (exemple:"1358924400000")
     * @param longDateTimeFin date de fin sous format numérique de type long
     * (exemple:"1359046800000")
     *
     * @return Renvoie True si les dates (début et fin) passées en paramètres
     * sont valide sinon false
     */
    public boolean verifierValiditeAbsencePointage(Integer idSalarie, Long longDateTimeDebut, Long longDateTimeFin) {

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM Presence p WHERE p.salarie.id=? AND ( "
                + "  (p.longDateTimeEntree is not null AND p.longDateTimeSortie is not null AND (     ( ? BETWEEN p.longDateTimeEntree AND p.longDateTimeSortie AND ?<>p.longDateTimeSortie ) "
                + " OR ( ? BETWEEN p.longDateTimeEntree AND p.longDateTimeSortie AND p.longDateTimeEntree<>? ) "
                + " OR ( p.longDateTimeEntree BETWEEN ? AND ? AND p.longDateTimeEntree<>? ) "
                + " OR ( p.longDateTimeSortie BETWEEN ? AND ? AND ?<>p.longDateTimeSortie ) "
                + " ) ) "
                + " OR  (p.longDateTimeEntree is not null AND p.longDateTimeSortie is null AND p.longDateTimeEntree BETWEEN ? AND ? AND p.longDateTimeEntree<>? ) "
                + " OR  (p.longDateTimeEntree is null AND p.longDateTimeSortie is not null AND p.longDateTimeSortie BETWEEN ? AND ? AND ?<>p.longDateTimeSortie ) )");

        query.setParameter(0, idSalarie, StandardBasicTypes.INTEGER);

        query.setParameter(1, longDateTimeDebut, StandardBasicTypes.LONG);
        query.setParameter(2, longDateTimeDebut, StandardBasicTypes.LONG);
        query.setParameter(3, longDateTimeFin, StandardBasicTypes.LONG);
        query.setParameter(4, longDateTimeFin, StandardBasicTypes.LONG);
        query.setParameter(5, longDateTimeDebut, StandardBasicTypes.LONG);
        query.setParameter(6, longDateTimeFin, StandardBasicTypes.LONG);
        query.setParameter(7, longDateTimeFin, StandardBasicTypes.LONG);
        query.setParameter(8, longDateTimeDebut, StandardBasicTypes.LONG);
        query.setParameter(9, longDateTimeFin, StandardBasicTypes.LONG);
        query.setParameter(10, longDateTimeDebut, StandardBasicTypes.LONG);

        query.setParameter(11, longDateTimeDebut, StandardBasicTypes.LONG);
        query.setParameter(12, longDateTimeFin, StandardBasicTypes.LONG);
        query.setParameter(13, longDateTimeFin, StandardBasicTypes.LONG);

        query.setParameter(14, longDateTimeDebut, StandardBasicTypes.LONG);
        query.setParameter(15, longDateTimeFin, StandardBasicTypes.LONG);
        query.setParameter(16, longDateTimeDebut, StandardBasicTypes.LONG);

        if (query.list().size() > 0) {
            return false;
        }

        return true;

    }

    /**
     * Créer une nouvelle absence dans la base de données
     *
     * @param absence une instance de l'objet Absence. il doit recevoir au
     * préalable les informations de l'absence via les setters
     *
     * @return id Absence créée
     */
    public Integer ajouterAbsence(Absence absence) {
        logger.debug("Ajouter une absence");

        Session session = sessionFactory.getCurrentSession();

        Integer id = (Integer) session.save(absence);

        return id;
    }

    /**
     * Récupérer l'absence par son id à partir de la base de données
     *
     * @param idAbsence l'identifiant de l'absence dans la base de données
     * @return Absence
     */
    public Absence getAbsenceById(Integer idAbsence) {
        logger.debug("Récupération d'une absence par id");

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM Absence WHERE id=:id");
        query.setParameter("id", idAbsence, StandardBasicTypes.INTEGER);

        //	session.get(Absence.class, idAbsence);
        Absence absence = (Absence) query.uniqueResult();

        return absence;

    }

    /**
     * Modifier les informationsd d'une Absence dans la base de données
     *
     * @param absence
     */
    public void modifierAbsence(Absence absence) {
        logger.debug("Modifier une absence");

        Session session = sessionFactory.getCurrentSession();

        session.update(absence);

    }

}
