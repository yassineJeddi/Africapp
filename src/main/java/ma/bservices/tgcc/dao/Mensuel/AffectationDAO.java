/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.dao.Mensuel;

import java.util.Date;
import java.util.List;
import ma.bservices.tgcc.Entity.Affectation;
import ma.bservices.tgcc.Entity.Mensuel;
import ma.bservices.tgcc.Entity.SousAffectation;

/**
 *
 * @author zakaria.dem
 */
public interface AffectationDAO {

    public void saveAffectation(List<Affectation> affect);

    public List<Date> getDatesOfAffectation(Mensuel _m);

    public Affectation get_lastAffectation_byid_Mensuel(int id);

    public List<Affectation> list_affectation_byid_Mensuel(int id);

    public List<Affectation> getAffectationOfMensuel(Mensuel _m, Date date);

    public List<Affectation> list_Affectaion_BetweenTo_Date_(Date date_debut, Date date_fin, Mensuel m);

    public List<SousAffectation> list_affectation_byid_Affectation(int id);

    public Affectation get_lastAffectation_byMensuelId(int id);

    public void deleteAffectation(List<Affectation> affect);

    public List<Affectation> Find_All();

    public List<SousAffectation> findAll_SousAffectation();

    public void save(SousAffectation affectation);

    public void save_Affecatation(Affectation affectation);

    public List<Date> list_Affectaion_BetweenTo_Date(Date date_debut, Date date_fin, Mensuel m);

    public void delete_to_SousAffectation(SousAffectation sousAffectation);

    public void delete_to_affe(Affectation affectation);

    public void save(Affectation affectation);

    /**
     * on a besoin de la liste des affectation qui ont été crée avant la fin de
     * la semaine que nous voulons pointer Après il faut effecteur des
     * traitement pour avoir seulement la liste des affectation qui ne sont pas
     * terminé avant cette semaine
     *
     * @param m
     * @param date_fin
     * @return
     */
    public List<Affectation> list_Affectaions_inWeek(Mensuel m, Date date_fin);

    public void deleteSA(SousAffectation sousAffectation);

    /**
     * la liste des sous affectaion non archivé
     *
     * @param _affec
     * @return
     */
    public List<SousAffectation> get_sousAffectation(Affectation _affec);

    /**
     * Dans le cas d'une modification de la dernière affectation nous devons
     * donner à l'utilisateur la possibilité de choisir une date supérieur à la
     * dernière affectation et donc son avant dernière affectation
     *
     * @param id_mensuel
     * @return
     */
    public Affectation get_lastAffectationOfCurrent_byMensuelId(int id_mensuel);
}
