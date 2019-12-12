/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.mb;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import ma.bservices.beans.Chantier;
import ma.bservices.beans.ChantierHierarchie;
import ma.bservices.beans.NiveauFonction;
import ma.bservices.beans.Salarie;
import ma.bservices.beans.SalarieChantier;
import ma.bservices.mb.services.Module;
import ma.bservices.services.ChantierService;
import ma.bservices.services.HierarchieService;
import ma.bservices.services.NiveauFonctionService;
import ma.bservices.services.SalarieService;
import org.springframework.stereotype.Component;

/**
 *
 * @author Mahdi
 */
@Component
@ManagedBean(name = "organigrammeMb")
@ViewScoped
public class OrganigrammeMb implements Serializable {

    /**
     * service
     */
    @ManagedProperty("#{hierarchieService}")
    private HierarchieService hierarchieService;
    @ManagedProperty("#{chantierServiceEvol}")
    private ChantierService chantierService;
    @ManagedProperty(value = "#{niveauFonctionService}")
    private NiveauFonctionService niveauFonctionService;
    /**
     * variable
     */
    private Integer idSalarie, idChantier, idNiveau;
    private List<Salarie> salaries;
    private List<Salarie> cadres;
    private List<Salarie> selectedSalaries;
    @ManagedProperty("#{salarieService}")
    private SalarieService salarieService;
    private List<Salarie> superieur;

//getter setter
    public List<Salarie> getSalaries() {
        return salaries;
    }

    public void setSalaries(List<Salarie> salaries) {
        this.salaries = salaries;
    }

    public HierarchieService getHierarchieService() {
        return hierarchieService;
    }

    public void setHierarchieService(HierarchieService hierarchieService) {
        this.hierarchieService = hierarchieService;
    }

    public Integer getIdSalarie() {
        return idSalarie;
    }

    public void setIdSalarie(Integer idSalarie) {
        this.idSalarie = idSalarie;
    }

    public Integer getIdChantier() {
        return idChantier;
    }

    public void setIdChantier(Integer idChantier) {
        this.idChantier = idChantier;
    }

    public SalarieService getSalarieService() {
        return salarieService;
    }

    public void setSalarieService(SalarieService salarieService) {
        this.salarieService = salarieService;
    }

    public List<Salarie> getSuperieur() {
        return superieur;
    }

    public void setSuperieur(List<Salarie> superieur) {
        this.superieur = superieur;
    }

    public ChantierService getChantierService() {
        return chantierService;
    }

    public void setChantierService(ChantierService chantierService) {
        this.chantierService = chantierService;
    }

    public Integer getIdNiveau() {
        return idNiveau;
    }

    public void setIdNiveau(Integer idNiveau) {
        this.idNiveau = idNiveau;
    }

    public List<Salarie> getSelectedSalaries() {
        return selectedSalaries;
    }

    public void setSelectedSalaries(List<Salarie> selectedSalaries) {
        this.selectedSalaries = selectedSalaries;
    }

    public List<Salarie> getCadres() {
        return cadres;
    }

    public void setCadres(List<Salarie> cadres) {
        this.cadres = cadres;
    }

    public NiveauFonctionService getNiveauFonctionService() {
        return niveauFonctionService;
    }

    public void setNiveauFonctionService(NiveauFonctionService niveauFonctionService) {
        this.niveauFonctionService = niveauFonctionService;
    }

    /**
     * methode init
     */
    @PostConstruct
    public void init() {
        System.out.println("init ");
        hierarchieService = Module.ctx.getBean(HierarchieService.class);
    }

    /**
     * chantier
     */
    public void changeChantier() {
        System.out.println("start");
        salaries = hierarchieService.getSalarieCadre(idChantier);
        System.out.println("End");
    }

    public void save(Salarie s, Integer idsup) {
        if (idSalarie != null) {
            try {
                ChantierHierarchie ch = new ChantierHierarchie();
                SalarieChantier sc = new SalarieChantier();
                Salarie sup = new Salarie();
                if (idSalarie != 0) {
                    sup = salarieService.getSalarie(idSalarie);
                    ch.setSuperieur(sup);
                } else {
                    ch.setSuperieur(null);
                }
                sc.setSalarie(s);
                sc.setChantier(chantierService.getChantier(idChantier));
                ch.setId(sc);

                if (hierarchieService.save(ch)) {
                    Module.message(0, "Info", s.getNom() + " est associé au " + sup.getNom());
                } else {
                    Module.message(2, "Echec", s.getNom() + " n'est pas associé au " + sup.getNom());
                }
            } catch (Exception e) {
                System.out.println("_______ Oups erreur " + e.getMessage());
                Module.message(3, "Oups", "Erreur hiérarchie");
            }
        }
    }

    public List<Salarie> loadSuperieur(Integer id) {
        Salarie s = salarieService.getSalarie(id);
        SalarieChantier sc = new SalarieChantier();
        sc.setSalarie(s);
        Chantier c = chantierService.getChantier(idChantier);
        sc.setChantier(c);
        idSalarie = (hierarchieService.getById(sc) != null) ? hierarchieService.getById(sc).getSuperieur().getId() : null;
        superieur = hierarchieService.getSalarieSuperieur(idChantier, s.getFonction().getFonctionLocal().getNiveauFonction());
        return superieur;
    }

    public void salarieByNiveau() {
        if (idNiveau != null) {
            if (idNiveau != 0) {
                NiveauFonction nf = niveauFonctionService.getById(idNiveau);
            }
        }
    }
}
