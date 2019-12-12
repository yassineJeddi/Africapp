/*

 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.mb.mensuel;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import ma.bservice.tgcc.Constante.Constante;
import ma.bservice.tgcc.Constante.Message;
import ma.bservices.beans.Chantier;
import ma.bservices.beans.Utilisateur;
import ma.bservices.tgcc.Entity.Crenau;
import ma.bservices.tgcc.Entity.Mensuel;
import ma.bservices.tgcc.Entity.Pointage;
import ma.bservices.tgcc.Entity.SousAffectation;
import ma.bservices.tgcc.mb.mensuel.bean.PointageFront;
import ma.bservices.tgcc.mb.mensuel.bean.PointageWeek;
import ma.bservices.tgcc.service.Engin.ChantierService;
import ma.bservices.tgcc.service.Engin.UtilisateurService;
import ma.bservices.tgcc.service.Mensuel.AbsenceService;
import ma.bservices.tgcc.service.Mensuel.AffectationService;
import ma.bservices.tgcc.service.Mensuel.MensuelService;
import ma.bservices.tgcc.service.Mensuel.PointageService;
import ma.bservices.tgcc.service.Mensuel.bean.PointageDataReturn;
import org.primefaces.context.RequestContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

/**
 *
 * @author zakaria.dem
 *
 */
@Component
@ManagedBean(name = "pointageMem")
@ViewScoped
public class PointageMb implements Serializable {

    @ManagedProperty(value = "#{affectationService}")
    private AffectationService affectationService;

    @ManagedProperty(value = "#{pointageService}")
    private PointageService pointageService;

    @ManagedProperty(value = "#{absenseService}")
    private AbsenceService absenceService;

    @ManagedProperty(value = "#{mensuelService}")
    private MensuelService mensuelService;

    @ManagedProperty(value = "#{utilisateurService}")
    private UtilisateurService utilisateurService;

    @ManagedProperty(value = "#{chantierService}")
    private ChantierService chantierService;

    final Integer first_year = 2015; //première année du pointage

    final static Integer count_week_to_display_by_couth = 6;

    private Date datePointage;

    private List<Integer> l_year;

    private Integer year_for_affectation;

    private Integer mounth_for_affectation;

    private List<String> list_chantier_toAdd;

    private int curent_week_number; // le date courante dans l'interface, si le mensuel clique sur la semaine 30 => la valeur de cet attribut sera 30 et pas le num de la semaine (daaaba :-) )

    private int curent_week_number_real_time; //la numero de la seamine courante dans le temps ( daba daba )

    private int curent_year_number_real_time;

    private List<PointageWeek> last_weeks_numbers; //le numéro des précedentes semaines à afficher dans l'interface

    private String day_start_of_current_week;

    private Date day_start_of_current_week_obj; //le premier jour de la date courante

    private Date day_end_of_current_week_obj; //le dernier jour de la date courante

    private String day_end_of_current_week;

    private Boolean val_definitive; //pour verifier si on doit autoriser la validation définitive ou non.

    private Boolean PointedDefenetively; //si la semaine est déjà valider définitivement, on n'a pas le droit de la modifier

    private Boolean pointadLast; //si la personnes a pointée les dernières semaine 

    private List<SousAffectation> l_aff;

    private Boolean display_pointage_array_in_view = Boolean.TRUE; //si la valuer est false on affiche pas le tableau du pointage.

    private List<String> l_aff_str;

    private List<Chantier> chantiers;

    private Mensuel current_connected_mensuel;

    private int autre; //l'index dans la liste des affectations, en cliquant sur ce le select item on verifie si c'est le bon item et on ouvre la popup 

    private int conge; //l'index dans la liste des affectations, en cliquant sur ce le select item on verifie si c'est le bon item et on ouvre la popup 

    private List<PointageFront> l_pf; //ici on enregistre la liste des pointage

    private int iteration = 0; //on sauvegarde l'iteration pour savoir où on va ajouter un nouveau chantier

    List<String> days_of_week; //le journées de la semaine à afficher dans la vue

    private int positionInAffectationList;

    private int day_number_in_desplayed_week;

    private int ValueInAffectationList;

    private String valuefOther;

    private Date last_week_pointed;

    private Date day_end_of_current_week_obj_forValidation;

    private Integer week_number; //enregistre la dernière semaine que nous avons charger dans la page, au cas où on veut ajouter un chantier, on aura pas l'information de la semaine chargé

    private List<Pointage> l_absense; //on rassemble la liste des abscences 

    private List<Chantier> chantierServ;

    private int varStatus1 = 0;

    private String chantierSearch;

    //Parameter pour la consultation de la fiche de pointage
    private int mensuel_forSearch;
    private List<String> codes_fiche_mensuel = new LinkedList<>();
    private String selected_code_fiche;
    private List<Mensuel> mensuels_l; //la liste des mensuel, pour le service RH, c'est pour selectionner la liste des pointage
    private Integer chantierSearch_rh = null; //contient le chantier de recherche du service RH
    private Boolean is_rh = Boolean.FALSE; //pour la vue, pour afficher ou non les fonctionnalités relatifs à un mensuel
    private Boolean display_weeks; //afficher les semaine dans la vue ou non, si c'est un RH , çà ne sert à rien de les afficher au premier coups

    public PointageMb() {

        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        utilisateurService = ctx.getBean(UtilisateurService.class);
        mensuelService = ctx.getBean(MensuelService.class);
        affectationService = ctx.getBean(AffectationService.class);

    }

