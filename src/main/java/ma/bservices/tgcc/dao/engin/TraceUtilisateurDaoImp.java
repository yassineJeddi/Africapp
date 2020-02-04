/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.dao.engin;
 
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List; 
import ma.bservices.tgcc.Entity.TraceBonLivraisonCiterne;
import ma.bservices.tgcc.Entity.TraceGestionCiterne;
import ma.bservices.tgcc.Entity.TraceUtilisateur;
import ma.bservices.tgcc.utilitaire.MbHibernateDaoSupport;
import org.hibernate.FlushMode;
import org.hibernate.Session; 
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author yassine
 */
@Repository("traceUtilisateurDao")
@Transactional
public class TraceUtilisateurDaoImp   extends MbHibernateDaoSupport  implements ITraceUtilisateurDao, Serializable{

    
    
         

    @Override
    public void addTraceUtilisateur(TraceUtilisateur t) {
        try {
            System.out.println("-> addTraceUtilisateur() "+t.toString());
                Session session = getSessionFactory().openSession();
                session.setFlushMode(FlushMode.AUTO);

                session.beginTransaction();
                session.save(t);

                session.getTransaction().commit();

                session.close();
        
        } catch (Exception e) {
            System.out.println("Erreur d'enregistrement TraceUtilisateur car "+e.getMessage());
        }
    }
    
         

    @Override
    public void addTraceBonLivraisonCiterne(TraceBonLivraisonCiterne t) {
        try { 
                Session session = getSessionFactory().openSession();
                session.setFlushMode(FlushMode.AUTO);

                session.beginTransaction();
                session.save(t);

                session.getTransaction().commit();

                session.close();
        
        } catch (Exception e) {
            System.out.println("Erreur d'enregistrement TraceUtilisateur car "+e.getMessage());
        }
    }

    @Override
    public void addTraceGestionCiterne(TraceGestionCiterne t) {
        try { 
                Session session = getSessionFactory().openSession();
                session.setFlushMode(FlushMode.AUTO);

                session.beginTransaction();
                session.save(t);

                session.getTransaction().commit();

                session.close();
        
        } catch (Exception e) {
            System.out.println("Erreur d'enregistrement TraceUtilisateur car "+e.getMessage());
        }
    }

    @Override
    public List<TraceUtilisateur> allTraceUtilisateur() {
        List<TraceUtilisateur> l = new ArrayList<TraceUtilisateur>();
        try {
               l= this.getHibernateTemplate().loadAll(TraceUtilisateur.class);
            
        } catch (Exception e) {
            System.out.println("Erreur de chargement liste des TraceUtilisateur car "+e.getMessage());
        }
        return l;
    }
    
}
