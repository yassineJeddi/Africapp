package ma.bservices.beans;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "MODELCONTRAT")
public class ModelContrat implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -3351389343485791343L;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "INTITULE")
    private String intitule;

    @Column(name = "FICHIERJRXML")
    private String fichierJrxml;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    public String getFichierJrxml() {
        return fichierJrxml;
    }

    public void setFichierJrxml(String fichierJrxml) {
        this.fichierJrxml = fichierJrxml;
    }

}
