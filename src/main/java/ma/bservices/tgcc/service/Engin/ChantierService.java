/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.service.Engin;

import java.util.List;
import ma.bservices.beans.Chantier;
import ma.bservices.beans.Document;
import ma.bservices.tgcc.Entity.Voiture;

/**
 *
 * @author j.allali
 */
public interface ChantierService {

    public final String CODE_CHANTIER_DEPOT = "DEPOT";

    public List<Chantier> findAll();

    public Chantier findById(int id);

    public Chantier findById_String(String id);

    public List<Chantier> findBy_Different_Id_(int id);

    public Chantier findByName(String name);

    public Boolean addChantier(Chantier chantier);

    public Boolean deleteChantier(Chantier chantier);

    public Boolean updateChantier(Chantier chantier);

    public List<Chantier> deleteInstanceFromList(List<Chantier> lc, int idChantier);

    public List<Chantier> addInstanceFromList(List<Chantier> lc, Chantier chantier);

    public List<Voiture> findAllVoiture();

    public List<Chantier> search(String code, String nom, String region);

    public Voiture saveVoiture(Voiture voitureChantier);

    public List<Voiture> searchVoitureBy(String matricule);

    public List<Chantier> findAllInChantier(int chantier_id);

    public List<Chantier> findAllInChantierByAffaire(String chantier_id);

    public List<Chantier> findByAffaire(String affaire);

    /**
     * get chantier by affaire
     *
     * @param affaire
     * @return chantier or null
     */
    public Chantier getByAffaire(String affaire);


    public List<Chantier> searchByUser(String user);

    public List<String> get_allChantiersCodes();

    public List<String> get_allChantiersNames();

    public List<String> get_allChantiersRegins();

    public Boolean add_affiniteChantier(Chantier c);

    public List<Chantier> findBy_ChantierAff_Id_(int id);

    public List<Chantier> getListChantierBy(String code, String nom, String region);

    public List<String> getListChantierDistinctRegion();

    public List<String> getListChantierDistinctRegion(String code);

    public List<String> getListChantierDistinctRegionBy(String nom, String code);

}
