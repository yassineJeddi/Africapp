/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.mb.achat;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import ma.bservices.beans.Article;
import ma.bservices.beans.ArticleDA;
import ma.bservices.beans.BonLivraison;
import ma.bservices.beans.Contrat;
import ma.bservices.beans.DemandeApprovisionnement;
import ma.bservices.beans.Docs;
import ma.bservices.beans.EtatDA;
import ma.bservices.mb.services.ConstanteMb;
import ma.bservices.mb.services.Module;
import ma.bservices.paginate.DmdPaginate;
import ma.bservices.services.AchatService;
import ma.bservices.services.AdministrationService;
import ma.bservices.services.ChantierService;
import ma.bservices.services.DocsService;
import ma.bservices.services.FamilleArticle;
import ma.bservices.services.ParametrageService;
import ma.bservices.tgcc.authentification.Authentification;
import ma.bservices.tgcc.utilitaire.FileOutils;
import org.springframework.beans.BeansException;
import org.springframework.stereotype.Component;

/**
 *
 * @author Mahdi
 */
@Component
@ManagedBean(name = "achatMb")
@ViewScoped
public class AchatMb implements Serializable {

    @ManagedProperty(value = "#{achatService}")
    private AchatService achatService;
    @ManagedProperty(value = "#{parametrageService}")
    private ParametrageService parametrageService;

    @ManagedProperty(value = "#{docsService}")
    private DocsService docsService;
    @ManagedProperty(value = "#{chantierServiceEvol}")
    private ChantierService chantierService;

    @ManagedProperty(value = "#{administrationService}")
    private AdministrationService administrationService;

    private List<DemandeApprovisionnement> demandes;
    private Integer page, nbr;

    private DemandeApprovisionnement DA;
    private Boolean disable;
    private String valueButton;
    private List<ArticleDA> listArticle = new ArrayList<>();
    private Boolean disableDelete;
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
    private int idChantier;
    private String demand;
    private Float quantite;
    private List<EtatDA> etatDA = new ArrayList<>();
    private DemandeApprovisionnement demandeApp = new DemandeApprovisionnement();

    private String numeroDA;
    private String chantier;
    private String etat, reference;
    private Date dateAjoutDe, dateAjoutA;
    private Date dateLSDe, dateLSA;
    private boolean buttonDis = true;

    private Integer var;
    private Boolean prev, next, last, first, pageId;

    public boolean isButtonDis() {
        return buttonDis;
    }

    public void setButtonDis(boolean buttonDis) {
        this.buttonDis = buttonDis;
    }

    public DocsService getDocsService() {
        return docsService;
    }

    public void setDocsService(DocsService docsService) {
        this.docsService = docsService;
    }

    public String getNumeroDA() {
        return numeroDA;
    }

