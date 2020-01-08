/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.mb.Engin;

import java.io.Serializable;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import ma.bservices.beans.Chantier;
import ma.bservices.beans.Groupe;
import ma.bservices.beans.Permission;
import ma.bservices.beans.Utilisateur;
import ma.bservices.services.AdministrationService;
import ma.bservices.tgcc.service.Engin.ChantierService;
import ma.bservices.tgcc.service.Engin.GroupeService;
import ma.bservices.tgcc.service.Engin.PermissionService;
import ma.bservices.tgcc.service.Engin.UtilisateurService;
import ma.bservices.tgcc.service.Mensuel.MensuelService;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

/**
 *
 * @author air
 */
@Component
@ManagedBean(name = "utilisateur")
@ViewScoped
public class UtilisateurMb implements Serializable {

    @ManagedProperty(value = "#{utilisateurService}")
    private UtilisateurService utilisateurService;

    @ManagedProperty(value = "#{groupeService}")
    private GroupeService groupService;

    @ManagedProperty(value = "#{administrationService}")
    private AdministrationService adminService;

    @ManagedProperty(value = "#{mensuelService}")
    private MensuelService mensuelService;

    @ManagedProperty(value = "#{permissionService}")
    private PermissionService permissionService;

    @ManagedProperty(value = "#{chantierService}")
    private ChantierService chantierService;

    private List<Utilisateur> users;

    private Integer intMensuel;

    //gestion des permissions utilisateur
    private TreeNode userPermTree = new DefaultTreeNode("Root", null);
    private TreeNode[] selectedUserNodes = new TreeNode[]{};

    private List<String> selectedPermissionsIds = new LinkedList<>();

    private List<String> groupIds = new ArrayList<>();

    private List<Chantier> listOfChantiersByUser = new LinkedList<>();

    private List<Chantier> listDiffChan = new LinkedList<>();

    List<TreeNode> nodesList = new ArrayList<>();

    private String oldPwd;
    private String newPwd;
    private String confirmNewPwd;

    private String grpToFilterBy;

    public String getGroupe() {
        return groupe;
    }

    public void setGroupe(String groupe) {
        this.groupe = groupe;
    }

    public String getGroupId() {
        return groupId;
    }

    public List<TreeNode> getNodesList() {
        return nodesList;
    }

    public Integer getIntMensuel() {
        return intMensuel;
    }

    public void setIntMensuel(Integer intMensuel) {
        this.intMensuel = intMensuel;
    }

    public void setNodesList(List<TreeNode> nodesList) {
        this.nodesList = nodesList;
    }

    public GroupeService getGroupService() {
        return groupService;
    }

    public TreeNode getUserPermTree() {
        return userPermTree;
    }

    public void setUserPermTree(TreeNode userPermTree) {
        this.userPermTree = userPermTree;
    }

    public TreeNode[] getSelectedUserNodes() {
        return selectedUserNodes;
    }

    public void setSelectedUserNodes(TreeNode[] selectedUserNodes) {
        this.selectedUserNodes = selectedUserNodes;
    }

    public void setGroupService(GroupeService groupService) {
        this.groupService = groupService;
    }

    public AdministrationService getAdminService() {
        return adminService;
    }

    public List<Chantier> getListDiffChan() {
        return listDiffChan;
    }

    public void setListDiffChan(List<Chantier> listDiffChan) {
        this.listDiffChan = listDiffChan;
    }

    public String getGrpToFilterBy() {
        return grpToFilterBy;
    }

    public void setGrpToFilterBy(String grpToFilterBy) {
        this.grpToFilterBy = grpToFilterBy;
    }

    public String getOldPwd() {
        return oldPwd;
    }

    public void setOldPwd(String oldPwd) {
        this.oldPwd = oldPwd;
    }

    public String getNewPwd() {
        return newPwd;
    }

    public void setNewPwd(String newPwd) {
        this.newPwd = newPwd;
    }

    public String getConfirmNewPwd() {
        return confirmNewPwd;
    }

    public void setConfirmNewPwd(String confirmNewPwd) {
        this.confirmNewPwd = confirmNewPwd;
    }

    public PermissionService getPermissionService() {
        return permissionService;
    }

