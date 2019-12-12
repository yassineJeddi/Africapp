/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.mb;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import ma.bservices.beans.Absence;
import ma.bservices.beans.Salarie;
import ma.bservices.mb.services.Module;
import ma.bservices.paginate.AbsensePagination;
import ma.bservices.services.AbsenceService;
import ma.bservices.services.ParametrageService;
import ma.bservices.services.SalarieService;
import org.primefaces.event.RowEditEvent;
import org.springframework.stereotype.Component;

/**
 *
 * @author Mahdi
 */
@Component
@ManagedBean(name = "evol_absenceMb")
@ViewScoped
public class Evol_AbsenceMb implements Serializable {

    //Absence
    @ManagedProperty(value = "#{absencesService}")
    private AbsenceService absenceService;
    private SalarieService salarieService;
    private ParametrageService parametrageService;
    private List<Absence> absences;
    private Salarie findSalarie = new Salarie();
    private Absence newAbsence = new Absence();
    private Integer IDtypeAb, minDebut, minFin, heureDebut, heureFin, idtypeAbs;
    private String idSalarie;
    private Integer page;
    private Boolean prev, next, last, first, pageId;
    //nombre de pages après traitement, c'est le nombre de page qu'on affiche dans la vue
    private Integer i;
    private Integer var;
    private DetailSalarieMb detailSalMB;

    public Integer getIdtypeAbs() {
        return idtypeAbs;
    }

    public void setIdtypeAbs(Integer idtypeAbs) {
        this.idtypeAbs = idtypeAbs;
    }

    public String getIdSalarie() {
        return idSalarie;
    }

    public void setIdSalarie(String idSalarie) {
        this.idSalarie = idSalarie;
    }

    public Integer getMinDebut() {
        return minDebut;
    }

    public void setMinDebut(Integer minDebut) {
        this.minDebut = minDebut;
    }

    public Integer getMinFin() {
        return minFin;
    }

    public void setMinFin(Integer minFin) {
        this.minFin = minFin;
    }

    public Integer getHeureDebut() {
        return heureDebut;
    }

    public void setHeureDebut(Integer heureDebut) {
        this.heureDebut = heureDebut;
    }

    public Integer getHeureFin() {
        return heureFin;
    }

    //getter & setter
    public void setHeureFin(Integer heureFin) {
        this.heureFin = heureFin;
    }

    public Absence getNewAbsence() {
        return newAbsence;
    }

    public void setNewAbsence(Absence newAbsence) {
        this.newAbsence = newAbsence;
    }

    public DetailSalarieMb getDetailSalMB() {
        return detailSalMB;
    }

    public void setDetailSalMB(DetailSalarieMb detailSalMB) {
        this.detailSalMB = detailSalMB;
    }

    public AbsenceService getAbsenceService() {
        return absenceService;
    }

    public void setAbsenceService(AbsenceService absenceService) {
        this.absenceService = absenceService;
    }

    public List<Absence> getAbsences() {
        return absences;
    }

    public void setAbsences(List<Absence> absences) {
        this.absences = absences;
    }

    public Salarie getFindSalarie() {
        return findSalarie;
    }

    public void setFindSalarie(Salarie findSalarie) {
        this.findSalarie = findSalarie;
    }

    public Integer getIDtypeAb() {
        return IDtypeAb;
    }

    public void setIDtypeAb(Integer IDtypeAb) {
        this.IDtypeAb = IDtypeAb;
    }

    public SalarieService getSalarieService() {
        return salarieService;
    }

    public void setSalarieService(SalarieService salarieService) {
        this.salarieService = salarieService;
    }

    public ParametrageService getParametrageService() {
        return parametrageService;
    }

