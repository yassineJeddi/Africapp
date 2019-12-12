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
@Table(name = "PERIODEESSAI")
public class PeriodeEssai implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 3012616165219204225L;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "PERIODEESSAI")
    private String periodeEssai;

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
     * @return the periodeEssai
     */
    public String getPeriodeEssai() {
        return periodeEssai;
    }

    /**
     * @param periodeEssai the periodeEssai to set
     */
    public void setPeriodeEssai(String periodeEssai) {
        this.periodeEssai = periodeEssai;
    }

}
