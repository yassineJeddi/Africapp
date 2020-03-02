/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.service;

import java.io.Serializable;
import java.util.List;
import ma.bservices.dao.DossierMedicalDao;
import ma.bservices.tgcc.Entity.Antecedent;
import ma.bservices.tgcc.Entity.AntecedentProfessionnel;
import ma.bservices.tgcc.Entity.DocumentDossierMedical;
import ma.bservices.tgcc.Entity.DossierMedical; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author BARAKA
 */
@Service("dossierMedicalService")
@Transactional
public class DossierMedicalServiceImp implements DossierMedicalService, Serializable{
    
    @Autowired
    DossierMedicalDao dossierMedicalDao;
    
    @Override
    public Long addDossierMedical(DossierMedical dossierMedical) {
        return dossierMedicalDao.addDossierMedical(dossierMedical);
    }

    @Override
    public Long updateDossierMedical(DossierMedical dossierMedical) {
        return dossierMedicalDao.updateDossierMedical(dossierMedical);
    }

    @Override
    public boolean deleteDossierMedical(DossierMedical dossierMedical) {
        return dossierMedicalDao.deleteDossierMedical(dossierMedical);
    }

    @Override
    public List<DossierMedical> findAllDossierMedical() {
        return dossierMedicalDao.findAllDossierMedical();
    }

    @Override
    public List<DossierMedical> findDossierMedicalChantiers(String code) {
        return dossierMedicalDao.findDossierMedicalChantiers(code);
    }

    @Override
    public List<DossierMedical> findDossierMedicalByChantier(int idChantier) {
        return dossierMedicalDao.findDossierMedicalByChantier(idChantier);
    }

    @Override
    public List<DossierMedical> findAllDossierMedicalByStatut(Integer Actif) {
        return dossierMedicalDao.findAllDossierMedicalByStatut(Actif);
    }

    @Override
    public List<DossierMedical> findDossierMedicalChantiersAndStatut(String code, Integer Actif) {
        return dossierMedicalDao.findDossierMedicalChantiersAndStatut(code, Actif);
    }

    @Override
    public List<DossierMedical> findDossierMedicalByChantierAndStatut(int idChantier, Integer Actif) {
        return dossierMedicalDao.findDossierMedicalByChantierAndStatut(idChantier, Actif);
    }

    @Override
    public Integer insertDocument(DocumentDossierMedical document) {
         return dossierMedicalDao.insertDocument(document); 
    } 

    @Override
    public List<DocumentDossierMedical> findDocumentDossierMedicalByDossier(DossierMedical dossierMedical) {
        return dossierMedicalDao.findDocumentDossierMedicalByDossier(dossierMedical);
    }

    @Override
    public void miseAjourSalarie(int id) {
        dossierMedicalDao.miseAjourSalarie(id);
    }

    @Override
    public List<AntecedentProfessionnel> findAntecedentsProfessionels(DossierMedical dossierMedical) {
        return dossierMedicalDao.findAntecedentsProfessionels(dossierMedical);
    }

    @Override
    public List<Antecedent> findAntecedentByType(String type) {
        return dossierMedicalDao.findAntecedentByType(type);
    }

    @Override
    public Long addAntecedentProfessionnel(AntecedentProfessionnel antecedentProfessionnel) {
        return dossierMedicalDao.addAntecedentProfessionnel(antecedentProfessionnel);
    }

    @Override
    public Long updateAntecedentProfessionnel(AntecedentProfessionnel antecedentProfessionnel) {
       return dossierMedicalDao.updateAntecedentProfessionnel(antecedentProfessionnel);
    } 

    @Override
    public Long addAntecedent(Antecedent antecedent) {
        return dossierMedicalDao.addAntecedent(antecedent);
    } 

    @Override
    public List<DossierMedical> findDosMedByIdsalarie(int id) {
        return dossierMedicalDao.findDosMedByIdsalarie(id);
    }
}
