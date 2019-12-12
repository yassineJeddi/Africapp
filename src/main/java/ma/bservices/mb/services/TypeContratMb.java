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
import ma.bservices.beans.TypeContrat;
import ma.bservices.services.ParametrageService;
import org.springframework.stereotype.Component;

/**
 *
 * @author Mahdi
 */
@Component
@ManagedBean
@ApplicationScoped
public class TypeContratMb implements Serializable {

    @ManagedProperty(value = "#{parametrageService}")
    private ParametrageService parametrageService;
    private List<TypeContrat> typeContrats;

    public ParametrageService getParametrageService() {
        return parametrageService;
    }

    public void setParametrageService(ParametrageService parametrageService) {
        this.parametrageService = parametrageService;
    }

    public List<TypeContrat> getTypeContrats() {
        return typeContrats;
    }

    public void setTypeContrats(List<TypeContrat> typeContrats) {
        this.typeContrats = typeContrats;
    }

    @PostConstruct
    public void init() {
        parametrageService = Module.ctx.getBean(ParametrageService.class);
        typeContrats = parametrageService.listeTypesContrats(0,
                Integer.parseInt(parametrageService.nombreTypesContrats("").toString()), "");
    }

}
