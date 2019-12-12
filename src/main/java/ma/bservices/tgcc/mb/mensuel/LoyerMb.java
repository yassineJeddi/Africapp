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
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import ma.bservice.tgcc.Constante.Message;
import ma.bservices.tgcc.Entity.BonCaisse;
import ma.bservices.beans.Chantier;
import ma.bservices.beans.Proprietaire;
import ma.bservices.mb.services.Module;
import ma.bservices.tgcc.Entity.FournisseurLoyer;
import ma.bservices.tgcc.Entity.Loyer;
import ma.bservices.tgcc.Entity.LoyerChantier;
import ma.bservices.tgcc.Entity.LoyerSalarie;
import ma.bservices.tgcc.Entity.Mensuel;
import ma.bservices.tgcc.service.Engin.ChantierService;
import ma.bservices.tgcc.service.Mensuel.BonCaisseService;
import ma.bservices.tgcc.service.Mensuel.LoyerService;
import ma.bservices.tgcc.service.Mensuel.MensuelService;
import ma.bservices.tgcc.utilitaire.Outils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

/**
 *
 * @author a.wattah
 */
@Component
@ManagedBean(name = "loyerMb")
@ViewScoped
public class LoyerMb implements Serializable {

    @ManagedProperty(value = "#{loyerService}")
    private LoyerService loyerService;

    @ManagedProperty(value = "#{boncaisseservice}")
    private BonCaisseService bonCaisseService;

    @ManagedProperty(value = "#{chantierService}")
    private ChantierService chantierService;

    @ManagedProperty(value = "#{mensuelService}")
    private MensuelService mensuelService;

    private LoyerChantier lc = new LoyerChantier();
    private List<Chantier> chantiers = new ArrayList<>();
    
    private List<Proprietaire> proprietaires;
    private Chantier chantier = new Chantier();

    private Date Date_Debut_Loyer_chantier = new Date();
    private Date Date_Fin_Loyer_chantier = new Date();

    private Chantier selectChantier = new Chantier();

    private Mensuel mensuel;
    private Mensuel mensuelBoncaisse = new Mensuel();
    private Mensuel mensuelToSearch = new Mensuel();

    private List<Mensuel> mensuels = new ArrayList<>();

    private List<Mensuel> mensuelsNom = new ArrayList<>();

    private LoyerSalarie lsgetByMensuel = new LoyerSalarie();

    private Loyer loyer = new Loyer();
    private Date Date_Debut_Loyer_Salarie = new Date();
    
    
    private Date date_Fin_Loyer_Salarie = new Date();
    
    private Date date_Fin_Loyer_Chantier = new Date();

    private List<Mensuel> searchMensuelList;

    private BonCaisse mensuelEditeBonCaisse;

    private LoyerSalarie loyerSalarieEdite = new LoyerSalarie();

    private List<LoyerSalarie> loyers = new ArrayList<>();

    private Boolean disable_buttonaffectSalrie = false;
    private Boolean disable_buttonaffectChantier = false;

    private Boolean disable_rib = false;

    /*
     * partie loyer chantier jihane 
     */
    private String valueOfRadioB;
    private Date d = new Date();

    private LoyerSalarie ls;
    private List<Mensuel> mensuelsChekBox;

    private String[] mensuels_str;

    private List<Mensuel> l_mensuelSec; 

    public LoyerMb() {

    }

    
    
    
    private List<FournisseurLoyer>  listFournisseurLoyers ;
    /*
     * getters and setters 
     */

    public List<FournisseurLoyer> getListFournisseurLoyers() {
        return listFournisseurLoyers;
    }

    public void setListFournisseurLoyers(List<FournisseurLoyer> listFournisseurLoyers) {
        this.listFournisseurLoyers = listFournisseurLoyers;
    }

    
    
    public List<Proprietaire> getProprietaires() {
        return proprietaires;
    }

    public void setProprietaires(List<Proprietaire> proprietaires) {
        this.proprietaires = proprietaires;
    }
       
    
    public List<Mensuel> getMensuels() {
        return mensuels;
    }

