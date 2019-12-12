/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.Entity;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author a.wattah
 */
@Entity
@Table(name = "LOYER")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@XmlRootElement
public class Loyer implements Serializable {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NOMPROPRIETAIRE")
    private String nomproprietaire;
    
    @Column(name = "NUMPROPRIETAIRE")
    private String numproprietaire;

    @Column(name = "PRENOMPROPRIETAIRE")
    private String prenomproprietaire;

    @Column(name = "ADRESSEROPRIETAIRE")
    private String adresseproprietaire;

    @Column(name = "DATEDEBUT")
    @Temporal(TemporalType.DATE)
    private Date datedebut;
    
    
     @Column(name = "DATEFINCONTRAT")
    @Temporal(TemporalType.DATE)
    private Date dateFinContrat;
    
    @Column(name = "DATEFIN")
    private String dateFin;

    @Column(name = "MOTANTLOYER")
    private String montantloyer;

    @Column(name = "MODEPAIEMENT")
    private String modepaiment;

    @Column(name = "RIB")
    private String rib;

    @Column(name = "NUMTELEPHONE")
    private String numtelephone;
    
    
    @Column(name = "ESTARCHIVE", nullable = true)
    private Boolean estArchive;

    @Column(name = "NUMCONTRAT")
    private String numcontrat;

    @Column(name = "VILLE")
    private String ville;

    @OneToOne
    private Mensuel mensuel_Principal;

    public Mensuel getMensuel_Principal() {
        return mensuel_Principal;
    }

    public void setMensuel_Principal(Mensuel mensuel_Principal) {
        this.mensuel_Principal = mensuel_Principal;
    }

    public Loyer() {
        super();
    }
    
    
    public Date getDateFinContrat() {
        return dateFinContrat;
    }

    public void setDateFinContrat(Date dateFinContrat) {
        this.dateFinContrat = dateFinContrat;
    }

    public String getNumproprietaire() {
        return numproprietaire;
    }

    public void setNumproprietaire(String numproprietaire) {
        this.numproprietaire = numproprietaire;
    }

    
    
    public String getNumcontrat() {
        return numcontrat;
    }

    public void setNumcontrat(String numcontrat) {
        this.numcontrat = numcontrat;
    }
    
    

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getNumtelephone() {
        return numtelephone;
    }

    public Boolean isEstArchive() {
        return estArchive;
    }

    public void setEstArchive(Boolean estArchive) {
        this.estArchive = estArchive;
    }   
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNumtelephone(String numtelephone) {
        this.numtelephone = numtelephone;
    }

    public String getNomproprietaire() {
        return nomproprietaire;
    }
    
    

    public void setNomproprietaire(String nomproprietaire) {
        this.nomproprietaire = nomproprietaire;
    }

    public String getPrenomproprietaire() {
        return prenomproprietaire;
    }

    public void setPrenomproprietaire(String prenomproprietaire) {
        this.prenomproprietaire = prenomproprietaire;
    }

    public String getAdresseproprietaire() {
        return adresseproprietaire;
    }

    public void setAdresseproprietaire(String adresseproprietaire) {
        this.adresseproprietaire = adresseproprietaire;
    }

    public String getDatedebut() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String s = formatter.format(datedebut);
        return s;
    }

    public String getDateFin() {
        return dateFin;
    }

    public void setDateFin(String dateFin) {
        this.dateFin = dateFin;
    }

    
    public void setDatedebut(String datedebuts) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date d = formatter.parse(datedebuts);
        System.out.println("date set : " + datedebuts);
        this.datedebut = d;
    }
    public void setDateDebutDate(Date d){
    datedebut = d;
    }
    public String getMontantloyer() {
        return montantloyer;
    }

    public void setMontantloyer(String montantloyer) {
        this.montantloyer = montantloyer;
    }

    public String getModepaiment() {
        return modepaiment;
    }

    public void setModepaiment(String modepaiment) {
        this.modepaiment = modepaiment;
    }

    public String getRib() {
        return rib;
    }

    public void setRib(String rib) {
        this.rib = rib;
    }

    @Override
    public String toString() {
        return "Loyer{" + "id=" + id + ", nomproprietaire=" + nomproprietaire + ", numproprietaire=" + numproprietaire + ", prenomproprietaire=" + prenomproprietaire + ", adresseproprietaire=" + adresseproprietaire + ", datedebut=" + datedebut + ", dateFinContrat=" + dateFinContrat + ", dateFin=" + dateFin + ", montantloyer=" + montantloyer + ", modepaiment=" + modepaiment + ", rib=" + rib + ", numtelephone=" + numtelephone + ", estArchive=" + estArchive + ", numcontrat=" + numcontrat + ", ville=" + ville + ", mensuel_Principal=" + mensuel_Principal + '}';
    }

    
}
