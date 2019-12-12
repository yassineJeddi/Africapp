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
import ma.bservices.beans.FonctionLocal;
import ma.bservices.services.FonctionService;
import org.springframework.stereotype.Component;

/**
 *
 * @author Mahdi
 */
@Component
@ManagedBean
@ApplicationScoped
public class FonctionLocalMb implements Serializable {

    @ManagedProperty(value = "#{fonctionServiceEvol}")
    private FonctionService fonctionService;
    private List<FonctionLocal> fonctionLocals;

    public FonctionService getFonctionService() {
        return fonctionService;
    }

    public void setFonctionService(FonctionService fonctionService) {
        this.fonctionService = fonctionService;
    }

    public List<FonctionLocal> getFonctionLocals() {
        return fonctionLocals;
    }

    public void setFonctionLocals(List<FonctionLocal> fonctionLocals) {
        this.fonctionLocals = fonctionLocals;
    }

    public FonctionLocalMb() {
    }

    @PostConstruct
    public void init() {
        fonctionService = Module.ctx.getBean(FonctionService.class);
        fonctionLocals = fonctionService.loadAll();
    }

}
