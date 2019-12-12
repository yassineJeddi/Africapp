/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.service.Engin;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import ma.bservices.beans.Groupe;
import ma.bservices.beans.Permission;
import ma.bservices.tgcc.dao.engin.GroupeDAO;
import ma.bservices.tgcc.dao.engin.PermissionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author a.wattah
 */
@Service("groupeService")
public class GroupeServiceImpl implements GroupeService, Serializable {

    @Autowired
    private GroupeDAO groupeDAO;

    @Autowired
    private PermissionDAO permissionDAO;

    public GroupeDAO getGroupeDAO() {
        return groupeDAO;
    }

    public void setGroupeDAO(GroupeDAO groupeDAO) {
        this.groupeDAO = groupeDAO;
    }

    public PermissionDAO getPermissionDAO() {
        return permissionDAO;
    }

    public void setPermissionDAO(PermissionDAO permissionDAO) {
        this.permissionDAO = permissionDAO;
    }

    @Override
    public List<Groupe> findAll() {
        return this.groupeDAO.findAll();
    }

    @Override
    public Boolean addPermissions(Integer g, List<Integer> lpIds) {

//        //permissions to add
//        List<Permission> lpToAdd = new LinkedList<Permission>();
//        //permission to delete
//        List<Permission> lpToDel = new LinkedList<Permission>();
//        //current permission in the group     
//        List<Permission> current = _g.getPermissions();
//
//        for (int i = 0; i < _lps.size(); i++) {
//            Boolean i_exist = false;
//            for (int j = 0; j < current.size(); j++) {
//
//                if (_lps.get(i).getId() == current.get(j).getId()) {
//                    i_exist = true;
//                    break;
//                }
//                
//            }
//
//        }
        List<Permission> _lps = new LinkedList<Permission>();
        Groupe _g = groupeDAO.findById(g);
        System.out.println("_______ Etape 1");
        System.out.println("size" + lpIds.size());
        for (int i = 0; i < lpIds.size(); i++) {
            
            String idStr = "" + lpIds.get(i);
            
          
            Permission _p = permissionDAO.findById(idStr);
            System.out.println("_______ Etape 2");
            if (_p != null) {
                _lps.add(_p);
            }
        }

        _g.setPermissions(_lps);

        groupeDAO.addPermissions(_g);
        System.out.println("_______ Etape 3");
        return true;
    }

    @Override
    public void validerGroupe(Groupe g) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Boolean ajouterGroupe(Groupe groupe) {
         groupeDAO.ajouterGroupe(groupe);    
         return true;
    }

    @Override
    public Boolean deleteGroupe(int id) {
    groupeDAO.delete(id);
    return true;
        }

     @Override
    public Boolean updateGroupe(Groupe groupe) {
    groupeDAO.updateGroupe(groupe);
    return true;
        }

    @Override
    public Boolean haveAdded(String groupe) {

        List<Groupe> l = groupeDAO.getGroupeByLibelle(groupe);

        if (l != null && l.size() > 0) {
            return true;
        }
        return false;
    }

    @Override
    public Groupe findById(Integer id) {
        return groupeDAO.findById(id);
    }

    @Override
    public Groupe findByString(String id) {
        return groupeDAO.findByString(id);
    }

    @Override
    public List<Permission> findPermissionsByGroup(Groupe g) {
        return groupeDAO.findPermissionsByGroupe(g);  
    
    
    
    }
}
