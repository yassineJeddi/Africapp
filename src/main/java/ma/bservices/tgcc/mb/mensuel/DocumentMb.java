/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.mb.mensuel;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import ma.bservice.tgcc.Constante.Message;
import ma.bservices.beans.Document;
import ma.bservices.beans.TypeDocument;
import ma.bservices.mb.services.ConstanteMb;
import ma.bservices.tgcc.Entity.Affectation;
import ma.bservices.tgcc.Entity.Mensuel;
import ma.bservices.tgcc.Entity.SousAffectation;
import ma.bservices.tgcc.service.Engin.Bean.CiterneServiceBean;
import ma.bservices.tgcc.service.Mensuel.AffectationService;
import ma.bservices.tgcc.service.Mensuel.DocumentService;
import ma.bservices.tgcc.service.Mensuel.MensuelService;
import ma.bservices.tgcc.service.Mensuel.TypeDocumentService;
import ma.bservices.tgcc.service.Mensuel.bean.TypeDocumentServiceInter;
import ma.bservices.tgcc.utilitaire.TgccFileManager;
import ma.bservices.tgcc.webService.MensuelWSCallManager;
import org.primefaces.model.UploadedFile;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;
import org.apache.commons.io.FilenameUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.primefaces.event.RowEditEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author a.wattah
 */
@Component
@ManagedBean(name = "documentMb")
@ViewScoped
public class DocumentMb implements Serializable {

    @ManagedProperty(value = "#{documentService}")
    private DocumentService documentService;

    @ManagedProperty(value = "#{affectationService}")
    private AffectationService affectationService;

    @ManagedProperty(value = "#{TypedocumentService}")
    private TypeDocumentService typeDocumentService;
    
    @ManagedProperty(value = "#{mensuelService}")
    private MensuelService mensuelService;

    private List<Document> documents;
    private Document documentsSelected = new Document();

    private Document document;

    private UploadedFile uploadedFile;

    private static String chemin;
    private String titre;
    private Part file;

    private String chantierDivaltoMensuel = "N/A";

    private Boolean dispaly_button_ajouter = true;

    private SousAffectation sousAffectation_To_Select;

    private List<SousAffectation> l_sousAffectations;

    private String typeAffectation = "";

    /**
     * variable pour list des affectations d'un mensuel
     */
    private List<Affectation> list_Affectation_ByMensuel_FromTo_Date;

    private Date date_to;

    private Date date_from;

    private List<Document> l_docs;

    private List<Affectation> list_Date_affectation;

    private List<SousAffectation> list_Affect_By_Date;

    private String date_to_affichaffectation_financiere;

    /**
     * instance de class typDocumentInter
     */
    private TypeDocumentServiceInter typeDocumentInter = new TypeDocumentServiceInter();

    private List<TypeDocument> l_type_docs = new ArrayList<>();
    private Mensuel mensuelAffect;

    private List<Document> listDocumentByIdMensuel = new ArrayList<>();
    /**
     * veriable pour afficher type doc obligatoir dialog box
     */
    private TypeDocument item_Type_Doc;

    private Document document_to_delete;

    private Document documents_consulte;

    private String document_chemin = "";
    private String titreUpdate;

    public Document getDocumentsSelected() {
        return documentsSelected;
    }

    public void setDocumentsSelected(Document documentsSelected) {
        this.documentsSelected = documentsSelected;
    }

    public String getTitreUpdate() {
        return titreUpdate;
    }

    /**
     * getters and setters
     *
     * @return
     */
    public void setTitreUpdate(String titreUpdate) {
        this.titreUpdate = titreUpdate;
    }

    public List<SousAffectation> getL_sousAffectations() {
        return l_sousAffectations;
    }

    public String getTypeAffectation() {
        return typeAffectation;
    }

    public void setTypeAffectation(String typeAffectation) {
        this.typeAffectation = typeAffectation;
    }

    public void setL_sousAffectations(List<SousAffectation> l_sousAffectations) {
        this.l_sousAffectations = l_sousAffectations;
    }

    public List<Document> getL_docs() {
        return l_docs;
    }

    public void setL_docs(List<Document> l_docs) {
        this.l_docs = l_docs;
    }

