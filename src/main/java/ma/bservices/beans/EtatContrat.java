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
@Table(name = "ETATCONTRAT")
public class EtatContrat implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 8122047955077336924L;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "ETATCONTRAT")
    private String etatContrat;

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
     * @return the etat
     */
    public String getEtatContrat() {
        return etatContrat;
    }

    /**
     * @param etat the etat to set
     */
    public void setEtatContrat(String etatContrat) {
        this.etatContrat = etatContrat;
    }

}
