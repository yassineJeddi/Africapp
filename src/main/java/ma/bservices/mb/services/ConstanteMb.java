/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.mb.services;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URLConnection;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import org.apache.commons.lang.ArrayUtils;
import org.apache.poi.ss.usermodel.DateUtil;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.stereotype.Component;

/**
 *
 * @author Mahdi
 */
@Component
@ManagedBean
@ApplicationScoped
public class ConstanteMb implements Serializable {

    private Date maxDate, minDateNaissance;
     private static String repertoire = "/opt";
     //private static String repertoire="D://PROJETS//NetBeans//data";
   //private static String repertoire="E://data";

 

//    private String realPath = "http://195.154.21.54:8080/files";
//RESSOURCES HUMAINES -> SALARIES
    private String listeSalaries    = "liste et recherche des salariés";
    private String ajouterSalarie   = "ajouter un nouveau salarié";
    private String modifierSalarie  = "modification salarié";
    private String detailSalarie    = "afficher le détail du salarié";
    private String modifierEtat     = "changer état du salarié";
    private String reglerOuvrier    = "régler un ouvrier";
    private String reinitialiserDateFinContrat = "réinitialiser la date de fin du contrat";
    private String changerTauxFonction = "changer taux/fonction";
    private String supprimerUnEnfant = "supprimer un enfant";
    private String changerStatutContrat = "changer statut contrat";
    private String modifierRIB = "modification rib";
    private String modifierTauxHoraire = "modification Taux Horaire";

 
//RESSOURCES HUMAINES -> PRESENCES
    private String listePresences = "liste et recherche des présences";
    private String pointageEntree = "pointage d\'entrée";
    private String pointageSortie = "pointage de sortie";
    private String pointageSalarie = "pointage par salarié";
    private String pointagesEntree = "pointages entrée";
    private String pointagesSortie = "pointages sortie";
    private String listeHS = "liste heures supplémentaires";
    private String ajouterHS = "ajouter heure(s) supplémentaire(s)";
    private String annulerPointage = "annuler le pointage";
    private String pointageMassif = "pointage massif";
    private String validerHS = "valider heures supplémentaires";

    public String getValiderHS() {
        return validerHS;
    }

//RESSOURCES HUMAINES -> ABSENCES
    private String listeAbsences = "liste et recherche des absences";
    private String ajouterAbsence = "marquer une absence";
    private String modifierAbsence = "modifier absence";

//RESSOURCES HUMAINES -> CHANTIERS
    private String listeChantiers = "liste chantiers";
    private String ajouterChantier = "affecter un salarié à un chantier";
    private String localiserChantier = "localiser un chantier";

//ADMINISTRATION -> UTILISATEURS
    private String listeUtilisateurs = "liste et recherche des utilisateurs";
    private String ajouterUtilisateur = "créer un nouvel utilisateur";
    private String listeUtilisateursGroupe = "lister les utilisateurs d'un groupe";
    private String droitsUtilisateur = "attribuer/désatribuer les droits d'un utilisateur";
    private String modifierGroupesUtilisateur = "modifier les groupes d'un utilisateur";

//ADMINISTRATION -> GROUPES
    private String listeGroupes = "liste groupes";
    private String ajouterGroupe = "créer un nouveau groupe";
    private String droitsGroupe = "attribuer/désatribuer les droits d'un groupe";
    
    
//ENGIN -> parcengins
    private String ajouterEngin="Ajouter Engin";
    private String reformerEngin="Reformer Engin";
    private String archiverEngin="Archiver Engin";
    private String affecterEngin="Affecter Engin";
    private String actionEngin="Action Engin";
    private String uploadDocEngin="Uploader document Engin";

//AccidentTravail
    
    private String validerRh ="valider at rh";
    private String validerQhse ="valider at qhse";
    private String receptionCertif ="reception certif";
    private String gestionQuitance ="gestion quittance";
    
    
    
    

