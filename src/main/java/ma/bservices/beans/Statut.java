package ma.bservices.beans;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "STATUT")
public class Statut implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -6728914914415221582L;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "CODENOVAPAIE")
    private String codeNovapaie;

    @Column(name = "STATUT")
    private String statut;

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
     * @return the codeNovapaie
     */
    public String getCodeNovapaie() {
        return codeNovapaie;
    }

    /**
     * @param codeNovapaie the codeNovapaie to set
     */
    public void setCodeNovapaie(String codeNovapaie) {
        this.codeNovapaie = codeNovapaie;
    }

    /**
     * @return the statut
     */
    public String getStatut() {
        return statut;
    }

    /**
     * @param statut the statut to set
     */
    public void setStatut(String statut) {
        this.statut = statut;
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
