/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.mb.services;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import ma.bservices.tgcc.Entity.Mensuel;
import ma.bservices.tgcc.service.Mensuel.MensuelService;
import ma.bservices.tgcc.service.Mensuel.OrdinateurService;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

/**
 *
 * @author Mahdi
 */
/**
 * class pour eviter conflits le nom mensuel (name : fonctionmb)
 */
@Component("mensuelServMb")
@ManagedBean(name = "mensuelServMb")
@ApplicationScoped
public class MensuelMb implements Serializable {

    @ManagedProperty(value = "#{mensuelService}")
    private MensuelService mensuelService;

    @ManagedProperty(value = "#{ordinateurService}")
    private OrdinateurService ordinateurService;

    private List<String> l_mensuel_nom;
    private List<String> l_mensuel_prenom;
    private List<String> l_mensuel_matricule;
    private List<String> l_mensuel_cin;
    public List<Mensuel> l_mensuels;
    public List<Mensuel> l_mensuels_inActifs;

    private List<String> l_ordianateur_marque;

    //constructor
    public MensuelMb() {
    }

    //constructor fin
    ///getters and setters
    public OrdinateurService getOrdinateurService() {
        return ordinateurService;
    }

    public List<Mensuel> getL_mensuels_inActifs() {
        return l_mensuels_inActifs;
    }

    public void setL_mensuels_inActifs(List<Mensuel> l_mensuels_inActifs) {
        this.l_mensuels_inActifs = l_mensuels_inActifs;
    }

    public void setOrdinateurService(OrdinateurService ordinateurService) {
        this.ordinateurService = ordinateurService;
    }

    public List<String> getL_ordianateur_marque() {
        return l_ordianateur_marque;
    }

    public List<String> getL_mensuel_cin() {
        return l_mensuel_cin;
    }

    public void setL_mensuel_cin(List<String> l_mensuel_cin) {
        this.l_mensuel_cin = l_mensuel_cin;
    }

    public void setL_ordianateur_marque(List<String> l_ordianateur_marque) {
        this.l_ordianateur_marque = l_ordianateur_marque;
    }

    public List<Mensuel> getL_mensuels() {
        return l_mensuels;
    }

    public void setL_mensuels(List<Mensuel> l_mensuels) {
        this.l_mensuels = l_mensuels;
    }

    public MensuelService getMensuelService() {
        return mensuelService;
    }

    public void setMensuelService(MensuelService mensuelService) {
        this.mensuelService = mensuelService;
    }

    public List<String> getL_mensuel_nom() {
        return l_mensuel_nom;
    }

    public void setL_mensuel_nom(List<String> l_mensuel_nom) {
        this.l_mensuel_nom = l_mensuel_nom;
    }

    public List<String> getL_mensuel_prenom() {
        return l_mensuel_prenom;
    }

    public void setL_mensuel_prenom(List<String> l_mensuel_prenom) {
        this.l_mensuel_prenom = l_mensuel_prenom;
    }

    public List<String> getL_mensuel_matricule() {
        return l_mensuel_matricule;
    }

    public void setL_mensuel_matricule(List<String> l_mensuel_matricule) {
        this.l_mensuel_matricule = l_mensuel_matricule;
    }

    //getters and setters fin
    @PostConstruct
    public void init() {

        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        mensuelService = ctx.getBean(MensuelService.class);
        ordinateurService = ctx.getBean(OrdinateurService.class);

        l_mensuel_nom = mensuelService.distinct_mensuel_name();
        l_mensuel_prenom = mensuelService.distinct_mensuel_firstName();
        l_mensuel_matricule = mensuelService.distinct_mensuel_matricule();
        l_mensuel_cin = mensuelService.distinct_mensuel_cin();
        l_mensuels_inActifs = mensuelService.inActiffindAll();
        l_mensuels = mensuelService.findAll();
        l_ordianateur_marque = ordinateurService.getList_ordinateur_distinct();

    }

    //methodes
    /**
     * matricules for autocomplete
     *
     * @param query
     * @return
     */
    public List<String> matricules(String query) {
        List<String> toReturn = new LinkedList<>();

        for (int i = 0; i < this.l_mensuel_matricule.size(); i++) {
            if (l_mensuel_matricule.get(i) != null) {
                if (l_mensuel_matricule.get(i).toLowerCase().startsWith(query.toLowerCase())) {
                    toReturn.add(l_mensuel_matricule.get(i));
                }
            }
        }

        return toReturn;
    }

    /**
     * first name for autocomplete
     *
     * @param query
     * @return
     */
    public List<String> first_name(String query) {
        List<String> toReturn = new LinkedList<>();

        for (int i = 0; i < this.l_mensuel_prenom.size(); i++) {
            if (l_mensuel_prenom.get(i) != null) {
                if (l_mensuel_prenom.get(i).toLowerCase().startsWith(query.toLowerCase())) {
                    toReturn.add(l_mensuel_prenom.get(i));
                }
            }
        }

        return toReturn;
    }

    /**
     * last name for autocomplete
     *
     * @param query
     * @return
     */
    public List<String> last_name(String query) {
        List<String> toReturn = new LinkedList<>();

        for (int i = 0; i < this.l_mensuel_nom.size(); i++) {
            if (l_mensuel_nom.get(i) != null) {
                if (l_mensuel_nom.get(i).toLowerCase().startsWith(query.toLowerCase())) {
                    toReturn.add(l_mensuel_nom.get(i));
                }
            }
        }

        return toReturn;
    }

    /**
     * matricules for autocomplete
     *
     * @param query
     * @return
     */
    public List<String> marques(String query) {
        List<String> toReturn = new LinkedList<>();

        for (int i = 0; i < this.l_ordianateur_marque.size(); i++) {
            if (l_ordianateur_marque.get(i) != null) {
                if (l_ordianateur_marque.get(i).toLowerCase().startsWith(query.toLowerCase())) {
                    toReturn.add(l_ordianateur_marque.get(i));
                }
            }
        }

        return toReturn;
    }

    /**
     * cin for autocomplete
     *
     * @param query
     * @return
     */
    public List<String> cins(String query) {
        List<String> toReturn = new LinkedList<>();

        for (int i = 0; i < this.l_mensuel_cin.size(); i++) {
            if (l_mensuel_cin.get(i) != null) {
                if (l_mensuel_cin.get(i).toLowerCase().startsWith(query.toLowerCase())) {
                    toReturn.add(l_mensuel_cin.get(i));
                }
            }
        }

        return toReturn;
    }

    /**
     * methode reload la liste des mensuels
     */
    public void reload_Mensuel() {

        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        mensuelService = ctx.getBean(MensuelService.class);

        if (l_mensuels != null) {
            l_mensuels.clear();
        }

        l_mensuels = mensuelService.findAll();
        l_mensuels_inActifs = mensuelService.inActiffindAll();
        //  FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, Message.UPLOAD_SUCCESS, Message.MENSUEL_UPLOAD));
    }

}
