/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.service.Engin;

import java.io.Serializable;
import java.util.List;
import ma.bservices.beans.Groupe;
import ma.bservices.beans.Permission;
import ma.bservices.tgcc.dao.engin.PermissionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author a.wattah
 */
@Service("permissionService")
public class PermissionServiceImpl implements PermissionService, Serializable {

    @Autowired
    private PermissionDAO permissionDAO;

    public PermissionDAO getPermissionDAO() {
        return permissionDAO;
    }

    public void setPermissionDAO(PermissionDAO permissionDAO) {
        this.permissionDAO = permissionDAO;
    }

    @Override
    public List<Permission> findAll() {
        return permissionDAO.findAll();
    }
    @Override
    public Boolean ajouterPermission(Permission permission){
        permissionDAO.ajouterPermission(permission);
        return true;
    }
    @Override
    public Boolean delete(int  id){
        permissionDAO.delete(id);
        return true;
    }
    @Override
    public Boolean updatePermission(Permission permission){
        permissionDAO.updatePermission(permission);
        return true;
    }

    @Override
    public List<Permission> findByGroup(Groupe g) {
      return permissionDAO.findByGroup(g);
    }
    
    
    
    
   

    

}
