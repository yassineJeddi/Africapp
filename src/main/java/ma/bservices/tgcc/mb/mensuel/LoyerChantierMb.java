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
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import ma.bservice.tgcc.Constante.Message;
import ma.bservices.beans.Chantier;
import ma.bservices.mb.services.ConstanteMb;
import ma.bservices.tgcc.service.Mensuel.DocumentService;
import ma.bservices.tgcc.Entity.BonCaisse;
import ma.bservices.tgcc.Entity.Historique_LoyerC;
import ma.bservices.tgcc.Entity.Loyer;
import ma.bservices.tgcc.Entity.LoyerChantier;
import ma.bservices.tgcc.service.Engin.Bean.CiterneServiceBean;
import ma.bservices.tgcc.service.Engin.ChantierService;
import ma.bservices.tgcc.service.Mensuel.BonCaisseService;
import ma.bservices.tgcc.service.Mensuel.HistoriqueService;
import ma.bservices.tgcc.service.Mensuel.LoyerService;
import ma.bservices.tgcc.service.Mensuel.MensuelService;
import static org.apache.catalina.connector.InputBuffer.DEFAULT_BUFFER_SIZE;
import org.apache.commons.io.FilenameUtils;
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
@ManagedBean(name = "loyer_ChantierMb")
@ViewScoped
public class LoyerChantierMb implements Serializable {

    @ManagedProperty(value = "#{loyerService}")
    private LoyerService loyerService;

    @ManagedProperty(value = "#{boncaisseservice}")
    private BonCaisseService bonCaisseService;

    @ManagedProperty(value = "#{historiqueService}")
    private HistoriqueService hService;

    @ManagedProperty(value = "#{chantierService}")
    private ChantierService chantierService;

    @ManagedProperty(value = "#{mensuelService}")
    private MensuelService mensuelService;
    
    @ManagedProperty(value = "#{documentService}")
    private DocumentService documentService;

    private LoyerChantier lc = new LoyerChantier();
    private List<Chantier> chantiers = new ArrayList<>();
    private List<LoyerChantier> listeLChantier = new ArrayList<>();
    private List<ma.bservices.beans.Document> listDoc = new LinkedList();
    private Chantier chantier = new Chantier();

    private boolean updateSalarie = true;
    private Chantier selectChantier = new Chantier();
    private boolean isBCValidated = false;
    private String codeCurrent;
    private String selectedDoc;
    private LoyerChantier lcSelected = new LoyerChantier();
    private LoyerChantier loyerC = new LoyerChantier();
    private BonCaisse BCSelected = new BonCaisse();
    private BonCaisse bcChantier = new BonCaisse();
    private Date Date_Debut_Loyer_chantier;
    private Date dateRenduC;
    private Date dateDebutChan;
    private Date Date_Debut_Loyer_Chantier_To_Search;
    private Loyer loyer_To_Search = new Loyer();
    private List<BonCaisse> listeBonCaiss;
    private LoyerChantier loyerChantierFicheLoyer;
    private String date_debut_loyer_ChantierString = "";
    private UploadedFile uploadedFile;
    private Loyer  loyerToShow;

    
    
    
    /**
     * getters and setters
     *
     * @return
     */
    
    public String getSelectedDoc() {
        return selectedDoc;
    }

    public void setSelectedDoc(String selectedDoc) {
        this.selectedDoc = selectedDoc;
    }

    public DocumentService getDocumentService() {
        return documentService;
    }

    public void setDocumentService(DocumentService documentService) {
        this.documentService = documentService;
    }

    public List<ma.bservices.beans.Document> getListDoc() {
        return listDoc;
    }
    
    public void setListDoc(List<ma.bservices.beans.Document> listDoc) {
        this.listDoc = listDoc;
    }

    public Loyer getLoyerToShow() {
        return loyerToShow;
    }

