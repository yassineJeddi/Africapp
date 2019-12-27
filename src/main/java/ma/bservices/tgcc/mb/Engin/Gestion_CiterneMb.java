
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.mb.Engin;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import ma.bservice.tgcc.Constante.Message;
import ma.bservices.beans.Chantier;
import ma.bservices.tgcc.Entity.Citerne;
import ma.bservices.tgcc.mb.services.ChantierMb;
import ma.bservices.tgcc.service.Engin.Bean.CiterneServiceBean;
import ma.bservices.tgcc.service.Engin.ChantierService;
import ma.bservices.tgcc.service.Engin.CiterneService;
import org.primefaces.event.RowEditEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

/**
 *
 * @author a.wattah
 */
@Component
@ManagedBean(name = "Gestion_CiterneMb")
@ViewScoped
public class Gestion_CiterneMb implements Serializable {

    @ManagedProperty(value = "#{citerneService}")
    private CiterneService citerneService;

    @ManagedProperty(value = "#{chantierService}")
    private ChantierService chantierService;

    private List<Citerne> l_citernes;

    private Citerne citerneToAdd;

    private List<Chantier> l_chantier;

    private List<Chantier> l_chantier_Sec;

    private List<Chantier> l_chntiers_secUpdate;

    private String code_chantier_ToAdd;

    private String code_chantier_toUpdate;

    private String[] l_code_chantier_ToAdd;

    private String[] l_code_chantier_ToUpdate;

    private CiterneServiceBean citerneServInter;

    LinkedList<Chantier> l_chantier_sec_afficher;

    private List<Chantier> l_chantiers_sec_to_add = new ArrayList<>();

    private Citerne citerne_chantier_sec;

    private String[] table_chantier_sec_to_add;

    private Integer chantier_id_toUpdate;

    private List<Chantier> l_chantiers_pricipal;

    private Citerne citern_to_update;

    private Double litre_Afficher;

    private Double tons;

    private Double capaciteMetreCube;

    private boolean isCiterneAdded = false;

    private boolean isTypeChanged = false;

    private String type_citerne_afficher;

    private Boolean activerSelectionneChantierSec = false;

    private Integer capacite_toUpdate;

    ELContext elContext = FacesContext.getCurrentInstance().getELContext();

    ChantierMb chantierMbBean = (ChantierMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "chantierServMb");

    /**
     * getters and setters
     *
     * @return
     */
    public String getType_citerne_afficher() {
        return type_citerne_afficher;
    }

    public boolean isIsCiterneAdded() {
        return isCiterneAdded;
    }

    public void setIsCiterneAdded(boolean isCiterneAdded) {
        this.isCiterneAdded = isCiterneAdded;
    }

    public boolean isIsTypeChanged() {
        return isTypeChanged;
    }

    public void setIsTypeChanged(boolean isTypeChanged) {
        this.isTypeChanged = isTypeChanged;
    }

    public void setType_citerne_afficher(String type_citerne_afficher) {
        this.type_citerne_afficher = type_citerne_afficher;
    }

    public Double getLitre_Afficher() {
        return litre_Afficher;
    }

    public void setLitre_Afficher(Double litre_Afficher) {
        this.litre_Afficher = litre_Afficher;
    }

    public List<Chantier> getL_chntiers_secUpdate() {
        return l_chntiers_secUpdate;
    }

    public void setL_chntiers_secUpdate(List<Chantier> l_chntiers_secUpdate) {
        this.l_chntiers_secUpdate = l_chntiers_secUpdate;
    }

    public String[] getL_code_chantier_ToUpdate() {
        return l_code_chantier_ToUpdate;
    }

    public void setL_code_chantier_ToUpdate(String[] l_code_chantier_ToUpdate) {
        this.l_code_chantier_ToUpdate = l_code_chantier_ToUpdate;
    }

    public Citerne getCitern_to_update() {
        return citern_to_update;
    }

    public void setCitern_to_update(Citerne citern_to_update) {
        this.citern_to_update = citern_to_update;
    }

    public List<Chantier> getL_chantiers_pricipal() {
        return l_chantiers_pricipal;
    }

    public void setL_chantiers_pricipal(List<Chantier> l_chantiers_pricipal) {
        this.l_chantiers_pricipal = l_chantiers_pricipal;
    }

    public Integer getChantier_id_toUpdate() {
        return chantier_id_toUpdate;
    }

