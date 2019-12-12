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
import ma.bservices.beans.Duree;
import ma.bservices.services.ParametrageService;
import org.springframework.stereotype.Component;

/**
 *
 * @author Mahdi
 */
@Component
@ManagedBean
@ApplicationScoped
public class DureeMb implements Serializable {

    @ManagedProperty(value = "#{parametrageService}")
    private ParametrageService parametrageService;
    private List<Duree> durees;

    public ParametrageService getParametrageService() {
        return parametrageService;
    }

    public void setParametrageService(ParametrageService parametrageService) {
        this.parametrageService = parametrageService;
    }

    public List<Duree> getDurees() {
        return durees;
    }

    public void setDurees(List<Duree> durees) {
        this.durees = durees;
    }

    @PostConstruct
    public void init() {
        parametrageService = Module.ctx.getBean(ParametrageService.class);
        durees = parametrageService.listeDureesContrats(0,
                Integer.parseInt(parametrageService.nombreDureesContrats("").toString()), "");
    }

}
