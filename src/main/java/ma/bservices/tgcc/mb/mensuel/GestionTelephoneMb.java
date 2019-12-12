/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.mb.mensuel;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.print.attribute.standard.Severity;
import ma.bservice.tgcc.Constante.Message;
import ma.bservices.tgcc.Entity.Modem3G;
import ma.bservices.tgcc.Entity.Telephone;
import ma.bservices.tgcc.service.Mensuel.TelephoneService;
import org.primefaces.event.RowEditEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

/**
 *
 * @author a.wattah
 */
@Component
@ManagedBean(name = "gestion_telephone")
@RequestScoped
public class GestionTelephoneMb implements Serializable {

    @ManagedProperty(value = "#{telephoneService}")
    private TelephoneService telephoneService;

    private Telephone telephone;
    private Telephone telephoneToAdd;

    private List<Telephone> telephones;

    /*
     * getters and setters
     */
    public TelephoneService getTelephoneService() {
        return telephoneService;
    }

    public void setTelephoneService(TelephoneService telephoneService) {
        this.telephoneService = telephoneService;
    }

    public Telephone getTelephone() {
        return telephone;
    }

    public void setTelephone(Telephone telephone) {
        this.telephone = telephone;
    }

    public Telephone getTelephoneToAdd() {
        return telephoneToAdd;
    }

    public void setTelephoneToAdd(Telephone telephoneToAdd) {
        this.telephoneToAdd = telephoneToAdd;
    }

    public List<Telephone> getTelephones() {
        return telephones;
    }

    public void setTelephones(List<Telephone> telephones) {
        this.telephones = telephones;
    }

    /*
     * setters and getters
     */
    @PostConstruct
    public void init() {
        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        telephoneService = ctx.getBean(TelephoneService.class);
        telephone = new Telephone();
        telephoneToAdd = new Telephone();
        telephones = telephoneService.findAll();

    }
    
    public void setlisttel(){
     telephones = telephoneService.findAll();
    }

    public void ajouter_Telephone() {

        Boolean numero_serie_exist = false;
        
        System.out.println("NUM DE LIGNE : " + telephoneToAdd.getNumLigneTel());

        ELContext elContext = FacesContext.getCurrentInstance().getELContext();

        ma.bservices.tgcc.mb.services.TelephoneMb telephone_ServMb = (ma.bservices.tgcc.mb.services.TelephoneMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "telephone_ServMb");

        List<Telephone> l_tel_s = telephone_ServMb.getL_telephonesFindAll();

        if (l_tel_s != null) {
            if (!l_tel_s.isEmpty()) {

                for (Telephone tel_ : l_tel_s) {

                    if (tel_.getNumSerieTel() != null) {

                        String numSerie = tel_.getNumSerieTel().replaceAll(" ", "");

                        if (telephoneToAdd.getNumSerieTel().equals(numSerie)) {
                            numero_serie_exist = true;
                        }
                    }
                }
            }
        }

        if (numero_serie_exist == true) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, Message.NUMS_TELEPHONE_EXIST, Message.NUMS_TELEPHONE_EXIST));

        } else {

            telephoneService.save(telephoneToAdd);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("" + Message.ADD_TELEPHONE_SUCCESS, ""));

            telephone_ServMb.reloadTelephoneFindAll();

            telephone_ServMb.reload();

            this.telephones = telephone_ServMb.getL_telephonesFindAll();
            telephones = telephoneService.findAll();
            telephoneToAdd = new Telephone();
        }

    }

    public void delete(Telephone telephone) {

        if (telephone.getMensuel() != null) {

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                    Message.DELETE_TELEPHONE_FALSE, Message.DELETE_TELEPHONE_FALSE));

//            FacesContext context = FacesContext.getCurrentInstance();
//            context.addMessage(null, new FacesMessage(Severity.WARNING + Message.DELETE_TELEPHONE_FALSE, ""));
        } else {
            Boolean delete = telephoneService.delete(telephone);
            if (delete == true) {
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage("" + Message.DELETE_TELEPHONE_SUCCESS, ""));

                ELContext elContext = FacesContext.getCurrentInstance().getELContext();

                ma.bservices.tgcc.mb.services.TelephoneMb telephone_ServMb = (ma.bservices.tgcc.mb.services.TelephoneMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "telephone_ServMb");
                telephone_ServMb.reloadTelephoneFindAll();
                this.telephones = telephone_ServMb.getL_telephonesFindAll();

            }
        }
    }

    public void onRowEdit(RowEditEvent event) {

        Telephone telephoneToUpdate = ((Telephone) event.getObject());

        Boolean existNumSerie = false;

        ELContext elContext = FacesContext.getCurrentInstance().getELContext();

        ma.bservices.tgcc.mb.services.TelephoneMb telephone_ServMb = (ma.bservices.tgcc.mb.services.TelephoneMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "telephone_ServMb");

        List<Telephone> l_telephones = telephoneService.getListTelephoneDifferentId(telephoneToUpdate.getId());

        if (l_telephones != null) {
            if (!l_telephones.isEmpty()) {
                for (Telephone tele : l_telephones) {
                    if (tele.getNumSerieTel() != null && telephoneToUpdate.getNumSerieTel() != null) {
                        if (tele.getNumSerieTel().equals(telephoneToUpdate.getNumSerieTel())) {
                            existNumSerie = true;
                        }
                    }
                }
            }
        }

        if (existNumSerie == true) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, Message.NUMS_TELEPHONE_EXIST, Message.NUMS_TELEPHONE_EXIST));
            telephones = telephone_ServMb.getL_telephonesFindAll();
        } else {

            Boolean update = telephoneService.update(((Telephone) event.getObject()));

            if (update == true) {
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage("" + Message.UPDATE_TELEPHONE_SUCCESS, ""));

                telephone_ServMb.reload();
                telephone_ServMb.reloadTelephoneFindAll();

                telephones = telephone_ServMb.getL_telephonesFindAll();
            }
        }
    }

    public void onRowCancel() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, Message.UPDATE_TELEPHONE_CANCEL, ""));
    }

}
