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
import ma.bservices.beans.PeriodeEssai;
import ma.bservices.services.ParametrageService;
import org.springframework.stereotype.Component;

/**
 *
 * @author Mahdi
 */
@Component
@ManagedBean
@ApplicationScoped
public class PeriodeEssaiMb implements Serializable {

    @ManagedProperty(value = "#{parametrageService}")
    private ParametrageService parametrageService;
    private List<PeriodeEssai> periodeEssais;

    public ParametrageService getParametrageService() {
        return parametrageService;
    }

    public void setParametrageService(ParametrageService parametrageService) {
        this.parametrageService = parametrageService;
    }

    public List<PeriodeEssai> getPeriodeEssais() {
        return periodeEssais;
    }

    public void setPeriodeEssais(List<PeriodeEssai> periodeEssais) {
        this.periodeEssais = periodeEssais;
    }

    @PostConstruct
    public void init() {
        parametrageService = Module.ctx.getBean(ParametrageService.class);
        periodeEssais = parametrageService.listePeriodesEssaiContrats(0,
                Integer.parseInt(parametrageService.nombrePeriodesEssaiContrats("").toString()), "");
    }
}
