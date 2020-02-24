/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.mb.demande;

import ma.bservices.mb.demande.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import ma.bservice.tgcc.Constante.Message;
import ma.bservices.beans.Article;
import ma.bservices.beans.ArticleDA;
import ma.bservices.beans.Chantier;
import ma.bservices.beans.DemandeApprovisionnement;
import ma.bservices.beans.EtatDA;
import ma.bservices.constantes.Constantes;
import ma.bservices.mb.services.ConstanteMb;
import ma.bservices.mb.services.EtatDAMb;
import ma.bservices.mb.services.Module;
import ma.bservices.services.AchatService;
import ma.bservices.services.AdministrationService;
import ma.bservices.services.ChantierService;
import ma.bservices.services.DemandeService;
import ma.bservices.services.FamilleArticle;
import ma.bservices.services.ParametrageService;
import ma.bservices.tgcc.Entity.DemandeDetail;
import ma.bservices.tgcc.Entity.DemandeEntete;
import ma.bservices.tgcc.authentification.Authentification;
import org.apache.commons.io.FilenameUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.BeansException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 *
 * @author Mahdi
 */
@Component
@ManagedBean
@ViewScoped
public class NouveauDemandeMb implements Serializable {

    @ManagedProperty(value = "#{demandeService}")
    private DemandeService demandeService;

    @ManagedProperty(value = "#{parametrageService}")
    private ParametrageService parametrageService;

    @ManagedProperty(value = "#{chantierServiceEvol}")
    private ChantierService chantierService;

    @ManagedProperty(value = "#{administrationService}")
    private AdministrationService administrationService;

//    private List<DemandeApprovisionnement> demandes;
//    private Integer page, nbr;
//    private DemandeApprovisionnement DA;
   private String valueButton;
    private Boolean disable;

    private Boolean disableDelete;
    //   private String valueButton;
    private List<DemandeDetail> listArticle = new ArrayList<>();
    //  private Boolean disableDelete;
    private DemandeDetail ADA = new DemandeDetail();
    private List<FamilleArticle> fam1;
    private List<FamilleArticle> fam2;
    private List<FamilleArticle> fam3;
    private String valueFam1;
    private String valueFam2;
    private String valueFam3;
    private String des;
    private List<Article> listeArticle = new ArrayList<>();
    private Article arti = new Article();
    private Integer idChantier;
    private Integer idAtelier;
    private String demand;
    private Long idDA;
    private Float quantite;
    private Float longeur;
    private Float largeur;
    private Float hauteur;
    private String unite;
    private String designation;
    private String autre;
    Date dateSouhaite;
    private String comantaire;
    private Date dateMax;
    private boolean btnEtat;
    //private List<EtatDA> etatDA = new ArrayList<>();
    private DemandeEntete demandeEnt = new DemandeEntete();
    List<UploadedFile> listFile = new ArrayList<UploadedFile>();
    // private String numeroDA;
    //private String chantier;
    private String reference = "";
    //private Date dateAjout;
    private Date dateLS;
    private boolean buttonDis = true;
    private UploadedFile uploadedFileQuitance;

    public boolean isBtnEtat() {
        return btnEtat;
    }

