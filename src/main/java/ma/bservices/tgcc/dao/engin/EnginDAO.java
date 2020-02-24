/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.dao.engin;

import java.util.List;
import ma.bservices.beans.Salarie;
import ma.bservices.tgcc.Entity.ECHEANCIER_VIDANGE;
import ma.bservices.tgcc.Entity.Engin;
import ma.bservices.tgcc.Entity.InterventionMaintenance;
import ma.bservices.tgcc.Entity.Panne;
import ma.bservices.tgcc.Entity.ReferentielEngin;

/**
 *
 * @author zakaria.dem
 */
public interface EnginDAO {

    
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
    public void updateEngin(Engin engin) ;
    public void updateListEngin(List<Engin> engin);
     
    public List<Engin> rechercherEngin(String code, String designation, String marque, String etat, int chantier_id);

    public void ajouterEngin(Engin engin);
    
    public Engin findOneById(Integer id);

    public void affecterEngin(Engin engin);
    
    public void affecterEnginTestunit(Engin engin);
    public Panne lastPanneByEngin(Engin engin);
    
    public Engin findOneByCode(String code);
    public List<String> findAllMarque();
    public List<Engin> findAllInChantier(Integer chantier_id);
    public List<Engin> findAllInChantierArchive(int chantier_id);
    public List<Engin> findAllChantierArchivePanne(int chantier_id);
    public List<Engin> findAllEnginPointageAutoParChantier(int chantier_id);
    public List<Panne> lastPanneByEnginPanne();
    
    public List<Salarie> allConducteur();
    
    
    public List<Engin> findAllEnginPointageAutoDept(int chantier_id);
    public List<Engin> findAllEnginPointageManuelDept(int chantier_id);
    
    
    public Boolean delete(Engin e);
    public List<Engin> findOneByArchive();
    public List<Engin> rechercherEnginByFa(String code, String designation, String marque, String etat, int chantier_id, String typeE, String familleE);
    public List<Engin> findAllEnginByChantierId(Integer chantier_id);
    
    
    public void addReferentielEngin(ReferentielEngin r);
    public void editReferentielEngin(ReferentielEngin r);
    public void remouvReferentielEngin(ReferentielEngin r);
    public List<ReferentielEngin> allReferentielEnginByEngin(Engin e);
    
}
