package ma.bservices.services;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.xml.ws.Holder;

//import ma.bservices.authentication.CmaUsernamePasswordAuthenticationToken;
import ma.bservices.beans.Contrat;
import ma.bservices.beans.Etat;
import ma.bservices.beans.Fonction;
import ma.bservices.beans.Presence;
import ma.bservices.beans.SituationFamiliale;
import ma.bservices.beans.Statut;
import ma.bservices.beans.Salarie;
import ma.bservices.beans.Type;
import ma.bservices.beans.TypeDocument;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
//import com.rivetlogic.core.cma.repo.Ticket;

import ma.bservices.tgcc.webService.DivaltoService;
import ma.bservices.tgcc.webService.DivaltoServiceSoap;
import javax.persistence.NonUniqueResultException;
import ma.bservice.tgcc.Constante.Constante;
import ma.bservices.tgcc.Entity.Mensuel;
import ma.bservices.tgcc.utilitaire.MbHibernateDaoSupport;
import org.hibernate.type.StandardBasicTypes;

/**
 * @author bservices
 *
 */
@Service("salarieService")
@Transactional
public class SalarieService   {

    protected static Logger logger = Logger.getLogger("service");
     
    
    
    
    
    @Resource(name = "sessionFactory")
    private SessionFactory sessionFactory;

    @Resource(name = "parametrageService")
    private ParametrageService parametrageService;

    @Resource(name = "contratService")
    private ContratService contratService;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public ParametrageService getParametrageService() {
        return parametrageService;
    }

    public void setParametrageService(ParametrageService parametrageService) {
        this.parametrageService = parametrageService;
    }

    public ContratService getContratService() {
        return contratService;
    }

    public void setContratService(ContratService contratService) {
        this.contratService = contratService;
    }

    /**
     * Créer un nouveau salarié dans la base de données. Avant de le passer au
     * paramètres il faut utiliser les Setters pour insérer les informations du
     * salarié
     *
     * @param salarie Instance d'un Objet Salarie
     * @return id du nouveau salarié
     */
    public Integer ajouterSalarie(Salarie salarie) {
        logger.debug("Ajouter un nouveau salarie");

        Session session = sessionFactory.getCurrentSession();

        Integer idSalarie = (Integer) session.save(salarie);

        //Génération de la valeur du matricule 
        //String typeSalarie=salarie.getType()!= null ? parametrageService.getType(salarie.getType().getId()).getType().substring(0, 1) : ""; 
        String matricule = idSalarie.toString();

        salarie.setMatricule(matricule);
        salarie.setId(idSalarie);
        session.update(salarie);

        return idSalarie;

    }

    /**
     * Récupérer le nombre des salariés selon les critères de recherche passés
     * en paramètres
     *
     * @param matricule le matricule du salarié
     * @param statut l'identifiant du statut du salarié dans la base de donnée
     * @param fonction l'identifiant de la fonction du salarié dans la base de
     * donnée
     * @param etat l'identifiant de l'état du salarié dans la base de donnée
     * @param cin la CIN (Numéro de la carte d'identité Nationale) du salarié
     * @param nom le nom du salarié
     * @param prenom le prénom du salarié
     * @param cnss
     * @param codeChantier
     * @param matriculeNova
     *
     * @return nombre des salariés
     */
    public Long nombreSalaries(String matricule,
            Integer statut, Integer fonction, Integer etat, String cin, String nom, String prenom, String cnss, String codeChantier, String matriculeNova) {
        logger.debug("Nombre des salaries");

        //System.out.println("nombre ->count 1 : ");

        Session session = sessionFactory.getCurrentSession();
        Long nbSalaries = null;

        String req = "";
//	    if(codeChantier==""){
//
//		if( matricule=="" && matriculeNova=="" && statut==null && fonction==null && etat==null && cin == "" && nom=="" && prenom=="" && cnss==""){
//
//			req="SELECT count(*) FROM Salarie";
//
//		}
//		else{
//
//			req="SELECT count(*) FROM Salarie s WHERE";
//
//			if (matricule!=""){
//				req=req+" s.matricule =:matricule";
//			}
//			if (matriculeNova!=""){
//				req=req+" s.matriculeDivalto =:matriculeNova";
//			}
//
//			if (statut!=null){
//				req=req+" AND s.fonction.statut.id =:statut";
//			}
//
//			if (fonction!=null){
//				req=req+" AND s.fonction.id =:fonction";
//			}
//
//			if (etat!=null){
//				req=req+" AND s.etat.id =:etat";
//			}
//
//			if (cin!=""){
//				req=req+" AND s.cin =:cin";
//			}
//
//			if (nom!=""){
//				req=req+" AND s.nom =:nom";
//			}
//
//			if (prenom!=""){
//				req=req+" AND s.prenom =:prenom";
//			}
//			if (cnss!=""){
//				req=req+" AND s.cnss =:cnss";
//			}
//
//			req=req.replaceAll("WHERE AND", "WHERE");
//
//
//
//		}
//
//		Query query=session.createQuery(req); 
//
//		int position;
//
//
//		if(matricule!=""){
//			query.setParameter("matricule",matricule,Hibernate.STRING);
//		}
//		if(matriculeNova!=""){
//			query.setParameter("matriculeNova",matriculeNova.toUpperCase().trim(),Hibernate.STRING);
//		}
//
//		if (statut!=null){
//			query.setParameter("statut",statut,Hibernate.INTEGER);
//		}
//
//		if (fonction!=null){
//			query.setParameter("fonction",fonction,Hibernate.INTEGER);
//		}
//
//		if (etat!=null){
//			query.setParameter("etat",etat,Hibernate.INTEGER);
//		}
//
//		if(cin!=""){
//			query.setParameter("cin",cin,Hibernate.STRING);
//		}
//
//		if(nom!=""){
//			query.setParameter("nom",nom,Hibernate.STRING);
//		}
//
//		if(prenom!=""){
//			query.setParameter("prenom",prenom,Hibernate.STRING);
//		}
//		if(cnss!=""){
//			query.setParameter("cnss",cnss,Hibernate.STRING);
//		}
//
//		System.out.println("count 2 : " + query.uniqueResult());
//		nbSalaries=(Long) query.uniqueResult();
//		return  nbSalaries;
//	}else{

        String recherche = "";
        String affChantier = "";
        if (!"".equals(matricule) && matricule != null) {
            recherche = recherche + " AND s.MATRICULE='" + matricule + "'";
        }
        if (!"".equals(matriculeNova) && matriculeNova != null) {
            recherche = recherche + " AND UPPER(s.MATRICULEDIVALTO)='" + matriculeNova.toUpperCase().trim() + "'";
        }
        if (fonction != null && fonction != 0) {
            //System.out.println(" @@@@@@ ---- fonction : " + fonction);
            recherche = recherche + " AND s.fonction_PPTEMP_ID=" + fonction + "";
        }
        if (etat != null && etat != 0) {
            //System.out.println(" @@@@@@ ---- etat : " + etat);
            recherche = recherche + " AND s.etat_ID=" + etat + "";
        }
        if (!"".equals(cin) && cin != null) {
            //System.out.println(" @@@@@@ ---- cin : " + cin);
            recherche = recherche + " AND s.CIN ='" + cin + "'";
        }
        if (!"".equals(nom) && nom != null) {
            //System.out.println(" @@@@@@ ---- nom: " + nom);
            recherche = recherche + " AND s.NOM ='" + nom + "'";
        }
        if (!"".equals(prenom) && prenom != null) {
            //System.out.println(" @@@@@@ ---- prenom : " + prenom);
            recherche = recherche + " AND s.PRENOM ='" + prenom + "'";
        }
        if (!"".equals(cnss) && cnss != null) {
            //System.out.println(" @@@@@@ ---- cin : " + cin);
            recherche = recherche + " AND s.CNSS ='" + cnss + "'";
        }
        if (!"".equals(codeChantier)) {
            affChantier = " inner join CHANTIER_SALARIE as c on s.ID=c.SALARIE_ID ";
            recherche = recherche + " AND c.CHANTIER_ID =" + codeChantier + "";
        }

        //recherche.replaceFirst("AND", "WHERE");
        req = "SELECT count(*) FROM SALARIE as s " + affChantier
                + " inner join ETAT as e on s.etat_ID=e.ID "
                + " inner join PPTEMP f on s.fonction_PPTEMP_ID=f.PPTEMP_ID where s.Dtype='Salarie' " + recherche;
        
        logger.info(req);
        Query query = session.createSQLQuery(req);
        //System.out.println(" @@@@@@ ---- requete : " + req);
//		query.setParameter(0, codeChantier, Hibernate.STRING);
        Integer nb = (Integer) query.uniqueResult();
        //System.out.println("nombre salariés: " + nb.longValue());
        nbSalaries = (Long) nb.longValue();
        return nbSalaries;
//	}

    }

 

