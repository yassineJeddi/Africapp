/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.dao;

import java.util.List;
import ma.bservices.beans.ChantierHierarchie;
import ma.bservices.beans.SalarieChantier;

/**
 *
 * @author Mahdi
 */
public interface HierarchieDao {

    public boolean ajouter(ChantierHierarchie ch);

    public boolean supprimer(ChantierHierarchie ch);

    public List<ChantierHierarchie> gets();

    public ChantierHierarchie get(Integer idHierarchie);

    /**
     * get le salariechantier d'un organigramme
     *
     * @param sc
     * @return chantier Hierarchie
     */
    public ChantierHierarchie get(SalarieChantier sc);

}
