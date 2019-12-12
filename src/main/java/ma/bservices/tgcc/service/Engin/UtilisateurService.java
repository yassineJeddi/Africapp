/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.service.Engin;

import java.util.List;
import ma.bservices.beans.Chantier;
import ma.bservices.beans.Groupe;
import ma.bservices.beans.Permission;
import ma.bservices.beans.Utilisateur;

/**
 *
 * @author a.wattah
 */
public interface UtilisateurService {

    public List<Utilisateur> findAll();
    
    public Utilisateur getUsersByLogin(String login);

    public List<Utilisateur> getUserByLNPG(String login, String nom, String prenom, String groupe);

    public void deleteUser(Utilisateur user);
    
    public void addGroupeToUser(Utilisateur user, List<Groupe> grp);  

    public void addUser(Utilisateur utilisateur);
    
    public void addChToUser(Utilisateur user, List<Chantier> ch);

    public Groupe groupeByid(String id);

    public Utilisateur userByLogin(String login);
    
    public List<Chantier> findChantiersByUser(Utilisateur u);
    
    public void updateUser(Utilisateur u);
    
    
    public void addPermissionsToUser(Utilisateur u, List<Permission> listOfPermissions);

    public List<Permission> findPermissionsByUser(Utilisateur u);
    
    public List<Groupe> findGroupsByUser(Utilisateur u);
}
