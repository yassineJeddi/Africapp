/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.dao.engin;

import java.util.List;
import ma.bservices.beans.Chantier;
import ma.bservices.beans.Document;
import ma.bservices.beans.Salarie;
import ma.bservices.tgcc.Entity.Voiture;

/**
 *
 * @author j.allali
 */
public interface ChantierDAO {

    public List<Chantier> findAll();

    public Chantier findById(int id);

    public List<Chantier> findBy_Different_Id_(int id);

    public Chantier findByName(String name);

    public void affectSalarieChatierFinance(Salarie s, Chantier c);
    public void deleteAffectSalarieToutChatierFinance(Salarie s) ;
    
    /**
     * get chantier by affaire
     *
     * @param affaire
     * @return chantier
     */
    public Chantier getByAffaire(String affaire);
    public Chantier findById(String id);
    public Chantier get_depot();
    public Boolean addChantier(Chantier chantier);
    public Boolean deleteChantier(Chantier chantier);
    public Boolean updateChantier(Chantier chantier);
    public Boolean deleteChantierTestUnitaire(Chantier chantier);
    public List<Chantier> search(String code, String nom, String region);
    public List<Chantier> getListChantierBy(String code, String nom, String region);
    public List<Chantier> findAllInChantier(int chantier_id);
    public List<Chantier> findByAffaire(String affaire);
    public List<Chantier> findAllInChantierByAffaire(String chantier_id);
    public List<Chantier> searchByUser(String user);
    public List<Document> getDocumentVoiture(Long id);
    public Voiture saveVoiture(Voiture voiture);
    public List<Voiture> searchVoitureBy(String matricule);
    public List<Voiture> getVoiture(int id);
    public List<Voiture> findAllVoiture();

    public List<Chantier> ateliersList(int start, int limit, String nom, String ville, int[] dos,String ate);
    public Object nombreChantiers(Integer idUser, boolean adminAlfresco, String querySearch, int[] dos);
    
    /**
     * get libelle zone to insert by increment index of last Zone insert in
     * chantier
     *
     * @param idChantier identifiant
     * @return libelleZone
     */
    public String libelleZoneToInsert(Integer idChantier);

    /**
     * Used in Test unitaire
     */
    public Document saveDocument(Document d);

    public Boolean deleteVoiture(Long id);

    public Boolean deleteChantierByquery(int id);

    public void update_Chantier_Test_Unitaire(Chantier chantier);

    public Chantier add_Chantier_Test_Unitaire(Chantier chantier);

    public List<String> get_allChantiersCodes();

    public List<String> get_allChantiersNames();

    public List<String> get_allChantiersRegins();

    public Boolean saveAffiniteChantier(Chantier chantier);

    public List<Chantier> findBy_ChantierAff_Id_(int id);

    public List<String> getListChantierDistinctRegion();

    public List<String> getListChantierDistinctRegion(String code);

    public List<String> getListChantierDistinctRegionBy(String nom, String code);

}
