/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import ma.bservices.mb.services.MbHibernateDaoSupport;
import ma.bservices.tgcc.Entity.Attestation;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author yassine
 */

@Repository("attestationDaoImp")
@Transactional
public class AttestationDaoImp extends MbHibernateDaoSupport implements IAttestationDao, Serializable{

    @Override
    @Transactional
    public void addAtestation(Attestation a) {
        Transaction tx = null;
        Session session = getSessionFactory().openSession();
        try {
            session.setFlushMode(FlushMode.AUTO);
            tx = session.beginTransaction();
            session.save(a);
            tx.commit();
            session.close();
        } catch (Exception exp) {
            System.out.println("exception : " + exp.getMessage());
            tx.rollback();
            session.close();            
        }       
    }

    @Override
    public List<Attestation> allAtestationBySalarie(int idSalarie) {
        List<Attestation>  l = new ArrayList<Attestation>();
        try {
             l = (List<Attestation>) this.getHibernateTemplate().find("SELECT a FROM Attestation a where a.salarie.id = " + idSalarie);
        } catch (Exception e) {
            System.out.println("Erreur de récupération liste des attestation du salarie car "+e.getMessage());
        }
        return l;
    }
    

    @Override
    public List<Attestation> allAtestationChantier() {
        List<Attestation>  l = new ArrayList<Attestation>();
        try {
             l = (List<Attestation>) this.getHibernateTemplate().find("SELECT a FROM Attestation a where a.chantier.id is not null ");
        } catch (Exception e) {
            System.out.println("Erreur de récupération liste des attestation par chantier car "+e.getMessage());
        }
        return l;
    }
    
    
}
