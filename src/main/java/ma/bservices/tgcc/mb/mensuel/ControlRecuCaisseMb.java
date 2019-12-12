/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.mb.mensuel;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
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
import ma.bservices.tgcc.service.Engin.ChantierService;
import ma.bservices.tgcc.service.Mensuel.BonCaisseService;
import ma.bservices.tgcc.service.Mensuel.LoyerService;
import ma.bservices.tgcc.service.Mensuel.MensuelService;
import org.apache.commons.io.FilenameUtils;
import org.primefaces.model.UploadedFile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

/**
 *
 * @author airaamane
 */
@Component
@ManagedBean(name = "control_recu_caisse_Mb")
@ViewScoped
public class ControlRecuCaisseMb implements Serializable {

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
    private int idRequest;
    private List<BonCaisse> BCRecu = new ArrayList<>();
    private String codeCurrent;
    private BonCaisse bc = new BonCaisse();
    private BonCaisse bcChantier = new BonCaisse();
    private UploadedFile uploadedFile;
    private List<BonCaisse> listeBonCaiss;

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

    public List<BonCaisse> getListeBonCaiss() {
        return listeBonCaiss;
    }

    public void setListeBonCaiss(List<BonCaisse> listeBonCaiss) {
        this.listeBonCaiss = listeBonCaiss;
    }

    public List<BonCaisse> getBCRecu() {
        return BCRecu;
    }

    public void setBCRecu(List<BonCaisse> BCRecu) {
        this.BCRecu = BCRecu;
    }

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public String getCodeCurrent() {
        return codeCurrent;
    }

    public BonCaisse getBc() {
        return bc;
    }

    public void setBc(BonCaisse bc) {
        this.bc = bc;
    }

    public void setCodeCurrent(String codeCurrent) {
        this.codeCurrent = codeCurrent;
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

    public int getIdRequest() {
        return idRequest;
    }

    public void setIdRequest(int idRequest) {
        this.idRequest = idRequest;
    }

    public BonCaisse getBcChantier() {
        return bcChantier;
    }

    public void setBcChantier(BonCaisse bcChantier) {
        this.bcChantier = bcChantier;
    }

    public LoyerSalarie getLoyerSalarieSelectMensuel() {
        return loyerSalarieSelectMensuel;
    }

    public void setLoyerSalarieSelectMensuel(LoyerSalarie loyerSalarieSelectMensuel) {
        this.loyerSalarieSelectMensuel = loyerSalarieSelectMensuel;
    }

    public LoyerChantier getLcSelected() {
        return lcSelected;
    }

    public void setLcSelected(LoyerChantier lcSelected) {
        this.lcSelected = lcSelected;
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
if(loyerSalarieSelectMensuel != null){
        BCRecu = bonCaisseService.getbcByIdLoyerSalarie(loyerSalarieSelectMensuel.getId());
}
if(lcSelected != null){
        listeBonCaiss = bonCaisseService.getbcByIdLoyerChan(lcSelected.getId());
}

    }

    public void initImportRC(BonCaisse bonCaisseIt) {
        System.out.println("IMPORTING RC : " + bonCaisseIt.getChemin());
        bc = bonCaisseIt;

    }

    public void initImportRCC(BonCaisse bonCaisseIt) {
        System.out.println("IMPORTING RC : " + bonCaisseIt.getChemin());
        bcChantier = bonCaisseIt;
    }

    public void save() throws IOException {

        Boolean existe = false;

        System.out.println("BON DE CAISSE CURRENT : " + bc.getChemin());

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

    public void saveRCC() throws IOException {

        Boolean existe = false;

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

                    //bcChantier.setLoyerChantier(lcSelected);
                    bonCaisseService.updateBonCaisse(bcChantier);

                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, Message.STRING_CHARGE_DOCUMENT_DONE, Message.STRING_CHARGE_DOCUMENT_DONE));
                    listeBonCaiss = bonCaisseService.getbcByIdLoyerChan(lcSelected.getId());
                }

            }
        }
    }

}
