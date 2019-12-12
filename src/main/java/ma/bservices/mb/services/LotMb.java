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
import ma.bservices.beans.Lot;
import ma.bservices.services.Evol_LotService;
import org.springframework.stereotype.Component;

/**
 *
 * @author Mahdi
 */
@Component
@ManagedBean(name = "Service_lots")
@ApplicationScoped
public class LotMb implements Serializable {

    @ManagedProperty("#{lotServiceEvol}")
    private Evol_LotService lotService;
    private List<Lot> lots;

    @PostConstruct
    public void init() {
        lotService = Module.ctx.getBean(Evol_LotService.class);
        lots = lotService.findAll();
    }

    public Evol_LotService getLotService() {
        return lotService;
    }

    public void setLotService(Evol_LotService lotService) {
        this.lotService = lotService;
    }

    public List<Lot> getLots() {
        return lots;
    }

    public void setLots(List<Lot> lots) {
        this.lots = lots;
    }

}
