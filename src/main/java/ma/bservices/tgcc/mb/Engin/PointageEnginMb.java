/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.mb.Engin;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import ma.bservice.tgcc.Constante.Constante;
import ma.bservice.tgcc.Constante.Message;
import ma.bservices.beans.Chantier;
import ma.bservices.beans.ChantierAffinite;
import ma.bservices.beans.Lot;
import ma.bservices.beans.Utilisateur;
import ma.bservices.beans.Zone;
import ma.bservices.mb.services.Module;
import ma.bservices.services.ChantierAffiniteService;
import ma.bservices.tgcc.Entity.CompteurrEngin;
import ma.bservices.tgcc.service.Engin.EnginService;
import ma.bservices.tgcc.service.Engin.LotService;
import ma.bservices.tgcc.service.Engin.PointageEnginServices;
import ma.bservices.tgcc.service.Engin.ZoneServices;
import ma.bservices.tgcc.Entity.Engin;
import ma.bservices.tgcc.Entity.PointageEngin;
import ma.bservices.tgcc.service.Engin.ChantierService;
import ma.bservices.tgcc.service.Engin.ICompteurEnginService;
import ma.bservices.tgcc.service.Engin.UtilisateurService;
import org.primefaces.context.RequestContext;
import org.primefaces.event.data.PageEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

/**
 *
 * @author j.allali
 */
@Component
@ManagedBean(name = "pointageEngin")
@ViewScoped
public class PointageEnginMb implements Serializable {

    @ManagedProperty(value = "#{pointageEnginService}")
    private PointageEnginServices pointageEnginSerive;

    @ManagedProperty(value = "#{enginService}")
    private EnginService enginSerive;

    @ManagedProperty(value = "#{zoneServices}")
    private ZoneServices zoneService;

    @ManagedProperty(value = "#{lotService}")
    private LotService lotService;

    @ManagedProperty(value = "#{utilisateurService}")
    private UtilisateurService utilisateurService;

    @ManagedProperty(value = "#{chantierAffiniteService}")
    private ChantierAffiniteService chantierAffiniteService;

    @ManagedProperty(value = "#{chantierService}")
    private ChantierService chantierService;
    
    @ManagedProperty(value = "#{compteurEnginServiceImp}")
    private ICompteurEnginService compteurEnginService;

    private List<Lot> lots = new ArrayList<Lot>();

    private List<PointageEngin> pointageEngin;
    private List<PointageEngin> filtredPointageEngin;
    private List<PointageEngin> pageChangePEValues;
    private String marque;
    private Date date_start = new Date();
    private Date date_to = new Date();
    private List<Zone> zones;
    private PointageEngin validatePE = new PointageEngin();
    private PointageEngin transientPE = new PointageEngin();
    private CompteurrEngin compteurEngin = new CompteurrEngin();

    private Date date = new Date();

    private List<PointageEngin> lpe;

    private List<PointageEngin> lpeF=new ArrayList<PointageEngin>();

    private int chantierSelect;

    private int chantierSelectHome;

    private String stringOfZone;
    private String messageErrorPointage = "";

    private boolean isTranfer;

    private Date last_day_pointed;

    Chantier chantier_selected_obj;

    private Date d = new Date();

    private boolean transfert;

    private List<ChantierAffinite> chantierPointageAff = new ArrayList<>();
    private int chantierAffiniID;

    private boolean isReadyToShowDateTo = true;

    private boolean enginLoaded = false; //ça prends la valeur true quand on clique sur le boutton : "charger les engins"s

    public boolean isIsReadyToShowDateTo() {
        return isReadyToShowDateTo;
    }

    public void setIsReadyToShowDateTo(boolean isReadyToShowDateTo) {
        this.isReadyToShowDateTo = isReadyToShowDateTo;
    }

    public int getChantierAffiniID() {
        return chantierAffiniID;
    }

    public List<PointageEngin> getPageChangePEValues() {
        return pageChangePEValues;
    }

    public void setPageChangePEValues(List<PointageEngin> pageChangePEValues) {
        this.pageChangePEValues = pageChangePEValues;
    }

    public PointageEngin getTransientPE() {
        return transientPE;
    }

    public void setTransientPE(PointageEngin transientPE) {
        this.transientPE = transientPE;
    }

    public void setChantierAffiniID(int chantierAffiniID) {
        this.chantierAffiniID = chantierAffiniID;
    }

    public UtilisateurService getUtilisateurService() {
        return utilisateurService;
    }

