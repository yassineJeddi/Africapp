/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.service.Engin;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import ma.bservices.beans.Chantier;
import ma.bservices.beans.Document;
import ma.bservices.tgcc.Entity.Voiture;
import ma.bservices.tgcc.dao.engin.ChantierDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author j.allali
 */
@Service("chantierService")
public class ChantierServiceImpl implements ChantierService, Serializable {

    @Autowired
    private ChantierDAO chantierDAO;

    public ChantierDAO getChantierDAO() {
        return chantierDAO;
    }

    public void setChantierDAO(ChantierDAO chantierDAO) {
        this.chantierDAO = chantierDAO;
    }

    @Override
    public List<Chantier> findAll() {
        List<Chantier> chantiers = chantierDAO.findAll();

        for (Chantier c : chantiers) {
            if (c.getAffiniteChantier() != null && !c.getAffiniteChantier().isEmpty()) {
                String s = "";
                for (Chantier c1 : c.getAffiniteChantier()) {
                    s += c1.getCode() + " ,";
                }
                c.setListe_Nom_Affinites(s.substring(0, s.length() - 2));
            }
        }

        return chantiers;
    }

    @Override
    public Chantier findById(int id) {
        return chantierDAO.findById(id);
    }

    @Override
    public Boolean addChantier(Chantier chantier) {
        this.chantierDAO.saveAffiniteChantier(chantier);
        return this.chantierDAO.addChantier(chantier);
    }

    @Override
    public Boolean deleteChantier(Chantier chantier) {
        return this.chantierDAO.deleteChantier(chantier);

    }

    @Override
    public Boolean updateChantier(Chantier chantier) {

        return this.chantierDAO.updateChantier(chantier);

    }

    @Override
    /**
     * Supprime un chantier depuis une liste de chantier pour ne pas afficher
     * les chantier dejà selectionnés dans l'affectation
     */
    public List<Chantier> deleteInstanceFromList(List<Chantier> lc, int idChantier) {
        for (int i = 0; i < lc.size(); i++) {
            if (lc.get(i).getId() == idChantier) {
                lc.remove(i);
                return lc;
            }
        }
        return lc;
    }

    /**
     * ajouter un chantier depuis une liste de chantier pour afficher les
     * chantier des affectations qu'on vient de supprimer
     *
     * @param lc
     * @param chantier
     * @return
     */
    @Override
    public List<Chantier> addInstanceFromList(List<Chantier> lc, Chantier chantier) {
        Boolean exist = false;
        for (int i = 0; i < lc.size(); i++) {
            if (lc.get(i).getId() == chantier.getId()) {
                exist = true;
                break;
            }
        }
        if (exist == false) {
            lc.add(chantier);
        }
        return lc;
    }

    @Override
    public Chantier findByName(String name) {
        return chantierDAO.findByName(name);
    }

    @Override
    public Chantier getByAffaire(String name) {
        return chantierDAO.getByAffaire(name);
    }

    @Override
    public List<Chantier> search(String code, String nom, String region) {
        return this.chantierDAO.search(code, nom, region);
    }

    @Override
    public Voiture saveVoiture(Voiture voitureChantier) {
        return this.chantierDAO.saveVoiture(voitureChantier);
    }

    @Override
    public List<Voiture> findAllVoiture() {
        return this.chantierDAO.findAllVoiture();
    }

    @Override
    public List<Voiture> searchVoitureBy(String matricule) {
        return this.chantierDAO.searchVoitureBy(matricule);
    }

    @Override
    public List<Chantier> findAllInChantier(int chantier_id) {
        return this.chantierDAO.findAllInChantier(chantier_id);
    }
 

    @Override
    public List<Chantier> findByAffaire(String affaire) {
        return this.chantierDAO.findByAffaire(affaire);
    }


    @Override
    public List<Chantier> findAllInChantierByAffaire(String chantier_id) {
        return this.chantierDAO.findAllInChantierByAffaire(chantier_id);
    }

    @Override
    public List<Chantier> searchByUser(String user) {
        return chantierDAO.searchByUser(user);
    }

    @Override
    public List<Chantier> findBy_Different_Id_(int id) {
        return this.chantierDAO.findBy_Different_Id_(id);
    }

    @Override
    public List<String> get_allChantiersCodes() {
        return chantierDAO.get_allChantiersCodes();
    }

    @Override
    public List<String> get_allChantiersNames() {
        return chantierDAO.get_allChantiersNames();
    }

    @Override
    public List<String> get_allChantiersRegins() {
        return chantierDAO.get_allChantiersRegins();
    }

    @Override
    public Chantier findById_String(String id) {
        return this.chantierDAO.findById(id);
    }

    @Override
    public Boolean add_affiniteChantier(Chantier c) {
        String[] array_chantiers = c.getChantier_str();

        for (int i = 0; i < array_chantiers.length; i++) {
            Chantier chByID = chantierDAO.findById(Integer.parseInt(array_chantiers[i]));
            
            if(chByID.getAffiniteChantier() != null && chByID.getAffiniteChantier().size() > 0){
                chByID.getAffiniteChantier().add(c);
            } else {
                List lc = new LinkedList<Chantier>();
                lc.add(c);
                chByID.setAffiniteChantier(lc);
            }
            
            
            System.out.println("----------- ________ CHANTIER BY ID ______---------" + Integer.parseInt(array_chantiers[i]));
            Boolean b = chantierDAO.saveAffiniteChantier(chByID);
        }

        return true;
    }

    @Override
    public List<Chantier> findBy_ChantierAff_Id_(int id) {
//        return chantierDAO.findBy_ChantierAff_Id_(id);
    return new LinkedList<>();
    }

    @Override
    public List<Chantier> getListChantierBy(String code, String nom, String region) {
        return this.chantierDAO.getListChantierBy(code, nom, region);
    }

    @Override
    public List<String> getListChantierDistinctRegion() {
        return this.chantierDAO.getListChantierDistinctRegion();
    }

    @Override
    public List<String> getListChantierDistinctRegion(String code) {
        return this.chantierDAO.getListChantierDistinctRegion(code);
    }

    @Override
    public List<String> getListChantierDistinctRegionBy(String nom, String code) {
        return this.chantierDAO.getListChantierDistinctRegionBy(nom, code);
    }

}