    public Boolean getDisable_buttonaffectChantier() {
        return disable_buttonaffectChantier;
    }

    public void setDisable_buttonaffectChantier(Boolean disable_buttonaffectChantier) {
        this.disable_buttonaffectChantier = disable_buttonaffectChantier;
    }

    public Boolean getDisable_buttonaffectSalrie() {
        return disable_buttonaffectSalrie;
    }

    public void setDisable_buttonaffectSalrie(Boolean disable_buttonaffectSalrie) {
        this.disable_buttonaffectSalrie = disable_buttonaffectSalrie;
    }

    public void setMensuels(List<Mensuel> mensuels) {
        this.mensuels = mensuels;
    }

    public Mensuel getMensuelBoncaisse() {
        return mensuelBoncaisse;
    }

    public Date getDate_Fin_Loyer_Chantier() {
        return date_Fin_Loyer_Chantier;
    }

    public Date getDate_Fin_Loyer_chantier() {
        return Date_Fin_Loyer_chantier;
    }

    public void setDate_Fin_Loyer_chantier(Date Date_Fin_Loyer_chantier) {
        this.Date_Fin_Loyer_chantier = Date_Fin_Loyer_chantier;
    }
    
    

    public void setDate_Fin_Loyer_Chantier(Date date_Fin_Loyer_Chantier) {
        this.date_Fin_Loyer_Chantier = date_Fin_Loyer_Chantier;
    }
    
    
    
    public void setMensuelBoncaisse(Mensuel mensuelBoncaisse) {
        this.mensuelBoncaisse = mensuelBoncaisse;
    }

    public LoyerService getLoyerService() {
        return loyerService;
    }

    public void setLoyerService(LoyerService loyerService) {
        this.loyerService = loyerService;
    }

    public Mensuel getMensuel() {
        return mensuel;
    }

    public void setMensuel(Mensuel mensuel) {
        this.mensuel = mensuel;
    }

    public Date getDate_Fin_Loyer_Salarie() {
        return date_Fin_Loyer_Salarie;
    }

    public void setDate_Fin_Loyer_Salarie(Date date_Fin_Loyer_Salarie) {
        this.date_Fin_Loyer_Salarie = date_Fin_Loyer_Salarie;
    }

    public Loyer getLoyer() {
        return loyer;
    }

    public void setLoyer(Loyer loyer) {
        this.loyer = loyer;
    }

    public List<Mensuel> getSearchMensuelList() {
        return searchMensuelList;
    }

    public Boolean getDisable_rib() {
        return disable_rib;
    }

    public void setDisable_rib(Boolean disable_rib) {
        this.disable_rib = disable_rib;
    }

    public void setSearchMensuelList(List<Mensuel> searchMensuelList) {
        this.searchMensuelList = searchMensuelList;
    }

    public List<LoyerSalarie> getLoyers() {
        return loyers;
    }

    public void setLoyers(List<LoyerSalarie> loyers) {
        this.loyers = loyers;
    }

    public Mensuel getMensuelToSearch() {
        return mensuelToSearch;
    }

    public void setMensuelToSearch(Mensuel mensuelToSearch) {
        this.mensuelToSearch = mensuelToSearch;
    }

    public BonCaisseService getBonCaisseService() {
        return bonCaisseService;
    }

    public void setBonCaisseService(BonCaisseService bonCaisseService) {
        this.bonCaisseService = bonCaisseService;
    }

    public LoyerSalarie getLoyerSalarieEdite() {
        return loyerSalarieEdite;
    }

    public void setLoyerSalarieEdite(LoyerSalarie loyerSalarieEdite) {
        this.loyerSalarieEdite = loyerSalarieEdite;
    }

    public BonCaisse getMensuelEditeBonCaisse() {
        return mensuelEditeBonCaisse;
    }

    public void setMensuelEditeBonCaisse(BonCaisse mensuelEditeBonCaisse) {
        this.mensuelEditeBonCaisse = mensuelEditeBonCaisse;
    }

    public List<Mensuel> getMensuelsNom() {
        return mensuelsNom;
    }

