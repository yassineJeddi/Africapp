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
import ma.bservices.tgcc.Entity.Historique_Telephone;
import ma.bservices.tgcc.utilitaire.MbHibernateDaoSupport;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author admin
 */

@Repository("historiqueTelephoneDAO")
@Transactional
public class HistoriqueTelephoneDAOImpl extends MbHibernateDaoSupport implements Serializable, HistoriqueTelephoneDAO {

    @Override
    public List<Historique_Telephone> findAll() {
        return this.getHibernateTemplate().loadAll(Historique_Telephone.class);
    }

    @Override
    public void addrecord(Historique_Telephone record) {
        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);
        session.beginTransaction();
        session.save(record);
        session.getTransaction().commit();
        session.close(); 
    }

    @Override
    public List<Historique_Telephone> findByDateRange(Date dateDebut, Date dateFin) {
          String dateFromString, dateToString;
         DateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
         dateFromString = formatDate.format(dateDebut);
         dateToString = formatDate.format(dateFin);
          System.out.println(formatDate.format(dateDebut));          
          System.out.println(formatDate.format(dateFin));
        //  return this.getHibernateTemplate().loadAll(AffectationStock.class);
        List l = this.getHibernateTemplate().find("SELECT s FROM Historique_Telephone s WHERE s.dateAffectation >= '" + dateFromString + "' AND s.dateAffectation <= '" + dateToString + "'");
        return l.size() > 0 ? l : null;
    }
    
}
