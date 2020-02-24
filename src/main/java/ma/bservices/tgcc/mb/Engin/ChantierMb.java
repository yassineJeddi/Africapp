/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.mb.Engin;

import java.io.Serializable;
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
import ma.bservices.beans.ChantierAffinite;
import ma.bservices.mb.services.Evol_ChantierMb;
import ma.bservices.services.ChantierAffiniteService;
import ma.bservices.tgcc.service.Engin.ChantierService;
import ma.bservices.tgcc.service.Engin.ZoneServices;
import org.primefaces.event.RowEditEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

/**
 *
 * @author j.allali
 */
@Component("ChantierMb")
@ManagedBean(name = "chantier")
@ViewScoped
public class ChantierMb implements Serializable {

    @ManagedProperty(value = "#{chantierService}")
    private ChantierService chantierService;

    @ManagedProperty(value = "#{zoneServices}")
    private ZoneServices zoneService;
    
    @ManagedProperty(value = "#{chantierAffiniteService}")
    private ChantierAffiniteService chantierAffiniteService;
    
    
/////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////Variables ///////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////
    private List<Chantier> chantiers;
    private List<Chantier> chantiersSelect2;
    private List<Chantier> chantier;
    private List<Chantier> chantiersDiff;
    private List<Chantier> ateliers;

    private Chantier chantierP = new Chantier();
    private Chantier chantierToEdit = new Chantier();
    private Chantier chantierAdd = new Chantier();
    private Chantier chantierToUse = new Chantier();
    private String chantierAffinit;
    private int sizeCus = 0;
    private int chad;

    private String chantierSearch;
    private Chantier chanAffinite = new Chantier();

    private Chantier chanSelected = new Chantier();
    
    private ChantierAffinite chantierAffinite = new ChantierAffinite();

    
    boolean isAdmin = false;
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
/////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////Geters Seters ///////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////
    public String getChantierAffinit() {
        return chantierAffinit;
    }

    public void setChantierAffinit(String chantierAffinit) {
        this.chantierAffinit = chantierAffinit;
    }

    public Chantier getChanSelected() {
        return chanSelected;
    }

    public void setChanSelected(Chantier chanSelected) {
        this.chanSelected = chanSelected;
    }

    public ChantierAffiniteService getChantierAffiniteService() {
        return chantierAffiniteService;
    }

    public void setChantierAffiniteService(ChantierAffiniteService chantierAffiniteService) {
        this.chantierAffiniteService = chantierAffiniteService;
    }

    public ChantierAffinite getChantierAffinite() {
        return chantierAffinite;
    }

    public void setChantierAffinite(ChantierAffinite chantierAffinite) {
        this.chantierAffinite = chantierAffinite;
    }    

    public Chantier getChanAffinite() {
        return chanAffinite;
    }

    public void setChanAffinite(Chantier chanAffinite) {
        this.chanAffinite = chanAffinite;
    }

    public ChantierService getChantierService() {
        return chantierService;
    }

    public List<Chantier> getChantiers() {
        return chantiers;
    }

    public int getSizeCus() {
        return sizeCus;
    }

    public List<Chantier> getChantiersSelect2() {
        return chantiersSelect2;
    }

    public void setChantiersSelect2(List<Chantier> chantiersSelect2) {
        this.chantiersSelect2 = chantiersSelect2;
    }

    public void setChantierService(ChantierService chantierService) {
        this.chantierService = chantierService;
    }

    public ZoneServices getZoneService() {
        return zoneService;
    }

    public void setZoneService(ZoneServices zoneService) {
        this.zoneService = zoneService;
    }

    public List<Chantier> getChantiersDiff() {
        return chantiersDiff;
    }

    public void setChantiersDiff(List<Chantier> chantiersDiff) {
        this.chantiersDiff = chantiersDiff;
    }

    public int getChad() {
        return chad;
    }

    public void setChad(int chad) {
        this.chad = chad;
    }

    public void setChantiers(List<Chantier> chantiers) {
        this.chantiers = chantiers;
    }

    public void setSizeCus(int sizeCus) {
        this.sizeCus = sizeCus;
    }

    public List<Chantier> getChantier() {
        return chantier;
    }

    public void setChantier(List<Chantier> chantier) {
        this.chantier = chantier;
    }

    public Chantier getChantierP() {
        return chantierP;
    }

    public void setChantierP(Chantier chantierP) {
        this.chantierP = chantierP;
    }