    public void setPermissionService(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    public void setAdminService(AdministrationService adminService) {
        this.adminService = adminService;
    }

    public List<String> getSelectedPermissionsIds() {
        return selectedPermissionsIds;
    }

    public void setSelectedPermissionsIds(List<String> selectedPermissionsIds) {
        this.selectedPermissionsIds = selectedPermissionsIds;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    private String groupe;
    private String groupId;

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public List<Utilisateur> getSearchEnginList() {
        return searchEnginList;
    }

    public ChantierService getChantierService() {
        return chantierService;
    }

    public void setChantierService(ChantierService chantierService) {
        this.chantierService = chantierService;
    }

    public void setSearchEnginList(List<Utilisateur> searchEnginList) {
        this.searchEnginList = searchEnginList;
    }

    public List<String> getGroupIds() {
        return groupIds;
    }

    public void setGroupIds(List<String> groupIds) {
        this.groupIds = groupIds;
    }

    public List<Chantier> getListOfChantiersByUser() {
        return listOfChantiersByUser;
    }

    public void setListOfChantiersByUser(List<Chantier> listOfChantiersByUser) {
        this.listOfChantiersByUser = listOfChantiersByUser;
    }

    private List<Utilisateur> searchEnginList;
    private Utilisateur userSearch = new Utilisateur();

    public Utilisateur getUserSearch() {
        return userSearch;
    }

    private Utilisateur utilisateurToAdd = new Utilisateur();
    private Utilisateur userCurrent = new Utilisateur();
    private Utilisateur user = new Utilisateur();

    public Utilisateur getUserCurrent() {
        return userCurrent;
    }

    public void setUserCurrent(Utilisateur userCurrent) {
        this.userCurrent = userCurrent;
    }

    public MensuelService getMensuelService() {
        return mensuelService;
    }

    public void setMensuelService(MensuelService mensuelService) {
        this.mensuelService = mensuelService;
    }

    public Utilisateur getUtilisateurToAdd() {
        return utilisateurToAdd;
    }

    public void setUtilisateurToAdd(Utilisateur utilisateurToAdd) {
        this.utilisateurToAdd = utilisateurToAdd;
    }

    public void setUserSearch(Utilisateur userSearch) {
        this.userSearch = userSearch;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    private Utilisateur utilisateur = new Utilisateur();

    public UtilisateurService getUtilisateurService() {
        return utilisateurService;
    }

    public void setUtilisateurService(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    public List<Utilisateur> getUsers() {
        return users;
    }

    public Utilisateur getUser() {
        return user;
    }

    public void setUser(Utilisateur user) {
        this.user = user;
    }

    public void setUsers(List<Utilisateur> users) {
        this.users = users;
    }

    @PostConstruct
    public void init() {
        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());

        utilisateurService = ctx.getBean(UtilisateurService.class);
        groupService = ctx.getBean(GroupeService.class);
        chantierService = ctx.getBean(ChantierService.class);
        mensuelService = ctx.getBean(MensuelService.class);
        findModules();
        user = adminService.getUtilisateur(userCurrent.getId());
        users = utilisateurService.findAll();
    }

    public void onGrpSelectFilter() {
        if (grpToFilterBy.compareToIgnoreCase("tous") == 0) {
            users = utilisateurService.findAll();
        } else {
           // System.out.println("group to filter by : " + grpToFilterBy);
            Groupe grp = groupService.findByString(grpToFilterBy);
          //  List<Utilisateur> listU = adminService.listeUtilisateursGroupe(grp.getId(), "", "", "", "");
            users = adminService.listeUtilisateursGroupe(grp.getId(), "", "", "", "");
            
        }
    }

    public void rechercherUsers() {

        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        utilisateurService = ctx.getBean(UtilisateurService.class);

        this.searchEnginList = utilisateurService.getUserByLNPG(utilisateur.getLogin(), utilisateur.getNom(), utilisateur.getPrenom(), "");
        this.users = searchEnginList;

//        System.out.println("user :" + users.get(0).getIdGroupe().getGroupe());
    }

    public void reinitPwd(Utilisateur user) {
        Utilisateur u = adminService.getUtilisateur(user.getId());
        String password = user.getLogin();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(password.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String hashtext = number.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            u.setPassword(hashtext);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        utilisateurService.updateUser(u);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Le mot de passe a été reinitialisé avec succès!"));
        users = utilisateurService.findAll();

    }

    public void deleteUser(Utilisateur u) {
        Utilisateur user = adminService.getUtilisateur(u.getId());
        this.utilisateurService.deleteUser(user);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Le compte de cet utilisateur est désactivé avec succès"));
        users = utilisateurService.findAll();

    }

    public void listOfChByUser(Utilisateur user) {
        System.out.println("USER : " + user.getLogin());
        listOfChantiersByUser = new LinkedList<>();
        Utilisateur u = adminService.getUtilisateur(user.getId());
        listOfChantiersByUser = utilisateurService.findChantiersByUser(u);

        System.out.println(listOfChantiersByUser);
//        listOfChantiersByUser = 
//        Set<Chantier> setCh = u.getChantiers();
//        List<Chantier> listCh = new LinkedList<>();
//        listCh.addAll(setCh);
//        System.out.println(setCh);
//        System.out.println("list : " + listCh);
//        return listOf;
    }

    public void modifyUserPermissions() {
        try {
            
                System.out.println(" ***************** PERMISSION 1 ****************");
        selectedPermissionsIds.clear();
        for (TreeNode node : selectedUserNodes) {
                System.out.println(" ***************** PERMISSION 2 ****************");
            try {
                System.out.println(" ***************** PERMISSION 3 ****************");
                Permission p = adminService.getPermissionByName(node.getData().toString());
                selectedPermissionsIds.add(p.getPermission());
            } catch (Exception e) {
                System.out.println(" ***************** PERMISSION 4 ****************");
                System.out.println(" ***************** PERMISSION NOT FOUUUND ****************");
            }
        }
                System.out.println(" ***************** PERMISSION 5 ****************");

        List<Permission> listOfPermissions = new LinkedList<>();
        Permission p = null;
        Utilisateur u = adminService.getUtilisateur(utilisateurToAdd.getId());
            System.out.println("u "+u.toString());
        for (String i : selectedPermissionsIds) {
                System.out.println(" ***************** PERMISSION 6 ****************");
            p = adminService.getPermissionByName(i);
            listOfPermissions.add(p);
        }
                System.out.println(" ***************** PERMISSION 7 ****************");
                System.out.println("Utilisateur !!!!!!!!!!!! : "+u.toString());
                System.out.println("listOfPermissions !!!!!!!!!!!! : "+listOfPermissions.toString());
        utilisateurService.addPermissionsToUser(u, listOfPermissions);

                System.out.println(" ***************** PERMISSION 8 ****************");
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "les permissions de l'utilisateur ont été modifiées avec succès."));

        } catch (Exception e) {
                System.out.println(" ***************** PERMISSION 9 ****************");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "", "Erreur de modification les permissions de l'utilisateur."));
            System.out.println("Erreur de modification les permissions de l'utilisateur car "+e.getMessage());
        }
    }

