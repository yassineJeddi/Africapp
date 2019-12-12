/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.mb.Engin;

import com.itextpdf.text.log.SysoLogger;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import ma.bservice.tgcc.Constante.Constante;
import ma.bservice.tgcc.Constante.Message;
import ma.bservices.beans.Chantier;
import ma.bservices.services.MailConfigService;
import ma.bservices.tgcc.Entity.Engin;
import ma.bservices.tgcc.Entity.MailConfigBean;
import ma.bservices.tgcc.Entity.Panne;
import ma.bservices.tgcc.service.Engin.EnginService;
import ma.bservices.tgcc.service.Engin.ChantierService;
import ma.bservices.tgcc.service.SendEmail;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

/**
 *
 * @author zakaria.dem
 */
@Component
@ManagedBean(name = "panneMb")
@ViewScoped
public class PanneMb implements Serializable {

    @ManagedProperty(value = "#{chantierService}")
    private ChantierService chantierService;

    @ManagedProperty(value = "#{mailConfigService}")
    private MailConfigService mailConfigService;

    @ManagedProperty(value = "#{enginService}")
    private EnginService enginService;

    private List<Engin> l_engins;

    private List<Engin> l_engins_in_chantier=new ArrayList<Engin>();

    private List<Engin> l_engins_toDisplay;

    private Chantier chantier_selected_obj;

    private Integer chantierID_selected_panne_declaration_popup;

    private Engin selected_engin_for_panne;

    private Panne panne = new Panne();

    public PanneMb() {
    }

    //getters and setters fin
    @PostConstruct
    public void init() {

        // selected_engin_for_panne = new Engin();
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        ma.bservices.tgcc.mb.services.EnginMb enginMb = (ma.bservices.tgcc.mb.services.EnginMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "enginServMb");

        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        chantierService = ctx.getBean(ChantierService.class);
        enginService = ctx.getBean(EnginService.class);
        mailConfigService = ctx.getBean(MailConfigService.class);
        enginMb.reload();
        l_engins = enginService.findAll();

        l_engins_toDisplay = l_engins;
    }

    /**
     * retourne la liste des engins d'un chantier
     *
     * @param chantier
     * @return
     */
    public List<Engin> get_chantiers_byEngin(Chantier chantier) {
        System.out.println("in the method *******");
        if (chantier != null) {
            System.out.println("chntier selected" + chantier.getCode());

            this.l_engins_in_chantier = new LinkedList<>();

            for (int i = 0; i < l_engins.size(); i++) {

                if (l_engins.get(i).getPrjapId().equals(chantier)) {
                    l_engins_in_chantier.add(l_engins.get(i));
                }

            }

            System.out.println("liste des engins" + l_engins_in_chantier.size());

        }
        return l_engins_in_chantier;
    }

    public void initPanneDialog() {

    }

    /**
     * on chantier selection, update the table of enfin to put "en panne" update
     * the informations on the popup panne
     */
    public void chantierByCode_get() {

        PointageEnginMb pointageEnginMb = null;

        if (this.chantierID_selected_panne_declaration_popup != null) {
            System.out.println("from panne declaration");
            this.chantier_selected_obj = chantierService.findById(this.chantierID_selected_panne_declaration_popup);
        } else {

            System.out.println("from pointage page");
            ELContext elContext = FacesContext.getCurrentInstance().getELContext();
            pointageEnginMb = (PointageEnginMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "pointageEngin");

            if (pointageEnginMb != null && pointageEnginMb.getChantierSelect() > 0) {
                this.chantier_selected_obj = chantierService.findById(pointageEnginMb.getChantierSelect());
            }
        }

        l_engins_toDisplay = new LinkedList<>();

        this.engin_to_display_forPanne();
    }

    /**
     * on select on the row
     */
    public void chantierPanneSelection() {

        this.panne = new Panne();

        if (selected_engin_for_panne != null) {
            panne.setCompteurHoraire(this.selected_engin_for_panne.getComteurHoraire());
            panne.setCompteurKilometrique(this.selected_engin_for_panne.getCompteurKilometrique());
            panne.setEngin(selected_engin_for_panne);

        }

    }

