/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.dao.engin;

import java.io.Serializable;
import ma.bservices.tgcc.Entity.Panne;
import ma.bservices.tgcc.utilitaire.MbHibernateDaoSupport;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository("panneDAO")
@Transactional
public class PanneDAOImpl extends MbHibernateDaoSupport implements PanneDAO, Serializable {

    /**
     * ajoute une panne dans l'historique des pannes
     *
     * @param panne
     */
    @Override
    @Transactional
    public void add(Panne panne) {

        try {
            
        System.out.println("***********> DEBUT Add panne <******************");
        System.out.println("***********> panne : "+panne.toString());
        // this.getHibernateTemplate().save(panne);
        

        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);

        session.beginTransaction();
        session.save(panne);

        session.getTransaction().commit();
        //session.close();
        System.out.println("***********> FRIN  Add panne <******************");
        
        } catch (Exception e) {
            System.out.println("Erreur d'enregistrement de la panne car : "+e.getMessage());
        }
    }

    @Override
    @Transactional
    public void update(Panne panne) {

//        this.getHibernateTemplate().saveOrUpdate(panne);
        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);

        session.beginTransaction();
        session.update(panne);

        session.getTransaction().commit();
        session.close();
    }

}
