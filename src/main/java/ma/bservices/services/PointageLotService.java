/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.services;

import java.util.Date;
import java.util.List;
import java.util.Map;
import ma.bservices.beans.Chantier;
import ma.bservices.beans.PointageLot;
import ma.bservices.beans.Salarie;

/**
 *
 * @author Mahdi
 */
public interface PointageLotService {

    /**
     * la liste des pointages par lot
     *
     * @return la liste des pointage par lots
     */
    public List<PointageLot> loadAll();

    /**
     * effectuer une recherche par date de pointage , par salarie ou par zone et
     * recupérer une listes des pointages par lots
     *
     * @param datepointage la date de pointage a recherché
     * @param s salarie qu'on veut rechercher
     * @param c chantier a rechercher
     * @return la liste des pointage par critère
     */
    public List<PointageLot> recherche(Date datepointage, Salarie s, Chantier c);

    /**
     * effectuer un pointage par lot
     *
     * @param p pointage a ajouter
     * @return true si pointage effectué et false sinon
     */
    public boolean pointage(PointageLot p);

    /**
     * effectuer une modification sur un pointage par lot
     *
     * @param p le pointage a modifié
     * @return true si modification effectué et false sinon
     */
    public boolean updatePointage(PointageLot p);

    /**
     * faire une recapitulative de pointage d'une semaine
     *
     * @param year l'année
     * @param Week le numero de la semaine dans l'année
     * @return une map contient les jours et les remarque sur les jours
     */
    public Map<String, Boolean> weekRecap(Integer year, Integer Week);

    /**
     * vérifier si le pointage par lot est éffétuée dans un date
     *
     * @param d la date a vérifié
     * @return true si déjà pointé false sinon
     */
    public boolean verifierPointage(Date d);

    /**
     * get last pointage by salarie Chef and chantier
     *
     * @param chantier
     * @param chef
     * @return
     */
    public PointageLot getLastPointage(Chantier chantier, Salarie chef);
}
