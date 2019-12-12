/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.dao.Mensuel;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import ma.bservices.beans.Chantier;
import ma.bservices.beans.Document;
import ma.bservices.beans.HistoriqueVoiture;
import ma.bservices.beans.Presence;
import ma.bservices.beans.Salarie;
import ma.bservices.tgcc.Entity.Voiture;
import ma.bservices.tgcc.utilitaire.MbHibernateDaoSupport;
import org.apache.log4j.Logger;
import org.hibernate.FlushMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author a.wattah
 */
@Repository("voitureDAO")
@Transactional
public class VoitureDAOImpl extends MbHibernateDaoSupport implements VoitureDAO, Serializable {

    protected static Logger logger = Logger.getLogger(VoitureDAOImpl.class); 
    
    @Autowired
    DocumentDAO documentDAO;

    @Override
    public void ajouter_voiture(Voiture v) {

          try { 
            v.setArchiver(Boolean.FALSE);
            v.setAffect(Boolean.FALSE);
            this.getHibernateTemplate().save(v);
            getHibernateTemplate().flush(); 
        } catch (HibernateException e) {
            logger.error("Erreur d'insertion Voiture car "+e.getMessage());
        }
          /*
        try { 
            System.out.println(v.toString());
            Session session = getSessionFactory().openSession();
            session.beginTransaction();
            v.setArchiver(Boolean.FALSE);
            v.setAffect(Boolean.FALSE);
            session.save(v);
            session.getTransaction().commit();
            session.close();
            System.out.println("voiture enregistrer ");
        } catch (HibernateException e) {
            logger.error("Erreur d'insertion Voiture car "+e.getMessage());
        }
        */
    }

    @Override
    public void delete(Voiture v) {
        /*    List<Document> documents = new ArrayList<Document>();
            documents = documentDAO.getDocumentByVoiture(v.getId());
            if (documents.size() > 0) {
                for (Document docTo_Delete : documents) {
                    if (docTo_Delete.getVoiture() != null) {
                        if (docTo_Delete.getVoiture().getId().equals(v.getId())) {
                            docTo_Delete.setVoiture(null);
                            documentDAO.update(docTo_Delete);
                        }
                    }
                }
            }
        */
        try {
            
            System.out.println("============> dao"+v.toString());
            v.setArchiver(Boolean.TRUE);
            this.getHibernateTemplate().saveOrUpdate(v);
            getHibernateTemplate().flush(); 
        } catch (Exception e) {
            logger.error("Erreur de supression de la Voiture car "+e.getMessage());
        }
    }

    @Override
    public Boolean update(Voiture v) {
        Transaction tx = null;
        Session session = getSessionFactory().openSession();
        try {
            session.setFlushMode(FlushMode.AUTO);
            tx = session.beginTransaction();
            session.update(v);
            tx.commit();
            session.close();
            return true;
        } catch (HibernateException e) {
            tx.rollback();
            session.close();
            logger.error("Erreur de Modification de la Voiture car "+e.getMessage());
            return false;
        }
    }

    @Override
    public Voiture getVoitureByID(Long id) {
        Voiture v = new Voiture();
        try {
            v=this.getHibernateTemplate().get(Voiture.class, id);
        } catch (DataAccessException e) {
            logger.error("Erreur de récupération de voiture par id car "+e.getMessage());
        }
        return v;
    }

    @Override
    public Voiture getVoitureByMatricule(String matricule) {
        Voiture v = new Voiture();
            try {
                List l = this.getHibernateTemplate().find(" SELECT v From Voiture "
                        + " where v.matriculevoiture ='"+matricule+"'"
                        + " AND  v.archiver = '" + Boolean.FALSE + "'");
                v=(Voiture) l.get(0);
            } catch (Exception e) {
            logger.error("Erreur de récupération de voiture par matricule car "+e.getMessage());
        }
            return v;
    }

    @Override
    public List<Voiture> findAll() {
        List<Voiture> l = new ArrayList<Voiture>();
        try {
            l = (List<Voiture>) this.getHibernateTemplate().find("From Voiture v "
                    + " where v.archiver = '" + Boolean.FALSE + "' ORDER BY v.affect");
        } catch (Exception e) {
         logger.error("Erreur de récupération liste des voiture car "+e.getMessage());
        }
        return l;
    }

    @Override
    public List<Voiture> getListeVoituresByChantier(Chantier c) {
         List<Voiture> l = new ArrayList<Voiture>();
        try {
            l = (List<Voiture>) this.getHibernateTemplate().find("From Voiture v "
                    + " where v.archiver = '" + Boolean.FALSE + "' "
                    + " AND v.chantier.id = "+c.getId());
        } catch (Exception e) {
         logger.error("Erreur de récupération liste des voiture car "+e.getMessage());
        }
        return l;
    }

