/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.dao.engin;

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
public interface PointageEnginDAO {

    public List<PointageEngin> findAll();

    public List<PointageEngin> RechercheEngin(String code, String Designation, String marque, Date date_from, Date date_to, int chantier);

    public Boolean save(PointageEngin pe);
    
    public void saveListPointage(List<PointageEngin> pe);

    public List<PointageEngin> getPointageEnginByDateAndChantierId(int chantier_id, String day, String day_tom);

    public void updateEtat(Engin peUpdate);

    public List<Engin> getCode(String code);

    public void addEnginPanne(Engin poiE);

    public Engin addEngin(String code);

    public Chantier addChantier(String chantier);

    public List<Zone> getZoneList(Integer id_pointage);

    public List<Engin> findAllPanne();

    /**
     * Used in unit test
     */
    public Boolean saveTestUnitaire(PointageEngin pe);

    public Boolean delete(Engin e);

    public Boolean deletePE(int id);

    /**
     * List Pointage Engin Non affecte le Depot
     *
     * @param idEngin
     * @return
     */
    public List<PointageEngin> getPointageEnginByDate_Diff_Depo(int idEngin);

    /**
     * retourne le dernier pointage lié à un chantier C'est pour autoriser de
     * pointer un jour dans une date supérieur à la date du dernier pointage
     *
     * @param chantier_id
     * @return
     */
    public PointageEngin getLastPointageEnginByChantierId(int chantier_id);

    /**
     *
     * @param idChantier
     * @return
     */
    public PointageEngin lastPointageEngin(Integer idChantier, Integer idEngin);
}
