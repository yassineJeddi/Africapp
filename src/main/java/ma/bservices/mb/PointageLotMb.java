/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.mb;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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
import javax.faces.model.ArrayDataModel;
import javax.faces.model.DataModel;
import ma.bservice.tgcc.Constante.Constante;
import ma.bservices.beans.Chantier;
import ma.bservices.beans.FichePointageLot;
import ma.bservices.beans.Fonction;
import ma.bservices.beans.Lot;
import ma.bservices.beans.PointageLot;
import ma.bservices.beans.Salarie;
import ma.bservices.beans.Zone;
import ma.bservices.mb.services.ConstanteMb;
import ma.bservices.mb.services.Module;
import ma.bservices.services.AffectationSSuppService;
import ma.bservices.services.ChantierService;
import ma.bservices.services.Evol_LotService;
import ma.bservices.services.PointageLotService;
import ma.bservices.services.PresenceService;
import ma.bservices.services.SalarieService;
import ma.bservices.services.ZoneServices;
import ma.bservices.services.FichePLService;
import ma.bservices.services.OrganigrameService;
import ma.bservices.services.ParametrageService;
import ma.bservices.services.SalarieServiceIn;
import ma.bservices.tgcc.Entity.Mensuel;
import ma.bservices.tgcc.Entity.Organigrame;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import static org.castor.mapping.AbstractMappingLoaderFactory.LOG;
import org.primefaces.model.UploadedFile;
import org.springframework.stereotype.Component;

/**
 *
 * @author Mahdi
 */
@Component
@ManagedBean(name = "pointageLotMb")
@ViewScoped
public class PointageLotMb implements Serializable {

    protected static Logger logger = Logger.getLogger(PointageLotMb.class);
    @ManagedProperty(value = "#{organigrameService}")
    private OrganigrameService organigramService;
    @ManagedProperty("#{zoneService}")
    private ZoneServices zoneServices;
    @ManagedProperty("#{lotServiceEvol}")
    private Evol_LotService lotService;
    @ManagedProperty("#{chantierServiceEvol}")
    private ChantierService chantierService;
    @ManagedProperty("#{salarieService}")
    private SalarieService salarieService;
    @ManagedProperty("#{pointageLotService}")
    private PointageLotService pointageLotService;
    @ManagedProperty(value = "#{presenceService}")
    private PresenceService presenceService;
    @ManagedProperty(value = "#{affectationSSuppService}")
    private AffectationSSuppService affectationSSuppService;
    @ManagedProperty(value = "#{fichePLService}")
    private FichePLService fichePLService;
    @ManagedProperty(value = "#{parametrageService}")
    private ParametrageService parametrageService;

    @ManagedProperty(value = "#{salarieServiceIn}")
    private SalarieServiceIn salarieServiceIn;
    
    @ManagedProperty(value = "#{chantierServiceEvol}")
    private ChantierService chantieService;

    private Integer idChantier, idChef;
    private Long codeFiche;
    private List<Salarie> chefs, salaries;
    private List<Zone> zones;
    private List<Lot> lots;
    private Date datePointage;
    private List<FichePointageLot> fiches;
    private UploadedFile uploadedFile;
    private DataModel<Salarie> salariesData;
    private String pdfview, btnPointage, messagePointage, messageConsult;
    private boolean visible;
    private boolean errChefE;
    /**
     * chemin de la fiche du pointage après la génération de la fiche
     */
    private String cheminFiche;
    private boolean ficheGenere = false;

    private boolean btnEtat; //si pointage existe le button sera inactif sinon il sera actif
    private List<Integer> idZones = new LinkedList<>();
    private String zonesString;
    private Integer tabId;

    @PostConstruct
    public void init() {
        tabId = 0;
        messageConsult = "";
        messagePointage = "";
        //System.out.println("init A");
        datePointage = new Date();
        ficheGenere = true;
        findCodeFiche();
        zoneServices = Module.ctx.getBean(ZoneServices.class);
        lotService = Module.ctx.getBean(Evol_LotService.class);
        salarieService = Module.ctx.getBean(SalarieService.class);
        chantierService = Module.ctx.getBean(ChantierService.class);
        pointageLotService = Module.ctx.getBean(PointageLotService.class);
        affectationSSuppService = Module.ctx.getBean(AffectationSSuppService.class);
        presenceService = Module.ctx.getBean(PresenceService.class);
        organigramService = Module.ctx.getBean(OrganigrameService.class);
        parametrageService = Module.ctx.getBean(ParametrageService.class);
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        ma.bservices.mb.services.LotMb lotMb = (ma.bservices.mb.services.LotMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "Service_lots");
        lots = lotMb.getLots();
        salaries = new ArrayList<>();
        btnEtat = true;
    }
    
