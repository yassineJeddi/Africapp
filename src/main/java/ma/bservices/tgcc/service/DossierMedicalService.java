/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.services;

import java.util.List;
import ma.bservices.tgcc.Entity.Antecedent;
import ma.bservices.tgcc.Entity.AntecedentProfessionnel;
import ma.bservices.tgcc.Entity.DocumentDossierMedical;
import ma.bservices.tgcc.Entity.DossierMedical;

/**
 *
 * @author BARAKA
 */
public interface DossierMedicalService {
    public Long addDossierMedical(DossierMedical dossierMedical);
    public Long updateDossierMedical(DossierMedical dossierMedical);
    public boolean deleteDossierMedical(DossierMedical dossierMedical);
    public List<DossierMedical> findAllDossierMedical();
    public List<DossierMedical> findDossierMedicalChantiers(String code);
    public List<DossierMedical> findDossierMedicalByChantier(int idChantier);
    
    
    public List<DossierMedical> findAllDossierMedicalByStatut(Integer Actif);
    public List<DossierMedical> findDossierMedicalChantiersAndStatut(String code, Integer Actif);
    public List<DossierMedical> findDossierMedicalByChantierAndStatut(int idChantier, Integer Actif);

    public Integer insertDocument(DocumentDossierMedical document);
    public List<DocumentDossierMedical> findDocumentDossierMedicalByDossier(DossierMedical dossierMedical);
    public void miseAjourSalarie(int id);
    public List<AntecedentProfessionnel> findAntecedentsProfessionels(DossierMedical dossierMedical);
    public List<Antecedent> findAntecedentByType(String type);
    public Long addAntecedentProfessionnel(AntecedentProfessionnel antecedentProfessionnel);
    public Long updateAntecedentProfessionnel(AntecedentProfessionnel antecedentProfessionnel); 
    public Long addAntecedent(Antecedent antecedent);
}