    /**
     * getter and setter
     *
     * @return
     */
    
    
       public String getAjouterEngin() {
        return ajouterEngin;
    }

    public void setAjouterEngin(String ajouterEngin) {
        this.ajouterEngin = ajouterEngin;
    }

    public String getReformerEngin() {
        return reformerEngin;
    }

    public void setReformerEngin(String reformerEngin) {
        this.reformerEngin = reformerEngin;
    }

    public String getArchiverEngin() {
        return archiverEngin;
    }

    public void setArchiverEngin(String archiverEngin) {
        this.archiverEngin = archiverEngin;
    }

    public String getAffecterEngin() {
        return affecterEngin;
    }

    public void setAffecterEngin(String affecterEngin) {
        this.affecterEngin = affecterEngin;
    }

    public String getActionEngin() {
        return actionEngin;
    }

    public void setActionEngin(String actionEngin) {
        this.actionEngin = actionEngin;
    }

    public String getUploadDocEngin() {
        return uploadDocEngin;
    }
    public void setUploadDocEngin(String uploadDocEngin) {
        this.uploadDocEngin = uploadDocEngin;
    }

    public static void setRepertoire(String repertoire) {
        ConstanteMb.repertoire = repertoire;
    }

    public static String getRepertoire() {
        return repertoire;
    }
    
    
    
    public Date getMaxDate() {
        maxDate = new Date(); 
        Calendar c = Calendar.getInstance(); 
        c.setTime(maxDate); 
        c.add(Calendar.DATE, 3);
        maxDate = c.getTime();
        return maxDate;
    }
    
    

    public void setMaxDate(Date maxDate) {
        this.maxDate = maxDate;
    }

    public String getListeSalaries() {
        return listeSalaries;
    }

    public void setListeSalaries(String listeSalaries) {
        this.listeSalaries = listeSalaries;
    }

    public String getAjouterSalarie() {
        return ajouterSalarie;
    }

    public void setAjouterSalarie(String ajouterSalarie) {
        this.ajouterSalarie = ajouterSalarie;
    }

    public String getModifierSalarie() {
        return modifierSalarie;
    }

    public void setModifierSalarie(String modifierSalarie) {
        this.modifierSalarie = modifierSalarie;
    }

    public String getDetailSalarie() {
        return detailSalarie;
    }

    public void setDetailSalarie(String detailSalarie) {
        this.detailSalarie = detailSalarie;
    }

    public String getModifierEtat() {
        return modifierEtat;
    }

    public void setModifierEtat(String modifierEtat) {
        this.modifierEtat = modifierEtat;
    }

    public String getReglerOuvrier() {
        return reglerOuvrier;
    }

    public void setReglerOuvrier(String reglerOuvrier) {
        this.reglerOuvrier = reglerOuvrier;
    }

    public String getReinitialiserDateFinContrat() {
        return reinitialiserDateFinContrat;
    }

    public void setReinitialiserDateFinContrat(String reinitialiserDateFinContrat) {
        this.reinitialiserDateFinContrat = reinitialiserDateFinContrat;
    }

    public String getChangerTauxFonction() {
        return changerTauxFonction;
    }

    public void setChangerTauxFonction(String changerTauxFonction) {
        this.changerTauxFonction = changerTauxFonction;
    }

    public String getSupprimerUnEnfant() {
        return supprimerUnEnfant;
    }

    public void setSupprimerUnEnfant(String supprimerUnEnfant) {
        this.supprimerUnEnfant = supprimerUnEnfant;
    }

    public String getChangerStatutContrat() {
        return changerStatutContrat;
    }

    public void setChangerStatutContrat(String changerStatutContrat) {
        this.changerStatutContrat = changerStatutContrat;
    }

    public String getListePresences() {
        return listePresences;
    }

    public void setListePresences(String listePresences) {
        this.listePresences = listePresences;
    }
    
    public String getModifierRIB() {
        return modifierRIB;
    }


