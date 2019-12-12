/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.dao.Mensuel;

import java.util.Date;
import java.util.List;
import ma.bservices.beans.Chantier;
import ma.bservices.beans.Salarie;
import ma.bservices.tgcc.Entity.CarteGasoil;
import ma.bservices.tgcc.Entity.HistoriqueCarteGazoile;
import ma.bservices.tgcc.Entity.Voiture;

/**
 *
 * @author wattah
 */
public interface CarteGasoilDAO {

    public void save(CarteGasoil gasoil);
    public void delete(CarteGasoil carteGasoil);
    public void update(CarteGasoil carteGasoil);
    public CarteGasoil carteGazoilById(Integer id);
    public List<CarteGasoil> findAll();
    public List<Salarie> findAllSalaries();
    public List<CarteGasoil> listCarteGasoil();
    public List<CarteGasoil> listCarteGasoilNonAffecte();
    public List<CarteGasoil> listCarteGasoilAffecte();
    public List<CarteGasoil> listCarteGasoilBySalarie(Salarie c );
    public List<CarteGasoil> listCarteGasoilByChantier(Chantier c);
    public List<CarteGasoil> listCarteGasoilSalarie();
    public List<CarteGasoil> listCarteGasoilChantier();
    public List<CarteGasoil> listCarteGasoilBySalarieArchive(Salarie c );
    public List<CarteGasoil> listCarteGasoilByChantierArchive(Chantier c);
    public List<CarteGasoil> listCarteGasoilSalarieArchive();
    public List<CarteGasoil> listCarteGasoilChantierArchive();
    public CarteGasoil lastCarteGasoil();
    public Voiture voitureBySalary(Long id);
    public Voiture voitureByChantier(Long id);
    
    
    ///////////////////////////////////////////////////////////////////////////
    ////////////////////////// Historique CARTE GAZOILE ///////////////////////
    ///////////////////////////////////////////////////////////////////////////
    
    public void ajoutHistorique(HistoriqueCarteGazoile hv);
    public List<HistoriqueCarteGazoile> getAllHistoriqueCarteGazoile();
    public List<HistoriqueCarteGazoile> getAllHistoriqueCarteGazoileByIdVoiture(Long id);
    public List<HistoriqueCarteGazoile> getAllHistoriqueCarteGazoileByIdSalarier(Long id);
    public List<HistoriqueCarteGazoile> getAllHistoriqueCarteGazoileByIdChantier(Long id);
    public List<HistoriqueCarteGazoile> getAllHistoriqueCarteGazoileSalarier();
    public List<HistoriqueCarteGazoile> getAllHistoriqueCarteGazoileChantier();
    public List<HistoriqueCarteGazoile> getAllHistoriqueCarteGazoileSalarierByDate(Date ddb,Date df);
    public List<HistoriqueCarteGazoile> getAllHistoriqueCarteGazoileChantierByDate(Date ddb,Date df);
    

}