    @Override
    public List<Voiture> getListeVoituresBySalarier(Salarie s) {
          List<Voiture> l = new ArrayList<Voiture>();
        try {
            l = (List<Voiture>) this.getHibernateTemplate().find("From Voiture v "
                    + " where v.archiver = '" + Boolean.FALSE + "' "
                    + " AND v.salarie.id = "+s.getId());
        } catch (Exception e) {
         logger.error("Erreur de récupération liste des voiture car "+e.getMessage());
        }
        return l;
    }

    @Override
    public List<Voiture> getListeVoituresNonAffecter() {
        List<Voiture> l = new ArrayList<Voiture>();
        try {
            l = (List<Voiture>) this.getHibernateTemplate().find("From Voiture v "
                    + " where v.archiver = '" + Boolean.FALSE + "' "
                    + " AND v.affect = '"+Boolean.FALSE+"'");
        } catch (Exception e) {
         logger.error("Erreur de récupération liste des voiture car "+e.getMessage());
        }
        return l;
    }

    @Override
    public List<Voiture> getListeVoituresAffecter() {
         List<Voiture> l = new ArrayList<Voiture>();
        try {
            l = (List<Voiture>) this.getHibernateTemplate().find("From Voiture v "
                    + " where v.archiver = '" + Boolean.FALSE + "' "
                    + " AND v.affect = '"+Boolean.TRUE+"'");
        } catch (Exception e) {
         logger.error("Erreur de récupération liste des voiture car "+e.getMessage());
        }
        return l;
    }

    @Override
    public List<Voiture> getListeVoitureByMarque(String marque) {
        List<Voiture> l = new ArrayList<Voiture>();
        try {
                l = (List<Voiture>) this.getHibernateTemplate().find(" SELECT v From Voiture "
                        + " where v.marque   = '"+marque+"'"
                        + " AND   v.archiver = '" + Boolean.FALSE + "'");
        } catch (Exception e) {
         logger.error("Erreur de récupération liste des voiture car "+e.getMessage());
        }
        return l;
    }

    @Override
    public Boolean existeVoiture(Voiture v) {
        Boolean ex =false;
         try {
                List l = (List<Voiture>) this.getHibernateTemplate().find("From Voiture v "
                        + " where  v.matriculevoiture = '" + v.getMatriculevoiture() + "'");
                if(l.size()>0){
                    ex = true;
                }
        } catch (Exception e) {
         logger.error("Erreur de récupération liste des voiture car "+e.getMessage());
        }
         return ex;
    }

    @Override
    public List<Voiture> getListeVoituresAffecterSalarie() {
        List<Voiture> l = new ArrayList<Voiture>();
        try {
            l = (List<Voiture>) this.getHibernateTemplate().find("From Voiture v "
                    + " where v.archiver = '" + Boolean.FALSE + "' "
                    + " AND v.affect = '"+Boolean.TRUE+"'"
                    + " AND v.salarie is not null");
        } catch (Exception e) {
         logger.error("Erreur de récupération liste des voiture afecte au salarie car "+e.getMessage());
        }
        return l;
    }

    @Override
    public List<Voiture> getListeVoituresAffecterChantier() {
         List<Voiture> l = new ArrayList<Voiture>();
        try {
            l = (List<Voiture>) this.getHibernateTemplate().find("From Voiture v "
                    + " where v.archiver = '" + Boolean.FALSE + "' "
                    + " AND v.affect = '"+Boolean.TRUE+"'"
                    + " AND v.chantier is not null ");
        } catch (Exception e) {
         logger.error("Erreur de récupération liste des voiture afecte au chantier car "+e.getMessage());
        }
        return l;
    }
    
    
    //////////////////////////////////////////////////////////////////////////////
    ////////////////////////// Historique voiture  ///////////////////////////////
    //////////////////////////////////////////////////////////////////////////////
    
    @Override
    public void ajoutHistorique(HistoriqueVoiture hv) {
          try { 
            this.getHibernateTemplate().save(hv);
            getHibernateTemplate().flush(); 
        } catch (HibernateException e) {
            logger.error("Erreur d'insertion Historique Voiture car "+e.getMessage());
        }
    }

    @Override
    public List<HistoriqueVoiture> getAllHistoriqueVoiture() {
        List<HistoriqueVoiture> l = new ArrayList<HistoriqueVoiture>();
        try {
            l = (List<HistoriqueVoiture>) this.getHibernateTemplate().find("From HistoriqueVoiture v "
                    + " where v.voiture.archiver = '" + Boolean.FALSE + "' ");
        } catch (Exception e) {
         logger.error("Erreur de récupération liste d'historiqe voiture car "+e.getMessage());
        }
        return l;
    }

    @Override
    public List<HistoriqueVoiture> getAllHistoriqueVoitureByIdVoiture(Long id) {
        List<HistoriqueVoiture> l = new ArrayList<HistoriqueVoiture>();
        try {
            l = (List<HistoriqueVoiture>) this.getHibernateTemplate().find("From HistoriqueVoiture v "
                    + " where v.voiture.archiver = '" + Boolean.FALSE + "' "
                    + " AND v.voiture.id="+id);
        } catch (Exception e) {
         logger.error("Erreur de récupération liste d'historiqe voiture car "+e.getMessage());
        }
        return l;
    }

