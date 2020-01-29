/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.mb;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import ma.bservices.beans.Chantier;
import ma.bservices.beans.EtatHeuresSupplementaires;
import ma.bservices.beans.Presence;
import ma.bservices.beans.Salarie;
import ma.bservices.mb.services.Module;
import ma.bservices.services.ChantierService;
import ma.bservices.services.ContratService;
import ma.bservices.services.HeuresSupplementairesService;
import ma.bservices.services.PresenceService;
import ma.bservices.services.SalarieService;
import ma.bservices.tgcc.Entity.Mensuel;
import ma.bservices.tgcc.Entity.SousAffectation;
import ma.bservices.tgcc.authentification.Authentification;
import ma.bservices.tgcc.service.Mensuel.AffectationService;
import org.springframework.stereotype.Component;

/**
 *
 * @author Mahdi
 */
@Component
@ManagedBean
@ViewScoped
public class PointageSalarieMb implements Serializable {

    @ManagedProperty(value = "#{contratService}")
    private ContratService contratService;
    @ManagedProperty(value = "#{presenceService}")
    private PresenceService presenceService;
    @ManagedProperty(value = "#{salarieService}")
    private SalarieService salarieService;
    //@ManagedProperty(value = "#{administrationService}")
    //private AdministrationService administrationService;
    @ManagedProperty(value = "#{heuresSupplementairesService}")
    private HeuresSupplementairesService heuresSupplementairesService;

    @ManagedProperty(value = "#{affectationService}")
    private AffectationService affectationService;

    private ChantierService chantierService;
    private Salarie findSalarie = new Salarie();
    private Integer idChantier;
    private String e_S, btnPoint, heure, min;
    private Date datePointage = new Date();
    private boolean tach;
    private Date currentDate;

    public void clear() {
        findSalarie = new Salarie();

    }

