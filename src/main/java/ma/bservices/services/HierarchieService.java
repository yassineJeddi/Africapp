/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.services;

import java.util.List;
import ma.bservices.beans.ChantierHierarchie;
import ma.bservices.beans.NiveauFonction;
import ma.bservices.beans.Salarie;
import ma.bservices.beans.SalarieChantier;

/**
 *
 * @author Mahdi
 */
public interface HierarchieService {

    /**
     * Ajouter un supérieur a un salarie dans un chantier
     *
     * @param ch L'objet qu'on veut ajouter
     * @return true si opération effectuée sinon false
     */
    public boolean save(ChantierHierarchie ch);

    /**
     * supprimer un élement dans l'hierarchie d'un chantier
     *
     * @param ch l'objet qu'on veut supprimer
     * @return true si opération reussite sinon false
     */
    public boolean delete(ChantierHierarchie ch);

    /**
     * recuperer la liste de tout les hierarchie de tous les chantiers
     *
     * @return une liste des Hierarchies
     */
    public List<ChantierHierarchie> loadAll();

    /**
     * recupérer une hirarchie données d'un chantier
     *
     * @param idHierarchie identifiant d'hierarchie
     * @return chantier hierarchie
     */
    public ChantierHierarchie getById(Integer idHierarchie);

    /**
     * permet de recupérer la liste des salariés dans un chantier qui peuvent
     * être des supérieur a d'autre salariés. donc les salariés qui ont une
     * fonction classé dans un niveau et aussi ils sont affectés a un chantier
     *
     * @param idChantier identifiant du chantier
     * @return liste des salariés Cadre dans un chantier
     */
    public List<Salarie> getSalarieCadre(Integer idChantier);

    /**
     * recupérer la liste des salaries qui peuvent être supérieur a un salarié
     * données dans un chantier données
     *
     * @param idChantier identifiant du chantier
     * @param niveau niveau du fonction du salarie
     * @return la listes des salaries superieur a un salarié
     */
    public List<Salarie> getSalarieSuperieur(Integer idChantier, NiveauFonction niveau);

    /**
     * recuperer hierarchie par Id SalarieChantier
     *
     * @param sc SalarieChantier
     * @return ChantierHierarchie
     */
    public ChantierHierarchie getById(SalarieChantier sc);

    /**
     * get la list des salaries ayant une fonction a un niveau donnée
     *
     * @param niveau niveau de la fonction rechercher
     * @param idChantier identifiant chantier
     * @return liste des salaries
     */
    public List<Salarie> listSalarieByNiveauAndChantier(NiveauFonction niveau, Integer idChantier);

}
