/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.dao;

import java.util.List;
import ma.bservices.beans.Salarie;
import ma.bservices.tgcc.Entity.Mensuel;

/**
 *
 * @author j.allali
 */
public interface SalarieDao {

    public Boolean affecterSupp(Salarie s, Salarie supp);

    /**
     *
     * @param Ajouter la methode addSalarie et deleteSalarie pour le test
     * unitaire
     * @return
     */
    public Salarie addSalarie(Salarie s);

    public Salarie deleteSalarie(Salarie s);

    public Boolean deleteSupp(Salarie s);
    
    public List<Salarie> getActifSalarieWithSup(Integer idc);

    public List<Salarie> listNotAffected();

    public List<Salarie> listSalarieBySupp(Salarie s);

    public List<Salarie> listSalarieBySupp();

    public List<Salarie> listSupp();

    List<Salarie> listSalarieByChantierId(int idChantier);
    
    public List<Salarie> getSalarieChefEquipe();

    public List<Mensuel> getMensuelsChefEquipe();
    
    public List<Salarie> getSalariesbyOrganigram(Integer id);

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
    
    /**
     * Retourne la liste des mensuels chef d'équipe dans un chantier envoyé en paramètre
     * 
     * @param idChantier
     * @return List<Mensuel>
     */
    public List<Mensuel> getMensuelsChefEquipeInChantier(int idChantier);

    public List<String> getListVilleDistinct();
    
    public List<Object> getSalarieHearchChef(Integer idChantier, Integer idChef);
    public Salarie getSalarieByID(Integer idc);
    
    public List<Salarie> listSalarieByListChantier(String listChantiers);
    public List<Salarie> listSalarieBlackListSorti();
    public List<Salarie> listSalarieActifByChantierId(int idChantier);
}