    public String getChantierDivaltoMensuel() {
        return chantierDivaltoMensuel;
    }

    public MensuelService getMensuelService() {
        return mensuelService;
    }

    public void setMensuelService(MensuelService mensuelService) {
        this.mensuelService = mensuelService;
    }
    
    public void setChantierDivaltoMensuel(String chantierDivaltoMensuel) {
        this.chantierDivaltoMensuel = chantierDivaltoMensuel;
    }

    public String getDate_to_affichaffectation_financiere() {
        return date_to_affichaffectation_financiere;
    }

    public void setDate_to_affichaffectation_financiere(String date_to_affichaffectation_financiere) {
        this.date_to_affichaffectation_financiere = date_to_affichaffectation_financiere;
    }

    public String getDocument_chemin() {
        return document_chemin;
    }

    public void setDocument_chemin(String document_chemin) {
        this.document_chemin = document_chemin;
    }

    public Document getDocuments_consulte() {
        return documents_consulte;
    }

    public void setDocuments_consulte(Document documents_consulte) {
        this.documents_consulte = documents_consulte;
    }

    public Document getDocument_to_delete() {
        return document_to_delete;
    }

    public void setDocument_to_delete(Document document_to_delete) {
        this.document_to_delete = document_to_delete;
    }

    public Boolean getDispaly_button_ajouter() {
        return dispaly_button_ajouter;
    }

    public void setDispaly_button_ajouter(Boolean dispaly_button_ajouter) {
        this.dispaly_button_ajouter = dispaly_button_ajouter;
    }

    public TypeDocument getItem_Type_Doc() {
        return item_Type_Doc;
    }

    public void setItem_Type_Doc(TypeDocument item_Type_Doc) {
        this.item_Type_Doc = item_Type_Doc;
    }

    public List<Document> getListDocumentByIdMensuel() {
        return listDocumentByIdMensuel;
    }

    public void setListDocumentByIdMensuel(List<Document> listDocumentByIdMensuel) {
        this.listDocumentByIdMensuel = listDocumentByIdMensuel;
    }

    public List<Affectation> getList_Date_affectation() {
        return list_Date_affectation;
    }

    public List<SousAffectation> getList_Affect_By_Date() {
        return list_Affect_By_Date;
    }

    public void setList_Affect_By_Date(List<SousAffectation> list_Affect_By_Date) {
        this.list_Affect_By_Date = list_Affect_By_Date;
    }

    public void setList_Date_affectation(List<Affectation> list_Date_affectation) {
        this.list_Date_affectation = list_Date_affectation;
    }

    public Date getDate_to() {
        return date_to;
    }

    public void setDate_to(Date date_to) {
        this.date_to = date_to;
    }

    public Date getDate_from() {
        return date_from;
    }

    public void setDate_from(Date date_from) {
        this.date_from = date_from;
    }

    public List<Affectation> getList_Affectation_ByMensuel_FromTo_Date() {
        return list_Affectation_ByMensuel_FromTo_Date;
    }

    public void setList_Affectation_ByMensuel_FromTo_Date(List<Affectation> list_Affectation_ByMensuel_FromTo_Date) {
        this.list_Affectation_ByMensuel_FromTo_Date = list_Affectation_ByMensuel_FromTo_Date;
    }

    public AffectationService getAffectationService() {
        return affectationService;
    }

    public void setAffectationService(AffectationService affectationService) {
        this.affectationService = affectationService;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public static String getChemin() {
        return chemin;
    }

    public static void setChemin(String chemin) {
        DocumentMb.chemin = chemin;
    }

    public DocumentService getDocumentService() {
        return documentService;
    }

    public void setDocumentService(DocumentService documentService) {
        this.documentService = documentService;
    }

    public List<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }

    public SousAffectation getSousAffectation_To_Select() {
        return sousAffectation_To_Select;
    }

    public void setSousAffectation_To_Select(SousAffectation sousAffectation_To_Select) {
        this.sousAffectation_To_Select = sousAffectation_To_Select;
    }

    public TypeDocumentService getTypeDocumentService() {
        return typeDocumentService;
    }

