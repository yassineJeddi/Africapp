/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.dao.engin;

import java.util.List;
import ma.bservices.tgcc.Entity.TraceBonLivraisonCiterne;
import ma.bservices.tgcc.Entity.TraceUtilisateur;

/**
 *
 * @author yassine
 */
public interface ITraceUtilisateurDao {
    public void addTraceUtilisateur(TraceUtilisateur t);
    public void addTraceBonLivraisonCiterne(TraceBonLivraisonCiterne t);
    public List<TraceUtilisateur> allTraceUtilisateur();
    
}
