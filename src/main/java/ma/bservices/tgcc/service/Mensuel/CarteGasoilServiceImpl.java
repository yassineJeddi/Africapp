/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.service.Mensuel;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import ma.bservices.beans.Chantier;
import ma.bservices.beans.Salarie;
import ma.bservices.tgcc.Entity.CarteGasoil;
import ma.bservices.tgcc.Entity.HistoriqueCarteGazoile;
import ma.bservices.tgcc.Entity.Voiture;
import ma.bservices.tgcc.dao.Mensuel.CarteGasoilDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("carteGasoilService")
public class CarteGasoilServiceImpl implements CarteGasoilService, Serializable {

    
    @Autowired
    private CarteGasoilDAO carteGasoilDAO;

    public CarteGasoilDAO getCarteGasoilDAO() {
        return carteGasoilDAO;
    }

    public void setCarteGasoilDAO(CarteGasoilDAO carteGasoilDAO) {
        this.carteGasoilDAO = carteGasoilDAO;
    }

    @Override
    public void save(CarteGasoil gasoil) {
       carteGasoilDAO.save(gasoil);
    }

    @Override
    public void delete(CarteGasoil carteGasoil) {
        carteGasoilDAO.delete(carteGasoil);
    }

    @Override
    public void update(CarteGasoil carteGasoil) {
        carteGasoilDAO.update(carteGasoil);
    }

    @Override
    public CarteGasoil carteGazoilById(Integer id) {
        return carteGasoilDAO.carteGazoilById(id);
    }

    @Override
    public List<CarteGasoil> findAll() {
        return carteGasoilDAO.findAll();
    }

    @Override
    public List<CarteGasoil> listCarteGasoil() {
        return carteGasoilDAO.listCarteGasoil();
    }
    @Override
    public List<CarteGasoil> listCarteGasoilNonAffecte() {
        return carteGasoilDAO.listCarteGasoilNonAffecte();
    }
    
    @Override
    public List<CarteGasoil> listCarteGasoilAffecte() {
        return carteGasoilDAO.listCarteGasoilAffecte();
    }

    @Override
    public List<CarteGasoil> listCarteGasoilBySalarie(Salarie c) {
        return carteGasoilDAO.listCarteGasoilBySalarie(c);
    }

    @Override
    public List<CarteGasoil> listCarteGasoilByChantier(Chantier c) {
        return carteGasoilDAO.listCarteGasoilByChantier(c);
    }

    @Override
    public List<CarteGasoil> listCarteGasoilBySalarieArchive(Salarie c) {
        return carteGasoilDAO.listCarteGasoilBySalarieArchive(c);
    }

    @Override
    public List<CarteGasoil> listCarteGasoilByChantierArchive(Chantier c) {
        return carteGasoilDAO.listCarteGasoilByChantierArchive(c);
    }


    @Override
    public List<CarteGasoil> listCarteGasoilSalarieArchive() {
        return carteGasoilDAO.listCarteGasoilSalarieArchive();
    }

    @Override
    public List<CarteGasoil> listCarteGasoilChantierArchive() {
        return carteGasoilDAO.listCarteGasoilChantierArchive();
    }

    @Override
    public CarteGasoil lastCarteGasoil() {
        return carteGasoilDAO.lastCarteGasoil();
    }

    @Override
    public List<CarteGasoil> listCarteGasoilSalarie() {
        return carteGasoilDAO.listCarteGasoilSalarie();
    }

    @Override
    public List<CarteGasoil> listCarteGasoilChantier() {
         return carteGasoilDAO.listCarteGasoilChantier();
    }

    public CarteGasoilServiceImpl() {
    }

    @Override
    public List<Salarie> findAllSalaries() {
         return carteGasoilDAO.findAllSalaries();
    }

    ///////////////////////////////////////////////////////////////////////////
    ////////////////////////// Historique CARTE GAZOILE ///////////////////////
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void ajoutHistorique(HistoriqueCarteGazoile hv) {
        carteGasoilDAO.ajoutHistorique(hv);
    }

    @Override
    public List<HistoriqueCarteGazoile> getAllHistoriqueCarteGazoile() {
        return carteGasoilDAO.getAllHistoriqueCarteGazoile();
    }

    @Override
    public List<HistoriqueCarteGazoile> getAllHistoriqueCarteGazoileByIdVoiture(Long id) {
        return carteGasoilDAO.getAllHistoriqueCarteGazoileByIdVoiture(id);
    }

    @Override
    public List<HistoriqueCarteGazoile> getAllHistoriqueCarteGazoileByIdSalarier(Long id) {
        return carteGasoilDAO.getAllHistoriqueCarteGazoileByIdSalarier(id);
    }

    @Override
    public List<HistoriqueCarteGazoile> getAllHistoriqueCarteGazoileByIdChantier(Long id) {
        return carteGasoilDAO.getAllHistoriqueCarteGazoileByIdChantier(id);
    }

    @Override
    public List<HistoriqueCarteGazoile> getAllHistoriqueCarteGazoileSalarier() {
        return carteGasoilDAO.getAllHistoriqueCarteGazoileSalarier();
    }

    @Override
    public List<HistoriqueCarteGazoile> getAllHistoriqueCarteGazoileChantier() {
        return carteGasoilDAO.getAllHistoriqueCarteGazoileChantier();
    }

    @Override
    public List<HistoriqueCarteGazoile> getAllHistoriqueCarteGazoileSalarierByDate(Date ddb, Date df) {
        return carteGasoilDAO.getAllHistoriqueCarteGazoileSalarierByDate(ddb, df);
    }

    @Override
    public List<HistoriqueCarteGazoile> getAllHistoriqueCarteGazoileChantierByDate(Date ddb, Date df) {
        return carteGasoilDAO.getAllHistoriqueCarteGazoileChantierByDate(ddb, df);
    }

    @Override
    public Voiture voitureBySalary(Long id){
        return carteGasoilDAO.voitureBySalary(id);
    }
    
    @Override
    public Voiture voitureByChantier(Long id){
        return carteGasoilDAO.voitureByChantier(id);
    }
   
}
