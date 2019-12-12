///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package ma.bservices.services;
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
//@TransactionConfiguration
//@Transactional
//public class NiveauFonctionServiceImplTest extends AbstractTransactionalJUnit4SpringContextTests {
//
//    public NiveauFonctionServiceImplTest() {
//    }
//
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
//    @Autowired
//    @Qualifier("niveauFonctionService")
//    NiveauFonctionService instance;
//
//    NiveauFonction nf = new NiveauFonction();
//
//    /**
//     * Test of getAll method, of class NiveauFonctionServiceImpl.
//     */
//    @Test
//    public void testGetAll() {
//        System.out.println("getAll");
//        List<NiveauFonction> result = instance.getAll();
//        assertNotNull(result);
//    }
//
//    /**
//     * Test of NiveauById method, of class NiveauFonctionServiceImpl.
//     */
//    @Test
//    public void testNiveauById() {
//        System.out.println("NiveauById");
//        instance.ajouter(nf);
//        NiveauFonction result = instance.getById(nf.getId());
//        assertEquals(result.getId(), nf.getId());
//        instance.supprimer(nf);
//    }
//
//    /**
//     * Test of Ajouter method, of class NiveauFonctionServiceImpl.
//     */
//    @Test
//    public void testAjouter() {
//        System.out.println("Ajouter");
//        nf.setNiveau("niveau 1");
//        boolean expResult = false;
//        boolean result = instance.ajouter(nf);
//        assertNotSame(expResult, result);
//        instance.supprimer(nf);
//    }
//
//    /**
//     * Test of Modifier method, of class NiveauFonctionServiceImpl.
//     */
//    @Test
//    public void testModifier() {
//        System.out.println("Modifier");
//        boolean expResult = false;
//        instance.ajouter(nf);
//        nf.setNiveau("niveau 3");
//        boolean result = instance.modifier(nf);
//        assertNotSame(expResult, result);
//        instance.supprimer(nf);
//    }
//
//    /**
//     * Test of supprimer method, of class NiveauFonctionServiceImpl.
//     */
//    @Test
//    public void testSupprimer() {
//        System.out.println("supprimer");
//        boolean expResult = true;
//        nf.setId(1);
//        instance.ajouter(nf);
//        boolean result = instance.supprimer(nf);
//        assertEquals(expResult, result);
//    }
//
//}
