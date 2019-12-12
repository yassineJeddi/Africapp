/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.mb.services;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import ma.bservices.beans.EtatHeuresSupplementaires;
import ma.bservices.beans.HeuresSupplementaires;
import ma.bservices.beans.Permission;
import ma.bservices.beans.Presence;
import ma.bservices.beans.Salarie;
import ma.bservices.beans.Utilisateur;
import ma.bservices.paginate.HeureSupPagination;
import ma.bservices.paginate.SalariePagination;
import ma.bservices.services.ChantierService;
import ma.bservices.services.HeuresSupplementairesService;
import ma.bservices.services.ParametrageService;
import ma.bservices.services.PresenceService;
import ma.bservices.services.SalarieService;
import ma.bservices.tgcc.authentification.Authentification;
import ma.bservices.tgcc.service.Engin.UtilisateurService;
import org.primefaces.context.RequestContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 *
 * @author Mahdi
 */
@Component
@ManagedBean(name = "heuresuppMb")
@ViewScoped
public class HeuresSupplementairesMb implements Serializable {

    @ManagedProperty(value = "#{heuresSupplementairesService}")
    private HeuresSupplementairesService heuresSupplementairesService;

    @ManagedProperty(value = "#{chantierServiceEvol}")
    private ChantierService chantierService;

    @ManagedProperty(value = "#{presenceService}")
    private PresenceService presenceService;

    @ManagedProperty(value = "#{salarieService}")
    private SalarieService salarieService;

    @ManagedProperty(value = "#{parametrageService}")
    private ParametrageService parametrageService;

    @ManagedProperty(value = "#{utilisateurService}")
    private UtilisateurService utilisateurService;

    private List<HeuresSupplementaires> listHS = new ArrayList<>();

    private List<HeuresSupplementaires> listHSFilter = new ArrayList<>();

    private int heureDiff;

    private HeuresSupplementaires heureSupp = new HeuresSupplementaires();
    //variable to add HS
    private String heureEntree, heureSortie, minEntree, minSortie;
    //var to update Hs
    private String heureDebutMod, heureFinMod, minDebutMod, minFinMod;

    private Integer codeChantier;

    //nombre de pages après traitement, c'est le nombre de page qu'on affiche dans la vue
    private Integer i;

    private String matricule;

    private String cin, cnss, matriculeSearch, cinSearch;

    private Integer etatHS;

    private EtatHeuresSupplementaires etat = new EtatHeuresSupplementaires();

    private Integer intChantier, idEtatHs;

    private Date currentDate, rechercheDate;

    private Utilisateur user_obj;

    private Boolean isAdmin = Boolean.FALSE;

    private Integer page;

    private HeuresSupplementaires hs = new HeuresSupplementaires();

    private Boolean prev, next, last, first, pageId;

    private Boolean addButtonValidate, addButtonMatri, addButtonDate, addButtonChantier;

    private Integer var;

    private String heureEntreeChantier, minEntreeChantier;
    private String heureSortieChantier, minSortieChantier;

    public String getCinSearch() {
        return cinSearch;
    }

    public PresenceService getPresenceService() {
        return presenceService;
    }

    public void setPresenceService(PresenceService presenceService) {
        this.presenceService = presenceService;
    }

    public void setCinSearch(String cinSearch) {
        this.cinSearch = cinSearch;
    }

    public Date getRechercheDate() {
        return rechercheDate;
    }

    public void setRechercheDate(Date rechercheDate) {
        this.rechercheDate = rechercheDate;
    }

    public String getMatriculeSearch() {
        return matriculeSearch;
    }

    public void setMatriculeSearch(String matriculeSearch) {
        this.matriculeSearch = matriculeSearch;
    }

    public Integer getIdEtatHs() {
        return idEtatHs;
    }

    public void setIdEtatHs(Integer idEtatHs) {
        this.idEtatHs = idEtatHs;
    }

    public HeuresSupplementaires getHs() {
        return hs;
    }

    public void setHs(HeuresSupplementaires hs) {
        this.hs = hs;
    }

    public String getCnss() {
        return cnss;
    }

    public void setCnss(String cnss) {
        this.cnss = cnss;
    }

    public Integer getIntChantier() {
        return intChantier;
    }

    public Date getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(Date currentDate) {
        this.currentDate = currentDate;
    }

