package ma.bservices.beans;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CATEGORIEOUTILTRAVAIL")
public class CategorieOutilTravail implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -5023928675532943542L;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "CATEGORIEOUTILTRAVAIL")
    private String categorieOutilTravail;

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
     * @return the categorieOutilTravail
     */
    public String getCategorieOutilTravail() {
        return categorieOutilTravail;
    }

    /**
     * @param categorieOutilTravail the categorieOutilTravail to set
     */
    public void setCategorieOutilTravail(String categorieOutilTravail) {
        this.categorieOutilTravail = categorieOutilTravail;
    }

}
