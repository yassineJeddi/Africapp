/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.dao.engin;

import java.util.List;
import ma.bservices.beans.Groupe;
import ma.bservices.beans.Permission;
import org.hibernate.FlushMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author a.wattah
 */
@Repository("groupeDAO")
@Transactional
public class GroupeDAOImpl extends HibernateDaoSupport implements GroupeDAO {

    
    
    @Override
    public List<Groupe> findAll() {
        List<Groupe> l=this.getHibernateTemplate().loadAll(Groupe.class);
        return l;
    }


    @Override
    public void validerGroupe(Groupe group) {
        Session session =getSessionFactory().openSession();
        
        session.setFlushMode(FlushMode.AUTO);
        
        session.beginTransaction();
        
        session.update(group);
        
        session.getTransaction().commit();

        session.close();
    }


    @Override
    public void addPermissions(Groupe _g) {
            
        Session session;
        session=getSessionFactory().openSession();

        System.out.println("________ A ");
        session.setFlushMode(FlushMode.AUTO);
        System.out.println("________ B ");

        session.beginTransaction();

        System.out.println("________ C ");
        session.update(session.merge(_g));
        System.out.println("________ D ");

        session.getTransaction().commit();

        System.out.println("________ E ");
        session.close();
    }

    @Override
    public Groupe findById(Integer id) {
         List l = this.getHibernateTemplate().find("SELECT g FROM Groupe g WHERE g.id = " + id);
        
        if(l.size() > 0){
            return (Groupe) l.get(0);
        }
        
        return null;
    }
    
       @Override
    public Groupe findByString(String id) {
           System.out.println(id);
         List l = this.getHibernateTemplate().find("SELECT g FROM Groupe g WHERE g.groupe = '" + id + "'");
        
        if(l.size() > 0){
            return (Groupe) l.get(0);
        }
        
        return null;
    }
    
    @Override
    @Transactional
    public Boolean ajouterGroupe(Groupe groupe) {

        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);

        session.beginTransaction();
        session.save(groupe);

        session.getTransaction().commit();

        session.close();
        return true;
}

    @Override
    public Boolean delete(int id) {
        System.out.println("delete group by ID");
        Session s = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
        System.out.println("dddddddddddd :" + id);

        Query query = s.createQuery("delete from Groupe where id=:id");
        query.setParameter("id", id);
        int deleted = query.executeUpdate();
        System.out.println("Deleted: " + deleted + " Groupe(s)");
        System.out.println("utilisateur  ------------" + id);    
        return true;
    }
    
    @Override
    public Boolean delete(Groupe g) {
        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);

        session.beginTransaction();
        session.delete(g);

        session.getTransaction().commit();

        session.close();
        return true;
    }
    
    @Override
    public Boolean updateGroupe(Groupe groupe){
        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);

        session.beginTransaction();
        session.update(groupe);

        session.getTransaction().commit();

//        session.close();
        return true;
    }

    @Override
    public List<Groupe> getGroupeByLibelle(String groupe) {
        List l = null;
        l = this.getHibernateTemplate().find("From Groupe where groupe = '"+groupe+ "'");

        if (l.size() > 0) {
            return l;
        }

        return null;    }

    @Override
    public List<Permission> findPermissionsByGroupe(Groupe g) {
        
         List<Permission> listOfCh = null;
        
        Session s = this.getHibernateTemplate().getSessionFactory().openSession();
        try {
            //demarrage de la session
            s.getTransaction().begin();

            String hql = "select p from Permission p join p.groups l where l.id =" + g.getId();

            Query query = s.createQuery(hql);

            listOfCh = query.list();

            System.out.println("LIST OF PERMISSIONS GOT DAO ::: " + listOfCh);

            s.getTransaction().commit();
            s.close();
        } catch (Exception e) {

            s.getTransaction().rollback();
        }

        return listOfCh;
    }
}
