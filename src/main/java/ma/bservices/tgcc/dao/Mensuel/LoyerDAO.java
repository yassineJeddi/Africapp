/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.dao.Mensuel;

import java.util.Date;
import java.util.List;
import ma.bservices.tgcc.Entity.BonCaisse;
import ma.bservices.beans.Chantier;
import ma.bservices.beans.Proprietaire;
import ma.bservices.tgcc.Entity.FournisseurLoyer;
import ma.bservices.tgcc.Entity.Loyer;
import ma.bservices.tgcc.Entity.LoyerChantier;
import ma.bservices.tgcc.Entity.LoyerSalarie;
import ma.bservices.tgcc.Entity.Mensuel;
import ma.bservices.tgcc.Entity.Telephone;

/**
 *
 * @author a.wattah
 */
public interface LoyerDAO {

    public List<Mensuel> findAll();

    public List<BonCaisse> findAllBonCaiss();
    
    public LoyerSalarie findOneLSById(Integer id);
    
    public LoyerChantier findOneLCById(Integer id);

    public List<Mensuel> findALLl();
    
    public void update(Loyer ls);

    public List<Mensuel> AfficherListMensuelByidLoyer(Long id);

    public List<LoyerSalarie> getListLoyerSalarieByIdMensuelPrincipal(int id);

    public List<Mensuel> search(int id, String matriculeB, String nomB, String dateDebut);

    public List<Mensuel> Consult(int id);

    public List<LoyerSalarie> getListLoyerSalarieByIdMensuel(int id);

    public List<Mensuel> rechercherMensuel(String matricule, String nom, String prenom, String fonction, String dateDebut);

    public BonCaisse findAllBonCaisseByIdMensuel(int id);

    public void saveAffectation(LoyerChantier loyer);

    public List<Chantier> ConsultChantier(int id);

    public void saveAffectation(LoyerSalarie loyer);

    public void deleteLoyerChantier(LoyerChantier loyer);

    public void saveLoyerSalarie(LoyerSalarie loyer);

    public void deleteLoyerSalarie(LoyerSalarie loyer);

    public List<LoyerChantier> loyerChantiers();

    public List<LoyerSalarie> loyerSalaries();
    
    
     public List<LoyerSalarie> loyerSalariesByDateDeb(Date date);
     
     public List<LoyerChantier> loyerChantierByDateDeb(Date date);

    public List<Mensuel> findALLlBy_Id_Loyer(Long id);

    public List<LoyerChantier> rechercher_Loyer_Chantier_ByIdentifient_Date_Debut(Long identifiant, String date_Debut);

    public List<LoyerSalarie> rechercher_Loyer_Salarier_ByIdentifient_Date_Debut(Long identifiant, String date_Debut);

    /*
     * test uniatire 
     */
    public void deleteLoyerSalarie_Test_Unitaire(LoyerSalarie loyer);

    public List<LoyerChantier> getLoyerChantierByChantier(int id);

    public void desaffecterLoyerMensuel(Loyer loyer);
    
    
    public List<Loyer> findByNumContrat(String numContrat);

    public List<Proprietaire> getListproPrietaireDistinct();
    
    public List<FournisseurLoyer>  listFournisseurLoyer();
    
}
