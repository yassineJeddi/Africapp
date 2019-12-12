/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.mb.mensuel;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ma.bservice.tgcc.Constante.Message;
import ma.bservices.mb.services.ConstanteMb;
import ma.bservices.mb.services.Module;
import ma.bservices.tgcc.Entity.BonCaisse;
import ma.bservices.tgcc.Entity.Historique_LoyerC;
import ma.bservices.tgcc.Entity.Historique_LoyerS;
import ma.bservices.tgcc.Entity.Loyer;
import ma.bservices.tgcc.Entity.LoyerChantier;
import ma.bservices.tgcc.Entity.LoyerSalarie;
import ma.bservices.tgcc.Entity.Mensuel;
import ma.bservices.tgcc.service.Engin.Bean.CiterneServiceBean;
import ma.bservices.tgcc.service.Engin.ChantierService;
import ma.bservices.tgcc.service.Mensuel.BonCaisseService;
import ma.bservices.tgcc.service.Mensuel.HistoriqueService;
import ma.bservices.tgcc.service.Mensuel.MensuelService;
import ma.bservices.tgcc.utilitaire.Outils;
import ma.bservices.tgcc.service.Mensuel.LoyerService;
import ma.bservices.tgcc.utilitaire.TgccFileManager;
import ma.bservices.tgcc.webService.MensuelWSCallManager;
import static org.apache.catalina.connector.InputBuffer.DEFAULT_BUFFER_SIZE;
import org.apache.commons.io.FilenameUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.primefaces.model.UploadedFile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

/**
 *
 * @author a.wattah
 */
@Component
@ManagedBean(name = "loyer_SalarieMb")
@ViewScoped
public class LoyerSalarieMb implements Serializable {

    @ManagedProperty(value = "#{loyerService}")
    private LoyerService loyerService;

    @ManagedProperty(value = "#{boncaisseservice}")
    private BonCaisseService bonCaisseService;

    @ManagedProperty(value = "#{chantierService}")
    private ChantierService chantierService;

    @ManagedProperty(value = "#{mensuelService}")
    private MensuelService mensuelService;

    @ManagedProperty(value = "#{historiqueService}")
    private HistoriqueService hService;

    private List<Mensuel> searchMensuelList;

    private boolean isSalarie = true;

    private boolean updateSalarie = true;

    String dateLoyerBon;

    private List<Mensuel> mensuels = new ArrayList<Mensuel>();

    private List<BonCaisse> BCRecu = new ArrayList<>();

    private Date dateDebutLoyerSelected;

    List<LoyerSalarie> lo;

    private Loyer loyer_To_Search_Salarie = new Loyer();

    private Date Date_Debut_Loyer_To_Search;

    private Mensuel mensuelToSearch = new Mensuel();

    List<LoyerSalarie> listeLSalrie = new ArrayList<>();
    List<Historique_LoyerS> loyerSH = new ArrayList<>();
    List<Historique_LoyerC> loyerCH = new ArrayList<>();

    private Date dateMensuel;

    private boolean isBCValidated = false;

    private String loyerTypeSelected;

    private Mensuel mensuelSearch = new Mensuel();
    private List<Mensuel> loyersM;

    private BonCaisse bc = new BonCaisse();

    private String codeCutrrent;

    private String mois;

    public String getDateLoyerBon() {
        return dateLoyerBon;
    }

    public void setDateLoyerBon(String dateLoyerBon) {
        this.dateLoyerBon = dateLoyerBon;
    }

    private String annee;

    private Integer idRequest;

    private LoyerSalarie loyerSalarieSelectMensuel = new LoyerSalarie();

    private List<BonCaisse> listeBonCaiss;

    private BonCaisse BonCaiss;

    private UploadedFile docUploaded;

    private Date dateFromS, dateToS;

    private List<Mensuel> liste_Mensuel_principal = new ArrayList<Mensuel>();
    private List<Mensuel> loyersML = new ArrayList<Mensuel>();

    private String date_debut_loyer_MensuelString = "";

    private String beneficiaire_sec = "";

    private BonCaisse BCSelectedSalarie = new BonCaisse();

    private Mensuel lsbc = new Mensuel();
    private UploadedFile uploadedFile;

    private Date dateRenduS;
    private String codeCurrent;

    /**
     * getters and setters
     *
     * @return
     */
    public String getMois() {
        return mois;
    }

    public void setMois(String mois) {
        this.mois = mois;
    }

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public String getAnnee() {
        return annee;
    }

    public void setAnnee(String annee) {
        this.annee = annee;
    }

    public HistoriqueService gethService() {
        return hService;
    }

    public boolean isUpdateSalarie() {
        return updateSalarie;
    }

    public void setUpdateSalarie(boolean updateSalarie) {
        this.updateSalarie = updateSalarie;
    }

    public Integer getIdRequest() {
        return idRequest;
    }

    public void setIdRequest(Integer idRequest) {
        this.idRequest = idRequest;
    }

    public String getCodeCurrent() {
        return codeCurrent;
    }

    public void setCodeCurrent(String codeCurrent) {
        this.codeCurrent = codeCurrent;
    }

    public void sethService(HistoriqueService hService) {
        this.hService = hService;
    }

    public List<Historique_LoyerS> getLoyerSH() {
        return loyerSH;
    }

    public void setLoyerSH(List<Historique_LoyerS> loyerSH) {
        this.loyerSH = loyerSH;
    }

    public String getCodeCutrrent() {
        return codeCutrrent;
    }

    public void setCodeCutrrent(String codeCutrrent) {
        System.out.println("CODE CURRENT PASSED FROM VIEW : " + codeCutrrent);
        this.codeCutrrent = codeCutrrent;
    }

    public boolean isIsBCValidated() {
        return isBCValidated;
    }

    public void setIsBCValidated(boolean isBCValidated) {
        this.isBCValidated = isBCValidated;
    }

    public List<Historique_LoyerC> getLoyerCH() {
        return loyerCH;
    }
    LoyerSalarie loyerS = new LoyerSalarie();
    LoyerChantier loyerC = new LoyerChantier();

    public void setLoyerCH(List<Historique_LoyerC> loyerCH) {
        this.loyerCH = loyerCH;
    }

