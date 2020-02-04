package ma.bservices.services;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import ma.bservice.tgcc.Constante.Constante;

//import ma.bservices.authentication.CmaUseverifierValiditeHeureMinutePointageSalarie rnamePasswordAuthenticationToken;
import ma.bservices.beans.Chantier;
import ma.bservices.beans.Contrat;
import ma.bservices.beans.Fonction;
import ma.bservices.beans.Presence;
import ma.bservices.beans.Salarie;
import ma.bservices.beans.Statut;
import ma.bservices.constantes.Constantes;
import ma.bservices.mb.services.MbHibernateDaoSupport;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

//import com.rivetlogic.core.cma.repo.Ticket;
/**
 * @author bservices
 *
 */
@Service("presenceService")
@Transactional
public class PresenceService extends MbHibernateDaoSupport {

    protected static Logger logger = Logger.getLogger("service");
    @Resource(name = "sessionFactory")
    private SessionFactory sessionFactory;

    @Resource(name = "salarieService")
    private SalarieService salarieService;

    @Resource(name = "contratService")
    private ContratService contratService;

    @Resource(name = "parametrageService")
    private ParametrageService parametrageService;

    @Resource(name = "chantierServiceEvol")
    private ChantierService chantierService;

    @Resource(name = "administrationService")
    private AdministrationService administrationService;

    

    public SalarieService getSalarieService() {
        return salarieService;
    }

