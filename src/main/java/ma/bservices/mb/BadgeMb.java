/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package ma.bservices.mb;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import ma.bservices.beans.Chantier;
import ma.bservices.beans.Salarie;
import ma.bservices.mb.services.Evol_ChantierMb;
import ma.bservices.mb.services.Module;
import ma.bservices.services.IAtestationService;
import ma.bservices.services.SalarieService;
import ma.bservices.services.SalarieServiceIn;
import ma.bservices.tgcc.Contrat.Attestations;
import ma.bservices.tgcc.Entity.Attestation;
import ma.bservices.tgcc.service.Engin.ChantierService;
import ma.bservices.tgcc.utilitaire.TgccFileManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

/**
 *
 * @author yassine
 */

@Component
@ManagedBean(name = "badgeMb")
@ViewScoped
public class BadgeMb implements Serializable {
    
    
    @ManagedProperty(value = "#{chantierService}")
    private ChantierService chantierService;
    
    @ManagedProperty(value = "#{salarieServiceIn}")
    private SalarieServiceIn salarieService;
    
    @ManagedProperty(value = "#{salarieService}")
    private SalarieService salarieServicenv;
       
    @ManagedProperty(value = "#{atestationServiceImp}")
    private IAtestationService atestationService;
    
    
    private List<Chantier> chantiers;
    private List<Attestation> attestations = new ArrayList<Attestation>();
    private List<Salarie> salaries= new ArrayList<Salarie>();
    
    
    private Chantier chantier = new Chantier();
    private int idChantier;
    
    public List<Salarie> getSalaries() {
        return salaries;
    }
    
    public void setSalaries(List<Salarie> salaries) {
        this.salaries = salaries;
    }
    
    public List<Attestation> getAttestations() {
        return attestations;
    }
    
    public void setAttestations(List<Attestation> attestations) {
        this.attestations = attestations;
    }
    
    public ChantierService getChantierService() {
        return chantierService;
    }
    
    public void setChantierService(ChantierService chantierService) {
        this.chantierService = chantierService;
    }
    
    public List<Chantier> getChantiers() {
        return chantiers;
    }
    
    public void setChantiers(List<Chantier> chantiers) {
        this.chantiers = chantiers;
    }
    
    public Chantier getChantier() {
        return chantier;
    }
    
    public void setChantier(Chantier chantier) {
        this.chantier = chantier;
    }
    
    public int getIdChantier() {
        return idChantier;
    }
    
    public void setIdChantier(int idChantier) {
        this.idChantier = idChantier;
    }
    
    public SalarieServiceIn getSalarieService() {
        return salarieService;
    }
    
    public void setSalarieService(SalarieServiceIn salarieService) {
        this.salarieService = salarieService;
    }

    public IAtestationService getAtestationService() {
        return atestationService;
    }

    public void setAtestationService(IAtestationService atestationService) {
        this.atestationService = atestationService;
    }

    public SalarieService getSalarieServicenv() {
        return salarieServicenv;
    }

    public void setSalarieServicenv(SalarieService salarieServicenv) {
        this.salarieServicenv = salarieServicenv;
    }
    
    
    
    
    
    @PostConstruct
    public void init() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("________ User Connected ________" + auth.getPrincipal().toString());
        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        chantierService = ctx.getBean(ChantierService.class);
        salarieService = Module.ctx.getBean(SalarieServiceIn.class);
        salarieServicenv = Module.ctx.getBean(SalarieService.class);
        
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        Evol_ChantierMb evol_ChantierMb = (Evol_ChantierMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "evol_chantierMb");
        boolean isAdmin = false;
        boolean isRh = false;
        for (GrantedAuthority grantedAuthority : auth.getAuthorities()) {
            if (grantedAuthority.getAuthority().equals("admin")) { //a verifier ?
                chantiers = evol_ChantierMb.getChantiers();
                isAdmin = true;
                break;
            }
        }
        if (!isAdmin) {
            chantiers = chantierService.searchByUser(auth.getPrincipal().toString());
        }
        
        for (Chantier c : chantiers) {
            c.setIdChantiers(new Integer[chantiers.size()]); // a verifier ?
        }
         attestations = atestationService.allAtestationChantier();
    }
    
    
    public void changeChantier(){
        System.out.println("===============> idChantier:"+idChantier);
    }
    
    public void ajouterAttestatoin(String nom) throws IOException{
        Attestations attestation = new Attestations();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Attestation attestationglobal = new Attestation();
        String cheminAttestation  = TgccFileManager.getCheminAttestation("Attestations");
        //cheminAttestation=cheminAttestation;
        Path folder = Paths.get(cheminAttestation);
        Files.createDirectories(folder);
        
        System.out.println("Génération DBG chantier id  : "+idChantier);
        if(nom != null){
            if("badge".equals(nom)){
                try {
                    salaries = salarieService.listSalarieByChantierId(idChantier);
                    System.out.println("Liste des salarie par chantier MB : "+salaries.size());
                } catch (Exception e) {
                    System.out.println("Erreur   : "+e.getMessage());
                }
                chantier = chantierService.findById(idChantier);
                ArrayList<Salarie> s = new ArrayList<Salarie>();
                for (int i = 0; i < salaries.size(); i++) {
                    s.add(salaries.get(i));
                }
                if(s.size()>0){
                    try {
                        cheminAttestation=attestation.editeBadge(cheminAttestation,s,"Badges_Chantier_"+chantier.getCode().trim().replace(" ", "_")+"_");  
                    } catch (Exception e) {
                        System.out.println("Erreur de generation badge car "+e.getMessage());
                    }
                    attestationglobal.setChemin(cheminAttestation);
                    attestationglobal.setDateCreation(new Date());
                    attestationglobal.setCreePar(auth.getPrincipal().toString());
                    attestationglobal.setTitre("Badges de Chantier "+chantier.getCode().trim());
                    attestationglobal.setChantier(chantier);
                    atestationService.addAtestation(attestationglobal);
                }
                    attestations = atestationService.allAtestationChantier();
                    attestationglobal = new Attestation();
                    cheminAttestation="";
                
            }
        }
    }
}