    /**
     * liste des engins pour une panne sur un chantier
     */
    public void engin_to_display_forPanne() {
        l_engins = enginService.findAll();
        l_engins_toDisplay = new LinkedList<>();

        if(this.chantier_selected_obj != null ){
            
        for (int i = 0; i < this.l_engins.size(); i++) {
            if (this.l_engins.get(i).getPrjapId() != null && this.l_engins.get(i).getPrjapId().equals(this.chantier_selected_obj)) {
                if (this.l_engins.get(i).getArchive() == false) {
                    l_engins_toDisplay.add(this.l_engins.get(i));
                }
            }
        }
        }else{
            for (int i = 0; i < this.l_engins.size(); i++) {
            if (this.l_engins.get(i).getPrjapId() != null ) {
                if (this.l_engins.get(i).getArchive() == false) {
                    l_engins_toDisplay.add(this.l_engins.get(i));
                }
            }
            }
        }
        
        
    }
    
    
    public List<String> getRecipientsByModule(String module) {
        List<String> destinataires = new LinkedList<>();
        MailConfigBean listDestinatairesMail = mailConfigService.findByModule(module).get(0);
        String[] email_strings = listDestinatairesMail.getMail_to().split(";");
        destinataires.addAll(Arrays.asList(email_strings));

        return destinataires;
    }