    public boolean isIsSalarie() {
        return isSalarie;
    }

    public void setIsSalarie(boolean isSalarie) {
        this.isSalarie = isSalarie;
    }

    public LoyerSalarie getLoyerS() {
        return loyerS;
    }

    public void setLoyerS(LoyerSalarie loyerS) {
        this.loyerS = loyerS;
    }

    public LoyerChantier getLoyerC() {
        return loyerC;
    }

    public void setLoyerC(LoyerChantier loyerC) {
        this.loyerC = loyerC;
    }

    public BonCaisse getBonCaiss() {
        return BonCaiss;
    }

    public void setBonCaiss(BonCaisse BonCaiss) {
        this.BonCaiss = BonCaiss;
    }

    public BonCaisse getBc() {
        return bc;
    }

    public void setBc(BonCaisse bc) {
        this.bc = bc;
    }

    public Date getDateDebutLoyerSelected() {
        return dateDebutLoyerSelected;
    }

    public void setDateDebutLoyerSelected(Date dateDebutLoyerSelected) {
        this.dateDebutLoyerSelected = dateDebutLoyerSelected;
    }

    public BonCaisse getBCSelectedSalarie() {
        return BCSelectedSalarie;
    }

    public void setBCSelectedSalarie(BonCaisse BCSelectedSalarie) {
        this.BCSelectedSalarie = BCSelectedSalarie;
    }

    public Mensuel getLsbc() {
        return lsbc;
    }

    public void setLsbc(Mensuel lsbc) {
        this.lsbc = lsbc;
    }

    public LoyerService getLoyerService() {
        return loyerService;
    }

    public void setLoyerService(LoyerService loyerService) {
        this.loyerService = loyerService;
    }

    public BonCaisseService getBonCaisseService() {
        return bonCaisseService;
    }

    public void setBonCaisseService(BonCaisseService bonCaisseService) {
        this.bonCaisseService = bonCaisseService;
    }

    public ChantierService getChantierService() {
        return chantierService;
    }

    public String getLoyerTypeSelected() {
        return loyerTypeSelected;
    }

    public void setLoyerTypeSelected(String loyerTypeSelected) {
        this.loyerTypeSelected = loyerTypeSelected;
    }

    public void setChantierService(ChantierService chantierService) {
        this.chantierService = chantierService;
    }

    public MensuelService getMensuelService() {
        return mensuelService;
    }

    public void setMensuelService(MensuelService mensuelService) {
        this.mensuelService = mensuelService;
    }

    public List<BonCaisse> getBCRecu() {
        return BCRecu;
    }

    public void setBCRecu(List<BonCaisse> BCRecu) {
        this.BCRecu = BCRecu;
    }

    public Date getDateFromS() {
        return dateFromS;
    }

    public void setDateFromS(Date dateFromS) {
        this.dateFromS = dateFromS;
    }

    public Date getDateToS() {
        return dateToS;
    }

    public void setDateToS(Date dateToS) {
        this.dateToS = dateToS;
    }

    public Loyer getLoyer_To_Search_Salarie() {
        return loyer_To_Search_Salarie;
    }

    public void setLoyer_To_Search_Salarie(Loyer loyer_To_Search_Salarie) {
        this.loyer_To_Search_Salarie = loyer_To_Search_Salarie;
    }

    public Date getDate_Debut_Loyer_To_Search() {
        return Date_Debut_Loyer_To_Search;
    }

    public void setDate_Debut_Loyer_To_Search(Date Date_Debut_Loyer_To_Search) {
        this.Date_Debut_Loyer_To_Search = Date_Debut_Loyer_To_Search;
    }

    public Mensuel getMensuelToSearch() {
        return mensuelToSearch;
    }

    public void setMensuelToSearch(Mensuel mensuelToSearch) {
        this.mensuelToSearch = mensuelToSearch;
    }

    public List<LoyerSalarie> getListeLSalrie() {
        return listeLSalrie;
    }

    public void setListeLSalrie(List<LoyerSalarie> listeLSalrie) {
        this.listeLSalrie = listeLSalrie;
    }

    public Date getDateMensuel() {
        return dateMensuel;
    }

    public void setDateMensuel(Date dateMensuel) {
        this.dateMensuel = dateMensuel;
    }

    public Mensuel getMensuelSearch() {
        return mensuelSearch;
    }

    public UploadedFile getDocUploaded() {
        return docUploaded;
    }

    public void setDocUploaded(UploadedFile docUploaded) {
        this.docUploaded = docUploaded;
    }

    public void setMensuelSearch(Mensuel mensuelSearch) {
        this.mensuelSearch = mensuelSearch;
    }

    public LoyerSalarie getLoyerSalarieSelectMensuel() {
        return loyerSalarieSelectMensuel;
    }

    public void setLoyerSalarieSelectMensuel(LoyerSalarie loyerSalarieSelectMensuel) {
        this.loyerSalarieSelectMensuel = loyerSalarieSelectMensuel;
    }

    public List<BonCaisse> getListeBonCaiss() {
        return listeBonCaiss;
    }

    public void setListeBonCaiss(List<BonCaisse> listeBonCaiss) {
        this.listeBonCaiss = listeBonCaiss;
    }

    public List<Mensuel> getListe_Mensuel_principal() {
        return liste_Mensuel_principal;
    }

    public void setListe_Mensuel_principal(List<Mensuel> liste_Mensuel_principal) {
        this.liste_Mensuel_principal = liste_Mensuel_principal;
    }

    public List<Mensuel> getLoyersML() {
        return loyersML;
    }

    public void setLoyersML(List<Mensuel> loyersML) {
        this.loyersML = loyersML;
    }

    public String getDate_debut_loyer_MensuelString() {
        return date_debut_loyer_MensuelString;
    }

    public void setDate_debut_loyer_MensuelString(String date_debut_loyer_MensuelString) {
        this.date_debut_loyer_MensuelString = date_debut_loyer_MensuelString;
    }

    public String getBeneficiaire_sec() {
        return beneficiaire_sec;
    }

    public void setBeneficiaire_sec(String beneficiaire_sec) {
        this.beneficiaire_sec = beneficiaire_sec;
    }

