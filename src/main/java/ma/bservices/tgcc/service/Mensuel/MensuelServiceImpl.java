/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.service.Mensuel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import ma.bservices.beans.Chantier;
import ma.bservices.beans.Document;
import ma.bservices.beans.Salarie;
import ma.bservices.tgcc.Entity.Affectation;
import ma.bservices.tgcc.Entity.Element;
import ma.bservices.tgcc.Entity.Mensuel;
import ma.bservices.tgcc.dao.Mensuel.AffectationDAO;
import ma.bservices.tgcc.dao.Mensuel.ElementDAO;
import ma.bservices.tgcc.dao.Mensuel.MensuelDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author j.allali
 */
@Service("mensuelService")
public class MensuelServiceImpl implements MensuelService, Serializable {

    @Autowired
    private MensuelDAO mensuelDAO;

    @Autowired
    private AffectationDAO affectDAO;

    @Autowired
    private ElementDAO elementDao;

    public MensuelDAO getMensuelDAO() {
        return mensuelDAO;
    }

    public ElementDAO getElementDao() {
        return elementDao;
    }

    public void setElementDao(ElementDAO elementDao) {
        this.elementDao = elementDao;
    }

    public void setMensuelDAO(MensuelDAO mensuelDAO) {
        this.mensuelDAO = mensuelDAO;
    }

    public AffectationDAO getAffectDAO() {
        return affectDAO;
    }

    public void setAffectDAO(AffectationDAO affectDAO) {
        this.affectDAO = affectDAO;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public List<Mensuel> findAll() {
        return mensuelDAO.findAll();
    }

    @Override
    public List<Mensuel> inActiffindAll() {
        return mensuelDAO.inActiffindAll();
    }

    @Override
    public List<Mensuel> search(String matricule, String nom, String prenom, String fonction, String cin) {
        return mensuelDAO.rechercherMensuel(matricule, nom, prenom, fonction, cin);
    }

    @Override
    public void saveAffectation(List<Affectation> affect) {
        affectDAO.saveAffectation(affect);
    }

    @Override
    public List<Chantier> getChantierAffect(int id) {
        return mensuelDAO.getChantierAffect(id);
    }

    @Override
    public List<Affectation> findAllAffectation() {
        return mensuelDAO.findAllAffectation();
    }

    @Override
    public Chantier ChantierByID(int id) {
        return mensuelDAO.ChantierByID(id);
    }

    @Override
    public Chantier getIdChantierByLib(String lib) {
        return mensuelDAO.getIdChantierByLib(lib);
    }

    @Override
    public void DeleteMensuelAndTsElements(int id) {
        List<Element> l = this.elementDao.getElementByIdMensuel(id);
        for (Element element : l) {
            element.setAffecterOrDesaffecter(Boolean.TRUE);
            this.elementDao.update(element);
        }
    }

    @Override
    public List<Mensuel> listMensuel(int id) {
        return this.mensuelDAO.listMensuel(id);
    }

    @Override
    public List<Mensuel> rechercherMensuelByfonction(String matricule, String nom, String prenom, String fonction, String status) {
        return mensuelDAO.rechercherMensuelByfonction(matricule, nom, prenom, fonction, status);
    }

    @Override
    public void saveMensuel(Mensuel m) {
        this.mensuelDAO.saveMensuel(m);
    }

    @Override
    public void updateMensuel(Mensuel m) {
        this.mensuelDAO.updateMensuel(m);
    }

    @Override
    public void deleteMensuel(Mensuel m) {
        this.mensuelDAO.deleteMensuel(m);
    }

    @Override
    public List<Mensuel> getMensuelDifferentId(int id) {
        return this.mensuelDAO.getMensuelDifferentId(id);
    }

    @Override
    public List<Mensuel> rechercherMensuelByDateCreation(String matricule, String nom, String prenom, String date_creation) {
        return this.mensuelDAO.rechercherMensuelByDateCreation(matricule, nom, prenom, date_creation);
    }

    @Override
    public Mensuel getById(int id) {

        return this.mensuelDAO.findById("" + id);
    }

    @Override
    public List<String> distinct_mensuel_name() {
        return this.mensuelDAO.distinct_mensuel_name();
    }

    @Override
    public List<String> distinct_mensuel_firstName() {
        return this.mensuelDAO.distinct_mensuel_firstName();
    }

    @Override
    public List<String> distinct_mensuel_matricule() {
        return this.mensuelDAO.distinct_mensuel_matricule();
    }

    @Override
    public Mensuel getById_string(String id) {
        return this.mensuelDAO.findById(id);
    }

    @Override
    public Mensuel getByMatricule(String matricule) {
        return this.mensuelDAO.findByMatricule(matricule);
    }

    @Override
    public List<Mensuel> getListMensuel_DocumentObligatoir() {
        List<Mensuel> mensuels = this.mensuelDAO.findAll();

        List<Mensuel> results = new ArrayList<>();

        for (Mensuel m_ : mensuels) {

            for (Document docs : m_.getDocuments()) {
                if (docs.getTypeDocument().getFonctions() != null) {

                    results.add(m_);

                }
            }

        }

        return results;

    }

    @Override
    public List<Mensuel> getListMensuelBy(String matricule, String nom, String prenom, String cin) {

        return this.mensuelDAO.getListMensuelBy(matricule, nom, prenom, cin);
    }

    @Override
    public Mensuel findById(String ID) {
        return this.mensuelDAO.findById(ID);
    }

    @Override
    public List<String> distinct_mensuel_cin() {
        return this.mensuelDAO.distinct_mensuel_cin();
    }

    @Override
    public List<Salarie> findAllActifs() {
       return this.mensuelDAO.findAllActif();
    }

    @Override
    public List<Mensuel> actifMensuelByChantier(int idChantier) {
        return this.mensuelDAO.actifMensuelByChantier(idChantier);
    }

}
