package ma.bservices.mb;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import ma.bservice.tgcc.Constante.Constante;
import ma.bservices.beans.Chantier;
import ma.bservices.beans.Etat;
import ma.bservices.beans.Fonction;
import ma.bservices.beans.ModePaiement;
import ma.bservices.beans.Nationalite;
import ma.bservices.beans.Pays;

import ma.bservices.beans.Salarie;
import ma.bservices.beans.SituationFamiliale;
import ma.bservices.constantes.Constantes;
import ma.bservices.mb.services.Evol_FonctionMb;
import ma.bservices.mb.services.Module;
//import ma.bservices.metier.SalarieManager;
//import ma.bservices.metier.SalarieManagerImpl;
import ma.bservices.services.ChantierService;
import ma.bservices.services.DocumentService;
import ma.bservices.services.ParametrageService;
import ma.bservices.services.SalarieService;
import ma.bservices.services.SalarieServiceIn;
import ma.bservices.services.ZoneServices;
import ma.bservices.tgcc.authentification.Authentification;

import org.springframework.stereotype.Component;

@Component
@ManagedBean
@ViewScoped
public class NouveauSalarieMb implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    //@Resource(name = "salarieService")
    //@Autowired
    @ManagedProperty(value = "#{salarieService}")
    private SalarieService salarieService;

    @ManagedProperty(value = "#{salarieServiceIn}")
    private SalarieServiceIn salarieServiceIn;

    @ManagedProperty(value = "#{chantieService}")
    private ChantierService chantieService;
    private List<Chantier> nonAffectChantier;
    private Integer idchantier;

    @ManagedProperty(value = "#{parametrageService}")
    private ParametrageService parametrageService;

    @ManagedProperty(value = "#{documentServiceEvol}")
    private DocumentService documentService;

    @ManagedProperty(value = "#{zoneService}")
    private ZoneServices zoneServices;

    private Integer pays, situationFa, nationalite, modepaiement, fonction, pointureID, tailleID, giletID, casqueID, typeID, civiliteID;
    /**
     * NouveauSalarie
     */
    private Salarie addSalarie = new Salarie();
    private Evol_FonctionMb fonctionMb;
    private String idStatut;
    private List<Fonction> fonctions = new LinkedList<>();
    // private int ;
    private Boolean disable, invalideCNSS, invalideRib, etatBtn, existCin;

    private List<String> l_salarie_ville;