    public List<Mensuel> getSearchMensuelList() {
        return searchMensuelList;
    }

    public void setSearchMensuelList(List<Mensuel> searchMensuelList) {
        this.searchMensuelList = searchMensuelList;
    }

    public List<Mensuel> getMensuels() {
        return mensuels;
    }

    public void setMensuels(List<Mensuel> mensuels) {
        this.mensuels = mensuels;
    }

    public List<LoyerSalarie> getLo() {
        return lo;
    }

    public void setLo(List<LoyerSalarie> lo) {
        this.lo = lo;
    }

    public List<Mensuel> getLoyersM() {
        return loyersM;
    }

    public void setLoyersM(List<Mensuel> loyersM) {
        this.loyersM = loyersM;
    }

    public Date getDateRenduS() {
        return dateRenduS;
    }

    public void setDateRenduS(Date dateRenduS) {
        this.dateRenduS = dateRenduS;
    }

    /**
     * getters and setters
     */
    public void searchByDateS() {
        if (isSalarie) {
            loyerSH = hService.findByDateRangeLS(dateFromS, dateToS);
        } else {
            loyerCH = hService.findByDateRangeLC(dateFromS, dateToS);
        }
    }

    public void reinitSearchS() {
        loyerSH = hService.findAllLoyerS();
        loyerCH = hService.findAllLoyerC();
        dateFromS = null;
        dateToS = null;

    }

    @PostConstruct
    public void init() {
        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        loyerService = ctx.getBean(LoyerService.class);
        bonCaisseService = ctx.getBean(BonCaisseService.class);
        chantierService = ctx.getBean(ChantierService.class);
        hService = ctx.getBean(HistoriqueService.class);

        listeLSalrie = loyerService.loyerSalaries();
        loyerSH = hService.findAllLoyerS();
        loyerCH = hService.findAllLoyerC();

        for (LoyerSalarie loyerSalarie : listeLSalrie) {
            for (Mensuel mensuelInit : loyerSalarie.getMensuels()) {
                mensuelSearch = mensuelInit;
            }
        }
        loyersM = loyerService.findALLl();

        // this.listeBonCaiss = bonCaisseService.findAll();
        for (Mensuel m : loyersM) {

            this.lo = m.getLoyerSalaries();

        }

    }

    public void search() {

        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        loyerService = ctx.getBean(LoyerService.class);
        String dateCrea = "";
        if (dateMensuel != null) {
            DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
            Date today = dateMensuel;
            dateCrea = df.format(today);
        }
        if (mensuelToSearch.getId() != null) {

            searchMensuelList = loyerService.search(mensuelToSearch.getId(), mensuelToSearch.getMatricule(), mensuelToSearch.getNom(), dateCrea);
        } else {
            searchMensuelList = loyerService.rechercherMensuel(mensuelToSearch.getMatricule(), mensuelToSearch.getNom(), mensuelToSearch.getPrenom(), "", dateCrea);
        }
        mensuels = searchMensuelList;

    }

    public void searchByDateLS() {
        listeLSalrie = loyerService.loyerSalariesByDateDeb(Date_Debut_Loyer_To_Search);
    }

    /**
     * cette methode qui permet de recherch Loyer a partir identifiant et date
     * Debut
     */
    public void recherche_Loyer_Salarie_ByIdentifiantAndDateDebut() {
        System.out.println("entree");
        String Date_Debut = "";
        if (Date_Debut_Loyer_To_Search != null) {
            System.out.println("entreee " + loyer_To_Search_Salarie.getId());
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            Date today = Date_Debut_Loyer_To_Search;
            Date_Debut = df.format(today);
        }
//        if (loyer_To_Search_Salarie.getId() == null) {
//            loyer_To_Search_Salarie.setId(new Long(-1));
//        }

        if (listeLSalrie != null) {
            listeLSalrie.clear();
        }
        listeLSalrie = loyerService.rechercher_Loyer_Salarier_ByIdentifient_Date_Debut(new Long(0), Date_Debut);
        if (listeLSalrie != null) {
            for (LoyerSalarie men : listeLSalrie) {
                for (Mensuel m : men.getMensuels()) {
                    mensuelSearch = m;
                }

            }
        }
    }

    /* selctionner type de loyer a afficher dans l'historique ,
     * @page historiques des affectations loyers */
    public void switchLoyerType() {
        switch (loyerTypeSelected) {
            case "s":
                System.out.println("loyer salarie");
                loyerSH = hService.findAllLoyerS();
                isSalarie = true;
                break;

            case "ch":
                System.out.println("loyer chantier");
                loyerCH = hService.findAllLoyerC();
                isSalarie = false;
                break;
            default:
                System.out.println("default");
                break;

        }
    }

    /**
     * cette methode qui permet de rediget vers page edit bon caiisse d un loyer
     * salarier
     *
     * @param selected
     */
    public void redirectEditBCaisseS(LoyerSalarie selected) {

        System.out.println("entree  " + selected.getNomproprietaire());
        loyerSalarieSelectMensuel = new LoyerSalarie();
        loyerSalarieSelectMensuel = selected;
    }

    /**
     * Editer recu caisse
     *
     */
    public void visualiserRecu(LoyerSalarie selected) {
        loyerSalarieSelectMensuel = selected;

        listeBonCaiss = bonCaisseService.getbcByIdLoyerSalarie(loyerSalarieSelectMensuel.getId());

    }

    /**
     * * visualise fiche loyer
     *
     * @param selected
     */
    public void initLoyer(LoyerSalarie loyer) {
        System.out.println("PRINCIPAL : " + loyer.getMensuel_Principal().getNom());
    }

    public String redirectFicheLoyer(LoyerSalarie selected) {
        return "ficheDetailLoyer.xhmtl?faces-redirect=true&loyerId=" + selected.getId();
    }

    public String redirectBonCaisse(LoyerSalarie selected) {
        System.out.println("REDIRECTING VERS SELECTED : " + selected.getId());
        return "editBonCaisse.xhmtl?faces-redirect=true&loyerId=" + selected.getId();
    }

    public String redirectRecuCaisse(LoyerSalarie selected) {
        System.out.println("REDIRECTING VERS SELECTED : " + selected.getId());
        return "telechargerRecuCaissSalarie.xhmtl?faces-redirect=true&loyerId=" + selected.getId();
    }

