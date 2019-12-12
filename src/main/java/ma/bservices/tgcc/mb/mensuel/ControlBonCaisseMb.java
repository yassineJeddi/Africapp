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
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import ma.bservice.tgcc.Constante.Message;
import ma.bservices.mb.services.ConstanteMb;
import ma.bservices.tgcc.Entity.BonCaisse;
import ma.bservices.tgcc.Entity.LoyerChantier;
import ma.bservices.tgcc.Entity.LoyerSalarie;
import ma.bservices.tgcc.Entity.Mensuel;
import ma.bservices.tgcc.service.Engin.ChantierService;
import ma.bservices.tgcc.service.Mensuel.BonCaisseService;
import ma.bservices.tgcc.service.Mensuel.LoyerService;
import ma.bservices.tgcc.service.Mensuel.MensuelService;
import ma.bservices.tgcc.webService.MensuelWSCallManager;
import org.json.JSONArray;
import org.json.JSONObject;
import org.primefaces.context.RequestContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

/**
 *
 * @author airaamane
 */
@Component
@ManagedBean(name = "control_bon_caisse_Mb")
@ViewScoped
public class ControlBonCaisseMb implements Serializable {

    /* -- SERVICES -- */
    @ManagedProperty(value = "#{loyerService}")
    private LoyerService loyerService;

    @ManagedProperty(value = "#{boncaisseservice}")
    private BonCaisseService bonCaisseService;

    @ManagedProperty(value = "#{chantierService}")
    private ChantierService chantierService;

    @ManagedProperty(value = "#{mensuelService}")
    private MensuelService mensuelService;

    /* -- VARIABLES -- */
    private LoyerSalarie loyerSalarieSelectMensuel = new LoyerSalarie();
    private LoyerChantier lcSelected = new LoyerChantier();
    private BonCaisse bc = new BonCaisse();
    private BonCaisse bcChantier = new BonCaisse();
    private int idRequest;
    private String codeCurrent;
    private String codeCurrentChantier;
    private String dateLoyerBon;

    public LoyerSalarie getLoyerSalarieSelectMensuel() {
        return loyerSalarieSelectMensuel;
    }

    public void setLoyerSalarieSelectMensuel(LoyerSalarie loyerSalarieSelectMensuel) {
        this.loyerSalarieSelectMensuel = loyerSalarieSelectMensuel;
    }

    public LoyerChantier getLoyerChantierFicheLoyer() {
        return lcSelected;
    }

    public LoyerChantier getLcSelected() {
        return lcSelected;
    }

    public void setLcSelected(LoyerChantier lcSelected) {
        this.lcSelected = lcSelected;
    }

    public String getCodeCurrentChantier() {
        return codeCurrentChantier;
    }

    public void setCodeCurrentChantier(String codeCurrentChantier) {
        this.codeCurrentChantier = codeCurrentChantier;
    }

    public BonCaisse getBcChantier() {
        return bcChantier;
    }

    public void setBcChantier(BonCaisse bcChantier) {
        this.bcChantier = bcChantier;
    }