    public void setLoyerToShow(Loyer loyerToShow) {
        this.loyerToShow = loyerToShow;
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

    public boolean isIsBCValidated() {
        return isBCValidated;
    }

    public boolean isUpdateSalarie() {
        return updateSalarie;
    }

    public void setUpdateSalarie(boolean updateSalarie) {
        this.updateSalarie = updateSalarie;
    }

    public void setIsBCValidated(boolean isBCValidated) {
        this.isBCValidated = isBCValidated;
    }

    public void setChantier(Chantier chantier) {
        this.chantier = chantier;
    }

    public Chantier getSelectChantier() {
        return selectChantier;
    }

    public void setSelectChantier(Chantier selectChantier) {
        this.selectChantier = selectChantier;
    }

    public List<LoyerChantier> getListeLChantier() {
        return listeLChantier;
    }

    public LoyerChantier getLoyerC() {
        return loyerC;
    }

    public String getCodeCurrent() {
        return codeCurrent;
    }

    public void setCodeCurrent(String codeCurrent) {
        this.codeCurrent = codeCurrent;
    }

    public Date getDateDebutChan() {
        return dateDebutChan;
    }

    public void setDateDebutChan(Date dateDebutChan) {
        this.dateDebutChan = dateDebutChan;
    }

    public void setLoyerC(LoyerChantier loyerC) {
        this.loyerC = loyerC;
    }

    public void setListeLChantier(List<LoyerChantier> listeLChantier) {
        this.listeLChantier = listeLChantier;
    }

    public Date getDate_Debut_Loyer_chantier() {
        return Date_Debut_Loyer_chantier;
    }

    public void setDate_Debut_Loyer_chantier(Date Date_Debut_Loyer_chantier) {
        this.Date_Debut_Loyer_chantier = Date_Debut_Loyer_chantier;
    }

    public Date getDate_Debut_Loyer_Chantier_To_Search() {
        return Date_Debut_Loyer_Chantier_To_Search;
    }

    public void setDate_Debut_Loyer_Chantier_To_Search(Date Date_Debut_Loyer_Chantier_To_Search) {
        this.Date_Debut_Loyer_Chantier_To_Search = Date_Debut_Loyer_Chantier_To_Search;
    }

    public Loyer getLoyer_To_Search() {
        return loyer_To_Search;
    }

    public Date getDateRenduC() {
        return dateRenduC;
    }

    public void setDateRenduC(Date dateRenduC) {
        this.dateRenduC = dateRenduC;
    }

    public void setLoyer_To_Search(Loyer loyer_To_Search) {
        this.loyer_To_Search = loyer_To_Search;
    }

    public List<BonCaisse> getListeBonCaiss() {
        return listeBonCaiss;
    }

    public void setListeBonCaiss(List<BonCaisse> listeBonCaiss) {
        this.listeBonCaiss = listeBonCaiss;
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

    public HistoriqueService gethService() {
        return hService;
    }

    public void sethService(HistoriqueService hService) {
        this.hService = hService;
    }

    public ChantierService getChantierService() {
        return chantierService;
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

    public LoyerChantier getLcSelected() {
        return lcSelected;
    }

    public void setLcSelected(LoyerChantier lcSelected) {
        this.lcSelected = lcSelected;
    }

    public BonCaisse getBCSelected() {
        return BCSelected;
    }

    public void setBCSelected(BonCaisse BCSelected) {
        this.BCSelected = BCSelected;
    }

    public BonCaisse getBcChantier() {
        return bcChantier;
    }

    public void setBcChantier(BonCaisse bcChantier) {
        this.bcChantier = bcChantier;
    }

    public LoyerChantier getLoyerChantierFicheLoyer() {
        return loyerChantierFicheLoyer;
    }

    public void setLoyerChantierFicheLoyer(LoyerChantier loyerChantierFicheLoyer) {
        this.loyerChantierFicheLoyer = loyerChantierFicheLoyer;
    }

    public String getDate_debut_loyer_ChantierString() {
        return date_debut_loyer_ChantierString;
    }

    public void setDate_debut_loyer_ChantierString(String date_debut_loyer_ChantierString) {
        this.date_debut_loyer_ChantierString = date_debut_loyer_ChantierString;
    }

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    /**
     * end getters and setters
     */
    @PostConstruct
    public void init() {
        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        loyerService = ctx.getBean(LoyerService.class);
        bonCaisseService = ctx.getBean(BonCaisseService.class);
        chantierService = ctx.getBean(ChantierService.class);
        hService = ctx.getBean(HistoriqueService.class);
        documentService = ctx.getBean(DocumentService.class);

        ELContext elContext = FacesContext.getCurrentInstance().getELContext();

//        ma.bservices.tgcc.mb.services.LoyerMb loyer_bean = (ma.bservices.tgcc.mb.services.LoyerMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "loyer_ServMb");
//        listeLChantier = loyer_bean.getL_loyer_chantiers();
        listeLChantier = loyerService.loyerChantiers();

        System.out.println("entree : " + listeLChantier.size());

        for (LoyerChantier loyerChantier : listeLChantier) {
            for (Chantier chantierInit : loyerChantier.getChantier()) {
                selectChantier = chantierInit;
            }

        }

    }

    public void visualiserRecuChantier(LoyerChantier selected) {
        lcSelected = selected;
        BonCaisse bcupdate = new BonCaisse();

        if (listeBonCaiss != null) {
            listeBonCaiss.clear();
        }
        listeBonCaiss = bonCaisseService.getbcByIdLoyerChan(lcSelected.getId());

    }

    /*
     * consulte loyer chantier
     */
    public void consultLoyerChantier(Chantier selected) {
        selectChantier = selected;
        listeLChantier = new ArrayList<>();
        listeLChantier = loyerService.getLoyerChantierByChantier(selectChantier.getId());
    }

//    public String getChantierFromLoyerCit(LoyerChantier l){
//    String s = "";
//    s = l.getChantiers().get(0).getLib80();
//    return s;
//    }
    /**
     * ajouter Boncaisse a un chantier
     */
    public void updateBonCaisseChantier() throws IOException, DocumentException {

        System.out.println("mois : " + bcChantier.getMois());
        System.out.println("annee : " + bcChantier.getAnnee());
        System.out.println("LOYER DATES : " + lcSelected.getDatedebut());

        String dateLoyer = lcSelected.getDatedebut().substring(lcSelected.getDatedebut().indexOf('-') + 1);

        System.out.println("date LOYEEER : " + dateLoyer);

        String anneeLoyer = dateLoyer.substring(dateLoyer.lastIndexOf('-') + 1);

        String moisLoyer = dateLoyer.substring(0, dateLoyer.lastIndexOf('-'));

        String moisValidation = bcChantier.getMois();
        String anneeValidation = bcChantier.getAnnee();

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

                System.out.println("DATE OK ...");

                Boolean existe = false;

                if (lcSelected != null) {
                    List<BonCaisse> l_bonCaisseChantiers = bonCaisseService.getbcByIdLoyerChan(lcSelected.getId());

                    if (l_bonCaisseChantiers != null) {
                        if (!l_bonCaisseChantiers.isEmpty()) {
                            for (BonCaisse b_ : l_bonCaisseChantiers) {
                                if (bcChantier.getAnnee().equals(b_.getAnnee()) && bcChantier.getMois().equals(b_.getMois())) {
                                    existe = true;
                                }
                            }
                        }
                    }

                }

                if (existe == true) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, Message.VERIFICATION_ANNEE_MOIS_BONCAISSE, Message.VERIFICATION_ANNEE_MOIS_BONCAISSE));

                } else {

                    bcChantier.setLoyerChantier(lcSelected);
                    bonCaisseService.saveBonCaisse(bcChantier);
                    FacesContext context = FacesContext.getCurrentInstance();
                    context.addMessage(null, new FacesMessage("" + Message.ADD_BON_CAISSE_LOYER, ""));
                    isBCValidated = true;

                }
            }

        } else {
            Boolean existe = false;

            if (lcSelected != null) {
                List<BonCaisse> l_bonCaisseChantiers = bonCaisseService.getbcByIdLoyerChan(lcSelected.getId());

                if (l_bonCaisseChantiers != null) {
                    if (!l_bonCaisseChantiers.isEmpty()) {
                        for (BonCaisse b_ : l_bonCaisseChantiers) {
                            if (b_.getAnnee().equals(bcChantier.getAnnee()) && b_.getMois().equals(bcChantier.getMois())) {
                                existe = true;
                            }

                        }
                    }
                }

            }

            if (existe == true) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, Message.VERIFICATION_ANNEE_MOIS_BONCAISSE, Message.VERIFICATION_ANNEE_MOIS_BONCAISSE));

            } else {

                bcChantier.setLoyerChantier(lcSelected);
                bonCaisseService.saveBonCaisse(bcChantier);
                showBC();
                bonCaisseService.updateBonCaisse(bcChantier);
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage("" + Message.ADD_BON_CAISSE_LOYER, ""));
                isBCValidated = true;

            }
        }

    }

    // show bon caisse chantier
    public void showBC() throws IOException, DocumentException {
        System.out.println("entree");

        String chemin = ConstanteMb.getRepertoire() + "/files/documentsLoyerChantier";
        Path folder = Paths.get(chemin);
        Files.createDirectories(folder);
        String uniqueId = bcChantier.getId().toString();
        //String chemin = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/document/");
        String nomFichier = "/bonCaisseChantier" + uniqueId + ".pdf";
        File file = new File(chemin + nomFichier);

        System.out.println("NOM FICHIER :: " + nomFichier);

        Document document = new Document();

        PdfWriter.getInstance(document, new FileOutputStream(file));

        document.open();

        String chemin_Image = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/img/logo.png");
        Image image1 = Image.getInstance(chemin_Image);
        document.add(image1);
        Font f = new Font(Font.FontFamily.TIMES_ROMAN, 20.0f, Font.UNDERLINE, BaseColor.BLACK);
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

        PdfPTable t = new PdfPTable(2);
        t.setWidthPercentage(100);
        PdfPCell cellFive = new PdfPCell(new Phrase(" Mois : " + bcChantier.getMois() + "/" + bcChantier.getAnnee()));
        PdfPCell cellTree = new PdfPCell(new Phrase("N Bon : " + bcChantier.getId()));

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
        PdfPCell cellFivet = new PdfPCell(new Phrase("Chantier : " + lcSelected.getChantiers().get(0).getCode()));
        PdfPCell cellTreet = new PdfPCell(new Phrase("Ville : " + lcSelected.getVille()));

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

        document.add(new Paragraph("Bénéficiaire : Appt Ouvriers"));

        document.add(new Paragraph(" "));

        PdfPTable r = new PdfPTable(2);
        r.setWidthPercentage(100);
        PdfPCell cellFivettr = new PdfPCell(new Phrase("Montant : " + lcSelected.getMontantloyer()));
        PdfPCell cellFivettrr = new PdfPCell(new Phrase("Mode de Paiement : " + lcSelected.getModepaiment()));
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

        PdfPTable s = new PdfPTable(2);
        s.setWidthPercentage(100);
        PdfPCell cellFivettrs = new PdfPCell(new Phrase("Signature de l'emetteur"));
        PdfPCell cellFivettrrs = new PdfPCell(new Phrase("Signature de recepteur"));

        cellFivettrs.setBorder(Rectangle.NO_BORDER);
        cellFivettrrs.setBorder(Rectangle.NO_BORDER);
        s.addCell(cellFivettrs);
        s.addCell(cellFivettrrs);
        s.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        s.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        s.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
        s.getDefaultCell().setFixedHeight(100);
        document.add(s);
        document.add(new Paragraph(" "));

        document.close();

        // codeCurrent =  FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/document/bonCaisseC" + lcSelected.getId() + ".pdf");
        codeCurrent = "/files/documentsLoyerChantier/" + nomFichier;
        bcChantier.setChemin(codeCurrent);
        bcChantier.setCheminRecu(null);
        System.out.println(codeCurrent);
        System.out.println("sortie");

    }

    /**
     * telecharger recu caisse chantier
     */

    /**
     * telecharger recu caisse chantier
     *
     * @throws java.io.IOException
     */
    public void downLoad_Recu_Caisse_Chantier() throws IOException, DocumentException {

        System.out.println("entree");

        if (listeBonCaiss != null) {

            if (listeBonCaiss.size() > 0) {

                System.out.println("entree 2 ");

                FacesContext context = FacesContext.getCurrentInstance();
                HttpServletResponse response = (HttpServletResponse) context
                        .getExternalContext().getResponse();
                String chemin = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/document");
                String nomFichier = "recuCaisse.pdf";

                File file = new File(chemin + nomFichier);
                if (!file.exists()) {
                    file.createNewFile();
                }
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
                document.add(new Paragraph("Identifiant :" + String.valueOf(lcSelected.getId())));
                document.add(new Paragraph(" "));
                document.add(new Paragraph("La ville :" + lcSelected.getVille()));
                document.add(new Paragraph(" "));
                document.add(new Paragraph("Numéro Contrat :" + lcSelected.getNumcontrat()));
                document.add(new Paragraph(" "));

                document.add(new Paragraph("Type Bénéficiaire : chantier"));
                document.add(new Paragraph(" "));

                if (lcSelected.getChantiers() != null) {

                    for (Chantier ch : lcSelected.getChantiers()) {
                        document.add(new Paragraph("Le nom de chantier :" + ch.getCode()));
                        document.add(new Paragraph(" "));

                        document.add(new Paragraph("Le code de chantier :" + ch.getCodeNovapaie()));
                        document.add(new Paragraph(" "));
                    }
                }

                document.add(new Paragraph("le montant de loyer :" + lcSelected.getMontantloyer()));
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

                if (!file.exists()) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                    return;
                }
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
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, Message.NO_BON_CAISSE_LOYER, ""));

        }

    }

    public void setSelectedLoyerC(LoyerChantier loyer) throws ParseException {
        loyerC = loyer;
        dateRenduC = null;
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        dateDebutChan = formatter.parse(loyer.getDatedebut());
    }

    /*annulation de l'affectation de loyer à un chantier*/
    public void annulerAffectLoyerC() throws ParseException {

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        hService.addRecordLoyerC(new Historique_LoyerC(), loyerC.getChantiers().get(0), loyerC, formatter.parse(loyerC.getDatedebut()), dateRenduC);
        loyerC.setEstArchive(true);
        loyerService.updateLoyerChantier(loyerC);
        System.out.println("anuulation de l'affectation loyer : " + loyerC.getVille());
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("affectation de loyer annulée avec succès!", ""));

    }

    /**
     * cette methode qui permet de rediget vers page edit bon caiisse d un loyer
     * chantier
     *
     * @param selected
     */
    public void redirectEditBCaisseCh(LoyerChantier selected) {

        lcSelected = selected;
    }

    public String redirectBonCaisseChantier(LoyerChantier selected) {
        System.out.println("REDIRECTING VERS SELECTED : " + selected.getId());
        return "editBonCaisseChantier.xhmtl?faces-redirect=true&loyerId=" + selected.getId();
    }

    public void initUpdate() {
        updateSalarie = true;
        loyerService.updateLoyerChantier(loyerChantierFicheLoyer);
    }

    public void removeBC(BonCaisse b) {

        for (int i = 0; i < listeBonCaiss.size(); i++) {
            if (listeBonCaiss.get(i).getId().equals(b.getId())) {
                listeBonCaiss.remove(i);
            }
        }

        bonCaisseService.removeBCChantierByLC(b);

    }

    public String redirectFicheLoyer(LoyerChantier selected) {
        System.out.println("REDIRECTING VERS SELECTED : " + selected.getId());
        return "ficheLoyerChantier.xhmtl?faces-redirect=true&loyerId=" + selected.getId();
    }

    public String redirectRCC(LoyerChantier selected) {
        System.out.println("REDIRECTING VERS SELECTED : " + selected.getId());
        return "telechargerRecuCaisseChantier.xhmtl?faces-redirect=true&loyerId=" + selected.getId();
    }

    /**
     * * visualise fiche loyer
     *
     * @param selected
     */
    public void visualiserFicheLoyer(LoyerChantier selected) {

        loyerChantierFicheLoyer = selected;
        if (loyerChantierFicheLoyer != null) {
            date_debut_loyer_ChantierString = loyerChantierFicheLoyer.getDatedebut().substring(0, 10);
        }

    }

    public void rechercheParDateC() {
        listeLChantier = loyerService.rechercher_Loyer_Chantier_ByIdentifient_Date_Debut(Date_Debut_Loyer_Chantier_To_Search);
    }

    /**
     * cette methode qui permet de rechercher Loyer chantier a partir date debut
     * et identification
     *
     */
    public void recherche_Loyer_Chantier_ByIdentifiantAndDateDebut() {
        String Date_Debut = "";

        if (Date_Debut_Loyer_Chantier_To_Search != null) {
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date today = Date_Debut_Loyer_Chantier_To_Search;
            Date_Debut = df.format(today);
        }

        if (loyer_To_Search.getId() == 0) {
            loyer_To_Search.setId(new Long(-1));
        }

        if (listeLChantier != null) {
            listeLChantier.clear();
        }

        // listeLChantier = loyerService.rechercher_Loyer_Chantier_ByIdentifient_Date_Debut(new Long(0), Date_Debut);
        if (listeLChantier != null) {
            for (LoyerChantier loyerChantier : listeLChantier) {
                for (Chantier c : loyerChantier.getChantiers()) {
                    selectChantier = c;
                }
            }

        }

    }

