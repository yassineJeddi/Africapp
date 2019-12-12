/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.mb.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import ma.bservices.beans.Chantier;
import ma.bservices.beans.Salarie;
import ma.bservices.services.ChantierService;
import ma.bservices.tgcc.Entity.CarteGasoil;
import ma.bservices.tgcc.service.Mensuel.CarteGasoilService;
import ma.bservices.tgcc.service.Mensuel.DocumentService;
import ma.bservices.tgcc.service.Mensuel.MensuelService;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

/**
 *
 * @author a.wattah
 */
public class CarteGasoilMb implements Serializable {

    public CarteGasoilMb() {
    }

  
}
