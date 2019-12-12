/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.mb.Engin;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import ma.bservice.tgcc.Constante.Constante;
import ma.bservice.tgcc.Constante.Message;
import ma.bservices.beans.Chantier;
import ma.bservices.beans.Utilisateur;
import ma.bservices.mb.services.Module;
import ma.bservices.tgcc.Entity.Engin;
import ma.bservices.tgcc.Entity.PointageEngin;
import ma.bservices.tgcc.dao.engin.UtilisateurDAO;
import ma.bservices.tgcc.service.Engin.ChantierService;
import ma.bservices.tgcc.service.Engin.EnginService;
import ma.bservices.tgcc.service.Engin.ICompteurEnginService;
import ma.bservices.tgcc.service.Engin.PointageEnginServices;
import ma.bservices.tgcc.service.Engin.UtilisateurService;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

/**
 *
 * @author yassine
 */
@Component
@ManagedBean(name = "pointageEnginDept")
@ViewScoped
public class PointageEnginDeptMb {
    
    
    @ManagedProperty(value = "#{pointageEnginService}")
    private PointageEnginServices pointageEnginSerive;

    @ManagedProperty(value = "#{enginService}")
    private EnginService enginSerive;
    
    @ManagedProperty(value = "#{compteurEnginServiceImp}")
    private ICompteurEnginService compteurEnginService;
    
    @ManagedProperty(value = "#{utilisateurService}")
    private UtilisateurService utilisateurService;;

    @ManagedProperty(value = "#{chantierService}")
    private ChantierService chantierService;
    
    
    @Autowired
    private UtilisateurDAO utilisateurDAO;
    
    private List<PointageEngin> lpM = new ArrayList<PointageEngin>();
    private List<PointageEngin> lpA = new ArrayList<PointageEngin>();
    private List<PointageEngin> lpe = new ArrayList<PointageEngin>();
    private List<Engin> le = new ArrayList<Engin>();
    
    private Date dateStart = new Date();
    private Date date = new Date();
    private Date last_day_pointed;
    private boolean isTranfer;
    private boolean isvalid = Boolean.FALSE;
    private Chantier chantierSelected;
    private String messageErrorPointage = "";
    private int nbr=0;

    public boolean isIsvalid() {
        return isvalid;
    }

    public void setIsvalid(boolean isvalid) {
        this.isvalid = isvalid;
    } 
    
    public String getMessageErrorPointage() {
        return messageErrorPointage;
    }

    public void setMessageErrorPointage(String messageErrorPointage) {
        this.messageErrorPointage = messageErrorPointage;
    }
    
    public PointageEnginServices getPointageEnginSerive() {
        return pointageEnginSerive;
    }

    public void setPointageEnginSerive(PointageEnginServices pointageEnginSerive) {
        this.pointageEnginSerive = pointageEnginSerive;
    }

    public EnginService getEnginSerive() {
        return enginSerive;
    }

    public void setEnginSerive(EnginService enginSerive) {
        this.enginSerive = enginSerive;
    }

    public ICompteurEnginService getCompteurEnginService() {
        return compteurEnginService;
    }

    public void setCompteurEnginService(ICompteurEnginService compteurEnginService) {
        this.compteurEnginService = compteurEnginService;
    }

    public UtilisateurService getUtilisateurService() {
        return utilisateurService;
    }