    public void setNumeroDA(String numeroDA) {
        this.numeroDA = numeroDA;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getChantier() {
        return chantier;
    }

    public void setChantier(String chantier) {
        this.chantier = chantier;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public Date getDateAjoutDe() {
        return dateAjoutDe;
    }

    public void setDateAjoutDe(Date dateAjoutDe) {
        this.dateAjoutDe = dateAjoutDe;
    }

    public Date getDateAjoutA() {
        return dateAjoutA;
    }

    public void setDateAjoutA(Date dateAjoutA) {
        this.dateAjoutA = dateAjoutA;
    }

    public Date getDateLSDe() {
        return dateLSDe;
    }

    public void setDateLSDe(Date dateLSDe) {
        this.dateLSDe = dateLSDe;
    }

    public Date getDateLSA() {
        return dateLSA;
    }

    public void setDateLSA(Date dateLSA) {
        this.dateLSA = dateLSA;
    }

    public List<EtatDA> getEtatDA() {
        return etatDA;
    }

    public void setEtatDA(List<EtatDA> etatDA) {
        this.etatDA = etatDA;
    }

    public Float getQuantite() {
        return quantite;
    }

    public void setQuantite(Float quantite) {
        this.quantite = quantite;
    }

    public int getIdChantier() {
        return idChantier;
    }

    public void setIdChantier(int idChantier) {
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

    public DemandeApprovisionnement getDA() {
        return DA;
    }

    public void setDA(DemandeApprovisionnement DA) {
        this.DA = DA;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getNbr() {
        return nbr;
    }

    public void setNbr(Integer nbr) {
        this.nbr = nbr;
    }

    public AchatService getAchatService() {
        return achatService;
    }

    public void setAchatService(AchatService achatService) {
        this.achatService = achatService;
    }

    public List<DemandeApprovisionnement> getDemandes() {
        return demandes;
    }

    public void setDemandes(List<DemandeApprovisionnement> demandes) {
        this.demandes = demandes;
    }

    public ParametrageService getParametrageService() {
        return parametrageService;
    }

    public void setParametrageService(ParametrageService parametrageService) {
        this.parametrageService = parametrageService;
    }

    public Integer getVar() {
        return var;
    }

    public void setVar(Integer var) {
        this.var = var;
    }

    public Boolean getPrev() {
        return prev;
    }

    public void setPrev(Boolean prev) {
        this.prev = prev;
    }

    public Boolean getNext() {
        return next;
    }

    public void setNext(Boolean next) {
        this.next = next;
    }

    public Boolean getLast() {
        return last;
    }

    public void setLast(Boolean last) {
        this.last = last;
    }

    public Boolean getFirst() {
        return first;
    }

    public void setFirst(Boolean first) {
        this.first = first;
    }

    public Boolean getPageId() {
        return pageId;
    }

    public void setPageId(Boolean pageId) {
        this.pageId = pageId;
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

            achatService = Module.ctx.getBean(AchatService.class);
            docsService = Module.ctx.getBean(DocsService.class);
            parametrageService = Module.ctx.getBean(ParametrageService.class);
            page = 1;
            nbr = Integer.parseInt(achatService.nombreDemandesAppro("", "", "", "", null, null, null, null, true).toString());
            ELContext elContext = FacesContext.getCurrentInstance().getELContext();
            Authentification authentification = (Authentification) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "authentification");
            demand = authentification.get_connected_user().getLogin();
            demandes = DmdPaginate.fisrt(numeroDA, chantier, etat, "", dateAjoutDe, dateAjoutA, dateLSDe, dateLSA);
            var = (nbr % 10 == 0) ? nbr / 10 : nbr / 10;
            System.out.println("PAGE : VAR : " + var);
            if (Objects.equals(page, var)) {
                last = true;
                pageId = true;
                first = false;
                next = true;
                prev = false;
            } else {
                onFirst();
            }
        } catch (BeansException | NumberFormatException ex) {
            System.out.println("erreur " + ex.getMessage());
        }
    }

    public void onPaginate() {
        demandes = DmdPaginate.page(page, numeroDA, chantier, etat, "", dateAjoutDe, dateAjoutA, dateLSDe, dateLSA);
        System.out.println("ON PAGINATE PAGE : " + page);
        if (Objects.equals(page, var)) {
            last = true;
            pageId = true;
            first = false;
            next = true;
            prev = false;
        } else if (page == 1) {
            last = false;
            pageId = false;
            first = true;
            next = false;
            prev = true;
        } else {
            last = false;
            pageId = false;
            first = false;
            next = false;
            prev = false;
        }
    }

    public void onNext() {
        page += 1;
        demandes = DmdPaginate.page(page, numeroDA, chantier, etat, "", dateAjoutDe, dateAjoutA, dateLSDe, dateLSA);
        last = false;
        prev = false;
        next = false;
        first = false;

        if (page.equals(var)) {
            last = true;
            pageId = true;
            first = false;
            next = true;
            prev = false;
        }
    }

    public void onPrevious() {
        page -= 1;
        demandes = DmdPaginate.page(page, numeroDA, chantier, etat, "", dateAjoutDe, dateAjoutA, dateLSDe, dateLSA);
        if (page == 1) {
            last = false;
            pageId = false;
            first = true;
            next = false;
            prev = true;
        } else {
            last = false;
            pageId = false;
            first = false;
            next = false;
            prev = false;
        }
    }

    public void onFirst() {
        page = 1;
        demandes = DmdPaginate.fisrt(numeroDA, chantier, etat, "", dateAjoutDe, dateAjoutA, dateLSDe, dateLSA);
        last = false;
        pageId = false;
        first = true;
        next = false;
        prev = true;
    }

    public void onLast() {
        page = (nbr % 10 == 0) ? nbr / 10 : nbr / 10;
        demandes = DmdPaginate.page(page, numeroDA, chantier, etat, "", dateAjoutDe, dateAjoutA, dateLSDe, dateLSA);
        last = true;
        pageId = false;
        first = false;
        next = true;
        prev = false;
    }

    /**
     * Partie; Detail Consultaion demande D'appro
     *
     * @throws IOException
     */
    public void disableButtonDetailDm() throws IOException {
        System.out.println("----------- Test Debut");
        if (DA != null) {
            System.out.println(";;;; ID DA " + DA.getId());
            listArticle = achatService.articlesDemandeApprovisionnement(DA.getId());
            if (!"".equals(DA.getNumeroDA()) || DA.getEtatDA().getId() != 1) {
                disable = true;
                valueButton = "Déjà envoyé";
                disableDelete = true;
                System.out.println("----------- Test True");
            }
            if (DA.getEtatDA().getEtat().equals("Brouillon")) {
                System.out.println("----------- Test False");
                disable = false;
                valueButton = "Envoyé";
                disableDelete = false;
            }
            System.out.println("************ List Article " + listArticle.size());
        }
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect(ec.getRequestContextPath() + "/evol/detaildemandeappro.xhtml");
    }

    public void envoyerDA() {
        EtatDA objetEtatDA = parametrageService.getEtatDA(2);

        DemandeApprovisionnement dapp = achatService.getDemandeAppro(DA.getId());

        dapp.setEtatDA(objetEtatDA);
        achatService.modifierDemandeAppro(dapp);

        Module.message(0, "Demande D'Approvisionnement est envoyée avec sucès", "Demande D'Approvisionnement est envoyée avec sucès");
    }

    public void listOfArticleByQuan() {
//       listeArticle.add(parametrageService.getArticleByCode(arti.getCodeArticle(),""));
        System.out.println("<<<<<<<<<<< ENTRE AJOUT ARTICLE >>>>>>>>>>>>>>>>>>>");
        /**
         * Partie Ajout de la demande d'appro
         */
        EtatDA objetEtatDA = parametrageService.getEtatDA(1);

        DemandeApprovisionnement DAt = new DemandeApprovisionnement();
        DAt.setDateLivraisonSouhaitee(demandeApp.getDateLivraisonSouhaitee());
        DAt.setDemandeur(administrationService.getUtilisateurByLogin(demand));
        DAt.setCommentaire(demandeApp.getCommentaire());
        DAt.setDestinationDA(demandeApp.getDestinationDA());
        DAt.setChantier(chantierService.getChantier(idChantier));
        DAt.setEtatDA(objetEtatDA);
        DAt.setDateAjout(new Date());
        Integer idDA = achatService.ajouterDemandeAppro(DAt);

        System.out.println("{{{{{{{{{{{{ chantier ++++++++ " + idChantier);
        System.out.println("{{{{{{{{{{{{ DestinataireDA ++++++++ " + DAt.getDestinationDA());
        System.out.println("{{{{{{{{{{{{ Demande ++++++++ " + demand);
        System.out.println("{{{{{{{{{{{{ Comm ++++++++ " + DAt.getCommentaire());
        DAt.setId(idDA);
        achatService.modifierDemandeAppro(DAt);

        /**
         * Partie Ajout de l'article(on doit changer dos apres , on l import
         * depuis divalto)
         */
        String dos = "700";
        Article objetArticle = parametrageService.getArticleByCode(arti.getCodeArticle(), dos);
        ArticleDA articleDA = new ArticleDA();

        articleDA.setDemandeApprovisionnement(DAt);
        articleDA.setArticle(objetArticle);
        articleDA.setQuantiteArticle(quantite);
        articleDA.setRefArticle(arti.getCodeArticle());

        Integer idADA = achatService.ajouterArticleDA(articleDA);

        listArticle.add(achatService.getArticleDA(idADA));

        System.out.println("<<<<<<<<< SIZE OF LISTE ARTICLE DA >>>>>>>>>>>>>> " + listArticle.size());

        Module.message(0, "L'article avec le code " + arti.getCodeArticle() + " est ajouté avec sucès ", "L'article avec le code " + arti.getCodeArticle() + " est ajouté avec sucès ");
    }

    public void reinitSearch() {

    }

    public void copyBL() {

        int i = 0;
        Docs docFound = new Docs();
        String categorie = "BL";

        List<BonLivraison> lBL = achatService.findAllBL();

        for (BonLivraison bl : lBL) {
            docFound = docsService.findByNodeRef(bl.getNodeRefDocument());
            if (docFound != null) {
                try {
                    String cheminDossier = docFound.getLocation().substring(docFound.getLocation().indexOf("/"), docFound.getLocation().lastIndexOf("/"));
                    String cheminComplet = ConstanteMb.getRepertoire() + "/files/resources/" + categorie + docFound.getLocation().substring(docFound.getLocation().indexOf("/"), docFound.getLocation().length()).replaceAll(".bin", ".pdf");
                    Path folder = Paths.get(ConstanteMb.getRepertoire() + "/files/resources/" + categorie + "/" + cheminDossier);
                    Files.createDirectories(folder);
                    System.out.println("cheminDossier = " + cheminDossier);
                    System.out.println("cheminComplet = " + cheminComplet);
                    System.out.println("folder = " + folder.normalize().toString());
                    fileCopy(ConstanteMb.getRepertoire() + "/" + docFound.getLocation(), cheminComplet);
                    bl.setNodeRefDocument(cheminComplet.substring(cheminComplet.indexOf("files/"), cheminComplet.length()));
                    bl.setModifiePar(docFound.getModifier());
                    bl.setCreePar(docFound.getCreator());
                    bl.setDateCreation(docFound.getCreationDate());
                    bl.setDateModification(docFound.getModificationDate());
                    achatService.modifierBonLivraison(bl);                 
                } catch (IOException IO) {
                    System.out.println("Une erreur s'est produite");
                }
            }
            i++;
        }
    }

    public void fileCopy(String cheminSource, String cheminDestination) throws IOException {
        File f = new File(cheminSource);
        FileOutils.copyFileUsingStream(f, new File(cheminDestination));
    }

    public void rechercheDA() {
        
        //il faut ajouter la liste des chantier de l'utilisateur
        demandes = achatService.listeDemandesAppro(numeroDA, chantier, etat, "", dateAjoutDe, dateAjoutA, dateLSDe, dateLSA, 0, 10, true);
        page = 1;
        nbr = Integer.parseInt(achatService.nombreDemandesAppro(numeroDA, chantier, etat, "", dateAjoutDe, dateAjoutA, dateLSDe, dateLSA, false).toString());
        var = (nbr % 10 == 0) ? nbr / 10 : nbr / 10 + 1;
        if (demandes.size() < 9) {
            next = true;
            prev = true;
            last = true;
            first = true;
            pageId = true;
        } else {
            next = false;
            prev = true;
            last = false;
            first = true;
            pageId = false;

        }
        System.out.println("<<<<<<<<<<<<< SIZE OF LIST DEMANDE >>>>>>>>>>>>>>> " + demandes.size());
    }

    public void btnAjoutArticleDisable() {
        buttonDis = !(idChantier != 0 || demandeApp.getDateLivraisonSouhaitee() != null);
    }

    public String detailDm(DemandeApprovisionnement dmd) throws IOException {
        return "detaildemandeappro.xhtml?faces-redirect=true&id=" + dmd.getId();
    }

}
