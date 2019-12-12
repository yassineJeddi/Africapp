package ma.bservices.beans;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "TYPEDOCUMENT")
public class TypeDocument implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -5207375928486430105L;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "TYPEDOCUMENT")
    private String typeDocument;

    @Column(name = "NODEREFIMAGE")
    private String nodeRefImage;

    @Column(name = "OBLIGATOIR")
    private Boolean obligatoir;

    @ManyToMany(mappedBy = "typesDocument")
    private List<Fonction> fonctions;

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
     * @return the typeDocument
     */
    public String getTypeDocument() {
        return typeDocument;
    }

    /**
     * @param typeDocument the typeDocument to set
     */
    public void setTypeDocument(String typeDocument) {
        this.typeDocument = typeDocument;
    }

    /**
     * @return the nodeRefImage
     */
    public String getNodeRefImage() {
        return nodeRefImage;
    }

    /**
     * @param nodeRefImage the nodeRefImage to set
     */
    public void setNodeRefImage(String nodeRefImage) {
        this.nodeRefImage = nodeRefImage;
    }

    public List<Fonction> getFonctions() {
        return fonctions;
    }

    public void setFonctions(List<Fonction> fonctions) {
        this.fonctions = fonctions;
    }

    public Boolean getObligatoir() {
        return obligatoir;
    }

    public void setObligatoir(Boolean obligatoir) {
        this.obligatoir = obligatoir;
    }

    /**
     * methode qui permet de affiche typdeocumen courent
     *
     * @param docs
     * @return
     */
    public Boolean display(List<Document> docs) {

        if (docs != null && docs.size() > 0) {

            for (Document doc : docs) {

                if (doc.getTypeDocument().equals(this)) {
                    return false;
                }

            }
        }
        return true;

    }

    /**
     * methode qui permet afficher cin
     *
     * @param docs
     * @return
     */
    public boolean display_Cin(List<Document> docs) {

        Boolean found = false;

        if (docs != null && docs.size() > 0) {
            for (Document doc_ : docs) {
                if (doc_.getTypeDocument() != null) {
                    if (doc_.getTypeDocument().getTypeDocument().equals(this.typeDocument)) {
                        found = true;
                    }
                }
            }
        }
        return found;

    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        TypeDocument other = (TypeDocument) obj;
        return Objects.equals(this.id, other.getId());
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + Objects.hashCode(this.id);
        return hash;
    }

}
