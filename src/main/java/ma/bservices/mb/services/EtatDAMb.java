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
import ma.bservices.beans.EtatDA;
import ma.bservices.services.FamilleArticle;
import ma.bservices.services.ParametrageService;
import org.springframework.stereotype.Component;

/**
 *
 * @author Mahdi
 */
@Component
@ManagedBean
@ApplicationScoped
public class EtatDAMb implements Serializable {

    @ManagedProperty(value = "#{parametrageService}")
    private ParametrageService parametrageService;
    private List<EtatDA> etatDA;

    private List<FamilleArticle> fam1;
    private List<FamilleArticle> fam2;
    private List<FamilleArticle> fam3;

    public EtatDAMb() {
    }

    public ParametrageService getParametrageService() {
        return parametrageService;
    }

    public void setParametrageService(ParametrageService parametrageService) {
        this.parametrageService = parametrageService;
    }

    public List<EtatDA> getEtatDA() {
        return etatDA;
    }

    public void setEtatDA(List<EtatDA> etatDA) {
        this.etatDA = etatDA;
    }

    public List<FamilleArticle> getFam1() {
        return fam1;
    }

    public void setFam1(List<FamilleArticle> fam1) {
        this.fam1 = fam1;
    }

    public List<FamilleArticle> getFam2() {
        return fam2;
    }

    public void setFam2(List<FamilleArticle> fam2) {
        this.fam2 = fam2;
    }

    public List<FamilleArticle> getFam3() {
        return fam3;
    }

    public void setFam3(List<FamilleArticle> fam3) {
        this.fam3 = fam3;
    }

    @PostConstruct
    public void init() {
        parametrageService = Module.ctx.getBean(ParametrageService.class);
        etatDA = parametrageService.listeEtatsDA(0, Integer.parseInt(parametrageService.nombreEtatsDA("").toString()), "");
        String reqDos = " (dos=700 OR dos=999 OR dos =998 OR dos=997) ";
        fam1 = (List<FamilleArticle>) parametrageService.listeFamilles(0, 10, 1, 3, "", reqDos);
//        fam2 = (List<FamilleArticle>) parametrageService.listeFamilles(0, 10, 1, 5, "", reqDos);
//        fam3 = (List<FamilleArticle>) parametrageService.listeFamilles(0, 10, 1, 8, "", reqDos);
    }

}
