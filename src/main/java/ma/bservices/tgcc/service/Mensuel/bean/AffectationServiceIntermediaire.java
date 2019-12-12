/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.service.Mensuel.bean;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import ma.bservices.beans.Chantier;
import ma.bservices.tgcc.Entity.Affectation;
import ma.bservices.tgcc.Entity.SousAffectation;

/**
 *
 * @author zakaria.dem
 */
public class AffectationServiceIntermediaire {

    private List<Chantier> l_chanier;

    private List<SousAffectation> l_sous_affectation;

    /**
     * to know if th e user had made change in affectation
     */
    private List<SousAffectation> l_sous_affectation_copy;

    private int poucentage_affectation;

    private int proposal_affectation_pourcent;

    private Affectation last_affectation;

    private Affectation before_last_affectation;

    private Date minDateForAffectaion;
    /**
     * parceque le date min est la dernière date de l'affectaion à la dernère
     * minute du jour, comme ça on pourra affecter le lendemain, mais pour jsf
     * qui ne prend pas en compte les heures, minute et seconde, ça revient à
     * autoriser cette date, c'est pour ça nous avons besoin de cette variable
     * qui contiendra la dernière date d'affectation pour 1 ( pour le min date
     * de JSF).
     */
    private Date minDateForAffectaionInInterface;

    /**
     * Constrcuteur
     */
    public AffectationServiceIntermediaire() {
        poucentage_affectation = 0;
        proposal_affectation_pourcent = 100;
    }

    public AffectationServiceIntermediaire(List<Chantier> l_chanier, List<SousAffectation> l_sous_affectation) {

        this.l_chanier = l_chanier;
        this.l_sous_affectation = l_sous_affectation;

        if (l_sous_affectation == null) {
            l_sous_affectation = new LinkedList();
        }

        if (l_sous_affectation == null || l_sous_affectation.isEmpty()) {
            l_sous_affectation_copy = null;
        } else {
            l_sous_affectation_copy = new LinkedList();
            for (int i = 0; i < this.l_sous_affectation.size(); i++) {
                this.l_sous_affectation_copy.add(new SousAffectation(this.l_sous_affectation.get(i).getPourcentage(), this.l_sous_affectation.get(i).getChantier()));
            }
        }

    }

    public AffectationServiceIntermediaire(List<Chantier> l_chanier, List<SousAffectation> l_sous_affectation, Affectation last_affectation, Affectation before_last_affectation) {
        this(l_chanier, l_sous_affectation);
        this.last_affectation = last_affectation;
        this.before_last_affectation = before_last_affectation;
        //on ne doit avoir qu'une seul affectation par jour
        //donc la date doit etre comprise entre la date de la dernière affectaion +1 et la date d'aujourd'hui
        Calendar cal = Calendar.getInstance();
        if (before_last_affectation != null) {

            cal.setTime(this.before_last_affectation.getDateeffect());
            cal.set(Calendar.HOUR, 23);
            cal.set(Calendar.MINUTE, 59);
            cal.set(Calendar.SECOND, 59);
            this.minDateForAffectaion = cal.getTime();
            cal.add(Calendar.DATE, 1);
            this.minDateForAffectaionInInterface = cal.getTime();
        }

    }

    // getters and setters
    public List<Chantier> getL_chanier() {
        return l_chanier;
    }

    public void setL_chanier(List<Chantier> l_chanier) {
        this.l_chanier = l_chanier;
    }

    public List<SousAffectation> getL_sous_affectation_copy() {
        return l_sous_affectation_copy;
    }

    public void setL_sous_affectation_copy(List<SousAffectation> l_sous_affectation_copy) {
        this.l_sous_affectation_copy = l_sous_affectation_copy;
    }

    public List<SousAffectation> getL_sous_affectation() {
        return l_sous_affectation;
    }

    public void setL_sous_affectation(List<SousAffectation> l_sous_affectation) {
        this.l_sous_affectation = l_sous_affectation;
    }

    public int getPoucentage_affectation() {

        return poucentage_affectation;
    }

    public Date getMinDateForAffectaion() {
        return minDateForAffectaion;
    }

    public void setMinDateForAffectaion(Date minDateForAffectaion) {
        this.minDateForAffectaion = minDateForAffectaion;
    }

    public void setPoucentage_affectation(int poucentage_affectation) {
        this.poucentage_affectation = poucentage_affectation;
    }

    public Affectation getBefore_last_affectation() {
        return before_last_affectation;
    }

    public void setBefore_last_affectation(Affectation before_last_affectation) {
        this.before_last_affectation = before_last_affectation;
    }

    public Affectation getLast_affectation() {
        return last_affectation;
    }

    public void setLast_affectation(Affectation last_affectation) {
        this.last_affectation = last_affectation;
    }

    public Date getMinDateForAffectaionInInterface() {
        return minDateForAffectaionInInterface;
    }

    public void setMinDateForAffectaionInInterface(Date minDateForAffectaionInInterface) {
        this.minDateForAffectaionInInterface = minDateForAffectaionInInterface;
    }

    // methods
    public int getProposal_affectation_pourcent() {

        if (proposal_affectation_pourcent > 100) {
            return 100;
        }

        if (proposal_affectation_pourcent < 0) {
            return 0;
        }

        return proposal_affectation_pourcent;
    }

