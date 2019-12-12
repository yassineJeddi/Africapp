/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.service.Mensuel.bean;

import java.util.List;
import ma.bservices.beans.Chantier;
import ma.bservices.tgcc.Entity.Pointage;

/**
 *
 * @author zakaria.dem
 */
public class PointageDataReturn {

    private List<Chantier> l_chantierFromPointage;
    private List<Pointage> l_pointage;
    private Boolean PointedDefenetively;

    public PointageDataReturn(List<Chantier> l_chantierFromPointage, List<Pointage> l_pointage) {
        this.l_chantierFromPointage = l_chantierFromPointage;
        this.l_pointage = l_pointage;
    }

    public PointageDataReturn(List<Chantier> l_chantierFromPointage) {
        this.l_chantierFromPointage = l_chantierFromPointage;
    }

    public PointageDataReturn(List<Chantier> l_chantierFromPointage, List<Pointage> l_pointage, Boolean PointedDefenetively) {
        this.l_chantierFromPointage = l_chantierFromPointage;
        this.l_pointage = l_pointage;
        this.PointedDefenetively = PointedDefenetively;
    }


    
    public Boolean getPointedDefenetively() {
        return PointedDefenetively;
    }

    public void setPointedDefenetively(Boolean PointedDefenetively) {
        this.PointedDefenetively = PointedDefenetively;
    }

    public List<Chantier> getL_chantierFromPointage() {
        return l_chantierFromPointage;
    }

    public void setL_chantierFromPointage(List<Chantier> l_chantierFromPointage) {
        this.l_chantierFromPointage = l_chantierFromPointage;
    }

    public List<Pointage> getL_pointage() {
        return l_pointage;
    }

    public void setL_pointage(List<Pointage> l_pointage) {
        this.l_pointage = l_pointage;
    }

}
