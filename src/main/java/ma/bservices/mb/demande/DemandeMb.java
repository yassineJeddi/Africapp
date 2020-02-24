/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.mb.demande;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
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
import ma.bservices.beans.EtatDA;
import ma.bservices.mb.services.Module;
import ma.bservices.paginate.DmdIntPaginate;
import ma.bservices.services.AdministrationService;
import ma.bservices.services.ChantierService;
import ma.bservices.services.DemandeService;
import ma.bservices.services.DocsService;
import ma.bservices.services.FamilleArticle;
import ma.bservices.services.ParametrageService;
import ma.bservices.tgcc.Entity.DemandeDetail;
import ma.bservices.tgcc.Entity.DemandeEntete;
import ma.bservices.tgcc.authentification.Authentification;
import ma.bservices.tgcc.utilitaire.FileOutils;
import org.springframework.beans.BeansException;
import org.springframework.stereotype.Component;

/**
 *
 * @author Mahdi
 */
@Component
@ManagedBean(name = "demandeMb")
@ViewScoped
public class DemandeMb implements Serializable {

    @ManagedProperty(value = "#{demandeService}")
    private DemandeService demandeService;
    
    @ManagedProperty(value = "#{parametrageService}")
    private ParametrageService parametrageService;

    @ManagedProperty(value = "#{docsService}")
    private DocsService docsService;
    
    @ManagedProperty(value = "#{chantierServiceEvol}")
    private ChantierService chantierService;

    @ManagedProperty(value = "#{administrationService}")
    private AdministrationService administrationService;

    private List<DemandeEntete> demandes;
    private Integer page, nbr;

    private DemandeEntete DA;
    private Boolean disable;
    private String valueButton;
    private List<DemandeDetail> listArticle = new ArrayList<>();
    private Boolean disableDelete;
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
    private int idChantier;
    private int idAtelier;
    private String demand;
    private Float quantite;
    private Float longeur;
    private Float largeur;
    private Float hauteur;
    private String unite;
    private String observation;
    private List<EtatDA> etatDA = new ArrayList<>();
    private DemandeEntete demandeApp = new DemandeEntete();

    private String numeroDA;
    private String chantier;
    private String atelier;
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

    public Boolean getDisableDelete() {
        return disableDelete;
    }