    public void setLoyerChantierFicheLoyer(LoyerChantier lcSelected) {
        this.lcSelected = lcSelected;
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

    public String getCodeCurrent() {
        return codeCurrent;
    }

    public void setCodeCurrent(String codeCurrent) {
        this.codeCurrent = codeCurrent;
    }

    public String getDateLoyerBon() {
        return dateLoyerBon;
    }

    public void setDateLoyerBon(String dateLoyerBon) {
        this.dateLoyerBon = dateLoyerBon;
    }

    public ChantierService getChantierService() {
        return chantierService;
    }

    public BonCaisse getBc() {
        return bc;
    }

    public void setBc(BonCaisse bc) {
        this.bc = bc;
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

    public int getIdRequest() {
        return idRequest;
    }

    public void setIdRequest(int idRequest) {
        this.idRequest = idRequest;
    }

    @PostConstruct
    public void init() {
        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        loyerService = ctx.getBean(LoyerService.class);
        bonCaisseService = ctx.getBean(BonCaisseService.class);
        chantierService = ctx.getBean(ChantierService.class);
        mensuelService = ctx.getBean(MensuelService.class);

        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        Map<String, String> requestParameters = externalContext.getRequestParameterMap();

        if (requestParameters.containsKey("loyerId")) {
            idRequest = Integer.valueOf(requestParameters.get("loyerId"));
            System.out.println("REQUEST PARAM SENT : " + idRequest);
        } else {
            System.out.println("NO REQUEST ITEM");
        }

        loyerSalarieSelectMensuel = loyerService.findOneLSById(idRequest);
        lcSelected = loyerService.findOneLCById(idRequest);

    }

    /* edit bon caisse */
    public void updateBC() throws IOException, DocumentException {

        BonCaisse bcFound = null;
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
                                    bcFound = b_;
                                }

                            }
                        }
                    }

                }

                if (existe == true) {
                    //FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, Message.VERIFICATION_ANNEE_MOIS_BONCAISSE, Message.VERIFICATION_ANNEE_MOIS_BONCAISSE));
                    showBC(bcFound);
                    bonCaisseService.updateBonCaisse(bcFound);
                    // codeCurrent = bcFound.getChemin();
                    RequestContext contextR = RequestContext.getCurrentInstance();
                    contextR.execute("PF('consultBC').show();");
                } else {

                    bc.setLoyerSalarie(loyerSalarieSelectMensuel);

                    bonCaisseService.saveBonCaisse(bc);
                    showBC(bc);
                    bonCaisseService.updateBonCaisse(bc);
                    FacesContext context = FacesContext.getCurrentInstance();
                    context.addMessage(null, new FacesMessage("" + Message.ADD_BON_CAISSE_LOYER, ""));
                    RequestContext contextR = RequestContext.getCurrentInstance();
                    contextR.execute("PF('consultBC').show();");

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
                                bcFound = b_;
                            }

                        }
                    }
                }

            }

            if (existe == true) {
                //  FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, Message.VERIFICATION_ANNEE_MOIS_BONCAISSE, Message.VERIFICATION_ANNEE_MOIS_BONCAISSE));

                showBC(bcFound);
                bonCaisseService.updateBonCaisse(bcFound);
                // codeCurrent = bcFound.getChemin();
                RequestContext contextR = RequestContext.getCurrentInstance();
                contextR.execute("PF('consultBC').show();");
            } else {

                bc.setLoyerSalarie(loyerSalarieSelectMensuel);
                bonCaisseService.saveBonCaisse(bc);
                showBC(bc);
                bonCaisseService.updateBonCaisse(bc);
                FacesContext context = FacesContext.getCurrentInstance();

                context.addMessage(null, new FacesMessage("" + Message.ADD_BON_CAISSE_LOYER, ""));
                RequestContext contextR = RequestContext.getCurrentInstance();
                contextR.execute("PF('consultBC').show();");

            }
        }

    }
    
    
      public Paragraph makePara(String p1, String p2) {
        Paragraph p = new Paragraph();
        Font ff = new Font(Font.FontFamily.HELVETICA, 12.0f, Font.BOLD, BaseColor.BLACK);
        Font fb = new Font(Font.FontFamily.HELVETICA, 12.0f, Font.NORMAL, BaseColor.BLACK);
        p.add(new Phrase(p1, ff));
        p.add(new Phrase(p2, fb));
        return p;
    }

    public void showBC(BonCaisse bc) throws IOException, DocumentException {
        System.out.println("entree");

        System.out.println("MOIS : " + bc.getMois() + "/" + bc.getAnnee());

        //String chemin = TgccFileManager.getCheminFichier("Bon Caisse Loyer Salarie");
        String chemin = ConstanteMb.getRepertoire() + "/files/documentsLoyer";

        Path folder = Paths.get(chemin);
        Files.createDirectories(folder);
        //String.valueOf(System.currentTimeMillis())
        //String uniqueId = bc.getId().toString();

        String uniqueId = loyerSalarieSelectMensuel.getId() + bc.getMois() + bc.getAnnee();

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
        document.add(new Paragraph(" "));
        PdfPTable t = new PdfPTable(2);
        t.setWidthPercentage(100);
        System.out.println("DATE LOYER BON : " + dateLoyerBon);
        PdfPCell cellFive = new PdfPCell(makePara("Date : " , dateLoyerBon));
        PdfPCell cellTree = new PdfPCell(makePara("N Bon : " , uniqueId));

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
        PdfPCell cellFivet = new PdfPCell(makePara("Chantier : " , findChantierDivalto(loyerSalarieSelectMensuel.getMensuel_Principal())));
        PdfPCell cellTreet = new PdfPCell(makePara("Bénéficiaire : " , loyerSalarieSelectMensuel.getMensuel_Principal().getNom() + " " + loyerSalarieSelectMensuel.getMensuel_Principal().getPrenom()));

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

     
        PdfPTable r = new PdfPTable(2);
        r.setWidthPercentage(100);
        PdfPCell cellFivettr = new PdfPCell(makePara("Montant : " , loyerSalarieSelectMensuel.getMontantloyer()));
        PdfPCell cellFivettrr = new PdfPCell(makePara("Mode de Paiement : " , loyerSalarieSelectMensuel.getModepaiment()));

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

        codeCurrent = "/files/documentsLoyer/" + nomFichier;
        bc.setChemin(codeCurrent);
        bc.setCheminRecu(null);

        System.out.println(codeCurrent);
        System.out.println("sortie");

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
                    String code_chan = arr.getJSONObject(i).getString("Code Chantier");
                    String lib_chan = arr.getJSONObject(i).getString("Libellé chantier");
                    System.out.println("MENSUEL CHANTIER DETECTED : " + lib_chan);
                    chantierDivaltoMensuel = lib_chan;
                }
            }
            return chantierDivaltoMensuel;
        } catch (Exception e) {
            System.out.println("JSON EXCEPTION DETECTED!" + e.getMessage());
        }

        System.out.println("finished ws!");
        return chantierDivaltoMensuel;
    }

    public void updateBonCaisseChantier() throws IOException, DocumentException {
        BonCaisse bcFound = null;
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
                                    bcFound = b_;
                                }
                            }
                        }
                    }

                }

                if (existe == true) {
                    //   FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, Message.VERIFICATION_ANNEE_MOIS_BONCAISSE, Message.VERIFICATION_ANNEE_MOIS_BONCAISSE));

                    showBCChantier(bcFound);
                    bonCaisseService.updateBonCaisse(bcFound);
                    //    codeCurrentChantier = bcFound.getChemin();

                    RequestContext contextR = RequestContext.getCurrentInstance();
                    contextR.execute("PF('consultBC').show();");
                } else {

                    bcChantier.setLoyerChantier(lcSelected);
                    bonCaisseService.saveBonCaisse(bcChantier);
                    showBCChantier(bcChantier);
                    bonCaisseService.updateBonCaisse(bcChantier);
                    FacesContext context = FacesContext.getCurrentInstance();
                    context.addMessage(null, new FacesMessage("" + Message.ADD_BON_CAISSE_LOYER, ""));
                    RequestContext contextR = RequestContext.getCurrentInstance();
                    contextR.execute("PF('consultBC').show();");
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
                                bcFound = b_;
                            }

                        }
                    }
                }

            }

            if (existe == true) {
                //     FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, Message.VERIFICATION_ANNEE_MOIS_BONCAISSE, Message.VERIFICATION_ANNEE_MOIS_BONCAISSE));
                showBCChantier(bcFound);
                bonCaisseService.updateBonCaisse(bcFound);
                // codeCurrentChantier = bcFound.getChemin();

                RequestContext contextR = RequestContext.getCurrentInstance();
                contextR.execute("PF('consultBC').show();");
            } else {

                bcChantier.setLoyerChantier(lcSelected);
                bonCaisseService.saveBonCaisse(bcChantier);
                showBCChantier(bcChantier);
                bonCaisseService.updateBonCaisse(bcChantier);
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage("" + Message.ADD_BON_CAISSE_LOYER, ""));
                RequestContext contextR = RequestContext.getCurrentInstance();
                contextR.execute("PF('consultBC').show();");
            }
        }

    }

    public void showBCChantier(BonCaisse bcChantier) throws IOException, DocumentException {
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
        PdfPCell cellFive = new PdfPCell(makePara("Mois : " , bcChantier.getMois() + "/" + bcChantier.getAnnee()));
        PdfPCell cellTree = new PdfPCell(makePara("N Bon : " , bcChantier.getId().toString()));

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
        PdfPCell cellFivet = new PdfPCell(makePara("Chantier : ", ""));
        PdfPCell cellTreet = new PdfPCell(makePara("Responsable : " , lcSelected.getNomproprietaire() + " " + lcSelected.getPrenomproprietaire() ));

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

        PdfPTable r = new PdfPTable(2);
        r.setWidthPercentage(100);
        PdfPCell cellFivettr = new PdfPCell(makePara("Montant : " , lcSelected.getMontantloyer()));
        PdfPCell cellFivettrr = new PdfPCell(makePara("Mode de Paiement : " , lcSelected.getModepaiment()));
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
        codeCurrentChantier = "/files/documentsLoyerChantier/" + nomFichier;
        bcChantier.setChemin(codeCurrentChantier);
        bcChantier.setCheminRecu(null);
        System.out.println(codeCurrentChantier);
        System.out.println("sortie");

    }
}
