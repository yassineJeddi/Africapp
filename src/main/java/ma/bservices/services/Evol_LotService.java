/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.services;

import java.util.List;
import ma.bservices.beans.Lot;

/**
 *
 * @author j.allali
 */
public interface Evol_LotService {

    public List<Lot> findAll();

    public void updateLot(Lot upLot);

    public Boolean deleteLot(int id);

    public Boolean insertLot(Lot insLot);

    /**
     * recupérer la liste des lots qui n'ont pas rattacher a une fonction
     *
     * @param idFonction identifiant de la fonction
     * @return une listes des lots
     */
    public List<Lot> findNotHaveFonction(Integer idFonction);

    /**
     * recupérer la liste des lots qui ont rattacher a une fonction
     *
     * @param idFonction identifiant de la fonction
     * @return la liste des lots par un fonction passé en parametre
     */
    public List<Lot> lotByFonction(Integer idFonction);
    
    /**
     * Get le chemin des icons de chaque lot
     * @param label
     * @return 
     */
    public Lot findIconByLabelLot(String label);

}
