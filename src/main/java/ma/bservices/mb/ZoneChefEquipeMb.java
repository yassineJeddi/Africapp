package ma.bservices.mb;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import ma.bservice.tgcc.Constante.Constante;
import ma.bservices.beans.Chantier;

import ma.bservices.beans.Salarie;
import ma.bservices.beans.Zone;
import ma.bservices.constantes.Constantes;
import ma.bservices.mb.services.Module;
import ma.bservices.mb.services.SalarieServMb;
import ma.bservices.mb.services.ZoneMb;
//import ma.bservices.metier.SalarieManager;
//import ma.bservices.metier.SalarieManagerImpl;
import ma.bservices.services.ChantierService;
import ma.bservices.services.OrganigrameService;
import ma.bservices.services.SalarieService;
import ma.bservices.services.SalarieServiceIn;
import ma.bservices.services.ZoneServices;
import ma.bservices.tgcc.Entity.Organigrame;
import ma.bservices.tgcc.authentification.Authentification;
import org.primefaces.model.LazyDataModel;

import org.springframework.stereotype.Component;

@Component
@ManagedBean(name = "zoneChefEquipeMb")
@ViewScoped
public class ZoneChefEquipeMb implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    //@Resource(name = "salarieService")
    //@Autowired
    @ManagedProperty(value = "#{salarieService}")
    private SalarieService salarieService;

    @ManagedProperty(value = "#{salarieServiceIn}")
    private SalarieServiceIn salarieServiceIn;

    @ManagedProperty(value = "#{chantieService}")
    private ChantierService chantieService;
    private List<Chantier> nonAffectChantier;
    private Integer idchantier;
    
     @ManagedProperty(value = "#{organigrameService}")
    private OrganigrameService organigramService;

    @ManagedProperty(value = "#{zoneService}")
    private ZoneServices zoneServices;

    private Integer fontion;
    private Integer codeChantier;
    private Integer fonction;
    private Integer statut;
    private Integer etat;
    private Integer pointureID, tailleID, giletID, casqueID, typeID, civiliteID;
    private String chantier = "";
    private LazyDataModel<Salarie> lazyModel;
    private Salarie findSalarie = new Salarie();
    /**
     * Partie Evols
     */
    private List<Chantier> chantiersBySalarie = new ArrayList<>();
    private List<Salarie> salarieChefEquipe = new ArrayList<>();
    private List<Zone> zones = new ArrayList<>();
    private List<Chantier> chantiers = new ArrayList<>();
    private Integer idChantier;
    private Integer idZone;
    private Salarie salaroeToAffect = new Salarie();
    private SalarieServMb salarieMb;
    private Integer i;
    private List<String> idZones;
    private String zonesString;
    /**
     * partie Consultation
     */
    private Salarie selectedSalarie;
    private Zone selectedZone;
    private List<Chantier> chantierWithZone;
    private ZoneMb zoneMb;

    /*
     getter setter
     */
    public Salarie getSelectedSalarie() {
        return selectedSalarie;
    }

    public void setSelectedSalarie(Salarie selectedSalarie) {
        this.selectedSalarie = selectedSalarie;
    }

    public Zone getSelectedZone() {
        return selectedZone;
    }

    public void setSelectedZone(Zone selectedZone) {
        this.selectedZone = selectedZone;
    }

    public List<Chantier> getChantierWithZone() {
        return chantierWithZone;
    }

    public void setChantierWithZone(List<Chantier> chantierWithZone) {
        this.chantierWithZone = chantierWithZone;
    }

    /**
     * ***************************
     *
     * @return
     */
    public Salarie getSalaroeToAffect() {
        return salaroeToAffect;
    }

    public void setSalaroeToAffect(Salarie salaroeToAffect) {
        this.salaroeToAffect = salaroeToAffect;
    }

    public SalarieServMb getSalarieMb() {
        return salarieMb;
    }

    public Integer getCodeChantier() {
        return codeChantier;
    }

    public void setCodeChantier(Integer codeChantier) {
        this.codeChantier = codeChantier;
    }
    
    

    public void setSalarieMb(SalarieServMb salarieMb) {
        this.salarieMb = salarieMb;
    }

    public OrganigrameService getOrganigramService() {
        return organigramService;
    }

    public void setOrganigramService(OrganigrameService organigramService) {
        this.organigramService = organigramService;
    }
    
    

    public List<String> getIdZones() {
        return idZones;
    }

    public void setIdZones(List<String> idZones) {
        this.idZones = idZones;
    }

    public String getZonesString() {
        return zonesString;
    }

    public void setZonesString(String zonesString) {
        this.zonesString = zonesString;
    }

    public Integer getTypeID() {
        return typeID;
    }

    public void setTypeID(Integer typeID) {
        this.typeID = typeID;
    }

    public Integer getCiviliteID() {
        return civiliteID;
    }

    public void setCiviliteID(Integer civiliteID) {
        this.civiliteID = civiliteID;
    }

    public Integer getIdZone() {
        return idZone;
    }

    public void setIdZone(Integer idZone) {
        this.idZone = idZone;
    }

    public ZoneServices getZoneServices() {
        return zoneServices;
    }

    public void setZoneServices(ZoneServices zoneServices) {
        this.zoneServices = zoneServices;
    }

    public List<Zone> getZones() {
        return zones;
    }

    public void setZones(List<Zone> zones) {
        this.zones = zones;
    }

    public Integer getIdChantier() {
        return idChantier;
    }

    public void setIdChantier(Integer idChantier) {
        this.idChantier = idChantier;
    }

    public List<Chantier> getChantiersBySalarie() {
        return chantiersBySalarie;
    }

    public void setChantiersBySalarie(List<Chantier> chantiersBySalarie) {
        this.chantiersBySalarie = chantiersBySalarie;
    }

    public List<Chantier> getChantiers() {
        return chantiers;
    }

    public void setChantiers(List<Chantier> chantiers) {
        this.chantiers = chantiers;
    }

    public List<Salarie> getSalarieChefEquipe() {
        return salarieChefEquipe;
    }

    public void setSalarieChefEquipe(List<Salarie> salarieChefEquipe) {
        this.salarieChefEquipe = salarieChefEquipe;
    }

    public SalarieServiceIn getSalarieServiceIn() {
        return salarieServiceIn;
    }

    public void setSalarieServiceIn(SalarieServiceIn salarieServiceIn) {
        this.salarieServiceIn = salarieServiceIn;
    }

    public Integer getFonction() {
        return fonction;
    }

    public void setFonction(Integer fonction) {
        this.fonction = fonction;
    }

    public Integer getIdchantier() {
        return idchantier;
    }

    public void setIdchantier(Integer idchantier) {
        this.idchantier = idchantier;
    }

    public List<Chantier> getNonAffectChantier() {
        return nonAffectChantier;
    }

    public void setNonAffectChantier(List<Chantier> nonAffectChantier) {
        this.nonAffectChantier = nonAffectChantier;
    }

    public Integer getPointureID() {
        return pointureID;
    }

    public void setPointureID(Integer pointureID) {
        this.pointureID = pointureID;
    }

    public Integer getTailleID() {
        return tailleID;
    }

    public void setTailleID(Integer tailleID) {
        this.tailleID = tailleID;
    }

    public Integer getGiletID() {
        return giletID;
    }

    public void setGiletID(Integer giletID) {
        this.giletID = giletID;
    }

    public Integer getCasqueID() {
        return casqueID;
    }

    public void setCasqueID(Integer casqueID) {
        this.casqueID = casqueID;
    }

    public Integer getFontion() {
        return fontion;
    }

    public void setFontion(Integer fontion) {
        this.fontion = fontion;
    }

    public Integer getStatut() {
        return statut;
    }

    public void setStatut(Integer statut) {
        this.statut = statut;
    }

    public Integer getEtat() {
        return etat;
    }

    public void setEtat(Integer etat) {
        this.etat = etat;
    }

    public String getChantier() {
        return chantier;
    }

    public void setChantier(String chantier) {
        this.chantier = chantier;
    }

    public ChantierService getChantieService() {
        return chantieService;
    }

    public void setChantieService(ChantierService chantieService) {
        this.chantieService = chantieService;
    }

    public Salarie getFindSalarie() {
        return findSalarie;
    }

    public void setFindSalarie(Salarie findSalarie) {
        this.findSalarie = findSalarie;
    }

    public SalarieService getSalarieService() {
        return salarieService;
    }

    public void setSalarieService(SalarieService salarieService) {
        this.salarieService = salarieService;
    }

    public LazyDataModel<Salarie> getLazyModel() {
        return lazyModel;
    }

    public void setLazyModel(LazyDataModel<Salarie> lazyModel) {
        this.lazyModel = lazyModel;
    }

    private List<Salarie> salaries;
    Constantes constantes = Constantes.getInstance();