    public void setIntChantier(Integer intChantier) {
        this.intChantier = intChantier;
    }

    public EtatHeuresSupplementaires getEtat() {
        return etat;
    }

    public void setEtat(EtatHeuresSupplementaires etat) {
        this.etat = etat;
    }

    public Integer getEtatHS() {
        return etatHS;
    }

    public void setEtatHS(Integer etatHS) {
        this.etatHS = etatHS;
    }

    public ParametrageService getParametrageService() {
        return parametrageService;
    }

    public void setParametrageService(ParametrageService parametrageService) {
        this.parametrageService = parametrageService;
    }

    public List<HeuresSupplementaires> getListHSFilter() {
        return listHSFilter;
    }

    public void setListHSFilter(List<HeuresSupplementaires> listHSFilter) {
        this.listHSFilter = listHSFilter;
    }

    public SalarieService getSalarieService() {
        return salarieService;
    }

    public void setSalarieService(SalarieService salarieService) {
        this.salarieService = salarieService;
    }

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public ChantierService getChantierService() {
        return chantierService;
    }

    public void setChantierService(ChantierService chantierService) {
        this.chantierService = chantierService;
    }

    public Integer getCodeChantier() {
        return codeChantier;
    }

    public void setCodeChantier(Integer codeChantier) {
        this.codeChantier = codeChantier;
    }

    public String getHeureEntree() {
        return heureEntree;
    }

    public void setHeureEntree(String heureEntree) {
        this.heureEntree = heureEntree;
    }

    public String getHeureSortie() {
        return heureSortie;
    }

    public void setHeureSortie(String heureSortie) {
        this.heureSortie = heureSortie;
    }

    public String getMinEntree() {
        return minEntree;
    }

    public void setMinEntree(String minEntree) {
        this.minEntree = minEntree;
    }

    public String getMinSortie() {
        return minSortie;
    }

    public void setMinSortie(String minSortie) {
        this.minSortie = minSortie;
    }

    public HeuresSupplementaires getHeureSupp() {
        return heureSupp;
    }

    public void setHeureSupp(HeuresSupplementaires heureSupp) {
        this.heureSupp = heureSupp;
    }

    public int getHeureDiff() {
        return heureDiff;
    }

    public void setHeureDiff(int heureDiff) {
        this.heureDiff = heureDiff;
    }

    public List<HeuresSupplementaires> getListHS() {
        return listHS;
    }

    public Integer getI() {
        return i;
    }

