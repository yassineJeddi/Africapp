/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.mb.mensuel;

import java.io.Serializable;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import ma.bservice.tgcc.Constante.Message;
import ma.bservices.tgcc.Entity.LoyerChantier;
import ma.bservices.tgcc.Entity.LoyerSalarie;
import ma.bservices.tgcc.service.Engin.ChantierService;
import ma.bservices.tgcc.service.Mensuel.BonCaisseService;
import ma.bservices.tgcc.service.Mensuel.HistoriqueService;
import ma.bservices.tgcc.service.Mensuel.LoyerService;
import ma.bservices.tgcc.service.Mensuel.MensuelService;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

/**
 *
 * @author airaamane
 *
 * **/
@Component
@ManagedBean(name = "control_fiche_loyer_Mb")
@ViewScoped
public class ControlFicheLoyerMb implements Serializable {


    
     @ManagedProperty(value = "#{loyerService}")
    private LoyerService loyerService;

    @ManagedProperty(value = "#{boncaisseservice}")
    private BonCaisseService bonCaisseService;

    @ManagedProperty(value = "#{chantierService}")
    private ChantierService chantierService;

    @ManagedProperty(value = "#{mensuelService}")
    private MensuelService mensuelService;

    /* VARIABLES */
    
    private LoyerSalarie loyerSalarieSelectMensuel = new LoyerSalarie();
    private LoyerChantier loyerChantierFicheLoyer = new LoyerChantier();
    private int idRequest;
    private boolean updateSalarie = true;

    public boolean isUpdateSalarie() {
        return updateSalarie;
    }

    public void setUpdateSalarie(boolean updateSalarie) {
        this.updateSalarie = updateSalarie;
    }

    public LoyerSalarie getLoyerSalarieSelectMensuel() {
        return loyerSalarieSelectMensuel;
    }

    public void setLoyerSalarieSelectMensuel(LoyerSalarie loyerSalarieSelectMensuel) {
        this.loyerSalarieSelectMensuel = loyerSalarieSelectMensuel;
    }

    public int getIdRequest() {
        return idRequest;
    }

    public void setIdRequest(int idRequest) {
        this.idRequest = idRequest;
    }

    public LoyerChantier getLoyerChantierFicheLoyer() {
        return loyerChantierFicheLoyer;
    }

    public void setLoyerChantierFicheLoyer(LoyerChantier loyerChantierFicheLoyer) {
        this.loyerChantierFicheLoyer = loyerChantierFicheLoyer;
    }
    
    
    public LoyerService getLoyerService() {
        return loyerService;
    }

    public void setLoyerService(LoyerService loyerService) {
        this.loyerService = loyerService;
    }

    public BonCaisseService getBonCaisseService() {
        return bonCaisseService;
    }

    public void setBonCaisseService(BonCaisseService bonCaisseService) {
        this.bonCaisseService = bonCaisseService;
    }

    public ChantierService getChantierService() {
        return chantierService;
    }

    public void setChantierService(ChantierService chantierService) {
        this.chantierService = chantierService;
    }

    public MensuelService getMensuelService() {
        return mensuelService;
    }

    public void setMensuelService(MensuelService mensuelService) {
        this.mensuelService = mensuelService;
    }
    
    
    @PostConstruct
    public void init() {
        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        loyerService = ctx.getBean(LoyerService.class);
        bonCaisseService = ctx.getBean(BonCaisseService.class);
        chantierService = ctx.getBean(ChantierService.class);
        mensuelService = ctx.getBean(MensuelService.class);
       
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        Map<String, String> requestParameters = externalContext.getRequestParameterMap();
       
         if (requestParameters.containsKey("loyerId")) {
            idRequest = Integer.valueOf(requestParameters.get("loyerId"));
             System.out.println("REQUEST PARAM SENT : " + idRequest);
        } else {
            System.out.println("NO REQUEST ITEM");
        }
         
         loyerSalarieSelectMensuel = loyerService.findOneLSById(idRequest);
         loyerChantierFicheLoyer = loyerService.findOneLCById(idRequest);

       //  System.out.println("FOUND : " + loyerSalarieSelectMensuel.getNomproprietaire());
    
}
    
    
    public void initUpdate(LoyerSalarie ls){
    updateSalarie = true;  
    
    System.out.println("loyersalariemensuel : " + loyerSalarieSelectMensuel.getAdresseproprietaire());
    loyerService.updateLoyerSalarie(loyerSalarieSelectMensuel);
     FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Modification terminée", "Vous avez modifié la fiche loyer"));

    }
    
   public void initUpdateLC(){
    updateSalarie = true;     
    loyerService.updateLoyerChantier(loyerChantierFicheLoyer);
     FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Modification terminée", "Vous avez modifié la fiche loyer"));

    }
    
}
