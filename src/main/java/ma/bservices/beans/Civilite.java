package ma.bservices.beans;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CIVILITE")
public class Civilite implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -8202395789846698166L;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "CIVILITE")
    private String civilite;

    @Column(name = "CODEDIVA")
    private String codeDiva;

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
     * @return the civilite
     */
    public String getCivilite() {
        return civilite;
    }

    /**
     * @param civilite the civilite to set
     */
    public void setCivilite(String civilite) {
        this.civilite = civilite;
    }

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

}