    @Override
    public List<HistoriqueVoiture> getAllHistoriqueVoitureByIdSalarier(Long id) {
         List<HistoriqueVoiture> l = new ArrayList<HistoriqueVoiture>();
        try {
            l = (List<HistoriqueVoiture>) this.getHibernateTemplate().find("From HistoriqueVoiture v "
                    + " where v.voiture.archiver = '" + Boolean.FALSE + "' "
                    + " AND v.salarie.id="+id);
        } catch (Exception e) {
         logger.error("Erreur de récupération liste d'historiqe voiture car "+e.getMessage());
        }
        return l;
    }

    @Override
    public List<HistoriqueVoiture> getAllHistoriqueVoitureByIdChantier(Long id) {
         List<HistoriqueVoiture> l = new ArrayList<HistoriqueVoiture>();
        try {
            l = (List<HistoriqueVoiture>) this.getHibernateTemplate().find("From HistoriqueVoiture v "
                    + " where v.voiture.archiver = '" + Boolean.FALSE + "' "
                    + " AND v.chantier.id="+id);
        } catch (Exception e) {
         logger.error("Erreur de récupération liste d'historiqe voiture car "+e.getMessage());
         }
        return l;
    }
    
    @Override
    public List<HistoriqueVoiture> getAllHistoriqueVoitureSalarier() {
         List<HistoriqueVoiture> l = new ArrayList<HistoriqueVoiture>();
        try {
            l = (List<HistoriqueVoiture>) this.getHibernateTemplate().find("From HistoriqueVoiture v "
                    + " where v.voiture.archiver = '" + Boolean.FALSE + "' "
                    + " AND v.salarie is not null");
        } catch (Exception e) {
         logger.error("Erreur de récupération liste d'historiqe voiture car "+e.getMessage());
         }
        return l;
    }

    @Override
    public List<HistoriqueVoiture> getAllHistoriqueVoitureChantier() {
         List<HistoriqueVoiture> l = new ArrayList<HistoriqueVoiture>();
        try {
            l = (List<HistoriqueVoiture>) this.getHibernateTemplate().find("From HistoriqueVoiture v "
                    + " where v.voiture.archiver = '" + Boolean.FALSE + "' "
                    + " AND v.chantier is not null");
        } catch (Exception e) {
         logger.error("Erreur de récupération liste d'historiqe voiture car "+e.getMessage());
         }
        return l;
    }

    @Override
    public List<HistoriqueVoiture> getAllHistoriqueVoitureSalarierByDate(Date ddb, Date df) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

         List<HistoriqueVoiture> l = new ArrayList<HistoriqueVoiture>();
         String req = "From HistoriqueVoiture v "
                    + " where v.voiture.archiver = '" + Boolean.FALSE + "' "
                    + " AND v.salarie is not null";
         if(ddb != null){
             req += " AND v.dateAfect >= '"+ sdf.format(ddb)+"'";
         }
         if(ddb != null){
             req += " AND v.dateDesafect <= '"+ sdf.format(df)+"'";
         }
        try {
            l = (List<HistoriqueVoiture>) this.getHibernateTemplate().find(req);
        } catch (Exception e) {
         logger.error("Erreur de récupération liste d'historiqe voiture car "+e.getMessage());
         }
        return l;
    }

    @Override
    public List<HistoriqueVoiture> getAllHistoriqueVoitureChantierByDate(Date ddb, Date df) {
        System.out.println("DB : "+ddb+" DF : "+df);
         List<HistoriqueVoiture> l = new ArrayList<HistoriqueVoiture>();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
          String req = "From HistoriqueVoiture v "
                    + " where v.voiture.archiver = '" + Boolean.FALSE + "' "
                    + " AND v.chantier is not null";
         if(ddb != null){
             req += " AND v.dateAfect >= '"+ sdf.format(ddb)+"'";
         }
         if(ddb != null){
             req += " AND v.dateDesafect <= '"+ sdf.format(df)+"'";
         }
        try {
            l = (List<HistoriqueVoiture>) this.getHibernateTemplate().find(req);
        } catch (Exception e) {
         logger.error("Erreur de récupération liste d'historiqe voiture car "+e.getMessage());
         }
        return l;
    }
    
    
    @Override
    public String getChantierBySalaryID(Long id) {
         String c="";
        try {
            Presence p  = (Presence) this.getHibernateTemplate().find("SELECT p From Presence p "
                    + " where p.id = "
                    + " (SELECT MAX(v.id) FROM Presence v WHERE v.salarie.id="+id+")").get(0);
            c=p.getChantier().getCode();
        } catch (Exception e) {
         logger.error("Erreur de récupération liste d'historiqe voiture car "+e.getMessage());
         }
        return c;
    }
    
    
}
