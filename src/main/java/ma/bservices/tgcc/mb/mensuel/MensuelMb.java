
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.mb.mensuel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import ma.bservice.tgcc.Constante.Message;
import ma.bservices.beans.BonLivraison;
import ma.bservices.beans.Chantier;
import ma.bservices.beans.Contrat;
import ma.bservices.beans.Docs;
import ma.bservices.beans.Document;
import ma.bservices.beans.Fonction;
import ma.bservices.beans.Type;
import ma.bservices.beans.TypeDocument;
import ma.bservices.mb.services.ConstanteMb;
import ma.bservices.mb.services.Module;
import ma.bservices.services.ContratService;
import ma.bservices.services.DocsService;
import ma.bservices.services.MailConfigService;
import ma.bservices.tgcc.Entity.Affectation;
import ma.bservices.tgcc.Entity.MailConfigBean;
import ma.bservices.tgcc.Entity.Mensuel;
import ma.bservices.tgcc.Entity.PointageMensuelQuinzinier;
import ma.bservices.tgcc.Entity.SousAffectation;
import ma.bservices.tgcc.service.Engin.ChantierService;
import ma.bservices.tgcc.service.Mensuel.AbsenceService;
import ma.bservices.tgcc.service.Mensuel.AffectationService;
import ma.bservices.tgcc.service.Mensuel.DocumentService;
import ma.bservices.tgcc.service.Mensuel.FonctionService;
import ma.bservices.tgcc.service.Mensuel.MensuelService;
import ma.bservices.tgcc.service.Mensuel.TypeDocumentService;
import ma.bservices.tgcc.service.SendEmail;
import ma.bservices.tgcc.utilitaire.FileOutils;
import ma.bservices.tgcc.utilitaire.TgccFileManager;
import ma.bservices.tgcc.webService.MensuelWSCallManager;
import org.apache.commons.io.FilenameUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.primefaces.model.UploadedFile;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

/**
 *
 * @author a.wattah
 */
@Component
@Transactional
@ManagedBean(name = "mensuelMb")
@ViewScoped
public class MensuelMb implements Serializable {

    @ManagedProperty(value = "#{mensuelService}")
    private MensuelService mensuelService;

    @ManagedProperty(value = "#{chantierService}")
    private ChantierService chantierService;

    @ManagedProperty(value = "#{fonctionService}")
    private FonctionService fonctionService;

    @ManagedProperty(value = "#{documentService}")
    private DocumentService documentService;

    @ManagedProperty(value = "#{contratService}")
    private ContratService contratService;

    public ContratService getContratService() {
        return contratService;
    }

    public void setContratService(ContratService contratService) {
        this.contratService = contratService;
    }

    public ELContext getElContext() {
        return elContext;
    }

    public void setElContext(ELContext elContext) {
        this.elContext = elContext;
    }

    public ma.bservices.tgcc.mb.services.MensuelMb getMensuelServMb() {
        return mensuelServMb;
    }

    public void setMensuelServMb(ma.bservices.tgcc.mb.services.MensuelMb mensuelServMb) {
        this.mensuelServMb = mensuelServMb;
    }

    @ManagedProperty(value = "#{docsService}")
    private DocsService docsService;

    @ManagedProperty(value = "#{absenceService}")
    private AbsenceService absenceService;

    @ManagedProperty(value = "#{affectationService}")
    private AffectationService affectationService;

    @ManagedProperty(value = "#{TypedocumentService}")
    private TypeDocumentService typeDocumentService;

    @ManagedProperty(value = "#{mailConfigService}")
    private MailConfigService mailConfigService;

    private List<Chantier> chantier = new ArrayList<>();

    private List<Fonction> fonctions;

    private List<Mensuel> mensuels;

    private Mensuel mensuelToSearch = new Mensuel();

    private Mensuel mensuel = new Mensuel();

    List<Mensuel> searchMensuelList = new ArrayList<Mensuel>();

    private List<Affectation> affectations = new ArrayList<Affectation>();

    private Mensuel mensuelAffect = new Mensuel();

    private List<Mensuel> l_mensuels;

    private String LibChantier;
    private String fonction;

    private int affectP = 100;
    private int poucentage;

    private UploadedFile file;
    private String fileContent;

    private List<SousAffectation> listeAffectation = new ArrayList<SousAffectation>();

    private Date dateAjourd = new Date();

    private Document documentT = new Document();

    private UploadedFile uploadedFile;

    private static String chemin;

    /*
     * partie jihane valide absence
     */
    private Mensuel mensuelSearch = new Mensuel();

    private List<PointageMensuelQuinzinier> listPointageMQ = new ArrayList<>();

    private String HeureE;

    private String HeureS;
    private String radioBC;