    public Date getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(Date currentDate) {
        this.currentDate = currentDate;
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

    public HeuresSupplementairesService getHeuresSupplementairesService() {
        return heuresSupplementairesService;
    }

    public void setHeuresSupplementairesService(HeuresSupplementairesService heuresSupplementairesService) {
        this.heuresSupplementairesService = heuresSupplementairesService;
    }

    public boolean isTach() {
        return tach;
    }

    //getter Setter
    public void setTach(boolean tach) {
        this.tach = tach;
    }

    public SalarieService getSalarieService() {
        return salarieService;
    }

    public void setSalarieService(SalarieService salarieService) {
        this.salarieService = salarieService;
    }

    public Integer getIdChantier() {
        return idChantier;
    }

    public void setIdChantier(Integer IdChantier) {
        this.idChantier = IdChantier;
    }

    public String getE_S() {
        return e_S;
    }

    public AffectationService getAffectationService() {
        return affectationService;
    }

    public void setAffectationService(AffectationService affectationService) {
        this.affectationService = affectationService;
    }

    public void setE_S(String E_S) {
        this.e_S = E_S;
    }

    public Date getDatePointage() {
        return datePointage;
    }

    public void setDatePointage(Date datePointage) {
        this.datePointage = datePointage;
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

    public Salarie getFindSalarie() {
        return findSalarie;
    }

    public void setFindSalarie(Salarie findSalarie) {
        this.findSalarie = findSalarie;
    }

    public ChantierService getChantierService() {
        return chantierService;
    }

    public void setChantierService(ChantierService chantierService) {
        this.chantierService = chantierService;
    }

    public String getBtnPoint() {
        return btnPoint;
    }

    public void setBtnPoint(String btnPoint) {
        this.btnPoint = btnPoint;
    }

    @PostConstruct
    public void init() {
        salarieService = Module.ctx.getBean(SalarieService.class);
        chantierService = Module.ctx.getBean(ChantierService.class);
        affectationService = Module.ctx.getBean(AffectationService.class);
        heuresSupplementairesService = Module.ctx.getBean(HeuresSupplementairesService.class);
        contratService = Module.ctx.getBean(ContratService.class);
        currentDate = datePointage;
        btnPoint = "Pointage";

        tach = false;
    }

    public void find() {
        String matricule = findSalarie.getMatricule();
        findSalarie = salarieService.getSalarieByMatricule(findSalarie.getMatricule());
        if (findSalarie == null) {
            Module.message(1, "Code 1601 : salarié avec le matricule : " + matricule + " n'éxiste pas", "");
            clear();
            updateHeurMin();
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            String dateChaine = sdf.format(datePointage);
            Date date = new Date(dateChaine);
            String codeChantier = presenceService.recupererCodeChantierPointageParSalarie(findSalarie.getMatricule(), date);
            if ("".equals(codeChantier)) {
                e_S = "E";
            } else {
                e_S = "S";
            }
            updateHeurMin();
        }
    }

    public void updateHeurMin() {
        System.out.println("update " + idChantier);
        Chantier c = chantierService.getChantier(idChantier);
        try {
            System.out.println("update heur" + c.getHeureEntree());
        } catch (Exception e) {
            System.out.println("Erreur d'afficher le message");
        }
        e_S = (e_S == null) ? "E" : e_S;
        if (c != null) {
            System.out.println("update not null ");

            heure = (e_S.equals("S")) ? c.getHeureSortie().substring(0, 2) : c.getHeureEntree().substring(0, 2);

            min = (e_S.equals("S")) ? c.getHeureSortie().substring(3, 5) : c.getHeureEntree().substring(3, 5);

            if (e_S.equals("S") && tach) {
                Calendar cal = Calendar.getInstance();

                cal.setTime(new Date());
                heure = String.valueOf(cal.get(Calendar.HOUR_OF_DAY));
                if (heure.length() == 1) {
                    heure = "0" + heure;
                }
                min = String.valueOf(cal.get(Calendar.MINUTE));
                if (min.length() == 1) {
                    min = "0" + min;
                }
                System.out.println("heure : " + heure + "Min : " + min);
            }
        }
        btnPoint = e_S.equals("S") ? "Pointage de Sortie" : "Pointage d'entrée";

    }

    public void pointer() {
        if (heure.length() == 1) {
            heure = "0" + heure;
        }
        if (min.length() == 1) {
            min = "0" + min;
        }
        Chantier c1 = chantierService.getChantier(idChantier); // recupere le chantier
        Integer hsc = Integer.parseInt(c1.getHeureSortie().substring(0, 2)); //recupere l'heure de sortie du chantier
        Integer msc = Integer.parseInt(c1.getHeureSortie().substring(3, 5));//recupere les minutes de sortie du chantier

        long timeSortieChoisie = new Date(2016, 8, 1, Integer.parseInt(heure), Integer.parseInt(min), 0).getTime();//Variable temps ?,
        long timeSortieChantier = new Date(2016, 8, 1, hsc, msc, 0).getTime();//sortie

        if (tach && e_S.equals("S") && timeSortieChoisie > timeSortieChantier) { //verifie si pointage à la tache avec une heure de sortie superieur à l'heure de sortie normale
            Module.message(3, "Code 1605 : Vous avez choisi le pointage à la tâche, \nl'heure du pointage doit être inférieure à l'heure normale du chantier", "");
            clear();
            return;
        }
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        Authentification authentification = (Authentification) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "authentification");
        String demand = authentification.get_connected_user().getLogin();
        Salarie salarie = salarieService.getSalarieByMatricule(findSalarie.getMatricule());
        boolean isMensuel = false;
        if (salarie instanceof Mensuel) { // traitement si mensuel 
            isMensuel = true;
            Mensuel mensuel = (Mensuel) salarie;
            if (!"ACTIF".equals(mensuel.getStatut().toUpperCase()) && !"1".equals(mensuel.getStatut().toUpperCase())) { // a verifier
                Module.message(3, "Erreur", "Code 1610 : le mensuel " + salarie.getMatricule() + " n'est pas actif ");
                clear();
                return;
            }
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(datePointage);
        String heureMinutePointage = heure + ":" + min;
        date = date.replaceAll("-", "/");
        datePointage = new Date(date);
        long longDateTimePointage = new Date(date + " " + heureMinutePointage).getTime();
        Presence p = null;
        /**
         * Vérification de l'affectation du salarié au chantier
         */
        if (!isMensuel) {
            boolean resultatVerifierContrat = contratService.verifierContratLegalise(salarie.getId(), datePointage);
            boolean salarieActif = salarieService.isActif(salarie.getMatricule());
            System.out.println("resultatVerifierContrat: " + resultatVerifierContrat);

            if (resultatVerifierContrat == false && salarie instanceof Salarie) {
                //drap = true;
                Module.message(3, "Erreur", " Code 1608 : le salarie " + salarie.getMatricule() + " n'a pas de contrat légalisé en cette date ");
                clear();
                return;
            }

            if (salarieActif == false && salarie instanceof Salarie) {
                Module.message(3, "Erreur", "Code 1610 : le Salarié " + salarie.getMatricule() + " n'est pas actif ");
                clear();
                return;
            }
        }
        if (isMensuel) {
            System.out.println("MENSUEL");
            boolean affectMensuel = false;
            List<SousAffectation> ls = new LinkedList<>();
            ls = affectationService.getAffectation((Mensuel) salarie);
            if (ls != null && !ls.isEmpty()) {
                for (SousAffectation sa : ls) {
                    System.out.println("AFFECTATIONS :  # " + sa.getChantier().getId());
                    System.out.println("ID CHANTIER : " + idChantier);
                    if (Objects.equals(sa.getChantier().getId(), idChantier)) {
                        System.out.println("FOUND!");
                        affectMensuel = true;
                    }
                }
            }

            if (((Mensuel) salarie).getTypeFonction().compareToIgnoreCase("Mensuel Type Quinzainier") == 0) {
                System.out.println("TYPE QUINZINIER");
                if (affectMensuel) {
                    System.out.println("POINTE");
                } else {
                    Module.message(3, "Erreur", "Code 1609 : le Salarié " + salarie.getMatricule() + " n'est pas affecté à ce chantier");
                    clear();
                    return;
                }
            } else {
                Module.message(3, "Erreur", " le Mensuel " + salarie.getMatricule() + " ne peut être pointé car il n'est pas de type Quinzainier ");
                clear();
                return;

            }
        }
        boolean resultat = chantierService.verifierAffectationSalarieChantier(idChantier, salarie.getId());
        if (resultat == false) {
//            if (salarie instanceof Mensuel) {
//                //List<Affectation>affectationService.getAffectation((Mensuel) salarie);
//                boolean affectMensuel = false;
//                List<SousAffectation> ls = affectationService.getAffectation((Mensuel) salarie);
//                if (ls != null && !ls.isEmpty()) {
//                    for (SousAffectation sa : ls) {
//                        System.out.println("AFFECTATIONS :  # " + sa.getChantier().getId());
//                        System.out.println("ID CHANTIER : " + IdChantier);
//                        if (Objects.equals(sa.getChantier().getId(), IdChantier)) {
//                            System.out.println("FOUND!");
//                            affectMensuel = true;
//                        }
//                    }
//                }
//
//                if (((Mensuel) salarie).getTypeFonction().compareToIgnoreCase("Mensuel Type Quinzainier") == 0 && affectMensuel) {
//                    System.out.println("TYPE QUINZINIER");
//                } else if (((Mensuel) salarie).getTypeFonction().compareToIgnoreCase("Mensuel") == 0) {
//                    System.out.println("MENSUEL");
//                } else {
//                    Module.message(3, "Erreur", "Code 1609 : le Salarié " + salarie.getMatricule() + " n'est pas affecté à ce chantier");
//                    clear();
//                    return;
//                }
//            } else {

            if (!isMensuel) {
                Module.message(3, "Erreur", "Code 1609 : le Salarié " + salarie.getMatricule() + " n'est pas affecté à ce chantier");
                clear();
                return;
            }
            //  }

        }
        ////
        boolean validiteHeureMinuteAbsence = false;
        if (e_S.equals("S")) {
            if (!presenceService.dejaPointeSortie(salarie.getId(), longDateTimePointage, datePointage, "S")) {
                Module.message(3, "Attention", "Code 1606 : ce salarié " + salarie.getMatricule() + " est déjà Pointé en sortie");
                clear();
                return;
            }
            p = presenceService.getPresence(idChantier, datePointage, salarie.getId(), "PS");
            if (p == null) {
                validiteHeureMinuteAbsence = presenceService.verifierValiditeDateHeureMinutePointageSalarieAbsence(salarie.getId(), null, longDateTimePointage, "PS");
            } else {
                validiteHeureMinuteAbsence = presenceService.verifierValiditeDateHeureMinutePointageSalarieAbsence(salarie.getId(), p.getLongDateTimeEntree(), longDateTimePointage, "PS");
            }
        } else {
            System.out.println("@@@@@pointage Entrer vérif");
            if (presenceService.dejaPointeSortie(salarie.getId(), longDateTimePointage, datePointage, "E") && presenceService.dejaPointeSortie(salarie.getId(), longDateTimePointage, datePointage, "S")) {
                System.out.println("dejà pointé");
                if (salarie instanceof Mensuel) {
                    System.out.println(" @ dejà pointé mensuel");
                    Mensuel m = (Mensuel) salarie;
                    if (m.getTypeAffectation()) {
                        System.out.println(" @@ mensuel multi");
                        Presence pre = presenceService.getPresence(idChantier, m.getId());
                        if (pre != null) {
                            System.out.println(" @@ déjà pointé présence ");
                            Chantier c = pre.getChantier();
                            pre.setDateSaisieHeureSortie(new Date());
                            pre.setCreePar(demand);
                            pre.setDateCreation(new Date());
                            pre.setHeureSortie(c.getHeureSortie());
                            pre.setLongDateTimeSortie(longDateTimePointage);
                            pre.setHeureSortieReelle(c.getHeureSortie());
                            String nombreHeuresMinutesPresence = presenceService.nombreHeuresMinutesPresence(pre.getHeureEntree(), heure + ":" + min);
                            if (!"".equals(nombreHeuresMinutesPresence)) {
                                String[] tab = nombreHeuresMinutesPresence.split(":");
                                pre.setNombreHeures(Integer.parseInt(tab[0]));
                                pre.setNombreMinutes(Integer.parseInt(tab[1]));
                            }
                            presenceService.modifierPresence(pre);
                        }
                    } else {
                        Module.message(1, "Attention", "ce Mensuel " + salarie.getMatricule() + " est déjà Pointé en entrée ");
                        clear();
                        return;
                    }
                } else {
                    Module.message(1, "Attention", "ce Salarié " + salarie.getMatricule() + " est déjà Pointé en entrée ");
                    clear();
                    return;
                }
            }
            p = presenceService.getPresence(idChantier, datePointage, salarie.getId(), "Evol");
            if (p == null) {
                validiteHeureMinuteAbsence = presenceService.verifierValiditeDateHeureMinutePointageSalarieAbsence(salarie.getId(), longDateTimePointage, null, "PE");
            } else {
                validiteHeureMinuteAbsence = presenceService.verifierValiditeDateHeureMinutePointageSalarieAbsence(salarie.getId(), longDateTimePointage, p.getLongDateTimeSortie(), "PE");
            }
        }
        if (validiteHeureMinuteAbsence == false) {
            Module.message(2, "Erreur", "Code1611 : Echec de pointage : ce salarié " + salarie.getMatricule() + " est déjà marqué absent à cette date");
            clear();
            return;
        }
        ////
        boolean validiteHeureMinute = false;
        if (e_S.equals("S")) {
            validiteHeureMinute = presenceService.verifierValiditeHeureMinutePointageSalarie(salarie.getId(), datePointage, longDateTimePointage, "PS");
        } else if (salarie instanceof Mensuel) {
            Mensuel m = (Mensuel) salarie;
            //si mensuel est multi chantier on ignore la vérification d'heur minute
            if (m.getTypeAffectation()) {
                validiteHeureMinute = true;
            }
        } else {
            validiteHeureMinute = presenceService.verifierValiditeHeureMinutePointageSalarie(salarie.getId(), datePointage, longDateTimePointage, "PE");
        }///vérifier l'heure du pointage 
        if (validiteHeureMinute == false) {
            Module.message(2, "Oups", "Code 1612 : Echec de pointage : ce salarié " + salarie.getMatricule() + " vérifier l'heure du pointage");
            clear();
            return;
        }
        if (e_S.equals("S")) {
            if (p == null) {
                Module.message(1, "Attention", "Code 1602 : Echec de pointage de sortie : ce salarié " + salarie.getMatricule() + " n'est pas pointé en entrée");
                clear();

            } else if (!presenceService.dejaPointeSortie(salarie.getId(), longDateTimePointage, datePointage, "S")) {
                Module.message(3, "Erreur", "Code 1607 : le salarié est déjà pointé en sortie");
                clear();
                return;
            } else {
                // ----- Transformation de présences hors heures normales en Heures Supplémentaires -----
                Chantier ch = chantierService.getChantierPresences(idChantier);
                Salarie s = salarieService.getSalarie(salarie.getId());
                Long longDateTimeSortieChantier = null;
                if (!date.equals("")) {
                    longDateTimeSortieChantier = new Date(date + " " + ch.getHeureSortie()).getTime();
                }
                if (longDateTimePointage > longDateTimeSortieChantier) {
                    p.setHeureSortie(ch.getHeureSortie());
                    p.setLongDateTimeSortie(longDateTimeSortieChantier);
                    String nombreHeuresMinutesPresence = presenceService.nombreHeuresMinutesPresence(p.getHeureEntree(), p.getHeureSortie());
                    if (!"".equals(nombreHeuresMinutesPresence)) {
                        String[] tab = nombreHeuresMinutesPresence.split(":");
                        p.setNombreHeures(Integer.parseInt(tab[0]));
                        p.setNombreMinutes(Integer.parseInt(tab[1]));
                    }
                    presenceService.modifierPresence(p);
                    //     ajouter les heures hors heures normale en tant que heures sup, 
                    //     qui auront l'heure de sortie normale de chantier comme heure de début .
                    //     et l'heure de pointage comme heure de fin
                    ma.bservices.beans.HeuresSupplementaires hs = new ma.bservices.beans.HeuresSupplementaires();
                    EtatHeuresSupplementaires etatHS = new EtatHeuresSupplementaires();
                    etatHS.setId(1);
                    hs.setSalarie(s);
                    hs.setChantier(ch);
                    hs.setEtat(etatHS);
                    hs.setDate(datePointage);
                    hs.setHeureDebut(ch.getHeureSortie());
                    hs.setHeureFin(heureMinutePointage);
                    String nbreHeures = presenceService.nombreHeuresMinutesPresence(ch.getHeureSortie(), heureMinutePointage);
                    if (nbreHeures != "") {
                        String[] tab = nbreHeures.split(":");
                        hs.setNombreHeures(Integer.parseInt(tab[0]));
                        hs.setNombreMinutes(Integer.parseInt(tab[1]));
                    }
                    heuresSupplementairesService.ajouterHS(hs);
                } else {
                    p.setDateSaisieHeureSortie(new Date());
                    p.setCreePar(demand);
                    p.setDateCreation(new Date());
                    if (tach == true) {
                        p.setHeureSortie(ch.getHeureSortie());
                        p.setLongDateTimeSortie(longDateTimePointage);
                        p.setHeureSortieReelle(heureMinutePointage);
                        String nombreHeuresMinutesPresence = presenceService.nombreHeuresMinutesPresence(p.getHeureEntree(), p.getHeureSortie());
                        if (!"".equals(nombreHeuresMinutesPresence)) {
                            String[] tab = nombreHeuresMinutesPresence.split(":");
                            p.setNombreHeures(Integer.parseInt(tab[0]));
                            p.setNombreMinutes(Integer.parseInt(tab[1]));
                        }
                    } else {
                        p.setHeureSortie(heureMinutePointage);
                        p.setLongDateTimeSortie(longDateTimePointage);
                        p.setHeureSortieReelle(heureMinutePointage);
                        String nombreHeuresMinutesPresence = presenceService.nombreHeuresMinutesPresence(p.getHeureEntree(), heure + ":" + min);
                        if (!"".equals(nombreHeuresMinutesPresence)) {
                            String[] tab = nombreHeuresMinutesPresence.split(":");
                            p.setNombreHeures(Integer.parseInt(tab[0]));
                            p.setNombreMinutes(Integer.parseInt(tab[1]));
                        }
                    }
                    presenceService.modifierPresence(p);
                }// ------ fin transformation de présences hors heures normales en Heures Supplémentaires -----
            }
        } else {
            Chantier chantier = chantierService.getChantierPresences(idChantier);
            if (p == null) {
                if (presenceService.dejaPointeSortie(salarie.getId(), longDateTimePointage, datePointage, "E") && presenceService.dejaPointeSortie(salarie.getId(), longDateTimePointage, datePointage, "S")) {
                    if (salarie instanceof Mensuel) {
                        System.out.println(" @ dejà pointé mensuel");
                        Mensuel m = (Mensuel) salarie;
                        if (!m.getTypeAffectation()) {
                            Module.message(2, "Code 1607 : Le Mensuel Matricule " + salarie.getId() + " est déjà pointé en cette période", "");
                            clear();
                            return;
                        }

                    } else {
                        Module.message(2, "Code 1607 : Le salarié " + salarie.getId() + " est déjà pointé en cette période", "");
                        clear();
                        return;
                    }
                }
                Presence presence = new Presence();
                presence.setDate(datePointage);
                presence.setSalarie(salarie);
                presence.setChantier(chantier);
                presence.setDateSaisieHeureEntree(new Date());
                if (tach == true) {
                    presence.setHeureEntree(chantier.getHeureEntree());
                    presence.setLongDateTimeEntree(longDateTimePointage);
                    presence.setHeureEntreeReelle(heureMinutePointage);
                } else {
                    presence.setHeureEntree(heureMinutePointage);
                    presence.setLongDateTimeEntree(longDateTimePointage);
                    presence.setHeureEntreeReelle(heureMinutePointage);
                }
                presence.setFlag(false);
                presence.setCreePar(demand);
                presence.setDateCreation(new Date());
                presenceService.ajouterPresence(presence);
            } else {
                if (tach == true) {
                    p.setHeureEntree(chantier.getHeureEntree());
                    p.setLongDateTimeEntree(longDateTimePointage);
                    p.setHeureEntreeReelle(heureMinutePointage);

                    p.setHeureEntree(heureMinutePointage);
                    p.setLongDateTimeEntree(longDateTimePointage);

                } else {
                    p.setHeureEntree(heureMinutePointage);
                    p.setLongDateTimeEntree(longDateTimePointage);
                    p.setHeureEntreeReelle(heureMinutePointage);

                    p.setHeureEntree(heureMinutePointage);
                    p.setLongDateTimeEntree(longDateTimePointage);

                }

                p.setCreePar(demand);
                p.setDateCreation(new Date());
                presenceService.modifierPresence(p);

            }
        }

        Module.message(0, "Info", "Pointage réussi");
        clear();
    }

}
