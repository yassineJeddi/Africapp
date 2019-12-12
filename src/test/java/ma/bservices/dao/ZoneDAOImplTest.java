///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package ma.bservices.dao;
//
//import java.util.ArrayList;
//import java.util.List;
//import javax.transaction.Transactional;
//import ma.bservices.beans.Chantier;
//import ma.bservices.beans.Salarie;
//import ma.bservices.beans.Zone;
//import ma.bservices.services.ChantierService;
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
//    "file:src/main/webapp/WEB-INF/beans_test.xml",
//    "file:src/main/webapp/WEB-INF/applicationContext.xml",
//    "file:src/main/webapp/WEB-INF/spring/hibernate-context.xml",
//    "file:src/main/webapp/WEB-INF/dispatcher-servlet.xml"
//})
////@ContextConfiguration(locations = {"classpath*:*beans.xml"})
//@TransactionConfiguration()
//@Transactional
//public class ZoneDAOImplTest extends AbstractTransactionalJUnit4SpringContextTests{
//    
//    public ZoneDAOImplTest() {
//    }
//    
//    @Autowired
//    ZoneDAO instance;
//    
//    @Autowired
//    ChantierService instance1;
//    
//    @Autowired
//    SalarieDao instanceSal;
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
//     * Test of findAll method, of class ZoneDAOImpl.
//     */
//    @Test
//    public void testFindAll() {
//        System.out.println("findAll");
//        
//        List<Zone> result = instance.findAll();
//        List<Zone> expResult = result;
//        assertEquals(expResult.size(), result.size());
//    }
//
//    /**
//     * Test of findById method, of class ZoneDAOImpl.
//     */
//    @Test
//    public void testFindById() {
//        System.out.println("findById");
//        Zone z =new Zone("ZoneTest");
//        instance.addZone(z);
//        String expResult = "ZoneTest";
//        Zone result = instance.findById(z.getIdZone());
//        assertEquals(expResult, result.getLibeleZone());
//        instance.deleteZone(z.getIdZone());
//       
//    }
//
//    /**
//     * Test of findByChantierID method, of class ZoneDAOImpl.
//     */
//    @Test
//    public void testFindByChantierID() {
//        System.out.println("findByChantierID");
////        Chantier C=new Chantier("findByChantierID","findByChantierID");
////        instance1.ajouterChantier(C);
//        Chantier C=instance1.getChantier(136);
//        Zone z =new Zone("findByChantierID",C);
//        instance.addZone(z);
//        int expResult = 1;
//        List<Zone> result = instance.findByChantierID(z.getIdChantier().getId());
//        assertEquals(expResult, result.size());
//        instance.deleteZone(z.getIdZone());
//    }
//
//    /**
//     * Test of addZone method, of class ZoneDAOImpl.
//     */
//    @Test
//    public void testAddZone() {
//        System.out.println("addZone");
//        Zone zone = new Zone("addZone");
//        Boolean expResult = true;
//        Boolean result = instance.addZone(zone);
//        assertEquals(expResult, result);
//        instance.deleteZone(zone.getIdZone());
//    }
//
//    /**
//     * Test of deleteZone method, of class ZoneDAOImpl.
//     */
//    @Test
//    public void testDeleteZone() {
//        System.out.println("deleteZone");
//        Zone zone = new Zone("deleteZone");
//        instance.addZone(zone);
//        Boolean expResult = true;
//        Boolean result = instance.deleteZone(zone.getIdZone());
//        assertEquals(expResult, result);
//    }
//
//    /**
//     * Test of uppdate method, of class ZoneDAOImpl.
//     */
//    @Test
//    public void testUppdate() {
//        System.out.println("uppdate");
//        Zone zone = new Zone("uppdate");
//        instance.addZone(zone);
//        zone.setLibeleZone("uppdateAfter");
//        Boolean expResult = true;
//        Boolean result = instance.uppdate(zone);
//        assertEquals(expResult, result);
//        instance.deleteZone(zone.getIdZone());
//    }
//    
//     /**
//     * Test of find Zone By Salarie method, of class ZoneDAOImpl.
//     */
//    @Test
//    public void testfindBySalarie() {
//        List<Zone> zones = new ArrayList<Zone>();
//        
//        System.out.println("testfindBySalarie");
//        Zone zone = new Zone("testfindBySalarie");
//        instance.addZone(zone);
//        
//        Salarie s = new Salarie();
//        s.setNom("Salarie Test Supp");    
//        zones.add(zone);
//        s.setZones(zones);
//        Salarie idS=instanceSal.addSalarie(s);
//        
//        int expResult = 1;
//        List<Zone> result = instance.findBySalarie(idS.getId());
//        assertEquals(expResult, result.size());
////        instance.deleteZone(zone.getIdZone());
////        instanceSal.deleteSalarie(idS);
//    }
//    
//      /**
//     * Test of affect Salarie to Zone method, of class ZoneDAOImpl.
//     */
//    @Test
//    public void testzoneToSalarie() {
//        
//        List<Zone> zones=new ArrayList<>();
//        
//        Salarie idS = new Salarie();
//        idS.setNom("Salarie Test Supp");    
//        Salarie salarie=instanceSal.addSalarie(idS);
//        
//        Zone zone =new Zone();
//        zone.setLibeleZone("testzoneToSalarie");
//        instance.addZone(zone);
//        
//        zones.add(zone);
//        
//        salarie.setZones(zones);  
//        
//        Boolean result = instance.affectZoneToSalarie(zone, salarie);
//
//        System.out.println(" <<<<<<<<<<<<<< RESULT >>>>>>>>>> "+result); 
//
//        Boolean expResult = true;
//        
//        assertEquals(expResult, result);
////        instanceSal.deleteSalarie(salarie);
////        instance.deleteZone(zone.getIdZone());
//        
//    }
//    
//}
