/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.service.Engin;

import java.util.List;
import ma.bservices.beans.Salarie;
import ma.bservices.tgcc.Entity.ECHEANCIER_VIDANGE;
import ma.bservices.tgcc.Entity.Engin;
import ma.bservices.tgcc.Entity.InterventionMaintenance;
import ma.bservices.tgcc.Entity.Panne;

/**
 *
 * @author zakaria.dem
 */
public interface EnginService {

    public List<InterventionMaintenance> listIntervPr(int idEngin);
    public List<InterventionMaintenance> listIntervCr(int idEngin);
    public ECHEANCIER_VIDANGE lastEcheancierVidangeByCodeEngin(String code);
    
    public List<Engin> findAll();
    public List<Engin> enginsArchive();
    public List<Engin> enginsReforme();
    public List<Engin> enginsActif();    
    public List<Engin> enginsEnPanne(); 
    public List<Engin> enginsActifNoPanne();
    public String nextCod(String designiation,String codeD);
    public void updateEngin(Engin engin);
    public void updateListEngin(List<Engin> engin);
     
    public Engin findOneById(Integer id);

    public void addEngin(Engin engin, int chantier_id);

    public List<Engin> search(String code, String designation, String marque, String etat, int chantier_id);

    public void affecterEngin(Engin engin, int chantierID);

    public List<Engin> findAllInChantier(int chantier_id);

    public Engin findEnginByCodeFromEnginsInChantier(List<Engin> enginsInChanier, String Code);

    public List<Engin> findOneByArchive();

    public List<Engin> findAllChantierArchivePanne(int chantier_id);
    
    public List<Engin> findAllEnginPointageAutoParChantier(int chantier_id);

    public void updateArchiveEngin(Engin engin);

    public List<Engin> rechercherEnginByFa(String code, String designation, String marque, String etat, int chantier_id, String typeE, String familleE);

    public List<Engin> findAllInChantierArchive(int chantier_id);

    public List<String> findAllMarque();

    public List<Panne> lastPanneByEnginPanne();
    
    public Engin findOneByCode(String code);

    public Boolean addEnginPanne(Panne panne);
    public Panne lastPanneByEngin(Engin engin);

    public void mise_en_marche(Engin engin, int chantierID, Panne p);
    
    public List<Salarie> allConducteur();
    
    
    public List<Engin> findAllEnginPointageAutoDept(int chantier_id);
    public List<Engin> findAllEnginPointageManuelDept(int chantier_id);
    
}
