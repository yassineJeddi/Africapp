/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.mb;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import ma.bservice.tgcc.Constante.Message;
import ma.bservices.mb.services.Module;
import ma.bservices.services.MailConfigService;
import ma.bservices.tgcc.Entity.MailConfigBean;
import ma.bservices.tgcc.utilitaire.EmailValidator;
import org.springframework.stereotype.Component;

/**
 *
 * @author airaamane
 */
@Component
@ManagedBean(name = "syncConfigMb")
@ViewScoped
public class SynchroConfigMb implements Serializable {

    @ManagedProperty(value = "#{mailConfigService}")
    private MailConfigService mailConfigService;

    List<MailConfigBean> emailConfigs = new LinkedList<>();

    String emails, module;

    @PostConstruct
    public void init() {
        System.out.println("INIT");
        mailConfigService = Module.ctx.getBean(MailConfigService.class);
        emailConfigs = mailConfigService.findAll();
        for (MailConfigBean m : emailConfigs) {
            System.out.println("MODULE : " + m.getModule());
        }
    }

    public void initMailsPerModule(String module) {
        System.out.println("MODULE : " + module);
        emailConfigs = mailConfigService.findByModule(module);
        emails = "";
        this.module = module;
        for (MailConfigBean mcb : emailConfigs) {
            emails += mcb.getMail_to() + ";";
        }
    }

    public void validateEmails() {

        String[] email_strings = emails.split(";");
        for (String mail : email_strings) {
            if (!EmailValidator.validate(mail)) {
                System.out.println("ONE EMAIL IS NOT VALID : " + mail);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Veuillez vérifiez ce(s) email(s) :" + mail, ""));
                return;
            }

        }

        MailConfigBean mcb = mailConfigService.findByModule(module).get(0);
        mcb.setMail_to(emails);
        mailConfigService.update(mcb);
         emailConfigs = mailConfigService.findAll();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Vous avez modifier les destinataires avec succès", ""));
    }

    /**
     * _____ GETTERS & SETTERS ______ *
     */
    public MailConfigService getMailConfigService() {
        return mailConfigService;
    }

    public void setMailConfigService(MailConfigService mailConfigService) {
        this.mailConfigService = mailConfigService;
    }

    public List<MailConfigBean> getEmailConfigs() {
        return emailConfigs;
    }

    public void setEmailConfigs(List<MailConfigBean> emailConfigs) {
        this.emailConfigs = emailConfigs;
    }

    public String getEmails() {
        return emails;
    }

    public void setEmails(String emails) {
        this.emails = emails;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

}
