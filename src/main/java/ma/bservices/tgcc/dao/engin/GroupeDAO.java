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
public interface GroupeDAO {

    public List<Groupe> findAll();

    public Groupe findById(Integer id);
    
    public Groupe findByString(String id);
    
    public void validerGroupe(Groupe group);
    
    public List<Permission> findPermissionsByGroupe(Groupe g);
    
    public void addPermissions(Groupe _g);
    public Boolean ajouterGroupe(Groupe groupe);
    public Boolean delete(int  id);
    /**
     * 
     * @param g
     * @return 
     * delete avec le paramete Groupe je l'aui ajouté lors du test unitaire
     * parce que le delte avec ID ne fonctionne pas
     */
    public Boolean delete(Groupe  g);
    public Boolean updateGroupe(Groupe groupe);
    public List<Groupe> getGroupeByLibelle(String groupe);
}
