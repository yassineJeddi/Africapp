/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.mb.services;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import ma.bservices.services.SalarieService;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

/**
 *
 * @author j.allali
 */
@ManagedBean
@RequestScoped
public class ValidatorCinMb implements Validator {

    @ManagedProperty(value = "#{salarieService}")
    private SalarieService salarieService;

    public SalarieService getSalarieService() {
        return salarieService;
    }

    public void setSalarieService(SalarieService salarieService) {
        this.salarieService = salarieService;
    }

    /**
     * Creates a new instance of validatorMb
     */
    public ValidatorCinMb() {
    }
    private static final String EMAIL_EXISTE_DEJA = "Ce Cin est déjà existant";

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {

        String cin = (String) value;
        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        salarieService = ctx.getBean(SalarieService.class);

        try {
            if (salarieService.getSalarieByCin(cin) != null) {
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