    public void visualiserFicheLoyer(LoyerSalarie selected) {

        System.out.println("INIT SELECTED LOYER : " + selected.getMensuel_Principal().getNom());

        loyerSalarieSelectMensuel = selected;

        if (liste_Mensuel_principal != null) {
            liste_Mensuel_principal.clear();
        }
        if (loyersML != null) {
            System.out.println("LOYEEEERRR ML : " + loyersML);
            loyersML.clear();
        }

        if (loyerSalarieSelectMensuel != null) {
            date_debut_loyer_MensuelString = loyerSalarieSelectMensuel.getDatedebut().substring(0, 10);
        }

        System.out.println("STARTING TRAITEMENT .... ");
        System.out.println("LOYER SALARIE SELECTED : " + selected.getMensuel_Principal().getNom());
        System.out.println("LOYER SALARIE SELECTED : " + loyerSalarieSelectMensuel.getMensuel_Principal().getNom());
        //System.out.println();

        List<Mensuel> ll = loyerService.findALLlBy_Id_Loyer(loyerSalarieSelectMensuel.getId());

        //List<Mensuel> ll = loyerSalarieSelectMensuel.getMensuels();
        System.out.println("LIST OF MENSUEEEEEEEEEEELS BY LOYER : === " + ll);
        // System.out.println("principal : " + listMensuelPrincipal);

        if (ll != null) {
            for (int i = 0; i < ll.size(); i++) {

                System.out.println("mensuel : " + loyerSalarieSelectMensuel.getMensuel_Principal().getNom());
                System.out.println("mensuel : " + ll.get(i).getId());

                if (loyerSalarieSelectMensuel.getMensuel_Principal().getId() == ll.get(i).getId()) {
                    liste_Mensuel_principal.add(ll.get(i));
                } else {
                    loyersML.add(ll.get(i));

                }

            }

            beneficiaire_sec = "";

            if (loyersML.size() == 0) {
                beneficiaire_sec = " aucun beneficiaire sec ";
            } else {
                for (int i = 0; i < loyersML.size(); i++) {
                    beneficiaire_sec = beneficiaire_sec + loyersML.get(i).getNom() + " " + loyersML.get(i).getPrenom();
                    beneficiaire_sec += (i == (loyersML.size() - 1)) ? "" : ", ";
                }

            }
        }

    }

    /*
     * partie jihane telcharger recu caisse
     */
    public void visualiserRecuSalarie(LoyerSalarie selected) {
        System.out.println("executing ......");
        loyerSalarieSelectMensuel = selected;
        BCRecu = bonCaisseService.getbcByIdLoyerSalarie(loyerSalarieSelectMensuel.getId());

    }

    public void consultation(Mensuel selected) {
        loyersM = new ArrayList<Mensuel>();
        mensuelSearch = selected;
        listeLSalrie = new ArrayList<>();
        listeLSalrie = this.loyerService.getListLoyerSalarieByIdMensuelPrincipal(mensuelSearch.getId());

    }

    public String findChantierDivalto(Mensuel s) {

        System.out.println("Calling ws chantier divalto");
        // MensuelWSCallManager.Get_Chantier_By_Mensuel("05-04-2010 15:05:00", "703");
        System.out.println("Done calling ws chantier divalto");
        String chantierDivaltoMensuel = "N/A";
        String jsonRes = "";
        JSONObject obj;
        JSONArray arr;
        try {
            String matricule = s.getMatricule();
            if (s.getMatricule().length() == 1) {
                matricule = "00" + s.getMatricule();
            } else if (s.getMatricule().length() == 2) {
                matricule = "0" + s.getMatricule();
            }
            jsonRes = MensuelWSCallManager.Get_Chantier_By_Mensuel("05-04-2010 15:05:00", matricule);
            if ("".equals(jsonRes)) {
                System.out.println("EXCEPTION JSON VIDE");
            } else {
                obj = new JSONObject(jsonRes);
                arr = obj.getJSONArray("Mensuels");
                for (int i = 0; i < arr.length(); i++) {
                    String id = arr.getJSONObject(i).getString("id_divalto");
                    // String matricule = arr.getJSONObject(i).getString("matricule");
                    String code_chan = arr.getJSONObject(i).getString("Code Chantier");
                    String lib_chan = arr.getJSONObject(i).getString("Libellé chantier");
                    System.out.println("MENSUEL CHANTIER DETECTED : " + lib_chan);
                    chantierDivaltoMensuel = lib_chan;
                }
            }
            return chantierDivaltoMensuel;
        } catch (Exception e) {
            System.out.println("JSON EXCEPTION DETECTED!");
        }

        System.out.println("finished ws!");
        return chantierDivaltoMensuel;
    }

