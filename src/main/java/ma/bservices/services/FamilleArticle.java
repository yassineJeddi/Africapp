/**
 *
 */
package ma.bservices.services;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author bservices
 *
 */
@Entity
@Table(name = "T012")
public class FamilleArticle implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -4629999812691625395L;

    @Id
    @Column(name = "T012_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "FAMNO")
    private Integer famno;

    @Column(name = "FAM")
    private String fam;

    @Column(name = "LIB")
    private String lib;

    @Column(name = "UP_COMPLEMENT")
    private char upcomplement;

    @Column(name = "DOS")
    private Integer dos;

    @Transient
    private String famille;

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
     * @return the famno
     */
    public Integer getFamno() {
        return famno;
    }

    /**
     * @param famno the famno to set
     */
    public void setFamno(Integer famno) {
        this.famno = famno;
    }

    /**
     * @return the fam
     */
    public String getFam() {
        return fam;
    }

    /**
     * @param fam the fam to set
     */
    public void setFam(String fam) {
        this.fam = fam;
    }

    /**
     * @return the lib
     */
    public String getLib() {
        return lib;
    }

    /**
     * @param lib the lib to set
     */
    public void setLib(String lib) {
        this.lib = lib;
    }

    /**
     * @return the upcomplement
     */
    public char getUpcomplement() {
        return upcomplement;
    }

    /**
     * @param upcomplement the upcomplement to set
     */
    public void setUpcomplement(char upcomplement) {
        this.upcomplement = upcomplement;
    }

    /**
     * @return the dos
     */
    public Integer getDos() {
        return dos;
    }

    /**
     * @param dos the dos to set
     */
    public void setDos(Integer dos) {
        this.dos = dos;
    }

    /**
     * @return the famille
     */
    public String getFamille() {
        return famille;
    }

    /**
     * @param famille the famille to set
     */
    public void setFamille(String famille) {
        this.famille = famille;
    }

}