    public void setDisableDelete(Boolean disableDelete) {
        this.disableDelete = disableDelete;
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



    public List<DemandeEntete> getDemandes() {
        return demandes;
    }

    public void setDemandes(List<DemandeEntete> demandes) {
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

    public DemandeEntete getDA() {
        return DA;
    }

    public List<DemandeDetail> getListArticle() {
        return listArticle;
    }

    public DemandeDetail getADA() {
        return ADA;
    }

    public DemandeEntete getDemandeApp() {
        return demandeApp;
    }

    public String getAtelier() {
        return atelier;
    }

    public void setDA(DemandeEntete DA) {
        this.DA = DA;
    }

    public void setListArticle(List<DemandeDetail> listArticle) {
        this.listArticle = listArticle;
    }

    public void setADA(DemandeDetail ADA) {
        this.ADA = ADA;
    }

    public void setDemandeApp(DemandeEntete demandeApp) {
        this.demandeApp = demandeApp;
    }

    public void setAtelier(String atelier) {
        this.atelier = atelier;
    }

    
    @PostConstruct
    public void init() {
        try {

            demandeService = Module.ctx.getBean(DemandeService.class);
            docsService = Module.ctx.getBean(DocsService.class);
            parametrageService = Module.ctx.getBean(ParametrageService.class);
            page = 1;
            nbr = Integer.parseInt(demandeService.nombreDemandesInterne("", "", "", "","", null, null, null, null, true).toString());
            ELContext elContext = FacesContext.getCurrentInstance().getELContext();
            Authentification authentification = (Authentification) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "authentification");
            demand = authentification.get_connected_user().getLogin();
            //System.out.println("test 1 ");
            
             
//            Permission persmission = administrationService.getPermissionByName("admin");
//            Boolean bool = administrationService.hasPermission(authentification.get_connected_user(), persmission);
//            
//            
            
            demandes = DmdIntPaginate.fisrt(numeroDA, chantier,atelier, etat, "", dateAjoutDe, dateAjoutA, dateLSDe, dateLSA);
            var = (nbr % 10 == 0) ? nbr / 10 : nbr / 10;
            //System.out.println("PAGE : VAR : " + var);
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
        demandes = DmdIntPaginate.page(page, numeroDA, chantier,atelier, etat, "", dateAjoutDe, dateAjoutA, dateLSDe, dateLSA);
        //System.out.println("ON PAGINATE PAGE : " + page);
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
        demandes = DmdIntPaginate.page(page, numeroDA, chantier,atelier, etat, "", dateAjoutDe, dateAjoutA, dateLSDe, dateLSA);
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
        demandes = DmdIntPaginate.page(page, numeroDA, chantier,atelier, etat, "", dateAjoutDe, dateAjoutA, dateLSDe, dateLSA);
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
        demandes = DmdIntPaginate.page(page, numeroDA, chantier,atelier, etat, "", dateAjoutDe, dateAjoutA, dateLSDe, dateLSA);
        last = false;
        pageId = false;
        first = true;
        next = false;
        prev = true;
    }

    public void onLast() {
        page = (nbr % 10 == 0) ? nbr / 10 : nbr / 10;
        demandes = DmdIntPaginate.page(page, numeroDA, chantier,atelier, etat, "", dateAjoutDe, dateAjoutA, dateLSDe, dateLSA);
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
        //System.out.println("----------- Test Debut");
        if (DA != null) {
            //System.out.println(";;;; ID DA " + DA.getId());
            listArticle = demandeService.DemandeDetail(DA.getId());
            if (!"".equals(DA.getNumDemande()) || DA.getEtatDA().getId() != 1) {
                disable = true;
                valueButton = "Déjà envoyé";
                disableDelete = true;
                //System.out.println("----------- Test True");
            }
            if (DA.getEtatDA().getEtat().equals("Brouillon")) {
                //System.out.println("----------- Test False");
                disable = false;
                valueButton = "Envoyé";
                disableDelete = false;
            }
            //System.out.println("************ List Article " + listArticle.size());
        }
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect(ec.getRequestContextPath() + "/evol/detaildemandeappro.xhtml");
    }

    public void envoyerDA() {
        EtatDA objetEtatDA = parametrageService.getEtatDA(2);

        DemandeEntete daet = demandeService.getDemandeEntete(DA.getId());

        daet.setEtatDA(objetEtatDA);
        demandeService.updateDemandeEntete(daet);

        Module.message(0, "Demande D'Approvisionnement est envoyée avec sucès", "Demande D'Approvisionnement est envoyée avec sucès");
    }

    //amine a revoir
    public void listOfArticleByQuan() {
//       listeArticle.add(parametrageService.getArticleByCode(arti.getCodeArticle(),""));
        //System.out.println("<<<<<<<<<<< ENTRE AJOUT ARTICLE >>>>>>>>>>>>>>>>>>>");
        /**
         * Partie Ajout de la demande d'appro
         */
        EtatDA objetEtatDA = parametrageService.getEtatDA(1);

        DemandeEntete DAt = new DemandeEntete();
        DAt.setDateLivraisonSouhaitee(demandeApp.getDateLivraisonSouhaitee());
        DAt.setDemandeur(administrationService.getUtilisateurByLogin(demand));
        DAt.setComantaire(demandeApp.getComantaire());
        // amine a revoir DAt.setDestinationDA(demandeApp.getDestinationDA());
        DAt.setChantier(chantierService.getChantier(idChantier));
        Integer idAteleier;
        DAt.setAtelier(chantierService.getChantier(idAtelier));
        DAt.setEtatDA(objetEtatDA);
        DAt.setDateAjout(new Date());
        long idDA = demandeService.addDemandeEntete(DAt);

        //System.out.println("{{{{{{{{{{{{ chantier ++++++++ " + idChantier);
        //System.out.println("{{{{{{{{{{{{ DestinataireDA ++++++++ " + DAt.getDestinationDA());
        //System.out.println("{{{{{{{{{{{{ Demande ++++++++ " + demand);
        //System.out.println("{{{{{{{{{{{{ Comm ++++++++ " + DAt.getCommentaire());
        DAt.setId(idDA);
        demandeService.updateDemandeEntete(DAt);

        /**
         * Partie Ajout de l'article(on doit changer dos apres , on l import
         * depuis divalto)
         */
         String dos = "700";
        Article objetArticle = parametrageService.getArticleByCode(arti.getCodeArticle(), dos);
        DemandeDetail articleDA = new DemandeDetail();

        articleDA.setDemandeEntete(DAt);
        articleDA.setArtcile(objetArticle);
        articleDA.setQte(quantite);
        articleDA.setRefArticle(arti.getCodeArticle());
        articleDA.setLargeur(largeur);
        articleDA.setLongeur(longeur);
        articleDA.setUnite(unite);
        articleDA.setHauteur(hauteur);
        articleDA.setObservation(observation);
        

        long idADA = demandeService.addDemandeDetail(articleDA);

        listArticle.add(demandeService.getDemandeDetail(idADA));

        //System.out.println("<<<<<<<<< SIZE OF LISTE ARTICLE DA >>>>>>>>>>>>>> " + listArticle.size());

        Module.message(0, "L'article avec le code " + arti.getCodeArticle() + " est ajouté avec sucès ", "L'article avec le code " + arti.getCodeArticle() + " est ajouté avec sucès ");
    }

    public void reinitSearch() {

    }

 

    public void fileCopy(String cheminSource, String cheminDestination) throws IOException {
        File f = new File(cheminSource);
        FileOutils.copyFileUsingStream(f, new File(cheminDestination));
    }

    public void rechercheDA() {
        
        //il faut ajouter la liste des chantier de l'utilisateur
        demandes = demandeService.listeDemandesInterne(numeroDA, chantier,atelier, etat, "", dateAjoutDe, dateAjoutA, dateLSDe, dateLSA, 0, 10, true);
        page = 1;
        nbr = Integer.parseInt(demandeService.nombreDemandesInterne(numeroDA, chantier,atelier, etat, "", dateAjoutDe, dateAjoutA, dateLSDe, dateLSA, false).toString());
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
        //System.out.println("<<<<<<<<<<<<< SIZE OF LIST DEMANDE >>>>>>>>>>>>>>> " + demandes.size());
    }

    public void btnAjoutArticleDisable() {
        buttonDis = !(idChantier != 0 || demandeApp.getDateLivraisonSouhaitee() != null);
    }

    public String detailDm(DemandeEntete dmd) throws IOException {
        return "detaildemandeInterne.xhtml?faces-redirect=true&id=" + dmd.getId();
    }

    public DemandeService getDemandeService() {
        return demandeService;
    }

    public int getIdAtelier() {
        return idAtelier;
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

    public String getObservation() {
        return observation;
    }

    public void setDemandeService(DemandeService demandeService) {
        this.demandeService = demandeService;
    }

    public void setIdAtelier(int idAtelier) {
        this.idAtelier = idAtelier;
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


    public void setObservation(String observation) {
        this.observation = observation;
    }

    public String getUnite() {
        return unite;
    }

    public void setUnite(String unite) {
        this.unite = unite;
    }
    
    

}
