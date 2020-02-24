package ma.bservices.mb;

//import com.rivetlogic.core.cma.repo.Ticket;
import com.itextpdf.text.DocumentException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import ma.bservice.tgcc.Constante.Constante;
import ma.bservices.beans.Absence;
import ma.bservices.beans.AffectationSalarieSupp;
import ma.bservices.beans.Chantier;
import ma.bservices.beans.Contrat;
import ma.bservices.beans.Document;
import ma.bservices.beans.Enfant;
import ma.bservices.beans.Etat;
import ma.bservices.beans.EtatContrat;
import ma.bservices.beans.Fonction;
import ma.bservices.beans.HistoriqueContrat;
import ma.bservices.beans.ModelContrat;
import ma.bservices.beans.Motif;
import ma.bservices.beans.OutilTravail;
import ma.bservices.beans.Presence;
import ma.bservices.beans.Salarie;
import ma.bservices.beans.Statut;
import ma.bservices.beans.TypeDocument;
import ma.bservices.constantes.Constantes;
import ma.bservices.mb.services.Evol_FonctionMb;
import ma.bservices.mb.services.Module;
//import ma.bservices.metier.ContratManager;
//import ma.bservices.metier.ContratManagerImpl;
import ma.bservices.services.AbsenceService;
import ma.bservices.services.AffectationSSuppService;
import ma.bservices.services.ChantierService;
import ma.bservices.services.ContratService;
import ma.bservices.services.DocumentService;
import ma.bservices.services.EnfantService;
import ma.bservices.services.OutilTravailService;
import ma.bservices.services.ParametrageService;
import ma.bservices.services.PresenceService;
import ma.bservices.services.SalarieService;
import ma.bservices.tgcc.authentification.Authentification;
import ma.bservices.tgcc.service.Mensuel.TauxHoraireService;
import ma.bservices.tgcc.utilitaire.TgccFileManager;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import org.apache.commons.io.FilenameUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.UploadedFile;
import org.primefaces.context.RequestContext;
import org.springframework.stereotype.Component;

/**
 *
 * @author Mahdi
 */
@Component
@ManagedBean(name = "detailMb")
@ViewScoped
public class DetailSalarieMb implements Serializable {

    @ManagedProperty(value = "#{parametrageService}")
    private ParametrageService parametrageService;
    @ManagedProperty(value = "#{salarieService}")
     
    private SalarieService salarieService;
    private Salarie salarie;
    private Integer idUser;
    private UploadedFile userPhotoUploaded;
    private Document document;
    private static String chemin;
    private String cheminPhotoSalarie;
    private String idStatut;
    private List<Fonction> fonctions;
    private Boolean invalideCNSS, invalideRib, etatBtn, existCin;
    private Integer nombreDocumentsNonAjoutes;
    //document
    @ManagedProperty(value = "#{documentServiceEvol}")
    private DocumentService documentService;
    private List<Document> docs;
    private Document docToAdd = new Document();
    private Document docToEdit = new Document();
    private UploadedFile docUploaded;
    private UploadedFile contratUploaded;
    private String cheminDocument;
    private String codeCurrent;
    private String headerText;
    private String docStatus;

    //enfant 
    @ManagedProperty(value = "#{enfantService}")
    private EnfantService enfantService;
    private List<Enfant> enfants;
    private Enfant newEnfant = new Enfant();
    private Enfant enf = new Enfant();
    //contrat
    @ManagedProperty(value = "#{contratService}")
    private ContratService contratService;
    private List<Contrat> contrats;
    private Contrat newContrat = new Contrat();
    private Contrat contratToEdit = new Contrat();
    private Integer idFonctContrat, idDuree, idPeriode, idPreavis, idModel, idTypeContrat, typeID, id_Pays;
    //Chantier
    @ManagedProperty(value = "#{chantierServiceEvol}")
    private ChantierService chantierService;
    private List<Chantier> chantiers;
    private List<Chantier> nonAffectChantier;
    private Integer idchantier, idchantierPresence;
    //Présences
    @ManagedProperty(value = "#{presenceService}")
    private PresenceService presenceService;
    private List<Presence> presences;
    private Date de, a;
    private String sum;
    //Absences
    @ManagedProperty(value = "#{absenceServiceEvol}")
    private AbsenceService absenceService;
    private List<Absence> absences;
    private Absence newAbsence = new Absence();
    private Integer idtypeAbs, minFin, minDebut, heureDebut, heureFin;
    //outils
    @ManagedProperty(value = "#{outilService}")
    private OutilTravailService outilService;
    private List<OutilTravail> outils;
    private OutilTravail newOutil = new OutilTravail();
    private Integer idCAT;
    //TauxHoraire

    @ManagedProperty(value = "#{tauxHoraireService}")
    private TauxHoraireService tauxHoraireService;
    private Fonction fonctionTauxHoraire;
    private boolean addContratBtnActive = true, activeChampContrat = false;
    private Integer fonctionId;

    /**
     * variable modification des contrat
     */
    private List<HistoriqueContrat> histoContrat;
    private String contratStatutCode;
    private Integer contratFonctionId, motif_FinContrat_Id, idEtatContrat;
    private List<Fonction> fonctionParCategorie;
    private Float taux_a_modifer;
    private Date dateFinContrat_modifier = new Date();
    Evol_FonctionMb fonctionMb;
    private String addContratLegal, changeTaux, regleOvrier, reinitialiser;
    //affectations salarie supp
    @ManagedProperty(value = "#{affectationSSupService}")
    private AffectationSSuppService affectSupService;

//    ContratManager contratManager = new ContratManagerImpl();
    Constantes constantes = Constantes.getInstance();
    private Integer id_Etat, id_ModeP, id_Situation, id_Civilite, id_Casque, id_Gilet, id_Pointure, id_Taille, id_Nationalite;
    
