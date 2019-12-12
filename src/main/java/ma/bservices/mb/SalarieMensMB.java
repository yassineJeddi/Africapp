/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.mb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import ma.bservices.tgcc.Entity.Mensuel;
import ma.bservices.tgcc.service.Mensuel.MensuelService;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

/**
 *
 * @author yassine
 */
@Component
@ManagedBean(name = "salarieMensMb")
@ViewScoped
public class SalarieMensMB  implements Serializable {
    
    
    @ManagedProperty(value = "#{mensuelService}")
    private MensuelService mensuelService;
    
    private List<Mensuel> mensuels;
    
    private ELContext elContext;

    public MensuelService getMensuelService() {
        return mensuelService;
    }

    public void setMensuelService(MensuelService mensuelService) {
        this.mensuelService = mensuelService;
    }

    public List<Mensuel> getMensuels() {
        return mensuels;
    }

    public void setMensuels(List<Mensuel> mensuels) {
        this.mensuels = mensuels;
    }

    public ELContext getElContext() {
        return elContext;
    }

    public void setElContext(ELContext elContext) {
        this.elContext = elContext;
    }
    
    
    
    
    @PostConstruct
    public void init() {
        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        mensuelService = ctx.getBean(MensuelService.class);
        
        elContext = FacesContext.getCurrentInstance().getELContext();
        mensuels = new ArrayList<Mensuel>();
    }
    
    
    public void chargerMensuel(){
        try {
            mensuels=mensuelService.findAll();
            
        } catch (Exception e) {
            mensuels = new ArrayList<Mensuel>();
            System.out.println("Erreur de chargement la liste des mensuel car "+e.getMessage());
        }
        
    }
    
    
}