    public void setI(Integer i) {
        this.i = i;
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

    public void setVar(Integer var) {
        this.var = var;
    }

    public void setListHS(List<HeuresSupplementaires> listHS) {
        this.listHS = listHS;
    }

    public HeuresSupplementairesService getHeuresSupplementairesService() {
        return heuresSupplementairesService;
    }

    public void setHeuresSupplementairesService(HeuresSupplementairesService heuresSupplementairesService) {
        this.heuresSupplementairesService = heuresSupplementairesService;
    }

    public HeuresSupplementairesMb() {
    }

    public String getHeureDebutMod() {
        return heureDebutMod;
    }

    public void setHeureDebutMod(String heureDebutMod) {
        this.heureDebutMod = heureDebutMod;
    }

    public String getHeureFinMod() {
        return heureFinMod;
    }

    public void setHeureFinMod(String heureFinMod) {
        this.heureFinMod = heureFinMod;
    }

    public String getMinDebutMod() {
        return minDebutMod;
    }

    public void setMinDebutMod(String minDebutMod) {
        this.minDebutMod = minDebutMod;
    }

    public String getMinFinMod() {
        return minFinMod;
    }

    public void setMinFinMod(String minFinMod) {
        this.minFinMod = minFinMod;
    }

    public UtilisateurService getUtilisateurService() {
        return utilisateurService;
    }

    public void setUtilisateurService(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    public Boolean getAddButtonValidate() {
        return addButtonValidate;
    }

    public void setAddButtonValidate(Boolean addButtonValidate) {
        this.addButtonValidate = addButtonValidate;
    }

    public Boolean getAddButtonMatri() {
        return addButtonMatri;
    }

    public void setAddButtonMatri(Boolean addButtonMatri) {
        this.addButtonMatri = addButtonMatri;
    }

    public Boolean getAddButtonDate() {
        return addButtonDate;
    }

    public void setAddButtonDate(Boolean addButtonDate) {
        this.addButtonDate = addButtonDate;
    }

    public Boolean getAddButtonChantier() {
        return addButtonChantier;
    }

    public void setAddButtonChantier(Boolean addButtonChantier) {
        this.addButtonChantier = addButtonChantier;
    }

    @PostConstruct
    public void init() {

        heuresSupplementairesService = Module.ctx.getBean(HeuresSupplementairesService.class);
        chantierService = Module.ctx.getBean(ChantierService.class);
        salarieService = Module.ctx.getBean(SalarieService.class);
        parametrageService = Module.ctx.getBean(ParametrageService.class);
        presenceService = Module.ctx.getBean(PresenceService.class);
        utilisateurService = Module.ctx.getBean(UtilisateurService.class);
        //on charge les premiers 10 enregistrement

        addButtonChantier = addButtonDate = addButtonMatri = addButtonValidate = Boolean.FALSE;

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String user = auth.getPrincipal().toString();

        for (GrantedAuthority grantedAuthority : auth.getAuthorities()) {
            if (grantedAuthority.getAuthority().equals("admin")) {
                isAdmin = true;
                break;
            }
        }

        //pour les admin on a pas besoin de l'utilisateur, on envoi null dans les param pour avoir toute la liste des chantiers
        user_obj = isAdmin == false ? utilisateurService.getUsersByLogin(user) : null;

        matriculeSearch = "";
        cinSearch = "";
        cnss = "";

        page = 1;
        List<Integer> lchantiers = HeureSupPagination.getChantierForSeach(intChantier, user_obj);

        i = heuresSupplementairesService.nombreHS("", "", "", null, lchantiers, null);
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

    public void ajouterHS() {

        if (!Module.checkDate(null, new Date(), currentDate)) {
            Module.message(3, "la date de ce champ ne peut pas être postérieure au date d'aujourd'hui ", "");
            return;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String chaineDateHS = sdf.format(currentDate);

        if (heureEntree.length() == 1) {
            heureEntree = "0" + heureEntree;
        }

        if (minEntree.length() == 1) {
            minEntree = "0" + minEntree;
        }

        if (heureSortie.length() == 1) {
            heureSortie = "0" + heureSortie;
        }

        if (minSortie.length() == 1) {
            minSortie = "0" + minSortie;
        }
        Salarie salarie = new Salarie();
        if (cin != null && !"".equals(cin)) {
            salarie = salarieService.getSalarieByCin(cin);
        }
        if (!"".equals(matricule)) {
            salarie = salarieService.getSalarieByMatricule(matricule);
        }
        if (salarie.getId() == null) {
            Module.message(3, "Salarie avec ce Matricule ou ce Cin n'existe pas", "");
            return;
        }
        if (!salarieService.isActif(salarie.getMatricule())) {
            Module.message(2, "ce Salarie n'est pas actif", "");
            return;
        }
        boolean resultat = chantierService.verifierAffectationSalarieChantier(codeChantier, salarie.getId());

        if (!resultat) {
            Module.message(2, "Le matricule " + matricule + " n'est pas affecté au chantier " + chantierService.getChantierById(codeChantier).getCode(), "");
            return;
        }
        if (heuresSupplementairesService.heuresSupExist(salarie.getId(), chaineDateHS, heureEntree + ":" + minEntree)) {

            Module.message(2, "Des heures supp existent pour ce salarié, matricule " + matricule + " dans cette date ", "");
            return;
        }

        if ((Integer.valueOf(heureEntree) > Integer.valueOf(heureSortie)) || ((Integer.valueOf(heureEntree) == Integer.valueOf(heureSortie)) && Integer.valueOf(minEntree) >= Integer.valueOf(minSortie))) {
            Module.message(2, "Les dates d'entrée et de sortie sont erronées", "");
            return;
        }

        /* if (((Integer.valueOf(heureSortieChantier) > Integer.valueOf(heureEntree)) || ((Integer.valueOf(heureSortieChantier) == Integer.valueOf(heureEntree)) && Integer.valueOf(minSortieChantier) >= Integer.valueOf(minEntree))) && ((Integer.valueOf(heureSortie) > Integer.valueOf(heureSortieChantier)) || ((Integer.valueOf(heureSortie) == Integer.valueOf(heureSortieChantier)) && Integer.valueOf(minSortie) >= Integer.valueOf(heureSortieChantier))) ) {
            Module.message(2, "Vous ne pouvez pas saisir des HS p la date de sortie de chantier ", "");
            return; 
        }*/
        Chantier c = chantierService.getChantier(codeChantier);

        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        Authentification authentification = (Authentification) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "authentification");
        EtatHeuresSupplementaires eHS;
        eHS = parametrageService.getEtatHS(EtatHeuresSupplementaires.ETAT_EN_COURS);

        // System.out.println("TESTING PRESENCE RETURNED : " + presenceService.dejaPointe(salarie.getId(), new Date(chaineDateHS)));
        heureSupp.setEtat(eHS);
        heureSupp.setChantier(c);
        heureSupp.setSalarie(salarie);
        heureSupp.setHeureDebut(heureEntree + ":" + minEntree);
        heureSupp.setHeureFin(heureSortie + ":" + minSortie);
        heureSupp.setDate(new Date(chaineDateHS));

        Long dateTimeDebut = new Date(chaineDateHS + " " + heureSupp.getHeureDebut()).getTime();
        Long dateTimeFin = new Date(chaineDateHS + " " + heureSupp.getHeureFin()).getTime();

        heureSupp.setLongDateTimeDebutHS(dateTimeDebut);
        heureSupp.setLongDateTimeFinHS(dateTimeFin);
        heureSupp.setCreePar(authentification.get_connected_user().getLogin());

        String nombreHeuresMinutesHS = presenceService.nombreHeuresMinutesPresence(heureEntree + ":" + minEntree, heureSortie + ":" + minSortie);
        if (!"".equals(nombreHeuresMinutesHS)) {
            String[] tab = nombreHeuresMinutesHS.split(":");
            heureSupp.setNombreHeures(Integer.parseInt(tab[0]));
            heureSupp.setNombreMinutes(Integer.parseInt(tab[1]));
            Integer idHS = heuresSupplementairesService.ajouterHS(heureSupp);
            if (idHS != 0) {
                Module.message(0, "Heure Supplémentaire ajoutée avec succès", "Heure Supplémentaire ajoutée avec succès");
                etatHS = (etatHS != null && etatHS == 0) ? null : etatHS;
                intChantier = (intChantier != null && intChantier == 0) ? null : intChantier;
                current();
                i = heuresSupplementairesService.nombreHS("", "", "", null, null, null);
                var = (i % 10 == 0) ? i / 10 : i / 10 + 1;

                heureSupp = new HeuresSupplementaires();
//                minSortie = "";
//                minEntree = "";
//                heureEntree = "";
//                heureSortie = "";
//                codeChantier = null;
                matricule = "";
                cin = "";

                //fermer la popup
//                RequestContext rc = RequestContext.getCurrentInstance();
//                rc.execute("PF('add').hide()");
            } else {
                Module.message(3, "Echec de création des heures supp", "");
            }
        }
    }

    public void rechercheHS() throws ParseException {

        if (user_obj != null) {
            List<Permission> lp = user_obj.getPermissions();

        }

        etatHS = (etatHS != null && etatHS == 0) ? null : etatHS;
        intChantier = (intChantier != null && intChantier == 0) ? null : intChantier;
        onFirst();

        page = 1;

        List<Integer> lchantiers = HeureSupPagination.getChantierForSeach(intChantier, user_obj);

        if (lchantiers == null) {
            lchantiers = new LinkedList<>();
            //    lchantiers.add(intChantier);
        }

        i = heuresSupplementairesService.nombreHS("", "", "", etatHS, lchantiers, rechercheDate);
        var = (i % 10 == 0) ? i / 10 : i / 10 + 1;
        //if it's the last page on the pagination 
        if (page == var) {
            last = true;
            pageId = true;
            first = true;
            next = true;
            prev = true;
        }

        matriculeSearch = "";
        cinSearch = "";
        cnss = "";

    }

    public void renitialiser() {
        matriculeSearch = "";
        cinSearch = "";
        cnss = "";
        etatHS = null;
        rechercheDate = null;
        intChantier = null;
        onFirst();

        page = 1;

        List<Integer> lchantiers = HeureSupPagination.getChantierForSeach(intChantier, user_obj);
        i = heuresSupplementairesService.nombreHS("", "", "", etatHS, lchantiers, rechercheDate);

        var = (i % 10 == 0) ? i / 10 : i / 10 + 1;
        //if it's the last page on the pagination 
        if (page == var) {
            last = true;
            pageId = true;
            first = true;
            next = true;
            prev = true;
        }
    }

    public void rechercheByMatCinCnss() {
        etatHS = (etatHS != null && etatHS == 0) ? null : etatHS;
        intChantier = (intChantier != null && intChantier == 0) ? null : intChantier;
        onFirst();

        page = 1;

        List<Integer> lchantiers = HeureSupPagination.getChantierForSeach(intChantier, user_obj);
        i = heuresSupplementairesService.nombreHS("", "", "", etatHS, lchantiers, rechercheDate);

        var = (i % 10 == 0) ? i / 10 : i / 10 + 1;
        //if it's the last page on the pagination 
        if (page == var) {
            last = true;
            pageId = true;
            first = true;
            next = true;
            prev = true;
        }
//        matriculeSearch = "";
//        cinSearch = "";
//        cnss = "";
    }

    /**
     * Modifier la date, l'heure début et l'heure de fin
     */
    public void updateHS() {
        String chaineDateHS = "";
        HeuresSupplementaires hs = heuresSupplementairesService.getHS(this.hs.getId());
        if (hs.getDate() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            chaineDateHS = sdf.format(this.hs.getDate());
            hs.setDate(new Date(chaineDateHS));

        }
        if (heureDebutMod.length() == 1) {
            heureDebutMod = "0" + heureDebutMod;
        }

        if (minDebutMod.length() == 1) {
            minDebutMod = "0" + minDebutMod;
        }

        if (heureFinMod.length() == 1) {
            heureFinMod = "0" + heureFinMod;
        }

        if (minFinMod.length() == 1) {
            minFinMod = "0" + minFinMod;
        }
        String hd = heureDebutMod + ":" + minDebutMod;
        String hf = heureFinMod + ":" + minFinMod;
        hs.setHeureDebut(hd);
        hs.setHeureFin(hf);
        String nombreHeuresMinutesHS = presenceService.nombreHeuresMinutesPresence(heureDebutMod + ":" + minDebutMod, heureFinMod + ":" + minFinMod);
        if (!"".equals(nombreHeuresMinutesHS)) {
            String[] tab = nombreHeuresMinutesHS.split(":");

            hs.setNombreHeures(Integer.parseInt(tab[0]));
            hs.setNombreMinutes(Integer.parseInt(tab[1]));
            ELContext elContext = FacesContext.getCurrentInstance().getELContext();
            Authentification authentification = (Authentification) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "authentification");
            hs.setModifiePar(authentification.get_connected_user().getLogin());
            hs.setDateModification(new Date());

            Long dateTimeDebut = new Date(chaineDateHS + " " + hs.getHeureDebut()).getTime();
            Long dateTimeFin = new Date(chaineDateHS + " " + hs.getHeureFin()).getTime();
            hs.setLongDateTimeDebutHS(dateTimeDebut);
            hs.setLongDateTimeFinHS(dateTimeFin);

            if (heuresSupplementairesService.modifierHS(hs)) {
                Module.message(0, "Modification faite avec succés", "");
                etatHS = (etatHS != null && etatHS == 0) ? null : etatHS;
                intChantier = (intChantier != null && intChantier == 0) ? null : intChantier;
                current();
            }
        }
    }

    /**
     * valider heures supplémentaires en modifiant son état en "Accepté"
     */
    public void acceptHS() {

        EtatHeuresSupplementaires eHS = parametrageService.getEtatHS(EtatHeuresSupplementaires.ETAT_ACCEPTE);

        HeuresSupplementaires hsToValid = heuresSupplementairesService.getHS(this.hs.getId());

        // l'utilisateur qui a valider l'heure sup
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        Authentification authentification = (Authentification) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "authentification");

        //il faut verifier si une heure supp n'est pas superposé avec l'actuel HS
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        String dateHS = df.format(hsToValid.getDate());

        // System.out.println("DAAAATTTEEEE :: " + hsToValid.getDate());
        //  System.out.println("HEURES SUP TO VALID : " + dateHS + "HEURE SUP HEURE : " + hsToValid.getHeureDebut() + "HEURE SUP HEUREF : " + hsToValid.getHeureFin());
        // Presence p = presenceService.getPresence(hsToValid.getChantier().getId(), hsToValid.getDate(), hsToValid.getSalarie().getId(), "");
        /*
        if (p == null) {
            Module.message(2, "Pas de presences sur cette date", "");
            return;
        }
         */
        //System.out.println("HEURE SUP PRESENCE :: " + p.getHeureEntree() + " --- SORTIE :: " + p.getHeureSortie() + "--- DATE :: " + p.getDate());
        /**
         * if(p.getHeureSortie().compareTo(hsToValid.getHeureDebut()) > 0){
         * Module.message(2, "Attention, ce salarié à un pointage dans cette
         * Heure, vous ne pouvez pas valider", ""); return; }
         */
        if (heuresSupplementairesService.heuresSupValidWithEtatExist(hsToValid.getSalarie().getId(), dateHS, hsToValid.getHeureDebut(), eHS.getId())) {

            Module.message(2, "Des heures supp existent pour ce salarié, matricule " + hsToValid.getSalarie().getMatricule() + " dans cette date ", "");
            return;
        }

        hsToValid.setValidePar(authentification.get_connected_user().getLogin());
        hsToValid.setDateValidation(new Date());
        hsToValid.setEtat(eHS);

        heuresSupplementairesService.validerHeuresSupplementaires(hsToValid);
        Presence presence = HeuresSupplementairesMb.createPresenceFromHS(hsToValid);

        presenceService.ajouterPresence(presence);

        Module.message(0, "Heures Supplementaires Validée avec succès", "");

        etatHS = (etatHS != null && etatHS == 0) ? null : etatHS;
        intChantier = (intChantier != null && intChantier == 0) ? null : intChantier;
        current();

    }

    public void initAjoutDialogue() {
        matricule = "";
//        codeChantier = null;
//        currentDate = null;
        this.addButtonValidate = Boolean.FALSE;
        this.addButtonChantier = Boolean.FALSE;
        this.addButtonMatri = Boolean.FALSE;
        this.addButtonDate = Boolean.FALSE;

    }

    /**
     * crée une présence à partir d'une heure supp
     *
     * @param hsToValid
     * @return
     */
    private static Presence createPresenceFromHS(HeuresSupplementaires hsToValid) {
        Presence presence = new Presence();
        presence.setSalarie(hsToValid.getSalarie());
        presence.setChantier(hsToValid.getChantier());
        presence.setHeureEntree(hsToValid.getHeureDebut());
        presence.setHeureSortie(hsToValid.getHeureFin());
        presence.setDate(hsToValid.getDate());
        presence.setDateSaisieHeureSortie(new Date());

        presence.setLongDateTimeEntree(hsToValid.getDate().getTime());
        presence.setLongDateTimeSortie(hsToValid.getDate().getTime());
        presence.setNombreHeures(hsToValid.getNombreHeures());
        presence.setNombreMinutes(hsToValid.getNombreMinutes());

        presence.setCreePar(hsToValid.getValidePar());
        presence.setDateCreation(new Date());

        presence.setFlag(true);

        return presence;
    }

    /**
     * Refuser une heure supplementaire
     */
    public void CancelHS() {

        EtatHeuresSupplementaires eHS = parametrageService.getEtatHS(EtatHeuresSupplementaires.ETAT_REFUSE);

        HeuresSupplementaires hsToValid = heuresSupplementairesService.getHS(this.hs.getId());
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        Authentification authentification = (Authentification) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "authentification");

        hsToValid.setValidePar(authentification.get_connected_user().getLogin());
        hsToValid.setDateValidation(new Date());
        hsToValid.setEtat(eHS);

        heuresSupplementairesService.validerHeuresSupplementaires(hsToValid);

        Module.message(0, "L\'heure supplementaire a été annulée", "");

        etatHS = (etatHS != null && etatHS == 0) ? null : etatHS;
        intChantier = (intChantier != null && intChantier == 0) ? null : intChantier;
        current();
    }