    private boolean disable_to_Affectation;

    private PointageMensuelQuinzinier PointageMQ = new PointageMensuelQuinzinier();
    /**
     * Partie Abderahim fiche
     *
     * @return
     */
    private Document documentTadd = new Document();

    List<SousAffectation> list_rempli_copie;

    private UploadedFile uploadedFileFiche;
    private static String cheminFiche;
    private String fileContentfiche;
    private List<Document> listDocumentByIdMensuel = new ArrayList<>();

    private List<TypeDocument> l_type_docs = new ArrayList<>();

    ELContext elContext;
    ma.bservices.tgcc.mb.services.MensuelMb mensuelServMb;

    /*
     * getters and setters
     */
    public TypeDocumentService getTypeDocumentService() {
        return typeDocumentService;
    }

    public void setTypeDocumentService(TypeDocumentService typeDocumentService) {
        this.typeDocumentService = typeDocumentService;
    }

    public DocsService getDocsService() {
        return docsService;
    }

    public void setDocsService(DocsService docsService) {
        this.docsService = docsService;
    }

    public List<TypeDocument> getL_type_docs() {
        return l_type_docs;
    }

    public void setL_type_docs(List<TypeDocument> l_type_docs) {
        this.l_type_docs = l_type_docs;
    }

    public boolean isDisable_to_Affectation() {
        return disable_to_Affectation;
    }

    public void setDisable_to_Affectation(boolean disable_to_Affectation) {
        this.disable_to_Affectation = disable_to_Affectation;
    }

    public List<SousAffectation> getList_rempli_copie() {
        return list_rempli_copie;
    }

    public void setList_rempli_copie(List<SousAffectation> list_rempli_copie) {
        this.list_rempli_copie = list_rempli_copie;
    }

    public AffectationService getAffectationService() {
        return affectationService;
    }

    public void setAffectationService(AffectationService affectationService) {
        this.affectationService = affectationService;
    }

    public List<Document> getListDocumentByIdMensuel() {
        return listDocumentByIdMensuel;
    }

    public void setListDocumentByIdMensuel(List<Document> listDocumentByIdMensuel) {
        this.listDocumentByIdMensuel = listDocumentByIdMensuel;
    }

    public Document getDocumentTadd() {
        return documentTadd;
    }

    public void setDocumentTadd(Document documentTadd) {
        this.documentTadd = documentTadd;
    }

    public UploadedFile getUploadedFileFiche() {
        return uploadedFileFiche;
    }

    public void setUploadedFileFiche(UploadedFile uploadedFileFiche) {
        this.uploadedFileFiche = uploadedFileFiche;
    }

    public static String getCheminFiche() {
        return cheminFiche;
    }

    public static void setCheminFiche(String cheminFiche) {
        MensuelMb.cheminFiche = cheminFiche;
    }

    public String getFileContentfiche() {
        return fileContentfiche;
    }

    public void setFileContentfiche(String fileContentfiche) {
        this.fileContentfiche = fileContentfiche;
    }

    public List<Mensuel> getMensuels() {
        return mensuels;
    }

    public void setMensuels(List<Mensuel> mensuels) {
        this.mensuels = mensuels;
    }

    public MensuelService getMensuelService() {
        return mensuelService;
    }

    public void setMensuelService(MensuelService mensuelService) {
        this.mensuelService = mensuelService;
    }

    public Mensuel getMensuel() {
        return mensuel;
    }

