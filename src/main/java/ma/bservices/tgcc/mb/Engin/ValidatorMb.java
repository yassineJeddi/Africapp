/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.mb.Engin;


import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import ma.bservices.tgcc.service.Engin.EnginService;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

/**
 *
 * @author j.allali
 */
@ManagedBean
@RequestScoped
public class ValidatorMb implements Validator{

    @ManagedProperty(value = "#{enginService}")
    private EnginService enginSerive;

    public EnginService getEnginSerive() {
        return enginSerive;
    }

    public void setEnginSerive(EnginService enginSerive) {
        this.enginSerive = enginSerive;
    }
    
    
    
    /**
     * Creates a new instance of validatorMb
     */
    public ValidatorMb() {
    }
    private static final String EMAIL_EXISTE_DEJA = "Ce code est déjà utilisé";
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        
        String code = (String) value;
        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        enginSerive = ctx.getBean(EnginService.class);
       
        try {
            if (enginSerive.findOneByCode(code)!=null) {
                throw new ValidatorException(new FacesMessage(
                        FacesMessage.SEVERITY_ERROR, EMAIL_EXISTE_DEJA, null));
            }

        } catch (Exception e) {
            FacesMessage message = new FacesMessage(
                    FacesMessage.SEVERITY_ERROR, e.getMessage(), null);
            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.addMessage(component.getClientId(facesContext),
                    message);
        }
        
        
    }
    
}