    public void setChantier_id_toUpdate(Integer chantier_id_toUpdate) {
        this.chantier_id_toUpdate = chantier_id_toUpdate;
    }

    public String[] getTable_chantier_sec_to_add() {
        return table_chantier_sec_to_add;
    }

    public void setTable_chantier_sec_to_add(String[] table_chantier_sec_to_add) {
        this.table_chantier_sec_to_add = table_chantier_sec_to_add;
    }

    public Citerne getCiterne_chantier_sec() {
        return citerne_chantier_sec;
    }

    public void setCiterne_chantier_sec(Citerne citerne_chantier_sec) {
        this.citerne_chantier_sec = citerne_chantier_sec;
    }

    public List<Chantier> getL_chantiers_sec_to_add() {
        return l_chantiers_sec_to_add;
    }

    public void setL_chantiers_sec_to_add(List<Chantier> l_chantiers_sec_to_add) {
        this.l_chantiers_sec_to_add = l_chantiers_sec_to_add;
    }

    public LinkedList<Chantier> getL_chantier_sec_afficher() {
        return l_chantier_sec_afficher;
    }

    public void setL_chantier_sec_afficher(LinkedList<Chantier> l_chantier_sec_afficher) {
        this.l_chantier_sec_afficher = l_chantier_sec_afficher;
    }

    public ChantierService getChantierService() {
        return chantierService;
    }

    public void setChantierService(ChantierService chantierService) {
        this.chantierService = chantierService;
    }

    public String getCode_chantier_ToAdd() {
        return code_chantier_ToAdd;
    }

    public void setCode_chantier_ToAdd(String code_chantier_ToAdd) {
        this.code_chantier_ToAdd = code_chantier_ToAdd;
    }

    public ELContext getElContext() {
        return elContext;
    }

    public void setElContext(ELContext elContext) {
        this.elContext = elContext;
    }

    public ChantierMb getChantierMbBean() {
        return chantierMbBean;
    }

    public void setChantierMbBean(ChantierMb chantierMbBean) {
        this.chantierMbBean = chantierMbBean;
    }

    public CiterneServiceBean getCiterneServInter() {
        return citerneServInter;
    }

    public void setCiterneServInter(CiterneServiceBean citerneServInter) {
        this.citerneServInter = citerneServInter;
    }

    public List<Chantier> getL_chantier_Sec() {
        return l_chantier_Sec;
    }

    public void setL_chantier_Sec(List<Chantier> l_chantier_Sec) {
        this.l_chantier_Sec = l_chantier_Sec;
    }

    public String[] getL_code_chantier_ToAdd() {
        return l_code_chantier_ToAdd;
    }

    public void setL_code_chantier_ToAdd(String[] l_code_chantier_ToAdd) {
        this.l_code_chantier_ToAdd = l_code_chantier_ToAdd;
    }

    public CiterneService getCiterneService() {
        return citerneService;
    }

    public void setCiterneService(CiterneService citerneService) {
        this.citerneService = citerneService;
    }

    public List<Citerne> getL_citernes() {
        return l_citernes;
    }

    public void setL_citernes(List<Citerne> l_citernes) {
        this.l_citernes = l_citernes;
    }

    public Citerne getCiterneToAdd() {
        return citerneToAdd;
    }

    public void setCiterneToAdd(Citerne citerneToAdd) {
        this.citerneToAdd = citerneToAdd;
    }

    public List<Chantier> getL_chantier() {
        return l_chantier;
    }

    public void setL_chantier(List<Chantier> l_chantier) {
        this.l_chantier = l_chantier;
    }

    public String getCode_chantier_toUpdate() {
        return code_chantier_toUpdate;
    }

    public void setCode_chantier_toUpdate(String code_chantier_toUpdate) {
        this.code_chantier_toUpdate = code_chantier_toUpdate;
    }

    public Boolean getActiverSelectionneChantierSec() {
        return activerSelectionneChantierSec;
    }

    public void setActiverSelectionneChantierSec(Boolean activerSelectionneChantierSec) {
        this.activerSelectionneChantierSec = activerSelectionneChantierSec;
    }

    public Integer getCapacite_toUpdate() {
        return capacite_toUpdate;
    }

    public void setCapacite_toUpdate(Integer capacite_toUpdate) {
        this.capacite_toUpdate = capacite_toUpdate;
    }

    public Double getCapaciteMetreCube() {
        return capaciteMetreCube;
    }

