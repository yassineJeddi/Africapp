/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.mb;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import ma.bservices.beans.Fonction;
import ma.bservices.beans.Lot;
import ma.bservices.mb.services.Module;
import ma.bservices.services.Evol_LotService;
import ma.bservices.services.ParametrageService;
import org.springframework.stereotype.Component;

/**
 *
 * @author Mahdi
 */
@Component
@ManagedBean
@ViewScoped
public class LotMb implements Serializable {

    @ManagedProperty(value = "#{lotServiceEvol}")
    private Evol_LotService lotService;
    private ParametrageService parametrageService;
    private List<Lot> lots, selection, lotsByFonction, filtedListLot, filterLot;
    private List<Fonction> filtedListFonction;
    private Integer idFonction;
    private Fonction fonction = new Fonction();

    @PostConstruct
    public void init() {
        lotService = Module.ctx.getBean(Evol_LotService.class);
        parametrageService = Module.ctx.getBean(ParametrageService.class);
    }

    public void affecter() {
        Fonction f = parametrageService.getFonction(idFonction);
        for (Lot l : selection) {
            l.getFonctions().add(f);
            lotService.updateLot(l);
        }
        onSelectedFonction();
        Module.message(0, "Afféctation éffectuée", "");
    }

    public void onSelectedFonction() {
        lots = (idFonction != null) ? lotService.findNotHaveFonction(idFonction) : null;
    }

    public void listLots() {
        try {
            lotsByFonction = lotService.lotByFonction(fonction.getId());
        } catch (Exception ex) {
            Module.message(3, "Oups !", "opération échouée");
        }
    }

    public void desaffecter(Lot l) {
        boolean remove = l.getFonctions().remove(fonction);
        try {
            if (remove) {
                lotService.updateLot(l);
                onSelectedFonction();
                listLots();
                Module.message(0, "désaffectation effectuée ", "");
            }
        } catch (Exception ex) {
            Module.message(3, "Oups ! erreur", "");
        }

    }

//getter et setter
    public List<Lot> getFiltedListLot() {
        return filtedListLot;
    }

    public List<Lot> getFilterLot() {
        return filterLot;
    }

    public void setFilterLot(List<Lot> filterLot) {
        this.filterLot = filterLot;
    }

    public List<Fonction> getFiltedListFonction() {
        return filtedListFonction;
    }

    public void setFiltedListFonction(List<Fonction> filtedListFonction) {
        this.filtedListFonction = filtedListFonction;
    }

    public void setFiltedListLot(List<Lot> filtedListLot) {
        this.filtedListLot = filtedListLot;
    }

    public Fonction getFonction() {
        return fonction;
    }

    public void setFonction(Fonction fonction) {
        this.fonction = fonction;
    }

    public List<Lot> getLotsByFonction() {
        return lotsByFonction;
    }

    public void setLotsByFonction(List<Lot> lotsByFonction) {
        this.lotsByFonction = lotsByFonction;
    }

    public ParametrageService getParametrageService() {
        return parametrageService;
    }

    public void setParametrageService(ParametrageService parametrageService) {
        this.parametrageService = parametrageService;
    }

    public Integer getIdFonction() {
        return idFonction;
    }

    public void setIdFonction(Integer idFonction) {
        this.idFonction = idFonction;
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

    public List<Lot> getSelection() {
        return selection;
    }

    public void setSelection(List<Lot> selection) {
        this.selection = selection;
    }
}
