/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.mb.Engin;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import ma.bservice.tgcc.Constante.Message;
import ma.bservices.beans.Permission;
import ma.bservices.tgcc.service.Engin.PermissionService;
import org.primefaces.event.RowEditEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

/**
 *
 * @author j.allali
 */
@Component
@ManagedBean(name = "permissionMb")
@ViewScoped
public class PermissionMb {

    @ManagedProperty(value = "#{permissionService}")
    private PermissionService permissionService;
    
    private List<Permission> permissions;

    private Permission permissionToAdd =new Permission();
    
    public PermissionService getPermissionService() {
        return permissionService;
    }

    public void setPermissionService(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    public Permission getPermissionToAdd() {
        return permissionToAdd;
    }

    /*
    Getters Setters
     */
    public void setPermissionToAdd(Permission permissionToAdd) {
        this.permissionToAdd = permissionToAdd;
    }

    /**
     * Creates a new instance of PermissionMb
     */
    @PostConstruct
    public void init() {
        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        permissionService = ctx.getBean(PermissionService.class);
        //find by type
        permissions = permissionService.findAll();

    }
    
    public PermissionMb() {
    }
    
    public void addPermission() {
        Boolean add =permissionService.ajouterPermission(permissionToAdd);
        if(add==true){
            FacesContext context = FacesContext.getCurrentInstance();
            String message =Message.ADD_PERMISSION + permissionToAdd.getPermission();
            context.addMessage(null, new FacesMessage("" + message, ""));
            permissions = permissionService.findAll();
            permissionToAdd=new Permission();
        }
        

    }
     
     public void deletePermission(Permission  p) {
        FacesContext context = FacesContext.getCurrentInstance();
        Boolean delete =permissionService.delete(p.getId());
        if(delete==true){
            context.addMessage(null, new FacesMessage("" + Message.DELETE_PERMISSION_SUCCESS, ""));
        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        permissionService = ctx.getBean(PermissionService.class);
        permissions = permissionService.findAll();
        }
         else {
           context.addMessage(null, new FacesMessage("" + Message.DELETE_PERMISSION_ERROR, "")); 
        }
    }
     public void onRowEdit(RowEditEvent event) {

        Boolean update =permissionService.updatePermission(((Permission) event.getObject()));
         if(update==true){
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("" + Message.UPDATE_PERMISSION_SUCCESS, ""));
            permissions = permissionService.findAll();
        }
    }
     
    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage(Message.UPDATE_PERMISSION_CANCEL, ((Permission) event.getObject()).getPermission());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
}
