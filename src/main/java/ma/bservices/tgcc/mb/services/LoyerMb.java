/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.mb.services;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import ma.bservices.tgcc.Entity.LoyerChantier;
import ma.bservices.tgcc.Entity.LoyerSalarie;
import ma.bservices.tgcc.service.Mensuel.LoyerService;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

/**
 *
 * @author a.wattah
 */
@Component
@ManagedBean(name = "loyer_ServMb")
@ApplicationScoped
public class LoyerMb implements Serializable {

    @ManagedProperty(value = "#{loyerService}")
    private LoyerService loyerService;

    private List<LoyerSalarie> l_loyer_salaries;

    private List<LoyerChantier> l_loyer_chantiers;

    /**
     * getters and setters
     *
     */
    public LoyerService getLoyerService() {
        return loyerService;
    }

    public void setLoyerService(LoyerService loyerService) {
        this.loyerService = loyerService;
    }

    public List<LoyerSalarie> getL_loyer_salaries() {
        return l_loyer_salaries;
    }

    public void setL_loyer_salaries(List<LoyerSalarie> l_loyer_salaries) {
        this.l_loyer_salaries = l_loyer_salaries;
    }

    public List<LoyerChantier> getL_loyer_chantiers() {
        return l_loyer_chantiers;
    }

    public void setL_loyer_chantiers(List<LoyerChantier> l_loyer_chantiers) {
        this.l_loyer_chantiers = l_loyer_chantiers;
    }

    /**
     * getters and setters
     */
    @PostConstruct
    public void init() {
        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        loyerService = ctx.getBean(LoyerService.class);

        l_loyer_salaries = loyerService.loyerSalaries();

        l_loyer_chantiers = loyerService.loyerChantiers();
    }

    public void reloadLoyerSalarie() {

        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        loyerService = ctx.getBean(LoyerService.class);

        if (l_loyer_salaries != null) {
            if (!l_loyer_salaries.isEmpty()) {
                l_loyer_salaries.clear();
            }
        }

        l_loyer_salaries = loyerService.loyerSalaries();

    }

    public void reloadLoyerChantier() {

        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        loyerService = ctx.getBean(LoyerService.class);

        if (l_loyer_chantiers != null) {
            if (!l_loyer_chantiers.isEmpty()) {
                l_loyer_chantiers.clear();
            }
        }

        l_loyer_chantiers = loyerService.loyerChantiers();

    }

}
