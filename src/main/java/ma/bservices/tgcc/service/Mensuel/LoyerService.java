/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.service.Mensuel;

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

/**
 *
 * @author a.wattah
 */
public interface LoyerService {

    public List<Mensuel> findAll();

    public List<Mensuel> findALLl();
    
    public LoyerSalarie findOneLSById(Integer id);
    
    public LoyerChantier findOneLCById(Integer id);

    public List<Mensuel> search(int id, String matriculeB, String nomB, String dateDebut);

    public List<Mensuel> AfficherListMensuelByidLoyer(Long id);

    public List<Mensuel> Consult(int id);

    public List<LoyerChantier> loyerChantiers();

    public List<LoyerSalarie> loyerSalaries();
    
     public List<LoyerSalarie> loyerSalariesByDateDeb(Date d);

    public List<LoyerSalarie> getListLoyerSalarieByIdMensuel(int id);

    public List<Mensuel> rechercherMensuel(String matricule, String nom, String prenom, String fonction, String dateDebut);

    public BonCaisse findAllBonCaisseByIdMensuel(int id);

    public List<LoyerSalarie> getListLoyerSalarieByIdMensuelPrincipal(int id);

    public void saveAffectation(LoyerChantier affect, Chantier c);

    public List<Chantier> ConsultChantier(int id);

    public void saveAffectation(LoyerSalarie affect, Mensuel mensu, String[] str);

    public void saveAffectation(LoyerSalarie affect, Mensuel mensu, List<Mensuel> mensuels);

    public void deleteLoyerChantier(LoyerChantier loyer);

    public void deleteLoyerSalarie(LoyerSalarie loyer);
    
    public void updateLoyerSalarie(LoyerSalarie loyer);
    public void updateLoyerChantier(LoyerChantier loyer);

    public List<Mensuel> findALLlBy_Id_Loyer(Long id);

    public List<LoyerChantier> getLoyerChantierByChantier(int id);

    public List<LoyerChantier> rechercher_Loyer_Chantier_ByIdentifient_Date_Debut(Date date_Debut);

    public List<LoyerSalarie> rechercher_Loyer_Salarier_ByIdentifient_Date_Debut(Long identifiant, String date_Debut);

    public List<Loyer> findByNumContrat(String numCOntrat);
    
    public List<Proprietaire> getListproPrietaireDistinct();
    
    public List<FournisseurLoyer>  listFournisseurLoyer();
}
