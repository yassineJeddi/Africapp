/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.mb;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import ma.bservices.beans.Chantier;
import ma.bservices.beans.Fonction;
import ma.bservices.beans.Presence;
import ma.bservices.beans.Salarie;
import ma.bservices.mb.services.Evol_FonctionMb;
import ma.bservices.mb.services.Module;
import ma.bservices.services.ChantierService;
import ma.bservices.services.ContratService;
import ma.bservices.services.ParametrageService;
import ma.bservices.services.PresenceService;
import ma.bservices.services.SalarieService;
import org.springframework.stereotype.Component;

/**
 *
 * @author Mahdi
 */
@Component
@ManagedBean
@ViewScoped
public class PointageEntreMb implements Serializable {

    @ManagedProperty(value = "#{contratService}")
    private ContratService contratService;
    @ManagedProperty(value = "#{presenceService}")
    private PresenceService presenceService;
    @ManagedProperty(value = "#{salarieService}")
    private SalarieService salarieService;

    @ManagedProperty(value = "#{parametrageService}")
    private ParametrageService parametrageService;

    private List<Presence> presences, selection;
    private ChantierService chantierService;
    private Integer IdChantier;
    private String p_np, btnPoint, heure, min;
    private String visible;
    private Date datePointage = new Date();
    private Date currentDate;
    private Salarie findSalarie = new Salarie();
    private Integer fonction;

    private String idStatut;
    private List<Fonction> fonctions;
    private Evol_FonctionMb fonctionMb;

    public ParametrageService getParametrageService() {
        return parametrageService;
    }

    public void setParametrageService(ParametrageService parametrageService) {
        this.parametrageService = parametrageService;
    }

    public String getIdStatut() {
        return idStatut;
    }

    public void setIdStatut(String idStatut) {
        this.idStatut = idStatut;
    }

    public List<Fonction> getFonctions() {
        return fonctions;
    }

    public void setFonctions(List<Fonction> fonctions) {
        this.fonctions = fonctions;
    }

    public Evol_FonctionMb getFonctionMb() {
        return fonctionMb;
    }

    public void setFonctionMb(Evol_FonctionMb fonctionMb) {
        this.fonctionMb = fonctionMb;
    }

    public Integer getFonction() {
        return fonction;
    }

    public void setFonction(Integer fonction) {
        this.fonction = fonction;
    }

    public Date getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(Date currentDate) {
        this.currentDate = currentDate;
    }

    public Date getDatePointage() {
        return datePointage;
    }

    public void setDatePointage(Date datePointage) {
        this.datePointage = datePointage;
    }

    public ContratService getContratService() {
        return contratService;
    }

    public void setContratService(ContratService contratService) {
        this.contratService = contratService;
    }

    public PresenceService getPresenceService() {
        return presenceService;
    }

    public void setPresenceService(PresenceService presenceService) {
        this.presenceService = presenceService;
    }

    public SalarieService getSalarieService() {
        return salarieService;
    }

    public void setSalarieService(SalarieService salarieService) {
        this.salarieService = salarieService;
    }

    public Integer getIdChantier() {
        return IdChantier;
    }

    public void setIdChantier(Integer IdChantier) {
        this.IdChantier = IdChantier;
    }

    public String getVisible() {
        return visible;
    }

    public void setVisible(String visible) {
        this.visible = visible;
    }

    public String getP_np() {
        return p_np;
    }

    public void setP_np(String p_np) {
        this.p_np = p_np;
    }

    public String getBtnPoint() {
        return btnPoint;
    }

    public void setBtnPoint(String btnPoint) {
        this.btnPoint = btnPoint;
    }

    public String getHeure() {
        return heure;
    }

