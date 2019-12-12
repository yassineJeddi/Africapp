/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.dao;

import java.util.Date;
import java.util.List;
import ma.bservices.beans.AffectationSalarieSupp;
import ma.bservices.beans.Salarie;

/**
 *
 * @author j.allali
 */
public interface AffectationSSuppDAO {

    public Boolean affecterSupp(AffectationSalarieSupp affect);

    public AffectationSalarieSupp updateSalarie(AffectationSalarieSupp affect);

    public List<AffectationSalarieSupp> listSalarieBySupp();

    public List<AffectationSalarieSupp> listSalarieBySupp(Integer idSupp);

    public AffectationSalarieSupp listSalarieByChantier(Integer idSupp, Integer idSalarie, Integer idChantier);

    public List<AffectationSalarieSupp> listSalarieByChantierDateSupp(Integer idSupp, Date de, Date a, Integer idChantier);

    public Boolean pointageEntreeSalarie(Integer idSalarie, Long longDateTimePointage, String datee);

    public List<AffectationSalarieSupp> listAffectationByChantier(Integer idChantier);
    
    public List<Salarie> listAffectationByChantierAndChefEquipe(Integer idChantier, Salarie chef);
    
    public List<AffectationSalarieSupp> listAffectationByChantierAndSalarie(Integer idChantier, Salarie s);
}
