/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.dao.Mensuel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import ma.bservices.beans.Document;
import ma.bservices.tgcc.Entity.Voiture;
import ma.bservices.tgcc.utilitaire.MbHibernateDaoSupport;
import org.hibernate.FlushMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author a.wattah
 */
@Repository("documentDAO")
@Transactional
public class DocumentDAOImpl extends MbHibernateDaoSupport implements DocumentDAO, Serializable {

    @Override
    public List<Document> findAll() {
        return this.getHibernateTemplate().loadAll(Document.class);
    }

    @Override
    public Boolean save(Document document) {

        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);

        session.beginTransaction();
        session.save(document);

        session.getTransaction().commit();

        session.close();
        return true;
    }

    @Override
    public Boolean deleteDocument(Document a) {

        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);

        session.beginTransaction();
        session.delete(a);

        session.getTransaction().commit();

        session.close();
        return true;
    }

    @Override
    public Boolean deleteVoiture(Voiture a) {
        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);

        session.beginTransaction();
        session.delete(a);

        session.getTransaction().commit();

        session.close();
        return true;
    }

    @Override
    public List<Document> getDocumentByLoyer(Long id) {
        List<Document> l = new ArrayList<Document>();
        try {
            if (id != null) {
                 l = (List<Document>) this.getHibernateTemplate().find("SELECT c FROM Document c WHERE c.loyer.id = " + id);
            }
        } catch (Exception e) {
            logger.error("Erreur de récupération les documents par Loyer car "+e.getMessage());
        }
        System.out.println("Liste des documment est : "+l);
        return l;
    }

    @Override
    public List<Document> getDocumentByVoiture(Long id) {
        List<Document> l = new ArrayList<Document>();
        try {
            if (id != null) {
                 l = (List<Document>) this.getHibernateTemplate().find("SELECT c FROM Document c WHERE c.voiture.id = " + id);
            }
        } catch (Exception e) {
            logger.error("Erreur de récupération les documents par voiture car "+e.getMessage());
        }

        return l;
    }
    
    @Override
    public List<Document> getDocumentByCarteGZ(Long id) {
        List<Document> l = new ArrayList<Document>();
        try {
            if (id != null) {
                 l = (List<Document>) this.getHibernateTemplate().find("SELECT c FROM Document c WHERE c.carteGasoil.id = " + id);
            }
        } catch (Exception e) {
            logger.error("Erreur de récupération les documents par carteGasoil car "+e.getMessage());
        }

        return l;
    }

    @Override
    public List<Document> getListDocumentById(Integer id) {
        List l = this.getHibernateTemplate().find("FROM Document WHERE salarie.id = " + id);
        //System.out.println("liste taille document " + l.size());
        return l;

    }

    @Override
    public void update(Document document) {

        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);

        session.beginTransaction();
        session.update(document);

        session.getTransaction().commit();

        session.close();
    }

    @Override
    public List<Document> findAllNoLocation() {
        List l = this.getHibernateTemplate().find("SELECT c FROM Document c WHERE chemin is null ");
        if (!l.isEmpty()) {
            return l;
        } else {
            return null;
        }
    }

}