    public void setSalarieService(SalarieService salarieService) {
        this.salarieService = salarieService;
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

    public ChantierService getChantierService() {
        return chantierService;
    }

    public void setChantierService(ChantierService chantierService) {
        this.chantierService = chantierService;
    }

    public AdministrationService getAdministrationService() {
        return administrationService;
    }

    public void setAdministrationService(AdministrationService administrationService) {
        this.administrationService = administrationService;
    }

    /**
     * ***************************************************************************************************************
     */
    /**
     * **************************** POINTAGE PAR SALARIE
     * *******************************************************
     */
    /**
     * Récupérer le nom du chantier dans le quel un salarié est pointé present
     * dans une date précise
     *
     * @param matricule le matricule du salarié
     * @param datePointage date de pointage (format: yyyy/mm/jj)
     *
     * @return nom du chantier
     */
    public String recupererCodeChantierPointageParSalarie(String matricule, Date datePointage) {

        Session session = sessionFactory.getCurrentSession();
//        try {
//
//            session.connection().setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
//
//        } catch (HibernateException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (SQLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
        Query query = session.createQuery("SELECT p.chantier.code FROM Presence p WHERE p.salarie.matricule=? AND p.date=? AND p.heureSortie IS NULL");
        query.setParameter(0, matricule, StandardBasicTypes.STRING);
        query.setParameter(1, datePointage, StandardBasicTypes.DATE);

        List listeCodesChantiers = query.list();

        if (listeCodesChantiers.size() == 0) {
            return "";
        }

        //		  System.out.println(listeCodesChantiers.get(0));
        //		  Object[] o=(Object[]) listeCodesChantiers.get(0);
        return (String) listeCodesChantiers.get(0);

    }

    /**
     * Vérifier la validité des heures de pointage d'un salarié
     *
     * @param idSalarie l'identifiant du salarié dans la base de données
     * @param datePointage date de pointage (format: yyyy/mm/jj)
     * @param heureMinute heure de pointage du salarié
     *
     * @return Renvoie True si l'heure de pointage est valide sinon False
     */
    public boolean verifierValiditeHeureMinutePointageSalarie(Integer idSalarie, Date datePointage, String heureMinute) {

        Session session = sessionFactory.getCurrentSession();
//        try {
//
//            session.connection().setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
//
//        } catch (HibernateException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (SQLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
        Query query = session.createQuery("SELECT p.heureEntree, p.heureSortie FROM Presence p WHERE p.salarie.id=? AND p.date=?");
        query.setParameter(0, idSalarie, StandardBasicTypes.INTEGER);
        query.setParameter(1, datePointage, StandardBasicTypes.DATE);

        int intHeureMinute = Integer.parseInt(heureMinute.replaceFirst(":", ""));
        int heure = Integer.parseInt(heureMinute.substring(0, 2));

        List listeHeures = query.list();

        for (int i = 0; i < listeHeures.size(); i++) {

            Object[] o = (Object[]) listeHeures.get(i);
            String heureEntree = (String) o[0];
            String heureSortie = (String) o[1];

            if (heureEntree != null && heureSortie == null) {
                if (Integer.parseInt(heureEntree.substring(0, 2)) <= heure) {
                    return false;
                }
            }

            if (heureEntree == null && heureSortie != null) {
                if (Integer.parseInt(heureSortie.substring(0, 2)) >= heure) {
                    return false;
                }
            }

            if (heureEntree != null && heureSortie != null) {
                if (Integer.parseInt(heureEntree.substring(0, 2)) <= heure && Integer.parseInt(heureSortie.substring(0, 2)) >= heure) {
                    return false;
                }
            }

        }

        return true;
    }

    /**
     * Vérifier la validité des heures et minutes avant le pointage d'un salarié
     *
     * @param idSalarie l'identifiant du salarié dans la base de données
     * @param date date
     * @param longDateTimePointage
     * @param typePointage le type de pointage
     *
     * @return Renvoie True si l'heure de pointage est valide sinon False
     */
    public boolean verifierValiditeHeureMinutePointageSalarie(Integer idSalarie, Date date, Long longDateTimePointage, String typePointage) {

        Session session = sessionFactory.getCurrentSession();
//        try {
//
//            session.connection().setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
//
//        } catch (HibernateException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (SQLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
        Query query = null;

        if (typePointage.equals("PE")) {

            query = session.createSQLQuery("Select p.ID FROM PRESENCE p WHERE p.salarie_ID=? AND p.DATE=? AND ("
                    + " ( p.LONGDATETIMEENTREE is not null AND p.LONGDATETIMESORTIE is not null AND ? BETWEEN p.LONGDATETIMEENTREE AND p.LONGDATETIMESORTIE AND ?<>p.LONGDATETIMESORTIE ) "
                    + " OR ( p.LONGDATETIMEENTREE is null AND p.LONGDATETIMESORTIE is not null AND ?>=p.LONGDATETIMESORTIE ) "
                    + " OR ( p.LONGDATETIMEENTREE is not null AND p.LONGDATETIMESORTIE is null ) )");

            query.setParameter(0, idSalarie, StandardBasicTypes.INTEGER);
            query.setParameter(1, date, StandardBasicTypes.DATE);
            query.setParameter(2, longDateTimePointage, StandardBasicTypes.LONG);
            query.setParameter(3, longDateTimePointage, StandardBasicTypes.LONG);
            query.setParameter(4, longDateTimePointage, StandardBasicTypes.LONG);

        } else {

            query = session.createSQLQuery("SELECT p.ID FROM PRESENCE p WHERE p.salarie_ID=? AND DATE=? AND "
                    + " (    ( p.LONGDATETIMEENTREE is not null AND p.LONGDATETIMESORTIE is not null AND ? BETWEEN p.LONGDATETIMEENTREE AND p.LONGDATETIMESORTIE AND p.LONGDATETIMESORTIE<>? ) "
                    + "   OR ( p.LONGDATETIMEENTREE is not null AND p.LONGDATETIMESORTIE is null AND ?<=p.LONGDATETIMEENTREE ) "
                    + "   OR ( p.LONGDATETIMEENTREE is null AND p.LONGDATETIMESORTIE is not null ) )");

            query.setParameter(0, idSalarie, StandardBasicTypes.INTEGER);
            query.setParameter(1, date, StandardBasicTypes.DATE);

            query.setParameter(2, longDateTimePointage, StandardBasicTypes.LONG);
            query.setParameter(3, longDateTimePointage, StandardBasicTypes.LONG);
            query.setParameter(4, longDateTimePointage, StandardBasicTypes.LONG);

        }

        if (query.list().size() > 0) {
            return false;
        }

        return true;
    }

    /**
     * Vérifier la validité des heures et minutes avant de marquer l'absence
     * d'un salarié
     *
     * @param idSalarie l'identifiant du salarié dans la base de données
     * @param longDateTimeEntree date d'entrée sous forme de numérique
     * (exemple:1358924400000)
     * @param longDateTimeSortie date de sortie sous forme de numérique
     * (exemple:1358924400000)
     * @param typePointage le type de pointage entrée u sortie ("PE" ou "PS")
     *
     * @return Renvoie True si la l'heure est valide sinon False
     */
    public boolean verifierValiditeDateHeureMinutePointageSalarieAbsence(Integer idSalarie, Long longDateTimeEntree, Long longDateTimeSortie, String typePointage) {

        Session session = sessionFactory.getCurrentSession();
//        try {
//
//            session.connection().setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
//
//        } catch (HibernateException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (SQLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
        Query query;

        if (typePointage.equals("PE")) {

            query = session.createSQLQuery("SELECT a.ID FROM ABSENCE a WHERE a.salarie_ID=? AND ( ( ? BETWEEN a.LONGDATETIMEDEBUT AND a.LONGDATETIMEFIN AND a.LONGDATETIMEFIN<>? ) "
                    + " OR (? is not null AND ? is not null AND a.LONGDATETIMEDEBUT BETWEEN ? AND ? AND a.LONGDATETIMEDEBUT<>?) "
                    + " OR (? is not null AND ? is not null AND a.LONGDATETIMEFIN BETWEEN ? AND ? AND a.LONGDATETIMEFIN<>?) )");

            query.setParameter(0, idSalarie, StandardBasicTypes.INTEGER);

            query.setParameter(1, longDateTimeEntree, StandardBasicTypes.LONG);
            query.setParameter(2, longDateTimeEntree, StandardBasicTypes.LONG);

            query.setParameter(3, longDateTimeEntree, StandardBasicTypes.LONG);
            query.setParameter(4, longDateTimeSortie, StandardBasicTypes.LONG);
            query.setParameter(5, longDateTimeEntree, StandardBasicTypes.LONG);
            query.setParameter(6, longDateTimeSortie, StandardBasicTypes.LONG);
            query.setParameter(7, longDateTimeSortie, StandardBasicTypes.LONG);

            query.setParameter(8, longDateTimeEntree, StandardBasicTypes.LONG);
            query.setParameter(9, longDateTimeSortie, StandardBasicTypes.LONG);
            query.setParameter(10, longDateTimeEntree, StandardBasicTypes.LONG);
            query.setParameter(11, longDateTimeSortie, StandardBasicTypes.LONG);
            query.setParameter(12, longDateTimeEntree, StandardBasicTypes.LONG);

        } else {

            query = session.createSQLQuery("SELECT a.ID FROM ABSENCE a WHERE a.salarie_ID=? AND ( ( ? BETWEEN a.LONGDATETIMEDEBUT AND a.LONGDATETIMEFIN AND a.LONGDATETIMEDEBUT<>? ) "
                    + " OR (? is not null AND ? is not null AND a.LONGDATETIMEDEBUT BETWEEN ? AND ? AND a.LONGDATETIMEDEBUT<>?) "
                    + " OR (? is not null AND ? is not null AND a.LONGDATETIMEFIN BETWEEN ? AND ? AND a.LONGDATETIMEFIN<>?) )");

            query.setParameter(0, idSalarie, StandardBasicTypes.INTEGER);
            query.setParameter(1, longDateTimeEntree, StandardBasicTypes.LONG);
            query.setParameter(2, longDateTimeSortie, StandardBasicTypes.LONG);

            query.setParameter(3, longDateTimeEntree, StandardBasicTypes.LONG);
            query.setParameter(4, longDateTimeSortie, StandardBasicTypes.LONG);
            query.setParameter(5, longDateTimeEntree, StandardBasicTypes.LONG);
            query.setParameter(6, longDateTimeSortie, StandardBasicTypes.LONG);
            query.setParameter(7, longDateTimeSortie, StandardBasicTypes.LONG);

            query.setParameter(8, longDateTimeEntree, StandardBasicTypes.LONG);
            query.setParameter(9, longDateTimeSortie, StandardBasicTypes.LONG);
            query.setParameter(10, longDateTimeEntree, StandardBasicTypes.LONG);
            query.setParameter(11, longDateTimeSortie, StandardBasicTypes.LONG);
            query.setParameter(12, longDateTimeEntree, StandardBasicTypes.LONG);

        }

        if (query.list().size() > 0) {
            return false;
        }

        return true;
    }

    /**
     * Vérifier la validité de la date de pointage avec la durée de contrat d'un
     * salarié. cette doit être supérieure à la date d'embauche et imférieure à
     * la date de fin de contrat Sinon la date de fin de contrat doit être null
     * et la date d'embauche est inférieure ou égale à la date passée en
     * paramètres
     *
     * @param idSalarie l'identifiant du salarié dans la base de données
     * @param date date de pointage à vérifier avant de pointer un salarié
     * @return renvoie True si la date est valide sinon False
     */
    public boolean verifierValiditeDatePointageContrat(Integer idSalarie, Date date) {

        Session session = sessionFactory.getCurrentSession();
//        try {
//
//            session.connection().setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
//
//        } catch (HibernateException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (SQLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
        Query query = session.createQuery("FROM Contrat c WHERE c.salarie.id=? AND ( (? BETWEEN dateEmbauche AND dateFin) OR (dateFin is null AND dateEmbauche<=?) )");

        query.setParameter(0, idSalarie, StandardBasicTypes.INTEGER);
        query.setParameter(1, date, StandardBasicTypes.DATE);
        query.setParameter(2, date, StandardBasicTypes.DATE);

        if (query.list().size() > 0) {
            return true;
        }

        return false;

    }

    /**
     * **************************** FIN DE POINTAGE PAR SALARIE
     * *********************************************************
     */
    /**
     * ***************************************************************************************************************
     */
    /**
     * ***************************************************************************************************************
     */
    /**
     * **************************** DEBUT DE POINTAGE D'ENTRÉE
     * *******************************************************
     */
    /**
     * Récupérer le nombre des salariés qui n'ont pas encore pointés dans une
     * date et sur un chantier. matricule, cin, cnss, statut et fonction sont
     * des paramètres de recherche. start et limit sont des paramètres de
     * pagination
     *
     *
     * @param idChantier l'identifiant du chantier dans la base de données
     * @param dateEntree date de pointage d'entrée (format:yyyy/mm/jj)
     * @param matriculeSalarie le matricule du salarié
     * @param cinSalarie le numéro de la carte d'identité nationale du salarié
     * @param cnssSalarie numéro de cnss du salarié
     * @param statut statut du salarié (Exemple: CADRE, OUVRIER....)
     * @param fonction fonction du salarié (exemple: CHEF DE CHANTIER,
     * BOISEUR...etc)
     *
     * @return le nombre des salariés non pointés
     */
    public Object nombreSalariesNonPointesEntree(Integer idChantier, Date dateEntree, String matriculeSalarie, String cinSalarie, String cnssSalarie, String statut, String fonction/*, String nomSalarie, String prenomSalarie*/) {

        Session session = sessionFactory.getCurrentSession();
//        try {
//
//            session.connection().setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
//
//        } catch (HibernateException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (SQLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
        // Partie pour la recherche d'un salarie via matricule, nom ou prenom
        String queryRecherche = " AND (S.etat_ID=1 OR S.etat_ID=5) ";

        if (matriculeSalarie != "") {
            queryRecherche += " AND S.MATRICULE='" + matriculeSalarie + "' ";
        }

        if (cinSalarie != "") {
            queryRecherche += " AND S.CIN='" + cinSalarie + "' ";
        }

        if (cnssSalarie != "") {
            queryRecherche += " AND S.CNSS='" + cnssSalarie + "' ";
        }

        if (fonction != "") {

            Fonction objetFonction = parametrageService.getFonction(fonction);
            queryRecherche += " AND S.fonction_PPTEMP_ID='" + objetFonction.getId() + "' ";
        }

        if (statut != "") {

            Statut objetStatut = parametrageService.getStatut(statut);
            queryRecherche += " AND F.codeDiva like '" + objetStatut.getCodeDiva() + "%' AND length(salarie.fonction.codeDiva)=3 ";
        }

        //        if(nomSalarie!=""){
        //        	queryRecherche += " AND S.NOM='" + nomSalarie + "' ";
        //        }
        //        
        //        if(prenomSalarie!=""){
        //        	queryRecherche += " AND S.PRENOM='" + prenomSalarie + "' ";
        //        }
        //        Query query = session.createSQLQuery("SELECT count(*) from SALARIE S ,CHANTIER_SALARIE CS WHERE CHANTIER_ID=? AND S.ID=CS.SALARIE_ID"+queryRecherche
        //        		);
        //      Requête pour lister les salaries qui n'ont pas encore pointés OU BIEN un salarié qui n'a pas encore pointé pour une date dans un chantier
//		Query query = session.createSQLQuery("SELECT count(*) from SALARIE S ,CHANTIER_SALARIE CS, PPTEMP F WHERE CHANTIER_ID=? AND S.ID=CS.SALARIE_ID AND S.fonction_PPTEMP_ID=F.PPTEMP_ID "+queryRecherche
//				+" AND CS.SALARIE_ID NOT IN ( SELECT salarie_ID FROM PRESENCE " +
//				" WHERE chantier_PRJAP_ID=? AND DATE=? AND HEUREENTREE IS NOT NULL AND NOMBREHEURES IS NULL ) AND CS.SALARIE_ID NOT IN ( SELECT salarie_ID FROM PRESENCE " +
//		" WHERE DATE=? GROUP BY SALARIE_ID HAVING (SUM(NOMBREHEURES) + SUM(NOMBREMINUTES) / 60) >= 8 ) ");
        int tab[] = Constante.Type_Fonction_Pointage_Upsit;
        String s = "";
        for (int i = 0; i < tab.length; i++) {
            s += tab[i] + ",";
        }
        s = s.substring(0, s.length() - 1);
        Query query = session.createSQLQuery("SELECT count(*) from SALARIE S ,CHANTIER_SALARIE CS, PPTEMP F WHERE F.UP_FONCT_QUAINZ in (" + s + ") AND CHANTIER_ID=? AND S.ID=CS.SALARIE_ID AND S.fonction_PPTEMP_ID=F.PPTEMP_ID " + queryRecherche
                + " AND CS.SALARIE_ID NOT IN ( SELECT salarie_ID FROM PRESENCE "
                + " WHERE chantier_PRJAP_ID=? AND DATE=? AND HEUREENTREE IS NOT NULL AND NOMBREHEURES IS NULL ) AND CS.SALARIE_ID NOT IN ( SELECT salarie_ID FROM PRESENCE "
                + " WHERE DATE=? GROUP BY SALARIE_ID) ");

        //Paramètres de la requête pour la partie de la liste des salaries qui n'ont pas encore pointés pour une date dans un chantier
        query.setParameter(0, idChantier, StandardBasicTypes.INTEGER);
        query.setParameter(1, idChantier, StandardBasicTypes.INTEGER);
        query.setParameter(2, dateEntree, StandardBasicTypes.DATE);
        query.setParameter(3, dateEntree, StandardBasicTypes.DATE);
//        query.setParameter(":typeF", s, StandardBasicTypes.STRING);

        return query.uniqueResult();
    }

    /**
     * Récupérer la liste des salariés qui n'ont pas encore pointés en entrée
     * dans une date et sur un chantier. matricule, cin, cnss, statut et
     * fonction sont des paramètres de recherche. start et limit sont des
     * paramètres de pagination
     *
     * @param idChantier l'identifiant du chantier dans la base de données
     * @param dateEntree date de pointage d'entrée (format:yyyy/mm/jj)
     * @param matriculeSalarie le matricule du salarié
     * @param cinSalarie le numéro de la carte d'identité nationale du salarié
     * @param cnssSalarie numéro de cnss du salarié
     * @param statut statut du salarié (Exemple: CADRE, OUVRIER....)
     * @param fonction fonction du salarié (exemple: CHEF DE CHANTIER,
     * BOISEUR...etc)
     * @param start pour la pagination, c'est un nombre entier qui représente
     * l'index du premier élément à afficher
     * @param limit pour la pagination, c'est un nombre entier qui représente
     * l'index du dernier élément à afficher
     *
     * @return la liste des Salariés
     */
    public List<Presence> listeSalariesNonPointesEntree(Integer idChantier, Date dateEntree, String matriculeSalarie, String cinSalarie, String cnssSalarie, String statut, String fonction, /*String nomSalarie, String prenomSalarie,*/ int start, int limit) {

        Session session = sessionFactory.getCurrentSession();

        /**
         * ***************************** Requête SQL **************************
         */
        String queryRecherche = " AND (S.etat_ID=1 OR S.etat_ID=5) ";

        // Partie pour la recherche d'un salarie via matricule, nom ou prenom
        if (matriculeSalarie != "") {
            queryRecherche += " AND S.MATRICULE='" + matriculeSalarie + "' ";
        }

        if (cinSalarie != "") {
            queryRecherche += " AND S.CIN='" + cinSalarie + "' ";
        }

        if (cnssSalarie != "") {
            queryRecherche += " AND S.CNSS='" + cnssSalarie + "' ";
        }

        if (fonction != "") {

            Fonction objetFonction = parametrageService.getFonction(fonction);
            queryRecherche += " AND S.fonction_PPTEMP_ID='" + objetFonction.getId() + "' ";
        }

        if (statut != "") {

            Statut objetStatut = parametrageService.getStatut(statut);
            queryRecherche += " AND F.codeDiva like '" + objetStatut.getCodeDiva() + "%' AND length(salarie.fonction.codeDiva)=3 ";
        }

        int tab[] = Constante.Type_Fonction_Pointage_Upsit;
        String s = "";
        for (int i = 0; i < tab.length; i++) {
            s += tab[i] + ",";
        }
        s = s.substring(0, s.length() - 1);
        System.out.println("liste des type fonction pointage upsite" + s);
        Query query = session.createSQLQuery("SELECT * from SALARIE S ,CHANTIER_SALARIE CS, PPTEMP F WHERE F.UP_FONCT_QUAINZ in (" + s + ") AND CHANTIER_ID=? AND S.ID=CS.SALARIE_ID" + queryRecherche + " AND S.fonction_PPTEMP_ID=F.PPTEMP_ID AND CS.SALARIE_ID NOT IN ( SELECT salarie_ID FROM PRESENCE "
                + " WHERE chantier_PRJAP_ID=? AND DATE=? AND HEUREENTREE IS NOT NULL AND NOMBREHEURES IS NULL ) AND CS.SALARIE_ID NOT IN ( SELECT salarie_ID FROM PRESENCE "
                + " WHERE DATE=? GROUP BY SALARIE_ID )"
                + " ORDER BY F.LIB, S.NOM, S.PRENOM").addEntity(Salarie.class);

        // Paramètres de la requête pour la partie de la liste des salariés qui n'ont pas encore pointés pour une date dans un chantier
        query.setParameter(0, idChantier, StandardBasicTypes.INTEGER);
//        query.setParameter(":typeF", s, StandardBasicTypes.STRING);
        query.setParameter(1, idChantier, StandardBasicTypes.INTEGER);
        query.setParameter(2, dateEntree, StandardBasicTypes.DATE);
        query.setParameter(3, dateEntree, StandardBasicTypes.DATE);

        query.setFirstResult(start);
        query.setMaxResults(limit);

        List<Salarie> listeSalaries = query.list();

        //Query query2 = session.createSQLQuery("SELECT * from PRESENCE P WHERE DATE=? GROUP BY P.SALARIE_ID HAVING (SUM(NOMBREHEURES) + SUM(NOMBREMINUTES) / 60) < 8").addEntity(Presence.class);
        Query query2 = session.createSQLQuery("SELECT salarie_ID from PRESENCE P WHERE DATE=?");
        query2.setParameter(0, dateEntree, StandardBasicTypes.DATE);

        List listePresencesMultiples = query2.list();
        List<Integer> listeIdSalariesMultiplesPresences = new ArrayList<Integer>();

        for (int j = 0; j < listePresencesMultiples.size(); j++) {
            listeIdSalariesMultiplesPresences.add((Integer) listePresencesMultiples.get(j));
        }

        List<Presence> listePresences = new ArrayList<Presence>();
        Presence presence = null;

        for (int i = 0; i < listeSalaries.size(); i++) {
            presence = new Presence();
            presence.setId(i);
            presence.setSalarie(listeSalaries.get(i));

            if (listeIdSalariesMultiplesPresences.contains(listeSalaries.get(i).getId())) {
                presence.setMultiplePointage(true);
            }

            listePresences.add(presence);

        }

        return listePresences;

    }

    /**
     * Récupérer le nombre des pointages d'entrée dans une date précisé en
     * paramètres matricule, cin, cnss, statut et fonction sont des paramètres
     * de recherche.
     *
     *
     * @param idChantier l'identifiant du chantier dans la base de données
     * @param dateEntree date de pointage d'entrée (format:yyyy/mm/jj)
     * @param matriculeSalarie le matricule du salarié
     * @param cinSalarie le numéro de la carte d'identité nationale du salarié
     * @param cnssSalarie numéro de cnss du salarié
     * @param statut statut du salarié (Exemple: CADRE, OUVRIER....)
     * @param fonction fonction du salarié (exemple: CHEF DE CHANTIER,
     * BOISEUR...etc)
     *
     * @return le nombre des pointages
     */
    public Object nombrePointagesEntree(Integer idChantier, Date dateEntree, String matriculeSalarie, String cinSalarie, String cnssSalarie, String statut, String fonction/*, String nomSalarie, String prenomSalarie*/) {

        Session session = sessionFactory.getCurrentSession();
//        try {
//
//            session.connection().setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
//
//        } catch (HibernateException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (SQLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
        String queryRecherche = "";

        if (matriculeSalarie != "") {
            queryRecherche += " AND salarie.matricule='" + matriculeSalarie + "' ";
        }

        if (cinSalarie != "") {
            queryRecherche += " AND salarie.cin='" + cinSalarie + "' ";
        }

        if (cnssSalarie != "") {
            queryRecherche += " AND salarie.cnss='" + cnssSalarie + "' ";
        }

        if (statut != "") {

            Statut objetStatut = parametrageService.getStatut(statut);
            queryRecherche += " AND salarie.fonction.codeDiva like '" + objetStatut.getCodeDiva() + "%' AND length(salarie.fonction.codeDiva)=3 ";
        }

        if (fonction != "") {

            Fonction objetFonction = parametrageService.getFonction(fonction);
            queryRecherche += " AND salarie.fonction.id ='" + objetFonction.getId() + "' ";
        }

        //        if(nomSalarie!=""){
        //        	queryRecherche += " AND salarie.nom='" + nomSalarie + "' ";
        //        }
        //        
        //        if(prenomSalarie!=""){
        //        	queryRecherche += " AND salarie.prenom='" + prenomSalarie + "' ";
        //        }
        Query query = session.createQuery("SELECT count(*) FROM Presence WHERE chantier.id=? AND date=? " + queryRecherche + " AND heureEntree is not null");
        query.setParameter(0, idChantier, StandardBasicTypes.INTEGER);
        query.setParameter(1, dateEntree, StandardBasicTypes.DATE);

        return query.uniqueResult();

    }

    /**
     * Récupérer la liste des pointages d'entrée matricule, cin, cnss, statut et
     * fonction sont des paramètres de recherche. start et limit sont des
     * paramètres de pagination
     *
     * @param idChantier l'identifiant du chantier dans la base de données
     * @param dateEntree date de pointage d'entrée (format:yyyy/mm/jj)
     * @param matriculeSalarie le matricule du salarié
     * @param cinSalarie le numéro de la carte d'identité nationale du salarié
     * @param cnssSalarie numéro de cnss du salarié
     * @param statut statut du salarié (Exemple: CADRE, OUVRIER....)
     * @param fonction fonction du salarié (exemple: CHEF DE CHANTIER,
     * BOISEUR...etc)
     * @param start pour la pagination, c'est un nombre entier qui représente
     * l'index du premier élément à afficher
     * @param limit pour la pagination, c'est un nombre entier qui représente
     * l'index du dernier élément à afficher
     *
     * @return la liste des Présences d'entrée
     */
    public List<Presence> listePointagesEntree(Integer idChantier, Date dateEntree, String matriculeSalarie, String cinSalarie, String cnssSalarie, String statut, String fonction, /*String nomSalarie, String prenomSalarie,*/ int start, int limit) {

        Session session = sessionFactory.getCurrentSession();
//        try {
//
//            session.connection().setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
//
//        } catch (HibernateException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (SQLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
        String queryRecherche = "";

        if (matriculeSalarie != "") {
            queryRecherche += " AND salarie.matricule='" + matriculeSalarie + "' ";
        }

        if (cinSalarie != "") {
            queryRecherche += " AND salarie.cin='" + cinSalarie + "' ";
        }

        if (cnssSalarie != "") {
            queryRecherche += " AND salarie.cnss='" + cnssSalarie + "' ";
        }

        if (statut != "") {

            Statut objetStatut = parametrageService.getStatut(statut);
            queryRecherche += " AND salarie.fonction.codeDiva like '" + objetStatut.getCodeDiva() + "%' AND length(salarie.fonction.codeDiva)=3 ";
        }

        if (fonction != "") {

            Fonction objetFonction = parametrageService.getFonction(fonction);
            queryRecherche += " AND salarie.fonction.id ='" + objetFonction.getId() + "' ";
        }

        //	        if(nomSalarie!=""){
        //	        	queryRecherche += " AND salarie.nom='" + nomSalarie + "' ";
        //	        }
        //	        
        //	        if(prenomSalarie!=""){
        //	        	queryRecherche += " AND salarie.prenom='" + prenomSalarie + "' ";
        //	        }
        Query query = session.createQuery("FROM Presence WHERE chantier.id=? AND date=? " + queryRecherche + " AND heureEntree is not null ORDER BY salarie.fonction.fonction, salarie.nom, salarie.prenom");
        query.setParameter(0, idChantier, StandardBasicTypes.INTEGER);
        query.setParameter(1, dateEntree, StandardBasicTypes.DATE);

        query.setFirstResult(start);
        query.setMaxResults(limit);

        List<Presence> listePresences = (List<Presence>) query.list();
        System.out.println("nombre list presence " + listePresences.size());
        return listePresences;

    }

    /**
     * **************************** FIN DE POINTAGE D'ENTRÉE
     * *********************************************************
     */
    /**
     * ***************************************************************************************************************
     */
    /**
     * ***************************************************************************************************************
     */
    /**
     * **************************** DEBUT DE POINTAGE DE SORTIE
     * *******************************************************
     */
    /**
     * Récupérer le nombre des salariés qui n'ont pas encore pointés en sortie
     * dans une date et sur un chantier. matricule, cin, cnss, statut et
     * fonction sont des paramètres de recherche.
     *
     *
     * @param idChantier l'identifiant du chantier dans la base de données
     * @param dateEntree date de pointage d'entrée (format:yyyy/mm/jj)
     * @param matriculeSalarie le matricule du salarié
     * @param cinSalarie le numéro de la carte d'identité nationale du salarié
     * @param cnssSalarie numéro de cnss du salarié
     * @param statut statut du salarié (Exemple: CADRE, OUVRIER....)
     * @param fonction fonction du salarié (exemple: CHEF DE CHANTIER,
     * BOISEUR...etc)
     *
     * @return le nombre des salariés non pointés en sortie
     */
    public Object nombreSalariesNonPointesSortie(Integer idChantier, Date dateSortie, String matriculeSalarie, String cinSalarie, String cnssSalarie, String statut, String fonction/*, String nomSalarie, String prenomSalarie*/) {

        Session session = sessionFactory.getCurrentSession();
//        try {
//
//            session.connection().setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
//
//        } catch (HibernateException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (SQLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
        String queryRecherche = "";

        if (matriculeSalarie != "") {
            queryRecherche += " AND salarie.matricule='" + matriculeSalarie + "' ";
        }

        if (cinSalarie != "") {
            queryRecherche += " AND salarie.cin='" + cinSalarie + "' ";
        }

        if (cnssSalarie != "") {
            queryRecherche += " AND salarie.cnss='" + cnssSalarie + "' ";
        }

        if (fonction != "") {

            Fonction objetFonction = parametrageService.getFonction(fonction);
            queryRecherche += " AND salarie.fonction.id ='" + objetFonction.getId() + "' ";
        }

        if (statut != "") {

            Statut objetStatut = parametrageService.getStatut(statut);
            queryRecherche += " AND salarie.statut.id ='" + objetStatut.getId() + "' ";
        }

        //     if(nomSalarie!=""){
        //     	queryRecherche += " AND salarie.nom='" + nomSalarie + "' ";
        //     }
        //     
        //     if(prenomSalarie!=""){
        //     	queryRecherche += " AND salarie.prenom='" + prenomSalarie + "' ";
        //     }
        Query query = session.createQuery("SELECT count(*) FROM Presence WHERE chantier.id=? AND date=? " + queryRecherche + " AND heureSortie is null");
        query.setParameter(0, idChantier, StandardBasicTypes.INTEGER);
        query.setParameter(1, dateSortie, StandardBasicTypes.DATE);

        return query.uniqueResult();
    }

    /**
     * Récupérer la liste des salariés qui n'ont pas encore pointés en sortie
     * dans une date et sur un chantier. matricule, cin, cnss, statut et
     * fonction sont des paramètres de recherche. start et limit sont des
     * paramètres de pagination
     *
     *
     * @param idChantier l'identifiant du chantier dans la base de données
     * @param dateEntree date de pointage d'entrée (format:yyyy/mm/jj)
     * @param matriculeSalarie le matricule du salarié
     * @param cinSalarie le numéro de la carte d'identité nationale du salarié
     * @param cnssSalarie numéro de cnss du salarié
     * @param statut statut du salarié (Exemple: CADRE, OUVRIER....)
     * @param fonction fonction du salarié (exemple: CHEF DE CHANTIER,
     * BOISEUR...etc)
     * @param start pour la pagination, c'est un nombre entier qui représente
     * l'index du premier élément à afficher
     * @param limit pour la pagination, c'est un nombre entier qui représente
     * l'index du dernier élément à afficher
     *
     * @return la liste des salariés non pointés en sortie
     */
    public List<Presence> listeSalariesNonPointesSortie(Integer idChantier, Date dateSortie, String matriculeSalarie, String cinSalarie, String cnssSalarie, String statut, String fonction, /* String nomSalarie, String prenomSalarie,*/ int start, int limit) {
        Session session = sessionFactory.getCurrentSession();
//        try {
//
//            session.connection().setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
//
//        } catch (HibernateException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (SQLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
        String queryRecherche = "";

        if (matriculeSalarie != "") {
            queryRecherche += " AND salarie.matricule='" + matriculeSalarie + "' ";
        }

        if (cinSalarie != "") {
            queryRecherche += " AND salarie.cin='" + cinSalarie + "' ";
        }

        if (cnssSalarie != "") {
            queryRecherche += " AND salarie.cnss='" + cnssSalarie + "' ";
        }

        if (fonction != "") {

            Fonction objetFonction = parametrageService.getFonction(fonction);
            queryRecherche += " AND salarie.fonction.id ='" + objetFonction.getId() + "' ";
        }

        if (statut != "") {

            Statut objetStatut = parametrageService.getStatut(statut);
            queryRecherche += " AND salarie.statut.id ='" + objetStatut.getId() + "' ";
        }

        //	        if(nomSalarie!=""){
        //	        	queryRecherche += " AND salarie.nom='" + nomSalarie + "' ";
        //	        }
        //	        
        //	        if(prenomSalarie!=""){
        //	        	queryRecherche += " AND salarie.prenom='" + prenomSalarie + "' ";
        //	        }
        Query query = session.createQuery("FROM Presence WHERE chantier.id=? AND date=? " + queryRecherche + " AND heureSortie is null ORDER BY salarie.fonction.fonction, salarie.nom, salarie.prenom");
        query.setParameter(0, idChantier, StandardBasicTypes.INTEGER);
        query.setParameter(1, dateSortie, StandardBasicTypes.DATE);

        List<Presence> listePresences = (ArrayList<Presence>) query.list();
        System.out.println("liste des presence " + listePresences.size());
        return listePresences;

    }

    /**
     * Récupérer le nombre des salariés pointés en sortie sur le chantier et la
     * date passés en paramètres matricule, cin, cnss, statut et fonction sont
     * des paramètres de recherche.
     *
     *
     * @param idChantier l'identifiant du chantier dans la base de données
     * @param dateEntree date de pointage d'entrée (format:yyyy/mm/jj)
     * @param matriculeSalarie le matricule du salarié
     * @param cinSalarie le numéro de la carte d'identité nationale du salarié
     * @param cnssSalarie numéro de cnss du salarié
     * @param statut statut du salarié (Exemple: CADRE, OUVRIER....)
     * @param fonction fonction du salarié (exemple: CHEF DE CHANTIER,
     * BOISEUR...etc)
     *
     * @return le nombre des salariés pointés en sortie
     */
    public Object nombrePointagesSortie(Integer idChantier, Date dateSortie, String matriculeSalarie, String cinSalarie, String cnssSalarie, String statut, String fonction/*, String nomSalarie, String prenomSalarie*/) {

        Session session = sessionFactory.getCurrentSession();
//        try {
//
//            session.connection().setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
//
//        } catch (HibernateException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (SQLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
        String queryRecherche = "";

        if (matriculeSalarie != "") {
            queryRecherche += " AND salarie.matricule='" + matriculeSalarie + "' ";
        }

        if (cinSalarie != "") {
            queryRecherche += " AND salarie.cin='" + cinSalarie + "' ";
        }

        if (cnssSalarie != "") {
            queryRecherche += " AND salarie.cnss='" + cnssSalarie + "' ";
        }

        if (fonction != "") {

            Fonction objetFonction = parametrageService.getFonction(fonction);
            queryRecherche += " AND salarie.fonction.id ='" + objetFonction.getId() + "' ";
        }

        if (statut != "") {

            Statut objetStatut = parametrageService.getStatut(statut);
            queryRecherche += " AND salarie.statut.id ='" + objetStatut.getId() + "' ";
        }

        //        if(nomSalarie!=""){
        //        	queryRecherche += " AND salarie.nom='" + nomSalarie + "' ";
        //        }
        //        
        //        if(prenomSalarie!=""){
        //        	queryRecherche += " AND salarie.prenom='" + prenomSalarie + "' ";
        //        }
        Query query = session.createQuery("SELECT count(*) FROM Presence WHERE chantier.id=? AND date=? " + queryRecherche + " AND heureSortie is not null");
        query.setParameter(0, idChantier, StandardBasicTypes.INTEGER);
        query.setParameter(1, dateSortie, StandardBasicTypes.DATE);

        return query.uniqueResult();

    }

    /**
     * Récupérer la liste des pointages de sortie sur le chantier et la date
     * passés en paramètres. matricule, cin, cnss, statut et fonction sont des
     * paramètres de recherche. start et limit sont des paramètres de pagination
     *
     *
     * @param idChantier l'identifiant du chantier dans la base de données
     * @param dateEntree date de pointage d'entrée (format:yyyy/mm/jj)
     * @param matriculeSalarie le matricule du salarié
     * @param cinSalarie le numéro de la carte d'identité nationale du salarié
     * @param cnssSalarie numéro de cnss du salarié
     * @param statut statut du salarié (Exemple: CADRE, OUVRIER....)
     * @param fonction fonction du salarié (exemple: CHEF DE CHANTIER,
     * BOISEUR...etc)
     * @param start pour la pagination, c'est un nombre entier qui représente
     * l'index du premier élément à afficher
     * @param limit pour la pagination, c'est un nombre entier qui représente
     * l'index du dernier élément à afficher
     *
     * @return la liste des pointage de sortie
     */
    public List<Presence> listePointagesSortie(Integer idChantier, Date dateSortie, String matriculeSalarie, String cinSalarie, String cnssSalarie, String statut, String fonction,/*String nomSalarie, String prenomSalarie,*/ int start, int limit) {

        Session session = sessionFactory.getCurrentSession();
        String queryRecherche = "";

        if (matriculeSalarie != "") {
            queryRecherche += " AND salarie.matricule='" + matriculeSalarie + "' ";
        }

        if (cinSalarie != "") {
            queryRecherche += " AND salarie.cin='" + cinSalarie + "' ";
        }

        if (cnssSalarie != "") {
            queryRecherche += " AND salarie.cnss='" + cnssSalarie + "' ";
        }

        if (fonction != "") {

            Fonction objetFonction = parametrageService.getFonction(fonction);
            queryRecherche += " AND salarie.fonction.id ='" + objetFonction.getId() + "' ";
        }

        if (statut != "") {

            Statut objetStatut = parametrageService.getStatut(statut);
            queryRecherche += " AND salarie.statut.id ='" + objetStatut.getId() + "' ";
        }
        Query query = session.createQuery("FROM Presence WHERE chantier.id=? AND date=? " + queryRecherche + " AND heureSortie is not null ORDER BY salarie.fonction.fonction, salarie.nom, salarie.prenom");
        query.setParameter(0, idChantier, StandardBasicTypes.INTEGER);
        query.setParameter(1, dateSortie, StandardBasicTypes.DATE);

        List<Presence> listePresences = (ArrayList<Presence>) query.list();
        System.out.println("liste présence " + listePresences.size());
        return listePresences;

    }

    /**
     * **************************** FIN DE POINTAGE DE SORTIE
     * *********************************************************
     */
    /**
     * ***************************************************************************************************************
     */
    /**
     * ***************************************************************************************************************
     */
    /**
     * **************************** DEBUT DE LA RECHERCHE DES PRESENCES D'UN
     * SALARIÉ *********************************
     */
    /**
     * Récupérer le nombre des présences d'un salarié sur un chantier et dans
     * une période (entre dateDe et dateA passées en paramètres)
     *
     * @param matricule le matricule du salarié
     * @param cin le numéro de la carte d'identité nationale
     * @param cnss le numéro de cnss
     * @param idChantier l'identifiant du chantier dans la base de données
     * @param dateDe date début (format:yyyy/mm/jj)
     * @param dateA date de fin (format:yyyy/mm/jj)
     *
     * @return le nombre des présences
     */
    public Object nombrePresencesSalarie(String matricule, String cin, String cnss, /*String nom, String prenom,*/ Integer idChantier, Date dateDe, Date dateA) {

        Session session = sessionFactory.getCurrentSession();
//        try {
//
//            session.connection().setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
//
//        } catch (HibernateException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (SQLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
        String queryRecherche = "";

        if (matricule != "") {
            queryRecherche += " AND salarie.matricule='" + matricule + "' ";
        }

        if (cin != "") {
            queryRecherche += " AND salarie.cin='" + cin + "' ";
        }

        if (cnss != "") {
            queryRecherche += " AND salarie.cnss='" + cnss + "' ";
        }

        //    if(nom!=""){
        //    	queryRecherche += " AND salarie.nom='" + nom + "' ";
        //    }
        //    
        //    if(prenom!=""){
        //    	queryRecherche += " AND salarie.prenom='" + prenom + "' ";
        //    }
        if (idChantier != null) {
            queryRecherche += " AND chantier.id=" + idChantier + " ";
        }

        if (dateDe != null && dateA != null) {
            queryRecherche += " AND date BETWEEN :dateDe AND :dateA ";

        } else {

            if (dateDe != null) {
                queryRecherche += " AND date=:dateDe ";
            }

            if (dateA != null) {
                queryRecherche += " AND date=:dateA ";
            }

        }

        queryRecherche = queryRecherche.replaceFirst("AND", " WHERE");

        if (queryRecherche == "") {
            return 0;
        }

        Query query = session.createQuery("SELECT count(*) FROM Presence " + queryRecherche + "");

        if (dateDe != null && dateA != null) {

            query.setParameter("dateDe", dateDe);
            query.setParameter("dateA", dateA);

        } else {

            if (dateDe != null) {
                query.setParameter("dateDe", dateDe);

            }

            if (dateA != null) {
                query.setParameter("dateA", dateA);

            }

        }

        return query.uniqueResult();
    }

    /**
     * Récupérer les présences d'un salarié sur un chantier et dans une période
     * les paramètres passés sont dédiés à la recherche start et limit sontdes
     * paramètres de pagination
     *
     *
     * @param matricule le matricule du salarié
     * @param cin le numéro de la carte d'identité nationale
     * @param cnss le numéro de cnss
     * @param idChantier l'identifiant du chantier dans la base de données
     * @param dateDe première date pour la période de présence
     * @param dateA deuxième date pour la période de présence
     * @param start pour la pagination, c'est un nombre entier qui représente
     * l'index du premier élément à afficher
     * @param limit pour la pagination, c'est un nombre entier qui représente
     * l'index du dernier élément à afficher
     *
     * @return les Présences d'un salarié
     */
    public List<Presence> listePresencesSalarie(String matricule, String cin, String cnss, /*String nom, String prenom,*/ Integer idChantier, Date dateDe, Date dateA, int start, int limit) {

        Session session = sessionFactory.getCurrentSession();
//        try {
//
//            session.connection().setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
//
//        } catch (HibernateException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (SQLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
        String queryRecherche = "";

        if (matricule != "") {
            queryRecherche += " AND salarie.matricule='" + matricule + "' ";
        }

        if (cin != "") {
            queryRecherche += " AND salarie.cin='" + cin + "' ";
        }

        if (cnss != "") {
            queryRecherche += " AND salarie.cnss='" + cnss + "' ";
        }

        //	        if(nom!=""){
        //	        	queryRecherche += " AND salarie.nom='" + nom + "' ";
        //	        }
        //	        
        //	        if(prenom!=""){
        //	        	queryRecherche += " AND salarie.prenom='" + prenom + "' ";
        //	        }
        if (idChantier != null) {
            queryRecherche += " AND chantier.id=" + idChantier + " ";
        }

        if (dateDe != null && dateA != null) {
            queryRecherche += " AND date BETWEEN :dateDe AND :dateA ";

        } else {

            if (dateDe != null) {
                queryRecherche += " AND date=:dateDe ";
            }

            if (dateA != null) {
                queryRecherche += " AND date=:dateA ";
            }

        }

        queryRecherche = queryRecherche.replaceFirst("AND", " WHERE");

        if (queryRecherche == "") {

            return new ArrayList<Presence>();
        }
        System.out.println("§§§§§§§§§§§§§§> Presence req : FROM Presence   " + queryRecherche + " ORDER BY date DESC");
        Query query = session.createQuery("FROM Presence   " + queryRecherche + " ORDER BY date DESC");

        if (dateDe != null && dateA != null) {

            query.setParameter("dateDe", dateDe);
            query.setParameter("dateA", dateA);

        } else {

            if (dateDe != null) {
                query.setParameter("dateDe", dateDe);

            }

            if (dateA != null) {
                query.setParameter("dateA", dateA);

            }

        }

//        query.setFirstResult(start);
//        query.setMaxResults(limit);
        List<Presence> listePresencesSalarie = (ArrayList<Presence>) query.list();
        System.out.println("@@  presence liste " + listePresencesSalarie.size());
        return listePresencesSalarie;
    }

    /**
     * Récupérer le nombre des heures de présences d'un salarié sur une période
     * (dateDe et dateA passées en paramètres)
     *
     * @param matricule le matricule du salarié
     * @param cin le numéro de la carte d'identité nationale du salarié
     * @param cnss numéro de cnss du salarié
     * @param idChantier l'identifiant du chantier dans la base de données
     * @param dateDe première date pour la période de présence
     * @param dateA deuxième date pour la période de présence
     *
     * @return nombre des heures sous forme d'une Chaine de caractère (exemple:
     * "45H30min")
     */
    public String nombreHeuresMinutesPresencesSalarie(String matricule, String cin, String cnss, /*String nom, String prenom,*/ Integer idChantier, Date dateDe, Date dateA) {
        String rt = "0";
        try {
           
        Salarie salarie = salarieService.getSalarie(matricule, cin, cnss, "", "");

        Session session = sessionFactory.getCurrentSession();
//        try {
//
//            session.connection().setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
//
//        } catch (HibernateException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (SQLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
        String queryRecherche = " WHERE salarie_ID=" + salarie.getId() + " ";

        //		    if(matricule!=""){
        //	        	queryRecherche += " AND salarie.matricule='" + matricule + "' ";
        //	        }
        //	        
        //		    if(cin!=""){
        //	        	queryRecherche += " AND salarie.cin='" + cin + "' ";
        //	        }
        //	        
        //	        if(cnss!=""){
        //	        	queryRecherche += " AND salarie.cnss='" + cnss + "' ";
        //	        }
        //	        if(nom!=""){
        //	        	queryRecherche += " AND salarie.nom='" + nom + "' ";
        //	        }
        //	        
        //	        if(prenom!=""){
        //	        	queryRecherche += " AND salarie.prenom='" + prenom + "' ";
        //	        }
        if (idChantier != null) {
            queryRecherche += " AND chantier_PRJAP_ID=" + idChantier + " ";
        }

        if (dateDe != null && dateA != null) {
            queryRecherche += " AND date BETWEEN :dateDe AND :dateA ";

        } else {

            if (dateDe != null) {
                queryRecherche += " AND DATE=:dateDe ";
            }

            if (dateA != null) {
                queryRecherche += " AND DATE=:dateA ";
            }

        }

        //	        queryRecherche = queryRecherche.replaceFirst("AND", " WHERE");
        if (queryRecherche == "") {

            return "";
        }

        Query query = session.createSQLQuery("SELECT CAST(sum(P.NOMBREHEURES) + sum(P.NOMBREMINUTES)/60 AS VARCHAR(10)) + 'H ' + CAST (sum(P.NOMBREMINUTES)%60 AS VARCHAR(10))+ 'Min' as totalHeures FROM PRESENCE P" + queryRecherche);

        //	        Query query=session.createQuery("SELECT sum(nombreHeures) + sum(nombreMinutes)/60, sum(nombreMinutes) DIV 60 FROM Presence " +queryRecherche);
        if (dateDe != null && dateA != null) {

            query.setParameter("dateDe", dateDe);
            query.setParameter("dateA", dateA);

        } else {

            if (dateDe != null) {
                query.setParameter("dateDe", dateDe);

            }

            if (dateA != null) {
                query.setParameter("dateA", dateA);

            }

        }

        List listePresences = query.list();

//		Object[] o = (Object[]) listePresences.get(0);
//
//		if(o[0] == null)
//			return "";
        rt= (String) listePresences.get(0);
         
        } catch (Exception e) {
            System.out.println("Erreur de récuperation la somme car "+e.getMessage());
        }
        return rt;
    }

    public List<Presence> findPresencesSalarie(Integer idChantier, Date dateDe, Date dateA) {
        List<Presence> lp = new ArrayList<Presence>();
        try { 
                Session session = sessionFactory.getCurrentSession();
                SimpleDateFormat  df = new SimpleDateFormat ("yyyy-MM-dd");
                String queryRecherche = " FROM Presence p WHERE p.salarie is not null " ;
                if (idChantier != null) {
                    queryRecherche += " AND p.chantier.id=" + idChantier ;
                }
                /*if (dateDe != null && dateA != null) {
                    queryRecherche += " AND p.date BETWEEN '"+df.format(dateDe)+"' AND '"+df.format(dateA)+"'" ;
                } else {*/
                    if (dateDe != null) {
                        queryRecherche += " AND p.date >= '"+df.format(dateDe)+"'" ;
                    }
                    if (dateA != null) {  
                        queryRecherche += " AND p.date <=  '"+df.format(dateA)+"'";
                    }
                //} 
                System.out.println(" queryRecherche ::::::> "+queryRecherche);
                lp = (List<Presence>) this.getHibernateTemplate().find(queryRecherche);   
               // Query query = session.createSQLQuery(queryRecherche); 
               // lp = query.list();

        } catch (Exception e) {
            System.out.println(" Erreur de récupération list des presences car "+e.getMessage());
        }
        return lp;
    }

    /**
     * **************************** FIN DE LA RECHERCHE DES PRESENCES D'UN
     * SALARIÉ ***********************************
     */
    /**
     * ***************************************************************************************************************
     */
    //	public Object nombrePointagesEntree(int idChantier){
    //		
    //        Session session=sessionFactory.getCurrentSession();
    //		
    //		Query query=session.createQuery("SELECT ch.salaries FROM Chantier ch WHERE ch.id=? ");
    //		query.setParameter(0, idChantier, Hibernate.INTEGER);
    //				
    //		return query.list().size();
    //	}
    //	public List<Presence> listePointagesEntree(int codeChantier, String dateEntree, String heureEntree, int start, int limit){
    //		
    //		  Session session=sessionFactory.getCurrentSession();
    //	
    //		  Query query=session.createQuery("SELECT ch.salaries FROM Chantier ch WHERE ch.id=? ");
    //		    query.setParameter(0, codeChantier, Hibernate.INTEGER);
    //		    query.setFirstResult(start);
    //		    query.setMaxResults(limit);
    //		   
    //		  List<Salarie> listeSalaries=query.list();
    //		  		  
    //		  List<Presence> listePresences=new ArrayList<Presence>();
    //		  Presence presence=null;
    //		  
    //		  for(int i=0;i<listeSalaries.size();i++){
    //			  presence= new Presence();
    //			  
    //			  presence.setId(i);
    //			  presence.setSalarie(listeSalaries.get(i));
    //			  listePresences.add(presence);
    //			  
    //		  }
    //		  
    //		
    //		return listePresences;
    //		
    //	}
    //	
    //Pointage de sortie
    //public Object nombrePointagesSortie(int idChantier){
    //		
    //        Session session=sessionFactory.getCurrentSession();
    //		
    //		Query query=session.createQuery("SELECT ch.salaries FROM Chantier ch WHERE ch.id=? ");
    //		query.setParameter(0, idChantier, Hibernate.INTEGER);
    //				
    //		return query.list().size();
    //	}
    //	
    //	            
    //	public List<Presence> listePointagesSortie(int codeChantier, String dateSortie, String heureSortie, int start, int limit){
    //		
    //		  Session session=sessionFactory.getCurrentSession();
    //	
    //		  Query query=session.createQuery("SELECT ch.salaries FROM Chantier ch WHERE ch.id=? ");
    //		    query.setParameter(0, codeChantier, Hibernate.INTEGER);
    //		    query.setFirstResult(start);
    //		    query.setMaxResults(limit);
    //		   
    //		  List<Salarie> listeSalaries=query.list();
    //		  		  
    //		  List<Presence> listePresences=new ArrayList<Presence>();
    //		  Presence presence=null;
    //		  
    //		  for(int i=0;i<listeSalaries.size();i++){
    //			  presence= new Presence();
    //			  
    //			  presence.setId(i);
    //			  presence.setSalarie(listeSalaries.get(i));
    //			  listePresences.add(presence);
    //			  
    //		  }
    //		  
    //		
    //		return listePresences;
    //		
    //	}
    /**
     * Créer une nouvelle présence
     *
     * @param presence instance de l'objet Presence, il doit au préaalabe
     * recevoir les informations du présence via les setters puis passé en
     * paramètres
     */
    public void ajouterPresence(Presence presence) {
        logger.debug("Ajouter une presence");

        Session session = sessionFactory.getCurrentSession();
        Transaction tx = null;
//        try {
//
//            session.connection().setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
//            session.connection().setAutoCommit(true);
//
//        } catch (HibernateException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (SQLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//		try{
//			tx = session.beginTransaction();
        Integer presenceId = (Integer) session.save(presence);

        presence.setId(presenceId);
        presence.setPresenceId(presenceId);
        presence.setCe1(String.valueOf(1));
        presence.setConf("DPAIE");
        session.update(presence);
//			tx.commit();
//		}catch (Exception e) {
//			tx.rollback();
//		}

    }

    /**
     * modifier une présence
     *
     * @param presence instance de l'objet Presence, il doit au préaalabe
     * récupéré à partir de la base de données et recevoir les nouvelles
     * informations via les setters puis passé en paramètres
     */
    public void modifierPresence(Presence presence) {
        logger.debug("Modifier une presence");

        Session session = sessionFactory.getCurrentSession();
//        try {
//
//            session.connection().setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
//
//        } catch (HibernateException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (SQLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
        presence.setPresenceId(presence.getId());
        presence.setCe1(String.valueOf(1));
        presence.setConf("DPAIE");
        session.update(presence);
    }

    /**
     * Récupérer une présence à partir de la base de données
     *
     * @param idPresence l'identifiant de présence dans la base de données
     * @return Présence
     */
    public Presence getPresenceById(Integer idPresence) {
        logger.debug("Récupération d'une presence via son id");

        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("FROM Presence WHERE ID=?");
        query.setParameter(0, idPresence, StandardBasicTypes.INTEGER);
        return (Presence) query.uniqueResult();
    }

    /**
     * Récupérer pointage (entrée ou sortie) d'un salarié sur un chantier et
     * dans une date
     *
     * @param idChantier l'identifiant du chantier dans la base de données
     * @param date date de pointage (format yyyy/mm/jj)
     * @param idSalarie l'identifiant du salarié dans la base de données
     * @param typePointage type de pointage (Entrée --> PE, Sortie--> PS)
     *
     * @return Présence
     */
    public Presence getPresence(Integer idChantier, Date date, Integer idSalarie, String typePointage) {
        logger.debug("Récupération d'une presence via idChantier, date et idSalarie");

        Session session = sessionFactory.getCurrentSession();
//        try {
//
//            session.connection().setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
//
//        } catch (HibernateException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (SQLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
        Query query = null;

        if (typePointage == "PE") {
            query = session.createSQLQuery("SELECT ID,salarie_ID,chantier_PRJAP_ID,DATE,HEUREENTREE,HEURESORTIE,NOMBREHEURES,NOMBREMINUTES,LONGDATETIMEENTREE,LONGDATETIMESORTIE,HEUREENTREEREELLE,HEURESORTIEREELLE,DATESAISIEHEUREENTREE,DATESAISIEHEURESORTIE,CREEPAR, DATECREATION FROM PRESENCE WHERE chantier_PRJAP_ID=? AND DATE=? AND salarie_ID=? AND HEUREENTREE is null AND HEURESORTIE is not null");
        }
        if (typePointage == "Evol") {
            query = session.createSQLQuery("SELECT ID,salarie_ID,chantier_PRJAP_ID,DATE,HEUREENTREE,HEURESORTIE,NOMBREHEURES,NOMBREMINUTES,LONGDATETIMEENTREE,LONGDATETIMESORTIE,HEUREENTREEREELLE,HEURESORTIEREELLE,DATESAISIEHEUREENTREE,DATESAISIEHEURESORTIE,CREEPAR, DATECREATION FROM PRESENCE WHERE chantier_PRJAP_ID=? AND DATE=? AND salarie_ID=? AND HEUREENTREE is not null AND HEURESORTIE is null");
        }

        if (typePointage == "PS") {
            query = session.createSQLQuery("SELECT ID,salarie_ID,chantier_PRJAP_ID,DATE,HEUREENTREE,HEURESORTIE,NOMBREHEURES,NOMBREMINUTES,LONGDATETIMEENTREE,LONGDATETIMESORTIE,HEUREENTREEREELLE,HEURESORTIEREELLE,DATESAISIEHEUREENTREE,DATESAISIEHEURESORTIE,CREEPAR, DATECREATION FROM PRESENCE WHERE chantier_PRJAP_ID=? AND DATE=? AND salarie_ID=? AND HEURESORTIE is null");
        }

        if (typePointage == "") {
            query = session.createSQLQuery("SELECT ID,salarie_ID,chantier_PRJAP_ID,DATE,HEUREENTREE,HEURESORTIE,NOMBREHEURES,NOMBREMINUTES,LONGDATETIMEENTREE,LONGDATETIMESORTIE,HEUREENTREEREELLE,HEURESORTIEREELLE,DATESAISIEHEUREENTREE,DATESAISIEHEURESORTIE,CREEPAR, DATECREATION FROM PRESENCE WHERE chantier_PRJAP_ID=? AND DATE=? AND salarie_ID=?");
        }

        query.setParameter(0, idChantier, StandardBasicTypes.INTEGER);
        query.setParameter(1, date, StandardBasicTypes.DATE);
        query.setParameter(2, idSalarie, StandardBasicTypes.INTEGER);

        //List<Presence> presences = query.list();
        List listeObjetsPresences = query.list();

        if (listeObjetsPresences.isEmpty()) {
            return null;
        } else {
            Presence objetPresence = null;
            Chantier objetChantier = null;
            Salarie objetSalarie = null;

            Object[] o = (Object[]) listeObjetsPresences.get(0);

            objetPresence = new Presence();
            objetPresence.setId((Integer) o[0]);

            objetSalarie = new Salarie();
            objetSalarie.setId((Integer) o[1]);
            objetPresence.setSalarie(objetSalarie);

            objetChantier = new Chantier();
            objetChantier.setId((Integer) o[2]);
            objetPresence.setChantier(objetChantier);

//	 			String chaineDate=((Date) o[3]).toString();
//	 			chaineDate = chaineDate.substring(0,4) + "/" + chaineDate.substring(5,7) + "/" + chaineDate.substring(8);
            objetPresence.setDate((Date) o[3]);
            objetPresence.setHeureEntree((String) o[4]);
            objetPresence.setHeureSortie((String) o[5]);
            objetPresence.setNombreHeures((Integer) o[6]);
            objetPresence.setNombreMinutes((Integer) o[7]);
            if (o[8] != null) {
                objetPresence.setLongDateTimeEntree(Long.parseLong(String.valueOf(o[8])));
            }
            if (o[9] != null) {
                objetPresence.setLongDateTimeSortie(Long.parseLong(String.valueOf(o[9])));
            }
            objetPresence.setHeureEntreeReelle((String) o[10]);
            objetPresence.setHeureSortieReelle((String) o[11]);
            objetPresence.setDateSaisieHeureEntree((Date) o[12]);
            objetPresence.setDateSaisieHeureSortie((Date) o[13]);

            if (o[14] != null) {
                objetPresence.setCreePar((String) o[14]);
            }
            if (o[15] != null) {
                objetPresence.setDateCreation((Date) o[15]);
            }

            return objetPresence;

        }

    }

    /**
     * Récupérer pointage (entrée ou sortie) d'un salarié sur un chantier et
     * dans une date
     *
     * @param idChantier l'identifiant du chantier dans la base de données
     * @param idSalarie l'identifiant du salarié dans la base de données
     *
     * @return Présence
     */
    public Presence getPresence(Integer idChantier, Integer idSalarie) {
        logger.debug("Récupération d'une presence via idChantier, date et idSalarie");

        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("FROM Presence WHERE chantier.id!=? AND salarie.id=? AND heureEntree is not null AND heureSortie is null order by date desc");

        query.setMaxResults(1);
        query.setParameter(0, idChantier, StandardBasicTypes.INTEGER);
        query.setParameter(1, idSalarie, StandardBasicTypes.INTEGER);

        Presence presence = (Presence) query.uniqueResult();
        return presence;
    }

    /**
     * Supprimer une présence
     *
     * @param presence
     */
    public void supprimerPresence(Presence presence) {
        logger.debug("Suppression d'une presence");

        Session session = sessionFactory.getCurrentSession();
//        try {
//
//            session.connection().setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
//
//        } catch (HibernateException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (SQLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
        session.delete(presence);

    }

    public void supprimerPresence(Integer salarieId, Integer chantierId, Date datePointage) {
        logger.debug("Suppression de presence(s) dans une date");

        Session session = sessionFactory.getCurrentSession();
//        try {
//
//            session.connection().setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
//
//        } catch (HibernateException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (SQLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
        Query query = session.createSQLQuery("DELETE FROM PRESENCE WHERE salarie_ID = ? AND DATE=?");
        query.setParameter(0, salarieId, StandardBasicTypes.INTEGER);
        query.setParameter(1, datePointage, StandardBasicTypes.DATE);
//		query.setParameter(2, chantierId, Hibernate.INTEGER);
        query.executeUpdate();

    }

    /**
     * ***************************************************************************************************************
     */
    /**
     * **************************** DEBUT DE LA RECHERCHE DES PRESENCES D'UN
     * SALARIÉ (AU NIVEAU DU DETAIL SALARIÉ) *********************************
     */
    /**
     * Récupérer le nombre des présences d'un salarié dans un chantier et sur
     * une période
     *
     * @param matriculeSalarie
     * @param idSalarie l'identifiant du salarié dans la base de données
     * @param idChantier l'identifiant du chantier dans la base de données
     * @param dateDe première date pour la période de présence
     * @param dateA deuxième date pour la période de présence
     *
     * @return le nombre des présences du salarié
     */
    public Integer nombrePresencesSalarie(String matriculeSalarie, Integer idChantier, Date dateDe, Date dateA) {

        Session session = sessionFactory.getCurrentSession();

//        try {
//
//            session.connection().setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
//
//        } catch (HibernateException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (SQLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
        String queryRecherche = "";

        if (idChantier != null) {
            queryRecherche += " AND chantier.id=" + idChantier + " ";
        }

        if (dateDe != null && dateA != null) {
            queryRecherche += " AND date BETWEEN :dateDe AND :dateA ";

        } else {

            if (dateDe != null) {
                queryRecherche += " AND date=:dateDe ";
            }

            if (dateA != null) {
                queryRecherche += " AND date=:dateA ";
            }

        }

        Query query = session.createQuery("SELECT count(*) FROM Presence WHERE salarie.matricule=? " + queryRecherche + "");
        query.setParameter(0, matriculeSalarie, StandardBasicTypes.STRING);

        if (dateDe != null && dateA != null) {

            query.setParameter("dateDe", dateDe);
            query.setParameter("dateA", dateA);

        } else {

            if (dateDe != null) {
                query.setParameter("dateDe", dateDe);

            }

            if (dateA != null) {
                query.setParameter("dateA", dateA);

            }

        }

        return (int) (long) query.uniqueResult();
    }

    /**
     * Récupérer la liste des présences d'un salarié idSalarie, idChantier,
     * dateDe, dateA sont des paramètres de recherche des présences, ils peuvent
     * être des chaines vides start et limit sont des paramètres de pagination
     *
     * @param idSalarie l'identifiant du salarié dans la base de données
     * @param idChantier l'identifiant du chantier dans la base de données
     * @param dateDe date de début (pour récupérer les présences sur une
     * période) (format: yyyy/mm/jj)
     * @param dateA date de fin (pour récupérer les présences sur une période)
     * (format: yyyy/mm/jj)
     * @param start pour la pagination, c'est un nombre entier qui représente
     * l'index du premier élément à afficher
     * @param limit pour la pagination, c'est un nombre entier qui représente
     * l'index du dernier élément à afficher
     *
     * @return la liste des Présences
     */
    public List<Presence> listePresencesSalarie(int start, int limit, String matriculeSalarie, Integer idChantier, Date dateDe, Date dateA) {

        Session session = sessionFactory.getCurrentSession();

        String queryRecherche = "";

        if (idChantier != null) {
            queryRecherche += " AND chantier.id=" + idChantier + " ";
        }

        if (dateDe != null && dateA != null) {
            queryRecherche += " AND date BETWEEN :dateDe AND :dateA ";

        }

        Query query = session.createQuery("FROM Presence WHERE salarie.matricule=? " + queryRecherche + " ORDER BY date DESC");
        query.setParameter(0, matriculeSalarie, StandardBasicTypes.STRING);

        if (dateDe != null && dateA != null) {

            query.setParameter("dateDe", dateDe);
            query.setParameter("dateA", dateA);

        } else {

            if (dateDe != null) {
                query.setParameter("dateDe", dateDe);

            }

            if (dateA != null) {
                query.setParameter("dateA", dateA);

            }

        }
        if(limit != 0){
        query.setFirstResult(start);
        query.setMaxResults(limit);
        }

        List<Presence> listePresencesSalarie = query.list();
        return listePresencesSalarie;

    }

    /**
     * Calcul le nombre des heures et minutes des présences de la journée en
     * soustractant une heure de déjeuner
     *
     * @param heureEntree l'heure d'entrée (exemple: 8:30).
     * @param heureSortie l'heure de sortie (exemple: 17:30)
     *
     * @return le nombre des heures de présence sous forme de chaine de
     * caractères
     */
    public String nombreHeuresMinutesPresence(String heureEntree, String heureSortie) {

        String nombreHeuresMinutes = "";

        int hE = Integer.parseInt(heureEntree.substring(0, 2));
        int minE = Integer.parseInt(heureEntree.substring(3, 5));
        int hS = Integer.parseInt(heureSortie.substring(0, 2));
        int minS = Integer.parseInt(heureSortie.substring(3, 5));

        int hmE = Integer.parseInt(heureEntree.replaceAll(":", "").trim());
        int hmS = Integer.parseInt(heureSortie.replaceAll(":", "").trim());

        //Pas besoin de cette vérification ça se fait avant (via la requête de la methode : verifierValiditeHeureMinutePointageSalarie) d'appeler cette methode			
        if (hmE >= hmS) {
            return "";
        }

        /**
         * *********** Nombre des heures et minutes en cas de pointage normal
         * (hE<=hS) **********
         */
        int heures = hS - hE;
        int minutes = minS - minE;

        if (minutes < 0) {
            heures -= 1;
            minutes += 60;
        }

        /**
         * ********** Condition de la soustraction d'heure de déjeuner
         * ************************
         */
        if (hmE <= 1200 && hmS > 1400) {
            heures -= 1;
        }

        nombreHeuresMinutes = heures + ":" + minutes;

        return nombreHeuresMinutes;
    }

    //Calcul de nombre des heures pour une période
    //	public String nombreHeuresPeriodePresences(List<Presence> listePresences){
    //		
    //		String totalHeuresMinutes = "0H 0Min";
    //		
    //		int nombreHeures = 0;
    //		int nombreMinutes = 0;
    //		
    //		for(int i=0; i<listePresences.size(); i++){
    //		
    //			if(listePresences.get(i).getNombreHeures() != null && listePresences.get(i).getNombreMinutes() != null){
    //				
    //				nombreHeures += listePresences.get(i).getNombreHeures();
    //				nombreMinutes += listePresences.get(i).getNombreMinutes();
    //				
    //			}else{
    //				
    //				
    //				System.out.println("nuuuuuuuuuuul");
    //			}
    //			
    //			
    //		}
    //		
    //		if(nombreHeures == 0 && nombreMinutes == 0)
    //			return totalHeuresMinutes;
    //		
    //		nombreHeures += nombreMinutes / 60;
    //		nombreMinutes = nombreMinutes % 60;
    //	
    //		totalHeuresMinutes = nombreHeures + "H " + nombreMinutes + "Min";
    //		
    //		return totalHeuresMinutes;
    //
    //		
    //	}
    //	
    /**
     * **************************** FIN DE LA RECHERCHE DES PRESENCES D'UN
     * SALARIÉ ***********************************
     */
    /**
     * ***************************************************************************************************************
     */
    /**
     * ***************************************************************************************************************
     */
    /**
     * **************************** DEBUT DE LA GENERATION EXCEL DES PRESENCES
     * *********************************
     */
    //
    //	public List<Object> listeObjetsGenerationExcelPresences(String ville, String codeChantier, Date dateDe, Date dateA){
    //		
    //		Session session=sessionFactory.getCurrentSession();
    //
    //		  String queryRecherche="";
    //	        
    //		  if(!ville.equals("")){
    //		    	queryRecherche += " AND chantier.ville=" + ville + " ";
    //		    }
    //		  
    //		  if(!codeChantier.equals("")){
    //		    	queryRecherche += " AND chantier.code=" + codeChantier + " ";
    //		    }
    //		    
    //		  if(dateDe != null && dateA != null){
    //		    	queryRecherche += " AND date BETWEEN :dateDe AND :dateA ";
    //		    	
    //		    }else{
    //		    	 
    //		    	if(dateDe != null){
    //		    		queryRecherche += " AND date=:dateDe ";
    //		    	}
    //		    	
    //		        if(dateA != null){
    //		        	queryRecherche += " AND date=:dateA ";
    //		    	}
    //		    	
    //		    }
    //
    //		  queryRecherche = queryRecherche.replaceFirst("AND", "WHERE");
    //       
    //		    Query query=session.createQuery("SELECT p.salarie.matricule, sum(p.nombreHeures) + sum(p.nombreMinutes)/60, sum(p.nombreMinutes) FROM Presence p" + queryRecherche);
    //                  
    //                if(dateDe != null && dateA != null){
    //      	        	
    //      	        	query.setParameter("dateDe", dateDe);
    //      	        	query.setParameter("dateA", dateA);
    //      	        	
    //      	        }
    //		    
    //	        List<Object> listeNombresHeuresMinutesSalaries = query.list();
    //	        
    //		
    //		return listeNombresHeuresMinutesSalaries;
    //		
    //	}
    /**
     * Créer une requete SQL pour la génération du document Excel de présences
     * des Salariés
     *
     * @param ville la ville du chantier
     * @param codeChantier code du chantier
     * @param dateDe date de début (format: yyyy/mm/jj)
     * @param dateA date de fin (format: yyyy/mm/jj)
     *
     * @return requête sous forme d'une chaine de caractères
     */
    public String requeteSQLGenerationExcelPresencesSalaries(String ville, String codeChantier, String dateDe, String dateA) {

        String requeteSQL = "";
        String criteresRequeteSQL = "";

        if (!ville.equals("")) {

            ville = StringEscapeUtils.escapeSql(ville);
            //criteresRequeteSQL += " and C.VILLE = '" + ville + "' ";
        }

        if (!codeChantier.equals("")) {

            codeChantier = StringEscapeUtils.escapeSql(codeChantier);
            criteresRequeteSQL += " and C.LIB80 = '" + codeChantier + "' ";
        }

        if (ville != "" || codeChantier != "") {

            //			  select S.matricule as matricule , S.nom as nom, S.prenom as prenom, S.cin as cin, F.fonction as fonction, 
            //			  sum(P.NOMBREHEURES) + sum(P.NOMBREMINUTES)/60 as nombreHeures from SALARIE S, FONCTION F, PRESENCE P, 
            //			  CHANTIER CH where S.ID = P.salarie_ID and S.fonction_PPTEMP_ID = F.ID and CH.ID = P.chantier_PRJAP_ID and 
            //			  P.date between '2012/10/01' and '2012/10/29' group by P.salarie_ID order by F.FONCTION, S.NOM, S.PRENOM
            //			requeteSQL = "select S.MATRICULE as matricule , S.NOM as nom, S.PRENOM as prenom, S.CNSS as cnss, S.CIN as cin, F.CODENOVAPAIE as code, sum(P.NOMBREHEURES) + sum(P.NOMBREMINUTES)/60 + ISNULL((sum(P.NOMBREMINUTES)%60)/(sum(P.NOMBREMINUTES)%60),0) as totalHeures from SALARIE S, " +
            //			"FONCTION F, PRESENCE P, CHANTIER CH where S.ID = P.salarie_ID and S.type_ID = 1 and S.fonction_PPTEMP_ID = F.ID and CH.ID = P.chantier_PRJAP_ID and P.DATE between CAST('" + dateDe +"' AS DATETIME) "
            //			+ "and CAST('" + dateA +"' AS DATETIME) " + criteresRequeteSQL + " group by P.salarie_ID, F.CODENOVAPAIE, S.NOM, S.PRENOM, S.MATRICULE, S.CIN, S.CNSS,P.NOMBREHEURES,P.NOMBREMINUTES HAVING sum(P.NOMBREHEURES) + sum(P.NOMBREMINUTES)/60 + ISNULL((sum(P.NOMBREMINUTES)%60)/(sum(P.NOMBREMINUTES)%60),0) > 0";
            requeteSQL = "select S.MATRICULE as matricule , S.NOM as nom, S.PRENOM as prenom, S.CNSS as cnss, S.CIN as cin, F.EMPLOICOD as code,F.LIB as fonction,C.AFFAIRE as chantier, CAST(sum(P.NOMBREHEURES) + sum(P.NOMBREMINUTES)/60 AS VARCHAR(10)) as totalHeures, CAST (sum(P.NOMBREMINUTES)%60 AS VARCHAR(10)) as totalMinutes "
                    + " from SALARIE S, PPTEMP F, PRESENCE P, PRJAP C where S.Dtype='Salarie' AND F.PPTEMP_ID=S.fonction_PPTEMP_ID and S.ID = P.salarie_ID and P.chantier_PRJAP_ID=C.PRJAP_ID and S.type_ID = 1 and P.DATE between CAST('" + dateDe + "' AS DATETIME) "
                    + "and CAST('" + dateA + "' AS DATETIME) " + criteresRequeteSQL + "  group by S.MATRICULE, F.EMPLOICOD,F.LIB, S.NOM, S.PRENOM,S.CIN, S.CNSS,C.AFFAIRE HAVING sum(P.NOMBREHEURES) + sum(P.NOMBREMINUTES)/60 > 0";

        } else {

            requeteSQL = "select S.MATRICULE as matricule , S.NOM as nom, S.PRENOM as prenom, S.CNSS as cnss, S.CIN as cin, F.EMPLOICOD as code,F.LIB as fonction,C.AFFAIRE as chantier, CAST(sum(P.NOMBREHEURES) + sum(P.NOMBREMINUTES)/60 AS VARCHAR(10)) as totalHeures, CAST (sum(P.NOMBREMINUTES)%60 AS VARCHAR(10)) as totalMinutes "
                    + "from SALARIE S, PPTEMP F, PRESENCE P, PRJAP C where S.Dtype='Salarie' AND F.PPTEMP_ID=S.fonction_PPTEMP_ID and S.ID = P.salarie_ID and P.chantier_PRJAP_ID=C.PRJAP_ID and S.type_ID = 1 and P.DATE between CAST('" + dateDe + "' AS DATETIME) "
                    + "and CAST('" + dateA + "' AS DATETIME) group by S.MATRICULE, F.EMPLOICOD,F.LIB, S.NOM, S.PRENOM,S.CIN, S.CNSS,C.AFFAIRE HAVING sum(P.NOMBREHEURES) + sum(P.NOMBREMINUTES)/60 > 0";

        }

        return requeteSQL;

    }

    // -----
    /**
     * Créer une requete SQL pour la génération du document Excel de présences
     *
     * @param dateDe date de début (format: yyyy/mm/jj)
     * @param dateA date de fin (format: yyyy/mm/jj)
     *
     * @return requête sous forme d'une chaine de caractères
     */
    public String requeteSQLPresences(String rubrique, String dateDe, String dateA) {

        String requeteSQL = "";
//        String dateDeDiva = dateDe.replaceAll("-", "");
//        dateDeDiva = dateDeDiva.substring(4, 6) + "/" + dateDeDiva.substring(6, 8) + "/" + dateDeDiva.substring(0, 4);
//        String dateADiva = dateA.replaceAll("-", "");
//        dateADiva = dateADiva.substring(4, 6) + "/" + dateADiva.substring(6, 8) + "/" + dateADiva.substring(0, 4);

        requeteSQL = "select 'T' as ce2, '700' as dossier,'1' as etablissement, P.salarie_ID as individu,'' as cordre,'" + rubrique + "' as rubrique,C.AFFAIRE as chantier, '" + dateDe + "' as datedebut, '" + dateA + "' as datefin, CAST(sum(P.NOMBREHEURES) + sum(P.NOMBREMINUTES)/60 AS VARCHAR(10)) as montantsalarial from "
                + " PRESENCE P, PRJAP C where P.chantier_PRJAP_ID=C.PRJAP_ID and P.DATE between CAST('" + dateDe + "' AS DATETIME) "
                + "and CAST('" + dateA + "' AS DATETIME) group by P.salarie_ID, C.AFFAIRE";

        System.out.println("requete sql: " + requeteSQL);

        return requeteSQL;

    }

    //----
    /**
     * **************************** FIN DE LA GENERATION EXCEL DES PRESENCES
     * ***********************************
     */
    /**
     * ***************************************************************************************************************
     */
    /**
     * Vérifier si le salarié passé en paramètres est pointé en entrée ou en
     * sortie
     *
     * @param idSalarie l'identifiant du salarié dans la base de données
     * @param datePointage date de pointage
     * @return true si le salarié est pointé sinon false
     */
    public boolean dejaPointeSortie(Integer idSalarie, Long longDateTimePointage, Date datePointage, String type) {

        logger.debug("Vérifier si le salarié est déjà pointé en sortie");

        Session session = sessionFactory.getCurrentSession();
//        try {
//
//            session.connection().setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
//
//        } catch (HibernateException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (SQLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
        Query query = null;
        if (type == "E") {
            query = session.createQuery("SELECT count(*) FROM Presence WHERE salarie.id=? AND date=? AND ? >= longDateTimeEntree");

            query.setParameter(0, idSalarie, StandardBasicTypes.INTEGER);
            query.setParameter(1, datePointage, StandardBasicTypes.DATE);
            query.setParameter(2, longDateTimePointage + 1, StandardBasicTypes.LONG);
        } else {
            query = session.createQuery("SELECT count(*) FROM Presence WHERE salarie.id=? AND date=? AND (heureSortie is null)");

            query.setParameter(0, idSalarie, StandardBasicTypes.INTEGER);
            query.setParameter(1, datePointage, StandardBasicTypes.DATE);
        }

        Long nombre = (Long) query.uniqueResult();
        int n = (int) (long) nombre;
        System.out.println("LondDateTimePointage :" + longDateTimePointage);
        System.out.println("controle contrat: " + n);

        if (n != 0) {
            return true;
        }

        return false;

    }

    /*
     * Appelée par le web service, pour récupérer les présences des quinzainiers
     * 
     */
    // -----
    /**
     * Appelée par le web service, pour récupérer les présences des quinzainiers
     *
     * @param dateDe date de début (format: yyyy/mm/jj)
     * @param dateA date de fin (format: yyyy/mm/jj)
     *
     * @return requête sous forme d'une chaine de caractères
     */
    public String PresencesQuinzainiers(String flag, String matricule, String chantier, String rubrique, String dateDe, String dateA) {

        SessionFactory sf = new AnnotationConfiguration().configure("hibernate-context.xml").buildSessionFactory();

        if (dateDe == "" || dateA == "" || dateDe == null || dateA == null || flag == "" || flag == null) {

            return "Erreur, verifier les paramètres: flag, date debut et date de fin sont obligatoires";
        }
        int f = Integer.parseInt(flag);
        String requeteSQL = "";
        String dateDeDiva = "";
        dateDeDiva = dateDe.substring(6, 8) + "/" + dateDe.substring(4, 6) + "/" + dateDe.substring(0, 4);
        String dateADiva = "";
        dateADiva = dateA.substring(6, 8) + "/" + dateA.substring(4, 6) + "/" + dateA.substring(0, 4);

        String dateDeSQL = "";
        dateDeSQL = dateDe.substring(0, 4) + "/" + dateDe.substring(6, 8) + "/" + dateDe.substring(4, 6);
        String dateASQL = "";
        dateASQL = dateA.substring(0, 4) + "/" + dateA.substring(6, 8) + "/" + dateA.substring(4, 6);

        String requeteRecherche = "";

        if (matricule != "" && matricule != null) {

            requeteRecherche += " AND S.matricule='" + matricule + "' ";
        }
        if (chantier != "" && chantier != null) {

            requeteRecherche += " AND C.AFFAIRE='" + chantier + "' ";
        }

        requeteSQL = "SELECT 'T' as ce2, '700' as dossier,'1' as etablissement, S.matricule as individu,'' as cordre,'" + rubrique + "' as rubrique,C.AFFAIRE as chantier, '" + dateDeDiva + "' as datedebut, '" + dateADiva + "' as datefin, sum(P.NOMBREHEURES) + sum(P.NOMBREMINUTES)/60 as montantsalarial "
                + "FROM PRESENCE P, PRJAP C , Salarie S "
                + "WHERE P.chantier_PRJAP_ID=C.PRJAP_ID and P.salarie_ID=S.ID AND P.FLAG=" + f + " " + requeteRecherche + " and P.DATE between CAST('" + dateDeSQL + "' AS DATETIME) "
                + "and CAST('" + dateASQL + "' AS DATETIME) AND (P.HEURESORTIE IS NOT NULL)  GROUP BY S.matricule, C.AFFAIRE";

        System.out.println("requete sql: " + requeteSQL);

        Query query = sf.openSession().createSQLQuery(requeteSQL);
//			Query query=session.createSQLQuery(requeteSQL);
        List liste = query.list();
        String reponse = "CE2;DOSSIER;ETABLISSEMENT,INDIVIDU;CORDRE;RUBRIQUE;CHANTIER;DateDebut;DateFin;Montant_Salarial" + "\n";

        try {
            String name = "";
            if (flag == "1") {
                name = "heures_Supp";
            } else {
                name = "heures_normales";
            }
            FileWriter writer = new FileWriter("/tmp/" + name + "_" + dateDe + "_" + dateA + ".csv");

            writer.append("CE2");
            writer.append(';');
            writer.append("DOSSIER");
            writer.append(';');
            writer.append("ETABLISSEMENT");
            writer.append(';');
            writer.append("INDIVIDU");
            writer.append(';');
            writer.append("CORDRE");
            writer.append(';');
            writer.append("RUBRIQUE");
            writer.append(';');
            writer.append("CHANTIER");
            writer.append(';');
            writer.append("DateDebut");
            writer.append(';');
            writer.append("DateFin");
            writer.append(';');
            writer.append("Montant_Salarial");
            writer.append('\n');

            for (int i = 0; i < liste.size(); i++) {
                Object[] o = (Object[]) liste.get(i);
                System.out.println("chantier: " + o[6]);
                reponse = reponse + String.valueOf(o[0]) + ";" + String.valueOf(o[1]) + ";" + String.valueOf(o[2]) + ";" + String.valueOf(o[3]) + ";" + String.valueOf(o[4]) + ";" + String.valueOf(o[5]) + ";" + String.valueOf(o[6]) + ";" + (String) o[7] + ";" + String.valueOf(o[8]) + ";" + String.valueOf(o[9]) + "\n";

                writer.append(String.valueOf(o[0]));
                writer.append(';');
                writer.append(String.valueOf(o[1]));
                writer.append(';');
                writer.append(String.valueOf(o[2]));
                writer.append(';');
                writer.append(String.valueOf(o[3]));
                writer.append(';');
                writer.append(String.valueOf(o[4]));
                writer.append(';');
                writer.append(String.valueOf(o[5]));
                writer.append(';');
                writer.append(String.valueOf(o[6]));
                writer.append(';');
                writer.append((String) o[7]);
                writer.append(';');
                writer.append(String.valueOf(o[8]));
                writer.append(';');
                writer.append(String.valueOf(o[9]));
                writer.append(';');
                writer.append('\n');

            }
            System.out.println("reponse: " + reponse);

            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return reponse;

    }

    /**
     * Appelée par le web service, pour récupérer les présences des quinzainiers
     *
     * @param dateDe date de début (format: yyyy/mm/jj)
     * @param dateA date de fin (format: yyyy/mm/jj)
     *
     * @return requête sous forme d'une chaine de caractères
     */
    public String PresencesQuinzainiers(String parametres) {

        SessionFactory sf = new AnnotationConfiguration().configure("hibernate-context.xml").buildSessionFactory();
        //parametres="flag:valeur1;matricule:valeur1;chantier:valeur1;rubrique:valeur1;dateDe:valeur1;dateA:valeur1";
        String[] params = parametres.trim().split(";");
        String flag = "";
        String matricule = "";
        String chantier = "";
        String rubrique = "";
        String dateDe = "";
        String dateA = "";

        try {
            if (params[0].split(":").length == 2) {
                flag = params[0].split(":")[1];
            }
            if (params[1].split(":").length == 2) {
                matricule = params[1].split(":")[1];
            }
            if (params[2].split(":").length == 2) {
                chantier = params[2].split(":")[1];
            }
            if (params[3].split(":").length == 2) {
                rubrique = params[3].split(":")[1];
            }
            if (params[4].split(":").length == 2) {
                dateDe = params[4].split(":")[1];
            }
            if (params[5].split(":").length == 2) {
                dateA = params[5].split(":")[1];
            }
        } catch (Exception e) {

            return "Erreur de paramètres, vérifiez le format: \n'flag:valeur1;matricule:valeur2;chantier:valeur3;rubrique:valeur4;dateDe:valeur5;dateA:valeur6'";
        }

        if (dateDe == "" || dateA == "" || dateDe == null || dateA == null || flag == "" || flag == null) {

            return "Erreur de paramètres: flag, date debut et date de fin sont obligatoires";
        }
        int f = Integer.parseInt(flag);
        String requeteSQL = "";
        String dateDeDiva = "";
        dateDeDiva = dateDe.substring(6, 8) + "/" + dateDe.substring(4, 6) + "/" + dateDe.substring(0, 4);
        String dateADiva = "";
        dateADiva = dateA.substring(6, 8) + "/" + dateA.substring(4, 6) + "/" + dateA.substring(0, 4);

        String dateDeSQL = "";
        dateDeSQL = dateDe.substring(0, 4) + "/" + dateDe.substring(6, 8) + "/" + dateDe.substring(4, 6);
        String dateASQL = "";
        dateASQL = dateA.substring(0, 4) + "/" + dateA.substring(6, 8) + "/" + dateA.substring(4, 6);

        String requeteRecherche = "";

        if (matricule != "" && matricule != null) {

            requeteRecherche += " AND S.matricule='" + matricule + "' ";
        }
        if (chantier != "" && chantier != null) {

            requeteRecherche += " AND C.AFFAIRE='" + chantier + "' ";
        }

        requeteSQL = "SELECT 'T' as ce2, '700' as dossier,'1' as etablissement, S.matricule as individu,'' as cordre,'" + rubrique + "' as rubrique,CAST(C.AFFAIRE as varchar(255)) as chantier, '" + dateDeDiva + "' as datedebut, '" + dateADiva + "' as datefin, sum(P.NOMBREHEURES) + sum(P.NOMBREMINUTES)/60 as montantsalarial "
                + "FROM PRESENCE P, PRJAP C , Salarie S "
                + "WHERE P.chantier_PRJAP_ID=C.PRJAP_ID and P.salarie_ID=S.ID AND P.FLAG=" + f + " " + requeteRecherche + " and P.DATE between CAST('" + dateDeSQL + "' AS DATETIME) "
                + "and CAST('" + dateASQL + "' AS DATETIME) AND (P.HEURESORTIE IS NOT NULL)  GROUP BY S.matricule, C.AFFAIRE";

        System.out.println("requete sql: " + requeteSQL);

        Query query = sf.openSession().createSQLQuery(requeteSQL);
//			Query query=session.createSQLQuery(requeteSQL);
        List liste = query.list();
        String reponse = "CE2;DOSSIER;ETABLISSEMENT;INDIVIDU;CORDRE;RUBRIQUE;CHANTIER;DateDebut;DateFin;Montant_Salarial" + "\n";

        try {
            String name = "";
            if (flag == "1") {
                name = "heures_Supp";
            } else {
                name = "heures_normales";
            }
            FileWriter writer = new FileWriter(Constantes.getInstance().cheminPresences + "/" + name + "_" + new Date() + ".csv");

            writer.append("CE2");
            writer.append(';');
            writer.append("DOSSIER");
            writer.append(';');
            writer.append("ETABLISSEMENT");
            writer.append(';');
            writer.append("INDIVIDU");
            writer.append(';');
            writer.append("CORDRE");
            writer.append(';');
            writer.append("RUBRIQUE");
            writer.append(';');
            writer.append("CHANTIER");
            writer.append(';');
            writer.append("DateDebut");
            writer.append(';');
            writer.append("DateFin");
            writer.append(';');
            writer.append("Montant_Salarial");
            writer.append('\n');

            for (int i = 0; i < liste.size(); i++) {
                Object[] o = (Object[]) liste.get(i);
                System.out.println("chantier: " + o[6]);
                reponse = reponse + String.valueOf(o[0]) + ";" + String.valueOf(o[1]) + ";" + String.valueOf(o[2]) + ";" + String.valueOf(o[3]) + ";" + String.valueOf(o[4]) + ";" + String.valueOf(o[5]) + ";" + String.valueOf(o[6]) + ";" + (String) o[7] + ";" + String.valueOf(o[8]) + ";" + String.valueOf(o[9]) + "\n";

                writer.append(String.valueOf(o[0]));
                writer.append(';');
                writer.append(String.valueOf(o[1]));
                writer.append(';');
                writer.append(String.valueOf(o[2]));
                writer.append(';');
                writer.append(String.valueOf(o[3]));
                writer.append(';');
                writer.append(String.valueOf(o[4]));
                writer.append(';');
                writer.append(String.valueOf(o[5]));
                writer.append(';');
                writer.append(String.valueOf(o[6]));
                writer.append(';');
                writer.append((String) o[7]);
                writer.append(';');
                writer.append(String.valueOf(o[8]));
                writer.append(';');
                writer.append(String.valueOf(o[9]));
                writer.append(';');
                writer.append('\n');

            }
            System.out.println("reponse: " + reponse);

            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return reponse;

    }

    /**
     * Vérifier si le salarié passé en paramètre a au moins un pointage dans la
     * date passée en paramètre
     *
     * @param idSalarie l'identifiant du salarié dans la base de données
     * @param datePointage date de pointage
     * @return true si le salarié est pointé sinon false
     */
    public boolean dejaPointe(Integer idSalarie, Date datePointage) {

        logger.debug("Vérifier si le salarié est déjà pointé en sortie");
        Session session = sessionFactory.getCurrentSession();

        Query query = null;
        query = session.createQuery("SELECT count(*) FROM Presence WHERE salarie.id=? AND date=? AND heureEntree is not null AND heureSortie is not null ");
        query.setParameter(0, idSalarie, StandardBasicTypes.INTEGER);
        query.setParameter(1, datePointage, StandardBasicTypes.DATE);
        Long nombre = (Long) query.uniqueResult();
        int n = (int) (long) nombre;
        if (n != 0) {
            return true;
        }

        return false;

    }

    /**
     * Récupérer la liste de présences (pointages) à partir d'un fichier excel
     *
     * @param document le document Excel de type MultipartFile
     * @return liste des présences récupérer à partir du ficher excel
     */
    public Map<String, String[]> recupererPresencesFichier(InputStream bis, String extensionFichier, int mois, int annee, boolean desaffectationChantiers,
            boolean verificationContrat,
            boolean ecraserPointagesExistants,
            boolean avecReglage,
            Integer chantierId) {

        logger.debug("insérer les présences à partir d'une liste");

//        Ticket ticket = ((CmaUsernamePasswordAuthenticationToken) SecurityContextHolder
//                .getContext().getAuthentication()).getTicket();
        Map<String, String[]> data = new HashMap<String, String[]>();
        Presence presence = null;
        try {
//            String originalFileName = document.getOriginalFilename();
//            int indexLastPoint = originalFileName.lastIndexOf(".");
//            String extensionFichier = originalFileName.substring(indexLastPoint);
            int key = 0;
//            byte[] bytes;
//            bytes = document.getBytes();
//            InputStream bis;
//            bis = new ByteArrayInputStream(bytes);
            Iterator<Row> rowIterator = null;

            if (extensionFichier.equals("xlsx")) {

                XSSFWorkbook workbook = new XSSFWorkbook(bis);
                XSSFSheet sheet = workbook.getSheetAt(0);
                rowIterator = sheet.iterator();

                //				XSSFFont font = workbook.createFont();
                //				font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
                //				XSSFCellStyle style = workbook.createCellStyle();
                //				style.setFont(font);
                while (rowIterator.hasNext()) {
                    Row row = rowIterator.next();
                    //				     if (row.getCell(0).getCellType()==Cell.CELL_TYPE_STRING && row.getCell(0).getStringCellValue().equals("MATRICULE")){ 
                    //				    	  System.out.println("colone: "+row.getCell(0).getStringCellValue());
                    //				    	  System.out.println("row index:"+row.getRowNum());
                    //				     }

                    if (row.getCell(0) != null) {
                        if (row.getRowNum() != 0 && row.getCell(0).getCellType() == Cell.CELL_TYPE_STRING && !StringUtils.isNumeric(row.getCell(0).getStringCellValue())) {
                            //matriculesProb.add(row.getCell(0).getStringCellValue());
                            System.out.println("row index:" + row.getRowNum());
                            String[] dataProb = new String[]{row.getCell(0).getStringCellValue(), "Matricule inexistant", ""};
                            key++;
                            data.put(String.valueOf(key), dataProb);
                        } else if (row.getRowNum() != 0 && row.getCell(0).getCellType() == Cell.CELL_TYPE_STRING && StringUtils.isNumeric(row.getCell(0).getStringCellValue()) && !salarieService.isExist(Integer.valueOf(row.getCell(0).getStringCellValue()))) {
                            //matriculesProb.add(String.valueOf(row.getCell(0).getStringCellValue()));
                            String[] dataProb = new String[]{String.valueOf(row.getCell(0).getStringCellValue()), "Matricule inexistant", ""};
                            key++;
                            data.put(String.valueOf(key), dataProb);
                        } else if (row.getRowNum() != 0 && row.getCell(0).getCellType() == Cell.CELL_TYPE_NUMERIC && !salarieService.isExist((int) row.getCell(0).getNumericCellValue())) {
                            //matriculesProb.add(String.valueOf((int)row.getCell(0).getNumericCellValue()));
                            String[] dataProb = new String[]{String.valueOf((int) row.getCell(0).getNumericCellValue()), "Matricule inexistant", ""};
                            key++;
                            data.put(String.valueOf(key), dataProb);
                        } else if (row.getRowNum() != 0 && ((row.getCell(0).getCellType() == Cell.CELL_TYPE_NUMERIC && salarieService.isExist((int) row.getCell(0).getNumericCellValue()))
                                || (row.getCell(0).getCellType() == Cell.CELL_TYPE_STRING && StringUtils.isNumeric(row.getCell(0).getStringCellValue()) && salarieService.isExist(Integer.valueOf(row.getCell(0).getStringCellValue()))))) {

                            if (row.getCell(0).getCellType() == Cell.CELL_TYPE_NUMERIC) {
                                //salariesIDs.add((int)row.getCell(0).getNumericCellValue()); 
                                System.out.println("matricule: " + (int) row.getCell(0).getNumericCellValue());
                                if (desaffectationChantiers) {
                                    chantierService.desaffecterSalarieTousChantiers((int) row.getCell(0).getNumericCellValue());
                                    Chantier chantier = new Chantier();
                                    Salarie salarie = new Salarie();
                                    salarie.setId((int) row.getCell(0).getNumericCellValue());
                                    if (chantierId != null) {
                                        chantier.setId(chantierId);
                                        chantierService.affecterSalarieChantier(salarie, chantier); // affecter le salarié au chantier
                                    } else {
                                        Integer idChantier = chantierService.getChantierByAffaire(row.getCell(3).getStringCellValue());
                                        if (idChantier != null) {
                                            chantier.setId(idChantier);
                                            chantierService.affecterSalarieChantier(salarie, chantier); // affecter le salarié au chantier
                                        }
                                    }
                                }

                                /*
                                 * code regalge deplacé
                                 * 
                                 */
                            } else if (row.getCell(0).getCellType() == Cell.CELL_TYPE_STRING) {
                                //salariesIDs.add(Integer.valueOf(row.getCell(0).getStringCellValue())); 
                                System.out.println("matricule: " + row.getCell(0).getStringCellValue());
                                if (desaffectationChantiers) {
                                    chantierService.desaffecterSalarieTousChantiers(Integer.valueOf(row.getCell(0).getStringCellValue()));
                                    Chantier chantier = new Chantier();
                                    Salarie salarie = new Salarie();
                                    salarie.setId(Integer.valueOf(row.getCell(0).getStringCellValue()));
                                    if (chantierId != null) {
                                        chantier.setId(chantierId);
                                        chantierService.affecterSalarieChantier(salarie, chantier); // affecter le salarié au chantier
                                    } else {
                                        Integer idChantier = chantierService.getChantierByAffaire(row.getCell(3).getStringCellValue());
                                        if (idChantier != null) {
                                            chantier.setId(idChantier);
                                            chantierService.affecterSalarieChantier(salarie, chantier); // affecter le salarié au chantier
                                        }
                                    }
                                }
                                if (avecReglage == true && (row.getCell(4, Row.RETURN_NULL_AND_BLANK) != null) || (row.getCell(4, Row.RETURN_BLANK_AS_NULL) != null)) {
                                    Date dtSortie = null;
                                    if (row.getCell(4).getCellType() == Cell.CELL_TYPE_NUMERIC && DateUtil.isCellDateFormatted(row.getCell(4))) {
                                        dtSortie = row.getCell(4).getDateCellValue();
                                    } else if (row.getCell(4).getCellType() == Cell.CELL_TYPE_STRING) {
                                        dtSortie = new Date(row.getCell(4).getStringCellValue().substring(6, 10) + "/" + row.getCell(4).getStringCellValue().substring(3, 5) + "/" + row.getCell(4).getStringCellValue().substring(0, 2));

                                    }

                                    if (contratService.nombreContratParEtat(Integer.valueOf(row.getCell(0).getStringCellValue()), 2) == 1) {
                                        Contrat contrat = null;
                                        contrat = contratService.dernierContratParEtat(Integer.valueOf((row.getCell(0).getStringCellValue())), 2);
                                        //Date dateFin =new Date(row.getCell(4).getStringCellValue().substring(6, 10)+"/"+row.getCell(4).getStringCellValue().substring(3, 5)+"/"+row.getCell(4).getStringCellValue().substring(0, 2));
                                        Date dateFin = dtSortie;
                                        if (dateFin.after(contrat.getDateEmbauche())) {
                                            String dateDebutContrat = contrat.getDateEmbauche().toString().substring(0, 10).replaceAll("-", "");
                                            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                            String dateFinContrat = dateFormat.format(dateFin).replaceAll("-", "");
                                            //String dateFinContrat=dateFin.toString().substring(0,10).replaceAll("-", "");
                                            System.out.println("Date de sortie: " + dateFinContrat);
                                            String motif = "";
                                            Integer idMotif = null;
                                            if ((row.getCell(36, Row.RETURN_NULL_AND_BLANK) != null) && (row.getCell(36, Row.RETURN_BLANK_AS_NULL) != null)) {
                                                motif = row.getCell(36).getStringCellValue().trim().toLowerCase();
                                                idMotif = parametrageService.getMotifByCode(motif).getId();
                                            } else {
                                                motif = "fcdd";
                                            }
                                            String refContrat = salarieService.modifierContratSalarieWS(String.valueOf(row.getCell(0).getStringCellValue()), dateDebutContrat,
                                                    dateFinContrat, motif, dateFinContrat).get("referenceContratDiva");
                                            if (!refContrat.equals("0") && !refContrat.equals("-1")) {
                                                contratService.reglerContrat(contrat.getId(), dateFin, idMotif);
                                                salarieService.changerEtatSalarie(Integer.valueOf((row.getCell(0).getStringCellValue())), 3);
                                            } else {
                                                System.out.println("Problème de réglage sur Divalto");
                                                String[] dataProb = new String[]{String.valueOf(row.getCell(0).getStringCellValue()), "Non réglé: Problème de réglage sur Divalto", ""};
                                                key++;
                                                data.put(String.valueOf(key), dataProb);
                                            }
                                        } else {
                                            System.out.println("Problème de réglage: date Fin inferieure a date Embauche");
                                            String[] dataProb = new String[]{String.valueOf((int) row.getCell(0).getNumericCellValue()), "Non réglé: Date Fin infénieure à Date Embauche", ""};
                                            key++;
                                            data.put(String.valueOf(key), dataProb);
                                        }
                                    } else if (contratService.nombreContratParEtat(Integer.valueOf(row.getCell(0).getStringCellValue()), 2) == 0) {
                                        System.out.println("matricule n\'a pas de contrat");
                                        String[] dataProb = new String[]{String.valueOf(row.getCell(0).getStringCellValue()), "Non réglé: matricule n\'a pas de contrat", ""};
                                        key++;
                                        data.put(String.valueOf(key), dataProb);
                                    } else if (contratService.nombreContratParEtat(Integer.valueOf(row.getCell(0).getStringCellValue()), 2) > 1) {
                                        System.out.println("matricule a plusieurs contrats légalisés");
                                        String[] dataProb = new String[]{String.valueOf(row.getCell(0).getStringCellValue()), "Non réglé: matricule a plusieurs contrats légalisés", ""};
                                        key++;
                                        data.put(String.valueOf(key), dataProb);
                                    }
                                }
                            }

                            Iterator<Cell> cellIterator = row.cellIterator();
                            while (cellIterator.hasNext()) {
                                Cell cell = cellIterator.next();
                                if (row.getCell(0).getCellType() == Cell.CELL_TYPE_STRING && row.getCell(0).getStringCellValue().equals("MATRICULE")) {
                                    System.out.println("colone: " + row.getCell(0).getStringCellValue());
                                } else if (cell.getColumnIndex() >= 5 && cell.getColumnIndex() <= 35) {

                                    if (cell != null) {

                                        if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC && cell.getNumericCellValue() > 0) {

                                            presence = new Presence();
                                            Salarie salarie = new Salarie();

                                            if (row.getCell(0).getCellType() == Cell.CELL_TYPE_NUMERIC) {
                                                salarie.setId((int) (row.getCell(0).getNumericCellValue()));
                                            } else if (row.getCell(0).getCellType() == Cell.CELL_TYPE_STRING) {
                                                salarie.setId(Integer.valueOf(row.getCell(0).getStringCellValue()));
                                            }

                                            presence.setSalarie(salarie);

                                            if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                                                presence.setNombreHeures((int) (cell.getNumericCellValue()));
                                            } else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                                                presence.setNombreHeures(Integer.valueOf(cell.getStringCellValue()));
                                            }

                                            Chantier chantier = null;
                                            if (chantierId != null) {
                                                chantier = new Chantier();
                                                chantier.setId(chantierId);
                                                presence.setChantier(chantier);
                                            } else {
                                                chantier = new Chantier();
                                                if (row.getCell(3).getCellType() == Cell.CELL_TYPE_STRING && !row.getCell(3).getStringCellValue().equals("")) {
                                                    Integer idChantier = chantierService.getChantierByAffaire(row.getCell(3).getStringCellValue());
                                                    if (idChantier != null) {
                                                        chantier.setId(idChantier);
                                                        presence.setChantier(chantier);
                                                    } else {
                                                        System.out.println("problème de chantier");
                                                        return null;
                                                    }
                                                } else {
                                                    System.out.println("problème de chantier");
                                                    return null;
                                                }
                                            }
                                            presence.setHeureEntree("07:30");

                                            if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                                                if (cell.getNumericCellValue() <= 5) {
                                                    if (cell.getNumericCellValue() < 3) {
                                                        presence.setHeureSortie(String.valueOf((7 + (cell.getNumericCellValue()))).substring(0, 1) + ":30");
                                                    } else {
                                                        presence.setHeureSortie(String.valueOf((7 + (cell.getNumericCellValue()))).substring(0, 2) + ":30");
                                                    }
                                                } else {
                                                    presence.setHeureSortie(String.valueOf((7 + (cell.getNumericCellValue()) + 1)).substring(0, 2) + ":30");
                                                }
                                            }
                                            if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                                                if (Integer.valueOf(cell.getStringCellValue()) <= 5) {
                                                    if (Integer.valueOf(cell.getStringCellValue()) < 3) {
                                                        presence.setHeureSortie(String.valueOf((7 + Integer.valueOf(cell.getStringCellValue()))).substring(0, 1) + ":30");
                                                    } else {
                                                        presence.setHeureSortie(String.valueOf((7 + Integer.valueOf(cell.getStringCellValue()))).substring(0, 2) + ":30");
                                                    }
                                                } else {
                                                    presence.setHeureSortie(String.valueOf((7 + Integer.valueOf(cell.getStringCellValue()) + 1)).substring(0, 2) + ":30");
                                                }
                                            }
                                            presence.setDate(new Date(annee + "/" + mois + "/" + (cell.getColumnIndex() - 4)));
                                            presence.setFlag(false);
                                            presence.setNombreMinutes(0);
                                            presence.setCreePar("");
                                            presence.setDateCreation(new Date());

                                            //---------------------------
                                            String chaineDateEntree = annee + "/" + mois + "/" + (cell.getColumnIndex() - 4);
                                            Long longDateTimeEntree = new Date(chaineDateEntree + " " + presence.getHeureEntree()).getTime();
                                            presence.setLongDateTimeEntree(longDateTimeEntree);

                                            String chaineDateSortie = annee + "/" + mois + "/" + (cell.getColumnIndex() - 4);
                                            Long longDateTimeSortie = new Date(chaineDateSortie + " " + presence.getHeureSortie()).getTime();
                                            presence.setLongDateTimeEntree(longDateTimeSortie);
                                            //--------------------------

                                            //listePresences.add(presence);
                                            String[] presProb = insererPointage(presence, desaffectationChantiers, verificationContrat, ecraserPointagesExistants, chantierId);
                                            if (presProb != null && presProb.length != 0) {
                                                key++;
                                                data.put(String.valueOf(key), presProb);
                                            }
                                            //presence.setDate(date)

                                        } else if (cell.getCellType() == Cell.CELL_TYPE_STRING && StringUtils.isNumeric(cell.getStringCellValue())
                                                && Integer.valueOf(cell.getStringCellValue()) > 0) {
                                            presence = new Presence();
                                            Salarie salarie = new Salarie();

                                            if (row.getCell(0).getCellType() == Cell.CELL_TYPE_NUMERIC) {
                                                salarie.setId((int) (row.getCell(0).getNumericCellValue()));
                                            } else if (row.getCell(0).getCellType() == Cell.CELL_TYPE_STRING) {
                                                salarie.setId(Integer.valueOf(row.getCell(0).getStringCellValue()));
                                            }

                                            presence.setSalarie(salarie);

                                            if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                                                presence.setNombreHeures((int) (cell.getNumericCellValue()));
                                            } else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                                                presence.setNombreHeures(Integer.valueOf(cell.getStringCellValue()));
                                            }

                                            Chantier chantier = null;
                                            if (chantierId != null) {
                                                chantier = new Chantier();
                                                chantier.setId(chantierId);
                                                presence.setChantier(chantier);
                                            } else {
                                                chantier = new Chantier();
                                                chantier.setId(chantierService.getChantierByAffaire(row.getCell(3).getStringCellValue()));
                                                presence.setChantier(chantier);
                                            }
                                            presence.setHeureEntree("07:30");

                                            if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                                                if (cell.getNumericCellValue() <= 5) {
                                                    if (cell.getNumericCellValue() < 3) {
                                                        presence.setHeureSortie(String.valueOf((7 + (cell.getNumericCellValue()))).substring(0, 1) + ":30");
                                                    } else {
                                                        presence.setHeureSortie(String.valueOf((7 + (cell.getNumericCellValue()))).substring(0, 2) + ":30");
                                                    }
                                                } else {
                                                    presence.setHeureSortie(String.valueOf((7 + (cell.getNumericCellValue()) + 1)).substring(0, 2) + ":30");
                                                }
                                            }
                                            if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                                                if (Integer.valueOf(cell.getStringCellValue()) <= 5) {
                                                    if (Integer.valueOf(cell.getStringCellValue()) < 3) {
                                                        presence.setHeureSortie(String.valueOf((7 + Integer.valueOf(cell.getStringCellValue()))).substring(0, 1) + ":30");
                                                    } else {
                                                        presence.setHeureSortie(String.valueOf((7 + Integer.valueOf(cell.getStringCellValue()))).substring(0, 2) + ":30");
                                                    }
                                                } else {
                                                    presence.setHeureSortie(String.valueOf((7 + Integer.valueOf(cell.getStringCellValue()) + 1)).substring(0, 2) + ":30");
                                                }
                                            }
                                            presence.setDate(new Date(annee + "/" + mois + "/" + (cell.getColumnIndex() - 4)));
                                            presence.setFlag(false);
                                            presence.setNombreMinutes(0);
                                            presence.setCreePar("");
                                            presence.setDateCreation(new Date());

                                            //---------------------------
                                            String chaineDateEntree = annee + "/" + mois + "/" + (cell.getColumnIndex() - 4);
                                            Long longDateTimeEntree = new Date(chaineDateEntree + " " + presence.getHeureEntree()).getTime();
                                            presence.setLongDateTimeEntree(longDateTimeEntree);

                                            String chaineDateSortie = annee + "/" + mois + "/" + (cell.getColumnIndex() - 4);
                                            Long longDateTimeSortie = new Date(chaineDateSortie + " " + presence.getHeureSortie()).getTime();
                                            presence.setLongDateTimeEntree(longDateTimeSortie);
                                            //--------------------------

                                            //listePresences.add(presence);
                                            String[] presProb = insererPointage(presence, desaffectationChantiers, verificationContrat, ecraserPointagesExistants, chantierId);
                                            if (presProb != null && presProb != null && presProb.length != 0) {
                                                key++;
                                                data.put(String.valueOf(key), presProb);
                                            }
                                            //presence.setDate(date)

                                        } else if (cell.getCellType() == Cell.CELL_TYPE_STRING && !StringUtils.isNumeric(cell.getStringCellValue())) {
                                            presence = new Presence();
                                            Salarie salarie = new Salarie();

                                            if (row.getCell(0).getCellType() == Cell.CELL_TYPE_NUMERIC) {
                                                salarie.setId((int) (row.getCell(0).getNumericCellValue()));
                                            } else if (row.getCell(0).getCellType() == Cell.CELL_TYPE_STRING) {
                                                salarie.setId(Integer.valueOf(row.getCell(0).getStringCellValue()));
                                            }

                                            presence.setSalarie(salarie);

                                            presence.setDate(new Date(annee + "/" + mois + "/" + (cell.getColumnIndex() - 4)));
                                            presence.setFlag(false);
                                            presence.setNombreMinutes(0);
                                            System.out.println("problème conversion de \"" + cell.getStringCellValue() + "\"");
                                            //								    	  	listePresencesProb.add(presence);
                                            key++;
                                            String[] dataProb = new String[]{presence.getSalarie().getId().toString(), "Probleme de conversion", presence.getDate().toString()};
                                            data.put(String.valueOf(key), dataProb);
                                        }
                                    }
                                }
                            }