    public void setBtnEtat(boolean btnEtat) {
        this.btnEtat = btnEtat;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Float getQuantite() {
        return quantite;
    }

    public void setQuantite(Float quantite) {
        this.quantite = quantite;
    }

    public Integer getIdChantier() {
        return idChantier;
    }

    public void setIdChantier(Integer idChantier) {
        this.idChantier = idChantier;
    }

    public String getDemand() {
        return demand;
    }

    public void setDemand(String demand) {
        this.demand = demand;
    }

    public ChantierService getChantierService() {
        return chantierService;
    }

    public void setChantierService(ChantierService chantierService) {
        this.chantierService = chantierService;
    }

    public AdministrationService getAdministrationService() {
        return administrationService;
    }

    public void setAdministrationService(AdministrationService administrationService) {
        this.administrationService = administrationService;
    }

    public Article getArti() {
        return arti;
    }

    public void setArti(Article arti) {
        this.arti = arti;
    }

    public List<Article> getListeArticle() {
        return listeArticle;
    }

    public void setListeArticle(List<Article> listeArticle) {
        this.listeArticle = listeArticle;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getValueFam1() {
        return valueFam1;
    }

    public void setValueFam1(String valueFam1) {
        this.valueFam1 = valueFam1;
    }

    public String getValueFam2() {
        return valueFam2;
    }

    public void setValueFam2(String valueFam2) {
        this.valueFam2 = valueFam2;
    }

    public String getValueFam3() {
        return valueFam3;
    }

    public void setValueFam3(String valueFam3) {
        this.valueFam3 = valueFam3;
    }

    public List<FamilleArticle> getFam2() {
        return fam2;
    }

    public void setFam2(List<FamilleArticle> fam2) {
        this.fam2 = fam2;
    }

    public List<FamilleArticle> getFam3() {
        return fam3;
    }

    public void setFam3(List<FamilleArticle> fam3) {
        this.fam3 = fam3;
    }

    public List<FamilleArticle> getFam1() {
        return fam1;
    }

    public void setFam1(List<FamilleArticle> fam1) {
        this.fam1 = fam1;
    }

    public Boolean getDisable() {
        return disable;
    }

    public void setDisable(Boolean disable) {
        this.disable = disable;
    }

    public DemandeService getDemandeService() {
        return demandeService;
    }

    public List<DemandeDetail> getListArticle() {
        return listArticle;
    }

    public DemandeDetail getADA() {
        return ADA;
    }

    public Integer getIdAtelier() {
        return idAtelier;
    }

    public Long getIdDA() {
        return idDA;
    }

    public Float getLongeur() {
        return longeur;
    }

    public Float getLargeur() {
        return largeur;
    }

    public Float getHauteur() {
        return hauteur;
    }

    public String getAutre() {
        return autre;
    }

    public void setAutre(String autre) {
        this.autre = autre;
    }

    public Date getDateSouhaite() {
        return dateSouhaite;
    }

    public DemandeEntete getDemandeEnt() {
        return demandeEnt;
    }

    public void setDemandeService(DemandeService demandeService) {
        this.demandeService = demandeService;
    }

    public void setListArticle(List<DemandeDetail> listArticle) {
        this.listArticle = listArticle;
    }

    public void setADA(DemandeDetail ADA) {
        this.ADA = ADA;
    }

    public void setIdAtelier(Integer idAtelier) {
        this.idAtelier = idAtelier;
    }

    public void setIdDA(Long idDA) {
        this.idDA = idDA;
    }

    public void setLongeur(Float longeur) {
        this.longeur = longeur;
    }

    public void setLargeur(Float largeur) {
        this.largeur = largeur;
    }

    public void setHauteur(Float hauteur) {
        this.hauteur = hauteur;
    }

    public void setDateSouhaite(Date dateSouhaite) {
        this.dateSouhaite = dateSouhaite;
    }

    public void setDemandeEnt(DemandeEntete demandeEnt) {
        this.demandeEnt = demandeEnt;
    }

    public ParametrageService getParametrageService() {
        return parametrageService;
    }

    public void setParametrageService(ParametrageService parametrageService) {
        this.parametrageService = parametrageService;
    }

    public Date getDateLS() {
        return dateLS;
    }

    public void setDateLS(Date dateLS) {
        this.dateLS = dateLS;
    }

    public boolean isButtonDis() {
        return buttonDis;
    }

    public void setButtonDis(boolean buttonDis) {
        this.buttonDis = buttonDis;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public List<UploadedFile> getListFile() {
        return listFile;
    }

    public void setListFile(List<UploadedFile> listFile) {
        this.listFile = listFile;
    }

    public UploadedFile getUploadedFileQuitance() {
        return uploadedFileQuitance;
    }

    public void setUploadedFileQuitance(UploadedFile uploadedFileQuitance) {
        this.uploadedFileQuitance = uploadedFileQuitance;
    }

    public String getUnite() {
        return unite;
    }

    public void setUnite(String unite) {
        this.unite = unite;
    }

    public String getComantaire() {
        return comantaire;
    }

    public void setComantaire(String comantaire) {
        this.comantaire = comantaire;
    }


    
    
    @PostConstruct
    public void init() {
        try {
            //System.out.println("HELLO FROM ACHAT");
            demandeService = Module.ctx.getBean(DemandeService.class);
            parametrageService = Module.ctx.getBean(ParametrageService.class);
            ELContext elContext = FacesContext.getCurrentInstance().getELContext();
            Authentification authentification = (Authentification) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "authentification");
            demand = authentification.get_connected_user().getLogin();
            EtatDAMb etatDAMb = (EtatDAMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "etatDAMb");
            fam1 = etatDAMb.getFam1();
            fam2 = etatDAMb.getFam2();
            fam3 = etatDAMb.getFam3();
            btnEtat = listArticle.isEmpty();

        } catch (BeansException | NumberFormatException ex) {
            Logger.getLogger(NouveauDemandeMb.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void viderArtcileDialog() {
          // System.out.println("vider les champs ");
           this.arti=null;
           this.arti=new Article();
           this.designation=null;
           this.quantite=null;
           this.largeur=null;
           this.longeur=null;
           this.hauteur=null;
           this.autre=null;
           this.dateMax=null;
           this.dateSouhaite=null;
           this.uploadedFileQuitance=null;
           
           
           
  
    }
       
    public void deleteArticleDA() {

        demandeService.deleteDemandeDetail(ADA);
        listArticle = demandeService.getListDemnadeDetailbyIdEntete(idDA);
        btnEtat = listArticle.isEmpty();
        Module.message(0, "L'article DA est supprimé avec sucès", "L'article DA est supprimé avec sucès");
    }

    public boolean qteValide() {
        //System.out.println("test qte");
        return !(quantite != null && quantite > 0.0);
    }

    public void ajouterNouvelleDA() throws IOException {
        if (qteValide()) {
            Module.message(2, "Veuillez saisir une quantité valide", "");
            return;
        }
          
        if (idChantier != null && idChantier != 0 && demandeEnt.getDateLivraisonSouhaitee() != null) {
            
            if (idDA == null) {
                
                Chantier objetChantier = new Chantier();
                objetChantier.setId(idChantier);
                Chantier objetAtelier = new Chantier();
                objetAtelier.setId(idAtelier);
                
                EtatDA objetEtatDA = new EtatDA();
                objetEtatDA.setId(1);
                ELContext elContext = FacesContext.getCurrentInstance().getELContext();
                Authentification authentification = (Authentification) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "authentification");

                demandeEnt.setChantier(objetChantier);
                demandeEnt.setAtelier(objetAtelier);
                demandeEnt.setEtatDA(objetEtatDA);
                demandeEnt.setDateAjout(new Date());
                demandeEnt.setDemandeur(authentification.get_connected_user());
                //demandeEnt.setComantaire(objetChantier.getCodeNovapaie()+ " . " + comantaire);
                idDA = demandeService.addDemandeEntete(demandeEnt);
                demandeEnt.setId(idDA);
                demandeService.updateDemandeEntete(demandeEnt);
            }
            if ("".equals(arti.getCodeArticle().trim())!=true && arti.getCodeArticle() !=null )
            {
                   
                if (demandeService.articleDADejaExiste(arti.getCodeArticle(), idDA) ) {
                    Module.message(2, "Code 2204 : Vous avez déjà ajouté cet Article", "");
                    return;
                }
            }
            DemandeDetail demandeDetail = new DemandeDetail();
            //System.out.println("id article: " + arti.getId());
            //-----------------------v
            String chemin = ConstanteMb.getRepertoire() + "/files/DemandeInterne";
            Path folder = Paths.get(chemin);
            Files.createDirectories(folder);
            if (uploadedFileQuitance.getFileName().isEmpty()==false) {
                //System.out.println(" test id article: 0006");
                String filename = FilenameUtils.getBaseName(uploadedFileQuitance.getFileName());
                String extension = FilenameUtils.getExtension(uploadedFileQuitance.getFileName());

                if (!"pdf".equals(extension) && !"jpg".equals(extension) && !"png".equals(extension) && !"jpeg".equals(extension)) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, Message.STRING_CHARGE_DOCUMENT_PDF, Message.STRING_CHARGE_DOCUMENT_PDF));
                } else {
                    Path file = Files.createTempFile(folder, filename, "." + extension);
                    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                    try (InputStream input = uploadedFileQuitance.getInputstream()) {
                        Files.copy(input, file, StandardCopyOption.REPLACE_EXISTING);
                        demandeDetail.setFileImport(chemin + "/" + file.getFileName());
                    }
                }
            }
                        if (arti.getId() !=null)
                        {
                        demandeDetail.setArtcile(arti);
                        demandeDetail.setRefArticle(arti.getCodeArticle());
                        }
                        demandeDetail.setDemandeEntete(demandeEnt);
                        float b = 0;
                        demandeDetail.setQte(quantite);
                        if (largeur !=null )
                        {
                            demandeDetail.setLargeur(largeur);
                        }
                        if (hauteur !=null )
                        {
                            demandeDetail.setHauteur(hauteur);
                        }
                        if (longeur !=null )
                        {
                            demandeDetail.setLongeur(longeur);
                        }
                        if (unite !=null )
                        {
                            demandeDetail.setUnite(unite);
                        }
                        if (autre !=null )
                        {
                            demandeDetail.setAutre(autre);
                        }
                        if (designation !=null )
                        {                        
                            demandeDetail.setDesignationDemandeur(designation);
                        }
                        if (dateMax !=null )
                        {                        
                            demandeDetail.setDateMaxUtilisation(dateMax);
                        }
                     
                        demandeDetail.setDateSouhaite(demandeEnt.getDateLivraisonSouhaitee());
                        //System.out.println(" test id article: 0005 ");
                        Long idADA = demandeService.addDemandeDetail(demandeDetail);
                        listArticle.add(demandeService.getDemandeDetail(idADA));
                        btnEtat = listArticle.isEmpty();
                        Module.message(0, "Article Ajoutée", "");
                        viderArtcileDialog();
        } else {
            Module.message(2, "veuillez saisir la date laivraison souhaitée", "");
            viderArtcileDialog();
        }
    }

    public void fam2ByFam1() {
        //System.out.println("<<<<<<<<<<<<<<<< ENTREE >>>>>>>>>>>>>>>>>>" + valueFam1);
        if ("".equals(valueFam1) || valueFam1 == null) {
            if (fam3 != null) {
                fam3.clear();
            }
            if (fam2 != null) {
                fam2.clear();
            }
            return;
        }
        if (fam3 != null) {
            fam3.clear();
        }
        String reqDos = " dos=700  ";
        // valueFam1 = valueFam1.substring(1, 4);
        fam2 = (List<FamilleArticle>) parametrageService.listeFamilles(0, 10, 1, 5, valueFam1.substring(1, 4), reqDos);
        fam3 = (List<FamilleArticle>) parametrageService.listeFamilles(0, 10, 1, 8, valueFam1.substring(1, 4), reqDos);

        //System.out.println("fam2 --------------> " + fam2.size());
        //System.out.println("fam3 --------------> " + fam3.size());
        //recherche des articles 

        // this.articleByFam();
        valueFam2 = "";
        valueFam3 = "";
        des = "";

        //System.out.println("********* REFERENCE: " + reference);
        //System.out.println("********* FAM1 " + valueFam1);
        //System.out.println("********* FAM2 " + valueFam2);
        //System.out.println("********* FAM3 " + valueFam3);
        //System.out.println("********* reqdos " + reqDos);
        //System.out.println("********* des " + des);

        articleByFam();

        listeArticle = demandeService.listeRechercheArticle(0, 10, reference, valueFam1, valueFam2, valueFam3 != null ? valueFam3.trim() : null, reqDos, des);

        //System.out.println("********* Size Fam 2 " + fam2.size());
        //System.out.println("********* Size Fam 3 " + fam3.size());
    }

    public void fam3ByFam2() {
        String reqDos = " dos=700 ";
        //valueFam2 = valueFam2.substring(1, 6);
        //valueFam2 ="";
        valueFam3 = "";
        des = "";
        fam3 = (List<FamilleArticle>) parametrageService.listeFamilles(0, 10, 1, 8, valueFam2.substring(1, 6), reqDos);
        listeArticle = demandeService.listeRechercheArticle(0, 10, reference, valueFam1, valueFam2, valueFam3 != null ? valueFam3.trim() : null, reqDos, des);
    }

    public void articleByFam() {
        if (idChantier != null && idChantier != 0 || demandeEnt.getDateLivraisonSouhaitee() != null) {
            //System.out.println("!!!!!!!!!!!!!!!!! ENTREE ??????????????????? ");
            String reqDos = " dos=700  ";
            //System.out.println("********* REFERENCE: " + reference);
            //System.out.println("********* FAM1 " + valueFam1);
            //System.out.println("********* FAM2 " + valueFam2);
            //System.out.println("********* FAM3 " + valueFam3);
            //System.out.println("********* reqdos " + reqDos);
            //System.out.println("********* des " + des);

            int limit = Integer.parseInt(demandeService.nombreRechercheArticle("", valueFam1, valueFam2, valueFam3 != null ? valueFam3.trim() : null, reqDos, des).toString());
            listeArticle = demandeService.listeRechercheArticle(0, limit, reference, valueFam1, valueFam2, valueFam3 != null ? valueFam3.trim() : null, reqDos, des);
            //System.out.println("************** SIZE ARTICLE ************ " + listeArticle.size() + " @@@@@@@@@@@ " + limit + " FAM1 " + valueFam1.trim() + " FAM2 " + valueFam2.trim() + " FAM3 " + valueFam3.trim());
        } else {
            Module.message(2, "Attention l'un des Champs (Chantier ou Date de livraison souhaitée) est vide", "Attention l'un des Champs est vide");
        }
    }

    public void envoyerArticleDA() {

          
    if (!Module.checkDate(new Date(), null, demandeEnt.getDateLivraisonSouhaitee())) {
            Module.message(2, "date livraison doit être supérieur a la date d'aujourd'hui", "");
            return;
        }
        double g = demandeService.updateDemandeEntete(demandeEnt);
        //System.out.println("demandeService.updateDemandeEntete(DA) ID ::::: " + g);
        idDA = demandeEnt.getId();
        DemandeEntete newDa = demandeService.getDemandeEntete(idDA);
        if (idDA != 0) {
            Chantier objetChantier = chantierService.getChantier(idChantier);
            Chantier objetAtelier = chantierService.getChantier(idAtelier);
            Date objetDateLivraisonSouhaiteeDA = null;

            if (demandeEnt.getDateLivraisonSouhaitee() != null) {
                objetDateLivraisonSouhaiteeDA = demandeEnt.getDateLivraisonSouhaitee();
            }
            EtatDA objetEtatDA = new EtatDA();
            objetEtatDA.setId(2);
            objetEtatDA.setEtat("Envoyée");
            ELContext elContext = FacesContext.getCurrentInstance().getELContext();
            Authentification authentification = (Authentification) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "authentification");
            Date date = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            
            int nbrD=demandeService.nombreDemandeInternebyDemandeur(newDa.getDemandeur().getId(), newDa.getDateAjout());
            newDa.setNumDemande("DI"+calendar.get(Calendar.MONTH)+calendar.get(Calendar.YEAR)+"-"+nbrD+"-"+idDA);
            newDa.setChantier(objetChantier);
            newDa.setAtelier(objetAtelier);
            newDa.setDateAjout(new Date());
            newDa.setDateLivraisonSouhaitee(objetDateLivraisonSouhaiteeDA);
            newDa.setDemandeur(authentification.get_connected_user());
            newDa.setComantaire(newDa.getChantier().getCode() + " . " + newDa.getComantaire());
            newDa.setEtatDA(objetEtatDA);
            demandeService.updateDemandeEntete(newDa);
             
             idDA = newDa.getId();
             listArticle = demandeService.getListDemnadeDetailbyIdEntete(idDA);
             for (int i = 0; i < listArticle.size(); i++) {
                ADA = new DemandeDetail();
                ADA=listArticle.get(i);
                objetEtatDA = new EtatDA();
                objetEtatDA.setId(2);
                objetEtatDA.setEtat("Envoyée");
                ADA.setEtatDA(objetEtatDA);
                demandeService.updateDemandeDetail(ADA); 
                
            }    
        } else {
            Module.message(1, "Demande Interne n'existe pas", "");
        }
       
            demandeEnt = demandeService.getDemandeEntete(idDA);
            listArticle = demandeService.DemandeDetail(idDA);   
            //System.out.println("idDA " + idDA);
            if (demandeEnt.getEtatDA().getId() == 2) {
                disable = true;
               
                valueButton = "Déjà envoyé";
                disableDelete = true;
            }
            if (demandeEnt.getEtatDA().getId() != 1 && demandeEnt.getEtatDA().getId() != 2) {
                disable = true;
               
                valueButton = "Déjà envoyé";
                disableDelete = true;
            }
            if (demandeEnt.getEtatDA().getEtat().equals("Brouillon")) {
                disable = false;
               
                valueButton = "Envoyé";
                disableDelete = false;
            }
    
    }

