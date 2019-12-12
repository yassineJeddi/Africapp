///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package ma.bservices.tgcc.service.Engin;
//
//import java.util.List;
//import javax.transaction.Transactional;
//import ma.bservices.tgcc.Entity.Chantier;
//import ma.bservices.tgcc.Entity.Document;
//import ma.bservices.tgcc.Entity.VoitureChantier;
//import ma.bservices.tgcc.dao.engin.ChantierDAO;
//import org.junit.After;
//import org.junit.AfterClass;
//import org.junit.Before;
//import org.junit.BeforeClass;
//import org.junit.Test;
//import static org.junit.Assert.*;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
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
//    "file:src/main/webapp/WEB-INF/beans.xml",
//    "file:src/main/webapp/WEB-INF/applicationContext.xml"
//})
////@ContextConfiguration(locations = {"classpath*:*beans.xml"})
//@TransactionConfiguration()
//@Transactional
//public class ChantierServiceImplTest extends AbstractTransactionalJUnit4SpringContextTests{
//    
//    public ChantierServiceImplTest() {
//    }
//    
//    @Autowired
//    ChantierService instance;
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
//
//    /**
//     * Test of get_affiniteChantier method, of class ChantierServiceImpl.
//     */
//    @Test
//    public void testGet_affiniteChantier() {
//        System.out.println("get_affiniteChantier");
//        
//        String[] array_chantierAff =new String[1];
//        array_chantierAff[0]="1";
//        
//        Chantier c = new Chantier();
//        c=instance.findById(35);
//        c.setChantier_str(array_chantierAff);
//        Boolean expResult = true;
//        
//        Boolean result = instance.get_affiniteChantier(c);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//    }
//    
//}
