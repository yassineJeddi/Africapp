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
import ma.bservices.beans.Chantier;
import ma.bservices.paginate.ChantierPaginate;
import ma.bservices.services.ChantierService;
import org.springframework.stereotype.Component;

/**
 *
 * @author Mahdi
 */
@Component
@ManagedBean(name = "evol_chantierMb")
@ApplicationScoped
public class Evol_ChantierMb implements Serializable {

    @ManagedProperty(value = "#{chantierServiceEvol}")
    private ChantierService chantierService;
    private List<Chantier> chantiers;
    private List<Chantier> ateliers;

    public Evol_ChantierMb() {
    }

    public ChantierService getChantierService() {
        return chantierService;
    }

    public void setChantierService(ChantierService chantierService) {
        this.chantierService = chantierService;
    }

    public List<Chantier> getChantiers() {
        return chantiers;
    }

    public void setChantiers(List<Chantier> chantiers) {
        this.chantiers = chantiers;
    }

    @PostConstruct
    public void init() {
        page = 1;
        chantierService = new ChantierService();
        //System.out.println("----------- Part 1");
        chantierService = Module.ctx.getBean(ChantierService.class);
        //System.out.println("----------- Part 2");
        nbr = Integer.parseInt(chantierService.nombreChantiers("", "", Module.dos).toString());
        //System.out.println("----------- Part 3");
        chantiers = chantierService.chantiersList(0, nbr, "", "", Module.dos);
        ateliers = chantierService.ateliersList(0, nbr, "", "", Module.dos,"DP ");

        //System.out.println("----------- Part 4" + chantiers.size());
    }
    private Integer page, nbr;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getNbr() {
        return nbr;
    }

    public void setNbr(Integer nbr) {
        this.nbr = nbr;
    }

    /**
     * methode reload la liste des mensuels
     */
    public void reload_Chantier() {

        if (chantiers != null) {
            chantiers.clear();
        }
        nbr = Integer.parseInt(chantierService.nombreChantiers("", "", Module.dos).toString());
        chantiers = chantierService.chantiersList(0, nbr, "", "", Module.dos);
    }

    public void onPaginate() {
        chantiers = ChantierPaginate.page(page);
    }

    public void onNext() {
        page += 1;
        chantiers = ChantierPaginate.page(page);
    }

    public void onPrevious() {
        page -= 1;
        chantiers = ChantierPaginate.page(page);
    }

    public void onFirst() {
        page = 1;
        chantiers = ChantierPaginate.page(page);
    }

    public void onLast() {
        page = (nbr % 10 == 0) ? nbr / 10 : nbr / 10 + 1;
        chantiers = ChantierPaginate.page(page);
    }

    public List<Chantier> getAteliers() {
        return ateliers;
    }

    public void setAteliers(List<Chantier> ateliers) {
        this.ateliers = ateliers;
    }
    
    
}
