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
import java.util.ArrayList;
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
        List<AbsenceMensuel> l = new ArrayList<AbsenceMensuel>();
        try {
             l = (List<AbsenceMensuel>) this.getHibernateTemplate().find("SELECT c FROM AbsenceMensuel c "
                + "WHERE c.salarie.id = '" + id + "' "
                + "AND ( c.checked = 1 OR c.checked = 2 )");
        } catch (Exception e) {
            System.out.println("Erreur de recuperation getAbsenceById car " +e.getMessage());
                    
        }

        return l;

    }

    @Override
    public List<AbsenceMensuel> getMensuelByDate(Date date) {
        List<AbsenceMensuel> l = new ArrayList<AbsenceMensuel>();
        try {
                String s = "";
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                   s = sdf.format(date);
                 l = (List<AbsenceMensuel>) this.getHibernateTemplate().find("SELECT c FROM AbsenceMensuel c WHERE c.dateDebut LIKE '" + s + "%'" );
            
        } catch (Exception e) {
            System.out.println("Erreur de recuperation getMensuelByDate car " +e.getMessage());
        }
       

        return l;
    }

    @Override
    public List<PointageMensuelQuinzinier> getAbsenceMensuelQById(int id) {
        List<PointageMensuelQuinzinier> l = new ArrayList<PointageMensuelQuinzinier>();
        try {
                l = (List<PointageMensuelQuinzinier>) this.getHibernateTemplate().find("SELECT c FROM PointageMensuelQuinzinier c WHERE c.mensuel.id = '" + id + "'");
            
        } catch (Exception e) {
            System.out.println("Erreur de  getAbsenceMensuelQById car " +e.getMessage());
        }
        return l;
    }

    @Override
    public void updatePointageMQ(AbsenceMensuel pointageMQ) {
        try {
                Session session = getSessionFactory().openSession();
                session.setFlushMode(FlushMode.AUTO);

                session.beginTransaction();
                pointageMQ.setDateAjout(new Date());
                session.update(pointageMQ);
                session.getTransaction().commit();
                session.close();
            
        } catch (Exception e) {
            System.out.println("Erreur de  updatePointageMQ car "+e.getMessage());
        }
    }

    @Override
    public void save(AbsenceMensuel _absense) {
        
        try {
            Session session = getSessionFactory().openSession();
            session.setFlushMode(FlushMode.AUTO);
            session.beginTransaction();
            _absense.setDateAjout(new Date());
            session.saveOrUpdate(_absense);
            session.getTransaction().commit();
            session.close();
            
        } catch (Exception e) {
            System.out.println("Erreur de save car "+e.getMessage());
            
        }


    }

    /*
    * test Unitaire suprimer absence
     */
    @Override
    public void delete(AbsenceMensuel _absense) {
        try {
                Session session = getSessionFactory().openSession();
                session.setFlushMode(FlushMode.AUTO);

                session.beginTransaction();
                session.save(_absense);
                session.getTransaction().commit();

                session.close();
            
        } catch (Exception e) {
            System.out.println("Erreur de delete AbsenceMensuel car "+e.getMessage());
        }

    }

    /**
     * retourne la liste des absences à valider
     *
     * @param id_mensuel
     * @return List<Absence>
     */
    @Override
    public List<AbsenceMensuel> getAbsenceById_tocheck(int id_mensuel) {
        List<AbsenceMensuel> l = new ArrayList<AbsenceMensuel>();
        try {
             l = (List<AbsenceMensuel>) this.getHibernateTemplate().find("SELECT c FROM AbsenceMensuel c "
                        + "WHERE c.salarie.id = '" + id_mensuel + "' "
                        + "AND c.checked is NULL");
            
        } catch (Exception e) {
            System.out.println("Erreur de getAbsenceById_tocheck car "+e.getMessage());
        }

        return l;
    }
    
    
    /**
     * retourne la liste des absences qui se chevauchement avec une absence donnée
     * @param absence
     * @return 
     */
    public List<AbsenceMensuel> getAbsencesChevauchement(AbsenceMensuel absence){
        List<AbsenceMensuel> l = new ArrayList<AbsenceMensuel>();
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd h:mm:ss");
                Date date_to = absence.getDateFin();
                Date date_From =  absence.getDateDebut();
                String date_To_String = df.format(date_to);
                String date_From_String = df.format(date_From);


            l = (List<AbsenceMensuel>) this.getHibernateTemplate().find("SELECT a FROM AbsenceMensuel a "
                    + "WHERE a.salarie.id = '" + absence.getSalarie().getId() + "' "
                    + "AND ( (a.dateDebut between ' " + date_From_String + " ' AND ' "  + date_To_String + " ') "
                   + "OR ( a.dateFin between ' " + date_From_String + " ' AND ' "  + date_To_String + " ' ) )"
               );
       
            
        } catch (Exception e) {
            System.out.println("Erreur de getAbsencesChevauchement car "+e.getMessage());
        }
       return l;
    }

}
