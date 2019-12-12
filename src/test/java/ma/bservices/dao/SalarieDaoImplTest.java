///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package ma.bservices.dao;
//
//import java.util.List;
//import javax.transaction.Transactional;
//import ma.bservices.beans.Salarie;
//import ma.bservices.services.SalarieService;
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
// * @author j.allali
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
//public class SalarieDaoImplTest extends AbstractTransactionalJUnit4SpringContextTests {
//    
//    public SalarieDaoImplTest() {
//    }
//    
//    @Autowired
//    @Qualifier("salarieDao")
//    SalarieDao instance;
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
//    /**
//     * Test of affecterSupp method, of class SalarieDaoImpl.
//     */
//    @Test
//    public void testAffecterSupp() {
//        System.out.println("affecterSupp");
//        
//        Salarie s = new Salarie();
//        s.setNom("Salarie Test Supp");
//        Salarie idS=instance.addSalarie(s);
//        
//        Salarie supp = new Salarie();
//        supp.setNom("Supperieur");
//        Salarie suppSalarie=instance.addSalarie(supp);
//                
//        Boolean result = instance.affecterSupp(idS, suppSalarie);
//        Boolean expResult = true;
//
//        assertEquals(expResult, result);
//        
//        instance.deleteSalarie(idS);
//        instance.deleteSalarie(suppSalarie);
//    }
//
//    /**
//     * Test of deleteSupp method, of class SalarieDaoImpl.
//     */
//    @Test
//    public void testDeleteSupp() {
//        System.out.println("deleteSupp");
//        
//        Salarie supp = new Salarie();
//        supp.setNom("deleteSupp");
//        Salarie suppSalarie=instance.addSalarie(supp);
//        
//        Boolean expResult = true;
//        Boolean result = instance.deleteSupp(suppSalarie);
//        assertEquals(expResult, result);
//        
//        instance.deleteSalarie(suppSalarie);
//    }
//
//    /**
//     * Test of listNotAffected method, of class SalarieDaoImpl.
//     */
//    @Test
//    public void testListNotAffected() {
//        System.out.println("listNotAffected");
//
//        List<Salarie> result = instance.listNotAffected();
//        int expResult = 0;
//        assertNotSame(expResult, result);
//    }
//
//    /**
//     * Test of listSupp method, of class SalarieDaoImpl.
//     */
//    @Test
//    public void testListSupp() {
//        System.out.println("listSupp");
//
//        List<Salarie> result = instance.listSupp();
//        int expResult = 0;
//        assertNotSame(expResult, result);
//    }
//    
//    /**
//     * Test of listSupp By Salarie 
//     */
//    @Test
//    public void testListSuppBySalarie() {
//        System.out.println("listSuppBySalarie");
//
//        Salarie supp = new Salarie();
//        supp.setNom("listSuppBySalarie");
//        Salarie suppSalarie=instance.addSalarie(supp);
//        
//        Salarie supp1 = new Salarie();
//        supp.setNom("Supperieur");
//        Salarie suppSalarie1=instance.addSalarie(supp1);
//                
//        instance.affecterSupp(suppSalarie, suppSalarie1);
//        
//        List<Salarie> result = instance.listSalarieBySupp(suppSalarie1);
//        System.out.println(" ************ SIZE OF RESULT ************** "+suppSalarie.getId());
//        int expResult = 1;
//        assertEquals(expResult, result.size());
//        
//        instance.deleteSalarie(suppSalarie);
//        instance.deleteSalarie(suppSalarie1);
//    }
//    
//    /**
//     * Test of get la liste des salaries chef d'equipe 
//     */
//    @Test
//    public void testgetSalarieChefEquipe() {
//        System.out.println("testgetSalarieChefEquipe");
//
//        List<Salarie> result = instance.getSalarieChefEquipe();
//
//        int expResult = result.size();
//        assertEquals(expResult, result.size());
//
//    }
//    
//     @Test
//    public void testupdateSalarie() {
//        System.out.println("updateSalarie");
//
//        Salarie supp = new Salarie();
//        supp.setNom("updateSalarie");
//        Salarie suppSalarie=instance.addSalarie(supp);
//        
//        suppSalarie.setNom("update2");
//        
//        Salarie result = instance.updateSalarie(suppSalarie);
//        System.out.println(" ************ SIZE OF RESULT ************** "+suppSalarie.getId());
//        String expResult ="update2";
//        assertEquals(expResult, result.getNom());
//        instance.deleteSalarie(suppSalarie);
//    }
//}
