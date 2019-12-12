/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.dao;

import java.util.List;
import ma.bservices.beans.NiveauFonction;

/**
 *
 * @author admin
 */
public interface NiveauFonctionDao {

    public List<NiveauFonction> findAll();
    
     public List<NiveauFonction> findByPriority(Integer p);

    public NiveauFonction findById(Integer idniveau);

    public NiveauFonction findByName(String niveau);

    public boolean addNiveau(NiveauFonction nf);

    public boolean deleteNiveau(NiveauFonction nf);

    public boolean updateNiveau(NiveauFonction nf);
}
