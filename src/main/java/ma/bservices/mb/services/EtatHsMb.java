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
import ma.bservices.beans.EtatHeuresSupplementaires;
import ma.bservices.services.ParametrageService;
import org.springframework.stereotype.Component;

/**
 *
 * @author Mahdi
 */
@Component
@ManagedBean
@ApplicationScoped
public class EtatHsMb implements Serializable {

    @ManagedProperty(value = "#{parametrageService}")
    private ParametrageService parametrageService;
    private List<EtatHeuresSupplementaires> etatHs;

    public EtatHsMb() {
    }

    public List<EtatHeuresSupplementaires> getEtatHs() {
        return etatHs;
    }

    public void setEtatHs(List<EtatHeuresSupplementaires> etatHs) {
        this.etatHs = etatHs;
    }

    public ParametrageService getParametrageService() {
        return parametrageService;
    }

    public void setParametrageService(ParametrageService parametrageService) {
        this.parametrageService = parametrageService;
    }

    @PostConstruct
    public void init() {
        parametrageService = Module.ctx.getBean(ParametrageService.class);
        etatHs = parametrageService.listeEtatHS(0, Integer.parseInt(parametrageService.nombreEtatsHS("").toString()), "");
    }

}
