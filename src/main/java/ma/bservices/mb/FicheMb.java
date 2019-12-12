/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.mb;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import ma.bservices.beans.Chantier;
import ma.bservices.beans.FichePointageLot;
import ma.bservices.beans.Utilisateur;
import ma.bservices.mb.services.Module;
import ma.bservices.services.FichePLService;
import ma.bservices.tgcc.Entity.PointageEngin;
import ma.bservices.tgcc.service.Engin.UtilisateurService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 *
 * @author Mahdi
 */
@Component
@ManagedBean
@ViewScoped
public class FicheMb implements Serializable {

    @ManagedProperty("#{fichePLService}")
    private FichePLService fichePLService;
    private List<FichePointageLot> fiches;
    private String chemin;

    @ManagedProperty(value = "#{utilisateurService}")
    private UtilisateurService utilisateurService;

    public List<FichePointageLot> getFiches() {
        return fiches;
    }

    public void setFiches(List<FichePointageLot> fiches) {
        this.fiches = fiches;
    }

    public FichePLService getFichePLService() {
        return fichePLService;
    }

    public void setFichePLService(FichePLService fichePLService) {
        this.fichePLService = fichePLService;
    }

    public UtilisateurService getUtilisateurService() {
        return utilisateurService;
    }

    public void setUtilisateurService(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }
    
    

    public String getChemin() {
        return chemin;
    }

    public void setChemin(String Chemin) {
        this.chemin = Chemin;
    }

    @PostConstruct
    public void init() {
        fichePLService = Module.ctx.getBean(FichePLService.class);
        utilisateurService = Module.ctx.getBean(UtilisateurService.class);
        fiches = fichePLService.findAll();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String user = auth.getPrincipal().toString();
        Utilisateur u = utilisateurService.getUsersByLogin(user);
        List<Chantier> listCh = utilisateurService.findChantiersByUser(u);
        
        List<FichePointageLot> temp = new LinkedList<>();
        if (fiches != null && listCh != null) {
            for (FichePointageLot c : fiches) {
                if (listCh.contains(c.getChantier())) {
                    temp.add(c);
                }
            }
        }
        fiches = new LinkedList<>();
        fiches.addAll(temp);

    }

    public void showFiche(FichePointageLot fiche) {
        System.out.println("fiche chemin" + fiche.getChemin());
        chemin = fiche.getChemin();
    }
}
