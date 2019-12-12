/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.dao;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import ma.bservices.beans.AffectationSalarieSupp;
import ma.bservices.beans.Salarie;
import ma.bservices.mb.services.MbHibernateDaoSupport;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author j.allali
 */
@Repository("affectationDAO")
@Transactional
public class AffectationSSuppDAOImpl extends MbHibernateDaoSupport implements AffectationSSuppDAO, Serializable {

    @Override
    public Boolean affecterSupp(AffectationSalarieSupp affect) {
        Transaction tx = null;
        Session session = getSessionFactory().openSession();
        try {
            session.setFlushMode(FlushMode.AUTO);
            tx = session.beginTransaction();
            session.save(session.merge(affect));
            tx.commit();
            session.close();
        } catch (Exception exp) {
            tx.rollback();
            session.close();
            return false;
        }
        return true;
    }

    @Override
    public AffectationSalarieSupp updateSalarie(AffectationSalarieSupp affect) {
        Transaction tx = null;
        Session session = getSessionFactory().openSession();
        try {
            session.setFlushMode(FlushMode.AUTO);
            tx = session.beginTransaction();
            session.update(affect);
            tx.commit();
            session.close();
        } catch (Exception exp) {
            tx.rollback();
            session.close();
            return null;
        }
        return affect;
    }

    @Override
    public List<AffectationSalarieSupp> listSalarieBySupp() {
        /*
          List<AffectationSalarieSupp> l = new ArrayList<AffectationSalarieSupp>();
        
        try {
            l = (List<AffectationSalarieSupp>) this.getHibernateTemplate().find("SELECT s FROM AffectationSalarieSupp s where s.currentSupp = true order by s.dateAffectatio DESC");
        } catch (Exception e) {
            System.out.println("Erreur de récuperation de la liste");
        }
        return l;
         */
        List l = this.getHibernateTemplate().find("SELECT s FROM AffectationSalarieSupp s where s.currentSupp = true order by s.dateAffectatio DESC");
        //System.out.println(l);
        if (l.size() > 0) {
            return l;
        }

        return new ArrayList<>();
    }

    @Override
    public List<AffectationSalarieSupp> listSalarieBySupp(Integer idSupp) {
        List l = this.getHibernateTemplate().find("SELECT s FROM AffectationSalarieSupp s where s.chefEquipe.id = " + idSupp);
        if (l.size() > 0) {
            return l;
        }

        return new ArrayList<>();
    }

    @Override
    public AffectationSalarieSupp listSalarieByChantier(Integer idSupp, Integer idSalarie, Integer idChantier) {
        String query = "";
        if (idSupp != null && idSupp != 0) {
            query += "  and s.chefEquipe.id = " + idSupp + " ";
        }
        List l = this.getHibernateTemplate().find("SELECT s FROM AffectationSalarieSupp s where s.currentSupp=true and s.salaries.id = " + idSalarie + " and s.chantier.id =" + idChantier + " " + query);
        if (l.size() > 0) {
            return (AffectationSalarieSupp) l.get(0);
        }
        return null;
    }

    @Override
    public List<AffectationSalarieSupp> listSalarieByChantierDateSupp(Integer idSupp, Date de, Date a, Integer idChantier) {
        String query = "", joint = "", date1, date2;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        if (de != null && a != null) {
            date1 = sdf.format(a);
            date2 = sdf.format(de);
            if (!"".equals(date1) && !"".equals(date2)) {
                //System.out.println("============= DAO : DATES NOT NULL =======");
                query += " and a.dateAffectatio between '" + date2 + "' and '" + date1 + "'";
                //System.out.println("============= DAO : DATES BETWEEN ======= : " + date1 + " AND " + date2);
                //System.out.println("QUERY SO FAR ===== " + query);
            }
        }

        if (idChantier != null && idChantier != 0) {
            //System.out.println("============= DAO : CHANTIER NOT NULL =======");
            //System.out.println("============= DAO : CHANTIER CODE ======= " + idChantier);
            joint += " left join a.chantier c ";
            query += " and c.id = " + idChantier + " ";
            //System.out.println("QUERY SO FAR ===== " + query);

        }

        if (idSupp != null && idSupp != 0) {
            //System.out.println("============= DAO : Salarie SUP NOT NULL =======");
            //System.out.println("============= DAO : Salarie SUP CODE ======= " + idSupp);

            joint += " left join a.chefEquipe s ";
            query += " and s.id = " + idSupp + " ";
            //System.out.println("QUERY SO FAR ===== " + query);
        }

        //System.out.println("QUERY TO EXECUTE === :: " + query);
        return (List<AffectationSalarieSupp>) this.getHibernateTemplate()
                .find("select a from AffectationSalarieSupp a " + joint + " where a.currentSupp = true " + query);

    }

