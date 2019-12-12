/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.Entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author yassine
 */
@Entity
@Table(name = "INTERVENTION_MAINTENANCE")
public class InterventionMaintenance implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
        
    @Column(name = "TECH_DIAG")
    private String TECH_DIAG;
        
    @Column(name = "TECH_REP")
    private String TECH_REP;
        
    @Column(name = "DESC_INTERV")
    private String DESC_INTERV;
        
    @Column(name = "DESC_DIAG")
    private String DESC_DIAG;
        
    @Column(name = "COMM_SECURITE")
    private String COMM_SECURITE;
        
    @Column(name = "TYPE_PANNE")
    private String TYPE_PANNE;
        
    @Column(name = "SECTEUR_INTERV")
    private String SECTEUR_INTERV;
        
    @Column(name = "NUM_FM")
    private String NUM_FM;
        
    @Column(name = "TYPE_INTERV")
    private String TYPE_INTERV;
        
    @Column(name = "ETAT")
    private String ETAT;
        
    @Column(name = "DUREE_PREVUE")
    private Integer DUREE_PREVUE;
         
    
    @Column(name = "ID_ENGIN")
    private Integer ID_ENGIN;
    
    @Column(name = "PRJAP_ID_INTER")
    private Integer PRJAP_ID_INTER;
    
    @Column(name = "TYPE_REV")
    private Integer TYPE_REV;
    
    @Column(name = "CPT_REV")
    private Float CPT_REV;
    
    @Column(name = "CPT_A_REV")
    private Float CPT_A_REV;
    
    @Column(name = "DATE_INTER")
    private Date DATE_INTER;
    
    @Column(name = "DATE_REV")
    private Date DATE_REV;

    @ManyToOne()
    @JoinColumn(name = "ID_HISTO_PANNE")
    private Panne panne;
    
    /**
     * Getters Setters
     */
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTECH_DIAG() {
        return TECH_DIAG;
    }

    public void setTECH_DIAG(String TECH_DIAG) {
        this.TECH_DIAG = TECH_DIAG;
    }

    public String getTECH_REP() {
        return TECH_REP;
    }

    public void setTECH_REP(String TECH_REP) {
        this.TECH_REP = TECH_REP;
    }

    public String getDESC_INTERV() {
        return DESC_INTERV;
    }

    public void setDESC_INTERV(String DESC_INTERV) {
        this.DESC_INTERV = DESC_INTERV;
    }

    public String getDESC_DIAG() {
        return DESC_DIAG;
    }

    public void setDESC_DIAG(String DESC_DIAG) {
        this.DESC_DIAG = DESC_DIAG;
    }

    public String getCOMM_SECURITE() {
        return COMM_SECURITE;
    }

    public void setCOMM_SECURITE(String COMM_SECURITE) {
        this.COMM_SECURITE = COMM_SECURITE;
    }

    public String getTYPE_PANNE() {
        return TYPE_PANNE;
    }

    public void setTYPE_PANNE(String TYPE_PANNE) {
        this.TYPE_PANNE = TYPE_PANNE;
    }

    public String getSECTEUR_INTERV() {
        return SECTEUR_INTERV;
    }

    public void setSECTEUR_INTERV(String SECTEUR_INTERV) {
        this.SECTEUR_INTERV = SECTEUR_INTERV;
    }

    public String getNUM_FM() {
        return NUM_FM;
    }

    public void setNUM_FM(String NUM_FM) {
        this.NUM_FM = NUM_FM;
    }

    public String getTYPE_INTERV() {
        return TYPE_INTERV;
    }

    public void setTYPE_INTERV(String TYPE_INTERV) {
        this.TYPE_INTERV = TYPE_INTERV;
    }

    public String getETAT() {
        return ETAT;
    }

    public void setETAT(String ETAT) {
        this.ETAT = ETAT;
    }

    public Integer getDUREE_PREVUE() {
        return DUREE_PREVUE;
    }

    public void setDUREE_PREVUE(Integer DUREE_PREVUE) {
        this.DUREE_PREVUE = DUREE_PREVUE;
    }

    public Integer getID_ENGIN() {
        return ID_ENGIN;
    }

    public void setID_ENGIN(Integer ID_ENGIN) {
        this.ID_ENGIN = ID_ENGIN;
    }

    public Integer getPRJAP_ID_INTER() {
        return PRJAP_ID_INTER;
    }

    public void setPRJAP_ID_INTER(Integer PRJAP_ID_INTER) {
        this.PRJAP_ID_INTER = PRJAP_ID_INTER;
    }

    public Integer getTYPE_REV() {
        return TYPE_REV;
    }

    public void setTYPE_REV(Integer TYPE_REV) {
        this.TYPE_REV = TYPE_REV;
    }

    public Float getCPT_REV() {
        return CPT_REV;
    }

    public void setCPT_REV(Float CPT_REV) {
        this.CPT_REV = CPT_REV;
    }

    public Float getCPT_A_REV() {
        return CPT_A_REV;
    }

    public void setCPT_A_REV(Float CPT_A_REV) {
        this.CPT_A_REV = CPT_A_REV;
    }

    public Date getDATE_INTER() {
        return DATE_INTER;
    }

    public void setDATE_INTER(Date DATE_INTER) {
        this.DATE_INTER = DATE_INTER;
    }

    public Date getDATE_REV() {
        return DATE_REV;
    }

    public void setDATE_REV(Date DATE_REV) {
        this.DATE_REV = DATE_REV;
    }

    public Panne getPanne() {
        return panne;
    }

    public void setPanne(Panne panne) {
        this.panne = panne;
    }
    
    public InterventionMaintenance() {
    }

    @Override
    public String toString() {
        return "InterventionMaintenance{" + "id=" + id + ", TECH_DIAG=" + TECH_DIAG + ", TECH_REP=" + TECH_REP + ", DESC_INTERV=" + DESC_INTERV + ", DESC_DIAG=" + DESC_DIAG + ", COMM_SECURITE=" + COMM_SECURITE + ", TYPE_PANNE=" + TYPE_PANNE + ", SECTEUR_INTERV=" + SECTEUR_INTERV + ", NUM_FM=" + NUM_FM + ", TYPE_INTERV=" + TYPE_INTERV + ", ETAT=" + ETAT + ", DUREE_PREVUE=" + DUREE_PREVUE + ", ID_ENGIN=" + ID_ENGIN + ", PRJAP_ID_INTER=" + PRJAP_ID_INTER + ", TYPE_REV=" + TYPE_REV + ", CPT_REV=" + CPT_REV + ", CPT_A_REV=" + CPT_A_REV + ", DATE_INTER=" + DATE_INTER + ", DATE_REV=" + DATE_REV + '}';
    }
 
    
    
    
}
