/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.mb.mensuel;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import ma.bservice.tgcc.Constante.Constante;
import ma.bservice.tgcc.Constante.Message;
import ma.bservices.beans.Chantier;
import ma.bservices.beans.Salarie;
import ma.bservices.services.SalarieService;
import ma.bservices.tgcc.Entity.Affectation;
import ma.bservices.tgcc.Entity.Mensuel;
import ma.bservices.tgcc.Entity.SousAffectation;
import ma.bservices.tgcc.service.Engin.ChantierService;
import ma.bservices.tgcc.service.Mensuel.AffectationService;
import ma.bservices.tgcc.service.Mensuel.bean.AffectationServiceIntermediaire;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

/**
 *
 * @author zakaria.dem
 */
@Component
@ManagedBean(name = "affectationMB")
@ViewScoped
public class AffectationMB implements Serializable {

    @ManagedProperty(value = "#{chantierService}")
    private ChantierService chantierService;
     

    @ManagedProperty(value = "#{chantierServiceEvol}")
    private ma.bservices.services.ChantierService chantierServ;

    @ManagedProperty(value = "#{affectationService}")
    private AffectationService affectationService;

    @ManagedProperty(value = "#{salarieService}")
    private SalarieService salarieService;

    private List<SousAffectation> l_sousAffec_to_display; //list of chantier to display if mensuel have already been affected to list of chantier 
    private List<Chantier> l_chantier;
    private AffectationServiceIntermediaire affecServInter;
    private Integer selected_chantier_id;
    private int pourcentage_affectation;
    private Date creation_date;
    private Date today_date;
    private Mensuel active_mensuel;

    public ChantierService getChantierService() {
        return chantierService;
    }

    public void setChantierService(ChantierService chantierService) {
        this.chantierService = chantierService;
    }

    public AffectationService getAffectationService() {
        return affectationService;
    }

    public void setAffectationService(AffectationService affectationService) {
        this.affectationService = affectationService;
    }

    public int getPourcentage_affectation() {

        if (this.getAffecServInter() != null) {
            this.pourcentage_affectation = this.affecServInter.calcul_proposal_affectation();
        }
        return this.pourcentage_affectation;
    }

    public void setPourcentage_affectation(int pourcentage_affectation) {
        this.pourcentage_affectation = pourcentage_affectation;
    }

    public List<SousAffectation> getL_sousAffec_to_display() {
        return l_sousAffec_to_display;
    }

    public Integer getSelected_chantier_id() {
        return selected_chantier_id;
    }

    public void setSelected_chantier_id(Integer selected_chantier_id) {
        this.selected_chantier_id = selected_chantier_id;
    }

    public void setL_sousAffec_to_display(List<SousAffectation> l_sousAffec_to_display) {
        this.l_sousAffec_to_display = l_sousAffec_to_display;
    }

    public AffectationServiceIntermediaire getAffecServInter() {
        return affecServInter;
    }

    public void setAffecServInter(AffectationServiceIntermediaire affecServInter) {
        this.affecServInter = affecServInter;
    }

    public List<Chantier> getL_chantier() {
        return l_chantier;
    }

    public void setL_chantier(List<Chantier> l_chantier) {
        this.l_chantier = l_chantier;
    }

    public Date getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(Date creation_date) {
        this.creation_date = creation_date;
    }

    public Mensuel getActive_mensuel() {
        return active_mensuel;
    }

    public void setActive_mensuel(Mensuel active_mensuel) {
        this.active_mensuel = active_mensuel;
    }

    public Date getToday_date() {
        return today_date;
    }

    public void setToday_date(Date today_date) {
        this.today_date = today_date;
    }

    public SalarieService getSalarieService() {
        return salarieService;
    }

    public void setSalarieService(SalarieService salarieService) {
        this.salarieService = salarieService;
    }

    public ma.bservices.services.ChantierService getChantierServ() {
        return chantierServ;
    }

    public void setChantierServ(ma.bservices.services.ChantierService chantierServ) {
        this.chantierServ = chantierServ;
    }

    /**
     * Creates a new instance of AffectationMB
     */
    public AffectationMB() {

    }

    @PostConstruct
    public void init() {

        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        chantierService = ctx.getBean(ChantierService.class);
        chantierServ = ctx.getBean(ma.bservices.services.ChantierService.class);
        affectationService = ctx.getBean(AffectationService.class);
        salarieService = ctx.getBean(SalarieService.class);
        this.creation_date = new Date();
        this.today_date = new Date();

    }

    /**
     * show popup of affectations
     *
     * @param _mensuel
     * @param affec
     * @throws Exception
     */
    public void show_popup_affectation_forMensuel(Mensuel _mensuel, Affectation affec) throws Exception {
        this.active_mensuel = _mensuel;

        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        ma.bservices.tgcc.mb.services.ChantierMb chantierMb_bean = (ma.bservices.tgcc.mb.services.ChantierMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "chantierServMb");

        List<Chantier> l_chantiers = chantierMb_bean.getChantiers();

        this.affecServInter = affectationService.get_affections(_mensuel, l_chantiers, affec);

        if (affec instanceof Affectation) {
            this.creation_date = affec.getDateeffect();
        }
    }

