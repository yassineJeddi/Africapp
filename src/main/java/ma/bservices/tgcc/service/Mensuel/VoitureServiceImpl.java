/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.service.Mensuel;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import ma.bservices.beans.Chantier;
import ma.bservices.beans.HistoriqueVoiture;
import ma.bservices.beans.Salarie;
import ma.bservices.tgcc.Entity.Voiture;
import ma.bservices.tgcc.dao.Mensuel.DocumentDAO;
import ma.bservices.tgcc.dao.Mensuel.VoitureDAO;
import ma.bservices.tgcc.utilitaire.TGCCMailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("voitureService")
public class VoitureServiceImpl implements VoitureServices, Serializable {

    @Autowired
    private VoitureDAO voitureDAO;

    @Autowired
    private DocumentDAO documentDAO;

    public VoitureDAO getVoitureDAO() {
        return voitureDAO;
    }

    public void setVoitureDAO(VoitureDAO voitureDAO) {
        this.voitureDAO = voitureDAO;
    }

    public DocumentDAO getDocumentDAO() {
        return documentDAO;
    }

    public void setDocumentDAO(DocumentDAO documentDAO) {
        this.documentDAO = documentDAO;
    }
    public String sendMailVisiteTechnique() {
        TGCCMailSender.sendMail();
        return "email sent avec succès";
    }

    @Override
    public void ajouter_voiture(Voiture v) {
        voitureDAO.ajouter_voiture(v);
    }

    @Override
    public void delete(Voiture v) {
        voitureDAO.delete(v);
    }

    @Override
    public Boolean update(Voiture v) {
       return voitureDAO.update(v);
    }

    @Override
    public Voiture getVoitureByID(Long id) {
            return voitureDAO.getVoitureByID(id);
    }

    @Override
    public Voiture getVoitureByMatricule(String matricule) {
        return voitureDAO.getVoitureByMatricule(matricule);
    }

    @Override
    public List<Voiture> findAll() {
        return voitureDAO.findAll();
    }

    @Override
    public List<Voiture> getListeVoituresByChantier(Chantier c) {
        return voitureDAO.getListeVoituresByChantier(c);
    }

    @Override
    public List<Voiture> getListeVoituresBySalarier(Salarie s) {
        return voitureDAO.getListeVoituresBySalarier(s);
    }

    @Override
    public List<Voiture> getListeVoituresNonAffecter() {
        return voitureDAO.getListeVoituresNonAffecter();
    }

    @Override
    public List<Voiture> getListeVoituresAffecter() {
        return voitureDAO.getListeVoituresAffecter();
    }

    @Override
    public List<Voiture> getListeVoitureByMarque(String marque) {
        return voitureDAO.getListeVoitureByMarque(marque);
    }

    @Override
    public Boolean existeVoiture(Voiture v) {
        return voitureDAO.existeVoiture(v);
    }

    @Override
    public List<Voiture> getListeVoituresAffecterSalarie() {
        return voitureDAO.getListeVoituresAffecterSalarie();
    }

    @Override
    public List<Voiture> getListeVoituresAffecterChantier() {
        return voitureDAO.getListeVoituresAffecterChantier();
    }

    //////////////////////////////////////////////////////////////////////////////
    ////////////////////////// Historique voiture  ///////////////////////////////
    //////////////////////////////////////////////////////////////////////////////
    @Override
    public void ajoutHistorique(HistoriqueVoiture hv) {
        voitureDAO.ajoutHistorique(hv);
    }

    @Override
    public List<HistoriqueVoiture> getAllHistoriqueVoiture() {
        return voitureDAO.getAllHistoriqueVoiture();
    }

    @Override
    public List<HistoriqueVoiture> getAllHistoriqueVoitureByIdVoiture(Long id) {
        return voitureDAO.getAllHistoriqueVoitureByIdVoiture(id);
    }

    @Override
    public List<HistoriqueVoiture> getAllHistoriqueVoitureByIdSalarier(Long id) {
        return voitureDAO.getAllHistoriqueVoitureByIdSalarier(id);
    }

    @Override
    public List<HistoriqueVoiture> getAllHistoriqueVoitureByIdChantier(Long id) {
        return voitureDAO.getAllHistoriqueVoitureByIdChantier(id);
    }

    @Override
    public List<HistoriqueVoiture> getAllHistoriqueVoitureSalarier() {
        return voitureDAO.getAllHistoriqueVoitureSalarier();
    }

    @Override
    public List<HistoriqueVoiture> getAllHistoriqueVoitureChantier() {
        return voitureDAO.getAllHistoriqueVoitureChantier();
    }

    @Override
    public List<HistoriqueVoiture> getAllHistoriqueVoitureSalarierByDate(Date ddb, Date df) {
        return voitureDAO.getAllHistoriqueVoitureSalarierByDate(ddb, df);
    }

    @Override
    public List<HistoriqueVoiture> getAllHistoriqueVoitureChantierByDate(Date ddb, Date df) {
        return voitureDAO.getAllHistoriqueVoitureChantierByDate(ddb, df);
    }

    @Override
    public String getChantierBySalaryID(Long id)  {
        return voitureDAO.getChantierBySalaryID(id);
    }

    
    
    
    
    
    
}
