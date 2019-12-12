package ma.bservices.beans;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TAILLEHABILLEMENT")
public class TailleHabillement implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 7419391584403839502L;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "TAILLEHABILLEMENT")
    private String tailleHabillement;

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
     * @return the tailleHabillement
     */
    public String getTailleHabillement() {
        return tailleHabillement;
    }

    /**
     * @param tailleHabillement the tailleHabillement to set
     */
    public void setTailleHabillement(String tailleHabillement) {
        this.tailleHabillement = tailleHabillement;
    }

}