    public String getPointageEntree() {
        return pointageEntree;
    }

    public void setPointageEntree(String pointageEntree) {
        this.pointageEntree = pointageEntree;
    }

    public String getPointageSortie() {
        return pointageSortie;
    }

    public void setPointageSortie(String pointageSortie) {
        this.pointageSortie = pointageSortie;
    }

    public String getPointageSalarie() {
        return pointageSalarie;
    }

    public void setPointageSalarie(String pointageSalarie) {
        this.pointageSalarie = pointageSalarie;
    }

    public String getPointagesEntree() {
        return pointagesEntree;
    }

    public void setPointagesEntree(String pointagesEntree) {
        this.pointagesEntree = pointagesEntree;
    }

    public String getPointagesSortie() {
        return pointagesSortie;
    }

    public void setPointagesSortie(String pointagesSortie) {
        this.pointagesSortie = pointagesSortie;
    }

    public String getListeHS() {
        return listeHS;
    }

    public void setListeHS(String listeHS) {
        this.listeHS = listeHS;
    }

    public String getAjouterHS() {
        return ajouterHS;
    }

    public void setAjouterHS(String ajouterHS) {
        this.ajouterHS = ajouterHS;
    }

    public String getAnnulerPointage() {
        return annulerPointage;
    }

    public void setAnnulerPointage(String annulerPointage) {
        this.annulerPointage = annulerPointage;
    }

    public String getPointageMassif() {
        return pointageMassif;
    }

    public void setPointageMassif(String pointageMassif) {
        this.pointageMassif = pointageMassif;
    }

    public String getListeAbsences() {
        return listeAbsences;
    }

    public void setListeAbsences(String listeAbsences) {
        this.listeAbsences = listeAbsences;
    }

    public String getAjouterAbsence() {
        return ajouterAbsence;
    }

    public void setAjouterAbsence(String ajouterAbsence) {
        this.ajouterAbsence = ajouterAbsence;
    }

    public String getModifierAbsence() {
        return modifierAbsence;
    }

    public void setModifierAbsence(String modifierAbsence) {
        this.modifierAbsence = modifierAbsence;
    }

    public String getListeChantiers() {
        return listeChantiers;
    }

    public void setListeChantiers(String listeChantiers) {
        this.listeChantiers = listeChantiers;
    }

    public String getAjouterChantier() {
        return ajouterChantier;
    }

    public void setAjouterChantier(String ajouterChantier) {
        this.ajouterChantier = ajouterChantier;
    }

    public String getLocaliserChantier() {
        return localiserChantier;
    }

    public void setLocaliserChantier(String localiserChantier) {
        this.localiserChantier = localiserChantier;
    }

    public String getListeUtilisateurs() {
        return listeUtilisateurs;
    }

    public void setListeUtilisateurs(String listeUtilisateurs) {
        this.listeUtilisateurs = listeUtilisateurs;
    }

    public String getAjouterUtilisateur() {
        return ajouterUtilisateur;
    }

    public void setAjouterUtilisateur(String ajouterUtilisateur) {
        this.ajouterUtilisateur = ajouterUtilisateur;
    }

    public String getListeUtilisateursGroupe() {
        return listeUtilisateursGroupe;
    }

    public void setListeUtilisateursGroupe(String listeUtilisateursGroupe) {
        this.listeUtilisateursGroupe = listeUtilisateursGroupe;
    }

    public String getDroitsUtilisateur() {
        return droitsUtilisateur;
    }

    public void setDroitsUtilisateur(String droitsUtilisateur) {
        this.droitsUtilisateur = droitsUtilisateur;
    }

    public String getModifierGroupesUtilisateur() {
        return modifierGroupesUtilisateur;
    }

    public void setModifierGroupesUtilisateur(String modifierGroupesUtilisateur) {
        this.modifierGroupesUtilisateur = modifierGroupesUtilisateur;
    }

