/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.service.Engin;

import java.util.Date;
import java.util.List;
import ma.bservices.tgcc.Entity.Bon_Livraison_Citerne;

/**
 *
 * @author a.wattah
 */
public interface LivraisonCiterneService {

    public void save(Bon_Livraison_Citerne bon_liv_citerne);
    public Bon_Livraison_Citerne findBonLivraisonCiterneById(Integer id);

    /**
     * methode qui permet de recuper list des bon_livraison (Historique)
     *
     * @return
     */
    public List<Bon_Livraison_Citerne> l_historiques();

    public void update(Bon_Livraison_Citerne bon_Livraison_Citerne);

    /**
     * methode qui permt de recupere list des bon livraison by id
     *
     * @param id
     * @return
     */
    public List<Bon_Livraison_Citerne> get_listBy_id_bonLivraison(int id);

    public List<Bon_Livraison_Citerne> get_liste_livraisonByDate_action(int id , Date date, String action, String Num_Commande, String num_Livraison);
}
