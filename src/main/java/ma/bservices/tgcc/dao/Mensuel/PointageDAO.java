/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.dao.Mensuel;

import java.util.Date;
import java.util.List;
import ma.bservices.tgcc.Entity.Crenau;
import ma.bservices.tgcc.Entity.Mensuel;
import ma.bservices.tgcc.Entity.Pointage;

/**
 *
 * @author zakaria.dem
 */
public interface PointageDAO {

    public void save(Pointage _p);

    public Pointage exist(Mensuel _m, Date date, Crenau _c);

    public List<Pointage> getPointageByMensuelBetweenTwoDates(Mensuel mensuel, Date start, Date end);

    public Pointage get_Last_Pointage(int mensuel_id);

    public List<String> distinct_fiche_mensuels();

    /**
     * methode de test unitaire
     */
    public void delete(Pointage _p);

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
