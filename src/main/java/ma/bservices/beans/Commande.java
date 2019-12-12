/**
 *
 */
package ma.bservices.beans;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author root
 *
 */
//@Entity
//@Table(name = "COMMANDE")
public class Commande implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -4306260933502130968L;

//	@Id
//  @Column(name="ID")
//  @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

//	@Column(name = "NUMEROCOMMANDE")
    private String numeroCommande;

//	@Column(name = "DATECOMMANDE")
//	@Temporal(TemporalType.DATE)
    private Date dateCommande;

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
     * @return the numeroCommande
     */
    public String getNumeroCommande() {
        return numeroCommande;
    }

    /**
     * @param numeroCommande the numeroCommande to set
     */
    public void setNumeroCommande(String numeroCommande) {
        this.numeroCommande = numeroCommande;
    }

    /**
     * @return the dateCommande
     */
    public Date getDateCommande() {
        return dateCommande;
    }

    /**
     * @param dateCommande the dateCommande to set
     */
    public void setDateCommande(Date dateCommande) {
        this.dateCommande = dateCommande;
    }

}
