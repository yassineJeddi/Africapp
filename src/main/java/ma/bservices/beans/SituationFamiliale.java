package ma.bservices.beans;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SITUATIONFAMILIALE")
public class SituationFamiliale implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -7992422308894776299L;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "SITUATIONFAMILIALE")
    private String situationFamiliale;

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
     * @return the situationFamiliale
     */
    public String getSituationFamiliale() {
        return situationFamiliale;
    }

    /**
     * @param situationFamiliale the situationFamiliale to set
     */
    public void setSituationFamiliale(String situationFamiliale) {
        this.situationFamiliale = situationFamiliale;
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
