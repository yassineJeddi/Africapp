/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.dao;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
//import javax.annotation.Resource;
import ma.bservices.mb.services.MbHibernateDaoSupport;
import ma.bservices.tgcc.Entity.Antecedent;
import ma.bservices.tgcc.Entity.AntecedentProfessionnel;
import ma.bservices.tgcc.Entity.DocumentDossierMedical;
import ma.bservices.tgcc.Entity.DossierMedical;
import org.hibernate.FlushMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Query; 
import org.hibernate.SessionFactory;
import org.hibernate.Transaction; 
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author BARAKA
 */
@Repository("dossierMedicalDao")
@Transactional
public class DossierMedicalDaoImpl extends MbHibernateDaoSupport implements DossierMedicalDao, Serializable  {
    
    @Resource(name = "sessionFactory")
    private SessionFactory sessionFactory;
    
    
    
    @Override
    public Long addDossierMedical(DossierMedical dossierMedical) {
        Transaction tx = null;
        try {
            Session session = getSessionFactory().openSession();
            session.setFlushMode(FlushMode.AUTO);
            tx = session.beginTransaction();
            session.persist(dossierMedical);
            tx.commit();
            session.close();
        } catch (DataAccessException exp) {
            System.err.print("Exception " + exp.getMessage()); 
            return null;
        }
        return dossierMedical.getId();
    }

    @Override
    public Long updateDossierMedical(DossierMedical dossierMedical) {
        Transaction tx = null;
        try {
            Session session = getSessionFactory().openSession();
            session.setFlushMode(FlushMode.AUTO);
            tx = session.beginTransaction();
            session.update(dossierMedical);
            tx.commit();
            session.close();
        } catch (DataAccessException exp) {
            System.err.print("Exception " + exp.getMessage()); 
            return null;
        }
        return dossierMedical.getId();
    }

    @Override
    public boolean deleteDossierMedical(DossierMedical dossierMedical) {
        Transaction tx = null;
        try {
            Session session = getSessionFactory().openSession();
            session.setFlushMode(FlushMode.AUTO);
            tx = session.beginTransaction();
            session.delete(dossierMedical);
            tx.commit();
            session.close();
        } catch (DataAccessException exp) {
            System.err.print("Exception " + exp.getMessage()); 
            return false;
        }
        return true;
    }

