/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.mb.mensuel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import ma.bservice.tgcc.Constante.Message;
import ma.bservices.tgcc.Entity.Ordinateur;
import ma.bservices.tgcc.service.Mensuel.OrdinateurService;
import org.primefaces.event.RowEditEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

/**
 *
 * @author a.wattah
 */
@Component
@ManagedBean(name = "gestion_ordinateur")
@ViewScoped
public class GestionOrdinateurMb implements Serializable {

    @ManagedProperty(value = "#{ordinateurService}")
    private OrdinateurService ordinateurService;

    private Ordinateur ordinateur;

    private List<Ordinateur> ordinateurs;

    private Ordinateur ordinateurToAdd;

    private List<String> list_ordinateur_distinctMarque;

    /*
     * getters and setters 
     */
    public List<String> getList_ordinateur_distinctMarque() {
        return list_ordinateur_distinctMarque;
    }

    public void setList_ordinateur_distinctMarque(List<String> list_ordinateur_distinctMarque) {
        this.list_ordinateur_distinctMarque = list_ordinateur_distinctMarque;
    }

    public OrdinateurService getOrdinateurService() {
        return ordinateurService;
    }

    public void setOrdinateurService(OrdinateurService ordinateurService) {
        this.ordinateurService = ordinateurService;
    }

    public Ordinateur getOrdinateur() {
        return ordinateur;
    }

    public void setOrdinateur(Ordinateur ordinateur) {
        this.ordinateur = ordinateur;
    }

    public List<Ordinateur> getOrdinateurs() {
        return ordinateurs;
    }

    public void setOrdinateurs(List<Ordinateur> ordinateurs) {
        this.ordinateurs = ordinateurs;
    }

    public Ordinateur getOrdinateurToAdd() {
        return ordinateurToAdd;
    }

    public void setOrdinateurToAdd(Ordinateur ordinateurToAdd) {
        this.ordinateurToAdd = ordinateurToAdd;
    }

    /*
     * end setters and getters 
     */
    @PostConstruct
    public void init() {
        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        ordinateurService = ctx.getBean(OrdinateurService.class);
        ordinateur = new Ordinateur();
        ordinateurToAdd = new Ordinateur();
        ordinateurs = ordinateurService.findAll();

        list_ordinateur_distinctMarque = ordinateurService.getList_ordinateur_distinct();

    }

    public void ajouter_ordinateur() {

        Boolean numSerie_exist = false;

        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        ma.bservices.tgcc.mb.services.OrdinateurMb ordiServMb = (ma.bservices.tgcc.mb.services.OrdinateurMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "ordinateur_ServMb");

        List<Ordinateur> l_ordinateurs_ = ordiServMb.getL_ordinateurs();

        if (l_ordinateurs_ != null) {
            if (!l_ordinateurs_.isEmpty()) {
                for (Ordinateur ord_ : l_ordinateurs_) {
                    if (ord_.getNumeroSerieOrd() != null) {

                        String numSerie_ = ord_.getNumeroSerieOrd().replaceAll(" ", "");

                        if (ordinateurToAdd.getNumeroSerieOrd().equals(numSerie_)) {
                            numSerie_exist = true;
                        }

                    }
                }
            }
        }

        if (numSerie_exist == true) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, Message.NUMS_ORDINATEUR_EXIST, Message.NUMS_ORDINATEUR_EXIST));

        } else {

            Boolean ajouterOrdinateur = ordinateurService.saveOrdinateur(ordinateurToAdd);

            if (ajouterOrdinateur == true) {
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage("" + Message.ADD_ORDINATEUR_SUCCESS, ""));
                // ordiServMb.reloadOrdinateurFidAll();
                // ordiServMb.reload();
                ordinateurs = ordinateurService.findAll();
                //   this.ordinateurs = ordiServMb.getL_ordinateurs();
            }
            ordinateurToAdd = new Ordinateur();
        }
    }

    public void setOrdiList() {
        ordinateurs = ordinateurService.findAll();
    }

    public void delete(Ordinateur ordinateur) {

        if (ordinateur.getMensuel() != null) {

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                    Message.DELETE_ORDINATEUR_FALSE, Message.DELETE_ORDINATEUR_FALSE));