    /**
     * Récupérer le nombre des salariés selon les critères de recherche passés
     * en paramètres
     *
     * @param matricule le matricule du salarié
     * @param statut l'identifiant du statut du salarié dans la base de donnée
     * @param fonction l'identifiant de la fonction du salarié dans la base de
     * donnée
     * @param etat l'identifiant de l'état du salarié dans la base de donnée
     * @param cin la CIN (Numéro de la carte d'identité Nationale) du salarié
     * @param nom le nom du salarié
     * @param prenom le prénom du salarié
     * @param cnss
     * @param codeChantier
     * @param matriculeNova
     *
     * @return nombre des salariés
     */
    public Long nombreAllSalaries(String matricule,
            Integer fonction, Integer etat, String cin, String nom, String prenom, String cnss, String codeChantier, String matriculeNova) {
        //logger.debug("Nombre des salaries");
        //System.out.println("nombre ->count All Salaries: ");
        Session session = sessionFactory.getCurrentSession();
        Long nbSalaries = null;
        String req = "";
        String recherche = "";
        String affChantier = "";
        if (!"".equals(matricule) && matricule != null) {
            recherche = recherche + " AND s.MATRICULE='" + matricule + "'";
        }
        if (!"".equals(matriculeNova) && matriculeNova != null) {
            recherche = recherche + " AND UPPER(s.MATRICULEDIVALTO)='" + matriculeNova.toUpperCase().trim() + "'";
        }
        if (fonction != null && fonction != 0) {
            //System.out.println(" @@@@@@ ---- fonction : " + fonction);
            recherche = recherche + " AND s.fonction_PPTEMP_ID=" + fonction + "";
        }
        if (etat != null && etat != 0) {
            //System.out.println(" @@@@@@ ---- etat : " + etat);
            recherche = recherche + " AND s.etat_ID=" + etat + "";
        }
        if (!"".equals(cin) && cin != null) {
            //System.out.println(" @@@@@@ ---- cin : " + cin);
            recherche = recherche + " AND s.CIN ='" + cin + "'";
        }
        if (!"".equals(nom) && nom != null) {
            //System.out.println(" @@@@@@ ---- nom: " + nom);
            recherche = recherche + " AND s.NOM ='" + nom + "'";
        }
        if (!"".equals(prenom) && prenom != null) {
            //System.out.println(" @@@@@@ ---- prenom : " + prenom);
            recherche = recherche + " AND s.PRENOM ='" + prenom + "'";
        }
        if (!"".equals(cnss) && cnss != null) {
            //System.out.println(" @@@@@@ ---- cin : " + cin);
            recherche = recherche + " AND s.CNSS ='" + cnss + "'";
        }
        if (!"".equals(codeChantier)) {
            affChantier = " inner join CHANTIER_SALARIE as c on s.ID=c.SALARIE_ID ";
            recherche = recherche + " AND c.CHANTIER_ID =" + codeChantier + "";
        }
        int tab[] = Constante.Type_Fonction_Pointage_Upsit;
        String s = "";
        for (int i = 0; i < tab.length; i++) {
            s += tab[i] + ",";
        }
        s = s.substring(0, s.length() - 1);
        req = "SELECT count(*) FROM SALARIE as s " + affChantier
                + " inner join ETAT as e on s.etat_ID=e.ID "
                + " inner join PPTEMP f on s.fonction_PPTEMP_ID=f.PPTEMP_ID where f.UP_FONCT_QUAINZ in (" + s + ") " + recherche;
        Query query = session.createSQLQuery(req);
        //System.out.println(" @@@@@@ ---- requete : " + req);
        Integer nb = (Integer) query.uniqueResult();
        //System.out.println("nombre salariés: " + nb.longValue());
        nbSalaries = (Long) nb.longValue();
        return nbSalaries;
    }

    /**
     * Récupérer la liste des salariés selon les critères de recherche passés en
     * paramètres
     *
     * @param start pour la pagination, c'est un nombre entier qui représente
     * l'index du premier élément à afficher
     * @param limit pour la pagination, c'est un nombre entier qui représente
     * l'index du dernier élément à afficher
     * @param matricule matricule du salarié. si renseigné le resultat sera un
     * seul élément.
     * @param statut statut du salarié dans la société (exemple: CADRE,
     * OUVRIER...etc)
     * @param fonction fonction du salarié (exemple: CHEF DE CHANTIER,
     * BOISEUR...etc)
     * @param etat état du salarié (exemple: Actif, Blacklist...etc)
     * @param cin la CIN (numéro de la Carte d'identité Nationale) du salarié
     * @param nom nom du salarié
     * @param prenom prénom du salarié
     * @param cnss
     * @param codeChantier
     * @param matriculeNova
     *
     * @return la liste des Salariés
     */
    public List<Salarie> listeSalaries(int start, int limit, String matricule, Integer statut,
            Integer fonction, Integer etat, String cin, String nom, String prenom, String cnss, String codeChantier, String matriculeNova) {
        //logger.debug("liste des salaries");

        //System.out.println("liste 1 : ");

        Session session = sessionFactory.getCurrentSession();
        String req;

        String recherche = "";
        String affChantier = "";
        if (!"".equals(matricule)) {
            recherche = recherche + " AND s.MATRICULE='" + matricule + "'";
        }
        if (!"".equals(matriculeNova)) {
            recherche = recherche + " AND UPPER(s.MATRICULEDIVALTO)='" + matriculeNova.toUpperCase().trim() + "'";
        }
        if (fonction != null && fonction != 0) {
            recherche = recherche + " AND s.fonction_PPTEMP_ID='" + fonction + "'";
        }
        if (etat != null && etat != 0) {
            recherche = recherche + " AND s.etat_ID=" + etat + "";
        }
        if (!"".equals(cin)) {
            recherche = recherche + " AND s.CIN ='" + cin + "'";
        }
        if (!"".equals(nom)) {
            recherche = recherche + " AND s.NOM ='" + nom + "'";
        }
        if (!"".equals(prenom)) {
            recherche = recherche + " AND s.PRENOM ='" + prenom + "'";
        }
        if (!"".equals(cnss)) {
            recherche = recherche + " AND s.CNSS ='" + cnss + "'";
        }
        if (!"".equals(codeChantier)) {
            affChantier = " inner join CHANTIER_SALARIE as c on s.ID=c.SALARIE_ID ";
            recherche = recherche + " AND c.CHANTIER_ID ='" + codeChantier + "'";
        }
        //recherche.replaceFirst("AND", "WHERE");

        req = "SELECT s.ID,s.MATRICULE,s.NOM,s.PRENOM,s.fonction_PPTEMP_ID,s.etat_ID,s.type_ID FROM SALARIE as s " + affChantier
                + " inner join ETAT as e on s.etat_ID=e.ID "
                + " inner join PPTEMP f on s.fonction_PPTEMP_ID=f.PPTEMP_ID  where s.Dtype='Salarie' " + recherche;
        Query query = session.createSQLQuery(req);
//		query.setParameter(0, codeChantier, Hibernate.STRING);
        query.setFirstResult(start);
        query.setMaxResults(limit);
        List<Integer> liste = query.list();
        List<Salarie> listeSalaries = new ArrayList<>();
        for (int i = 0; i < liste.size(); i++) {

            Salarie salarie = new Salarie();
            Object[] o = (Object[]) query.list().get(i);

            salarie.setId((Integer) o[0]);
            salarie.setMatricule((String) o[1]);
            salarie.setNom((String) o[2]);
            salarie.setPrenom((String) o[3]);
            Etat etatSalarie = new Etat();
            etatSalarie = parametrageService.getEtat((Integer) o[5]);
            salarie.setEtat(etatSalarie);
            Fonction fonctionSalarie = new Fonction();
            fonctionSalarie = parametrageService.getFonction((Integer) o[4]);
            salarie.setFonction(fonctionSalarie);
            Type typeSalarie = new Type();
            typeSalarie = parametrageService.getType((Integer) o[6]);
            salarie.setType(typeSalarie);

//			salarie=getSalarie(liste.get(i));
            listeSalaries.add(salarie);

        }
        //System.out.println("nombre salaries du chantier: " + listeSalaries.size());
        return listeSalaries;
//	}

    }

    /**
     * Récupérer la liste des salariés y inclus les mensuels type quinzainier
     * selon les critères de recherche passés en paramètres
     *
     * @param start pour la pagination, c'est un nombre entier qui représente
     * l'index du premier élément à afficher
     * @param limit pour la pagination, c'est un nombre entier qui représente
     * l'index du dernier élément à afficher
     * @param matricule matricule du salarié. si renseigné le resultat sera un
     * seul élément.
     * @param fonction fonction du salarié (exemple: CHEF DE CHANTIER,
     * BOISEUR...etc)
     * @param etat état du salarié (exemple: Actif, Blacklist...etc)
     * @param cin la CIN (numéro de la Carte d'identité Nationale) du salarié
     * @param nom nom du salarié
     * @param prenom prénom du salarié
     * @param cnss
     * @param codeChantier
     * @param matriculeNova
     *
     * @return la liste des Salariés
     */
    public List<Salarie> listeAllSalaries(int start, int limit, String matricule,
            Integer fonction, Integer etat, String cin, String nom, String prenom, String cnss, String codeChantier, String matriculeNova) {
       

        Session session = sessionFactory.getCurrentSession();
        String req;

        String recherche = "";
        String affChantier = "";
        if (!"".equals(matricule)) {
            recherche = recherche + " AND s.MATRICULE='" + matricule + "'";
        }
        if (!"".equals(matriculeNova)) {
            recherche = recherche + " AND UPPER(s.MATRICULEDIVALTO)='" + matriculeNova.toUpperCase().trim() + "'";
        }
        if (fonction != null && fonction != 0) {
            recherche = recherche + " AND s.fonction_PPTEMP_ID=" + fonction + "";
        }
        if (etat != null && etat != 0) {
            recherche = recherche + " AND s.etat_ID=" + etat + "";
        }
        if (!"".equals(cin)) {
            recherche = recherche + " AND s.CIN ='" + cin + "'";
        }
        if (!"".equals(nom)) {
            recherche = recherche + " AND s.NOM ='" + nom + "'";
        }
        if (!"".equals(prenom)) {
            recherche = recherche + " AND s.PRENOM ='" + prenom + "'";
        }
        if (!"".equals(cnss)) {
            recherche = recherche + " AND s.CNSS ='" + cnss + "'";
        }
        if (!"".equals(codeChantier)) {
            affChantier = " inner join CHANTIER_SALARIE as c on s.ID=c.SALARIE_ID ";
            recherche = recherche + " AND c.CHANTIER_ID =" + codeChantier + "";
        }
        int tab[] = Constante.Type_Fonction_Pointage_Upsit;
        String s = "";
        for (int i = 0; i < tab.length; i++) {
            s += tab[i] + ",";
        }
        s = s.substring(0, s.length() - 1);
        req = "SELECT s.ID,s.MATRICULE,s.NOM,s.PRENOM,s.fonction_PPTEMP_ID,s.etat_ID,s.type_ID FROM SALARIE as s " + affChantier
                + " inner join ETAT as e on s.etat_ID=e.ID "
                + " inner join PPTEMP f on s.fonction_PPTEMP_ID=f.PPTEMP_ID  where f.UP_FONCT_QUAINZ in (" + s + ") " + recherche;
        Query query = session.createSQLQuery(req);
        // query.setFirstResult(start);
        // query.setMaxResults(limit);
        List<Integer> liste = query.list();
        List<Salarie> listeSalaries = new ArrayList<>();
        for (int i = 0; i < liste.size(); i++) {
            Salarie salarie = new Salarie();
            Object[] o = (Object[]) query.list().get(i);
            salarie.setId((Integer) o[0]);
            salarie.setMatricule((String) o[1]);
            salarie.setNom((String) o[2]);
            salarie.setPrenom((String) o[3]);
            Etat etatSalarie = parametrageService.getEtat((Integer) o[5]);
            salarie.setEtat(etatSalarie);
            Fonction fonctionSalarie = parametrageService.getFonction((Integer) o[4]);
            salarie.setFonction(fonctionSalarie);
            Type typeSalarie = parametrageService.getType((Integer) o[6]);
            salarie.setType(typeSalarie);
            listeSalaries.add(salarie);

        }
        //System.out.println("nombre AllSalaries du chantier: " + listeSalaries.size());
        return listeSalaries;
//	}

    }

