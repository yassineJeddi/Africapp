/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.mb.achat;

import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import ma.bservices.beans.Article;
import ma.bservices.beans.ArticleDA;
import ma.bservices.beans.Chantier;
import ma.bservices.beans.DemandeApprovisionnement;
import ma.bservices.beans.EtatDA;
import ma.bservices.constantes.Constantes;
import ma.bservices.mb.services.EtatDAMb;
import ma.bservices.mb.services.Module;
import ma.bservices.services.AchatService;
import ma.bservices.services.AdministrationService;
import ma.bservices.services.ChantierService;
import ma.bservices.services.FamilleArticle;
import ma.bservices.services.ParametrageService;
import ma.bservices.tgcc.authentification.Authentification;
import org.springframework.beans.BeansException;
import org.springframework.stereotype.Component;

/**
 *
 * @author Mahdi
 */
@Component
@ManagedBean
@ViewScoped
public class NouveauDaMb implements Serializable {

    @ManagedProperty(value = "#{achatService}")
    private AchatService achatService;
    
    @ManagedProperty(value = "#{parametrageService}")
    private ParametrageService parametrageService;

    @ManagedProperty(value = "#{chantierServiceEvol}")
    private ChantierService chantierService;

    @ManagedProperty(value = "#{administrationService}")
    private AdministrationService administrationService;

//    private List<DemandeApprovisionnement> demandes;
//    private Integer page, nbr;
//    private DemandeApprovisionnement DA;
    private Boolean disable;
    //   private String valueButton;
    private List<ArticleDA> listArticle = new ArrayList<>();
    //  private Boolean disableDelete;
    private ArticleDA ADA = new ArticleDA();
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
    private String demand;
    private Integer idDA;
    private Float quantite;
    private String commentaireArticle="";
    private boolean btnEtat;
    //private List<EtatDA> etatDA = new ArrayList<>();
    private DemandeApprovisionnement demandeApp = new DemandeApprovisionnement();

    // private String numeroDA;
    //private String chantier;
    private String reference = "";
    //private Date dateAjout;
    private Date dateLS;
    private boolean buttonDis = true;

    public String getCommentaireArticle() {
        return commentaireArticle;
    }

    public void setCommentaireArticle(String commentaireArticle) {
        this.commentaireArticle = commentaireArticle;
    }

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

    public Integer getIdDA() {
        return idDA;
    }

