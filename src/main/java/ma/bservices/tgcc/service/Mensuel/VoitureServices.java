/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.service.Mensuel;

import java.util.Date;
import java.util.List;
import ma.bservices.beans.Chantier;
import ma.bservices.beans.HistoriqueVoiture;
import ma.bservices.beans.Salarie;
import ma.bservices.tgcc.Entity.Voiture;

/**
 *
 * @author a.wattah
 */
public interface VoitureServices {

   
    public void ajouter_voiture(Voiture v);
    public void delete(Voiture v);
    public Boolean update(Voiture v);
    public Voiture getVoitureByID(Long id);
    public Voiture getVoitureByMatricule(String matricule);
    public Boolean existeVoiture(Voiture v);
    public List<Voiture> findAll();
    public List<Voiture> getListeVoituresByChantier(Chantier c);
    public List<Voiture> getListeVoituresBySalarier(Salarie s);
    public List<Voiture> getListeVoituresNonAffecter();
    public List<Voiture> getListeVoituresAffecter();
    public List<Voiture> getListeVoituresAffecterSalarie();
    public List<Voiture> getListeVoituresAffecterChantier();
    public List<Voiture> getListeVoitureByMarque(String marque);
    public String getChantierBySalaryID(Long id) ;

    //////////////////////////////////////////////////////////////////////////////
    ////////////////////////// Historique voiture  ///////////////////////////////
    //////////////////////////////////////////////////////////////////////////////
    
    public void ajoutHistorique(HistoriqueVoiture hv);
    public List<HistoriqueVoiture> getAllHistoriqueVoiture();
    public List<HistoriqueVoiture> getAllHistoriqueVoitureByIdVoiture(Long id);
    public List<HistoriqueVoiture> getAllHistoriqueVoitureByIdSalarier(Long id);
    public List<HistoriqueVoiture> getAllHistoriqueVoitureByIdChantier(Long id);
    public List<HistoriqueVoiture> getAllHistoriqueVoitureSalarier();
    public List<HistoriqueVoiture> getAllHistoriqueVoitureChantier();
    public List<HistoriqueVoiture> getAllHistoriqueVoitureSalarierByDate(Date ddb,Date df);
    public List<HistoriqueVoiture> getAllHistoriqueVoitureChantierByDate(Date ddb,Date df);
    

    

}