    public void setCapaciteMetreCube(Double capaciteMetreCube) {
        this.capaciteMetreCube = capaciteMetreCube;
    }

    /**
     * end getters and setters
     */
    @PostConstruct
    public void init() {
        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        citerneService = ctx.getBean(CiterneService.class);
        chantierService = ctx.getBean(ChantierService.class);

        citerneToAdd = new Citerne();

        citerneServInter = new CiterneServiceBean();

//        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
//
//        ma.bservices.tgcc.mb.services.CiterneMb citerne_bean = (ma.bservices.tgcc.mb.services.CiterneMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "citerne_ServMb");
        l_citernes = citerneService.find_allCiterneNon_archiver();

    }

    /**
     * methode qui permet de ajouter citerne
     */
    public void addCiterne_To_Chantier() {

        ELContext elContext = FacesContext.getCurrentInstance().getELContext();

        ma.bservices.tgcc.mb.services.CiterneMb citerne_bean = (ma.bservices.tgcc.mb.services.CiterneMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "citerne_ServMb");
        citerne_bean.reload();
        List<Citerne> l_cit_s = citerne_bean.getCiternes_nom_Archive();

//        List<Citerne> l_cit_s = this.citerneService.find_allCiterneNon_archiver();
        Boolean existLibelleCiterne = false;

        if (l_cit_s != null) {
            if (!l_cit_s.isEmpty()) {

                for (Citerne c_ : l_cit_s) {
                    if (citerneToAdd != null) {
                        if (citerneToAdd.getLibelle_citerne() != null) {
                            if (citerneToAdd.getLibelle_citerne().toUpperCase().equals(c_.getLibelle_citerne().toUpperCase())) {
                                existLibelleCiterne = true;

                            }
                        }
                    }
                }

            }
        }

        if (citerneToAdd != null) {

            if (existLibelleCiterne == true) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, Message.LIBELLE_CITERNE_EXIST, Message.LIBELLE_CITERNE_EXIST));

            } else if (capaciteMetreCube == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, Message.CITERNE_CONTROL, Message.CITERNE_CONTROL));

            } else if (citerneToAdd.getLibelle_citerne() == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, Message.CITERNE_CONTROL, Message.CITERNE_CONTROL));

            } else if (code_chantier_ToAdd.equals("-1")) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, Message.CITERNE_CONTROL, Message.CITERNE_CONTROL));

            } else {
                Chantier chantier_ = chantierService.findById_String(code_chantier_ToAdd);

                citerneToAdd.setChantier_Principal(chantier_);
                citerneToAdd.setArchiver(Boolean.FALSE);

                citerneToAdd.setVolume_actuel(0d);
                citerneToAdd.setVolume_actuel_(0d);
                citerneToAdd.setCapacite(capaciteMetreCube.intValue() * 1000);
                this.citerneService.save_citerne(citerneToAdd, l_code_chantier_ToAdd);

                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage("" + Message.AOUT_CITERNE, ""));

                citerne_bean.reloadCiterneNonArchiver();

                l_citernes = citerne_bean.getCiternes_nom_Archive();

