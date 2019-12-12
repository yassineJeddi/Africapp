/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.mb.mensuel;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import static java.util.Calendar.HOUR_OF_DAY;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import ma.bservice.tgcc.Constante.Message;
import ma.bservices.beans.Fonction;
import ma.bservices.tgcc.Entity.AbsenceMensuel;
import ma.bservices.tgcc.Entity.Crenau;
import ma.bservices.tgcc.Entity.Mensuel;
import ma.bservices.tgcc.service.Mensuel.AbsenceService;
import ma.bservices.tgcc.service.Mensuel.MensuelService;
import ma.bservices.tgcc.service.Mensuel.PointageService;
import ma.bservices.tgcc.webService.MensuelWSCallManager;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

/**
 *
 * @author j.allali
 */
@Component
@ManagedBean(name = "absenceMb")
@ViewScoped
public class AbsenceMb {

    @ManagedProperty(value = "#{mensuelService}")
    private MensuelService mensuelService;

    @ManagedProperty(value = "#{absenceService}")
    private AbsenceService absenceService;

    private Mensuel mensuelToSearch = new Mensuel();

    private Mensuel mensuelToSearchForNewAbsence = new Mensuel();

    private Mensuel mensuel = new Mensuel();

    private AbsenceMensuel absenceToAdd;

    private int mensuel_id_selected;

    private String fonction;

    private boolean readyToConfirm = false;

    List<Mensuel> searchMensuelList = new ArrayList<>();

    private List<AbsenceMensuel> absences = new ArrayList<>();
    private AbsenceMensuel selectAB = new AbsenceMensuel();
    private String HeureE;

    private String HeureS;
    private String radioBC;

    private String heuredebut; //l'heure de début d'une absence

    private String heureFin; //l'heure de la fin d'une absence

    private Date date;

    private int crenauxF = 0;

    private int crenauxT = 0;

    private AbsenceMensuel AB = new AbsenceMensuel();

    public AbsenceMensuel getAB() {
        return AB;
    }

    public void setAB(AbsenceMensuel AB) {
        this.AB = AB;
    }

    public Date getDate() {
        return date;
    }

    public int getMensuel_id_selected() {
        return mensuel_id_selected;
    }

    public void setMensuel_id_selected(int mensuel_id_selected) {
        this.mensuel_id_selected = mensuel_id_selected;
    }

    public int getCrenauxF() {
        return crenauxF;
    }

    public void setCrenauxF(int crenauxF) {
        this.crenauxF = crenauxF;
    }

    public int getCrenauxT() {
        return crenauxT;
    }

