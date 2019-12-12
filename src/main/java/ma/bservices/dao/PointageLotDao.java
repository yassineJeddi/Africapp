/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.dao;

import java.util.Date;
import java.util.List;
import ma.bservices.beans.Chantier;
import ma.bservices.beans.PointageLot;
import ma.bservices.beans.Salarie;
import ma.bservices.beans.Zone;

/**
 *
 * @author Mahdi
 */
public interface PointageLotDao {

    public List<PointageLot> getAll();

    /**
     * recuperer pointage d'un salarié dans une date données
     *
     * @param datepointage de pointage
     * @param salarie salarie recherche
     * @param zone
     * @return
     */
    public List<PointageLot> getByX(Date datepointage, Salarie salarie, Zone zone);

    /**
     * effectuer un pointage par lot
     *
     * @param p l'objet a pointe
     * @return true si pointage effectuée sinon false
     */
    public boolean pointer(PointageLot p);

    public boolean updatePointage(PointageLot pl);

    /**
     * recuperer le dernier pointage effectuée
     *
     * @return pointage de lot
     */
    public PointageLot getLastPointage();

    /**
     * recuperer le dernier pointage effectuée
     *
     * @param c chantier
     * @param chef salarie chef d'équipe
     * @return pointage de lot
     */
    public PointageLot getLastPointage(Chantier c, Salarie chef);
    
    /**
     * LEs pointage par lot d'un Salarie dans un chantier ( en passant par la zone ) et par la date de pointage
     * @param datepointage
     * @param s
     * @param c
     * @return 
     */
    public List<PointageLot> getListPointageParLotBySalarie(Date datepointage, Salarie s, Chantier c);
}