    /**
     * Récupérer un salarié via son CIN
     *
     * @param cin la CIN (numéro de la Carte d'identité Nationale) du salarié
     *
     * @return Salarié
     */
    public Salarie getSalarieByCin(String cin) {

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM Salarie WHERE cin=?");
        query.setParameter(0, cin, StandardBasicTypes.STRING);
        Salarie salarie = (Salarie) query.uniqueResult();
        return salarie;
    }

    /**
     * vérifier si le numéro de CNSS passé en paramètre existe déjà
     *
     * @param cnss
     * @param numéro de la CNSS du salarié
     *
     * @return True si le le numéro existe sinon False
     */
    public String checkCnss(String cnss, String salarie) {

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("SELECT matricule  FROM Salarie WHERE cnss=? AND Matricule!=?");
        query.setParameter(0, cnss, StandardBasicTypes.STRING);
        query.setParameter(1, salarie, StandardBasicTypes.STRING);
        if ((query.list().size()) > 0) {

            return query.list().get(0).toString();
        } else {
            return "";
        }
    }

    public String checkCnss(String cnss) {

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("SELECT matricule  FROM Salarie WHERE cnss=? ");
        query.setParameter(0, cnss, StandardBasicTypes.STRING);

        if ((query.list().size()) > 0) {

            return query.list().get(0).toString();
        } else {
            return "";
        }
    }

    /**
     * vérifier si le numéro de CIN passé en paramètre existe déjà
     *
     * @param numéro de la CIN du salarié
     *
     * @return True si le le numéro existe sinon False
     */
    public String checkCin(String cin) {

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("SELECT id  FROM Salarie WHERE cin=?");
        query.setParameter(0, cin.trim(), StandardBasicTypes.STRING);

        if ((query.list().size()) > 0) {

            return query.list().get(0).toString();
        } else {
            return "";
        }
    }

    /**
     * vérifier si le numéro de RIB passé en paramètre existe déjà
     *
     * @param numéro de RIB du salarié
     *
     * @return True si le le numéro existe sinon False
     */
    public String checkRIB(String rib) {

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("SELECT matricule  FROM Salarie WHERE rib=?");
        query.setParameter(0, rib.trim(), StandardBasicTypes.STRING);

        if ((query.list().size()) > 0) {

            return query.list().get(0).toString();
        } else {
            return "";
        }
    }

    public String checkRIB(String rib, String salarie) {

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("SELECT matricule  FROM Salarie WHERE rib=? AND matricule !=?");
        query.setParameter(0, rib.trim(), StandardBasicTypes.STRING);
        query.setParameter(1, salarie, StandardBasicTypes.STRING);

        if ((query.list().size()) > 0) {

            return query.list().get(0).toString();
        } else {
            return "";
        }
    }

    /**
     * Récupérer un salarié via son Matricule
     *
     * @param matricule matricule du salarié
     *
     * @return Salarié
     */
    public Salarie getSalarieByMatricule(String matricule) {

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM Salarie WHERE matricule=?");
//        Query query = session.createQuery("SELECT id,matricule,nom,prenom,cin,cnss,etat.id,etat.etat,"
//                + "civilite.id,civilite.civilite,fonction.id,fonction.fonction,fonction.codeDiva,type.id,type.type"
//                + " FROM Salarie WHERE matricule=?  ");

        query.setParameter(0, matricule, StandardBasicTypes.STRING);
//		Salarie salarie=(Salarie)query.list();

        List<Salarie> salaries = query.list();
        Salarie s_toReturn = salaries.get(0);
        if (salaries.size() > 1) {
            for (Salarie s : salaries) {
                System.out.println("SALARIE : " + s.getNom());
                if (s instanceof Mensuel) {
                    System.out.println("MENSUEL");
                    if (((Mensuel) s).getStatut().compareTo("1") == 0) {
                        s_toReturn = s;
                    }
                }
            }
        }
        else{
            s_toReturn = salaries.get(0);
        }

        return s_toReturn;

//        Salarie salarie = new Salarie();
//        for (int i = 0; i < list.size(); i++) {
//
//            Object[] o = (Object[]) list.get(i);
//            salarie.setId((Integer) o[0]);
//            salarie.setMatricule((String) o[1]);
//            salarie.setNom((String) o[2]);
//            salarie.setPrenom((String) o[3]);
//            salarie.setCin((String) o[4]);
//            salarie.setCnss((String) o[5]);
//
//            Etat etat = new Etat();
//            etat.setId((Integer) o[6]);
//            etat.setEtat((String) o[7]);
//            salarie.setEtat(etat);
//
//            Civilite civilite = new Civilite();
//            civilite.setId((Integer) o[8]);
//            civilite.setCivilite((String) o[9]);
//            salarie.setCivilite(civilite);
//
//            Fonction fonction = new Fonction();
//            fonction.setId((Integer) o[10]);
//            fonction.setFonction((String) o[11]);
//            fonction.setCodeDiva((String) o[12]);
//            salarie.setFonction(fonction);
//
//            Type type = new Type();
//            type.setId((Integer) o[13]);
//            type.setType((String) o[14]);
//            salarie.setType(type);
//            DossierSalarie dossierSalarie = new DossierSalarie();
//            dossierSalarie.setId((Integer) o[15]);
//            dossierSalarie.setNodeRefPhoto((String) o[16]);
//            salarie.setDossierSalarie(dossierSalarie);
        // }
    }

    /**
     * Récupérer un salarié via son ID
     *
     * @param idSalarie l'identifiant du salarié dans la base de données
     *
     * @return Salarié
     */
    public Salarie getSalarie(Integer idSalarie) {
        logger.debug("getSalarie");

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
        Query query = session.createQuery("FROM Salarie WHERE id=?");

        query.setParameter(0, idSalarie, StandardBasicTypes.INTEGER);
        Salarie salarie = (Salarie) query.uniqueResult();

        return salarie;

    }

    /**
     * Récupérer un salarié moyenant les valeurs des attributs passées en
     * paramètres. pour avoir de résultat au moins un seul paramètre doit être
     * non vide
     *
     * @param matricule le Matricule du salarié
     * @param cin la CIN (numéro de la carte d'identité Nationale) su salarié
     * @param cnss numéro de CNSS
     * @param nom le no du salarié
     * @param prenom le prénom du salarié
     *
     * @return Salarié
     *
     */
    public Salarie getSalarie(String matricule, String cin, String cnss, String nom, String prenom) {
        logger.debug("getSalarie");

        Session session = sessionFactory.getCurrentSession();

        String req = "FROM Salarie s WHERE";

        if (matricule != null && !"".equals(matricule)) {
            req = req + " s.matricule =:matricule";
        }

        if (cin != null && cin != "") {
            req = req + " AND s.cin =:cin";

        }

        if (cnss != null && cnss != "") {
            req = req + " AND s.cnss =:cnss";

        }

        if (nom != null && nom != "") {
            req = req + " AND s.nom =:nom";
        }

        if (prenom != null && prenom != "") {
            req = req + " AND s.prenom =:prenom";

        }

        req = req.replaceAll("WHERE AND", "WHERE");

        Query query = session.createQuery(req);

        if (matricule != null && matricule != "") {

            query.setParameter("matricule", matricule, StandardBasicTypes.STRING);
        }

        if (cin != null && cin != "") {

            query.setParameter("cin", cin, StandardBasicTypes.STRING);

        }

        if (cnss != null && cnss != "") {

            query.setParameter("cnss", cnss, StandardBasicTypes.STRING);

        }

        if (nom != null && nom != "") {

            query.setParameter("nom", nom, StandardBasicTypes.STRING);

        }

        if (prenom != null && prenom != "") {

            query.setParameter("prenom", prenom, StandardBasicTypes.STRING);

        }

        List<Salarie> salaries = query.list();
        Salarie salarie = salaries.get(0);
        for (Salarie s : salaries) {
            if (!(s instanceof Mensuel)) {
                salarie = s;
            }
        }

        return salarie;

    }