    public Chantier getChantierAdd() {
        return chantierAdd;
    }

    public void setChantierAdd(Chantier chantierAdd) {
        this.chantierAdd = chantierAdd;
    }

    public String getChantierSearch() {
        return chantierSearch;
    }

    public void setChantierSearch(String chantierSearch) {
        this.chantierSearch = chantierSearch;
    }

    public Chantier getChantierToEdit() {
        return chantierToEdit;
    }

    public void setChantierToEdit(Chantier chantierToEdit) {
        this.chantierToEdit = chantierToEdit;
    }

    public Chantier getChantierToUse() {
        return chantierToUse;
    }

    public void setChantierToUse(Chantier chantierToUse) {
        this.chantierToUse = chantierToUse;
    }
    
    public List<Chantier> getAteliers() {
        return ateliers;
    }

    public void setAteliers(List<Chantier> ateliers) {
        this.ateliers = ateliers;
    }
    
    
/////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////Fonctions ///////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////

    @PostConstruct
    public void init() {
        System.out.println("________ User Connected ________" + auth.getPrincipal().toString());
        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        chantierService = ctx.getBean(ChantierService.class);
        zoneService = ctx.getBean(ZoneServices.class);
        chantierAffiniteService = ctx.getBean(ChantierAffiniteService.class);
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        Evol_ChantierMb evol_ChantierMb = (Evol_ChantierMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "evol_chantierMb");
        
        boolean isRh = false;
       
        for (GrantedAuthority grantedAuthority : auth.getAuthorities()) {
            if (grantedAuthority.getAuthority().equals("admin")) { //a verifier ? 
                chantiers = chantierService.findAll();
                ateliers = evol_ChantierMb.getAteliers();
                isAdmin = true;
                break;
            }
        }
        if (!isAdmin) {
            chantiers = chantierService.searchByUser(auth.getPrincipal().toString());
            ateliers = evol_ChantierMb.getAteliers();
        }

        for (Chantier c : chantiers) {
            c.setIdChantiers(new Integer[chantiers.size()]); // a verifier ? 
        }
    }

    /*
     * Ajouter Chantier 
     */
    public void addChantier() {
        FacesContext context = FacesContext.getCurrentInstance();
        Boolean b = chantierService.addChantier(chantierAdd);
        String messageTrue = Message.CHANTIER_ZONE_SUCCESS + chantierAdd.getCode();
        String messagefalse = Message.CHANTIER_ZONE_FALSE + chantierAdd.getCode();
        if (b == true) {
            context.addMessage(null, new FacesMessage("" + messageTrue, ""));
        } else {
            context.addMessage(null, new FacesMessage("" + messagefalse, ""));
        }
        chantiers = chantierService.findAll();
    }

    /*
     * Modifier Chantier 
     */
    public void editChantier() {
        FacesContext context = FacesContext.getCurrentInstance();
        Boolean b = chantierService.updateChantier(chantierToUse);
        String messageTrue = Message.CHANTIER_ZONE_SUCCESS + chantierToUse.getCode();
        String messagefalse = Message.CHANTIER_ZONE_FALSE + chantierToUse.getCode();
        if (b == true) {
            context.addMessage(null, new FacesMessage("" + messageTrue, ""));
        } else {
            context.addMessage(null, new FacesMessage("" + messagefalse, ""));
        }
        chantiers = chantierService.findAll();
    }
    /*
     * Supprimer Chantier 
     */
    public void deleteChantier() {
        FacesContext context = FacesContext.getCurrentInstance();
        Boolean b = chantierService.deleteChantier(chantierToUse);
        if (b == true) {
            context.addMessage(null, new FacesMessage("Chantier Supprimé avec succées", ""));
        } else {
            context.addMessage(null, new FacesMessage("Echec du suppression de Chantier", ""));
        }
        chantiers = chantierService.findAll();
    }

    /*
     * Charger Chantier 
     */
    public void chargerChantier() {
        chantiers = chantierService.findAll();
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Chargement des chantiers est terminé", ""));
    }

