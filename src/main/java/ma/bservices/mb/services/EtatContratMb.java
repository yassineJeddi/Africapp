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
import ma.bservices.beans.EtatContrat;
import ma.bservices.services.ParametrageService;
import org.springframework.stereotype.Component;

/**
 *
 * @author Mahdi
 */
@Component
@ManagedBean
@ApplicationScoped
public class EtatContratMb implements Serializable {

    @ManagedProperty(value = "#{parametrageService}")
    private ParametrageService parametrageService;
    private List<EtatContrat> etats;

    public EtatContratMb() {
    }

    public ParametrageService getParametrageService() {
        return parametrageService;
    }

    public void setParametrageService(ParametrageService parametrageService) {
        this.parametrageService = parametrageService;
    }

    public List<EtatContrat> getEtats() {
        return etats;
    }

    public void setEtats(List<EtatContrat> etats) {
        this.etats = etats;
    }

    @PostConstruct
    public void init() {
        parametrageService = Module.ctx.getBean(ParametrageService.class);
        etats = parametrageService.listeEtatsContrats(0, Integer.parseInt(parametrageService.nombreEtatsContrats("").toString()), "");
    }

}
