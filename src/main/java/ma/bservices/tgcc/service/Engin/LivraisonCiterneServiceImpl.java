/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.service.Engin;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import ma.bservices.tgcc.Entity.Bon_Livraison_Citerne;
import ma.bservices.tgcc.dao.engin.LivraisonCiterneDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author a.wattah
 */
@Service("livraisonCiterneService")
public class LivraisonCiterneServiceImpl implements LivraisonCiterneService, Serializable {

    @Autowired
    private LivraisonCiterneDAO livraison_CiterneDAO;

    /**
     * getters and setters
     *
     * @return
     */
    public LivraisonCiterneDAO getLivraison_CiterneDAO() {
        return livraison_CiterneDAO;
    }

    public void setLivraison_CiterneDAO(LivraisonCiterneDAO livraison_CiterneDAO) {
        this.livraison_CiterneDAO = livraison_CiterneDAO;
    }

    /**
     * end setters and getters
     * @param bon_liv_citerne
     */
    @Override
    public void save(Bon_Livraison_Citerne bon_liv_citerne) {

        this.livraison_CiterneDAO.save(bon_liv_citerne);

    }

    @Override
    public List<Bon_Livraison_Citerne> l_historiques() {

        return this.livraison_CiterneDAO.l_historiques();
    }

    @Override
    public void update(Bon_Livraison_Citerne bon_Livraison_Citerne) {
        this.livraison_CiterneDAO.update(bon_Livraison_Citerne);
    }

    @Override
    public List<Bon_Livraison_Citerne> get_listBy_id_bonLivraison(int id) {

        return this.livraison_CiterneDAO.get_listBy_id_bonLivraison(id);
    }

    @Override
    public List<Bon_Livraison_Citerne> get_liste_livraisonByDate_action(int id , Date date, String action, String Num_Commande, String num_Livraison) {
        return this.livraison_CiterneDAO.get_liste_livraisonByDate_action( id ,date, action, Num_Commande, num_Livraison);
    }

}
