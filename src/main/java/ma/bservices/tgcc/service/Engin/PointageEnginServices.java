/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.service.Engin;

import java.util.Date;
import java.util.List;
import ma.bservices.beans.Chantier;
import ma.bservices.beans.Zone;
import ma.bservices.tgcc.Entity.Engin;
import ma.bservices.tgcc.Entity.PointageEngin;

/**
 *
 * @author j.allali
 */
public interface PointageEnginServices {

    public List<PointageEngin> findAll();

    public List<PointageEngin> RechercheEngin(String code, String Designation, String marque, Date date_from, Date date_to, int chantier);

    public void pointageEnginAction(List<PointageEngin> pe, Date datePointage);
    public void saveListPointage(List<PointageEngin> pe);

    public Boolean havePointed(int chantier_id, Date day);

    public void updateEtat(Engin peUpdate);

    public List<Engin> findAllPanne();

    public List<Zone> findAllZones(Integer id_pointage);

    public List<Engin> getCode(String code);

    public void addEnginPanne(Engin poiE);

    public Engin addEngin(String code);

    public Chantier addChantier(String chantier);

    public List<PointageEngin> getPointageEnginByDate_Diff_Depo(int idEngin);

    public void addEngin_PAnne(Engin peUpdate);

    /**
     * retourne la date du dernier pointage au niveau d'un engin On peut pointer
     * les engins d'un chantier tant qu'on n'est pas arriver à la date de son
     * dernier pointage
     *
     * @param chantier_id
     * @return
     */
    public Date lastDayPointed(int chantier_id);

    public PointageEngin lastPointageEngin(Integer idChantier, Integer idEngin);
}
