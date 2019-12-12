/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.services;

import java.util.List;
import ma.bservices.beans.NiveauFonction;

/**
 *
 * @author admin
 */
public interface NiveauFonctionService {

    /**
     *
     * @return la liste des niveaux
     */
    public List<NiveauFonction> getAll();

    public NiveauFonction getById(Integer id);
    
    public NiveauFonction getByString(String lib);

    public boolean ajouter(NiveauFonction nf);

    public boolean modifier(NiveauFonction nf);

    public boolean supprimer(NiveauFonction nf);
    
    public List<NiveauFonction> findByPriority(Integer base_priority);
}
