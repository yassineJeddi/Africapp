/**
 *
 */
package ma.bservices.beans;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author root
 *
 */
@Entity
@Table(name = "PPTSOR")
public class Motif implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "PPTSOR_ID")
    //@GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    @Column(name = "SORTIECOD", columnDefinition = "char(5)")
    private String motifCode;

    @Column(name = "LIB", columnDefinition = "char(40)")
    private String motif;

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
     * @return the motifCode
     */
    public String getMotifCode() {
        return motifCode;
    }

    /**
     * @param motifCode the motifCode to set
     */
    public void setMotifCode(String motifCode) {
        this.motifCode = motifCode;
    }

    /**
     * @return the motif
     */
    public String getMotif() {
        return motif;
    }

    /**
     * @param motif the motif to set
     */
    public void setMotif(String motif) {
        this.motif = motif;
    }

}
