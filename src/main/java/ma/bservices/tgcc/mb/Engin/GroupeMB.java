/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.mb.Engin;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import ma.bservice.tgcc.Constante.Message;
import ma.bservices.beans.Groupe;
import ma.bservices.beans.Permission;
import ma.bservices.beans.Utilisateur;
//import ma.bservices.mb.services.ModulesPermService;
import ma.bservices.services.AdministrationService;
import ma.bservices.tgcc.service.Engin.GroupeService;
import ma.bservices.tgcc.service.Engin.PermissionService;
import ma.bservices.tgcc.service.Engin.UtilisateurService;
//import ma.bservices.tgcc.utilitaire.ModulesPerm;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

/**
 *
 * @author a.wattah
 */
@Component
@ManagedBean(name = "groupe")
@ViewScoped
public class GroupeMB implements Serializable {

    @ManagedProperty(value = "#{administrationService}")
    private AdministrationService adminService;

    private TreeNode permTree = new DefaultTreeNode("Root", null);
    List<TreeNode> nodesList = new ArrayList<>();

    private Map<String, Boolean> moduleCheking = new HashMap<>();

    public AdministrationService getAdminService() {
        return adminService;
    }

    public void setAdminService(AdministrationService adminService) {
        this.adminService = adminService;
    }

    public GroupeService getGroupeService() {
        return groupeService;
    }

    public void setGroupeService(GroupeService groupeService) {
        this.groupeService = groupeService;
    }

    @ManagedProperty(value = "#{groupeService}")
    private GroupeService groupeService;

    @ManagedProperty(value = "#{utilisateurService}")
    private UtilisateurService utilisateurService;

    @ManagedProperty(value = "#{permissionService}")
    private PermissionService permissionService;

    private List<Permission> listMods = new LinkedList<>();

    private List<TreeNode> listOfSelectedNodes = new LinkedList<>();

    private boolean modChecked, ssModChecked;

    private List<Groupe> groupes;

    private Map<String, List<String>> mapingModules = new HashMap<>();

    public List<TreeNode> getNodesList() {
        return nodesList;
    }

    public void setNodesList(List<TreeNode> nodesList) {
        this.nodesList = nodesList;
    }

    private Map<String, Map<String, List<String>>> ppm = new HashMap<>();

    Set<Map.Entry<String, Map<String, List<String>>>> pm;

    public UtilisateurService getUtilisateurService() {
        return utilisateurService;
    }