    public void setMensuel(Mensuel mensuel) {
        this.mensuel = mensuel;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public String getFileContent() {
        return fileContent;
    }

    public void setFileContent(String fileContent) {
        this.fileContent = fileContent;
    }

    public ChantierService getChantierService() {
        return chantierService;
    }

    public void setChantierService(ChantierService chantierService) {
        this.chantierService = chantierService;
    }

    public FonctionService getFonctionService() {
        return fonctionService;
    }

    public void setFonctionService(FonctionService fonctionService) {
        this.fonctionService = fonctionService;
    }

    public List<Chantier> getChantier() {
        return chantier;
    }

    public void setChantier(List<Chantier> chantier) {
        this.chantier = chantier;
    }

    public List<Fonction> getFonctions() {
        return fonctions;
    }

    public void setFonctions(List<Fonction> fonctions) {
        this.fonctions = fonctions;
    }

    public Mensuel getMensuelToSearch() {
        return mensuelToSearch;
    }

    public void setMensuelToSearch(Mensuel mensuelToSearch) {
        this.mensuelToSearch = mensuelToSearch;
    }

    public List<Mensuel> getSearchMensuelList() {
        return searchMensuelList;
    }

    public void setSearchMensuelList(List<Mensuel> searchMensuelList) {
        this.searchMensuelList = searchMensuelList;
    }

    public Mensuel getMensuelAffect() {
        return mensuelAffect;
    }

    public List<Mensuel> getL_mensuels() {
        return l_mensuels;
    }

    public void setL_mensuels(List<Mensuel> l_mensuels) {
        this.l_mensuels = l_mensuels;
    }

    public void setMensuelAffect(Mensuel mensuelAffect) {
        this.mensuelAffect = mensuelAffect;
    }

    public String getLibChantier() {
        return LibChantier;
    }

    public void setLibChantier(String LibChantier) {
        this.LibChantier = LibChantier;
    }

    public String getFonction() {
        return fonction;
    }

    public void setFonction(String fonction) {
        this.fonction = fonction;
    }

    public int getAffectP() {
        return affectP;
    }

    public void setAffectP(int affectP) {
        this.affectP = affectP;
    }

    public int getPoucentage() {
        return poucentage;
    }

    public void setPoucentage(int poucentage) {
        this.poucentage = poucentage;
    }

    public List<SousAffectation> getListeAffectation() {
        return listeAffectation;
    }

    public void setListeAffectation(List<SousAffectation> listeAffectation) {
        this.listeAffectation = listeAffectation;
    }

    public Date getDateAjourd() {
        return dateAjourd;
    }

    public void setDateAjourd(Date dateAjourd) {
        this.dateAjourd = dateAjourd;
    }

    public List<Affectation> getAffectations() {
        return affectations;
    }

    public void setAffectations(List<Affectation> affectations) {
        this.affectations = affectations;
    }

    public Document getDocumentT() {
        return documentT;
    }

    public void setDocumentT(Document documentT) {
        this.documentT = documentT;
    }

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public static String getChemin() {
        return chemin;
    }

    public static void setChemin(String chemin) {
        MensuelMb.chemin = chemin;
    }

    public DocumentService getDocumentService() {
        return documentService;
    }

    public void setDocumentService(DocumentService documentService) {
        this.documentService = documentService;
    }

    public Mensuel getMensuelSearch() {
        return mensuelSearch;
    }

    public void setMensuelSearch(Mensuel mensuelSearch) {
        this.mensuelSearch = mensuelSearch;
    }

    public MailConfigService getMailConfigService() {
        return mailConfigService;
    }

    public void setMailConfigService(MailConfigService mailConfigService) {
        this.mailConfigService = mailConfigService;
    }
    
    

    public List<PointageMensuelQuinzinier> getListPointageMQ() {
        return listPointageMQ;
    }

    public void setListPointageMQ(List<PointageMensuelQuinzinier> listPointageMQ) {
        this.listPointageMQ = listPointageMQ;
    }

    public String getHeureE() {
        return HeureE;
    }

    public void setHeureE(String HeureE) {
        this.HeureE = HeureE;
    }

    public String getHeureS() {
        return HeureS;
    }

    public void setHeureS(String HeureS) {
        this.HeureS = HeureS;
    }

    public String getRadioBC() {
        return radioBC;
    }

    public void setRadioBC(String radioBC) {
        this.radioBC = radioBC;
    }

    public PointageMensuelQuinzinier getPointageMQ() {
        return PointageMQ;
    }

    public void setPointageMQ(PointageMensuelQuinzinier PointageMQ) {
        this.PointageMQ = PointageMQ;
    }

    public AbsenceService getAbsenceService() {
        return absenceService;
    }

    public void setAbsenceService(AbsenceService absenceService) {
        this.absenceService = absenceService;
    }

    /*
     * end setters and getters
     */
    @PostConstruct
    public void init() {
        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        mensuelService = ctx.getBean(MensuelService.class);
        documentService = ctx.getBean(DocumentService.class);
        absenceService = ctx.getBean(AbsenceService.class);
        fonctionService = ctx.getBean(FonctionService.class);
        chantierService = ctx.getBean(ChantierService.class);
        affectationService = ctx.getBean(AffectationService.class);
        typeDocumentService = ctx.getBean(TypeDocumentService.class);
        docsService = ctx.getBean(DocsService.class);
        mailConfigService = ctx.getBean(MailConfigService.class);

        mensuel = new Mensuel();

        elContext = FacesContext.getCurrentInstance().getELContext();
        mensuelServMb = (ma.bservices.tgcc.mb.services.MensuelMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "mensuelServMb");
        mensuelServMb.reload_Mensuel();
        l_mensuels = mensuelServMb.l_mensuels;
        l_type_docs = typeDocumentService.l_docs_Obligatoire();
        
            }

    public void copyDocs() {

        int i = 0;
        Docs docFound = new Docs();
        List<Docs> ld = docsService.findAll();
        String categorie = "DocumentSalarie";

        List<Document> ldocuments = documentService.findAllNoLocation();
        List<String> ldocs_noderef = new LinkedList<>();
        for (Document d : ldocuments) {
            ldocs_noderef.add(d.getNodeRef());
        }

        for (Document doc : ldocuments) {
            System.out.println("NODEREF : " + doc.getNodeRef());
            docFound = docsService.findByNodeRef(doc.getNodeRef());
            if (docFound != null) {
                System.out.println("CHEMIN doc found : " + docFound.getLocation());
                try {
                    String cheminDossier = docFound.getLocation().substring(docFound.getLocation().indexOf("/"), docFound.getLocation().lastIndexOf("/"));

                    String cheminComplet = ConstanteMb.getRepertoire() + "/files/resources/" + categorie + docFound.getLocation().substring(docFound.getLocation().indexOf("/"), docFound.getLocation().length()).replaceAll(".bin", ".pdf");
                    Path folder = Paths.get(ConstanteMb.getRepertoire() + "/files/resources/" + categorie + "/" + cheminDossier);
                    Files.createDirectories(folder);
                    System.out.println("cheminDossier = " + cheminDossier);
                    System.out.println("cheminComplet = " + cheminComplet);
                    System.out.println("folder = " + folder.normalize().toString());

                    fileCopy(ConstanteMb.getRepertoire() + "/" + docFound.getLocation(), cheminComplet);
                    doc.setChemin(cheminComplet.substring(cheminComplet.indexOf("files/"), cheminComplet.length()));
                    doc.setModifiePar(docFound.getModifier());
                    doc.setCreePar(docFound.getCreator());
                    doc.setDateCreation(docFound.getCreationDate());
                    doc.setDateModification(docFound.getModificationDate());
                    documentService.update(doc);

                } catch (IOException IO) {
                    System.out.println("Une erreur s'est produite");

                }
            }
            i++;
        }

        System.out.println(" ============ **********   THE i : " + i);

    }

    public void copyContratsL() {

        int i = 0;
        Docs docFound = new Docs();
        List<Docs> ld = docsService.findAll();
        String categorie = "ContratSalarie/Legaliser";

        List<Contrat> lContrats = contratService.findAllContratsL();
        List<String> lcontrats_noderef = new LinkedList<>();

        for (Contrat C : lContrats) {
            System.out.println("NODEREF : " + C.getNodeRefLegalise());
            docFound = docsService.findByNodeRef(C.getNodeRefLegalise());
            if (docFound != null) {
                System.out.println("CHEMIN doc found : " + docFound.getLocation());
                try {
                    String cheminDossier = docFound.getLocation().substring(docFound.getLocation().indexOf("/"), docFound.getLocation().lastIndexOf("/"));
                    String cheminComplet = ConstanteMb.getRepertoire() + "/files/resources/" + categorie + docFound.getLocation().substring(docFound.getLocation().indexOf("/"), docFound.getLocation().length()).replaceAll(".bin", ".pdf");
                    Path folder = Paths.get(ConstanteMb.getRepertoire() + "/files/resources/" + categorie + "/" + cheminDossier);
                    Files.createDirectories(folder);
                    System.out.println("cheminDossier = " + cheminDossier);
                    System.out.println("cheminComplet = " + cheminComplet);
                    System.out.println("folder = " + folder.normalize().toString());

                    fileCopy(ConstanteMb.getRepertoire() + "/" + docFound.getLocation(), cheminComplet);
                    C.setNodeRefLegalise(cheminComplet.substring(cheminComplet.indexOf("files/"), cheminComplet.length()));
                    C.setModifiePar(docFound.getModifier());
                    C.setCreePar(docFound.getCreator());
                    C.setDateCreation(docFound.getCreationDate());
                    C.setDateModification(docFound.getModificationDate());
                    contratService.modifierContrat(C);

                } catch (IOException IO) {
                    System.out.println("Une erreur s'est produite");
                }
                System.out.println("DONE ..... NEXT");
            }
            i++;
        }

        System.out.println(" ============ **********   THE i : " + i);

    }

    public void copyContratsNonL() {

        int i = 0;
        Docs docFound = new Docs();
        List<Docs> ld = docsService.findAll();
        String categorie = "ContratSalarie/NonLegaliser";

        List<Contrat> lContrats = contratService.findAllContratsNonL();

        for (Contrat C : lContrats) {
            System.out.println("NODEREF : " + C.getNodeRefNonLegalise());
            docFound = docsService.findByNodeRef(C.getNodeRefNonLegalise());
            if (docFound != null) {
                System.out.println("CHEMIN doc found : " + docFound.getLocation());
                try {
                    String cheminDossier = docFound.getLocation().substring(docFound.getLocation().indexOf("/"), docFound.getLocation().lastIndexOf("/"));
                    String cheminComplet = ConstanteMb.getRepertoire() + "/files/resources/" + categorie + docFound.getLocation().substring(docFound.getLocation().indexOf("/"), docFound.getLocation().length()).replaceAll(".bin", ".pdf");
                    Path folder = Paths.get(ConstanteMb.getRepertoire() + "/files/resources/" + categorie + "/" + cheminDossier);
                    Files.createDirectories(folder);
                    System.out.println("cheminDossier = " + cheminDossier);
                    System.out.println("cheminComplet = " + cheminComplet);
                    System.out.println("folder = " + folder.normalize().toString());

                    fileCopy(ConstanteMb.getRepertoire() + "/" + docFound.getLocation(), cheminComplet);
                    C.setNodeRefNonLegalise(cheminComplet.substring(cheminComplet.indexOf("files/"), cheminComplet.length()));
                    C.setModifiePar(docFound.getModifier());
                    C.setCreePar(docFound.getCreator());
                    C.setDateCreation(docFound.getCreationDate());
                    C.setDateModification(docFound.getModificationDate());
                    contratService.modifierContrat(C);

                } catch (IOException IO) {
                    System.out.println("Une erreur s'est produite");
                }
            }
            i++;
        }

        System.out.println(" ============ **********   THE i : " + i);

    }

    public void fileCopy() throws IOException {

        String chemin = "C:\\Users\\admin\\Documents\\NetBeansProjects\\TGCC-Integration-3\\src\\main\\webapp\\resources\\ezivzbejobnoznjvnouzbzv.bin";
        String s = "C:\\Users\\admin\\Documents\\NetBeansProjects\\TGCC-Integration-3\\src\\main\\webapp\\resources\\testFile\\ezivzbejobnoznjvnouzbzv.bin";
        s = s.replaceAll(".bin", ".pdf");
        File f = new File(chemin);
        FileOutils.copyFileUsingStream(f, new File(s));
    }

    public void fileCopy(String cheminSource, String cheminDestination) throws IOException {
        File f = new File(cheminSource);
        FileOutils.copyFileUsingStream(f, new File(cheminDestination));
    }

    /*
     * cette methode qui fait la recherche a partir les information d'un salarié
     */
    public void rechercherEngin() {
        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        mensuelService = ctx.getBean(MensuelService.class);
        this.searchMensuelList = mensuelService.search(mensuelToSearch.getMatricule(), mensuelToSearch.getNom(), mensuelToSearch.getPrenom(), fonction, mensuelToSearch.getCin());
        this.mensuels = searchMensuelList;
    }

    /**
     * Creates a new instance of MensuelMb
     */
    public MensuelMb() {

    }

    public void validateAffect() {
        Chantier c = new Chantier();
        mensuelService.getIdChantierByLib(LibChantier);
        this.dateAjourd = new Date();
    }

    public void upload() {
        if (file != null) {
            FacesMessage message = new FacesMessage(Message.STRING_SUCCESS, file.getFileName() + Message.STRING_UPLOAD);
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }


    /*
     * cette methode qui fait la recherche a partir les information d'un salarié
     */
    public void rechercherMensuel() {
        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        mensuelService = ctx.getBean(MensuelService.class);

        this.searchMensuelList = mensuelService.search(mensuelToSearch.getMatricule(), mensuelToSearch.getNom(), mensuelToSearch.getPrenom(), "", mensuelToSearch.getCin());
        this.mensuels = searchMensuelList;

    }

    public void rechercherMensuelByFon() {

        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        mensuelService = ctx.getBean(MensuelService.class);
        if (mensuelToSearch.getMatricule().compareTo("") == 0 && mensuelToSearch.getNom().compareTo("") == 0 && mensuelToSearch.getPrenom().compareTo("") == 0 && fonction.compareTo("") == 0 && mensuelToSearch.getStatut().compareTo("") == 0) {

            l_mensuels = mensuelServMb.l_mensuels;
        } else {
            this.searchMensuelList = mensuelService.rechercherMensuelByfonction(mensuelToSearch.getMatricule(), mensuelToSearch.getNom(), mensuelToSearch.getPrenom(), fonction.trim(), "actif");
            l_mensuels = searchMensuelList;
        }

    }

    public void typeAffectation() {

        for (Mensuel m : mensuels) {
            this.affectations = m.getAffectations();
        }

    }

    public void save() throws IOException {
        chemin = TgccFileManager.getCheminFichier("Documents Mensuel");
        Path folder = Paths.get(chemin);
        Files.createDirectories(folder);
        String filename = FilenameUtils.getBaseName(uploadedFile.getFileName());
        String extension = FilenameUtils.getExtension(uploadedFile.getFileName());
        Path file = Files.createTempFile(folder, filename + "-", "." + extension);
        try (InputStream input = uploadedFile.getInputstream()) {
            Files.copy(input, file, StandardCopyOption.REPLACE_EXISTING);
            documentT.setChemin(chemin + "/" + file.getFileName());
            documentT.setSalarie(mensuelAffect);
            documentService.save(documentT);
        }

    }

    public void chargerFonctions() throws FileNotFoundException {
        //System.setProperty("console.encoding", "UTF-8");
        File f = new File("C:\\Users\\admin\\Documents\\NetBeansProjects\\TGCC-D\\TGCC-M\\src\\main\\webapp\\mensuel\\mensuel");
        Scanner s = new Scanner(f);
        String codeFonction = "";
        String libFonction = "";
        String docs = "";
        String[] doc;
        String line = "";
        StringBuilder str;
        TypeDocument type = null;
        List<TypeDocument> listOfDocsByFonction = new LinkedList<>();
        while (s.hasNextLine()) {
            line = s.nextLine();
            str = new StringBuilder(line);
            codeFonction = str.substring(0, 3);
            libFonction = str.substring(4, line.indexOf(";"));
            docs = str.substring(line.indexOf(";") + 1, line.length());
            doc = docs.split(",");
            Fonction func = fonctionService.findByCode(codeFonction);
            if (func == null) {
                System.out.println("NO SUCH FONCTION EXISTS : " + codeFonction);
            } else {

                for (String documentType : doc) {

                    type = typeDocumentService.type_doc(documentType);
                    listOfDocsByFonction.add(type);

                }
                fonctionService.setListTypeDocsObligatoires(func, listOfDocsByFonction);

                listOfDocsByFonction.clear();
            }

            //System.out.println("LIB: " + libFonction);
            System.out.println("DOCS: " + docs);

            System.out.println("_");

        }

    }

    /*
     * partie jihane rechercher ordinateur d'un mensuel
     */
    public void rechercheMensuOrdi() {
        if (searchMensuelList != null) {
            searchMensuelList.clear();
        }
        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        mensuelService = ctx.getBean(MensuelService.class);

        this.searchMensuelList = mensuelService.search(mensuelToSearch.getMatricule(), mensuelToSearch.getNom(), mensuelToSearch.getPrenom(), "", "");
//       this.mensuels = searchMensuelList;
    }

    /*
     * partie jihane valide absence 
     */
 /*
     * modifier heur entre et sortie d'un mensuel 
     */
//    public void updatePointageMQ(){
//           System.out.println("Heure Entreeeeeeeeeeeeeeeee "+HeureE);
//           PointageMQ.setHeureEntree(HeureE);
//           PointageMQ.setHeureSortie(HeureS);
//           absenceService.updatePointageMQ(PointageMQ);
//           FacesContext context = FacesContext.getCurrentInstance();
//           String message ="Absence est modifié avec succés " ;
//           context.addMessage(null, new FacesMessage("Successful " + message, ""));
//       }
    public void consultAbsenceMQ() {
        listPointageMQ = absenceService.getAbsenceMensuelQById(mensuelSearch.getId());
    }

    /**
     * Partie Abderahim Fiche
     */
    public void listDocuementsBy(Mensuel _m) {
        mensuelAffect = _m;
        listDocumentByIdMensuel = documentService.getListDocumentById(mensuelAffect.getId());

    }

    public void saveFicheMensuel() throws IOException {

        documentTadd = new Document();
        chemin = TgccFileManager.getCheminFichier("Fiche Mensuel");
        Path folder = Paths.get(chemin);
        Files.createDirectories(folder);
        String filename = FilenameUtils.getBaseName(uploadedFileFiche.getFileName());
        String extension = FilenameUtils.getExtension(uploadedFileFiche.getFileName());
        Path file = Files.createTempFile(folder, filename + "-", "." + extension);

//        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
//        MensuelMb mensuelBean = (MensuelMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "mensuelMb");
        try (InputStream input = uploadedFileFiche.getInputstream()) {
            Files.copy(input, file, StandardCopyOption.REPLACE_EXISTING);
            documentTadd.setChemin(chemin + "/" + file.getFileName());
            documentTadd.setSalarie(mensuelAffect);
            documentTadd.setDateCreation(new Date());
            listDocumentByIdMensuel.add(documentTadd);
            documentService.save(documentTadd);
        }
    }

    public void checkobli(Mensuel m) {
        Fonction fonction_mensuel = m.getFonction();
        l_type_docs = fonction_mensuel.getTypesDocument();
        System.out.println("FONCTION DE CE MENSUEL: " + fonction_mensuel.getFonction());
        System.out.print("DOCUMENTS OBLIGATOIRES POUR CE MENSUEL : ");
        for (TypeDocument type : l_type_docs) {
            System.out.println(type.getTypeDocument());
        }
    }

    public List<String> getRecipientsByModule(String module) {

        List<String> destinataires = new LinkedList<>();
        List<MailConfigBean> listDestinatairesMail = mailConfigService.findByModule(module);
        for (MailConfigBean mcb : listDestinatairesMail) {
            destinataires.add(mcb.getMail_to());
        }
        return destinataires;

    }

    public void testWSMensuell() {
        System.out.println("LAUCHING WS ...");
        String jsonRes = MensuelWSCallManager.mensuelWS("11/12/2000");
    }

    public void callWSmensuel() {
        Mensuel m = new Mensuel();
        System.out.println("LAUCHING WS ...");
        String jsonRes = MensuelWSCallManager.mensuelWS("20/01/2020");

        JSONObject obj = new JSONObject(jsonRes);
        JSONArray arr = obj.getJSONArray("Mensuels");
        System.out.println("nobre mensuels" + arr.length());
        for (int i = 0; i < arr.length(); i++) {
            String id = arr.getJSONObject(i).getString("id_divalto");
            String datec = arr.getJSONObject(i).getString("date_creation");
            String dated = arr.getJSONObject(i).getString("date_debut");
            String matricule = arr.getJSONObject(i).getString("matricule").replaceAll("\\s", "");
            String nom = arr.getJSONObject(i).getString("nom");
            String prenom = arr.getJSONObject(i).getString("prenom");
            String fonctionDiva = arr.getJSONObject(i).getString("fonction");
            int status = arr.getJSONObject(i).getInt("status");
            String cin = arr.getJSONObject(i).getString("cin");
            String cnss = arr.getJSONObject(i).getString("cnss");
            String civilite = arr.getJSONObject(i).getString("Civilité");
            String etablissement = arr.getJSONObject(i).getString("établissement");
            String estQuinzainier = arr.getJSONObject(i).getString("Quinzainier");
            String uniMulti;
            try {
                uniMulti = arr.getJSONObject(i).getString("Nature"); //uni multi chantier
            } catch (Exception e) {
                uniMulti = "";
            }
            String typeC;
            try {
                typeC = arr.getJSONObject(i).getString("Type Contrat");
            } catch (Exception e) {
                typeC = "";
            }
//String uniMulti = arr.getJSONObject(i).getString("Nature"); //uni multi chantier
/*
            System.out.println(id);
            System.out.println("date creation" + datec);
            System.out.println("date debut" + dated);
            System.out.println(matricule);
            System.out.println(nom);
            System.out.println(prenom);
            System.out.println(fonctionDiva);
            System.out.println(status);
            System.out.println(cin);
            System.out.println(cnss);
            System.out.println(uniMulti);
            System.out.println("type contrat " + typeC);
            System.out.println("end of one");
            */
            try {
                matricule = Integer.parseInt(matricule.substring(0, matricule.length() - 1)) + "";
            } catch (Exception e) {
                System.out.println("EXCEPTION");
            }

           // System.out.println(" ==== = === = === matricule " + matricule);
            Mensuel testExist = mensuelService.getByMatricule(matricule);

           

            if (testExist != null) {
                System.out.println("mensuel avec matricule : " + matricule + "already exists");
                try {
                    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                    testExist.setDateCreation(formatter.parse(datec));
                    testExist.setDateDebut(formatter.parse(dated));
                } catch (Exception e) {
                    Module.message(1, "Erreur Parse Date Création Mensuel ", e.getMessage());
                    System.out.println(" @@@@@@@@  exist  __ #### "
                            + "erruer parse date contrat " + datec + " date debut " + dated);
                }
                
                
                
                 //detects status change Actif -> Inactif
            if (testExist.getStatut().compareToIgnoreCase("1") == 0 && status == 0) {
                //mail de notification changement d'état
                ApplicationContext context = new ClassPathXmlApplicationContext("mail.xml");
                SendEmail sm = (SendEmail) context.getBean("sendEmail");

                try {
                    String msg1 = "Bonjour, le mensuel " + testExist.getMatricule() + " " + testExist.getNom() + " " + testExist.getPrenom() + " est inactif, prière de lui désaffecter l'ensemble des éléments";

                    if (getRecipientsByModule("MENSUEL") != null ) {
                        for (String recipient : getRecipientsByModule("MENSUEL")) {
                            sm.sendMail("tgccbtp@gmail.com", recipient, "MENSUEL INACTIF", msg1);
                        }
                    }

                } catch (Exception ex) {
                    System.out.println(" ====    EXCEPTION WS MENSUEL CHANGEMENT ETAT : " + ex.getMessage());
                }
            }

                testExist.setCin(cin);
                testExist.setCnss(cnss);
                testExist.setFonction(fonctionService.findByCode(fonctionDiva));
                testExist.setNom(nom);
                testExist.setCiviliteDiva(civilite);
                testExist.setEtablissement(etablissement);
                testExist.setPrenom(prenom);
                testExist.setTypeFonction("Mensuel Type Quinzainier");
                testExist.setStatut(String.valueOf(status));
                testExist.setCiviliteDiva(civilite);
                testExist.setTypeAffectationDiva(uniMulti);
                testExist.setTypeAffectation(true);
                Type type = new Type();
                type.setId(2);
                testExist.setType(type);
                testExist.setTypeContrat(typeC);

                mensuelService.updateMensuel(testExist);

            } else {
                m = new Mensuel();
                try {
                    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                    m.setDateCreation(formatter.parse(datec));
                    m.setDateDebut(formatter.parse(dated));
                } catch (Exception e) {
                    Module.message(1, "Erreur Parse Date Création Mensuel ", e.getMessage());
                    System.out.println("erruer parse date contrat " + datec + " date debut " + dated);
                }
                try {
                    m.setId(Integer.parseInt(id));
                } catch (Exception e) {
                    System.out.println("EXCEPTIOn");
                }
                m.setCin(cin);
                m.setCnss(cnss);
                m.setMatricule(matricule);
                m.setFonction(fonctionService.findByCode(fonctionDiva));
                m.setNom(nom);
                m.setCiviliteDiva(civilite);
                m.setPrenom(prenom);
                m.setTypeFonction("Mensuel Type Quinzainier");
                m.setStatut(String.valueOf(status));
                m.setTypeAffectationDiva(uniMulti);
                m.setTypeAffectation(true);
                Type type = new Type();
                type.setId(2);
                m.setType(type);
                m.setTypeContrat(typeC);
                mensuelService.saveMensuel(m);
            }
        }

        // elContext = FacesContext.getCurrentInstance().getELContext();
        // mensuelServMb = (ma.bservices.tgcc.mb.services.MensuelMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "mensuelServMb");
        // mensuelServMb.reload_Mensuel();
        // l_mensuels = mensuelServMb.l_mensuels;
        //mensuelServMb = (ma.bservices.tgcc.mb.services.MensuelMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "mensuelServMb");
        l_mensuels = mensuelService.findAll();

        System.out.println("WS CALL DONE ...");
    }

    public boolean checkdocs(Mensuel mensuel) {
        //System.out.println("id mensuel " + mensuel.getId());
        List<Document> mensueldocs = documentService.getListDocumentById(mensuel.getId());
        List<TypeDocument> l_doc = new ArrayList<>();
        List<TypeDocument> type_Doc_of_Mensuel = new ArrayList<>();
        Fonction fonction_mensuel = new Fonction();
        try {
              fonction_mensuel=  mensuel.getFonction();
              l_type_docs = typeDocumentService.findDocsByFonction(fonction_mensuel.getId());
        } catch (Exception e) {
            System.out.println("Erreur : "+e.getMessage());
        } 
        if (l_type_docs == null) {
            //System.out.println("Null Pointer");
            return false;
        } else {

            if ((mensueldocs != null) && (mensueldocs.size() > 0)) {
                for (Document doc : mensueldocs) {
                    type_Doc_of_Mensuel.add(doc.getTypeDocument());
                }
                for (TypeDocument Tdoc : l_type_docs) {
                    if (type_Doc_of_Mensuel.contains(Tdoc)) {
                        l_doc.add(Tdoc);
                    }
                }
            }

            if (l_doc.size() == l_type_docs.size()) {
                return true;
            } else {
                return false;
            }
//        return false;
        }

//    public boolean filterByName(Object value, Object filter, Locale locale) {
//        String filterText = (filter == null) ? null : filter.toString().trim();
//        if (filterText == null || filterText.equals("")) {
//            return true;
//        }
//
//        if (value == null) {
//            return false;
//        }
//
//        String carName = value.toString().toUpperCase();
//        filterText = filterText.toUpperCase();
//
//        if (carName.contains(filterText)) {
//            return true;
//        } else {
//            return false;
//        }
//    }
    }

    /**
     *
     * @param str
     * @return
     */
    public String format(String str) {
        String str_2 = "";
        if (str != null) {

            try {

                str_2 = str.substring(8, 10) + "-" + str.substring(5, 7) + "-" + str.substring(0, 4);
                return str_2;

            } catch (Exception e) {
                System.out.println("exception " + e.getMessage());
            }

        }
        return str_2;
    }

    public String navigateToFicheMensuel(Mensuel _m) {
        return "ficheMensuel.xhmtl?faces-redirect=true&mensuelId=" + _m.getId();
    }
}
