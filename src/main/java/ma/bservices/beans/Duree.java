/**
 *
 */
package ma.bservices.beans;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author root
 *
 */
@Entity
@Table(name = "DUREE")
public class Duree implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -8811704518334918943L;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "DUREECONTRAT")
    private String dureeContrat;

    @Column(name = "NOMBREMOIS")
    private Integer nombreMois;

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
     * @return the dureeContrat
     */
    public String getDureeContrat() {
        return dureeContrat;
    }

    /**
     * @param dureeContrat the dureeContrat to set
     */
    public void setDureeContrat(String dureeContrat) {
        this.dureeContrat = dureeContrat;
    }

    /**
     * @return the nombreMois
     */
    public Integer getNombreMois() {
        return nombreMois;
    }

    /**
     * @param nombreMois the nombreMois to set
     */
    public void setNombreMois(Integer nombreMois) {
        this.nombreMois = nombreMois;
    }

}