    //Getter & Setter

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }    

    public Integer getFonctionId() {
        return fonctionId;
    }

    public void setFonctionId(Integer fonctionId) {
        this.fonctionId = fonctionId;
    }

    public String getContratStatutCode() {
        return contratStatutCode;
    }

    public void setContratStatutCode(String contratStatutCode) {
        this.contratStatutCode = contratStatutCode;
    }

    public List<Fonction> getFonctionParCategorie() {
        return fonctionParCategorie;
    }

    public void setFonctionParCategorie(List<Fonction> fonctionParCategorie) {
        this.fonctionParCategorie = fonctionParCategorie;
    }

    public Integer getContratFonctionId() {
        return contratFonctionId;
    }

    public void setContratFonctionId(Integer contratFonctionId) {
        this.contratFonctionId = contratFonctionId;
    }

    public Integer getNombreDocumentsNonAjoutes() {
        return nombreDocumentsNonAjoutes;
    }

    public void setNombreDocumentsNonAjoutes(Integer nombreDocumentsNonAjoutes) {
        this.nombreDocumentsNonAjoutes = nombreDocumentsNonAjoutes;
    }

    public String getAddContratLegal() {
        return addContratLegal;
    }

    public void setAddContratLegal(String addContratLegal) {
        this.addContratLegal = addContratLegal;
    }

    public String getChangeTaux() {
        return changeTaux;
    }

    public void setChangeTaux(String changeTaux) {
        this.changeTaux = changeTaux;
    }

    public String getRegleOvrier() {
        return regleOvrier;
    }

    public void setRegleOvrier(String regleOvrier) {
        this.regleOvrier = regleOvrier;
    }

    public String getReinitialiser() {
        return reinitialiser;
    }

    public void setReinitialiser(String reinitialiser) {
        this.reinitialiser = reinitialiser;
    }

    public Integer getIdEtatContrat() {
        return idEtatContrat;
    }

    public void setIdEtatContrat(Integer idEtatContrat) {
        this.idEtatContrat = idEtatContrat;
    }

    public Integer getMotif_FinContrat_Id() {
        return motif_FinContrat_Id;
    }

    public void setMotif_FinContrat_Id(Integer motif_FinContrat_Id) {
        this.motif_FinContrat_Id = motif_FinContrat_Id;
    }

    public Date getDateFinContrat_modifier() {
        return dateFinContrat_modifier;
    }

    public void setDateFinContrat_modifier(Date dateFinContrat_modifier) {
        this.dateFinContrat_modifier = dateFinContrat_modifier;
    }

    public Float getTaux_a_modifer() {
        return taux_a_modifer;
    }

    public void setTaux_a_modifer(Float taux_a_modifer) {
        this.taux_a_modifer = taux_a_modifer;
    }

    public List<HistoriqueContrat> getHistoContrat() {
        return histoContrat;
    }

    public void setHistoContrat(List<HistoriqueContrat> histoContrat) {
        this.histoContrat = histoContrat;
    }

    public Constantes getConstantes() {
        return constantes;
    }

    public void setConstantes(Constantes constantes) {
        this.constantes = constantes;
    }

    public Integer getId_Pays() {
        return id_Pays;
    }

    public void setId_Pays(Integer id_Pays) {
        this.id_Pays = id_Pays;
    }

    public Integer getId_Nationalite() {
        return id_Nationalite;
    }

    public void setId_Nationalite(Integer id_Nationalite) {
        this.id_Nationalite = id_Nationalite;
    }

    public Integer getId_Etat() {
        return id_Etat;
    }

    public void setId_Etat(Integer id_Etat) {
        this.id_Etat = id_Etat;
    }

    public String getIdStatut() {
        return idStatut;
    }

    public void setIdStatut(String idStatut) {
        this.idStatut = idStatut;
    }

    public List<Fonction> getFonctions() {
        return fonctions;
    }

    public void setFonctions(List<Fonction> fonctions) {
        this.fonctions = fonctions;
    }

    public Evol_FonctionMb getFonctionMb() {
        return fonctionMb;
    }

    public void setFonctionMb(Evol_FonctionMb fonctionMb) {
        this.fonctionMb = fonctionMb;
    }

    public Integer getId_Taille() {
        return id_Taille;
    }

    public void setId_Taille(Integer id_Taille) {
        this.id_Taille = id_Taille;
    }

    public Integer getId_ModeP() {
        return id_ModeP;
    }

    public void setId_ModeP(Integer id_ModeP) {
        this.id_ModeP = id_ModeP;
    }

    public Integer getId_Situation() {
        return id_Situation;
    }

    public void setId_Situation(Integer id_Situation) {
        this.id_Situation = id_Situation;
    }

    public Integer getId_Civilite() {
        return id_Civilite;
    }

    public void setId_Civilite(Integer id_Civilite) {
        this.id_Civilite = id_Civilite;
    }

    public Integer getId_Casque() {
        return id_Casque;
    }

    public UploadedFile getContratUploaded() {
        return contratUploaded;
    }

    public void setContratUploaded(UploadedFile contratUploaded) {
        this.contratUploaded = contratUploaded;
    }

    public void setId_Casque(Integer id_Casque) {
        this.id_Casque = id_Casque;
    }

    public Integer getId_Gilet() {
        return id_Gilet;
    }

    public void setId_Gilet(Integer id_Gilet) {
        this.id_Gilet = id_Gilet;
    }

    public Integer getId_Pointure() {
        return id_Pointure;
    }

    public Contrat getContratToEdit() {
        return contratToEdit;
    }

    public void setContratToEdit(Contrat contratToEdit) {
        this.contratToEdit = contratToEdit;
    }

    public void setId_Pointure(Integer id_Pointure) {
        this.id_Pointure = id_Pointure;
    }

    public Enfant getEnf() {
        return enf;
    }

    public void setEnf(Enfant enf) {
        this.enf = enf;
    }

    public String getDocStatus() {
        return docStatus;
    }

    public void setDocStatus(String docStatus) {
        this.docStatus = docStatus;
    }

    public Integer getTypeID() {
        return typeID;
    }

    public AffectationSSuppService getAffectSupService() {
        return affectSupService;
    }

    public void setAffectSupService(AffectationSSuppService affectSupService) {
        this.affectSupService = affectSupService;
    }

    public void setTypeID(Integer typeID) {
        this.typeID = typeID;
    }

    public Integer getMinFin() {
        return minFin;
    }

    public void setMinFin(Integer minFin) {
        this.minFin = minFin;
    }

    public Integer getMinDebut() {
        return minDebut;
    }

    public void setMinDebut(Integer minDebut) {
        this.minDebut = minDebut;
    }

    public Integer getHeureDebut() {
        return heureDebut;
    }

    public void setHeureDebut(Integer heureDebut) {
        this.heureDebut = heureDebut;
    }

    public Integer getHeureFin() {
        return heureFin;
    }

    public void setHeureFin(Integer heureFin) {
        this.heureFin = heureFin;
    }

    public void setIdCAT(Integer idCAT) {
        this.idCAT = idCAT;
    }

    public Integer getIdCAT() {
        return idCAT;
    }

    public Integer getIdDuree() {
        return idDuree;
    }

    public void setIdDuree(Integer idDuree) {
        this.idDuree = idDuree;
    }

    public String getCodeCurrent() {
        return codeCurrent;
    }

    public void setCodeCurrent(String codeCurrent) {
        this.codeCurrent = codeCurrent;
    }

    public Document getDocToEdit() {
        return docToEdit;
    }

    public void setDocToEdit(Document docToEdit) {
        this.docToEdit = docToEdit;
    }

    public UploadedFile getDocUploaded() {
        return docUploaded;
    }

    public void setDocUploaded(UploadedFile docUploaded) {
        this.docUploaded = docUploaded;
    }

    public String getCheminDocument() {
        return cheminDocument;
    }

    public void setCheminDocument(String cheminDocument) {
        this.cheminDocument = cheminDocument;
    }

    public String getCheminPhotoSalarie() {
        return cheminPhotoSalarie;
    }

    public void setCheminPhotoSalarie(String cheminPhotoSalarie) {
        this.cheminPhotoSalarie = cheminPhotoSalarie;
    }

    public UploadedFile getUserPhotoUploaded() {
        return userPhotoUploaded;
    }

    public void setUserPhotoUploaded(UploadedFile userPhotoUploaded) {
        this.userPhotoUploaded = userPhotoUploaded;
    }

    public Integer getIdPeriode() {
        return idPeriode;
    }

    public void setIdPeriode(Integer idPeriode) {
        this.idPeriode = idPeriode;
    }

    public Document getDocument() {
        return document;
    }

    public String getHeaderText() {
        return headerText;
    }

    public void setHeaderText(String headerText) {
        this.headerText = headerText;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public static String getChemin() {
        return chemin;
    }

    public static void setChemin(String chemin) {
        DetailSalarieMb.chemin = chemin;
    }

    public Integer getIdPreavis() {
        return idPreavis;
    }

    public void setIdPreavis(Integer idPreavis) {
        this.idPreavis = idPreavis;
    }

    public Document getDocToAdd() {
        return docToAdd;
    }

    public void setDocToAdd(Document docToAdd) {
        this.docToAdd = docToAdd;
    }

    public Integer getIdModel() {
        return idModel;
    }

    public void setIdModel(Integer idModel) {
        this.idModel = idModel;
    }

    public Integer getIdTypeContrat() {
        return idTypeContrat;
    }

    public void setIdTypeContrat(Integer idTypeContrat) {
        this.idTypeContrat = idTypeContrat;
    }

    public Integer getIdFonctContrat() {
        return idFonctContrat;
    }

    public void setIdFonctContrat(Integer idFonctContrat) {
        this.idFonctContrat = idFonctContrat;
    }

    public Contrat getNewContrat() {
        return newContrat;
    }

    public void setNewContrat(Contrat newContrat) {
        this.newContrat = newContrat;
    }

    public ParametrageService getParametrageService() {
        return parametrageService;
    }

    public void setParametrageService(ParametrageService parametrageService) {
        this.parametrageService = parametrageService;
    }

    public OutilTravail getNewOutil() {
        return newOutil;
    }

    public void setNewOutil(OutilTravail newOutil) {
        this.newOutil = newOutil;
    }

    public Integer getIdtypeAbs() {
        return idtypeAbs;
    }

    public void setIdtypeAbs(Integer idtypeAbs) {
        this.idtypeAbs = idtypeAbs;
    }

    public Integer getIdchantier() {
        return idchantier;
    }

    public void setIdchantier(Integer idchantier) {
        this.idchantier = idchantier;
    }

    public Absence getNewAbsence() {
        return newAbsence;
    }

    public void setNewAbsence(Absence newAbsence) {
        this.newAbsence = newAbsence;
    }

    public SalarieService getSalarieService() {
        return salarieService;
    }

    public void setSalarieService(SalarieService salarieService) {
        this.salarieService = salarieService;
    }

    public Salarie getSalarie() {
        return salarie;
    }

    public void setSalarie(Salarie salarie) {
        this.salarie = salarie;
    }

    public EnfantService getEnfantService() {
        return enfantService;
    }

    public void setEnfantService(EnfantService enfantService) {
        this.enfantService = enfantService;
    }

    public List<Enfant> getEnfants() {
        return enfants;
    }

    public void setEnfants(List<Enfant> enfants) {
        this.enfants = enfants;
    }

    public Enfant getNewEnfant() {
        return newEnfant;
    }

    public void setNewEnfant(Enfant newEnfant) {
        this.newEnfant = newEnfant;
    }

    public DocumentService getDocumentService() {
        return documentService;
    }

    public void setDocumentService(DocumentService documentService) {
        this.documentService = documentService;
    }

    public List<Document> getDocs() {
        return docs;
    }

    public void setDocs(List<Document> docs) {
        this.docs = docs;
    }

    public ContratService getContratService() {
        return contratService;
    }

    public void setContratService(ContratService contratService) {
        this.contratService = contratService;
    }

    public List<Contrat> getContrats() {
        return contrats;
    }

    public void setContrats(List<Contrat> contrats) {
        this.contrats = contrats;
    }

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

    public List<Chantier> getNonAffectChantier() {
        return nonAffectChantier;
    }

    public void setNonAffectChantier(List<Chantier> nonAffectChantier) {
        this.nonAffectChantier = nonAffectChantier;
    }

    public PresenceService getPresenceService() {
        return presenceService;
    }

    public void setPresenceService(PresenceService presenceService) {
        this.presenceService = presenceService;
    }

    public List<Presence> getPresences() {
        return presences;
    }

    public void setPresences(List<Presence> presences) {
        this.presences = presences;
    }

    public AbsenceService getAbsenceService() {
        return absenceService;
    }

    public void setAbsenceService(AbsenceService absenceService) {
        this.absenceService = absenceService;
    }

    public List<Absence> getAbsences() {
        return absences;
    }

    public void setAbsences(List<Absence> absences) {
        this.absences = absences;
    }

    public OutilTravailService getOutilService() {
        return outilService;
    }

    public void setOutilService(OutilTravailService outilService) {
        this.outilService = outilService;
    }

    public List<OutilTravail> getOutils() {
        return outils;
    }

    public void setOutils(List<OutilTravail> outils) {
        this.outils = outils;
    }

    public Integer getIdchantierPresence() {
        return idchantierPresence;
    }

    public void setIdchantierPresence(Integer idchantierPresence) {
        this.idchantierPresence = idchantierPresence;
    }

    public Date getDe() {
        return de;
    }

    public void setDe(Date de) {
        this.de = de;
    }

    public Date getA() {
        return a;
    }

    public void setA(Date a) {
        this.a = a;
    }

    public String getSum() {
        return sum;
    }

    public void setSum(String sum) {
        this.sum = sum;
    }

    public Boolean getInvalideCNSS() {
        return invalideCNSS;
    }

    public void setInvalideCNSS(Boolean invalideCNSS) {
        this.invalideCNSS = invalideCNSS;
    }

    public Boolean getInvalideRib() {
        return invalideRib;
    }

    public void setInvalideRib(Boolean invalideRib) {
        this.invalideRib = invalideRib;
    }

    public Boolean getEtatBtn() {
        return etatBtn;
    }

    public void setEtatBtn(Boolean etatBtn) {
        this.etatBtn = etatBtn;
    }

    public Boolean getExistCin() {
        return existCin;
    }

    public void setExistCin(Boolean existCin) {
        this.existCin = existCin;
    }

    public TauxHoraireService getTauxHoraireService() {
        return tauxHoraireService;
    }

    public void setTauxHoraireService(TauxHoraireService tauxHoraireService) {
        this.tauxHoraireService = tauxHoraireService;
    }

    public Fonction getFonctionTauxHoraire() {
        return fonctionTauxHoraire;
    }

    public void setFonctionTauxHoraire(Fonction fonctionTauxHoraire) {
        this.fonctionTauxHoraire = fonctionTauxHoraire;
    }

    public boolean isAddContratBtnActive() {
        return addContratBtnActive;
    }

    public void setAddContratBtnActive(boolean addContratBtnActive) {
        this.addContratBtnActive = addContratBtnActive;
    }

    public boolean isActiveChampContrat() {
        return activeChampContrat;
    }

    public void setActiveChampContrat(boolean activeChampContrat) {
        this.activeChampContrat = activeChampContrat;
    }

//Methode
    @PostConstruct
    public void init() {
        
        enfantService = Module.ctx.getBean(EnfantService.class);
        salarieService = Module.ctx.getBean(SalarieService.class);
        documentService = Module.ctx.getBean(DocumentService.class);
        contratService = Module.ctx.getBean(ContratService.class);
        chantierService = Module.ctx.getBean(ChantierService.class);
        presenceService = Module.ctx.getBean(PresenceService.class);
        absenceService = Module.ctx.getBean(AbsenceService.class);
        outilService = Module.ctx.getBean(OutilTravailService.class);
        affectSupService = Module.ctx.getBean(AffectationSSuppService.class);
        
        //minFin = 0;
        //minDebut = 0;
        //heureDebut = 8;
        //heureFin = 18;
        int id=0;

        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        fonctionMb = (Evol_FonctionMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "evol_fonctionMb");
        
        fonctionParCategorie = fonctionMb.getFonctions();
        fonctions = fonctionMb.getFonctions();
        invalideCNSS = false;
        existCin = false;
        invalideRib = false;
        //nombreDocumentsNonAjoutes = 0;

        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        Map<String, String> requestParameters = externalContext.getRequestParameterMap();
        //System.out.println(requestParameters.containsKey("salarieId"));
        try {
             id = Integer.valueOf(requestParameters.get("salarieId"));
        } catch (Exception e) {
            System.out.println("Erreur de recuperation id car "+e.getMessage());
        }
        /*
        if(id>0){
            idSalarie=id;
        }
        if (requestParameters.containsKey("salarieId")) {
            id = Integer.valueOf(requestParameters.get("salarieId"));
            //System.out.println(id);

        } else {
            System.out.println("Erreur d'afectation d'id");
//            throw new WebServiceException("No item id in request parameters");
        }
*/
        salarie = salarieService.getSalarie(id);
//        System.out.println(salarie.toString());
        
        
        
        boolean isAdmin = false;
        if ("admin".equals(Constantes.getRoleAuth()) || "EMAIL_CONTRIBUTORS".equals(Constantes.getRoleAuth())) {
            isAdmin = true;
        }
        Authentification authentification = (Authentification) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "authentification");
        idUser = authentification.get_connected_user().getId();
        //chantiers = chantierService.listeChantiersAffectes(id, 1, 0, Integer.parseInt(chantierService.nombreChantiers("", "", Module.dos).toString()), "", "", Module.dos, isAdmin, idUser);

        if (salarie != null) {
            try {
                //sum = presenceService.nombreHeuresMinutesPresencesSalarie(salarie.getMatricule(), salarie.getCin(), salarie.getCnss(), null, null, null);
                typeID = (salarie.getType() != null) ? salarie.getType().getId() : null;
                id_Casque = (salarie.getCouleurCasque() != null) ? salarie.getCouleurCasque().getId() : null;
                id_Gilet = (salarie.getCouleurGilet() != null) ? salarie.getCouleurGilet().getId() : null;
                id_Civilite = (salarie.getCivilite() != null) ? salarie.getCivilite().getId() : null;
                id_Taille = (salarie.getTailleHabillement() != null) ? salarie.getTailleHabillement().getId() : null;
                id_Situation = (salarie.getSituationFamiliale() != null) ? salarie.getSituationFamiliale().getId() : null;
                id_Pointure = (salarie.getPointure() != null) ? salarie.getPointure().getId() : null;
                id_Etat = (salarie.getEtat() != null) ? salarie.getEtat().getId() : null;
                id_Nationalite = (salarie.getNationalite() != null) ? salarie.getNationalite().getId() : null;
                id_ModeP = (salarie.getModePaiement() != null) ? salarie.getModePaiement().getId() : null;
                id_Pays = (salarie.getPays() != null) ? salarie.getPays().getId() : null;
                idStatut = parametrageService.getStatut(salarie.getFonction().getCodeDiva().substring(0, 1)).getCodeDiva();
                nombreDocumentsNonAjoutes = salarieService.listeDocumentNonAjoutes(salarie).size();
           
            etatBtn = false;
                
            } catch (Exception e) {
            System.out.println("Erreur de recuperarer information du salarie car "+e.getMessage());
            }
        }

    }
    public void chargerPresences(){
            presences = presenceService.listePresencesSalarie(0, 10, salarieService.getSalarie(salarie.getId()).getMatricule(), null, null, null);
    }
    public void chargerDocs(){
            docs = documentService.listeDocumentsSalarie(salarie.getId(), 0, Integer.parseInt(documentService.nombreDocumentsSalarie(salarie.getId()).toString()));
    }
    public void chargerContrats(){
            contrats = contratService.listeContratsSalarie(0, Integer.parseInt(contratService.nombreContratsSalarie(salarie.getId()).toString()), salarie.getId());
    }
    public void chargerEnfants(){
            enfants = enfantService.listeEnfantsSalarie(salarie.getId(), 0, Integer.parseInt(enfantService.nombreEnfantsSalarie(salarie.getId()).toString()));
    }
    public void chargerChantiers(){
            chantiers = chantierService.listeChantiersAffectes(salarie.getId(), 1, 0, Integer.parseInt(chantierService.nombreChantiers("", "", Module.dos).toString()), "", "", Module.dos, true, idUser);
    }
    public void chargerAbsences(){
            absences = absenceService.listeAbsences(0, 10, salarie.getMatricule(), salarie.getCin(), salarie.getCnss(), null);
    }
    public void chargerOutils(){
            outils = outilService.listeOutilsTravail(salarie.getId(), 0, Integer.parseInt(outilService.nombreOutilsTravail(salarie.getId()).toString()));
    }

    public void reinitAddDoc() {
        docToAdd = new Document();
    }

    public void onRowEdit(RowEditEvent event) {
        Enfant en = (Enfant) event.getObject();
        System.out.println("modification" + en.getPrenom());
        if (en.getPrenom().equals("")) {
            en.setPrenom(en.getPrenom().substring(0, 1).toUpperCase() + en.getPrenom().substring(1).toLowerCase());
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String dateNaissanceEnfant = sdf.format(en.getDateNaissance());
        dateNaissanceEnfant = dateNaissanceEnfant.replaceAll("/", "");
        //dateNaissanceEnfant = dateNaissanceEnfant.substring(4, 8) + dateNaissanceEnfant.substring(0, 2) + dateNaissanceEnfant.substring(2, 4);
        System.out.println("date naissance: " + dateNaissanceEnfant);
        salarieService.modifierEnfantSalarieWS(salarie.getMatricule(),
                en.getPrenom(), dateNaissanceEnfant, "p");
        Map<String, String> mapModifierEnfantWS = salarieService.modifierEnfantSalarieWS(salarie.getMatricule(),
                en.getPrenom(), dateNaissanceEnfant, "dn");
        String referenceEnfantDiva = mapModifierEnfantWS.get("referenceEnfantDiva");

        // Fin de web services
        if (referenceEnfantDiva.equals("0") || referenceEnfantDiva.equals("-1")) {
            Module.message(2, "Erreur au niveau mofication sur divalto", "");
        } else {
            enfantService.modifierEnfant(en);
            Module.message(0, "Modification éffectuée", "");
        }
    }

    public void onRowCancel(RowEditEvent event) {

        FacesMessage msg = new FacesMessage("Modification Annulée", ((Enfant) event.getObject()).getId().toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void ajouterEnfant() {
        System.out.println("add enfant ");

        if (salarie != null) {
            if (salarie.getSituationFamiliale() != null) {
                if (salarie.getSituationFamiliale().getSituationFamiliale().equals("Célibataire")) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "", " Code 1202 : Impossible d'ajouter des enfants"));

                } else if (newEnfant != null) {
                    newEnfant.setSalarie(salarie);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                    String dateNaissanceEnfant = sdf.format(newEnfant.getDateNaissance());
                    dateNaissanceEnfant = dateNaissanceEnfant.replaceAll("/", "");
//                    dateNaissanceEnfant = dateNaissanceEnfant.substring(4, 8) + dateNaissanceEnfant.substring(0, 2) + dateNaissanceEnfant.substring(2, 4);
                    System.out.println("date naissance: " + dateNaissanceEnfant);
                    //appel du web service
                    Map<String, String> mapAjouterEnfantWS = salarieService.ajouterEnfantSalarieWS(salarie.getMatricule(), newEnfant.getPrenom(), dateNaissanceEnfant);
                    String reponseDiva = mapAjouterEnfantWS.get("referenceEnfantSalarieDiva");
                    // Fin appel du web service
                    if (reponseDiva.equals("0") || reponseDiva.equals("-1")) {
                        Module.message(2, "Code WS005 : erreur au niveau d'ajout d'enfant", " Code WS005 : Enfant anon ajouté \n veuillez contacter votre administrateur");
                    } else {
                        Integer ajoutEnfantSalarie = enfantService.ajouterEnfant(newEnfant);
                        System.out.println("add it");
                        enfants = enfantService.listeEnfantsSalarie(salarie.getId(), 0, Integer.parseInt(enfantService.nombreEnfantsSalarie(salarie.getId()).toString()));
                        Module.message(0, "Enfant ajouté", " Avec succés ");
                        newEnfant = new Enfant();
                    }
                }
            }
        }
    }

    public void ajouterAbsence() {
        //System.out.println("Add Absence");
        if (newAbsence != null) {
            System.out.println("Add It");
            String heureD = (heureDebut < 10) ? "0" + heureDebut : heureDebut + "";
            String minD = (minDebut < 10) ? "0" + minDebut : minDebut + "";
            String heureF = (heureFin < 10) ? "0" + heureFin : heureFin + "";
            String minF = (minFin < 10) ? "0" + minFin : minFin + "";
            newAbsence.setHeureDebut(heureD + ":" + minD);
            newAbsence.setHeureFin(heureF + ":" + minF);
            newAbsence.setTypeAbsence(parametrageService.getTypeAbsence(idtypeAbs));
            newAbsence.setSalarie(salarie);
            absenceService.ajouterAbsence(newAbsence);
            Module.message(0, "Absence Ajoutée", "avec succès ");
            absences = absenceService.listeAbsences(0, 10, salarie.getMatricule(), salarie.getCin(), salarie.getCnss(), null);
            newAbsence = new Absence();
        }
    }

    public void updateChantierNonAffecter() {
        boolean isAdmin = false;
        if ("admin".equals(Constantes.getRoleAuth()) || "EMAIL_CONTRIBUTORS".equals(Constantes.getRoleAuth())) {
            isAdmin = true;
        }
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        Authentification authentification = (Authentification) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "authentification");
        Integer idUser = authentification.get_connected_user().getId();
        nonAffectChantier = chantierService.listeChantiersAffectes(salarie.getId(), 0, 0, Integer.parseInt(chantierService.nombreChantiers("", "", Module.dos).toString()), "", "", Module.dos, isAdmin, idUser);
        //System.out.println("Non affect" + nonAffectChantier.size());
    }

    public void affectation(Integer idChantier) {
        boolean isAdmin = false;
        if ("admin".equals(Constantes.getRoleAuth()) || "EMAIL_CONTRIBUTORS".equals(Constantes.getRoleAuth())) {
            isAdmin = true;
        }
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        Authentification authentification = (Authentification) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "authentification");
        Integer idUser = authentification.get_connected_user().getId();
        chantiers = chantierService.listeChantiersAffectes(salarie.getId(), 1, 0, Integer.parseInt(chantierService.nombreChantiers("", "", Module.dos).toString()), "", "", Module.dos, isAdmin, idUser);
        // Module.message(0, "Affectation", "salarié affecté au chantier " + idChantier);
    }

    public void FilterChantier() {
        if (idchantier != null) {
            nonAffectChantier.clear();
            nonAffectChantier.add(chantierService.getChantier(idchantier));
        } else {
            updateChantierNonAffecter();
        }
    }

    public void ajouterOutil() {
        newOutil.setSalarie(salarie);
        newOutil.setCategorieOutilTravail(parametrageService.getCategorieOutilTravail(idCAT));
        outilService.ajouterOutilTravail(newOutil);
        Module.message(0, "Outil ajouté", "et affecté au salarié " + salarie.getMatricule());
        outils = outilService.listeOutilsTravail(salarie.getId(), 0, Integer.parseInt(outilService.nombreOutilsTravail(salarie.getId()).toString()));
    }

    public void supprimerOutil(OutilTravail o) {
        if (o != null) {
            outilService.supprimerOutilTravail(o);
            Module.message(0, "Outil désaffecté", "du salarié " + salarie.getMatricule());
            outils = outilService.listeOutilsTravail(salarie.getId(), 0, Integer.parseInt(outilService.nombreOutilsTravail(salarie.getId()).toString()));
        }
    }

    public void supprimerEnfant(Enfant enf) {
        if (enf != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            String dateNaissanceEnfant = sdf.format(enf.getDateNaissance());
            dateNaissanceEnfant = dateNaissanceEnfant.replaceAll("/", "");
            // Appel du web Service pour supprimer l'enfant sur Divalto
            Map<String, String> mapSupprimerEnfantWS = salarieService.supprimerEnfantSalarieWS(salarie.getId().toString(), enf.getPrenom(), dateNaissanceEnfant);
            String reponseDiva = mapSupprimerEnfantWS.get("referenceEnfantSalarieDiva");
            // Fin appel du web service
            if (reponseDiva.equals("0") || reponseDiva.equals("-1")) {
                Module.message(2, "erreur au niveau de suppression sur divalto", "");
            } else {
                // supprimer de l'enfant de la base de données si la supppression est effectuée sur divalto
                enfantService.supprimerEnfant(enf);
                Module.message(0, "Enfant supprimé", "");
                enfants = enfantService.listeEnfantsSalarie(salarie.getId(), 0, Integer.parseInt(enfantService.nombreEnfantsSalarie(salarie.getId()).toString()));
            }
        }
    }

    public void desAffectation(Integer idChantier) {
        System.out.println("@@function salarié" + salarie.getFonction().getFonction());
        if (salarie.getFonction().getId() == Constante.FONCTION_ID_CHEF_EQUIPE) {
            affectSupService.desaffectation(chantierService.getChantier(idChantier), salarie);
        } else {
            AffectationSalarieSupp sup = affectSupService.getLastSuperiorBySalarieAndChantier(salarie, idChantier);
            if (sup != null) {
                sup.setCurrentSupp(Boolean.FALSE);
                affectSupService.updateSalarie(sup);
            }

        }
        chantierService.desaffecterSalarieChantier(salarie, chantierService.getChantier(idChantier));
        boolean isAdmin = false;
        if ("admin".equals(Constantes.getRoleAuth()) || "EMAIL_CONTRIBUTORS".equals(Constantes.getRoleAuth())) {
            isAdmin = true;
        }
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        Authentification authentification = (Authentification) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "authentification");
        Integer idUser = authentification.get_connected_user().getId();
        chantiers = chantierService.listeChantiersAffectes(salarie.getId(), 1, 0, Integer.parseInt(chantierService.nombreChantiers("", "", Module.dos).toString()), "", "", Module.dos, true, idUser);
        Module.message(0, "Désaffectation du salarié", "");
    }

    public void onRowEditAbsence(RowEditEvent event) {
        Absence a1 = (Absence) event.getObject();
        absenceService.modifierAbsence(a1);
        Module.message(1, "Modification éffectuée", (a1.getId().toString()));
    }

    public void onRowCancelAbsence(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Modification Annulée", ((Absence) event.getObject()).getId().toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void findPresence() {
        Date de_Presence = null, a_Presence = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        if (de != null) {
            de_Presence = new Date(sdf.format(de));
        }
        if (a != null) {
            a_Presence = new Date(sdf.format(a));
        }
        if (de != null && a != null) {
            if (!Module.checkDate(de, null, a)) {
                Module.message(2, "date De doit être inférieur a date A", "");
                return;
            }
        }
        presences = presenceService.listePresencesSalarie(0, presenceService.nombrePresencesSalarie(salarie.getMatricule(), (idchantierPresence != null && idchantierPresence == 0) ? null : idchantierPresence, de_Presence, a_Presence), salarie.getMatricule(), (idchantierPresence != null && idchantierPresence == 0) ? null : idchantierPresence, de_Presence, a_Presence);

    }

    public void addContrat() {
        // Date de début	

        if (newContrat.getTauxHoraire() > 0) {

            Date objetDate;
            SimpleDateFormat sdfdate = new SimpleDateFormat("yyyy/MM/dd");
            if (newContrat.getDateEmbauche() == null) {
                Module.message(3, "veuillez saisir une date", "");
                return;
            }
            if (!Module.checkDate(null, new Date(), newContrat.getDateEmbauche())) {
                Module.message(3, "date d'embauche doit être en passé", "");
                return;
            }
            String date = sdfdate.format(newContrat.getDateEmbauche());
            date = date.substring(0, 10);
            date = date.replaceAll("-", "/");
            objetDate = new Date(date);

            if (contratService.verifierExistanceContrat(salarie.getId(), objetDate, null)) {
                Module.message(2, "Ce salarié a déjà un contrat en cette date", "");
                return;
            }
            if (salarie.getEtat().getId() == 2) {
                Module.message(2, "Oups! Contrat non ajouté: ", "l'Etat du Salarié est : " + salarie.getEtat().getEtat());
                return;
            }
            String nomPrenomSalarie;
            if (idModel == 2 || salarie.getNomArabe() == null || salarie.getPrenomArabe() == null || salarie.getNomArabe().equals("NC") || salarie.getPrenomArabe().equals("NC") || salarie.getNomArabe().length() == 0 || salarie.getPrenomArabe().length() == 0) {
                nomPrenomSalarie = salarie.getNom() + " " + salarie.getPrenom();
            } else {
                nomPrenomSalarie = salarie.getNomArabe() + " " + salarie.getPrenomArabe();
            }
            String adresseSalarie = salarie.getAdresseArabe();
            if (idModel == 2 || adresseSalarie == null || adresseSalarie.equals("NC") || adresseSalarie.length() == 0) {
                adresseSalarie = salarie.getAdresse();
            }
            Map parametresContrat = new HashMap();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMM dd");
            Float salaireJrnl = null;
            String salaireJrnlStr ="";
            try {
                salaireJrnl = newContrat.getTauxHoraire().floatValue() *9;
                salaireJrnlStr =String.format("%.2f",salaireJrnl);
            } catch (Exception e) {
                salaireJrnl=(float)0.00;
                System.out.println("Erreur de Calculer le salaire journalier car :"+e.getMessage());
            }
             
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

          //  String sourcePath = "sous1ContratCDD_arabe.jasper";
          //  String destinationPath = "sous1ContratCDD_arabe.jrxml";
            /*
            String sourcePath =  "/C:/Users/devup/Documents/NetBeansProjects/tgcc/target/Bservices-1.0-SNAPSHOT/WEB-INF/classes/contratCDD_arabe/sous1ContratCDD_arabe.jasper";
            String destinationPath = "/C:/Users/devup/Documents/NetBeansProjects/tgcc/target/Bservices-1.0-SNAPSHOT/WEB-INF/classes/contratCDD_arabe/sous1ContratCDD_arabe.jrxml";

            JasperReport report;
            try {
                report = (JasperReport) JRLoader.loadObject(sourcePath);
                JRXmlWriter.writeReport(report, destinationPath, "UTF-8");
            } catch (JRException ex) {
                System.out.println("====== jasper " + ex.getMessage());
                Logger.getLogger(DetailSalarieMb.class.getName()).log(Level.SEVERE, null, ex);
            }
          */
          

            String cheminSubReport = classLoader.getResource(parametrageService.getModelContrat(idModel).getFichierJrxml().substring(0, parametrageService.getModelContrat(idModel).getFichierJrxml().lastIndexOf('/'))).getPath();
            System.out.println(" test 3 " + cheminSubReport);
            parametresContrat.put("SUBREPORT_DIR", cheminSubReport + "/");
            parametresContrat.put("NOM_PRENOM", nomPrenomSalarie);
            parametresContrat.put("ADRESSE", adresseSalarie);
            parametresContrat.put("CIN", salarie.getCin());
            parametresContrat.put("FONCTION", parametrageService.getFonction(idFonctContrat).getFonction());
            parametresContrat.put("DATE_DEBUT", "");
            parametresContrat.put("Salaire_Journalier", salaireJrnlStr);
            try {
                //            Ticket ticket = null;
                String cheminContrat = genereContrat(parametresContrat, salarie, parametrageService.getModelContrat(idModel));
                System.out.println(" test 4  && Chemin Contrat" + cheminContrat);
                //
                if (parametrageService.getDuree(idDuree).getNombreMois() != null) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(newContrat.getDateEmbauche());
                    calendar.add(Calendar.MONTH, parametrageService.getDuree(idDuree).getNombreMois());
                    Date objetDateFin = calendar.getTime();
                    newContrat.setDateFin(objetDateFin);
                }
                
                //            newContrat.setNodeRefNonLegalise(nodeRefPdfContratSalarieGenere.getId());
                newContrat.setEtatContrat(parametrageService.getEtatContrat("Non légalisé"));
                newContrat.setModelContrat(parametrageService.getModelContrat(idModel));
                newContrat.setTypeContrat(parametrageService.getTypeContrat(idTypeContrat));
                newContrat.setFonction(parametrageService.getFonction(idFonctContrat));
                newContrat.setDuree(parametrageService.getDuree(idDuree));
                newContrat.setPeriodeEssai(parametrageService.getPeriodeEssai(idPeriode));
                newContrat.setPreavis(parametrageService.getPreavis(idPreavis));
                newContrat.setSalarie(salarie);
                newContrat.setNodeRefNonLegalise(cheminContrat);
                Salarie s = salarieService.getSalarie(salarie.getId());
                s.setEtat(parametrageService.getEtat(4));
                salarieService.modifierInformationsSalarie(s);
                salarie = s;
                if (contratService.ajouterContrat(newContrat) != 0) {
                    contrats = contratService.listeContratsSalarie(0, Integer.parseInt(contratService.nombreContratsSalarie(salarie.getId()).toString()), salarie.getId());
                    Module.message(0, "Contrat ajouté", "");
                    RequestContext contextDialog = RequestContext.getCurrentInstance();
                    contextDialog.execute("PF('addContrat').hide();");
                } else {
                    Module.message(3, "Erreur", "! Opération echouée");
                }
            } catch (NumberFormatException ex) {
                System.out.println("@@@@@ erreur \n\n\n genretion contrat echouee \n\n" + ex.getMessage());
                Module.message(2, "Erreur", " au moment de génération du contrat");
            }

        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "", "La fonction sélectionnée n'a pas de taux horaire, Merci de Contacter RH !"));
        }

    }

    /**
     * modification du salarie
     *
     * @throws IOException
     */
    public void modifier() throws IOException {
        //System.out.println("Modifier Sal");

        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        Authentification authentification = (Authentification) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "authentification");
