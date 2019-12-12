/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.service.Mensuel;

import java.util.Date;
import java.util.List;
import ma.bservices.beans.Chantier;
import ma.bservices.tgcc.Entity.Historique_CG;
import ma.bservices.tgcc.Entity.Historique_LoyerC;
import ma.bservices.tgcc.Entity.Historique_LoyerS;
import ma.bservices.tgcc.Entity.Historique_Modem3G;
import ma.bservices.tgcc.Entity.Historique_Ordinateur;
import ma.bservices.tgcc.Entity.Historique_Telephone;
import ma.bservices.tgcc.Entity.Historique_Voiture;
import ma.bservices.tgcc.Entity.Historique_VoitureChantier;
import ma.bservices.tgcc.Entity.LoyerChantier;
import ma.bservices.tgcc.Entity.LoyerSalarie;
import ma.bservices.tgcc.Entity.Mensuel;
import ma.bservices.tgcc.Entity.Modem3G;
import ma.bservices.tgcc.Entity.Ordinateur;
import ma.bservices.tgcc.Entity.Telephone;
import ma.bservices.tgcc.Entity.Voiture;

/**
 *
 * @author admin
 */
public interface HistoriqueService {

    /**
     * VOITURE SALARIE*
     */
    public List<Historique_Voiture> findAllVoituresSalarie();

    public void addRecordVoitureSalarie(Historique_Voiture record, Long id_boiture, int id_mensuel, Date dateAff, Date dateDesaff);

     public List<Historique_Voiture> findByDateRangeVS(Date from, Date to);
     
     /**
     * VOITURE CHANTIER*
     */
    public List<Historique_VoitureChantier> findAllVoituresChantier();

    public void addRecordVoitureChantier(Historique_VoitureChantier record, Voiture voitureCh, int id_chantier, Date dateAff, Date dateDesaff);

    public List<Historique_VoitureChantier> findByDateRangeVC(Date from, Date to);
    
    /**
     * CARTE GASOIL*
     */
    public List<Historique_CG> findAllCG();

  //  public void addRecordCG(Historique_CG record, CarteGasoilSalarie carte, Mensuel mensuel, Date dateAff, Date dateDesaff);

    public List<Historique_CG> findByDateRangeCG(Date from, Date to);
    
     /**
     * CARTE GASOIL CHANTIER*
     */
   /* public List<Historique_CGC> findAllCGC();

    public void addRecordCGC(Historique_CGC record, CarteGasoilChantier carte, Chantier chantier, Date dateAff, Date dateDesaff);

    public List<Historique_CGC> findByDateRangeCGC(Date from, Date to);
    
  */   /**
     * TELEPHONE*
     */
    public List<Historique_Telephone> findAllTels();

    public void addRecordTelephone(Historique_Telephone record, Telephone tel, Mensuel mensuel, Date dateAff, Date dateDesaff);

    public List<Historique_Telephone> findByDateRange(Date from, Date to);
     
     /**
     * ORDINATEUR*
     */
    public List<Historique_Ordinateur> findAllOrdis();

    public void addRecordOrdinateur(Historique_Ordinateur record, Ordinateur ordi, Mensuel mensuel, Date dateAff, Date dateDesaff);

    
    public List<Historique_Ordinateur> findByDateRangeOrdi(Date from, Date to);
     

    
     /**
     * MODEM 3G*
     */
    public List<Historique_Modem3G> findAllModems();

    public void addRecordModem(Historique_Modem3G record, Modem3G modem, Mensuel mensuel, Date dateAff, Date dateDesaff);

    public List<Historique_Modem3G> findByDateRange3G(Date from, Date to);
    
    /**
     * LOYER SALARIE *
     */
   
    public List<Historique_LoyerS> findAllLoyerS();

    public void addRecordLoyerS(Historique_LoyerS record, Mensuel mensuel, LoyerSalarie loyerS, Date dateAff, Date dateDesaff);

     public List<Historique_LoyerS> findByDateRangeLS(Date from, Date to);
     
     
     /**
     * LOYER CHANTIER *
     */
   
    public List<Historique_LoyerC> findAllLoyerC();

    public void addRecordLoyerC(Historique_LoyerC record, Chantier chantier, LoyerChantier loyerC, Date dateAff, Date dateDesaff);

     public List<Historique_LoyerC> findByDateRangeLC(Date from, Date to);
     
    
}