//    SalarieManager salarieManager = new SalarieManagerImpl();
    public List<Salarie> getSalaries() {
        return salaries;
    }

    public void setSalaries(List<Salarie> salaries) {
        this.salaries = salaries;
    }
    private Integer page;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getI() {
        return i;
    }

    public void setI(Integer i) {
        this.i = i;
    }

    /**
     * *********
     * Methodes
     */
    @PostConstruct
    public void init() {
        System.out.println("___faces ___ " + FacesContext.getCurrentInstance().toString());
        salarieService = Module.ctx.getBean(SalarieService.class);
        chantieService = Module.ctx.getBean(ChantierService.class);
        zoneServices = Module.ctx.getBean(ZoneServices.class);
        organigramService = Module.ctx.getBean(OrganigrameService.class);
        /**
         *
         */
        salarieServiceIn = Module.ctx.getBean(SalarieServiceIn.class);
//        salarieChefEquipe.addAll(salarieServiceIn.getSalarieChefEquipe());
        //salarieChefEquipe.addAll(salarieServiceIn.getMensuelChefEquipe());
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        salarieMb = (SalarieServMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "salarieServMb");
       // salarieChefEquipe = salarieMb.getListCE();
        zoneMb = (ZoneMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "zoneMb");
        zones = zoneMb.getZones();
        zonesString = "Zones";
        
        
        
        
    }

    
    public void findByChantier(){
        
      // traitement organigrame :: ajoute les salaries marqués chefs d'équipe au niveau de l'organigrame a la liste des chefs d'équipe
            Chantier chantierOrg = chantieService.getChantier(codeChantier);
            List<Organigrame> orgs = organigramService.findByChantierChef(chantierOrg);
            salarieChefEquipe = new LinkedList<>();
            if (orgs == null) {
                salarieChefEquipe = new LinkedList<>();
            } else {
                for (Organigrame org : orgs) {
                    System.out.println("CHANTIER : " + chantierOrg.getCode());
                    System.out.println("CHEF : " + org.getSalarie().getNom());
                    if (!salarieChefEquipe.contains(org.getSalarie())) {
                        salarieChefEquipe.add(org.getSalarie());
                    }
                }
            }
    
    
    
    }
    /**
     * Partie Evols
     */
    public void listeZoneByChantier() {
        System.out.println("<<<<<<<< ID CHANTIER *********** " + idChantier);
        zones = zoneServices.findByChantierID(idChantier);
        System.out.println("@@@@@@@@ ZONE ID @@@@@@@@@@@@ " + zones.size());
    }

    /**
     * Partie Evols
     */
    public void SelectedZonesString() {
        if (!idZones.isEmpty()) {
            zonesString = "";
            for (String s : idZones) {
                for (Zone z : zones) {
                    if (z.getIdZone().equals(Integer.parseInt(s))) {
                        zonesString += z.getLibeleZone() + ",";
                        break;
                    }
                }
            }
            zonesString = zonesString.substring(0, zonesString.length() - 1);
        } else {
            zonesString = "Zones";
        }

    }

    public String affectSalarietoZone() {
        Salarie salarie = salarieService.getSalarie(salaroeToAffect.getId());
//        List<Zone> result = new LinkedList<>();
        for (String s : idZones) {
            Zone z = zoneServices.getbyId(Integer.parseInt(s));
            if (z != null) {
                if (!salarie.getZones().contains(z)) {
                    salarie.getZones().add(z);
                }
            }
        }
        //  salaroeToAffect.setZones(result);
        if (salarieService.modifierInformationsSalarie(salarie)) {
            idZones.clear();
            zonesString = "Zones";
            Module.message(0, idZones.size() + " zones Associé au Chef d'équipe ", "" + salaroeToAffect.getNom() + " " + salaroeToAffect.getPrenom());
        } else {
            Module.message(2, "opération échouée", "");
        }
        return "salarieChefEquipe.xhtml?faces-redirect=true";
    }

    public void rechercheSalarie() {
        i = Integer.parseInt(salarieService.nombreAllSalaries(findSalarie.getMatricule(), Constante.FONCTION_ID_CHEF_EQUIPE, etat, findSalarie.getCin(), findSalarie.getNom(), findSalarie.getPrenom(), findSalarie.getCnss(), chantier, findSalarie.getMatriculeDivalto()) + "");
        System.out.println("i" + i);
        salarieChefEquipe = salarieService.listeAllSalaries(0, i, findSalarie.getMatricule(), Constante.FONCTION_ID_CHEF_EQUIPE, etat, findSalarie.getCin(), findSalarie.getNom(), findSalarie.getPrenom(), findSalarie.getCnss(), chantier, findSalarie.getMatriculeDivalto());
    }

    public void chantiersOf() {
        boolean isAdmin = false;
        if ("admin".equals(Constantes.getRoleAuth()) || "EMAIL_CONTRIBUTORS".equals(Constantes.getRoleAuth())) {
            isAdmin = true;
        }
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        Authentification authentification = (Authentification) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "authentification");
        Integer idUser = authentification.get_connected_user().getId();
        chantiersBySalarie = chantieService.listeChantiersAffectes(salaroeToAffect.getId(), 1, 0, Integer.parseInt(chantieService.nombreChantiers("", "", Module.dos).toString()), "", "", Module.dos, isAdmin, idUser);
    }

    public void zoneByChantier() {
        zones = (idChantier != null) ? zoneServices.findByChantierID(idChantier) : zoneMb.getZones();
        salaries = salarieService.listeSalaries(0, salarieMb.getNbrCE(), "", null, Constante.FONCTION_ID_CHEF_EQUIPE, null, "", "", "", "", idChantier + "", "");
    }

    public void salarieByZone() {
        Zone zone = zoneServices.getbyId(selectedZone.getIdZone());
        salaries = zone.getSalaries();
    }

    public void desaffecter(Zone z) {
        Salarie s = salarieService.getSalarie(selectedSalarie.getId());
        boolean remove = s.getZones().remove(z);
        try {
            if (remove) {
                salarieService.modifierInformationsSalarie(s);
                zoneBySalarie(s);
                Module.message(0, "désaffectation effectuée", "");
            }
        } catch (Exception ex) {
            Module.message(3, "Oups ! erreur", "");
        }
    }

    public void zoneBySalarie(Salarie sal) {
        //zones = new LinkedList<>();
        Salarie s = salarieService.getSalarie(sal.getId());
        selectedSalarie = s;
        chantierWithZone = s.getChantiers();
        for (Chantier c : chantierWithZone) {
            //zones.clear();
            for (Zone z : zoneServices.findByChantierID(c.getId())) {
                Zone zone = zoneServices.getbyId(z.getIdZone());
                for (Salarie s1 : zone.getSalaries()) {
                    if (s1.getId().equals(sal.getId())) {
                        c.getZoneOfChefEquipe().add(zone);
                        break;
                    }
                }
            }
        }
        System.out.println("Salarie Chef With Chantier ");
    }

}