//            FacesContext context = FacesContext.getCurrentInstance();
//            context.addMessage(null, new FacesMessage(Severity.WARNING + Message.DELETE_ORDINATEUR_FALSE, ""));
        } else {

            ordinateurService.delete(ordinateur);

            ELContext elContext = FacesContext.getCurrentInstance().getELContext();
            ma.bservices.tgcc.mb.services.OrdinateurMb ordiServMb = (ma.bservices.tgcc.mb.services.OrdinateurMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "ordinateur_ServMb");
            ordiServMb.reloadOrdinateurFidAll();

            this.ordinateurs = ordiServMb.getL_ordinateurs();

            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("" + Message.DELETE_ORDINATEUR_SUCCESS, ""));
        }
    }

    public void onRowEdit(RowEditEvent event) {

        Boolean existNumSerie = Boolean.FALSE;

        Ordinateur ordinateur_toUpdate = ((Ordinateur) event.getObject());

        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        ma.bservices.tgcc.mb.services.OrdinateurMb ordiServMb = (ma.bservices.tgcc.mb.services.OrdinateurMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "ordinateur_ServMb");

        List<Ordinateur> l_ordinateurs = this.ordinateurService.getListeOrdinateurDifferentId(ordinateur_toUpdate.getId());
//
        if (l_ordinateurs != null) {
            if (!l_ordinateurs.isEmpty()) {
                for (Ordinateur or_ : l_ordinateurs) {
                    if (or_.getNumeroSerieOrd().equals(ordinateur_toUpdate.getNumeroSerieOrd())) {

                        existNumSerie = Boolean.TRUE;
                    }
                }
            }
        }

        if (existNumSerie.equals(Boolean.TRUE)) {

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, Message.NUMS_ORDINATEUR_EXIST, Message.NUMS_ORDINATEUR_EXIST));

        } else {

            this.ordinateurService.update(ordinateur_toUpdate);

            System.out.println("update ");

            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("" + Message.UPDATE_ORDINATEUR_SUCCESS, ""));

//                ordiServMb.reloadOrdinateurFidAll();
//
//                this.ordinateurs = ordiServMb.getL_ordinateurs();
        }
        this.ordinateurs.clear();
        ordiServMb.reloadOrdinateurFidAll();
        this.ordinateurs = ordiServMb.getL_ordinateurs();

    }

    public void onRowCancel() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, Message.UPDATE_ORDINATEUR_CANCEL, ""));
    }

    public List<String> completeText(String query) {
        List<String> results = new ArrayList<String>();
        for (int i = 0; i < list_ordinateur_distinctMarque.size(); i++) {
            if (list_ordinateur_distinctMarque.get(i) != null) {
                results.add(list_ordinateur_distinctMarque.get(i));
            }
        }

        return results;
    }

    /**
     * methode pour affiche tt ordinateurs dans la base
     */
    public void afficherOrdinateur() {

        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        ma.bservices.tgcc.mb.services.OrdinateurMb ordiServMb = (ma.bservices.tgcc.mb.services.OrdinateurMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "ordinateur_ServMb");

        this.ordinateurs = ordiServMb.getL_ordinateurs();
    }

    /**
     * afficher tt ordianteur affecter a un salarie
     */
    public void afficherOrdinateurAffecter() {
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        ma.bservices.tgcc.mb.services.OrdinateurMb ordiServMb = (ma.bservices.tgcc.mb.services.OrdinateurMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "ordinateur_ServMb");

        this.ordinateurs = ordiServMb.getL_ordinateursAffecter();
    }

}
