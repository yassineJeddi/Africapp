/**
 *
 */
package ma.bservices.beans;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author root
 *
 */
@Entity
@Table(name = "TYPECONTRAT")
public class TypeContrat implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -7847841122544366548L;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "TYPECONTRAT")
    private String typeContrat;

    @Column(name = "NODEREFTYPE")
    private String nodeRefType;

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
     * @return the typeContrat
     */
    public String getTypeContrat() {
        return typeContrat;
    }

    /**
     * @param typeContrat the typeContrat to set
     */
    public void setTypeContrat(String typeContrat) {
        this.typeContrat = typeContrat;
    }

    /**
     * @return the nodeRefType
     */
    public String getNodeRefType() {
        return nodeRefType;
    }

    /**
     * @param nodeRefType the nodeRefType to set
     */
    public void setNodeRefType(String nodeRefType) {
        this.nodeRefType = nodeRefType;
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