    /**
     * initialiser les valeur a modifier
     */
    public void beforeUpdate() {
        this.heureDebutMod = hs.getHeureDebut().substring(0, 2);
        this.minDebutMod = hs.getHeureDebut().substring(3, 5);
        this.heureFinMod = hs.getHeureFin().substring(0, 2);
        this.minFinMod = hs.getHeureFin().substring(3, 5);
    }

    public void onNext() {
        listHS.clear();
        page += 1;
        listHS = HeureSupPagination.page(page, matriculeSearch, cinSearch, cnss, etatHS, intChantier, rechercheDate, user_obj);
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
        listHS.clear();

        listHS = HeureSupPagination.page(page, matriculeSearch, cinSearch, cnss, etatHS, intChantier, rechercheDate, user_obj);

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
        listHS.clear();
        page -= 1;
        listHS = HeureSupPagination.page(page, matriculeSearch, cinSearch, cnss, etatHS, intChantier, rechercheDate, user_obj);

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
        listHS.clear();
        listHS = HeureSupPagination.page(page, matriculeSearch, cinSearch, cnss, etatHS, intChantier, rechercheDate, user_obj);

    }

    public void onFirst() {
        page = 1;
        listHS = HeureSupPagination.page(page, matriculeSearch, cinSearch, cnss, etatHS, intChantier, rechercheDate, user_obj);

        for (HeuresSupplementaires hs : listHS) {
            System.out.println("CHANTIER : " + hs.getChantier().getCode());
        }

        last = false;
        pageId = false;
        first = true;
        next = false;
        prev = true;

    }

