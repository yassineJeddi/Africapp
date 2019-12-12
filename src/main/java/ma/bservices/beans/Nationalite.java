package ma.bservices.beans;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "NATIONALITE")
public class Nationalite implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 4053017573060716585L;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "NATIONALITE")
    private String nationalite;

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
     * @return the nationalite
     */
    public String getNationalite() {
        return nationalite;
    }

    /**
     * @param nationalite the nationalite to set
     */
    public void setNationalite(String nationalite) {
        this.nationalite = nationalite;
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
