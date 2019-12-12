/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.dao.engin;

import java.util.Date;
import java.util.List;
import ma.bservices.tgcc.Entity.Bon_Livraison_Citerne;

/**
 *
 * @author a.wattah
 */
public interface LivraisonCiterneDAO {

    public void save(Bon_Livraison_Citerne liv_citerne);

    /**
     * methode qui permet de recupere liste historique a prtir table bon
     * livraison
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

    public void delete(Bon_Livraison_Citerne bon_Livraison_Citerne);

    public Bon_Livraison_Citerne get_bon_livraison_by_Id(int id);

    public List<Bon_Livraison_Citerne> get_liste_livraisonByDate_action(int id , Date date, String action, String Num_Commande, String num_Livraison);
}
