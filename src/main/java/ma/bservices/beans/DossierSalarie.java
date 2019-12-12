package ma.bservices.beans;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "DOSSIERSALARIE")
public class DossierSalarie implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -8764534643048589992L;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "NODEREFPHOTO")
    private String nodeRefPhoto;

    @Column(name = "NODEREFDOSSIER")
    private String nodeRefDossier;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNodeRefPhoto() {
        return nodeRefPhoto;
    }

    public void setNodeRefPhoto(String nodeRefPhoto) {
        this.nodeRefPhoto = nodeRefPhoto;
    }

    public String getNodeRefDossier() {
        return nodeRefDossier;
    }

    public void setNodeRefDossier(String nodeRefDossier) {
        this.nodeRefDossier = nodeRefDossier;
    }

}
