/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.dao.Mensuel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import ma.bservices.beans.TypeDocument;
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
@Repository("typeDocumentDAO")
@Transactional
public class TypeDocumentDAOImpl extends MbHibernateDaoSupport implements TypeDocumentDAO, Serializable {

    /**
     * recupere list des types de documents
     */
    @Override
    public List<TypeDocument> find_all() {
        return this.getHibernateTemplate().loadAll(TypeDocument.class);
    }

    /**
     * methode c pour recupere list des type document
     *
     * @return
     */
    @Override
    public List<String> l_type_docs() {
        List l = this.getHibernateTemplate().find(" select distinct(typeDocument) From TypeDocument ");

        return l;
    }

    @Override
    public TypeDocument type_doc(String type) {

        List l = null;
        l = this.getHibernateTemplate().find(" select t From TypeDocument t  where t.typeDocument =  '" + type + "'");

        if (l.size() > 0) {
            return (TypeDocument) l.get(0);
        }
        return null;

    }
    
      @Override
    public List<TypeDocument> findDocsByFonction(Integer id_fonction) {

        List<TypeDocument> docs = new ArrayList<TypeDocument>();

        Session s = this.getHibernateTemplate().getSessionFactory().openSession();
        try {
            //demarrage de la session
            s.getTransaction().begin();
//            String hql="select m from Mensuel m , in(m.loyerSalaries) l where m.id ="+id;
//            select p from ModPm p join p.modScopeTypes type where type.scopeTypeId = 1
            String hql = "select t from TypeDocument t join t.fonctions f where f.id =" + id_fonction;

            Query query = s.createQuery(hql);

            docs = query.list();

            s.getTransaction().commit();
            s.close();
        } catch (Exception e) {

            s.getTransaction().rollback();
        }

        return docs;
    }
    
    

    @Override
    public List<TypeDocument> l_docs_Obligatoire() {

        List l = this.getHibernateTemplate().find(" select t From TypeDocument t where t.obligatoir = true");

        return l;
    }

    @Override
    public List<TypeDocument> l_docs_Non_Obligatoir() {
        List l = this.getHibernateTemplate().find(" select t From TypeDocument t where t.obligatoir = false ");

        return l;
    }

    @Override
    public void update(TypeDocument typeDoc) {

        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);

        session.beginTransaction();
        session.save(typeDoc);

        session.getTransaction().commit();

        session.close();
    }

}
