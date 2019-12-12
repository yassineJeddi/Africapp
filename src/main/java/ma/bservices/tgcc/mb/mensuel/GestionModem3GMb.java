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
import ma.bservices.tgcc.service.Mensuel.Modem3gService;
import org.primefaces.event.RowEditEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

/**
 *
 * @author a.wattah
 */
@Component
@ManagedBean(name = "gestion_modem3G")
@RequestScoped
public class GestionModem3GMb implements Serializable {

    @ManagedProperty(value = "#{modem3gService}")
    private Modem3gService modem3gService;

    private Modem3G modem3g;
    private Modem3G modem3gToAdd;
    private List<Modem3G> modem3gs;

    public Modem3gService getModem3gService() {
        return modem3gService;
    }

    public void setModem3gService(Modem3gService modem3gService) {
        this.modem3gService = modem3gService;
    }

    public Modem3G getModem3g() {
        return modem3g;
    }

    public void setModem3g(Modem3G modem3g) {
        this.modem3g = modem3g;
    }

    public List<Modem3G> getModem3gs() {
        return modem3gs;
    }

    public void setModem3gs(List<Modem3G> modem3gs) {
        this.modem3gs = modem3gs;
    }

    public Modem3G getModem3gToAdd() {
        return modem3gToAdd;
    }

    public void setModem3gToAdd(Modem3G modem3gToAdd) {
        this.modem3gToAdd = modem3gToAdd;
    }

    /**
     * getters and setters
     */
    @PostConstruct
    public void init() {
        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        modem3gService = ctx.getBean(Modem3gService.class);
        modem3g = new Modem3G();
        modem3gToAdd = new Modem3G();
        modem3gs = modem3gService.findAll();

        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        ma.bservices.tgcc.mb.services.Modem3gMb modemServMb = (ma.bservices.tgcc.mb.services.Modem3gMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "modem3g_ServMb");

       // modem3gs = modemServMb.getL_modem3gs();

//        for(Modem3G m : modem3gs ){
//            System.out.println("mensuel : " + m.getMensuel().getId());
//        }
    }

    public void ajouter_3G() {

        Boolean imei_exist = false;

        Boolean num_seri_exist = false;

        Boolean numTel_exist = false;

        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        ma.bservices.tgcc.mb.services.Modem3gMb modemServMb = (ma.bservices.tgcc.mb.services.Modem3gMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "modem3g_ServMb");

        List<Modem3G> l_modem_s = modemServMb.getL_modem3gs();

        if (l_modem_s
                != null) {
            if (!l_modem_s.isEmpty()) {
                for (Modem3G modem_ : l_modem_s) {

                    if (modem_.getImei() != null) {

                        String imei = modem3gToAdd.getImei().replaceAll(" ", "");
                        System.out.println("entre :" + imei);
                        if (modem_.getImei().equals(imei)) {

                            imei_exist = true;

                        }
                    }

                    if (modem_.getSerie_numero() != null) {
                        String numSerie = modem3gToAdd.getSerie_numero().replaceAll(" ", "");

                        if (modem_.getSerie_numero().equals(numSerie)) {
                            num_seri_exist = true;
                        }

                    }

                    if (modem_.getNumero() != null) {

                        if (modem_.getNumero().equals(modem3gToAdd.getNumero())) {
                            numTel_exist = true;
                        }
                    }
                }
            }
        }

        if (imei_exist == true) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, Message.IMEI_3G_EXIST, Message.IMEI_3G_EXIST));

        } else if (num_seri_exist == true) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, Message.NUMS_3G_EXIST, Message.NUMS_3G_EXIST));

        } else if (numTel_exist == true) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, Message.NUMT_3G_EXIST, Message.NUMT_3G_EXIST));

        } else {

            modem3gService.save(modem3gToAdd);
            FacesContext context = FacesContext.getCurrentInstance();

            context.addMessage(null, new FacesMessage("" + Message.ADD_3G_SUCCESS, ""));

            modemServMb.reloadList3g();
            modemServMb.reloadList3gNonAffecter();

            modemServMb.reload();

            this.modem3gs = modemServMb.getL_modem3gs();

            modem3gToAdd = new Modem3G();

        }
    }

    public void delete(Modem3G modem3G) {

        if (modem3G.getMensuel() != null) {

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                    Message.DELETE_3G_FALSE, Message.DELETE_3G_FALSE));