    public void onLast() {

        page = (i % 10 == 0) ? i / 10 : i / 10 + 1;

        listHS = HeureSupPagination.last(matriculeSearch, cinSearch, cnss, etatHS, intChantier, rechercheDate, user_obj);

        last = true;
        pageId = true;
        first = false;
        next = true;
        prev = false;
    }

    /**
     * retourne l'heure de sortie du chantier
     *
     * @param ch
     */
    public void getHeureChantierByChantier() {
        Chantier ch = chantierService.getChantier(codeChantier);

        if (ch instanceof Chantier) {
            String heureSortie = ch.getHeureSortie();
            String[] tab = heureSortie.split(":");
            if (tab.length == 2) {
                this.heureEntree = tab[0].trim();
                this.heureSortie = tab[0].trim();
                this.minEntree = tab[1].trim();
                this.minSortie = tab[1].trim();

                this.heureSortieChantier = tab[0].trim();
                this.minSortieChantier = tab[1].trim();

                this.addButtonChantier = Boolean.TRUE;
            } else {
                this.addButtonChantier = Boolean.FALSE;
            }
        }
        this.validate();
    }

    public void onDateHSBlur() {
        if (currentDate != null) {
            this.addButtonDate = Boolean.TRUE;
        } else {
            this.addButtonDate = Boolean.FALSE;

        }
        this.validate();
    }

