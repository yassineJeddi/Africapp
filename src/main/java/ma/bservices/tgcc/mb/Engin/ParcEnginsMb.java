/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.mb.Engin;


import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
import ma.bservices.beans.Chantier;
import ma.bservices.beans.Designation;
import ma.bservices.beans.Marque;
import ma.bservices.beans.Salarie;
import ma.bservices.mb.services.ConstanteMb;
import ma.bservices.mb.services.Module;
import ma.bservices.services.IMarqueService;
import ma.bservices.services.ITraceUserService;
import ma.bservices.services.SalarieService; 
import ma.bservices.tgcc.service.Engin.EnginService;
import ma.bservices.tgcc.service.Engin.EtatService;
import ma.bservices.tgcc.Entity.Engin;
import ma.bservices.tgcc.Entity.EtatEngin;
import ma.bservices.tgcc.Entity.FamilleEngin;
import ma.bservices.tgcc.Entity.FournisseurEngin;
import ma.bservices.tgcc.Entity.LocataireEngin;
import ma.bservices.tgcc.Entity.SstEngin;
import ma.bservices.tgcc.Entity.TraceUser;
import ma.bservices.tgcc.Entity.TypeEngin;
import ma.bservices.tgcc.pdf.FicheEngin;
import ma.bservices.tgcc.service.Engin.Bean.CiterneServiceBean;
import ma.bservices.tgcc.service.Engin.ChantierService;
import ma.bservices.tgcc.service.Engin.DocumentEnginService;
import ma.bservices.tgcc.service.Engin.IDesignationServices;
import ma.bservices.tgcc.service.Engin.IFamilleEnginService;
import ma.bservices.tgcc.service.Engin.IFournisseurEnginService;
import ma.bservices.tgcc.service.Engin.ILocataireEnginService;
import ma.bservices.tgcc.service.Engin.ISstEnginService;
import ma.bservices.tgcc.service.Engin.ITypeEnginService;
import ma.bservices.tgcc.utilitaire.Outils;
import ma.bservices.tgcc.utilitaire.TgccFileManager;
import org.primefaces.context.RequestContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

/**
 *
 * @author a.wattah
 */
@Component
@ManagedBean(name = "parcEngins")
@ViewScoped
public class ParcEnginsMb implements Serializable {
 
    
    @ManagedProperty(value = "#{salarieService}")
    private SalarieService salarieService;
    
    @ManagedProperty(value = "#{traceUserService}")
    private ITraceUserService traceUserService;
    
    @ManagedProperty(value = "#{enginService}")
    private EnginService enginSerive;

    @ManagedProperty(value = "#{documentEnginService}")
    private DocumentEnginService documentEnginService;

    @ManagedProperty(value = "#{etatService}")
    private EtatService etatService;

    @ManagedProperty(value = "#{chantierService}")
    private ChantierService chantierService;
 
    @ManagedProperty(value = "#{marqueService}")
    private IMarqueService marqueService;
    
    @ManagedProperty(value = "#{designiationService}")
    private IDesignationServices designiationService;
    
    @ManagedProperty(value = "#{familleEnginServiceImp}")
    private IFamilleEnginService familleEnginService;
    
    @ManagedProperty(value = "#{typeEnginServiceImp}")
    private ITypeEnginService typeEnginService;
    
    @ManagedProperty(value = "#{fournisseurEnginServiceImp}")
    private IFournisseurEnginService fournisseurEnginService ;
    
 
    @ManagedProperty(value = "#{sstEnginServiceImp}")
    private ISstEnginService sstEnginService;
 
    @ManagedProperty(value = "#{locataireEnginService}")
    private ILocataireEnginService locataireEnginService;
    
    
    
