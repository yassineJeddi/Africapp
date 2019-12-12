/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.service.Mensuel;

import java.io.Serializable;
import java.util.List;
import ma.bservices.tgcc.Entity.Ordinateur;
import ma.bservices.tgcc.dao.Mensuel.OrdinateurDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author a.wattah
 */
@Service("ordinateurService")
public class OrdinateurServiceImpl implements OrdinateurService, Serializable {

    @Autowired
    private OrdinateurDAO ordinateurDAO;

    public OrdinateurDAO getOrdinateurDAO() {
        return ordinateurDAO;
    }

    public void setOrdinateurDAO(OrdinateurDAO ordinateurDAO) {
        this.ordinateurDAO = ordinateurDAO;
    }

    @Override
    public Boolean saveOrdinateur(Ordinateur ordi) {

        ordinateurDAO.saveOrdinateur(ordi);
        return true;
    }

    @Override
    public List<Ordinateur> ConsultOrdinateur(int id) {
        return ordinateurDAO.ConsultOrdinateur(id);
    }

    @Override
    public void delete(Ordinateur ordinateur) {
        this.ordinateurDAO.delete(ordinateur);
    }

    @Override
    public List<Ordinateur> findAll() {
        return this.ordinateurDAO.findAll();
    }

    @Override
    public Boolean update(Ordinateur ordinateur) {
        return this.ordinateurDAO.update(ordinateur);
    }

    @Override
    public List<Ordinateur> listOrdinateur_NonAffecter() {
        return this.ordinateurDAO.listOrdinateur_NonAffecter();
    }

    @Override
    public List<String> getList_ordinateur_distinct() {
        return this.ordinateurDAO.getList_ordinateur_distinct();
    }

    @Override
    public List<Ordinateur> listOrdinateur_Affecte() {
        return this.ordinateurDAO.listOrdinateur_Affecte();
    }

    @Override
    public void desaffecter_ordianteur_mensuel(Ordinateur ordinateur) {
        this.ordinateurDAO.desaffecter_ordianteur_mensuel(ordinateur);
    }

    @Override
    public List<Ordinateur> findByMArque(String marque, String numSerie) {
        return ordinateurDAO.findByMArque(marque, numSerie);
    }

    @Override
    public List<Ordinateur> getListeOrdinateurDifferentId(int id) {

        return this.ordinateurDAO.getListeOrdinateurDifferentId(id);
    }
}
