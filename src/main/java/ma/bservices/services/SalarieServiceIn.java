/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.services;

import java.util.List;
import ma.bservices.beans.Salarie;
import ma.bservices.tgcc.Entity.Mensuel;

/**
 *
 * @author j.allali
 */
public interface SalarieServiceIn {

    public Boolean affecterSupp(Salarie s, Salarie supp);

    /**
     *
     * @param s
     * @return
     */
    public Salarie addSalarie(Salarie s);

    public Salarie deleteSalarie(Salarie s);

    public Boolean deleteSupp(Salarie s);

    public List<Salarie> listNotAffected();

    public List<Salarie> listSalarieBySupp(Salarie s);

    public List<Salarie> listSupp();

    public List<Salarie> listSalarieByChantierId(int idChantier);
    /**
     * recuperer la liste des chefs d'équipes Quinzainiers qu'ils ont en etat
     * Actif ou Actif Provisoir
     *
     * @return la liste des chef d'équipes
     */
    public List<Salarie> getSalarieChefEquipe();

    /**
     * recuperer la liste des chefs d'équipes Mensuels qu'ils ont en etat Actif
     * ou Actif Provisoir
     *
     * @return la liste des chef d'équipes
     */
    public List<Mensuel> getMensuelChefEquipe();
    
    /**
     * Retourne la liste des mensuels chef d'équipe dans un chantier envoyé en paramètressss
     * 
     * @param idChantier
     * @return List<Mensuel>
     */
    public List<Mensuel> getMensuelChefEquipeInChantier(int chantier);

    public List<Salarie> listSalarieBySupp();

    public Salarie updateSalarie(Salarie s);

//    public List<Salarie> getSalarieChefEquipeByChantier(int idChantier);
    public List<Salarie> getSalarieNotChef(Integer idc, Integer etatID);
    
    /**
     * 
     * Recupère de la base de données la liste des salarié avec le type de pointage : TYPE_FONCTION_POINTAGE_UPSIT
     * dans une chantier en paramètere, qui ne sont pas chef d'équipe et qui sont en état Actif ou Actif provisoire.
     * 
     * @param idc Id du chantier
     * @return List<Salarie>
    */
    public List<Salarie> getActifSalarie(Integer idc);
    
    public List<String> distinct_Salarie_Ville(); 
    
    public List<Object> getSalarieHearchChef(Integer idChantier, Integer idChef);
    public Salarie getSalarieByID(Integer idc);
    
    public List<Salarie> listSalarieByListChantier(String listChantiers);
    public List<Salarie> listSalarieBlackListSorti();
}