    /**
     * Récupérer la liste des informations d'un salarié
     *
     * @param idSalarie l'identifiant du salarié dans la base de données
     *
     * @return informations d'un salarié sous forme de "List"
     */
    public List<Object> listeInformationsSalarie(Integer idSalarie) {
        logger.debug("Liste des informations d'un salarie");

        List<Object> listeInformationsSalarie = new ArrayList<Object>();

        Salarie salarie = getSalarie(idSalarie);
        if (salarie != null) {

            listeInformationsSalarie.add(salarie.getMatricule());

            if (salarie.getCivilite() != null) {
                listeInformationsSalarie.add(salarie.getCivilite().getCivilite());
            } else {
                listeInformationsSalarie.add("");
            }

            listeInformationsSalarie.add(StringEscapeUtils.escapeJavaScript(salarie.getNom()));
            listeInformationsSalarie.add(StringEscapeUtils.escapeJavaScript(salarie.getPrenom()));
            listeInformationsSalarie.add(salarie.getCin());

            Statut statut = parametrageService.getStatut(salarie.getFonction().getCodeDiva().substring(0, 1));

            listeInformationsSalarie.add(StringEscapeUtils.escapeJavaScript(salarie.getFonction() != null ? statut.getStatut() : ""));
            listeInformationsSalarie.add(StringEscapeUtils.escapeJavaScript(salarie.getFonction() != null ? salarie.getFonction().getFonction() : ""));

            listeInformationsSalarie.add(salarie.getType() != null ? salarie.getType().getType() : "");
            listeInformationsSalarie.add(salarie.getEtat() != null ? salarie.getEtat().getEtat() : "");

            listeInformationsSalarie.add(salarie.getDateNaissance());
            listeInformationsSalarie.add(StringEscapeUtils.escapeJavaScript(salarie.getLieuNaissance()));
            listeInformationsSalarie.add(salarie.getNationalite() != null ? StringEscapeUtils.escapeJavaScript(salarie.getNationalite().getNationalite()) : "");

            listeInformationsSalarie.add(StringEscapeUtils.escapeJavaScript(salarie.getAdresse()));
            listeInformationsSalarie.add(StringEscapeUtils.escapeJavaScript(salarie.getVille()));
            listeInformationsSalarie.add(salarie.getPays() != null ? StringEscapeUtils.escapeJavaScript(salarie.getPays().getPays()) : "");

            listeInformationsSalarie.add(salarie.getTelephone());
            listeInformationsSalarie.add(salarie.getGsm());
            listeInformationsSalarie.add(salarie.getEmail());

            listeInformationsSalarie.add(salarie.getCnss());
            listeInformationsSalarie.add(salarie.getRib());

            listeInformationsSalarie.add(salarie.getPointure() != null ? salarie.getPointure().getPointure() : "");
            listeInformationsSalarie.add(salarie.getTailleHabillement() != null ? salarie.getTailleHabillement().getTailleHabillement() : "");
            listeInformationsSalarie.add(salarie.getCouleurGilet() != null ? salarie.getCouleurGilet().getCouleurGilet() : "");

//            DossierSalarie dossierSalarie = salarie.getDossierSalarie();
//            listeInformationsSalarie.add(dossierSalarie.getId());
            //           listeInformationsSalarie.add(dossierSalarie.getNodeRefPhoto());
            listeInformationsSalarie.add(salarie.getFonction().getId());

            listeInformationsSalarie.add(salarie.getModePaiement() != null ? salarie.getModePaiement().getModePaiement() : "");

            listeInformationsSalarie.add(StringEscapeUtils.escapeJavaScript(salarie.getNomBanque()));
            listeInformationsSalarie.add(StringEscapeUtils.escapeJavaScript(salarie.getNomAgence()));

            //Champs nom, prenom, adresse en arabe
            listeInformationsSalarie.add(StringEscapeUtils.escapeJavaScript(salarie.getNomArabe()));
            listeInformationsSalarie.add(StringEscapeUtils.escapeJavaScript(salarie.getPrenomArabe()));
            listeInformationsSalarie.add(StringEscapeUtils.escapeJavaScript(salarie.getAdresseArabe()));

            listeInformationsSalarie.add(salarie.getCouleurCasque() != null ? salarie.getCouleurCasque().getCouleurCasque() : "");

            listeInformationsSalarie.add(salarie.getSituationFamiliale() != null ? salarie.getSituationFamiliale().getSituationFamiliale() : "");
            listeInformationsSalarie.add(salarie.getNombreEnfants() != null ? salarie.getNombreEnfants() : "");
            listeInformationsSalarie.add(salarie.getMatriculeDivalto() != null ? StringEscapeUtils.escapeJavaScript(salarie.getMatriculeDivalto().toUpperCase()) : "");

            return listeInformationsSalarie;
        } else {
            return null;
        }

    }

    /**
     * Récupérer la liste des informations d'un salarié
     *
     * @param idSalarie l'identifiantdu salarié dans la base de données
     *
     * @return informations d'un salarié sous forme de "Map"
     */
    public Map<String, Object> informationsSalarie(Integer idSalarie) {

        Map<String, Object> informationsSalarie = new HashMap<String, Object>();
        logger.debug("informations d'un salarie");

        Salarie salarie = getSalarie(idSalarie);

        informationsSalarie.put("matriculeSalarie", salarie.getMatricule());
        informationsSalarie.put("civiliteSalarie", salarie.getCivilite() != null ? salarie.getCivilite().getId() : "");
        informationsSalarie.put("nomSalarie", salarie.getNom());
        informationsSalarie.put("nomArabeSalarie", salarie.getNomArabe());
        informationsSalarie.put("prenomSalarie", salarie.getPrenom());
        informationsSalarie.put("prenomArabeSalarie", salarie.getPrenomArabe());
        informationsSalarie.put("cinSalarie", salarie.getCin());

        Statut statut = parametrageService.getStatut(salarie.getFonction().getCodeDiva().substring(0, 1));

        informationsSalarie.put("statutSalarie", salarie.getFonction() != null ? statut.getId() : "");
        informationsSalarie.put("fonctionSalarie", salarie.getFonction() != null ? salarie.getFonction().getId() : "");
        informationsSalarie.put("typeSalarie", salarie.getType() != null ? salarie.getType().getId() : "");
        informationsSalarie.put("etatSalarie", salarie.getEtat() != null ? salarie.getEtat().getId() : "");

        informationsSalarie.put("dateNaissanceSalarie", salarie.getDateNaissance());
        informationsSalarie.put("lieuNaissanceSalarie", salarie.getLieuNaissance());
        informationsSalarie.put("nationaliteSalarie", salarie.getNationalite());

        informationsSalarie.put("adresseSalarie", salarie.getAdresse());
        informationsSalarie.put("adresseArabeSalarie", salarie.getAdresseArabe());
        informationsSalarie.put("villeSalarie", salarie.getVille());
        informationsSalarie.put("paysSalarie", salarie.getPays());
        informationsSalarie.put("telephoneSalarie", salarie.getTelephone());
        informationsSalarie.put("gsmSalarie", salarie.getGsm());
        informationsSalarie.put("emailSalarie", salarie.getEmail());

        informationsSalarie.put("cnssSalarie", salarie.getCnss());
        informationsSalarie.put("ribSalarie", salarie.getRib());
        informationsSalarie.put("nomBanqueSalarie", salarie.getNomBanque());
        informationsSalarie.put("nomAgenceSalarie", salarie.getNomAgence());
        informationsSalarie.put("modePaiementSalarie", salarie.getModePaiement() != null ? salarie.getModePaiement().getId() : "");

        informationsSalarie.put("pointureSalarie", salarie.getPointure() != null ? salarie.getPointure().getId() : "");
        informationsSalarie.put("tailleHabillementSalarie", salarie.getTailleHabillement() != null ? salarie.getTailleHabillement().getId() : "");
        informationsSalarie.put("couleurGiletSalarie", salarie.getCouleurGilet() != null ? salarie.getCouleurGilet().getId() : "");
        informationsSalarie.put("couleurCasqueSalarie", salarie.getCouleurCasque() != null ? salarie.getCouleurCasque().getId() : "");

        return informationsSalarie;

    }

    /**
     * Modifier les informations d'un salarié
     *
     * @param salarie instance de l'Objet Salarie. Avant de le passer au
     * paramètres il faut utiliser les Setters pour insérer les nouvelles
     * informations du salarié
     *
     * @return Renvoie True si la modification est effectué avec succès sinon
     * False
     */
    public boolean modifierInformationsSalarie(Salarie salarie) {

        Session session = sessionFactory.getCurrentSession();

        session.update(salarie);

        return true;
    }

    /**
     * Récupérer les types de document selon le profil (fonction) du salarié
     *
     * @param idFonction l'identifiant du de la fonction dans la base de données
     *
     * @return Types de Document
     */
    public List<TypeDocument> getTypeDocumentByProfil(Integer idFonction) {

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("SELECT f.typesDocument FROM Fonction f WHERE f.id=?");
        query.setParameter(0, idFonction, StandardBasicTypes.INTEGER);

        return query.list();

    }

    /**
     * Récupérer un Type de document par id
     *
     * @param idTypeDocument l'identifiant du Type de document
     *
     * @return Type de document
     */
    public TypeDocument getTypeDocument(Integer idTypeDocument) {

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM TypeDocument td WHERE td.id=?");
        query.setParameter(0, idTypeDocument, StandardBasicTypes.INTEGER);

        return (TypeDocument) query.uniqueResult();

    }

    /**
     * Récupérer la liste des Types de document ajouté pour un salarié
     *
     * @param salarie instance d'un Objet Salarie. le Salarié doit être au
     * préalable récupéré de la base de données puis passé en paramètres
     *
     * @return Renvoie les Types de document
     */
    public List<TypeDocument> listeDocumentsAjoutes(Salarie salarie) {
        List<TypeDocument> l= new ArrayList();
        try {
            
        //		  List<TypeDocument> listeDocumentsAjoutes=new ArrayList<TypeDocument>(salarie.getFonction().getTypesDocument());
        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("SELECT d.typeDocument FROM Document as d WHERE d.salarie=:salarie");
        query.setParameter("salarie", salarie);
        l=query.list();
        } catch (Exception e) {
            System.out.println("erreur de récuperation de la liste car "+e.getMessage());
        }
        return l;
    }

    /**
     * Récupérer la fonction d'un salarié.
     *
     * @param salarie instance d'un Objet Salarie. le Salarié doit être au
     * préalable récupéré de la base de données puis passé en paramètres
     *
     * @return Fonction du salarié
     */
    public Fonction getFonctionSalarie(Salarie salarie) {

        Session session = sessionFactory.getCurrentSession();

        System.out.println("SALARIE ID : " + salarie.getId());

        Query query = session.createQuery("SELECT s.fonction FROM Salarie as s WHERE  s.id=:idSalarie");
        query.setParameter("idSalarie", salarie.getId());

        Fonction fonction = (Fonction) query.uniqueResult();

        return fonction;

    }