    private List<Engin> engins;
    private List<Engin> filtredengins;
    private List<EtatEngin> etats;
    private List<Engin> searchEnginList = new ArrayList<Engin>();
    private List<String> listMarque = new ArrayList<>();
    private List<Salarie> conducteurEngins = new ArrayList<Salarie>();
    private List<Marque> marques = new ArrayList<Marque>();
    private List<Designation> designiations = new ArrayList<Designation>();
    private List<FamilleEngin> familleEngins = new ArrayList<FamilleEngin>();
    private List<TypeEngin> typeEngins = new ArrayList<TypeEngin>();
    private List<FournisseurEngin> fournisseurEngins = new ArrayList<FournisseurEngin>();
    private List<SstEngin> sstEngins = new ArrayList<SstEngin>();
    private List<LocataireEngin> locataireEngins = new ArrayList<LocataireEngin>();
    private Engin engin,enginsToAdd,searchEngin = new Engin(),infoEngin = new Engin();
    private Salarie conducteur=new Salarie();
    private Designation designation = new Designation();
    private Marque marqueClass = new Marque();
    private FamilleEngin familleEngin =new FamilleEngin();
    private TypeEngin typeEngin = new TypeEngin();
    private FournisseurEngin fournisseurEngin=new FournisseurEngin();
    private String idEtatSearch,marqueSearch,marque,compValue;
    private int chantierAffectation,chantierToAdd,sizeCus = 0;
    private Integer chantierSearch,idDesignation,idConducteur;
    private Boolean disable,cKilom,cHoraire;
    private String pdfFicheEngin="";
    private SstEngin sstEngin;
    private LocataireEngin locataireEngin;
    private String nomSstEngin="";
    private String nomLocEngin="";


    @PostConstruct
    public void init() {
        enginsToAdd = new Engin();
        enginsToAdd.setTypeCompteur("sansCompteur");
        enginsToAdd.setCompteurKilometrique((float) 0.0);
        enginsToAdd.setComteurHoraire((float) 0.0);
        enginsToAdd.setNbrHeures((float) 250.0);
        enginsToAdd.setNbrKilometrage((float) 10000.0);
        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        enginSerive = ctx.getBean(EnginService.class);
        chantierService = ctx.getBean(ChantierService.class);
        documentEnginService = ctx.getBean(DocumentEnginService.class);
        salarieService = Module.ctx.getBean(SalarieService.class);
        sstEnginService  = Module.ctx.getBean(ISstEnginService.class);
        locataireEnginService  = Module.ctx.getBean(ILocataireEnginService.class);
        

        //engins = enginSerive.findAll();
//        engins = enginSerive.findOneByArchive();
        /**
         * re charge liste engins non affecte application scoped
         */
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();

        ma.bservices.tgcc.mb.services.EnginMb engin_bean = (ma.bservices.tgcc.mb.services.EnginMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "enginServMb");

        // this.engins = engin_bean.getL_engins_non_archives();
        etatService = ctx.getBean(EtatService.class);
        etats = etatService.findAllEtatEngin();

    }

    public void rechercherEngin() {
        this.filtredengins = null;
        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        enginSerive = ctx.getBean(EnginService.class);

//        this.searchEnginList = enginSerive.search(searchEngin.getCode(), searchEngin.getDesignation(), searchEngin.getMarque(), idEtatSearch, -1);
        this.searchEnginList = enginSerive.rechercherEnginByFa(searchEngin.getCode(), searchEngin.getDesignation(), searchEngin.getMarque(), idEtatSearch, chantierSearch, searchEngin.getTypeEngin(), searchEngin.getFamilleEngin());
        this.engins = searchEnginList;
        this.chantierSearch = null;

    }

    public void rechercheEngin_byChantier() {

        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        ma.bservices.tgcc.mb.services.EnginMb engin_bean = (ma.bservices.tgcc.mb.services.EnginMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "enginServMb");
        this.filtredengins = null;
        if (chantierSearch == -1) {
            this.engins = engin_bean.getL_engins_non_archives();
        } else {
            this.engins = enginSerive.findAllInChantierArchive(chantierSearch);
        }
    }
    public void prepAddSstEngin(){
        nomSstEngin = "";
    }
    public void addSstEngin(){
        if(!nomSstEngin.isEmpty()){
            try { 
                    sstEngin = new SstEngin();
                    sstEngin.setNomSST(nomSstEngin);
                    sstEnginService.addSstEngin(sstEngin);
            } catch (Exception e) {
                System.out.println("Erreur d'enregistrement sst Engin car "+e.getMessage());
            }        
        }
        sstEngins = sstEnginService.allSstEngin();
    }
    
    public void prepAddlocatEngin(){
        nomLocEngin = "";
    }
    public void addLocatEngin(){
        if(!nomLocEngin.isEmpty()){
            try { 
                    locataireEngin = new LocataireEngin();
                    locataireEngin.setNomLOCATAIRE(nomLocEngin);
                    locataireEnginService.addLocatEngin(locataireEngin);
            } catch (Exception e) {
                System.out.println("Erreur d'enregistrement sst Engin car "+e.getMessage());
            }
        }
        locataireEngins = locataireEnginService.allLocatEngin();
    }

