/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.services;

import java.util.List;
import ma.bservices.beans.Fonction;
import ma.bservices.beans.FonctionLocal;

/**
 *
 * @author Mahdi
 */
public interface FonctionService {

    /**
     * affecter une fonction a un niveau
     *
     * @param f fonction a affecter
     * @return true si affectation effectuée et false sinon
     */
    public boolean ajouterFonction(FonctionLocal f);

    /**
     * modifier une fonction
     *
     * @param f fonction a modifier
     * @return true si modification effectuée et false sinon
     */
    public boolean modifierFonction(FonctionLocal f);

    public boolean supprimerFonction(FonctionLocal f);

    /**
     *
     * @return la liste des fonctions
     */
    public List<FonctionLocal> loadAll();

    /**
     * la liste des fonctions qui n'ont pas de niveau
     *
     * @return une liste des fonctions
     */
    public List<Fonction> nonAffecter();

    /**
     * recuperer la fonction local
     *
     * @param idFonction identifiant de la fonction qu'on veut recupérer
     * @return une fonction
     */
    public FonctionLocal getById(Integer idFonction);
}