    /**
     * Récupérer la liste des types de document non ajoutés pour un salarié
     *
     * @param salarie instance d'un Objet Salarie. le Salarié doit être au
     * préalable récupéré de la base de données puis passé en paramètres
     *
     * @return liste de Types de document
     */
    public List<TypeDocument> listeDocumentNonAjoutes(Salarie salarie) {

        List<TypeDocument> listeDocumentsAjoutes = new ArrayList<TypeDocument>();
        List<TypeDocument> listeTypeDocumentsProfil = new ArrayList<TypeDocument>();
        List<TypeDocument> listeTypeDocumentsNonAjoutes = new ArrayList<TypeDocument>();

        try {
        listeTypeDocumentsProfil = getTypeDocumentByProfil(getFonctionSalarie(salarie).getId());

        listeDocumentsAjoutes = listeDocumentsAjoutes(salarie);

        Session session = sessionFactory.getCurrentSession();
        Query query;
            if (!listeDocumentsAjoutes.isEmpty()) {
                for (int i = 0; i < listeTypeDocumentsProfil.size(); i++) {
                    if (!listeDocumentsAjoutes.contains(listeTypeDocumentsProfil.get(i))) {
                        listeTypeDocumentsNonAjoutes.add(listeTypeDocumentsProfil.get(i));
                    }
                }
                return listeTypeDocumentsNonAjoutes;
                //			  query=session.createQuery("SELECT f.typesDocument FROM Fonction as f WHERE f.id=:idFonction AND f.typesDocument not in(:listeTypes)"); 
                //			  query.setParameter("idFonction", salarie.getFonction().getId());
                //			  query.setParameter("listeTypes", listeDocumentsAjoutes);
            } else {

                return listeTypeDocumentsProfil;
            }
        } catch (Exception e) { 
            System.out.println("Erreur de récuperation de la liste des factures car "+e.getMessage());
            return listeTypeDocumentsProfil;
            
        }
    }

    /**
     * Récupérer les informations d'un salarié pour le pointage
     *
     * @param matricule le Matricule du salarié
     *
     * @return Informations du salarié sous forme de "Map"
     */
    public Map<String, Object> recupererInformationsSalariePourPointage(String matricule) {

        Map<String, Object> informationsSalarie = new HashMap<String, Object>();
        logger.debug("informations d'un salarie via son matricule pour le pointage par salarié");

        Salarie salarie = this.getSalarieByMatricule(matricule);

        if (salarie != null) {

            informationsSalarie.put("idSalarie", salarie.getId());
            informationsSalarie.put("civilite", salarie.getCivilite() != null ? salarie.getCivilite().getCivilite() : "");
            informationsSalarie.put("nom", salarie.getNom());
            informationsSalarie.put("prenom", salarie.getPrenom());
            informationsSalarie.put("cin", salarie.getCin());

            Statut statut = parametrageService.getStatut(salarie.getFonction().getCodeDiva().substring(0, 1));

            informationsSalarie.put("statut", salarie.getFonction() != null ? statut.getStatut() : "");
            informationsSalarie.put("fonction", salarie.getFonction() != null ? salarie.getFonction().getFonction() : "");
            informationsSalarie.put("type", salarie.getType() != null ? salarie.getType().getType() : "");
            informationsSalarie.put("etat", salarie.getEtat() != null ? salarie.getEtat().getEtat() : "");

//            informationsSalarie.put("nodeRefPhoto", salarie.getDossierSalarie().getNodeRefPhoto());
        }

        return informationsSalarie;

    }

    /**
     * Récupérer la situation familiale d'un salarié
     *
     * @param idSalarie l'identifiant du salarié dans la base de données
     * @return situation familiale du salarié
     */
    public SituationFamiliale getSFSalarie(Integer idSalarie) {

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("SELECT s.situationFamiliale FROM Salarie as s WHERE s.id=:idSalarie");
        query.setParameter("idSalarie", idSalarie);

        SituationFamiliale situationFamiliale = (SituationFamiliale) query.uniqueResult();

        return situationFamiliale;
    }

