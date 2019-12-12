/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.service.Mensuel;

import java.util.Date;
import java.util.List;
import ma.bservices.tgcc.Entity.Mensuel;
import ma.bservices.tgcc.Entity.Pointage;
import ma.bservices.tgcc.service.Mensuel.bean.PointageDataReturn;

/**
 *
 * @author zakaria.dem
 */
public interface PointageService {

    public final int CRANEAU_8_A_10 = 1;
    public final int CRANEAU_10_A_12 = 2;
    public final int CRANEAU_14_A_16 = 3;
    public final int CRANEAU_16_A_18 = 4;

    public final String STR_AUTRE_VISITE = "Autre visite";
    public final String STR_VISITE_SIEGE = "Visite siège";
    public final String STR_CONGE = "Congé";
    public final String STR_ARRET = "Arrêt";

    public final int STR_AUTRE_VISITE_POS = 1;
    public final int STR_VISITE_SIEGE_POS = 2;
    public final int STR_CONGE_POS = 3;
    public final int STR_ARRET_POS = 4;

    public void save(Pointage _p);

    public PointageDataReturn getListePointageValue(Date start, Date end, Mensuel mensuel);

    public Date get_LastWeek_Pointed(int mensuel_id);

    public List<String> distinct_fiche_mensuels();

    /**
     * Retourne la liste des mensuels qui ont pointé un jour sur le chantier
     * donnée en paramètere Cette fonction est utilisé pour le service RH pour
     * facilité la recherche des fiches de pointage.
     *
     * @param chantier_id
     * @return
     */
    public List<Mensuel> distinct_mensuels_by_chantier(int chantier_id);

    /**
     *
     * selectionne les code des fiches de pointage d'un mensuel donnée en
     * paramètre dans un chantier donnée aussi en paramètre
     *
     * @param selected_chantier
     * @param selected_mensuel
     * @return List<String>
     */
    public List<String> get_fiches_Bymensuel_inChantier(Integer selected_chantier, int selected_mensuel);

}
