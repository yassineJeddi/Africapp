/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.dao.stock;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import ma.bservices.tgcc.Entity.TransferStock;
import ma.bservices.tgcc.utilitaire.MbHibernateDaoSupport;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author IRAAMANE
 */
@Repository("transfertStockDAO")
@Transactional
public class TransferStockDAOImpl extends MbHibernateDaoSupport implements TransferStockDAO, Serializable {

    @Override
    public List<TransferStock> findAll() {
        List l = this.getHibernateTemplate().find("SELECT s FROM TransferStock s WHERE s.isRetour = '" + false + "'");
        return l.size() > 0 ? l : null;
    }
    
      @Override
    public List<TransferStock> findAllBoth() {
        List l = this.getHibernateTemplate().find("SELECT s FROM TransferStock s");
        if(l.size()>0)
            return l;
        else
            return null;
    }
    
     @Override
    public List<TransferStock> findAllRetours() {
        List l = this.getHibernateTemplate().find("SELECT s FROM TransferStock s WHERE s.isRetour = '" + true + "'");
        return l.size() > 0 ? l : null;
    }

    @Override
    @Transactional
    public void addTransfertStock(TransferStock transferStock) {
        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);
        session.beginTransaction();
        session.save(transferStock);
        session.getTransaction().commit();
        session.close();
    }
    
    @Override
    @Transactional
    public void updateTransfertStock(TransferStock transferStock) {
        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);
        session.beginTransaction();
        session.update(transferStock);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void receptTransfertStock(TransferStock transferStock) {
        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);
        session.beginTransaction();
        session.update(session.merge(transferStock));
        session.getTransaction().commit();
        session.close();
    }
    
      @Override
    public void removeTransferStock(TransferStock transferStock) {
        Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.AUTO);
        session.beginTransaction();
        session.delete(transferStock);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List<TransferStock> findById(int id, int chantier_id) {
        List l = this.getHibernateTemplate().find("SELECT s FROM TransferStock s WHERE s.referenceTransfer = '" + id + "' AND s.chantierRecepteurId = '"+chantier_id+"'AND s.isRetour = '"+false+"' AND s.quantiteReception > 0");
        if(l.size()>0)
            return l;
        else
            return null;
    }
    
     @Override
    public List<TransferStock> findByRef(int ref) {
        List l = this.getHibernateTemplate().find("SELECT s FROM TransferStock s WHERE s.referenceTransfer = '" + ref + "' AND s.isRetour = '"+false+"'");
        if(l.size()>0)
            return l;
        else
            return null;
    }

    @Override
    public List<TransferStock> findByStatus(int status_id) {
        List l = this.getHibernateTemplate().find("SELECT s.dateTransferStock, s.dateReceptionStock, s.quantite, s.quantiteReception,s.quantiteRetour, s.isRetour, s.referenceTransfer, s.chantierEmetteurId, s.chantierRecepteurId, s.articleId, s.statusTransferId FROM TransferStock s WHERE s.statusTransferId.idStatusTransfert = '" + status_id + "'");
        return l.size() > 0 ? l : null;
    }

    @Override
    public List<TransferStock> findByChantierEmetteur(int id_chantier_emetteur) {
        List l = this.getHibernateTemplate().find("SELECT s  FROM TransferStock s WHERE s.chantierEmetteurId.id = '" + id_chantier_emetteur + "' AND s.isRetour = false ");
        return l.size() > 0 ? l : null;
    }
    
     @Override
    public List<TransferStock> findRByChantierEmetteur(int id_chantier_emetteur) {
        List l = this.getHibernateTemplate().find("SELECT s FROM TransferStock s WHERE s.chantierEmetteurId.id = '" + id_chantier_emetteur + "' AND s.isRetour = true");
        return l.size() > 0 ? l : null;
    }

    @Override
    public List<TransferStock> findByChantierRecepteur(int id_chantier_recepteur) {
        List l = this.getHibernateTemplate().find("SELECT s FROM TransferStock s WHERE s.chantierRecepteurId.id = '" + id_chantier_recepteur + "' AND s.isRetour = false");
        return l.size() > 0 ? l : null;
    }
    
     @Override
    public List<TransferStock> findRByChantierRecepteur(int id_chantier_recepteur) {
        List l = this.getHibernateTemplate().find("SELECT s FROM TransferStock s WHERE s.chantierRecepteurId.id = '" + id_chantier_recepteur + "' AND s.isRetour = true");
        return l.size() > 0 ? l : null;
    }
    
        @Override
    public List<TransferStock> findByIntervalDate(int chantier_id, Date dateFrom, Date dateTo) {
        String dateFromString, dateToString;
         DateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
         dateFromString = formatDate.format(dateFrom);
         dateToString = formatDate.format(dateTo);
          System.out.println(formatDate.format(dateFrom));          
          System.out.println(formatDate.format(dateTo));
        //  return this.getHibernateTemplate().loadAll(AffectationStock.class);
        List l = this.getHibernateTemplate().find("SELECT s FROM TransferStock s WHERE s.dateTransferStock >= '" + dateFromString + "' AND s.dateTransferStock <= '" + dateToString + "' AND s.chantierEmetteurId.id = '" + chantier_id + "'");
        return l.size() > 0 ? l : null;
    }

}
