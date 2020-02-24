/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.Resource;
import ma.bservices.beans.Article;

import ma.bservices.beans.Chantier;

import ma.bservices.beans.EtatDA;
import ma.bservices.beans.Permission;
import ma.bservices.beans.Utilisateur;
import ma.bservices.mb.services.MbHibernateDaoSupport;
import ma.bservices.services.AdministrationService;
import ma.bservices.tgcc.Entity.DemandeDetail;
import ma.bservices.tgcc.Entity.DemandeEntete;
import ma.bservices.tgcc.Entity.Engin;
import ma.bservices.tgcc.dao.engin.EnginDAOImpl;
import ma.bservices.tgcc.service.Engin.UtilisateurService;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.logging.Log;
import org.hibernate.Criteria;
import org.hibernate.FlushMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Projections;
import org.hibernate.type.DateType;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.StringType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author admin
 */
@Repository("demandeDao")
@Transactional
public class DemandeDaoImp extends MbHibernateDaoSupport  implements DemandeDao,Serializable {

    private static final long serialVersionUID = 1L;

    
    @Autowired
    @Resource(name="sessionFactory")
    private SessionFactory sessionFactory;
        
    @Resource(name = "administrationService")
    private AdministrationService administrationService;
    
    @Autowired
    private UtilisateurService utilisateurService;
        
    @Override
    public DemandeEntete findDemandeEnteteById(long id) {
        DemandeEntete demandeEntete=new DemandeEntete();
        try
        {
                Session session=sessionFactory.openSession();
	        demandeEntete= (DemandeEntete) session.get(DemandeEntete.class, id);
		session.close();
		
                     
        } catch (Exception e) {
            System.out.println("DAO --> Erreur findDemandeEnteteById "+e.getMessage());
        }
        return demandeEntete;  
    }

    @Override
    public List<DemandeEntete> getListDemandeEntete() {
        List<DemandeEntete> l = new ArrayList<DemandeEntete>();
        try {
            
         l = (List<DemandeEntete>) this.getHibernateTemplate().find("SELECT e FROM DemandeEntete e ");
        } catch (Exception e) {
             System.out.println("DAO --> Erreur findDemandeEnteteById "+e.getMessage());
        }
        return l;   
                
    }

    @Override
    public List<Engin> getListEngin() {
       		
        List<Engin> l = new ArrayList<Engin>();
        try { 
         l = (List<Engin>) this.getHibernateTemplate().find("SELECT  e  FROM Engin e ");
        } catch (Exception e) {
            System.out.println("DAO --> Erreur getListEngin "+e.getMessage());
        }
        return l;   
                
    }
    
    @Override
    @Transactional
    public long addDemandeEntete(DemandeEntete demandeEntete) {
		try{
		        Session session=sessionFactory.openSession();
                        session.setFlushMode(FlushMode.AUTO);
			session.getTransaction().begin();
			session.persist(demandeEntete);
			session.getTransaction().commit();
			session.close();
			return demandeEntete.getId();
		}
		catch (Exception e)
		{
			System.out.println("DAO --> Erreur addDemandeEntete "+e.getMessage());                        
			return 0;
		}
    }

    @Override
    public long updateDemandeEntete(DemandeEntete demandeEntete) {
            long id =0;
		try{
                        Session session=sessionFactory.openSession();
		        session.setFlushMode(FlushMode.AUTO);
			session.getTransaction().begin();
			session.update(demandeEntete);
			session.getTransaction().commit();
			session.close();
			id= demandeEntete.getId();
		}
		catch (Exception e)
		{ 
		    System.out.println("DAO --> Erreur updateDemandeEntete "+e.getMessage());        	
                    
		}
                return id;
    }

    @Override
    public long deleteDemandeEntete(DemandeEntete demandeEntete) {

		try{
                Session session=sessionFactory.openSession();
		session.setFlushMode(FlushMode.AUTO);
			session.getTransaction().begin();
			session.delete(demandeEntete);
			session.getTransaction().commit();
			session.close();
			return demandeEntete.getId();
		}
		catch (Exception e)
		{  
                    System.out.println("DAO --> Erreur deleteDemandeEntete "+e.getMessage());   
			return 0;
		} 
    }

