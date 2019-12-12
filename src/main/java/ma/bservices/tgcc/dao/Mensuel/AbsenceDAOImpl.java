/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.dao.Mensuel;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import ma.bservices.tgcc.Entity.AbsenceMensuel;
import ma.bservices.tgcc.Entity.PointageMensuelQuinzinier;
import ma.bservices.tgcc.utilitaire.MbHibernateDaoSupport;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author j.allali
 */
@Repository("absenceDAO")
@Transactional
public class AbsenceDAOImpl extends MbHibernateDaoSupport implements AbsenceDAO, Serializable {

    @Override
    public List<AbsenceMensuel> getAbsenceById(int id) {
        List l = this.getHibernateTemplate().find("SELECT c FROM AbsenceMensuel c "
                + "WHERE c.salarie.id = '" + id + "' "
                + "AND ( c.checked = 1 OR c.checked = 2 )");

        return l;

    }

    @Override
    public List<AbsenceMensuel> getMensuelByDate(Date date) {
        String s = "";
      
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
         
     
           s = sdf.format(date);
      
        
        System.out.println("DATE : " + s);
        
        List l = this.getHibernateTemplate().find("SELECT c FROM AbsenceMensuel c WHERE c.dateDebut LIKE '" + s + "%'" );
        if (l.size() > 0) {

            return l;
        }

        return null;
    }

    @Override
    public List<PointageMensuelQuinzinier> getAbsenceMensuelQById(int id) {
        List l = this.getHibernateTemplate().find("SELECT c FROM PointageMensuelQuinzinier c WHERE c.mensuel.id = '" + id + "'");
        if (l.size() > 0) {

            return l;
        }

        return null;
    }

    @Override
    public void updatePointageMQ(AbsenceMensuel pointageMQ) {
        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);

        session.beginTransaction();
        pointageMQ.setDateAjout(new Date());
        session.update(pointageMQ);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void save(AbsenceMensuel _absense) {

        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);

        session.beginTransaction();
        _absense.setDateAjout(new Date());
        session.saveOrUpdate(_absense);
        session.getTransaction().commit();

        session.close();

    }

    /*
    * test Unitaire suprimer absence
     */
    @Override
    public void delete(AbsenceMensuel _absense) {

        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);

        session.beginTransaction();
        session.save(_absense);
        session.getTransaction().commit();

        session.close();

    }

    /**
     * retourne la liste des absences à valider
     *
     * @param id_mensuel
     * @return List<Absence>
     */
    @Override
    public List<AbsenceMensuel> getAbsenceById_tocheck(int id_mensuel) {
        List l = this.getHibernateTemplate().find("SELECT c FROM AbsenceMensuel c "
                + "WHERE c.salarie.id = '" + id_mensuel + "' "
                + "AND c.checked is NULL");

        return l;
    }
    
    
    /**
     * retourne la liste des absences qui se chevauchement avec une absence donnée
     * @param absence
     * @return 
     */
    public List<AbsenceMensuel> getAbsencesChevauchement(AbsenceMensuel absence){
        
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd h:mm:ss");
            Date date_to = absence.getDateFin();
            Date date_From =  absence.getDateDebut();
            String date_To_String = df.format(date_to);
            String date_From_String = df.format(date_From);
        
        
       List l = this.getHibernateTemplate().find("SELECT a FROM AbsenceMensuel a "
                + "WHERE a.salarie.id = '" + absence.getSalarie().getId() + "' "
                + "AND ( (a.dateDebut between ' " + date_From_String + " ' AND ' "  + date_To_String + " ') "
               + "OR ( a.dateFin between ' " + date_From_String + " ' AND ' "  + date_To_String + " ' ) )"
               );
       
       return l;
    }

}
