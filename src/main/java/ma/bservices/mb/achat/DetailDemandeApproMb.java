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
import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import ma.bservices.beans.Article;
import ma.bservices.beans.ArticleDA;
import ma.bservices.beans.DemandeApprovisionnement;
import ma.bservices.beans.EtatDA;
import ma.bservices.constantes.Constantes;
import ma.bservices.mb.services.EtatDAMb;
import ma.bservices.mb.services.Module;
import ma.bservices.services.AchatService;
import ma.bservices.services.ChantierService;
import ma.bservices.services.FamilleArticle;
import ma.bservices.services.ParametrageService;
import ma.bservices.tgcc.authentification.Authentification;
import org.springframework.stereotype.Component;

/**
 *
 * @author Mahdi
 */
@Component
@ManagedBean
@ViewScoped
public class DetailDemandeApproMb implements Serializable {

    @ManagedProperty(value = "#{achatService}")
    private AchatService achatService;
    @ManagedProperty(value = "#{parametrageService}")
    private ParametrageService parametrageService;
    @ManagedProperty(value = "#{chantieService}")
    private ChantierService chantieService;

    private DemandeApprovisionnement DA;
    private String valueButton;
    private Boolean disable;
    private Boolean disableDelete;
    private List<ArticleDA> listArticle;
    private List<Article> listeArticle = new ArrayList<>();
    private ArticleDA ADA = new ArticleDA();
    private Float quantite;
    private Article arti = new Article();
    private List<FamilleArticle> fam1;
    private List<FamilleArticle> fam2;
    private List<FamilleArticle> fam3;
    private String valueFam1;
    private String valueFam2;
    private String valueFam3;
    private String des;
    private String reference;
    private Integer idChantier;

    public Article getArti() {
        return arti;
    }

    public void setArti(Article arti) {
        this.arti = arti;
    }

    public Integer getIdChantier() {
        return idChantier;
    }

    public void setIdChantier(Integer idChantier) {
        this.idChantier = idChantier;
    }

    public String getDes() {
        return des;
    }

    public ChantierService getChantieService() {
        return chantieService;
    }

