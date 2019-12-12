/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.service.Mensuel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import ma.bservices.beans.Chantier;
import ma.bservices.tgcc.Entity.Affectation;
import ma.bservices.tgcc.Entity.Mensuel;
import ma.bservices.tgcc.Entity.SousAffectation;
import ma.bservices.tgcc.dao.Mensuel.AffectationDAO;
import ma.bservices.tgcc.dao.engin.ChantierDAO;
import ma.bservices.tgcc.service.Mensuel.bean.AffectationServiceIntermediaire;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author j.allali
 */
@Service("affectationService")
public class AffectationServiceImpl implements AffectationService, Serializable {

    @Autowired
    private AffectationDAO affectDAO;

    @Autowired
    private ChantierDAO chantierDAO;

    public AffectationDAO getAffectDAO() {
        return affectDAO;
    }

    public void setAffectDAO(AffectationDAO affectDAO) {
        this.affectDAO = affectDAO;
    }

    public ChantierDAO getChantierDAO() {
        return chantierDAO;
    }

    public void setChantierDAO(ChantierDAO chantierDAO) {
        this.chantierDAO = chantierDAO;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * retourne la date de la dernière affectation à un chantier ou à une liste
     * de chantier
     *
     * @param Mensuel _m
     * @return
     *
     */
    private Date getDateOfLastAffectation(Mensuel _m) {
        List<Date> l_dateAffectation = affectDAO.getDatesOfAffectation(_m);
        if (l_dateAffectation != null && l_dateAffectation.size() > 0) {
            return l_dateAffectation.get(0);
        }
        return null;
    }

    /**
     * retourne la liste des sous-affectation d'un mensul, ca represente la
     * liste des chantiers auxquelles le mensuel est affecté.
     *
     * @param _m
     * @return
     */
    @Override
    public List<SousAffectation> getAffectation(Mensuel _m) {

        Affectation last_affectation;
        last_affectation = affectDAO.get_lastAffectation_byMensuelId(_m.getId());

        if (last_affectation != null && last_affectation.getSousAffectation().size() > 0) {
            return last_affectation.getSousAffectation();
        }

        return null;
    }

    /**
     *
     * retourne la liste des affectations entre deux dates ( t1 et t2 )
     *
     * @param _m
     * @param start
     * @param to
     * @return
     */
    public List<SousAffectation> getAffectation(Mensuel _m, Date start, Date to) {

        List<Affectation> list_affectations;
        List<SousAffectation> list_subAffectation = new ArrayList<>();

        list_affectations = affectDAO.list_Affectaions_inWeek(_m, to);

        if (list_affectations != null && list_affectations.size() > 0) {

            for (int i = 0; i < list_affectations.size(); i++) {

                // Il faut voir si l'affectation de n'est pas terminé avant le début de la semaine.
                // La fin d'une affectaion est détérminé par le début d'une nouvelle affectation.
                if ((i + 1 < list_affectations.size()) && list_affectations.get(i + 1).getDateeffect().compareTo(start) < 0) {
                    continue;
                }

                if (list_affectations.get(i).getSousAffectation() != null && list_affectations.get(i).getSousAffectation().size() > 0) {
                    //Ajouter les sous affectation non archivées
                    for (int k = 0; k < list_affectations.get(i).getSousAffectation().size(); k++) {
                        if (list_affectations.get(i).getSousAffectation().get(k).getArchived().equals(Boolean.FALSE)) {
                            list_subAffectation.add(list_affectations.get(i).getSousAffectation().get(k));
                        }
                    }
                }
            }
            return list_subAffectation;
        }
        return null;
    }

    @Override
    public List<SousAffectation> remove_redundancy_inChantierSubAffectation(List<SousAffectation> l_sousAffectation) {

        for (int i = 0; i < l_sousAffectation.size(); i++) {
            for (int j = i + 1; j < l_sousAffectation.size(); j++) {
                //System.out.println("1 " + l_sousAffectation.get(i).getChantier().getId() + " -- " + l_sousAffectation.get(j).getChantier().getId());
                if (i != j && l_sousAffectation.get(i).getChantier().equals(l_sousAffectation.get(j).getChantier())) {
                    //  System.out.println("to remove");
                    l_sousAffectation.remove(j);
                }
            }
        }

        return l_sousAffectation;

    }

    @Override
    public void deleteAffectation(List<Affectation> affect) {
        this.affectDAO.deleteAffectation(affect);
    }

    @Override
    public List<Affectation> list_Affectaion_BetweenTo_Date(Date date_debut, Date date_fin, Mensuel m) {

        List<Affectation> affectations = new ArrayList<>();

        List<Affectation> list_affectaion = affectDAO.Find_All();
        List<Date> list_affect_toDate = affectDAO.list_Affectaion_BetweenTo_Date(date_debut, date_fin, m);

        if (list_affectaion != null && list_affect_toDate != null) {
            for (Affectation af : list_affectaion) {
                for (Date da : list_affect_toDate) {
                    if (af.getDateeffect().equals(da)) {
                        affectations.add(af);
                    }
                }
            }

        }

        return affectations;

    }

    @Override
    public List<Date> list_Date_Affectation_Distinct(Date date_debut, Date date_fin, Mensuel m) {
        return this.affectDAO.list_Affectaion_BetweenTo_Date(date_debut, date_fin, m);
    }

    @Override
    public List<Affectation> findAll() {
        return this.affectDAO.Find_All();
    }

    /**
     * save sous affectation
     *
     * @param affectation
     */
    @Override
    public void save(SousAffectation affectation) {
        this.affectDAO.save(affectation);
    }

    /**
     * save affectation
     *
     * @param affectation
     */
    @Override
    public void save(Affectation affectation) {
        this.affectDAO.save(affectation);
    }

    @Override
    public void save_Affecatation(Affectation affectation) {
        this.affectDAO.save_Affecatation(affectation);
    }

    @Override
    public List<SousAffectation> findAll_SousAffectation() {
        return this.affectDAO.findAll_SousAffectation();
    }

    @Override
    public List<Affectation> list_Affectaion_BetweenTo_Date_(Date date_debut, Date date_fin, Mensuel m) {
        return this.affectDAO.list_Affectaion_BetweenTo_Date_(date_debut, date_fin, m);
    }

    @Override
    public List<SousAffectation> list_affectation_byid_Affectation(int id) {
        return this.affectDAO.list_affectation_byid_Affectation(id);
    }

    @Override
    public Affectation list_Affectation_By_Mensuel(int id) {
        return this.affectDAO.get_lastAffectation_byMensuelId(id);
    }

    @Override
    public List<Affectation> list_affectation_byid_Mensuel(int id) {
        return this.affectDAO.list_affectation_byid_Mensuel(id);
    }

    /**
     * return list of affectation (chantier) for user in method parameters
     *
     * @param _mensuel
     * @return List<Chantier>
     * @throws Exception
     */
    @Override
    public AffectationServiceIntermediaire get_affections(Mensuel _mensuel, List<Chantier> l_all_chantiers, Affectation current_affectation) throws Exception {

        if (_mensuel == null) {
            throw new Exception("You must specify the mensuel object");
        }

        Affectation afec_ = null;
        Affectation last_affectation = null;
        if (current_affectation != null && (current_affectation instanceof Affectation)) {
            afec_ = current_affectation;

            last_affectation = this.affectDAO.get_lastAffectationOfCurrent_byMensuelId(_mensuel.getId());

        } else {
            afec_ = this.list_Affectation_By_Mensuel(_mensuel.getId());
            last_affectation = afec_;
        }

        List<Chantier> l_chantiers = null;
//        List<Chantier> l_all_chantiers = chantierDAO.findAll();

        List<SousAffectation> list_sousAffectations = new ArrayList<>();

        if (afec_ != null) {
            Hibernate.initialize(afec_);
            list_sousAffectations = this.affectDAO.get_sousAffectation(afec_);

            l_chantiers = new LinkedList<>();

            for (int i = 0; i < list_sousAffectations.size(); i++) {
                l_chantiers.add(list_sousAffectations.get(i).getChantier());

            }

        }
        return new AffectationServiceIntermediaire(AffectationServiceIntermediaire.merge_chantier(l_all_chantiers, l_chantiers), list_sousAffectations, afec_, last_affectation);
    }

    /**
     * save affectation with sub affectations object
     *
     * @param l_sousAffect
     * @param _mensuel
     * @param date_creation
     * @param affect_to_update
     */
    @Override
    public void add_affectation_withSubAffectation(List<SousAffectation> l_sousAffect, Mensuel _mensuel, Date date_creation, Affectation affect_to_update) {

        if (l_sousAffect != null) {
            Affectation affectation = null;

            if (l_sousAffect.size() > 0) {

                if (affect_to_update != null && (affect_to_update instanceof Affectation)) {
                    //pour la modification d'une affectation
                    affectation = affect_to_update;
                    affectation.setDateeffect(date_creation);
                    for (int j = 0; j < affectation.getSousAffectation().size(); j++) {
                        this.affectDAO.deleteSA(affectation.getSousAffectation().get(j));
                    }

                } else {
                    //pour l'ajout d'une affectation
                    affectation = new Affectation(date_creation, _mensuel);
                    affectation.setSousAffectation(new LinkedList<SousAffectation>());
                }

                for (int i = 0; i < l_sousAffect.size(); i++) {
                    if (l_sousAffect.get(i).getAffectation() != null) {
                        SousAffectation sa = new SousAffectation(l_sousAffect.get(i));
                        sa.setAffectation(affectation);
                        affectation.getSousAffectation().add(sa);
                    } else {
                        l_sousAffect.get(i).setAffectation(affectation);
                        affectation.getSousAffectation().add(l_sousAffect.get(i));
                    }

                }
            }

            this.affectDAO.save(affectation);

        }

    }

    @Override
    public List<SousAffectation> getAffectation_betweenTwoDates(Mensuel _m, Date start, Date end) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
