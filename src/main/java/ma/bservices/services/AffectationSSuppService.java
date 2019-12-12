/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.services;

import java.util.Date;
import java.util.List;
import ma.bservices.beans.AffectationSalarieSupp;
import ma.bservices.beans.Chantier;
import ma.bservices.beans.Salarie;

/**
 *
 * @author j.allali
 */
public interface AffectationSSuppService {

    public Boolean affecterSupp(AffectationSalarieSupp affect);

    public AffectationSalarieSupp updateSalarie(AffectationSalarieSupp affect);

    public List<AffectationSalarieSupp> listSalarieBySupp();

    public List<AffectationSalarieSupp> listSalarieBySupp(Integer idSupp);

    public AffectationSalarieSupp listSalarieByChantier(Integer idSupp, Integer idSalarie, Integer idChantier);

    public List<AffectationSalarieSupp> listSalarieByChantierDateSupp(Integer idSupp, Date dateDe, Date dateA, Integer idChantier);

    public Boolean pointageEntreeSalarie(Integer idSalarie, Long longDateTimePointage, String datee);

    /**
     * Désaffecter les salariés a un chef d'équipe
     *
     * @param c chantier
     * @param chef chef d'équipe
     * @return true si désaffectation effectuer sinon false
     */
    public Boolean desaffectation(Chantier c, Salarie chef);

    /**
     * get la liste des salaries par chef d'équipe courant dans un chantier
     *
     * @param chef le chef d'équipe
     * @param c chantier
     * @return liste des salaries
     */
    public List<Salarie> salarieByLastSuperieurAndChantier(Salarie chef, Chantier c);

    /**
     * get le dernier supérieur by salarie
     *
     * @param s salarie
     * @return deriner affectatiton
     */
    public AffectationSalarieSupp getLastSuperiorBySalarie(Salarie s);

    /**
     * get le dernier supérieur par salarie et chantier
     *
     * @param s salarie
     * @param idChantier identifiant chantier
     * @return deriner affectatiton
     */
    public AffectationSalarieSupp getLastSuperiorBySalarieAndChantier(Salarie s, Integer idChantier);
}