//            l_citernes = this.citerneService.find_allCiterneNon_archiver();
                citerneToAdd = new Citerne();
                code_chantier_ToAdd = "";
                capaciteMetreCube = null;
                tons = null;
                l_code_chantier_ToAdd = new String[chantierService.findAll().size()];
                l_chantier_sec_afficher = new LinkedList<>();
               // isCiterneAdded = true;
            }
        }

    }

    /**
     * cette pour affiche detail des chantiers
     *
     * @param citerne
     */
    public void redirect_Togle_Affectation(Citerne citerne) {

        this.citerne_chantier_sec = citerne;

        System.out.println("entree");

        l_chantier = new ArrayList<>();

//        l_chantier = citerne_chantier_sec.getL_chantiers();
        l_chantier = this.citerneService.getListeChantierByCiterne(citerne.getId());

        System.out.println("entre chantiers : " + l_chantier.size());

        l_chantiers_sec_to_add = new ArrayList<>();

        l_chantiers_sec_to_add = this.citerneServInter.merge_to_listChantier_display(l_chantier, this.chantierMbBean.getChantiers());

    }

    /**
     * cette methode pour afficher list chantier Non principal
     */
    public void displayChantierPrincipal() {

        System.out.println("entree ");

        this.l_chantier_Sec = this.citerneServInter.get_l_chantiers_sec(this.chantierMbBean.getChantiers(), code_chantier_ToAdd);

        this.activerSelectionneChantierSec = true;

        this.l_chntiers_secUpdate = this.citerneServInter.get_l_chantiers_sec(this.chantierMbBean.getChantiers(), code_chantier_ToAdd);

    }

    /**
     * cette methode qui permet de afficher list chantier seco selectionne
     */
    public void afficher_chantier_selectionne() {

        l_chantier_sec_afficher = new LinkedList<>();

        if (l_code_chantier_ToAdd != null) {

            for (int i = 0; i < l_code_chantier_ToAdd.length; i++) {

                Chantier chantier = chantierService.findById_String(l_code_chantier_ToAdd[i]);

                l_chantier_sec_afficher.add(chantier);

            }

        }

    }

    /**
     * methode qui permet de supprimer citerne
     *
     * @param citerne
     */
    public void supprimer_citerne(Citerne seCiterne) {

        this.citerneService.delete(seCiterne);

        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("" + Message.DELETE_CITERNE, ""));

        ELContext elContext = FacesContext.getCurrentInstance().getELContext();

        ma.bservices.tgcc.mb.services.CiterneMb citerne_bean = (ma.bservices.tgcc.mb.services.CiterneMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "citerne_ServMb");

        citerne_bean.reloadCiterneNonArchiver();

        l_citernes = citerne_bean.getCiternes_nom_Archive();