//        if (!chantierService.verifierAffectation(salarie.getId(), authentification.get_connected_user().getId()) ) {
//            Module.message(2, "Vous n'avez pas le droit de modifier la fiche de ce salarié.\nVeuillez vérifier son affectation au chantier", "");
//            return;
//        }

//        Salarie salarie = salarieService.getSalarie(id_salarie);
        if (invalideCNSS) {
            Module.message(3, "CNSS INVALIDE", "");
            return;
        }

        if (invalideRib) {
            Module.message(3, "RIB INVALIDE", "");
            return;
        }

        if (id_Situation == 1 && (Long) enfantService.nombreEnfantsSalarie(salarie.getId()) > 0) {
            Module.message(1, "Code 1103: l'individu avec enfants ne peut pas passer en Célibataire", "");
            return;
        }
        salarie.setModifiePar(authentification.get_connected_user().getLogin());
        salarie.setType(parametrageService.getType(typeID));
        salarie.setFonction(parametrageService.getFonction(salarie.getFonction().getId()));
        salarie.setCivilite(parametrageService.getCivilite(id_Civilite));
        salarie.setTailleHabillement(parametrageService.getTailleHabillement(id_Taille));
        salarie.setCouleurCasque(parametrageService.getCouleurCasque(id_Casque));
        salarie.setCouleurGilet(parametrageService.getCouleurGilet(id_Gilet));
        salarie.setSituationFamiliale(parametrageService.getSituationFamiliale(id_Situation));
