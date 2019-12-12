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
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "PERMISSION")
public class Permission implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 6122603065692779340L;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "MODULE")
    private String module;

    @Column(name = "SOUSMODULE")
    private String sousModule;

    @Column(name = "PERMISSION")
    private String permission;
    
    
    
    @Transient
    private boolean modChecked = true;
    
    @Transient
    private boolean ssmodChecked = true;
    
    @ManyToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "permissions")   
    private Set<Utilisateur> utilisateurs;

     @ManyToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "permissions") 
    private List<Groupe> groups;

    @Column(name = "TYPE")
    private String type;
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
     * @return the module
     */
    public String getModule() {
        return module;
    }

    public boolean isModChecked() {
        return modChecked;
    }

    public void setModChecked(boolean modChecked) {
        this.modChecked = modChecked;
    }

    public boolean isSsmodChecked() {
        return ssmodChecked;
    }

    public void setSsmodChecked(boolean ssmodChecked) {
        this.ssmodChecked = ssmodChecked;
    }

  

    
    /**
     * @param module the module to set
     */
    public void setModule(String module) {
        this.module = module;
    }

    /**
     * @return the sousModule
     */
    public String getSousModule() {
        return sousModule;
    }

    /**
     * @param sousModule the sousModule to set
     */
    public void setSousModule(String sousModule) {
        this.sousModule = sousModule;
    }

    /**
     * @return the permission
     */
    public String getPermission() {
        return permission;
    }

    /**
     * @param permission the permission to set
     */
    public void setPermission(String permission) {
        this.permission = permission;
    }

    public List<Groupe> getGroups() {
        return groups;
    }

    public void setGroups(List<Groupe> groups) {
        this.groups = groups;
    }

    public String getType() {
        return type;
    }

    public Set<Utilisateur> getUtilisateurs() {
        return utilisateurs;
    }

    public void setUtilisateurs(Set<Utilisateur> utilisateurs) {
        this.utilisateurs = utilisateurs;
    }
    
    

    public void setType(String type) {
        this.type = type;
    }

}
