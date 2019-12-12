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
import java.util.logging.Logger;
import ma.bservices.beans.Chantier;
import ma.bservices.beans.Salarie;
import ma.bservices.tgcc.Entity.CarteGasoil;
import ma.bservices.tgcc.Entity.HistoriqueCarteGazoile;
import ma.bservices.tgcc.Entity.Voiture;
import ma.bservices.tgcc.utilitaire.MbHibernateDaoSupport;
import org.hibernate.FlushMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author wattah
 */
@Repository("carteGasoilDAO")
@Transactional
public class CarteGasoilDAOImpl extends MbHibernateDaoSupport implements CarteGasoilDAO, Serializable {

    private static final Logger looger = Logger.getLogger(CarteGasoilDAOImpl.class.getName());

    public CarteGasoilDAOImpl() {
    }
    
    

    @Override
    public void save(CarteGasoil gasoil) {
        try {
            Session session = getSessionFactory().openSession();
            session.setFlushMode(FlushMode.AUTO);
            session.beginTransaction();
            gasoil.setArchiver(Boolean.FALSE);
            gasoil.setAffect(Boolean.FALSE);
            session.save(gasoil);
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            looger.warning("Erreur d'enregistrement de la carte car :"+e.getMessage());
         }
    }

    @Override
    public void delete(CarteGasoil carteGasoil) {
        try {
            Session session = getSessionFactory().openSession();
            session.setFlushMode(FlushMode.AUTO);
            session.beginTransaction();
            carteGasoil.setArchiver(Boolean.TRUE);
            session.update(carteGasoil);
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            looger.warning("Erreur de suppression de la carte car :"+e.getMessage());
        }
    }

    @Override
    public void update(CarteGasoil carteGasoil) {
        try {
            Session session = getSessionFactory().openSession();
            session.setFlushMode(FlushMode.AUTO);
            session.beginTransaction();
            carteGasoil.setArchiver(Boolean.FALSE);
            session.update(carteGasoil);
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            looger.warning("Erreur de modification de la carte car :"+e.getMessage());
        }
    }

    @Override
    public List<CarteGasoil> findAll() {
        List<CarteGasoil> l = new ArrayList<CarteGasoil>();
        
        try {            
            l =  (List<CarteGasoil>) this.getHibernateTemplate().find("From CarteGasoil c where  c.archiver = '" + Boolean.FALSE + "'");
            System.out.println("=========> l : "+l.get(0).toString());
        } catch (Exception e) {
            looger.warning("Erreur de récupération de la liste des cartes car :"+e.getMessage());
        }
        return l;
    }
///////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    
    public List<CarteGasoil> getListCarte_GasoilNonAffecte() {

        List l = null;

        l = this.getHibernateTemplate().find("From CarteGasoil v where "
                + " v.archiver = '" + Boolean.FALSE + "' and  Dtype = 'CarteGasoil' ");

        return l;
    }