    /**
     * Appel au web service pour créer un nouveau salarié sur DIVALTO avec les
     * informations passées en paramètres
     *
     * @param matricule le matricule du salarié
     * @param civilité le code du civilité stocké sur Divalto (Mr => 01 / Mme =>
     * 02 / Mlle => 03)
     * @param situationFamiliale le code du situation familiale stocké sur
     * Divalto (Célibataire =>1 , Marié => 2, Divorcé=>3, Veuf=>4)
     * @param nom le nom du salarié
     * @param prenom prénom du salarié
     * @param cin le numéro de la CIN du salarié
     * @param cnss le numéro du cnss
     * @param dateNaissance date de naissance format: yyyymmjj
     * @param lieuNaissance le lieu de naissance du salarié
     * @param nationalite le code de la nationalité du salarié (exemple: MA pour
     * Maroc, FR pour France...)
     * @param pays le code du pays du salarié (exemple: MA pour Maroc, FR pour
     * France...)
     * @param adresse l'adresse ddu salarié
     * @param ville la ville du salarié
     * @param telephone le numéro de téléphone fixe du salarié
     * @param gsm le numéro de téléphone portable du salarié
     * @param email l'adresse electronique du salarié
     * @param rib le RIB du salarié (doit contenir 24 chiffres)
     * @param banque le nom de la banque
     * @param statut le code du statut du salarié stocké sur Divalto
     * @param fonction le code de la fonction du salarié stocké sur Divalto
     * @param modePaiement le code du mode de paiement du salarié stocké sur
     * Divalto
     *
     * @return le matricule du salarié en cas de succès, 0 en cas d'echec et -1
     * en cas de existance du salarié
     */
    public Map<String, String> ajouterSalarieWS(String matricule,
            String civilite, String situationFamiliale, String nom, String prenom, String cin, String cnss,
            String dateNaissance, String lieuNaissance, String nationalite, String pays,
            String adresse, String ville, String telephone, String gsm, String email, String rib,
            String banque, String statut, String fonction, String modePaiement,String dossier) {

        Map<String, String> mapAjouterSalarieWS = new HashMap<String, String>();
        String referenceSalarieDiva = "";

        try {
            DivaltoService divaltoService = new DivaltoService();

            DivaltoServiceSoap divaltoServiceSoap = divaltoService.getDivaltoServiceSoap();

            Holder<Integer> hI = new Holder<Integer>();
            Holder<String> hS = new Holder<String>();
            divaltoServiceSoap.webServiceDiva("<ACTION>WSTGCC", "<Task>ajouterSalarie<matricule>" + matricule + "<civilite>" + civilite + "<nom>" + nom + "<prenom>" + prenom + "<cin>" + cin + "<dateNaissance>" + dateNaissance + "<lieuNaissance>" + lieuNaissance + "<nationalite>" + nationalite + "<adresse>" + adresse + "<ville>" + ville + "<pays>" + pays + "<telephone>" + telephone + "<gsm>" + gsm + "<email>" + email + "<situationFamiliale>" + situationFamiliale + "<statut>" + statut + "<fonction>" + fonction + "<type><cnss>" + cnss + "<rib>" + rib + "<banque>" + banque + "<modePaiement>" + modePaiement+ "<dossier>" + dossier, hI, hS);

            String chaine = StringEscapeUtils.unescapeHtml(hS.value).trim();
            System.out.println("requete ajouterSalarie WS: " + "<Task>ajouterSalarie<matricule>" + matricule + "<civilite>" + civilite + "<nom>" + nom + "<prenom>" + prenom + "<cin>" + cin + "<dateNaissance>" + dateNaissance + "<lieuNaissance>" + lieuNaissance + "<nationalite>" + nationalite + "<adresse>" + adresse + "<ville>" + ville + "<pays>" + pays + "<telephone>" + telephone + "<gsm>" + gsm + "<email>" + email + "<situationFamiliale>" + situationFamiliale + "<statut>" + statut + "<fonction>" + fonction + "<type><cnss>" + cnss + "<rib>" + rib + "<banque>" + banque + "<modePaiement>" + modePaiement+ "<dossier>" + dossier);
            System.out.println("reponse ajouterSalarie WS: " + chaine);
            System.out.println("code: " + hI.value);

            if (hI.value == 8212) {
                Thread.sleep(100);
                ajouterSalarieWS(matricule,
                        civilite, situationFamiliale, nom, prenom, cin, cnss,
                        dateNaissance, lieuNaissance, nationalite, pays,
                        adresse, ville, telephone, gsm, email, rib,
                        banque, statut, fonction, modePaiement,dossier);
            }
            if (chaine.startsWith("{") && chaine.endsWith("}")) {

                mapAjouterSalarieWS.put("erreur", "0");
                int ind1 = chaine.indexOf(":") + 2;
                int ind2 = chaine.indexOf("\"", ind1);
                referenceSalarieDiva = chaine.substring(ind1, ind2);

                mapAjouterSalarieWS.put("referenceSalarieDiva", referenceSalarieDiva);
            } else {
                mapAjouterSalarieWS.put("referenceSalarieDiva", "-1");
                mapAjouterSalarieWS.put("erreur", "1");
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            mapAjouterSalarieWS.put("erreur", "0");
            mapAjouterSalarieWS.put("referenceSalarieDiva", "-1");
        }

        return mapAjouterSalarieWS;
    }

    /**
     * Appel au web service pour modifier un salarié sur DIVALTO avec les
     * informations passées en paramètres
     *
     * @param matricule le matricule du salarié
     * @param civilité le code du civilité stocké sur Divalto (Mr => 01 / Mme =>
     * 02 / Mlle => 03)
     * @param situationFamiliale le code du situation familiale stocké sur
     * Divalto (Célibataire =>1 , Marié => 2, Divorcé=>3, Veuf=>4)
     * @param nom le nom du salarié
     * @param prenom prénom du salarié
     * @param cin le numéro de la CIN du salarié
     * @param cnss le numéro du cnss
     * @param dateNaissance date de naissance format: yyyymmjj
     * @param lieuNaissance le lieu de naissance du salarié
     * @param nationalite le code de la nationalité du salarié (exemple: MA pour
     * Maroc, FR pour France...)
     * @param pays le code du pays du salarié (exemple: MA pour Maroc, FR pour
     * France...)
     * @param adresse l'adresse ddu salarié
     * @param ville la ville du salarié
     * @param telephone le numéro de téléphone fixe du salarié
     * @param gsm le numéro de téléphone portable du salarié
     * @param email l'adresse electronique du salarié
     * @param rib le RIB du salarié (doit contenir 24 chiffres)
     * @param banque le nom de la banque
     * @param statut le code du statut du salarié stocké sur Divalto
     * @param fonction le code de la fonction du salarié stocké sur Divalto
     * @param modePaiement le code du mode de paiement du salarié stocké sur
     * Divalto
     *
     * @return le matricule du salarié en cas de succès, 0 en cas d'echec et -1
     * en cas de inexistance du salarié
     */
    public Map<String, String> modifierSalarieWS(String matricule,
            String civilite, String situationFamiliale, String nom, String prenom, String cin, String cnss,
            String dateNaissance, String lieuNaissance, String nationalite, String pays,
            String adresse, String ville, String telephone, String gsm, String email, String rib,
            String banque, String statut, String fonction, String modePaiement) {

        Map<String, String> mapModifierSalarieWS = new HashMap<String, String>();
        String referenceSalarieDiva = "";

        try {
            DivaltoService divaltoService = new DivaltoService();

            DivaltoServiceSoap divaltoServiceSoap = divaltoService.getDivaltoServiceSoap();

            Holder<Integer> hI = new Holder<Integer>();
            Holder<String> hS = new Holder<String>();

            divaltoServiceSoap.webServiceDiva("<ACTION>WSTGCC", "<Task>modifierSalarie<matricule>" + matricule + "<civilite>" + civilite + "<nom>" + nom + "<prenom>" + prenom + "<cin>" + cin + "<dateNaissance>" + dateNaissance + "<lieuNaissance>" + lieuNaissance + "<nationalite>" + nationalite + "<adresse>" + adresse + "<ville>" + ville + "<pays>" + pays + "<telephone>" + telephone + "<gsm>" + gsm + "<email>" + email + "<situationFamiliale>" + situationFamiliale + "<statut>" + statut + "<fonction>" + fonction + "<type><cnss>" + cnss + "<rib>" + rib + "<banque>" + banque + "<modePaiement>" + modePaiement, hI, hS);

            String chaine = StringEscapeUtils.unescapeHtml(hS.value).trim();
            System.out.println("requete modifierSalarie WS: " + "<Task>modifierSalarie<matricule>" + matricule + "<civilite>" + civilite + "<nom>" + nom + "<prenom>" + prenom + "<cin>" + cin + "<dateNaissance>" + dateNaissance + "<lieuNaissance>" + lieuNaissance + "<nationalite>" + nationalite + "<adresse>" + adresse + "<ville>" + ville + "<pays>" + pays + "<telephone>" + telephone + "<gsm>" + gsm + "<email>" + email + "<situationFamiliale>" + situationFamiliale + "<statut>" + statut + "<fonction>" + fonction + "<type><cnss>" + cnss + "<rib>" + rib + "<banque>" + banque + "<modePaiement>" + modePaiement);
            System.out.println("reponse modifierSalarie WS: " + chaine);

            if (hI.value == 8212) {
                Thread.sleep(100);
                modifierSalarieWS(matricule,
                        civilite, situationFamiliale, nom, prenom, cin, cnss,
                        dateNaissance, lieuNaissance, nationalite, pays,
                        adresse, ville, telephone, gsm, email, rib,
                        banque, statut, fonction, modePaiement);
            }
            if (chaine.startsWith("{") && chaine.endsWith("}")) {
                int ind1 = chaine.indexOf(":") + 2;
                int ind2 = chaine.indexOf("\"", ind1);
                referenceSalarieDiva = chaine.substring(ind1, ind2);

                mapModifierSalarieWS.put("referenceSalarieDiva", referenceSalarieDiva);
            } else {
                mapModifierSalarieWS.put("referenceSalarieDiva", "-1");
                mapModifierSalarieWS.put("msgErreur", chaine);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            mapModifierSalarieWS.put("referenceSalarieDiva", "-1");
            mapModifierSalarieWS.put("msgErreur", e.getMessage());
        }

        return mapModifierSalarieWS;
    }

    /**
     * Appel du web service pour Créer un nouveau enfant d'un salarié sur
     * DIVALTO
     *
     * @param matriculeSalarie le matricule du salarié
     * @param prenomEnfant le prenom de l'enfant
     * @param dateNaissanceEnfant la date de naissance de l'enfant format:
     * yyyymmjj
     *
     * @return la référence de l'enfant en cas de succès, 0 en cas d'echec et -1
     * en cas de inexistance du salarié
     */
    public Map<String, String> ajouterEnfantSalarieWS(String matriculeSalarie,
            String prenomEnfant, String dateNaissanceEnfant) {

        Map<String, String> mapAjouterEnfantSalarieWS = new HashMap<String, String>();
        String referenceEnfantSalarieDiva = "";
        try {
            DivaltoService divaltoService = new DivaltoService();

            DivaltoServiceSoap divaltoServiceSoap = divaltoService.getDivaltoServiceSoap();

            Holder<Integer> hI = new Holder<Integer>();
            Holder<String> hS = new Holder<String>();
            divaltoServiceSoap.webServiceDiva("<ACTION>WSTGCC", "<Task>ajouterEnfantSalarie<matriculeSalarieDivalto>" + matriculeSalarie + "<prenomEnfant>" + prenomEnfant + "<dateNaissanceEnfant>" + dateNaissanceEnfant, hI, hS);
            System.out.println("requete ajouterEnfantSalarie WS: <Task>ajouterEnfantSalarie<matriculeSalarieDivalto>" + matriculeSalarie + "<prenomEnfant>" + prenomEnfant + "<dateNaissanceEnfant>" + dateNaissanceEnfant);
            String chaine = StringEscapeUtils.unescapeHtml(hS.value).trim();
            System.out.println("reponse ajouterEnfantSalarie WS: " + chaine);
            if (hI.value == 8212) {
                Thread.sleep(100);
                ajouterEnfantSalarieWS(matriculeSalarie,
                        prenomEnfant, dateNaissanceEnfant);
            }
            if (chaine.startsWith("{") && chaine.endsWith("}")) {
                int ind1 = chaine.indexOf(":") + 2;
                int ind2 = chaine.indexOf("\"", ind1);
                referenceEnfantSalarieDiva = chaine.substring(ind1, ind2);

                mapAjouterEnfantSalarieWS.put("referenceEnfantSalarieDiva", referenceEnfantSalarieDiva);
            } else {
                mapAjouterEnfantSalarieWS.put("referenceEnfantSalarieDiva", "-1");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            mapAjouterEnfantSalarieWS.put("referenceEnfantSalarieDiva", "-1");
        }
        return mapAjouterEnfantSalarieWS;
    }

    /**
     * Appel du web service pour Modifier les informations d'un enfant d'un
     * salarié sur DIVALTO
     *
     * @param prenomEnfant le prenom de l'enfant
     * @param dateNaissanceEnfant la date de naissance de l'enfant format:
     * yyyymmjj
     *
     * @return la référence de l'enfant en cas de succès, 0 en cas d'echec et -1
     * en cas de inexistance de l'enfant
     */
    public Map<String, String> modifierEnfantSalarieWS(String referenceEnfantDivalto,
            String prenomEnfant, String dateNaissanceEnfant, String modif) {

        Map<String, String> mapModifierEnfantSalarieWS = new HashMap<String, String>();
        String referenceEnfantDiva = "";
        try {
            DivaltoService divaltoService = new DivaltoService();

            DivaltoServiceSoap divaltoServiceSoap = divaltoService.getDivaltoServiceSoap();

            Holder<Integer> hI = new Holder<Integer>();
            Holder<String> hS = new Holder<String>();
            String chaine = "";

            if (modif.equals("p")) {
                divaltoServiceSoap.webServiceDiva("<ACTION>WSTGCC", "<Task>modifierPrenomEnfant<matriculeSalarieDivalto>" + referenceEnfantDivalto + "<prenomEnfant>" + prenomEnfant + "<dateNaissanceEnfant>" + dateNaissanceEnfant, hI, hS);

                chaine = StringEscapeUtils.unescapeHtml(hS.value).trim();
                System.out.println("requete modifierPrenomEnfant WS: <Task>modifierPrenomEnfant<matriculeSalarieDivalto>" + referenceEnfantDivalto + "<prenomEnfant>" + prenomEnfant + "<dateNaissanceEnfant>" + dateNaissanceEnfant);
                System.out.println("reponse modifierPrenomEnfant WS: " + chaine);
            } else if (modif.equals("dn")) {

                divaltoServiceSoap.webServiceDiva("<ACTION>WSTGCC", "<Task>modifierDateNaissanceEnfant<matriculeSalarieDivalto>" + referenceEnfantDivalto + "<prenomEnfant>" + prenomEnfant + "<dateNaissanceEnfant>" + dateNaissanceEnfant, hI, hS);

                chaine = StringEscapeUtils.unescapeHtml(hS.value).trim();
                System.out.println("requete modifierDateNaissanceEnfant WS: <Task>modifierDateNaissanceEnfant<matriculeSalarieDivalto>" + referenceEnfantDivalto + "<prenomEnfant>" + prenomEnfant + "<dateNaissanceEnfant>" + dateNaissanceEnfant);
                System.out.println("reponse modifierDateNaissanceEnfant WS: " + chaine);
            }
            if (hI.value == 8212) {
                Thread.sleep(100);
                modifierEnfantSalarieWS(referenceEnfantDivalto,
                        prenomEnfant, dateNaissanceEnfant, modif);
            }
            if (chaine.startsWith("{") && chaine.endsWith("}")) {
                int ind1 = chaine.indexOf(":") + 2;
                int ind2 = chaine.indexOf("\"", ind1);
                referenceEnfantDiva = chaine.substring(ind1, ind2);

                mapModifierEnfantSalarieWS.put("referenceEnfantDiva", referenceEnfantDiva);
            } else {
                mapModifierEnfantSalarieWS.put("referenceEnfantDiva", "-1");
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            mapModifierEnfantSalarieWS.put("referenceEnfantDiva", "-1");
            return mapModifierEnfantSalarieWS;
        }
        return mapModifierEnfantSalarieWS;
    }

    public Map<String, String> supprimerEnfantSalarieWS(String matriculeSalarie,
            String prenomEnfant, String dateNaissanceEnfant) {

        Map<String, String> mapSupprimerEnfantSalarieWS = new HashMap<String, String>();
        String referenceEnfantSalarieDiva = "";
        try {
            DivaltoService divaltoService = new DivaltoService();

            DivaltoServiceSoap divaltoServiceSoap = divaltoService.getDivaltoServiceSoap();

            Holder<Integer> hI = new Holder<Integer>();
            Holder<String> hS = new Holder<String>();
            divaltoServiceSoap.webServiceDiva("<ACTION>WSTGCC", "<Task>supprimerEnfant<matriculeSalarieDivalto>" + matriculeSalarie + "<prenomEnfant>" + prenomEnfant + "<dateNaissanceEnfant>" + dateNaissanceEnfant, hI, hS);
            System.out.println("requete supprimerEnfantSalarie WS: <Task>supprimerEnfant<matriculeSalarieDivalto>" + matriculeSalarie + "<prenomEnfant>" + prenomEnfant + "<dateNaissanceEnfant>" + dateNaissanceEnfant);
            String chaine = StringEscapeUtils.unescapeHtml(hS.value).trim();
            System.out.println("reponse supprimerEnfantSalarie WS: " + chaine);
            if (chaine.startsWith("{") && chaine.endsWith("}")) {
                int ind1 = chaine.indexOf(":") + 2;
                int ind2 = chaine.indexOf("\"", ind1);
                referenceEnfantSalarieDiva = chaine.substring(ind1, ind2);

                mapSupprimerEnfantSalarieWS.put("referenceEnfantSalarieDiva", referenceEnfantSalarieDiva);
            } else {
                mapSupprimerEnfantSalarieWS.put("referenceEnfantSalarieDiva", "-1");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            mapSupprimerEnfantSalarieWS.put("referenceEnfantSalarieDiva", "-1");
            return mapSupprimerEnfantSalarieWS;
        }
        return mapSupprimerEnfantSalarieWS;
    }

    /**
     * Appel du web service pour créer un contrat d'un salarié sur DIVALTO
     *
     * @param matriculeSalarieDivalto le matricule du salarié
     * @param dateDebutContrat date de début du contrat format yyyymmjj
     * @param dateFinContrat date de fin du contrat format yyyymmjj
     * @param typeContrat le code du type de contrat sur Divalto
     * @param tauxHoraire taux horaire en dirhams par heure (exemple: 13.50)
     *
     * @return la référence du contrat en cas de succès, 0 en cas d'echec et -1
     * en cas de inexistance du salarié
     */
    public Map<String, String> ajouterContratSalarieWS(String matriculeSalarieDivalto,
            String dateDebutContrat, String dateFinContrat,
            String typeContrat, String tauxHoraire) {

        Map<String, String> mapAjouterContratSalarieWS = new HashMap<String, String>();
        String referenceContratDiva = "";

        try {
            DivaltoService divaltoService = new DivaltoService();

            DivaltoServiceSoap divaltoServiceSoap = divaltoService.getDivaltoServiceSoap();

            Holder<Integer> hI = new Holder<Integer>();
            Holder<String> hS = new Holder<String>();
            divaltoServiceSoap.webServiceDiva("<ACTION>WSTGCC", "<Task>ajouterContratSalarie<matriculeSalarieDivalto>" + matriculeSalarieDivalto + "<dateDebutContrat>" + dateDebutContrat + "<dateFinContrat>" + dateFinContrat + "<typeContrat>" + typeContrat + "<tauxHoraire>" + tauxHoraire, hI, hS);

            String chaine = StringEscapeUtils.unescapeHtml(hS.value).trim();
            System.out.println("requete ajouterContrat WS: " + "<Task>ajouterContratSalarie<matriculeSalarieDivalto>" + matriculeSalarieDivalto + "<dateDebutContrat>" + dateDebutContrat + "<dateFinContrat>" + dateFinContrat + "<typeContrat>" + typeContrat + "<tauxHoraire>" + tauxHoraire);
            System.out.println("reponse ajouterContratSalarieWS: " + hS.value);
            if (hI.value == 8212) {
                Thread.sleep(100);
                ajouterContratSalarieWS(matriculeSalarieDivalto,
                        dateDebutContrat, dateFinContrat,
                        typeContrat, tauxHoraire);
            }
            if (chaine.startsWith("{") && chaine.endsWith("}")) {
                int ind1 = chaine.indexOf(":") + 2;
                int ind2 = chaine.indexOf("\"", ind1);
                referenceContratDiva = chaine.substring(ind1, ind2);

                mapAjouterContratSalarieWS.put("referenceContratDiva", referenceContratDiva);
            } else {
                mapAjouterContratSalarieWS.put("referenceContratDiva", "-1");
                mapAjouterContratSalarieWS.put("msgErreur", chaine);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            mapAjouterContratSalarieWS.put("referenceContratDiva", "-1");
            mapAjouterContratSalarieWS.put("msgErreur", e.getMessage());
        }

        return mapAjouterContratSalarieWS;
    }

    /**
     * Appel du web service pour modifier un contrat d'un salarié sur DIVALTO
     *
     * @param matriculeSalarieDivalto le matricule du salarié
     * @param dateDebutContrat date de début du contrat format yyyymmjj
     * @param dateFinContrat date de fin du contrat format yyyymmjj
     * @param codeSortie le code du type de contrat sur Divalto
     *
     * @return la référence du contrat en cas de succès, 0 en cas d'echec et -1
     * en cas de inexistance du contrat
     */
    public Map<String, String> modifierContratSalarieWS(String referenceContratDivalto,
            String dateDebutContrat, String dateFinContrat,
            String codeSortie, String dateSortie) {

        Map<String, String> mapModifierContratSalarieWS = new HashMap<String, String>();
        String referenceContratDiva = "";
        try {
            DivaltoService divaltoService = new DivaltoService();

            DivaltoServiceSoap divaltoServiceSoap = divaltoService.getDivaltoServiceSoap();

            Holder<Integer> hI = new Holder<Integer>();
            Holder<String> hS = new Holder<String>();
            divaltoServiceSoap.webServiceDiva("<ACTION>WSTGCC", "<Task>modifierContratSalarie<matriculeSalarieDivalto>" + referenceContratDivalto + "<dateDebutContrat>" + dateDebutContrat + "<dateFinContrat>" + dateFinContrat + "<codeSortie>" + codeSortie + "<dateSortie>" + dateSortie, hI, hS);

            String chaine = StringEscapeUtils.unescapeHtml(hS.value).trim();
            System.out.println("requete modifierContrat WS: " + "<Task>modifierContratSalarie<matriculeSalarieDivalto>" + referenceContratDivalto + "<dateDebutContrat>" + dateDebutContrat + "<dateFinContrat>" + dateFinContrat + "<codeSortie>" + codeSortie + "<dateSortie>" + dateSortie);
            System.out.println("reponse modifierContratSalarieWS: " + chaine);

//		if(hI.value==8212){
//			Thread.sleep(100);
//			modifierContratSalarieWS( referenceContratDivalto,
//					   dateDebutContrat,  dateFinContrat,
//					   codeSortie,dateSortie);
//		}
            if (chaine.startsWith("{") && chaine.endsWith("}")) {
                int ind1 = chaine.indexOf(":") + 2;
                int ind2 = chaine.indexOf("\"", ind1);
                referenceContratDiva = chaine.substring(ind1, ind2);

                mapModifierContratSalarieWS.put("referenceContratDiva", referenceContratDiva);
            } else {
                mapModifierContratSalarieWS.put("referenceContratDiva", "-1");
                mapModifierContratSalarieWS.put("msgErreur", chaine);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            mapModifierContratSalarieWS.put("referenceContratDiva", "-1");
            mapModifierContratSalarieWS.put("msgErreur", e.getMessage());
        }
        return mapModifierContratSalarieWS;
    }

    /**
     * Appel du web service pour modifier le taux et/ou la fonction (liés au
     * contrat) d'un salarié sur DIVALTO
     *
     * @param matriculeSalarieDivalto le matricule du salarié
     * @param fonction le code du type de contrat sur Divalto
     * @param tauxHoraire taux horaire en dirhams par heure (exemple: 13.50)
     *
     * @return la référence du contrat en cas de succès, 0 en cas d'echec et -1
     * en cas de inexistance du contrat
     */
    public Map<String, String> modifierFonctionTauxHoraireWS(String matriculeSalarieDivalto,
            String fonction, String tauxHoraire) {

        Map<String, String> mapModifierContratSalarieWS = new HashMap<String, String>();
        String referenceContratDiva = "";
        try {
            DivaltoService divaltoService = new DivaltoService();

            DivaltoServiceSoap divaltoServiceSoap = divaltoService.getDivaltoServiceSoap();

            Holder<Integer> hI = new Holder<Integer>();
            Holder<String> hS = new Holder<String>();
            divaltoServiceSoap.webServiceDiva("<ACTION>WSTGCC", "<Task>modifierFonctionTauxHoraire<matriculeSalarieDivalto>" + matriculeSalarieDivalto + "<fonction>" + fonction + "<tauxHoraire>" + tauxHoraire, hI, hS);

            String chaine = StringEscapeUtils.unescapeHtml(hS.value).trim();
            System.out.println("requete modifierFonctionTauxHoraire WS: " + "<Task>modifierFonctionTauxHoraire<matriculeSalarieDivalto>" + matriculeSalarieDivalto + "<fonction>" + fonction + "<tauxHoraire>" + tauxHoraire);
            System.out.println("reponse modifierFonctionTauxHoraire WS: " + chaine);

            if (chaine.startsWith("{") && chaine.endsWith("}")) {
                int ind1 = chaine.indexOf(":") + 2;
                int ind2 = chaine.indexOf("\"", ind1);
                referenceContratDiva = chaine.substring(ind1, ind2);

                mapModifierContratSalarieWS.put("referenceContratDiva", referenceContratDiva);
            } else {
                mapModifierContratSalarieWS.put("referenceContratDiva", "-1");
                mapModifierContratSalarieWS.put("detailMessageErreur", chaine);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            mapModifierContratSalarieWS.put("referenceContratDiva", "-1");
            mapModifierContratSalarieWS.put("detailMessageErreur", e.getMessage());
        }
        return mapModifierContratSalarieWS;
    }

    /**
     * Appel au web service pour modifier le taux et/ou la fonction (liés au
     * contrat) d'un salarié sur DIVALTO en pricisant la date d'effet
     *
     * @param matriculeSalarieDivalto le matricule du salarié
     * @param fonction le code du type de contrat sur Divalto
     * @param tauxHoraire taux horaire en dirhams par heure (exemple: 13.50)
     * @param dateEffet la date d'effet(exemple:20131201)
     *
     * @return la référence du contrat en cas de succès, 0 en cas d'echec et -1
     * en cas de inexistance du contrat
     */
    public Map<String, String> modifierFonctionTauxHoraireWS(String matriculeSalarieDivalto,
            String fonction, String tauxHoraire, String dateEffet) {

        Map<String, String> mapModifierContratSalarieWS = new HashMap<String, String>();
        String referenceContratDiva = "";
        try {
            DivaltoService divaltoService = new DivaltoService();

            DivaltoServiceSoap divaltoServiceSoap = divaltoService.getDivaltoServiceSoap();

            Holder<Integer> hI = new Holder<Integer>();
            Holder<String> hS = new Holder<String>();
            divaltoServiceSoap.webServiceDiva("<ACTION>WSTGCC", "<Task>modifierFonctionTauxHoraire2<matriculeSalarieDivalto>" + matriculeSalarieDivalto + "<fonction>" + fonction + "<tauxHoraire>" + tauxHoraire + "<dateEffet>" + dateEffet, hI, hS);

            String chaine = StringEscapeUtils.unescapeHtml(hS.value).trim();
            System.out.println("requete modifierFonctionTauxHoraire WS: " + "<Task>modifierFonctionTauxHoraire2<matriculeSalarieDivalto>" + matriculeSalarieDivalto + "<fonction>" + fonction + "<tauxHoraire>" + tauxHoraire + "<dateEffet>" + dateEffet);
            System.out.println("reponse modifierFonctionTauxHoraire WS avec dateEffet: " + chaine);

            if (chaine.startsWith("{") && chaine.endsWith("}")) {
                int ind1 = chaine.indexOf(":") + 2;
                int ind2 = chaine.indexOf("\"", ind1);
                referenceContratDiva = chaine.substring(ind1, ind2);

                mapModifierContratSalarieWS.put("referenceContratDiva", referenceContratDiva);
            } else {
                mapModifierContratSalarieWS.put("referenceContratDiva", "-1");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            mapModifierContratSalarieWS.put("referenceContratDiva", "-1");
        }
        return mapModifierContratSalarieWS;
    }

    /**
     * Supprimer un salarie
     *
     * @param salarie instance de l'objet Salarie
     * @return true si la suppression est effectuée avec sussès
     */
    public boolean supprimerSalarie(Salarie salarie) {

        Session session = sessionFactory.getCurrentSession();
        session.delete(salarie);

        return true;
    }

    /**
     * Vérifier si le salarié est actif ou pas
     *
     * @param matriculeSalarie
     * @param matriculeSalarie l'identifiant du salarié dans la base de donnéess
     * @return True si le salarié est actif sinon False
     */
    public boolean isActif(String matriculeSalarie) {

        Integer etatSalarie = getSalarieByMatricule(matriculeSalarie).getEtat().getId();

        if (etatSalarie == 1 || etatSalarie == 5) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Vérifier si le matircule du salarié existe
     *
     * @param salarieId matrricule salarié
     * @return true si le matricule existe sinon false
     */
    public boolean isExist(Integer salarieId) {

        Session session = sessionFactory.getCurrentSession();
        Query query = session.createSQLQuery("Select count(*) from Salarie where id=?");
        query.setParameter(0, salarieId, StandardBasicTypes.INTEGER);

        if ((Integer) query.uniqueResult() > 0) {
            return true;
        }
        return false;

    }

    public Map<String, String[]> reglageParFicher(MultipartFile document, int mois, int annee,
            boolean desaffectationChantier,
            boolean verificationContrat,
            Integer chantierId) {
        logger.debug("Régler les salariés à partir du fichier");

//        Ticket ticket = ((CmaUsernamePasswordAuthenticationToken) SecurityContextHolder
//                .getContext().getAuthentication()).getTicket();
        Map<String, String[]> data = new HashMap<String, String[]>();
        Presence presence = null;
        try {
            String originalFileName = document.getOriginalFilename();
            int indexLastPoint = originalFileName.lastIndexOf(".");
            String extensionFichier = originalFileName.substring(indexLastPoint);
            int key = 0;
            byte[] bytes;
            bytes = document.getBytes();
            InputStream bis;
            bis = new ByteArrayInputStream(bytes);
            Iterator<Row> rowIterator = null;

            if (extensionFichier.equals(".xlsx")) {
                System.out.println("fichier xlsx");
                XSSFWorkbook workbook = new XSSFWorkbook(bis);
                XSSFSheet sheet = workbook.getSheetAt(0);
                rowIterator = sheet.iterator();

                while (rowIterator.hasNext()) {
                    Row row = rowIterator.next();
                    Iterator<Cell> cellIterator = row.cellIterator();
                    if (row.getRowNum() != 0 && matriculeExist(row.getCell(0))) {
                        if (contratService.nombreContratParEtat(Integer.valueOf((String) getValue(row.getCell(0))), 2) == 1) {
                            Contrat contrat = null;
                            contrat = contratService.dernierContratParEtat(Integer.valueOf((String) getValue(row.getCell(0))), 2);
                            //Date=row.getCell(2).getStringCellValue().substring(0, 2)+;
                            contratService.reglerContrat(contrat.getId(), row.getCell(2).getDateCellValue(), null);
//							this.modifierContratSalarieWS(Integer.valueOf((String) getValue(row.getCell(0))), contrat.getDateEmbauche(),
//														  row.getCell(1).getDateCellValue(), (String) getValue(row.getCell(2)), (String) getValue(row.getCell(1)));
                        } else if (contratService.nombreContratParEtat(Integer.valueOf((String) getValue(row.getCell(0))), 2) == 0) {
                            System.out.println("matricule n\'a pas de contrat");
                        } else if (contratService.nombreContratParEtat(Integer.valueOf((String) getValue(row.getCell(0))), 2) > 1) {
                            System.out.println("matricule a plusieurs contrats légalisés");
                        }

                    } else if (row.getRowNum() != 0 && !matriculeExist(row.getCell(0))) {
                        System.out.println("matricule inexistant");
                    }
                }

            } else if (extensionFichier.equals(".xls")) {
                System.out.println("extension fichier: " + originalFileName);
                HSSFWorkbook workbook = new HSSFWorkbook();
                HSSFSheet sheet = workbook.getSheetAt(0);
                rowIterator = sheet.iterator();

            } else {
                System.out.println("extension fichier: " + originalFileName);
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return null;
    }

    public Object getValue(Cell cell) {

        if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            return cell.getNumericCellValue();
        } else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
            return cell.getStringCellValue();
        }

        return null;
    }

    public boolean matriculeExist(Cell cell) {

        if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC && isExist((int) cell.getNumericCellValue())) {
            return true;
        } else if (cell.getCellType() == Cell.CELL_TYPE_STRING
                && StringUtils.isNumeric(cell.getStringCellValue())
                && isExist(Integer.valueOf(cell.getStringCellValue()))) {
            return true;
        }
        return false;
    }

    public Integer changerEtatSalarie(Integer idSalarie, Integer etat) {

        Session session = sessionFactory.getCurrentSession();
        Query query = session.createSQLQuery("UPDATE SALARIE set etat_ID=? where ID=?");
        query.setParameter(0, etat, StandardBasicTypes.INTEGER);
        query.setParameter(1, idSalarie, StandardBasicTypes.INTEGER);
        Integer id = query.executeUpdate();

        return id;
    }

    public Integer calculerAgeSalarie(Integer idSalarie) {

        Session session = sessionFactory.getCurrentSession();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String aujourdhui = sdf.format(new Date());
        Query query = session.createSQLQuery("SELECT DATEDIFF(year,DATENAISSANCE,'" + aujourdhui + "') FROM SALARIE where ID=?");
        query.setParameter(0, idSalarie, StandardBasicTypes.INTEGER);
        Integer id = (Integer) query.uniqueResult();

        return id;
    }
    
    public List<Salarie> salarieByChantier(){
        List<Salarie> l = new ArrayList<Salarie>();
        List<Salarie> lm = new ArrayList<Salarie>();
        try {
            
            Session session = sessionFactory.getCurrentSession(); 
            Query query = session.createQuery(" FROM Salarie s WHERE s.etat.etat in('Actif','Actif provisoire') ");
            Query queryM = session.createQuery(" FROM Mensuel ");
            l= query.list(); 
            lm=queryM.list();
            
            if(lm.size()>0){
                l.addAll(lm);
            }
            
        } catch (Exception e) {
            System.out.println("Erreur de récuperer les salarier du chantier  car "+e.getMessage());
        }
        return l;
    }
}