    // show bon caisse salarie
    public void showBC() throws IOException, DocumentException {
        System.out.println("entree");

        System.out.println("MOIS : " + bc.getMois() + "/" + bc.getAnnee());

        //String chemin = TgccFileManager.getCheminFichier("Bon Caisse Loyer Salarie");
        String chemin = ConstanteMb.getRepertoire() + "/files/documentsLoyer";

        Path folder = Paths.get(chemin);
        Files.createDirectories(folder);
        //String.valueOf(System.currentTimeMillis())
        String uniqueId = bc.getId().toString();

        // String chemin = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/document/");
        String nomFichier = "/bonCaisse" + uniqueId + ".pdf";
        File file = new File(chemin + nomFichier);

        Document document = new Document();

        PdfWriter.getInstance(document, new FileOutputStream(file));

        document.open();

        String chemin_Image = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/img/logo.png");

        // System.out.println("CHEMIN DU TRUC MACHIN : " + chemin_Image);
        Image image1 = Image.getInstance(chemin_Image);
        document.add(image1);
        Font f = new Font(FontFamily.TIMES_ROMAN, 20.0f, Font.UNDERLINE, BaseColor.BLACK);
        Paragraph paragraph = new Paragraph("Bon de Caisse Loyer", f);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        document.add(paragraph);

        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));
        PdfPTable t = new PdfPTable(2);
        t.setWidthPercentage(100);
        System.out.println("DATE LOYER BON : " + dateLoyerBon);
        PdfPCell cellFive = new PdfPCell(new Phrase("Date: " + dateLoyerBon));
        PdfPCell cellTree = new PdfPCell(new Phrase("N Bon : " + uniqueId));

        cellTree.setBorder(Rectangle.NO_BORDER);
        cellFive.setBorder(Rectangle.NO_BORDER);

        t.addCell(cellFive);

        t.addCell(cellTree);

        t.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        t.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        t.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
        t.getDefaultCell().setFixedHeight(70);
        document.add(t);
        document.add(new Paragraph(" "));
        PdfPTable tt = new PdfPTable(2);
        tt.setWidthPercentage(100);
        PdfPCell cellFivet = new PdfPCell(new Phrase("Chantier : " + findChantierDivalto(loyerSalarieSelectMensuel.getMensuel_Principal())));
        PdfPCell cellTreet = new PdfPCell(new Phrase("Ville : " + loyerSalarieSelectMensuel.getVille()));

        cellTreet.setBorder(Rectangle.NO_BORDER);
        cellFivet.setBorder(Rectangle.NO_BORDER);
        tt.addCell(cellFivet);
        tt.addCell(cellTreet);

        tt.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        tt.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        tt.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
        tt.getDefaultCell().setFixedHeight(70);
        document.add(tt);
        document.add(new Paragraph(" "));

        document.add(new Paragraph("Bénéficiaire : " + loyerSalarieSelectMensuel.getMensuel_Principal().getNom() + " " + loyerSalarieSelectMensuel.getMensuel_Principal().getPrenom()));

        document.add(new Paragraph(" "));
        PdfPTable r = new PdfPTable(2);
        r.setWidthPercentage(100);
        PdfPCell cellFivettr = new PdfPCell(new Phrase("Montant : " + loyerSalarieSelectMensuel.getMontantloyer()));
        PdfPCell cellFivettrr = new PdfPCell(new Phrase("Mode de Paiement : " + loyerSalarieSelectMensuel.getModepaiment()));

        cellFivettr.setBorder(Rectangle.NO_BORDER);
        cellFivettrr.setBorder(Rectangle.NO_BORDER);
        r.addCell(cellFivettr);
        r.addCell(cellFivettrr);

        r.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        r.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        r.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
        r.getDefaultCell().setFixedHeight(100);
        document.add(r);
        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));

        PdfPTable r5 = new PdfPTable(2);
        r5.setWidthPercentage(100);
        PdfPCell cellFivettr5 = new PdfPCell(new Phrase("Signature Emetteur"));
        PdfPCell cellFivettr55 = new PdfPCell(new Phrase("Signature Récepteur"));

        cellFivettr5.setBorder(Rectangle.NO_BORDER);
        cellFivettr55.setBorder(Rectangle.NO_BORDER);
        r5.addCell(cellFivettr5);
        r5.addCell(cellFivettr55);

        r5.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        r5.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        r5.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
        r5.getDefaultCell().setFixedHeight(100);
        document.add(r5);

        document.close();

        codeCutrrent = "/files/documentsLoyer/" + nomFichier;
        bc.setChemin(codeCutrrent);
        bc.setCheminRecu(null);

        System.out.println(codeCutrrent);
        System.out.println("sortie");

    }

    public void changeDateBC() {
        System.out.println("DATE ENTREIN DE CHANGER");
        isBCValidated = false;
    }

    /**
     * Partie Editer le bon de caisse
     */
    public void updateBC() throws IOException, DocumentException {

        String dateLoyer = loyerSalarieSelectMensuel.getDatedebut().substring(loyerSalarieSelectMensuel.getDatedebut().indexOf('/') + 1);
        String anneeLoyer = dateLoyer.substring(dateLoyer.lastIndexOf('-') + 1);
        String moisLoyer = dateLoyer.substring(3, 5);
        String moisValidation = bc.getMois();
        String anneeValidation = bc.getAnnee();

        dateLoyerBon = bc.getMois() + "/" + bc.getAnnee();
        System.out.println("MOIS LOYER : " + moisLoyer);
        System.out.println("ANNEE LOYER : " + anneeLoyer);

        System.out.println("MOIS VALIDATION : " + moisValidation);
        System.out.println("ANNEE VALIDATION : " + anneeValidation);

        if (anneeValidation.compareToIgnoreCase(anneeLoyer) < 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "ATTENTION", "la date renseignée est anterieure à la date du début du loyer!"));

        } else if (anneeValidation.compareToIgnoreCase(anneeLoyer) == 0) {

            if (moisValidation.compareToIgnoreCase(moisLoyer) < 0) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "ATTENTION", "la date renseignée est anterieure à la date du début du loyer!"));

            } else if (moisValidation.compareToIgnoreCase(moisLoyer) >= 0) {
                Boolean existe = false;

                if (loyerSalarieSelectMensuel != null) {
                    System.out.println("testing mensuel != null");
                    List<BonCaisse> l_boncaisLoyerSalaries = bonCaisseService.getbcByIdLoyerSalarie(loyerSalarieSelectMensuel.getId());

                    if (l_boncaisLoyerSalaries != null) {
                        if (!l_boncaisLoyerSalaries.isEmpty()) {
                            for (BonCaisse b_ : l_boncaisLoyerSalaries) {
                                if (b_.getAnnee().equals(bc.getAnnee()) && b_.getMois().equals(bc.getMois())) {
                                    existe = true;
                                }

                            }
                        }
                    }

                }

                if (existe == true) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, Message.VERIFICATION_ANNEE_MOIS_BONCAISSE, Message.VERIFICATION_ANNEE_MOIS_BONCAISSE));
                    isBCValidated = true;
                    codeCutrrent = "/files/documentsLoyer/bonCaisse" + bc.getId().toString() + ".pdf";
                } else {

                    bc.setLoyerSalarie(loyerSalarieSelectMensuel);

                    bonCaisseService.saveBonCaisse(bc);
                    showBC();
                    bonCaisseService.updateBonCaisse(bc);
                    FacesContext context = FacesContext.getCurrentInstance();
                    context.addMessage(null, new FacesMessage("" + Message.ADD_BON_CAISSE_LOYER, ""));
                    isBCValidated = true;

                }
            }

        } else {
            Boolean existe = false;

            if (loyerSalarieSelectMensuel != null) {
                List<BonCaisse> l_boncaisLoyerSalaries = bonCaisseService.getbcByIdLoyerSalarie(loyerSalarieSelectMensuel.getId());

                if (l_boncaisLoyerSalaries != null) {
                    if (!l_boncaisLoyerSalaries.isEmpty()) {
                        for (BonCaisse b_ : l_boncaisLoyerSalaries) {
                            if (b_.getAnnee().equals(bc.getAnnee()) && b_.getMois().equals(bc.getMois())) {
                                existe = true;
                            }

                        }
                    }
                }

            }

            if (existe == true) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, Message.VERIFICATION_ANNEE_MOIS_BONCAISSE, Message.VERIFICATION_ANNEE_MOIS_BONCAISSE));

            } else {

                bc.setLoyerSalarie(loyerSalarieSelectMensuel);

                bonCaisseService.saveBonCaisse(bc);
                showBC();
                bonCaisseService.updateBonCaisse(bc);
                FacesContext context = FacesContext.getCurrentInstance();

                context.addMessage(null, new FacesMessage("" + Message.ADD_BON_CAISSE_LOYER, ""));
                isBCValidated = true;

            }
        }