    public List<CarteGasoil> getListCarteGasoilDifferentId(Long id) {

        List l;

        l = this.getHibernateTemplate().find("From CarteGasoil c where c.archiver = '" + Boolean.FALSE + "' and c.id != " + id);

        return l;
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public CarteGasoil carteGazoilById(Integer id) {
        CarteGasoil l = new CarteGasoil();
        
      try {
            l=this.getHibernateTemplate().get(CarteGasoil.class, id);
        } catch (Exception e) {
            logger.error("Erreur de récupération de CarteGasoil par id car "+e.getMessage());
        }
        return l;
    }
    
    @Override
    public List<CarteGasoil> listCarteGasoil() {
        List<CarteGasoil> l = new ArrayList<CarteGasoil>();
        try {
            l= (List<CarteGasoil>) this.getHibernateTemplate().find("From CarteGasoil c "
                    + " where c.archiver = '" + Boolean.FALSE + "'"
                    + " ORDER BY c.affect ");
        } catch (Exception e) {
            logger.error("Erreur de récupération liste CarteGasoil par id car "+e.getMessage());
        }
        return l;
    }
    @Override
    public List<CarteGasoil> listCarteGasoilNonAffecte() {
        List<CarteGasoil> l = new ArrayList<CarteGasoil>();
        try {
            l= (List<CarteGasoil>) this.getHibernateTemplate().find("From CarteGasoil c "
                    + " where c.archiver = '" + Boolean.FALSE + "'"
                    + " AND c.affect ='"+ Boolean.FALSE +"'");
        } catch (Exception e) {
            logger.error("Erreur de récupération liste CarteGasoil par id car "+e.getMessage());
        }
        return l;
    }

    @Override
    public List<CarteGasoil> listCarteGasoilAffecte() {
        List<CarteGasoil> l = new ArrayList<CarteGasoil>();
        try {
            l= (List<CarteGasoil>) this.getHibernateTemplate().find("From CarteGasoil c "
                    + " where c.archiver = '" + Boolean.FALSE + "'"
                    + " AND c.affect ='"+ Boolean.TRUE +"'");
        } catch (Exception e) {
            logger.error("Erreur de récupération liste CarteGasoil par id car "+e.getMessage());
        }
        return l;
    }

    @Override
    public List<CarteGasoil> listCarteGasoilSalarie() {
         List<CarteGasoil> l = new ArrayList<CarteGasoil>();
        try {
            l= (List<CarteGasoil>) this.getHibernateTemplate().find("From CarteGasoil c "
                    + " where c.archiver = '" + Boolean.FALSE + "'"
                    + " AND c.salarie is not null");
        } catch (Exception e) {
            logger.error("Erreur de récupération liste CarteGasoil afecter au salarier par id car "+e.getMessage());
        }
        return l;
    }
    @Override
    public List<CarteGasoil> listCarteGasoilBySalarie(Salarie c) {
        List<CarteGasoil> l = new ArrayList<CarteGasoil>();
        try {
            l= (List<CarteGasoil>) this.getHibernateTemplate().find("From CarteGasoil c "
                    + " where c.archiver = '" + Boolean.FALSE + "'"
                    + " AND c.salarie.id ="+c.getId());
        } catch (Exception e) {
            logger.error("Erreur de récupération liste CarteGasoil afecter au salarier par id car "+e.getMessage());
        }
        return l;
    }
    
    
    @Override
    public List<CarteGasoil> listCarteGasoilChantier() {
          List<CarteGasoil> l = new ArrayList<CarteGasoil>();
        try {
            l= (List<CarteGasoil>) this.getHibernateTemplate().find("From CarteGasoil c "
                    + " where c.archiver = '" + Boolean.FALSE + "'"
                    + " AND c.chantier is not null");
        } catch (Exception e) {
            logger.error("Erreur de récupération liste CarteGasoil afecter au chantier par id car "+e.getMessage());
        }
        return l;
    }

    @Override
    public List<CarteGasoil> listCarteGasoilByChantier(Chantier c) {
        List<CarteGasoil> l = new ArrayList<CarteGasoil>();
        try {
            l= (List<CarteGasoil>) this.getHibernateTemplate().find("From CarteGasoil c "
                    + " where c.archiver = '" + Boolean.FALSE + "'"
                    + " AND c.chantier.id ="+c.getId());
        } catch (Exception e) {
            logger.error("Erreur de récupération liste CarteGasoil afecter au chantier par id car "+e.getMessage());
        }
        return l;
    }

    @Override
    public List<CarteGasoil> listCarteGasoilSalarieArchive() {
        List<CarteGasoil> l = new ArrayList<CarteGasoil>();
        try {
            l= (List<CarteGasoil>) this.getHibernateTemplate().find("From CarteGasoil c "
                    + " where c.archiver = '" + Boolean.TRUE + "'"
                    + " AND c.salarie is not null");
        } catch (Exception e) {
            logger.error("Erreur de récupération liste CarteGasoil afecter au chantier par id car "+e.getMessage());
        }
        return l;
    }

    @Override
    public List<CarteGasoil> listCarteGasoilChantierArchive() {
        List<CarteGasoil> l = new ArrayList<CarteGasoil>();
        try {
            l= (List<CarteGasoil>) this.getHibernateTemplate().find("From CarteGasoil c "
                    + " where c.archiver = '" + Boolean.TRUE + "'"
                    + " AND c.chantier is not null");
        } catch (Exception e) {
            logger.error("Erreur de récupération liste CarteGasoil afecter au chantier par id car "+e.getMessage());
        }
        return l;
    }

    @Override
    public CarteGasoil lastCarteGasoil() {
        CarteGasoil c = new CarteGasoil();
        try {
             c =  (CarteGasoil) this.getHibernateTemplate().find(" From CarteGasoil c "
                    + " where c.id = "
                    + " (SELECT MAX(d.id) "
                    + "     FROM CarteGasoil d "
                    + "     where d.archiver= '" + Boolean.FALSE + "')").get(0);
        } catch (Exception e) {
            logger.error("Erreur de récupération dernier CarteGasoil car "+e.getMessage());
        }
        return c;
    }

    @Override
    public Voiture voitureBySalary(Long id) {
        Voiture v = new Voiture();
        try {
             v =  (Voiture) this.getHibernateTemplate().find(" From Voiture v "
                    + " where v.id = "
                    + " (SELECT MAX(d.id) "
                    + "     FROM Voiture d "
                    + "     where d.archiver= '" + Boolean.FALSE + "'"
                    + "     AND d.salarie.id="+id+")").get(0);
        } catch (Exception e) {
            logger.error("Erreur de récupération dernier Voiture car "+e.getMessage());
        }
        return v;
    }
    @Override
    public Voiture voitureByChantier(Long id) {
        Voiture v = new Voiture();
        try {
             v =  (Voiture) this.getHibernateTemplate().find(" From Voiture v "
                    + " where v.id = "
                    + " (SELECT MAX(d.id) "
                    + "     FROM Voiture d "
                    + "     where d.archiver= '" + Boolean.FALSE + "'"
                    + "     AND d.chantier.id="+id+")").get(0);
        } catch (Exception e) {
            logger.error("Erreur de récupération dernier Voiture car "+e.getMessage());
        }
        return v;
    }

    @Override
    public List<CarteGasoil> listCarteGasoilBySalarieArchive(Salarie c) {
        List<CarteGasoil> l = new ArrayList<CarteGasoil>();
        try {
            l= (List<CarteGasoil>) this.getHibernateTemplate().find("From CarteGasoil c "
                    + " where c.archiver = '" + Boolean.TRUE + "'"
                    + " AND c.salarie.id ="+c.getId());
        } catch (Exception e) {
            logger.error("Erreur de récupération liste CarteGasoil afecter au chantier par id car "+e.getMessage());
        }
        return l;
    }

    @Override
    public List<CarteGasoil> listCarteGasoilByChantierArchive(Chantier c) {
         List<CarteGasoil> l = new ArrayList<CarteGasoil>();
        try {
            l= (List<CarteGasoil>) this.getHibernateTemplate().find("From CarteGasoil c "
                    + " where c.archiver = '" + Boolean.TRUE + "'"
                    + " AND c.chantier.id="+c.getId());
        } catch (Exception e) {
            logger.error("Erreur de récupération liste CarteGasoil afecter au chantier par id car "+e.getMessage());
        }
        return l;
    }

    @Override
    public List<Salarie> findAllSalaries(){
        List<Salarie> l = new ArrayList<Salarie>();
        
        try {
            l= (List<Salarie>) this.getHibernateTemplate().find("From Salarie s "
                    + " where s in (SELECT v.salarie From Voiture v "
                    + " where v.archiver = '" + Boolean.FALSE + "' "
                    + " AND v.affect = '"+Boolean.TRUE+"'"
                    + " AND v.salarie is not null)");
        } catch (Exception e) {
            logger.error("Erreur de récupération liste des SALARIE qui on une voiture car "+e.getMessage());
        }
        return l;
    }

    ///////////////////////////////////////////////////////////////////////////
    ////////////////////////// Historique CARTE GAZOILE ///////////////////////
    ///////////////////////////////////////////////////////////////////////////

    
    @Override
    public void ajoutHistorique(HistoriqueCarteGazoile hv) {
          try { 
            this.getHibernateTemplate().save(hv);
            getHibernateTemplate().flush(); 
        } catch (HibernateException e) {
            logger.error("Erreur d'insertion Historique carteGasoil car "+e.getMessage());
        }
    }

    @Override
    public List<HistoriqueCarteGazoile> getAllHistoriqueCarteGazoile() {
        List<HistoriqueCarteGazoile> l = new ArrayList<HistoriqueCarteGazoile>();
        try {
            l = (List<HistoriqueCarteGazoile>) this.getHibernateTemplate().find("From HistoriqueCarteGazoile v "
                    + " where v.carteGasoil.archiver = '" + Boolean.FALSE + "' ");
        } catch (Exception e) {
         logger.error("Erreur de récupération liste d'historiqe carteGasoil car "+e.getMessage());
        }
        return l;
    }

    @Override
    public List<HistoriqueCarteGazoile> getAllHistoriqueCarteGazoileByIdVoiture(Long id) {
        List<HistoriqueCarteGazoile> l = new ArrayList<HistoriqueCarteGazoile>();
        try {
            l = (List<HistoriqueCarteGazoile>) this.getHibernateTemplate().find("From HistoriqueCarteGazoile v "
                    + " where v.carteGasoil.archiver = '" + Boolean.FALSE + "' "
                    + " AND v.carteGasoil.id="+id);
        } catch (Exception e) {
         logger.error("Erreur de récupération liste d'historiqe carteGasoil car "+e.getMessage());
        }
        return l;
    }

    @Override
    public List<HistoriqueCarteGazoile> getAllHistoriqueCarteGazoileByIdSalarier(Long id) {
         List<HistoriqueCarteGazoile> l = new ArrayList<HistoriqueCarteGazoile>();
        try {
            l = (List<HistoriqueCarteGazoile>) this.getHibernateTemplate().find("From HistoriqueCarteGazoile v "
                    + " where v.carteGasoil.archiver = '" + Boolean.FALSE + "' "
                    + " AND v.salarie.id="+id);
        } catch (Exception e) {
         logger.error("Erreur de récupération liste d'historiqe carteGasoil car "+e.getMessage());
        }
        return l;
    }

    @Override
    public List<HistoriqueCarteGazoile> getAllHistoriqueCarteGazoileByIdChantier(Long id) {
         List<HistoriqueCarteGazoile> l = new ArrayList<HistoriqueCarteGazoile>();
        try {
            l = (List<HistoriqueCarteGazoile>) this.getHibernateTemplate().find("From HistoriqueCarteGazoile v "
                    + " where v.carteGasoil.archiver = '" + Boolean.FALSE + "' "
                    + " AND v.chantier.id="+id);
        } catch (Exception e) {
         logger.error("Erreur de récupération liste d'historiqe voiture car "+e.getMessage());
         }
        return l;
    }
    
    @Override
    public List<HistoriqueCarteGazoile> getAllHistoriqueCarteGazoileSalarier() {
         List<HistoriqueCarteGazoile> l = new ArrayList<HistoriqueCarteGazoile>();
        try {
            l = (List<HistoriqueCarteGazoile>) this.getHibernateTemplate().find("From HistoriqueCarteGazoile v "
                    + " where v.carteGasoil.archiver = '" + Boolean.FALSE + "' "
                    + " AND v.salarie is not null");
        } catch (Exception e) {
         logger.error("Erreur de récupération liste d'historiqe voiture car "+e.getMessage());
         }
        return l;
    }

    @Override
    public List<HistoriqueCarteGazoile> getAllHistoriqueCarteGazoileChantier() {
         List<HistoriqueCarteGazoile> l = new ArrayList<HistoriqueCarteGazoile>();
        try {
            l = (List<HistoriqueCarteGazoile>) this.getHibernateTemplate().find("From HistoriqueCarteGazoile v "
                    + " where v.carteGasoil.archiver = '" + Boolean.FALSE + "' "
                    + " AND v.chantier is not null");
        } catch (Exception e) {
         logger.error("Erreur de récupération liste d'historiqe carteGasoil car "+e.getMessage());
         }
        return l;
    }

    @Override
    public List<HistoriqueCarteGazoile> getAllHistoriqueCarteGazoileSalarierByDate(Date ddb, Date df) {
        System.out.println("DB : "+ddb+" DF : "+df);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

         List<HistoriqueCarteGazoile> l = new ArrayList<HistoriqueCarteGazoile>();
         String req = "From HistoriqueCarteGazoile v "
                    + " where v.voiture.archiver = '" + Boolean.FALSE + "' "
                    + " AND v.salarie is not null";
         if(ddb != null){
             req += " AND v.dateAfect >= '"+ sdf.format(ddb)+"'";
         }
         if(ddb != null){
             req += " AND v.dateDesafect <= '"+ sdf.format(df)+"'";
         }
        try {
            l = (List<HistoriqueCarteGazoile>) this.getHibernateTemplate().find(req);
        } catch (Exception e) {
         logger.error("Erreur de récupération liste d'historiqe voiture car "+e.getMessage());
         }
        return l;
    }

    @Override
    public List<HistoriqueCarteGazoile> getAllHistoriqueCarteGazoileChantierByDate(Date ddb, Date df) {
        System.out.println("DB : "+ddb+" DF : "+df);
         List<HistoriqueCarteGazoile> l = new ArrayList<HistoriqueCarteGazoile>();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
          String req = "From HistoriqueCarteGazoile v "
                    + " where v.voiture.archiver = '" + Boolean.FALSE + "' "
                    + " AND v.chantier is not null";
         if(ddb != null){
             req += " AND v.dateAfect >= '"+ sdf.format(ddb)+"'";
         }
         if(ddb != null){
             req += " AND v.dateDesafect <= '"+ sdf.format(df)+"'";
         }
        try {
            l = (List<HistoriqueCarteGazoile>) this.getHibernateTemplate().find(req);
        } catch (Exception e) {
         logger.error("Erreur de récupération liste d'historiqe voiture car "+e.getMessage());
         }
        return l;
    }
    
    
    
    

}
