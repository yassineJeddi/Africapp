/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.mb.services;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import ma.bservices.beans.Utilisateur;
import ma.bservices.services.AdministrationService;
import org.springframework.stereotype.Component;

/**
 *
 * @author Mahdi
 */
@Component
@ManagedBean
@ApplicationScoped
public class UsersMb implements Serializable {

    @ManagedProperty(value = "#{administrationService}")
    private AdministrationService administrationService;
    private List<Utilisateur> utilisateurs;

    public AdministrationService getAdministrationService() {
        return administrationService;
    }

    public void setAdministrationService(AdministrationService administrationService) {
        this.administrationService = administrationService;
    }

    public List<Utilisateur> getUtilisateurs() {
        return utilisateurs;
    }

    public void setUtilisateurs(List<Utilisateur> utilisateurs) {
        this.utilisateurs = utilisateurs;
    }

    @PostConstruct
    public void init() {
        administrationService = Module.ctx.getBean(AdministrationService.class);
        utilisateurs = administrationService.listeUtilisateurs(0,
                Integer.parseInt(administrationService.nombreUsers(0, 0, null, "", "", "", "").toString()),
                "", "", "", "");
    }

}
