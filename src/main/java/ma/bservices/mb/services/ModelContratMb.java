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
import ma.bservices.beans.ModelContrat;
import ma.bservices.services.ParametrageService;
import org.springframework.stereotype.Component;

/**
 *
 * @author Mahdi
 */
@Component
@ManagedBean(name = "modelContrat")
@ApplicationScoped
public class ModelContratMb implements Serializable {

    @ManagedProperty(value = "#{parametrageService}")
    private ParametrageService parametrageService;
    private List<ModelContrat> modeles;

    public ParametrageService getParametrageService() {
        return parametrageService;
    }

    public void setParametrageService(ParametrageService parametrageService) {
        this.parametrageService = parametrageService;
    }

    public List<ModelContrat> getModeles() {
        return modeles;
    }

    public void setModeles(List<ModelContrat> modeles) {
        this.modeles = modeles;
    }
        
    @PostConstruct
    public void init() {    
        parametrageService = Module.ctx.getBean(ParametrageService.class);
        modeles = parametrageService.listeModelsContrats(0,
                Integer.parseInt(parametrageService.nombreModelsContrats("").toString()), "");
    }
}
