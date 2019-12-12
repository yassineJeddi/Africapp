package ma.bservices.beans;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "GROUPE")
public class Groupe implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -1774444055971139114L;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "GROUPE")
    private String groupe;

    @Column(name = "NODEREF")
    private String nodeRef;
    
    @ManyToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "groupes")   
    private Set<Utilisateur> utilisateurs;
    
    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinTable(name = "GROUPE_PERMISSION", joinColumns = {
        @JoinColumn(name = "GROUPE_ID")}, inverseJoinColumns = {
        @JoinColumn(name = "PERMISSION_ID")})
    private List<Permission> permissions;

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
     * @return the groupe
     */
    public String getGroupe() {
        return groupe;
    }

    /**
     * @param groupe the groupe to set
     */
    public void setGroupe(String groupe) {
        this.groupe = groupe;
    }

    /**
     * @return the permissions
     */
    public List<Permission> getPermissions() {
        return permissions;
    }

    /**
     * @param permissions the permissions to set
     */
    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    /**
     * @return the nodeRef
     */
    public String getNodeRef() {
        return nodeRef;
    }

    /**
     * @param nodeRef the nodeRef to set
     */
    public void setNodeRef(String nodeRef) {
        this.nodeRef = nodeRef;
    }

    public Set<Utilisateur> getUtilisateurs() {
        return utilisateurs;
    }

    public void setUtilisateurs(Set<Utilisateur> utilisateurs) {
        this.utilisateurs = utilisateurs;
    }
    
    

}