    public void setChantieService(ChantierService chantieService) {
        this.chantieService = chantieService;
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

    public DemandeApprovisionnement getDA() {
        return DA;
    }

    public void setDA(DemandeApprovisionnement DA) {
        this.DA = DA;
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

    public List<Article> getListeArticle() {
        return listeArticle;
    }

    public void setListeArticle(List<Article> listeArticle) {
        this.listeArticle = listeArticle;
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

    public Boolean getDisableDelete() {
        return disableDelete;
    }

    public void setDisableDelete(Boolean disableDelete) {
        this.disableDelete = disableDelete;
    }

    public List<ArticleDA> getListArticle() {
        return listArticle;
    }

    public void setListArticle(List<ArticleDA> listArticle) {
        this.listArticle = listArticle;
    }

    public Float getQuantite() {
        return quantite;
    }

    public void setQuantite(Float quantite) {
        this.quantite = quantite;
    }

    public ArticleDA getADA() {
        return ADA;
    }

    public void setADA(ArticleDA ADA) {
        this.ADA = ADA;
    }

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

        DA = achatService.getDemandeAppro(id);
        if (DA != null) {
            listArticle = achatService.articlesDemandeApprovisionnement(DA.getId());
            idChantier = DA.getChantier().getId();
            System.out.println("idChantier " + idChantier);
            if (!"".equals(DA.getNumeroDA()) || DA.getEtatDA().getId() != 1) {
                disable = true;
                valueButton = "Déjà envoyé";
                disableDelete = true;
            }

            if (DA.getEtatDA().getEtat().equals("Brouillon")) {
                disable = false;
                valueButton = "Envoyé";
                disableDelete = false;
            }
        } else {
            Module.message(3, "Demande D'appro n'existe pas", "");
            disable = true;
            disableDelete = true;
            DA = new DemandeApprovisionnement();
        }

        achatService = Module.ctx.getBean(AchatService.class);
        parametrageService = Module.ctx.getBean(ParametrageService.class);
        chantieService = Module.ctx.getBean(ChantierService.class);
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        EtatDAMb etatDAMb = (EtatDAMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "etatDAMb");
        fam1 = etatDAMb.getFam1();
        fam2 = etatDAMb.getFam2();
        fam3 = etatDAMb.getFam3();
    }

    public void envoyerDA() {
        if (idChantier == null || idChantier == 0 || DA.getDateLivraisonSouhaitee() == null) {
            Module.message(2, "Veuillez selectionner une date de livraison et un chantier", "");
            return;
        }
        if (!Module.checkDate(new Date(), null, DA.getDateLivraisonSouhaitee())) {
            System.out.println("date livr < = today");
            Module.message(2, "date livraison doit être supérieur a la date d'aujourd'hui", "");
            return;
        }
        System.out.println("<<<<<<<<<<< Test ENVOI DIVALTO >>>>>>>>>>>>>> ");

        //for (ArticleDA listArticle1 : listArticle) {
        Integer idDA = DA.getId();
        DemandeApprovisionnement newDa = achatService.getDemandeAppro(idDA);
        if (idDA != 0) {
            ELContext elContext = FacesContext.getCurrentInstance().getELContext();
            Authentification authentification = (Authentification) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "authentification");
            Date objetDateLivraisonSouhaiteeDA = DA.getDateLivraisonSouhaitee();

            System.out.println("<<<<<<<< ID OF DA >>>>>>>>>>>>> " + idDA);
//            newDa.setId(idDA);
            newDa.setChantier(chantieService.getChantier(idChantier));
            newDa.setCommentaire(DA.getDestinationDA() + ". " + DA.getCommentaire());
            newDa.setDestinationDA(DA.getDestinationDA());
            newDa.setDateAjout(new Date());
            newDa.setDateLivraisonSouhaitee(objetDateLivraisonSouhaiteeDA);
            newDa.setDemandeur(authentification.get_connected_user());
            if ((Long) achatService.nombreArticlesDemandeApprovisionnement(idDA) > 0) {
                List<ArticleDA> listeArticles = achatService.articlesDemandeApprovisionnement(idDA);
                String listeArticleQuantite = listeArticles.get(0).getArticle().getCodeArticle().trim() + ";" + listeArticles.get(0).getQuantiteArticle().toString().replace(".", ",");
                for (int i = 1; i < listeArticles.size(); i++) {
                    listeArticleQuantite += "|" + listeArticles.get(i).getArticle().getCodeArticle().trim() + ";" + listeArticles.get(i).getQuantiteArticle().toString().replace(".", ",");
                }
                String codeChantier = newDa.getChantier().getCodeNovapaie();
                String demandeur = newDa.getDemandeur().getNom() + " " + DA.getDemandeur().getPrenom();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                String dateLivraisonSouhaitee = sdf.format(DA.getDateLivraisonSouhaitee());
                dateLivraisonSouhaitee = dateLivraisonSouhaitee.replace("/", "");
                //20130420
                System.out.println("date livraison DA: " + dateLivraisonSouhaitee);
                /**
                 * ********* Web service d'envoi de la demande
                 * d'approvisionnement ********
                 */
                Map<String, String> cmdInterneMap = achatService.commandeInterne_trsDepot(listeArticleQuantite, dateLivraisonSouhaitee, codeChantier, demandeur, DA.getCommentaire());
                System.out.println("test1");
                if (!cmdInterneMap.get("referenceDADiva").equals("0") && !cmdInterneMap.get("referenceDADiva").equals("-1")) {
                    EtatDA objetEtatDA = parametrageService.getEtatDA(2);
                    newDa.setNumeroDA(cmdInterneMap.get("referenceDADiva"));
                    newDa.setEtatDA(objetEtatDA);
                    achatService.modifierDemandeAppro(newDa);
                    Module.message(0, "Demande D'appro est envoyé avec sucès", "");
                    DA = achatService.getDemandeAppro(newDa.getId());
                    disable = true;
                } else {
                    Module.message(2, "Erreur au niveau mofication sur divalto", "");
                    System.out.println("test2");
                }
            } else {
                Module.message(2, "Erreur, la Liste des articles est vide", "");
            }
        }
    }

    public boolean qteValide() {
        System.out.println("test qte");
        return !(quantite != null && quantite > 0.0);
    }

    public void deleteArticleDA() {
        if (!achatService.demandeApproValidee(DA.getId())) {
            if ((Long) achatService.nombreArticlesDemandeApprovisionnement(DA.getId()) == 1) {
                achatService.supprimerArticleDA(ADA);
                achatService.supprimerDA(DA);
                try {
                    ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
                    ec.redirect(ec.getRequestContextPath() + "/evol/dmdAchat.xhtml");
                } catch (IOException ex) {
                    System.out.println("redirecton erreur " + ex.getMessage());
                }
            } else {
                achatService.supprimerArticleDA(ADA);
                listArticle = achatService.articlesDemandeApprovisionnement(DA.getId());
            }
            Module.message(0, "Suppression réussie", "");
        } else {
            Module.message(2, "Suppression échouée: Cette demande est déjà envoyée", "");
        }
    }

    public void ajouterNouvelleDA() {
        if (qteValide()) {
            Module.message(2, "Veuillez saisir une quantité valide", "");
            return;
        }
        if (DA.getChantier().getId() != 0 && DA.getDateLivraisonSouhaitee() != null) {
            if (achatService.articleDADejaExiste(arti.getCodeArticle(), DA.getId())) {
                Module.message(2, "Vous avez déjà ajouté cet Article", "");
                return;
            }
            ArticleDA articleDA = new ArticleDA();
            System.out.println("id article: " + arti.getId());
            articleDA.setArticle(arti);
            articleDA.setRefArticle(arti.getCodeArticle());
            articleDA.setDemandeApprovisionnement(DA);
            articleDA.setQuantiteArticle(quantite);
            Integer idADA = achatService.ajouterArticleDA(articleDA);
            listArticle.add(achatService.getArticleDA(idADA));
            Module.message(0, "Article Ajoutée", "");
            arti = new Article();
        } else {
            Module.message(2, "veuillez saisir la date laivraison souhaitée", "");
        }
    }

    public void fam2ByFam1() {
        System.out.println("<<<<<<<<<<<<<<<< ENTREE >>>>>>>>>>>>>>>>>>" + valueFam1);
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

        System.out.println("********* Size Fam 2 " + fam2.size());
        System.out.println("********* Size Fam 3 " + fam3.size());
    }

    public void fam3ByFam2() {
        String reqDos = " dos=700 ";
        valueFam2 = valueFam2.substring(1, 6);
        fam3 = (List<FamilleArticle>) parametrageService.listeFamilles(0, 10, 1, 8, valueFam2, reqDos);
    }

    public void articleByFam() {
        if (DA.getChantier().getId() != 0 || DA.getDateLivraisonSouhaitee() != null) {
            System.out.println("!!!!!!!!!!!!!!!!! ENTREE ??????????????????? ");
            String reqDos = " dos=700  ";
            int limit = Integer.parseInt(achatService.nombreRechercheArticle("", valueFam1, valueFam2, valueFam3.trim(), reqDos, des).toString());
            listeArticle = achatService.listeRechercheArticle(0, limit, reference, valueFam1, valueFam2, valueFam3.trim(), reqDos, des);
            System.out.println("************** SIZE ARTICLE ************ " + listeArticle.size() + " @@@@@@@@@@@ " + limit + " FAM1 " + valueFam1.trim() + " FAM2 " + valueFam2.trim() + " FAM3 " + valueFam3.trim());
        } else {
            Module.message(2, "Attention l'un des Champs (Chantier ou Date de livraison souhaitée) est vide", "Attention l'un des Champs est vide");
        }
    }

    public void findArticle() {
        String dos = Constantes.getInstance().numeroDossierDivalto;
        Article objetArticle = parametrageService.getArticleByCode(arti.getCodeArticle(), dos);
        if (objetArticle == null) {
            Module.message(2, "Erreur, Article n'existe pas ou Réference incomplète", "");
            return;
        }
        arti = objetArticle;
    }
}
