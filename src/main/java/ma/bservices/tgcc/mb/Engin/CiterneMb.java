/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.mb.Engin;

import com.itextpdf.text.DocumentException;
import java.io.IOException;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
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
import ma.bservices.beans.Utilisateur;
import ma.bservices.tgcc.Entity.Bon_Livraison_Citerne;
import ma.bservices.tgcc.Entity.Citerne;
import ma.bservices.tgcc.Entity.Engin;
import ma.bservices.tgcc.Entity.Mensuel;
import ma.bservices.tgcc.Entity.TraceBonLivraisonCiterne;
import ma.bservices.tgcc.Entity.TraceCiterne;
import ma.bservices.tgcc.Entity.TraceUtilisateur;
import ma.bservices.tgcc.Entity.Voiture;
import ma.bservices.tgcc.service.Engin.Bean.CiterneServiceBean;
import ma.bservices.tgcc.service.Engin.CiterneService;
import ma.bservices.tgcc.service.Engin.EnginService;
import ma.bservices.tgcc.service.Engin.ITraceUtilisateurService;
import ma.bservices.tgcc.service.Engin.LivraisonCiterneService;
import ma.bservices.tgcc.service.Engin.TraceUtilisateurServiceImp;
import ma.bservices.tgcc.service.Engin.UtilisateurService;
import ma.bservices.tgcc.service.Mensuel.MensuelService;
import ma.bservices.tgcc.service.SendEmail;
import ma.bservices.tgcc.utilitaire.Outils;
import ma.bservices.tgcc.utilitaire.RemplireTrace;
import org.apache.commons.io.FilenameUtils;
import org.primefaces.context.RequestContext;
import org.primefaces.model.UploadedFile;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

/**
 *
 * @author a.wattah
 */
@Component("citerneMb")
@ManagedBean(name = "citerneMb")
@ViewScoped
public class CiterneMb implements Serializable {

    @ManagedProperty(value = "#{citerneService}")
    private CiterneService citerneService;

    @ManagedProperty(value = "#{utilisateurService}")
    private UtilisateurService utilisateurService;

    @ManagedProperty(value = "#{livraisonCiterneService}")
    private LivraisonCiterneService livraisonCiterneService;

    @ManagedProperty(value = "#{enginService}")
    private EnginService enginService;

    @ManagedProperty(value = "#{mensuelService}")
    private MensuelService mensuelService;

    @ManagedProperty(value = "#{traceUtilisateurService}")
    private ITraceUtilisateurService traceUtilisateurService;


    private CiterneServiceBean citerneServiceBean;
    private boolean isButtonVisible = true;
    private boolean closeBGMWindow = false;
    private Citerne citerne;
    private Citerne citerne_bon_gasoil_mensuel;
    private Engin engin=new Engin(); 
    private Bon_Livraison_Citerne bon_gasoil = new Bon_Livraison_Citerne();
    private Bon_Livraison_Citerne bon_gasoil_To_Edit = new Bon_Livraison_Citerne();
    
    private Utilisateur u  = new Utilisateur();
    
    private Citerne citerne_ToDetail_livraison;
    private Citerne historique_searchBonLivraison;
    private Bon_Livraison_Citerne bon_Livraison_Citerne_historique;
    private Bon_Livraison_Citerne bon_gasoil_mensuel_to_add;
    private Mensuel mensuel;
    private Mensuel mensuel_to_search = new Mensuel();
    private Citerne citerne_bon_caisse;
    private Double volume_actuel;
    private Double Volume_actuelTonne;
    private Double afficherVolumeTonne;
    private Date date_Bon_livraison;
    private Date date_bon_caisse;
    private String Kilométrage_bon_caisse;
    private String heure_engin;
    private Double Tonnes_bonCaisse;
    private UploadedFile uploadedFile;
    private String l_engin_to_bonCaisse;
    private Bon_Livraison_Citerne bon_Livraison_Citerne_to_add_boncaisse, bon_temp;
    private String numero_commande_livraison;
    private String numero_commande;
    private Boolean display_button_telecharger = false;
    private Boolean display_button_telecharger_bon_gasoil_mensuel = false;
    private String numero_commande_bon_caisse;
    private Double nombre_litre_bon_gasoil_mensuel;
    private String num_commande_bon_gasoil_mensuel;
    private String id_mensuel;
    private Date date_bon_gasoil_mensuel;
    private Date date_historique_search;
    private String action_search;
    private String numCommande_search;
    private String numLivraison_search;
    private Boolean kilometrique_display = false;
    private Boolean heure_display = false;
    private Integer chantierSecSelectionneBonGasoilEngin;
    private Boolean checkBtn;
    private String cheminBGEngin;
    private Double tons_max_afficherLivraison;
    private String commentBl;
    private TraceCiterne traceCitern;
    private Citerne citernDist;
    private Citerne citernSrc;
    private Integer idCiternTrans;
    private String codeEnginMvmtEdit;
    
    private Float cptK;
    private Float cptH;
    private float cptHr = 0,cptKl = 0;
    
    private List<Mensuel> mensuels=new ArrayList<Mensuel>();
    private List<Voiture> voitureSalaries=new ArrayList<Voiture>();
    private List<Chantier> l_chantierSecBonGasoilEngin=new ArrayList<Chantier>();
    public  List<Engin> l_enginBonGasoilSecCopie=new ArrayList<Engin>();
    public  List<Engin> allEngins=new ArrayList<Engin>();
    private List<Bon_Livraison_Citerne> l_Historiqes=new ArrayList<Bon_Livraison_Citerne>();
    private List<Bon_Livraison_Citerne> l_detail_citerne_historique=new ArrayList<Bon_Livraison_Citerne>();
    private List<Chantier> l_chantier_sec_bon_caisse=new ArrayList<Chantier>() ;
    private List<Engin> l_engin_chantier_bon_caisse = new ArrayList<>();
    private List<Engin> listEnginCiterne = new ArrayList<>();
    private List<Citerne> l_citernes;
    private List<Citerne> l_citerneTransfaire;
    private List<TraceCiterne> traceCiternes = new ArrayList<TraceCiterne>();


    /**
     * getters and setters
     *
     * @return
     */
    
    
    public Bon_Livraison_Citerne getBon_gasoil_To_Edit() {    
        return bon_gasoil_To_Edit;
    }
    public void setBon_gasoil_To_Edit(Bon_Livraison_Citerne bon_gasoil_To_Edit) {
        this.bon_gasoil_To_Edit = bon_gasoil_To_Edit;
    }

    public Integer getIdCiternTrans() {
        return idCiternTrans;
    }

    public void setIdCiternTrans(Integer idCiternTrans) {
        this.idCiternTrans = idCiternTrans;
    }

    public Citerne getCiternDist() {
        return citernDist;
    }

    public void setCiternDist(Citerne citernDist) {
        this.citernDist = citernDist;
    }

    public Citerne getCiternSrc() {
        return citernSrc;
    }
    public void setCiternSrc(Citerne citernSrc) {
        this.citernSrc = citernSrc;
    }

    public TraceCiterne getTraceCitern() {
        return traceCitern;
    }

    public void setTraceCitern(TraceCiterne traceCitern) {
        this.traceCitern = traceCitern;
    }

    public List<Citerne> getL_citerneTransfaire() {
        return l_citerneTransfaire;
    }

    public void setL_citerneTransfaire(List<Citerne> l_citerneTransfaire) {
        this.l_citerneTransfaire = l_citerneTransfaire;
    }

    public List<TraceCiterne> getTraceCiternes() {
        return traceCiternes;
    }
    public void setTraceCiternes(List<TraceCiterne> traceCiternes) {
        this.traceCiternes = traceCiternes;
    }

    public float getCptHr() {
        return cptHr;
    }

    public void setCptHr(float cptHr) {
        this.cptHr = cptHr;
    }

    public float getCptKl() {
        return cptKl;
    }
    
