/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.service.Mensuel;

import java.util.List;
import ma.bservices.tgcc.Entity.MarqueTelephone;
import ma.bservices.tgcc.Entity.Mensuel;
import ma.bservices.tgcc.Entity.Telephone;

/**
 *
 * @author a.wattah
 */
public interface TelephoneService {

    public Boolean save(Telephone telephone);

    public List<Mensuel> search(String matricule, String nom, String prenom, String cin);

    public List<Telephone> getListTelephoneDifferentId(int id);

    public List<Telephone> findAll();

    public List<Mensuel> findAllM();

    public List<Telephone> ConsultOrdinateur(int id);

    public List<MarqueTelephone> getMarqueByLib(String lib);

    public Boolean save(MarqueTelephone marqueTelephone);

    public Boolean delete(Telephone telephone);

    public Boolean update(Telephone telephone);

    public List<Telephone> listTelephone_Non_Affecter();

    public List<Telephone> listTelephophone_Affecter();

    /**
     * methode qui permet de desaffecter telephone a un mensuel
     *
     * @param telephone
     */
    public void desaffecter_telephone_mensuel(Telephone telephone);

    /**
     * methode qui permet de recherche telephone Non affecter
     *
     * @param num
     * @param marque
     * @return
     */
    public List<Telephone> getList_telephone_NonAffecter(String num, String marque, String model);

}