//    public void recherche_Loyer_Chantier(){
//    listeLChantier = loyerService.loyerChantiers();
//    }
    public void annulerUpdateBC() {
        bcChantier = null;
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("" + Message.MISE_JOUR_CANCEL, ""));
    }

    /*
     * rechercher chantier 
     */
    public void rechercheChantier() {
        chantiers = chantierService.search(chantier.getCodeNovapaie().toUpperCase(), chantier.getCode().toUpperCase(), chantier.getRegion().toUpperCase());

        if (chantier.getCodeNovapaie().equals("") && chantier.getCode().equals("") && chantier.getRegion().equals("")) {
            ELContext elContext = FacesContext.getCurrentInstance().getELContext();

            ma.bservices.tgcc.mb.services.ChantierMb chantier_bean = (ma.bservices.tgcc.mb.services.ChantierMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "chantierServMb");
            chantiers = chantier_bean.getChantiers();
        }
    }

    /*
     * afficher loyer chantiers
     */
    public void afficherLoyerChantier() {

//        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
//
//        ma.bservices.tgcc.mb.services.LoyerMb loyer_bean = (ma.bservices.tgcc.mb.services.LoyerMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "loyer_ServMb");
//        listeLChantier = loyer_bean.getL_loyer_chantiers();
        listeLChantier = loyerService.loyerChantiers();
        Date_Debut_Loyer_Chantier_To_Search = null;

    }

    /**
     * afficher chantiers
     */
    public void afficherChantiers() {

        ELContext elContext = FacesContext.getCurrentInstance().getELContext();

        ma.bservices.tgcc.mb.services.ChantierMb chantier_bean = (ma.bservices.tgcc.mb.services.ChantierMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "chantierServMb");
        chantiers = chantier_bean.getChantiers();
    }

    /*
     * upload file 
     */
    public void save() throws IOException {

        Boolean existe = false;

//        if (lcSelected != null) {
//            List<BonCaisse> l_bonCaisseChantiers = bonCaisseService.getbcByIdLoyerChan(lcSelected.getId());
//
//            if (l_bonCaisseChantiers != null) {
//                if (!l_bonCaisseChantiers.isEmpty()) {
//                    for (BonCaisse b_ : l_bonCaisseChantiers) {
//                        if (bcChantier.getAnnee().equals(b_.getAnnee()) && bcChantier.getMois().equals(b_.getMois())) {
//                            existe = true;
//                        }
//                    }
//                }
//            }
//
//        }
        //String chemin = TgccFileManager.getCheminFichier("Documents Mensuel");
        String chemin = ConstanteMb.getRepertoire() + "/files/documentsLoyerChantier";
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
            } else if (existe == true) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, Message.VERIFICATION_ANNEE_MOIS_BONCAISSE, Message.VERIFICATION_ANNEE_MOIS_BONCAISSE));
            } else {

                Path file = Files.createTempFile(folder, filename + "-", "." + extension);

                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                try (InputStream input = uploadedFile.getInputstream()) {
                    Files.copy(input, file, StandardCopyOption.REPLACE_EXISTING);
                    bcChantier.setCheminRecu("/files/documentsLoyerChantier" + "/" + file.getFileName());

                    //bcChantier.setLoyerChantier(lcnSelected);
                    bonCaisseService.updateBonCaisse(bcChantier);

                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, Message.STRING_CHARGE_DOCUMENT_DONE, Message.STRING_CHARGE_DOCUMENT_DONE));
                    listeBonCaiss = bonCaisseService.getbcByIdLoyerChan(lcSelected.getId());
                }

            }
        }
    }

    public void initImportRC(BonCaisse bonCaisseIt) {
        System.out.println("IMPORTING RC : " + bonCaisseIt.getChemin());
        bcChantier = bonCaisseIt;
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
    
    public void initLoyerChantierToShowDocs(Loyer lc) { 
        loyerToShow = lc;
        System.out.println("::::::::::::::::::::::::> loyerChantierToShow.getIdentifiant() "+loyerToShow.getId());
        listDoc = documentService.getDocumentByLoyer(loyerToShow.getId());
    }
    public void visualiser(String chemin) {
        System.out.println("chemin : " + chemin);
        selectedDoc = "";
        selectedDoc = chemin.substring(chemin.indexOf("files"), chemin.length());
        System.out.println("s : " + selectedDoc);
    }
    
    public void removeGest(ma.bservices.beans.Document a) throws IOException {
        documentService.deleteDocument(a);
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("" + Message.DELETE_DOCUMENT, Message.DELETE_DOCUMENT));
        listDoc = documentService.getDocumentByLoyer(loyerToShow.getId());

//        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
//        ec.redirect(ec.getRequestContextPath() + "/mensuel/docVoiture.xhtml");
    }
    
    

}
