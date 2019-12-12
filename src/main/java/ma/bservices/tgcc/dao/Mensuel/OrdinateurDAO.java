/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.dao.Mensuel;

import java.util.List;
import ma.bservices.tgcc.Entity.Affectation;
import ma.bservices.tgcc.Entity.Ordinateur;

/**
 *
 * @author j.allali
 */
public interface OrdinateurDAO {

    public Boolean saveOrdinateur(Ordinateur ordi);

    public List<Ordinateur> ConsultOrdinateur(int id);

    public void delete(Ordinateur ordinateur);

    public List<Ordinateur> findAll();

    public Boolean update(Ordinateur ordinateur);

    public List<Ordinateur> listOrdinateur_NonAffecter();

    public List<Ordinateur> listOrdinateur_Affecte();
    
    public List<Ordinateur> getListeOrdinateurDifferentId(int id);

    /**
     * methode pour recupeere list distinct marque ordinateur affece a un
     * mensuel
     *
     * @return
     */
    public List<String> getList_ordinateur_distinct();

    /**
     * methode qui permet de desaffecter ordianteur a un mensuel
     *
     * @param ordinateur
     */
    public void desaffecter_ordianteur_mensuel(Ordinateur ordinateur);
    /**
     * 
     * @param marque
     * @param numSerie
     * @return 
     */
    public List<Ordinateur> findByMArque(String marque,String numSerie);

}
