/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.service.Mensuel;

import java.util.List;
import ma.bservices.beans.Chantier;
import ma.bservices.beans.Salarie;
import ma.bservices.tgcc.Entity.Affectation;
import ma.bservices.tgcc.Entity.Mensuel;

/**
 *
 * @author j.allali
 */
public interface MensuelService {

    public List<Mensuel> findAll();
    
    public List<Salarie> findAllActifs();

    public List<Mensuel> inActiffindAll();

    public List<Affectation> findAllAffectation();

    public Mensuel getById(int id);

    public Mensuel getByMatricule(String matricule);

    public Mensuel getById_string(String id);

    public List<Mensuel> search(String matricule, String nom, String prenom, String fonction, String cin);

    public void saveAffectation(List<Affectation> affect);

    public List<Chantier> getChantierAffect(int id);

    public Chantier ChantierByID(int id);

    public Chantier getIdChantierByLib(String lib);

    public void DeleteMensuelAndTsElements(int id);

    public List<Mensuel> listMensuel(int id);
    
    public List<Mensuel> actifMensuelByChantier(int idChantier);

    public List<Mensuel> rechercherMensuelByDateCreation(String matricule, String nom, String prenom, String date_creation);

    public List<Mensuel> rechercherMensuelByfonction(String matricule, String nom, String prenom, String fonction, String status);

    public List<Mensuel> getMensuelDifferentId(int id);

    public void saveMensuel(Mensuel m);

    public void updateMensuel(Mensuel m);

    //public void saveOrUpdateMensuel(Mensuel m, String nom, String fonctionCode, String prenom, String cin, String cnss, String matricule, String dateDebut, String dateCreation, String status);
    public void deleteMensuel(Mensuel m);

    public List<String> distinct_mensuel_name();

    public List<String> distinct_mensuel_firstName();

    public List<String> distinct_mensuel_matricule();

    //public Mensuel findByMatricule(String matricule);
    public List<Mensuel> getListMensuel_DocumentObligatoir();

    public List<Mensuel> getListMensuelBy(String matricule, String nom, String preno, String cin);

    public Mensuel findById(String ID);

    public List<String> distinct_mensuel_cin();

//    public List<Mensuel> rechercherMensuelOrd(String matricule, String nom, String prenom, String cin);
}
