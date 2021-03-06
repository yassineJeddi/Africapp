package ma.bservices.beans;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "COULEURGILET")
public class CouleurGilet implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -1501146503671693179L;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "COULEURGILET")
    private String couleurGilet;

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
     * @return the couleurGilet
     */
    public String getCouleurGilet() {
        return couleurGilet;
    }

    /**
     * @param couleurGilet the couleurGilet to set
     */
    public void setCouleurGilet(String couleurGilet) {
        this.couleurGilet = couleurGilet;
    }

}
