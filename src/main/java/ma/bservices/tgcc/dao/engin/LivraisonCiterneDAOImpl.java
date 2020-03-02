/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.dao.engin;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import ma.bservices.tgcc.Entity.Bon_Livraison_Citerne;
import ma.bservices.tgcc.utilitaire.MbHibernateDaoSupport;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author a.wattah
 */
@Repository("livraison_CiterneDAO")
@Transactional
public class LivraisonCiterneDAOImpl extends MbHibernateDaoSupport implements LivraisonCiterneDAO, Serializable {

    @Override
    public void save(Bon_Livraison_Citerne liv_citerne) {

        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);

        session.beginTransaction();
        session.save(liv_citerne);

        session.getTransaction().commit();

        session.close();
    }

    @Override
    public List<Bon_Livraison_Citerne> l_historiques() {
        List<Bon_Livraison_Citerne> l = new ArrayList<Bon_Livraison_Citerne>();
        try {
            l = (List<Bon_Livraison_Citerne>) this.getHibernateTemplate().find("FROM Bon_Livraison_Citerne ");
            //l= this.getHibernateTemplate().loadAll(Bon_Livraison_Citerne.class);
            
        } catch (Exception e) {
            System.out.println("Erreu de chargement liste des citernes car "+e.getMessage());
        }
        return l;
    }

    @Override
    public Bon_Livraison_Citerne findBonLivraisonCiterneById(Integer id) {
        Bon_Livraison_Citerne b = new Bon_Livraison_Citerne();
        try {
            b =   this.getHibernateTemplate().get(Bon_Livraison_Citerne.class, id);
            //l= this.getHibernateTemplate().loadAll(Bon_Livraison_Citerne.class);
            
        } catch (Exception e) {
            System.out.println("Erreu de requperer Bon_Livraison_Citerne car "+e.getMessage());
        }
        return b;
    }

    @Override
    public void update(Bon_Livraison_Citerne bon_Livraison_Citerne) {
        try {
                Session session = getSessionFactory().openSession();
                session.setFlushMode(FlushMode.AUTO);

                session.beginTransaction();
                session.update(bon_Livraison_Citerne);

                session.getTransaction().commit();

                session.close();
        } catch (Exception e) {
            System.out.println("Erreu de modification Bon_Livraison_Citerne car "+e.getMessage());
        }
    }

    @Override
    public List<Bon_Livraison_Citerne> get_listBy_id_bonLivraison(int id) {

        List l = this.getHibernateTemplate().find("From Bon_Livraison_Citerne f where f.citerne.id = " + id + "ORDER BY f.date desc");

        return l;
    }

    @Override
    public void delete(Bon_Livraison_Citerne bon_Livraison_Citerne) {

        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);

        session.beginTransaction();
        session.delete(bon_Livraison_Citerne);

        session.getTransaction().commit();

        session.close();
    }

    @Override
    public Bon_Livraison_Citerne get_bon_livraison_by_Id(int id) {

        List l = this.getHibernateTemplate().find("From Bon_Livraison_Citerne f where f.citerne.id = " + id);

        if (l != null && l.size() > 0) {
            return (Bon_Livraison_Citerne) l.get(0);
        }
        return null;

    }

    @Override
    public List<Bon_Livraison_Citerne> get_liste_livraisonByDate_action(int id , Date date, String action, String Num_Commande, String num_Livraison) {

        String date_String = "";
        if (date != null) {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date date_Debu_Convert = date;
            date_String = df.format(date_Debu_Convert);
        }

        List l = this.getHibernateTemplate().find("SELECT c FROM Bon_Livraison_Citerne c WHERE "
                +"c.citerne.id =  '" + id + "' and ("
                + "c.action = '" + action + "' OR c.date = '" + date_String
                + "' OR c.numero_Livraison = '" + num_Livraison + "' OR c.numero_commande = '" + Num_Commande + "')");
        if (l.size() > 0) {

            return l;
        }
        return null;

    }

}
