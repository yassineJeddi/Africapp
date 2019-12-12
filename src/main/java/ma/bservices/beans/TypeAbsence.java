package ma.bservices.beans;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "TYPEABSENCE")
public class TypeAbsence implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -8202395789846698166L;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "TYPEABSENCE")
    private String typeAbsence;
    
    

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
     * @return the typeAbsence
     */
    public String getTypeAbsence() {
        return typeAbsence;
    }

    /**
     * @param typeAbsence the typeAbsence to set
     */
    public void setTypeAbsence(String typeAbsence) {
        this.typeAbsence = typeAbsence;
    }

    
    
}