    public void setCptKl(float cptKl) {
        this.cptKl = cptKl;
    }

    public void setListEnginCiterne(List<Engin> listEnginCiterne) {
        this.listEnginCiterne = listEnginCiterne;
    }
    public List<Engin> getListEnginCiterne() {
        return listEnginCiterne;
    }

    public UtilisateurService getUtilisateurService() {
        return utilisateurService;
    }

    public void setUtilisateurService(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    public String getCheminBGEngin() {
        return cheminBGEngin;
    }

    public void setCheminBGEngin(String cheminBGEngin) {
        this.cheminBGEngin = cheminBGEngin;
    }

    public Bon_Livraison_Citerne getBon_temp() {
        return bon_temp;
    }

    public void setBon_temp(Bon_Livraison_Citerne bon_temp) {
        this.bon_temp = bon_temp;
    }

    public Mensuel getMensuel_to_search() {
        return mensuel_to_search;
    }

    public void setMensuel_to_search(Mensuel mensuel_to_search) {
        this.mensuel_to_search = mensuel_to_search;
    }

    public List<Mensuel> getMensuels() {
        return mensuels;
    }

    public Citerne getHistorique_searchBonLivraison() {
        return historique_searchBonLivraison;
    }

    public void setHistorique_searchBonLivraison(Citerne historique_searchBonLivraison) {
        this.historique_searchBonLivraison = historique_searchBonLivraison;
    }

    public void setMensuels(List<Mensuel> mensuels) {
        this.mensuels = mensuels;
    }

    public boolean isIsButtonVisible() {
        return isButtonVisible;
    }

    public void setIsButtonVisible(boolean isButtonVisible) {
        this.isButtonVisible = isButtonVisible;
    }

    public String getNumCommande_search() {
        return numCommande_search;
    }

    public void setNumCommande_search(String numCommande_search) {
        this.numCommande_search = numCommande_search;
    }

    public String getNumLivraison_search() {
        return numLivraison_search;
    }

    public void setNumLivraison_search(String numLivraison_search) {
        this.numLivraison_search = numLivraison_search;
    }

    public MensuelService getMensuelService() {
        return mensuelService;
    }

    public void setMensuelService(MensuelService mensuelService) {
        this.mensuelService = mensuelService;
    }

    public EnginService getEnginService() {
        return enginService;
    }

    public void setEnginService(EnginService enginService) {
        this.enginService = enginService;
    }

    public boolean isCloseBGMWindow() {
        return closeBGMWindow;
    }

    public void setCloseBGMWindow(boolean closeBGMWindow) {
        this.closeBGMWindow = closeBGMWindow;
    }

    public Boolean getKilometrique_display() {
        return kilometrique_display;
    }

    public void setKilometrique_display(Boolean kilometrique_display) {
        this.kilometrique_display = kilometrique_display;
    }

    public Boolean getHeure_display() {
        return heure_display;
    }

    public void setHeure_display(Boolean heure_display) {
        this.heure_display = heure_display;
    }

    public String getHeure_engin() {
        return heure_engin;
    }

    public void setHeure_engin(String heure_engin) {
        this.heure_engin = heure_engin;
    }

    public String getAction_search() {
        return action_search;
    }


    public List<Voiture> getVoitureSalaries() {
        return voitureSalaries;
    }

    public void setVoitureSalaries(List<Voiture> voitureSalaries) {
        this.voitureSalaries = voitureSalaries;
    }

    public Mensuel getMensuel() {
        return mensuel;
    }

    public void setMensuel(Mensuel mensuel) {
        this.mensuel = mensuel;
    }

    public void setAction_search(String action_search) {
        this.action_search = action_search;
    }

    public Date getDate_historique_search() {
        return date_historique_search;
    }

    public void setDate_historique_search(Date date_historique_search) {
        this.date_historique_search = date_historique_search;
    }

    public Boolean getDisplay_button_telecharger_bon_gasoil_mensuel() {
        return display_button_telecharger_bon_gasoil_mensuel;
    }

    public void setDisplay_button_telecharger_bon_gasoil_mensuel(Boolean display_button_telecharger_bon_gasoil_mensuel) {
        this.display_button_telecharger_bon_gasoil_mensuel = display_button_telecharger_bon_gasoil_mensuel;
    }

    public Bon_Livraison_Citerne getBon_gasoil_mensuel_to_add() {
        return bon_gasoil_mensuel_to_add;
    }

    public Boolean getCheckBtn() {
        return checkBtn;
    }

    public void setCheckBtn(Boolean checkBtn) {
        this.checkBtn = checkBtn;
    }

    public void setBon_gasoil_mensuel_to_add(Bon_Livraison_Citerne bon_gasoil_mensuel_to_add) {
        this.bon_gasoil_mensuel_to_add = bon_gasoil_mensuel_to_add;
    }

    public Date getDate_bon_gasoil_mensuel() {
        return date_bon_gasoil_mensuel;
    }

    public void setDate_bon_gasoil_mensuel(Date date_bon_gasoil_mensuel) {
        this.date_bon_gasoil_mensuel = date_bon_gasoil_mensuel;
    }

    public String getId_mensuel() {
        return id_mensuel;
    }

    public void setId_mensuel(String id_mensuel) {
        this.id_mensuel = id_mensuel;
    }

    public Citerne getCiterne_bon_gasoil_mensuel() {
        return citerne_bon_gasoil_mensuel;
    }

    public void setCiterne_bon_gasoil_mensuel(Citerne citerne_bon_gasoil_mensuel) {
        this.citerne_bon_gasoil_mensuel = citerne_bon_gasoil_mensuel;
    }

    public Double getNombre_litre_bon_gasoil_mensuel() {
        return nombre_litre_bon_gasoil_mensuel;
    }

    public void setNombre_litre_bon_gasoil_mensuel(Double nombre_litre_bon_gasoil_mensuel) {
        this.nombre_litre_bon_gasoil_mensuel = nombre_litre_bon_gasoil_mensuel;
    }

    public String getNum_commande_bon_gasoil_mensuel() {
        return num_commande_bon_gasoil_mensuel;
    }

    public void setNum_commande_bon_gasoil_mensuel(String num_commande_bon_gasoil_mensuel) {
        this.num_commande_bon_gasoil_mensuel = num_commande_bon_gasoil_mensuel;
    }

    public List<Bon_Livraison_Citerne> getL_detail_citerne_historique() {
        return l_detail_citerne_historique;
    }

    public void setL_detail_citerne_historique(List<Bon_Livraison_Citerne> l_detail_citerne_historique) {
        this.l_detail_citerne_historique = l_detail_citerne_historique;
    }

    public Bon_Livraison_Citerne getBon_Livraison_Citerne_historique() {
        return bon_Livraison_Citerne_historique;
    }

    public void setBon_Livraison_Citerne_historique(Bon_Livraison_Citerne bon_Livraison_Citerne_historique) {
        this.bon_Livraison_Citerne_historique = bon_Livraison_Citerne_historique;
    }

    public List<Bon_Livraison_Citerne> getL_Historiqes() {
        return l_Historiqes;
    }

    public void setL_Historiqes(List<Bon_Livraison_Citerne> l_Historiqes) {
        this.l_Historiqes = l_Historiqes;
    }

    public String getNumero_commande_livraison() {
        return numero_commande_livraison;
    }

    public void setNumero_commande_livraison(String numero_commande_livraison) {
        this.numero_commande_livraison = numero_commande_livraison;
    }

    public String getNumero_commande_bon_caisse() {
        return numero_commande_bon_caisse;
    }

    public void setNumero_commande_bon_caisse(String numero_commande_bon_caisse) {
        this.numero_commande_bon_caisse = numero_commande_bon_caisse;
    }

    public Bon_Livraison_Citerne getBon_Livraison_Citerne_to_add_boncaisse() {
        return bon_Livraison_Citerne_to_add_boncaisse;
    }

    public void setBon_Livraison_Citerne_to_add_boncaisse(Bon_Livraison_Citerne bon_Livraison_Citerne_to_add_boncaisse) {
        this.bon_Livraison_Citerne_to_add_boncaisse = bon_Livraison_Citerne_to_add_boncaisse;
    }

    public Boolean getDisplay_button_telecharger() {
        return display_button_telecharger;
    }

    public void setDisplay_button_telecharger(Boolean display_button_telecharger) {
        this.display_button_telecharger = display_button_telecharger;
    }

    public String getKilométrage_bon_caisse() {
        return Kilométrage_bon_caisse;
    }

    public void setKilométrage_bon_caisse(String Kilométrage_bon_caisse) {
        this.Kilométrage_bon_caisse = Kilométrage_bon_caisse;
    }

    public Double getTonnes_bonCaisse() {
        return Tonnes_bonCaisse;
    }

    public void setTonnes_bonCaisse(Double Tonnes_bonCaisse) {
        this.Tonnes_bonCaisse = Tonnes_bonCaisse;
    }

    public Date getDate_bon_caisse() {
        return date_bon_caisse;
    }

    public void setDate_bon_caisse(Date date_bon_caisse) {
        this.date_bon_caisse = date_bon_caisse;
    }

    public String getL_engin_to_bonCaisse() {
        return l_engin_to_bonCaisse;
    }

    public void setL_engin_to_bonCaisse(String l_engin_to_bonCaisse) {
        this.l_engin_to_bonCaisse = l_engin_to_bonCaisse;
    }

    public List<Engin> getL_engin_chantier_bon_caisse() {
        return l_engin_chantier_bon_caisse;
    }

    public void setL_engin_chantier_bon_caisse(List<Engin> l_engin_chantier_bon_caisse) {
        this.l_engin_chantier_bon_caisse = l_engin_chantier_bon_caisse;
    }

    public List<Chantier> getL_chantier_sec_bon_caisse() {
        return l_chantier_sec_bon_caisse;
    }

    public void setL_chantier_sec_bon_caisse(List<Chantier> l_chantier_sec_bon_caisse) {
        this.l_chantier_sec_bon_caisse = l_chantier_sec_bon_caisse;
    }

    public Citerne getCiterne_bon_caisse() {
        return citerne_bon_caisse;
    }

    public void setCiterne_bon_caisse(Citerne citerne_bon_caisse) {
        this.citerne_bon_caisse = citerne_bon_caisse;
    }

    public CiterneServiceBean getCiterneServiceBean() {
        return citerneServiceBean;
    }

    public void setCiterneServiceBean(CiterneServiceBean citerneServiceBean) {
        this.citerneServiceBean = citerneServiceBean;
    }

    public Double getVolume_actuel() {
        return volume_actuel;
    }

    public void setVolume_actuel(Double volume_actuel) {
        this.volume_actuel = volume_actuel;
    }

    public LivraisonCiterneService getLivraisonCiterneService() {
        return livraisonCiterneService;
    }

    public void setLivraisonCiterneService(LivraisonCiterneService livraisonCiterneService) {
        this.livraisonCiterneService = livraisonCiterneService;
    }

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public Date getDate_Bon_livraison() {
        return date_Bon_livraison;
    }

    public void setDate_Bon_livraison(Date date_Bon_livraison) {
        this.date_Bon_livraison = date_Bon_livraison;
    }

    public List<Citerne> getL_citernes() {
        return l_citernes;
    }

    public void setL_citernes(List<Citerne> l_citernes) {
        this.l_citernes = l_citernes;
    }

    public CiterneService getCiterneService() {
        return citerneService;
    }

    public void setCiterneService(CiterneService citerneService) {
        this.citerneService = citerneService;
    }

    public Citerne getCiterne() {
        return citerne;
    }

    public void setCiterne(Citerne citerne) {
        this.citerne = citerne;
    }

    public Citerne getCiterne_ToDetail_livraison() {
        return citerne_ToDetail_livraison;
    }

    public void setCiterne_ToDetail_livraison(Citerne citerne_ToDetail_livraison) {
        this.citerne_ToDetail_livraison = citerne_ToDetail_livraison;
    }

    public String getNumero_commande() {
        return numero_commande;
    }

    public void setNumero_commande(String numero_commande) {
        this.numero_commande = numero_commande;
    }

    public Double getTons_max_afficherLivraison() {
        return tons_max_afficherLivraison;
    }

    public void setTons_max_afficherLivraison(Double tons_max_afficherLivraison) {
        this.tons_max_afficherLivraison = tons_max_afficherLivraison;
    }

    public Double getAfficherVolumeTonne() {
        return afficherVolumeTonne;
    }

    public void setAfficherVolumeTonne(Double afficherVolumeTonne) {
        this.afficherVolumeTonne = afficherVolumeTonne;
    }

    public List<Chantier> getL_chantierSecBonGasoilEngin() {
        return l_chantierSecBonGasoilEngin;
    }

    public void setL_chantierSecBonGasoilEngin(List<Chantier> l_chantierSecBonGasoilEngin) {
        this.l_chantierSecBonGasoilEngin = l_chantierSecBonGasoilEngin;
    }

    public Integer getChantierSecSelectionneBonGasoilEngin() {
        return chantierSecSelectionneBonGasoilEngin;
    }

    public void setChantierSecSelectionneBonGasoilEngin(Integer chantierSecSelectionneBonGasoilEngin) {
        this.chantierSecSelectionneBonGasoilEngin = chantierSecSelectionneBonGasoilEngin;
    }

    public List<Engin> getL_enginBonGasoilSecCopie() {
        return l_enginBonGasoilSecCopie;
    }

    public void setL_enginBonGasoilSecCopie(List<Engin> l_enginBonGasoilSecCopie) {
        this.l_enginBonGasoilSecCopie = l_enginBonGasoilSecCopie;
    }

    public Double getVolume_actuelTonne() {
        return Volume_actuelTonne;
    }

    public void setVolume_actuelTonne(Double Volume_actuelTonne) {
        this.Volume_actuelTonne = Volume_actuelTonne;
    }

    public Engin getEngin() {
        return engin;
    }

    public void setEngin(Engin engin) {
        this.engin = engin;
    }

    public String getCommentBl() {
        return commentBl;
    }

    public void setCommentBl(String commentBl) {
        this.commentBl = commentBl;
    }

    public Bon_Livraison_Citerne getBon_gasoil() {
        return bon_gasoil;
    }

    public void setBon_gasoil(Bon_Livraison_Citerne bon_gasoil) {
        this.bon_gasoil = bon_gasoil;
    }

    public Utilisateur getU() {
        return u;
    }

    public void setU(Utilisateur u) {
        this.u = u;
    }

    public Float getCptK() {
        return cptK;
    }

    public void setCptK(Float cptK) {
        this.cptK = cptK;
    }

    public Float getCptH() {
        return cptH;
    }

    public void setCptH(Float cptH) {
        this.cptH = cptH;
    }

    public String getCodeEnginMvmtEdit() {
        return codeEnginMvmtEdit;
    }

    public void setCodeEnginMvmtEdit(String codeEnginMvmtEdit) {
        this.codeEnginMvmtEdit = codeEnginMvmtEdit;
    }

    public List<Engin> getAllEngins() {
        return allEngins;
    }

    public void setAllEngins(List<Engin> allEngins) {
        this.allEngins = allEngins;
    }

    public ITraceUtilisateurService getTraceUtilisateurService() {
        return traceUtilisateurService;
    }

    public void setTraceUtilisateurService(ITraceUtilisateurService traceUtilisateurService) {
        this.traceUtilisateurService = traceUtilisateurService;
    }

    

    
    
    

    /**
     * end getters and setters
     */
    @PostConstruct
    public void init() {
        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        citerneService = ctx.getBean(CiterneService.class);
        livraisonCiterneService = ctx.getBean(LivraisonCiterneService.class);
        enginService = ctx.getBean(EnginService.class);
        mensuelService = ctx.getBean(MensuelService.class);
        utilisateurService = ctx.getBean(UtilisateurService.class);

        citerne = new Citerne();
        citerne_ToDetail_livraison = new Citerne();
        citerne_bon_caisse = new Citerne();
        citerneServiceBean = new CiterneServiceBean();
        date_Bon_livraison = new Date();
        date_bon_caisse = new Date();
        bon_Livraison_Citerne_to_add_boncaisse = new Bon_Livraison_Citerne();
        citerne_bon_gasoil_mensuel = new Citerne();
        bon_gasoil_mensuel_to_add = new Bon_Livraison_Citerne();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String user = auth.getPrincipal().toString();
        u = utilisateurService.getUsersByLogin(user); 
        
        date_bon_gasoil_mensuel = new Date();
        mensuel = new Mensuel();
        mensuel_to_search = new Mensuel();
        mensuels = new ArrayList<>();
        historique_searchBonLivraison = new Citerne();

    }
    
    public void chargerCiterne(){
        List<Chantier> listCh = utilisateurService.findChantiersByUser(u);    
        l_citernes = citerneService.find_all_Citerne();
        List<Citerne> temp = new LinkedList<>();
        if (l_citernes != null && listCh != null) {
            for (Citerne c : l_citernes) {
                if (listCh.contains(c.getChantier_Principal())) {
                    temp.add(c);
                }
            }
        }   
        
        l_citernes = new LinkedList<>();
        l_citernes.addAll(temp);
        
        //l_Historiqes = livraisonCiterneService.l_historiques();
    }

    /**
     * methode redirect vers popu livraison
     *
     * @param selected
     */
    public void redirect_pop_livraison(Citerne selected) {
        citerne_ToDetail_livraison = selected;

        tons_max_afficherLivraison = 0.0;
        try {
            
            if (selected.getType_citerne().equals("Diesel")) {
                tons_max_afficherLivraison = selected.getCapacite() * 0.8 / 1000;

            } else if (selected.getType_citerne().equals("Essence")) {
                tons_max_afficherLivraison = selected.getCapacite() * 0.79 / 1000;
            }

            converti(citerne_ToDetail_livraison.getCapacite() - citerne_ToDetail_livraison.getVolume_actuel_(), citerne_ToDetail_livraison.getType_citerne());

            } catch (Exception e) {
                System.out.println("Erreur de chargement les données car "+e.getMessage());
           }
        }
    
    public void refrechBl(){
          redirect_pop_livraison(citerne_ToDetail_livraison);
    }

    /**
     * methode pour sauvegarde livraison + fichier
     *
     * @throws java.io.IOException
     */
    public void save() throws IOException {

        boolean value = false;
//        litreToTon();
        List<Bon_Livraison_Citerne> l_his_s = this.livraisonCiterneService.l_historiques();
        /*System.out.println("+++++++> numero_commande : "+numero_commande);
        for (Bon_Livraison_Citerne l_histori : l_his_s) {
        System.out.println("+++++++> numero_commande : "+l_histori.getNumero_commande());
            if (l_histori.getCiterne() != null) {
                if (l_histori.getCiterne().getArchiver() != null) {
                    if (l_histori.getCiterne().getArchiver().equals(false)) {
                        if (l_histori.getNumero_commande() != null) {
                            if (l_histori.getNumero_commande().equals(numero_commande)) {
                                value = true;
                            }
                        }

                    }
                }
            }
        }*/

        Double volume_somme = 0d;

        if (uploadedFile == null) {
//            tonToLitre();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, Message.CHARGE_DOCUMERNT, Message.CHARGE_DOCUMERNT));

        } else if (uploadedFile.getFileName().equals("")) {
//            tonToLitre();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, Message.CHARGE_DOCUMERNT, Message.CHARGE_DOCUMERNT));
        } else if (!"pdf".equals(FilenameUtils.getExtension(uploadedFile.getFileName()))) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, Message.STRING_CHARGE_DOCUMENT_PDF, Message.STRING_CHARGE_DOCUMENT_PDF));
//            tonToLitre();

        }// else if (value == true) {