    public void setCrenauxT(int crenauxT) {
        this.crenauxT = crenauxT;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<AbsenceMensuel> getAbsences() {
        return absences;
    }

    public Mensuel getMensuelToSearchForNewAbsence() {
        return mensuelToSearchForNewAbsence;
    }

    public void setMensuelToSearchForNewAbsence(Mensuel mensuelToSearchForNewAbsence) {
        this.mensuelToSearchForNewAbsence = mensuelToSearchForNewAbsence;
    }

    public String getHeuredebut() {
        return heuredebut;
    }

    public void setHeuredebut(String heuredebut) {
        this.heuredebut = heuredebut;
    }

    public String getHeureFin() {
        return heureFin;
    }

    public void setHeureFin(String heureFin) {
        this.heureFin = heureFin;
    }

    public void setAbsences(List<AbsenceMensuel> absences) {
        this.absences = absences;
    }

    public AbsenceService getAbsenceService() {
        return absenceService;
    }

    public void setAbsenceService(AbsenceService absenceService) {
        this.absenceService = absenceService;
    }

    public Mensuel getMensuel() {
        return mensuel;
    }

    public void setMensuel(Mensuel mensuel) {
        this.mensuel = mensuel;
    }

    public boolean isReadyToConfirm() {
        return readyToConfirm;
    }

    public void setReadyToConfirm(boolean readyToConfirm) {
        this.readyToConfirm = readyToConfirm;
    }

    public Mensuel getMensuelToSearch() {
        return mensuelToSearch;
    }

    public void setMensuelToSearch(Mensuel mensuelToSearch) {
        this.mensuelToSearch = mensuelToSearch;
    }

    public String getFonction() {
        return fonction;
    }

    public AbsenceMensuel getAbsenceToAdd() {
        return absenceToAdd;
    }

    public void setAbsenceToAdd(AbsenceMensuel absenceToAdd) {
        this.absenceToAdd = absenceToAdd;
    }

    public void setFonction(String fonction) {
        this.fonction = fonction;
    }

    public List<Mensuel> getSearchMensuelList() {
        return searchMensuelList;
    }

    public void setSearchMensuelList(List<Mensuel> searchMensuelList) {
        this.searchMensuelList = searchMensuelList;
    }

    public MensuelService getMensuelService() {
        return mensuelService;
    }

    public void setMensuelService(MensuelService mensuelService) {
        this.mensuelService = mensuelService;
    }

    public AbsenceMensuel getSelectAB() {
        return selectAB;
    }

    public void setSelectAB(AbsenceMensuel selectAB) {
        this.selectAB = selectAB;
    }

    public String getHeureE() {
        return HeureE;
    }

    public void setHeureE(String HeureE) {
        this.HeureE = HeureE;
    }

    public String getHeureS() {
        return HeureS;
    }

    public void setHeureS(String HeureS) {
        this.HeureS = HeureS;
    }

    public String getRadioBC() {
        return radioBC;
    }

    public void setRadioBC(String radioBC) {
        this.radioBC = radioBC;
    }

    @PostConstruct
    public void init() {
        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        mensuelService = ctx.getBean(MensuelService.class);
        absenceService = ctx.getBean(AbsenceService.class);

        mensuelToSearch = new Mensuel();
        mensuelToSearch.setFonction(new Fonction());

        this.searchMensuelList = mensuelService.findAll();

    }

    /**
     * Creates a new instance of AbsenceMb
     */
    public AbsenceMb() {
    }

    public void rechercherMensuel() {

        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        mensuelService = ctx.getBean(MensuelService.class);

        this.searchMensuelList = mensuelService.search(mensuelToSearch.getMatricule(), mensuelToSearch.getNom(), mensuelToSearch.getPrenom(), mensuelToSearch.getFonction().getFonction(), "");
    }

    /**
     * retourne la liste des absence généré à partir du pointage
     *
     * @param mensuel_id
     */
    public void absenceByIdMesuel_tocheck(int mensuel_id) {

        mensuel_id_selected = mensuel_id;
        absences = absenceService.getAbsenceById_toCheck(mensuel_id);

    }

    /**
     * à la selection du mensuel depuis le dialog de la recherche pour ajouter
     * une nouvelle absence.
     *
     * @param mensuel_id
     */
    public void selectMensuel_ForNewAbsence(int mensuel_id) {
        mensuel_id_selected = mensuel_id;
        mensuelToSearchForNewAbsence = mensuelService.getById(mensuel_id);

        absenceToAdd = new AbsenceMensuel();
        absenceToAdd.setSalarie(mensuelToSearchForNewAbsence);
    }

    public void absenceByIdMesuel(int mensuel_id) {

        absences = absenceService.getAbsenceById(mensuel_id);

    }
    
    public void reinitSearch(){
     absences = absenceService.getAbsenceById(mensuel.getId());
     date = null;
    }

    public void searchByDate() throws ParseException {

        if (date == null) {
           absences = absenceService.getAbsenceById(mensuel.getId());
        } else {
          //  absences.clear();
            absences = absenceService.getMensuelByDate(date);
        }

    }

    public void updatePointageMQ() {

        absenceService.updatePointageMQ(selectAB);
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("" + Message.UPDATE_ABSENCE, ""));

    }