                            if (avecReglage == true && (row.getCell(4, Row.RETURN_NULL_AND_BLANK) != null) && (row.getCell(4, Row.RETURN_BLANK_AS_NULL) != null)) {

                                Date dtSortie = null;
                                if (row.getCell(4).getCellType() == Cell.CELL_TYPE_NUMERIC && DateUtil.isCellDateFormatted(row.getCell(4))) {
                                    dtSortie = row.getCell(4).getDateCellValue();
                                    System.out.println("Date sortie:" + dtSortie);
                                } else if (row.getCell(4).getCellType() == Cell.CELL_TYPE_STRING) {
                                    dtSortie = new Date(row.getCell(4).getStringCellValue().substring(6, 10) + "/" + row.getCell(4).getStringCellValue().substring(3, 5) + "/" + row.getCell(4).getStringCellValue().substring(0, 2));
                                    System.out.println("Date sortie:" + dtSortie);
                                }

                                if (contratService.nombreContratParEtat((int) row.getCell(0).getNumericCellValue(), 2) == 1) {
                                    Contrat contrat = null;
                                    contrat = contratService.dernierContratParEtat((int) (row.getCell(0).getNumericCellValue()), 2);
                                    //Date dateFin =new Date(row.getCell(4).getStringCellValue().substring(6, 10)+"/"+row.getCell(4).getStringCellValue().substring(3, 5)+"/"+row.getCell(4).getStringCellValue().substring(0, 2));
                                    Date dateFin = dtSortie;
                                    if (dateFin.after(contrat.getDateEmbauche())) {
                                        String dateDebutContrat = contrat.getDateEmbauche().toString().substring(0, 10).replaceAll("-", "");
                                        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                        String dateFinContrat = dateFormat.format(dateFin).replaceAll("-", "");
                                        //String dateFinContrat=dateFin.toString().substring(0,10).replaceAll("-", "");
                                        String motif = "";
                                        Integer idMotif = null;
                                        if ((row.getCell(36, Row.RETURN_NULL_AND_BLANK) != null) && (row.getCell(36, Row.RETURN_BLANK_AS_NULL) != null)) {
                                            motif = row.getCell(36).getStringCellValue().trim().toLowerCase();
                                            idMotif = parametrageService.getMotifByCode(motif).getId();
                                        } else {
                                            motif = "fcdd";
                                        }
                                        String refContrat = salarieService.modifierContratSalarieWS(String.valueOf(row.getCell(0).getNumericCellValue()).substring(0, 7), dateDebutContrat,
                                                dateFinContrat, motif, dateFinContrat).get("referenceContratDiva");
                                        if (!refContrat.equals("0") && !refContrat.equals("-1")) {
                                            contratService.reglerContrat(contrat.getId(), dateFin, idMotif);
                                            salarieService.changerEtatSalarie((int) (row.getCell(0).getNumericCellValue()), 3);
                                        } else {
                                            System.out.println("Problème de réglage sur Divalto");
                                            String[] dataProb = new String[]{String.valueOf((int) row.getCell(0).getNumericCellValue()), "Non réglé: Problème de réglage sur Divalto", ""};
                                            key++;
                                            data.put(String.valueOf(key), dataProb);
                                        }
                                    } else {
                                        System.out.println("Problème de réglage: date Fin inferieure a date Embauche");
                                        String[] dataProb = new String[]{String.valueOf((int) row.getCell(0).getNumericCellValue()), "Non réglé: Date Fin infénieure à Date Embauche", ""};
                                        key++;
                                        data.put(String.valueOf(key), dataProb);
                                    }
                                } else if (contratService.nombreContratParEtat((int) row.getCell(0).getNumericCellValue(), 2) == 0) {
                                    System.out.println("matricule n\'a pas de contrat");
                                    String[] dataProb = new String[]{String.valueOf((int) row.getCell(0).getNumericCellValue()), "Non réglé: matricule n\'a pas de contrat", ""};
                                    key++;
                                    data.put(String.valueOf(key), dataProb);
                                } else if (contratService.nombreContratParEtat((int) row.getCell(0).getNumericCellValue(), 2) > 1) {
                                    System.out.println("matricule a plusieurs contrats légalisés");
                                    String[] dataProb = new String[]{String.valueOf((int) row.getCell(0).getNumericCellValue()), "Non réglé: matricule a plusieurs contrats légalisés", ""};
                                    key++;
                                    data.put(String.valueOf(key), dataProb);
                                }
                            }

                        }
                    }
                }

            } else if (extensionFichier.equals("xls")) {
                HSSFWorkbook workbook = new HSSFWorkbook();
                HSSFSheet sheet = workbook.getSheetAt(0);
                rowIterator = sheet.iterator();

                //				HSSFFont font = workbook.createFont();
                //				font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
                //				HSSFCellStyle style = workbook.createCellStyle();
                //				style.setFont(font);
                while (rowIterator.hasNext()) {
                    Row row = rowIterator.next();
                    //				     if (row.getCell(0).getCellType()==Cell.CELL_TYPE_STRING && row.getCell(0).getStringCellValue().equals("MATRICULE")){ 
                    //				    	  System.out.println("colone: "+row.getCell(0).getStringCellValue());
                    //				    	  System.out.println("row index:"+row.getRowNum());
                    //				     }
                    if (row.getCell(0) != null) {
                        if (row.getRowNum() != 0 && row.getCell(0).getCellType() == Cell.CELL_TYPE_STRING && !StringUtils.isNumeric(row.getCell(0).getStringCellValue())) {
                            //matriculesProb.add(row.getCell(0).getStringCellValue());
                            System.out.println("row index:" + row.getRowNum());
                            String[] dataProb = new String[]{row.getCell(0).getStringCellValue(), "Matricule inexistant", ""};
                            key++;
                            data.put(String.valueOf(key), dataProb);
                        } else if (row.getRowNum() != 0 && row.getCell(0).getCellType() == Cell.CELL_TYPE_STRING && StringUtils.isNumeric(row.getCell(0).getStringCellValue()) && !salarieService.isExist(Integer.valueOf(row.getCell(0).getStringCellValue()))) {
                            //matriculesProb.add(String.valueOf(row.getCell(0).getStringCellValue()));
                            String[] dataProb = new String[]{String.valueOf(row.getCell(0).getStringCellValue()), "Matricule inexistant", ""};
                            key++;
                            data.put(String.valueOf(key), dataProb);
                        } else if (row.getRowNum() != 0 && row.getCell(0).getCellType() == Cell.CELL_TYPE_NUMERIC && !salarieService.isExist((int) row.getCell(0).getNumericCellValue())) {
                            //matriculesProb.add(String.valueOf((int)row.getCell(0).getNumericCellValue()));
                            String[] dataProb = new String[]{String.valueOf((int) row.getCell(0).getNumericCellValue()), "Matricule inexistant", ""};
                            key++;
                            data.put(String.valueOf(key), dataProb);
                        } else if (row.getRowNum() != 0 && ((row.getCell(0).getCellType() == Cell.CELL_TYPE_NUMERIC && salarieService.isExist((int) row.getCell(0).getNumericCellValue()))
                                || (row.getCell(0).getCellType() == Cell.CELL_TYPE_STRING && StringUtils.isNumeric(row.getCell(0).getStringCellValue()) && salarieService.isExist(Integer.valueOf(row.getCell(0).getStringCellValue()))))) {

                            if (row.getCell(0).getCellType() == Cell.CELL_TYPE_NUMERIC) {
                                //salariesIDs.add((int)row.getCell(0).getNumericCellValue()); 
                                System.out.println("matricule: " + (int) row.getCell(0).getNumericCellValue());
                                if (desaffectationChantiers) {
                                    chantierService.desaffecterSalarieTousChantiers((int) row.getCell(0).getNumericCellValue());
                                    Chantier chantier = new Chantier();
                                    Salarie salarie = new Salarie();
                                    salarie.setId((int) row.getCell(0).getNumericCellValue());
                                    if (chantierId != null) {
                                        chantier.setId(chantierId);
                                        chantierService.affecterSalarieChantier(salarie, chantier); // affecter le salarié au chantier
                                    } else {
                                        Integer idChantier = chantierService.getChantierByAffaire(row.getCell(3).getStringCellValue());
                                        if (idChantier != null) {
                                            chantier.setId(idChantier);
                                            chantierService.affecterSalarieChantier(salarie, chantier); // affecter le salarié au chantier
                                        }
                                    }
                                }

                                /*
                                 * code reglage deplacé
                                 * 
                                 */
                            } else if (row.getCell(0).getCellType() == Cell.CELL_TYPE_STRING) {
                                //salariesIDs.add(Integer.valueOf(row.getCell(0).getStringCellValue())); 
                                System.out.println("matricule: " + row.getCell(0).getStringCellValue());
                                if (desaffectationChantiers) {
                                    chantierService.desaffecterSalarieTousChantiers(Integer.valueOf(row.getCell(0).getStringCellValue()));
                                    Chantier chantier = new Chantier();
                                    Salarie salarie = new Salarie();
                                    salarie.setId(Integer.valueOf(row.getCell(0).getStringCellValue()));
                                    if (chantierId != null) {
                                        chantier.setId(chantierId);
                                        chantierService.affecterSalarieChantier(salarie, chantier); // affecter le salarié au chantier
                                    } else {
                                        Integer idChantier = chantierService.getChantierByAffaire(row.getCell(3).getStringCellValue());
                                        if (idChantier != null) {
                                            chantier.setId(idChantier);
                                            chantierService.affecterSalarieChantier(salarie, chantier); // affecter le salarié au chantier
                                        }
                                    }
                                }
                                if (avecReglage == true && (row.getCell(4, Row.RETURN_NULL_AND_BLANK) != null) || (row.getCell(4, Row.RETURN_BLANK_AS_NULL) != null)) {
                                    Date dtSortie = null;
                                    if (row.getCell(4).getCellType() == Cell.CELL_TYPE_NUMERIC && DateUtil.isCellDateFormatted(row.getCell(4))) {
                                        dtSortie = row.getCell(4).getDateCellValue();
                                    } else if (row.getCell(4).getCellType() == Cell.CELL_TYPE_STRING) {
                                        dtSortie = new Date(row.getCell(4).getStringCellValue().substring(6, 10) + "/" + row.getCell(4).getStringCellValue().substring(3, 5) + "/" + row.getCell(4).getStringCellValue().substring(0, 2));

                                    }

                                    if (contratService.nombreContratParEtat(Integer.valueOf(row.getCell(0).getStringCellValue()), 2) == 1) {
                                        Contrat contrat = null;
                                        contrat = contratService.dernierContratParEtat(Integer.valueOf((row.getCell(0).getStringCellValue())), 2);
                                        //Date dateFin =new Date(row.getCell(4).getStringCellValue().substring(6, 10)+"/"+row.getCell(4).getStringCellValue().substring(3, 5)+"/"+row.getCell(4).getStringCellValue().substring(0, 2));
                                        Date dateFin = dtSortie;
                                        if (dateFin.after(contrat.getDateEmbauche())) {
                                            String dateDebutContrat = contrat.getDateEmbauche().toString().substring(0, 10).replaceAll("-", "");
                                            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                            String dateFinContrat = dateFormat.format(dateFin).replaceAll("-", "");
                                            //String dateFinContrat=dateFin.toString().substring(0,10).replaceAll("-", "");
                                            System.out.println("Date de sortie: " + dateFinContrat);
                                            String motif = "";
                                            Integer idMotif = null;
                                            if ((row.getCell(36, Row.RETURN_NULL_AND_BLANK) != null) && (row.getCell(36, Row.RETURN_BLANK_AS_NULL) != null)) {
                                                motif = row.getCell(36).getStringCellValue().trim().toLowerCase();
                                                idMotif = parametrageService.getMotifByCode(motif).getId();
                                            } else {
                                                motif = "fcdd";
                                            }
                                            String refContrat = salarieService.modifierContratSalarieWS(String.valueOf(row.getCell(0).getStringCellValue()), dateDebutContrat,
                                                    dateFinContrat, motif, dateFinContrat).get("referenceContratDiva");
                                            if (!refContrat.equals("0") && !refContrat.equals("-1")) {
                                                contratService.reglerContrat(contrat.getId(), dateFin, idMotif);
                                                salarieService.changerEtatSalarie(Integer.valueOf((row.getCell(0).getStringCellValue())), 3);
                                            } else {
                                                System.out.println("Problème de réglage sur Divalto");
                                                String[] dataProb = new String[]{String.valueOf(row.getCell(0).getStringCellValue()), "Non réglé: Problème de réglage sur Divalto", ""};
                                                key++;
                                                data.put(String.valueOf(key), dataProb);
                                            }
                                        } else {
                                            System.out.println("Problème de réglage: date Fin inferieure a date Embauche");
                                            String[] dataProb = new String[]{String.valueOf((int) row.getCell(0).getNumericCellValue()), "Non réglé: Date Fin infénieure à Date Embauche", ""};
                                            key++;
                                            data.put(String.valueOf(key), dataProb);
                                        }
                                    } else if (contratService.nombreContratParEtat(Integer.valueOf(row.getCell(0).getStringCellValue()), 2) == 0) {
                                        System.out.println("matricule n\'a pas de contrat");
                                        String[] dataProb = new String[]{String.valueOf(row.getCell(0).getStringCellValue()), "Non réglé: matricule n\'a pas de contrat", ""};
                                        key++;
                                        data.put(String.valueOf(key), dataProb);
                                    } else if (contratService.nombreContratParEtat(Integer.valueOf(row.getCell(0).getStringCellValue()), 2) > 1) {
                                        System.out.println("matricule a plusieurs contrats légalisés");
                                        String[] dataProb = new String[]{String.valueOf(row.getCell(0).getStringCellValue()), "Non réglé: matricule a plusieurs contrats légalisés", ""};
                                        key++;
                                        data.put(String.valueOf(key), dataProb);
                                    }
                                }
                            }

                            Iterator<Cell> cellIterator = row.cellIterator();
                            while (cellIterator.hasNext()) {
                                Cell cell = cellIterator.next();
                                if (row.getCell(0).getCellType() == Cell.CELL_TYPE_STRING && row.getCell(0).getStringCellValue().equals("MATRICULE")) {
                                    System.out.println("colone: " + row.getCell(0).getStringCellValue());
                                } else if (cell.getColumnIndex() >= 5 && cell.getColumnIndex() <= 35) {

                                    if (cell != null) {

                                        if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC && cell.getNumericCellValue() > 0) {

                                            presence = new Presence();
                                            Salarie salarie = new Salarie();

                                            if (row.getCell(0).getCellType() == Cell.CELL_TYPE_NUMERIC) {
                                                salarie.setId((int) (row.getCell(0).getNumericCellValue()));
                                            } else if (row.getCell(0).getCellType() == Cell.CELL_TYPE_STRING) {
                                                salarie.setId(Integer.valueOf(row.getCell(0).getStringCellValue()));
                                            }

                                            presence.setSalarie(salarie);

                                            if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                                                presence.setNombreHeures((int) (cell.getNumericCellValue()));
                                            } else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                                                presence.setNombreHeures(Integer.valueOf(cell.getStringCellValue()));
                                            }

                                            Chantier chantier = null;
                                            if (chantierId != null) {
                                                chantier = new Chantier();
                                                chantier.setId(chantierId);
                                                presence.setChantier(chantier);
                                            } else {
                                                chantier = new Chantier();
                                                if (row.getCell(3).getCellType() == Cell.CELL_TYPE_STRING && !row.getCell(3).getStringCellValue().equals("")) {
                                                    Integer idChantier = chantierService.getChantierByAffaire(row.getCell(3).getStringCellValue());
                                                    if (idChantier != null) {
                                                        chantier.setId(idChantier);
                                                        presence.setChantier(chantier);
                                                    } else {
                                                        System.out.println("problème de chantier");
                                                        return null;
                                                    }
                                                } else {
                                                    System.out.println("problème de chantier");
                                                    return null;
                                                }
                                            }
                                            presence.setHeureEntree("07:30");

                                            if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                                                if (cell.getNumericCellValue() <= 5) {
                                                    if (cell.getNumericCellValue() < 3) {
                                                        presence.setHeureSortie(String.valueOf((7 + (cell.getNumericCellValue()))).substring(0, 1) + ":30");
                                                    } else {
                                                        presence.setHeureSortie(String.valueOf((7 + (cell.getNumericCellValue()))).substring(0, 2) + ":30");
                                                    }
                                                } else {
                                                    presence.setHeureSortie(String.valueOf((7 + (cell.getNumericCellValue()) + 1)).substring(0, 2) + ":30");
                                                }
                                            }
                                            if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                                                if (Integer.valueOf(cell.getStringCellValue()) <= 5) {
                                                    if (Integer.valueOf(cell.getStringCellValue()) < 3) {
                                                        presence.setHeureSortie(String.valueOf((7 + Integer.valueOf(cell.getStringCellValue()))).substring(0, 1) + ":30");
                                                    } else {
                                                        presence.setHeureSortie(String.valueOf((7 + Integer.valueOf(cell.getStringCellValue()))).substring(0, 2) + ":30");
                                                    }
                                                } else {
                                                    presence.setHeureSortie(String.valueOf((7 + Integer.valueOf(cell.getStringCellValue()) + 1)).substring(0, 2) + ":30");
                                                }
                                            }
                                            presence.setDate(new Date(annee + "/" + mois + "/" + (cell.getColumnIndex() - 4)));
                                            presence.setFlag(false);
                                            presence.setNombreMinutes(0);
                                            presence.setCreePar("");
                                            presence.setDateCreation(new Date());

                                            //---------------------------
                                            String chaineDateEntree = annee + "/" + mois + "/" + (cell.getColumnIndex() - 4);
                                            Long longDateTimeEntree = new Date(chaineDateEntree + " " + presence.getHeureEntree()).getTime();
                                            presence.setLongDateTimeEntree(longDateTimeEntree);

                                            String chaineDateSortie = annee + "/" + mois + "/" + (cell.getColumnIndex() - 4);
                                            Long longDateTimeSortie = new Date(chaineDateSortie + " " + presence.getHeureSortie()).getTime();
                                            presence.setLongDateTimeEntree(longDateTimeSortie);
                                            //--------------------------
                                            //						     			 
                                            //listePresences.add(presence);
                                            String[] presProb = insererPointage(presence, desaffectationChantiers, verificationContrat, ecraserPointagesExistants, chantierId);
                                            if (presProb != null && presProb.length != 0) {
                                                key++;
                                                data.put(String.valueOf(key), presProb);
                                            }
                                            //presence.setDate(date)

                                        } else if (cell.getCellType() == Cell.CELL_TYPE_STRING && StringUtils.isNumeric(cell.getStringCellValue())
                                                && Integer.valueOf(cell.getStringCellValue()) > 0) {
                                            presence = new Presence();
                                            Salarie salarie = new Salarie();

                                            if (row.getCell(0).getCellType() == Cell.CELL_TYPE_NUMERIC) {
                                                salarie.setId((int) (row.getCell(0).getNumericCellValue()));
                                            } else if (row.getCell(0).getCellType() == Cell.CELL_TYPE_STRING) {
                                                salarie.setId(Integer.valueOf(row.getCell(0).getStringCellValue()));
                                            }

                                            presence.setSalarie(salarie);

                                            if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                                                presence.setNombreHeures((int) (cell.getNumericCellValue()));
                                            } else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                                                presence.setNombreHeures(Integer.valueOf(cell.getStringCellValue()));
                                            }

                                            Chantier chantier = null;
                                            if (chantierId != null) {
                                                chantier = new Chantier();
                                                chantier.setId(chantierId);
                                                presence.setChantier(chantier);
                                            } else {
                                                chantier = new Chantier();
                                                chantier.setId(chantierService.getChantierByAffaire(row.getCell(3).getStringCellValue()));
                                                presence.setChantier(chantier);
                                            }
                                            presence.setHeureEntree("07:30");

                                            if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                                                if (cell.getNumericCellValue() <= 5) {
                                                    if (cell.getNumericCellValue() < 3) {
                                                        presence.setHeureSortie(String.valueOf((7 + (cell.getNumericCellValue()))).substring(0, 1) + ":30");
                                                    } else {
                                                        presence.setHeureSortie(String.valueOf((7 + (cell.getNumericCellValue()))).substring(0, 2) + ":30");
                                                    }
                                                } else {
                                                    presence.setHeureSortie(String.valueOf((7 + (cell.getNumericCellValue()) + 1)).substring(0, 2) + ":30");
                                                }
                                            }
                                            if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                                                if (Integer.valueOf(cell.getStringCellValue()) <= 5) {
                                                    if (Integer.valueOf(cell.getStringCellValue()) < 3) {
                                                        presence.setHeureSortie(String.valueOf((7 + Integer.valueOf(cell.getStringCellValue()))).substring(0, 1) + ":30");
                                                    } else {
                                                        presence.setHeureSortie(String.valueOf((7 + Integer.valueOf(cell.getStringCellValue()))).substring(0, 2) + ":30");
                                                    }
                                                } else {
                                                    presence.setHeureSortie(String.valueOf((7 + Integer.valueOf(cell.getStringCellValue()) + 1)).substring(0, 2) + ":30");
                                                }
                                            }
                                            presence.setDate(new Date(annee + "/" + mois + "/" + (cell.getColumnIndex() - 4)));
                                            presence.setFlag(false);
                                            presence.setNombreMinutes(0);
                                            presence.setCreePar("");
                                            presence.setDateCreation(new Date());

                                            //---------------------------
                                            String chaineDateEntree = annee + "/" + mois + "/" + (cell.getColumnIndex() - 4);
                                            Long longDateTimeEntree = new Date(chaineDateEntree + " " + presence.getHeureEntree()).getTime();
                                            presence.setLongDateTimeEntree(longDateTimeEntree);

                                            String chaineDateSortie = annee + "/" + mois + "/" + (cell.getColumnIndex() - 4);
                                            Long longDateTimeSortie = new Date(chaineDateSortie + " " + presence.getHeureSortie()).getTime();
                                            presence.setLongDateTimeEntree(longDateTimeSortie);
                                            //--------------------------

                                            //listePresences.add(presence);
                                            String[] presProb = insererPointage(presence, desaffectationChantiers, verificationContrat, ecraserPointagesExistants, chantierId);
                                            if (presProb != null && presProb != null && presProb.length != 0) {
                                                key++;
                                                data.put(String.valueOf(key), presProb);
                                            }
                                            //presence.setDate(date)

                                        } else if (cell.getCellType() == Cell.CELL_TYPE_STRING && !StringUtils.isNumeric(cell.getStringCellValue())) {
                                            presence = new Presence();
                                            Salarie salarie = new Salarie();

                                            if (row.getCell(0).getCellType() == Cell.CELL_TYPE_NUMERIC) {
                                                salarie.setId((int) (row.getCell(0).getNumericCellValue()));
                                            } else if (row.getCell(0).getCellType() == Cell.CELL_TYPE_STRING) {
                                                salarie.setId(Integer.valueOf(row.getCell(0).getStringCellValue()));
                                            }

                                            presence.setSalarie(salarie);

                                            presence.setDate(new Date(annee + "/" + mois + "/" + (cell.getColumnIndex() - 4)));
                                            presence.setFlag(false);
                                            presence.setNombreMinutes(0);
                                            System.out.println("problème conversion de \"" + cell.getStringCellValue() + "\"");
                                            //								    	  	listePresencesProb.add(presence);
                                            key++;
                                            String[] dataProb = new String[]{presence.getSalarie().getId().toString(), "Probleme de conversion", presence.getDate().toString()};
                                            data.put(String.valueOf(key), dataProb);
                                        }
                                    }
                                }
                            }

                            if (avecReglage == true && (row.getCell(4, Row.RETURN_NULL_AND_BLANK) != null) && (row.getCell(4, Row.RETURN_BLANK_AS_NULL) != null)) {

                                Date dtSortie = null;
                                if (row.getCell(4).getCellType() == Cell.CELL_TYPE_NUMERIC && DateUtil.isCellDateFormatted(row.getCell(4))) {
                                    dtSortie = row.getCell(4).getDateCellValue();
                                    System.out.println("Date sortie:" + dtSortie);
                                } else if (row.getCell(4).getCellType() == Cell.CELL_TYPE_STRING) {
                                    dtSortie = new Date(row.getCell(4).getStringCellValue().substring(6, 10) + "/" + row.getCell(4).getStringCellValue().substring(3, 5) + "/" + row.getCell(4).getStringCellValue().substring(0, 2));
                                    System.out.println("Date sortie:" + dtSortie);
                                }

                                if (contratService.nombreContratParEtat((int) row.getCell(0).getNumericCellValue(), 2) == 1) {
                                    Contrat contrat = null;
                                    contrat = contratService.dernierContratParEtat((int) (row.getCell(0).getNumericCellValue()), 2);
                                    //Date dateFin =new Date(row.getCell(4).getStringCellValue().substring(6, 10)+"/"+row.getCell(4).getStringCellValue().substring(3, 5)+"/"+row.getCell(4).getStringCellValue().substring(0, 2));
                                    Date dateFin = dtSortie;
                                    if (dateFin.after(contrat.getDateEmbauche())) {
                                        String dateDebutContrat = contrat.getDateEmbauche().toString().substring(0, 10).replaceAll("-", "");
                                        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                        String dateFinContrat = dateFormat.format(dateFin).replaceAll("-", "");
                                        //String dateFinContrat=dateFin.toString().substring(0,10).replaceAll("-", "");
                                        String motif = "";
                                        Integer idMotif = null;
                                        if ((row.getCell(36, Row.RETURN_NULL_AND_BLANK) != null) && (row.getCell(36, Row.RETURN_BLANK_AS_NULL) != null)) {
                                            motif = row.getCell(36).getStringCellValue().trim().toLowerCase();
                                            idMotif = parametrageService.getMotifByCode(motif).getId();
                                        } else {
                                            motif = "fcdd";
                                        }
                                        String refContrat = salarieService.modifierContratSalarieWS(String.valueOf(row.getCell(0).getNumericCellValue()).substring(0, 7), dateDebutContrat,
                                                dateFinContrat, motif, dateFinContrat).get("referenceContratDiva");
                                        if (!refContrat.equals("0") && !refContrat.equals("-1")) {
                                            contratService.reglerContrat(contrat.getId(), dateFin, idMotif);
                                            salarieService.changerEtatSalarie((int) (row.getCell(0).getNumericCellValue()), 3);
                                        } else {
                                            System.out.println("Problème de réglage sur Divalto");
                                            String[] dataProb = new String[]{String.valueOf((int) row.getCell(0).getNumericCellValue()), "Non réglé: Problème de réglage sur Divalto", ""};
                                            key++;
                                            data.put(String.valueOf(key), dataProb);
                                        }
                                    } else {
                                        System.out.println("Problème de réglage: date Fin inferieure a date Embauche");
                                        String[] dataProb = new String[]{String.valueOf((int) row.getCell(0).getNumericCellValue()), "Non réglé: Date Fin infénieure à Date Embauche", ""};
                                        key++;
                                        data.put(String.valueOf(key), dataProb);
                                    }
                                } else if (contratService.nombreContratParEtat((int) row.getCell(0).getNumericCellValue(), 2) == 0) {
                                    System.out.println("matricule n\'a pas de contrat");
                                    String[] dataProb = new String[]{String.valueOf((int) row.getCell(0).getNumericCellValue()), "Non réglé: matricule n\'a pas de contrat", ""};
                                    key++;
                                    data.put(String.valueOf(key), dataProb);
                                } else if (contratService.nombreContratParEtat((int) row.getCell(0).getNumericCellValue(), 2) > 1) {
                                    System.out.println("matricule a plusieurs contrats légalisés");
                                    String[] dataProb = new String[]{String.valueOf((int) row.getCell(0).getNumericCellValue()), "Non réglé: matricule a plusieurs contrats légalisés", ""};
                                    key++;
                                    data.put(String.valueOf(key), dataProb);
                                }
                            }

                        }
                    }
                }

            } else {

                return null;
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
        //return listes;
        return data;
    }

    public boolean insererLigneMatProb(HSSFSheet sheet, String[] matProb, int rownum) {

        try {
            Row row = sheet.createRow(rownum++);
            Object[] objArr = matProb;
            int cellnum = 0;
            for (Object obj : objArr) {
                Cell cell = row.createCell(cellnum++);
                if (obj instanceof Date) {
                    cell.setCellValue((Date) obj);
                } else if (obj instanceof Boolean) {
                    cell.setCellValue((Boolean) obj);
                } else if (obj instanceof String) {
                    cell.setCellValue((String) obj);
                } else if (obj instanceof Double) {
                    cell.setCellValue((Double) obj);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

        public ByteArrayOutputStream creerDocument(Map<String, String[]> donnees) {

        ByteArrayOutputStream output = new ByteArrayOutputStream();

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("pointage non inseres");

        Map<String, Object[]> data = new HashMap<String, Object[]>();
        String[] dataProb = null;

        Set<String> keysetDonnees = donnees.keySet();
        Object[] objets = null;
        for (String key : keysetDonnees) {
            dataProb = new String[]{donnees.get(key)[0], donnees.get(key)[1], donnees.get(key)[2]};
            objets = new Object[]{dataProb[0], dataProb[1], dataProb[2]};
//			objets[0]=key;
//			objets[1]=donnees.get(key);
            data.put(key, objets);
        }

        Set<String> keyset = data.keySet();
        int rownum = 0;
        Row entete = sheet.createRow(rownum++);
        int celNum = 0;
        Object[] objEntete = new String[]{"MATRICULE", "DESCRIPTION", "DATE"};
        for (Object o : objEntete) {
            Cell cell = entete.createCell(celNum++);
            if (o instanceof Date) {
                cell.setCellValue((Date) o);
            } else if (o instanceof Boolean) {
                cell.setCellValue((Boolean) o);
            } else if (o instanceof String) {
                cell.setCellValue((String) o);
            } else if (o instanceof Double) {
                cell.setCellValue((Double) o);
            }
        }

        for (String key : keyset) {
            Row row = sheet.createRow(rownum++);
            Object[] objArr = data.get(key);
            int cellnum = 0;
            for (Object obj : objArr) {
                Cell cell = row.createCell(cellnum++);
                if (obj instanceof Date) {
                    cell.setCellValue((Date) obj);
                } else if (obj instanceof Boolean) {
                    cell.setCellValue((Boolean) obj);
                } else if (obj instanceof String) {
                    cell.setCellValue((String) obj);
                } else if (obj instanceof Double) {
                    cell.setCellValue((Double) obj);
                }
            }
        }

        try {

            //FileOutputStream out=new FileOutputStream(new File("C:\\new.xls"));
            workbook.write(output);
            //out.close();
            //System.out.println("Excel créé avec succès..");

        } catch (IOException e) {
            e.printStackTrace();
        }

        return output;
    }

    /**
     * Insérer les pointages à partir d'une liste de présences
     *
     * @param presences la liste des présences (pointages) à insérer dans la
     * base données
     * @return liste des pointages problématiques
     */
    public Map<String, String[]> insererPointagesFichiers(Map<String, Object> presences, boolean desaffectationChantiers,
            boolean verificationContrat, boolean ecraserPointagesExistants, Integer chantierId) {
        logger.debug("insérer les présences à partir d'une liste");

        Session session = sessionFactory.getCurrentSession();
//        try {
//
//            session.connection().setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
//
//        } catch (HibernateException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (SQLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
        List<Presence> listePresencesProb = new ArrayList<Presence>();
        Map<String, String[]> data = new HashMap<String, String[]>();
        String[] dataProb = null;
        int i = 0;
        int j = 0;
        if (desaffectationChantiers && chantierId != null) {
            for (i = 0; i < ((List<Integer>) presences.get("listeSalaries")).size(); i++) {
                chantierService.desaffecterSalarieTousChantiers(((List<Integer>) presences.get("listeSalaries")).get(i));
                Salarie salarie = new Salarie();
                salarie.setId(((List<Integer>) presences.get("listeSalaries")).get(i));
                Chantier chantier = new Chantier();
                chantier.setId(chantierId);
                chantierService.affecterSalarieChantier(salarie, chantier); // affecter le salarié au chantier
            }
        }

        for (i = 0; i < ((List<Presence>) presences.get("listePresences")).size(); i++) {

//			if(desaffectationChantiers){
//				chantierService.desaffecterSalarieTousChantiers(presences.get(i).getSalarie());
//				chantierService.affecterSalarieChantier(presences.get(i).getSalarie(), presences.get(i).getChantier()); // affecter le salarié au chantier
//			}
            if (verificationContrat) {
                if (salarieService.isActif(((List<Presence>) presences.get("listePresences")).get(i).getSalarie().getId().toString())) {
                    if (!this.dejaPointe(((List<Presence>) presences.get("listePresences")).get(i).getSalarie().getId(), ((List<Presence>) presences.get("listePresences")).get(i).getDate())) {
                        ajouterPresence(((List<Presence>) presences.get("listePresences")).get(i)); // ajouter le pointage du salarié dans la base de données
                    } else if (ecraserPointagesExistants) {
                        supprimerPresence(((List<Presence>) presences.get("listePresences")).get(i).getSalarie().getId(), ((List<Presence>) presences.get("listePresences")).get(i).getChantier().getId(), ((List<Presence>) presences.get("listePresences")).get(i).getDate());
                        ajouterPresence(((List<Presence>) presences.get("listePresences")).get(i));
                    } else {
                        listePresencesProb.add(((List<Presence>) presences.get("listePresences")).get(i));
                        dataProb = new String[]{((List<Presence>) presences.get("listePresences")).get(i).getSalarie().getId().toString(), "Matricule déjà pointé", ((List<Presence>) presences.get("listePresences")).get(i).getDate().toString()};
                        data.put(String.valueOf(i), dataProb);
                        //data.put(String.valueOf(i), listepresencesProb.get(i).getSalarie().getId().toString()+" : Matricule déjà pointé le "+listepresencesProb.get(i).getDate());
                    }
                } else {
                    listePresencesProb.add(((List<Presence>) presences.get("listePresences")).get(i));
                    dataProb = new String[]{((List<Presence>) presences.get("listePresences")).get(i).getSalarie().getId().toString(), "Matricule Inactif", ((List<Presence>) presences.get("listePresences")).get(i).getDate().toString()};
                    data.put(String.valueOf(i), dataProb);
//					data.put(String.valueOf(i), listepresencesProb.get(i).getSalarie().getId().toString()+" : Matricule Inactif");
                }
            } else if (!this.dejaPointe(((List<Presence>) presences.get("listePresences")).get(i).getSalarie().getId(), ((List<Presence>) presences.get("listePresences")).get(i).getDate())) {
                ajouterPresence(((List<Presence>) presences.get("listePresences")).get(i)); // ajouter le pointage du salarié dans la base de données
            } else if (ecraserPointagesExistants) {
                supprimerPresence(((List<Presence>) presences.get("listePresences")).get(i).getSalarie().getId(), ((List<Presence>) presences.get("listePresences")).get(i).getChantier().getId(), ((List<Presence>) presences.get("listePresences")).get(i).getDate());
                ajouterPresence(((List<Presence>) presences.get("listePresences")).get(i));
            } else {
                listePresencesProb.add(((List<Presence>) presences.get("listePresences")).get(i));
                dataProb = new String[]{((List<Presence>) presences.get("listePresences")).get(i).getSalarie().getId().toString(), "Matricule déjà pointé", ((List<Presence>) presences.get("listePresences")).get(i).getDate().toString()};
                data.put(String.valueOf(i), dataProb);
                //matProb.put(listepresencesProb.get(i).getSalarie().getId().toString(), "Matricule déjà pointé le "+listepresencesProb.get(i).getDate());

            }

        }
        for (j = 0; j < ((List<Presence>) presences.get("listePresencesProb")).size(); j++) {

            dataProb = new String[]{((List<Presence>) presences.get("listePresencesProb")).get(j).getSalarie().getId().toString(), "Probleme de conversion", ((List<Presence>) presences.get("listePresencesProb")).get(j).getDate().toString()};
            data.put(String.valueOf(j + i + 1), dataProb);
        }

        for (int k = 0; k < ((List<String>) presences.get("matriculesProb")).size(); k++) {
            dataProb = new String[]{((List<String>) presences.get("matriculesProb")).get(k), "Matricule inexistant", ""};
            data.put(String.valueOf(i + j + k + 2), dataProb);
        }

        return data;

    }

    public String[] insererPointage(Presence presence, boolean desaffectationChantiers,
            boolean verificationContrat, boolean ecraserPointagesExistants, Integer chantierId) {
        logger.debug("insérer les présences à partir d'une liste");
        Session session = sessionFactory.getCurrentSession();
//        try {
//
//            session.connection().setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
//
//        } catch (HibernateException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (SQLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
        String[] dataProb = null;
        if (verificationContrat) {
            if (salarieService.isActif((presence.getSalarie().getId().toString()))) {
                if (!this.dejaPointe(presence.getSalarie().getId(), presence.getDate())) {
                    ajouterPresence(presence); // ajouter le pointage du salarié dans la base de données
                } else if (ecraserPointagesExistants) {
                    supprimerPresence(presence.getSalarie().getId(), presence.getChantier().getId(), presence.getDate());
                    ajouterPresence(presence); // ajouter le pointage du salarié dans la base de données
                } else {
                    dataProb = new String[]{presence.getSalarie().getId().toString(), "Matricule déjà pointé", presence.getDate().toString()};
                }
            } else {
                dataProb = new String[]{presence.getSalarie().getId().toString(), "Matricule Inactif", presence.getDate().toString()};
            }
        } else if (!this.dejaPointe(presence.getSalarie().getId(), presence.getDate())) {
            ajouterPresence(presence); // ajouter le pointage du salarié dans la base de données
        } else if (ecraserPointagesExistants) {
            supprimerPresence(presence.getSalarie().getId(), presence.getChantier().getId(), presence.getDate());
            ajouterPresence(presence); // ajouter le pointage du salarié dans la base de données
        } else {
            dataProb = new String[]{presence.getSalarie().getId().toString(), "Matricule déjà pointé", presence.getDate().toString()};
        }
        return dataProb;
    }

}