    /*
     * modifier chantier
     */
    public void onRowEdit(RowEditEvent event) {
 
        FacesContext context = FacesContext.getCurrentInstance();
        String messageTrue = Message.ONROWEDIT_CHANTIER_SUCCESS + ((Chantier) event.getObject()).getCode();
        String messagefalse = Message.ONROWEDIT_CHANTIER_FALSE + ((Chantier) event.getObject()).getCode();
        
        int heureEntree = Integer.parseInt(((Chantier) event.getObject()).getHeureEntree().substring(0, 2));
        int heureSortie = Integer.parseInt(((Chantier) event.getObject()).getHeureSortie().substring(0, 2)); 
        Integer nbHeures = heureSortie - heureEntree;

        ((Chantier) event.getObject()).setNombreHeures(nbHeures);

        chanSelected = (Chantier) event.getObject();
        
        Boolean b = chantierService.updateChantier((Chantier) event.getObject());
        if (b == true) {
            context.addMessage(null, new FacesMessage("" + messageTrue, ""));
        } else {
            context.addMessage(null, new FacesMessage("" + messagefalse, ""));
        } 

    }

    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage(Message.ONROWCANCEL_CHANTIER_TRUE, ((Chantier) event.getObject()).getCode());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowInit(RowEditEvent event) {
        System.out.println("On Row Init in");
        rempliIdChantier((Chantier) event.getObject());
    }

    /*
     * supprimer Chantier 
     */
    public void deleteZone(Chantier c) {
        FacesContext context = FacesContext.getCurrentInstance();
        Boolean b = this.chantierService.deleteChantier(c);
        if (b == true) {
            context.addMessage(null, new FacesMessage("" + Message.DELETE_CHANTIER_TRUE, ""));
            chantiers.remove(c);
        }
    }

    /**
     * ajouter Affiniter
     */
    public void addAffinite() {
     List<Chantier> listOfAffinites = new LinkedList<>();
        for (Integer i : chanSelected.getIdChantiers()) {
            //System.out.println("CHANTIERS FROM LIST : " + i);
            if (i != null) {
                Chantier c = chantierService.findById(i);
                //System.out.println("CHANTIER AFF : " + c.getCode());
                if (chanSelected.getAffiniteChantier() == null) {
                    chanSelected.setAffiniteChantier(listOfAffinites);
                }
                
               listOfAffinites.add(c);
                
                //c.setAffiniteChantier(chanSelected);
            }
        }
        chanSelected.setAffiniteChantier(listOfAffinites);
        for(Chantier c : chanSelected.getAffiniteChantier()){
            chantierAffinite = new ChantierAffinite();
            chantierAffinite.setChantier(chanSelected);
            chantierAffinite.setChantierAffinite(c);
            chantierAffiniteService.save(chantierAffinite);
        }
        
        
       // chantierService.updateChantier(chanSelected);
    }

    /**
     * remplie la liste des Id des chantier affinite d'un chantier c
     *
     * @param c chantier
     */
    public void rempliIdChantier(Chantier c) {
        if (c.getAffiniteChantier() != null && !c.getAffiniteChantier().isEmpty()) {
            for (Chantier c1 : c.getAffiniteChantier()) {
                c.getIdChantiers()[indexOfChantier(c1)] = c1.getId();
            }
        }
    }

    /**
     * recupere l'index d'un chantier dans la liste des chantiers
     *
     * @param chantier
     * @return la position du chantier dans la liste
     */
    private int indexOfChantier(Chantier chantier) {
        int i = 0;
        for (Chantier c : chantiers) {
            if (c.getId().equals(chantier.getId())) {
                return i;
            }
            i++;
        }
        return -1;
    }
    
    public void peprChantier(Chantier c){
        chantierToEdit=chantierService.findById(c.getId());
        //System.out.println("MB:::::::::::::::> chantierToEdit "+chantierToEdit.toString());
        chantierToEdit.setCode(chantierToEdit.getCode().trim());
    }
    
    public void saveChantier(){ 
        FacesContext context = FacesContext.getCurrentInstance();
        Boolean b = chantierService.updateChantier(chantierToEdit);
       
        if (b.equals(Boolean.TRUE)) {
            context.addMessage(null, new FacesMessage("La modification de chantier a été effectué" , ""));
        }else if (b.equals(Boolean.FALSE)){
            context.addMessage(null, new FacesMessage("Erreur de modification de chantier, merci de réessayer ou contactez votre administrateur" , ""));
        }
         if (!isAdmin) {
            chantiers = chantierService.searchByUser(auth.getPrincipal().toString());
        }else{
            chantiers = chantierService.findAll();
         }
        chantierToEdit=new Chantier();
    }

}