    @PostConstruct
    public void init() {

        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        utilisateurService = ctx.getBean(UtilisateurService.class);
        pointageService = ctx.getBean(PointageService.class);

        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        ma.bservices.tgcc.mb.services.ChantierMb chantierSerMb = (ma.bservices.tgcc.mb.services.ChantierMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "chantierServMb");
        ma.bservices.tgcc.mb.services.MensuelMb mensuelMb = (ma.bservices.tgcc.mb.services.MensuelMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "mensuelServMb");

        this.chantierServ = chantierSerMb.getChantiers();

        this.chantiers = new LinkedList<>();

        for (int i = 0; i < chantierServ.size(); i++) {
            this.chantiers.add(chantierServ.get(i));
        }

        this.mensuels_l = mensuelMb.l_mensuels;

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String user = auth.getPrincipal().toString();

        Utilisateur user_obj = utilisateurService.getUsersByLogin(user);

        if (user_obj.has_group(Constante.GROUPE_MENSUELS)) {
            this.current_connected_mensuel = user_obj.getMensuel();
        }

        datePointage = new Date();
        DateFormat df = new SimpleDateFormat("yyyy");
        String date_pointage_year = df.format(datePointage);

        //la liste des années qu'on affiche
        l_year = new LinkedList<Integer>();

        Integer year_start = first_year;

        do {
            l_year.add(year_start);
            year_start++;
        } while (year_start <= Integer.parseInt(date_pointage_year));

        //connecté à l'interface via un mensuel
        if (this.current_connected_mensuel instanceof Mensuel) {
            try {
                this.get_Calendar_forPoint_byWeekNumber(null, null, false);
            } catch (ParseException ex) {
                Logger.getLogger(PointageMb.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        //connecté à l'interface via un RH
        if (user_obj.has_group(Constante.GROUPE_RHS)) {
            this.is_rh = Boolean.TRUE;
            this.display_weeks = Boolean.FALSE;
            this.codes_fiche_mensuel = pointageService.distinct_fiche_mensuels();
            this.display_pointage_array_in_view = Boolean.FALSE;
        } else {
            this.is_rh = Boolean.FALSE;
            this.display_weeks = Boolean.TRUE;
        }

    }

    /**
     * get view calendar by week number in year
     *
     * @param week_number
     * @param year_of_week
     * @param add_chantier
     * @throws ParseException
     */
    public void get_Calendar_forPoint_byWeekNumber(Integer week_number, Integer year_of_week, Boolean add_chantier) throws ParseException {

        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        this.display_weeks = Boolean.TRUE;

        this.chantiers = new LinkedList<>();

        for (int i = 0; i < this.chantierServ.size(); i++) {
            this.chantiers.add(chantierServ.get(i));
        }

        if (year_of_week != null && year_of_week.compareTo(0) == 0) {
            year_of_week = null;
        }

        this.year_for_affectation = year_of_week != null ? year_of_week : this.year_for_affectation;
        System.out.println("affectation year " + this.year_for_affectation);

        System.out.println("first week number" + week_number);
        if (add_chantier == false) {

            System.out.println("ADD CHANTIER");
            this.week_number = week_number;
            list_chantier_toAdd = new LinkedList<>();
        } else {
            week_number = this.week_number;

            list_chantier_toAdd.add(chantierSearch);

        }

        if (week_number != null && week_number.intValue() == 0) {
            week_number = null;
        }

        //on verifie si le numéro de la semaine est dans le laps de temps géré.
        this.display_pointage_array_in_view = true;

        //les informations à propos des dates, on doit envoyer la semaine plus l'année
        calculateTheCurrentInformationsAboutDate(week_number);

        if (week_number != null && week_number > this.curent_week_number_real_time && this.year_for_affectation >= this.curent_year_number_real_time) {
            this.display_pointage_array_in_view = false;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Impossible d'ajouter une absence dans cette plage horaire", ""));
            return;
        }

        Calendar first_week_in_pointage = Calendar.getInstance();
        first_week_in_pointage.setTime(Constante.get_first_day_in_pointage_forAllApplication());

        Calendar date_creation_mensuel = Calendar.getInstance();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        date_creation_mensuel.setTime(this.current_connected_mensuel.getDateCreation());

        if (first_week_in_pointage.compareTo(date_creation_mensuel) < 0) {
            first_week_in_pointage = date_creation_mensuel;

        }

        int first_year_forThisMensuel = first_week_in_pointage.get(Calendar.YEAR);
        int first_week = first_week_in_pointage.get(Calendar.WEEK_OF_YEAR);

        if (week_number != null && week_number < first_week && this.year_for_affectation <= first_year_forThisMensuel) {
            this.display_pointage_array_in_view = false;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "", Message.POIN_DECLARATIF_DATE));
            return;
        }

        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        affectationService = ctx.getBean(AffectationService.class);
        pointageService = ctx.getBean(PointageService.class);

        pointadLast = false;

        l_aff_str = new ArrayList<String>(20);

        for (int i = 0; i < 20; i++) {
            l_aff_str.add("");
        }

        //les dates
        //les journée sous forme de string à afficher 
        getDataForArray_dayOfWeek();

        //si on doit autoriser la validation définitive ou non.
        this.val_definitive = definitiveValidation(this.day_end_of_current_week_obj_forValidation);

        //retourne la date à pointer, la semaine après la dernière semaine pointé.
        this.last_week_pointed = this.lastWeekPointed();
        //pour alerter  l'utilisteur s'il n'as pas fait ses pointages.
        if (this.last_week_pointed != null) {

            Calendar current_calendar = Calendar.getInstance();
            current_calendar.setTime(new Date());

            int current_year = this.year_for_affectation;

            Calendar calendar_last_week_pointed = Calendar.getInstance();
            calendar_last_week_pointed.setTime(this.last_week_pointed);

            int last_week_pointed_int = calendar_last_week_pointed.get(Calendar.WEEK_OF_YEAR);
            int last_year_pointed_int = calendar_last_week_pointed.get(Calendar.YEAR);

            //si l'année courrante est sup à la dernière année du pointage ou bien si ils sont égaux et la semaine courante est sup à la dernière semaine du pointage
            if ((current_year - last_year_pointed_int > 0) || ((current_year - last_year_pointed_int == 0) && (this.curent_week_number - last_week_pointed_int > 0))) {
                this.pointadLast = true;
//                if (this.is_rh && this.is_rh.equals(Boolean.FALSE)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "", Message.MSG_SEMAINE_APOINTER + (df.format(this.last_week_pointed))));
//                }

            }
        }

        //les pointage de la semaine :D
        //tous les pointage entre la date du premier jour et la date du dernier jour
        PointageDataReturn pointageDataReturn = pointageService.getListePointageValue(this.day_start_of_current_week_obj, this.day_end_of_current_week_obj, this.current_connected_mensuel);
        l_aff = null;
        //get affections from Affectation table or from liste of pointage, si on n'a jammais pointer pour la date
        //we have to get the first and the last day in the week.

        System.out.println("day start " + this.day_start_of_current_week_obj + "day end " + this.day_end_of_current_week_obj);
        l_aff = affectationService.getAffectation(this.current_connected_mensuel, this.day_start_of_current_week_obj, this.day_end_of_current_week_obj);
        //supprime les redondonce, deux affectations peuvent être liés au même chantier.
        if (l_aff != null) {
            l_aff = affectationService.remove_redundancy_inChantierSubAffectation(l_aff);
            System.out.println("list affectaion " + l_aff.size());
        }

        List<Chantier> list_chantier = concat_ListChantierByAffection_ListChantierByPointage(l_aff, pointageDataReturn.getL_chantierFromPointage());

        System.out.println("after concat " + list_chantier.size());

        //concatener les chantier depuis les affectations et ceux du pointage
        for (int i = 0; i < list_chantier.size(); i++) {
            l_aff_str.set(i, list_chantier.get(i).getCode());
        }

        //C'est la variable "list_chantier" qui stocke la liste des chantier ( concatenation de l'affectation financière, avec les chantiers déjà pointés)
        this.iteration = list_chantier.size();

        System.out.println("iteration id " + this.iteration);

        if (add_chantier == Boolean.TRUE) {

            for (int m = 0; m < list_chantier_toAdd.size(); m++) {

                System.out.println("iteration " + this.iteration + "chantier " + list_chantier_toAdd.get(m));
                l_aff_str.set(this.iteration, list_chantier_toAdd.get(m));
                iteration++;
            }

        }

        if (this.iteration == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "", Message.NO_CHANTIER_AFFECTED));
        }

