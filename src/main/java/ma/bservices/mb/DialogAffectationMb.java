/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.mb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import ma.bservice.tgcc.Constante.Constante;
import ma.bservices.beans.AffectationSalarieSupp;
import ma.bservices.beans.Chantier;
import ma.bservices.beans.Salarie;
import ma.bservices.constantes.Constantes;
import ma.bservices.mb.services.Module;
import ma.bservices.services.AffectationSSuppService;
import ma.bservices.services.ChantierService;
import ma.bservices.services.OrganigrameService;
import ma.bservices.services.SalarieService;
import ma.bservices.tgcc.Entity.Organigrame;
import ma.bservices.tgcc.authentification.Authentification;
import org.springframework.stereotype.Component;

/**
 *
 * @author Mahdi
 */
@Component
@ManagedBean
@ViewScoped
public class DialogAffectationMb implements Serializable {

    @ManagedProperty(value = "#{chantieService}")
    private ChantierService chantieService;
    private List<Chantier> chantiers;

    @ManagedProperty(value = "#{salarieService}")
    private SalarieService salarieService;
    @ManagedProperty(value = "#{affectationSSuppService}")
    private AffectationSSuppService affectationSSuppService;

    @ManagedProperty(value = "#{organigrameService}")
    private OrganigrameService organigramService;

    private Salarie salarie = new Salarie();
    private List<Chantier> nonAffectChantier = new ArrayList<>();
    private List<Salarie> chefEquipes;
    private Integer idchantier, idChefEquipe;
    private Boolean hadCE;

    //getter et setter 
    public ChantierService getChantieService() {
        return chantieService;
    }

    public void setChantieService(ChantierService chantieService) {
        this.chantieService = chantieService;
    }

    public SalarieService getSalarieService() {
        return salarieService;
    }

    public void setSalarieService(SalarieService salarieService) {
        this.salarieService = salarieService;
    }

    public List<Chantier> getChantiers() {
        return chantiers;
    }

    public OrganigrameService getOrganigramService() {
        return organigramService;
    }

    public void setOrganigramService(OrganigrameService organigramService) {
        this.organigramService = organigramService;
    }

    public void setChantiers(List<Chantier> chantiers) {
        this.chantiers = chantiers;
    }

    public Salarie getSalarie() {
        return salarie;
    }

    public void setSalarie(Salarie salarie) {
        this.salarie = salarie;
    }

    public List<Chantier> getNonAffectChantier() {
        return nonAffectChantier;
    }

    public void setNonAffectChantier(List<Chantier> nonAffectChantier) {
        this.nonAffectChantier = nonAffectChantier;
    }

    public Integer getIdchantier() {
        return idchantier;
    }

    public void setIdchantier(Integer idchantier) {
        this.idchantier = idchantier;
    }

    public AffectationSSuppService getAffectationSSuppService() {
        return affectationSSuppService;
    }

    public void setAffectationSSuppService(AffectationSSuppService affectationSSuppService) {
        this.affectationSSuppService = affectationSSuppService;
    }

    public Integer getIdChefEquipe() {
        return idChefEquipe;
    }

    public void setIdChefEquipe(Integer idChefEquipe) {
        this.idChefEquipe = idChefEquipe;
    }

    public List<Salarie> getChefEquipes() {
        return chefEquipes;
    }

    public void setChefEquipes(List<Salarie> chefEquipes) {
        this.chefEquipes = chefEquipes;
    }

    public Boolean getHadCE() {
        return hadCE;
    }

    public void setHadCE(Boolean hadCE) {
        this.hadCE = hadCE;
    }

