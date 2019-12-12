/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.service.Mensuel.bean;

import java.util.ArrayList;
import java.util.List;
import ma.bservices.beans.Chantier;
import ma.bservices.tgcc.Entity.SousAffectation;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

/**
 *
 * @author a.wattah
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AffectationServiceIntermediaireIT {

    static AffectationServiceIntermediaire affectationServiceIntermediaire = new AffectationServiceIntermediaire();
    static Chantier chantier_ = new Chantier();
    static SousAffectation sou_aff = new SousAffectation();
    static List<Chantier> l_chantier1 = new ArrayList<>();
    static List<Chantier> l_chantier2 = new ArrayList<>();

    static List<SousAffectation> l_sous_affects = new ArrayList<>();

    public AffectationServiceIntermediaireIT() {
    }

    /**
     * Test of getProposal_affectation_pourcent method, of class
     * AffectationServiceIntermediaire.
     */
    @Test
    public void testAGetProposal_affectation_pourcent() {
        System.out.println("getProposal_affectation_pourcent");

        int expResult = 0;
        /**
         * test if proposal inferieur 0 return 0
         */
        affectationServiceIntermediaire.setProposal_affectation_pourcent(-1);
        int result = affectationServiceIntermediaire.getProposal_affectation_pourcent();
        assertEquals(expResult, result);

    }

    /**
     * Test of get_somme_affectation method, of class
     * AffectationServiceIntermediaire.
     */
    @Test
    public void testBGet_somme_affectation() {
        System.out.println("get_somme_affectation");
        int expResult = 50;
        /**
         * test la somme pourcentage de list affectation
         */

        List<SousAffectation> l_sous_affcts = new ArrayList<>();

        SousAffectation sous_affect = new SousAffectation();
        sous_affect.setPourcentage(50);
        l_sous_affcts.add(sous_affect);

        AffectationServiceIntermediaireIT.affectationServiceIntermediaire.setL_sous_affectation(l_sous_affcts);

        int result = affectationServiceIntermediaire.get_somme_affectation();
        assertEquals(expResult, result);

    }

    /**
     * Test of calcul_proposal_affectation method, of class
     * AffectationServiceIntermediaire.
     */
    @Test
    public void testCalcul_proposal_affectation() {
        System.out.println("calcul_proposal_affectation");
        int expResult = 0;
        /**
         * list pour remplir les sous affectation calcule
         */

        sou_aff.setPourcentage(150);
        sou_aff.setChantier(chantier_);

        l_sous_affects.add(sou_aff);

        affectationServiceIntermediaire.setL_sous_affectation(l_sous_affects);

        int result = affectationServiceIntermediaire.calcul_proposal_affectation();
        assertEquals(expResult, result);

    }

    /**
     * Test of merge_chantier method, of class AffectationServiceIntermediaire.
     */
    @Test
    public void testDMerge_chantier() {
        System.out.println("merge_chantier");

        chantier_.setCode("test_chantier");
        l_chantier1.add(chantier_);
        l_chantier2.add(chantier_);
        Boolean display = true;
        Boolean expResult = false;
        List<Chantier> result = AffectationServiceIntermediaire.merge_chantier(l_chantier1, l_chantier2);
        if (chantier_ != null) {
            for (Chantier cha_ : result) {
                display = cha_.getDisplay();

            }

        }

        assertEquals(expResult, display);

    }

    /**
     * Test of add_sousAffectation method, of class
     * AffectationServiceIntermediaire.
     */
    @Test
    public void testEAdd_sousAffectation() {
        System.out.println("add_sousAffectation");

        System.out.println("cantier : " + chantier_.getDisplay());
        int pourcentage = 100;
        int result = 0;

        affectationServiceIntermediaire = new AffectationServiceIntermediaire(l_chantier1, l_sous_affects);

        this.affectationServiceIntermediaire.add_sousAffectation(this.chantier_, pourcentage);

        for (SousAffectation sa : affectationServiceIntermediaire.getL_sous_affectation()) {
            result = sa.getPourcentage();
        }

        assertEquals(pourcentage, result);

    }

    /**
     * Test of have_made_modification method, of class
     * AffectationServiceIntermediaire.
     */
    @Test
    public void testFhave_made_modification() {

        System.out.println("testhave_made_modification");

        Boolean expResult = true;

        Boolean result = affectationServiceIntermediaire.have_made_modification();

        assertEquals(expResult, result);

    }

    /**
     * Test of validate method, of class AffectationServiceIntermediaire.
     */
    @Test
    public void testJValidate() {

        System.out.println("testJValidate");

        List<SousAffectation> result = this.affectationServiceIntermediaire.validate();

        assertNotNull(result);

    }

    /**
     * Test of delete_sousAffectation method, of class
     * AffectationServiceIntermediaire.
     */
    @Test
    public void testHdelete_sousAffectation() {

        System.out.println("delete affectation");

        Boolean expResult = true;

        affectationServiceIntermediaire.delete_sousAffectation(sou_aff);

        Boolean result = sou_aff.getChantier().getDisplay();

        assertEquals(expResult, result);

    }

}