    public void addChantierToUser(Chantier ch) {
        Utilisateur u = adminService.getUtilisateur(utilisateurToAdd.getId());
        List<Chantier> lch = utilisateurService.findChantiersByUser(utilisateurToAdd);
        if (lch == null) {
            lch = new LinkedList<>();
        }

        if (lch.contains(ch)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "", "L'utilisateur est deja affecté au chantier choisi."));

        } else {
            lch.add(ch);
            utilisateurService.addChToUser(u, lch);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "L'utilisateur est affecté au chantier choisi avec succès."));
            prepareChantiersUser(u);
        }

    }

    public void prepareGroups(Utilisateur user) {
        System.out.println("user : " + user.getLogin());
        groupIds.clear();
        List<Groupe> lg = new LinkedList<>();
        lg = utilisateurService.findGroupsByUser(user);
        for (Groupe g : lg) {
            groupIds.add(g.getGroupe());
            System.out.println("THIS GROUP APARTIENT A THIS USER : " + g.getGroupe());
        }

    }

    public void prepareChantiersUser(Utilisateur user) {
        listDiffChan.clear();
        Utilisateur u = adminService.getUtilisateur(user.getId());
        List<Chantier> lch = utilisateurService.findChantiersByUser(u);
        List<Chantier> allChantiers = chantierService.findAll();
        List<Chantier> allChantiers2 = new LinkedList<>();

        for (Chantier ch : allChantiers) {
            if (!lch.contains(ch)) {
                System.out.println("adding : " + ch.getCode());
                allChantiers2.add(ch);
            }
        }

        listDiffChan = allChantiers2;
        // System.out.println(listDiffChan);

    }

    public void preparePermissions(Utilisateur user) {
        selectedPermissionsIds.clear();

        List<Permission> lp = new LinkedList<>();
        List<Permission> temp = new LinkedList<>();
        List<Permission> listOfPerGrp = new LinkedList<>();
        List<Groupe> listGroupe = utilisateurService.findGroupsByUser(user);

//        for (Groupe p : listGroupe) {
//            listOfPerGrp = groupService.findPermissionsByGroup(p);
//            for (Permission pp : listOfPerGrp) {
//                if (!selectedPermissionsIds.contains(pp.getPermission())) {
//                    selectedPermissionsIds.add(pp.getPermission());
//                }
//            }
//        }
        lp = utilisateurService.findPermissionsByUser(user);

        for (Permission l : lp) {
            if (!selectedPermissionsIds.contains(l.getPermission())) {
                selectedPermissionsIds.add(l.getPermission());
            }
        }
        nodesList = new ArrayList<>();
        List<TreeNode> children = userPermTree.getChildren();
        for (TreeNode t : children) {
            List<TreeNode> leaves = t.getChildren();
            for (TreeNode tn : leaves) {
                List<TreeNode> leavesX = tn.getChildren();
                for (TreeNode leaf : leavesX) {
                    if (selectedPermissionsIds.contains(leaf.getData().toString())) {
                        // System.out.println("NODE SELECTED FROM GROUP CHANGE : " + leaf.getData().toString());
                        // leaf.setPartialSelected(true);
                        leaf.setSelected(true);
                        nodesList.add(leaf);
                    } else {
                        leaf.setSelected(false);
                    }
                }
            }
        }
        selectedUserNodes = new TreeNode[nodesList.size()];
        for (int i = 0; i < nodesList.size(); i++) {
            selectedUserNodes[i] = nodesList.get(i);
        }
    }

    public void editUser() {
        userCurrent.setMensuel(mensuelService.getById(intMensuel));
        utilisateurService.updateUser(userCurrent);
        users = utilisateurService.findAll();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "L'utilisateur est modifié avec succès"));

    }

    public void addUser() {

        utilisateurService.addUser(utilisateurToAdd);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "L'utilisateur est ajouté avec succès."));

    }

    public void addGrpToUser() {
        Groupe g;
        List<Groupe> listOfGroups = new LinkedList<>();
        List<Permission> listOfPermissionsByGroup = new LinkedList<>();
        Utilisateur u = adminService.getUtilisateur(utilisateurToAdd.getId());
        List<Permission> listExist = new LinkedList<>();
        List<String> listOfPerString = new LinkedList<>();

        List<Permission> temp = new LinkedList<>();

        for (String s : groupIds) {
            g = groupService.findByString(s);
            listOfGroups.add(g);
        }

        for (Groupe gg : listOfGroups) {

            System.out.println("GROUP : " + gg.getGroupe());
            temp = permissionService.findByGroup(gg);

            for (Permission p : temp) {
                if (!listOfPerString.contains(p.getPermission())) {
                    listExist.add(p);
                    listOfPerString.add(p.getPermission());
                }
            }

        }

        for (Permission p : listExist) {
            System.out.println("PERMISSION TEMP: " + p.getPermission());
        }

//            temp.clear();
//            temp = permissionService.findByGroup(g);            
//            for(Permission p : temp){
//            System.out.println("PERMISSION TEMP : " + p.getPermission());
//            if(!listOfPerString.contains(p.getPermission()))
//                listOfPermissionsByGroup.add(p);
//            }
        utilisateurService.addPermissionsToUser(u, listExist);
        utilisateurService.addGroupeToUser(u, listOfGroups);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "L'utilisateur est ajouté aux groupes choisis avec succès."));
    }

    public void removeChantierFromUser(Chantier ch) {
        Utilisateur u = adminService.getUtilisateur(utilisateurToAdd.getId());
        List<Chantier> lch = new LinkedList<>();
        lch = utilisateurService.findChantiersByUser(utilisateurToAdd);
        lch.remove(ch);
        utilisateurService.addChToUser(u, lch);
        listOfChantiersByUser = utilisateurService.findChantiersByUser(utilisateurToAdd);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "L'utilisateur est desaffecté avec succès."));

    }

    public void addNewUser() {
        utilisateurToAdd = new Utilisateur();
    }

    public String extractGroups(Utilisateur user) {
        /// System.out.println("user : " + user.getLogin());
        String groups = "";
        List<Groupe> lg = new LinkedList<>();
        lg = utilisateurService.findGroupsByUser(user);
        System.out.println("GROUPS BY USER : ");

        for (Groupe g : lg) {
            if (groups.compareTo("") == 0) {
                groups += g.getGroupe();
            } else {
                groups = groups + "," + g.getGroupe();
            }
        }

        System.out.println(groups);
        return groups;
    }

    public void addUutilisateur() {

        utilisateurToAdd.setGroupes(new LinkedList<Groupe>());
        utilisateurToAdd.setPermissions(new LinkedList<Permission>());
        utilisateurToAdd.setChantiers(new HashSet<Chantier>());
        utilisateurToAdd.setIsActive(true);
        if (intMensuel != null) {
            System.out.println("Mensuel lié: " + intMensuel);
            utilisateurToAdd.setMensuel(mensuelService.getById(intMensuel));
        }
        String password = utilisateurToAdd.getPassword();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(password.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String hashtext = number.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            utilisateurToAdd.setPassword(hashtext);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        Utilisateur u = adminService.getUtilisateurByLogin(utilisateurToAdd.getLogin());
        if (u != null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "", "ce login existe deja"));

        } else {
            this.utilisateurService.addUser(utilisateurToAdd);
            utilisateurToAdd = new Utilisateur();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "L'utilisateur est ajouté avec succès."));
        }
        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        utilisateurService = ctx.getBean(UtilisateurService.class);
        users = utilisateurService.findAll();
    }

    public void changePwd(Utilisateur user) {
        String theOld = "";
        System.out.println("connected user : " + user.getNom());
        String oldPassword = user.getPassword();

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(oldPwd.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String hashtext = number.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            theOld = hashtext;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        if (theOld.compareTo(oldPassword) == 0) {
            System.out.println("ANCIEN VALID");
            if (newPwd.compareTo(confirmNewPwd) == 0) {
                try {
                    MessageDigest md = MessageDigest.getInstance("MD5");
                    byte[] messageDigest = md.digest(newPwd.getBytes());
                    BigInteger number = new BigInteger(1, messageDigest);
                    String hashtext = number.toString(16);
                    while (hashtext.length() < 32) {
                        hashtext = "0" + hashtext;
                    }
                    user.setPassword(hashtext);
                } catch (NoSuchAlgorithmException e) {
                    throw new RuntimeException(e);
                }

                utilisateurService.updateUser(user);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "votre mot de passe a été changé"));

            } else {
                System.out.println("new NOT ALIGN");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "", "veuillez confirmer le nouveau mot de passe"));

            }
        } else {
            System.out.println("ANCIEN NOT VALID");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "", "veuillez entrer votre mot de passe pour procéder"));

        }

    }

    public List<Permission> findSousModulesByModule(String module) {
        return adminService.listeSousModules(module);
    }

    public List<Permission> findPermissionsBySousModule(String ss_module) {
        return adminService.listePermissions(ss_module);
    }

    //regroupe les permissions selon modules et sous modules
    public void findModules() {

        List<Permission> modules = adminService.listeModules();
        List<Permission> ssmods;
        List<Permission> perms;

        for (Permission p : modules) {
            TreeNode mod = new DefaultTreeNode(p.getModule(), userPermTree);
            ssmods = findSousModulesByModule(p.getModule());
            mod.setExpanded(true);
            mod.isExpanded();
            for (Permission pp : ssmods) {
                TreeNode ssmod = new DefaultTreeNode(pp.getSousModule(), mod);
                ssmod.setExpanded(true);
                ssmod.isExpanded();
                perms = findPermissionsBySousModule(pp.getSousModule());
                for (Permission ppp : perms) {
                    ssmod.getChildren().add(new DefaultTreeNode(ppp.getPermission(), ssmod));

                }
            }
        }

    }

}