    public void valider() {
        /**
         * verifier si que tt les salariés et pointé en moins dans un lots
         */
        if (salaries.size()>0) {
            int nbrPointe = 0;
            for (Salarie s : salaries) {
                boolean check = false;
                for (int b = 0; b < s.getCheck().length; b++) {
                    if (s.getCheck()[b]) {
                        check = true;
                    }
                }
                if (check) {
                    nbrPointe++;
                }
            }
            if (nbrPointe == salaries.size()) {
                int cpt = 0;
                for (Salarie s : salaries) {
                    try {
                        if (s.getCheck().length > 0) {
                            PointageLot p = new PointageLot();
                            p.setDatePointage(datePointage);
                            p.setSalarie(s);
        //                    p.setZone(zoneServices.getbyId(s.getIdZone()));
                            List<Zone> zonePointe = new LinkedList<>();
                            for (String idZonePointe : s.getIdZone()) {
                                zonePointe.add(zoneServices.getbyId(Integer.parseInt(idZonePointe)));
                            }
                            p.setZones(zonePointe);
                            List<Lot> l = new ArrayList<>();
                            for (int b = 0; b < s.getCheck().length; b++) {
                                if (!s.getLotAutoriser()[b] && s.getCheck()[b]) {
                                    l.add(lots.get(b));
                                }
                            }
                            p.setLots(l);
                            FichePointageLot f = fichePLService.getByCode(codeFiche);
                            p.setFiche(f);
                            /**
                             * on verifier si le pointage déjà efféctuée : si oui
                             * notifier l'utilisateur sinon on effectue un nouveau
                             * pointage, la verification faite selon le salarie et la
                             * date de pointage
                             */
                            List<PointageLot> result = pointageLotService.recherche(datePointage, s, null);
                            
                            if (result.isEmpty()) {
                                cpt++;
                                pointageLotService.pointage(p);
                            } else {
                                Module.message(1, "le Salarié " + result.get(0).getSalarie().getNom() + " " + result.get(0).getSalarie().getPrenom() + " est déjà pointé", "");
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Erreur de pointage : "+e.getMessage());
                        Module.message(1, "Erreur de pointage merci de contacter l'administrateur", "");
                        btnEtat = false;
                        tabId = 0;
                    }
                }
                Module.message(0, "Pointage par Lot", "effectué pour " + cpt + " salaries");
                btnEtat = true;
                tabId = 1;
                messageConsult = "";
            } else {
                Module.message(1, "vous devez pointé tous les salariés", salaries.size() - nbrPointe + " salariés non pointé");
                btnEtat = false;
                tabId = 0;
            }
        }else {
                Module.message(1, "N'existe aucun salarié pour pointer", "");
                btnEtat = false;
                tabId = 0;
        }
        

    }

    /**
     * Quand on change de chantier
     */
    public void onChangeChantier() {
        salaries.clear();
        zones = zoneServices.findByChantierID(idChantier);
        errChefE = false;
        btnEtat = false;
        Chantier chantierOrg = chantierService.getChantier(idChantier);
        List<Organigrame> orgs = organigramService.findByChantierChef(chantierOrg);
        chefs = new LinkedList<>();
        if (orgs == null) {
            chefs = new LinkedList<>();
        } else {
            for (Organigrame org : orgs) {
                if (!chefs.contains(org.getSalarie())) {
                    chefs.add(org.getSalarie());
                }
            }
        }
        
        
//        chefs = salarieService.listeSalaries(0,
//                Integer.parseInt(salarieService.nombreSalaries(
//                                "", null, Constante.FONCTION_ID_CHEF_EQUIPE, null, "", "", "", "", idChantier.toString(), "").toString()
//                ),
//                "", null, Constante.FONCTION_ID_CHEF_EQUIPE, null, "", "", "", "", idChantier.toString(), "");
        onChangeChef();
    }

    public void onChangeChef() {
        salaries.clear();
        //System.out.println("_____ date selected " + datePointage);
        if (idChef != null) {
            errChefE = false;
            btnEtat = false;
            Salarie chef = salarieService.getSalarie(idChef);
            //System.out.println("ON CHANGE CHEF : " + chef.getNom());
            Chantier c = new Chantier();
            c.setId(idChantier);
            List<Zone> ZoneOfSalarieByChantier = zoneServices.zoneBy(chef, c);

            Chantier ch = chantieService.getChantier(idChantier);
            List<Salarie> sals = affectationSSuppService.salarieByLastSuperieurAndChantier(chef, ch);

           // System.out.println("Sals by Supp " + sals.size());

            /*CHECK PRESENCE SALARIE */
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            String d = sdf.format(datePointage);
            String heure = ch.getHeureEntree().substring(0, 2);
            String min = ch.getHeureEntree().substring(3, 5);

            heure = (heure.length() == 1) ? "0" + heure : heure;
            min = (min.length() == 1) ? "0" + min : min;

            String heuremin = heure + ":" + min;

            /**
             * ¨Partie de génération LongTimeEntree
             */
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd");
            String date1 = sdf1.format(datePointage);
            Date testDate = new Date(date1 + " " + heuremin);
            long longDateTimeEntree = testDate.getTime();

            /* END OF CHECK POINTAGE SALARIE ENTREE */
            for (Salarie s : sals) {
                Boolean pe = affectationSSuppService.pointageEntreeSalarie(s.getId(), longDateTimeEntree, d);
                if (pe == true) {
                    try {
                        List<String> idZoneInSalarie = new LinkedList<>();
                        //String strZone = "";
                        for (Zone z : this.zones) {
                            idZoneInSalarie.add(z.getIdZone() + "");
                            //  strZone += z.getLibeleZone() + ", ";
                        }
                        //s.setZonePointeConsult(strZone.substring(strZone.length() - 1));
                        s.setIdZone(idZoneInSalarie);
                        SelectedZonesString(s);
                        //t.setIdZone(ZoneOfSalarieByChantier.get(0).getIdZone());
                    } catch (Exception e) {
                        System.out.println("error : " + e.getMessage());
                    }
                    salaries.add(s);
                }
            } // FIN de la boucle sur les Salariés => sals
            //System.out.println("salarie pointé " + salaries.size());
            if (salaries != null) {
                boolean verifMessage = false;//on verifier si le msg et déjà afficher ou non
                for (Salarie t : salaries) {
                    t.setCheck(new boolean[lots.size()]);
                    t.setLotAutoriser(new boolean[lots.size()]);
                    Fonction f = parametrageService.getFonction(t.getFonction().getId());
                    List<Lot> l1 = (f.getLots().isEmpty())
                            ? (chef.getFonction().getLots().isEmpty()
                            ? null
                            : chef.getFonction().getLots())
                            : f.getLots();
                    if (l1 != null) {
                        for (int i = 0; i < lots.size(); i++) {
                            t.getLotAutoriser()[i] = true;
                        }
                        for (Lot l : l1) {
                            t.getLotAutoriser()[indexOfLot(l)] = false;
                        }
                    }
                    List<PointageLot> list = pointageLotService.recherche(datePointage, t, c);
                    /**
                     * attribuer la zone de chef d'équipe au salarié
                     */

                    if (!list.isEmpty()) {
                        List<String> idZoneInSalarie = new LinkedList<>();
                        //String strZone = "";
                        for (Zone z : list.get(0).getZones()) {
                            idZoneInSalarie.add(z.getIdZone() + "");
                            //strZone += z.getLibeleZone() + ", ";
                        }
                        //t.setZonePointeConsult(strZone.substring(strZone.length() - 1));
                        t.setIdZone(idZoneInSalarie);
                        SelectedZonesString(t);
                        for (Lot l : list.get(0).getLots()) {
                            t.getCheck()[indexOfLot(l)] = true;
                        }
                        errChefE = true;
                        btnEtat = true;
                        //System.out.println("pointage existe pour cette date");
                        if (!verifMessage) {
                            Module.message(1, "pointage effectueé dans cette date pour ce chantier et ce Chef D'équipe", "");
                            verifMessage = true;
                        }
                    } else {
                        btnEtat = false;
                    }
//                    SelectedZonesString(t);
                }
            }
            salariesData = new ArrayDataModel(salaries.toArray());
            ficheGenere = false;
        }
        findCodeFiche();
    }

    public void onChangeChef_old() {
        salaries.clear();
        //System.out.println("_____ date selected " + datePointage);
        if (idChef != null) {
            errChefE = false;
            btnEtat = false;
            Salarie chef = salarieService.getSalarie(idChef);
            //System.out.println("ON CHANGE CHEF : " + chef.getNom());
            Chantier c = new Chantier();
            c.setId(idChantier);
            List<Zone> ZoneOfSalarieByChantier = zoneServices.zoneBy(chef, c);

            Chantier ch = chantieService.getChantier(idChantier);
            List<Salarie> sals = affectationSSuppService.salarieByLastSuperieurAndChantier(chef, ch);

           // System.out.println("Sals by Supp " + sals.size());

            /*CHECK PRESENCE SALARIE */
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            String d = sdf.format(datePointage);
            String heure = ch.getHeureEntree().substring(0, 2);
            String min = ch.getHeureEntree().substring(3, 5);

            heure = (heure.length() == 1) ? "0" + heure : heure;
            min = (min.length() == 1) ? "0" + min : min;

            String heuremin = heure + ":" + min;

            /**
             * ¨Partie de génération LongTimeEntree
             */
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd");
            String date1 = sdf1.format(datePointage);
            Date testDate = new Date(date1 + " " + heuremin);
            long longDateTimeEntree = testDate.getTime();

            /* END OF CHECK POINTAGE SALARIE ENTREE */
            for (Salarie s : sals) {
                Boolean pe = affectationSSuppService.pointageEntreeSalarie(s.getId(), longDateTimeEntree, d);
                if (pe == true) {
                    try {
                        List<String> idZoneInSalarie = new LinkedList<>();
                        //String strZone = "";
                        for (Zone z : this.zones) {
                            idZoneInSalarie.add(z.getIdZone() + "");
                            //  strZone += z.getLibeleZone() + ", ";
                        }
                        //s.setZonePointeConsult(strZone.substring(strZone.length() - 1));
                        s.setIdZone(idZoneInSalarie);
                        SelectedZonesString(s);
                        //t.setIdZone(ZoneOfSalarieByChantier.get(0).getIdZone());
                    } catch (Exception e) {
                        System.out.println("error : " + e.getMessage());
                    }
                    salaries.add(s);
                }
            } // FIN de la boucle sur les Salariés => sals
            //System.out.println("salarie pointé " + salaries.size());
            if (salaries != null) {
                boolean verifMessage = false;//on verifier si le msg et déjà afficher ou non
                for (Salarie t : salaries) {
                    t.setCheck(new boolean[lots.size()]);
                    t.setLotAutoriser(new boolean[lots.size()]);
                    Fonction f = parametrageService.getFonction(t.getFonction().getId());
                    List<Lot> l1 = (f.getLots().isEmpty())
                            ? (chef.getFonction().getLots().isEmpty()
                            ? null
                            : chef.getFonction().getLots())
                            : f.getLots();
                    if (l1 != null) {
                        for (int i = 0; i < lots.size(); i++) {
                            t.getLotAutoriser()[i] = true;
                        }
                        for (Lot l : l1) {
                            t.getLotAutoriser()[indexOfLot(l)] = false;
                        }
                    }
                    List<PointageLot> list = pointageLotService.recherche(datePointage, t, c);
                    /**
                     * attribuer la zone de chef d'équipe au salarié
                     */

                    if (!list.isEmpty()) {
                        List<String> idZoneInSalarie = new LinkedList<>();
                        //String strZone = "";
                        for (Zone z : list.get(0).getZones()) {
                            idZoneInSalarie.add(z.getIdZone() + "");
                            //strZone += z.getLibeleZone() + ", ";
                        }
                        //t.setZonePointeConsult(strZone.substring(strZone.length() - 1));
                        t.setIdZone(idZoneInSalarie);
                        SelectedZonesString(t);
                        for (Lot l : list.get(0).getLots()) {
                            t.getCheck()[indexOfLot(l)] = true;
                        }
                        errChefE = true;
                        btnEtat = true;
                        //System.out.println("pointage existe pour cette date");
                        if (!verifMessage) {
                            Module.message(1, "pointage effectueé dans cette date pour ce chantier et ce Chef D'équipe", "");
                            verifMessage = true;
                        }
                    } else {
                        btnEtat = false;
                    }
//                    SelectedZonesString(t);
                }
            }
            salariesData = new ArrayDataModel(salaries.toArray());
            ficheGenere = false;
        }
        findCodeFiche();
    }

    public void onBlurDate() {
        //System.out.println("DATE SAISI : " + datePointage);
        //System.out.println("DATE OF TODAY : " + new Date());
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        String date1 = sdf2.format(datePointage);
        String date2 = sdf2.format(new Date());
        //System.out.println("1 : " + date1 + "2 today : " + date2);

    }

    public int indexOfLot(Lot l) {
        int i = 0;
        for (Lot lot : lots) {
            if (l.getId().equals(lot.getId())) {
                return i;
            }
            i++;
        }
        return -1;
    }

    public void findCodeFiche() {
        //System.out.println("date : " + datePointage);
        datePointage = Constante.getDateFrFromDate(datePointage);
        visible = true;
        // pdfview = "";
        codeFiche = null;
        if (idChantier != null && idChef != null && datePointage != null) {
            //System.out.println("IN IF FOIND CODE FICHE");
            Chantier c = new Chantier();
            c.setId(idChantier);
            Salarie s = new Salarie();
            s.setId(idChef);
            PointageLot p = pointageLotService.getLastPointage(c, s);
            Calendar cal = Calendar.getInstance();
            try {
                cal.setTime((p != null) ? p.getDatePointage() : Constante.get_first_day_in_pointage_lot_forAllApplication());
            } catch (ParseException ex) {
                System.out.println("erreur : " + ex.getMessage());
            }
            int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
            if (dayOfWeek == Calendar.SATURDAY) {
                cal.add(Calendar.DATE, 2);
            } else {
                cal.add(Calendar.DATE, 1);
            }
//            long nbrDay = datePointage.compareTo(cal.getTime());

            fiches = fichePLService.findFicheByChefDateChantier(idChantier, idChef, datePointage);
            if (fiches != null && !fiches.isEmpty()) {
                codeFiche = fiches.get(0).getId();
                pdfview = fiches.get(0).getChemin();
                //System.out.println("PDF VIEW CHEMIN : " + pdfview);
                visible = (pdfview == null || "".equals(pdfview));
                //System.out.println("PDF Chemin : " + pdfview);
                btnEtat = false;
                messagePointage = "";
                messageConsult = "";
                tabId = 1;
            }
//            else {
//                tabId = 0;
//                messagePointage = "Veuillez générer la fiche de pointage pour ce chantier et ce chef d'équipe";
//                messageConsult = "Veuillez générer la fiche de pointage pour ce chantier et ce chef d'équipe";
//                Module.message(1, "Veuillez générer la fiche de pointage ", "pour ce chantier et ce chef d'équipe");
//                btnEtat = true;
//                return;
//            }
//            if (nbrDay <= 0) {
//                btnEtat = false;
//                messagePointage = "";
//                messageConsult = "";
//            } else {
//                long dif = (datePointage.getTime() - cal.getTimeInMillis()) / 86400000;
//                
//                btnEtat = true;
//                messagePointage = "vous devez pointer les " + dif + "jours précedente";
//                messageConsult = "vous devez pointer les " + dif + "jours précedente";
//                Module.message(1, "vous devez pointer les " + dif + "jours précedente", "");
//                return;
//            }
            if (errChefE) {
                tabId = 1;
                btnEtat = true;
                messagePointage = "pointage effectueé dans cette date pour ce chantier et ce Chef D'équipe, Veuillez Consulter le pointage";
                messageConsult = "";
            } else {
                tabId = 0;
                btnEtat = false;
                messagePointage = "";
                messageConsult = "Veuillez effectuer le pointage de lot";
            }
        }
    }

    public void findChantierAndChefAndDateByCodeFiche() {
        if (codeFiche != null) {
            errChefE = false;
            btnEtat = false;
            FichePointageLot f = fichePLService.getByCode(codeFiche);
            if (f != null) {
                idChantier = f.getChantier().getId();
                //System.out.println("FICHE FOUND: CHANTIER : " + f.getChantier().getCode());
                datePointage = f.getDate();
                //System.out.println("FICHE FOUND: DATE : " + f.getDate());
                idChef = f.getSalarieSupp().getId();
                //System.out.println("FICHE FOUND: CHEF : " + f.getSalarieSupp().getNom());
                messageConsult = "";
                messagePointage = "";
                onChangeChantier();
//                onChangeChef();
            } else {
                messageConsult = "ce Code n'éxiste pas";
                messagePointage = "ce Code n'éxiste pas";
                Module.message(1, "Attention", "ce Code n'éxiste pas");
            }
        }
    }
    
    public void onSelectLotSalarie(Salarie s , int lotIndex){
        System.out.println("yes event work" + lotIndex);
//        this.salariesData
    }

    public void upload() throws IOException {
        //System.out.println("upload file ");
        if (codeFiche != null) {
            FichePointageLot fiche = fichePLService.getByCode(codeFiche);
            if (fiche != null) {
                //  String chemin = TgccFileManager.getCheminFichier("FichePointage");
                String chemin = ConstanteMb.getRepertoire() + "/files/fichesPointages";
                Path folder = Paths.get(chemin);
                Files.createDirectories(folder);
                //String filename = FilenameUtils.getBaseName(uploadedFile.getFileName());
                String extension = FilenameUtils.getExtension(uploadedFile.getFileName());
                if (!"PDF".equals(extension.toUpperCase())) {
                    Module.message(2, "Veuillez choisir un PDF", "");
                    return;
                }
                Path file = Files.createTempFile(folder, codeFiche.toString() + "_", "." + extension);
                try (InputStream input = uploadedFile.getInputstream()) {
                    Files.copy(input, file, StandardCopyOption.REPLACE_EXISTING);
                    chemin = chemin + "/" + file.getFileName();
                    chemin = chemin.substring(chemin.indexOf("files"));
                    fiche.setChemin(chemin);
                    fichePLService.update(fiche);
                    Module.message(0, "Info", "Importation réussie");
                    findCodeFiche();
                } catch (Exception e) {
                    System.out.println("erreur _ " + e.getMessage() + " __ " + e.getLocalizedMessage());
                    Module.message(2, "Erreur", "importation echouée");
                }
            } else {
                messageConsult = "ce Code n'éxiste pas";
                Module.message(1, "Attention", "ce Code n'éxiste pas");
            }
        } else {
            Module.message(1, "Attention", "Veuillez saisir le code de la fiche de pointage");
        }
    }

    public void SelectedZonesString(Salarie sal) {
        if (!sal.getIdZone().isEmpty()) {
            zonesString = "";
            for (String s : sal.getIdZone()) {
                for (Zone z : zones) {
                    if (z.getIdZone().equals(Integer.parseInt(s))) {
                        zonesString += z.getLibeleZone() + ", ";
                        break;
                    }
                }
            }
            zonesString = zonesString.substring(0, zonesString.length() - 1);
        } else {
            zonesString = "";
        }
        sal.setZonePointeConsult(zonesString);
    }

    //getter & setter 
    public boolean isBtnEtat() {
        return btnEtat;
    }

    public void setBtnEtat(boolean btnEtat) {
        this.btnEtat = btnEtat;
    }

    public Integer getIdChantier() {
        return idChantier;
    }

    public void setIdChantier(Integer idChantier) {
        this.idChantier = idChantier;
    }

    public Integer getIdChef() {
        return idChef;
    }

    public void setIdChef(Integer idChef) {
        this.idChef = idChef;
    }

    public List<Salarie> getChefs() {
        return chefs;
    }

    public void setChefs(List<Salarie> chefs) {
        this.chefs = chefs;
    }

    public List<Salarie> getSalaries() {
        return salaries;
    }

    public void setSalaries(List<Salarie> salaries) {
        this.salaries = salaries;
    }

    public List<Zone> getZones() {
        return zones;
    }

    public void setZones(List<Zone> zones) {
        this.zones = zones;
    }

    public List<Lot> getLots() {
        return lots;
    }

    public void setLots(List<Lot> lots) {
        this.lots = lots;
    }

    public ZoneServices getZoneServices() {
        return zoneServices;
    }

    public void setZoneServices(ZoneServices zoneServices) {
        this.zoneServices = zoneServices;
    }

    public Evol_LotService getLotService() {
        return lotService;
    }

    public void setLotService(Evol_LotService lotService) {
        this.lotService = lotService;
    }

    public ChantierService getChantierService() {
        return chantierService;
    }

    public void setChantierService(ChantierService chantierService) {
        this.chantierService = chantierService;
    }

    public SalarieService getSalarieService() {
        return salarieService;
    }

    public void setSalarieService(SalarieService salarieService) {
        this.salarieService = salarieService;
    }

    public Date getDatePointage() {
        return datePointage;
    }

    public void setDatePointage(Date datePointage) {
        this.datePointage = datePointage;
    }

    public PointageLotService getPointageLotService() {
        return pointageLotService;
    }

    public void setPointageLotService(PointageLotService pointageLotService) {
        this.pointageLotService = pointageLotService;
    }

    public DataModel<Salarie> getSalariesData() {
        return salariesData;
    }

    public void setSalariesData(DataModel<Salarie> salariesData) {
        this.salariesData = salariesData;
    }

    public AffectationSSuppService getAffectationSSuppService() {
        return affectationSSuppService;
    }

    public void setAffectationSSuppService(AffectationSSuppService affectationSSuppService) {
        this.affectationSSuppService = affectationSSuppService;
    }

    public PresenceService getPresenceService() {
        return presenceService;
    }

    public void setPresenceService(PresenceService presenceService) {
        this.presenceService = presenceService;
    }

    public Long getCodeFiche() {
        return codeFiche;
    }

    public void setCodeFiche(Long codeFiche) {
        this.codeFiche = codeFiche;
    }

    public FichePLService getFichePLService() {
        return fichePLService;
    }

    public void setFichePLService(FichePLService fichePLService) {
        this.fichePLService = fichePLService;
    }

    public List<FichePointageLot> getFiches() {
        return fiches;
    }

    public void setFiches(List<FichePointageLot> fiches) {
        this.fiches = fiches;
    }

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public String getPdfview() {
        return pdfview;
    }

    public void setPdfview(String pdfview) {
        this.pdfview = pdfview;
    }

    public boolean getVisible() {
        return visible;
    }

    public ChantierService getChantieService() {
        return chantieService;
    }

    public void setChantieService(ChantierService chantieService) {
        this.chantieService = chantieService;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public String getBtnPointage() {
        return btnPointage;
    }

    public void setBtnPointage(String btnPointage) {
        this.btnPointage = btnPointage;
    }

    public String getMessagePointage() {
        return messagePointage;
    }

    public boolean isFicheGenere() {
        return ficheGenere;
    }

    public void setFicheGenere(boolean ficheGenere) {
        this.ficheGenere = ficheGenere;
    }

    public void setMessagePointage(String messagePointage) {
        this.messagePointage = messagePointage;
    }

    public String getMessageConsult() {
        return messageConsult;
    }

    public void setMessageConsult(String MessageConsult) {
        this.messageConsult = MessageConsult;
    }

    public boolean isErrChefE() {
        return errChefE;
    }

    public void setErrChefE(boolean errChefE) {
        this.errChefE = errChefE;
    }

    public List<Integer> getIdZones() {
        return idZones;
    }

    public void setIdZones(List<Integer> idZones) {
        this.idZones = idZones;
    }

    public String getZonesString() {
        return zonesString;
    }

    public void setZonesString(String zonesString) {
        this.zonesString = zonesString;
    }

    public ParametrageService getParametrageService() {
        return parametrageService;
    }

    public void setParametrageService(ParametrageService parametrageService) {
        this.parametrageService = parametrageService;
    }

    public Integer getTabId() {
        return tabId;
    }

    public String getCheminFiche() {
        return cheminFiche;
    }

    public void setCheminFiche(String cheminFiche) {
        this.cheminFiche = cheminFiche;
    }

    public void setTabId(Integer tabId) {
        this.tabId = tabId;
    }

    public OrganigrameService getOrganigramService() {
        return organigramService;
    }

    public void setOrganigramService(OrganigrameService organigramService) {
        this.organigramService = organigramService;
    }

    public SalarieServiceIn getSalarieServiceIn() {
        return salarieServiceIn;
    }

    public void setSalarieServiceIn(SalarieServiceIn salarieServiceIn) {
        this.salarieServiceIn = salarieServiceIn;
    }

    
    
    /* MIGRATION FICHE POINTAGE ET POINTAGE PAR LOT DANS UN SEUL ECRAN */
    /**
     * Imprimer Fiche Pointage Lot
     *
     * @throws java.io.IOException
     * @throws com.itextpdf.text.DocumentException
     */
    public void downLoad_Fiche_Pointage_Lot() throws IOException, DocumentException {
        downLoad_Fiche_Pointage_Lot_generation();
        try {
            findCodeFiche();
            
        } catch (Exception e) {
            System.out.println("Erreur de upload fichier car : "+e.getMessage());
        }
        
    }
    public void downLoad_Fiche_Pointage_Lot_generation() throws IOException, DocumentException {
        try {
            
        if (idChantier != null && datePointage != null && idChef != null) {
           // System.out.println("entree" /*+ salarieToAffect.size()*/);
            Chantier ch = chantieService.getChantier(idChantier);

            Salarie s1 = salarieService.getSalarie(idChef);
            List<Salarie> salBySupp = affectationSSuppService.salarieByLastSuperieurAndChantier(s1, ch);

            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            String d = sdf.format(datePointage);
            String heure = ch.getHeureEntree().substring(0, 2);
            String min = ch.getHeureEntree().substring(3, 5);

            heure = (heure.length() == 1) ? "0" + heure : heure;
            min = (min.length() == 1) ? "0" + min : min;

            String heuremin = heure + ":" + min;

            /**
             * ¨Partie de génération LongTimeEntree
             */
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd");
            SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
            String date1 = sdf1.format(datePointage);
            Date testDate = new Date(date1 + " " + heuremin);
            long longDateTimeEntree = testDate.getTime();
//            if (!affectationSSuppService.pointageEntreeSalarie(idSalarieSupp, longDateTimeEntree, d)) {
//                Module.message(1, "Chef d'équipe n'a pas pointé en Entrée dans ce chantier et dans cette date", "");
//                return;
//            }
            Date idDate = new Date();
            long id = idDate.getTime();
            //System.out.println("id fiche " + id + " date " + idDate.getTime());
            /**
             * Save Fiche Salarie
             */
            //ficheTest = new ArrayList<>();
            FichePointageLot ficheValue = new FichePointageLot();
            List<FichePointageLot> ficheTest = fichePLService.findFicheByChefDateChantier(idChantier, idChef, datePointage);
            if (ficheTest != null && (!ficheTest.isEmpty())) {
                System.out.println(" ------- EXIST -----------");
                //ficheValue=ficheTest.get(ficheTest.size());
               
                for (FichePointageLot f : ficheTest) {
                    ficheValue = f;
                }
                
            } else {
                FichePointageLot fpl = new FichePointageLot();
                fpl.setSalarieSupp(s1);
                fpl.setDate(datePointage);
                fpl.setChantier(ch);
                fpl.setId(id);
                ficheValue = fichePLService.saveFiche(fpl);
            }

            /**
             * Fin Save Fiche Salarie
             */
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

            String date_start_str = format.format(datePointage);
            //if (salarieToAffect.size() > 0) {

            String chemin = ConstanteMb.getRepertoire() + "/files/fichePointage";
            Path folder = Paths.get(chemin);
            Files.createDirectories(folder);

            // String chemin = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/document/");
            String nomFichier = "FichePointageLot - " + id + ".pdf";
            File file = new File(chemin + "/" + nomFichier);
            Document document = new Document(PageSize.A4.rotate());

            document.addCreationDate();

            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
            /**
             * Numero da la page
             */
            document.open();

            String chemin_Image = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/img/logo.png");
            Image image1 = Image.getInstance(chemin_Image);

            document.add(image1);
            Font f = new Font(Font.FontFamily.TIMES_ROMAN, 16.0f, Font.UNDERLINE, BaseColor.BLACK);
            Font f1 = new Font(Font.FontFamily.TIMES_ROMAN, 9.0f, 0, BaseColor.BLACK);
            Font fm = new Font(Font.FontFamily.TIMES_ROMAN, 8.0f, 0, BaseColor.BLACK);
            Font f2 = new Font(Font.FontFamily.TIMES_ROMAN, 9.0f, 0, BaseColor.BLACK);
            Paragraph paragraph = new Paragraph("Fiche Pointage Lot N° " + ficheValue.getId(), f);
            paragraph.setAlignment(Element.ALIGN_CENTER);

            Salarie sa = salarieService.getSalarie(idChef);

            document.add(paragraph);

            document.add(new Paragraph(" "));

            String zone = "";
            for (Zone z : zoneServices.zoneBy(sa, ch)) {
                zone += z.getLibeleZone() + ",";
            }
            if (!"".equals(zone)) {
                zone = zone.substring(0, zone.length() - 1) + ".";
            }
            PdfPTable t = new PdfPTable(2);
            t.setWidthPercentage(100);
            PdfPCell cellFive = new PdfPCell(new Phrase("Chef d'équipe :" + sa.getNom() + " " + sa.getPrenom() + "/" + sa.getMatricule(), f1));
            PdfPCell cellTree = new PdfPCell(new Phrase("Chantier :" + ch.getCode().trim() + "/" + ch.getCodeNovapaie().trim(), f1));
            PdfPCell cellOne = new PdfPCell(new Phrase("Date :" + sdf2.format(datePointage), f1));
            PdfPCell cellTow = new PdfPCell(new Phrase("Zone :" + zone, f1));
            List<Salarie> temp = new LinkedList<>();
            for (Salarie s : salBySupp) {
                Boolean pe = affectationSSuppService.pointageEntreeSalarie(s.getId(), longDateTimeEntree, d);
                if (pe == true) {
                    temp.add(s);
                }
            }
            PdfPCell cellFo = new PdfPCell(new Phrase("Effectif :" + temp.size(), f1));
            PdfPCell cellSix = new PdfPCell(new Phrase("Code de la fiche :" + ficheValue.getId(), f1));

            cellOne.setBorder(Rectangle.NO_BORDER);
            cellTow.setBorder(Rectangle.NO_BORDER);
            cellTree.setBorder(Rectangle.NO_BORDER);
            cellFo.setBorder(Rectangle.NO_BORDER);
            cellFive.setBorder(Rectangle.NO_BORDER);
            cellSix.setBorder(Rectangle.NO_BORDER);

            t.addCell(cellFive);
            t.addCell(cellOne);

            t.addCell(cellTree);
            t.addCell(cellTow);

            t.addCell(cellFo);
            t.addCell(cellSix);

            t.getDefaultCell().setBorder(Rectangle.NO_BORDER);
            t.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            t.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
            t.getDefaultCell().setFixedHeight(70);
            document.add(t);

            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));

            //   System.out.println(" AAAAAAAAAAA  ICON CHEMIN AAAAAAAAAAAA " + lotService.findIconByLabelLot("elevation").getCheminIcon());
            List<Lot> lotIc = lotService.findAll();

            PdfPTable table = new PdfPTable(20);
            table.setWidthPercentage(100);

            table.addCell(new PdfPCell(new Phrase("Matricule", fm)));
            table.addCell(new PdfPCell(new Phrase("Nom", f2)));
            table.addCell(new PdfPCell(new Phrase("Prénom", f2)));
            table.addCell(new PdfPCell(new Phrase("Fonction", f2)));
            table.addCell(new PdfPCell(new Phrase("Zone", f2)));

            for (Lot li : lotIc) {
                System.out.println("LOT : " + li.getLibelle());
                try {
                    String chemin_iImgEle = FacesContext.getCurrentInstance().getExternalContext().getRealPath(li.getCheminIcon());
                    Image elev = Image.getInstance(chemin_iImgEle);

                    elev.scaleAbsolute(10, 10);
                    PdfPCell cell = new PdfPCell();
                    Font fabr = new Font(Font.FontFamily.TIMES_ROMAN, 9.0f, 0, BaseColor.BLACK);

                    Phrase ss = new Phrase(li.getAbreviation(), fabr);
                    cell.addElement(elev);
                    cell.addElement(new Phrase(""));
                    cell.addElement(ss);

                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    table.addCell(cell);
                } catch (Exception e) {
                    System.out.println("EXCEPTION NO IMAGE");
                    PdfPCell cell = new PdfPCell(new Phrase(li.getLibelle()));
                    table.addCell(cell);
                }

            }
            List<Salarie> salariePointeEntree = new LinkedList<>();
            for (Salarie s : salBySupp) {
                Boolean pe = affectationSSuppService.pointageEntreeSalarie(s.getId(), longDateTimeEntree, d);
                //System.out.println(" TIMEEEEEEEEEEEE  LONG EEEEEEEEEEEEEEEMIT" + pe);

                if (pe == true) {
                    //salariePointeEntree.add(s);
                    s.getFichePointageLots().add(ficheValue);
                    salarieService.modifierInformationsSalarie(s);
                    //System.out.println("///////////// TEST ENTREE --------------- " + s.getId());
                    table.addCell(new PdfPCell(new Phrase(s.getMatricule(), f1)));
                    table.addCell(new PdfPCell(new Phrase(s.getNom().substring(0, 1).toUpperCase() + s.getNom().substring(1).toLowerCase(), f1)));
                    table.addCell(new PdfPCell(new Phrase(s.getPrenom().substring(0, 1).toUpperCase() + s.getPrenom().substring(1).toLowerCase(), f1)));
                    table.addCell(new PdfPCell(new Phrase(s.getFonction().getFonction(), f1)));
                    table.addCell(new PdfPCell(new Phrase("", f1)));
                    for (Lot li : lotIc) {
                        table.addCell(new PdfPCell(new Phrase("", f1)));
                    }

                }
            }
            
            for (Lot li : lotIc) {
                System.out.println("LOT : " + li.getLibelle());
                try {
                    String chemin_iImgEle = FacesContext.getCurrentInstance().getExternalContext().getRealPath(li.getCheminIcon());
                    Image elev = Image.getInstance(chemin_iImgEle);

                    elev.scaleAbsolute(10, 10);
                    PdfPCell cell = new PdfPCell();
                    Font fabr = new Font(Font.FontFamily.TIMES_ROMAN, 9.0f, 0, BaseColor.BLACK);

                    Phrase ss = new Phrase(li.getAbreviation(), fabr);
                    cell.addElement(elev);
                    cell.addElement(new Phrase(""));
                    cell.addElement(ss);

                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    table.addCell(cell);
                } catch (Exception e) {
                    System.out.println("EXCEPTION NO IMAGE");
                    PdfPCell cell = new PdfPCell(new Phrase(li.getLibelle()));
                    table.addCell(cell);
                }

            }
            //ficheValue.setSalaries(salariePointeEntree);
            //fichePLService.update(ficheValue);
            document.add(table);

            document.add(new Paragraph(" "));

            document.add(new Paragraph("Signature:", f1));

            document.add(new Paragraph(" "));

            PdfPTable tableSigna = new PdfPTable(4);
            tableSigna.setWidthPercentage(100);
/*  old version
            tableSigna.addCell(new PdfPCell(new Phrase("", f1)));
            tableSigna.addCell(new PdfPCell(new Phrase("Prénom", f1)));
            tableSigna.addCell(new PdfPCell(new Phrase("Type", f1)));
            tableSigna.addCell(new PdfPCell(new Phrase("Fonction", f1)));

            tableSigna.addCell(new PdfPCell(new Phrase("Pointeur", f1)));
            tableSigna.addCell(new PdfPCell(new Phrase("", f1)));
            tableSigna.addCell(new PdfPCell(new Phrase("", f1)));
            tableSigna.addCell(new PdfPCell(new Phrase("", f1)));

            tableSigna.addCell(new PdfPCell(new Phrase("Chef d'équipe", f1)));
            tableSigna.addCell(new PdfPCell(new Phrase("", f1)));
            tableSigna.addCell(new PdfPCell(new Phrase("", f1)));
            tableSigna.addCell(new PdfPCell(new Phrase("", f1)));

            tableSigna.addCell(new PdfPCell(new Phrase("Conducteur de travaux", f1)));
            tableSigna.addCell(new PdfPCell(new Phrase("", f1)));
            tableSigna.addCell(new PdfPCell(new Phrase("", f1)));
            tableSigna.addCell(new PdfPCell(new Phrase("", f1)));
*/

            tableSigna.addCell(new PdfPCell(new Phrase("Pointeur", f1)));
            tableSigna.addCell(new PdfPCell(new Phrase("Chef d'équipe", f1)));
            tableSigna.addCell(new PdfPCell(new Phrase("Chef de Chantier", f1)));
            tableSigna.addCell(new PdfPCell(new Phrase("Conducteur de travaux", f1)));

            PdfPCell celle = new PdfPCell(new Phrase("\n \n \n \n \n", f1)); 
            tableSigna.addCell(celle);
            tableSigna.addCell(celle);
            tableSigna.addCell(celle);
            tableSigna.addCell(celle);

            document.add(tableSigna);

            /**
             * Lot Abreviat
             */
            List<Lot> l = lotService.findAll();

            // document.add(new Paragraph("Lot abréviation:", f1));
            document.add(new Paragraph(" "));

            PdfPTable tL = new PdfPTable(10);
            tL.setWidthPercentage(100);
            for (Lot lImg : l) {

                System.out.println("xxxxxxxxxxxxxxx TEST IMG BD Xxxxxxxxxxx" + l.size());
                try {
                    String chemin_im = FacesContext.getCurrentInstance().getExternalContext().getRealPath(lImg.getCheminIcon());
                    Image im = Image.getInstance(chemin_im);
                    im.scaleAbsolute(10, 10);
                    PdfPCell cell = new PdfPCell();
                    Font fabr2 = new Font(Font.FontFamily.TIMES_ROMAN, 9.0f, 0, BaseColor.BLACK);
                    Paragraph ss = new Paragraph(lImg.getAbreviation(), fabr2);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);

                    // cell.addElement(im);
                    //   im.setAlignment(Element.ALIGN_LEFT);
                    //  cell.addElement(new Phrase(""));
                    //cell.addElement(ss);
                    ss.add(" ");
                    //   p = new Paragraph("The quick brown ");
                    ss.add(new Chunk(im, 0, 0, true));
                    // p.add(" jumps over the lazy ");
                    //  p.add(new Chunk(img2, 0, 0, true));
                    cell = new PdfPCell();
                    cell.addElement(ss);
                    //   table.addCell(cell);

                    PdfPCell cellL1 = new PdfPCell(cell);
                    PdfPCell cellL1a = new PdfPCell(new Phrase(lImg.getLibelle(), f1));

                    tL.addCell(cell);
                    tL.addCell(cellL1a);
                } catch (Exception e) {
                    System.out.println("IMAGE NOT FOUND EXCEPTION");
                }

            }
            document.add(tL);

            /**
             *
             */
            PdfTemplate total = writer.getDirectContent().createTemplate(100, 100);
            BaseFont helv = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.NOT_EMBEDDED);

            total.setBoundingBox(new Rectangle(-20, -20, 100, 100));
            LOG.info("Generation des numeros de page ...");
            final PdfContentByte directcontent = writer.getDirectContent();

            directcontent.saveState();
            final String text = "Page " + writer.getPageNumber() + " Date :" + date_start_str;
            final float textBase = document.bottom() - 50;
            final float textSize = helv.getWidthPoint(text, 12);

            directcontent.beginText();
            directcontent.setFontAndSize(helv, 11);

            // Ecriture du numero de page a gauche pour les pages impaires.
            if ((writer.getPageNumber() % 2) == 1) {
                directcontent.setTextMatrix(document.right(), textBase);
                directcontent.showText(text);
                directcontent.endText();
                directcontent.addTemplate(total, document.right() + textSize, textBase);
            } else {
                // Ecriture du numero de page a droite pour les pages paires.
                final float adjust = helv.getWidthPoint("0", 12);
                directcontent.setTextMatrix(document.right() - textSize - adjust, textBase);
                directcontent.showText(text);
                directcontent.endText();
                directcontent.addTemplate(total, document.right() - adjust, textBase);
            }
            directcontent.restoreState();
            /**
             *
             */
            document.close();
            this.pdfview = file.getPath().substring(file.getPath().indexOf("files"));
            ficheValue.setChemin(this.pdfview);
            fichePLService.update(ficheValue);
            System.out.println("chemin " + this.pdfview);
            //}
        } else {
            Module.message(3, "veuillez remplir tous les champs", "");
        }
        
        } catch (Exception e) {
            logger.error("Erreur de chargement la fiche de pointage car : "+e.getMessage());
        }
    }
    
    

}