    public String getListeGroupes() {
        return listeGroupes;
    }

    public void setListeGroupes(String listeGroupes) {
        this.listeGroupes = listeGroupes;
    }

    public String getAjouterGroupe() {
        return ajouterGroupe;
    }

    public void setAjouterGroupe(String ajouterGroupe) {
        this.ajouterGroupe = ajouterGroupe;
    }

    public String getDroitsGroupe() {
        return droitsGroupe;
    }

    public void setDroitsGroupe(String droitsGroupe) {
        this.droitsGroupe = droitsGroupe;
    }

    public Date getMinDateNaissance() {
        minDateNaissance = new Date();
        minDateNaissance.setYear(maxDate.getYear() - 18);
        return minDateNaissance;
    }

    public void setMinDateNaissance(Date minDateNaissance) {
        this.minDateNaissance = minDateNaissance;
    }

    public String getModifierTauxHoraire() {
        return modifierTauxHoraire;
    }

    public void setModifierTauxHoraire(String modifierTauxHoraire) {
        this.modifierTauxHoraire = modifierTauxHoraire;
    }

    public String getValiderRh() {
        return validerRh;
    }

    public void setValiderRh(String validerRh) {
        this.validerRh = validerRh;
    }

    public String getValiderQhse() {
        return validerQhse;
    }

    public void setValiderQhse(String validerQhse) {
        this.validerQhse = validerQhse;
    }

    public String getReceptionCertif() {
        return receptionCertif;
    }

    public void setReceptionCertif(String receptionCertif) {
        this.receptionCertif = receptionCertif;
    }

    public String getGestionQuitance() {
        return gestionQuitance;
    }

    public void setGestionQuitance(String gestionQuitance) {
        this.gestionQuitance = gestionQuitance;
    }
    
    

    @PostConstruct
    public void init() {
        maxDate = new Date();
        System.out.println("max date " + maxDate);
        minDateNaissance = new Date();
        minDateNaissance.setYear(maxDate.getYear() - 18);
        System.out.println("date min date naissance " + minDateNaissance);
    }

    /**
     * parse date format to dd-MM-yyyy
     *
     * @param date
     * @return String
     * @throws ParseException
     */
    public String getDateFormatFR(Date date) throws ParseException {
        if (date != null) {
            //System.out.println("date  input " + date);
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            return df.format(date);
        } else {
            return "";
        }
    }

    /**
     * get output stream from file input
     *
     * @param path to get
     * @return output stream
     */
    public StreamedContent displayFile(String path) {
        try {
            if (FacesContext.getCurrentInstance().getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
                //System.out.println("phase is render response");
                return new DefaultStreamedContent();
            }
            String absoluteWebPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
            File webRoot = new File(absoluteWebPath + "/" + path);
            //System.out.println("file with absolute path " + webRoot.getAbsolutePath());
            byte[] buffer = Files.readAllBytes(webRoot.toPath());
            ByteArrayOutputStream baos = new ByteArrayOutputStream(buffer.length);
            baos.write(buffer, 0, buffer.length);
            //System.out.println("\n\n\nbuffer " + Arrays.toString(buffer) + "\n\n");

            String mimeType = URLConnection.guessContentTypeFromName(webRoot.getName());
            //System.out.println("1 - mime Type " + mimeType);
            InputStream inputStream;
            Byte[] byteObjects = new Byte[buffer.length];
            inputStream = new ByteArrayInputStream(ArrayUtils.toPrimitive(byteObjects));
            //System.out.println("\n\n input stream " + inputStream.toString() + " \n");

            StreamedContent result = new DefaultStreamedContent(inputStream, mimeType, webRoot.getName());
            //System.out.println("\n\n result " + result.getName() + " \n");
            return result;
        } catch (IOException ex) {
            System.out.println("erreur buffer output stream\n\n\t" + ex.getMessage() + "\n\n end erreur outputStream");
        }
        return null;

    }
}
