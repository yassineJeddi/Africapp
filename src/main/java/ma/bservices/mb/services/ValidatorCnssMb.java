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
import ma.bservices.constantes.Constantes;
import ma.bservices.services.SalarieService;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

/**
 *
 * @author j.allali
 */
@ManagedBean
@RequestScoped
public class ValidatorCnssMb implements Validator {

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
    public ValidatorCnssMb() {
    }
    private static final String EMAIL_EXISTE_DEJA = "salarie avec N°CNSS déjà existe!!! ";

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {

        String cnss = (String) value;
        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        salarieService = ctx.getBean(SalarieService.class);

        try {
            if (cnss == null || cnss.length() != 9) {
                throw new ValidatorException(new FacesMessage(
                        FacesMessage.SEVERITY_ERROR, "N°CNSS doit contenir 9 chiffres", null));
            } else if (Constantes.validationCnss(cnss) == 0) {
                throw new ValidatorException(new FacesMessage(
                        FacesMessage.SEVERITY_ERROR, "N°CNSS invalide", null));
            } else if (!"".equals(salarieService.checkCnss(cnss))) {
                throw new ValidatorException(new FacesMessage(
                        FacesMessage.SEVERITY_ERROR, EMAIL_EXISTE_DEJA, ""));
            }
        } catch (Exception e) {
            Module.message(3, "Erreur CNSS " + e.getMessage(), "");
            System.out.println("@@ ## validation cnss erreur " + e.getMessage());
        }

    }

}
