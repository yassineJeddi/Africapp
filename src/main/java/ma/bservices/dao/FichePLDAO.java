/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.dao;

import java.util.Date;
import java.util.List;
import ma.bservices.beans.FichePointageLot;

/**
 *
 * @author j.allali
 */
public interface FichePLDAO {

    public FichePointageLot saveFiche(FichePointageLot fiche);

    public boolean updateFiche(FichePointageLot fiche);

    public List<FichePointageLot> findAll();

    public FichePointageLot getById(Long id);
    
    public List<FichePointageLot> findFicheByChefDateChantier(Integer idChantier, Integer idChef, Date datePointage);

}