        int after_chantier = iteration;

        l_aff_str.set(after_chantier, PointageService.STR_VISITE_SIEGE);
        l_aff_str.set(++after_chantier, PointageService.STR_AUTRE_VISITE);
        l_aff_str.set(++after_chantier, PointageService.STR_CONGE);
        l_aff_str.set(++after_chantier, PointageService.STR_ARRET);

        this.autre = after_chantier - 2;
        this.conge = after_chantier - 0;

        //supprimer les chantiers déjà affecté à la liste des chantiers à ajouter par le menseul
        for (int j = 0; j < this.getL_aff_str().size(); j++) {
            this.deleteChantierFromList(this.getL_aff_str().get(j));
        }

        //on initilize le liste des PointageFront
        initialisePointageFront(pointageDataReturn.getL_pointage());

        //voir si la semaine a déjà été pointé d'une mannière définitive.
        this.setPointedDefenetively(pointageDataReturn.getPointedDefenetively());

        if (this.is_rh.equals(Boolean.TRUE) && this.PointedDefenetively.equals(Boolean.FALSE)) {
            this.display_pointage_array_in_view = false;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "", Message.POINTAGE_NON_EFFECTUE));
            return;
        }

        if (PointedDefenetively == true) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", Message.POINTAGE_EFFECTUE));
        }

        if (val_definitive == false) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "", Message.VALIDATE_POINTAGE));
        }

    }

    /**
     * on doit savoir si c'est le vendredi de la semaine courante pour activer
     * la validation définitive. si c'est le cas on doit activer la validation
     * définitive.
     *
     * @param last_Day_in_current_week
     * @return Boolean
     */
    private Boolean definitiveValidation(Date last_Day_in_current_week) {

        Date date = new Date();

        Calendar cal_lDay = Calendar.getInstance();
        cal_lDay.setTime(last_Day_in_current_week);
        cal_lDay.add(Calendar.DATE, -2); //le vendredi de la dernière semaine

        if (date.compareTo(cal_lDay.getTime()) > 0) {
            return true;
        }
        return false;
    }

    /**
     * Supprime un chantier depuis la liste des chantiers Quand on ajoute un
     * chantier à la liste des affectations, ça ne sert plus à rien de le
     * re-proposer dans la liste des chantier. On parle là de liste des
     * chantiers
     *
     * @param chantier_label
     */
    private void deleteChantierFromList(String chantier_label) {

        for (int i = 0; i < this.chantiers.size(); i++) {
            if (this.getChantiers().get(i).getCode() != null && chantier_label != null) {
                if (this.getChantiers().get(i).getCode().compareTo(chantier_label) == 0) {
                    this.chantiers.remove(i);
                    break;
                }
            }
        }
    }

    /**
     * calaculate the current informations about dates to display in array
     *
     * @param week_number
     * @param year
     */
    private void calculateTheCurrentInformationsAboutDate(Integer week_number) {

        datePointage = new Date();

        System.out.println("current date " + datePointage);

        DateFormat df = new SimpleDateFormat("yyyy");
        DateFormat days = new SimpleDateFormat("dd-MM-yyyy");
        String date_pointage_year = df.format(datePointage);

        Calendar cal = Calendar.getInstance();
        Calendar cal_real_time = Calendar.getInstance();
        cal.setTime(datePointage);
        cal_real_time.setTime(datePointage);

        //set the informations in the selects list
        //list of years and mounths for displaying weeks
//        
        this.mounth_for_affectation = cal.get(Calendar.MONTH);
        System.out.println("week number " + week_number);
        if (week_number != null) {
            System.out.println("week number diff de null");
            this.curent_week_number = week_number;
            cal.set(Calendar.WEEK_OF_YEAR, this.curent_week_number);
            cal.set(Calendar.YEAR, this.year_for_affectation);
//            this.curent_week_number_real_time = cal_real_time.get(Calendar.WEEK_OF_YEAR);
//            this.curent_year_number_real_time = cal_real_time.get(Calendar.YEAR);

        } else {
            System.out.println("week number diff == null");
            //the current week number
            this.year_for_affectation = cal.get(Calendar.YEAR);
            this.curent_week_number = cal.get(Calendar.WEEK_OF_YEAR);

//            System.out.println("real time" + this.curent_week_number_real_time + "--" + this.curent_year_number_real_time);
        }

        this.curent_week_number_real_time = cal_real_time.get(Calendar.WEEK_OF_YEAR);
        this.curent_year_number_real_time = cal_real_time.get(Calendar.YEAR);

        //on affiche des lien vers les 4 dernières semaines seulement pour le premier lancement ou bien si on appuie sur le lien "Cete semaine"
        if (week_number == null || this.last_weeks_numbers == null) {
            Calendar for_display_weeks = Calendar.getInstance();
            for_display_weeks.setTime(cal.getTime());
            this.last_weeks_numbers = new LinkedList<PointageWeek>();
            for (int i = 5; i > 0; i--) {
                for_display_weeks.add(Calendar.WEEK_OF_YEAR, -i);
                this.last_weeks_numbers.add((new PointageWeek(for_display_weeks.get(Calendar.WEEK_OF_YEAR), for_display_weeks.get(Calendar.YEAR))));
                for_display_weeks.setTime(cal.getTime());
            }

        }

        System.out.println("before day start and end" + cal.getTime());
        //the first day in the current week
        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
        this.day_start_of_current_week_obj = cal.getTime();
        this.day_start_of_current_week = days.format(day_start_of_current_week_obj);

        cal.add(Calendar.DATE, 6);
        this.day_end_of_current_week_obj_forValidation = cal.getTime();
        //the last day in the current week
        this.day_end_of_current_week = days.format(this.day_end_of_current_week_obj_forValidation);

        System.out.println("day 1 of current year" + this.day_start_of_current_week);
        System.out.println("day n of current year" + this.day_end_of_current_week);
        System.out.println("---------------------------------------------------------");

        //la liste des années qu'on affiche
        l_year = new LinkedList<Integer>();

        Integer year_start = first_year;

        do {
            l_year.add(year_start);
            year_start++;
        } while (year_start <= Integer.parseInt(date_pointage_year));

    }

    /**
     * retourne un tableau avec les jours de la semaine, tels qu'il seront
     * affiché dans page, le xhtml
     */
    private void getDataForArray_dayOfWeek() {

        Date date_TOday = new Date();

        days_of_week = new LinkedList<String>();
        DateFormat days = new SimpleDateFormat("dd MMMM yyyy", Locale.FRANCE);
        DateFormat days_letters = new SimpleDateFormat("EEEE", Locale.FRANCE);
        Calendar cal = Calendar.getInstance();
        cal.setTime(this.day_start_of_current_week_obj);

        this.day_number_in_desplayed_week = 0;
        do {
            days_of_week.add(days_letters.format(cal.getTime()) + ", " + days.format(cal.getTime()));

            cal.add(Calendar.DATE, 1);
            this.day_end_of_current_week_obj = cal.getTime();
            this.day_number_in_desplayed_week++;
            //on compare la date du calendier avec la date d'aujourd'hui.
        } while (this.day_number_in_desplayed_week < 6 && (cal.getTime().compareTo(date_TOday) <= 0));

    }

    public void addChantier() {

//        this.get_Calendar_forPoint_byWeekNumber(curent_week_number);
        l_aff_str.add(this.iteration, chantierSearch);

//        on supprime le chantier ajouté à la liste des chantier à ajouter comme affectation
        this.deleteChantierFromList(chantierSearch);

        this.autre++;
        this.conge++;
//
//        //En ajoutant un chantier dans la vue, il faut ajouter toutes les valeurs à 1 après, dans les affection qui sont après celle ajouté
        for (int i = 0; i < l_pf.size(); i++) {

            if (l_pf.get(i).getValue() != null) {
                int value_int = Integer.parseInt(l_pf.get(i).getValue());
                if (value_int >= iteration) {
                    value_int++;
                    l_pf.get(i).setValue("" + value_int);
                }
            }
        }

        this.iteration++;
//        calculateTheCurrentInformationsAboutDate(this.curent_week_number);

//        System.out.println("Chantier à afficher");
//        for (int i = 0; i < l_pf.size(); i++) {
//            System.out.println("" + l_pf.get(i));
//        }
    }

    /**
     *
     * enregistre les pointage à partir de la vue vers la base de données en
     * passant biensur par le service
     *
     * @param definitive Boolean
     */
    public void validate(Boolean definitive) {

        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        absenceService = ctx.getBean(AbsenceService.class);
        chantierService = ctx.getBean(ChantierService.class);
        pointageService = ctx.getBean(PointageService.class);

        //this.day_start_of_current_week
        int day = 0;
        Calendar cal = Calendar.getInstance();

        cal.setTime(this.day_start_of_current_week_obj);

        String code_fiche = this.current_connected_mensuel.getId() + "-" + cal.get(Calendar.WEEK_OF_YEAR) + "-" + cal.get(Calendar.YEAR);

        for (int j = 0; j < this.l_pf.size(); j++) {
            day = (int) j / 4;
            int mod = (int) j % 4;

            switch (mod) {
                case 0:
                    createAndAdd_pointage(day, j, PointageService.CRANEAU_8_A_10, this.current_connected_mensuel.getId(), cal, definitive, code_fiche);
                    break;

                case 1:
                    createAndAdd_pointage(day, j, PointageService.CRANEAU_10_A_12, this.current_connected_mensuel.getId(), cal, definitive, code_fiche);
                    break;

                case 2:
                    createAndAdd_pointage(day, j, PointageService.CRANEAU_14_A_16, this.current_connected_mensuel.getId(), cal, definitive, code_fiche);
                    break;

                case 3:
                    createAndAdd_pointage(day, j, PointageService.CRANEAU_16_A_18, this.current_connected_mensuel.getId(), cal, definitive, code_fiche);
                    break;
            }
        }

        if (definitive == true) {
            absenceService.addAbsense(this.l_absense);
        }

        l_absense = null;

        //les messages a afficher à l'utilsateur
        if (definitive == true) {
            this.val_definitive = false;
            this.PointedDefenetively = true;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, Message.STRING_VALIDATION_DEFE, Message.ADD_POINTAGE_SUCCESS));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, Message.STRING_VALIDATION, Message.ADD_UPDATE_POINTAGE));
        }

    }

    /**
     * Créee un Pointage metier depuis un pointage à partir de l'interface front
     * en associant les informations necessaires
     *
     * @param day
     * @param l_pf_iter
     * @param id_creneau
     * @param id_mensuel
     * @param cal
     */
    private void createAndAdd_pointage(int day, int l_pf_iter, int id_creneau, int id_mensuel, Calendar cal, Boolean definitive, String code_fiche) {

        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        absenceService = ctx.getBean(AbsenceService.class);
        chantierService = ctx.getBean(ChantierService.class);

        Pointage _p = new Pointage();
        Calendar calPoin = Calendar.getInstance();
        calPoin.setTime(cal.getTime());
        calPoin.add(Calendar.DATE, day);

        _p.setDay(calPoin.getTime());
        Crenau _crenau = new Crenau(id_creneau);

        //définition des crénaux pour ajouter les pointages
        switch (id_creneau) {
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

        _p.setCodeFiche(code_fiche);
        _p.setCrenau(_crenau);
        _p.setDefinitive(definitive == true ? 1 : 0);
        _p.setChantier(null);
        _p.setMensuel(new Mensuel(id_mensuel));

        if (l_pf.get(l_pf_iter).getValue() != null && l_pf.get(l_pf_iter).getValue().compareTo("") != 0) {

            int int_value;

            try {
                int_value = Integer.parseInt(l_pf.get(l_pf_iter).getValue());
                if (int_value < this.iteration) {
//            On traite les chantier d'affectation
                    //get chantier by name
                    Chantier chantier = chantierService.findByName(l_aff_str.get(int_value));
                    _p.setChantier(chantier);

                } else {

//            On traite les autres jours, congé , arret ....
                    _p.setAutre(l_aff_str.get(int_value));
                    _p.setAutreType(l_pf.get(l_pf_iter).getSubValue());

                    if (definitive == true && (_p.getAutre().compareTo(PointageService.STR_CONGE) == 0 || _p.getAutre().compareTo(PointageService.STR_ARRET) == 0)) {
                        l_absense = absenceService.AddAbsenceIntoList(l_absense, _p);
                    }
                }
                pointageService.save(_p);

            } catch (NumberFormatException e) {

            }

        } else if (definitive == true) {

            if (calPoin.get(Calendar.DAY_OF_WEEK) < 7) {
                System.out.println("DAY FOR ABSCENCE " + calPoin.get(Calendar.DAY_OF_WEEK));
                _p.setAutre("absence");
                l_absense = absenceService.AddAbsenceIntoList(l_absense, _p);
            }

        }

    }

    /**
     * affiche les popup quand on click soit sur un arret ou un autre
     * deplacement.
     *
     * @param i
     * @param j
     */
    public void changeLis(int i, int j) {

        this.valuefOther = "";

        this.positionInAffectationList = i;
        this.ValueInAffectationList = j;

        this.l_pf.get(positionInAffectationList).setSubValue("");
        this.l_pf.get(positionInAffectationList).setSubValueAb("");

        if (this.autre == j) {
            RequestContext rc = RequestContext.getCurrentInstance();
            rc.execute("PF('dlg1').show()");
        } else if (this.conge == j) {
            RequestContext rc = RequestContext.getCurrentInstance();
            rc.execute("PF('dlg2').show()");
        }
    }

    /**
     * ajoute le type si on a cliqué sur un arret ou une autre visite.
     */
    public void addOtherInPointageList() {
        this.l_pf.get(positionInAffectationList).setSubValue(valuefOther);
        valuefOther = "";

    }

    /**
     * concatène la liste des pointage depuis les affectation et la liste des
     * chantier depuis les pointage
     *
     * @param l_c_affectation
     * @param l_c_pointage
     * @return
     */
    private List<Chantier> concat_ListChantierByAffection_ListChantierByPointage(List<SousAffectation> l_c_affectation, List<Chantier> l_c_pointage) {
        List<Chantier> l_c = new LinkedList<Chantier>();

        //on ajoute les chantiers des affectation dans une list de chantier
        if (l_c_affectation != null) {
            for (int i = 0; i < l_c_affectation.size(); i++) {
                l_c.add(l_c_affectation.get(i).getChantier());
            }

        }

        for (int i = 0; i < l_c_pointage.size(); i++) {

            Boolean found = false;
            for (int j = 0; j < l_c.size(); j++) {

                if (l_c.get(j).equals(l_c_pointage.get(i))) {
                    found = true;
                    break;
                }

            }

            if (found == false) {
                l_c.add(l_c_pointage.get(i));
            }
        }

        return l_c;
    }

    /**
     * récupérer les pointages que le mensuel à déjà effectué, les mettre dans
     * la liste des pointage des pointages à affciher.
     *
     * @param l_p
     */
    private void initialisePointageFront(List<Pointage> l_p) {

        //on initilize le liste des PointageFront
        this.l_pf = new LinkedList<PointageFront>();
        Date day1 = this.day_start_of_current_week_obj;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar cal = Calendar.getInstance();
        cal.setTime(day1);

        cal.add(Calendar.DATE, -1);

        for (int i = 0; i < this.days_of_week.size(); i++) {

            cal.add(Calendar.DATE, 1);
            Date date_current = cal.getTime();

            //4 crenaux dans chaque jour //les jours de la semaines commencent par 1
            for (int j = 0; j < 4; j++) {
                Boolean found = false;
                if (l_p.size() > 0) {
                    for (int k = 0; k < l_p.size(); k++) {

                        if (sdf.format(l_p.get(k).getDay()).equals(sdf.format(date_current)) && l_p.get(k).getCrenau().getId() == j + 1) {

                            //get Position of the affection in the list
                            if (l_p.get(k).getChantier() != null) {
                                int value = l_aff_str.indexOf(l_p.get(k).getChantier().getCode());
                                if (value >= 0) {
                                    this.l_pf.add(new PointageFront("" + value));
                                } else {
                                    this.l_pf.add(new PointageFront());
                                }

                            } else if (l_p.get(k).getAutre() != null) {
                                int value = l_aff_str.indexOf(l_p.get(k).getAutre());
                                if (value >= 0) {
                                    this.l_pf.add(new PointageFront("" + value, l_p.get(k).getAutreType()));
                                } else {
                                    this.l_pf.add(new PointageFront());
                                }
                            }

                            l_p.remove(k);

                            found = true;
                            break;
                        }
                    }
                }

                if (found == false) {
                    this.l_pf.add(new PointageFront());
                }
            }
        }
    }

    /**
     * retourn la dernière semaine pointé on bloque le pointage si les dernières
     * semaines ne sont pas pointées.
     *
     * On verifie par rapport à la date de création du mensuel et à la première
     * semaine du pointage dans le système
     *
     * @param mensuel_id
     * @return
     */
    private Date lastWeekPointed() throws ParseException {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        Date _dToReturn;

        //date du premier jour de pointage dans le systeme
        Date first_day_for_pointage = Constante.get_first_day_in_pointage_forAllApplication();

        _dToReturn = first_day_for_pointage;

        //date de la creation de l'utilisateur
        Date creation_date = first_day_for_pointage;
        Date return_date = this.current_connected_mensuel.getDateCreation();
        if (return_date != null) {
            creation_date = return_date;
        }

        //date du dernier pointage effectué par l'utilisateur
        Date l_date_pointed = first_day_for_pointage;
        return_date = this.pointageService.get_LastWeek_Pointed(current_connected_mensuel.getId());
        if (return_date != null) {
            l_date_pointed = return_date;
        }

        if ((first_day_for_pointage.compareTo(creation_date) >= 0) && (first_day_for_pointage.compareTo(l_date_pointed) >= 0)) {
            return getNextWeek(first_day_for_pointage);

        }

        if ((creation_date.compareTo(first_day_for_pointage) >= 0) && (creation_date.compareTo(l_date_pointed) >= 0)) {
            return getNextWeek(creation_date);
        }

        if ((l_date_pointed.compareTo(first_day_for_pointage) >= 0) && (l_date_pointed.compareTo(creation_date) >= 0)) {
            return getNextWeek(l_date_pointed);
        }
        return getNextWeek(first_day_for_pointage);

    }

    private static Date getNextWeek(Date date) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        cal.add(Calendar.WEEK_OF_YEAR, 1);
        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());

        return cal.getTime();

    }

    /**
     * on modifie les semaines affiché par en fonction du mois choisit
     *
     * @return List<Integer>
     */
    public void weeksOfMonth() {

        last_weeks_numbers = new LinkedList<PointageWeek>();

        Calendar cal = Calendar.getInstance();

        cal.set(this.year_for_affectation, this.mounth_for_affectation, 1);

        int firt_week_to_dispolay = cal.get(Calendar.WEEK_OF_YEAR) - 1; // on commence par une semaine avant le mois courant

        int i = 0;
        last_weeks_numbers.add(new PointageWeek(cal.get(Calendar.WEEK_OF_YEAR), getYear_ofWeak(cal)));
        while (i < PointageMb.count_week_to_display_by_couth) {
            cal.add(Calendar.WEEK_OF_YEAR, 1);
            last_weeks_numbers.add(new PointageWeek(cal.get(Calendar.WEEK_OF_YEAR), getYear_ofWeak(cal)));
            i++;
        }

        //on termine par une semaine après le mois courant.
//        return this.last_weeks_numbers;
    }

    /**
     * Cette methode est utilisé pour avoir l'année de la dernère semaine dans
     * l'année.
     *
     * Prenons l'exemple de la dernière semaine de l'année 2015, c'est la
     * semaine 53 elle commence à partir du lundi 28 Dec 2015 et se termine le
     * dimanche 03 Janvier 2016. Donc par defaut la methode getCalendar de J ava
     * retourne 2016 et pas 2015.
     *
     * Ici on doit récupérer 2015 comme année de cette semaine. car, dans le cas
     * contraire quand on voudrai avoir les pointages de la de cette semaine
     * elle va nous donner la semaine 53 de l'année 2016.
     *
     * @param cal
     * @return
     */
    private Integer getYear_ofWeak(Calendar cal) {

        Calendar first_day_of_week = Calendar.getInstance();
        first_day_of_week.setTime(cal.getTime());
        first_day_of_week.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());

        Calendar last_day_of_week = Calendar.getInstance();
        last_day_of_week.setTime(first_day_of_week.getTime());
        last_day_of_week.add(Calendar.DATE, 6);

        if (first_day_of_week.get(Calendar.YEAR) < last_day_of_week.get(Calendar.YEAR)) {
            return first_day_of_week.get(Calendar.YEAR);
        }

        return cal.get(Calendar.YEAR);
    }

    /**
     * ************************************************************************************************
     * ************************************************************************************************
     * ************************************************************************************************
     * PARTIE RH
     * ************************************************************************************************
     * ************************************************************************************************
     * ************************************************************************************************
     */
    /**
     * Methode qui renvoi la liste des mensuel qui ont pointé au moins une seul
     * fois dans un chantier le chantier vient de l'interface
     *
     * @param selected_chantier
     */
    public void get_mensuel_by_Chantier_pointage(Integer selected_chantier) {
        this.mensuels_l = pointageService.distinct_mensuels_by_chantier(selected_chantier);
    }

    /**
     * Cette la methode de recherche des fiches de pointage
     *
     * @param mensuel_id
     * @param fiche
     */
    public void get_pointageTableby_codeChantier_and_mensuel(Integer mensuel_id, String fiche) throws ParseException {

        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        mensuelService = ctx.getBean(MensuelService.class);

        if (fiche.compareTo("-1") != 0) {
            String[] parts = fiche.split("-");
            if (parts.length == 3) {

                int mensuel_id_ = Integer.valueOf(parts[0]);
                this.week_number = Integer.valueOf(parts[1]);
                this.year_for_affectation = Integer.valueOf(parts[2]);

                this.current_connected_mensuel = mensuelService.findById("" + mensuel_id_);
                this.get_Calendar_forPoint_byWeekNumber(week_number, this.year_for_affectation, false);

            }
        } else if (mensuel_id != null) {
            this.current_connected_mensuel = mensuelService.findById("" + mensuel_id);
            if (this.current_connected_mensuel instanceof Mensuel) {
                this.get_Calendar_forPoint_byWeekNumber(null, null, false);
            }

        }

    }

    /**
     *
     * selectionne les code des fiches de pointage d'un mensuel donnée en
     * paramètre dans un chantier donnée aussi en paramètre
     *
     * @param selected_chantier
     * @param selected_mensuel
     */
    public void get_fiches_Bymensuel_inChantier(Integer selected_chantier, int selected_mensuel) {

        this.setMensuel_forSearch(selected_mensuel);
        this.codes_fiche_mensuel = pointageService.get_fiches_Bymensuel_inChantier(selected_chantier, selected_mensuel);

        System.out.println("Code fiches");
        if (this.codes_fiche_mensuel != null) {
            System.out.println("size code fiches : " + this.codes_fiche_mensuel.size());
        }

        this.current_connected_mensuel = new Mensuel(selected_mensuel);
    }

    /**
     * ************************************************************************************************
     * ************************************************************************************************
     * ************************************************************************************************
     * GETTERS AND SETTERS END
     * ************************************************************************************************
     * ************************************************************************************************
     * ************************************************************************************************
     */
    public Integer getMounth_for_affectation() {
        return mounth_for_affectation;
    }

    public void setMounth_for_affectation(Integer mounth_for_affectation) {
        this.mounth_for_affectation = mounth_for_affectation;
    }

    public Mensuel getCurrent_connected_mensuel() {
        return current_connected_mensuel;
    }

    public Integer getWeek_number() {
        return week_number;
    }

    public void setWeek_number(Integer week_number) {
        this.week_number = week_number;
    }

    public void setCurrent_connected_mensuel(Mensuel current_connected_mensuel) {
        this.current_connected_mensuel = current_connected_mensuel;
    }

    public AffectationService getAffectationService() {
        return affectationService;
    }

    public void setAffectationService(AffectationService affectationService) {
        this.affectationService = affectationService;
    }

    public Date getDay_start_of_current_week_obj() {
        return day_start_of_current_week_obj;
    }

    public int getCurent_week_number_real_time() {
        return curent_week_number_real_time;
    }

    public void setCurent_week_number_real_time(int curent_week_number_real_time) {
        this.curent_week_number_real_time = curent_week_number_real_time;
    }

    public Boolean getDisplay_pointage_array_in_view() {
        return display_pointage_array_in_view;
    }

    public int getVarStatus1() {
        return varStatus1;
    }

    public void setVarStatus1(int varStatus1) {
        this.varStatus1 = varStatus1;
    }

    public void setDisplay_pointage_array_in_view(Boolean display_pointage_array_in_view) {
        this.display_pointage_array_in_view = display_pointage_array_in_view;
    }

    public List<String> getL_aff_str() {
        return l_aff_str;
    }

    public MensuelService getMensuelService() {
        return mensuelService;
    }

    public void setMensuelService(MensuelService mensuelService) {
        this.mensuelService = mensuelService;
    }

    public void setL_aff_str(List<String> l_aff_str) {
        this.l_aff_str = l_aff_str;
    }

    public List<Chantier> getChantiers() {
        return chantiers;
    }

    public void setChantiers(List<Chantier> chantiers) {
        this.chantiers = chantiers;
    }

    public int getCurent_year_number_real_time() {
        return curent_year_number_real_time;
    }

    public void setCurent_year_number_real_time(int curent_year_number_real_time) {
        this.curent_year_number_real_time = curent_year_number_real_time;
    }

    public void setDay_start_of_current_week_obj(Date day_start_of_current_week_obj) {
        this.day_start_of_current_week_obj = day_start_of_current_week_obj;
    }

    public List<Integer> getL_year() {
        return l_year;
    }

    public void setL_year(List<Integer> l_year) {
        this.l_year = l_year;
    }

    public List<String> getDays_of_week() {
        return days_of_week;
    }

    public List<SousAffectation> getL_aff() {
        return l_aff;
    }

    public void setL_aff(List<SousAffectation> l_aff) {
        this.l_aff = l_aff;
    }

    public Boolean getPointadLast() {
        return pointadLast;
    }

    public void setPointadLast(Boolean pointadLast) {
        this.pointadLast = pointadLast;
    }

    public Date getLast_week_pointed() {
        return last_week_pointed;
    }

    public void setLast_week_pointed(Date last_week_pointed) {
        this.last_week_pointed = last_week_pointed;
    }

    public List<PointageFront> getL_pf() {
        return l_pf;
    }

    public void setL_pf(List<PointageFront> l_pf) {
        this.l_pf = l_pf;
    }

    public void setDays_of_week(List<String> days_of_week) {
        this.days_of_week = days_of_week;
    }

    public int getCurent_week_number() {
        return curent_week_number;
    }

    public void setCurent_week_number(int curent_week_number) {
        this.curent_week_number = curent_week_number;
    }

    public String getDay_start_of_current_week() {
        return day_start_of_current_week;
    }

    public void setDay_start_of_current_week(String day_start_of_current_week) {
        this.day_start_of_current_week = day_start_of_current_week;
    }

    public String getDay_end_of_current_week() {
        return day_end_of_current_week;
    }

    public void setDay_end_of_current_week(String day_end_of_current_week) {
        this.day_end_of_current_week = day_end_of_current_week;
    }

    public Date getDatePointage() {
        return datePointage;
    }

    public void setDatePointage(Date datePointage) {
        this.datePointage = datePointage;
    }

    public Integer getYear_for_affectation() {
        return year_for_affectation;
    }

    public void setYear_for_affectation(Integer year_for_affectation) {
        this.year_for_affectation = year_for_affectation;
    }

    public ChantierService getChantierService() {
        return chantierService;
    }

    public void setChantierService(ChantierService chantierService) {
        this.chantierService = chantierService;
    }

    public List<String> getList_chantier_toAdd() {
        return list_chantier_toAdd;
    }

    public void setList_chantier_toAdd(List<String> list_chantier_toAdd) {
        this.list_chantier_toAdd = list_chantier_toAdd;
    }

    public String getChantierSearch() {
        return chantierSearch;
    }

    public void setChantierSearch(String chantierSearch) {
        this.chantierSearch = chantierSearch;
    }

    public int getIteration() {
        return iteration;
    }

    public void setIteration(int iteration) {
        this.iteration = iteration;
    }

    public PointageService getPointageService() {
        return pointageService;
    }

    public void setPointageService(PointageService pointageService) {
        this.pointageService = pointageService;
    }

    public Boolean getPointedDefenetively() {
        return PointedDefenetively;
    }

    public void setPointedDefenetively(Boolean PointedDefenetively) {
        this.PointedDefenetively = PointedDefenetively;
    }

    public int getAutre() {
        return autre;
    }

    public void setAutre(int autre) {
        this.autre = autre;
    }

    public int getConge() {
        return conge;
    }

    public void setConge(int conge) {
        this.conge = conge;
    }

    public int getPositionInAffectationList() {
        return positionInAffectationList;
    }

    public void setPositionInAffectationList(int positionInAffectationList) {
        this.positionInAffectationList = positionInAffectationList;
    }

    public int getValueInAffectationList() {
        return ValueInAffectationList;
    }

    public void setValueInAffectationList(int ValueInAffectationList) {
        this.ValueInAffectationList = ValueInAffectationList;
    }

    public String getValuefOther() {
        return valuefOther;
    }

    public void setValuefOther(String valuefOther) {
        this.valuefOther = valuefOther;
    }

    public int getDay_number_in_desplayed_week() {
        return day_number_in_desplayed_week;
    }

    public void setDay_number_in_desplayed_week(int day_number_in_desplayed_week) {
        this.day_number_in_desplayed_week = day_number_in_desplayed_week;
    }

    public Date getDay_end_of_current_week_obj() {
        return day_end_of_current_week_obj;
    }

    public UtilisateurService getUtilisateurService() {
        return utilisateurService;
    }

    public void setUtilisateurService(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    public void setDay_end_of_current_week_obj(Date day_end_of_current_week_obj) {
        this.day_end_of_current_week_obj = day_end_of_current_week_obj;
    }

    public List<PointageWeek> getLast_weeks_numbers() {
        return last_weeks_numbers;
    }

    public void setLast_weeks_numbers(List<PointageWeek> last_weeks_numbers) {
        this.last_weeks_numbers = last_weeks_numbers;
    }

    public AbsenceService getAbsenceService() {
        return absenceService;
    }

    public void setAbsenceService(AbsenceService absenceService) {
        this.absenceService = absenceService;
    }

    public List<Pointage> getL_absense() {
        return l_absense;
    }

    public void setL_absense(List<Pointage> l_absense) {
        this.l_absense = l_absense;
    }

    public Boolean getVal_definitive() {
        return val_definitive;
    }

    public void setVal_definitive(Boolean val_definitive) {
        this.val_definitive = val_definitive;
    }

    public Date getDay_end_of_current_week_obj_forValidation() {
        return day_end_of_current_week_obj_forValidation;
    }

    public void setDay_end_of_current_week_obj_forValidation(Date day_end_of_current_week_obj_forValidation) {
        this.day_end_of_current_week_obj_forValidation = day_end_of_current_week_obj_forValidation;
    }

    public Boolean getIs_rh() {
        return is_rh;
    }

    public void setIs_rh(Boolean is_rh) {
        this.is_rh = is_rh;
    }

    public int getMensuel_forSearch() {
        return mensuel_forSearch;
    }

    public void setMensuel_forSearch(int mensuel_forSearch) {
        this.mensuel_forSearch = mensuel_forSearch;
    }

    public List<String> getCodes_fiche_mensuel() {
        return codes_fiche_mensuel;
    }

    public void setCodes_fiche_mensuel(List<String> codes_fiche_mensuel) {
        this.codes_fiche_mensuel = codes_fiche_mensuel;
    }

    public String getSelected_code_fiche() {
        return selected_code_fiche;
    }

    public void setSelected_code_fiche(String selected_code_fiche) {
        this.selected_code_fiche = selected_code_fiche;
    }

    public List<Mensuel> getMensuels_l() {
        return mensuels_l;
    }

    public void setMensuels_l(List<Mensuel> mensuels_l) {
        this.mensuels_l = mensuels_l;
    }

    public Integer getChantierSearch_rh() {
        return chantierSearch_rh;
    }

    public void setChantierSearch_rh(Integer chantierSearch_rh) {
        this.chantierSearch_rh = chantierSearch_rh;
    }

    public Boolean getDisplay_weeks() {
        return display_weeks;
    }

    public void setDisplay_weeks(Boolean display_weeks) {
        this.display_weeks = display_weeks;
    }

}