    public void setUtilisateurService(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    public ChantierAffiniteService getChantierAffiniteService() {
        return chantierAffiniteService;
    }

    public void setChantierAffiniteService(ChantierAffiniteService chantierAffiniteService) {
        this.chantierAffiniteService = chantierAffiniteService;
    }

    public ChantierService getChantierService() {
        return chantierService;
    }

    public void setChantierService(ChantierService chantierService) {
        this.chantierService = chantierService;
    }

    public List<ChantierAffinite> getChantierPointageAff() {
        return chantierPointageAff;
    }

    public void setChantierPointageAff(List<ChantierAffinite> chantierPointageAff) {
        this.chantierPointageAff = chantierPointageAff;
    }

    public boolean isTransfert() {
        return transfert;
    }

    public void setTransfert(boolean transfert) {
        this.transfert = transfert;
    }

    public Date getLast_day_pointed() {
        return last_day_pointed;
    }

    public void setLast_day_pointed(Date last_day_pointed) {
        this.last_day_pointed = last_day_pointed;
    }

    public Date getD() {
        return d;
    }

    public void setD(Date d) {
        this.d = d;
    }

    public int getChantierSelect() {
        return chantierSelect;
    }

    public void setChantierSelect(int chantierSelect) {
        this.chantierSelect = chantierSelect;
    }

    public boolean isIsTranfer() {
        return isTranfer;
    }

    public void setIsTranfer(boolean isTranfer) {
        this.isTranfer = isTranfer;
    }

    public String getStringOfZone() {
        return stringOfZone;
    }

    public void setStringOfZone(String stringOfZone) {
        this.stringOfZone = stringOfZone;
    }

    public boolean isEnginLoaded() {
        return enginLoaded;
    }

    public void setEnginLoaded(boolean enginLoaded) {
        this.enginLoaded = enginLoaded;
    }

    public List<PointageEngin> getLpeF() {
        return lpeF;
    }

    public void setLpeF(List<PointageEngin> lpeF) {
        this.lpeF = lpeF;
    }

    public String getMessageErrorPointage() {
        return messageErrorPointage;
    }

    public void setMessageErrorPointage(String messageErrorPointage) {
        this.messageErrorPointage = messageErrorPointage;
    }

    public ICompteurEnginService getCompteurEnginService() {
        return compteurEnginService;
    }

    public void setCompteurEnginService(ICompteurEnginService compteurEnginService) {
        this.compteurEnginService = compteurEnginService;
    }

    public CompteurrEngin getCompteurEngin() {
        return compteurEngin;
    }

    public void setCompteurEngin(CompteurrEngin compteurEngin) {
        this.compteurEngin = compteurEngin;
    }

    
    
    
    
    
    
    
    private List<PointageEngin> searchButPointageEngin = new ArrayList<PointageEngin>();

    private int sizeCus = 0;

    PointageEngin recherchePointageEngin = new PointageEngin();

    private String id;
    private List<String> libelleZone;

    /**
     * Creates a new instance of PointageEnginMb
     */
    public PointageEnginMb() {
        this.last_day_pointed = new Date();
    }

    @PostConstruct
    public void init() {
        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        pointageEnginSerive = ctx.getBean(PointageEnginServices.class);
        chantierService = ctx.getBean(ChantierService.class);
        zoneService = ctx.getBean(ZoneServices.class);
        lotService = ctx.getBean(LotService.class);
        utilisateurService = ctx.getBean(UtilisateurService.class);

        this.lots = lotService.findAll();
    }

    /**
     * retourne l'historique du pointage
     */
    public List<PointageEngin> getPointageHistorique() {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, -2);

        pointageEngin = pointageEnginSerive.RechercheEngin("",
                "", "", c.getTime(), new Date(),
                -1);
        System.out.println("pointage engin historique " + pointageEngin.size());

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String user = auth.getPrincipal().toString();
        Utilisateur u = utilisateurService.getUsersByLogin(user);
        List<Chantier> listCh = utilisateurService.findChantiersByUser(u);

//        System.out.println("llist des chantier users " + listCh);
        List<PointageEngin> temp = new LinkedList<>();
        if (pointageEngin != null && listCh != null) {
            for (PointageEngin pe : pointageEngin) {
                if (listCh.contains(pe.getChantierPointage())) {
                    temp.add(pe);
                }
            }
        }
        pointageEngin = new LinkedList<>();
        pointageEngin.addAll(temp);

        return this.pointageEngin;
    }

    /*
     Boutton de recherche
     */
    public void search() {

        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        pointageEnginSerive = ctx.getBean(PointageEnginServices.class);
        try {
            pointageEngin = pointageEnginSerive.RechercheEngin(recherchePointageEngin.getCode(),
                    recherchePointageEngin.getDesignation(), marque, date_start, date_to,
                    chantierSelect);
        } catch (Exception e) {
            pointageEngin = new ArrayList<PointageEngin>();
        }
        filtredPointageEngin = null;
    }

    public void initRechercheDialog() {
        chantierSelect = -1;
        recherchePointageEngin = new PointageEngin();
        marque = "";
        date_start = null;
        date_to = null;
        isReadyToShowDateTo = true;
    }