//        salarie.setEtat(parametrageService.getEtat(id_Etat));
        salarie.setPays(parametrageService.getPays(id_Pays));
        salarie.setNationalite(parametrageService.getNationalite(id_Nationalite));
        salarie.setModePaiement(parametrageService.getModePaiement(id_ModeP));
        salarie.setPointure(parametrageService.getPointure(id_Pointure));
        salarie.setDateModification(new Date());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Statut st = parametrageService.getStatut(idStatut);
        //on ne manipule pas une chaine de caracteres vide
        String chaineDateNaissance = salarie.getDateNaissance() != null ? sdf.format(salarie.getDateNaissance()) : "";
        System.out.println("date naissance before trait: " + chaineDateNaissance);
        if (chaineDateNaissance != null && !"".equals(chaineDateNaissance)) {
            chaineDateNaissance = chaineDateNaissance.replaceAll("/", "");
            chaineDateNaissance = chaineDateNaissance.replaceAll("-", "");
            System.out.println("date naissance after replace : " + chaineDateNaissance);
            chaineDateNaissance = chaineDateNaissance.substring(0, 4) + chaineDateNaissance.substring(4, 6) + chaineDateNaissance.substring(6, 8);
            System.out.println("date naissance after substring : " + chaineDateNaissance);
        }
        String statutCodeDiva = "";
        if (st != null) {
            statutCodeDiva = st.getCodeDiva();
        }
        Map<String, String> mapModifierSalarieWS = salarieService.modifierSalarieWS(salarie.getMatricule(), salarie.getCivilite().getCodeDiva(), salarie.getSituationFamiliale().getCodeDiva(),
                salarie.getNom(), salarie.getPrenom(), salarie.getCin(), salarie.getCnss(), chaineDateNaissance,
                salarie.getLieuNaissance(), salarie.getNationalite().getCodeDiva(), salarie.getPays().getCodeDiva(), salarie.getAdresse(), salarie.getVille(),
                salarie.getTelephone(), salarie.getGsm(), salarie.getEmail(), salarie.getRib(), salarie.getNomBanque(), statutCodeDiva, salarie.getFonction().getCodeDiva().trim(),
                salarie.getModePaiement().getCodeDiva());
        System.out.println(mapModifierSalarieWS);
        String reponseDiva = mapModifierSalarieWS.get("referenceSalarieDiva");
        System.out.println("réponse appel web service modification individu sur Divalto: " + reponseDiva);
        // --- fin d'appel de web service ---
        if (reponseDiva.equals("0") || reponseDiva.equals("-1")) {
            Module.message(2, "Informations du salarié non modifiées,Veuillez contacter votre Administrateur \n" + mapModifierSalarieWS.get("msgErreur"), "");
        } else {
            salarieService.modifierInformationsSalarie(salarie);
            Module.message(0, "Informations modifiées avec succès", "");
        }
    }

    public void initDocToShow(Document doc) {
        headerText = doc.getTitre();
        System.out.println(codeCurrent);
        codeCurrent = doc.getChemin();
        System.out.println(codeCurrent);

    }

    public void initDocToEdit(Document doc) {
        docToEdit = doc;
        docStatus = "Modifier";

    }

    public void initContratToShow(Contrat contrat) {
        contratToEdit = contrat;
        salarie = salarieService.getSalarie(contrat.getSalarie().getId());
        codeCurrent = (contrat.getNodeRefLegalise() != null) ? contrat.getNodeRefLegalise() : contrat.getNodeRefNonLegalise();
        histoContrat = contratService.historiqueContrat(0, 20, contrat.getId());
        contratStatutCode = parametrageService.getStatut(contrat.getFonction().getCodeDiva().substring(0, 1)).getCodeDiva();
        contratFonctionId = contrat.getFonction().getId();
        boolean isLast = contratService.dernierContrat(contrat.getId(), contrat.getSalarie().getId());

        taux_a_modifer = contrat.getTauxHoraire();
        System.out.println("chemin courant du contrat" + codeCurrent);
//        if (codeCurrent != null && !"".equals(codeCurrent) && codeCurrent.contains("resources")) {
//            codeCurrent = codeCurrent.substring(codeCurrent.indexOf("resources"));
//        }
        reinitialiser = "flex";
        addContratLegal = "flex";
        changeTaux = "flex";
        regleOvrier = "flex";

        if (salarie.getEtat().getId() == 6 || salarie.getEtat().getId() == 2) {
            reinitialiser = "none";
            addContratLegal = "none";
            changeTaux = "none";
            regleOvrier = "none";
        } else if (salarie.getEtat().getId() == 3) {
            reinitialiser = "flex";
            addContratLegal = "none";
            changeTaux = "none";
            regleOvrier = "none";
        } else if (salarie.getEtat().getId() == 1 || salarie.getEtat().getId() == 5) {
            reinitialiser = "none";
            addContratLegal = "flex";
            changeTaux = "flex";
            regleOvrier = "flex";
        }
        if (contrat.getEtatContrat().getId() == 3) {
            reinitialiser = "none";
            addContratLegal = "none";
            changeTaux = "none";
            regleOvrier = "none";
        } else if (contrat.getEtatContrat().getId() == 1) {
            reinitialiser = "none";
            addContratLegal = "flex";
            changeTaux = "flex";
            regleOvrier = "none";
        }
        if (contrat.getEtatContrat().getId() == 3 && isLast) {
            reinitialiser = "flex";
        }
        //System.out.println(codeCurrent);
    }

    public void updateFontion() {
        if (idStatut != null && !idStatut.equals("")) {
            fonctions = fonctionMb.foncByStatut(contratStatutCode);
        } else {
            fonctions = fonctionMb.getFonctions();
        }
    }

    public void fonctionByStatut() {
        if (contratStatutCode != null && !contratStatutCode.equals("")) {
            fonctionParCategorie = fonctionMb.foncByStatut(contratStatutCode);

        } else {
            fonctionParCategorie = fonctionMb.getFonctions();
        }
    }

    Date dateToEdit = new Date();

    public Date getDateToEdit() {
        return dateToEdit;
    }

    public void setDateToEdit(Date dateToEdit) {
        this.dateToEdit = dateToEdit;
    }

    public void testDate() {
        System.out.println("DAAAAAAAAAAAAAAAATETETETETETETE : " + dateFinContrat_modifier);
        dateToEdit = dateFinContrat_modifier;
    }

    public void editContratSalarie(FileUploadEvent event) throws IOException {
        /**
         * sauvegarde la date entrée manuellement *
         */
        // Date dateToEdit = dateFinContrat_modifier;

        // System.out.println(" DATE FIN **  : " + dateFinContrat_modifier);
        /**
         * vérifier l'éxistance du contrat avant d'ajouter
         */
        //System.out.println("Date d'embauche" + contratToEdit.getDateEmbauche());
        // Date de début Creation
        Date objetDate;
        SimpleDateFormat sdfdate = new SimpleDateFormat("yyyy/MM/dd");
        String date = sdfdate.format(contratToEdit.getDateEmbauche());
//        date = date.substring(0, 10);
//        date = date.replaceAll("-", "/");
        objetDate = new Date(date);
        /**
         * verification de l'etat du salarie et contrat (salarie en blacklist et
         * salarie avec contrat
         */
        if (salarieService.getSalarie(salarie.getId()).getEtat().getId() == 2) {
            Module.message(2, "Ce salarié a une etat Blacklist", "");
            return;
        }

        if (contratService.verifierExistanceContrat(salarie.getId(), objetDate, contratToEdit.getId())) {
            Module.message(2, "Ce salarié a déjà un contrat en cette date", "");
            return;
        }
        /**
         * modification de contrat salarié
         */
        /*repertoire pour l'enregistrement*/
        chemin = TgccFileManager.getArboFichier("ContratSalarie/Legaliser");
        Path folder = Paths.get(chemin);
        Files.createDirectories(folder);
        String filename = FilenameUtils.getBaseName(event.getFile().getFileName());
        String extension = FilenameUtils.getExtension(event.getFile().getFileName());

        if (!"PDF".equals(extension.toUpperCase())) {
            System.out.println("extention invalide " + extension);
            Module.message(2, "type de ficher invalide veuillez choisir un PDF", "");
            return;
        }
        Path file = Files.createTempFile(folder, filename + "-", "." + extension);
        try (InputStream input = event.getFile().getInputstream()) {
            Files.copy(input, file, StandardCopyOption.REPLACE_EXISTING);
            cheminDocument = chemin + "/" + file.getFileName();
            cheminDocument = cheminDocument.substring(cheminDocument.indexOf("files"));
            System.out.println(cheminDocument);
            Contrat contrat = contratService.getContrat(contratToEdit.getId());
            contrat.setNodeRefLegalise(cheminDocument);
            contratService.modifierContrat(contrat);
        }
        /**
         * calcul de la date de fin datembauche + durée contrat
         */
        Date objetDateFin = null;

        if (contratToEdit.getDuree().getNombreMois() != null) {

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(objetDate);
            calendar.add(Calendar.MONTH, contratToEdit.getDuree().getNombreMois());
            objetDateFin = calendar.getTime();

        }
        Contrat contrat = contratService.getContrat(contratToEdit.getId());
        contrat.setDateEmbauche(objetDate);
        contrat.setDateFin(objetDateFin);
        // --- Appel du web Service pour la création de contrat du salarié sur Divalto ---
        HistoriqueContrat historiqueContrat = new HistoriqueContrat();
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        Authentification authentification = (Authentification) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "authentification");
        /*traitement si le contrat n'est pas encore legalisé */
        if (contrat.getEtatContrat().getId() != 2) {
            System.out.println(" * -* -* -* - *- *- *-*-* *- *-*-*- *- *-* -* -* - *- * AAAAAAAAAAAAAAAJJJJJJJJJJJJJJJJJJJOOOOOOOOOOOOUUUUUUUUUUUUTTTTTTTTTTTT ========== *-* -*- * -*- *- *- *- *- ");
            /*preparation des données pour divalto*/
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            String dateDebutContrat = sdf.format(contrat.getDateEmbauche());
            //String dateDebutContrat = Constantes.dateAnglaisVersFrancais(contratToEdit.getDateEmbauche()).replaceAll("/", "");
            dateDebutContrat = dateDebutContrat.replace("/", "");
            System.out.println("dateDebutContrat: " + dateDebutContrat);
            String dateFinContrat = sdf.format(contrat.getDateFin());
            dateFinContrat = dateFinContrat.replace("/", "");
            System.out.println("dateFinContrat: " + dateFinContrat);
            String typeContrat = contrat.getTypeContrat().getCodeDiva();
            String tauxHoraire = contrat.getTauxHoraire().toString();
            Map<String, String> mapAjouterContratWS = salarieService.ajouterContratSalarieWS(contrat.getSalarie().getMatricule(),
                    dateDebutContrat, dateFinContrat, typeContrat, tauxHoraire);
            String referenceContratDiva = mapAjouterContratWS.get("referenceContratDiva");
            System.out.println("reponse ajout contrat sur Divalto: " + referenceContratDiva);

            // --- fin appel web service ---
            if (referenceContratDiva.equals("-3") || referenceContratDiva.equals("-1")) {
                Module.message(2, "Erreur !", " au niveau contrat sur divalto");
                return;
            } else {
                contrat.setRefContratDiva(referenceContratDiva);
                contrat.setEtatContrat(parametrageService.getEtatContrat(2));
                contrat.setCreePar(authentification.get_connected_user().getLogin());
                contrat.setDateCreation(new Date());
                contratService.modifierContrat(contrat);
                Etat etatSalarie;

                Salarie salarie1 = contrat.getSalarie();
                etatSalarie = parametrageService.getEtat(5);
                salarie1.setEtat(etatSalarie);
                salarieService.modifierInformationsSalarie(salarie1);

                // ajouter le contrat dans l'historique
                historiqueContrat.setIdContrat(contrat.getId());
                historiqueContrat.setFonction(contrat.getFonction());
                historiqueContrat.setTauxHoraire(contrat.getTauxHoraire());
                historiqueContrat.setDate(new Date());
                historiqueContrat.setUtilisateur(authentification.get_connected_user());
                contratService.ajouterHistoriqueContrat(historiqueContrat);
            }
        } else {
            /*si contrat legalisé*/
 /*preparation des données pour divalto*/
            System.out.println(" * -* -* -* - *- *- *-*-* *- *-*-*- *- *-* -* -* - *- * MOFIIIIIIIIIIIIIIIIFIIIIIIIIIIIIICAAAAAAAAAAAAAAATIOOOOOOOOOOOOOOOOON ========== *-* -*- * -*- *- *- *- *- ");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            String dateDebutContrat = sdf.format(objetDate);
            dateDebutContrat = dateDebutContrat.replace("/", "");
            System.out.println("dateDebutContrat: " + dateDebutContrat);
            String dateFinContrat = sdf.format(objetDateFin);
            dateFinContrat = dateFinContrat.replace("/", "");
            System.out.println("dateFinContrat: " + dateFinContrat);

            contrat.setDateEmbauche(objetDate);

//            Calendar calendar = Calendar.getInstance();
//            System.out.println("DATE EMBAUCHE INITIAL : " + dateToEdit);
//            calendar.setTime;
//            calendar.add(Calendar.MONTH, contratToEdit.getDuree().getNombreMois());
//            Date objetDateFinNormal = calendar.getTime();
//            
            contrat.setDateFin(objetDateFin);
            // System.out.println("DATE FIN A ENVOYER WS : " + objetDateFinNormal);

            //String dateFinWS = sdf.format(objetDateFinNormal);
            //dateFinWS = dateFinWS.replace("/", "");
            //System.out.println("DATE FIN NORM%AL : " + contratToEdit.getDuree().getNombreMois());
            //contratService.editContrat(contrat);
            Map<String, String> mapModifierContratWS = salarieService.modifierContratSalarieWS(contrat.getSalarie().getMatricule(),
                    dateDebutContrat, dateFinContrat, "", "");

            String referenceContratDiva = mapModifierContratWS.get("referenceContratDiva");
            System.out.println("reponse modification contrat sur Divalto: " + referenceContratDiva);

            // --- fin appel web service ---
            switch (referenceContratDiva) {
                case "-3":
                case "-1":
                    Module.message(2, "Erreur !", " Code WS003 : La modification n'est pas été effectuée. \n veuillez contacter votre administrateur");
                    return;
                case "-2":
                    Module.message(2, "Code 1405 : Ce salarie a déjà un contrat en cette date", " ");
                    return;
                default:
                    contrat.setCreePar(authentification.get_connected_user().getLogin());
                    contrat.setDateCreation(new Date());
                    contrat.setModifiePar(authentification.get_connected_user().getLogin());
                    contrat.setDateModification(new Date());
                    contratService.modifierContrat(contrat);
                    Salarie salarie1 = contrat.getSalarie();
                    Etat etatSalarie = parametrageService.getEtat(5);
                    salarie1.setEtat(etatSalarie);
                    salarieService.modifierInformationsSalarie(salarie1);
                    // ajouter le contrat dans l'historique
                    historiqueContrat.setIdContrat(contrat.getId());
                    historiqueContrat.setFonction(contrat.getFonction());
                    historiqueContrat.setTauxHoraire(contrat.getTauxHoraire());
                    historiqueContrat.setDate(new Date());
                    historiqueContrat.setUtilisateur(authentification.get_connected_user());
                    contratService.ajouterHistoriqueContrat(historiqueContrat);
                    break;
            }
        }
        Module.message(0, "Vous avez ajouté un contrat légalisé avec succès!", "");
        contrats = contratService.listeContratsSalarie(0, Integer.parseInt(contratService.nombreContratsSalarie(salarie.getId()).toString()), salarie.getId());
        initContratToShow(contrat);
    }

    public void initContratToEdit(Contrat contrat) throws IOException, DocumentException {

//        codeCurrent = ("".equals(contrat.getNodeRefLegalise())) ? contrat.getNodeRefLegalise() : contrat.getNodeRefNonLegalise();
//        codeCurrent = codeCurrent.substring(codeCurrent.indexOf("resources"));
//        System.out.println(codeCurrent);
        contratToEdit = contrat;
    }

    public void modifierFTcontrat() {
        System.out.println("contrat to edit " + contratToEdit.getId() + " taux " + taux_a_modifer + " fonction " + contratFonctionId);
        Map<String, String> mapModifierTFContratWS;
        Fonction fonc = parametrageService.getFonction(contratFonctionId);
        if (!contratToEdit.getTauxHoraire().equals(taux_a_modifer) || !contratToEdit.getFonction().getId().equals(contratFonctionId)) {
            mapModifierTFContratWS = salarieService.modifierFonctionTauxHoraireWS(contratToEdit.getSalarie().getMatricule().trim(), fonc.getCodeDiva(), taux_a_modifer.toString());
            String referenceContratDiva = mapModifierTFContratWS.get("referenceContratDiva");
            System.out.println("reponse modification contrat sur Divalto: " + referenceContratDiva);
            if (referenceContratDiva.equals("0") || referenceContratDiva.equals("-1")) {
                Module.message(2, "La modification n'est pas effectuée, veuillez contacter votre administrateur Détail:", " " + mapModifierTFContratWS.get("referenceContratDiva"));
            } else {
                ELContext elContext = FacesContext.getCurrentInstance().getELContext();
                Authentification authentification = (Authentification) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "authentification");
                //---- garder le taux et la fonction dans l'historique
                HistoriqueContrat historiqueContrat = new HistoriqueContrat();
                historiqueContrat.setIdContrat(contratToEdit.getId());
                historiqueContrat.setFonction(fonc);
                historiqueContrat.setTauxHoraire(taux_a_modifer);
                historiqueContrat.setDate(new Date());
                historiqueContrat.setUtilisateur(authentification.get_connected_user());
                contratService.ajouterHistoriqueContrat(historiqueContrat);
                //---- modifier la fonction et le taux liés au contrat
                Contrat contrat = contratService.getContrat(contratToEdit.getId());
                contrat.setTauxHoraire(taux_a_modifer);
                contrat.setFonction(fonc);
                contratService.modifierContrat(contrat);
                //---- modifier la fonction globale du salarié
                Salarie salarieC = contrat.getSalarie();
                salarieC.setFonction(fonc);
                salarieService.modifierInformationsSalarie(salarieC);
//                histoContrat = contratService.historiqueContrat(0, 20, contrat.getId());
                Module.message(0, "Modification effectuée", "");
                contrats = contratService.listeContratsSalarie(0, Integer.parseInt(contratService.nombreContratsSalarie(salarie.getId()).toString()), salarie.getId());
                initContratToShow(contrat);
            }
        }
    }

    public void modifierDateFinContratSalarie() {
        if (!Module.checkDate(contratToEdit.getDateEmbauche(), null, dateFinContrat_modifier)) {
            Module.message(2, "Code 1404 : date fin contrat doit être supérieur a la date de début", "");
            return;
        }
        System.out.println("date fin " + dateFinContrat_modifier + " motif Id " + motif_FinContrat_Id);
        Motif motif = parametrageService.getMotif(motif_FinContrat_Id);
        if (contratToEdit.getDuree().getNombreMois() != null) {
            //Si la date de fin dépasse la durée de contrat
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(contratToEdit.getDateEmbauche());
            calendar.add(Calendar.MONTH, contratToEdit.getDuree().getNombreMois());
            Date objetDateFinNormal = calendar.getTime();
            if (dateFinContrat_modifier.after(objetDateFinNormal)) {
                Module.message(2, "Code 1403 : La date de fin ne doit pas dépasser la durée de contrat", "");
                return;
            }
            Contrat contrat = contratService.getContrat(contratToEdit.getId());
            contrat.setDateFin(dateFinContrat_modifier);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            String dateDebutContrat = sdf.format(contrat.getDateEmbauche());
            dateDebutContrat = dateDebutContrat.replace("/", "");
            System.out.println("dateDebutContrat: " + dateDebutContrat);
            String dateFinContrat = sdf.format(contrat.getDateFin());
            dateFinContrat = dateFinContrat.replace("/", "");
            System.out.println("dateFinContrat: " + dateFinContrat);

            Map<String, String> mapModifierContratWS = salarieService.modifierContratSalarieWS(salarie.getMatricule(),
                    dateDebutContrat, dateFinContrat, motif.getMotifCode(), dateFinContrat);

            String referenceContratDiva = mapModifierContratWS.get("referenceContratDiva");
            System.out.println("reponse modification contrat sur Divalto: " + referenceContratDiva);

            // --- fin appel web service ---
            if (referenceContratDiva.equals("0") || referenceContratDiva.equals("-1")) {
                Module.message(2, "Code WS004 : Le salarié n'est pas réglé, veuillez contacter votre administrateur", "");
            } else {
                ELContext elContext = FacesContext.getCurrentInstance().getELContext();
                Authentification authentification = (Authentification) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "authentification");
                contrat.setMotif(motif);
                EtatContrat etatContrat = parametrageService.getEtatContrat(Constante.Etat_ID_Contrat_Inactif);
                contrat.setEtatContrat(etatContrat);
                contrat.setModifiePar(authentification.get_connected_user().getLogin());
                contrat.setDateModification(new Date());
                contratService.modifierContrat(contrat);
                Salarie salarieC = contrat.getSalarie();
                Etat etatSalarie = parametrageService.getEtat(Constante.Etat_ID_Sortie);
                salarieC.setEtat(etatSalarie);
                salarieService.modifierInformationsSalarie(salarieC);
//                histoContrat = contratService.historiqueContrat(0, 20, contrat.getId());
                Module.message(0, "Modification éffectuée", "");
                contrats = contratService.listeContratsSalarie(0, Integer.parseInt(contratService.nombreContratsSalarie(salarie.getId()).toString()), salarie.getId());
                initContratToShow(contrat);
            }
        }
    }

    public void modifierEtat() {
        Contrat contrat = contratService.getContrat(contratToEdit.getId());
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        Authentification authentification = (Authentification) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "authentification");
        contrat.setEtatContrat(parametrageService.getEtatContrat(idEtatContrat));
        contrat.setModifiePar(authentification.get_connected_user().getLogin());
        contrat.setDateModification(new Date());
        contratService.modifierContrat(contrat);
        Module.message(0, "Modification éffectuée", "");
        contrats = contratService.listeContratsSalarie(0, Integer.parseInt(contratService.nombreContratsSalarie(salarie.getId()).toString()), salarie.getId());
        initContratToShow(contrat);
    }

    public void reinitialiserDateFinContrat() {
        Contrat contrat = contratService.getContrat(contratToEdit.getId());
        Date dateFin = null;
        if (contrat.getDateFin() != null) {
            if (contrat.getDuree().getNombreMois() != null) {

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(contrat.getDateEmbauche());
                calendar.add(Calendar.MONTH, contrat.getDuree().getNombreMois());
                dateFin = calendar.getTime();

            }
        } else {
            dateFin = contrat.getDateFin();
        }
        contrat.setDateFin(dateFin);
        contrat.setMotif(null);
        EtatContrat etatContrat = parametrageService.getEtatContrat(2);
        contrat.setEtatContrat(etatContrat);
        contratService.modifierContrat(contrat);

        Salarie salariec = contrat.getSalarie();
        Etat etat = parametrageService.getEtat(1);
        salariec.setEtat(etat);
        salarieService.modifierInformationsSalarie(salariec);
        salarie = salarieService.getSalarie(salariec.getId());
        contrats = contratService.listeContratsSalarie(0, Integer.parseInt(contratService.nombreContratsSalarie(salarie.getId()).toString()), salarie.getId());
        initContratToShow(contrat);
    }

    public String saveDocSalarie() throws IOException {
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        Authentification authentification = (Authentification) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "authentification");
        System.out.println("DOC STATUS ===== " + docStatus);
        if (docStatus.compareToIgnoreCase("Ajouter") == 0) {
            document = new Document();
            System.out.println("ADDING DOCUMENT ...");
        }
        if (docStatus.compareToIgnoreCase("Modifier") == 0) {
            document = docToEdit;
            System.out.println("EDITING DOCUMENT ...");
        }

        chemin = TgccFileManager.getArboFichier("DocumentsSalarie");

        Path folder = Paths.get(chemin);
        Files.createDirectories(folder);
        String filename = FilenameUtils.getBaseName(docUploaded.getFileName());
        String extension = FilenameUtils.getExtension(docUploaded.getFileName());
        if (!"PDF".equals(extension.toUpperCase())) {
            Module.message(2, "Veuillez choisir un PDF", "");
        } else {
            Path file = Files.createTempFile(folder, filename + "-", "." + extension);
            try (InputStream input = docUploaded.getInputstream()) {
                Files.copy(input, file, StandardCopyOption.REPLACE_EXISTING);
                cheminDocument = chemin + "/" + file.getFileName();
                cheminDocument = cheminDocument.substring(cheminDocument.indexOf("files"));
                System.out.println(cheminDocument);
                if (docUploaded != null) {
                    document.setChemin(cheminDocument);
                }
                document.setSalarie(salarie);
//                documentService.listeDocumentsSalarie(salarie.getId(), 0, 0);

//                try {
//                    List<Document> listDocs = salarie.getDocuments();
//
//                    for (Document l : listDocs) {
//                        System.out.println("***************************LIST DOC SALARIIIIEEEEEE : " + l.getTitre());
//                    }
//
//                } catch (NullPointerException e) {
//
//                    System.out.println("*-*-*-*-*-*--******************* NULL - SALARIE HAS NOOO DOOOOCSSSSS ");
//                }
                document.setTitre(docToAdd.getTitre());
                document.setCommentaire(docToAdd.getCommentaire());
                TypeDocument autre = parametrageService.getTypeDocument("Autre");
                document.setTypeDocument(autre);

                if (docStatus.compareToIgnoreCase("Ajouter") == 0) {
                    document.setDateCreation((new Date()));
                    document.setCreePar(authentification.get_connected_user().getLogin());
                    documentService.ajouterDocument(document);
                    Module.message(0, "Document Ajouté", "");
                }

                if (docStatus.compareToIgnoreCase("Modifier") == 0) {
                    document.setDateModification((new Date()));
                    document.setModifiePar(authentification.get_connected_user().getLogin());
                    documentService.modifierDocument(document);
                    Module.message(0, "Document Modifié", "");
                }

                docs = documentService.listeDocumentsSalarie(salarie.getId(), 0, Integer.parseInt(documentService.nombreDocumentsSalarie(salarie.getId()).toString()));

            }
        }
        return "/evol/detail.xhtml?faces-redirect=true&salarieId=" + salarie.getId();
    }

    public void deleteDoc(Document doc) {
        documentService.supprimerDocument(doc);
    }

    public void savePhoto(FileUploadEvent event) throws IOException {
        /**
         * upload photo salarie
         */
        chemin = TgccFileManager.getCheminFichier("PhotosSalarie");

        Path folder = Paths.get(chemin);
        Files.createDirectories(folder);
        String filename = FilenameUtils.getBaseName(event.getFile().getFileName());
        String extension = FilenameUtils.getExtension(event.getFile().getFileName());
        if (!"PNG".equals(extension.toUpperCase())
                && !"JPEG".equals(extension.toUpperCase())
                && !"JPG".equals(extension.toUpperCase())) {
            Module.message(2, "Veuillez choisir une image ", "(jpeg, jpg, png)");
            return;
        }
        Path file = Files.createTempFile(folder, filename + "-", "." + extension);
        try (InputStream input = event.getFile().getInputstream()) {
            Files.copy(input, file, StandardCopyOption.REPLACE_EXISTING);
            cheminPhotoSalarie = chemin + "/" + file.getFileName();
            cheminPhotoSalarie = cheminPhotoSalarie.substring(cheminPhotoSalarie.indexOf("files"));
            salarie.setCheminPhoto(cheminPhotoSalarie);
            System.out.println("image path :" + cheminPhotoSalarie);
            salarieService.modifierInformationsSalarie(salarie);
        }
        Module.message(0, "photo modifiée", "");

    }

    public void savePhotoServer(FileUploadEvent event) throws IOException {
        /**
         * upload photo salarie
         */
        chemin = TgccFileManager.getArboFichier("PhotosSalarie");

        Path folder = Paths.get(chemin);
        Files.createDirectories(folder);
        String filename = FilenameUtils.getBaseName(event.getFile().getFileName());
        String extension = FilenameUtils.getExtension(event.getFile().getFileName());
        if (!"PNG".equals(extension.toUpperCase())
                && !"JPEG".equals(extension.toUpperCase())
                && !"JPG".equals(extension.toUpperCase())) {
            Module.message(2, "Veuillez choisir une image ", "(jpeg, jpg, png)");
            return;
        }
        Path file = Files.createTempFile(folder, filename + "-", "." + extension);
        try (InputStream input = event.getFile().getInputstream()) {
            Files.copy(input, file, StandardCopyOption.REPLACE_EXISTING);
            cheminPhotoSalarie = chemin + "/" + file.getFileName();
            cheminPhotoSalarie = cheminPhotoSalarie.substring(cheminPhotoSalarie.indexOf("files"));
            salarie.setCheminPhoto(cheminPhotoSalarie);
            System.out.println("image path :" + cheminPhotoSalarie);
            salarieService.modifierInformationsSalarie(salarie);
        }
        Module.message(0, "photo modifiée", "");

    }

    /**
     * generer contrat
     *
     * @param parametresContrat
     * @param s
     * @param md
     * @return
     * @throws IOException
     */
    private String genereContrat(Map parametresContrat, Salarie s, ModelContrat md) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(md.getFichierJrxml());

        try {
            //Chargement
            JasperDesign jasperDesign = JRXmlLoader.load(inputStream);

            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametresContrat, new JREmptyDataSource());
            //JasperExportManager.exportReportToPdfFile(jasperPrint, "D:\\iReport-1.2.1\\classic.pdf");
            byte[] bytes = JasperExportManager.exportReportToPdf(jasperPrint);

            chemin = TgccFileManager.getArboFichier("ContratSalarie/NonLegaliser");

            Path folder = Paths.get(chemin);
            Files.createDirectories(folder);
            try (InputStream input = new ByteArrayInputStream(bytes);) {
                Path file = Files.createTempFile(folder, salarie.getId() + "", ".pdf");
                Files.copy(input, file, StandardCopyOption.REPLACE_EXISTING);
                String cheminContrat = chemin + "/" + file.getFileName();
                cheminContrat = cheminContrat.substring(cheminContrat.indexOf("files"));
                System.out.println("chmin contrat " + cheminContrat);
                codeCurrent = cheminContrat.substring(cheminContrat.indexOf("files"));
                System.out.println("code current : " + codeCurrent);
                return cheminContrat;
            } catch (Exception e) {
                System.out.println("erreur création contrat" + e.getMessage());
            }
            //Map<QName, Serializable> props = new HashMap<>();
            //  NodeRef nodeRefDossierSalarie = new NodeRef("workspace://SpacesStore/" + dossierSalarie.getNodeRefDossier());
            //  nodeRefPdfContratGenere = nodeService.createFileFromStream(ticket, "contrat_" + (int) (Math.random() * 10000) + ".pdf", nodeRefDossierSalarie, props, bis);
            //  nodeService.rename(ticket, nodeRefPdfContratGenere, "doc_" + nodeRefPdfContratGenere.getId() + ".pdf");
        } catch (JRException | IOException e) {
            System.out.println("erreur generation contrat " + e.getMessage());
        }
        return "";
    }

    private String genereContrat(Map parametresContrat, Salarie s, ModelContrat md, Date dateContrat) throws IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(md.getFichierJrxml());

        try {
            //Chargement
            JasperDesign jasperDesign = JRXmlLoader.load(inputStream);

            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametresContrat, new JREmptyDataSource());
            //JasperExportManager.exportReportToPdfFile(jasperPrint, "D:\\iReport-1.2.1\\classic.pdf");
            byte[] bytes = JasperExportManager.exportReportToPdf(jasperPrint);

            chemin = TgccFileManager.getArboFichier("ContratSalarie", dateContrat);

            Path folder = Paths.get(chemin);
            Files.createDirectories(folder);
            try (InputStream input = new ByteArrayInputStream(bytes);) {
                Path file = Files.createTempFile(folder, salarie.getId() + "", ".pdf");
                Files.copy(input, file, StandardCopyOption.REPLACE_EXISTING);
                String cheminContrat = chemin + "/" + file.getFileName();
                cheminContrat = cheminContrat.substring(cheminContrat.indexOf("files"));
                System.out.println("chmin contrat " + cheminContrat);
                codeCurrent = cheminContrat.substring(cheminContrat.indexOf("files"));
                System.out.println("code current : " + codeCurrent);
                return cheminContrat;
            } catch (Exception e) {
                System.out.println("erreur création contrat" + e.getMessage());
            }
            //Map<QName, Serializable> props = new HashMap<>();
            //  NodeRef nodeRefDossierSalarie = new NodeRef("workspace://SpacesStore/" + dossierSalarie.getNodeRefDossier());
            //  nodeRefPdfContratGenere = nodeService.createFileFromStream(ticket, "contrat_" + (int) (Math.random() * 10000) + ".pdf", nodeRefDossierSalarie, props, bis);
            //  nodeService.rename(ticket, nodeRefPdfContratGenere, "doc_" + nodeRefPdfContratGenere.getId() + ".pdf");
        } catch (JRException e) {
            System.out.println("erreur generation contrat" + e.getMessage());
        }
        return "";
    }

    public void updateAffectation() {
        //System.out.println("update chantier affectée : nbr chantier" + chantiers.size());
        boolean isAdmin = false;
        if ("admin".equals(Constantes.getRoleAuth()) || "EMAIL_CONTRIBUTORS".equals(Constantes.getRoleAuth())) {
            isAdmin = true;
        }
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        Authentification authentification = (Authentification) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "authentification");
        Integer idUser = authentification.get_connected_user().getId();
        chantiers = chantierService.listeChantiersAffectes(salarie.getId(), 1, 0, Integer.parseInt(chantierService.nombreChantiers("", "", Module.dos).toString()), "", "", Module.dos, isAdmin, idUser);
        //System.out.println("after update: nbr chantier" + chantiers.size());
    }

    public void checkCNSS(String cnss, String salarie) {
        invalideCNSS = false;
        if (cnss == null || cnss.equals("")) {
            invalideCNSS = false;
        } else if (cnss.length() != 9) {
            Module.message(3, "CNSS", "Le Numéro de la CNSS doit contenir 9 chiffres");
            invalideCNSS = true;
        } else if (Constantes.validationCnss(cnss) == 0) {
            Module.message(3, "CNSS", "N°CNSS invalide");
            invalideCNSS = true;
        } else if (!"".equals(salarieService.checkCnss(cnss, salarie))) {
            Module.message(3, "CNSS", "N°CNSS est déjà affecté à un salarie");
            invalideCNSS = true;
        }
        etatBtn = (invalideCNSS || invalideRib || existCin);
    }

    public void checkCNSS(String cnss) {
        invalideCNSS = false;
        if (cnss == null || cnss.equals("")) {
            invalideCNSS = false;
        } else if (cnss.length() != 9) {
            Module.message(3, "Le Numéro de la CNSS doit contenir 9 chiffres", "");
            invalideCNSS = true;
        } else if (Constantes.validationCnss(cnss) == 0) {
            Module.message(3, "Code 1106 : le N°CNSS saisie est invalide", "");
            invalideCNSS = true;
        } else if (!"".equals(salarieService.checkCnss(cnss))) {
            Module.message(3, "Code 1104 : N°CNSS doit être unique", "");
            invalideCNSS = true;
        }
        etatBtn = (invalideCNSS || invalideRib || existCin);
    }

    public void checkRIB(String rib) {
        invalideRib = false;
        if (rib == null || rib.equals("")) {
            invalideRib = false;
        } else if (rib.length() != 24) {
            Module.message(3, "N°Rib doit contenir 24 chiffres", "");
            invalideRib = true;
        } else if (Constantes.validationCnss(rib) == 0) {
            Module.message(3, "Code 1107 : N°RIB invalide", "");
            invalideRib = true;
        } else if (!"".equals(salarieService.checkRIB(rib))) {
            Module.message(3, "Code 1104 : N°RIB doit être unique ", "");
            invalideRib = true;
        }
        etatBtn = (invalideCNSS || invalideRib || existCin);
    }

    public void checkRIB(String rib, String salarie) {
        invalideRib = false;
        if (rib == null || rib.equals("")) {
            invalideRib = false;
        } else if (rib.length() != 24) {
            Module.message(3, "N°Rib doit contenir 24 chiffres", "");
            invalideRib = true;
        } else if (Constantes.validationRib(rib) == 0) {
            Module.message(3, " Code 1107 : N°RIB invalide", "");
            invalideRib = true;
        } else if (!"".equals(salarieService.checkRIB(rib, salarie))) {
            Module.message(3, "Code 1104 : N°RIB doit être unique ", "");
            invalideRib = true;
        }
        etatBtn = (invalideCNSS || invalideRib || existCin);
    }

    public void checkCin(String cin) {
        existCin = false;
        Salarie s = salarieService.getSalarieByCin(cin);
        if (s != null) {
            existCin = true;
            Module.message(3, "Code 1113 : Le salarié " + s.getNom() + " " + s.getPrenom() + " ayant la CIN " + s.getCin() + " existe déjà ", "");
        }
        etatBtn = (invalideCNSS || invalideRib || existCin);
    }

    public void updateEtat() {
        Salarie s = salarieService.getSalarie(salarie.getId());
        if (s != null) {
            s.setEtat(parametrageService.getEtat(id_Etat));
            if (salarieService.modifierInformationsSalarie(s)) {
                Module.message(0, "Etat Salarie est modifié", "");
                salarie = s;
            } else {
                Module.message(2, "Code 1111 : La Modification de l'état du salarié n'est pas effectuée", "");
            }
        }
    }

    public void initialContratToAdd() {
        activeChampContrat=false;
        
        float t1= (float) 0.0, t2= (float) 0.0;
        t1=tauxHoraireService.findByCode(salarie.getFonction().getCodeDiva()).getTAUX_HORAIRE();
        try {
            t2=contratService.dernierContrat(salarie.getId()).getTauxHoraire();
        } catch (Exception e) {
            System.out.println("Erreur :"+e.getMessage());
        }
        idModel = Constante.ID_Model_Arabe;
        idDuree = Constante.ID_Duree_Contrat_1an;
        idTypeContrat = Constante.ID_Type_Contrat_CDD;
        idPreavis = Constante.ID_Periode_Preavis;
        idPeriode = Constante.ID_Periode_Essai;
        idFonctContrat = salarie.getFonction().getId();
        if(t2<t1){
            newContrat.setTauxHoraire(t1);
        }
        else{
            newContrat.setTauxHoraire(t2);
        }
        newContrat.setDateEmbauche(new Date());
        try {
            if(contratService.dernierContrat(salarie.getId()).getEtatContrat()!=null){
                if(contratService.dernierContrat(salarie.getId()).getEtatContrat().getId()>0){
                    activeChampContrat=true;
                }
            }
        } catch (Exception e) {
            System.out.println("Erreur de récuperation etat du contrat  " + e.getMessage());
        }
    }

    public List<Fonction> fonctionByStatut(String idStatut) {
        if (idStatut != null && !"".equals(idStatut)) {
            fonctions = fonctionMb.foncByStatut(idStatut);
        } else {
            fonctions = fonctionMb.getFonctions();
        }
        return fonctions;
    }
    public void changeFunction(){
        addContratBtnActive=true;
        float t1= (float) 0.0,t2= (float) 0.0;
        System.out.println("==============>salarie.getId() : "+salarie.getId());
        try {
            t2=contratService.dernierContrat(salarie.getId()).getTauxHoraire();
        } catch (Exception e) {
            t2= (float) 0.0;
            System.out.println("Erreur :"+e.getMessage());
        }
        int idFanc=idFonctContrat.intValue();
        if(idFanc>0){
            for (int i=0;i<fonctions.size();i++) {
              if(fonctions.get(i).getId()== idFanc){
                    //System.out.println("tauxHoraireService au moment de changement de la fonctions  taux :" +tauxHoraireService.findByCode(fonctions.get(i).getCodeDiva()).getTAUX_HORAIRE());
                    try {
                    t1=tauxHoraireService.findByCode(fonctions.get(i).getCodeDiva()).getTAUX_HORAIRE();
                  } catch (Exception e) {
                      t1= (float) 0.0;
                  }
                    break;
              }
            }
        System.out.println("==============>t1 : "+t1+" ===============> t2 : "+t2);
            
            if(t1>0){
                     if(t2<t1){
                            newContrat.setTauxHoraire(t1);
                        }
                        else{
                            newContrat.setTauxHoraire(t2);
                        }
                     addContratBtnActive=false;
                }
        System.out.println("==============>newContrat.getTauxHoraire() : "+newContrat.getTauxHoraire());
            if(addContratBtnActive){  
                newContrat.setTauxHoraire((float) 0.0);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                        "", "La fonction sélectionnée n'a pas de taux horaire, Merci de Contacter RH !"));
            }
        }

    }
    public void deleteContrat(Contrat c){
        contratService.deleteContratsNonL(c);
        contrats = contratService.listeContratsSalarie(0, Integer.parseInt(contratService.nombreContratsSalarie(salarie.getId()).toString()), salarie.getId());
    }

}
