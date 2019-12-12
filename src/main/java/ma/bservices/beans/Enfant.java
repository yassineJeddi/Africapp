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
import javax.persistence.Transient;

@Entity
@Table(name = "ENFANT")
public class Enfant implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -4674461274575980190L;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "PRENOM")
    private String prenom;

    @Column(name = "DATENAISSANCE")
    private Date dateNaissance;

    @Column(name = "CODEDIVA")
    private String codeDiva;

    /**
     * @return the codeDiva
     */
    public String getCodeDiva() {
        return codeDiva;
    }

    /**
     * @param codeDiva the codeDiva to set
     */
    public void setCodeDiva(String codeDiva) {
        this.codeDiva = codeDiva;
    }

    @ManyToOne
    private Salarie salarie;

    @Transient
    private String chaineDateNaissance;

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
     * @return the prenom
     */
    public String getPrenom() {
        return prenom;
    }

    /**
     * @param prenom the prenom to set
     */
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    /**
     * @return the dateNaissance
     */
    public Date getDateNaissance() {
        return dateNaissance;
    }

    /**
     * @param dateNaissance the dateNaissance to set
     */
    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    /**
     * @return the salarie
     */
    public Salarie getSalarie() {
        return salarie;
    }

    /**
     * @param salarie the salarie to set
     */
    public void setSalarie(Salarie salarie) {
        this.salarie = salarie;
    }

    /**
     * @return the chaineDateNaissance
     */
    public String getChaineDateNaissance() {
        return chaineDateNaissance;
    }

    /**
     * @param chaineDateNaissance the chaineDateNaissance to set
     */
    public void setChaineDateNaissance(String chaineDateNaissance) {
        this.chaineDateNaissance = chaineDateNaissance;
    }

}
