/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.dao;

import java.util.List;
import ma.bservices.beans.Fonction;
import ma.bservices.beans.FonctionLocal;

/**
 *
 * @author Mahdi
 */
public interface FonctionDao {

    /**
     *
     * @param f fonction a ajoutée
     * @return true si insertion effectuée sinon false
     */
    public boolean Add(FonctionLocal f);

    /**
     *
     * @param f fonction a supprimée
     * @return true si suppression effectuée , false sinon
     */
    public boolean delete(FonctionLocal f);

    /**
     *
     * @return liste des fonctions
     */
    public List<FonctionLocal> getAll();

    /**
     *
     * @param f fonction a modifiée
     * @return true modification effectuée , false sinon
     */
    public boolean update(FonctionLocal f);

    /**
     *
     * @return la liste des fonctions qui n'ont pas un niveau
     */
    public List<Fonction> NotHaveNiveau();

    /**
     * recuperer la fonction local
     *
     * @param idFonction identifiant de la fonction qu'on veut recupérer
     * @return une fonction
     */
    public FonctionLocal getById(Integer idFonction);
}