    public void initDateTo() {
        System.out.println("executing listener");
        isReadyToShowDateTo = false;
    }

    /*
     Boutton de recherche
     */
    public void search_chantier() {

        this.filtredPointageEngin = null;

        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        pointageEnginSerive = ctx.getBean(PointageEnginServices.class);

        this.pointageEngin = pointageEnginSerive.RechercheEngin(null, null, null, null, null, chantierSelectHome);
    }

    public PointageEngin recherchePointage(PointageEngin pointageEngin) {
        return pointageEngin;
    }

    public void viewPointageEngin() {
        RequestContext.getCurrentInstance().openDialog("/search.xhtml");
    }

    public void viewDialog() {
        Map<String, Object> options = new HashMap<String, Object>();

        RequestContext.getCurrentInstance().openDialog("dialog", options, null);

    }

    /**
     * COnstruit une liste de pointage engin à partir des engin dans la BD
     *
     * @param reload
     * @return List<PointageEngin>
     */
    public void getPointageEnginByEngin(Boolean reload) {

        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        enginSerive = ctx.getBean(EnginService.class);
        pointageEnginSerive = ctx.getBean(PointageEnginServices.class);

        this.lpe = new LinkedList<PointageEngin>();
        List<Engin> le = new LinkedList<>();

        if (this.chantierSelect > 0) {
            System.out.println("this.chantierSelect > 0 ");
            this.last_day_pointed = pointageEnginSerive.lastDayPointed(chantierSelect);
            System.out.println("this.last_day_pointed 1 : "+this.last_day_pointed);

            Calendar cal_pointage = Calendar.getInstance();
            if (this.last_day_pointed == null) {
                try {
                    this.last_day_pointed = Constante.get_first_day_in_pointage_Engin_forAllApplication();
                    cal_pointage.setTime(this.last_day_pointed);
                } catch (ParseException ex) {
                    System.out.println("erreur parsing date " + ex.getMessage());
                }
            } else {
                cal_pointage.setTime(this.last_day_pointed);
            }
            
            cal_pointage.add(Calendar.DATE, 1);
            this.last_day_pointed = cal_pointage.getTime();
            System.out.println("this.last_day_pointed 2 : "+this.last_day_pointed);
            
            //on verifie si on a dejà pointé le chantier pour ce jour
            Boolean havePointed = pointageEnginSerive.havePointed(chantierSelect, date);

            System.out.println("havePointed : "+havePointed);
            if (havePointed == false) {
                le = enginSerive.findAllChantierArchivePanne(this.chantierSelect);
                if (le == null || le.size() <= 0) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, Message.AFFECT_ENGIN_CHANTIER, ""));
                }
                zones = zoneService.findByChantierID(chantierSelect);
            } else if (reload.equals(Boolean.FALSE)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, Message.POINTAGE_DEJA_AFFECTED, ""));
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, Message.NO_ENGIN_SELECTED, ""));
        }
        
        /*
        Avant modification du 22/11/2018
        this.libelleZone = new LinkedList<>();
        if (le != null) {
                Lot l = new Lot();
                PointageEngin pe = new PointageEngin();
            for (int i = 0; i < le.size(); i++) {
                pe =  new PointageEngin();
                pe.setZoneList(new LinkedList<Zone>());
                pe.setIdLot(l);
                pe.setIDEngin(le.get(i)); 
           //on recupère l'inforamtion du dernier pointage
                PointageEngin lastPE = pointageEnginSerive.lastPointageEngin(chantierSelect, le.get(i).getIDEngin());
                if (lastPE != null) {
                    pe.setNbrHeures(lastPE.getNbrHeures() != null ? lastPE.getNbrHeures() : le.get(i).getComteurHoraire());
                    pe.setNbrKm(lastPE.getNbrKm() != null ? lastPE.getNbrKm() : le.get(i).getCompteurKilometrique());
                    pe.setLibLot(lastPE.getLibLot());
                    if (lastPE.getZoneList() != null) {
                        for (Zone z : lastPE.getZoneList()) {
                            libelleZone.add(z.getLibeleZone());
                        }
                    }
                    System.out.println("");

                } else {
                
                    pe.setNbrHeures(le.get(i).getComteurHoraire());
                    pe.setNbrKm(le.get(i).getCompteurKilometrique());

                //}
                String[] temp_s = new String[1];
                for (Zone z : zones) { 
                    if (z.getLibeleZone().compareToIgnoreCase("Zone_1") == 0) { 
                        temp_s[0] = z.getIdZone().toString();
                        pe.setZones_str(temp_s);
                    }
                }
        
        

        this.libelleZone = new LinkedList<>();
        for (Zone z : zones) {
            if (!this.libelleZone.contains(z.getLibeleZone())) {
                libelleZone.add(z.getLibeleZone());
            }
        } */
        if (le != null) {

            String[] temp_s = new String[1];
            //temp_s[0] = zones.get(0).getIdZone().toString();
            PointageEngin pe = new PointageEngin();
            //Lot l = new Lot();
            for (int i = 0; i < le.size(); i++) {
                pe = new PointageEngin();
                //pe.setZoneList(new LinkedList<Zone>());
                //pe.setIdLot(l);
                pe.setIDEngin(le.get(i));
                pe.setNbrHeures(le.get(i).getComteurHoraire());
                pe.setNbrKm(le.get(i).getCompteurKilometrique());
                pe.setZones_str(temp_s);
                lpe.add(pe);
            }
            lpeF.clear();
        }
    }

    public void onPageChange(PageEvent event) {
        System.out.println("PAGE CHANGED");
        pageChangePEValues = new LinkedList<>(lpe);
    }

    public void setLotChoisi(PointageEngin i) {
        System.out.println("LOT : " + i.getLibLot());
    }

    public void setEtatRadio(PointageEngin i) {
        System.out.println("ETAT : " + i.getIDEngin().getEtat());
    }

    public void setEtatTransfer(PointageEngin i) {
        //System.out.println("ETAT TRANSFER : " + i.getIDEngin().getEtatTransfert());
        isTranfer = i.getIDEngin().getEtatTransfert();

    }

