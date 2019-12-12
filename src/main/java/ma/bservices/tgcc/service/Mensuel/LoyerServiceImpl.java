/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.service.Mensuel;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import ma.bservices.tgcc.Entity.BonCaisse;
import ma.bservices.beans.Chantier;
import ma.bservices.beans.Proprietaire;
import ma.bservices.tgcc.Entity.FournisseurLoyer;
import ma.bservices.tgcc.Entity.Loyer;
import ma.bservices.tgcc.Entity.LoyerChantier;
import ma.bservices.tgcc.Entity.LoyerSalarie;
import ma.bservices.tgcc.Entity.Mensuel;
import ma.bservices.tgcc.dao.Mensuel.LoyerDAO;
import ma.bservices.tgcc.dao.Mensuel.MensuelDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author a.wattah
 */
@Service("loyerService")
public class LoyerServiceImpl implements LoyerService, Serializable {

    @Autowired
    private LoyerDAO loyerDAO;

    @Autowired
    private MensuelDAO mensuelDAO;

    public LoyerDAO getLoyerDAO() {
        return loyerDAO;
    }

    public void setLoyerDAO(LoyerDAO loyerDAO) {
        this.loyerDAO = loyerDAO;
    }

    public MensuelDAO getMensuelDAO() {
        return mensuelDAO;
    }

    public void setMensuelDAO(MensuelDAO mensuelDAO) {
        this.mensuelDAO = mensuelDAO;
    }

    @Override
    public List<Mensuel> findAll() {
        return this.loyerDAO.findAll();
    }

    @Override
    public List<Mensuel> search(int id, String matriculeB, String nomB, String dateDebut) {
        return loyerDAO.search(id, matriculeB, nomB, dateDebut);
    }
    
   

    @Override
    public List<Mensuel> Consult(int id) {
        return this.loyerDAO.Consult(id);
    }

    @Override
    public List<LoyerSalarie> getListLoyerSalarieByIdMensuel(int id) {
        return this.loyerDAO.getListLoyerSalarieByIdMensuel(id);
    }

    @Override
    public List<Mensuel> rechercherMensuel(String matricule, String nom, String prenom, String fonction, String dateDebut) {
        return this.loyerDAO.rechercherMensuel(matricule, nom, prenom, fonction, dateDebut);
    }

    @Override
    public List<Mensuel> findALLl() {
        return this.loyerDAO.findALLl();
    }

    @Override
    public List<Mensuel> AfficherListMensuelByidLoyer(Long id) {
        return this.loyerDAO.AfficherListMensuelByidLoyer(id);
    }

    @Override
    public BonCaisse findAllBonCaisseByIdMensuel(int id) {
        return this.loyerDAO.findAllBonCaisseByIdMensuel(id);
    }

    @Override
    public void saveAffectation(LoyerChantier affect, Chantier c) {
        LinkedList<Chantier> l = new LinkedList<Chantier>();
        l.add(c);
        affect.setChantier(l);
        loyerDAO.saveAffectation(affect);
    }

    @Override
    public List<Chantier> ConsultChantier(int id) {
        return this.loyerDAO.ConsultChantier(id);
    }

    @Override
    public void saveAffectation(LoyerSalarie affect, Mensuel mensu, String[] str) {

        LinkedList<Mensuel> l = new LinkedList<Mensuel>();
        l.add(mensu);
        for (int i = 0; i < str.length; i++) {

            Mensuel mensuel = mensuelDAO.findById(str[i]);

            l.add(mensuel);

        }
        affect.setMensuels(l);
        loyerDAO.saveAffectation(affect);
    }

    @Override
    public void deleteLoyerChantier(LoyerChantier loyer) {
        this.loyerDAO.deleteLoyerChantier(loyer);
    }
    
    @Override
    public void updateLoyerSalarie(LoyerSalarie loyer){
    loyerDAO.update(loyer);
    }
    
    @Override
    public void updateLoyerChantier(LoyerChantier loyer){
    loyerDAO.update(loyer);
    }

    @Override
    public void deleteLoyerSalarie(LoyerSalarie loyer) {
        this.loyerDAO.deleteLoyerSalarie(loyer);
    }

    @Override
    public List<LoyerChantier> rechercher_Loyer_Chantier_ByIdentifient_Date_Debut(Date date_Debut) {
        return this.loyerDAO.loyerChantierByDateDeb(date_Debut);
    }

    @Override
    public List<LoyerSalarie> rechercher_Loyer_Salarier_ByIdentifient_Date_Debut(Long identifiant, String date_Debut) {
        return this.loyerDAO.rechercher_Loyer_Salarier_ByIdentifient_Date_Debut(identifiant, date_Debut);
    }

    @Override
    public List<Mensuel> findALLlBy_Id_Loyer(Long id) {
        return this.loyerDAO.findALLlBy_Id_Loyer(id);
    }

    @Override
    public List<LoyerChantier> loyerChantiers() {
        return this.loyerDAO.loyerChantiers();
    }

    @Override
    public List<LoyerSalarie> loyerSalaries() {
        return this.loyerDAO.loyerSalaries();
    }
    
    @Override
    public List<LoyerSalarie> loyerSalariesByDateDeb(Date d) {
        return this.loyerDAO.loyerSalariesByDateDeb(d);
    }

    @Override
    public void saveAffectation(LoyerSalarie affect, Mensuel mensu, List<Mensuel> mensuels) {

        LinkedList<Mensuel> l = new LinkedList<Mensuel>();
        //l.add(mensu);
        if (mensuels != null) {
            for (int i = 0; i < mensuels.size(); i++) {
                
                Mensuel mensuel = mensuelDAO.findById_integer(mensuels.get(i).getId());
                l.add(mensuel);

            }
        }
        affect.setMensuel_Principal(mensu);
        affect.setMensuels(l);
        
        System.out.println("PRE DAO ===== MENSUEL PRINCIPAL " + mensu.getNom());
        System.out.println("PRE DAO ======= MENSUELS SEC : " + mensuels);
        
        loyerDAO.saveAffectation(affect);
    }

    @Override
    public List<LoyerSalarie> getListLoyerSalarieByIdMensuelPrincipal(int id) {
        return this.loyerDAO.getListLoyerSalarieByIdMensuelPrincipal(id);
    }

    @Override
    public List<LoyerChantier> getLoyerChantierByChantier(int id) {
        return this.loyerDAO.getLoyerChantierByChantier(id);
    }

    @Override
    public List<Loyer> findByNumContrat(String numContrat) {
        return this.loyerDAO.findByNumContrat(numContrat);
    }

    @Override
    public LoyerSalarie findOneLSById(Integer id) {
        System.out.println("HELLO FROM SERVICE LOYER SALARIE");
       return this.loyerDAO.findOneLSById(id);
    }

    @Override
    public LoyerChantier findOneLCById(Integer id) {
       return this.loyerDAO.findOneLCById(id);
    }

    @Override
    public List<Proprietaire> getListproPrietaireDistinct() {
       return this.loyerDAO.getListproPrietaireDistinct();
    }

    @Override
    public List<FournisseurLoyer> listFournisseurLoyer() {
        return this.loyerDAO.listFournisseurLoyer();
    }
    
    
}
