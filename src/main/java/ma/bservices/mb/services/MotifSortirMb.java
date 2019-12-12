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
import ma.bservices.beans.Motif;
import ma.bservices.services.ParametrageService;
import org.springframework.stereotype.Component;

/**
 *
 * @author Mahdi
 */
@Component
@ManagedBean(name = "motifSortirMb")
@ApplicationScoped
public class MotifSortirMb implements Serializable {

    @ManagedProperty(value = "#{parametrageService}")
    private ParametrageService parametrageService;
    private List<Motif> motifs;

    public ParametrageService getParametrageService() {
        return parametrageService;
    }

    public void setParametrageService(ParametrageService parametrageService) {
        this.parametrageService = parametrageService;
    }

    public List<Motif> getMotifs() {
        return motifs;
    }

    public void setMotifs(List<Motif> motifs) {
        this.motifs = motifs;
    }

    @PostConstruct
    public void init() {
        parametrageService = Module.ctx.getBean(ParametrageService.class);
        motifs = parametrageService.listeMotifs(0,
                Integer.parseInt(parametrageService.nombreMotifsSortie("").toString()), "");
    }
}
