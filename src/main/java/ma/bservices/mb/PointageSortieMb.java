/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.mb;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import ma.bservices.beans.Chantier;
import ma.bservices.beans.Presence;
import ma.bservices.beans.Salarie;
import ma.bservices.mb.services.Module;
import ma.bservices.services.ChantierService;
import ma.bservices.services.ContratService;
import ma.bservices.services.PresenceService;
import ma.bservices.services.SalarieService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

/**
 *
 * @author Mahdi
 */
@Component
@ManagedBean
@ViewScoped
public class PointageSortieMb implements Serializable {

    @ManagedProperty(value = "#{contratService}")
    private ContratService contratService;
    @ManagedProperty(value = "#{presenceService}")
    private PresenceService presenceService;
    @ManagedProperty(value = "#{salarieService}")
    private SalarieService salarieService;
    private List<Presence> presences, selection;
    private ChantierService chantierService;
    private Integer IdChantier;
    private String p_np, btnPoint, heure, min;
    private String visible;
    private Date datePointage = new Date();
    private Date currentDate;
    private Salarie findSalarie = new Salarie();
    private String fonction;

    public String getFonction() {
        return fonction;
    }

    public void setFonction(String fonction) {
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
        btnPoint = "pointage";
    }

    public void updateHeurMin() {
        System.out.println("update " + IdChantier);
        Chantier c = chantierService.getChantier(IdChantier);
        if (c != null && p_np.equals("np")) {
            heure = c.getHeureSortie().substring(0, 2);
            min = c.getHeureSortie().substring(3, 5);
        }
    }

    public void updateBtn() {
        btnPoint = (p_np.equals("p")) ? "Annuler Pointage" : "Pointage de Sortie";
        visible = p_np.equals("np") ? "block" : "none";
    }

    public void entre() {
        updateBtn();
        updateHeurMin();
        presences = (p_np.equals("np")) ? presenceService.listeSalariesNonPointesSortie(IdChantier, datePointage, "", "", "", "", "", 0,
                Integer.parseInt(presenceService.nombreSalariesNonPointesSortie(IdChantier, datePointage, "", "", "", "", "").toString()))
                : (p_np.equals("p")) ? presenceService.listePointagesSortie(IdChantier, datePointage, "", "", "", "", "", 0,
                                Integer.parseInt(presenceService.nombrePointagesSortie(IdChantier, datePointage, "", "", "", "", "").toString()))
                        : new ArrayList<Presence>();
    }

    public void recherche() {
        presences = (p_np.equals("np")) ? presenceService.listeSalariesNonPointesSortie(IdChantier, datePointage, findSalarie.getMatricule(), findSalarie.getCin(), findSalarie.getCnss(), "", fonction, 0,
                Integer.parseInt(presenceService.nombreSalariesNonPointesSortie(IdChantier, datePointage, findSalarie.getMatricule(), findSalarie.getCin(), findSalarie.getCnss(), "", fonction).toString()))
                : presenceService.listePointagesSortie(IdChantier, datePointage, "", "", "", "", fonction, 0,
                        Integer.parseInt(presenceService.nombrePointagesSortie(IdChantier, datePointage, findSalarie.getMatricule(), findSalarie.getCin(), findSalarie.getCnss(), "", fonction).toString()));
    }

    public void pointage() {
        heure = (heure.length() == 1) ? "0" + heure : heure;
        min = (min.length() == 1) ? "0" + min : min;
        String heuremin = heure + ":" + min;
        if (p_np.equals("np")) {
            for (Presence p : selection) {
                Chantier c = new Chantier();
                c.setId(IdChantier);
                p.setChantier(c);
                p.setDate(datePointage);
                p.setHeureSortie(heuremin);
                //formater la date pour recuperer time de sortie
                SimpleDateFormat d = new SimpleDateFormat("yyyy/MM/dd");
                String date = d.format(datePointage);
                Date testDate = new Date(date + " " + heuremin);
                System.out.println(" date time : " + testDate.getTime());
                long longDateTimeSortie = testDate.getTime();
                p.setLongDateTimeSortie(longDateTimeSortie);

                
                //
                String matriculesAbsences = "";
                String matriculesMultiplePointage = "";
                Presence exist = presenceService.getPresence(IdChantier, datePointage, p.getSalarie().getId(), "PS");
                boolean resultatAbsence = (exist == null) ? presenceService.verifierValiditeDateHeureMinutePointageSalarieAbsence(p.getSalarie().getId(), longDateTimeSortie, null, "PS")
                        : presenceService.verifierValiditeDateHeureMinutePointageSalarieAbsence(p.getSalarie().getId(), longDateTimeSortie, exist.getLongDateTimeSortie(), "PS");
                if (!resultatAbsence) {
                    System.out.println("salarie absent " + p.getSalarie().getMatricule());
                    matriculesAbsences += p.getSalarie().getMatricule() + " ";
                    System.out.println("resultat Absence not " + resultatAbsence);
                } else {
                    boolean resultat = presenceService.verifierValiditeHeureMinutePointageSalarie(p.getSalarie().getId(), datePointage, longDateTimeSortie, "PS");
                    if (!resultat) {
                        matriculesMultiplePointage += p.getSalarie().getMatricule() + " ";
                        System.out.println("resultat not " + resultat);
                    } else {
                        if (exist == null) {
                            //p.setSalarie(findSalarie);
                            System.out.println("pointage not existe");
                            p.setFlag(false);
                            p.setDateCreation(new Date());
                            p.setCreePar(date);
                            presenceService.ajouterPresence(p);
                        } else {
                            System.out.println("pointage existe");
                            exist.setHeureSortie(heuremin);
                            exist.setLongDateTimeSortie(longDateTimeSortie);
                            String nombreHeuresMinutesPresence = presenceService.nombreHeuresMinutesPresence(exist.getHeureEntree(), exist.getHeureSortie());
                            if (!nombreHeuresMinutesPresence.equals("")) {
                                String[] tab = nombreHeuresMinutesPresence.split(":");
                                exist.setNombreHeures(Integer.parseInt(tab[0]));
                                exist.setNombreMinutes(Integer.parseInt(tab[1]));
                            }
                            presenceService.modifierPresence(exist);
                        }
                    }
                }
                //
            }
            Module.message(0, "Pointage de Sortie", "Pointage de Sortie est faite avec succées pour " + selection.size() + " salaries");
            selection = new LinkedList<>();
            entre();
        } else {
            System.out.println("annulation de pointage ");
            for (Presence p : selection) {
                Presence presenceToCancel = presenceService.getPresenceById(p.getId());
                System.out.println("\n\n heure entrer " + presenceToCancel.getHeureEntree() + " \nheure de sortie " + presenceToCancel.getHeureSortie());
                if (presenceToCancel.getHeureSortie() == null) {
                    presenceService.supprimerPresence(presenceToCancel);
                } else {
                    presenceToCancel.setHeureSortie(null);
                    presenceToCancel.setLongDateTimeSortie(null);
                    presenceToCancel.setNombreHeures(null);
                    presenceToCancel.setNombreMinutes(null);
                    presenceService.modifierPresence(presenceToCancel);
                    System.out.println("\n\n heure entrer " + presenceToCancel.getHeureEntree() + " \nheure de sortie " + presenceToCancel.getHeureSortie());
                }
            }
            Module.message(0, "Pointage de Sortie", "Annulation du Pointage de Sortie est faite avec succées pour " + selection.size() + " salaries");
            entre();
            selection = new LinkedList<>();
        }
    }
}
