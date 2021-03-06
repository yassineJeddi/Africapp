/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.dao.Mensuel;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import ma.bservices.tgcc.Entity.Historique_Voiture;
import ma.bservices.tgcc.utilitaire.MbHibernateDaoSupport;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author admin
 */

@Repository("historiqueVDAO")
@Transactional
public class HistoriqueVoitureDAOImpl extends MbHibernateDaoSupport implements Serializable, HistoriqueVoitureDAO {

    @Override
    public List<Historique_Voiture> findAll() {     
        return this.getHibernateTemplate().loadAll(Historique_Voiture.class);
    }
        
    

    @Override
    public void addRecordVoitureHistorique(Historique_Voiture recordVH) {
        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);
        session.beginTransaction();
        session.save(recordVH);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List<Historique_Voiture> findByDateRange(Date from, Date to) {
         String dateFromString, dateToString;
         DateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
         dateFromString = formatDate.format(from);
         dateToString = formatDate.format(to);
          System.out.println(formatDate.format(from));          
          System.out.println(formatDate.format(to));
        //  return this.getHibernateTemplate().loadAll(AffectationStock.class);
        List l = this.getHibernateTemplate().find("SELECT s FROM Historique_Voiture s WHERE s.dateAffectationVoiture >= '" + dateFromString + "' AND s.dateAffectationVoiture <= '" + dateToString + "'");
        return l.size() > 0 ? l : null;
    }
    
    
    
    
}
