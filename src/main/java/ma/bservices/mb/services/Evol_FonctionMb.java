/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.mb.services;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import ma.bservices.beans.Fonction;
import ma.bservices.services.ParametrageService;
import org.springframework.stereotype.Component;

/**
 *
 * @author Mahdi
 */
@Component
@ManagedBean(name = "evol_fonctionMb")
@ApplicationScoped
public class Evol_FonctionMb implements Serializable {

    @ManagedProperty(value = "#{parametrageService}")
    private ParametrageService parametrageService;
    private List<Fonction> fonctions;
    private String idStatut;

    public String getIdStatut() {
        return idStatut;
    }

    public void setIdStatut(String idStatut) {
        this.idStatut = idStatut;
    }

    public ParametrageService getParametrageService() {
        return parametrageService;
    }

    public void setParametrageService(ParametrageService parametrageService) {
        this.parametrageService = parametrageService;
    }

    public List<Fonction> getFonctions() {
        return fonctions;
    }

    public void setFonctions(List<Fonction> fonctions) {
        this.fonctions = fonctions;
    }

    public Evol_FonctionMb() {
    }

    @PostConstruct
    public void init() {
        parametrageService = Module.ctx.getBean(ParametrageService.class);
        //fonctions = parametrageService.listeFonctions(0, Integer.parseInt(parametrageService.nombreFonctions("").toString()), "");
        fonctions= parametrageService.listeAllFonctions();
        //System.out.println("====================9999999> fonctions : "+fonctions);
    }

    public void foncByStatut() {
        System.out.println("Statut Id " + idStatut + " fonction By Statut");

        if (idStatut != null) {
            fonctions = parametrageService.listeFonctionsParStatut(0, Integer.parseInt(parametrageService.nombreFonctions("").toString()), "", idStatut, " dos=700 OR dos=999 ", 3);
        } else {
            fonctions = parametrageService.listeFonctions(
                    0, Integer.parseInt(parametrageService.nombreFonctions("").toString()), ""
            );
        }
    }

    public List<Fonction> foncByStatut(String codeStatut) {
        System.out.println("code Statut" + codeStatut);
        List<Fonction> result = new LinkedList<>();
        for (Fonction f : fonctions) {
            if (f.getCodeDiva() != null && f.getCodeDiva().startsWith(codeStatut)) {
                result.add(f);
            }
        }
        System.out.println("resultat " + result.size());
        return result;
    }
}