    public void onDateSelect() {
        /*
        Boolean pointageSortie = presenceService.dejaPointe(Integer.valueOf(this.getMatricule()), this.currentDate);

         if (pointageSortie.equals(Boolean.FALSE)) {
            Module.message(2, "Warning", "Ce salarié n'as pas pointé en sortie");
            this.addButtonDate = Boolean.FALSE;
        } else {
            this.addButtonDate = Boolean.TRUE;
        }*/
        this.addButtonDate = Boolean.TRUE;

        this.validate();
    }

    /**
     * on blur sur le matricule du salarie
     */
    public void salarieVerification() {
        try {
            Salarie sForAdd = salarieService.getSalarie(this.matricule, "", "", "", "");

        if (sForAdd instanceof Salarie) {

            if ((salarieService.isActif(this.matricule))) {
                this.addButtonMatri = Boolean.TRUE;
            } else {
                Module.message(2, "Warning", "Ce salarié n'est pas actif");
                this.addButtonMatri = Boolean.FALSE;
            }
        } else {
            Module.message(2, "Warning", "Ce salarié n'existe pas ");
            this.addButtonMatri = Boolean.FALSE;
        }
        
        this.validate();
        } catch (Exception e) {
            this.validate();
            System.out.println("Erreur au niveau de verification du salarier (heursupp) car "+e.getMessage());
        }
        


    }

    public void reinitSearch() {
        matriculeSearch = null;
        cinSearch = null;
        cnss = null;
    }

    public void validate() {
        if (this.addButtonDate == Boolean.TRUE && this.addButtonMatri == Boolean.TRUE && this.addButtonChantier == Boolean.TRUE) {
            this.addButtonValidate = Boolean.TRUE;
        } else {
            this.addButtonValidate = Boolean.FALSE;
        }
    }

}
