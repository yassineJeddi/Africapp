/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.mb;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import ma.bservices.beans.Chantier;
import ma.bservices.beans.Presence;
import ma.bservices.beans.Salarie;
import ma.bservices.constantes.Constantes;
import ma.bservices.mb.services.Module;
import ma.bservices.paginate.AbsensePagination;
import ma.bservices.paginate.PresencePagination;
import ma.bservices.services.ChantierService;
import ma.bservices.services.PresenceService;
import ma.bservices.services.SalarieService;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.stereotype.Component;

/**
 *
 * @author Mahdi
 */
@Component
@ManagedBean
@ViewScoped
public class PresenceMb implements Serializable {

    //Présences
    @ManagedProperty(value = "#{presenceService}")
    private PresenceService presenceService;
    @ManagedProperty(value = "#{salarieService}")
    private SalarieService salarieService;
    @ManagedProperty(value = "#{chantierServiceEvol}")
    private ChantierService chantierService;
    private List<Presence> presences;
    private Salarie findSalarie = new Salarie();
    private Date dateDe, dateDe_Excel, dateDe_Excel_Q1, dateDe_Excel_Q2;
    private Date dateA, dateA_Excel, dateA_Excel_Q1, dateA_Excel_Q2;
    private Integer idChantier, idChantier_Excel;
    private String cin = "", cnss = "", matricule = "";
    private String nombreHeuresMinutesPresencesSalarie;
    private StreamedContent file_Excel;
    private StreamedContent file_Excel_Q;


    /* variable de pagination */
    private Integer page;
    private Boolean prev, next, last, first, pageId;
    private Integer i; //nombre de pages après traitement, c'est le nombre de page qu'on affiche dans la vue
    private Integer var;
    private DetailSalarieMb detailSalMB;
    /*END OF variables de pagination*/

 /*variable de la recherche onglet presence*/
    private Date de, a;
    private Integer idchantierPresence;
    private String sum;

    /*end of variables de recherche*/
    //getters & setters
    public Integer getIdChantier_Excel() {
        return idChantier_Excel;
    }

    public ChantierService getChantierService() {
        return chantierService;
    }

    public StreamedContent getFile_Excel_Q() {
        return file_Excel_Q;
    }

    public void setFile_Excel_Q(StreamedContent file_Excel_Q) {
        this.file_Excel_Q = file_Excel_Q;
    }

    public Date getDateDe_Excel_Q1() {
        return dateDe_Excel_Q1;
    }

    public void setDateDe_Excel_Q1(Date dateDe_Excel_Q1) {
        this.dateDe_Excel_Q1 = dateDe_Excel_Q1;
    }

    public String getSum() {
        return sum;
    }

