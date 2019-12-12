/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.service.Mensuel;

import java.util.Date;
import java.util.List;
import ma.bservices.beans.Chantier;
import ma.bservices.tgcc.Entity.Affectation;
import ma.bservices.tgcc.Entity.Mensuel;
import ma.bservices.tgcc.Entity.SousAffectation;
import ma.bservices.tgcc.service.Mensuel.bean.AffectationServiceIntermediaire;

/**
 *
 * @author j.allali
 */
public interface AffectationService {

    public List<SousAffectation> getAffectation(Mensuel _m);

    public void deleteAffectation(List<Affectation> affect);

    public List<Affectation> list_affectation_byid_Mensuel(int id);

    public List<Affectation> findAll();

    public List<SousAffectation> findAll_SousAffectation();

    public void save_Affecatation(Affectation affectation);

    public void save(SousAffectation affectation);

    public List<SousAffectation> list_affectation_byid_Affectation(int id);

    public List<Affectation> list_Affectaion_BetweenTo_Date(Date date_debut, Date date_fin, Mensuel m);

    public List<Affectation> list_Affectaion_BetweenTo_Date_(Date date_debut, Date date_fin, Mensuel m);

    public List<Date> list_Date_Affectation_Distinct(Date date_debut, Date date_fin, Mensuel m);

    public Affectation list_Affectation_By_Mensuel(int id);

    /**
     * return list of affectation (chantier) for user in method parameters
     *
     * @param _mensuel
     * @return
     * @throws Exception if mensuel is null
     */
    public AffectationServiceIntermediaire get_affections(Mensuel _mensuel, List<Chantier> l_all_chantiers, Affectation last_affectation) throws Exception;

    public void save(Affectation affectation);

    public void add_affectation_withSubAffectation(List<SousAffectation> l_sousAffect, Mensuel _mensuel, Date date_creation, Affectation affect_to_update);

    public List<SousAffectation> getAffectation_betweenTwoDates(Mensuel _m, Date start, Date end);

    public List<SousAffectation> getAffectation(Mensuel _m, Date start, Date to);

    public List<SousAffectation> remove_redundancy_inChantierSubAffectation(List<SousAffectation> l_sousAffectation);

}
