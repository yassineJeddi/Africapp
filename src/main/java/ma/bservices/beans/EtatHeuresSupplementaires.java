package ma.bservices.beans;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ETATHS")
public class EtatHeuresSupplementaires implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1103206617956070525L;

    public static final String ETAT_ACCEPTE = "Accepté";
    public static final String ETAT_REFUSE = "Refusé";
    public static final String ETAT_EN_COURS = "En cours";

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "ETATHEURESSUPPLEMENTAIRES")
    private String etatHeuresSupplementaires;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEtatHeuresSupplementaires() {
        return etatHeuresSupplementaires;
    }

    public void setEtatHeuresSupplementaires(String etatHeuresSupplementaires) {
        this.etatHeuresSupplementaires = etatHeuresSupplementaires;
    }

}
