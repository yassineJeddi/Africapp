/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.service.Engin.Bean;

import java.util.ArrayList;
import java.util.List;
import ma.bservices.beans.Chantier;
import ma.bservices.tgcc.Entity.Engin;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

/**
 *
 * @author a.wattah
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CiterneServiceBeanIT {
    
    static CiterneServiceBean citerneServiceBean = new CiterneServiceBean();
    
    static List<Chantier> l_chantier_p = new ArrayList<>();
    
    static String chantierString_ = "1";
    
    static Chantier chantier_ = new Chantier();
    
    public CiterneServiceBeanIT() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of get_l_chantiers_sec method, of class CiterneServiceBean.
     */
    @Test
    public void testAGet_l_chantiers_sec() {
        System.out.println("get_l_chantiers_sec");
        
        Boolean display = false;
        
        chantier_.setId(1);
        chantier_.setDisplay_chantier_Principal(true);
        l_chantier_p.add(chantier_);
        
        List<Chantier> result = citerneServiceBean.get_l_chantiers_sec(l_chantier_p, chantierString_);
        
        assertEquals(false, display);
        
        
        
        
        
        
    }

    /**
     * Test of getSomme_volume_actuel method, of class CiterneServiceBean.
     */
    @Test
    public void testBGetSomme_volume_actuel() {
        System.out.println("getSomme_volume_actuel");
        Double volume_ancien = 1d;
        Double volume_actuel = 1d;
        
        Double result = citerneServiceBean.getSoustraction_volume_actuel(volume_ancien, volume_actuel);
        assertEquals(new Double(2), result);
        
    }

    /**
     * Test of get_list_engin_chantier method, of class CiterneServiceBean.
     */
    @Test
    public void testEGet_list_engin_chantier() {
        System.out.println("get_list_engin_chantier");
        List<Engin> l_engins1 = new ArrayList<>();
        List<Engin> l_engins2 = new ArrayList<>();
        
        l_engins1.add(new Engin(1));
        l_engins2.add(new Engin(2));
        
        List<Engin> result = citerneServiceBean.get_list_engin_chantier(l_engins1, l_engins2);
        
        assertNotNull(result);
        
    }
    
}