    @Override
    public DemandeDetail FindDemandeDetailById(long id) {
        DemandeDetail demandeDetail=new DemandeDetail();
        try{                 
            Session session=sessionFactory.openSession();
            demandeDetail=(DemandeDetail) session.get(DemandeDetail.class,id);
            session.close();
            
            }
	    catch (Exception e)		
            {  
                    System.out.println("DAO --> Erreur FindDemandeDetailById "+e.getMessage());   
		   
            } 
         return demandeDetail;	
    }

    @Override
    public List<DemandeDetail> getListDemnadeDetailbyIdEntete(long id) {   
       List<DemandeDetail> l =new ArrayList<DemandeDetail>();
       try{
          
           l = (List<DemandeDetail>) this.getHibernateTemplate().find("SELECT d FROM DemandeDetail d WHERE d.demandeEntete.id=" + id);
       
       }catch(Exception e){
           System.out.println("DAO --> Erreur getListDemnadeDetailbyIdEntete "+e.getMessage());   
        }
        return l;   
       
    }

    @Override
    public long addDemandeDetail(DemandeDetail demandeDetail) {
  
		try{
                Session session=sessionFactory.openSession();
		session.setFlushMode(FlushMode.AUTO);
			session.getTransaction().begin();
			session.persist(demandeDetail);
			session.getTransaction().commit();
			session.close();
			return demandeDetail.getId();
		}
		catch (Exception e)
		{	
                        System.out.println("DAO --> Erreur addDemandeDetail "+e.getMessage());   
			return 0;
		}
    }

    @Override
    public long updateDemandeDetail(DemandeDetail demandeDetail) {
 
		try{
                        Session session=sessionFactory.openSession();
		        session.setFlushMode(FlushMode.AUTO);
			session.getTransaction().begin();
			session.merge(demandeDetail);
			session.getTransaction().commit();
			session.close();
			return demandeDetail.getId();
		}
		catch (Exception e)
		{
			System.out.println("DAO --> Erreur updateDemandeDetail "+e.getMessage());
			return 0;
		}  
    }

    @Override
    public long deleteDemandeDetail(DemandeDetail demandeDetail) {
                 
		try{
                        Session session=sessionFactory.openSession();
		        session.setFlushMode(FlushMode.AUTO);
			session.getTransaction().begin();
			session.delete(demandeDetail);
			session.getTransaction().commit();
			session.close();
			return demandeDetail.getId();
		}
		catch (Exception e)
		{
			System.out.println("DAO --> Erreur deleteDemandeDetail "+e.getMessage());
			return 0;
		} 
    }
    