//            FacesContext context = FacesContext.getCurrentInstance();
//            context.addMessage(null, new FacesMessage(Severity.WARNING + Message.DELETE_3G_FALSE, ""));
        } else {
            Boolean delete = modem3gService.delete(modem3G);
            if (delete == true) {

                ELContext elContext = FacesContext.getCurrentInstance().getELContext();
                ma.bservices.tgcc.mb.services.Modem3gMb modemServMb = (ma.bservices.tgcc.mb.services.Modem3gMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "modem3g_ServMb");
                modemServMb.reloadList3g();
                modemServMb.reloadList3gNonAffecter();
                this.modem3gs = modemServMb.getL_modem3gs();

                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage("" + Message.DELETE_3G_SUCCESS, ""));
                modem3gs = modemServMb.getL_modem3gs();
            }
        }
    }

    public void onRowEdit(RowEditEvent event) {

        
        Boolean imei_exist = false;
        Boolean num_seri_exist = false;
        Boolean numTel_exist = false;

        Modem3G m3G_update = ((Modem3G) event.getObject());
        String imei = m3G_update.getImei().replaceAll(" ", "");
        String numSerie = m3G_update.getSerie_numero().replaceAll(" ", "");
//        String num3G = m3G_update.getNumero().replaceAll(" ", "");

        System.out.println("Modem to update : " + m3G_update.getImei());
        System.out.println("Modem to update DTAE BEFORE : " + m3G_update.getDateDebut());
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        ma.bservices.tgcc.mb.services.Modem3gMb modemServMb = (ma.bservices.tgcc.mb.services.Modem3gMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "modem3g_ServMb");

        List<Modem3G> l_modem_s = modem3gService.getListe3gDifferentId(m3G_update.getId());

        System.out.println("entre list 3 g " + l_modem_s.size());

        for (int i = 0; i < l_modem_s.size(); i++) {

            if (l_modem_s.get(i).getImei() != null) {

                System.out.println("entre :" + imei);
                if (l_modem_s.get(i).getImei().equals(imei)) {

                    imei_exist = true;

                    System.out.println(" imei_exist : " + imei_exist);

                }
            }

            if (l_modem_s.get(i).getSerie_numero() != null) {

                if (l_modem_s.get(i).getSerie_numero().equals(numSerie)) {
                    num_seri_exist = true;

                    System.out.println(" num_seri_exist : " + num_seri_exist);

                }

            }

            if (l_modem_s.get(i).getNumero() != null) {

                if (l_modem_s.get(i).getNumero().equals(m3G_update.getNumero())) {
                    numTel_exist = true;

                    System.out.println(" numTel_exist : " + numTel_exist);

                }
            }

        }

        if (imei_exist == true) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, Message.IMEI_3G_EXIST, Message.IMEI_3G_EXIST));

            modemServMb.reloadList3g();

            this.modem3gs = modemServMb.getL_modem3gs();

        } else if (num_seri_exist == true) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, Message.NUMS_3G_EXIST, Message.NUMS_3G_EXIST));

            modemServMb.reloadList3g();

            this.modem3gs = modemServMb.getL_modem3gs();

        } else if (numTel_exist == true) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, Message.NUMT_3G_EXIST, Message.NUMT_3G_EXIST));

            modemServMb.reloadList3g();

            this.modem3gs = modemServMb.getL_modem3gs();

        } else {

            modem3gService.update(m3G_update);
            System.out.println("Modem to update DTAE AFTER : " + m3G_update.getDateDebut());
            System.out.println(" entre update  : ");

            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("" + Message.UPDATE_3G_SUCCESS, ""));

            modemServMb.reloadList3g();
            this.modem3gs = modemServMb.getL_modem3gs();

        }

    }

    public void onRowCancel() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, Message.UPDATE_3G_CANCEL, ""));
    }

    public void afficherModem3g() {

        System.out.println("entre");

        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        ma.bservices.tgcc.mb.services.Modem3gMb modemServMb = (ma.bservices.tgcc.mb.services.Modem3gMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "modem3g_ServMb");
        this.modem3gs = modemServMb.getL_modem3gs();
    }

}
