/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.service.Engin;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import ma.bservice.tgcc.Constante.Constante;
import ma.bservices.beans.Chantier;
import ma.bservices.beans.Salarie;
import ma.bservices.beans.Utilisateur;
import ma.bservices.tgcc.Entity.ECHEANCIER_VIDANGE;
import ma.bservices.tgcc.Entity.Engin;
import ma.bservices.tgcc.Entity.InterventionMaintenance;
import ma.bservices.tgcc.Entity.Panne;
import ma.bservices.tgcc.Entity.ReferentielEngin;
import ma.bservices.tgcc.dao.engin.ChantierDAO;
import ma.bservices.tgcc.dao.engin.EnginDAO;
import ma.bservices.tgcc.dao.engin.EnginDAOImpl;
import ma.bservices.tgcc.dao.engin.PanneDAO;
import ma.bservices.tgcc.dao.engin.UtilisateurDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 *
 * @author zakaria.dem
 */
@Service("enginService")
public class EnginServiceImpl implements EnginService, Serializable {

    @Autowired
    private EnginDAO enginDAO;

    @Autowired
    private ChantierDAO chantierDAO;

    @Autowired
    private PanneDAO panneDAO;

    public EnginDAO getEnginDAO() {
        return enginDAO;
    }

    @Autowired
    private UtilisateurDAO utilisateurDAO;

    public void setEnginDAO(EnginDAO enginDAO) {
        this.enginDAO = enginDAO;
    }

    public ChantierDAO getChantierDAO() {
        return chantierDAO;
    }

    public PanneDAO getPanneDAO() {
        return panneDAO;
    }

    public void setPanneDAO(PanneDAO panneDAO) {
        this.panneDAO = panneDAO;
    }

    public UtilisateurDAO getUtilisateurDAO() {
        return utilisateurDAO;
    }

    public void setUtilisateurDAO(UtilisateurDAO utilisateurDAO) {
        this.utilisateurDAO = utilisateurDAO;
    }

    public void setChantierDAO(ChantierDAO chantierDAO) {
        this.chantierDAO = chantierDAO;
    }

    @Override
    public List<Engin> findAll() {
        return enginDAO.findAll();
    }
    @Override
    public List<Engin> enginsArchive() {
        return enginDAO.enginsArchive();
    }
    @Override
    public List<Engin> enginsReforme() {
        return enginDAO.enginsReforme();
    }
    @Override
    public List<Engin> enginsActif() {
        return enginDAO.enginsActif();
    }

    @Override
    public List<Engin> enginsEnPanne(){
        return enginDAO.enginsEnPanne();
    }

    @Override
    public List<Engin> enginsActifNoPanne(){
        return enginDAO.enginsActifNoPanne();
    }
    @Override
    public void addEngin(Engin engin, int chantier_id) {

        Chantier chantier = chantierDAO.findById(chantier_id);
        engin.setPrjapId(chantier);

        enginDAO.ajouterEngin(engin);
    }

    @Override
    public List<Panne> lastPanneByEnginPanne(){
        return enginDAO.lastPanneByEnginPanne();
    }

    @Override
    public List<Engin> search(String code, String designation, String marque, String etat, int chantier_id) {

        return enginDAO.rechercherEngin(code, designation, marque, etat, chantier_id);

    }


    @Override
    public List<Engin> findAllEnginByChantierId(Integer chantier_id){
        return enginDAO.findAllEnginByChantierId(chantier_id);
    }
    /**
     * affectation d'un engin à un chantier
     *
     * @param engin
     * @param chantierID
     */
    @Override
    public void affecterEngin(Engin engin, int chantierID) {

        Chantier Ch = chantierDAO.findById(chantierID);
        // ajouter date affectation
        engin.setDateAFFECTATION(new Date());
//        Engin e_toUpdate = enginDAO.findOneByCode(engin.getCode());
        if (Ch instanceof Chantier && engin instanceof Engin) {

            engin.setPrjapId(Ch);
//            engin.setEtatTransfert(true);
            enginDAO.affecterEngin(engin);

        }
    }

    @Override
    public List<Engin> findAllInChantier(Integer chantier_id) {
        return enginDAO.findAllInChantier(chantier_id);

    }

    /**
     * Retourne un engin dans un liste d'engin dans un chantier ( la liste est
     * données en paramètre ) par son code la recherche se fera dans la partie
     * business sans accèder à la base de donnés
     *
     *
     * @param enginsInChanier
     * @return
     */
    @Override
    public Engin findEnginByCodeFromEnginsInChantier(List<Engin> enginsInChanier, String Code) {

        Engin engin = null;

        for (int i = 0; i < enginsInChanier.size(); i++) {
            if (enginsInChanier.get(i).getCode().compareTo(Code) == 0) {
                engin = enginsInChanier.get(i);
                return engin;
            }
        }

        return null;
    }

