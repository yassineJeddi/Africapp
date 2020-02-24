/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.mb.demande;

import com.itextpdf.text.log.SysoLogger;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.el.ELContext;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import ma.bservice.tgcc.Constante.Message;
import ma.bservices.beans.Article;
import ma.bservices.beans.Chantier;
import ma.bservices.beans.EtatDA;
import ma.bservices.beans.Permission;
import ma.bservices.beans.Utilisateur;
import ma.bservices.constantes.Constantes;
import ma.bservices.mb.services.ConstanteMb;
import ma.bservices.mb.services.EtatDAMb;
import ma.bservices.mb.services.Module;
import ma.bservices.services.AdministrationService;
import ma.bservices.services.ChantierService;
import ma.bservices.services.DemandeService;
import ma.bservices.services.FamilleArticle;
import ma.bservices.services.ParametrageService;
import ma.bservices.tgcc.Entity.DemandeDetail;
import ma.bservices.tgcc.Entity.DemandeEntete;
import ma.bservices.tgcc.Entity.Engin;
import ma.bservices.tgcc.authentification.Authentification;
import ma.bservices.tgcc.service.Engin.EnginService;
import org.apache.commons.io.FilenameUtils;
import org.primefaces.model.UploadedFile;
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
public class DetailDemandeMb implements Serializable {

    @ManagedProperty(value = "#{demandeService}")
    private DemandeService demandeService;

    @ManagedProperty(value = "#{enginService}")
    private EnginService enginService;

    @ManagedProperty(value = "#{parametrageService}")
    private ParametrageService parametrageService;
    
    @ManagedProperty(value = "#{chantieService}")
    private ChantierService chantieService;

    @ManagedProperty(value = "#{administrationService}")
    private AdministrationService administrationService;
    
    private DemandeEntete DA;
    private String valueButton;
    private Boolean disable;
    private Boolean disable1;
    private Boolean disableadd;
    private Boolean disableDelete;
    private List<DemandeDetail> listArticle;
    private List<Engin> listEngin = new ArrayList<Engin>();
    private List<Article> listeArticle = new ArrayList<>();
    private DemandeDetail ADA = new DemandeDetail();
    private Float quantite;
    private Float longeur;
    private Float largeur;
    private Float hauteur;
    private String unite;
    private String observation;
    private String autre;
    private Date dateSouhaite;
    private Date dateMax;
    private Article arti = new Article();
    private String codeArt = null;
    private Engin engin = new Engin();
    private Integer enginId = null;
    private List<FamilleArticle> fam1;
    private List<FamilleArticle> fam2;
    private List<FamilleArticle> fam3;
    private String valueFam1;
    private String valueFam2;
    private String valueFam3;
    private String des;
    private String reference;
    private Integer idChantier, idAtelier;
    private Long idDA;
    private String designation;
    private Float quantitePrepare;
    private Float quantiteArticle;
    private Float quantiteArt;
    private UploadedFile uploadedFileQuitance;
    private String codee;
    private String dese;
    private Boolean disableIntervenant;
    private Boolean disableDemandeur;
    private Permission persmission;
    private Permission persmission1;
     private boolean btnEtat;
    @PostConstruct
    public void init() {

        int id = -1;
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        Map<String, String> requestParameters = externalContext.getRequestParameterMap();
        

        

        
        if (requestParameters.containsKey("id")) {
            id = Integer.valueOf(requestParameters.get("id"));

        } else {
//            throw new WebServiceException("No item id in request parameters");
        }
        //listEngin = demandeService.getListEngin();
        DA = demandeService.getDemandeEntete(id);
        if (DA != null) {
            listArticle = demandeService.DemandeDetail(DA.getId());
            idChantier = DA.getChantier().getId();
            idAtelier = DA.getAtelier().getId();
            //System.out.println("idChantier " + idChantier);
            if (DA.getEtatDA().getId() == 2) {
                disable = true;
                disable1 = false;
                valueButton = "Déjà envoyé";
                disableDelete = true;
                disableadd = false;
            }
            if (DA.getEtatDA().getId() != 1 && DA.getEtatDA().getId() != 2) {
                disable = true;
                disable1 = true;
                valueButton = "Déjà envoyé";
                disableDelete = true;
                disableadd = false;
            }
            if (DA.getEtatDA().getEtat().equals("Brouillon")) {
                disable = false;
                disable1 = true;
                valueButton = "Envoyé";
                disableDelete = false;
                disableadd = true;
            }


        } else {
            Module.message(3, "Demande  n'existe pas", "");
            disable = true;
            disableDelete = true;
            DA = new DemandeEntete();
        }

        demandeService = Module.ctx.getBean(DemandeService.class);
        parametrageService = Module.ctx.getBean(ParametrageService.class);
        chantieService = Module.ctx.getBean(ChantierService.class);
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        EtatDAMb etatDAMb = (EtatDAMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "etatDAMb");
        fam1 = etatDAMb.getFam1();
        fam2 = etatDAMb.getFam2();
        fam3 = etatDAMb.getFam3();
        
        Authentification authentification = (Authentification) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "authentification");
        Utilisateur User = authentification.get_connected_user();
        //System.out.println("test0000001");
        persmission = administrationService.getPermissionByName("intervenant");
        
        Boolean bool = administrationService.hasPermission(User, persmission);
        
 
        persmission1 = administrationService.getPermissionByName("admin");
        Boolean bool1 = administrationService.hasPermission(User, persmission1);
        
