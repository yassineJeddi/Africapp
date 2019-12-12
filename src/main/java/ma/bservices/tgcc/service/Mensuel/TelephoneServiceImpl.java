/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.service.Mensuel;

import java.io.Serializable;
import java.util.List;
import ma.bservices.tgcc.Entity.MarqueTelephone;
import ma.bservices.tgcc.Entity.Mensuel;
import ma.bservices.tgcc.Entity.Telephone;
import ma.bservices.tgcc.dao.Mensuel.TelephoneDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author a.wattah
 */
@Service("telephoneService")
public class TelephoneServiceImpl implements TelephoneService, Serializable {

    @Autowired
    private TelephoneDAO telephoneDAO;

    public TelephoneDAO getTelephoneDAO() {
        return telephoneDAO;
    }

    public void setTelephoneDAO(TelephoneDAO telephoneDAO) {
        this.telephoneDAO = telephoneDAO;
    }

    @Override
    public Boolean save(Telephone telephone) {
        return this.telephoneDAO.save(telephone);
    }

    @Override
    public List<Telephone> findAll() {
        return this.telephoneDAO.findAll();
    }

    @Override
    public List<Mensuel> search(String matricule, String nom, String prenom, String cin) {
        return this.telephoneDAO.rechercherMensuel(matricule, nom, prenom, cin);
    }

    @Override
    public List<Mensuel> findAllM() {
        return this.telephoneDAO.findAllM();
    }

    @Override
    public List<Telephone> ConsultOrdinateur(int id) {
        return this.telephoneDAO.ConsultOrdinateur(id);
    }

    @Override
    public List<MarqueTelephone> getMarqueByLib(String lib) {
        return this.telephoneDAO.getMarqueByLib(lib);
    }

    @Override
    public Boolean save(MarqueTelephone marqueTelephone) {
        return this.telephoneDAO.save(marqueTelephone);
    }

    @Override
    public Boolean delete(Telephone telephone) {
        return this.telephoneDAO.delete(telephone);
    }

    @Override
    public Boolean update(Telephone telephone) {
        return this.telephoneDAO.update(telephone);
    }

    @Override
    public List<Telephone> listTelephone_Non_Affecter() {
        return this.telephoneDAO.listTelephone_Non_Affecter();
    }

    @Override
    public List<Telephone> listTelephophone_Affecter() {
        return this.telephoneDAO.listTelephophone_Affecter();
    }

    @Override
    public void desaffecter_telephone_mensuel(Telephone telephone) {
        this.telephoneDAO.desaffecter_telephone_mensuel(telephone);
    }

    @Override
    public List<Telephone> getList_telephone_NonAffecter(String num, String marque, String model) {
        return this.telephoneDAO.getList_telephone_NonAffecter(num, marque, model);
    }

    @Override
    public List<Telephone> getListTelephoneDifferentId(int id) {
        return this.telephoneDAO.getListTelephoneDifferentId(id);
    }

}