    public void setUtilisateurService(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    private List<Permission> permissions;

    private TreeNode[] selectedNodes = new TreeNode[]{};

    public TreeNode getPermTree() {
        return permTree;
    }

    public void setPermTree(TreeNode permTree) {
        this.permTree = permTree;
    }

    private Groupe groupeToAdd = new Groupe();

    private Boolean haveAdded;
    /**
     * elle stocke la liste des permission Ã  affecter Ã  un groupe
     */
    private List<Integer> selectedPermissionsIds = new LinkedList<>();

    public TreeNode[] getSelectedNodes() {
        return selectedNodes;
    }

    public List<TreeNode> getListOfSelectedNodes() {
        return listOfSelectedNodes;
    }

    public void setListOfSelectedNodes(List<TreeNode> listOfSelectedNodes) {
        this.listOfSelectedNodes = listOfSelectedNodes;
    }

    public void setSelectedNodes(TreeNode[] selectedNodes) {
        this.selectedNodes = selectedNodes;
    }

    /**
     * stock l'id du groupe dont on va affecter les permission selectionÃ©es
     */
    private Integer selectedGroup;

    public Boolean getHaveAdded() {
        return haveAdded;
    }

//    ModulesPerm mp = new ModulesPerm();
    public void setHaveAdded(Boolean haveAdded) {
        this.haveAdded = haveAdded;
    }

    public Groupe getGroupeToAdd() {
        return groupeToAdd;
    }

    public Map<String, Boolean> getModuleCheking() {
        return moduleCheking;
    }

    public void setModuleCheking(Map<String, Boolean> moduleCheking) {
        this.moduleCheking = moduleCheking;
    }

    public List<Permission> getListMods() {
        return listMods;
    }

    public void setListMods(List<Permission> listMods) {
        this.listMods = listMods;
    }

    public Set<Map.Entry<String, Map<String, List<String>>>> getPm() {
        return pm;
    }

    public void setPm(Set<Map.Entry<String, Map<String, List<String>>>> pm) {
        this.pm = pm;
    }

    public Map<String, List<String>> getMapingModules() {
        return mapingModules;
    }

    public void setMapingModules(Map<String, List<String>> mapingModules) {
        this.mapingModules = mapingModules;
    }

    public boolean isModChecked() {
        return modChecked;
    }

    public void setModChecked(boolean modChecked) {
        this.modChecked = modChecked;
    }

    public boolean isSsModChecked() {
        return ssModChecked;
    }

    public void setSsModChecked(boolean ssModChecked) {
        this.ssModChecked = ssModChecked;
    }

    //getters and setters
    public void setGroupeToAdd(Groupe groupeToAdd) {
        this.groupeToAdd = groupeToAdd;
    }

    public PermissionService getPermissionService() {
        return permissionService;
    }

//    public ModulesPerm getMp() {
//        return mp;
//    }
//
//    public void setMp(ModulesPerm mp) {
//        this.mp = mp;
//    }
    public void setPermissionService(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public Map<String, Map<String, List<String>>> getPpm() {
        return ppm;
    }

    public void setPpm(Map<String, Map<String, List<String>>> ppm) {
        this.ppm = ppm;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    public List<Groupe> getGroupes() {
        return groupes;
    }

    public void setGroupes(List<Groupe> groupes) {
        this.groupes = groupes;
    }

    public List<Integer> getSelectedPermissionsIds() {
        return selectedPermissionsIds;
    }

    public void setSelectedPermissionsIds(List<Integer> selectedPermissionsIds) {
        this.selectedPermissionsIds = selectedPermissionsIds;
    }

    public Integer getSelectedGroup() {
        return selectedGroup;
    }

    public void setSelectedGroup(Integer selectedGroup) {
        this.selectedGroup = selectedGroup;
    }

    //end getters and setters
    @PostConstruct
    public void init() {
        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        groupeService = ctx.getBean(GroupeService.class);
        permissionService = ctx.getBean(PermissionService.class);

        groupes = groupeService.findAll();
        findModules();
        mapingModules.put("admin", new LinkedList<String>());
        permissions = permissionService.findAll();

    }

    public void displaySelectedMultiple() {

        selectedPermissionsIds.clear();

        for (TreeNode node : selectedNodes) {
          //  System.out.println("PERMISSION TO SEARCH : " + node.getData().toString());
            try {
                Permission p = adminService.getPermissionByName(node.getData().toString());
                
                selectedPermissionsIds.add(p.getId());
            } catch (Exception e) {
                System.out.println(" ***************** PERMISSION NOT FOUUUND ****************");
            }
        }
    
    }

    public void checkChanged() {
        System.out.println("******************** HELOOOOOO CHECK CHAAAAAAAAAANGEEEEED *************");
    }

 

    public void deleteGroupe() throws IOException, InterruptedException {
        groupeService.deleteGroupe(selectedGroup);
        groupes = groupeService.findAll();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "le groupe a été supprimé avec succès."));

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
            TreeNode mod = new DefaultTreeNode(p.getModule(), permTree);
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

// charger les permissions par groupe
    public void onGroupChange() {

        nodesList = new ArrayList<>();
        List<String> perms = new LinkedList<>();
        selectedPermissionsIds.clear();
        System.out.println("selected group : " + selectedGroup);
        Groupe g = adminService.getGroupe(selectedGroup);
        List<Permission> lp = groupeService.findPermissionsByGroup(g);

        for (Permission p : lp) {
            perms.add(p.getPermission());
        }

        List<TreeNode> children = permTree.getChildren();
        for (TreeNode t : children) {
            List<TreeNode> leaves = t.getChildren();
            for (TreeNode tn : leaves) {
                List<TreeNode> leavesX = tn.getChildren();
                for (TreeNode leaf : leavesX) {
                    if (perms.contains(leaf.getData().toString())) {
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
        selectedNodes = new TreeNode[nodesList.size()];
        for (int i = 0; i < nodesList.size(); i++) {
            selectedNodes[i] = nodesList.get(i);
        }   

    }


    //valider l'ajout des permissions au groupe
    public void valider() throws IOException {
        displaySelectedMultiple();
        System.out.println("sleceted GPR" + selectedGroup);
        System.out.println("valider size " + selectedPermissionsIds.size());

        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        groupeService = ctx.getBean(GroupeService.class);

        try {
            groupeService.addPermissions(this.selectedGroup, selectedPermissionsIds);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO : ", "les permissions du groupe ont été modifiées avec succès!"));
        } catch (Exception e) {
            System.out.println("Erreur d'affectation les roles au group car "+e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Erreur : ", "Veuillez essayer de nouveau!"));
        }

    }

    public void addGroupe() {

        if (groupeService.findById(groupeToAdd.getId()) != null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "", "le groupe existe deja"));

        } else {
            haveAdded = groupeService.ajouterGroupe(groupeToAdd);
            if (haveAdded == true) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO :", "Le nouveau groupe a été ajouté avec succès!"));

                groupes = groupeService.findAll();
                groupeToAdd = new Groupe();
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "WARNING :", "erreur d'ajout du nouveau groupe!"));
            }
        }
    }

    public void deleteGroupe(Groupe g) {

        Boolean delete = groupeService.deleteGroupe(g.getId());
        if (delete == true) {

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO :", "le groupe a été supprimé avec succès! "));

            groupes = groupeService.findAll();
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "WARNING :", "erreur de suppression du groupe!"));

        }
    }



    public void onRowEdit(RowEditEvent event) {

        Boolean update = groupeService.updateGroupe(((Groupe) event.getObject()));
        if (update == true) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("" + Message.UPDATE_GROUPE, ""));
            groupes = groupeService.findAll();
        }
    }

    public void nodeClicked() {
        System.out.println("HELLOOOOO NOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOODE");
    }

    public void onRowCancel(RowEditEvent event) {
//        FacesMessage msg = new FacesMessage("Edit Cancelled", ((Groupe) event.getObject()).getGroupe());
//        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
}