    public void setParametrageService(ParametrageService parametrageService) {
        this.parametrageService = parametrageService;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Boolean getPrev() {
        return prev;
    }

    public void setPrev(Boolean prev) {
        this.prev = prev;
    }

    public Boolean getNext() {
        return next;
    }

    public void setNext(Boolean next) {
        this.next = next;
    }

    public Boolean getLast() {
        return last;
    }

    public void setLast(Boolean last) {
        this.last = last;
    }

    public Boolean getFirst() {
        return first;
    }

    public void setFirst(Boolean first) {
        this.first = first;
    }

    public Boolean getPageId() {
        return pageId;
    }

    public void setPageId(Boolean pageId) {
        this.pageId = pageId;
    }

    public Integer getVar() {
        return var;
    }

    public Integer getI() {
        return i;
    }

    public void setI(Integer i) {
        this.i = i;
    }

    public void setVar(Integer var) {
        this.var = var;
    }

    //methodes
    @PostConstruct
    public void init() {
        reinitNewActionObject();
        absenceService = Module.ctx.getBean(AbsenceService.class);
        salarieService = Module.ctx.getBean(SalarieService.class);
        parametrageService = Module.ctx.getBean(ParametrageService.class);
        //   absences = absenceService.listeAbsences(0, 10, "", "", "", null);

        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        detailSalMB = (DetailSalarieMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "detailMb");

        if (detailSalMB != null && detailSalMB.getSalarie() != null) {
            this.findSalarie = detailSalMB.getSalarie();
        }

        page = 1;

        i = absenceService.nombreAbsences(this.findSalarie.getMatricule() == null ? "" : this.findSalarie.getMatricule(), this.findSalarie.getCin() == null ? "" : this.findSalarie.getCin(), this.findSalarie.getCnss() == null ? "" : this.findSalarie.getCnss(), this.IDtypeAb);
        var = (i % 10 == 0) ? i / 10 : i / 10 + 1;
        //if it's the last page on the pagination 
        if (page == var) {
            last = true;
            pageId = true;
            first = true;
            next = true;
            prev = true;
        }

        onFirst();
    }

    public void getBySalarie(Salarie s) {

        this.findSalarie = s;

        page = 1;
        i = absenceService.nombreAbsences(this.findSalarie.getMatricule() == null ? "" : this.findSalarie.getMatricule(), this.findSalarie.getCin() == null ? "" : this.findSalarie.getCin(), this.findSalarie.getCnss() == null ? "" : this.findSalarie.getCnss(), this.IDtypeAb);
        var = (i % 10 == 0) ? i / 10 : i / 10 + 1;
        //if it's the last page on the pagination 
        if (page == var) {
            last = true;
            pageId = true;
            first = true;
            next = true;
            prev = true;
        }
        onFirst();

    }

    /**
     * on reinialise l'objet responsable de la création
     */
    public void reinitNewActionObject() {

        newAbsence = new Absence();
        newAbsence.setDateDebut(new Date());
        newAbsence.setDateFin(new Date());

        heureDebut = 0;
        minDebut = 0;
        heureFin = 0;
        minFin = 0;

        idSalarie = "";
        idtypeAbs = null;
    }

    public void recherche() {
        IDtypeAb = (IDtypeAb != null && IDtypeAb == 0) ? null : IDtypeAb;

        page = 1;

        i = absenceService.nombreAbsences(this.findSalarie.getMatricule() == null ? "" : this.findSalarie.getMatricule(), this.findSalarie.getCin() == null ? "" : this.findSalarie.getCin(), this.findSalarie.getCnss() == null ? "" : this.findSalarie.getCnss(), this.IDtypeAb);

        var = (i % 10 == 0) ? i / 10 : i / 10 + 1;
        //if it's the last page on the pagination 
        if (page == var) {
            last = true;
            pageId = true;
            first = true;
            next = true;
            prev = true;
        }
        onFirst();

//findSalarie = new Salarie();
        //IDtypeAb = null;
    }

    public void ajouterAbsence() {
        if (newAbsence != null) {

            //au cas ou c'est lancé depuis la vue "detail MB" on prend le salarie de detailMB
            if (detailSalMB != null && detailSalMB.getSalarie() != null) {
                this.idSalarie = ""+detailSalMB.getSalarie().getId();
            }

            Salarie salarie = salarieService.getSalarieByMatricule(idSalarie);
            System.out.println("Add It");

            String heureD = (heureDebut < 10) ? "0" + heureDebut : heureDebut + "";
            String minD = (minDebut < 10) ? "0" + minDebut : minDebut + "";
            String heureF = (heureFin < 10) ? "0" + heureFin : heureFin + "";
            String minF = (minFin < 10) ? "0" + minFin : minFin + "";

            newAbsence.setHeureDebut(heureD + ":" + minD);
            newAbsence.setHeureFin(heureF + ":" + minF);
            newAbsence.setTypeAbsence(parametrageService.getTypeAbsence(idtypeAbs));
            newAbsence.setSalarie(salarie);

            absenceService.ajouterAbsence(newAbsence);

            Module.message(0, "Absence Ajoutée", "");

            page = 1;

            i = absenceService.nombreAbsences(this.findSalarie.getMatricule() == null ? "" : this.findSalarie.getMatricule(), this.findSalarie.getCin() == null ? "" : this.findSalarie.getCin(), this.findSalarie.getCnss() == null ? "" : this.findSalarie.getCnss(), this.IDtypeAb);

            var = (i % 10 == 0) ? i / 10 : i / 10 + 1;
            //if it's the last page on the pagination 
            if (page == var) {
                last = true;
                pageId = true;
                first = true;
                next = true;
                prev = true;
            }
            onFirst();
            reinitNewActionObject();
        }
    }

    public void onRowEdit(RowEditEvent event) {

        Absence absence = (Absence) event.getObject();

        String heureD = (absence.getHeuredeb() < 10) ? "0" + absence.getHeuredeb() : absence.getHeuredeb() + "";
        String minD = (absence.getMindeb() < 10) ? "0" + absence.getMindeb() : absence.getMindeb() + "";
        String heureF = (absence.getHeureF() < 10) ? "0" + absence.getHeureF() : absence.getHeureF() + "";
        String minF = (absence.getMinF() < 10) ? "0" + absence.getMinF() : absence.getMinF() + "";

        absence.setHeureDebut(heureD + ":" + minD);
        absence.setHeureFin(heureF + ":" + minF);

        absence.setTypeAbsence(parametrageService.getTypeAbsence(absence.getIdtypeAbs()));

        absenceService.modifierAbsence(absence);

        Module.message(0, "L'absence " + absence.getId() + " est modifiée avec succès", "");
//        absences = absenceService.listeAbsences(0, 10, "", "", "", null);

//        FacesMessage msg = new FacesMessage("Car Edited", ((Car) event.getObject()).getId());
//        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowCancel(RowEditEvent event) {
//        FacesMessage msg = new FacesMessage("Car Edited", ((Car) event.getObject()).getId());
//        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    //le module de la paginaltion
    public void onNext() {
        absences.clear();
        page += 1;
        absences = AbsensePagination.page(page, this.findSalarie.getMatricule(), this.findSalarie.getCin(), this.findSalarie.getCnss(), this.IDtypeAb);

          last = false;
        prev = false;
        next = false;
        first = false;

        if (page.equals(var)) {
            last = true;
            pageId = false;
            first = false;
            next = true;
            prev = false;
        }
    }

    public void onPaginate() {
        absences.clear();
        absences = AbsensePagination.page(page, this.findSalarie.getMatricule(), this.findSalarie.getCin(), this.findSalarie.getCnss(), this.IDtypeAb);

       if (page == var) {
            last = true;
            pageId = true;
            first = false;
            next = true;
            prev = false;
        } else if (page == 1) {
            last = false;
            pageId = false;
            first = true;
            next = false;
            prev = true;
        } else {
            last = false;
            pageId = false;
            first = false;
            next = false;
            prev = false;
        }

    }

    public void onPrevious() {
        absences.clear();
        page -= 1;
        absences = AbsensePagination.page(page, this.findSalarie.getMatricule(), this.findSalarie.getCin(), this.findSalarie.getCnss(), this.IDtypeAb);

       
           if (page == 1) {
            last = false;
            pageId = false;
            first = true;
            next = false;
            prev = true;
        } else {
            last = false;
            pageId = false;
            first = false;
            next = false;
            prev = false;
        }
    }

    public void current() {
        absences.clear();
        absences = AbsensePagination.page(page, this.findSalarie.getMatricule(), this.findSalarie.getCin(), this.findSalarie.getCnss(), this.IDtypeAb);
    }

    public void onFirst() {
        page = 1;
        absences = AbsensePagination.page(page, this.findSalarie.getMatricule(), this.findSalarie.getCin(), this.findSalarie.getCnss(), this.IDtypeAb);
          last = false;
        pageId = false;
        first = true;
        next = false;
        prev = true;
    }

    public void onLast() {
        page = (i % 10 == 0) ? i / 10 : i / 10 + 1;
        absences = AbsensePagination.last(this.findSalarie.getMatricule(), this.findSalarie.getCin(), this.findSalarie.getCnss(), this.IDtypeAb);

         last = true;
        pageId = true;
        first = false;
        next = true;
        prev = false;
    }
}