    public void addEngin() {
        this.filtredengins = null;
//        if (Ckilom == true) {
//            enginsToAdd.setCompteurKilometrique(0);
//        }
//        if (Choraire == true) {
//            enginsToAdd.setComteurHoraire(0);
//        }
       // enginsToAdd.setMarque(marque);
       
        enginsToAdd.setEtat("EN_MARCHE");
        enginsToAdd.setArchive(Boolean.FALSE);
        enginsToAdd.setReforme(Boolean.FALSE);
        enginsToAdd.setDateAFFECTATION(new Date());

        enginsToAdd.setConducteur(salarieService.getSalarie(idConducteur));
        enginSerive.addEngin(enginsToAdd, chantierToAdd);

        ELContext elContext = FacesContext.getCurrentInstance().getELContext();

        ma.bservices.tgcc.mb.services.EnginMb engin_bean = (ma.bservices.tgcc.mb.services.EnginMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "enginServMb");

        engin_bean.reload();

       // this.engins = engin_bean.getL_engins_non_archives();
         this.engins = enginSerive.findAll();
        
        

//        engins = enginSerive.findOneByArchive();
        enginsToAdd = new Engin();
        marque = "";
        chantierToAdd = 0;
        cKilom = false;
        cHoraire = false;

        FacesContext context = FacesContext.getCurrentInstance();

        context.addMessage(null, new FacesMessage("" + Message.ADD_ENGIN, ""));

        RequestContext rc = RequestContext.getCurrentInstance();
        rc.execute("PF('dlg2').hide()");
        designation = new Designation();
    }

    public List<String> listMarque(String query) {
        listMarque = enginSerive.findAllMarque();
        List<String> results = new ArrayList<String>();
        for (int i = 0; i < listMarque.size(); i++) {
            results.add(query + (listMarque.get(i)));
        }
//        listMarque.add(query);
        return results;
    }
    public void prepareEngins() {
        fournisseurEngins = fournisseurEnginService.allFournisseurEngin();
        typeEngins = typeEnginService.allTypeEngin();
        familleEngins = familleEnginService.allFamilleEngin();
        conducteurEngins = enginSerive.allConducteur();
        marques=marqueService.allMarque();
        designiations = designiationService.allDesignation(); 
        sstEngins = sstEnginService.allSstEngin();
        locataireEngins = locataireEnginService.allLocatEngin();
    }
    
    public void familleEnginChange(){
        designiations = designiationService.allDesignationByFamille(enginsToAdd.getFamilleEngin());
    }
    public void preparFamilleEngin(){
        familleEngin=new FamilleEngin();
    }
    public void addFamilleEngin(){
        familleEnginService.addFamilleEngin(familleEngin);
        familleEngins = familleEnginService.allFamilleEngin();
    }
    public void prepareModifEngins() { 
        familleEngins       = familleEnginService.allFamilleEngin();
        fournisseurEngins   = fournisseurEnginService.allFournisseurEngin();
        conducteurEngins    = enginSerive.allConducteur();
        marques             = marqueService.allMarque();
        designiations       = designiationService.allDesignation(); 
        try { 
        String typeM = infoEngin.getTypeCompteur();
            if(typeM.equals("kilométrique_horaire")){
                infoEngin.setComteurHoraire(infoEngin.getComteurHoraire());
                infoEngin.setCompteurKilometrique(infoEngin.getCompteurKilometrique());
            } else if(typeM.equals("kilométrique") ){
                infoEngin.setCompteurKilometrique(infoEngin.getCompteurKilometrique());
            }else if(typeM.equals("horaire") ){
                infoEngin.setComteurHoraire(infoEngin.getComteurHoraire());
            }
        } catch (Exception e) {
            looger.warning("Erreur de charger type de pointage car : "+e.getMessage());
        }
    }

