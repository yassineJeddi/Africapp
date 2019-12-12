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
@Table(name = "ART")
public class Article implements Serializable {

    private static final long serialVersionUID = 1133760143555785007L;

    @Id
    @Column(name = "ART_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "REF")
    private String codeArticle;

    @Column(name = "DES")
    private String designation;

    @Column(name = "ACHUN")
    private String unite;

    @Column(name = "FAM_0001")
    private String fam1;

    @Column(name = "FAM_0002")
    private String fam2;

    @Column(name = "FAM_0003")
    private String fam3;

    @Column(name = "DOS")
    private Integer dos;

    @Column(name = "UP_NATURE")
    private String natureArticle;

    @Column(name = "UP_TYPOLOGIE")
    private String typo;

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
     * @return the codeArticle
     */
    public String getCodeArticle() {
        return codeArticle;
    }

    /**
     * @param codeArticle the codeArticle to set
     */
    public void setCodeArticle(String codeArticle) {
        this.codeArticle = codeArticle;
    }

    /**
     * @return the designation
     */
    public String getDesignation() {
        return designation;
    }

    /**
     * @param designation the designation to set
     */
    public void setDesignation(String designation) {
        this.designation = designation;
    }

//	/**
//	 * @return the codeProgib
//	 */
//	public String getCodeProgib() {
//		return codeProgib;
//	}
//
//	/**
//	 * @param codeProgib the codeProgib to set
//	 */
//	public void setCodeProgib(String codeProgib) {
//		this.codeProgib = codeProgib;
//	}
    /**
     * @return the unite
     */
    public String getUnite() {
        return unite;
    }

    /**
     * @param unite the unite to set
     */
    public void setUnite(String unite) {
        this.unite = unite;
    }

    /**
     * @return the fam1
     */
    public String getFam1() {
        return fam1;
    }

    /**
     * @param fam1 the fam1 to set
     */
    public void setFam1(String fam1) {
        this.fam1 = fam1;
    }

    /**
     * @return the fam2
     */
    public String getFam2() {
        return fam2;
    }

    /**
     * @param fam2 the fam2 to set
     */
    public void setFam2(String fam2) {
        this.fam2 = fam2;
    }

    /**
     * @return the fam3
     */
    public String getFam3() {
        return fam3;
    }

    /**
     * @param fam3 the fam3 to set
     */
    public void setFam3(String fam3) {
        this.fam3 = fam3;
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

 
    public String getNatureArticle() {
        return natureArticle;
    }

    public void setNatureArticle(String natureArticle) {
        this.natureArticle = natureArticle;
    }

    public String getTypo() {
        return typo;
    }

    public void setTypo(String typo) {
        this.typo = typo;
    }

}