//    public void roundDouble(Double d){
//    
//    return round(d, 2);
//    
//    }
//    
//    public  double round(double value, int places) {
//    if (places < 0) throw new IllegalArgumentException();
//
//    long factor = (long) Math.pow(10, places);
//    value = value * factor;
//    long tmp = Math.round(value);
//    return (double) tmp / factor;
//}
    /**
     * Enregistre les pointage dans la BD
     */
    public void validate() {
        //System.out.println("ma.bservices.tgcc.mb.Engin.PointageEnginMb.validate()");
        Boolean havePointed = pointageEnginSerive.havePointed(chantierSelect, date);
        
        if (havePointed == true) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "", Message.ERROR_DONNEE_PE));
        } else {
            if (lpe.size() > 0) {
                DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                String date_format = df.format(date);
                /**
                 * 1: Si ETAT POINTAGE = EN Marche   ::::> Compteurs aujourdhui > Compteur de la veille.      
                 * 2: SI ETAT POINTAGE = EN Panne ou NON Receptionne :::::> le Compteur  >= Compteur engin. 
                 * 3: Le compteur ne doit pas changer si Etat engin inactif. 
                 * 4: La différence entre compteur horaire  précédent et le compteur horaire d’aujourd'hui Doit être <= 24h. 
                 * 5: La différence du compteur Km <= 1000 km. 
                 * 6: La fiche d'engin doit contenir dernier compteur horaire et kilométrique.
                 *
                 */

                Engin enginPourTest = new Engin();
                boolean verif = true, verif2 = true;
                List<PointageEngin> lpev = new ArrayList<PointageEngin>();
                messageErrorPointage = ""; 
                 for (PointageEngin p : lpe) {
                    //System.out.println("Dans la boucle de la liste des pointages des engins p: "+p.toString());
                   
                    if (p != null) {
                        // System.out.println("p:"+p.toString());
                        try {
                            verif2 = verifEnginPointage(p);
                        } catch (Exception e) {
                            verif2 = false;
                            System.err.println("Erreur de validation pointage engin ! car : "+e.getMessage());
                        } 
                    }
                      //   System.out.println("verif2:"+verif2);
                    
                    //System.out.println("==================> verif2 : "+verif2);
                    if (verif2) {
                       lpev.add(p);
                    }else{
                       lpeF.add(p);
                    }
                }
                lpe = lpev; 
                    if (lpe.size()<1 && lpeF.size()>0 ) {
                        
                    lpe=lpeF;
                    for (PointageEngin p : lpe) {
                        enginPourTest = p.getIDEngin();
                        
                        compteurEngin = compteurEnginService.findCompteurrEnginByDate(this.date);
                        try {
                            if(p.getIDEngin().getNbrKilometragePointe()!=null){
                                p.setNbrJKm((p.getNbrKm()+((compteurEngin.getOldCptKReel()!=null)?compteurEngin.getOldCptKReel():0))- p.getIDEngin().getNbrKilometragePointe());  
                            }else{
                                p.setNbrJKm(p.getNbrKm()); 
                            }
                        } catch (Exception e) {
                            System.out.println("Erreur de calculer nombre Km pointe : " + e.getMessage());
                        }
                        try {
                            if(p.getIDEngin().getNbrHeuresPointe()!=null){
                                p.setNbrJHeures((p.getNbrHeures()+((compteurEngin.getOldCptHReel()!=null)?compteurEngin.getOldCptHReel():0))-p.getIDEngin().getNbrHeuresPointe());
                             }else{
                                p.setNbrJKm(p.getNbrHeures()); 
                            }
                        } catch (Exception e) {
                            System.out.println("Erreur de calculer nombre H pointe : " + e.getMessage());
                        }
                        try {
                            p.getIDEngin().setCompteurKilometrique(p.getNbrKm());
                            p.getIDEngin().setNbrKilometragePointe(p.getNbrKm());
                        } catch (Exception e) {
                            System.out.println("Erreur au niveau nombre kilométrique car : " + e.getMessage());
                        }
                        try {
                            p.getIDEngin().setComteurHoraire(p.getNbrHeures());
                            p.getIDEngin().setNbrHeuresPointe(p.getNbrHeures());
                        } catch (Exception e) {
                            System.out.println("Erreur au niveau nombre des heurs car : " + e.getMessage());
                        }
                        
                        enginSerive.updateArchiveEngin(p.getIDEngin());
                        pointageEnginSerive.updateEtat(p.getIDEngin());
                        p.setChantierPointage(p.getIDEngin().getPrjapId());
                    }

                    pointageEnginSerive.pointageEnginAction(lpe, this.date);
                    
                    int sizePointage = lpe.size();
                    // FacesContext context = FacesContext.getCurrentInstance();
                    String message = sizePointage + Message.ADD_PE_BYDATE + date_format;
                    Module.message(1, message, "");
                    //context.addMessage(null, new FacesMessage("" + message, ""));
                    getPointageEnginByEngin(Boolean.TRUE);
                    filter();
                    }else {
                    //System.out.println("messageErrorPointage : " + messageErrorPointage);
                    //FacesContext context = FacesContext.getCurrentInstance();
                    messageErrorPointage=messageErrorPointage.substring(0, messageErrorPointage.indexOf("."));
                    String message = Message.ERROR_DONNEE_PE + date_format + " " + messageErrorPointage;
                   // Module.message(1, message, "OK");
                    RequestContext.getCurrentInstance().execute("PF('dlgMsgErreur').show();");
                    
                }
                    /*
                    System.out.println("==================> lpev : "+lpev);
                    System.out.println("==================> lpeF : "+lpeF); 
                    */
                
            }
            
        }
    }
    
    public void annulerPointageEnginList(PointageEngin p){
        lpe.add(p);
        lpeF.remove(p);
    }

    public String zoneFromList(Integer idPointage) {

        List<Zone> listOfZones = pointageEnginSerive.findAllZones(idPointage);
        String zones = "";
        if (listOfZones != null) {
            for (Zone zone : listOfZones) {
                zones += zone.getLibeleZone() + ",";
            }
        }
        //zones.replace(zones.charAt(zones.length() - 1), ' ');
        return zones;
    }

    public void filter() {
        //System.out.println("==============> 1 ");
        getPointageEnginByEngin(Boolean.FALSE);
        //System.out.println("==============> 2 ");
        chantierPointageAff = chantierAffiniteService.findByChantier(chantierSelect);
        //System.out.println("==============> 3 ");

        this.enginLoaded = true;
        //   chantierPointageAff = chantierService.findBy_ChantierAff_Id_(chantierSelect);

    }

    public void changeChantier() {

        this.enginLoaded = false;
    }

    public void chafSelected(PointageEngin _pe) {
        System.out.println("CHAF CHANGED...");
    }

    /**
     * to display the list of selected zone in pointage list
     *
     * @param _pe
     */
    public void zoneSelected(PointageEngin _pe) {

        int row = lpe.indexOf(_pe);

//        Integer rowIndex = (Integer) ae.getComponent().getNamingContainer().getAttributes().get("idTable");
        String zone_to_display = "";

        for (int i = 0; i < lpe.get(row).getZones_str().length; i++) {

            id = lpe.get(row).getZones_str()[i];

            for (int j = 0; j < zones.size(); j++) {
                if (zones.get(j).getIdZone().equals(new Integer(id))) {
                    zone_to_display += zones.get(j).getLibeleZone() + " ";
                }
            }

//            zone_to_display += "" + zones.get(Integer.parseInt(id) - 1).getLibeleZone() + " ";
        }
        this.libelleZone.set(row, zone_to_display);

    }

    public void updateDate() {
        this.filter();
    }

    public boolean verifEnginPointage(PointageEngin p) {
        /**
         * 1. Si ETAT POINTAGE = EN Marche   ::::> Compteurs aujourdhui >
         * Compteur de la veille        2. SI ETAT POINTAGE = EN Panne ou NON
         * Receptionne :::::> le Compteur  >= Compteur engin 3. Le compteur ne
         * doit pas changer si Etat engin inactif 4. La différence entre
         * compteur horaire  précédent et le compteur horaire d’aujourd'hui Doit
         * être < = 24h   5. La différence du compteur Km <= 1000 km 6. La fiche
         * d'engin doit contenir dernier compteur horaire et kilométrique.
         *
         */

        boolean verif = false;
        if (p.getiDEngin().getTypeCompteur() != null) {
            
            Engin e = enginSerive.findOneById(p.getIDEngin().getIDEngin());
            /*
            System.out.println("================> p.getEtatEngin() " + p.getEtatEngin());
            System.out.println("================> (p.getiDEngin().getTypeCompteur() " + p.getiDEngin().getTypeCompteur());
            System.out.println("================> (p.getiDEngin().getTypeCompteur()=='kilométrique_horaire') " + (p.getiDEngin().getTypeCompteur().equals("kilométrique_horaire")));
            System.out.println("================> (p.getiDEngin().getTypeCompteur()=='kilométrique') " + (p.getiDEngin().getTypeCompteur().equals("kilométrique")));
            System.out.println("================> (p.getiDEngin().getTypeCompteur()=='horaire') " + (p.getiDEngin().getTypeCompteur().equals("horaire")));

            System.out.println("================> Engin e tostring " + e.toString());
            System.out.println("================> Engin p tostring " + p.getiDEngin().toString());
            System.out.println("================> PointageEngin p " + p.toString());
            System.out.println("================> verifEnginPointage : TypeCompteur() " + p.getiDEngin().getTypeCompteur());
            System.out.println("================> verifEnginPointage : p.getIDEngin().getEtat()  " + p.getIDEngin().getEtat());
            System.out.println("================> verifEnginPointage : p.getNbrHeures() " + p.getNbrHeures());
            System.out.println("================> verifEnginPointage : p.getNbrKm() " + p.getNbrKm());
            System.out.println("================> verifEnginPointage : p.getIDEngin().getComteurHoraire() " + p.getIDEngin().getComteurHoraire());
            System.out.println("================> verifEnginPointage : p.getIDEngin().getCompteurKilometrique() " + p.getIDEngin().getCompteurKilometrique());
             */
            if (p.getNbrHeures() == null || p.getNbrKm() == null) {
                messageErrorPointage += "NbrHeures ou NbrKm ou Lot n'est Valide pour l'engin " + p.getiDEngin().getCode();
                //System.out.println("messageErrorPointage :"+messageErrorPointage);
                return true;
            }
            //System.out.println("=======> p.getiDEngin().getTypeCompteur() : "+p.getiDEngin().getTypeCompteur());

            if (p.getiDEngin().getTypeCompteur().equals("kilométrique_horaire")) {

                if (p.getIDEngin().getEtat().equals("EN_MARCHE") && p.getNbrKm() <= p.getIDEngin().getCompteurKilometrique()) {
                    messageErrorPointage += "L'engin " + p.getiDEngin().getCode() + " est 'EN_MARCHE' alors que le compteur saisi est inférieur ou égal au compteur de la veille (KM).";
                    //System.out.println("messageErrorPointage :"+messageErrorPointage);
                    verif = true;
                }
                if (p.getIDEngin().getEtat().equals("EN_MARCHE") && p.getNbrHeures() <= p.getIDEngin().getComteurHoraire()) {
                    messageErrorPointage += "L'engin " + p.getiDEngin().getCode() + " est 'EN_MARCHE' alors que le compteur saisi est inférieur ou égal au compteur de la veille (H).";
                    //System.out.println("messageErrorPointage :"+messageErrorPointage);
                    verif = true;
                }
                if ((p.getIDEngin().getEtat().equals("EN_PANNE") || p.getIDEngin().getEtat().equals("NON_RECEPTIONNE")) && p.getNbrKm() < p.getIDEngin().getCompteurKilometrique()) {
                    messageErrorPointage += "Le compteur kilométrique saisie supérieur ou éguale au celle de l'engin " + p.getiDEngin().getCode() + " qui'est EN_PANNE ou NON_RECEPTIONNE.";
                    //System.out.println("messageErrorPointage :"+messageErrorPointage);
                    verif = true;
                }
                if ((p.getIDEngin().getEtat().equals("EN_PANNE") || p.getIDEngin().getEtat().equals("NON_RECEPTIONNE")) && p.getNbrHeures() < p.getIDEngin().getComteurHoraire()) {
                    messageErrorPointage += "Le compteur horaire saisie supérieur ou éguale au celle de l'engin " + p.getiDEngin().getCode() + " qui'est EN_PANNE ou NON_RECEPTIONNE.";
                    //System.out.println("messageErrorPointage :"+messageErrorPointage);
                    verif = true;
                }
                if (p.getIDEngin().getEtat().equals("INACTIF") && p.getIDEngin().getCompteurKilometrique() != p.getNbrKm()) {
                    messageErrorPointage += "L'engin  " + p.getiDEngin().getCode() + " est 'INACTIF' alors que le compteur saisi est différent du compteur de la veille (KM).";
                    //System.out.println("messageErrorPointage :"+messageErrorPointage);
                    verif = true;
                }
                if (p.getIDEngin().getEtat().equals("INACTIF") && p.getIDEngin().getComteurHoraire() != p.getNbrHeures()) {
                    messageErrorPointage += "L'engin  " + p.getiDEngin().getCode() + " est 'INACTIF' alors que le compteur saisi est différent du compteur de la veille (H).";
                    //System.out.println("messageErrorPointage :"+messageErrorPointage);
                    verif = true;
                }
                if ((p.getNbrKm() - p.getIDEngin().getCompteurKilometrique()) >= 1000) {
                    messageErrorPointage += "Le compteur saisi pour l'engin " + p.getiDEngin().getCode()+" est supérieur de 1000 Km par rapport à celui de la veille.";
                    //System.out.println("messageErrorPointage :"+messageErrorPointage);
                    verif = true;
                }
                if ((p.getNbrHeures() - p.getIDEngin().getComteurHoraire()) > 24) {
                    messageErrorPointage += "Le compteur saisi pour l'engin "+ p.getiDEngin().getCode()+" est supérieur de 24H par rapport à celui de la veille." ;
                    //System.out.println("messageErrorPointage :"+messageErrorPointage);
                    verif = true;
                }
            } else if (p.getiDEngin().getTypeCompteur().equals("kilométrique")) {

                if (p.getIDEngin().getEtat().equals("EN_MARCHE") && p.getNbrKm() <= p.getIDEngin().getCompteurKilometrique()) {
                    messageErrorPointage += "L'engin " + p.getiDEngin().getCode() + " est 'EN_MARCHE' alors que le compteur saisi est inférieur ou égal au compteur de la veille (KM).";
                    //System.out.println("messageErrorPointage :"+messageErrorPointage);
                    verif = true;
                }
                if ((p.getIDEngin().getEtat().equals("EN_PANNE") || p.getIDEngin().getEtat().equals("NON_RECEPTIONNE")) && p.getNbrKm() < p.getIDEngin().getCompteurKilometrique()) {
                    messageErrorPointage += "Le compteur kilométrique saisie supérieur ou éguale au celle de l'engin " + p.getiDEngin().getCode() + " qui'est EN_PANNE ou NON_RECEPTIONNE.";
                    //System.out.println("messageErrorPointage :"+messageErrorPointage);
                    verif = true;
                }
                if (p.getIDEngin().getEtat().equals("INACTIF") && p.getIDEngin().getCompteurKilometrique().intValue() != p.getNbrKm()) {
                    messageErrorPointage += "L'engin  " + p.getiDEngin().getCode() + " est 'INACTIF' alors que le compteur saisi est différent du compteur de la veille (KM).";
                    //System.out.println("messageErrorPointage :"+messageErrorPointage);
                    verif = true;
                }
                if ((p.getNbrKm() - p.getIDEngin().getCompteurKilometrique()) >= 1000) {
                    messageErrorPointage += "Le compteur saisi pour l'engin " + p.getiDEngin().getCode()+" est supérieur de 1000 Km par rapport à celui de la veille.";
                    //System.out.println("messageErrorPointage :"+messageErrorPointage);
                    verif = true;
                }
            } else if (p.getiDEngin().getTypeCompteur().equals("horaire")) {
                if (p.getIDEngin().getEtat().equals("EN_MARCHE") && p.getNbrHeures() <= p.getIDEngin().getComteurHoraire()) {
                    messageErrorPointage += "L'engin " + p.getiDEngin().getCode() + " est 'EN_MARCHE' alors que le compteur saisi est inférieur ou égal au compteur de la veille (H).";
                    //System.out.println("messageErrorPointage :"+messageErrorPointage);
                    verif = true;
                }
                if ((p.getIDEngin().getEtat().equals("EN_PANNE") || p.getIDEngin().getEtat().equals("NON_RECEPTIONNE")) && p.getNbrHeures() < p.getIDEngin().getComteurHoraire()) {
                    messageErrorPointage += "Le compteur horaire saisie supérieur ou éguale au celle de l'engin " + p.getiDEngin().getCode() + " qui'est EN_PANNE ou NON_RECEPTIONNE.";
                    //System.out.println("messageErrorPointage :"+messageErrorPointage);
                    verif = true;
                }
                if (p.getIDEngin().getEtat().equals("INACTIF") && p.getIDEngin().getComteurHoraire().intValue() != p.getNbrHeures()) {
                    messageErrorPointage += "L'engin  " + p.getiDEngin().getCode() + " est 'INACTIF' alors que le compteur saisi est différent du compteur de la veille (H).";
                    //System.out.println("messageErrorPointage :"+messageErrorPointage);
                    verif = true;
                }
                if ((p.getNbrHeures() - p.getIDEngin().getComteurHoraire()) > 24) {
                    messageErrorPointage += "Le compteur saisi pour l'engin "+ p.getiDEngin().getCode()+" est supérieur de 24H par rapport à celui de la veille." ;
                    //System.out.println("messageErrorPointage :"+messageErrorPointage);
                    verif = true;
                }
            }
        } else {
            messageErrorPointage += "Merci de préciser le type du compteur de l'engin " + p.getiDEngin().getCode()+".";
            //System.out.println("messageErrorPointage :"+messageErrorPointage);
            verif = true;
        }
        return verif;

    }

    /**
     *
     * @param code
     */
    public void getChantierByCode() {

        if (this.chantierSelect > 0) {
            this.chantier_selected_obj = chantierService.findById(this.chantierSelect);
        }

    }

    //getters and setters
    public List<String> getLibelleZone() {
        return libelleZone;
    }

    public String getLibelleZoneByPE(PointageEngin _pe) {

        int row = this.lpe.indexOf(_pe);

        return libelleZone.get(row);
    }

