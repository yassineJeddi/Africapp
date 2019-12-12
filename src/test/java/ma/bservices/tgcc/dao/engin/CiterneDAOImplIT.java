/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.dao.engin;

import java.util.List;
import ma.bservices.tgcc.Entity.Bon_Livraison_Citerne;
import ma.bservices.tgcc.Entity.Citerne;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author a.wattah
 */
public class CiterneDAOImplIT {
    
    public CiterneDAOImplIT() {
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
     * Test of find_all_Citerne method, of class CiterneDAOImpl.
     */
    @Test
    public void testFind_all_Citerne() {
        System.out.println("find_all_Citerne");
        CiterneDAOImpl instance = new CiterneDAOImpl();
        List<Citerne> expResult = null;
        List<Citerne> result = instance.find_all_Citerne();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of save_citerne method, of class CiterneDAOImpl.
     */
    @Test
    public void testSave_citerne() {
        System.out.println("save_citerne");
        Citerne citerne = null;
        CiterneDAOImpl instance = new CiterneDAOImpl();
        instance.save_citerne(citerne);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of update_citerne method, of class CiterneDAOImpl.
     */
    @Test
    public void testUpdate_citerne() {
        System.out.println("update_citerne");
        Citerne citerne = null;
        CiterneDAOImpl instance = new CiterneDAOImpl();
        instance.update_citerne(citerne);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of save_bon_caisse_citerne method, of class CiterneDAOImpl.
     */
    @Test
    public void testSave_bon_caisse_citerne() {
        System.out.println("save_bon_caisse_citerne");
        Bon_Livraison_Citerne bon_Livraison_Citerne = null;
        CiterneDAOImpl instance = new CiterneDAOImpl();
        instance.save_bon_caisse_citerne(bon_Livraison_Citerne);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