    @Override
    public Object nombreDemandesInterne(String numeroDA, String chantier,String atelier, String etat, String demandeur, Date dateAjoutDe, Date dateAjoutA, Date dateLivraisonSouhaiteeDe, Date dateLivraisonSouhaiteeA, boolean userIsAdmin) {

        try{

        Session session = sessionFactory.getCurrentSession();
        String queryRecherche = "";
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Utilisateur User = utilisateurService.getUsersByLogin(auth.getPrincipal().toString());
        
        Permission persmission = administrationService.getPermissionByName("admin");
        Boolean bool = administrationService.hasPermission(User, persmission);
        
        Permission persmission1 = administrationService.getPermissionByName("intervenant");
        Boolean bool1 = administrationService.hasPermission(User, persmission1);
        
        //System.out.println("bool" +bool);
        //System.out.println("bool1" +bool1);

        Integer idUser = User.getId();
        if (!bool) {     
            if (bool1) {  
                queryRecherche = " AND DI.etatDA.etat <> 'Brouillon'";
            }else{
                Query q = session.createSQLQuery("SELECT CE.CHANTIER_ID FROM CHANTIER_UTILISATEUR CE WHERE CE.UTILISATEUR_ID=?");          
                q.setParameter(0, idUser, StandardBasicTypes.INTEGER);       
                String chantiersAffectes = "";        
                List liste = q.list();         
                for (int i = 0; i < liste.size(); i++) {          
                    chantiersAffectes = chantiersAffectes + liste.get(i).toString() + ",";        
                }        
                chantiersAffectes = chantiersAffectes.substring(0, chantiersAffectes.lastIndexOf(','));        
                queryRecherche = " AND DI.chantier.id in (" + chantiersAffectes + ")";  
            }
        }

        if (numeroDA != "") {
            queryRecherche += " AND DI.numDemande='" + numeroDA + "' ";
        }

        if (chantier != "") {
            queryRecherche += " AND DI.chantier.code='" + StringEscapeUtils.escapeSql(chantier) + "' ";
        }
        
                if (atelier != "") {
            queryRecherche += " AND DI.atelier.code='" + StringEscapeUtils.escapeSql(atelier) + "' ";
        }

        if (etat != "") {
            queryRecherche += " AND DI.etatDA.etat='" + StringEscapeUtils.escapeSql(etat) + "' ";
        }

        if (dateAjoutDe != null) {

            if (dateAjoutA == null) {

                queryRecherche += " AND DI.dateAjout=:dateAjoutDe ";
            } else {
                queryRecherche += " AND DI.dateAjout BETWEEN :dateAjoutDe AND :dateAjoutA ";
            }

        }

        if (dateLivraisonSouhaiteeDe != null) {

            if (dateLivraisonSouhaiteeA == null) {

                queryRecherche += " AND DI.dateLivraisonSouhaitee=:dateLivraisonSouhaiteeDe ";

            } else {
                queryRecherche += " AND DI.dateLivraisonSouhaitee BETWEEN :dateLivraisonSouhaiteeDe AND :dateLivraisonSouhaiteeA ";
            }

        }

        queryRecherche = queryRecherche.replaceFirst("AND", "WHERE");
        Query query = session.createQuery("SELECT count(*) FROM DemandeEntete DI " + queryRecherche);

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
		catch (Exception e)
		{
			System.out.println("DAO --> Erreur nombreDemandesInterne "+e.getMessage());
			return 0;
		} 
         
    }
    
    @Override
    public List<DemandeEntete> listeDemandesInterne(String numeroDA, String chantier,String atelier, String etat, String demandeur, Date dateAjoutDe, Date dateAjoutA, Date dateLivraisonSouhaiteeDe, Date dateLivraisonSouhaiteeA, int start, int limit, boolean userIsAdmin) {

        List<DemandeEntete> listeDemandesEntete = new ArrayList<DemandeEntete>();
        try{

        Session session = sessionFactory.getCurrentSession();
        String queryRecherche = "";
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Utilisateur User = utilisateurService.getUsersByLogin(auth.getPrincipal().toString());

        Permission persmission = administrationService.getPermissionByName("admin");
        Boolean bool = administrationService.hasPermission(User, persmission);
        
        Permission persmission1 = administrationService.getPermissionByName("intervenant");
        Boolean bool1 = administrationService.hasPermission(User, persmission1);
        
        //System.out.println("bool" +bool);
        //System.out.println("bool1" +bool1);

        Integer idUser = User.getId();
        if (!bool) {     
            if (bool1) {  
                queryRecherche = " AND DI.etatDA.etat <> 'Brouillon'";
            }else{
                               Query q = session.createSQLQuery("SELECT CE.CHANTIER_ID FROM CHANTIER_UTILISATEUR CE WHERE CE.UTILISATEUR_ID=?");          
                q.setParameter(0, idUser, StandardBasicTypes.INTEGER);       
                String chantiersAffectes = "";        
                List liste = q.list();         
                for (int i = 0; i < liste.size(); i++) {          
                    chantiersAffectes = chantiersAffectes + liste.get(i).toString() + ",";        
                }        
                chantiersAffectes = chantiersAffectes.substring(0, chantiersAffectes.lastIndexOf(','));        
                queryRecherche = " AND DI.chantier.id in (" + chantiersAffectes + ")";  
            }
        }
        if (numeroDA != "") {
            queryRecherche += " AND DI.numDemande='" + numeroDA + "' ";
        }

        if (chantier != "") {
            queryRecherche += " AND DI.chantier.code='" + StringEscapeUtils.escapeSql(chantier) + "' ";
        }
        
                if (atelier != "" && dateAjoutDe != null ) {
            queryRecherche += " AND DI.atelier.code='" + StringEscapeUtils.escapeSql(atelier) + "' ";
        }

        if (etat != "") {
            queryRecherche += " AND DI.etatDA.etat='" + StringEscapeUtils.escapeSql(etat) + "' ";
        }

        if (dateAjoutDe != null) {

            if (dateAjoutA == null) {

                queryRecherche += " AND DI.dateAjout=:dateAjoutDe ";
            } else {
                queryRecherche += " AND DI.dateAjout BETWEEN :dateAjoutDe AND :dateAjoutA ";
            }

        }

        if (dateLivraisonSouhaiteeDe != null) {

            if (dateLivraisonSouhaiteeA == null) {

                queryRecherche += " AND DI.dateLivraisonSouhaitee=:dateLivraisonSouhaiteeDe ";

            } else {
                queryRecherche += " AND DI.dateLivraisonSouhaitee BETWEEN :dateLivraisonSouhaiteeDe AND :dateLivraisonSouhaiteeA ";
            }

        }

        queryRecherche = queryRecherche.replaceFirst("AND", "WHERE");

        Query query = session.createQuery("SELECT id, numDemande, chantier.code,atelier.code, dateAjout, dateLivraisonSouhaitee, demandeur.nom, etatDA.etat FROM DemandeEntete DI" + queryRecherche + " order by dateAjout desc, dateLivraisonSouhaitee desc");

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
        //System.out.println(query.toString());
        List listeObjetsDemandesEntete = query.list();
        //System.out.println("test liste amine :"+listeObjetsDemandesEntete.size());
        

        DemandeEntete objetDemandeEntete = null;
        Chantier objetChantier = null;
        Chantier objetAtelier = null;
        Utilisateur objetUtilisateur = null; //demandeur du demande d'approvisionnement
        EtatDA objetEtatDA = null;

        for (int i = 0; i < listeObjetsDemandesEntete.size(); i++) {

            Object[] o = (Object[]) listeObjetsDemandesEntete.get(i);

            objetDemandeEntete = new DemandeEntete();
            objetDemandeEntete.setId((long) o[0]);
            objetDemandeEntete.setNumDemande((String) o[1]);

            objetChantier = new Chantier();
            objetChantier.setCode((String) o[2]);
            objetDemandeEntete.setChantier(objetChantier);
            
            objetAtelier = new Chantier();
            objetAtelier.setCode((String) o[3]);
            objetDemandeEntete.setAtelier(objetAtelier);

            String chaineDateAjout = ((Date) o[4]).toString();
            chaineDateAjout = chaineDateAjout.substring(0, 4) + "/" + chaineDateAjout.substring(5, 7) + "/" + chaineDateAjout.substring(8);
            objetDemandeEntete.setDateAjout(new Date(chaineDateAjout));

            String chaineDA = chaineDateAjout.substring(8, 10) + "/" + chaineDateAjout.substring(5, 7) + "/" + chaineDateAjout.substring(0, 4);
            objetDemandeEntete.setChaineDateAjout(chaineDA);

            String chaineDateLivraisonSouhaitee = ((Date) o[5]).toString();
            chaineDateLivraisonSouhaitee = chaineDateLivraisonSouhaitee.substring(0, 4) + "/" + chaineDateLivraisonSouhaitee.substring(5, 7) + "/" + chaineDateLivraisonSouhaitee.substring(8);
            objetDemandeEntete.setDateLivraisonSouhaitee(new Date(chaineDateLivraisonSouhaitee));

            String chaineDLS = chaineDateLivraisonSouhaitee.substring(8, 10) + "/" + chaineDateLivraisonSouhaitee.substring(5, 7) + "/" + chaineDateLivraisonSouhaitee.substring(0, 4);
            objetDemandeEntete.setChaineDateLivraisonSouhaitee(chaineDLS);

            objetUtilisateur = new Utilisateur();
            objetUtilisateur.setNom((String) o[6]);
            objetDemandeEntete.setDemandeur(objetUtilisateur);

            objetEtatDA = new EtatDA();
            objetEtatDA.setEtat((String) o[7]);
            objetDemandeEntete.setEtatDA(objetEtatDA);

            listeDemandesEntete.add(objetDemandeEntete);
            //		        	id, numeroDA, chantier.code, dateAjout, dateLivraisonSouhaitee, demandeur.nom, etatDA.etat

        }

        return listeDemandesEntete;
        
              }
		catch (Exception e)
		{
			System.out.println("DAO --> Erreur listeDemandesInterne "+e.getMessage());
			return listeDemandesEntete;
		} 
    }

    @Override
    public DemandeEntete getDemandeEntete(long idDA) {
        DemandeEntete objetDemandeEntete=new DemandeEntete();
       try{

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM DemandeEntete WHERE id=?");
        query.setParameter(0, idDA, StandardBasicTypes.LONG);

        objetDemandeEntete = (DemandeEntete) query.uniqueResult();
        return objetDemandeEntete;
         }
		catch (Exception e)
		{
			System.out.println("DAO --> Erreur getDemandeEntete "+e.getMessage());
			return objetDemandeEntete;
		} 
    }
  
    @Override    
    public Object nombreArticlesDemandeInterne(long idDA) {
        try{

        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("SELECT count(*) FROM DemandeDetail WHERE demandeEntete.id=?");
        query.setParameter(0, idDA, StandardBasicTypes.LONG);

        return query.uniqueResult();
         }
		catch (Exception e)
		{
			System.out.println("DAO --> Erreur getDemandeEntete "+e.getMessage());
			return 0;
		} 
    }
    
    @Override
    public List<DemandeDetail> DemandeDetail(long idDA) {
        List<DemandeDetail> listeDemandeDetail =new ArrayList<DemandeDetail>();
        try{

        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("FROM DemandeDetail WHERE demandeEntete.id=? order by id");
        query.setParameter(0, idDA, StandardBasicTypes.LONG);

         listeDemandeDetail = (List<DemandeDetail>) query.list();

        return listeDemandeDetail;
                 }
		catch (Exception e)
		{
			System.out.println("DAO --> Erreur DemandeDetail "+e.getMessage());
			return listeDemandeDetail;
		} 

    }
    
    @Override
    public DemandeDetail getDemandeDetail(long idArticleDA) {
        DemandeDetail demandeDetail =new  DemandeDetail();
        try{

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM DemandeDetail WHERE id=?");

        query.setParameter(0, idArticleDA, StandardBasicTypes.LONG);
         demandeDetail = (DemandeDetail) query.uniqueResult();

        return demandeDetail;
  
        }
		catch (Exception e)
		{
			System.out.println("DAO --> Erreur getDemandeDetail "+e.getMessage());
			return demandeDetail;
		} 
    }
    
    @Override
    public boolean articleExiste(String codeArticle) {
      
        try {
        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("SELECT count(*) FROM Article WHERE codeArticle LIKE '" + codeArticle + "%'");

        if ((int) (long) query.list().size() > 0) {
            return true;
        }

        return false;
        
         }
		catch (Exception e)
		{
			System.out.println("DAO --> Erreur articleExiste "+e.getMessage());
			return false;
		} 
    }
    
    @Override
    public boolean articleDADejaExiste(String codeArticle, long da) {

        try{
        Session session = sessionFactory.getCurrentSession();

        Query query = session.createSQLQuery("SELECT count(*) FROM DemandeDetail WHERE REF LIKE '" + codeArticle + "%' and Id_Demande=" + da + "");
        //query.setParameter(0, da, Hibernate.INTEGER);
        System.out.println("article deja ajouté: " + (int) (Integer) query.uniqueResult());
        if ((int) (Integer) query.uniqueResult() > 0) {
            return true;
        }

        return false;
        
         }
		catch (Exception e)
		{
			System.out.println("DAO --> Erreur articleDADejaExiste "+e.getMessage());
			return false;
		} 
    }
    
    @Override
     public Object nombreRechercheArticle(String querySearch, String fam1, String fam2, String fam3, String reqDos, String designation) {

        try{

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
 
        }
		catch (Exception e)
		{
			System.out.println("DAO --> Erreur nombreRechercheArticle "+e.getMessage());
			return 0;
                }
    }
    
    @Override
     public Object nombreRechercheEngin(String Ref, String designation) {

        try {

        Session session = sessionFactory.getCurrentSession();
        String ssql = "SELECT count(*) FROM Engin ";
        if (Ref != null && Ref !="" && (designation == null || designation ==""))
        {
            ssql = ssql + "WHERE code LIKE '%"+ Ref + "%'";
        }
        if (designation != null && designation !="" && (Ref == null || Ref ==""))
        {
            ssql = ssql + "WHERE designation LIKE '%"+ designation + "%'";
        }
        if (designation != null && designation !="" && Ref != null && Ref !="")
        {
            ssql = ssql + "WHERE code like '%"+ Ref + "%' AND designation='" + designation + "'";
        }
        //System.out.println(ssql);
        Query query = session.createSQLQuery(ssql);
        return query.uniqueResult();

        }
        catch (Exception e)
		{
			System.out.println("DAO --> Erreur nombreRechercheEngin "+e.getMessage());
			return 0;
                }

    }

    @Override
     public List<Engin> listeRechercheEngin(int start, int limit, String Ref, String designation) {
           List<Engin> l = new ArrayList<Engin>();        
       try{
        Session session = sessionFactory.getCurrentSession();

        String ssql = "";
        if (Ref != null && Ref !="" && (designation == null || designation ==""))
        {
            ssql = ssql + "WHERE code like '%"+  StringEscapeUtils.escapeSql(Ref).toLowerCase()  + "%'";
        }
        if (designation != null && designation !="" && (Ref == null || Ref ==""))
        {
            ssql = ssql + "WHERE lower(designation) like '%"+  StringEscapeUtils.escapeSql(designation).toLowerCase() + "%'";
        }
        if (designation != null && designation !="" && Ref != null && Ref !="")
        {
            ssql = ssql + "WHERE lower(code) like '%"+  StringEscapeUtils.escapeSql(Ref).toLowerCase()  + "%' AND lower(designation) like '" +  StringEscapeUtils.escapeSql(designation).toLowerCase()  + "'";
        }

       
        try { 
         l = (List<Engin>) this.getHibernateTemplate().find("SELECT  e  FROM Engin e "+ ssql +"");
         //System.out.println("listEngin >>>>>>>>>>>>>>>> " + l.size());
        } catch (Exception e) {
            System.out.println("Erreur de récuperation des engins Archivé car : "+e.getMessage());
        }
       
        }
        catch (Exception e)
		{
			System.out.println("DAO --> Erreur listeRechercheEngin "+e.getMessage());
			
                }
        return l;  
    }
     
    @Override
     public List<Article> listeRechercheArticle(int start, int limit, String querySearch, String fam1, String fam2, String fam3, String reqDos, String designation) {
           List<Article> l=new ArrayList<Article>();
        try{

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
        return criteria.list();
        }
          catch (Exception e)
		{
			System.out.println("DAO --> Erreur listeRechercheArticle "+e.getMessage());
			return l ;
                }

    }

 

    public AdministrationService getAdministrationService() {
        return administrationService;
    }

    public UtilisateurService getUtilisateurService() {
        return utilisateurService;
    }


    public Log getLogger() {
        return logger;
    }



    public void setAdministrationService(AdministrationService administrationService) {
        this.administrationService = administrationService;
    }

    public void setUtilisateurService(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    @Override
    public int nombreDemandeInternebyDemandeur(int Demandeur,Date dateD) {
        try{
        Session session = sessionFactory.getCurrentSession();

        Query query = session.createSQLQuery("SELECT count(*) FROM DemandeEntete WHERE  id_demandeur=" + Demandeur + " and Year(Date_Demande)=Year(Convert(date,'" + dateD +"')) and Month(Date_Demande)=Month(Convert(date,'" + dateD +"'))");

        return (int) query.uniqueResult();
        }
          catch (Exception e)
		{
			System.out.println("DAO --> Erreur nombreDemandeInternebyDemandeur "+e.getMessage());
			return 0 ;
                }
    }
     
     
     
}
