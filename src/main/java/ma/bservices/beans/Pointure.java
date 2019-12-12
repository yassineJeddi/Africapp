package ma.bservices.beans;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "POINTURE")
public class Pointure implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 3663636349719899799L;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "POINTURE")
    private String pointure;

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
     * @return the pointure
     */
    public String getPointure() {
        return pointure;
    }

    /**
     * @param pointure the pointure to set
     */
    public void setPointure(String pointure) {
        this.pointure = pointure;
    }

}