    /**
     * recuperer la liste des chnatier non affecter a un salarié
     *
     * @param idsal identifiant salarié
     */
    public void updateChantierNonAffecter(Integer idsal) {

//        RequestContext context = RequestContext.getCurrentInstance();
//            context.execute("PF('affecter').show()");
        this.salarie = salarieService.getSalarie(idsal);
        System.out.println("In : Salarie = " + idsal);
        if (salarie.getId() != null) {
            System.out.println("In If");
            boolean isAdmin = false;
            if ("admin".equals(Constantes.getRoleAuth()) || "EMAIL_CONTRIBUTORS".equals(Constantes.getRoleAuth())) {
                isAdmin = true;
            }
            ELContext elContext = FacesContext.getCurrentInstance().getELContext();
            Authentification authentification = (Authentification) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "authentification");
            Integer idUser = authentification.get_connected_user().getId();

            nonAffectChantier = chantieService.listeChantiersAffectes(idsal, 0, 0, Integer.parseInt(chantieService.nombreChantiers("", "", Module.dos).toString()), "", "", Module.dos, isAdmin, idUser);

        }
//        context.update(":formAffect:nonAffect");
    }

    /**
     * list des chef d'équipe dans un chantier
     *
     * @param idChantier
     */
    public void chefsBy(Integer idChantier) {

        /**
         * List Chef d'equipe On a passer id de la fonction chef d'equipe '268'
         * et le chantier choisi ont un staut Actif + AP+En cours
         */
//        int j = Integer.parseInt(salarieService.nombreSalaries("", null, Constante.FONCTION_ID_CHEF_EQUIPE, null, "", "", "", "", idChantier.toString(), "") + "");
//        List<Salarie> listActChef = salarieService.listeSalaries(0, j, "", null, Constante.FONCTION_ID_CHEF_EQUIPE, 1, "", "", "", "", idChantier.toString(), "");
//        List<Salarie> listAPChef = salarieService.listeSalaries(0, j, "", null, Constante.FONCTION_ID_CHEF_EQUIPE, 5, "", "", "", "", idChantier.toString(), "");
//        List<Salarie> listEChef = salarieService.listeSalaries(0, j, "", null, Constante.FONCTION_ID_CHEF_EQUIPE, 4, "", "", "", "", idChantier.toString(), "");
//        chefEquipes = new ArrayList<>();
//        chefEquipes.addAll(listActChef);
//        chefEquipes.addAll(listAPChef);
//        chefEquipes.addAll(listEChef);
        Chantier chantierOrg = chantieService.getChantier(idChantier);
        List<Organigrame> orgs = organigramService.findByChantierChef(chantierOrg);
        chefEquipes = new LinkedList<>();
        if (orgs == null) {
            chefEquipes = new LinkedList<>();
        } else {
            for (Organigrame org : orgs) {
                System.out.println("CHANTIER : " + chantierOrg.getCode());
                System.out.println("CHEF : " + org.getSalarie().getNom());
                if (!chefEquipes.contains(org.getSalarie())) {
                    chefEquipes.add(org.getSalarie());
                }
            }
         
        }
        
           this.idchantier = idChantier;
            hadCE = chefEquipes.isEmpty();

    }

    public void filterChantier() {
        if (idchantier != null) {
            List<Chantier> result = new LinkedList<>();
            nonAffectChantier.clear();
            nonAffectChantier.add(chantieService.getChantier(idchantier));
        } else {
            updateChantierNonAffecter(salarie.getId());
        }
    }

    public void affectationCE(Integer idChantier, String action) {
        System.out.println("affecter salarier " + salarie.getMatricule() + " au chantier " + idChantier);
        chantieService.affecterSalarieChantier(salarie, chantieService.getChantier(idChantier));
        boolean isAdmin = false;
        if ("admin".equals(Constantes.getRoleAuth()) || "EMAIL_CONTRIBUTORS".equals(Constantes.getRoleAuth())) {
            isAdmin = true;
        }
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        Authentification authentification = (Authentification) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "authentification");
        Integer idUser = authentification.get_connected_user().getId();
        chantiers = chantieService.listeChantiersAffectes(salarie.getId(), 1, 0, Integer.parseInt(chantieService.nombreChantiers("", "", Module.dos).toString()), "", "", Module.dos, isAdmin, idUser);
        Chantier c = chantieService.getChantier(idChantier);
        Module.message("Salarié affecté ", " au chantier " + c.getCode());
        idchantier = null;
        if (action.equals("detail")) {
            DetailSalarieMb detailMb = (DetailSalarieMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "detailMb");
            detailMb.updateAffectation();
        }
    }

    public void affectation(String action) {
        System.out.println("affecter salarié " + salarie.getMatricule() + " au chantier " + idchantier + " chef d'équipe Id : " + idChefEquipe);
        Chantier c = chantieService.getChantier(this.idchantier);
        
        chantieService.affecterSalarieChantier(salarie, c);
        
        boolean isAdmin = false;
        if ("admin".equals(Constantes.getRoleAuth()) || "EMAIL_CONTRIBUTORS".equals(Constantes.getRoleAuth())) {
            isAdmin = true;
        }
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        Authentification authentification = (Authentification) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "authentification");
        Integer idUser = authentification.get_connected_user().getId();
        chantiers = chantieService.listeChantiersAffectes(salarie.getId(), 1, 0, Integer.parseInt(chantieService.nombreChantiers("", "", Module.dos).toString()), "", "", Module.dos, isAdmin, idUser);
        if (idChefEquipe != null && idChefEquipe != 0) {
            AffectationSalarieSupp affect = new AffectationSalarieSupp();
            affect.setChantier(c);
            affect.setChefEquipe(salarieService.getSalarie(idChefEquipe));
            affect.setSalaries(salarie);
            affect.setDateAffectatio(new Date());
            AffectationSalarieSupp a1 = affectationSSuppService.listSalarieByChantier(idChefEquipe, salarie.getId(), idchantier);
            if (a1 != null) {
                a1.setCurrentSupp(false);
                affectationSSuppService.updateSalarie(a1);
            }
            affect.setCurrentSupp(true);
            affectationSSuppService.affecterSupp(affect);
        }
        Module.message("Salarié affecté ", " au chantier " + c.getCode());
        idchantier = null;
        if (action.equals("detail")) {
            DetailSalarieMb detailMb = (DetailSalarieMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "detailMb");
            detailMb.updateAffectation();
        }
    }

    @PostConstruct
    public void init() {
        affectationSSuppService = Module.ctx.getBean(AffectationSSuppService.class);
        salarieService = Module.ctx.getBean(SalarieService.class);
        chantieService = Module.ctx.getBean(ChantierService.class);
        organigramService = Module.ctx.getBean(OrganigrameService.class);
    }
}
