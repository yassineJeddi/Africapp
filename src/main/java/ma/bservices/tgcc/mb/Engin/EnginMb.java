/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.mb.Engin;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import ma.bservices.tgcc.service.Engin.EnginService;
import ma.bservices.tgcc.service.Engin.PointageEnginServices;
import ma.bservices.tgcc.Entity.Engin;
import ma.bservices.tgcc.Entity.PointageEngin;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

/**
 *
 * @author zakaria.dem
 */
@Component
@ManagedBean(name = "engin")
@RequestScoped
public class EnginMb implements Serializable {

    @ManagedProperty(value = "#{enginService}")
    private EnginService enginSerive;

    @ManagedProperty(value = "#{pointageEnginService}")
    private PointageEnginServices pointageEnginSerive;

    private List<Engin> engin_panne;

    private List<Engin> engins;

    private PointageEngin recherchePointageEngin = new PointageEngin();

    private int sizeCus = 0;

    public EnginService getEnginSerive() {
        return enginSerive;
    }

    public void setEnginSerive(EnginService enginSerive) {
        this.enginSerive = enginSerive;
    }

    public int getSizeCus() {
        return sizeCus;
    }

    public List<Engin> getEngins() {
        return engins;
    }

    public void setEngins(List<Engin> engins) {
        this.engins = engins;
    }

    public void setSizeCus(int sizeCus) {
        this.sizeCus = sizeCus;
    }

    public PointageEngin getRecherchePointageEngin() {
        return recherchePointageEngin;
    }

    public void setRecherchePointageEngin(PointageEngin recherchePointageEngin) {
        this.recherchePointageEngin = recherchePointageEngin;
    }

    public PointageEnginServices getPointageEnginSerive() {
        return pointageEnginSerive;
    }

    public void setPointageEnginSerive(PointageEnginServices pointageEnginSerive) {
        this.pointageEnginSerive = pointageEnginSerive;
    }

    public List<Engin> getEngin_panne() {
        return engin_panne;
    }

    public void setEngin_panne(List<Engin> engin_panne) {
        this.engin_panne = engin_panne;
    }

    /**
     * Creates a new instance of EnginMb
     */
    public EnginMb() {

    }

    @PostConstruct
    public void init() {
        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        enginSerive = ctx.getBean(EnginService.class);
        
        engins = enginSerive.findAll();
        
        System.out.println("engin_panne");
        
      
        
        engin_panne = enginSerive.search(null, null  , "panne", null, -1);
        
    }
    
    
    

}
