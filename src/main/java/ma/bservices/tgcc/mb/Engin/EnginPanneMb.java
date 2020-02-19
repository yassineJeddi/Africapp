/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.mb.Engin;

import com.itextpdf.text.log.SysoLogger;
import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;
import ma.bservice.tgcc.Constante.Constante;
import ma.bservices.services.MailConfigService;
import ma.bservices.tgcc.service.Engin.EnginService;
import ma.bservices.tgcc.service.Engin.PointageEnginServices;
import ma.bservices.tgcc.Entity.Engin;
import ma.bservices.tgcc.Entity.MailConfigBean;
import ma.bservices.tgcc.Entity.Panne;
import ma.bservices.tgcc.Entity.PointageEngin;
import ma.bservices.tgcc.utilitaire.Outils;
import org.quartz.SchedulerException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;
import ma.bservices.tgcc.service.SendEmail;

/**
 *
 * @author zakaria.dem
 */
@Component
@ManagedBean(name = "enginPanne")
@ViewScoped
public class EnginPanneMb implements Serializable {

    @ManagedProperty(value = "#{enginService}")
    private EnginService enginSerive;

    @ManagedProperty(value = "#{enginPanneServices}")
    private PointageEnginServices pointageEnginSerive;

    @ManagedProperty(value = "#{mailConfigService}")
    private MailConfigService mailConfigService;

    /**
     * engin mise en marche
     */
    private Engin engin_mem;

    private Engin selected;

    private String etat;

    private Integer chantier;

    private String designation;

    private String code;

    private Map<String, String> codes;

    private Map<String, Map<String, String>> data = new HashMap<>();

    private Map<String, String> chantiersMap;

    private List<Engin> peCodeDesMarqu;

    private String designation1;

    private String marque1;

    private String lieu;

    private String compteur;

    private Engin declarationPanne = new Engin();

    private String comm;

    private List<Engin> engins;

    private List<Engin> enginInChantier;

    private Integer idChantierAffected;

    private String lastChantierBeforeDepot;
    
    private List<Panne> allPanne;
    
    private Panne lastPanne;

    public Panne getLastPanne() {
        return lastPanne;
    }

    public void setLastPanne(Panne lastPanne) {
        this.lastPanne = lastPanne;
    }
    
    public List<Panne> getAllPanne() {
        return allPanne;
    }

    public void setAllPanne(List<Panne> allPanne) {
        this.allPanne = allPanne;
    }

    public String getLastChantierBeforeDepot() {
        return lastChantierBeforeDepot;
    }

    public void setLastChantierBeforeDepot(String lastChantierBeforeDepot) {
        this.lastChantierBeforeDepot = lastChantierBeforeDepot;
    }

    public Integer getIdChantierAffected() {
        return idChantierAffected;
    }

    public void setIdChantierAffected(Integer idChantierAffected) {
        this.idChantierAffected = idChantierAffected;
    }

    public Map<String, String> getCodes() {
        return codes;
    }

    public Map<String, Map<String, String>> getData() {
        return data;
    }

    public void setData(Map<String, Map<String, String>> data) {
        this.data = data;
    }

    public Map<String, String> getChantiersMap() {
        return chantiersMap;
    }

    public void setChantiersMap(Map<String, String> chantiersMap) {
        this.chantiersMap = chantiersMap;
    }

    public void setCodes(Map<String, String> codes) {
        this.codes = codes;
    }

    public EnginService getEnginSerive() {
        return enginSerive;
    }

    public void setEnginSerive(EnginService enginSerive) {
        this.enginSerive = enginSerive;
    }

    public String getMarque1() {
        return marque1;
    }

    public void setMarque1(String marque1) {
        this.marque1 = marque1;
    }

    public List<Engin> getEngins() {
        return engins;
    }

