/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.dao.engin;

import java.util.List;
import ma.bservices.beans.Groupe;
import ma.bservices.beans.Permission;

/**
 *
 * @author a.wattah
 */
public interface PermissionDAO {

    public List<Permission> findAll();
    public Permission findById(String id);
    public Boolean ajouterPermission(Permission permission);
    public Boolean delete(int  id);
    public Boolean updatePermission(Permission permission);
    
    public List<Permission> findByGroup(Groupe g);
    /**
     * Utilisé dans le test unitaire car la supp avec ID ne fonctionne pas dans le test
     */
    public Boolean delete(Permission  p);
}