    public void setTypeDocumentService(TypeDocumentService typeDocumentService) {
        this.typeDocumentService = typeDocumentService;
    }

    public TypeDocumentServiceInter getTypeDocumentInter() {
        return typeDocumentInter;
    }

    public void setTypeDocumentInter(TypeDocumentServiceInter typeDocumentInter) {
        this.typeDocumentInter = typeDocumentInter;
    }

    public List<TypeDocument> getL_type_docs() {
        return l_type_docs;
    }

    public void setL_type_docs(List<TypeDocument> l_type_docs) {
        this.l_type_docs = l_type_docs;
    }

    public Mensuel getMensuelAffect() {
        return mensuelAffect;
    }

    public void setMensuelAffect(Mensuel mensuelAffect) {
        this.mensuelAffect = mensuelAffect;
    }

    /*
     * end getters and setters 
     */
    @PostConstruct
    public void init() {
        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        documentService = ctx.getBean(DocumentService.class);
        mensuelService = ctx.getBean(MensuelService.class);
        affectationService = ctx.getBean(AffectationService.class);
        typeDocumentService = ctx.getBean(TypeDocumentService.class);
        document = new Document();
        this.l_type_docs = typeDocumentService.l_docs_Obligatoire();
        documents_consulte = new Document();
//        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
//        ma.bservices.tgcc.mb.services.DocumentMb document_bean = (ma.bservices.tgcc.mb.services.DocumentMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "document_ServMb");

        // document_bean.reload();
        //  l_docs = document_bean.getL_documents();
        
        int id = -1;
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        Map<String, String> requestParameters = externalContext.getRequestParameterMap();
        if (requestParameters.containsKey("mensuelId")) {
            id = Integer.valueOf(requestParameters.get("mensuelId"));
            mensuelAffect = mensuelService.getById(id);
            if(mensuelAffect instanceof Mensuel) {
                this.listDocuementsBy(mensuelAffect);
            }            
        }
    }

    /**
     * methode qui permet de telecharger fichier
     *
     * @param d
     * @throws IOException
     */
    public void downLoad(Document d) throws IOException {

        CiterneServiceBean citerneServiceBean = new CiterneServiceBean();
        if (d != null) {
            if (d.getChemin() != null) {
                citerneServiceBean.telecharger_fichier(d.getChemin());

            }
        }

    }