    /**
     * modifie l'etat de l'engin, le rendre en panne ajouter l'engin dans
     * l'historique des pannes
     *
     * @param panne
     */
    @Override
    public Boolean addEnginPanne(Panne panne) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        try {
            Engin enginToupdate = panne.getEngin();
            enginToupdate.setEtat(Constante.CODE_ETAT_ENGIN_PANNE);

            //mettre en dépôt
            Chantier chantier_depot = chantierDAO.get_depot();
            enginToupdate.setPrjapId(chantier_depot);

            //ajouter la date de la panne
            panne.setDate(new Date());

            Utilisateur u = utilisateurDAO.UserByLogin(auth.getPrincipal().toString());
            panne.setUser_MEPanne(u);

            panneDAO.add(panne);

            //ajouter l'historique de la panne à l'engin
            enginToupdate.setHist_panne_id(panne);
            enginDAO.ajouterEngin(enginToupdate);

            return Boolean.TRUE;
        } catch (Exception e) {
            System.out.println("ex" + e.getMessage());
            return Boolean.FALSE;
        }

    }

    @Override
    public Panne lastPanneByEngin(Engin engin){
        return enginDAO.lastPanneByEngin(engin);
    }
    
    @Override
    public List<Engin> findOneByArchive() {
        return enginDAO.findOneByArchive();
    }

    @Override
    public void updateArchiveEngin(Engin engin) {
        enginDAO.affecterEngin(engin);
    }

    @Override
     public void updateEngin(Engin engin)  {
        enginDAO.updateEngin(engin);
    }

    @Override
     public void updateListEngin(List<Engin> engin){
        enginDAO.updateListEngin(engin);
    }
    @Override
    public List<Engin> rechercherEnginByFa(String code, String designation, String marque, String etat, int chantier_id, String typeE, String familleE) {
        return enginDAO.rechercherEnginByFa(code, designation, marque, etat, chantier_id, typeE, familleE);
    }

    @Override
    public List<Engin> findAllInChantierArchive(int chantier_id) {
        return enginDAO.findAllInChantierArchive(chantier_id);
    }

    @Override
    public List<Engin> findAllChantierArchivePanne(int chantier_id) {
        return enginDAO.findAllChantierArchivePanne(chantier_id);
    }

    @Override
    public List<Engin> findAllEnginPointageAutoParChantier(int chantier_id){
         return enginDAO.findAllEnginPointageAutoParChantier(chantier_id);
    }

    @Override
    public List<Engin> findAllEnginPointageAutoDept(int chantier_id){
         return enginDAO.findAllEnginPointageAutoDept(chantier_id);
    }

    @Override
    public List<Engin> findAllEnginPointageManuelDept(int chantier_id){
         return enginDAO.findAllEnginPointageManuelDept(chantier_id);
    }
    @Override
    public List<String> findAllMarque() {
        return enginDAO.findAllMarque();
    }
    
    
    @Override
    public Engin findOneById(Integer id) {
         return enginDAO.findOneById(id);
    }

    @Override
    public Engin findOneByCode(String code) {
        return enginDAO.findOneByCode(code);
    }

    /*
     on affecte à un chantier et puis ajoute la date de la mise en marche dans l'historique de la panne associé à l'engin en question.
     */
    @Override
    public void mise_en_marche(Engin engin, int chantierID, Panne p) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        affecterEngin(engin, chantierID);

        if (engin.getHist_panne_id() != null) {

            System.out.println("entre : " + auth.getPrincipal().toString());

            Utilisateur u = utilisateurDAO.getUserByLogin(auth.getPrincipal().toString());
            engin.getHist_panne_id().setUser_MEMarche(u);
            EnginDAOImpl eDao = new EnginDAOImpl();
            p.setUser_MEMarche(u);
            panneDAO.update(p);

        }

    }

    @Override
    public List<Salarie> allConducteur() {
        return enginDAO.allConducteur();
    }

    @Override
    public String nextCod(String designiation,String codeD){
         return enginDAO.nextCod(designiation,codeD);
    }

    @Override
    public List<InterventionMaintenance> listIntervPr(int idEngin) {
        return enginDAO.listIntervPr(idEngin);
    }

    @Override
    public List<InterventionMaintenance> listIntervCr(int idEngin) {
        return enginDAO.listIntervCr(idEngin);
    }

    @Override
    public ECHEANCIER_VIDANGE lastEcheancierVidangeByCodeEngin(String code) {
        return enginDAO.lastEcheancierVidangeByCodeEngin(code);
    }

    @Override
    public void addReferentielEngin(ReferentielEngin r) {
          enginDAO.addReferentielEngin(r);
    }

    @Override
    public void editReferentielEngin(ReferentielEngin r) {
        enginDAO.editReferentielEngin(r);
    }

    @Override
    public void remouvReferentielEngin(ReferentielEngin r) {
        enginDAO.remouvReferentielEngin(r);
    }

    @Override
    public List<ReferentielEngin> allReferentielEnginByEngin(Engin e) {
        return enginDAO.allReferentielEnginByEngin(e);
    }
    
    
}