    public void setEngins(List<Engin> engins) {
        this.engins = engins;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public MailConfigService getMailConfigService() {
        return mailConfigService;
    }

    public void setMailConfigService(MailConfigService mailConfigService) {
        this.mailConfigService = mailConfigService;
    }

    public List<Engin> getEnginInChantier() {
        return enginInChantier;
    }

    public void setEnginInChantier(List<Engin> enginInChantier) {
        this.enginInChantier = enginInChantier;
    }

    public Engin getDeclarationPanne() {
        return declarationPanne;
    }

    public void setDeclarationPanne(Engin declarationPanne) {
        this.declarationPanne = declarationPanne;
    }

    public String getCompteur() {
        return compteur;
    }

    public void setCompteur(String compteur) {
        this.compteur = compteur;
    }

    public Engin getEngin_mem() {
        return engin_mem;
    }

    public void setEngin_mem(Engin engin_mem) {
        this.engin_mem = engin_mem;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public String getDesignation1() {
        return designation1;
    }

    public void setDesignation1(String designation1) {
        this.designation1 = designation1;
    }

    public Integer getChantier() {
        return chantier;
    }

    public void setChantier(Integer chantier) {
        this.chantier = chantier;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public List<Engin> getPeCodeDesMarqu() {
        return peCodeDesMarqu;
    }

    public void setPeCodeDesMarqu(List<Engin> peCodeDesMarqu) {
        this.peCodeDesMarqu = peCodeDesMarqu;
    }

    public PointageEnginServices getPointageEnginSerive() {
        return pointageEnginSerive;
    }

    public void setPointageEnginSerive(PointageEnginServices pointageEnginSerive) {
        this.pointageEnginSerive = pointageEnginSerive;
    }

    public String getCode() {
        return code;
    }

    public String getComm() {
        return comm;
    }

    public void setComm(String comm) {
        this.comm = comm;
    }

    public Engin getSelected() {
        return selected;
    }

    public void setSelected(Engin selected) {
        this.selected = selected;
    }

    public void setCode(String code) {
        this.code = code;
    }

    private List<Engin> engin_panne;

    public List<Engin> getEngin_panne() {
        return engin_panne;
    }

    public void setEngin_panne(List<Engin> engin_panne) {
        this.engin_panne = engin_panne;
    }

    /**
     * Creates a new instance of EnginMb
     */
    public EnginPanneMb() {

    }

    @PostConstruct
    public void init() {
        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        enginSerive = ctx.getBean(EnginService.class);

        engin_mem = new Engin();

//        enginSerive = ctx.getBean(EnginService.class);
//        engins = enginSerive.findAll();
        pointageEnginSerive = ctx.getBean(PointageEnginServices.class);
        mailConfigService = ctx.getBean(MailConfigService.class);
        selected = new Engin();

        enginInChantier = new LinkedList<Engin>();

        engin_panne = enginSerive.search(null, null, null, Constante.CODE_ETAT_ENGIN_PANNE, -1);
        
        allPanne = enginSerive.lastPanneByEnginPanne();
        
    }

    /**
     * mise en marche un vehicule L'affect à un chantier ce chantier doit le
     * pointer en état "non receptionné" jusqu'à réception physique
     */
    public void updateEtat() {

        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        pointageEnginSerive = ctx.getBean(PointageEnginServices.class);

        //Engin eng = enginSerive.findOneById(engin_mem.getIDEngin());
        Engin eng = lastPanne.getEngin();
        try {
            if(eng!=null){
               // if(eng.getCompteurKilometrique()<=engin_mem.getCompteurKilometrique()
               // && eng.getComteurHoraire()<=engin_mem.getComteurHoraire()){
                    designation = engin_mem.getDesignation();
                    engin_mem.setEtat(Constante.CODE_ETAT_ENGIN_MARCHE);
                    
                    Date _d = new Date();
                    
                    //Panne p = enginSerive.lastPanneByEngin(eng);
                    
                    //ajouter la date de mise en marche dans l'historique de la panne
                    if (engin_mem.getHist_panne_id() != null) {
                        engin_mem.getHist_panne_id().setDate_marche(_d);
                    }
                    

                    //Update Affectation du Chantier
                    engin_mem.setDateMiseMarche(_d);
                    lastPanne.setDate_marche(_d);
                    //enginSerive.affecterEngin(engin_mem, chantier);
                    enginSerive.mise_en_marche(engin_mem, chantier,lastPanne);
                    allPanne = enginSerive.lastPanneByEnginPanne();

               // }
            }
        } catch (Exception e) {
            System.out.println("Erreur de mettre l'engin en marche car "+e.getMessage());
        }

        //envoyer un email aux responsable du chantier
        ApplicationContext context = new ClassPathXmlApplicationContext("mail.xml");

        List<String> listDestinatairesMail = getRecipientsByModule("ENGIN_MEM");

        SendEmail sm = (SendEmail) context.getBean("sendEmail");

        if (listDestinatairesMail != null && !listDestinatairesMail.isEmpty()) {
            for (String m : listDestinatairesMail) {
                sm.sendMail("tgccbtp@gmail.com", m, "Engin réparé", "Bonjour, L'engin " + engin_mem.getCode() + " : " + engin_mem.getDesignation() + " du chantier : " + engin_mem.getPrjapId().getCode() + " a été réparé");
            }
        }
        
        this.engin_panne = enginSerive.search(null, null, null, Constante.CODE_ETAT_ENGIN_PANNE, -1);

        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        ma.bservices.tgcc.mb.services.EnginMb enginMb = (ma.bservices.tgcc.mb.services.EnginMb) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "enginServMb");

        enginMb.reload();

    }

    public void onChantierChange() {

        if (chantier != -1) {
            enginInChantier = enginSerive.findAllInChantier(chantier);
        } else {

        }
    }
    
      public List<String> getRecipientsByModule(String module) {
        List<String> destinataires = new LinkedList<>();
        MailConfigBean listDestinatairesMail = mailConfigService.findByModule(module).get(0);
        String[] email_strings = listDestinatairesMail.getMail_to().split(";");
        destinataires.addAll(Arrays.asList(email_strings));

        return destinataires;
    }

    public void onchangeDesMarq() {

        //code, get Engin by code in Chantier "enginInChantier"
        selected = enginSerive.findEnginByCodeFromEnginsInChantier(enginInChantier, code);

    }

    public void validateDeclaration() throws SchedulerException, ParseException, MessagingException {
        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        pointageEnginSerive = ctx.getBean(PointageEnginServices.class);

        pointageEnginSerive.addEngin_PAnne(selected);

        FacesContext context = FacesContext.getCurrentInstance();
        String message = ma.bservice.tgcc.Constante.Message.ENGIN_PANNE + selected.getCode();
        context.addMessage(null, new FacesMessage("" + message, ""));

        this.engin_panne = enginSerive.search(null, null, null, "panne", -1);
        /**
         * Partie d'envoi d'un email
         */
//        SchedulerFactory sf=new StdSchedulerFactory();
//        Scheduler sched=sf.getScheduler();
//        JobDetail jd=new JobDetail("Job1", "group1", CronJob.class);
//        CronTrigger ct=new CronTrigger("conTrigger", "group2", "* * * * * ?");
//        sched.scheduleJob(jd, ct);
//        sched.start();

        Properties props = new Properties();
        props.put("mail.smtp.host", "mail.bservices.ma");
        props.put("mail.from", "jal@novway.com");
        props.put("mail.smtp.auth", true);

        Authenticator authenticator = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("jal@novway.com", "bservices2012#");
            }
        };

        Session session = Session.getInstance(props, authenticator);
        try {
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom();
            msg.setRecipients(Message.RecipientType.TO, "jihan.allali@gmail.com");
            msg.setSubject("Message d'un engin en panne");
            msg.setSentDate(new Date());
            msg.setText("Attentien !!! L'engin :" + selected.getCode() + "est en panne . Merci\n");
            Transport.send(msg);
        } catch (MessagingException mex) {

            FacesMessage facesMessage = new FacesMessage();
            facesMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
            facesMessage.setSummary("" + mex);
            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.addMessage("null", facesMessage);
        }

    }
    public void prepPanne(Panne p){
        lastPanne = p;
        dernierChan_Avant_Depot();
    }

    public void dernierChan_Avant_Depot() {
        List<PointageEngin> listChan = new ArrayList<>();
        engin_mem=lastPanne.getEngin();
        //System.out.println("=============> engin_mem : "+engin_mem.toString());
        listChan = pointageEnginSerive.getPointageEnginByDate_Diff_Depo(engin_mem.getIDEngin());

        //System.out.println("=============> listChan : "+listChan);
        if (listChan != null && listChan.size() > 0) {
            //System.out.println("chantier d'afectation ::::> "+engin_mem.getPrjapId().getCode());
            if("DEPARTEMENT LOGISTIQUE".equals(engin_mem.getPrjapId().getCode().trim())){
                chantier=158;
            }else{
                chantier = listChan.get(0).getChantierPointage().getId();   
            }
        }
    }

    public String convertFormatDate(Date date) {

        Outils outil = new Outils();
        return outil.convertDate_To_string(date);

    }
     

}