    public void setMensuelsNom(List<Mensuel> mensuelsNom) {
        this.mensuelsNom = mensuelsNom;
    }

    public LoyerSalarie getLsgetByMensuel() {
        return lsgetByMensuel;
    }

    public void setLsgetByMensuel(LoyerSalarie lsgetByMensuel) {
        this.lsgetByMensuel = lsgetByMensuel;
    }

    public String getValueOfRadioB() {
        return valueOfRadioB;
    }

    public void setValueOfRadioB(String valueOfRadioB) {
        this.valueOfRadioB = valueOfRadioB;
    }

    public Date getD() {
        return d;
    }

    public void setD(Date d) {
        this.d = d;
    }

    public LoyerSalarie getLs() {
        return ls;
    }

    public void setLs(LoyerSalarie ls) {
        this.ls = ls;
    }

    public MensuelService getMensuelService() {
        return mensuelService;
    }

    public void setMensuelService(MensuelService mensuelService) {
        this.mensuelService = mensuelService;
    }

    public ChantierService getChantierService() {
        return chantierService;
    }

    public void setChantierService(ChantierService chantierService) {
        this.chantierService = chantierService;
    }

    public List<Mensuel> getMensuelsChekBox() {
        return mensuelsChekBox;
    }

    public List<Mensuel> getL_mensuelSec() {
        return l_mensuelSec;
    }

    public void setL_mensuelSec(List<Mensuel> l_mensuelSec) {
        this.l_mensuelSec = l_mensuelSec;
    }

    public void setMensuelsChekBox(List<Mensuel> mensuelsChekBox) {
        this.mensuelsChekBox = mensuelsChekBox;
    }

    public String[] getMensuels_str() {
        return mensuels_str;
    }

    public void setMensuels_str(String[] mensuels_str) {
        this.mensuels_str = mensuels_str;
    }

    public Date getDate_Debut_Loyer_Salarie() {
        return Date_Debut_Loyer_Salarie;
    }

    public void setDate_Debut_Loyer_Salarie(Date Date_Debut_Loyer_Salarie) {
        this.Date_Debut_Loyer_Salarie = Date_Debut_Loyer_Salarie;
    }

    public LoyerChantier getLc() {
        return lc;
    }