    @Override
    public Boolean pointageEntreeSalarie(Integer idSalarie, Long longDateTimePointage, String datee) {
        //System.out.println("date : " + datee);
        List l = this.getHibernateTemplate().find("FROM Presence "
                + "where salarie.id = " + idSalarie + " "
                + "and DATE = '" + datee + "'");
        
        if (l.size() > 0) {
            //System.out.println("______________ SIZE PRES _____________ " + l.size());
            return true;
        }

        return false;
    }

    @Override
    public List<AffectationSalarieSupp> listAffectationByChantier(Integer idChantier) {
        List<AffectationSalarieSupp> l = (List<AffectationSalarieSupp>) this.getHibernateTemplate().find("SELECT s FROM AffectationSalarieSupp s"
                + " where s.currentSupp=true "
                + "and s.chantier.id =" + idChantier + " "
                + "order by s.salaries.matricule"
        );
        //this.getHibernateTemplate().getSessionFactory().close();
        if (l.size() > 0) {
            //System.out.println("@@          size supp" + l.size());
            return l;
        }
        //System.out.println("@@          liste null ");
        return new LinkedList<>();
    }

    @Override
    public List<AffectationSalarieSupp> listAffectationByChantierAndSalarie(Integer idChantier, Salarie s) {
        List<AffectationSalarieSupp> l = (List<AffectationSalarieSupp>) this.getHibernateTemplate().find("SELECT s FROM AffectationSalarieSupp s"
                + " where "
                + "s.currentSupp=true "
                + "and s.chantier.id =" + idChantier + " "
                + "and s.salaries.id =" + s.getId() + " "
                //+ "order by s.salaries.matricule"
                /*+ "  s.id=(SELECT MAX(m.id)   FROM AffectationSalarieSupp m"
                        + " WHERE m.currentSupp=true "
                        + " and m.chantier.id =" + idChantier
                        + " and m.salaries.id =" + s.getId() + " )"*/
        );
        //this.getHibernateTemplate().getSessionFactory().close();
        if (l.size() > 0) {
            //System.out.println("@@          size supp" + l.size());
            return l;
        }
        //System.out.println("@@          liste null ");
        return new LinkedList<>();
    }

    @Override
    public List<Salarie> listAffectationByChantierAndChefEquipe(Integer idChantier, Salarie chef) {
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");  
        String d = sdf.format(new Date());
       
        List<Salarie> lSalarie = (List<Salarie>) this.getHibernateTemplate().find("SELECT s.salaries FROM AffectationSalarieSupp s"
                + " where " 
                + " s.currentSupp=true "
                + " AND s.chantier.id =" + idChantier  
                + " AND s.chefEquipe.id =" + chef.getId() 
                + " AND s.salaries.id in "
                + " ( SELECT p.salarie.id FROM Presence p "
                + " WHERE p.chantier.id = "+idChantier
                + "  AND p.date = '"+d+"')"
                //+ "order by s.salaries.matricule" 
        );
        if (lSalarie.size() > 0) {
            //System.out.println("@@          size supp chef" + lSalarie.size());
            return lSalarie;
        }
        return new LinkedList<>();
    }

}
