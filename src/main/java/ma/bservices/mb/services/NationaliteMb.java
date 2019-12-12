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
import ma.bservices.services.ParametrageService;
import org.springframework.stereotype.Component;
import ma.bservices.beans.Nationalite;

/**
 *
 * @author Mahdi
 */
@Component
@ManagedBean
@ApplicationScoped
public class NationaliteMb implements Serializable {

    @ManagedProperty(value = "#{parametrageService}")
    private ParametrageService parametrageService;
    private List<Nationalite> nationalites;

    public NationaliteMb() {
    }

    public ParametrageService getParametrageService() {
        return parametrageService;
    }

    public void setParametrageService(ParametrageService parametrageService) {
        this.parametrageService = parametrageService;
    }

    public List<Nationalite> getNationalites() {
        return nationalites;
    }

    public void setNationalites(List<Nationalite> nationalites) {
        this.nationalites = nationalites;
    }

    @PostConstruct
    public void init() {
        parametrageService = Module.ctx.getBean(ParametrageService.class);
        nationalites = parametrageService.listeNationalites(0, Integer.parseInt(parametrageService.nombreNationalites("").toString()), "");
    }

}