    public void setLc(LoyerChantier lc) {
        this.lc = lc;
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

    public Date getDate_Debut_Loyer_chantier() {
        return Date_Debut_Loyer_chantier;
    }

    public void setDate_Debut_Loyer_chantier(Date Date_Debut_Loyer_chantier) {
        this.Date_Debut_Loyer_chantier = Date_Debut_Loyer_chantier;
    }

    public Chantier getSelectChantier() {
        return selectChantier;
    }

    public void setSelectChantier(Chantier selectChantier) {
        this.selectChantier = selectChantier;
    }

    /*
     * end getters and setters 
     */
    @PostConstruct
    public void init() {
        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        loyerService = ctx.getBean(LoyerService.class);
        bonCaisseService = ctx.getBean(BonCaisseService.class);
        chantierService = ctx.getBean(ChantierService.class);
        mensuelService = ctx.getBean(MensuelService.class);
        
        proprietaires=new ArrayList<Proprietaire>();
        proprietaires=loyerService.getListproPrietaireDistinct();
        System.out.println(this.proprietaires);
        
        mensuel = new Mensuel();
        ls = new LoyerSalarie();
        mensuelsChekBox = new ArrayList<>();
        listFournisseurLoyers = loyerService.listFournisseurLoyer();

    }

    public int checkRIB(String rib) {
        System.out.println("PROCESSING RIB ....  " + rib);
        String ribPart1;

        if ("0".equals(rib.substring(0, 1))) {
            ribPart1 = rib.substring(1, 6);
        } else {
            ribPart1 = rib.substring(0, 6);
        }

        String ribPart2 = rib.substring(6, 12);
        String ribPart3 = rib.substring(12, 18);
        String ribPart4 = rib.substring(18, 22) + "00";

        int a = Integer.parseInt(ribPart1) % 97;
        int b = Integer.parseInt((a + "" + ribPart2)) % 97;
        int c = Integer.parseInt((b + "" + ribPart3)) % 97;
        int d1 = Integer.parseInt((c + "" + ribPart4)) % 97;

        int cle = 97 - d1;
        if (cle == Integer.parseInt(rib.substring(22, 24))) {
            return 1;
        } else {
            return 0;
        }
    }

    public void saveLoyerSalarie() throws ParseException {
        
         if (!Module.checkDate(Date_Debut_Loyer_Salarie, null, new Date())) {
            Module.message(2, "date affectation doit etre inférieure a la date d'aujourd'hui", "");
            return;
        }
         
        FacesContext context = FacesContext.getCurrentInstance();
        int isRibValid = 0;
        
        
        List<Loyer> l = loyerService.findByNumContrat(ls.getNumcontrat());
        
        if(ls.getModepaiment().compareToIgnoreCase("Virement") == 0 && ls.getRib().compareToIgnoreCase("") != 0){
         isRibValid = checkRIB(ls.getRib());
        }
        
        if(ls.getModepaiment().compareToIgnoreCase("Virement") == 0 && isRibValid == 0){
            context.addMessage(null, new FacesMessage("LE NUMERO DE RIB EST INVALID", ""));
        }            
        if (l != null) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "" + "Ce numero de contrat existe déja!", ""));
        }
        else if((ls.getModepaiment().compareToIgnoreCase("Virement") == 0 && isRibValid == 1 && l == null) || (ls.getModepaiment().compareToIgnoreCase("Espece") == 0 && l == null) || (ls.getModepaiment().compareToIgnoreCase("Sur bulletin") == 0 && l == null))
        {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String s = df.format(Date_Debut_Loyer_Salarie);
            System.out.println("date debut to set : " + s);
            ls.setDateDebutDate(Date_Debut_Loyer_Salarie);
            ls.setDateFinContrat(date_Fin_Loyer_Salarie);
            System.out.println(l_mensuelSec);
            
            loyerService.saveAffectation(ls, mensuel, l_mensuelSec);
            ls = new LoyerSalarie();
            
            
            mensuel = null;
            if (l_mensuelSec != null) {

                if (!l_mensuelSec.isEmpty()) {

                    l_mensuelSec.clear();
                }
            }
            
            
            Date_Debut_Loyer_Salarie = new Date();

            context.addMessage(null, new FacesMessage("" + Message.AFFECT_LOYER_SUCCESS, ""));

            ELContext elContext = FacesContext.getCurrentInstance().getELContext();
            ma.bservices.tgcc.mb.mensuel.LoyerSalarieMb loyerSalarier_bean = (ma.bservices.tgcc.mb.mensuel.LoyerSalarieMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "loyer_SalarieMb");
            loyerSalarier_bean.setListeLSalrie(loyerService.loyerSalaries());
        }

    }

    public void validateSaveLoyer() throws ParseException {

        ELContext elContext = FacesContext.getCurrentInstance().getELContext();

        Boolean exist = false;

        ma.bservices.tgcc.mb.mensuel.LoyerChantierMb loyerChantier_bean = (ma.bservices.tgcc.mb.mensuel.LoyerChantierMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "loyer_ChantierMb");

        ma.bservices.tgcc.mb.mensuel.LoyerSalarieMb loyerSalarier_bean = (ma.bservices.tgcc.mb.mensuel.LoyerSalarieMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "loyer_SalarieMb");

        List<LoyerChantier> l_chant_s = loyerChantier_bean.getListeLChantier();

        List<LoyerSalarie> l_salari_s = loyerSalarier_bean.getListeLSalrie();

        if (l_chant_s != null) {
            if (!l_chant_s.isEmpty()) {
                for (LoyerChantier l_chant : l_chant_s) {
                    if (ls.getNumcontrat().equals(l_chant.getNumcontrat())) {
                        exist = true;
                    }
                }
            }
        }

        if (l_salari_s != null) {
            if (!l_salari_s.isEmpty()) {
                for (LoyerSalarie l_sala : l_salari_s) {

                    if (l_sala.getNumcontrat().equals(ls.getNumcontrat())) {
                        exist = true;
                    }

                }

            }
        }

        if (exist == true) {

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, Message.NUMCONTRAT_EXIST, Message.NUMCONTRAT_EXIST));

        } else if (mensuel == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                    Message.MENSUEL_EXIST_LOYER, Message.MENSUEL_EXIST_LOYER));

        } else if (ls.getModepaiment().equals("Virement")) {

            if (ls.getNomproprietaire().equals("")) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                        Message.VERIFICATION_NOM, Message.VERIFICATION_NOM));

            } else if (ls.getPrenomproprietaire().equals("")) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                        Message.VERIFICATION_PRENOM, Message.VERIFICATION_PRENOM));

            } else if (ls.getRib().equals("")) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                        Message.VERIFICATION_RIB, Message.VERIFICATION_RIB));

            } else if (checkRIB(ls.getRib()) == 1) {
                BonCaisse bc = new BonCaisse();
                List<Mensuel> Ms = new ArrayList<>();
                String Date_Debut_Salarie = "";
                DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                if (Date_Debut_Loyer_Salarie != null) {
                    Date today = Date_Debut_Loyer_Salarie;
                    Date_Debut_Salarie = df.format(today);
                }
                 ls.setDateDebutDate(Date_Debut_Loyer_Salarie);
                ls.setMensuel_Principal(mensuel);
                Ms.add(mensuel);
                ls.setMensuels(Ms);

                loyerService.saveAffectation(ls, mensuel, l_mensuelSec);

                loyerSalarier_bean.init();

                ma.bservices.tgcc.mb.services.LoyerMb loyer_bean = (ma.bservices.tgcc.mb.services.LoyerMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "loyer_ServMb");
                loyer_bean.reloadLoyerSalarie();

                ls = new LoyerSalarie();
                mensuel = null;
                if (l_mensuelSec != null) {

                    if (!l_mensuelSec.isEmpty()) {

                        l_mensuelSec.clear();
                    }
                }
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage("" + Message.AFFECT_LOYER_SUCCESS, ""));

            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, Message.ADD_LOYER_FAIL_RIB, Message.ADD_LOYER_FAIL_RIB));
            }
        } else if (ls.getModepaiment()
                .equals("Espece")) {

            BonCaisse bc = new BonCaisse();
            List<Mensuel> Ms = new ArrayList<>();
            String Date_Debut_Salarie = "";
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            if (Date_Debut_Loyer_Salarie != null) {
                Date today = Date_Debut_Loyer_Salarie;
                Date_Debut_Salarie = df.format(today);
            }
            ls.setDateDebutDate(Date_Debut_Loyer_Salarie);
            ls.setMensuel_Principal(mensuel);
            Ms.add(mensuel);
            ls.setMensuels(Ms);

            loyerService.saveAffectation(ls, mensuel, l_mensuelSec);

            loyerSalarier_bean.init();

            ma.bservices.tgcc.mb.services.LoyerMb loyer_bean = (ma.bservices.tgcc.mb.services.LoyerMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "loyer_ServMb");
            loyer_bean.reloadLoyerSalarie();

            ls = new LoyerSalarie();
            mensuel = new Mensuel();
            if (l_mensuelSec != null) {
                if (!l_mensuelSec.isEmpty()) {
                    l_mensuelSec.clear();
                }
            }
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("" + Message.AFFECT_LOYER_SUCCESS, ""));

            ls = new LoyerSalarie();
            mensuel = null;
            if (l_mensuelSec != null) {

                if (!l_mensuelSec.isEmpty()) {

                    l_mensuelSec.clear();
                }
            }

        }

        Date_Debut_Loyer_Salarie = new Date();

    }

    public void rechercheMensuOrdi() {
        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        mensuelService
                = ctx.getBean(MensuelService.class
                );

        mensuels = new ArrayList<>();

//        this.searchMensuelList = mensuelService.getListMensuelBy(mensuelToSearch.getMatricule(), mensuelToSearch.getNom(), mensuelToSearch.getPrenom(), mensuelToSearch.getCin());
//
//        this.mensuels = searchMensuelList;
        if (mensuelToSearch.getMatricule()
                .equals("") && mensuelToSearch.getNom().equals("") && mensuelToSearch.getPrenom().equals("") && mensuelToSearch.getCin().equals("")) {

            ELContext elContext = FacesContext.getCurrentInstance().getELContext();

            ma.bservices.tgcc.mb.services.MensuelMb mensuel_bean = (ma.bservices.tgcc.mb.services.MensuelMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "mensuelServMb");
            mensuels = mensuel_bean.getL_mensuels();

        } else {
            this.mensuels = mensuelService.search(mensuelToSearch.getMatricule(), mensuelToSearch.getNom(), mensuelToSearch.getPrenom(), "", mensuelToSearch.getCin());
        }

    }

    /**
     * methode pour redirect l object mensuel rechercher
     *
     * @param selected
     */
    public void redirectMensuel(Mensuel selected) {
        mensuel = selected;

        System.out.println("mensuel : " + selected.getId());

        mensuelsChekBox = new ArrayList<>();

        this.disable_buttonaffectSalrie = true;

        if (mensuel.getId() != null) {

            System.out.println("entre : " + mensuel.getId());

            mensuelsChekBox = mensuelService.getMensuelDifferentId(mensuel.getId());
        }

    }

    public void redirect_MensuelSec() {

        for (String mensuels_str1 : mensuels_str) {
            Mensuel m = mensuelService.findById(mensuels_str1);
            l_mensuelSec.add(m);
        }
        System.out.println("entre : " + l_mensuelSec.size());

    }

    public void reload() {

    }

    /**
     * *
     * sauvegarde Loyer Chantier et bon caisse
     */
    public void saveLoyerChantier() throws ParseException {

        if (!Module.checkDate(Date_Debut_Loyer_chantier, null, new Date())) {
            Module.message(2, "date affectation doit etre inférieure a la date d'aujourd'hui", "");
            return;
        }
        
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();

        Boolean exist = false;

        ma.bservices.tgcc.mb.mensuel.LoyerChantierMb loyerChantier_bean = (ma.bservices.tgcc.mb.mensuel.LoyerChantierMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "loyer_ChantierMb");

        ma.bservices.tgcc.mb.mensuel.LoyerSalarieMb loyerSalarier_bean = (ma.bservices.tgcc.mb.mensuel.LoyerSalarieMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "loyer_SalarieMb");

        List<LoyerChantier> l_chant_s = loyerChantier_bean.getListeLChantier();

        List<LoyerSalarie> l_salari_s = loyerSalarier_bean.getListeLSalrie();

        if (l_chant_s != null) {
            if (!l_chant_s.isEmpty()) {
                for (LoyerChantier l_chant : l_chant_s) {
                    if (lc.getNumcontrat().equals(l_chant.getNumcontrat())) {

                        exist = true;
                    }
                }
            }
        }

        if (l_salari_s != null) {
            if (!l_salari_s.isEmpty()) {
                for (LoyerSalarie l_sala : l_salari_s) {

                    if (l_sala.getNumcontrat().equals(lc.getNumcontrat())) {
                        exist = true;
                    }

                }

            }
        }

        if (exist == true) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, Message.NUMCONTRAT_EXIST, Message.NUMCONTRAT_EXIST));

        } else if (selectChantier == null) {

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                    Message.CHANTIER_EXIST_LOYER, Message.CHANTIER_EXIST_LOYER));

        } else if (lc.getModepaiment().equals("Virement")) {
            try { 
                if ("".equals(lc.getNomproprietaire())) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                            Message.VERIFICATION_NOM, Message.VERIFICATION_NOM));

                } else if ("".equals(lc.getPrenomproprietaire())) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                            Message.VERIFICATION_PRENOM, Message.VERIFICATION_PRENOM));

                } else if ("".equals(lc.getRib())) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                            Message.VERIFICATION_RIB, Message.VERIFICATION_RIB));

                } else if (checkRIB(lc.getRib()) == 1) {
                   lc.setDateDebutDate(Date_Debut_Loyer_chantier);
                   lc.setDateFinContrat(date_Fin_Loyer_Chantier);

                    loyerService.saveAffectation(lc, selectChantier);

                    ma.bservices.tgcc.mb.services.LoyerMb loyer_bean = (ma.bservices.tgcc.mb.services.LoyerMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "loyer_ServMb");
                    loyer_bean.reloadLoyerChantier();
                    FacesContext context = FacesContext.getCurrentInstance();
                    String message = Message.AFFECT_LOYER_CHANTIER + selectChantier.getCode();
                    context.addMessage(null, new FacesMessage("" + message, ""));

                    loyerChantier_bean.init();
                    selectChantier = null;
                    lc = new LoyerChantier();
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, Message.ADD_LOYER_FAIL_RIB, Message.ADD_LOYER_FAIL_RIB));

                }
            
            } catch (Exception e) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "Problém d'enregistrement du loyer car "+e.getMessage(), " ,Merci de contacter votre administrateur."));
            }
        } else if (lc.getModepaiment().equals("Espece")) {
            
                lc.setDateDebutDate(Date_Debut_Loyer_chantier);
                lc.setDateFinContrat(date_Fin_Loyer_Chantier);
            

            loyerService.saveAffectation(lc, selectChantier);

            ma.bservices.tgcc.mb.services.LoyerMb loyer_bean = (ma.bservices.tgcc.mb.services.LoyerMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "loyer_ServMb");
            loyer_bean.reloadLoyerChantier();
            FacesContext context = FacesContext.getCurrentInstance();
            String message = Message.AFFECT_LOYER_CHANTIER + selectChantier.getCode();
            context.addMessage(null, new FacesMessage("" + message, ""));

            loyerChantier_bean.init();
            selectChantier = null;
            lc = new LoyerChantier();
        }

        Date_Debut_Loyer_chantier = new Date();
    }

    /*
     * rechercher chantier 
     */
    public void rechercheChantier() {

        if (chantier.getCodeNovapaie().equals("") && chantier.getCode().equals("") && chantier.getRegion().equals("")) {
            ELContext elContext = FacesContext.getCurrentInstance().getELContext();

            ma.bservices.tgcc.mb.services.ChantierMb chantier_bean = (ma.bservices.tgcc.mb.services.ChantierMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "chantierServMb");
            chantiers = chantier_bean.getChantiers();
        } else {
            chantiers = chantierService.search(chantier.getCodeNovapaie().toUpperCase(), chantier.getCode().toUpperCase(), chantier.getRegion().toUpperCase());
        }
    }

    /**
     * methode pour redirect l object chantier rechercher
     *
     * @param selected
     */
    public void redirectChantier(Chantier selected) {
        chantier = selected;

        selectChantier = selected;

        this.disable_buttonaffectChantier = true;
    }

    /**
     *
     */
    public void afficherMensuel() {

        ELContext elContext = FacesContext.getCurrentInstance().getELContext();

        ma.bservices.tgcc.mb.services.MensuelMb mensuel_bean = (ma.bservices.tgcc.mb.services.MensuelMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "mensuelServMb");
        mensuels = mensuel_bean.getL_mensuels();

    }

    public void afficherChantier() {

        System.out.println("entre ");
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();

        ma.bservices.tgcc.mb.services.ChantierMb chantier_bean = (ma.bservices.tgcc.mb.services.ChantierMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "chantierServMb");
        chantiers = chantier_bean.getChantiers();
    }

    public void disbledRibLoyerSalarie() {

        if (ls.getModepaiment().equals("Espece")) {
            disable_rib = false;
        } else if (ls.getModepaiment().equals("Virement")) {
            disable_rib = true;
        }

    }

    public void disbledRibLoyerChantier() {

        if (lc.getModepaiment().equals("Espece")) {
            disable_rib = false;
        } else if (lc.getModepaiment().equals("Virement")) {
            disable_rib = true;
        }

    }

    /*
     *
     */
    public String format(String str) {
        Outils outils = new Outils();
        return outils.format(str);
    }

}
