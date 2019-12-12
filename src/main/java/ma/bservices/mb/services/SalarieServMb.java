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
import ma.bservices.beans.Chantier;
import ma.bservices.beans.Salarie;
import ma.bservices.services.SalarieServiceIn;
import org.springframework.stereotype.Component;

/**
 *
 * @author Mahdi
 */
@Component("salarieServMb")
@ManagedBean(name = "salarieServMb")
@ApplicationScoped
public class SalarieServMb implements Serializable {

    @ManagedProperty(value = "#{salarieServiceIn}")
    private SalarieServiceIn salarieServiceIn;
    private List<Salarie> listCE = new LinkedList<>();
    private Integer nbrCE;

    @PostConstruct
    public void init() {
        salarieServiceIn = Module.ctx.getBean(SalarieServiceIn.class);
        listCE.addAll(salarieServiceIn.getSalarieChefEquipe());
        listCE.addAll(salarieServiceIn.getMensuelChefEquipe());
        nbrCE = listCE.size();
    }

//    /**
//     * liste des Chef d'equipe par Chantier
//     *
//     * @param idChantier identifiant chantier
//     * @return liste des Chef d'équipe
//     */
//    public List<Salarie> ChefEquipeParChantier(Integer idChantier) {
//        List<Salarie> result = new LinkedList<>();
//        for (Salarie s : listCE) {
//            for (Chantier c : s.getChantiers()) {
//                if (c.getId().equals(idChantier)) {
//                    result.add(s);
//                    break;
//                }
//            }
//        }
//        return result;
//    }

    public List<Salarie> getListCE() {
        return listCE;
    }

    public void setListCE(List<Salarie> listCE) {
        this.listCE = listCE;
    }

    public Integer getNbrCE() {
        return nbrCE;
    }

    public void setNbrCE(Integer nbrCE) {
        this.nbrCE = nbrCE;
    }

    public SalarieServiceIn getSalarieServiceIn() {
        return salarieServiceIn;
    }

    public void setSalarieServiceIn(SalarieServiceIn salarieServiceIn) {
        this.salarieServiceIn = salarieServiceIn;
    }

}