//            tonToLitre();
         //  FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, Message.NUM_COMMANDE, Message.NUM_COMMANDE));
        //} 
        else {

            if (volume_actuel != null && this.citerne_ToDetail_livraison.getVolume_actuel_() != null) {
                volume_somme = (volume_actuel * 1000) + this.getCiterne_ToDetail_livraison().getVolume_actuel_();
            }
            if (volume_somme > this.citerne_ToDetail_livraison.getCapacite()) {
//            tonToLitre();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, Message.VOLUME_CAPACITE_CITERNE_DEPASSE, Message.VOLUME_CAPACITE_CITERNE_DEPASSE));

            } else {
                Bon_Livraison_Citerne bon_Livraison_Citerne = new Bon_Livraison_Citerne();

                bon_Livraison_Citerne.setChemin_fichier(this.citerneServiceBean.upload_fichier(uploadedFile));

//                Integer volume = this.citerneServiceBean.getSomme_volume_actuel(this.citerne_ToDetail_livraison.getVolume_actuel(), volume_actuel);
                System.out.println("entre volume 1:" + this.citerne_ToDetail_livraison.getVolume_actuel_());
                System.out.println("entre volume 2:" + volume_actuel.intValue() * 1000);
                System.out.println("entre volume 3:" + volume_somme);

                this.citerne_ToDetail_livraison.setVolume_actuel_(volume_somme);

                this.citerneService.update_citerne(citerne_ToDetail_livraison);

                bon_Livraison_Citerne.setCiterne(citerne_ToDetail_livraison);

                bon_Livraison_Citerne.setVolume_actuel(volume_somme);

                bon_Livraison_Citerne.setVolume(volume_actuel * 1000);

                bon_Livraison_Citerne.setNumero_commande(numero_commande);
                bon_Livraison_Citerne.setNumero_Livraison(numero_commande_livraison);

                bon_Livraison_Citerne.setDate(date_Bon_livraison);

                bon_Livraison_Citerne.setAction("LIVRAISON");
                bon_Livraison_Citerne.setCommentaire(commentBl);

                livraisonCiterneService.save(bon_Livraison_Citerne);

                FacesContext context = FacesContext.getCurrentInstance();

                context.addMessage(null, new FacesMessage("" + Message.BON_LIVRAISON_TRUE, ""));

                volume_actuel = 0.0;

                numero_commande_livraison = "";

                numero_commande = "";
            }
        }
    }

    public String dedecimals(Double d) {
        try {

                if (d == 0 || d == null) {
                    return "0.00";
                } else {
                    String s = "";
                    DecimalFormat df = new DecimalFormat("#.##");
                    s = df.format(d);
                    return s;
                }
        } catch (Exception e) {
            System.out.println("Erreur de dedecimals car  "+e.getMessage());
            return "0.00";
        }
    }

    /* handles la selection d'une ligne de la table des mensuels */
    public void onRowSelectMensuel(Mensuel m) {
        System.out.println("MENSUEL : SELECLED : " + m.getNom());
    }

    /**
     * metdhode qui permet de redirect vers bon caisse
     *
     * @param selected
     */
    public void nv_bon_gasoil(){
        redirect_bon_caisse(citerne_bon_caisse);
        chargerBonG();
    }
    public void chargerBonG(){
          bon_gasoil = new Bon_Livraison_Citerne();
         // bon_gasoil.setDate(new Date());   
          date_bon_caisse = new Date();
          bon_gasoil.setDate(date_bon_caisse);
    }
    public void redirect_bon_caisse(Citerne selected) {
      chargerBonG();
      cptHr=0;cptKl=0;
      bon_gasoil.setCiterne(selected);
      listEnginCiterne = enginService.findAllInChantier(selected.getChantier_Principal().getId());
        System.out.println("listEnginCiterne :::::::> "+listEnginCiterne);
        System.out.println("bon_gasoil :::::::> "+bon_gasoil.toString());
        Kilométrage_bon_caisse = null;
        heure_engin = null;
        Tonnes_bonCaisse = null;
        l_engin_to_bonCaisse = null;

        isButtonVisible = true;

        citerne_bon_caisse = selected;

        List<Engin> l_engins_sec = new ArrayList<>();

        List<Engin> l_engins_prin = new ArrayList<>();

        l_chantier_sec_bon_caisse = this.citerneService.getListeChantierByCiterne(citerne_bon_caisse.getId());

        System.out.println("entre engin 1 :  " + l_chantier_sec_bon_caisse.size());

        if (l_chantier_sec_bon_caisse != null) {
            for (Chantier ch_ : l_chantier_sec_bon_caisse) {
                for (Engin en_ : ch_.getEnginList()) {
                    l_engins_sec.add(en_);
                }
            }
        }

        l_chantierSecBonGasoilEngin = this.citerneService.getListeChantierByCiterne(selected.getId());

        System.out.println("entre engin 2 :  " + l_engins_sec.size());

        ELContext elContext = FacesContext.getCurrentInstance().getELContext();

        ma.bservices.tgcc.mb.services.EnginMb engin_bean = (ma.bservices.tgcc.mb.services.EnginMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "enginServMb");

        List<Engin> engin_to_boucle = engin_bean.getL_engins();

        if (!engin_to_boucle.isEmpty()) {

            for (Engin engin__ : engin_to_boucle) {
                if (engin__ != null) {

                    if (citerne_bon_caisse.getChantier_Principal() != null && engin__.getPrjapId() != null) {
                        if (citerne_bon_caisse.getChantier_Principal().getId().equals(engin__.getPrjapId().getId())) {
                            if (engin__.getArchive() != true) {
                                l_engins_prin.add(engin__);
                                l_enginBonGasoilSecCopie = l_engins_prin;
                            }
                        }
                    }
                }
            }
        }

        System.out.println("entre engin 3 :  " + l_engins_prin.size());

//        for (Engin en_ : citerne_bon_caisse.getChantier_Principal().getEnginList()) {
//            
//        }
     //   this.l_engin_chantier_bon_caisse = this.citerneServiceBean.get_list_engin_chantier(l_engins_prin, l_engins_sec);
      this.l_engin_chantier_bon_caisse = l_enginBonGasoilSecCopie;
      bon_gasoil= new Bon_Livraison_Citerne();

//          l_enginBonGasoilSecCopie = this.l_engin_chantier_bon_caisse;
    }

    public void afficherEnginLieChantierSelectionne() {

        if (chantierSecSelectionneBonGasoilEngin == -1) {
            this.l_engin_chantier_bon_caisse = l_enginBonGasoilSecCopie;
        } else {

            l_engin_chantier_bon_caisse = this.citerneService.getEnginByChantierId(chantierSecSelectionneBonGasoilEngin);
        }

    }

    /**
     * methode pour sauvegarde bonn gasoil
     *
     * @throws java.io.IOException
     * @throws com.itextpdf.text.DocumentException
     */
    /*
    public void save_bon_gasoil() throws IOException, DocumentException {

        if(engin.getCompteurKilometrique()>=0 && engin.getComteurHoraire()>=0){
            //if(engin.getCompteurKilometrique()>=cptK && engin.getComteurHoraire()>= cptH){
                if (Tonnes_bonCaisse != null && this.citerne_bon_caisse.getCapacite() != null) {
                    if (Tonnes_bonCaisse > this.citerne_bon_caisse.getVolume_actuel_()) {

                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, Message.VOLUME_CAPACITE_CITERNE_INF, Message.VOLUME_CAPACITE_CITERNE_INF));

                    } else {

                        bon_Livraison_Citerne_to_add_boncaisse.setAction("BON_GASOIL_ENGIN");
                        bon_Livraison_Citerne_to_add_boncaisse.setCiterne(citerne_bon_caisse);
                        bon_Livraison_Citerne_to_add_boncaisse.setDate(date_bon_caisse);
                        bon_Livraison_Citerne_to_add_boncaisse.setHeure(engin.getComteurHoraire().toString());

                        Double volume_actuel_somme = this.citerneServiceBean.getSoustraction_volume_actuel(this.citerne_bon_caisse.getVolume_actuel_(), Tonnes_bonCaisse);

                        bon_Livraison_Citerne_to_add_boncaisse.setVolume_actuel(volume_actuel_somme);
                        citerne_bon_caisse.setVolume_actuel_(volume_actuel_somme);
                         try {
                            System.out.println("===================> engin Citerne "+engin.toString());
                            this.enginService.updateEngin(engin);
                            
                        } catch (Exception e) {
                            System.out.println("Erreur d'enregistrement d'engin car "+e.getMessage());
                        }
                        
                        this.citerneService.update_citerne(citerne_bon_caisse);
                        bon_Livraison_Citerne_to_add_boncaisse.setVolume(Tonnes_bonCaisse);
                        bon_Livraison_Citerne_to_add_boncaisse.setKilometrage(engin.getCompteurKilometrique().toString());
                        this.citerneService.save_bon_caisse_citerne_engin(bon_Livraison_Citerne_to_add_boncaisse, l_engin_to_bonCaisse);
                        this.livraisonCiterneService.update(bon_Livraison_Citerne_to_add_boncaisse);
                       
                        this.display_button_telecharger = true;
                        // this.citerneServiceBean.telecharger_bon_gasoil_engin(bon_Livraison_Citerne_to_add_boncaisse);
                        this.bon_temp = bon_Livraison_Citerne_to_add_boncaisse;
                        // bon_Livraison_Citerne_to_add_boncaisse.setChemin_fichier();
                        
                        bon_Livraison_Citerne_to_add_boncaisse = new Bon_Livraison_Citerne();
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Succes", "Bon Edité avec succès"));
                        isButtonVisible = false;
                    }

               // }
            }
        }
        else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "Veuiller Remplir Kilometrage ou Heures Engin"));
            System.out.println("Kilometrage ou Horaire");
        }
        
    }*/

    public void save_bon_gasoil() throws IOException, DocumentException {

        if ((cptKl <0 ) && (cptHr<0)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "les Compteurs doit être positif"));
        }/*else if(bon_gasoil.getEngin().getCompteurKilometrique()>cptKl && ("kilométrique".equals(bon_gasoil.getEngin().getTypeCompteur()) || "kilométrique_horaire".equals(bon_gasoil.getEngin().getTypeCompteur()))){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "le compteur kilométrique doit être supérieur ou égal "+bon_gasoil.getEngin().getCompteurKilometrique()));
        } else if(bon_gasoil.getEngin().getComteurHoraire()>cptHr && ("horaire".equals(bon_gasoil.getEngin().getTypeCompteur()) || "kilométrique_horaire".equals(bon_gasoil.getEngin().getTypeCompteur()))){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "le compteur horaire doit être supérieur ou égal "+bon_gasoil.getEngin().getComteurHoraire()));
        }*/else if (Tonnes_bonCaisse != null && this.citerne_bon_caisse.getCapacite() != null) {
             
            if (Tonnes_bonCaisse <0 ) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "les litres doit être positif"));
            } else
            if (Tonnes_bonCaisse > this.citerne_bon_caisse.getVolume_actuel_()) {

                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, Message.VOLUME_CAPACITE_CITERNE_INF, Message.VOLUME_CAPACITE_CITERNE_INF));

            } else {
                bon_gasoil.setAction("BON_GASOIL_ENGIN");
                bon_gasoil.setCiterne(citerne_bon_caisse);
                bon_gasoil.setDate(new Date());
                bon_gasoil.setHeure(cptHr+"");
                bon_gasoil.setDateOperation(date_bon_caisse); 

                Double volume_actuel_somme = this.citerneServiceBean.getSoustraction_volume_actuel(this.citerne_bon_caisse.getVolume_actuel_(), Tonnes_bonCaisse);

                bon_gasoil.setVolume_actuel(volume_actuel_somme);
                citerne_bon_caisse.setVolume_actuel_(volume_actuel_somme);
                this.citerneService.update_citerne(citerne_bon_caisse);
                bon_gasoil.setVolume(Tonnes_bonCaisse);
                bon_gasoil.setKilometrage(cptKl+"");
                
                this.citerneService.save_bon_caisse_citerne_engin(bon_gasoil, l_engin_to_bonCaisse);
                this.display_button_telecharger = true;
                // this.citerneServiceBean.telecharger_bon_gasoil_engin(bon_gasoil);
                this.bon_temp = bon_gasoil;
                // bon_Livraison_Citerne_to_add_boncaisse.setChemin_fichier();
                this.livraisonCiterneService.update(bon_gasoil);
                /* Modification les compteurs d'engin
                Engin eng = bon_gasoil.getEngin();
                eng.setCompteurKilometrique(cptKl);
                eng.setComteurHoraire(cptHr);
                enginService.updateEngin(eng);
                */
                bon_gasoil = new Bon_Livraison_Citerne();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Succes", "Bon Edité avec succès"));
                isButtonVisible = false;
                /*
                bon_Livraison_Citerne_to_add_boncaisse.setAction("BON_GASOIL_ENGIN");
                bon_Livraison_Citerne_to_add_boncaisse.setCiterne(citerne_bon_caisse);
                bon_Livraison_Citerne_to_add_boncaisse.setDate(date_bon_caisse);
                bon_Livraison_Citerne_to_add_boncaisse.setHeure(heure_engin);

                Double volume_actuel_somme = this.citerneServiceBean.getSoustraction_volume_actuel(this.citerne_bon_caisse.getVolume_actuel_(), Tonnes_bonCaisse);

                bon_Livraison_Citerne_to_add_boncaisse.setVolume_actuel(volume_actuel_somme);

                citerne_bon_caisse.setVolume_actuel_(volume_actuel_somme);

                this.citerneService.update_citerne(citerne_bon_caisse);

                bon_Livraison_Citerne_to_add_boncaisse.setVolume(Tonnes_bonCaisse);
                bon_Livraison_Citerne_to_add_boncaisse.setKilometrage(Kilométrage_bon_caisse);

                this.citerneService.save_bon_caisse_citerne_engin(bon_Livraison_Citerne_to_add_boncaisse, l_engin_to_bonCaisse);

                this.display_button_telecharger = true;

                // this.citerneServiceBean.telecharger_bon_gasoil_engin(bon_Livraison_Citerne_to_add_boncaisse);
                this.bon_temp = bon_Livraison_Citerne_to_add_boncaisse;
                // bon_Livraison_Citerne_to_add_boncaisse.setChemin_fichier();

                this.livraisonCiterneService.update(bon_Livraison_Citerne_to_add_boncaisse);

                bon_Livraison_Citerne_to_add_boncaisse = new Bon_Livraison_Citerne();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Succes", "Bon Edité avec succès"));
                isButtonVisible = false;
                */
            }
            
        }
    }
    
    public void downloadBonGasoil() throws IOException, DocumentException {
        String s = this.citerneServiceBean.telecharger_bon_gasoil_engin(bon_temp);

        bon_temp.setChemin_fichier(s);
        System.out.println("CHEMIN FICHIER SET TO : " + bon_temp.getChemin_fichier());
        this.livraisonCiterneService.update(bon_temp);

    }

    /**
     * methode qui permet de telecharger Bon caisse citerne
     *
     *
     * @throws java.io.IOException
     * @throws com.itextpdf.text.DocumentException
     */
    public void telecharger_bon_caisse() throws IOException, DocumentException {

        this.citerneServiceBean.telecharger_bon_gasoil_engin(bon_Livraison_Citerne_to_add_boncaisse);

        this.livraisonCiterneService.update(bon_Livraison_Citerne_to_add_boncaisse);

    }

    /**
     * methode qui permet de telecharger bon caisse
     *
     * @param livraison_Citerne
     * @throws java.io.IOException
     */
    public void telecharger_bon_gasoil_historique(Bon_Livraison_Citerne livraison_Citerne) throws IOException {

        System.out.println("entre : " + livraison_Citerne.getChemin_fichier());

        if (livraison_Citerne.getChemin_fichier() != null) {

            this.citerneServiceBean.telecharger_fichier(livraison_Citerne.getChemin_fichier());
        }

    }
    public void prepModifierMvmt(Bon_Livraison_Citerne livraison_Citerne){
        bon_gasoil_To_Edit = livraison_Citerne;
    }
    public void modifierMvmt(Bon_Livraison_Citerne livraison_Citerne){
        bon_gasoil_To_Edit = livraison_Citerne;
        
    }

    /**
     * methode qui redirect vers hstorique citernee
     *
     * @param selected
     */
    public void redirectHistoriqueCiterne(Citerne selected) {

        historique_searchBonLivraison = selected;

        //System.out.println("historique_searchBonLivraison 1: " + historique_searchBonLivraison.getId());

        if (l_detail_citerne_historique != null) {
            this.l_detail_citerne_historique.clear();
        }

        this.l_detail_citerne_historique = this.livraisonCiterneService.get_listBy_id_bonLivraison(selected.getId());

    }
    public void prepMvmt( Bon_Livraison_Citerne b){
        bon_gasoil_To_Edit = b;
        allEngins= enginService.enginsActif();
        RequestContext.getCurrentInstance().execute("PF('dlg_mvmtCiterne').show();");
    }
    public void changeEnginEditMvmt(){
        if(codeEnginMvmtEdit.trim().length()>0){
            Engin e = enginService.findOneByCode(codeEnginMvmtEdit);
            if(e!=null){
                bon_gasoil_To_Edit.setEngin(e);
            }
        }
    }
    public void enregistrerMvmt(){
        RemplireTrace r = new RemplireTrace();
        TraceBonLivraisonCiterne t = new TraceBonLivraisonCiterne();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication(); 
        t = r.remplirTraceBonLivraisonCiterne(livraisonCiterneService.findBonLivraisonCiterneById(bon_gasoil_To_Edit.getId()), auth.getPrincipal().toString(), "Modification MVMT Citerne"); 
        
        traceUtilisateurService.addTraceBonLivraisonCiterne(t);
        livraisonCiterneService.update(bon_gasoil_To_Edit);
        bon_gasoil_To_Edit= new Bon_Livraison_Citerne();
        codeEnginMvmtEdit="";
    }

    /**
     * methode qui permet de redirect vers dialog bon gasoil mensuel
     *
     * @param selected
     */
    public void redirect_bon_gasoil_mensuel(Citerne selected) {
        mensuel = null;
        mensuel_to_search = new Mensuel();
        mensuels = null;
        voitureSalaries = null;
        nombre_litre_bon_gasoil_mensuel = null;
        date_bon_gasoil_mensuel = new Date();
        closeBGMWindow = false;
        this.citerne_bon_gasoil_mensuel = selected;

    }

    /**
     * methode qui permet de sauvegarde bon gasoil mensuel
     *
     * @throws java.io.IOException
     * @throws com.itextpdf.text.DocumentException
     */
    public void save_bon_gasoil_mensuel() throws IOException, DocumentException {

        if (nombre_litre_bon_gasoil_mensuel != null && this.citerne_bon_gasoil_mensuel != null) {

            if (voitureSalaries == null || voitureSalaries.isEmpty()) {

                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Pas de voiture", "Ce mensuel n'a pas de voiture"));

            } else if (this.citerne_bon_gasoil_mensuel.getVolume_actuel_() < nombre_litre_bon_gasoil_mensuel) {

                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, Message.VOLUME_CAPACITE_CITERNE_INF, Message.VOLUME_CAPACITE_CITERNE_INF));

            } else {

                bon_gasoil_mensuel_to_add.setAction("BON_GASOIL_MENSUEL");
                System.out.println("NOMBRE LITRES : " + nombre_litre_bon_gasoil_mensuel);
                bon_gasoil_mensuel_to_add.setCiterne(this.citerne_bon_gasoil_mensuel);
                bon_gasoil_mensuel_to_add.setDate(date_bon_gasoil_mensuel);
                Double volume_actuel_somme = this.citerneServiceBean.getSoustraction_volume_actuel(this.citerne_bon_gasoil_mensuel.getVolume_actuel_(), nombre_litre_bon_gasoil_mensuel);
                System.out.println("VOLUME ACTUEL AFTER SOMME : " + volume_actuel_somme);

                bon_gasoil_mensuel_to_add.setVolume_actuel(volume_actuel_somme);

                this.citerne_bon_gasoil_mensuel.setVolume_actuel_(volume_actuel_somme);

                bon_gasoil_mensuel_to_add.setVolume(nombre_litre_bon_gasoil_mensuel);

                // modifier volume actuel citerne
                this.citerneService.update_citerne(this.citerne_bon_gasoil_mensuel);

                this.citerneService.save_bon_caisse_citerne_mensuel(bon_gasoil_mensuel_to_add, mensuel.getId());

                this.display_button_telecharger_bon_gasoil_mensuel = true;

                this.citerneServiceBean.telecharger_bon_gasoil_mensuel(this.bon_gasoil_mensuel_to_add);

                this.livraisonCiterneService.update(this.bon_gasoil_mensuel_to_add);

                closeBGMWindow = true;

                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Succes", "L'opération à été terminé avec succès!"));

            }
        }

    }

    /**
     * methode qui permet de telecharger bon gasoil mensuel
     *
     * @throws java.lang.Exception
     */
    public void telecharger_bon_gasoil_mensuel() throws Exception {

    }

    /**
     * methode qui permet de recherche sur historique
     */
    public void rechercher_historique_citerne() {

        System.out.println("entre :" + date_historique_search);

        if (date_historique_search == null && action_search.equals("-1") && numCommande_search.equals("") && numLivraison_search.equals("")) {

            System.out.println("historique_searchBonLivraison 2: " + historique_searchBonLivraison.getId());

            l_detail_citerne_historique = this.livraisonCiterneService.get_listBy_id_bonLivraison(historique_searchBonLivraison.getId());

        } else {

            l_detail_citerne_historique = this.livraisonCiterneService.get_liste_livraisonByDate_action(historique_searchBonLivraison.getId(), date_historique_search, action_search, numCommande_search, numLivraison_search);

        }

    }

    public void reinitHistoCiterne() {

        if (l_detail_citerne_historique != null) {
            this.l_detail_citerne_historique.clear();
        }

        this.l_detail_citerne_historique = this.livraisonCiterneService.get_listBy_id_bonLivraison(historique_searchBonLivraison.getId());

    }

    /**
     * methode pour display
     *
     * @param code
     */
    public void display_engin_kiometrique(String code) {
        chargerBonG();
        Tonnes_bonCaisse = 0.0;
        System.out.println("entre :" + code);
        Engin en_ = this.enginService.findOneByCode(code);
        engin=new Engin();
        setEngin(en_);
        bon_gasoil.setEngin(engin);
        cptKl = engin.getCompteurKilometrique();
        cptHr = engin.getComteurHoraire();
        this.kilometrique_display = false;
        this.heure_display = false;
        if (en_.getCompteurKilometrique() != null) {
            kilometrique_display = true;
        }
        if (en_.getComteurHoraire() != null) {
            heure_display = true;
        }
        System.out.println("::::> bon_gasoil "+bon_gasoil.getEngin().toString());
    }

    public String convertToDoubleDecimals(Double d) {

        DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.FRANCE);
        DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();

        symbols.setGroupingSeparator(' ');

        formatter.setDecimalFormatSymbols(symbols);
        // System.out.println(formatter.format(d));

        String s = formatter.format(d.doubleValue());
        return s;
    }

    /**
     * methode qui permet de afficher voiture mensuel
     *
     * @param selected
     */


    /**
     * methode fermer pop
     *
     * @throws java.io.IOException
     */
    public void fermer_pop() throws IOException {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect(ec.getRequestContextPath() + "/engin/Citerne.xhtml");
    }

    /**
     * methode qui permet de rechercher mensuel
     */
    public void rechercher_mensuel_By() {

        mensuels = this.mensuelService.search(mensuel_to_search.getMatricule(), mensuel_to_search.getNom(), mensuel_to_search.getPrenom(), "", "");
        for (Mensuel m : mensuels) {
            System.out.println("MENSUEL : " + m.getNom());
        }
    }

    /**
     * methode qui permet de converti date en format jj-mm-aaaa
     *
     * @param date
     * @return
     */
    public String convertFormatDate(Date date) {

        Outils outils = new Outils();
        return outils.convertDate_To_string(date);

    }

    /**
     * convert ton to litre
     */
    private void litreToTon() {

        if (citerne_ToDetail_livraison.getType_citerne() != null) {
            switch (citerne_ToDetail_livraison.getType_citerne()) {
                case "Diesel":
                    // 1 kg = 1litre/0.85
                    // 1t = 1000kg => kg = t/1000
                    tons_dispo = volume_actuel * 0.8 / 1000;
                    break;
                case "Essence":
                    tons_dispo = volume_actuel * 0.79 / 1000;
                    break;
            }
        }
        volume_actuel = tons_dispo;
        System.out.println("volume " + volume_actuel);
    }

    public void tonToLitre() {
        Double t = 0.0;
        if (citerne_ToDetail_livraison.getType_citerne() != null) {
            switch (citerne_ToDetail_livraison.getType_citerne()) {
                case "Diesel":
                    // 1 kg = 1litre/0.85
                    // 1t = 1000kg => kg = t/1000
                    t = tons_dispo / (0.85 * 1000);
                    break;
                case "Essence":
                    t = tons_dispo / (0.79 * 1000);
                    break;
            }
        }
        volume_actuel = t;
        System.out.println("volume " + volume_actuel);
    }
    private Double tons, tons_dispo;

    /**
     * methode converti Litre to Ton
     *
     * @param litre
     * @param type
     */
    private void converti(Double litre, String type) {

        if (type != null) {
            switch (type) {
                case "Diesel":
                    // 1 kg = 1litre/0.85
                    // 1t = 1000kg => kg = t/1000
                    tons = (litre / 0.85) / 1000.0;
                    break;
                case "Essence":
                    tons = (litre / 0.79) / 1000.0;
                    break;
            }
        }
        tons = Math.floor(tons * 100) / 100;
    }

    public Double getTons() {
        return tons;
    }

    public void setTons(Double tons) {
        this.tons = tons;
    }

    public Double getTons_dispo() {
        return tons_dispo;
    }

    public void setTons_dispo(Double tons_dispo) {
        this.tons_dispo = tons_dispo;
    }

    public void checkCapacite(Double vol) {

        volume_actuel = vol;

        if (volume_actuel != null) {

            afficherVolumeTonne = vol * 0.8;
        }

        afficherVolumeTonne = Math.floor(afficherVolumeTonne * 100) / 100;

        checkBtn = afficherVolumeTonne > tons_max_afficherLivraison;
        if (checkBtn) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, Message.VOLUME_CAPACITE_CITERNE_DEPASSE, Message.VOLUME_CAPACITE_CITERNE_DEPASSE));
        } else {
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, Message.VOLUME_CAPACITE_CITERNE_INF_SUCCESS, Message.VOLUME_CAPACITE_CITERNE_INF_SUCCESS));
        }
    }

    public void checkCapaciteTonne(Double vol) {

        afficherVolumeTonne = vol;

        if (afficherVolumeTonne != null) {

            volume_actuel = afficherVolumeTonne * 0.8;
        }

        afficherVolumeTonne = Math.floor(afficherVolumeTonne * 100) / 100;

        checkBtn = afficherVolumeTonne > tons_max_afficherLivraison;
        if (checkBtn) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, Message.VOLUME_CAPACITE_CITERNE_DEPASSE, Message.VOLUME_CAPACITE_CITERNE_DEPASSE));
        } else {
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, Message.VOLUME_CAPACITE_CITERNE_INF_SUCCESS, Message.VOLUME_CAPACITE_CITERNE_INF_SUCCESS));
        }
    }
    
    /*** Debut Gestion TRansfaire gasoil entre citerne***/
    public void prepTransfaire(Citerne c){ 
        l_citerneTransfaire = citerneService.find_allCiterneNon_archiver();        
        citernSrc = c; 
        citernDist = new Citerne();
        traceCitern = new TraceCiterne();
        traceCiternes =new ArrayList<TraceCiterne>();
        try {
            traceCiternes = citerneService.findAllTraceCiterneByCiterne(c);
        } catch (Exception e) {
            System.out.println(" ::::> Erreur de chargement la liste transfaire citernSrc  car : "+e.getMessage());
        }
        System.out.println(" ::::> chargement liste de transfaire citernSrc : "+traceCiternes.size());
    }
    
    public void chageCiternEv(){
        System.out.println("id ::::::::::> "+idCiternTrans);
        if(idCiternTrans>0){
            citernDist=citerneService.findCiternById(idCiternTrans);
        }
        System.out.println("=======> citernDist change : "+citernDist.toString());
    }
    
    public void chargerListTransfaire(){
        if(citernSrc !=null){
            try {
                traceCiternes = citerneService.findAllTraceCiterneByCiterne(citernSrc);
            } catch (Exception e) {
                System.out.println("Erreur de récuperation de la liste des transfaire engin car "+e.getMessage());
            }
            
        }
    }
    public void prepReceptionGz(Citerne c){
        try {
                traceCiternes = citerneService.findAllTraceCiterneByCiterneDist(c);
            } catch (Exception e) {
                System.out.println("Erreur de récuperation de la liste des transfaire engin car "+e.getMessage());
            }
        
    }
    public void saveReceptionGz(TraceCiterne t){
        citernDist = t.getCiternDist();
        citernDist.setVolume_actuel_(((citernDist.getVolume_actuel_()!=null)?citernDist.getVolume_actuel_():0)+t.getLitreReceptione()); 
        t.setUtilisateurReception(u);
        t.setValide(Boolean.TRUE);
        t.setDateOperationRecep(new Date());
            try {
                    citerneService.update_citerne(citernDist);
            } catch (Exception e) {
                System.out.println("Erreur de modification citernDist  car "+e.getMessage());
            }
            
            try { 
                    citerneService.editTraceCiterne(t); 
                    System.out.println("t.getLitreReceptione() : "+t.getLitreReceptione());
                    System.out.println("t.getLitreTransf() : "+t.getLitreTransf());
                    System.out.println("t.getLitreReceptione()!= t.getLitreTransf() : "+(t.getLitreReceptione()!= t.getLitreTransf()));
                    if((t.getLitreReceptione()-t.getLitreTransf())!=0){
                        String msgEmail;
                        try {
                          msgEmail = "Bonjour, \n\nUn écart a été constaté concernant le transfert de gasoil N° "+t.getNumBon()
                                  +", ID "+t.getId()+", en provenance du chantier "+ t.getCiternSrc().getChantier_Principal().getCode().trim()
                                  +"("+t.getLitreTransf()+" L)"
                                  +" et à destination du chantier "+ t.getCiternDist().getChantier_Principal().getCode().trim()
                                  +"("+t.getLitreReceptione()+" L)"
                                  +".\n\nCordialement.";
                        } catch (Exception e) {
                            msgEmail = "Bonjour, \nUne déference de transfert de gasoil de la "
                                        + t.getCiternSrc().getLibelle_citerne()+" ("+t.getLitreTransf()+"L) vers La citerne "
                                        + t.getCiternDist().getLibelle_citerne()+" ("+t.getLitreReceptione()+"L). \nCordialement." ;
                            System.out.println("Erreu d'affectation du message car "+e.getMessage());
                        }
                        try {
                            ApplicationContext context = new ClassPathXmlApplicationContext("mail.xml");
                            SendEmail sm = (SendEmail) context.getBean("sendEmail");
                           String[] listDestinatairesMail = {"hamza.achtioua@tgcc.ma"} ;
                            String[] cc = {"said.jamaleddine@tgcc.ma","mohamed.benseddik@tgcc.ma","yassine.jeddi@tgcc.ma"} ; 
                             
                            if (listDestinatairesMail != null ) {
                                for (String m : listDestinatairesMail) {
                                    sm.sendMailCc("notification@tgcc.ma", m, "Écart Gasoil", msgEmail,cc);
                                }
                            }
                        } catch (Exception e) {
                            System.out.println("Erreur d'envoi l'email car "+e.getMessage());
                        }
                    }
            } catch (Exception e) {
                System.out.println("Erreur de modifier traceCitern  car "+e.getMessage());
            }
    }
    public void saveTransfaire(){
        Boolean b = Boolean.FALSE;
        try {
            
            traceCitern.setCiternSrc(citernSrc);
            traceCitern.setCiternSrcLitre(citernSrc.getVolume_actuel_());
            traceCitern.setCiternDist(citernDist);
            traceCitern.setCiternDistLitre(citernDist.getVolume_actuel_());
            traceCitern.setUtilisateurExpedition(u);
            traceCitern.setValide(Boolean.FALSE);
            
            citernSrc.setVolume_actuel_(citernSrc.getVolume_actuel_()-traceCitern.getLitreTransf()); 
             b = Boolean.TRUE;
            } catch (Exception e) {
                System.out.println("Erreur d'operation sur les objets  car "+e.getMessage());
            }
            try {
                if(b){
                    citerneService.update_citerne(citernSrc);
                }
            } catch (Exception e) {
                System.out.println("Erreur de modification citernSrc  car "+e.getMessage());
            }
            try {
                if(b){
                    citerneService.addTraceCiterne(traceCitern);
                     ApplicationContext context = new ClassPathXmlApplicationContext("mail.xml");

           // List<String> listDestinatairesMail = getRecipientsByModule("ENGIN_PANNE");
                    SendEmail sm = (SendEmail) context.getBean("sendEmail");
                    String[] listDestinatairesMail = {"hamza.achtioua@tgcc.ma"} ;
                    String[] cc = {"said.jamaleddine@tgcc.ma","mohamed.benseddik@tgcc.ma","yassine.jeddi@tgcc.ma"} ; 
                    if (listDestinatairesMail != null ) {
                        for (String m : listDestinatairesMail) {
                            sm.sendMailCc("notification@tgcc.ma", m, "Notification de réception de Gasoil", "Bonjour,\n\nUn transfert d'une quantité de Gasoil de "
                                    + traceCitern.getLitreTransf()+"L a été effectuée vers le chantier "
                                    + traceCitern.getCiternDist().getChantier_Principal().getCode().trim()+" en provenance du chantier  "
                                    + traceCitern.getCiternSrc().getChantier_Principal().getCode().trim()+".\n\nMerci de procéder à la réception sur UPSIT.\n\nCordialement.",cc );
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("Erreur d'ajout traceCitern  car "+e.getMessage());
            } 
            if (b) {
                try {
                        traceCiternes = citerneService.findAllTraceCiterneByCiterne(citernSrc);
                    } catch (Exception e) {
                        System.out.println(" ::::> Erreur de chargement la liste transfaire citernSrc  car : "+e.getMessage());
                    }
                prepTransfaire(citernSrc);
            }else{
                citernSrc.setVolume_actuel_(citernSrc.getVolume_actuel_()+traceCitern.getLitreTransf());
                prepTransfaire(citernSrc);
            }
            chargerListTransfaire();
    }
    /*** Fin Gestion TRansfaire gasoil entre citerne ***/
    

}
