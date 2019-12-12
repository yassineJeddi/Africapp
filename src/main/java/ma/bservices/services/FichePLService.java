/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.services;

import java.util.Date;
import java.util.List;
import ma.bservices.beans.FichePointageLot;

/**
 *
 * @author j.allali
 */
public interface FichePLService {

    
    public FichePointageLot saveFiche(FichePointageLot fiche);

    /**
     * recherche dans la List des fiches de pointage par 3 critères
     *
     * @param IdChantier Identifiant de chantier
     * @param idChef identifiant de chef d'équipe
     * @param datePointage date de pointage
     * @return la liste des fiches de pointage
     */
    public List<FichePointageLot> findFicheByChefDateChantier(Integer IdChantier, Integer idChef, Date datePointage);

    /**
     * recuperer la liste de tous les fiche de pointage
     *sdzkd
     * @return list des fiches de pointage
     */
    public List<FichePointageLot> findAll();

    /**
     * recuperer un fiche de pointage selon le code
     *
     * @param code
     * @return tous la liste
     */
    public FichePointageLot getByCode(Long code);
/**
 * permettant de modifier une fiche
 * @param p fiche a modifier
 * @return true si modification effectuée sinon false
 */
    public boolean update(FichePointageLot p);
}
