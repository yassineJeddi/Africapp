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
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import ma.bservice.tgcc.Constante.Message;
import ma.bservices.mb.services.Module;
import ma.bservices.services.PresenceService;
import ma.bservices.tgcc.utilitaire.TgccFileManager;
import org.apache.commons.io.FilenameUtils;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.springframework.stereotype.Component;

/**
 *
 * @author zakaria.dem
 */
@Component
@ManagedBean(name = "importAbsensesMb")
@ViewScoped
public class ImportAbsensesMb {

    @ManagedProperty(value = "#{presenceService}")
    private PresenceService presenceService;

    private UploadedFile importFile;

    private int mounth;

    private int year;

    private String[] selectedValues;

    private Integer chantierSearch;

    private StreamedContent file_Excel;

    /**
     * Creates a new instance of ImportAbsensesMb
     */
    public ImportAbsensesMb() {
    }

    @PostConstruct
    public void init() {
        Date current_date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(current_date);

        presenceService = Module.ctx.getBean(PresenceService.class);

        this.mounth = cal.get(Calendar.MONTH)+1;
        this.year = cal.get(Calendar.YEAR);
    }

    /**
     * On importe les documents des absenses des salariés.
     *
     * @throws IOException
     */
    public void importer() throws IOException {

        //les valeurs des checkboxes.
        Boolean desaffectationChantiers = arrayContainValue(selectedValues, "0");
        Boolean verificationContrat = arrayContainValue(selectedValues, "2");
        Boolean ecraserPointagesExistants = arrayContainValue(selectedValues, "1");
        Boolean avecReglage = arrayContainValue(selectedValues, "3");

        if (this.chantierSearch == null || this.chantierSearch == -1) {
         } else {
            // Le document est xls ou .xls
            String extension = FilenameUtils.getExtension(importFile.getFileName());
            if ("xls".equals(extension) || "xlsx".equals(extension)) {
                
                try (InputStream input = importFile.getInputstream()) {

                    //On recupère le fichier excel de la vue et on crée les présences
                    Map<String, String[]> data = presenceService.recupererPresencesFichier(input, extension, this.mounth, this.year, desaffectationChantiers, verificationContrat, ecraserPointagesExistants, avecReglage, this.chantierSearch);
                    if (data != null) {
                        //on crée le document rectifié pour le téléchargement
                        ByteArrayOutputStream output = presenceService.creerDocument(data);
                        byte[] bytes = output.toByteArray();

                        //Enregidtrement du document
                        try (InputStream inputEx = new ByteArrayInputStream(bytes)) {

                            String chemin = TgccFileManager.getArboFichier("presenceSalarie");
                            Path folder = Paths.get(chemin);
                            Files.createDirectories(folder);

                            Path file = Files.createTempFile(folder, FilenameUtils.getBaseName(importFile.getFileName()), ".xls");

                            Files.copy(inputEx, file, StandardCopyOption.REPLACE_EXISTING);

                            //inputEx.close();
                          // ByteArrayOutputStream output2=null;                     
                                   
                           //Files.copy(file, output2);

                            //file_Excel = new DefaultStreamedContent(inputEx,"application/vnd.ms-excel",FilenameUtils.getBaseName(importFile.getFileName()) + ".xls");
                            //file_Excel = (StreamedContent) output2;

                        }
                        try (InputStream inputEx2 = new ByteArrayInputStream(bytes)){
                        
                             file_Excel = new DefaultStreamedContent(inputEx2,"application/vnd.ms-excel",FilenameUtils.getBaseName(importFile.getFileName()) + ".xls");
                       
                        }
                        //                    //on enregistre le document sur le repertoire
                        //                    
                        //                    
                        //                    Path folder = Paths.get(chemin);
                        //                    Files.createDirectories(folder);
                        //                    
                        //                    String filename = FilenameUtils.getBaseName(event.getFile().getFileName());
                    }

                } catch (Exception e) {
                   // System.out.println("erreur _ " + e.getMessage() + " __ " + e.getLocalizedMessage());
                    Module.message(2, "Erreur", "importation echouée");
//                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Erreur", "L'importation du fichier a échouée"));

                }

            } else {
                Module.message(2, "Veuillez choisir un fichier excel (xls,xlsx) ", "");
//                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Erreur", "Veuillez choisir un fichier excel (xls,xlsx)"));

            }
        }

    }

    /**
     * si un string est un dans un tableau de string
     *
     * @param values
     * @param value
     * @return
     */
    static Boolean arrayContainValue(String[] values, String value) {

        for (int i = 0; i < values.length; i++) {
            if (values[i].equals(value)) {
                return Boolean.TRUE;
            }
        }

        return Boolean.FALSE;

    }

    //GETTERS AND SETTERS
    public UploadedFile getImportFile() {
        return importFile;
    }

    public void setImportFile(UploadedFile importFile) {
        this.importFile = importFile;
    }

    public int getMounth() {
        return mounth;
    }

    public void setMounth(int mounth) {
        this.mounth = mounth;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String[] getSelectedValues() {
        return selectedValues;
    }

    public void setSelectedValues(String[] selectedValues) {
        this.selectedValues = selectedValues;
    }

    public Integer getChantierSearch() {
        return chantierSearch;
    }

    public void setChantierSearch(Integer chantierSearch) {
        this.chantierSearch = chantierSearch;
    }

    public PresenceService getPresenceService() {
        return presenceService;
    }

    public void setPresenceService(PresenceService presenceService) {
        this.presenceService = presenceService;
    }

    public StreamedContent getFile_Excel() {
        return file_Excel;
    }

    public void setFile_Excel(StreamedContent file_Excel) {
        this.file_Excel = file_Excel;
    }

}