//    public void getLibelleLotByPE(PointageEngin _pe) {
//        System.out.println("POINTAGE ENGIN SELECTED: " + _pe.getIdLot().getLibelle());
//    }
    public void setLibelleZone(List<String> libelleZone) {
        this.libelleZone = libelleZone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<PointageEngin> getSearchButPointageEngin() {
        return searchButPointageEngin;
    }

    public void setSearchButPointageEngin(List<PointageEngin> searchButPointageEngin) {
        this.searchButPointageEngin = searchButPointageEngin;
    }

    public LotService getLotService() {
        return lotService;
    }

    public void setLotService(LotService lotService) {
        this.lotService = lotService;
    }

    public PointageEngin getRecherchePointageEngin() {
        return recherchePointageEngin;
    }

    public void setRecherchePointageEngin(PointageEngin recherchePointageEngin) {
        this.recherchePointageEngin = recherchePointageEngin;
    }

    public PointageEngin getValidatePE() {
        return validatePE;
    }

    public void setValidatePE(PointageEngin validatePE) {
        this.validatePE = validatePE;
    }

    public List<PointageEngin> getFiltredPointageEngin() {
        return filtredPointageEngin;
    }

    public void setFiltredPointageEngin(List<PointageEngin> filtredPointageEngin) {
        this.filtredPointageEngin = filtredPointageEngin;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public PointageEnginServices getPointageEnginSerive() {
        return pointageEnginSerive;
    }

    public int getChantierSelectHome() {
        return chantierSelectHome;
    }

    public ZoneServices getZoneService() {
        return zoneService;
    }

    public void setZoneService(ZoneServices zoneService) {
        this.zoneService = zoneService;
    }

    public List<Lot> getLots() {
        return lots;
    }

    public void setLots(List<Lot> lots) {
        this.lots = lots;
    }

    public List<Zone> getZones() {
        return zones;
    }

    public void setZones(List<Zone> zones) {
        this.zones = zones;
    }

    public void setChantierSelectHome(int chantierSelectHome) {
        this.chantierSelectHome = chantierSelectHome;
    }

    public EnginService getEnginSerive() {
        return enginSerive;
    }

    public void setEnginSerive(EnginService enginSerive) {
        this.enginSerive = enginSerive;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public Date getDate_start() {
        return date_start;
    }

    public void setDate_start(Date date_start) {
        this.date_start = date_start;
    }

    public Date getDate_to() {
        return date_to;
    }

    public void setDate_to(Date date_to) {
        this.date_to = date_to;
    }

    public List<PointageEngin> getPointageEngin() {
        return pointageEngin;
    }

    public int getSizeCus() {
        return sizeCus;
    }

    public List<PointageEngin> getLpe() {
        return lpe;
    }

    public void setLpe(List<PointageEngin> lpe) {
        this.lpe = lpe;
    }

    public void setPointageEnginSerive(PointageEnginServices pointageEnginSerive) {
        this.pointageEnginSerive = pointageEnginSerive;
    }

    public void setPointageEngin(List<PointageEngin> pointageEngin) {
        this.pointageEngin = pointageEngin;
    }

    public void setSizeCus(int sizeCus) {
        this.sizeCus = sizeCus;
    }

    public Chantier getChantier_selected_obj() {
        return chantier_selected_obj;
    }

    public void setChantier_selected_obj(Chantier chantier_selected_obj) {
        this.chantier_selected_obj = chantier_selected_obj;
    }
    

}