    public String converdate(Date d) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy : HH");
        String sd = sdf.format(d);
        System.out.println(sd);
        return sd;
    }

    public String converdateDay(Date d) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String sd = sdf.format(d);
        System.out.println(sd);
        return sd;
    }

    public String converdateHour(Date d) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH");
        String sd = sdf.format(d);
        System.out.println(sd);
        return sd;
    }

    /**
     * valider ou annule les absences
     */
    public void valider_cancel__Absence() {

        MensuelWSCallManager.absenceWS("005", "02-11-2015 10:00:00", "03-11-2015 18:00:00", "maladie", 0.25f);

        int startDay, endDay, startHour, endHour;
        float nbHeures = 0f;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String dateDeb, dateFin;

        for (AbsenceMensuel abs : absences) {
            if (!abs.getChecked().equals(null)) {
                if (abs.getChecked() == 1) {
//                System.out.println("id mensuel : " + mensuel_id_selected);
//                System.out.println("date debut : " + abs.getDateDebut());
//                
//                System.out.println("day debut : " + abs.getDateDebut().toString().substring(8, 10));
                    startDay = Integer.parseInt(abs.getDateDebut().toString().substring(8, 10));

//                System.out.println("day fin : " + abs.getDateFin().toString().substring(8, 10));
                    endDay = Integer.parseInt(abs.getDateFin().toString().substring(8, 10));

//                System.out.println("heure debut : " + abs.getDateDebut().toString().substring(11, 13));
                    startHour = Integer.parseInt(abs.getDateDebut().toString().substring(11, 13));

//                System.out.println("heure fin : " + abs.getDateFin().toString().substring(11, 13));
                    endHour = Integer.parseInt(abs.getDateFin().toString().substring(11, 13));

//                System.out.println("daytime debut : " + startDay + "::" + startHour);
//                System.out.println("daytime fin : " + endDay + "::" + endHour);
                    if (startDay == endDay) {
                        if ((endHour > 12 && startHour > 12) || endHour <= 12) {
                            nbHeures = (float) 0.25 * ((endHour - startHour) / 2);
                        } else if (endHour > 12 && startHour < 12) {
                            nbHeures = (float) 0.25 * (((endHour - startHour) - 2) / 2);
                        }
                    } else if (endDay > startDay) {

                        if ((endHour > 12 && startHour > 12) || endHour <= 12) {
                            nbHeures = (float) ((endDay - startDay) + 0.25 * ((endHour - startHour) / 2));
                        } else if (endHour > 12 && startHour < 12) {
                            nbHeures = (float) ((endDay - startDay) + 0.25 * (((endHour - startHour) - 2) / 2));
                        }

                    }

                    System.out.println("NB HEURES : " + nbHeures);

                    dateDeb = dateFormat.format(abs.getDateDebut());
                    dateFin = dateFormat.format(abs.getDateFin());
//                System.out.println("date fin : " + abs.getDateFin() +  "formatter : " + dateFormat.format(abs.getDateDebut()));
//                System.out.println("nbHeures : " + );
//                System.out.println("type : " + abs.getTypeAbsence());
                    System.out.println(String.valueOf(mensuel_id_selected));
                    MensuelWSCallManager.absenceWS(String.valueOf(mensuel_id_selected), dateDeb, dateFin, abs.getTypeAbsence(), nbHeures);
//                MensuelWSCallManager.absenceWS("997", "29-04-2016 08:00:00", "29-04-2016 18:00:00", "maladie", 0.25f);
                }
            }
        }

        absenceService.valider_cancel__Absence(absences);
        absences = absenceService.getAbsenceById_toCheck(mensuel_id_selected);
        System.out.println(mensuel_id_selected);
        System.out.println(absences);
    }

    public void addAbsence(AbsenceMensuel ab) {
//        absenceService.a

        List<AbsenceMensuel> lab_chev = absenceService.getAbsencesChevauchement(ab);

        if (lab_chev.size() > 0) {
            FacesContext context = FacesContext.getCurrentInstance();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "", "Tu ne peux pas ajouter d'absence dans cet intervalle"));

        } else {
            if (crenauxF == 0) {
                crenauxF = PointageService.CRANEAU_8_A_10;
            }

            if (crenauxT == 0) {
                crenauxT = PointageService.CRANEAU_16_A_18;
            }

            Crenau crenauF = getHoraireCrenau(crenauxF);
            Crenau crenauT = getHoraireCrenau(crenauxT);

            //ajout de la date de début
            Calendar cal_debut = Calendar.getInstance();
            cal_debut.setTime(ab.getDateDebut());
            cal_debut.set(HOUR_OF_DAY, crenauF.getFrom());
            ab.setDateDebut(cal_debut.getTime());

            //ajout de la date de fin
            Calendar cal_fin = Calendar.getInstance();
            cal_fin.setTime(ab.getDateFin());
            cal_fin.set(HOUR_OF_DAY, crenauT.getTo());
            ab.setDateFin(cal_fin.getTime());

            absenceService.add_absense(ab);

            absenceToAdd = new AbsenceMensuel();
            mensuelToSearchForNewAbsence = null;
            FacesContext context = FacesContext.getCurrentInstance();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "L'absence a été bien ajoutée"));
        }
    }

    /**
     * get crenaux object
     */
    public Crenau getHoraireCrenau(int crenauInt) {
        Crenau _crenau = new Crenau(crenauInt);

        switch (crenauInt) {
            case PointageService.CRANEAU_8_A_10:
                _crenau.setFrom(8);
                _crenau.setTo(10);
                break;

            case PointageService.CRANEAU_10_A_12:
                _crenau.setFrom(10);
                _crenau.setTo(12);
                break;

            case PointageService.CRANEAU_14_A_16:
                _crenau.setFrom(14);
                _crenau.setTo(16);
                break;

            case PointageService.CRANEAU_16_A_18:
                _crenau.setFrom(16);
                _crenau.setTo(18);
                break;

        }

        return _crenau;
    }

}
