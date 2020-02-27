/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.dao;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import ma.bservices.beans.Presence;
import ma.bservices.mb.services.MbHibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author yassine
 */
@Repository("presenceDao")
@Transactional
public class PresenceDaoImp extends MbHibernateDaoSupport implements IPresenceDao,Serializable {

    @Override
    public List<Presence> allPresenceByChantier(Integer chantierId) {
        
        List<Presence> l= new ArrayList<Presence>();
        try {
            l = (List<Presence>) this.getHibernateTemplate().find("FROM Presence WHERE chantier.id="+chantierId);
        } catch (Exception e) {
            System.out.println("Erreur de recuperation allPresenceByChantier car "+e.getMessage());
        }
        return l;
    }

    @Override
    public List<Presence> allPresenceByChantierAndDate(Integer chantierId, Date dd, Date df) {
        List<Presence> l= new ArrayList<Presence>();
           try {     
               SimpleDateFormat  sdf = new SimpleDateFormat ("yyyyMMdd");
                String rq="";
                if (dd != null) {
                    rq += " AND p.date >= '"+sdf.format(dd)+"'" ;
                    //queryRecherche += " AND p.date >= '"+dateDe+"'" ;
                }
                if (df != null) {  
                    rq += " AND p.date <=  '"+sdf.format(df)+"'";
                   // queryRecherche += " AND p.date <=  '"+dateA+"'";
                }
            l = (List<Presence>) this.getHibernateTemplate().find("FROM Presence p WHERE p.chantier.id="+chantierId+" "+rq);
        } catch (Exception e) {
            System.out.println("Erreur de recuperation allPresenceByChantierAndDate car "+e.getMessage());
        }
        return l;
    }

    @Override
    public List<Presence> allPresenceBySalarie(Integer salarieId) {
        List<Presence> l= new ArrayList<Presence>();
        try {
            l = (List<Presence>) this.getHibernateTemplate().find("FROM Presence WHERE salarie.id="+salarieId+" order by date desc");
        } catch (Exception e) {
            System.out.println("Erreur de recuperation allPresenceBySalarie car "+e.getMessage());
        }
        return l;
    }
    
}