    public void setSum(String sum) {
        this.sum = sum;
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

    public DetailSalarieMb getDetailSalMB() {
        return detailSalMB;
    }

    public void setDetailSalMB(DetailSalarieMb detailSalMB) {
        this.detailSalMB = detailSalMB;
    }

    public Date getDe() {
        return de;
    }

    public void setDe(Date de) {
        this.de = de;
    }

    public Date getA() {
        return a;
    }

    public void setA(Date a) {
        this.a = a;
    }

    public Integer getIdchantierPresence() {
        return idchantierPresence;
    }

    public void setIdchantierPresence(Integer idchantierPresence) {
        this.idchantierPresence = idchantierPresence;
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

    public Integer getI() {
        return i;
    }

    public void setI(Integer i) {
        this.i = i;
    }

    public Integer getVar() {
        return var;
    }

    public void setVar(Integer var) {
        this.var = var;
    }

    public Date getDateDe_Excel_Q2() {
        return dateDe_Excel_Q2;
    }

    public void setDateDe_Excel_Q2(Date dateDe_Excel_Q2) {
        this.dateDe_Excel_Q2 = dateDe_Excel_Q2;
    }

    public Date getDateA_Excel_Q1() {
        return dateA_Excel_Q1;
    }

    public void setDateA_Excel_Q1(Date dateA_Excel_Q1) {
        this.dateA_Excel_Q1 = dateA_Excel_Q1;
    }

    public Date getDateA_Excel_Q2() {
        return dateA_Excel_Q2;
    }

    public void setDateA_Excel_Q2(Date dateA_Excel_Q2) {
        this.dateA_Excel_Q2 = dateA_Excel_Q2;
    }

    public StreamedContent getFile_Excel() {
        return file_Excel;
    }

    public void setFile_Excel(StreamedContent file_Excel) {
        this.file_Excel = file_Excel;
    }

    public void setChantierService(ChantierService chantierService) {
        this.chantierService = chantierService;
    }

    public void setIdChantier_Excel(Integer idChantier_Excel) {
        this.idChantier_Excel = idChantier_Excel;
    }

    public Date getDateDe_Excel() {
        return dateDe_Excel;
    }

    public void setDateDe_Excel(Date dateDe_Excel) {
        this.dateDe_Excel = dateDe_Excel;
    }

    public Date getDateA_Excel() {
        return dateA_Excel;
    }

    public void setDateA_Excel(Date dateA_Excel) {
        this.dateA_Excel = dateA_Excel;
    }

    public String getNombreHeuresMinutesPresencesSalarie() {
        return nombreHeuresMinutesPresencesSalarie;
    }

    public void setNombreHeuresMinutesPresencesSalarie(String nombreHeuresMinutesPresencesSalarie) {
        this.nombreHeuresMinutesPresencesSalarie = nombreHeuresMinutesPresencesSalarie;
    }

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public String getCnss() {
        return cnss;
    }

    public void setCnss(String cnss) {
        this.cnss = cnss;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public Integer getIdChantier() {
        return idChantier;
    }

    public void setIdChantier(Integer idChantier) {
        this.idChantier = idChantier;
    }

    public Date getDateDe() {
        return dateDe;
    }

    public void setDateDe(Date dateDe) {
        this.dateDe = dateDe;
    }

    public Date getDateA() {
        return dateA;
    }

    public void setDateA(Date dateA) {
        this.dateA = dateA;
    }

    public PresenceService getPresenceService() {
        return presenceService;
    }

    public void setPresenceService(PresenceService presenceService) {
        this.presenceService = presenceService;
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

    public SalarieService getSalarieService() {
        return salarieService;
    }

    public void setSalarieService(SalarieService salarieService) {
        this.salarieService = salarieService;
    }

    //methode
    @PostConstruct
    public void init() {
        presenceService = Module.ctx.getBean(PresenceService.class);
        salarieService = Module.ctx.getBean(SalarieService.class);
        chantierService = Module.ctx.getBean(ChantierService.class);

        // @handles pagination
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        detailSalMB = (DetailSalarieMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "detailMb");
        
        
        if (detailSalMB != null && detailSalMB.getSalarie() != null) {
            this.findSalarie = detailSalMB.getSalarie();
            sum = presenceService.nombreHeuresMinutesPresencesSalarie(findSalarie.getMatricule(), findSalarie.getCin(), findSalarie.getCnss(), null, null, null);
        }
        

       // sum = presenceService.nombreHeuresMinutesPresencesSalarie(findSalarie.getMatricule(), findSalarie.getCin(), findSalarie.getCnss(), null, null, null);
        
        
        page = 1;
        i = 0;
        //i = presenceService.nombrePresencesSalarie(this.findSalarie.getMatricule(), null, null, null);
        // i = absenceService.nombreAbsences(this.findSalarie.getMatricule() == null ? "" : this.findSalarie.getMatricule(), this.findSalarie.getCin() == null ? "" : this.findSalarie.getCin(), this.findSalarie.getCnss() == null ? "" : this.findSalarie.getCnss(), this.IDtypeAb);
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

    public void recherche() {
        if ("".equals(cin) && "".equals(cnss) && "".equals(matricule)) {
            Module.message(2, "Veuillez saisir un des champs suivants : matricule, cin ou cnss", "");
            return;
        }
        if (dateDe != null && dateA != null && dateDe.compareTo(dateA) >= 1) {
            Module.message(2, "Attention ! La date 'De' doit être inférieur ou égal à la date 'À'", "");
            return;
        }
        findSalarie = salarieService.getSalarie(matricule, cin, cnss, "", "");
        if (findSalarie == null) {
            Module.message(2, "Salarié inexistant", "");
            return;
        }
        idChantier = (idChantier != null && idChantier == 0) ? null : idChantier;
        presences = presenceService.listePresencesSalarie(matricule, cin, cnss, idChantier, dateDe, dateA, 0, 10);
        String date_De, date_A;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        if (dateDe != null) {
            date_De = sdf.format(dateDe);
            dateDe = new Date(date_De);
        }
        if (dateA != null) {
            date_A = sdf.format(dateA);
            dateA = new Date(date_A);
        }
        nombreHeuresMinutesPresencesSalarie = presenceService.nombreHeuresMinutesPresencesSalarie(matricule, cin, cnss, idChantier, dateDe, dateA);
        System.out.println("nombre d'heures " + nombreHeuresMinutesPresencesSalarie);

    }

    public void findPresenceSalarie() {
        
        presences = new ArrayList<Presence>();
       if ("".equals(idChantier) || idChantier == null || idChantier<=0) {
            Module.message(2, "Veuillez sélectionner le chantier", "");
            return;
        } 
       if ("".equals(dateDe) || dateDe == null) {
            Module.message(2, "Veuillez sélectionner date début", "");
            return;
        }  
        try { 
            presences = presenceService.findPresencesSalarie(idChantier, dateDe, dateA);
        } catch (Exception e) {
            System.out.println(" Erreur de récupération list des presences car "+e.getMessage());
        }
       
    }

    public void generateExcel() throws IOException {
        if (dateDe_Excel == null || dateA_Excel == null || idChantier_Excel == null) {
            Module.message(2, "Attention ! Veuillez saisir tous les champs", "");
            return;
        }
        if (dateDe_Excel.compareTo(dateA_Excel) == 1) {
            Module.message(2, "Attention ! La date 'De' doit être inférieur ou égal à la date 'À'", "");
            return;
        }
        String date_De, date_A;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        date_De = sdf.format(dateDe_Excel);
        date_A = sdf.format(dateA_Excel);
        dateDe_Excel = new Date(date_De);
        dateA_Excel = new Date(date_A);
        System.out.println("dateDe " + date_De + " DateA" + date_A);
        System.out.println("dateDe " + dateDe_Excel + " DateA" + dateA_Excel);
        Chantier c = chantierService.getChantier(idChantier_Excel);
        JasperPrint jasperPrint = jasperPrintNombreHeuresSalarie("", c.getCode(), date_De, date_A);
        try {

            if (jasperPrint != null) {

                /**
                 * *** Contenu temporairement en alfresco ****
                 */
                ByteArrayOutputStream output = new ByteArrayOutputStream();
                JRXlsExporter exporterXLS = new JRXlsExporter();
                exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
                exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, output);
                exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
                exporterXLS.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
                exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
                exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);

                exporterXLS.exportReport();

                byte[] bytes = output.toByteArray();
                try (InputStream input = new ByteArrayInputStream(bytes)) {
                    file_Excel = new DefaultStreamedContent(input,
                            "application/vnd.ms-excel",
                            date_De + "-" + date_A + "-" + c.getCode().trim() + ".xls");
                }
                /**
                 * ****************************
                 */
            } else {
                Module.message(2, "Echec de la génération du fichier excel", "");
            }
        } catch (JRException e) {
            System.out.println("erreur " + e.getMessage());
            Module.message(2, "Echec de la génération du fichier excel", "");
        }
    }

    public void genereteExcelQ(String q) throws IOException {
        String param = "";
        String date_De, date_A;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        if (q.equals("Q1")) {
            if (dateDe_Excel_Q1 == null || dateA_Excel_Q1 == null) {
                Module.message(2, "Attention ! Veuillez saisir tous les champs", "");
                return;
            }
            if (dateDe_Excel_Q1.compareTo(dateA_Excel_Q1) == 1) {
                Module.message(2, "Attention ! La date 'De' doit être inférieur ou égal à la date 'À'", "");
                return;
            }
            param = "heures_travaillées_Q";
            date_De = sdf.format(dateDe_Excel_Q1);
            date_A = sdf.format(dateA_Excel_Q1);
        } else {
            if (dateDe_Excel_Q2 == null || dateA_Excel_Q2 == null) {
                Module.message(2, "Attention ! Veuillez saisir tous les champs", "");
                return;
            }
            if (dateDe_Excel_Q2.compareTo(dateA_Excel_Q2) == 1) {
                Module.message(2, "Attention ! La date 'De' doit être inférieur ou égal à la date 'À'", "");
                return;
            }
            param = "heures_travaillées";
            date_De = sdf.format(dateDe_Excel_Q2);
            date_A = sdf.format(dateA_Excel_Q2);
        }
        System.out.println("dateDe " + date_De + " DateA" + date_A);
        try {
            JasperPrint jasperPrint = jasperPrintPresences(param, date_De, date_A);
            if (jasperPrint != null) {
                ByteArrayOutputStream output = new ByteArrayOutputStream();
                JRXlsExporter exporterXLS = new JRXlsExporter();
                exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
                exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, output);
                exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
                exporterXLS.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
                exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
                exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
                exporterXLS.exportReport();

                byte[] bytes = output.toByteArray();
                try (InputStream input = new ByteArrayInputStream(bytes)) {
                    file_Excel_Q = new DefaultStreamedContent(input,
                            "application/vnd.ms-excel",
                            date_De + "-" + date_A + ".xls");
                }

                /**
                 * ****************************
                 */
            } else {
                Module.message(2, "Echec de la génération du fichier excel", "");
            }

        } catch (JRException e) {
            System.out.println("erreur " + e.getMessage());
            Module.message(2, "Echec de la génération du fichier excel", "");
        }

    }

    /**
     * Générer un fichier jasper: nombres des heures de travail d'un salarié
     * dans un chantier et pour une période déterminé par la date de début et la
     * date de fin
     *
     * @param ville
     * @param codeChantier
     * @param dateDe
     * @param dateA
     * @return
     */
    public JasperPrint jasperPrintNombreHeuresSalarie(String ville, String codeChantier,
            String dateDe, String dateA) {
        // TODO Auto-generated method stub

        try {

            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            InputStream inputStream = classLoader.getResourceAsStream(Constantes.getInstance().jrxmlRapportPresencesSalaries);
            JasperDesign jasperDesign = JRXmlLoader.load(inputStream);
            System.out.println("date de  " + dateDe + " date A : " + dateA);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date datede = new Date(dateDe);
            Date datea = new Date(dateA);
            dateDe = sdf.format(datede);
            dateA = sdf.format(datea);
            System.out.println("after date de  " + dateDe + " date A : " + dateA);
            //Avoir la requête SQL selon les critères saisis au niveau du formulaire
            String requeteSQL = presenceService.requeteSQLGenerationExcelPresencesSalaries(ville, codeChantier, dateDe, dateA);
            System.out.println("requeteSQL: " + requeteSQL);
            //Ajout de la requête au niveau du fichier rapportPresences.jrxml
            JRDesignQuery query = new JRDesignQuery();
            query.setText(requeteSQL);
            jasperDesign.setQuery(query);

            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

            String bdUrl = Constantes.getInstance().bdUrl;
            String nomUtilisateur = Constantes.getInstance().nomUtilisateur;
            String motDePasse = Constantes.getInstance().motDePasse;
            Connection connection = DriverManager.getConnection(bdUrl, nomUtilisateur, motDePasse);

            //Paramètres du rapport
            Map parametresRapport = new HashMap();

            if (codeChantier != "") {
                parametresRapport.put("CHANTIER", "Chantier : " + codeChantier.toUpperCase());
            } else {
                parametresRapport.put("CHANTIER", "");
            }

            if (ville != "") {
                parametresRapport.put("VILLE", "Ville : " + ville.toUpperCase());
            } else {
                parametresRapport.put("VILLE", "");
            }
//            dateDe = dateDe.substring(5, 7) + "/" + dateDe.substring(8) + "/" + dateDe.substring(0, 4);
//            dateA = dateA.substring(5, 7) + "/" + dateA.substring(8) + "/" + dateA.substring(0, 4);
            parametresRapport.put("DATE_POINTAGE", "POINTAGE DU  " + dateDe + " AU " + dateA);

            //Compilation : vérifie la structure du modèle XML
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametresRapport, connection);

            return jasperPrint;

        } catch (JRException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Générer un fichier jasper: les présences des salariés dans une période
     * déterminée par la date de début et la date de fin passées en paramètres
     *
     * @param rubrique
     * @param dateDe
     * @param dateA
     * @return
     */
    public JasperPrint jasperPrintPresences(String rubrique, String dateDe, String dateA) {
        // TODO Auto-generated method stub

        try {

            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            InputStream inputStream = classLoader.getResourceAsStream(Constantes.getInstance().jrxmlRapportPresences);

            //Chargement
            JasperDesign jasperDesign = JRXmlLoader.load(inputStream);
            System.out.println("date de  " + dateDe + " date A : " + dateA);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date datede = new Date(dateDe);
            Date datea = new Date(dateA);
            dateDe = sdf.format(datede);
            dateA = sdf.format(datea);
            System.out.println("after date de  " + dateDe + " date A : " + dateA);
            //Avoir la requête SQL selon les critères saisis au niveau du formulaire
            String requeteSQL = presenceService.requeteSQLPresences(rubrique, dateDe, dateA);

            //Ajout de la requête au niveau du fichier rapportPresences.jrxml
            JRDesignQuery query = new JRDesignQuery();
            query.setText(requeteSQL);
            jasperDesign.setQuery(query);

            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

            String bdUrl = Constantes.getInstance().bdUrl;
            String nomUtilisateur = Constantes.getInstance().nomUtilisateur;
            String motDePasse = Constantes.getInstance().motDePasse;
            Connection connection = DriverManager.getConnection(bdUrl, nomUtilisateur, motDePasse);

            //Paramètres du rapport
            Map parametresRapport = new HashMap();

//            dateDe = dateDe.substring(5, 7) + "/" + dateDe.substring(8) + "/" + dateDe.substring(0, 4);
//            dateA = dateA.substring(5, 7) + "/" + dateA.substring(8) + "/" + dateA.substring(0, 4);
            parametresRapport.put("DATE_POINTAGE", "POINTAGE DU  " + dateDe + " AU " + dateA);

            //Compilation : vérifie la structure du modèle XML
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametresRapport, connection);

            return jasperPrint;

        } catch (JRException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    /*@handles pagination tab presence*/
    public void onNext() {
        //absences.clear();
        presences.clear();
        page += 1;
        presences = PresencePagination.page(page, this.findSalarie.getMatricule(), idChantier, de, a);

        for (Presence p : presences) {
            System.out.println("PRESENCE N : " + p.getDateSaisieHeureEntree() + "CHANTIER : " + p.getChantier().getCode());
        }

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
        presences.clear();
        presences = PresencePagination.page(page, this.findSalarie.getMatricule(), idChantier, de, a);
        if (page == var) {
            last = true;
            pageId = false;
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
        //presences.clear();
        page -= 1;
        presences = PresencePagination.page(page, this.findSalarie.getMatricule(), idChantier, de, a);

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

    public void initFind() {
        de = a = null;
        idChantier = null;
    }

    public void reinitSearchPresence() {
        page = 1;
        idChantier = null;
        de = null;
        a = null;
        i = presenceService.nombrePresencesSalarie(this.findSalarie.getMatricule(), null, null, null);
        // i = absenceService.nombreAbsences(this.findSalarie.getMatricule() == null ? "" : this.findSalarie.getMatricule(), this.findSalarie.getCin() == null ? "" : this.findSalarie.getCin(), this.findSalarie.getCnss() == null ? "" : this.findSalarie.getCnss(), this.IDtypeAb);
        var = (i % 10 == 0) ? i / 10 : i / 10 + 1;
        //if it's the last page on the pagination 
        if (page == var) {
            last = true;
            pageId = true;
            first = true;
            next = true;
            prev = true;
        }

        sum = presenceService.nombreHeuresMinutesPresencesSalarie(findSalarie.getMatricule(), findSalarie.getCin(), findSalarie.getCnss(), null, null, null);
        onFirst();
    }

    public void current() {
        presences.clear();
        presences = PresencePagination.page(page, this.findSalarie.getMatricule(), idChantier, de, a);

    }

    public void onFirst() {
        page = 1;
        presences = PresencePagination.page(page, this.findSalarie.getMatricule(), idChantier, de, a);

        last = false;
        pageId = false;
        first = true;
        next = false;
        prev = true;

    }

    public void onLast() {
        page = (i % 10 == 0) ? i / 10 : i / 10 + 1;
        presences = PresencePagination.last(this.findSalarie.getMatricule(), idChantier, de, a);
        last = true;
        pageId = false;
        first = false;
        next = true;
        prev = false;
    }

    public void findPresence() {
        System.out.println("DATE DE : " + de);
        System.out.println("DATE A : " + a);

        Date de_Presence = null, a_Presence = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

        if (de != null) {
            de_Presence = new Date(sdf.format(de));
        }
        if (a != null) {
            a_Presence = new Date(sdf.format(a));
        }
        if (de != null && a != null) {
            if (!Module.checkDate(de, null, a)) {
                Module.message(2, "date De doit être inférieur a date A", "");
                return;
            }
        }

        Integer sumJava = 0;
        presences = presenceService.listePresencesSalarie(0, presenceService.nombrePresencesSalarie(findSalarie.getMatricule(), (idchantierPresence != null && idchantierPresence == 0) ? null : idchantierPresence, de, a), findSalarie.getMatricule(), (idchantierPresence != null && idchantierPresence == 0) ? null : idchantierPresence, de, a);
        sum = presenceService.nombreHeuresMinutesPresencesSalarie(findSalarie.getMatricule(), findSalarie.getCin(), findSalarie.getCnss(), (idchantierPresence != null && idchantierPresence == 0) ? null : idchantierPresence, de, a);

        if (sum == null) {
            for (Presence p : presences) {
                if (p.getNombreHeures() != null) {
                    sumJava += p.getNombreHeures() * 60;
                }
                if (p.getNombreMinutes() != null) {
                    sumJava += p.getNombreMinutes();
                }
            }
            sum = (int) (sumJava / 60) + "H " + (int) (sumJava % 60) + " MIN";
            System.out.println("AFTER FOR : DUM JAVA : " + (int) (sumJava / 60) + "H " + (int) (sumJava % 60) + " MIN");
        }

        
        
        page = 1;
        
        i = presenceService.nombrePresencesSalarie(this.findSalarie.getMatricule(), (idchantierPresence != null && idchantierPresence == 0) ? null : idchantierPresence, de, a);
        
        
        System.out.println("NOMBRE DE PAGES : " + i);
        
        
        // i = absenceService.nombreAbsences(this.findSalarie.getMatricule() == null ? "" : this.findSalarie.getMatricule(), this.findSalarie.getCin() == null ? "" : this.findSalarie.getCin(), this.findSalarie.getCnss() == null ? "" : this.findSalarie.getCnss(), this.IDtypeAb);
        var = (i % 10 == 0) ? i / 10 : i / 10 + 1;
        
        
        System.out.println("VAR : " + var);
        
        System.out.println("SUM : " + sum);

        idChantier = (idchantierPresence != null && idchantierPresence == 0) ? null : idchantierPresence;
        if (presences.size() < 10) {
            next = true;
            prev = true;
            last = true;
            first = true;
            pageId = true;
        } else {
            next = false;
            prev = true;
            last = false;
            first = true;
            pageId = false;

        }

    }
}