//        l_citernes = this.citerneService.find_allCiterneNon_archiver();
    }

    /**
     * methode qui permet de supprimer chantier secondaire
     *
     * @param chantier
     */
    public void supprime_chantier_sec(Chantier chantier) {

        this.citerneService.delete_chantierSec_citerne(this.citerne_chantier_sec, chantier);

        l_chantier = this.citerne_chantier_sec.getL_chantiers();
//        l_chantier = this.citerneService.getListeChantierByCiterne(this.citerne_chantier_sec.getId());

        l_chantiers_sec_to_add = new ArrayList<>();

        l_chantiers_sec_to_add = this.citerneServInter.merge_to_listChantier_display(l_chantier, this.chantierMbBean.getChantiers());
    }

    /**
     * methode qui permet de ajouter chantier sec
     */
    public void add_Chantier_Sec() {

        this.citerneService.add_chantierSec_citerne(this.citerne_chantier_sec, table_chantier_sec_to_add);

        l_chantier = this.citerne_chantier_sec.getL_chantiers();
//        l_chantier = this.citerneService.getListeChantierByCiterne(this.citerne_chantier_sec.getId());

        l_chantiers_sec_to_add = new ArrayList<>();

        l_chantiers_sec_to_add = this.citerneServInter.merge_to_listChantier_display(l_chantier, this.chantierMbBean.getChantiers());

    }

    public void initRenderBtn() {
        isCiterneAdded = false;
        tons = null;
        type_citerne_afficher = "none";
    }

    /**
     * methode qui permet de modifier citerne
     *
     * @param event
     */
    public void onRowEdit(RowEditEvent event) {

        Citerne citerne_to_update = (Citerne) event.getObject();

    }

    /**
     * methode de annuler modification
     */
    public void onRowCancel() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Warning " + Message.UPDATE_CITERNE_CANCEL, ""));
    }

    /**
     * modification citerne
     *
     * @param selected
     */
    public void redirect_modification_citerne(Citerne selected) {

        citern_to_update = selected;

        capacite_toUpdate = citern_to_update.getCapacite() / 1000;

        List<Chantier> l_chanti_s = this.citerneService.getListeChantierByCiterne(citern_to_update.getId());

        l_chantiers_pricipal = this.citerneServInter.merge_to_listChantier_display(l_chanti_s, this.chantierMbBean.getChantiers());

    }

    /**
     * methode qui permet de modifie citerne
     */
    public void modifie_citerne() {

        /**
         * test si capacite modifie est inferieur volume actuel dans la citerne
         * ( message d 'erreur )
         */
        if (citern_to_update.getVolume_actuel() != 0) {

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, Message.VOLUMEDEPPASE0_ERREUR, Message.VOLUMEDEPPASE0_ERREUR));

        } else if (capacite_toUpdate < citern_to_update.getVolume_actuel() / 1000) {

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, Message.CAPACITE_INFERIURE_VOLUME_ACTUEL, Message.CAPACITE_INFERIURE_VOLUME_ACTUEL));

        } else {

            Chantier c_ = this.chantierService.findById_String(code_chantier_toUpdate);

            citern_to_update.setChantier_Principal(c_);

            citern_to_update.setCapacite(capacite_toUpdate * 1000);

            this.citerneService.update_citerne(citern_to_update);

//        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
//
//        ma.bservices.tgcc.mb.services.CiterneMb citerne_bean = (ma.bservices.tgcc.mb.services.CiterneMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "citerne_ServMb");
//
//        citerne_bean.reload();
            l_citernes = citerneService.find_allCiterneNon_archiver();

            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("" + Message.UPDATE_CITERNE_SUCCESS, ""));

        }

    }
    
    public void modifie_citerne_() {

        /**
         * test si capacite modifie est inferieur volume actuel dans la citerne
         * ( message d 'erreur )
         */
        if (citern_to_update.getVolume_actuel_() <= 0) {
            System.out.println("=============> 1 ");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, Message.VOLUMEMOIN0_ERREUR, Message.VOLUMEMOIN0_ERREUR));

        } else if (capacite_toUpdate < citern_to_update.getVolume_actuel_() / 1000) {
            System.out.println("=============> 2 ");

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, Message.CAPACITE_INFERIURE_VOLUME_ACTUEL, Message.CAPACITE_INFERIURE_VOLUME_ACTUEL));

        } else {
            System.out.println("=============> 1 ");

            Chantier c_ = this.chantierService.findById_String(code_chantier_toUpdate);

            citern_to_update.setChantier_Principal(c_);

            citern_to_update.setCapacite(capacite_toUpdate * 1000);

            this.citerneService.update_citerne(citern_to_update);

//        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
//
//        ma.bservices.tgcc.mb.services.CiterneMb citerne_bean = (ma.bservices.tgcc.mb.services.CiterneMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "citerne_ServMb");
//
//        citerne_bean.reload();
            l_citernes = citerneService.find_allCiterneNon_archiver();

            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("" + Message.UPDATE_CITERNE_SUCCESS, ""));

        }

    }

    public void afficherType(String selectionne) {
        type_citerne_afficher = selectionne;

        switch (type_citerne_afficher) {
            case "none":
                isTypeChanged = false;
                break;
            default:
                isTypeChanged = true;
                break;
        }

    }

    /**
     * methode converti ton vers Litre
     *
     * @param metreCube
     */
    public void converti(Double metreCube) {
//        litre_Afficher = (metreCube * 1000) / 1.0;

        litre_Afficher = 0.0;
        tons = 0.0;
//        if (type_citerne_afficher != null) {
//            switch (type_citerne_afficher) {
//                case "Diesel":
        // 1 kg = 1litre/0.85 
        // 1t = 1000kg => kg = t/1000

        if (metreCube != null) {
            tons = (metreCube * 0.8);
            System.out.println("METRE CUBE : " + metreCube);
//                    }

//
//                    break;
//
//                case "Essence":
//                litre_Afficher = ton_ * 1000 / 0.79;
//                    if (metreCube != null) {
//
//                        tons = (metreCube * 0.8);
//                    }
//                    break;
        }
//        }
        tons = Math.floor(tons * 100) / 100;
        System.out.println("TONNE : " + tons);
    }

    public void convertiMetreCube(Double metreCube) {
//        litre_Afficher = (metreCube * 1000) / 1.0;
        System.out.println("CONVERSION : " + metreCube);
        litre_Afficher = 0.0;
        capaciteMetreCube = 0.0;
        if (type_citerne_afficher != null) {
            switch (type_citerne_afficher) {
                case "Diesel":
                    // 1 kg = 1litre/0.85 
                    // 1t = 1000kg => kg = t/1000

                    if (metreCube != null) {
                        capaciteMetreCube = (metreCube / 0.8);
                    }

                    break;

                case "Essence":
//                litre_Afficher = ton_ * 1000 / 0.79;

                    if (metreCube != null) {

                        capaciteMetreCube = (metreCube / 0.8);
                    }
                    break;
            }
        }
        capaciteMetreCube = Math.floor(capaciteMetreCube * 100) / 100;
    }

    public Double getTons() {
        return tons;
    }

    public void setTons(Double tons) {
        this.tons = tons;
    }
    //

}
