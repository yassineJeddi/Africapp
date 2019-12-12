package ma.bservices.beans;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "COULEURCASQUE")
public class CouleurCasque implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -1501146503671693179L;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "COULEURCASQUE")
    private String couleurCasque;

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
    public String getCouleurCasque() {
        return couleurCasque;
    }

    /**
     * @param couleurGilet the couleurGilet to set
     */
    public void setCouleurCasque(String couleurCasque) {
        this.couleurCasque = couleurCasque;
    }

}
