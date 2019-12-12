/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.dao.Mensuel;

import java.util.List;
import ma.bservices.beans.Chantier;
import ma.bservices.beans.Salarie;
import ma.bservices.tgcc.Entity.Affectation;
import ma.bservices.tgcc.Entity.Mensuel;

/**
 *
 * @author j.allali
 */
public interface MensuelDAO {

    public List<Mensuel> findAll();

    public List<Mensuel> inActiffindAll();

    public List<Affectation> findAllAffectation();
    
    public List<Salarie> findAllActif();

    public List<Mensuel> getListMensuelBy(String matricule, String nom, String prenom, String cin);

    public List<Mensuel> rechercherMensuel(String matricule, String nom, String prenom, String fonction, String cin);

    public List<Chantier> getChantierAffect(int id);

    public Chantier ChantierByID(int id);

    public Mensuel findById(String ID);

    public Mensuel findById_integer(int ID);

    public Mensuel findByMatricule(String matricule);

    public void update(Mensuel m);

    public Chantier getIdChantierByLib(String lib);

    public List<Mensuel> listMensuel(int id);

    public void saveAffectation(List<Affectation> affect);

    public List<Mensuel> rechercherMensuelByDateCreation(String matricule, String nom, String prenom, String date_creation);

    public List<Mensuel> rechercherMensuelByfonction(String matricule, String nom, String prenom, String fonction, String status);

    public void deleteMensuel(Mensuel m);

    public void saveMensuel(Mensuel m);

    public void updateMensuel(Mensuel m);

    public List<String> distinct_mensuel_cin();

    public List<Mensuel> getMensuelDifferentId(int id);

    public List<String> distinct_mensuel_name();

    public List<String> distinct_mensuel_firstName();

    public List<String> distinct_mensuel_matricule();
    
    public List<Mensuel> actifMensuelByChantier(int idChantier);

    /*
     *recherche des mensuels pour les affectes des ordianateurs 
     */
//    public List<Mensuel> rechercherMensuelOrd(String matricule, String nom, String prenom, String cin);
}