//        } else {
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error Ce Loyer n'a pas de bon caisse", ""));
//
//        }
    }

    public void annulerUpdateBC() {
        lsbc = null;
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("" + Message.MISE_JOUR_CANCEL, ""));
    }

    public void removeBCSalarie(BonCaisse b) {

        for (int i = 0; i < BCRecu.size(); i++) {

            if (BCRecu.get(i).getId().equals(b.getId())) {
                BCRecu.remove(BCRecu.get(i));
            }
        }

        bonCaisseService.removeBCChantierByLC(b);

        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("" + Message.DELETE_RECUCAISSE_TRUE, Message.DELETE_RECUCAISSE_TRUE));

    }

    /**
     * telecharger recu caisse salarie
     */
    public void visualiser_Recu_Caisse_salarie() throws IOException, DocumentException {

        System.out.println("entree");

        // if (BCRecu.size() > 0) {
        FacesContext context = FacesContext.getCurrentInstance();
//            HttpServletResponse response = (HttpServletResponse) context
//                    .getExternalContext().getResponse();

        // String chemin = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/document");
        String chemin = ConstanteMb.getRepertoire() + "/files/RecuCaisseLoyerSalarie";
        String nomFichier = "recuCaisse.pdf";
        File file = new File(chemin + nomFichier);
        Document document = new Document();

        PdfWriter.getInstance(document, new FileOutputStream(file));

        document.open();
        String chemin_Image = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/img/logo.png");
        Image image1 = Image.getInstance(chemin_Image);

        document.add(image1);
        Font f = new Font(Font.FontFamily.TIMES_ROMAN, 20.0f, Font.UNDERLINE, BaseColor.BLACK);
        Paragraph paragraph = new Paragraph("Reçu Caisse ", f);
        paragraph.setAlignment(Element.ALIGN_CENTER);

        document.add(paragraph);
        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));
        document.add(new Paragraph("Identifiant :" + String.valueOf(loyerSalarieSelectMensuel.getId()) + "\t            " + "La ville :" + loyerSalarieSelectMensuel.getVille()));
        document.add(new Paragraph(" "));
        document.add(new Paragraph("La ville :" + loyerSalarieSelectMensuel.getVille()));
        document.add(new Paragraph(" "));
        document.add(new Paragraph("Numéro Contrat :" + loyerSalarieSelectMensuel.getNumcontrat()));
        document.add(new Paragraph(" "));

        document.add(new Paragraph("Type Bénéficiaire : salarié"));
        document.add(new Paragraph(" "));

        if (loyerSalarieSelectMensuel.getMensuel_Principal().getMatricule() != null) {

            document.add(new Paragraph("Matricule de  bénéficiaire :" + loyerSalarieSelectMensuel.getMensuel_Principal().getMatricule()));
            document.add(new Paragraph(" "));
        }

        if (loyerSalarieSelectMensuel.getMensuel_Principal().getNom() != null) {

            document.add(new Paragraph("Le nom de bénéficiaire :" + loyerSalarieSelectMensuel.getMensuel_Principal().getNom()));
            document.add(new Paragraph(" "));
        }

        if (loyerSalarieSelectMensuel.getMensuel_Principal().getPrenom() != null) {

            document.add(new Paragraph("Le prénom de  bénéficiaire :" + loyerSalarieSelectMensuel.getMensuel_Principal().getPrenom()));
            document.add(new Paragraph(" "));

        }

        if (loyerSalarieSelectMensuel.getMensuel_Principal().getFonction() != null) {
            document.add(new Paragraph("la fonction du Bénéficiaire :" + loyerSalarieSelectMensuel.getMensuel_Principal().getFonction().getFonction()));
            document.add(new Paragraph(" "));
        } else {
            document.add(new Paragraph("la fonction du Bénéficiaire : aucun fonction à ce mensuel"));
            document.add(new Paragraph(" "));
        }

        document.add(new Paragraph("le montant de loyer :" + loyerSalarieSelectMensuel.getMontantloyer()));
        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));
        document.add(new Paragraph("Signature  récépteur :"));
        Paragraph paragraph_emetteur = new Paragraph("Signature de l'émetteur :");
        paragraph_emetteur.setAlignment(Element.ALIGN_RIGHT);
        document.add(paragraph_emetteur);
        document.close();
        codeCurrent = "/files/RecuCaisseLoyerSalarie" + nomFichier;