    public void setIdDA(Integer idDA) {
        this.idDA = idDA;
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

    public DemandeApprovisionnement getDemandeApp() {
        return demandeApp;
    }

    public void setDemandeApp(DemandeApprovisionnement demandeApp) {
        this.demandeApp = demandeApp;
    }

    public ArticleDA getADA() {
        return ADA;
    }

    public void setADA(ArticleDA ADA) {
        this.ADA = ADA;
    }

    public List<ArticleDA> getListArticle() {
        return listArticle;
    }

    public void setListArticle(List<ArticleDA> listArticle) {
        this.listArticle = listArticle;
    }

    public Boolean getDisable() {
        return disable;
    }

    public void setDisable(Boolean disable) {
        this.disable = disable;
    }

    public AchatService getAchatService() {
        return achatService;
    }

    public void setAchatService(AchatService achatService) {
        this.achatService = achatService;
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

//    private void checkDA() throws IOException {
//        if (DA == null) {
//            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
//            ec.redirect(ec.getRequestContextPath() + "/evol/dmdAchat.xhtml");
//        }
//    }
    @PostConstruct
    public void init() {
        try {
            System.out.println("HELLO FROM ACHAT");
            achatService = Module.ctx.getBean(AchatService.class);
            parametrageService = Module.ctx.getBean(ParametrageService.class);
            ELContext elContext = FacesContext.getCurrentInstance().getELContext();
            Authentification authentification = (Authentification) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "authentification");
            demand = authentification.get_connected_user().getLogin();
            try {
                if(authentification.get_connected_user().getMobile()!=null){
                    demand = demand+" - "+authentification.get_connected_user().getMobile();
                }
            } catch (Exception e) {
                System.out.println("Erreur de récuperation mobile car "+e.getMessage());
            }
            
            EtatDAMb etatDAMb = (EtatDAMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "etatDAMb");
            fam1 = etatDAMb.getFam1();
            fam2 = etatDAMb.getFam2();
            fam3 = etatDAMb.getFam3();
            btnEtat = listArticle.isEmpty();
            
            
           
        } catch (BeansException | NumberFormatException ex) {
            Logger.getLogger(NouveauDaMb.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deleteArticleDA() {

        achatService.supprimerArticleDA(ADA);
        listArticle = achatService.articlesDemandeApprovisionnement(idDA);
        btnEtat = listArticle.isEmpty();
        Module.message(0, "L'article DA est supprimé avec sucès", "L'article DA est supprimé avec sucès");
    }

    public boolean qteValide() {
        System.out.println("test qte");
        return !(quantite != null && quantite > 0.0);
    }

    /**
     * Partie Nouvelle Demande D'appro
     */
    public void ajouterNouvelleDA() {
        if (qteValide()) {
            Module.message(2, "Veuillez saisir une quantité valide", "");
            return;
        }
        if (idChantier != null && idChantier != 0 && demandeApp.getDateLivraisonSouhaitee() != null) {
            if (idDA == null) {
                Chantier objetChantier = new Chantier();
                objetChantier.setId(idChantier);

                EtatDA objetEtatDA = new EtatDA();
                objetEtatDA.setId(1);
                ELContext elContext = FacesContext.getCurrentInstance().getELContext();
                Authentification authentification = (Authentification) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "authentification");

                demandeApp.setChantier(objetChantier);
                demandeApp.setEtatDA(objetEtatDA);
                demandeApp.setDateAjout(new Date());
                demandeApp.setDemandeur(authentification.get_connected_user());

                idDA = achatService.ajouterDemandeAppro(demandeApp);

                demandeApp.setId(idDA);
                achatService.modifierDemandeAppro(demandeApp);
            }
            if (achatService.articleDADejaExiste(arti.getCodeArticle(), idDA)) {
                Module.message(2, "Code 2204 : Vous avez déjà ajouté cet Article", "");
                return;
            }
            ArticleDA articleDA = new ArticleDA();
            System.out.println("id article: " + arti.getId());

            articleDA.setArticle(arti);
            articleDA.setRefArticle(arti.getCodeArticle());
            articleDA.setDemandeApprovisionnement(demandeApp);
            articleDA.setQuantiteArticle(quantite); 
            articleDA.setCommentaire(commentaireArticle);
            Integer idADA = achatService.ajouterArticleDA(articleDA);
            listArticle.add(achatService.getArticleDA(idADA));
            btnEtat = listArticle.isEmpty();
            Module.message(0, "Article Ajoutée", "");
            arti = new Article();
            quantite = null;
            commentaireArticle="";
        } else {
            Module.message(2, "veuillez saisir la date laivraison souhaitée", "");
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
        
        //recherche des articles 
        
       // this.articleByFam();
       
        valueFam2 ="";
      valueFam3 = "";
      des ="";
      
      //System.out.println("********* REFERENCE: " + reference);
      //System.out.println("********* FAM1 " + valueFam1);
      //System.out.println("********* FAM2 " + valueFam2);
      //System.out.println("********* FAM3 " + valueFam3);
      //System.out.println("********* reqdos " + reqDos);
      //System.out.println("********* des " + des);
      
     articleByFam();
      
      
      //      listeArticle = achatService.listeRechercheArticle(0, 10, reference, valueFam1, valueFam2, valueFam3 != null ? valueFam3.trim() : null, reqDos, des);

              
        System.out.println("********* Size Fam 2 " + fam2.size());
        System.out.println("********* Size Fam 3 " + fam3.size());
    }

    public void fam3ByFam2() {
        String reqDos = " dos=700 ";
        //valueFam2 = valueFam2.substring(1, 6);
          //valueFam2 ="";
      valueFam3 = "";
      des ="";
        fam3 = (List<FamilleArticle>) parametrageService.listeFamilles(0, 10, 1, 8, valueFam2.substring(1, 6), reqDos);
       listeArticle = achatService.listeRechercheArticle(0, 10, reference, valueFam1, valueFam2, valueFam3 != null ? valueFam3.trim() : null, reqDos, des);
    }

    public void articleByFam() {
        if (idChantier != null && idChantier != 0 || demandeApp.getDateLivraisonSouhaitee() != null) {
            System.out.println("!!!!!!!!!!!!!!!!! ENTREE ??????????????????? ");
            String reqDos = " dos=700  ";
              System.out.println("********* REFERENCE: " + reference);
      //System.out.println("********* FAM1 " + valueFam1);
      //System.out.println("********* FAM2 " + valueFam2);
      //System.out.println("********* FAM3 " + valueFam3);
      //System.out.println("********* reqdos " + reqDos);
      //System.out.println("********* des " + des);
            
            int limit = Integer.parseInt(achatService.nombreRechercheArticle("", valueFam1, valueFam2, valueFam3 != null ? valueFam3.trim() : null, reqDos, des).toString());
            listeArticle = achatService.listeRechercheArticle(0, limit, reference, valueFam1, valueFam2, valueFam3 != null ? valueFam3.trim() : null, reqDos, des);
            //System.out.println("************** SIZE ARTICLE ************ " + listeArticle.size() + " @@@@@@@@@@@ " + limit + " FAM1 " + valueFam1.trim() + " FAM2 " + valueFam2.trim() + " FAM3 " + valueFam3.trim());
        } else {
            Module.message(2, "Attention l'un des Champs (Chantier ou Date de livraison souhaitée) est vide", "Attention l'un des Champs est vide");
        }
    }

    public void envoyerArticleDA() {
        if (!Module.checkDate(new Date(), null, demandeApp.getDateLivraisonSouhaitee())) {
            System.out.println("date livr < = today");
            Module.message(2, "date livraison doit être supérieur a la date d'aujourd'hui", "");
            return;
        }
        System.out.println("<<<<<<<<<<< Test ENVOI DIVALTO >>>>>>>>>>>>>> ");
        //for (ArticleDA listArticle1 : listArticle) {
        achatService.modifierDemandeAppro(demandeApp);
        idDA = demandeApp.getId();
        DemandeApprovisionnement newDa = achatService.getDemandeAppro(idDA);
        if (idDA != 0) {
            System.out.println("<<<<<<<< ID OF DA >>>>>>>>>>>>> " + idDA);

            Chantier objetChantier = chantierService.getChantier(idChantier);

            Date objetDateLivraisonSouhaiteeDA = null;

            if (demandeApp.getDateLivraisonSouhaitee() != null) {
//			dateLivraisonSouhaiteeDA = dateLivraisonSouhaiteeDA.substring(5,7) + "/" + dateLivraisonSouhaiteeDA.substring(8,10) + "/" + dateLivraisonSouhaiteeDA.substring(0,4);  	
                objetDateLivraisonSouhaiteeDA = demandeApp.getDateLivraisonSouhaitee();
            }
            ELContext elContext = FacesContext.getCurrentInstance().getELContext();
            Authentification authentification = (Authentification) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "authentification");
//            newDa.setId(idDA);
            newDa.setChantier(objetChantier);
            newDa.setDateAjout(new Date());
            newDa.setDateLivraisonSouhaitee(objetDateLivraisonSouhaiteeDA);
            newDa.setDemandeur(authentification.get_connected_user());
            newDa.setCommentaire(demandeApp.getDestinationDA() + ". " + demandeApp.getCommentaire());
            newDa.setDestinationDA(demandeApp.getDestinationDA());

            //achatService.modifierDemandeAppro(demandeApp);
            if ((Long) achatService.nombreArticlesDemandeApprovisionnement(idDA) > 0) {

                List<ArticleDA> listeArticles = achatService.articlesDemandeApprovisionnement(idDA);
                System.out.println("Article size DA" + listeArticles.size());
                String listeArticleQuantite = listeArticles.get(0).getArticle().getCodeArticle().trim() + ";" + listeArticles.get(0).getQuantiteArticle().toString()+ ";" + listeArticles.get(0).getCommentaire().replace(".", ",");
                System.out.println("liste Article Quantite before " + listeArticleQuantite);
                for (int i = 1; i < listeArticles.size(); i++) {
                    listeArticleQuantite += "|" + listeArticles.get(i).getArticle().getCodeArticle().trim() + ";" + listeArticles.get(i).getQuantiteArticle().toString()+ ";" + listeArticles.get(i).getCommentaire().replace(".", ",");
                }
                System.out.println("liste Article Quantite after " + listeArticleQuantite);
                String codeChantier = objetChantier.getCodeNovapaie();
                String demandeur = demandeApp.getDemandeur().getLogin();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                String dateLivraisonSouhaitee = sdf.format(demandeApp.getDateLivraisonSouhaitee()).replaceAll("/", "");

                //20130420
                System.out.println("date livraison DA: " + dateLivraisonSouhaitee);
                /**
                 * ********* Web service d'envoi de la demande
                 * d'approvisionnement ********
                 */
                Map<String, String> cmdInterneMap = achatService.commandeInterne_trsDepot(listeArticleQuantite, dateLivraisonSouhaitee, codeChantier, demandeur, demandeApp.getCommentaire());
                System.out.println("test1");
                if (!cmdInterneMap.get("referenceDADiva").equals("0") && !cmdInterneMap.get("referenceDADiva").equals("-1")) {
                    System.out.println("IN if3");

                    EtatDA objetEtatDA = new EtatDA();
                    objetEtatDA.setId(2);

                    newDa.setNumeroDA(cmdInterneMap.get("referenceDADiva"));
                    newDa.setEtatDA(objetEtatDA);
                    achatService.modifierDemandeAppro(newDa);
                    Module.message(0, "Demande D'appro est envoyé avec sucès", "");
                    btnEtat = true;
                    try {
                        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
                        ec.redirect(ec.getRequestContextPath() + "/achat/dmdAchat.xhtml");
                    } catch (IOException ex) {
                        System.out.println("redirecton erreur " + ex.getMessage());
                    }
                } else {
                    //System.out.println("else if echec");
                    Module.message(3, "Code 2202 : Echec d'envoi de la demande d'approvisionement", "");
                }
            } else {
                Module.message(1, "Code 2201 : la demande ne peut pas être envoyée: Aucun article ajouté", "");
            }
        } else {
            Module.message(1, "Demande D'appro n'existe pas", "");
        }
        //}

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
        if (demandeApp.getDateLivraisonSouhaitee() != null && !Module.checkDate(new Date(), null, demandeApp.getDateLivraisonSouhaitee())) {
            //System.out.println("date livr < = today");
            Module.message(2, "date livraison doit être supérieur a la date d'aujourd'hui", "");
            return;
        }
        buttonDis = !(idChantier != null || idChantier != 0 || demandeApp.getDateLivraisonSouhaitee() != null);
    }
}
