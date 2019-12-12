package ma.bservices.beans;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "HISTORIQUECONTRAT")
public class HistoriqueContrat implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "IDCONTRAT")
    private Integer idContrat;

    @ManyToOne
    private Utilisateur utilisateur;

    @ManyToOne
    private Fonction fonction;

    @Column(name = "TAUXHORAIRE")
    private Float tauxHoraire;

    @Column(name = "DATE")
    @Temporal(TemporalType.DATE)
    private Date date;

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the idContrat
     */
    public Integer getIdContrat() {
        return idContrat;
    }

    /**
     * @param idContrat the idContrat to set
     */
    public void setIdContrat(Integer idContrat) {
        this.idContrat = idContrat;
    }

    /**
     * @return the utlisateur
     */
    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    /**
     * @param utlisateur the utlisateur to set
     */
    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    /**
     * @return the fonction
     */
    public Fonction getFonction() {
        return fonction;
    }

    /**
     * @param fonction the fonction to set
     */
    public void setFonction(Fonction fonction) {
        this.fonction = fonction;
    }

    /**
     * @return the tauxHoraire
     */
    public Float getTauxHoraire() {
        return tauxHoraire;
    }

    /**
     * @param tauxHoraire the tauxHoraire to set
     */
    public void setTauxHoraire(Float tauxHoraire) {
        this.tauxHoraire = tauxHoraire;
    }

    /**
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(Date date) {
        this.date = date;
    }

}
