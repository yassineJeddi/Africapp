///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package ma.bservices.dao;
//
//import java.util.List;
//import javax.transaction.Transactional;
//import ma.bservices.beans.NiveauFonction;
//import org.junit.After;
//import org.junit.AfterClass;
//import org.junit.Before;
//import org.junit.BeforeClass;
//import org.junit.Test;
//import static org.junit.Assert.*;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.transaction.TransactionConfiguration;
//
///**
// *
// * @author admin
// */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration({
//    "file:src/main/webapp/WEB-INF/beans_test.xml",
//    "file:src/main/webapp/WEB-INF/applicationContext.xml",
//    "file:src/main/webapp/WEB-INF/spring/hibernate-context.xml",
//    "file:src/main/webapp/WEB-INF/dispatcher-servlet.xml"
//})
////@ContextConfiguration(locations = {"classpath*:*beans.xml"})
//@TransactionConfiguration()
//@Transactional
//public class NiveauFonctionImplTest extends AbstractTransactionalJUnit4SpringContextTests {
//
//    public NiveauFonctionImplTest() {
//    }
//
//    @Autowired
//    @Qualifier("niveauFonctionDao")
//    NiveauFonctionDao instance;
//
//    //NiveauFonction result = new NiveauFonction();
//    @BeforeClass
//    public static void setUpClass() {
//    }
//
//    @AfterClass
//    public static void tearDownClass() {
//    }
//
//    @Before
//    public void setUp() {
//    }
//
//    @After
//    public void tearDown() {
//    }
//
//    /**
//     * Test of findAll method, of class NiveauFonctionImpl.
//     */
//    /**
//     * Test of addNiveau method, of class NiveauFonctionImpl.
//     */
//    NiveauFonction nf = new NiveauFonction();
//
//    @Test
//    public void testAddNiveau() {
//        System.out.println("addNiveau");
//        nf.setNiveau("niveau 1");
//        boolean expResult = false;
//        boolean result = instance.addNiveau(nf);
//        assertNotSame(expResult, result);
//        instance.deleteNiveau(nf);
//    }
//
//    @Test
//    public void testFindAll() {
//        System.out.println("findAll");
//        List<NiveauFonction> result = instance.findAll();
//        System.out.println("size" + result.size());
//        assertNotNull(result);
//    }
//
//    /**
//     * Test of findById method, of class NiveauFonctionImpl.
//     */
//    @Test
//    public void testFindById() {
//        System.out.println("findById");
//        instance.addNiveau(nf);
//        NiveauFonction result = instance.findById(nf.getId());
//        assertEquals(result.getId(), nf.getId());
//        instance.deleteNiveau(nf);
//    }
//
//    /**
//     * Test of deleteNiveau method, of class NiveauFonctionImpl.
//     */
//    @Test
//    public void testDeleteNiveau() {
//        System.out.println("deleteNiveau");
//        boolean expResult = true;
//        nf.setId(1);
//        instance.addNiveau(nf);
//        boolean result = instance.deleteNiveau(nf);
//        assertEquals(expResult, result);
//    }
//
//    /**
//     * Test of updateNiveay method, of class NiveauFonctionImpl.
//     */
//    @Test
//    public void testUpdateNiveau() {
//        System.out.println("updateNiveay");
//        boolean expResult = false;
//        instance.addNiveau(nf);
//        nf.setNiveau("niveau 3");
//        boolean result = instance.updateNiveau(nf);
//        assertNotSame(expResult, result);
//        instance.deleteNiveau(nf);
//    }
//
//}
