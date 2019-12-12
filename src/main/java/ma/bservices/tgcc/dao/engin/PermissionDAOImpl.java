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
@Repository("permissionDAO")
@Transactional
public class PermissionDAOImpl extends HibernateDaoSupport implements PermissionDAO {

    @Override
    public List<Permission> findAll() {
        return this.getHibernateTemplate().loadAll(Permission.class);
    }

    @Override
    public Permission findById(String id) {
        List l = this.getHibernateTemplate().find("SELECT p FROM Permission p WHERE p.id = " + id);

        if (l.size() > 0) {

            return (Permission) l.get(0);
        }

        return null;
    }

    @Override
    public Boolean ajouterPermission(Permission permission) {

        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);

        session.beginTransaction();
        session.save(permission);

        session.getTransaction().commit();

        session.close();
        return true;
    }

    @Override
    public Boolean delete(int id) {
        Session s = this.getHibernateTemplate().getSessionFactory().getCurrentSession();

        Query query = s.createQuery("delete from Permission where id=:id");
        query.setParameter("id", id);
        int deleted = query.executeUpdate();
        return true;
    }

    @Override
    public Boolean delete(Permission p) {
        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);

        session.beginTransaction();
        session.delete(p);

        session.getTransaction().commit();

        session.close();
        return true;
    }

    @Override
    public Boolean updatePermission(Permission permission) {
        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);

        session.beginTransaction();
        session.update(permission);

        session.getTransaction().commit();

        session.close();
        return true;
    }

    @Override
    public List<Permission> findByGroup(Groupe g) {
        List<Permission> listOfP = null;
        System.out.println("HERE DAAAAAAAAAAAAAAAAAAAAAAAAO");

        Session s = this.getHibernateTemplate().getSessionFactory().openSession();
        try {
            //demarrage de la session
            System.out.println("trying ============================");
            s.getTransaction().begin();

            String hql = "select p from Permission p join p.groups l where l.id =" + g.getId();

            Query query = s.createQuery(hql);

            listOfP = query.list();
            
            for(Permission p : listOfP){
                System.out.println("permission of user from dao  : " + p.getPermission());
            }
            System.out.println(" = = == = = == = LIST OF PERMISSIONS BY GROUPE DAO ::: " + listOfP);

            s.getTransaction().commit();
            s.close();
        } catch (Exception e) {

            s.getTransaction().rollback();
        }

        return listOfP;
    }

}
