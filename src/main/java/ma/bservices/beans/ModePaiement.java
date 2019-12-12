package ma.bservices.beans;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "MODEPAIEMENT")
public class ModePaiement implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -5411977809068345868L;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "MODEPAIEMENT")
    private String modePaiement;

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
     * @return the modePaiement
     */
    public String getModePaiement() {
        return modePaiement;
    }

    /**
     * @param modePaiement the modePaiement to set
     */
    public void setModePaiement(String modePaiement) {
        this.modePaiement = modePaiement;
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