    /**
     * ajouter la panne à l'historque des pannes
     */
    public void addPanne() throws IOException {
        System.out.println("CHANTIER PANNE : " + panne.getEngin().getPrjapId().getCode());
        panne.setChantierPanne(panne.getEngin().getPrjapId().getCode());
    
        Boolean resultPanne = Boolean.FALSE;
         
        try {
           Engin eng = enginService.findOneById(this.selected_engin_for_panne.getIDEngin());
           if(eng!=null){
               // if(  eng.getComteurHoraire() <= this.panne.getCompteurHoraire()
               //     && eng.getCompteurKilometrique()<= this.panne.getCompteurKilometrique()){
                    
               eng.setComteurHoraire(this.panne.getCompteurHoraire());
               eng.setCompteurKilometrique(this.panne.getCompteurKilometrique());
               eng.setDateLastPanne(new Date());
               
               panne.setEngin(eng);
               enginService.addEnginPanne(panne);
               Panne lastPanneEng = enginService.lastPanneByEngin(eng);
              // System.out.println("addPanne============> AP panne eng==>"+eng.getCode()+" K: "+eng.getCompteurKilometrique()+" H: "+eng.getComteurHoraire());
              eng.setHist_panne_id(lastPanneEng);
              enginService.updateEngin(eng);
              // System.out.println("addPanne============> AP eng==>"+eng.getCode()+" K: "+eng.getCompteurKilometrique()+" H: "+eng.getComteurHoraire());
               
                
                resultPanne = Boolean.TRUE;
                
                
              // }
           }
        } catch (Exception e) {
            System.out.println("Erreur de modification d'engin car "+e.getMessage());
        }
                System.out.println("addPanne============> resultPanne  "+resultPanne);
            
        if (resultPanne) {
            
                System.out.println("addPanne============> engin_to_display_forPanne()  ");
            this.engin_to_display_forPanne();

                System.out.println("addPanne============> FacesMessage  ");
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", Message.MISE_EN_PANNE_SUCCESS);
            System.out.println("success" + Message.MISE_EN_PANNE_SUCCESS);
            FacesContext.getCurrentInstance().addMessage(null, message);

                System.out.println("addPanne============> MAIL  ");
            List<String> listDestinatairesMail = getRecipientsByModule("ENGIN_PANNE");
            //mail de notification panne
            ApplicationContext context = new ClassPathXmlApplicationContext("mail.xml");

           // List<String> listDestinatairesMail = getRecipientsByModule("ENGIN_PANNE");
            

            SendEmail sm = (SendEmail) context.getBean("sendEmail");
            if (listDestinatairesMail != null && !listDestinatairesMail.isEmpty()) {
                for (String m : listDestinatairesMail) {
                    sm.sendMail("tgccbtp@gmail.com", m, "ENGIN EN PANNE", "PANNE : L'engin : " + panne.getEngin().getCode() + " " + panne.getEngin().getDesignation() + " est en panne sur le chantier  : " + panne.getChantierPanne());
                }
            }
            
                System.out.println("addPanne============> enginPanneMb_bean.setEngin_panne  ");
            ELContext elContext = FacesContext.getCurrentInstance().getELContext();
            ma.bservices.tgcc.mb.Engin.EnginPanneMb enginPanneMb_bean = (ma.bservices.tgcc.mb.Engin.EnginPanneMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "enginPanne");
            enginPanneMb_bean.setEngin_panne(enginService.search(null, null, null, Constante.CODE_ETAT_ENGIN_PANNE, -1));
            
            
                System.out.println("addPanne============> pointageMb.getPointageEnginByEngin  ");
            ma.bservices.tgcc.mb.Engin.PointageEnginMb pointageMb = (ma.bservices.tgcc.mb.Engin.PointageEnginMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "pointageEngin");
            pointageMb.getPointageEnginByEngin(Boolean.FALSE);

                System.out.println("addPanne============> redirect ");
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());

        } else {

            //message error
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, Message.MISE_EN_PANNE_ERROR, " ");
            FacesContext.getCurrentInstance().addMessage(null, message);

        }
    }
    
    public void prepareDecPanne(){ 
        System.out.println("===================> prepareDecPanne dd :"+(new Date()));
        engin_to_display_forPanne() ;
        System.out.println("===================> l_engins_in_chantier  :"+l_engins_in_chantier.size());
        System.out.println("===================> prepareDecPanne df :"+(new Date()));
        
    }

    /**
     * *****************************************************************************************************************************
     *******************************************************************************************************************************
     *******************************************************************************************************************************
     * GETTERS AND SETTERS
     * *******************************************************************************************************************************
     * *******************************************************************************************************************************
     */
    public List<Engin> getL_engins() {
        return l_engins;
    }

    public void setL_engins(List<Engin> l_engins) {
        this.l_engins = l_engins;
    }

    public List<Engin> getL_engins_in_chantier() {
        return l_engins_in_chantier;
    }

    public void setL_engins_in_chantier(List<Engin> l_engins_in_chantier) {
        this.l_engins_in_chantier = l_engins_in_chantier;
    }

    public List<Engin> getL_engins_toDisplay() {
        return l_engins_toDisplay;
    }

    public void setL_engins_toDisplay(List<Engin> l_engins_toDisplay) {
        this.l_engins_toDisplay = l_engins_toDisplay;
    }

    public Engin getSelected_engin_for_panne() {
        return selected_engin_for_panne;
    }

    public void setSelected_engin_for_panne(Engin selected_engin_for_panne) {
        this.selected_engin_for_panne = selected_engin_for_panne;
    }

    public ChantierService getChantierService() {
        return chantierService;
    }

    public void setChantierService(ChantierService chantierService) {
        this.chantierService = chantierService;
    }

    public Chantier getChantier_selected_obj() {
        return chantier_selected_obj;
    }

    public void setChantier_selected_obj(Chantier chantier_selected_obj) {
        this.chantier_selected_obj = chantier_selected_obj;
    }

    public Panne getPanne() {
        return panne;
    }

    public void setPanne(Panne panne) {
        this.panne = panne;
    }

    public EnginService getEnginService() {
        return enginService;
    }

    public void setEnginService(EnginService enginService) {
        this.enginService = enginService;
    }

    public Integer getChantierID_selected_panne_declaration_popup() {
        return chantierID_selected_panne_declaration_popup;
    }

    public void setChantierID_selected_panne_declaration_popup(Integer chantierID_selected_panne_declaration_popup) {
        this.chantierID_selected_panne_declaration_popup = chantierID_selected_panne_declaration_popup;
    }

    public MailConfigService getMailConfigService() {
        return mailConfigService;
    }

    public void setMailConfigService(MailConfigService mailConfigService) {
        this.mailConfigService = mailConfigService;
    }

}
