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
import ma.bservices.beans.NiveauFonction;
import ma.bservices.services.NiveauFonctionService;
import org.springframework.stereotype.Component;

/**
 *
 * @author Mahdi
 */
@Component
@ManagedBean
@ApplicationScoped
public class NiveauMb implements Serializable {

    @ManagedProperty(value = "#{niveauFonctionService}")
    private NiveauFonctionService niveauFonctionService;
    private List<NiveauFonction> niveauFonctions;

    public NiveauFonctionService getNiveauFonctionService() {
        return niveauFonctionService;
    }

    public void setNiveauFonctionService(NiveauFonctionService niveauFonctionService) {
        this.niveauFonctionService = niveauFonctionService;
    }

    public List<NiveauFonction> getNiveauFonctions() {
        return niveauFonctions;
    }

    public void setNiveauFonctions(List<NiveauFonction> niveauFonctions) {
        this.niveauFonctions = niveauFonctions;
    }

    @PostConstruct
    public void init() {
        niveauFonctions = niveauFonctionService.getAll();
    }
}
