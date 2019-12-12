/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.paginate;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import ma.bservices.beans.Chantier;
import ma.bservices.beans.HeuresSupplementaires;
import ma.bservices.beans.Salarie;
import ma.bservices.beans.Utilisateur;
import ma.bservices.mb.services.Module;

import ma.bservices.services.HeuresSupplementairesService;
import ma.bservices.services.SalarieService;
import ma.bservices.tgcc.service.Engin.ChantierService;

/**
 *
 * @author Mahdi
 */
public class HeureSupPagination {

    private static HeuresSupplementairesService IntegerHeuresSupplementairesService;
    private static ChantierService chantierService;
    private static int i;

    public int getI() {
        return i;
    }

    public void setI(int i) {
        HeureSupPagination.i = i;
    }

    static {
        if (IntegerHeuresSupplementairesService == null) {
            IntegerHeuresSupplementairesService = Module.ctx.getBean(HeuresSupplementairesService.class);
        }
          if (chantierService == null) {
            chantierService = Module.ctx.getBean(ChantierService.class);
        }
    }
    
    
    /**
     * retourne une liste de chantier qu'on doit afficher dans le resultat de la liste des heure supplentaires
     * @param chantier
     * @param user
     * @return 
     */
    public static List<Integer> getChantierForSeach(Integer chantier, Utilisateur user){
        LinkedList<Integer> lChantiers = new LinkedList<>();//liste des ids des chantiers
        LinkedList<Integer> lChantiersForSearch = null;
        LinkedList<Chantier> lUserChantiers = null; //liste des chantiers de l'utilisateur
        
        if (user != null) {
            lUserChantiers = new LinkedList<>(user.getChantiers());
            
            for (int i = 0; i < lUserChantiers.size(); i++) {
                lChantiers.add(lUserChantiers.get(i).getId());
            }
        }
        else
        {
        lUserChantiers = new LinkedList<>(chantierService.findAll());
        
        for (int i = 0; i < lUserChantiers.size(); i++) {
                lChantiers.add(lUserChantiers.get(i).getId());
            }
        }
        
        // on verifie si le chantier de la recherche est dans la liste des chantiers de l'utilisateur
        // && user , quand il est admin l'utlisateur est null
        if(chantier != null && lChantiers.contains(chantier)){ //si le chantier est dans la liste d'utlisateur on utilise le chantier
            lChantiersForSearch = new LinkedList<>();
            lChantiersForSearch.add(chantier);
            
            
        } else if( chantier == null && lChantiers.size() > 0 ){ // si le chantier est null et la lise des chantiers de l'utilisateur ne l'est pas on prend tous les chantiers
            lChantiersForSearch = lChantiers;
        }
        
        return lChantiersForSearch; //liste des chantiers pour la recherche
    }

    /**
     * retourne la première partie de la liste des HeureSup
     *
     * @param matricule
     * @param statut
     * @param fonction
     * @param etat
     * @param cin
     * @param nom
     * @param prenom
     * @param cnss
     * @param codeChantier
     * @param matriculeNova
     * @return
     */
    public static List<HeuresSupplementaires> first(String matricule, String cin, String cnss, Integer etat,
            Integer chantier, Date date, Utilisateur user) {

        
        List<Integer> chantiers = getChantierForSeach(chantier, user);
        

        i = IntegerHeuresSupplementairesService.nombreHS((matricule == null) ? "" : matricule, (cin == null) ? "" : cin, (cnss == null) ? "" : cnss, etat,
                chantiers, date);

        return IntegerHeuresSupplementairesService.listeHeuresSupplementaires(0, 10, (matricule == null) ? "" : matricule, (cin == null) ? "" : cin, (cnss == null) ? "" : cnss, etat,
                chantiers, date);
    }

    /**
     * liste des heure sup suivant
     *
     * @param matricule
     * @param cin
     * @param cnss
     * @param etat
     * @param chantier
     * @param date
     * @return
     */
    public static List<HeuresSupplementaires> last(String matricule, String cin, String cnss, Integer etat,
            Integer chantier, Date date, Utilisateur user) {

        List<Integer> chantiers = getChantierForSeach(chantier, user);
        
        i = IntegerHeuresSupplementairesService.nombreHS((matricule == null) ? "" : matricule, (cin == null) ? "" : cin, (cnss == null) ? "" : cnss, etat,
                chantiers, date);

        int p = i / 10;

        return IntegerHeuresSupplementairesService.listeHeuresSupplementaires(p * 10, 10, (matricule == null) ? "" : matricule, (cin == null) ? "" : cin, (cnss == null) ? "" : cnss, etat,
                chantiers, date);
    }

    /**
     * retourne la liste des heure sup par page
     *
     * @param index
     * @param matricule
     * @param cin
     * @param cnss
     * @param etat
     * @param chantier
     * @param date
     * @param user
     * @return
     */
    public static List<HeuresSupplementaires> page(Integer index, String matricule, String cin, String cnss, Integer etat,
            Integer chantier, Date date, Utilisateur user) {

        List<Integer> chantiers = getChantierForSeach(chantier, user);
        
                
        i = IntegerHeuresSupplementairesService.nombreHS((matricule == null) ? "" : matricule, (cin == null) ? "" : cin, (cnss == null) ? "" : cnss, etat,
                chantiers, date);
        
        
        


        int p = (index > 0) ? (index - 1) * 10 : 0;
        
        System.out.println("********************************** START ******************** :: " + p);

        return IntegerHeuresSupplementairesService.listeHeuresSupplementaires(p, 10, (matricule == null) ? "" : matricule, (cin == null) ? "" : cin, (cnss == null) ? "" : cnss, etat,
                chantiers, date);

    }
}
