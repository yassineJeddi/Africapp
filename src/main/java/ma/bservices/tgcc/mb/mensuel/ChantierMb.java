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
import java.util.Collection;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import ma.bservices.beans.Chantier;
import ma.bservices.beans.Document;
import ma.bservices.beans.Zone;
import ma.bservices.tgcc.Entity.Voiture;
import ma.bservices.tgcc.service.Engin.ChantierService;
import ma.bservices.tgcc.service.Engin.ZoneServices;
import ma.bservices.tgcc.service.Mensuel.DocumentService;
import ma.bservices.tgcc.utilitaire.TgccFileManager;
import org.apache.commons.io.FilenameUtils;
import org.primefaces.model.UploadedFile;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

/**
 *
 * @author j.allali
 */
@Component
@ManagedBean(name = "chantierMb")
@ApplicationScoped
public class ChantierMb implements Serializable {

    @ManagedProperty(value = "#{chantierService}")
    private ChantierService chantierService;

    private List<Chantier> chantiers;
    private List<Chantier> chantiersById;
    private List<Zone> zonesInChantier;

    public List<Zone> getZonesInChantier() {
        return zonesInChantier;
    }

    public void setZonesInChantier(List<Zone> zonesInChantier) {
        this.zonesInChantier = zonesInChantier;
    }
//    private List<VoitureChantier> VoitureChantier;
//    private List<VoitureChantier> VoitureChantiers;
    private Chantier chantier = new Chantier();
    private Chantier chantierToSearch = new Chantier();
    private Chantier chantierSearch = new Chantier();

    private Integer chantierId;
    private String lib;
    private String region;

//    gestion des documents 
    @ManagedProperty(value = "#{documentService}")
    private DocumentService documentService;

    @ManagedProperty(value = "#{zoneServices}")
    private ZoneServices zoneService;

    public ZoneServices getZoneService() {
        return zoneService;
    }

    public void setZoneService(ZoneServices zoneService) {
        this.zoneService = zoneService;
    }

    private List<Document> documents;

    private Document document = new Document();
    private Document documentToAdd;
    private Document documentToLoad = new Document();
//    List<VoitureChantier> voituresByIdCantier;
    private String titre;
    private String commentaire;

    private UploadedFile uploadedFile;

    private static String chemin;
//    private VoitureChantier voitureChantierD = new VoitureChantier();

    private Part file;
    private String valueOfRadioB;

    public ChantierMb() {

    }

    public String getValueOfRadioB() {
        return valueOfRadioB;
    }

    public void setValueOfRadioB(String valueOfRadioB) {
        this.valueOfRadioB = valueOfRadioB;
    }

 //   private VoitureChantier vc = new VoitureChantier();

    /*
     * getters setters 
     */
    public ChantierService getChantierService() {
        return chantierService;
    }

    public void setChantierService(ChantierService chantierService) {
        this.chantierService = chantierService;
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
/*
    public List<VoitureChantier> getVoitureChantier() {
        return VoitureChantier;
    }

    public void setVoitureChantier(List<VoitureChantier> VoitureChantier) {
        this.VoitureChantier = VoitureChantier;
    }
*/
    public Chantier getChantierToSearch() {
        return chantierToSearch;
    }

    public void setChantierToSearch(Chantier chantierToSearch) {
        this.chantierToSearch = chantierToSearch;
    }
/*
    public VoitureChantier getVc() {
        return vc;
    }

    public void setVc(VoitureChantier vc) {
        this.vc = vc;
    }
*/
    public String getLib() {
        return lib;
    }

    public void setLib(String lib) {
        this.lib = lib;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }
/*
    public List<VoitureChantier> getVoitureChantiers() {
        return VoitureChantiers;
    }

    public void setVoitureChantiers(List<VoitureChantier> VoitureChantiers) {
        this.VoitureChantiers = VoitureChantiers;
    }
*/
    public Chantier getChantierSearch() {
        return chantierSearch;
    }

    public void setChantierSearch(Chantier chantierSearch) {
        this.chantierSearch = chantierSearch;
    }

    public Integer getChantierId() {
        return chantierId;
    }

    public void setChantierId(Integer chantierId) {
        this.chantierId = chantierId;
    }

    public List<Chantier> getChantiersById() {
        return chantiersById;
    }

    public void setChantiersById(List<Chantier> chantiersById) {
        this.chantiersById = chantiersById;
    }

    public DocumentService getDocumentService() {
        return documentService;
    }

    public void setDocumentService(DocumentService documentService) {
        this.documentService = documentService;
    }

    public Collection<Document> getDocuments() {
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

    public static String getChemin() {
        return chemin;
    }

    public static void setChemin(String chemin) {
        ChantierMb.chemin = chemin;
    }

    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }

    public Document getDocumentToAdd() {
        return documentToAdd;
    }

    public void setDocumentToAdd(Document documentToAdd) {
        this.documentToAdd = documentToAdd;
    }

    public Document getDocumentToLoad() {
        return documentToLoad;
    }

    public void setDocumentToLoad(Document documentToLoad) {
        this.documentToLoad = documentToLoad;
    }
/*
    public List<VoitureChantier> getVoituresByIdCantier() {
        return voituresByIdCantier;
    }

    public void setVoituresByIdCantier(List<VoitureChantier> voituresByIdCantier) {
        this.voituresByIdCantier = voituresByIdCantier;
    }

    public VoitureChantier getVoitureChantierD() {
        return voitureChantierD;
    }

    public void setVoitureChantierD(VoitureChantier voitureChantierD) {
        this.voitureChantierD = voitureChantierD;
    }
*/
    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    /**
     * Creates a new instance of ChantierMb
     */
    @PostConstruct
    public void init() {
        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        chantierService = ctx.getBean(ChantierService.class);
        //   VoitureChantiers = chantierService.findAllVoiture();
        documentService = ctx.getBean(DocumentService.class);

    }

 

    public void onChantierChange() {

        chantiers = chantierService.findByAffaire(chantierToSearch.getCodeNovapaie());

    }

//    gestion des documents 
    /*
     * upload file 
     */
    public void save() throws IOException {
        documentToAdd = new Document();
        chemin = TgccFileManager.getCheminFichier("Voitures");
        Path folder = Paths.get(chemin);
        String filename = FilenameUtils.getBaseName(uploadedFile.getFileName());
        String extension = FilenameUtils.getExtension(uploadedFile.getFileName());
        Path file = Files.createTempFile(folder, filename + "-", "." + extension);
        try (InputStream input = uploadedFile.getInputstream()) {
            Files.copy(input, file, StandardCopyOption.REPLACE_EXISTING);

            documentToAdd.setChemin(chemin + "/" + file.getFileName());
         //   documentToAdd.setVoiture(voitureChantierD);
            documentToAdd.setTitre(titre);
            documentToAdd.setCommentaire(commentaire);
            documents.add(documentToAdd);

            documentService.save(documentToAdd);
            titre = "";
        }
    }

    public void remove(Document a) {
        for (int i = 0; i < documents.size(); i++) {
            if (documents.get(i).getId().equals(a.getId())) {
                documents.remove(i);
            }
        }
        documentService.deleteDocument(a);

    }

    private static final int DEFAULT_BUFFER_SIZE = 10240;

    public void downLoad() throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletResponse response = (HttpServletResponse) context
                .getExternalContext().getResponse();

        File file = new File(documentToLoad.getChemin());
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

    public void consultDocumentVoitureChantier() {
        documents = new ArrayList<>();
     //   documents = chantierService.getDocumentVoitureChantier(voitureChantierD.getId());
    }

}