    public void affecterEngin() {
        this.filtredengins = null;

        enginSerive.affecterEngin(engin, chantierAffectation);
       // engins = enginSerive.findAll();
        //  engins = enginSerive.findOneByArchive();

        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("" + Message.AFFECT_ENGIN, ""));
    }

    public void archiver(String s) throws IOException {
        engin.setArchive(true);
        enginSerive.updateArchiveEngin(engin);
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("" + Message.ARCHIVE_ENGIN, ""));
        if("reforme".equals(s)){
            engins = enginSerive.enginsReforme();
        }else{
            engins = enginSerive.enginsActif();
        }
    }


    public void integrer(String s) throws IOException {
        engin.setArchive(false);
        engin.setReforme(false);
        System.out.println("===================> engin "+engin.toString());
        enginSerive.updateArchiveEngin(engin);
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("" + Message.REINTEGRE_ENGIN, ""));
        if("reforme".equals(s)){
            engins = enginSerive.enginsReforme();
        }else{
            engins = enginSerive.enginsActif();
        }
    }

    public void reforme() throws IOException {
        engin.setReforme(Boolean.TRUE);
        enginSerive.updateArchiveEngin(engin);
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("" + Message.REFORME_ENGIN, ""));
        engins = enginSerive.enginsActif();
    }

    public void updateEnginInfo() {

        Chantier cha = chantierService.findById(chantierToAdd);

        if (cha != null) {
            infoEngin.setPrjapId(cha);
        } 
        try {
            Engin oldE = enginSerive.findOneById(infoEngin.getIDEngin());
            Authentication auth = SecurityContextHolder.getContext().getAuthentication(); 
            TraceUser t = new TraceUser();
            t.setAction("Modification Engin");
            t.setDateIntervention(new Date());
            t.setUser(auth.getPrincipal().toString());
            t.setAction(oldE.toString());
            traceUserService.addTraceUser(t);
            System.out.println(traceUserService.allTraceUser());
        } catch (Exception e) {
            System.out.println("Erreur d'enregistrement de la trace de modification d'engin car "+e.getMessage());
        }
                    
        enginSerive.updateArchiveEngin(infoEngin);

        cha = null; //pour les nouvelles modifications
        FacesContext context = FacesContext.getCurrentInstance();

        String message = "Cet engin est modifié avec succès";
        context.addMessage(null, new FacesMessage("Successful " + message, ""));
    }

    /**
     * afficher detail engin
     *
     * @param selected
     */
    public void afficherDetailEngin(Engin selected) {
        infoEngin = selected;
    }

    /**
     * convert format date
     *
     * @param date
     * @return
     */
    public String convertToFormat(Date date) {
        Outils outils = new Outils();
        return outils.convertDate_To_string(date);
    }

    public void initChan() {
        chantierSearch = null;
        searchEngin = new Engin();
        idEtatSearch = "";
    }

    /**
     * recharger les doones dans liste engins
     */
    public void afficherEngins() {
        this.engins = enginSerive.findAll();
    }
    public void enginsActif() {
        this.engins = enginSerive.enginsActif();
    }
    public void enginsArchive() {
        this.engins = enginSerive.enginsArchive();
    }
    public void enginsDept() {
        Chantier chantierDept = chantierService.findByName("DEPARTEMENT LOGISTIQUE");
        this.engins = enginSerive.findAllInChantier(chantierDept.getId());
    }
    public void editEnginsDept( Engin en) {
        try {
            if(en != null){
                    enginSerive.updateEngin(en);
            }
        } catch (Exception e) {
            System.out.println("Erreur de modification d'engin car "+e.getMessage());
        }
    }
    public void enginsReforme() {
        this.engins = enginSerive.enginsReforme();
    }
    public void redirectEngin_Document(Engin selected) throws IOException {
        System.out.println("redirect");
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect(ec.getRequestContextPath() + "/engin/DocumentEngin.xhtml?faces-redirect=true&enginId=" + selected.getIDEngin());
    } 
    public void redirectEngin_Intervention(Engin selected) throws IOException {
        System.out.println("redirect");
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect(ec.getRequestContextPath() + "/engin/InterventionEngin.xhtml?faces-redirect=true&enginId=" + selected.getIDEngin());
    }    
    public void changeDesignation(){
        System.out.println("==================> idDesignation : "+idDesignation);
        designation = designiationService.designationById(idDesignation);
        System.out.println("==================> designation : "+designation.toString());
          Short act = 0;
        try { 
                act = Short.parseShort(designation.getTypePointage());
        } catch (Exception e) {
            looger.warning("Erreur de convertir type de pointage car "+e.getMessage());
        }
        System.out.println("==================> act : "+act);
        enginsToAdd.setActif(act);
        enginsToAdd.setTypeCompteur(designation.getTypeCompteur());
        enginsToAdd.setCode(enginSerive.nextCod(designation.getNomDesignation(),designation.getCodeDesignation()));
        enginsToAdd.setFamilleEngin(designation.getFamilleEngin());
        enginsToAdd.setDesignation(designation.getNomDesignation());
        System.out.println("==================> enginsToAdd : "+enginsToAdd.toString());
    }
    public void preparAddDesignation(){
        designation=new Designation();
    }
    public void preparAddMarque(){
        marqueClass=new Marque();
        typeEngins = typeEnginService.allTypeEngin();
    }
    public void addDesignation(){
        designation.setCodeDesignation(designation.getCodeDesignation().toUpperCase());
        designation.setFamilleEngin(designation.getFamilleEngin().toUpperCase());
        designation.setNomDesignation(designation.getNomDesignation().toUpperCase());
        designiationService.addDesignation(designation);
        designiations=designiationService.allDesignation();
    }
    public void addMarque(){
        marqueClass.setNom(marqueClass.getNom().toUpperCase());
        marqueService.addMarque(marqueClass);
        marques=marqueService.allMarque();
    }
    public void addTypeEngin(){
        typeEnginService.addTypeEngin(typeEngin);
        typeEngin= new  TypeEngin();
        typeEngins = typeEnginService.allTypeEngin();
    }
    public void addFournisseurEngin(){
        fournisseurEnginService.addFournisseurEngin(fournisseurEngin);
        fournisseurEngin=new FournisseurEngin();
        fournisseurEngins=fournisseurEnginService.allFournisseurEngin();
    }
            
    public void typeEnginChange(){
        marques=marqueService.allMarqueByType(enginsToAdd.getTypeEngin());
    }
    
    public String imprimerFicheEngin(Engin eng) throws IOException{
        String chenimFiche="";
        System.out.println("Engin  : "+eng.toString());
        try { 
                    String chemin  = TgccFileManager.getArboFichier("FichesEngin"); 
                    System.out.println("chemin : "+chemin);
                    Path folder = Paths.get(chemin);
                    Files.createDirectories(folder);
                    System.out.println("folder : "+folder);
                    System.out.println("Engin  : "+eng.toString());
                    FicheEngin f = new FicheEngin();
                    chenimFiche=f.editeFicheEngin(chemin, eng);
        } catch (Exception e) {
            System.out.println("Erreur de génération de la fiche car "+e.getMessage());
        }
        pdfFicheEngin=ConstanteMb.getRepertoire()+"/"+chenimFiche;
        CiterneServiceBean c = new CiterneServiceBean();
        c.telecharger_fichier(pdfFicheEngin);
        System.out.println("pdfFicheEngin  : "+pdfFicheEngin);
        
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect(pdfFicheEngin);
    
        return chenimFiche;
    }
    
    
    
    /**
     * getters and setters
     *
     * @return
     */
    
    public String getNomSstEngin() {    
        return nomSstEngin;
    }

    public void setNomSstEngin(String nomSstEngin) {
        this.nomSstEngin = nomSstEngin;
    }

    public String getNomLocEngin() {
        return nomLocEngin;
    }

    public void setNomLocEngin(String nomLocEngin) {
        this.nomLocEngin = nomLocEngin;
    }
    
    public SstEngin getSstEngin() {
        return sstEngin;
    }

    public void setSstEngin(SstEngin sstEngin) {
        this.sstEngin = sstEngin;
    }

    public LocataireEngin getLocataireEngin() {
        return locataireEngin;
    }
    public void setLocataireEngin(LocataireEngin locataireEngin) {
        this.locataireEngin = locataireEngin;
    }

    public ISstEnginService getSstEnginService() {    
        return sstEnginService;
    }

    public void setSstEnginService(ISstEnginService sstEnginService) {
        this.sstEnginService = sstEnginService;
    }

    public ILocataireEnginService getLocataireEnginService() {
        return locataireEnginService;
    }

    public void setLocataireEnginService(ILocataireEnginService locataireEnginService) {
        this.locataireEnginService = locataireEnginService;
    }

    public List<SstEngin> getSstEngins() {    
        return sstEngins;
    }

    public void setSstEngins(List<SstEngin> sstEngins) {
        this.sstEngins = sstEngins;
    }

    public List<LocataireEngin> getLocataireEngins() {
        return locataireEngins;
    }

    public void setLocataireEngins(List<LocataireEngin> locataireEngins) {
        this.locataireEngins = locataireEngins;
    }

    public String getPdfFicheEngin() {    
        return pdfFicheEngin;
    }
    public void setPdfFicheEngin(String pdfFicheEngin) {
        this.pdfFicheEngin = pdfFicheEngin;
    }    

    public FournisseurEngin getFournisseurEngin() {
        return fournisseurEngin;
    }
    public void setFournisseurEngin(FournisseurEngin fournisseurEngin) {
        this.fournisseurEngin = fournisseurEngin;
    }

    public IFournisseurEnginService getFournisseurEnginService() {
        return fournisseurEnginService;
    }

    public void setFournisseurEnginService(IFournisseurEnginService fournisseurEnginService) {
        this.fournisseurEnginService = fournisseurEnginService;
    }

    public List<FournisseurEngin> getFournisseurEngins() {
        return fournisseurEngins;
    }

    public void setFournisseurEngins(List<FournisseurEngin> fournisseurEngins) {
        this.fournisseurEngins = fournisseurEngins;
    }

    public ITypeEnginService getTypeEnginService() {
        return typeEnginService;
    }
    public void setTypeEnginService(ITypeEnginService typeEnginService) {
        this.typeEnginService = typeEnginService;
    }

    public TypeEngin getTypeEngin() {
        return typeEngin;
    }
    public void setTypeEngin(TypeEngin typeEngin) {
        this.typeEngin = typeEngin;
    }

    public List<TypeEngin> getTypeEngins() {
        return typeEngins;
    }

    public void setTypeEngins(List<TypeEngin> typeEngins) {
        this.typeEngins = typeEngins;
    }

    public IFamilleEnginService getFamilleEnginService() {
        return familleEnginService;
    }

    public void setFamilleEnginService(IFamilleEnginService familleEnginService) {
        this.familleEnginService = familleEnginService;
    }

    public List<FamilleEngin> getFamilleEngins() {
        return familleEngins;
    }

    public void setFamilleEngins(List<FamilleEngin> familleEngins) {
        this.familleEngins = familleEngins;
    }

    public FamilleEngin getFamilleEngin() {
        return familleEngin;
    }
    public void setFamilleEngin(FamilleEngin familleEngin) {
        this.familleEngin = familleEngin;
    }

    public Boolean getcKilom() {
        return cKilom;
    }   

    public void setcKilom(Boolean cKilom) {
        this.cKilom = cKilom;
    }

    public Boolean getcHoraire() {
        return cHoraire;
    }
    public void setcHoraire(Boolean cHoraire) {
        this.cHoraire = cHoraire;
    }

    public Marque getMarqueClass() {
        return marqueClass;
    }   
    
    public Integer getIdConducteur() {    
        return idConducteur;
    }
    public void setIdConducteur(Integer idConducteur) {
        this.idConducteur = idConducteur;
    }

    public void setMarqueClass(Marque marqueClass) {
        this.marqueClass = marqueClass;
    }

    public Integer getIdDesignation() {
        return idDesignation;
    }

    public void setIdDesignation(Integer idDesignation) {
        this.idDesignation = idDesignation;
    }
    
    private static final Logger looger = Logger.getLogger(ParcEnginsMb.class.getName());

    public void setDesignation(Designation designation) {
        this.designation = designation;
    }

    public Designation getDesignation() {
        return designation;
    }

    
    public IDesignationServices getDesigniationService() {
        return designiationService;
    }

    public List<Designation> getDesigniations() {
        return designiations;
    }

    public void setDesigniationService(IDesignationServices designiationService) {
        this.designiationService = designiationService;
    }

    public void setDesigniations(List<Designation> designiations) {
        this.designiations = designiations;
    }

    
    public List<Marque> getMarques() {
        return marques;
    }

    public void setMarques(List<Marque> marques) {
        this.marques = marques;
    }    
 
    public void setMarqueService(IMarqueService marqueService) {
        this.marqueService = marqueService;
    }

    public IMarqueService getMarqueService() {
        return marqueService;
    }

    public void setConducteur(Salarie conducteur) {
        this.conducteur = conducteur;
    }

    public Salarie getConducteur() {
        return conducteur;
    }

    public ChantierService getChantierService() {
        return chantierService;
    }

    public void setChantierService(ChantierService chantierService) {
        this.chantierService = chantierService;
    }

    public String getCompValue() {
        return compValue;
    }

    public void setCompValue(String compValue) {
        this.compValue = compValue;
    }

    public Engin getInfoEngin() {
        return infoEngin;
    }

    public void setInfoEngin(Engin infoEngin) {
        this.infoEngin = infoEngin;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public List<String> getListMarque() {
        return listMarque;
    }

    public void setListMarque(List<String> listMarque) {
        this.listMarque = listMarque;
    }

    public Boolean getDisable() {
        return disable;
    }

    public void setDisable(Boolean disable) {
        this.disable = disable;
    }

    public List<Engin> getSearchEnginList() {
        return searchEnginList;
    }

    public void setSearchEnginList(List<Engin> searchEnginList) {
        this.searchEnginList = searchEnginList;
    }

    public Engin getSearchEngin() {
        return searchEngin;
    }

    public void setSearchEngin(Engin searchEngin) {
        this.searchEngin = searchEngin;
    }

    public List<Engin> getFiltredengins() {
        return filtredengins;
    }

    public void setFiltredengins(List<Engin> filtredengins) {
        this.filtredengins = filtredengins;
    }

    public int getChantierToAdd() {
        return chantierToAdd;
    }

    public void setChantierToAdd(int chantierToAdd) {
        this.chantierToAdd = chantierToAdd;
    }

    public Integer getChantierSearch() {
        return chantierSearch;
    }

    public void setChantierSearch(Integer chantierSearch) {
        this.chantierSearch = chantierSearch;
    }

    public int getChantierAffectation() {
        return chantierAffectation;
    }

    public void setChantierAffectation(int chantierAffectation) {
        this.chantierAffectation = chantierAffectation;
    }

    public String getIdEtatSearch() {
        return idEtatSearch;
    }

    public void setIdEtatSearch(String idEtatSearch) {
        this.idEtatSearch = idEtatSearch;
    }

    public String getMarqueSearch() {
        return marqueSearch;
    }

    public void setMarqueSearch(String marqueSearch) {
        this.marqueSearch = marqueSearch;
    }

    public Engin getEngin() {
        return engin;
    }

    public void setEngin(Engin engin) {
        this.engin = engin;
    }

    public EtatService getEtatService() {
        return etatService;
    }

    public void setEtatService(EtatService etatService) {
        this.etatService = etatService;
    }

    public List<EtatEngin> getEtats() {
        return etats;
    }

    public void setEtats(List<EtatEngin> etats) {
        this.etats = etats;
    }

    public Engin getEnginsToAdd() {
        return enginsToAdd;
    }

    public void setEnginsToAdd(Engin enginsToAdd) {
        this.enginsToAdd = enginsToAdd;
    }

    public ParcEnginsMb(EnginService enginSerive, List<Engin> engins) {

        this.enginSerive = enginSerive;
        this.engins = engins;
    }

    public EnginService getEnginSerive() {
        return enginSerive;
    }

    public List<Engin> getEngins() {
        return engins;
    }

    public void setEnginSerive(EnginService enginSerive) {
        this.enginSerive = enginSerive;
    }

    public ParcEnginsMb() {
    }

    public void setEngins(List<Engin> engins) {
        this.engins = engins;
    }

    public void setSizeCus(int sizeCus) {
        this.sizeCus = sizeCus;
    }

    public int getSizeCus() {
        return sizeCus;
    }

    public DocumentEnginService getDocumentEnginService() {
        return documentEnginService;
    }

    public void setDocumentEnginService(DocumentEnginService documentEnginService) {
        this.documentEnginService = documentEnginService;
    }

    public List<Salarie> getConducteurEngins() {
        return conducteurEngins;
    }

    public void setConducteurEngins(List<Salarie> conducteurEngins) {
        this.conducteurEngins = conducteurEngins;
    }

    public SalarieService getSalarieService() {
        return salarieService;
    }

    public void setSalarieService(SalarieService salarieService) {
        this.salarieService = salarieService;
    }

    public ITraceUserService getTraceUserService() {
        return traceUserService;
    }

    public void setTraceUserService(ITraceUserService traceUserService) {
        this.traceUserService = traceUserService;
    }
    
    
    
    
}