//    private String imgUrl;
//    private Integer var;

    public SalarieService getSalarieService() {
        return salarieService;
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

    public Evol_FonctionMb getFonctionMb() {
        return fonctionMb;
    }

    public void setFonctionMb(Evol_FonctionMb fonctionMb) {
        this.fonctionMb = fonctionMb;
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

    public void setSalarieService(SalarieService salarieService) {
        this.salarieService = salarieService;
    }

    public SalarieServiceIn getSalarieServiceIn() {
        return salarieServiceIn;
    }

    public void setSalarieServiceIn(SalarieServiceIn salarieServiceIn) {
        this.salarieServiceIn = salarieServiceIn;
    }

    public ChantierService getChantieService() {
        return chantieService;
    }

    public void setChantieService(ChantierService chantieService) {
        this.chantieService = chantieService;
    }

    public List<Chantier> getNonAffectChantier() {
        return nonAffectChantier;
    }

    public void setNonAffectChantier(List<Chantier> nonAffectChantier) {
        this.nonAffectChantier = nonAffectChantier;
    }

    public Integer getIdchantier() {
        return idchantier;
    }

    public void setIdchantier(Integer idchantier) {
        this.idchantier = idchantier;
    }

    public ParametrageService getParametrageService() {
        return parametrageService;
    }

    public void setParametrageService(ParametrageService parametrageService) {
        this.parametrageService = parametrageService;
    }

    public DocumentService getDocumentService() {
        return documentService;
    }

    public void setDocumentService(DocumentService documentService) {
        this.documentService = documentService;
    }

    public ZoneServices getZoneServices() {
        return zoneServices;
    }

    public void setZoneServices(ZoneServices zoneServices) {
        this.zoneServices = zoneServices;
    }

    public Integer getPays() {
        return pays;
    }

    public void setPays(Integer pays) {
        this.pays = pays;
    }

    public Integer getSituationFa() {
        return situationFa;
    }

    public void setSituationFa(Integer situationFa) {
        this.situationFa = situationFa;
    }

    public Integer getNationalite() {
        return nationalite;
    }

    public void setNationalite(Integer nationalite) {
        this.nationalite = nationalite;
    }

    public Integer getModepaiement() {
        return modepaiement;
    }

    public void setModepaiement(Integer modepaiement) {
        this.modepaiement = modepaiement;
    }

    public Integer getFonction() {
        return fonction;
    }

    public void setFonction(Integer fonction) {
        this.fonction = fonction;
    }

    public Integer getPointureID() {
        return pointureID;
    }

    public void setPointureID(Integer pointureID) {
        this.pointureID = pointureID;
    }

    public Integer getTailleID() {
        return tailleID;
    }

    public void setTailleID(Integer tailleID) {
        this.tailleID = tailleID;
    }

    public Integer getGiletID() {
        return giletID;
    }

    public void setGiletID(Integer giletID) {
        this.giletID = giletID;
    }

    public Integer getCasqueID() {
        return casqueID;
    }

    public void setCasqueID(Integer casqueID) {
        this.casqueID = casqueID;
    }

    public Integer getTypeID() {
        return typeID;
    }

    public void setTypeID(Integer typeID) {
        this.typeID = typeID;
    }

    public Integer getCiviliteID() {
        return civiliteID;
    }

    public void setCiviliteID(Integer civiliteID) {
        this.civiliteID = civiliteID;
    }

    public Salarie getAddSalarie() {
        return addSalarie;
    }

    public void setAddSalarie(Salarie addSalarie) {
        this.addSalarie = addSalarie;
    }

    public Boolean getDisable() {
        return disable;
    }

    public void setDisable(Boolean disable) {
        this.disable = disable;
    }

    public List<String> getL_salarie_ville() {
        return l_salarie_ville;
    }

    public void setL_salarie_ville(List<String> l_salarie_ville) {
        this.l_salarie_ville = l_salarie_ville;
    }

    /**
     * *********
     * Methodes
     */
    @PostConstruct
    public void init() {
        invalideCNSS = false;
        invalideRib = false;
        existCin = false;
        System.out.println("___faces ___ " + FacesContext.getCurrentInstance().toString());
        salarieService = Module.ctx.getBean(SalarieService.class);
        chantieService = Module.ctx.getBean(ChantierService.class);
        zoneServices = Module.ctx.getBean(ZoneServices.class);
        parametrageService = Module.ctx.getBean(ParametrageService.class);
        documentService = Module.ctx.getBean(DocumentService.class);
        salarieServiceIn = Module.ctx.getBean(SalarieServiceIn.class);
        l_salarie_ville = new ArrayList<>();
        civiliteID = Constante.ID_CIVILITE_MONSIEUR;
        typeID = Constante.ID_Type_Quinzainier;
        situationFa = Constante.ID_Situation_Cilibataire;
        modepaiement = Constante.ID_Mode_Paiement;
        disable = true;
        civiliteID = Constante.ID_CIVILITE_MONSIEUR;
        pays = Constante.ID_Pays_Maroc;
        nationalite = Constante.ID_NATIONALITE_MAROCAINE;
        l_salarie_ville = salarieServiceIn.distinct_Salarie_Ville();
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        fonctionMb = (Evol_FonctionMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "evol_fonctionMb");
        fonctions = fonctionMb.getFonctions();
        idStatut = "P";
        fonctionByStatut();
    }

    public void addNewSalarie() throws IOException {
        if (!Module.checkDate(null, Constante.getMinDateNaissance(), addSalarie.getDateNaissance())) {
            System.out.println("date naissance < = today");
            Module.message(2, "Code 1110 : l'age du salariée doit être supérieur a 18 ans", "");
            return;
        }
        System.out.println("@@@@@@ add Salarie");
        Nationalite na = parametrageService.getNationalite(nationalite);
        SituationFamiliale sf = parametrageService.getSituationFamiliale(situationFa);
        Pays payss = parametrageService.getPays(pays);
        ModePaiement md = parametrageService.getModePaiement(modepaiement);

        System.out.println("------------ nationa" + na.getNationalite());
        if (!"".equals(salarieService.checkCin(addSalarie.getCin()))
                && !"".equals(salarieService.checkCnss(addSalarie.getCnss()))
                && !"".equals(salarieService.checkRIB(addSalarie.getRib()))) {
            Module.message(3, "Ce Salarié existe déjà  !!! ", "");
        } else {
            Etat etats = parametrageService.getEtat("En cours");
            System.out.println("etat " + etats);
            addSalarie.setEtat(etats);
            //addSalarie.setEtat(parametrageService.getEtat("En cours"));
            addSalarie.setCivilite(parametrageService.getCivilite(civiliteID));
            addSalarie.setPointure(parametrageService.getPointure(pointureID));
            addSalarie.setTailleHabillement(parametrageService.getTailleHabillement(tailleID));
            addSalarie.setCouleurCasque(parametrageService.getCouleurCasque(casqueID));
            addSalarie.setCouleurGilet(parametrageService.getCouleurGilet(giletID));
            addSalarie.setType(parametrageService.getType(typeID));
            addSalarie.setFonction(parametrageService.getFonction(fonction));
            ELContext elContext = FacesContext.getCurrentInstance().getELContext();
            Authentification authentification = (Authentification) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "authentification");
            String demand = authentification.get_connected_user().getLogin();
            addSalarie.setCreePar(demand);
            addSalarie.setDateCreation(new Date());
            addSalarie.setNationalite(na);
            addSalarie.setSituationFamiliale(sf);
            addSalarie.setPays(payss);
            addSalarie.setModePaiement(md);
            // addSalarie.setCheminPhoto(cheminPhotoSalarie);
            Integer idSalarie = salarieService.ajouterSalarie(addSalarie);
            if (idSalarie == null || idSalarie == 0) {
                Module.message(3, "Erreur Création Salarié", "");
                System.out.println("@@erreur d'ajouter salarie sur base de donnée: " + idSalarie);
                return;
            }
            // --- Appel du web Service pour le salarié sur Divalto ---

            //String matriculeSalarieCree=salarieService.getSalarie(idSalarie).getMatricule();
            //Statut st=parametrageService.getStatut(statut);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String chaineDateNaissance = sdf.format(addSalarie.getDateNaissance());
            if (chaineDateNaissance != null && !"".equals(chaineDateNaissance)) {
                chaineDateNaissance = chaineDateNaissance.replaceAll("-", "");
                chaineDateNaissance = chaineDateNaissance.substring(0, 4) + chaineDateNaissance.substring(4, 6) + chaineDateNaissance.substring(6, 8);
            }
            System.out.println("date naissance: " + chaineDateNaissance);
            Map<String, String> mapAjouterSalarieWS;
            mapAjouterSalarieWS = salarieService.ajouterSalarieWS(addSalarie.getId().toString(), addSalarie.getCivilite().getCodeDiva(), addSalarie.getSituationFamiliale().getCodeDiva(),
                    addSalarie.getNom(), addSalarie.getPrenom(), addSalarie.getCin(), addSalarie.getCnss(), chaineDateNaissance,
                    addSalarie.getLieuNaissance(), addSalarie.getNationalite().getCodeDiva(), addSalarie.getPays().getCodeDiva(), addSalarie.getAdresse(), addSalarie.getVille(),
                    addSalarie.getTelephone(), addSalarie.getGsm(), addSalarie.getEmail(), addSalarie.getRib(), addSalarie.getNomBanque(), "", addSalarie.getFonction().getCodeDiva().trim(),
                    addSalarie.getModePaiement().getCodeDiva(),"719");

            String reponseDiva = mapAjouterSalarieWS.get("referenceSalarieDiva");
            System.out.println("reponse ajout individu sur Divalto: " + reponseDiva);

            // --- fin appel web service ---
            if (reponseDiva.equals("0") || reponseDiva.equals("-1")) {
                salarieService.supprimerSalarie(addSalarie);
                Module.message(2, "erreur au niveau de l'ajout sur divalto", "");
            } else {
                System.out.println("addSalarie");
                Module.message(0, "Salarie ajouté", "");
                NouveauSalarieDocMb nouveauSalarieDocMb = (NouveauSalarieDocMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "nouveauSalarieDocMb");
                nouveauSalarieDocMb.documentSalarie(idSalarie);
                System.out.println("id salarie send to nouveau salarie docx " + idSalarie);
                ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
                ec.redirect(ec.getRequestContextPath() + "/evol/nouveausalariedocx.xhtml");
            }

        }
    }

    public void changeModePaiment() {
        System.out.println("_______ Entree ___________ ");
        ModePaiement MP = parametrageService.getModePaiement(modepaiement);
        disable = !MP.getModePaiement().equals("Virement");
        checkRIB("");
        addSalarie.setRib("");
        System.out.println("@@@@@@ Mode Paiement" + modepaiement);
    }

    public void checkCNSS(String cnss) {
        invalideCNSS = false;
        if (cnss == null || cnss.equals("")) {
            invalideCNSS = false;
        } else if (cnss.length() != 9) {
            Module.message(3, "N°CNSS doit contenir 9 chiffres", "");
            invalideCNSS = true;
        } else if (Constantes.validationCnss(cnss) == 0) {
            Module.message(3, "Code 1106 : N°CNSS invalide", "");
            invalideCNSS = true;
        } else if (!"".equals(salarieService.checkCnss(cnss))) {
            Module.message(3, "Code 1109 : Ce N°CNSS existe déja ", "");
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
        } else if (Constantes.validationRib(rib) == 0) {
            Module.message(3, "Code 1107 : N°RIB invalide", "");
            invalideRib = true;
        } else if (!"".equals(salarieService.checkRIB(rib))) {
            Module.message(3, "Code 1108 : Un salarie avec le même numero de RIB existe Déjà ", "");
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

    /**
     * ville for autocomplete
     *
     * @param query
     * @return
     */
    public List<String> villes(String query) {
        List<String> toReturn = new LinkedList<>();

        for (String l_salarie_ville1 : this.l_salarie_ville) {
            if (l_salarie_ville1 != null) {
                if (l_salarie_ville1.toLowerCase().startsWith(query.toLowerCase())) {
                    toReturn.add(l_salarie_ville1);
                }
            }
        }

        return toReturn;
    }

    public void fonctionByStatut() {
        if (idStatut != null && !"".equals(idStatut)) {
            fonctions = fonctionMb.foncByStatut(idStatut);
        } else {
            fonctions = fonctionMb.getFonctions();
        }
        System.out.println("ma.bservices.mb.NouveauSalarieMb.fonctionByStatut()"+fonctions.toString());
    }
}
