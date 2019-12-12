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
@Table(name = "PREAVIS")
public class Preavis implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 5188387574989148627L;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "PREAVIS")
    private String preavis;

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
     * @return the preavis
     */
    public String getPreavis() {
        return preavis;
    }

    /**
     * @param preavis the preavis to set
     */
    public void setPreavis(String preavis) {
        this.preavis = preavis;
    }

}
