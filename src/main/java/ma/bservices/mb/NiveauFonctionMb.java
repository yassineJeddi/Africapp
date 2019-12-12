/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.mb;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import ma.bservices.beans.Fonction;
import ma.bservices.beans.FonctionLocal;
import ma.bservices.beans.NiveauFonction;
import ma.bservices.mb.services.FonctionLocalMb;
import ma.bservices.mb.services.Module;
import ma.bservices.services.FonctionService;
import ma.bservices.services.NiveauFonctionService;
import ma.bservices.tgcc.authentification.Authentification;
import org.primefaces.event.RowEditEvent;
import org.springframework.stereotype.Component;

/**
 *
 * @author Mahdi
 */
@Component
@ManagedBean
@ViewScoped
public class NiveauFonctionMb implements Serializable {

    @ManagedProperty(value = "#{fonctionServiceEvol}")
    private FonctionService fonctionService;
    private List<Fonction> fonctions, fonction_Selected;
    
   

    @ManagedProperty(value = "#{niveauFonctionService}")
    private NiveauFonctionService niveauFonctionService;
    private List<FonctionLocal> fls;
    private Integer idNiveau, idNiveauEdit;

    public Integer getIdNiveau() {
        return idNiveau;
    }

    public Integer getIdNiveauEdit() {
        return idNiveauEdit;
    }

    public void setIdNiveauEdit(Integer idNiveauEdit) {
        this.idNiveauEdit = idNiveauEdit;
    }

    public void setIdNiveau(Integer idNiveau) {
        this.idNiveau = idNiveau;
    }

    public List<FonctionLocal> getFls() {
        return fls;
    }

    public void setFls(List<FonctionLocal> fls) {
        this.fls = fls;
    }

    public List<Fonction> getFonction_Selected() {
        return fonction_Selected;
    }

    public void setFonction_Selected(List<Fonction> fonction_Selected) {
        this.fonction_Selected = fonction_Selected;
    }

    public FonctionService getFonctionService() {
        return fonctionService;
    }

    public void setFonctionService(FonctionService fonctionService) {
        this.fonctionService = fonctionService;
    }

    public NiveauFonctionService getNiveauFonctionService() {
        return niveauFonctionService;
    }

    public void setNiveauFonctionService(NiveauFonctionService niveauFonctionService) {
        this.niveauFonctionService = niveauFonctionService;
    }

    public List<Fonction> getFonctions() {
        return fonctions;
    }

    public void setFonctions(List<Fonction> fonctions) {
        this.fonctions = fonctions;
    }

    @PostConstruct
    public void init() {
        fonctionService = Module.ctx.getBean(FonctionService.class);
        niveauFonctionService = Module.ctx.getBean(NiveauFonctionService.class);
        System.out.println("init ");
        fonctions = fonctionService.nonAffecter();
       // ELContext elContext = FacesContext.getCurrentInstance().getELContext();
      //  FonctionLocalMb fonctionLocalMb = (FonctionLocalMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "fonctionLocalMb");
        fls = fonctionService.loadAll();
    }

    public void affecter() {
        try {
            for (Fonction f : fonction_Selected) {
                NiveauFonction nf = niveauFonctionService.getById(idNiveau);
                FonctionLocal fl = new FonctionLocal();
                fl.setId(f.getId());
                fl.setFonction(f);
                fl.setNiveauFonction(nf);
                fonctionService.ajouterFonction(fl);
            }
            Module.message(0, "Affectation effectuée", "");
            fls = fonctionService.loadAll();
        } catch (Exception ex) {
            Module.message(2, "Oups Niveau Fonction Affecter!", "");
        }
    }

    public void onRowEdit(RowEditEvent event) {
        FonctionLocal f = (FonctionLocal) event.getObject();
        try {
            f.setNiveauFonction(niveauFonctionService.getById(idNiveauEdit));
            fonctionService.modifierFonction(f);
            Module.message(0, "Modification", "effectuée");
        } catch (Exception ex) {
            Module.message(3, "Oups!", "");
        }
    }

    public void onRowCancel(RowEditEvent event) {
        Module.message(1, "Annulation!", "Opération annulée");
    }
}
