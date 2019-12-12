/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.dao.engin;

import java.util.List;
import ma.bservices.beans.Chantier;
import ma.bservices.beans.Groupe;
import ma.bservices.beans.Permission;
import ma.bservices.beans.Utilisateur;

/**
 *
 * @author a.wattah
 */
public interface UtilisateurDAO {

    public List<Utilisateur> usersAll();

    public Utilisateur UserByLogin(String login);

    public List<Utilisateur> usersByLNPG(String login, String nom, String prenom, String groupe);

    public void delete(int id);
    
    public void updateUser(Utilisateur u);

    public void addUser(Utilisateur utilisateur);

    public Groupe groupeByid(String id);
    
    public void addGroupeToUser(Utilisateur user);

    public void addGroupe(Groupe group);

    public Utilisateur getUserByLogin(final String login);
    
    public List<Chantier> findChantiersByUser(Utilisateur u);
    
    public List<Permission> findPermissionsByUser(Utilisateur u);
    
    public List<Groupe> findGroupsByUser(Utilisateur u);

    /**
     * Unit Test
     */
    public Boolean delete(Utilisateur id);

}