    public void findArticle() {
        String dos = Constantes.getInstance().numeroDossierDivalto;
        Article objetArticle = parametrageService.getArticleByCode(arti.getCodeArticle(), dos);
        if (objetArticle == null) {
            Module.message(2, "Code 2203: Article n'existe pas ou Réference incomplète", "");
            return;
        }
        arti = objetArticle;
    }

    public void btnAjoutArticleDisable() {
        if (demandeEnt.getDateLivraisonSouhaitee() != null && !Module.checkDate(new Date(), null, demandeEnt.getDateLivraisonSouhaitee())) {
            //System.out.println("date livr < = today");
            Module.message(2, "date livraison doit être supérieur a la date d'aujourd'hui", "");
            return;
        }
        buttonDis = !(idChantier != null || idChantier != 0 || demandeEnt.getDateLivraisonSouhaitee() != null);
    }

    public String getValueButton() {
        return valueButton;
    }

    public void setValueButton(String valueButton) {
        this.valueButton = valueButton;
    }

    public Boolean getDisableDelete() {
        return disableDelete;
    }

    public void setDisableDelete(Boolean disableDelete) {
        this.disableDelete = disableDelete;
    }

    public Date getDateMax() {
        return dateMax;
    }

    public void setDateMax(Date dateMax) {
        this.dateMax = dateMax;
    }
    
    
    
}