    public void setProposal_affectation_pourcent(int proposal_affectation_pourcent) {

        if (proposal_affectation_pourcent > 100) {
            proposal_affectation_pourcent = 100;
        }

        if (proposal_affectation_pourcent < 0) {
            proposal_affectation_pourcent = 0;
        }

        this.proposal_affectation_pourcent = proposal_affectation_pourcent;
    }

    /**
     * retourne la somme des pourcentages des sous affectations.
     *
     * @return
     */
    public int get_somme_affectation() {

        int somme = 0;

        if (this.l_sous_affectation == null) {
            return somme;
        }

        for (int i = 0; i < this.l_sous_affectation.size(); i++) {
            somme += this.l_sous_affectation.get(i).getPourcentage();
        }

        return somme;
    }

    /**
     * retourne la somme des affectation qu'il a saisie, pour proposer la
     * pourcent à affecter
     *
     * @return
     */
    public int calcul_proposal_affectation() {

        int somme = 100;

        if (this.l_sous_affectation == null) {
            return somme;
        }

        for (int i = 0; i < this.l_sous_affectation.size(); i++) {
            somme -= this.l_sous_affectation.get(i).getPourcentage();
        }

        return somme < 0 ? 0 : somme;

    }

    /**
     * merge 2 list of chantier, it's for not having doubles
     *
     * @param l_chantier1
     * @param l_chantier2
     * @return
     */
    public static List<Chantier> merge_chantier(List<Chantier> l_chantier1, List<Chantier> l_chantier2) {

        if (l_chantier2 == null) {

            /**
             * pour réinitialiser le display des chantier, si on ajoute un
             * chantier et puis on ne valide pas quand on recharge on devrai
             * avoir la liste de tous les chantiers.
             */
            for (int i = 0; i < l_chantier1.size(); i++) {
                l_chantier1.get(i).setDisplay(Boolean.TRUE);
            }

            return l_chantier1;
        }
        for (int i = 0; i < l_chantier1.size(); i++) {
            if (l_chantier2.contains(l_chantier1.get(i))) {
//                int chantier_list_1_index = l_chantier1.indexOf(l_chantier2.get(i));
                System.out.println("SET CHANTIER TO FALSE");
                l_chantier1.get(i).setDisplay(Boolean.FALSE);
            } else {
//                int chantier_list_1_index = l_chantier1.indexOf(l_chantier2.get(i));

                l_chantier1.get(i).setDisplay(Boolean.TRUE);
            }
        }

        return l_chantier1;

    }

    /**
     * add chantier to sub affectation
     *
     * @param _chantier
     * @param pourcentage
     */
    public void add_sousAffectation(Chantier _chantier, int pourcentage) {

        System.out.println("pourcentage" + pourcentage);

        if (pourcentage <= 100 && pourcentage > 0) {

            System.out.println("ADD SOUS AFFECTATION ENTER");

            SousAffectation s_affectation = new SousAffectation(pourcentage, _chantier);
            if (this.l_sous_affectation == null) {
                this.l_sous_affectation = new LinkedList<>();
            }
            this.l_sous_affectation.add(s_affectation);

            int index = this.l_chanier.indexOf(_chantier);
            this.l_chanier.get(index).setDisplay(Boolean.FALSE);
        }

    }

    /**
     * delete chantier from sub affectation
     *
     * @param _sousAffectatoin
     */
    public void delete_sousAffectation(SousAffectation _sousAffectatoin) {

        if (this.l_sous_affectation != null && this.l_sous_affectation.contains(_sousAffectatoin)) {
            this.l_sous_affectation.remove(_sousAffectatoin);
            int index = this.l_chanier.indexOf(_sousAffectatoin.getChantier());
            this.l_chanier.get(index).setDisplay(Boolean.TRUE);
        }

    }

    /**
     * get sub affectation list to insert as affectations
     */
    public List<SousAffectation> validate() {

        return this.l_sous_affectation;
//        return (this.have_made_modification().equals(Boolean.TRUE)) ? this.l_sous_affectation : null;
    }

    /**
     * see if the user had made any modification
     *
     * @return
     */
    public Boolean have_made_modification() {

        if (this.l_sous_affectation == null && this.l_sous_affectation_copy == null) {
            return Boolean.FALSE;
        }

        // XOR
        if ((this.l_sous_affectation == null ^ this.l_sous_affectation_copy != null)) {
            return Boolean.TRUE;
        }

        if (this.l_sous_affectation.size() != this.l_sous_affectation_copy.size()) {
            return Boolean.FALSE;
        }

        //having the same size
        Boolean found;
        for (int i = 0; i < this.l_sous_affectation.size(); i++) {

            found = Boolean.FALSE;
            for (int j = 0; j < this.l_sous_affectation.size(); j++) {

                if (this.l_sous_affectation_copy.get(j).equals(this.l_sous_affectation.get(i))) {
                    found = Boolean.TRUE;
                    break;
                }
            }
            if (found.equals(Boolean.FALSE)) {
                return false;
            }

        }

        return Boolean.TRUE;

    }

}