        if (bool==true)
        {
            disableIntervenant=false;
            disableDemandeur=true;
        }else
        {
            disableIntervenant=true;
            disableDemandeur=false;
        }
        if (bool1==true)
        {
           disableIntervenant=false;
           disableDemandeur=true;
        }

    }

    public boolean qteValide() {
        //System.out.println("test qte");
        return !(quantite != null && quantite > 0.0);
    }

    public void recuperationArt(DemandeDetail d) {
         if (DA.getEtatDA().getEtat().equals("Brouillon")==true || DA.getEtatDA().getEtat().equals("Envoyée")==true) {        
            viderArtcileDialog();
            Module.message(2, "Vous Devez Accepté le demande avant de la Traiter ", "");
            return;
        }
        if (DA.getEtatDA().getEtat().equals("Préparée")) {        
            viderArtcileDialog();
            Module.message(2, "La ligne demande est déja préparée ", "");
            return;
        }
        try {
             EtatDA objetEtatDA = new EtatDA();
             objetEtatDA = new EtatDA();
             objetEtatDA.setEtat("KIT En-cours");
             objetEtatDA.setId(9);
             d.setEtatDA(objetEtatDA);
             demandeService.updateDemandeDetail(d);
                
            viderArtcileDialog();
            designation = null;
            System.out.println("d.getDesignationDemandeur()"+d.getDesignationDemandeur());
            designation = d.getDesignationDemandeur();
            System.out.println("d.designation()"+ designation);
            dateSouhaite = null;
            dateSouhaite = d.getDateSouhaite();
            dateMax = null;
            dateMax = d.getDateMaxUtilisation();
            //quantiteArt=null;
            //quantiteArt=d.getQte();
        } catch (Exception e) {
            System.out.println("ma.bservices.mb.demande.DetailDemandeMb.recuperationArt()" + e.getCause());
        }
    }

    public void recuperationArtDeatil(DemandeDetail d) {
         //System.out.println("test010011010");
             //System.out.println(DA.getEtatDA().getEtat());
        if (DA.getEtatDA().getEtat().equals("Brouillon")==true || DA.getEtatDA().getEtat().equals("Envoyée")==true) {        
            viderArtcileDialog();
            Module.message(2, "Vous Devez Accepté le demande avant de la Traiter ", "");
            return;
        }
        if (DA.getEtatDA().getEtat().equals("Préparée")) {        
            viderArtcileDialog();
            Module.message(2, "La ligne demande est déja préparée ", "");
            return;
        }
        
        try {
            viderArtcileDialog();
            //System.out.println("recuperationArtDeatil");
            designation = d.getDesignationDemandeur();
            //System.out.println("recuperationArtDeatil" + d.getQte());
            quantite = d.getQte();
            largeur = d.getLargeur();
            longeur = d.getLongeur();
            hauteur = d.getHauteur();
            autre = d.getAutre();
            dateMax = d.getDateMaxUtilisation();
            dateSouhaite = d.getDateSouhaite();
            observation = d.getObservation();
            quantitePrepare = d.getQuantitePrepare();
            quantiteArticle = d.getQuantiteArticle();
            if (d.getArtcile() != null && d.getArtcile().getId() != null){
            arti = d.getArtcile();
            }
            idDA = d.getId();
            if (d.getEngin() != null && d.getEngin().getIDEngin() != null) {
                engin = d.getEngin();
                enginId = engin.getIDEngin();
            }
        } catch (Exception e) {
            System.out.println("ma.bservices.mb.demande.DetailDemandeMb.recuperationArt()" + e.getCause());
        }
    }

    public void refuseArt(DemandeDetail d) {
           if (d.getEtatDA().getEtat().equals("En-cours")==false) {        
            viderArtcileDialog();
            Module.message(2, "Opération non autorisée ", "");
            return;
        }
        try {
            if (d.getEtatDA().getEtat().equals("En-cours") == true || d.getEtatDA().getEtat().equals("Envoyée") == true) {
                //System.out.println(" test Refusée" + d.getId());
                EtatDA objetEtatDA = new EtatDA();
                objetEtatDA = new EtatDA();
                objetEtatDA.setEtat("Refusée");
                objetEtatDA.setId(4);
                d.setEtatDA(objetEtatDA);
                demandeService.updateDemandeDetail(d);
                listArticle = demandeService.getListDemnadeDetailbyIdEntete(d.getDemandeEntete().getId());
                disableadd = true;
                 disable1 = true;
            }
        } catch (Exception e) {
            System.out.println("ma.bservices.mb.demande.DetailDemandeMb.refuseArt()" + e.getMessage());
        }
    }

    public void accepteArt(DemandeDetail d) {
                  if (d.getEtatDA().getEtat().equals("Refusée")==false) {        
            viderArtcileDialog();
            Module.message(2, "Opération non autorisée ", "");
            return;
        }
        try {
            if (d.getEtatDA().getEtat().equals("Refusée") == true) {
                //System.out.println(" test En-cours" + d.getId());
                EtatDA objetEtatDA = new EtatDA();
                objetEtatDA = new EtatDA();
                objetEtatDA.setEtat("En-cours");
                objetEtatDA.setId(5);
                d.setEtatDA(objetEtatDA);
                demandeService.updateDemandeDetail(d);
                listArticle = demandeService.getListDemnadeDetailbyIdEntete(d.getDemandeEntete().getId());
                disableadd = true;
                disable1 = true;
            }
        } catch (Exception e) {
            System.out.println("ma.bservices.mb.demande.DetailDemandeMb.refuseArt()" + e.getMessage());
        }
    }

    public void deleteArticleDA() {
        if ((Long) demandeService.nombreArticlesDemandeInterne(DA.getId()) == 1) {
            demandeService.deleteDemandeDetail(ADA);
            demandeService.deleteDemandeEntete(DA);
           /* try {
                ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
                ec.redirect(ec.getRequestContextPath() + "/evol/dmdAchat.xhtml");
            } catch (IOException ex) {
                System.out.println("redirecton erreur " + ex.getMessage());
            }
            */
        } else {
            demandeService.deleteDemandeDetail(ADA);
            listArticle = demandeService.getListDemnadeDetailbyIdEntete(DA.getId());
        }
        Module.message(0, "Suppression réussie", "");

    }

    public void ajouterNouvelleDA() {

        if (DA.getEtatDA().getEtat().equals("Brouillon")==true || DA.getEtatDA().getEtat().equals("Envoyée")==true) {        
            viderArtcileDialog();
            Module.message(2, "Vous Devez Accepté le demande avant de la Traiter ", "");
            return;
        }
        if (DA.getEtatDA().getEtat().equals("Préparée")) {        
            viderArtcileDialog();
            Module.message(2, "La ligne demande est déja préparée ", "");
            return;
        }
        if (quantitePrepare == null) {
                quantitePrepare = (float) 0.0;
            }
            if (quantiteArticle == null) {
                quantiteArticle = (float) 0.0;
            }
            if (quantiteArticle < quantitePrepare) {
                Module.message(2, "la quantite preparée est superiere à la quantité article", "");
                viderArtcileDialog();
                return;
            }
        if (quantiteArticle ==(float)0.0) {
                Module.message(2, "Vous devez saisir la quantité article", "");
                //viderArtcileDialog();
                return;
            }
        if (DA.getChantier().getId() != 0 && DA.getDateLivraisonSouhaitee() != null) {
            if (arti.getCodeArticle() != null && arti.getCodeArticle() != "") {
                if (demandeService.articleDADejaExiste(arti.getCodeArticle(), DA.getId())) {
                    //System.out.println("id article deja ajouté : " + arti.getCodeArticle());
                    Module.message(2, "Vous avez déjà ajouté cet Article", "");
                    viderArtcileDialog();
                    return;

                }
            }
            DemandeDetail articleDA = new DemandeDetail();
            if (arti.getId() != null) {
                //System.out.println("id article: " + arti.getId());
                articleDA.setArtcile(arti);
                articleDA.setRefArticle(arti.getCodeArticle());
            }
            
            if (engin.getiDEngin() != null) {       
                articleDA.setEngin(engin);
            }
            articleDA.setDemandeEntete(DA);
            articleDA.setDesignationDemandeur(designation);
            if (quantiteArt != null) {
                articleDA.setQte(quantiteArt);
            }
            if (quantiteArticle != null) {
                articleDA.setQuantiteArticle(quantiteArticle);
            }
            if (quantitePrepare != null) {
                articleDA.setQuantitePrepare(quantitePrepare);
            }
            articleDA.setObservation(observation);
            EtatDA objetEtatDA = new EtatDA();
            objetEtatDA = new EtatDA();
            objetEtatDA.setEtat("En-cours");
            objetEtatDA.setId(5);
            articleDA.setEtatDA(objetEtatDA);
            if (quantitePrepare != null && quantiteArticle != null) {
                if (quantiteArticle > quantitePrepare && quantitePrepare > 0.0) {
                    objetEtatDA = new EtatDA();
                    objetEtatDA = new EtatDA();
                    objetEtatDA.setEtat("Préparée partialement");
                    objetEtatDA.setId(6);
                    articleDA.setEtatDA(objetEtatDA);
                }
                if (quantitePrepare >= quantiteArticle) {
                    objetEtatDA = new EtatDA();
                    objetEtatDA = new EtatDA();
                    objetEtatDA.setEtat("Préparée");
                    objetEtatDA.setId(7);
                    articleDA.setEtatDA(objetEtatDA);
                }
            }

            long idADA = demandeService.addDemandeDetail(articleDA);
            listArticle.add(demandeService.getDemandeDetail(idADA));
            
            Module.message(0, "Article Ajoutée", "");
            viderArtcileDialog();
        } else {
            Module.message(2, "veuillez saisir la date laivraison souhaitée", "");
            viderArtcileDialog();
        }
    }

        public void ajouterNouvelleDA1() throws IOException {
        if (qteValide()) {
            Module.message(2, "Veuillez saisir une quantité valide", "");
            return;
        }
          
        if (DA.getChantier() != null  && DA.getDateLivraisonSouhaitee() != null) {
            
            if (DA.getId() == null) {
                
                
                Chantier objetChantier = new Chantier();
                objetChantier.setId(idChantier);
                Chantier objetAtelier = new Chantier();
                objetAtelier.setId(idAtelier);
                
                EtatDA objetEtatDA = new EtatDA();
                objetEtatDA.setId(1);
                ELContext elContext = FacesContext.getCurrentInstance().getELContext();
                Authentification authentification = (Authentification) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "authentification");

                DA.setChantier(objetChantier);
                DA.setAtelier(objetAtelier);
                DA.setEtatDA(objetEtatDA);
                DA.setDateAjout(new Date());
                DA.setDemandeur(authentification.get_connected_user());
                //DA.setComantaire(objetChantier.getCodeNovapaie()+ " . " + comantaire);
                idDA = demandeService.addDemandeEntete(DA);
                DA.setId(idDA);
                demandeService.updateDemandeEntete(DA);
            }else
            {
                idDA=DA.getId();
            }
             //System.out.println("test hhhhh01: " + idDA);
             
            if ("".equals(arti.getCodeArticle().trim())!=true && arti.getCodeArticle() !=null )
            {
                    //System.out.println("test hhhhh02: " + idDA);
                   // System.out.println("test hhhhh03: " +  arti.getCodeArticle());
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
                        demandeDetail.setDemandeEntete(DA);
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
                     
                        demandeDetail.setDateSouhaite(DA.getDateLivraisonSouhaitee());
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
    public void listenerTest() {
        try {
            //   System.out.println("enginId >>>>>>>>>>>>>>>>>>>>>" + enginId);
        } catch (Exception e) {
        }
    }

    public void modifierNouvelleDA() {
        
        if (DA.getEtatDA().getEtat().equals("Brouillon")==true || DA.getEtatDA().getEtat().equals("Envoyée")==true) {        
            viderArtcileDialog();
            Module.message(2, "Vous Devez Accepté le demande avant de la Traiter ", "");
            return;
        }
        if (DA.getEtatDA().getEtat().equals("Préparée")) {        
            viderArtcileDialog();
            Module.message(2, "La ligne demande est déja préparée ", "");
            return;
        }
        
        //System.out.println("getChantier >>>>>>>>>>>>>>>>>>>>>" + DA.getChantier().getId());
        //System.out.println("DA.getDateLivraisonSouhaitee() >>>>>>>>>>>>>>>>>>>>>" + DA.getDateLivraisonSouhaitee());
        if (DA.getChantier().getId() != 0 && DA.getDateLivraisonSouhaitee() != null) {

            //System.out.println("DA.loop1() IN >>>>>>>>>>>>>>>>>>>>>");
            if (quantitePrepare == null) {
                quantitePrepare = (float) 0.0;
            }
            if (quantiteArticle == null) {
                quantiteArticle = (float) 0.0;
            }
            if (quantiteArticle < quantitePrepare) {
                Module.message(2, "la quantite preparée est superiere à la quantité article", "");
                viderArtcileDialog();
                return;
            }
            if (quantiteArticle ==(float)0.0) {
                Module.message(2, "Vous devez saisir la quantité article", "");
                //viderArtcileDialog();
                return;
            }
            //System.out.println("enginId >>>>>>>>>>>>>>>>>>>>>" + enginId);
            DemandeDetail articleDA = new DemandeDetail();
            articleDA = demandeService.getDemandeDetail(idDA);
            //System.out.println("id detail: " + idDA);
            //System.out.println("getCodeArticle >>>>>>>>>>>>>>>>>>>>>" + codeArt);
            if (arti.getId() != null)
            {            
                if (arti.getId() != null ) {               
                    articleDA.setArtcile(arti);
                    articleDA.setRefArticle(arti.getCodeArticle());           
                }
            }
            if (engin.getiDEngin() != null) {       
                articleDA.setEngin(engin);
            }

            articleDA.setDemandeEntete(DA);
            articleDA.setDesignationDemandeur(designation);

            if (quantiteArticle > quantitePrepare && quantitePrepare > 0.0) {
                EtatDA objetEtatDA = new EtatDA();
                objetEtatDA = new EtatDA();
                objetEtatDA.setEtat("Préparée partialement");
                objetEtatDA.setId(6);
                articleDA.setEtatDA(objetEtatDA);
            }
            if (quantitePrepare >= quantiteArticle) {
                EtatDA objetEtatDA = new EtatDA();
                objetEtatDA = new EtatDA();
                objetEtatDA.setEtat("Préparée");
                objetEtatDA.setId(7);
                articleDA.setEtatDA(objetEtatDA);
            }
            if (quantiteArt != null) {
                articleDA.setQte(quantiteArt);
            }
            if (quantiteArticle != null) {
                articleDA.setQuantiteArticle(quantiteArticle);
            }
            if (quantitePrepare != null) {
                articleDA.setQuantitePrepare(quantitePrepare);
            }
            articleDA.setObservation(observation);
            if (dateMax != null) {
                articleDA.setDateMaxUtilisation(dateMax);
            }

            long idADA = demandeService.updateDemandeDetail(articleDA);
            listArticle = demandeService.DemandeDetail(articleDA.getDemandeEntete().getId());
            Module.message(0, "Article Modifié", "");
            boolean testEtat=false;
            for (int i = 0; i < listArticle.size(); i++) {
                if ((listArticle.get(i).getArtcile() != null || listArticle.get(i).getEngin()!= null)&& listArticle.get(i).getEtatDA().getEtat().equals("Refusée") == false) {
                    if (listArticle.get(i).getQuantitePrepare() != null) {
                        if (listArticle.get(i).getQuantitePrepare() >= 0.0) {
                            //System.out.println("yyyyy01"+ DA.getEtatDA().getEtat());  
                            //System.out.println("yyyyy01"+ listArticle.get(i).getId());
                            if (listArticle.get(i).getQuantitePrepare() < listArticle.get(i).getQuantiteArticle()) {
                                 //System.out.println("yyyyy02"+ DA.getEtatDA().getEtat());    
                                 //System.out.println("yyyyy02"+ listArticle.get(i).getId()); 
                                EtatDA objetEtatDA = new EtatDA();
                                objetEtatDA = new EtatDA();
                                objetEtatDA.setEtat("Préparée partialement");
                                objetEtatDA.setId(6);
                                DA = demandeService.getDemandeEntete(listArticle.get(i).getDemandeEntete().getId());
                                DA.setEtatDA(objetEtatDA);
                                demandeService.updateDemandeEntete(DA);
                               
                                break;
                            } else {
                                EtatDA objetEtatDA = new EtatDA();
                                objetEtatDA = new EtatDA();
                                objetEtatDA.setEtat("Préparée");
                                objetEtatDA.setId(7);
                                DA.setEtatDA(objetEtatDA);
                                DA = demandeService.getDemandeEntete(listArticle.get(i).getDemandeEntete().getId());
                                DA.setEtatDA(objetEtatDA);
                                demandeService.updateDemandeEntete(DA);
                                testEtat=true;
                            }
                        }
                    }
                }
            }
             // System.out.println("xxxxxx01"+ DA.getEtatDA().getEtat());         
            for (int i = 0; i < listArticle.size(); i++) {
               //  System.out.println("xxxxxx02"+ DA.getEtatDA().getEtat());   
               //  System.out.println(listArticle.get(i).toString());
                  if (listArticle.get(i).getArtcile() == null && listArticle.get(i).getEngin()== null && listArticle.get(i).getEtatDA().getEtat().equals("En-cours") == true     && (listArticle.get(i).getQuantiteArticle() == null || listArticle.get(i).getQuantiteArticle() ==0.0 )&& listArticle.get(i).getQte() != null &&  testEtat==true) {                   
                         //System.out.println("xxxxxx04"+ DA.getEtatDA().getEtat());   
                         //System.out.println("xxxxxx04"+ listArticle.get(i).getId()); 
                         //System.out.println("listArticle.get(i).getArtcile()"  + listArticle.get(i).getArtcile());
                         //System.out.println("listArticle.get(i).getEngin()" + listArticle.get(i).getEngin());
                         //System.out.println("listArticle.get(i).getEtatDA().getEtat()" + listArticle.get(i).getEtatDA().getEtat());
                         //System.out.println("listArticle.get(i).getQuantiteArticle()"+listArticle.get(i).getQuantiteArticle());
                         //System.out.println("listArticle.get(i).getQuantiteArticle()"+listArticle.get(i).getQuantiteArticle());
                         //System.out.println("listArticle.get(i).getQte()"+listArticle.get(i).getQte());
                         //System.out.println("testEtat==true"+testEtat);
                                EtatDA objetEtatDA = new EtatDA();
                                objetEtatDA = new EtatDA();
                                objetEtatDA.setEtat("Préparée partialement");
                                objetEtatDA.setId(6);
                                DA = demandeService.getDemandeEntete(listArticle.get(i).getDemandeEntete().getId());
                                DA.setEtatDA(objetEtatDA);
                                demandeService.updateDemandeEntete(DA);
                                break;
                            }
                }
            
        //    System.out.println("test01011fdffd"+ DA.getEtatDA().getEtat());
            if (DA.getEtatDA().getEtat().equals("Préparée")) {
                for (int i = 0; i < listArticle.size(); i++) {
                    //System.out.println(listArticle.get(i).toString());
                    
                    if (listArticle.get(i).getArtcile() == null && listArticle.get(i).getEngin()== null && listArticle.get(i).getEtatDA().getEtat().equals("Refusée") == false     && (listArticle.get(i).getQuantiteArticle() == null || listArticle.get(i).getQuantiteArticle() ==0.0 )&& listArticle.get(i).getQte() != null) {
                        if (listArticle.get(i).getQte() != 0.0)
                        {
                        EtatDA objetEtatDA = new EtatDA();
                        objetEtatDA = new EtatDA();
                        objetEtatDA.setEtat("KIT");
                        objetEtatDA.setId(8);
                        articleDA = demandeService.getDemandeDetail(listArticle.get(i).getId());
                        articleDA.setEtatDA(objetEtatDA);
                        demandeService.updateDemandeDetail(articleDA);

                    }
                    }
                }
            }
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
        valueFam1 = valueFam1.substring(1, 4);
        fam2 = (List<FamilleArticle>) parametrageService.listeFamilles(0, 10, 1, 5, valueFam1, reqDos);
        fam3 = (List<FamilleArticle>) parametrageService.listeFamilles(0, 10, 1, 8, valueFam1, reqDos);

        //System.out.println("********* Size Fam 2 " + fam2.size());
        //System.out.println("********* Size Fam 3 " + fam3.size());
    }

    public void fam3ByFam2() {
        String reqDos = " dos=700 ";
        valueFam2 = valueFam2.substring(1, 6);
        fam3 = (List<FamilleArticle>) parametrageService.listeFamilles(0, 10, 1, 8, valueFam2, reqDos);
    }

    public void articleByFam() {
        if (DA.getChantier().getId() != 0 || DA.getDateLivraisonSouhaitee() != null) {
            //System.out.println("!!!!!!!!!!!!!!!!! ENTREE ??????????????????? ");
            String reqDos = " dos=700  ";
            int limit = Integer.parseInt(demandeService.nombreRechercheArticle("", valueFam1, valueFam2, valueFam3.trim(), reqDos, des).toString());
            listeArticle = demandeService.listeRechercheArticle(0, limit, reference, valueFam1, valueFam2, valueFam3.trim(), reqDos, des);
            //System.out.println("************** SIZE ARTICLE ************ " + listeArticle.size() + " @@@@@@@@@@@ " + limit + " FAM1 " + valueFam1.trim() + " FAM2 " + valueFam2.trim() + " FAM3 " + valueFam3.trim());
        } else {
            Module.message(2, "Attention l'un des Champs (Chantier ou Date de livraison souhaitée) est vide", "Attention l'un des Champs est vide");
        }
    }

    public void enginByFam() {
        if (DA.getChantier().getId() != 0 || DA.getDateLivraisonSouhaitee() != null) {
            //System.out.println("!!!!!!!!!!!!!!!!! ENTREE ??????????????????? ");
             //System.out.println(codee);
            int limit = Integer.parseInt(demandeService.nombreRechercheEngin(codee, dese).toString());
            //System.out.println("nombre engin : "+limit);
            listEngin = demandeService.listeRechercheEngin(0, limit, codee, dese);
           
            
           //System.out.println("listEngin : "+listEngin.size());
           //System.out.println("listEngin : test");
            //System.out.println("listEngin : "+listEngin.get(0).toString());
               //System.out.println("listEngin : "+listEngin.get(0).getCode());
               //System.out.println("listEngin : "+listEngin.get(0).getDesignation());
           
        } else {
            Module.message(2, "Attention l'un des Champs (Chantier ou Date de livraison souhaitée) est vide", "Attention l'un des Champs est vide");
        }
    }
    
    public void findEngin() {
        try {
            
            String dos = Constantes.getInstance().numeroDossierDivalto;
            if (engin != null) {
               
                if (engin.getCode() != null && engin.getCode().trim().equals("") != true) {
                   
                    Engin objetEngin = parametrageService.getEnginByCode(engin.getCode());
                    if (objetEngin.getCode() == null) {
                        Module.message(2, "Erreur, Engin n'existe pas ou Réference incomplète", "");
                        return;
                    }
                    
                    engin = objetEngin;
                    //System.out.print("IN arti.getCodeArticle() " + engin.getCode());
                } else {
                    engin = new Engin();
                    
                }
            }
        } catch (Exception e) {
            engin = new Engin();
            e.printStackTrace();
        }
    }
        
    public void findArticle() {
        try {
            String dos = Constantes.getInstance().numeroDossierDivalto;
            if (arti != null) {
                if (arti.getCodeArticle() != null && arti.getCodeArticle().trim().equals("") != true) {
                    // if(codeArt!=null&&codeArt.trim().equals("")!=true){
                    Article objetArticle = parametrageService.getArticleByCode(arti.getCodeArticle(), dos);
                    if (objetArticle.getCodeArticle() == null) {
                        Module.message(2, "Erreur, Article n'existe pas ou Réference incomplète", "");
                        return;
                    }
                    //System.out.print("IN codeArt " + codeArt);
                    arti = objetArticle;
                    //System.out.print("IN arti.getCodeArticle() " + arti.getCodeArticle());
                } else {
                    arti = new Article();
                    System.out.println("ARTICLE VIDE");
                }
            }
        } catch (Exception e) {
            arti = new Article();
            e.printStackTrace();
        }
    }

    public void downloalImage1(String path1) throws IOException {
        try {
            File file = new File(path1);

            FacesContext facesContext = null;
            HttpServletResponse response;
            response = null;

            OutputStream responseOutputStream = null;
            InputStream fileInputStream = null;
            //System.out.println("IN ");
            //System.out.println(file.getName());
            //System.out.println(file.getPath());
            //File file = new File(reclamationToShow.getImage_rec()+reclamationToShow.getId()+".jpg");
            //System.out.println(file.getName());
            //System.out.println(file.getPath());
            if (file.exists()) {

                facesContext = FacesContext.getCurrentInstance();
                response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
                response.reset();
                response.setHeader("Content-Type", "application/octet-stream");
                response.setHeader("Content-Disposition", "attachment;filename=" + file.getName());
                responseOutputStream = response.getOutputStream();
                fileInputStream = new FileInputStream(file);
                byte[] bytesBuffer = new byte[2048];
                int bytesRead;
                while ((bytesRead = fileInputStream.read(bytesBuffer)) > 0) {
                    responseOutputStream.write(bytesBuffer, 0, bytesRead);
                }
                responseOutputStream.flush();
                fileInputStream.close();
                responseOutputStream.close();
                facesContext.responseComplete();

            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "", "Fichier inexistant!! Merci de contacter votre administrateur!"));
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Une erreur s'est produite merci de contacter l'administrateur!!"));
        }
    }

    public void accepteDA() {

     
        //System.out.println("test 1 : ");
        EtatDA objetEtatDA = new EtatDA();
        objetEtatDA.setEtat("En-cours");
        objetEtatDA.setId(5);
        DA.setEtatDA(objetEtatDA);
        //System.out.println("test 2 : ");
        demandeService.updateDemandeEntete(DA);
        idDA = DA.getId();
        //System.out.println("test 4 : " + idDA);
        listArticle = demandeService.getListDemnadeDetailbyIdEntete(idDA);
        for (int i = 0; i < listArticle.size(); i++) {
            ADA = new DemandeDetail();
            ADA = listArticle.get(i);
            objetEtatDA = new EtatDA();
            objetEtatDA.setEtat("En-cours");
            objetEtatDA.setId(5);
            ADA.setEtatDA(objetEtatDA);
            demandeService.updateDemandeDetail(ADA);
        }
        disableadd = false;
        disable1= true;
    }

    public void envoyerDA() {

        if (!Module.checkDate(new Date(), null, DA.getDateLivraisonSouhaitee())) {
            Module.message(2, "date livraison doit être supérieur a la date d'aujourd'hui", "");
            return;
        }
        double g = demandeService.updateDemandeEntete(DA);
        //System.out.println("demandeService.updateDemandeEntete(DA) ID ::::: " + g);
        idDA = DA.getId();
        DemandeEntete newDa = demandeService.getDemandeEntete(idDA);
        if (idDA != 0) {
            Chantier objetChantier = chantieService.getChantier(idChantier);
            Chantier objetAtelier = chantieService.getChantier(idAtelier);
            Date objetDateLivraisonSouhaiteeDA = null;

            if (DA.getDateLivraisonSouhaitee() != null) {
                objetDateLivraisonSouhaiteeDA = DA.getDateLivraisonSouhaitee();
            }
            EtatDA objetEtatDA = new EtatDA();
            objetEtatDA.setId(2);
            objetEtatDA.setEtat("Envoyée");
            ELContext elContext = FacesContext.getCurrentInstance().getELContext();
            Authentification authentification = (Authentification) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "authentification");
            Date date = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            int nbrD = demandeService.nombreDemandeInternebyDemandeur(newDa.getDemandeur().getId(), newDa.getDateAjout());
            newDa.setNumDemande("DI" + calendar.get(Calendar.MONTH) + calendar.get(Calendar.YEAR) + "-" + nbrD + "-" + idDA);
            newDa.setChantier(objetChantier);
            newDa.setAtelier(objetAtelier);
            newDa.setDateAjout(new Date());
            newDa.setDateLivraisonSouhaitee(objetDateLivraisonSouhaiteeDA);
            newDa.setDemandeur(authentification.get_connected_user());
            newDa.setComantaire(DA.getChantier().getCode() + " . " + DA.getComantaire());
            newDa.setEtatDA(objetEtatDA);
            demandeService.updateDemandeEntete(newDa);

            idDA = newDa.getId();
            listArticle = demandeService.getListDemnadeDetailbyIdEntete(idDA);
            for (int i = 0; i < listArticle.size(); i++) {
                ADA = new DemandeDetail();
                ADA = listArticle.get(i);
                objetEtatDA = new EtatDA();
                objetEtatDA.setId(2);
                objetEtatDA.setEtat("Envoyée");
                ADA.setEtatDA(objetEtatDA);
                demandeService.updateDemandeDetail(ADA);

            }
        } else {
            Module.message(1, "Demande Interne n'existe pas", "");
        }

        DA = demandeService.getDemandeEntete(idDA);
        listArticle = demandeService.DemandeDetail(idDA);
        //System.out.println("idDA " + idDA);
        if (DA.getEtatDA().getId() == 2) {
            disable = true;
            disable1 = false;
            valueButton = "Déjà envoyé";
            disableDelete = true;
            disableadd = false;
        }
        if (DA.getEtatDA().getId() != 1 && DA.getEtatDA().getId() != 2) {
            disable = true;
            disable1 = true;
            valueButton = "Déjà envoyé";
            disableDelete = true;
             disableadd = false;
        }
        if (DA.getEtatDA().getEtat().equals("Brouillon")) {
            disable = false;
            disable1 = true;
            valueButton = "Envoyé";
            disableDelete = false;
            disableadd = true;
        }
    }

    public void refuseDA() {

        EtatDA objetEtatDA = new EtatDA();
        objetEtatDA.setEtat("Refusée");
        objetEtatDA.setId(4);
        DA.setEtatDA(objetEtatDA);
        demandeService.updateDemandeEntete(DA);
        idDA = DA.getId();
        listArticle = demandeService.getListDemnadeDetailbyIdEntete(idDA);
        for (int i = 0; i < listArticle.size(); i++) {
            ADA = new DemandeDetail();
            ADA = listArticle.get(i);
            objetEtatDA = new EtatDA();
            objetEtatDA.setEtat("Refusée");
            objetEtatDA.setId(4);
            ADA.setEtatDA(objetEtatDA);
            demandeService.updateDemandeDetail(ADA);
        }
    }

    public void viderArtcileDialog() {
        //System.out.println("vider les champs ");
        arti = null;
        arti = new Article();
        //System.out.println("vider les champs " + arti.getCodeArticle());
        this.designation = null;
        this.quantite = null;
        this.largeur = null;
        this.longeur = null;
        this.hauteur = null;
        this.autre = null;
        this.dateMax = null;
        this.uploadedFileQuitance = null;
        enginId = null;
        engin = null;
        engin = new Engin();
        this.dateSouhaite = null;
        this.quantiteArticle = null;
        this.quantitePrepare = null;

    }

    public DemandeService getDemandeService() {
        return demandeService;
    }

    public void setDemandeService(DemandeService demandeService) {
        this.demandeService = demandeService;
    }

    public ParametrageService getParametrageService() {
        return parametrageService;
    }

    public void setParametrageService(ParametrageService parametrageService) {
        this.parametrageService = parametrageService;
    }

    public ChantierService getChantieService() {
        return chantieService;
    }

    public void setChantieService(ChantierService chantieService) {
        this.chantieService = chantieService;
    }

    public DemandeEntete getDA() {
        return DA;
    }

    public void setDA(DemandeEntete DA) {
        this.DA = DA;
    }

    public String getValueButton() {
        return valueButton;
    }

    public void setValueButton(String valueButton) {
        this.valueButton = valueButton;
    }

    public Boolean getDisable() {
        return disable;
    }

    public void setDisable(Boolean disable) {
        this.disable = disable;
    }

    public Boolean getDisableDelete() {
        return disableDelete;
    }

    public void setDisableDelete(Boolean disableDelete) {
        this.disableDelete = disableDelete;
    }

    public List<DemandeDetail> getListArticle() {
        return listArticle;
    }

    public void setListArticle(List<DemandeDetail> listArticle) {
        this.listArticle = listArticle;
    }

    public List<Article> getListeArticle() {
        return listeArticle;
    }

    public void setListeArticle(List<Article> listeArticle) {
        this.listeArticle = listeArticle;
    }

    public DemandeDetail getADA() {
        return ADA;
    }

    public void setADA(DemandeDetail ADA) {
        this.ADA = ADA;
    }

    public Float getQuantite() {
        return quantite;
    }

    public void setQuantite(Float quantite) {
        this.quantite = quantite;
    }

    public Float getLongeur() {
        return longeur;
    }

    public void setLongeur(Float longeur) {
        this.longeur = longeur;
    }

    public Float getLargeur() {
        return largeur;
    }

    public void setLargeur(Float largeur) {
        this.largeur = largeur;
    }

    public Float getHauteur() {
        return hauteur;
    }

    public void setHauteur(Float hauteur) {
        this.hauteur = hauteur;
    }

    public String getUnite() {
        return unite;
    }

    public void setUnite(String unite) {
        this.unite = unite;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public Boolean getDisableadd() {
        return disableadd;
    }

    public void setDisableadd(Boolean disableadd) {
        this.disableadd = disableadd;
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

    public void setDateSouhaite(Date dateSouhaite) {
        this.dateSouhaite = dateSouhaite;
    }

    public Article getArti() {
        return arti;
    }

    public void setArti(Article arti) {
        this.arti = arti;
    }

    public List<FamilleArticle> getFam1() {
        return fam1;
    }

    public void setFam1(List<FamilleArticle> fam1) {
        this.fam1 = fam1;
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

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Integer getIdChantier() {
        return idChantier;
    }

    public void setIdChantier(Integer idChantier) {
        this.idChantier = idChantier;
    }

    public Integer getIdAtelier() {
        return idAtelier;
    }

    public void setIdAtelier(Integer idAtelier) {
        this.idAtelier = idAtelier;
    }

    public long getIdDA() {
        return idDA;
    }

    public void setIdDA(long idDA) {
        this.idDA = idDA;
    }

    public Boolean getDisable1() {
        return disable1;
    }

    public void setDisable1(Boolean disable1) {
        this.disable1 = disable1;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public Float getQuantitePrepare() {
        return quantitePrepare;
    }

    public void setQuantitePrepare(Float quantitePrepare) {
        this.quantitePrepare = quantitePrepare;
    }

    public Float getQuantiteArticle() {
        return quantiteArticle;
    }

    public void setQuantiteArticle(Float quantiteArticle) {
        this.quantiteArticle = quantiteArticle;
    }

    public Float getQuantiteArt() {
        return quantiteArt;
    }

    public void setQuantiteArt(Float quantiteArt) {
        this.quantiteArt = quantiteArt;
    }

    public Boolean getDisable1add() {
        return disableadd;
    }

    public void setDisable1add(Boolean disableadd) {
        this.disableadd = disableadd;
    }

    public Date getDateMax() {
        return dateMax;
    }

    public void setDateMax(Date dateMax) {
        this.dateMax = dateMax;
    }

    public UploadedFile getUploadedFileQuitance() {
        return uploadedFileQuitance;
    }

    public void setUploadedFileQuitance(UploadedFile uploadedFileQuitance) {
        this.uploadedFileQuitance = uploadedFileQuitance;
    }

    public Engin getEngin() {
        return engin;
    }

    public void setEngin(Engin engin) {
        this.engin = engin;
    }

    public List<Engin> getListEngin() {
        return listEngin;
    }

    public void setListEngin(List<Engin> listEngin) {
        this.listEngin = listEngin;
    }

    public Integer getEnginId() {
        return enginId;
    }

    public void setEnginId(Integer enginId) {
        this.enginId = enginId;
    }

    public String getCodeArt() {
        return codeArt;
    }

    public void setCodeArt(String codeArt) {
        this.codeArt = codeArt;
    }

    public EnginService getEnginService() {
        return enginService;
    }

    public void setEnginService(EnginService enginService) {
        this.enginService = enginService;
    }

    public String getCodee() {
        return codee;
    }

    public void setCodee(String codee) {
        this.codee = codee;
    }

    public String getDese() {
        return dese;
    }

    public void setDese(String dese) {
        this.dese = dese;
    }

    public AdministrationService getAdministrationService() {
        return administrationService;
    }

    public void setAdministrationService(AdministrationService administrationService) {
        this.administrationService = administrationService;
    }

    public Boolean getDisableIntervenant() {
        return disableIntervenant;
    }

    public void setDisableIntervenant(Boolean disableIntervenant) {
        this.disableIntervenant = disableIntervenant;
    }

    public Boolean getDisableDemandeur() {
        return disableDemandeur;
    }

    public void setDisableDemandeur(Boolean disableDemandeur) {
        this.disableDemandeur = disableDemandeur;
    }

    public Permission getPersmission() {
        return persmission;
    }

    public void setPersmission(Permission persmission) {
        this.persmission = persmission;
    }

    public Permission getPersmission1() {
        return persmission1;
    }

    public void setPersmission1(Permission persmission1) {
        this.persmission1 = persmission1;
    }
    
    
    

}