    /**
     * delete a sub affectation from chantier
     *
     * @throws Exception
     */
    public void addChantier_toAffectations() throws Exception {

        if (this.selected_chantier_id == null) {
            throw new Exception("you must select a chantier object !!");
        } else {

            System.out.println("Chantier to add selected");
            Chantier chantier_selected = chantierService.findById(this.selected_chantier_id);

            if (chantier_selected instanceof Chantier) {

                System.out.println("Chantier to add selected found");

                if (this.active_mensuel.getTypeAffectation() == Boolean.FALSE && this.affecServInter.getL_sous_affectation().size() == 0) {
                    this.pourcentage_affectation = 100;

                    System.out.println("percent affectation 100");
                }

                if (this.active_mensuel.getTypeAffectation() == Boolean.FALSE && this.affecServInter.getL_sous_affectation().size() > 0) {
                    this.pourcentage_affectation = 0;

                    System.out.println("percent affectation 0");
                }

                System.out.println("affectation pourcent" + this.pourcentage_affectation);
                this.affecServInter.add_sousAffectation(chantier_selected, this.pourcentage_affectation);

                System.out.println("Sub affectation added");
               
            }

        }
    }

    public void deleteAffectation_fromTheList(SousAffectation _sAff_toDelete) {
        if (_sAff_toDelete instanceof SousAffectation) {
            this.affecServInter.delete_sousAffectation(_sAff_toDelete);
        }
    }

    /**
     * ajouter ou modifier une affectation
     *
     * @param update si on ajoute une affectation ou on la modifie
     */
    public void validate(Boolean update) {
        
        Salarie s = this.active_mensuel;

        FacesContext context = FacesContext.getCurrentInstance();
        if (update.equals(Boolean.FALSE)) {
            if (this.affecServInter.getMinDateForAffectaion() != null) {
                if ((!creation_date.after(this.affecServInter.getMinDateForAffectaion()))) {
                    context.addMessage(null, new FacesMessage("" + "Impossible d' affecter ce mensuel pour ce jour", ""));
                    return;
                }
            }
            if (!creation_date.before(today_date)) {
                context.addMessage(null, new FacesMessage("" + "Impossible d' affecter ce mensuel pour ce jour", ""));
                return;
            }
        }
        List<SousAffectation> l_to_add = this.affecServInter.validate();
        if (l_to_add != null) {
            /*
             * pour la modification on doit envoyer l'affectaion à modifier en paramètre.
             * Pour l'ajout, c'est pas la peine.
             */
            this.affectationService.add_affectation_withSubAffectation(l_to_add, active_mensuel, this.creation_date, (update.equals(Boolean.TRUE) ? this.affecServInter.getLast_affectation() : null));
            int tab[] = Constante.Type_Fonction_Pointage_Upsit;
            for (int i = 0; i < tab.length; i++) {
                if (active_mensuel.getFonction().getTypeFonction() == tab[i]) {
                    chantierServ.desaffecterSalarieTousChantiers(active_mensuel.getId());
                     try { 
                        System.out.println(" AffectationMB ::::> affecter salarie au chantier  ");
                        chantierService.deleteAffectSalarieToutChatierFinance(s);
                    } catch (Exception e) {
                        System.out.println(" AffectationMB ::::> Erreur d'affecter salarie au chantier car "+e.getMessage());
                    }
                    for (SousAffectation sa : l_to_add) {
                        chantierServ.affecterSalarieChantier(active_mensuel, sa.getChantier());
                        try {
                                chantierService.affectSalarieChatierFinance(s,sa.getChantier());
                        } catch (Exception e) {
                            System.out.println(" AffectationMB ::::> Erreur d'affecter salarie au chantier car "+e.getMessage());
                        }
                    }
                }
            }
            ELContext elContext = FacesContext.getCurrentInstance().getELContext();
            ma.bservices.tgcc.mb.mensuel.DocumentMb documentMb = (ma.bservices.tgcc.mb.mensuel.DocumentMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "documentMb");

            //    documentMb.getAffectation_byMensuel();
        }

        context.addMessage(null, new FacesMessage("Succes" + Message.AFFECT_FINAN, ""));

    }

    /**
     * supprimer une affectation ou plutôt l'archiver
     *
     * @param _affec
     */
    public void deleteAffectation(Affectation _affec) {
        List<Affectation> _affecList = new LinkedList<>();
        _affecList.add(_affec);
        this.affectationService.deleteAffectation(_affecList);

        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        ma.bservices.tgcc.mb.mensuel.DocumentMb documentMb = (ma.bservices.tgcc.mb.mensuel.DocumentMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "documentMb");

        documentMb.getAffectation_byMensuel();
    }

    public void checkdocs(Mensuel mensuel) {

    }

}
