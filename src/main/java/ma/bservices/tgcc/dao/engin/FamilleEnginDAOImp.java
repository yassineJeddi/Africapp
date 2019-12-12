package ma.bservices.tgcc.dao.engin;


import java.io.Serializable;
import java.util.List;
import ma.bservices.tgcc.Entity.FamilleEngin;
import ma.bservices.tgcc.dao.engin.IFamilleEnginDAO;
import ma.bservices.tgcc.utilitaire.MbHibernateDaoSupport;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author yassine.jeddi
 */
@Repository("familleEnginDAO")
@Transactional
public class FamilleEnginDAOImp extends MbHibernateDaoSupport implements Serializable, IFamilleEnginDAO {

    @Override
    @Transactional
    public void addFamilleEngin(FamilleEngin f) {
        
        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);
        session.beginTransaction();
        session.save(f);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List<FamilleEngin> allFamilleEngin() { 
        return this.getHibernateTemplate().loadAll(FamilleEngin.class);
    }

    @Override
    public FamilleEngin familleEnginByID(Integer id) {
        FamilleEngin f = new FamilleEngin();
        try {
            f=this.getHibernateTemplate().get(FamilleEngin.class, id);
        } catch (DataAccessException e) {
            logger.error("Erreur de récupération de voiture par id car "+e.getMessage());
        }
        return f;
    }
    
}
