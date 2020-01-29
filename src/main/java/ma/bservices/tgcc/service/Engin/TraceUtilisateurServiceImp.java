/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.service.Engin;

import java.io.Serializable;
import java.util.List;
import ma.bservices.tgcc.Entity.TraceBonLivraisonCiterne;
import ma.bservices.tgcc.Entity.TraceUtilisateur;
import ma.bservices.tgcc.dao.engin.ITraceUtilisateurDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author yassine
 */
@Service("traceUtilisateurService")
public class TraceUtilisateurServiceImp  implements ITraceUtilisateurService, Serializable{
    
    
    @Autowired
    private ITraceUtilisateurDao traceUtilisateurDao;

    public ITraceUtilisateurDao getTraceUtilisateurDao() {
        return traceUtilisateurDao;
    }

    public void setTraceUtilisateurDao(ITraceUtilisateurDao traceUtilisateurDao) {
        this.traceUtilisateurDao = traceUtilisateurDao;
    }
    
    

    @Override
    public void addTraceUtilisateur(TraceUtilisateur t) {
        traceUtilisateurDao.addTraceUtilisateur(t);
    }


    @Override
    public void addTraceBonLivraisonCiterne(TraceBonLivraisonCiterne t) {
        traceUtilisateurDao.addTraceBonLivraisonCiterne(t);
    }
    
    @Override
    public List<TraceUtilisateur> allTraceUtilisateur() {
        return traceUtilisateurDao.allTraceUtilisateur();
    }
    
}
