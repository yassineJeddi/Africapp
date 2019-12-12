/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.service.Mensuel;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import ma.bservices.beans.Chantier;
import ma.bservices.tgcc.Entity.Mensuel;
import ma.bservices.tgcc.Entity.Pointage;
import ma.bservices.tgcc.dao.Mensuel.PointageDAO;
import ma.bservices.tgcc.service.Mensuel.bean.PointageDataReturn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author zakaria.dem
 */
@Service("pointageService")
public class PointageServiceImpl implements PointageService, Serializable {

    @Autowired
    private PointageDAO pointageDAO;

    public PointageDAO getPointageDAO() {
        return pointageDAO;
    }

    public void setPointageDAO(PointageDAO pointageDAO) {
        this.pointageDAO = pointageDAO;
    }

    @Override
    public void save(Pointage _p) {
        //on verifie si le pointage existe déjà , on fait la verification pour la date et par le creneau   
        Pointage pointage_exist = pointageDAO.exist(_p.getMensuel(), _p.getDay(), _p.getCrenau());
        if (pointage_exist instanceof Pointage) {

            pointage_exist.setChantier(_p.getChantier());
            pointage_exist.setAutre(_p.getAutre());
            pointage_exist.setAutreType(_p.getAutreType());
            pointage_exist.setDefinitive(_p.getDefinitive());

            pointageDAO.save(pointage_exist);
        } else {

            pointageDAO.save(_p);
        }
    }

    @Override
    /**
     * retourne la liste des pointage pour être afficé dans l'interface et la
     * liste des affections depuis la table pointage
     */
    public PointageDataReturn getListePointageValue(Date start, Date end, Mensuel _m) {

        List<Pointage> l_p = pointageDAO.getPointageByMensuelBetweenTwoDates(_m, start, end);

        List<Chantier> l_c = new LinkedList<Chantier>();

        Boolean pointerDef = false;
        for (int i = 0; i < l_p.size(); i++) {

            //verifier si le user a pointer définitivement ou non
            if (pointerDef == false && l_p.get(i).getDefinitive() == 1) {
                pointerDef = true;
            }

            if (l_p.get(i).getChantier() != null) {
                Boolean found = false;
                for (int j = 0; j < l_c.size(); j++) {
                    if (l_c.get(j).equals(l_p.get(i).getChantier())) {
                        found = true;
                        break;
                    }
                }

                if (found == false) {
                    l_c.add(l_p.get(i).getChantier());
                }
            }
        }

        return new PointageDataReturn(l_c, l_p, pointerDef);

        //getAllPointage in the list
    }

    @Override
    public Date get_LastWeek_Pointed(int mensuel_id) {

        Pointage _lastPoint = pointageDAO.get_Last_Pointage(mensuel_id);

        if (_lastPoint != null) {
            Date day = _lastPoint.getDay();

//            Calendar cal = Calendar.getInstance();
//            cal.setTime(day);
//            Integer week_number = cal.get(Calendar.WEEK_OF_YEAR);
            return day;

        }

        return null;
    }

    @Override
    public List<String> distinct_fiche_mensuels() {
        return pointageDAO.distinct_fiche_mensuels();
    }

    /**
     * Retourne la liste des mensuels qui ont pointé un jour sur le chantier
     * donnée en paramètere Cette fonction est utilisé pour le service RH pour
     * facilité la recherche des fiches de pointage.
     *
     * @param chantier_id
     * @return
     */
    @Override
    public List<Mensuel> distinct_mensuels_by_chantier(int chantier_id) {
        return pointageDAO.distinct_mensuels_by_chantier(chantier_id);
    }

    /**
     *
     * selectionne les code des fiches de pointage d'un mensuel donnée en
     * paramètre dans un chantier donnée aussi en paramètre
     *
     * @param selected_chantier
     * @param selected_mensuel
     * @return List<String>
     */
    @Override
    public List<String> get_fiches_Bymensuel_inChantier(Integer selected_chantier, int selected_mensuel) {
        return pointageDAO.get_fiches_Bymensuel_inChantier(selected_chantier, selected_mensuel);
    }

}