    public void setHeure(String heure) {
        this.heure = heure;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public ChantierService getChantierService() {
        return chantierService;
    }

    public void setChantierService(ChantierService chantierService) {
        this.chantierService = chantierService;
    }

    public List<Presence> getPresences() {
        return presences;
    }

    public void setPresences(List<Presence> presences) {
        this.presences = presences;
    }

    public Salarie getFindSalarie() {
        return findSalarie;
    }

    public void setFindSalarie(Salarie findSalarie) {
        this.findSalarie = findSalarie;
    }

    public List<Presence> getSelection() {
        return selection;
    }

    public void setSelection(List<Presence> selection) {
        this.selection = selection;
    }

    @PostConstruct
    public void init() {
        salarieService = Module.ctx.getBean(SalarieService.class);
        chantierService = Module.ctx.getBean(ChantierService.class);
        contratService = Module.ctx.getBean(ContratService.class);
        currentDate = datePointage;
        btnPoint = "Pointer";
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        fonctionMb = (Evol_FonctionMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "evol_fonctionMb");
    }

    public void updateHeurMin() {
        System.out.println("update " + IdChantier);
        Chantier c = chantierService.getChantier(IdChantier);
        if (c != null && p_np.equals("np")) {
            heure = c.getHeureEntree().substring(0, 2);
            min = c.getHeureEntree().substring(3, 5);
        }
    }

    public void updateBtn() {
        btnPoint = (p_np.equals("p")) ? "Annuler Pointage" : "Pointage d'entrée";
        visible = p_np.equals("np") ? "block" : "none";
    }

    public void entre() {
        updateBtn();
        updateHeurMin();
        presences = (p_np.equals("np")) ? presenceService.listeSalariesNonPointesEntree(IdChantier, datePointage, "", "", "", "", "", 0,
                Integer.parseInt(presenceService.nombreSalariesNonPointesEntree(IdChantier, datePointage, "", "", "", "", "").toString()))
                : presenceService.listePointagesEntree(IdChantier, datePointage, "", "", "", "", "", 0,
                        Integer.parseInt(presenceService.nombrePointagesEntree(IdChantier, datePointage, "", "", "", "", "").toString()));
        if (presences.isEmpty()) {
//            Module.message(1, "Veuillez vérifier les états des salariés", "");
        }
    }

    public void recherche() {
        presences = (p_np.equals("np")) ? presenceService.listeSalariesNonPointesEntree(IdChantier, datePointage, "", "", "", idStatut, (fonction == null) ? "" : fonction + "", 0,
                Integer.parseInt(presenceService.nombreSalariesNonPointesEntree(IdChantier, datePointage, "", "", "", idStatut, (fonction == null) ? "" : fonction + "").toString()))
                : presenceService.listePointagesEntree(IdChantier, datePointage, "", "", "", idStatut, (fonction == null) ? "" : fonction + "", 0,
                        Integer.parseInt(presenceService.nombrePointagesEntree(IdChantier, datePointage, "", "", "", idStatut, (fonction == null) ? "" : fonction + "").toString()));
    }

    public void pointage() {
        try {
            System.out.println("Pointage Entrée multip");
            String matriculesAbsences = "";
            String matriculesMultiplePointage = "";
            heure = (heure.length() == 1) ? "0" + heure : heure;
            min = (min.length() == 1) ? "0" + min : min;
            String heuremin = heure + ":" + min;
            if (p_np.equals("np")) {
                int cpt = 0;
                for (Presence p : selection) {
                    p.setFlag(false);
                    p.setDate(datePointage);
                    p.setHeureEntree(heuremin);
                    p.setChantier(chantierService.getChantier(IdChantier));
                    SimpleDateFormat d = new SimpleDateFormat("yyyy/MM/dd");
                    String date = d.format(datePointage);
                    Date testDate = new Date(date + " " + heuremin);
                    System.out.println(" date time : " + testDate.getTime());
                    long longDateTimeEntree = testDate.getTime();
                    p.setLongDateTimeEntree(longDateTimeEntree);
                    //
                    Presence exist = presenceService.getPresence(IdChantier, datePointage, p.getSalarie().getId(), "PE");
                    boolean resultatAbsence = (exist == null) ? presenceService.verifierValiditeDateHeureMinutePointageSalarieAbsence(p.getSalarie().getId(), longDateTimeEntree, null, "PE")
                            : presenceService.verifierValiditeDateHeureMinutePointageSalarieAbsence(p.getSalarie().getId(), longDateTimeEntree, exist.getLongDateTimeSortie(), "PE");
                    if (!resultatAbsence) {
                        matriculesAbsences += p.getSalarie().getMatricule() + " ";
                    } else {
                        boolean resultat = presenceService.verifierValiditeHeureMinutePointageSalarie(p.getSalarie().getId(), datePointage, longDateTimeEntree, "PE");
                        if (!resultat) {
                            matriculesMultiplePointage += p.getSalarie().getMatricule() + " ";
                        } else {
                            cpt++;
                            if (exist == null) {
                                p.setDateCreation(new Date());
                                presenceService.ajouterPresence(p);
                            } else {
                                exist.setHeureEntree(heuremin);
                                exist.setLongDateTimeEntree(longDateTimeEntree);
                                String nombreHeuresMinutesPresence = presenceService.nombreHeuresMinutesPresence(exist.getHeureEntree(), exist.getHeureSortie());
                                if (nombreHeuresMinutesPresence.equals("")) {
                                    String[] tab = nombreHeuresMinutesPresence.split(":");
                                    exist.setNombreHeures(Integer.parseInt(tab[0]));
                                    exist.setNombreMinutes(Integer.parseInt(tab[1]));
                                }
                                presenceService.modifierPresence(p);
                            }
                        }
                    }
                }
                if (!"".equals(matriculesAbsences)) {
                    Module.message(1, "Code 1611 : Echec de pointage pour le(s) salarié(s)(" + matriculesAbsences + ") : sont marqués absent ", "");
                }
                if (!"".equals(matriculesMultiplePointage)) {
                    Module.message(1, "Code 1612 : Echec de pointage pour le(s) salarié(s)(" + matriculesMultiplePointage + ") : vérifier l'heure de pointage (Via la liste des présences de chaque salarié en cette date)", "");
                }
                Module.message(0, "Pointage d'entrée", "Pointage d'entrée effectué avec succés pour " + cpt + " salariés");
                entre();
                selection = new LinkedList<>();
            } else {
                for (Presence p : selection) {
//                    if (p.getHeureSortie() == null) {
                        presenceService.supprimerPresence(p);
//                    } else {
//                        p.setHeureEntree(null);
//                        p.setLongDateTimeEntree(null);
//                        p.setNombreHeures(null);
//                        p.setNombreMinutes(null);
//                        presenceService.modifierPresence(p);
//                    }
                }
                Module.message(0, "Annulation du Pointage", " d'entrée pour les " + selection.size() + " salariés");
                entre();
                selection = new LinkedList<>();
            }
        } catch (Exception e) {
            Module.message(3, "Oups", e.getMessage());
        }
    }

    public void fonctionByStatut() {
        if (idStatut != null && !idStatut.equals("")) {
            fonctions = fonctionMb.foncByStatut(idStatut);
        } else {
            fonctions = fonctionMb.getFonctions();
        }
    }
}