    public void setUtilisateurService(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    public ChantierService getChantierService() {
        return chantierService;
    }

    public void setChantierService(ChantierService chantierService) {
        this.chantierService = chantierService;
    }

    public List<PointageEngin> getLpM() {
        return lpM;
    }

    public void setLpM(List<PointageEngin> lpM) {
        this.lpM = lpM;
    }

    public List<PointageEngin> getLpA() {
        return lpA;
    }

    public void setLpA(List<PointageEngin> lpA) {
        this.lpA = lpA;
    }

    public List<PointageEngin> getLpe() {
        return lpe;
    }

    public void setLpe(List<PointageEngin> lpe) {
        this.lpe = lpe;
    }

    public List<Engin> getLe() {
        return le;
    }

    public void setLe(List<Engin> le) {
        this.le = le;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public Date getLast_day_pointed() {
        return last_day_pointed;
    }

    public void setLast_day_pointed(Date last_day_pointed) {
        this.last_day_pointed = last_day_pointed;
    }

    public boolean isIsTranfer() {
        return isTranfer;
    }

    public void setIsTranfer(boolean isTranfer) {
        this.isTranfer = isTranfer;
    }

    public Chantier getChantierSelected() {
        return chantierSelected;
    }

    public void setChantierSelected(Chantier chantierSelected) {
        this.chantierSelected = chantierSelected;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    
    
    
    
    
    @PostConstruct
    public void init() {
        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        pointageEnginSerive = ctx.getBean(PointageEnginServices.class);
        chantierService = ctx.getBean(ChantierService.class); 
        utilisateurService = ctx.getBean(UtilisateurService.class);        
        enginSerive = ctx.getBean(EnginService.class);
        pointageEnginSerive = ctx.getBean(PointageEnginServices.class);
        
        chantierSelected = chantierService.findByName("DEPARTEMENT LOGISTIQUE");
        last_day_pointed = pointageEnginSerive.lastDayPointed(chantierSelected.getId());
        lpM= chargerListPointage(enginSerive.findAllEnginPointageManuelDept(chantierSelected.getId()));
        lpA = chargerListPointage(enginSerive.findAllEnginPointageAutoDept(chantierSelected.getId()));
        nbr=lpM.size()+lpA.size();
        
        if (chantierSelected !=null) {
            this.last_day_pointed = pointageEnginSerive.lastDayPointed(chantierSelected.getId());
            Calendar cal_pointage = Calendar.getInstance();
            if (this.last_day_pointed == null) {
                try {
                    this.last_day_pointed = Constante.get_first_day_in_pointage_Engin_forAllApplication();
                    cal_pointage.setTime(this.last_day_pointed);
                } catch (ParseException ex) {
                    System.out.println("erreur parsing date " + ex.getMessage());
                }
            } else {
                cal_pointage.setTime(this.last_day_pointed);
            }
            
            cal_pointage.add(Calendar.DATE, 1);
            this.last_day_pointed = cal_pointage.getTime();
            date=last_day_pointed;
        }
        
    }
    

    public void filter() {
        //System.out.println("==============> 1 ");
        getPointageEnginByEngin(Boolean.FALSE);
        //System.out.println("==============> 2 ");
        //chantierPointageAff = chantierAffiniteService.findByChantier(chantierSelected);
        //System.out.println("==============> 3 ");

        //this.enginLoaded = true;
        //   chantierPointageAff = chantierService.findBy_ChantierAff_Id_(chantierSelect);

    } 
    
    public void getPointageEnginByEngin(Boolean reload) {
        if (chantierSelected !=null) {
            this.last_day_pointed = pointageEnginSerive.lastDayPointed(chantierSelected.getId());
            Calendar cal_pointage = Calendar.getInstance();
            if (this.last_day_pointed == null) {
                try {
                    this.last_day_pointed = Constante.get_first_day_in_pointage_Engin_forAllApplication();
                    cal_pointage.setTime(this.last_day_pointed);
                } catch (ParseException ex) {
                    System.out.println("erreur parsing date " + ex.getMessage());
                }
            } else {
                cal_pointage.setTime(this.last_day_pointed);
            }
            
            cal_pointage.add(Calendar.DATE, 1);
            this.last_day_pointed = cal_pointage.getTime(); 
            System.out.println("this.last_day_pointed 2 : "+this.last_day_pointed);
            
            //on verifie si on a dejà pointé le chantier pour ce jour
            Boolean havePointed = pointageEnginSerive.havePointed(chantierSelected.getId(), dateStart);

            System.out.println("havePointed : "+havePointed);
            if (havePointed == false) {
                le = enginSerive.findAllChantierArchivePanne(chantierSelected.getId());
                if (le == null || le.size() <= 0) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, Message.AFFECT_ENGIN_CHANTIER, ""));
                }
            } else if (reload.equals(Boolean.FALSE)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, Message.POINTAGE_DEJA_AFFECTED, ""));
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, Message.NO_ENGIN_SELECTED, ""));
        }
        
        if (le != null) {

            String[] temp_s = new String[1];
            //temp_s[0] = zones.get(0).getIdZone().toString();
            PointageEngin pe = new PointageEngin();
            //Lot l = new Lot();
            for (int i = 0; i < le.size(); i++) {
                pe = new PointageEngin();
                //pe.setZoneList(new LinkedList<Zone>());
                //pe.setIdLot(l);
                pe.setIDEngin(le.get(i));
                pe.setNbrHeures(le.get(i).getComteurHoraire());
                pe.setNbrKm(le.get(i).getCompteurKilometrique());
                pe.setZones_str(temp_s);
                lpe.add(pe);
            } 
        }
    }
    public List<PointageEngin>  chargerListPointage(List<Engin> leng){
        List<PointageEngin> l = new ArrayList<PointageEngin>();
        try {
            
            PointageEngin pe = new PointageEngin();
            for (int i = 0; i < leng.size(); i++) {
                pe = new PointageEngin();
                pe.setIDEngin(leng.get(i));
                pe.setNbrHeures(leng.get(i).getComteurHoraire());
                pe.setNbrKm(leng.get(i).getCompteurKilometrique()); 
                l.add(pe);
            } 
        } catch (Exception e) {
        }
        return l;
    }
    
    public void validerLpA(){
        List<PointageEngin> lpT = new ArrayList<PointageEngin>();
        try {
            for (PointageEngin p : lpA){
                lpT.add(p);
            }
            for (PointageEngin p : lpT){
                lpe.add(p);
            }
            for (PointageEngin p : lpT){
                lpA.remove(lpA.indexOf(p));
            }
            
        } catch (Exception e) {
            System.out.println("Erreur de validation le pointage automatique car "+e.getMessage());
        }
        isValidPointage();
    }
    
    public void validerLpM(){
        List<PointageEngin> lpT,lpS = new ArrayList<PointageEngin>();
        boolean verif = false;
        try {
           lpT = lpM; 
            for (PointageEngin p : lpT){
               verif =  verifEnginPointage(p);
                if(verif){
                    lpS.add(p);
                }else{
                    lpe.add(p);
                }
            }
           lpM = lpS; 
        } catch (Exception e) {
            System.out.println("Erreur de validation le pointage Manuel car "+e.getMessage());
        }
        isValidPointage();
    }
    
    public void isValidPointage(){
        if(lpe.size()==nbr){
            isvalid=Boolean.TRUE;
        }else{
            isvalid=Boolean.FALSE;
        }
    }

    public void updateDate() {
        //this.filter();
        lpe.clear();
        lpM = chargerListPointage(enginSerive.findAllEnginPointageManuelDept(chantierSelected.getId()));
        lpA = chargerListPointage(enginSerive.findAllEnginPointageAutoDept(chantierSelected.getId()));
        nbr=lpM.size()+lpA.size(); 

        isValidPointage();
    }
    
    public void annulerPointageEnginList(PointageEngin p){
        if("manuel".equals(p.getIDEngin().getTypePointageDept())){
            lpM.add(p);
            lpe.remove(p);
        }else{
            lpA.add(p);
            lpe.remove(p);
        }
        isValidPointage();
    }
    
    public void validate() {
        try {
            if (lpe.size() > 0) {
                DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                System.out.println("date ::::> "+date);
                String date_format = df.format(date);
                System.out.println("date_format ::::> "+date_format);
                messageErrorPointage = "";
                List<Engin> enginUpdate = new ArrayList<Engin>(); 
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                System.out.println("auth.getPrincipal().toString() :::::::::> "+auth.getPrincipal().toString());
                Utilisateur u = new Utilisateur();
                try {
                      u = utilisateurDAO.UserByLogin(auth.getPrincipal().toString());
                } catch (Exception e) {
                    System.out.println("Erreur de recupere l'utilisateur car "+e.getMessage());
                }
                System.out.println("Utilisateur u ::::::>  "+u.toString());
                for (int i=0 ; i<lpe.size();i++){
                    if(lpe.get(i).getIDEngin() !=null){
                        //System.out.println("ENGIN I : "+lpe.get(i).getIDEngin().toString());
                        lpe.get(i).setChantierPointage(lpe.get(i).getIDEngin().getPrjapId());
                        lpe.get(i).setDateCreation(date);
                        if(u!=null){
                            lpe.get(i).setUser(u);
                        }
                        enginUpdate.add(lpe.get(i).getIDEngin());
                    }
                }
                System.out.println("Debut de modification des engin ");
                enginSerive.updateListEngin(enginUpdate);
                System.out.println("Fin de modification des engin ");
                
                System.out.println("Debut d'enregistrement des pointage ");
                    pointageEnginSerive.saveListPointage(lpe);
                System.out.println("Fin d'enregistrement des pointage ");
                
                int sizePointage = lpe.size();
                if (chantierSelected !=null) {
                    this.last_day_pointed = pointageEnginSerive.lastDayPointed(chantierSelected.getId());
                    Calendar cal_pointage = Calendar.getInstance();
                    if (this.last_day_pointed == null) {
                        try {
                            this.last_day_pointed = Constante.get_first_day_in_pointage_Engin_forAllApplication();
                            cal_pointage.setTime(this.last_day_pointed);
                        } catch (ParseException ex) {
                            System.out.println("erreur parsing date " + ex.getMessage());
                        }
                    } else {
                        cal_pointage.setTime(this.last_day_pointed);
                    }

                cal_pointage.add(Calendar.DATE, 1);
                this.last_day_pointed = cal_pointage.getTime();
                date=last_day_pointed;
                    String message = sizePointage + Message.ADD_PE_BYDATE + date_format;
                    Module.message(1, message, "");
                    getPointageEnginByEngin(Boolean.TRUE);
                }
                updateDate();
            }
        } catch (Exception e) {
            System.out.println("Erreur d'effectuer le pointage car "+e.getMessage());
        }
    }

    public boolean verifEnginPointage(PointageEngin p) {
        /**
         * 1. Si ETAT POINTAGE = EN Marche   ::::> Compteurs aujourdhui >
         * Compteur de la veille        2. SI ETAT POINTAGE = EN Panne ou NON
         * Receptionne :::::> le Compteur  >= Compteur engin 3. Le compteur ne
         * doit pas changer si Etat engin inactif 4. La différence entre
         * compteur horaire  précédent et le compteur horaire d’aujourd'hui Doit
         * être < = 24h   5. La différence du compteur Km <= 1000 km 6. La fiche
         * d'engin doit contenir dernier compteur horaire et kilométrique.
         *
         */

        boolean verif = false;
        if (p.getiDEngin().getTypeCompteur() != null) {
            
            Engin e = enginSerive.findOneById(p.getIDEngin().getIDEngin());
            /*
            System.out.println("================> p.getEtatEngin() " + p.getEtatEngin());
            System.out.println("================> (p.getiDEngin().getTypeCompteur() " + p.getiDEngin().getTypeCompteur());
            System.out.println("================> (p.getiDEngin().getTypeCompteur()=='kilométrique_horaire') " + (p.getiDEngin().getTypeCompteur().equals("kilométrique_horaire")));
            System.out.println("================> (p.getiDEngin().getTypeCompteur()=='kilométrique') " + (p.getiDEngin().getTypeCompteur().equals("kilométrique")));
            System.out.println("================> (p.getiDEngin().getTypeCompteur()=='horaire') " + (p.getiDEngin().getTypeCompteur().equals("horaire")));

            System.out.println("================> Engin e tostring " + e.toString());
            System.out.println("================> Engin p tostring " + p.getiDEngin().toString());
            System.out.println("================> PointageEngin p " + p.toString());
            System.out.println("================> verifEnginPointage : TypeCompteur() " + p.getiDEngin().getTypeCompteur());
            System.out.println("================> verifEnginPointage : p.getIDEngin().getEtat()  " + p.getIDEngin().getEtat());
            System.out.println("================> verifEnginPointage : p.getNbrHeures() " + p.getNbrHeures());
            System.out.println("================> verifEnginPointage : p.getNbrKm() " + p.getNbrKm());
            System.out.println("================> verifEnginPointage : p.getIDEngin().getComteurHoraire() " + p.getIDEngin().getComteurHoraire());
            System.out.println("================> verifEnginPointage : p.getIDEngin().getCompteurKilometrique() " + p.getIDEngin().getCompteurKilometrique());
             */
            if (p.getNbrHeures() == null || p.getNbrKm() == null) {
                messageErrorPointage += "NbrHeures ou NbrKm ou Lot n'est Valide pour l'engin " + p.getiDEngin().getCode();
                //System.out.println("messageErrorPointage :"+messageErrorPointage);
                return true;
            }
            System.out.println("=======> p.getiDEngin().getTypeCompteur() : "+p.getiDEngin().getTypeCompteur());

            if (p.getiDEngin().getTypeCompteur().equals("kilométrique_horaire")) {

                if (p.getIDEngin().getEtat().equals("EN_MARCHE") && p.getNbrKm() <= p.getIDEngin().getCompteurKilometrique()) {
                    messageErrorPointage += "L'engin " + p.getiDEngin().getCode() + " est 'EN_MARCHE' alors que le compteur saisi est inférieur ou égal au compteur de la veille (KM).";
                    //System.out.println("messageErrorPointage :"+messageErrorPointage);
                    verif = true;
                    verif =false; // a supprimer
                }
                if (p.getIDEngin().getEtat().equals("EN_MARCHE") && p.getNbrHeures() <= p.getIDEngin().getComteurHoraire()) {
                    messageErrorPointage += "L'engin " + p.getiDEngin().getCode() + " est 'EN_MARCHE' alors que le compteur saisi est inférieur ou égal au compteur de la veille (H).";
                    //System.out.println("messageErrorPointage :"+messageErrorPointage);
                    verif = true;
                    verif =false; // a supprimer
                }
                if ((p.getIDEngin().getEtat().equals("EN_PANNE") || p.getIDEngin().getEtat().equals("NON_RECEPTIONNE")) && p.getNbrKm() < p.getIDEngin().getCompteurKilometrique()) {
                    messageErrorPointage += "Le compteur kilométrique saisie supérieur ou éguale au celle de l'engin " + p.getiDEngin().getCode() + " qui'est EN_PANNE ou NON_RECEPTIONNE.";
                    //System.out.println("messageErrorPointage :"+messageErrorPointage);
                    verif = true;
                    verif =false; // a supprimer
                }
                if ((p.getIDEngin().getEtat().equals("EN_PANNE") || p.getIDEngin().getEtat().equals("NON_RECEPTIONNE")) && p.getNbrHeures() < p.getIDEngin().getComteurHoraire()) {
                    messageErrorPointage += "Le compteur horaire saisie supérieur ou éguale au celle de l'engin " + p.getiDEngin().getCode() + " qui'est EN_PANNE ou NON_RECEPTIONNE.";
                    //System.out.println("messageErrorPointage :"+messageErrorPointage);
                    verif = true;
                    verif =false; // a supprimer
                }
                if (p.getIDEngin().getEtat().equals("INACTIF") && p.getIDEngin().getCompteurKilometrique() != p.getNbrKm()) {
                    messageErrorPointage += "L'engin  " + p.getiDEngin().getCode() + " est 'INACTIF' alors que le compteur saisi est différent du compteur de la veille (KM).";
                    //System.out.println("messageErrorPointage :"+messageErrorPointage);
                    verif = true;
                    verif =false; // a supprimer
                }
                if (p.getIDEngin().getEtat().equals("INACTIF") && p.getIDEngin().getComteurHoraire() != p.getNbrHeures()) {
                    messageErrorPointage += "L'engin  " + p.getiDEngin().getCode() + " est 'INACTIF' alors que le compteur saisi est différent du compteur de la veille (H).";
                    //System.out.println("messageErrorPointage :"+messageErrorPointage);
                    verif = true;
                    verif =false; // a supprimer
                }
                if ((p.getNbrKm() - p.getIDEngin().getCompteurKilometrique()) >= 1000) {
                    messageErrorPointage += "Le compteur saisi pour l'engin " + p.getiDEngin().getCode()+" est supérieur de 1000 Km par rapport à celui de la veille.";
                    //System.out.println("messageErrorPointage :"+messageErrorPointage);
                    verif = true;
                }
                if ((p.getNbrHeures() - p.getIDEngin().getComteurHoraire()) > 24) {
                    messageErrorPointage += "Le compteur saisi pour l'engin "+ p.getiDEngin().getCode()+" est supérieur de 24H par rapport à celui de la veille." ;
                    //System.out.println("messageErrorPointage :"+messageErrorPointage);
                    verif = true;
                }
            } else if (p.getiDEngin().getTypeCompteur().equals("kilométrique")) {

                if (p.getIDEngin().getEtat().equals("EN_MARCHE") && p.getNbrKm() <= p.getIDEngin().getCompteurKilometrique()) {
                    messageErrorPointage += "L'engin " + p.getiDEngin().getCode() + " est 'EN_MARCHE' alors que le compteur saisi est inférieur ou égal au compteur de la veille (KM).";
                    //System.out.println("messageErrorPointage :"+messageErrorPointage);
                    verif = true;
                    verif =false; // a supprimer
                }
                if ((p.getIDEngin().getEtat().equals("EN_PANNE") || p.getIDEngin().getEtat().equals("NON_RECEPTIONNE")) && p.getNbrKm() < p.getIDEngin().getCompteurKilometrique()) {
                    messageErrorPointage += "Le compteur kilométrique saisie supérieur ou éguale au celle de l'engin " + p.getiDEngin().getCode() + " qui'est EN_PANNE ou NON_RECEPTIONNE.";
                    //System.out.println("messageErrorPointage :"+messageErrorPointage);
                    verif = true;
                    verif =false; // a supprimer
                }
                if (p.getIDEngin().getEtat().equals("INACTIF") && p.getIDEngin().getCompteurKilometrique().intValue() != p.getNbrKm()) {
                    messageErrorPointage += "L'engin  " + p.getiDEngin().getCode() + " est 'INACTIF' alors que le compteur saisi est différent du compteur de la veille (KM).";
                    //System.out.println("messageErrorPointage :"+messageErrorPointage);
                    verif = true;
                    verif =false; // a supprimer
                }
                if ((p.getNbrKm() - p.getIDEngin().getCompteurKilometrique()) >= 1000) {
                    messageErrorPointage += "Le compteur saisi pour l'engin " + p.getiDEngin().getCode()+" est supérieur de 1000 Km par rapport à celui de la veille.";
                    //System.out.println("messageErrorPointage :"+messageErrorPointage);
                    verif = true;
                }
            } else if (p.getiDEngin().getTypeCompteur().equals("horaire")) {
                if (p.getIDEngin().getEtat().equals("EN_MARCHE") && p.getNbrHeures() <= p.getIDEngin().getComteurHoraire()) {
                    messageErrorPointage += "L'engin " + p.getiDEngin().getCode() + " est 'EN_MARCHE' alors que le compteur saisi est inférieur ou égal au compteur de la veille (H).";
                    //System.out.println("messageErrorPointage :"+messageErrorPointage);
                    verif = true;
                    verif =false; // a supprimer
                }
                if ((p.getIDEngin().getEtat().equals("EN_PANNE") || p.getIDEngin().getEtat().equals("NON_RECEPTIONNE")) && p.getNbrHeures() < p.getIDEngin().getComteurHoraire()) {
                    messageErrorPointage += "Le compteur horaire saisie supérieur ou éguale au celle de l'engin " + p.getiDEngin().getCode() + " qui'est EN_PANNE ou NON_RECEPTIONNE.";
                    //System.out.println("messageErrorPointage :"+messageErrorPointage);
                    verif = true;
                    verif =false; // a supprimer
                }
                if (p.getIDEngin().getEtat().equals("INACTIF") && p.getIDEngin().getComteurHoraire().intValue() != p.getNbrHeures()) {
                    messageErrorPointage += "L'engin  " + p.getiDEngin().getCode() + " est 'INACTIF' alors que le compteur saisi est différent du compteur de la veille (H).";
                    //System.out.println("messageErrorPointage :"+messageErrorPointage);
                    verif = true;
                    verif =false; // a supprimer
                }
                if ((p.getNbrHeures() - p.getIDEngin().getComteurHoraire()) > 24) {
                    messageErrorPointage += "Le compteur saisi pour l'engin "+ p.getiDEngin().getCode()+" est supérieur de 24H par rapport à celui de la veille." ;
                    //System.out.println("messageErrorPointage :"+messageErrorPointage);
                    verif = true;
                }
            }
        } else {
            messageErrorPointage += "Merci de préciser le type du compteur de l'engin " + p.getiDEngin().getCode()+".";
            //System.out.println("messageErrorPointage :"+messageErrorPointage);
            verif = true;
        }
        return verif;

    }
}
