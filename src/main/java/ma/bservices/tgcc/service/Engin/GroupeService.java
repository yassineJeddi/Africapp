/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.service.Engin;

import java.util.List;
import ma.bservices.beans.Groupe;
import ma.bservices.beans.Permission;

/**
 *
 * @author a.wattah
 */
public interface GroupeService {
    
    public List<Groupe> findAll();
//    public List<String> findAllGroupe();
    public void validerGroupe(Groupe g);
//    public List<String> droitsListe();
    public Boolean addPermissions(Integer g, List<Integer> lpIds);
    public Boolean ajouterGroupe(Groupe groupe);
    public Boolean deleteGroupe(int id);
    public Boolean updateGroupe(Groupe groupe);
    public Boolean haveAdded(String groupe);
    public Groupe findById(Integer id); 
    
    public Groupe findByString(String id); 
    
    public List<Permission> findPermissionsByGroup(Groupe g);
}
