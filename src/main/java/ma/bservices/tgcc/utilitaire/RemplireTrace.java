/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.utilitaire;

import java.util.Date;
import ma.bservices.tgcc.Entity.Bon_Livraison_Citerne;
import ma.bservices.tgcc.Entity.TraceBonLivraisonCiterne;

/**
 *
 * @author yassine
 */
public class RemplireTrace {
    
    public TraceBonLivraisonCiterne remplirTraceBonLivraisonCiterne(Bon_Livraison_Citerne b,String utilisateur,String module){
        
        TraceBonLivraisonCiterne t = new TraceBonLivraisonCiterne();
        try {
            if(!b.equals(null)){
               
                t.setIdBonLivraisonCiterne(((b.getId().toString())==null)?"":b.getId().toString());
                t.setDate(b.getDate());
                t.setDateOperation(b.getDateOperation());
                t.setChemin_fichier((b.getChemin_fichier()==null)?"":b.getChemin_fichier());
                t.setVolume_actuel((b.getVolume_actuel()==null)?0:b.getVolume_actuel());
                t.setVolume((b.getVolume()==null)?0:b.getVolume());
                t.setCiterne((b.getCiterne().getLibelle_citerne()==null)?"":b.getCiterne().getLibelle_citerne());
                t.setEngin((b.getEngin().getCode()==null)?"":b.getEngin().getCode());
                t.setMensuel((b.getMensuel()==null)?"":(b.getMensuel().getNom()+" "+b.getMensuel().getPrenom()));
                t.setKilometrage((b.getKilometrage() == null)?"":b.getKilometrage());
                t.setHeure((b.getHeure()==null)?"":b.getHeure());
                t.setAction((b.getAction()==null)?"":b.getAction());
                t.setNumero_Livraison((b.getNumero_Livraison()==null)?"":b.getNumero_Livraison());
                t.setNumero_commande((b.getNumero_commande()==null)?"":b.getNumero_commande());
                t.setCommentaire((b.getCommentaire()==null)?"":b.getCommentaire());
                t.setNumBon((b.getNumBon()==null)?"":b.getNumBon());
                t.setDateAction(new Date());
                t.setUtilisateur(utilisateur);
                t.setModule(module);
                
            }
            
        } catch (Exception e) {
            System.out.println("ERREUR DE REMPLIRE TRACE BON LIVRAISON CAR "+e.getMessage());
        }
        
        return t;
        
    }
}