//            response.reset();
//            response.setBufferSize(DEFAULT_BUFFER_SIZE);
//            response.setContentType("application/pdf");
//            response.setHeader("Content-Length", String.valueOf(file.length()));
//            response.setHeader("Content-Disposition", "attachment;filename=\""
//                    + nomFichier + "\"");
//            BufferedInputStream input = null;
//            BufferedOutputStream output = null;
//            try {
//                input = new BufferedInputStream(new FileInputStream(file),
//                        DEFAULT_BUFFER_SIZE);
//                output = new BufferedOutputStream(response.getOutputStream(),
//                        DEFAULT_BUFFER_SIZE);
//                byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
//                int length;
//                while ((length = input.read(buffer)) > 0) {
//                    output.write(buffer, 0, length);
//                }
//            } finally {
//                input.close();
//                output.close();
//            }
//            context.responseComplete();
        // } else {
        //     FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, Message.NO_BON_CAISSE_LOYER, ""));

        //}
    }

    /**
     * telecharger recu caisse salarie
     */
    public void downLoad_Recu_Caisse_salarie() throws IOException, DocumentException {

        System.out.println("entree");

        if (BCRecu.size() > 0) {
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletResponse response = (HttpServletResponse) context
                    .getExternalContext().getResponse();

            String chemin = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/document");
            // String chemin = ConstanteMb.getRepertoire() + "/files/RecuCaisseLoyerSalarie";
            String nomFichier = "recuCaisse.pdf";
            File file = new File(chemin + nomFichier);
            Document document = new Document();

            PdfWriter.getInstance(document, new FileOutputStream(file));

            document.open();
            String chemin_Image = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/img/logo.png");
            Image image1 = Image.getInstance(chemin_Image);

            document.add(image1);
            Font f = new Font(Font.FontFamily.TIMES_ROMAN, 20.0f, Font.UNDERLINE, BaseColor.BLACK);
            Paragraph paragraph = new Paragraph("Reçu Caisse ", f);
            paragraph.setAlignment(Element.ALIGN_CENTER);

            document.add(paragraph);
            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Identifiant :" + String.valueOf(loyerSalarieSelectMensuel.getId()) + "\t" + "La ville :" + loyerSalarieSelectMensuel.getVille()));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("La ville :" + loyerSalarieSelectMensuel.getVille()));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Numéro Contrat :" + loyerSalarieSelectMensuel.getNumcontrat()));
            document.add(new Paragraph(" "));

            document.add(new Paragraph("Type Bénéficiaire : salarié"));
            document.add(new Paragraph(" "));

            if (loyerSalarieSelectMensuel.getMensuel_Principal().getMatricule() != null) {

                document.add(new Paragraph("Matricule de  bénéficiaire :" + loyerSalarieSelectMensuel.getMensuel_Principal().getMatricule()));
                document.add(new Paragraph(" "));
            }

            if (loyerSalarieSelectMensuel.getMensuel_Principal().getNom() != null) {

                document.add(new Paragraph("Le nom de bénéficiaire :" + loyerSalarieSelectMensuel.getMensuel_Principal().getNom()));
                document.add(new Paragraph(" "));
            }

            if (loyerSalarieSelectMensuel.getMensuel_Principal().getPrenom() != null) {

                document.add(new Paragraph("Le prénom de  bénéficiaire :" + loyerSalarieSelectMensuel.getMensuel_Principal().getPrenom()));
                document.add(new Paragraph(" "));

            }

            if (loyerSalarieSelectMensuel.getMensuel_Principal().getFonction() != null) {
                document.add(new Paragraph("la fonction du Bénéficiaire :" + loyerSalarieSelectMensuel.getMensuel_Principal().getFonction().getFonction()));
                document.add(new Paragraph(" "));
            } else {
                document.add(new Paragraph("la fonction du Bénéficiaire : aucun fonction à ce mensuel"));
                document.add(new Paragraph(" "));
            }

            document.add(new Paragraph("le montant de loyer :" + loyerSalarieSelectMensuel.getMontantloyer()));
            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Signature  récépteur :"));
            Paragraph paragraph_emetteur = new Paragraph("Signature de l'émetteur :");
            paragraph_emetteur.setAlignment(Element.ALIGN_RIGHT);
            document.add(paragraph_emetteur);
            document.close();
            //  codeCurrent = "/files/RecuCaisseLoyerSalarie" + nomFichier;
            response.reset();
            response.setBufferSize(DEFAULT_BUFFER_SIZE);
            response.setContentType("application/pdf");
            response.setHeader("Content-Length", String.valueOf(file.length()));
            response.setHeader("Content-Disposition", "attachment;filename=\""
                    + nomFichier + "\"");
            BufferedInputStream input = null;
            BufferedOutputStream output = null;
            try {
                input = new BufferedInputStream(new FileInputStream(file),
                        DEFAULT_BUFFER_SIZE);
                output = new BufferedOutputStream(response.getOutputStream(),
                        DEFAULT_BUFFER_SIZE);
                byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
                int length;
                while ((length = input.read(buffer)) > 0) {
                    output.write(buffer, 0, length);
                }
            } finally {
                input.close();
                output.close();
            }
            context.responseComplete();
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, Message.NO_BON_CAISSE_LOYER, ""));

        }
    }

    public void loadHisto() {
        System.out.println("loading historique");
        loyerSH = hService.findAllLoyerS();
        loyerCH = hService.findAllLoyerC();
    }

    public void setLoyerSelected(LoyerSalarie loyer) throws ParseException {
        loyerS = loyer;
        dateRenduS = null;

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        dateDebutLoyerSelected = formatter.parse(loyerS.getDatedebut());

    }

    public void annulerAffectLoyerS() throws ParseException {

        if (!Module.checkDate(dateDebutLoyerSelected, null, dateRenduS)) {
            Module.message(2, "date affectation doit etre supperieure a la derniere date de rendu", "");
            return;
        }

        if (!Module.checkDate(dateRenduS, null, new Date())) {
            Module.message(2, "date affectation doit etre inférieure a la date d'aujourd'hui", "");
            return;
        }

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        System.out.println("annulation de " + loyerS.getVille());
        hService.addRecordLoyerS(new Historique_LoyerS(), loyerS.getMensuel_Principal(), loyerS, formatter.parse(loyerS.getDatedebut()), dateRenduS);
        //loyerS.setMensuel_Principal(null);      
        loyerS.setEstArchive(true);
        System.out.println("UPDATE STARTING === ");
        loyerService.updateLoyerSalarie(loyerS);
        System.out.println("UPDATE DONE....");
        //loyerService.deleteLoyerSalarie(loyerS);
        //listeLSalrie = loyerService.loyerSalaries();
        loyerSH = hService.findAllLoyerS();
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("affectation de loyer annulée avec succès!", ""));
    }

    public void rechercheMensuel() {
        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        mensuelService = ctx.getBean(MensuelService.class);

        mensuels = new ArrayList<>();

        if (mensuelToSearch.getMatricule().equals("") && mensuelToSearch.getNom().equals("") && mensuelToSearch.getPrenom().equals("") && mensuelToSearch.getCin().equals("")) {

            ELContext elContext = FacesContext.getCurrentInstance().getELContext();

            ma.bservices.tgcc.mb.services.MensuelMb mensuel_bean = (ma.bservices.tgcc.mb.services.MensuelMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "mensuelServMb");
            mensuels = mensuel_bean.getL_mensuels();

        }

        this.searchMensuelList = mensuelService.search(mensuelToSearch.getMatricule(), mensuelToSearch.getNom(), mensuelToSearch.getPrenom(), "", mensuelToSearch.getCin());

        this.mensuels = searchMensuelList;

    }

    //selectionne bon de caisse pour afficher details
    public void setBonCaisseSelected(BonCaisse bc2) {
        BonCaiss = bc2;
        System.out.println("bon caisse : " + bc2.getMois());

    }

    public void initUpdate(LoyerSalarie ls) {
        updateSalarie = true;

        loyerService.updateLoyerSalarie(ls);
    }

    /**
     * methode qui permet de redirect vers ajoute bon caisse
     */
    public void redirect_boncaisse(BonCaisse bon) {

        bc = bon;

    }

    /**
     *
     */
    public void afficherMensuel() {

        ELContext elContext = FacesContext.getCurrentInstance().getELContext();

        ma.bservices.tgcc.mb.services.MensuelMb mensuel_bean = (ma.bservices.tgcc.mb.services.MensuelMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "mensuelServMb");
        mensuels = mensuel_bean.getL_mensuels();

    }

    public void afficherListeLoyerSalarie() {
        // ELContext elContext = FacesContext.getCurrentInstance().getELContext();

        // ma.bservices.tgcc.mb.services.LoyerMb loyer_bean = (ma.bservices.tgcc.mb.services.LoyerMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "loyer_ServMb");
        //listeLSalrie = loyer_bean.getL_loyer_salaries();
        // System.out.println(listeLSalrie);
        Date_Debut_Loyer_To_Search = null;
        listeLSalrie = loyerService.loyerSalaries();
        // date_Debut_Loyer_To_Search = null;
    }

    public void desaffecter_() {

    }

    public String convertFormtDateString(String date) {

        String d = "";
        if (date != null) {

            return d = date.subSequence(8, 10) + "/" + date.subSequence(5, 7) + "/" + date.subSequence(0, 4);

        }
        return d;
    }

    public void initImportRC(BonCaisse bonCaisseIt) {
        System.out.println("IMPORTING RC : " + bonCaisseIt.getChemin());
        bc = bonCaisseIt;
    }

    /*
     * upload file 
     */
    public void save() throws IOException {

        Boolean existe = false;

        System.out.println("BON DE CAISSE CURRENT : " + bc.getChemin());

//        if (bc != null) {
//            List<BonCaisse> l_bonCaisseChantiers = bonCaisseService.getbcByIdLoyerSalarie(loyerSalarieSelectMensuel.getId());
//
//            if (l_bonCaisseChantiers != null) {
//                if (!l_bonCaisseChantiers.isEmpty()) {
//                    for (BonCaisse b_ : l_bonCaisseChantiers) {
//                        if (bc.getAnnee().equals(b_.getAnnee()) && bc.getMois().equals(b_.getMois())) {
//                            existe = true;
//                            System.out.println("DOC ALREADY THERE! BON");
//                        }
//                    }
//                }
//            }
//
//        }
        //  String chemin = TgccFileManager.getCheminFichier("Documents Mensuel");
        String chemin = ConstanteMb.getRepertoire() + "/files/documentsLoyer";
        Path folder = Paths.get(chemin);
        Files.createDirectories(folder);
        if (uploadedFile == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, Message.STRING_CHARGE_DOCUMENT, Message.STRING_CHARGE_DOCUMENT));

        } else if (uploadedFile.getFileName().equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, Message.STRING_CHARGE_DOCUMENT, Message.STRING_CHARGE_DOCUMENT));
        } else {
            String filename = FilenameUtils.getBaseName(uploadedFile.getFileName());
            String extension = FilenameUtils.getExtension(uploadedFile.getFileName());

            if (!"pdf".equals(extension)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, Message.STRING_CHARGE_DOCUMENT_PDF, Message.STRING_CHARGE_DOCUMENT_PDF));
            } else {

                Path file = Files.createTempFile(folder, filename + "-", "." + extension);

                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                try (InputStream input = uploadedFile.getInputstream()) {
                    Files.copy(input, file, StandardCopyOption.REPLACE_EXISTING);
                    bc.setCheminRecu("/files/documentsLoyer" + "/" + file.getFileName());
                    //    bc.setLoyerSalarie(loyerSalarieSelectMensuel);

                    bonCaisseService.updateBonCaisse(bc);

                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, Message.STRING_CHARGE_DOCUMENT_DONE, Message.STRING_CHARGE_DOCUMENT_DONE));
                    // listeBonCaiss = bonCaisseService.getbcByIdLoyerSalarie(loyerSalarieSelectMensuel.getId());
                    BCRecu = bonCaisseService.getbcByIdLoyerSalarie(loyerSalarieSelectMensuel.getId());
                    System.out.println("RECU ADDED : CHEMIN BON : " + bc.getChemin());
                    System.out.println("RECU ADDED : CHEMIN RECU : " + bc.getCheminRecu());

                    //  return "telechargerRecuSalarie.xhtml?faces-redirect=true";
                }

            }
        }

    }

    /**
     * methode qui permet de telecharger fichier
     *
     * @param d
     * @throws IOException
     */
    public void downLoad(BonCaisse d) throws IOException {

        CiterneServiceBean citerneServiceBean = new CiterneServiceBean();
        if (d != null) {
            if (d.getChemin() != null) {
                citerneServiceBean.telecharger_fichier(d.getChemin());

            }
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