    @Override
    public List<DossierMedical> findAllDossierMedical() {
        try {
            List l = this.getHibernateTemplate().find("SELECT s FROM DossierMedical s ");
            if (l.size() > 0) {
                return l;
        } 
        } catch (DataAccessException e) {
        System.err.print("Exception " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<DossierMedical> findDossierMedicalChantiers(String code) {
        List<DossierMedical> l = new ArrayList<>();  
        try {   
             String req=" FROM DossierMedical s where s.salarie.id in (SELECT SALARIE_ID FROM CHANTIER_SALARIE WHERE CHANTIER_ID in ("+code+"))";
             l = (List<DossierMedical>) this.getHibernateTemplate().find(req);   

        } catch (DataAccessException e) {
            System.err.println("Erreur de récupération des salarier par Listechantiers "+e.getMessage());
        }   
        return l;
    }

    @Override
    public List<DossierMedical> findDossierMedicalByChantier(int idChantier) {
        List<DossierMedical> l = new ArrayList<>();  
        try {   
             String req=" FROM DossierMedical s where s.salarie.id in (SELECT SALARIE_ID FROM CHANTIER_SALARIE WHERE CHANTIER_ID ="+idChantier+")";
             l = (List<DossierMedical>) this.getHibernateTemplate().find(req);  

        } catch (DataAccessException e) {
            System.err.println("Erreur de récupération des salarier par chantier "+e.getMessage());
        }   
        return l;
    }

    @Override
    public List<DossierMedical> findAllDossierMedicalByStatut(Integer Actif) {
        List <DossierMedical>  l = new ArrayList<DossierMedical> ();
       try {
            if(Actif!=null){
                if(Actif==1)      l = (List<DossierMedical>) this.getHibernateTemplate().find("SELECT s FROM DossierMedical s where s.actif = 1");
                else if(Actif==0) l = (List<DossierMedical>) this.getHibernateTemplate().find("SELECT s FROM DossierMedical s where s.actif <> 1");
            }else{
                l = (List<DossierMedical>) this.getHibernateTemplate().find("SELECT s FROM DossierMedical s ");
            }
        } catch (DataAccessException e) {
        System.err.print("Exception " + e.getMessage());
        }
        return l;
    }

    @Override
    public List<DossierMedical> findDossierMedicalChantiersAndStatut(String code, Integer Actif) {  
        try {
            Session session = sessionFactory.getCurrentSession();
            Query query = session.createSQLQuery("select [id],[ACCIDNT],[ACTIF],[ACT_PROF],[AUTRES],[BCG],[CONJOIN],[CREEPAR],[DATECREATION],[DATEMODIFICATION],[DOCTEUR],[ENFANTS],[ETAT],[FORMATION_PROF],[FRERES],[INTOXICATION],[IPP],[DERN_VISITE],[MALADIE_PERS],[MERE],[MODIFIEPAR],[PERE],[POST_TRA],[RISQUE],[SERVICE],[SOEURS],[TETANOS],[salarie_ID],[DATE_EMBAUCHE],[PROCHAINE_VISITE],[TITULARISATION] from View_DOSSIER_MEDICAL");

            List <DossierMedical> dossierMedicaux = new ArrayList<>();
            for (int i = 0; i < query.list().size(); i++) { 
                dossierMedicaux.add((DossierMedical) query.list().get(i));
            }

            return dossierMedicaux; 
        } catch (Exception e) {
            System.out.println("findDossierMedicalChantiersAndStatut: " +e.getMessage());
            return null;
        }
    }

    @Override 
    public List<DossierMedical> findDossierMedicalByChantierAndStatut(int idChantier, Integer Actif) {
        /*System.out.println("----DAO-----idChantier--Actif--->" + idChantier+" Actif "+Actif);   
        try {
            if(idChantier!=0){ 
                String req = " FROM DossierMedical s where s.actif=1 AND s.salarie.id in (SELECT SALARIE_ID FROM CHANTIER_SALARIE where CHANTIER_ID= "+idChantier+")";
                List<DossierMedical> l = (List<DossierMedical>) this.getHibernateTemplate().find(req);   
                return l;                 
            }
            else{ 
                String req=" FROM DossierMedical s where s.actif=0";
                List<DossierMedical> l = (List<DossierMedical>) this.getHibernateTemplate().find(req);    
                return l;
            }
        } catch (HibernateException e) {
            System.out.println("Exception: " +e.getMessage());
        }
      
        return null;*/
        List<DossierMedical> listDossierMedical = new ArrayList<DossierMedical>();         
        try {
            if(idChantier!=0){  
                String temps = new SimpleDateFormat("yyyyMMdd").format(new Date()); 
                String req = "";
                if(idChantier==136){
                    req = " FROM DossierMedical s where s.actif=1 AND s.salarie.id in (SELECT SALARIE_ID FROM CHANTIER_SALARIE where CHANTIER_ID= "+idChantier+")";
                }
                else {
                    req = " FROM DossierMedical s where s.salarie.id in (SELECT p.salarie.id FROM Presence p WHERE p.date='"+temps+"' AND chantier.id= "+idChantier+")";
                }
                listDossierMedical = (List<DossierMedical>) this.getHibernateTemplate().find(req);              
            }
            else{ 
                String req=" FROM DossierMedical s where s.actif=0";
                listDossierMedical = (List<DossierMedical>) this.getHibernateTemplate().find(req);    
            }
        } catch (HibernateException e) {
            System.out.println("Exception: " +e.getMessage());
        }
      
        return listDossierMedical;
        }

    @Override
    public Integer insertDocument(DocumentDossierMedical document) {
        Transaction tx = null;
        try {
            Session session = getSessionFactory().openSession();
            session.setFlushMode(FlushMode.AUTO);
            tx = session.beginTransaction();
            session.persist(document);
            tx.commit();
            session.close();
        } catch (DataAccessException exp) {
            System.err.print("Exception " + exp.getMessage()); 
            return null;
        }
        return document.getId();
    }
    @Override
    public List<DocumentDossierMedical> findDocumentDossierMedicalByDossier(DossierMedical dossierMedical) {  
        List<DocumentDossierMedical> l = new ArrayList<>();  
        try {   
             String req=" FROM DocumentDossierMedical s where s.dossierMedical.id="+dossierMedical.getId()+")";
             l = (List<DocumentDossierMedical>) this.getHibernateTemplate().find(req);  

        } catch (DataAccessException e) {
            System.err.println("Erreur de récupération des salarier par chantier "+e.getMessage());
        }   
        return l;
    }
    @Override
    public void miseAjourSalarie(int id) { 
        try {
            Session s = sessionFactory.openSession();
//            s.setFlushMode(FlushMode.AUTO);
//            s.getTransaction().begin();
            Query _q=sessionFactory.getCurrentSession().createSQLQuery("{CALL PROC_DOSSIER_MEDICAL("+id+")}"); 
            _q.executeUpdate();
            System.out.println("FROM DAO :::::::::::::::: : "+id);
//            s.getTransaction().commit();
            s.close();  
        } catch (HibernateException e) {
            System.err.println("HibernateException ::>  "+e.getMessage()); 
        }

    }
    
    @Override
    public List<AntecedentProfessionnel> findAntecedentsProfessionels(DossierMedical dossierMedical) {  
        List<AntecedentProfessionnel> l = new ArrayList<>();  
        try {   
             String req=" FROM AntecedentProfessionnel s where s.dossierMedical.id="+dossierMedical.getId()+")";
             l = (List<AntecedentProfessionnel>) this.getHibernateTemplate().find(req);  

        } catch (DataAccessException e) {
            System.err.println("Erreur de récupération AntecedentProfessionnels "+e.getMessage());
        }   
        return l;
    }
    
    @Override
    public List<Antecedent> findAntecedentByType(String type) {  
        List<Antecedent> l = new ArrayList<>();  
        try {   
             String req=" FROM Antecedent s where s.type ='"+type+"')";
             l = (List<Antecedent>) this.getHibernateTemplate().find(req);  

        } catch (DataAccessException e) {
            System.err.println("Erreur de récupération des Antecedents "+e.getMessage());
        }   
        return l;
    }
    @Override
    public Long addAntecedentProfessionnel(AntecedentProfessionnel antecedentProfessionnel) {
        Transaction tx = null;
        try {
            Session session = getSessionFactory().openSession();
            session.setFlushMode(FlushMode.AUTO);
            tx = session.beginTransaction();
            session.persist(antecedentProfessionnel);
            tx.commit();
            session.close();
        } catch (DataAccessException exp) {
            System.err.print("Exception " + exp.getMessage()); 
            return null;
        }
        return antecedentProfessionnel.getId();
    }
    
    @Override
    public Long updateAntecedentProfessionnel(AntecedentProfessionnel antecedentProfessionnel) {
        Transaction tx = null;
        try {
            Session session = getSessionFactory().openSession();
            session.setFlushMode(FlushMode.AUTO);
            tx = session.beginTransaction();
            session.update(antecedentProfessionnel);
            tx.commit();
            session.close();
        } catch (DataAccessException exp) {
            System.err.print("Exception " + exp.getMessage()); 
            return null;
        }
        return antecedentProfessionnel.getId();
    }

    @Override
    public Long addAntecedent(Antecedent antecedent) {
        Transaction tx = null;
        try {
            Session session = getSessionFactory().openSession();
            session.setFlushMode(FlushMode.AUTO);
            tx = session.beginTransaction();
            session.persist(antecedent);
            tx.commit();
            session.close();
        } catch (DataAccessException exp) {
            System.err.print("Exception " + exp.getMessage()); 
            return null;
        }
        return antecedent.getId();
    }

    @Override
    public List<DossierMedical> findDosMedByIdsalarie(int id) {
        List<DossierMedical> l = new ArrayList<>();  
        try {   
             String req=" FROM DossierMedical s where s.salarie.id="+id+")";
             l = (List<DossierMedical>) this.getHibernateTemplate().find(req);  

        } catch (DataAccessException e) {
            System.err.println("Erreur de récupération des salarier par chantier "+e.getMessage());
        }   
        return l;
    }

     
}
