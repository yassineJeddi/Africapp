/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.service.Engin;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import ma.bservices.beans.Chantier;
import ma.bservices.beans.Groupe;
import ma.bservices.beans.Permission;
import ma.bservices.beans.Utilisateur;
import ma.bservices.tgcc.dao.engin.UtilisateurDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 *
 * @author a.wattah
 */
@Service("utilisateurService")
public class UtilisateurServiceImpl implements UtilisateurService, Serializable {
    
    @Autowired
    private UtilisateurDAO utilisateurDAO;
    
    @Override
    public List<Utilisateur> findAll() {
         
        return utilisateurDAO.usersAll();
    }
    
    public UtilisateurDAO getUtilisateurDAO() {
        return utilisateurDAO;
    }
    
    public void setUtilisateurDAO(UtilisateurDAO utilisateurDAO) {
        this.utilisateurDAO = utilisateurDAO;
    }
    
    @Override
    public Utilisateur getUsersByLogin(String login) {
        return this.utilisateurDAO.UserByLogin(login);
    }
    
    @Override
    public List<Utilisateur> getUserByLNPG(String login, String nom, String prenom, String groupe) {
        return this.utilisateurDAO.usersByLNPG(login, nom, prenom, groupe);
    }
    
    @Override
    public void deleteUser(Utilisateur user) {
        user.setIsActive(false);
        this.utilisateurDAO.updateUser(user);
    }
    
    @Override
    public void addUser(Utilisateur utilisateur) {
        this.utilisateurDAO.addUser(utilisateur);
    }
    
    @Override
    public Groupe groupeByid(String id) {
        return utilisateurDAO.groupeByid(id);
    }
    
    @Override
    public Utilisateur userByLogin(String login) {
        return utilisateurDAO.UserByLogin(login);
    }
    
    @Override
    public void addGroupeToUser(Utilisateur user, List<Groupe> grp) {        
        user.setGroupes(grp);
        utilisateurDAO.addGroupeToUser(user);
        System.out.println("GROUPS AFTER RESET THAT ARE ADDED : **** " + user.getGroupes());
    }
    
    @Override
    public void addChToUser(Utilisateur user, List<Chantier> ch) {
        
        for (Chantier c : ch) {
            System.out.println("list of chantiers : " + c.getCode());
        }
        Set<Chantier> setOfChantiers = new HashSet(ch);
        //System.out.println("set of chantiers " + setOfChantiers);
        user.setChantiers(setOfChantiers);
        utilisateurDAO.addGroupeToUser(user);
    }
    
    @Override
    public List<Chantier> findChantiersByUser(Utilisateur u) {
        return utilisateurDAO.findChantiersByUser(u);
    }
    
    @Override
    public void addPermissionsToUser(Utilisateur u, List<Permission> listOfPermissions) {
        u.setPermissions(listOfPermissions);
        utilisateurDAO.addGroupeToUser(u);        
        System.out.println("PERMISSIONS FROM SERVICE : " + u.getPermissions());
    }
    
    @Override
    public List<Permission> findPermissionsByUser(Utilisateur u) {
        return utilisateurDAO.findPermissionsByUser(u);
    }
    
    @Override
    public List<Groupe> findGroupsByUser(Utilisateur u) {
        return utilisateurDAO.findGroupsByUser(u);
    }
    
    @Override
    public void updateUser(Utilisateur u) {
        utilisateurDAO.updateUser(u);
    }
    
}