    /*
     * upload file 
     */
    public void save() throws IOException {

//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMM dd HH:mm:ss");        
//        Date tDate = new Date();        
//        System.out.println("DATE:" + sdf.format(tDate));
//        Calendar calendar = new GregorianCalendar();        
//        chemin = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/"+calendar.get(Calendar.YEAR)+"/"+calendar.get(Calendar.MONTH)+"/"+calendar.get(Calendar.DAY_OF_MONTH)+"/"+calendar.get(Calendar.HOUR_OF_DAY)+"/"+calendar.get(Calendar.MINUTE));
        // DEPRECTAED!  chemin = TgccFileManager.getCheminFichier("Documents Mensuel");
        chemin = ConstanteMb.getRepertoire() + "/files/documentsSalarie";

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
                    document.setChemin(chemin + "/" + file.getFileName());

                    document.setDateCreation(new Date());
                    document.setSalarie(this.mensuelAffect);

                    TypeDocument type_d = typeDocumentService.type_doc("AUTRE");
                    document.setTypeDocument(type_d);

                    document.setCreePar(auth.getPrincipal().toString());

                    documentService.save(document);

                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, Message.STRING_CHARGE_DOCUMENT_DONE, Message.STRING_CHARGE_DOCUMENT_DONE));

                    documents = documentService.getListDocumentById(this.mensuelAffect.getId());

                    document = new Document();

                }

                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, Message.STRING_CHARGE_DOCUMENT_DONE, Message.STRING_CHARGE_DOCUMENT_DONE));

                                    ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
                ec.redirect(ec.getRequestContextPath() + "/mensuel/ficheMensuel.xhtml?mensuelId=" + mensuelAffect.getId());

            }
        }
        // FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, Message.ADD_DOCUMENT, Message.ADD_DOCUMENT));
    }

    /**
     * methode qui permet ajouter document obligatoire
     *
     * @throws IOException
     */
    public void saveObligatoir() throws IOException {
        chemin = ConstanteMb.getRepertoire() + "/files/DocumentsObligatoires";
        // DEPRECTARED! chemin = TgccFileManager.getCheminFichier("Documents Obligatoires");
        Path folder = Paths.get(chemin);
        Files.createDirectories(folder);
        if (uploadedFile == null) {

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, Message.STRING_CHARGE_DOCUMENT, Message.STRING_CHARGE_DOCUMENT));

        } else if (uploadedFile.getFileName().equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, Message.STRING_CHARGE_DOCUMENT, Message.STRING_CHARGE_DOCUMENT));
        } else {
            String filename = FilenameUtils.getBaseName(uploadedFile.getFileName());
            String extension = FilenameUtils.getExtension(uploadedFile.getFileName());

            System.out.println("format pdf :" + extension);
            if (!"pdf".equals(extension)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, Message.STRING_CHARGE_DOCUMENT_PDF, Message.STRING_CHARGE_DOCUMENT_PDF));

            } else {
                Path file = Files.createTempFile(folder, filename + "-", "." + extension);
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                try (InputStream input = uploadedFile.getInputstream()) {
                    Files.copy(input, file, StandardCopyOption.REPLACE_EXISTING);
                    document.setChemin(chemin + "/" + file.getFileName());
                    document.setDateCreation(new Date());
                    document.setSalarie(this.mensuelAffect);

                    document.setTypeDocument(item_Type_Doc);

                    document.setCreePar(auth.getPrincipal().toString());

                    documentService.save(document);

                    documents = documentService.getListDocumentById(this.mensuelAffect.getId());

                    document = new Document();

                }

                ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
                ec.redirect(ec.getRequestContextPath() + "/mensuel/ficheMensuel.xhtml?mensuelId=" + mensuelAffect.getId());
            }
        }
    }

    private static final int DEFAULT_BUFFER_SIZE = 10240;

    public void downLoad() throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletResponse response = (HttpServletResponse) context
                .getExternalContext().getResponse();
        File file = new File(document.getChemin());
        if (!file.exists()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        response.reset();
        response.setBufferSize(DEFAULT_BUFFER_SIZE);
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Length", String.valueOf(file.length()));
        response.setHeader("Content-Disposition", "attachment;filename=\""
                + file.getName() + "\"");
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

    public void rechercherMensuelsAffecter() {

        if (list_Date_affectation != null) {
            list_Date_affectation.clear();
        }

        list_Date_affectation = affectationService.list_Affectaion_BetweenTo_Date_(date_to, date_from, this.mensuelAffect);

        if (list_Date_affectation == null) {
            list_Date_affectation = affectationService.list_affectation_byid_Mensuel(this.mensuelAffect.getId());
        }

    }
    
     public String findChantierDivaltoWS(Mensuel s) {

        //System.out.println("Calling ws chantier divalto");
        // MensuelWSCallManager.Get_Chantier_By_Mensuel("05-04-2010 15:05:00", "703");
        //System.out.println("Done calling ws chantier divalto");

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
//                    String id = arr.getJSONObject(i).getString("id_divalto");
//                    String matricule = arr.getJSONObject(i).getString("matricule");
//                    String code_chan = arr.getJSONObject(i).getString("Code Chantier");
                    String lib_chan = arr.getJSONObject(i).getString("Libellé chantier");
                    //System.out.println("MENSUEL CHANTIER DETECTED : " + lib_chan);
                     return lib_chan;
                }
            }
        } catch (Exception e) {
            System.out.println("EXCEPTION DETECTED!" + e.getMessage());
        }

        return "N/A";

    }

    public List<TypeDocument> findTypesByFonction() {
        if (mensuelAffect != null && mensuelAffect.getFonction() != null) {
            return typeDocumentService.findDocsByFonction(mensuelAffect.getFonction().getId());
        }
        return null;
    }

    public void findChantierDivalto(Mensuel s) {

        //System.out.println("Calling ws chantier divalto");
        // MensuelWSCallManager.Get_Chantier_By_Mensuel("05-04-2010 15:05:00", "703");
        //System.out.println("Done calling ws chantier divalto");

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
//                    String id = arr.getJSONObject(i).getString("id_divalto");
//                    String matricule = arr.getJSONObject(i).getString("matricule");
//                    String code_chan = arr.getJSONObject(i).getString("Code Chantier");
                    String lib_chan = arr.getJSONObject(i).getString("Libellé chantier");
                    //System.out.println("MENSUEL CHANTIER DETECTED : " + lib_chan);
                    chantierDivaltoMensuel = lib_chan;
                }
            }
        } catch (Exception e) {
            System.out.println("EXCEPTION DETECTED!" + e.getMessage());
            chantierDivaltoMensuel = "N/A";
        }

        System.out.println("finished ws!");

    }

    /**
     * Charge la liste des documents et des affectation d'un mensuel
     *
     * @param _m
     */
    public void listDocuementsBy(Mensuel _m) {

        System.out.println("entre : ");
        this.findChantierDivalto(_m);
        this.mensuelAffect = _m;

        if (this.mensuelAffect != null) {
            if (this.mensuelAffect.getTypeAffectation() != null) {

                if (this.mensuelAffect.getTypeAffectationDiva().equals(true)) {
                    this.typeAffectation = "Multi Chantier";
                } else {
                    this.typeAffectation = "Uni Chantier";
                }

            }
        }

        this.getAffectation_byMensuel();

        sousAffectation_To_Select = new SousAffectation();

        this.list_Affect_By_Date = new ArrayList<>();

        //find document by mensuel
        if (documents != null && documents.size() > 0) {
            documents.clear();
        }
        //System.out.println("id mensuel fiche : " + this.mensuelAffect.getId());
        documents = documentService.getListDocumentById(this.mensuelAffect.getId());

        /**
         * recupere liste des type document
         */
        //  listDocumentByIdMensuel = documentService.getListDocumentById(this.mensuelAffect.getId());
    }

    public void getAffectation_byMensuel() {
        System.out.println("rechargement de mensuels");
        list_Date_affectation = affectationService.list_affectation_byid_Mensuel(this.mensuelAffect.getId());
    }

    public void redirect_Togle_Affectation(Affectation aff) {

        System.out.println("entree");
        list_Affect_By_Date = new ArrayList<>();
        list_Affect_By_Date = affectationService.list_affectation_byid_Affectation(aff.getIDAffectation());

    }

    /**
     * cette metthode qui permet de supprimer document d'un mensuel
     *
     * @param doc
     */
    public void delete() throws IOException {

        documentService.deleteDocument(document_to_delete);

        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("" + Message.DELETE_DOCUMENT, ""));

        if (documents.size() > 0) {
            documents.clear();
        }

        documents = documentService.getListDocumentById(this.mensuelAffect.getId());

        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect(ec.getRequestContextPath() + "/mensuel/ficheMensuel.xhtml?mensuelId=" + mensuelAffect.getId());

    }

    /**
     * methode recuper list documents d'un mensuel
     */
    public void get_list_docs_mensuel(Mensuel mensuel) {
        documents = documentService.getListDocumentById(mensuel.getId());
    }

    public void display_button_add() {

    }

    /**
     * methode de consulte les documents
     *
     * @param selected
     */
    public void consult_documents(Document selected) {

        documents_consulte = selected;

    }

    /**
     * Consulter Document By Mahdi
     */
    private String selectedDoc;

    public String getSelectedDoc() {
        return selectedDoc;
    }

    public void setSelectedDoc(String selectedDoc) {
        this.selectedDoc = selectedDoc;
    }

    public void visualiser(String chemin) {
        selectedDoc = "";
        selectedDoc = chemin.substring(chemin.indexOf("files"), chemin.length());
    }

    public void onRowEdit(RowEditEvent event) {

        System.out.println("EDITING ...");
        System.out.println("INSIDE EDIT : TITRE : " + ((Document) event.getObject()).getTitre());
        documentService.update((Document) event.getObject());

        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(" " + Message.UPDATE_TITRE, ""));
    }

    public void onRowCancel() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, Message.UPDATE_TITRE_CANCEL, ""));
    }
}
