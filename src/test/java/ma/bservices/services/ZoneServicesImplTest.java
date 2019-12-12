///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package ma.bservices.services;
//
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.LinkedList;
//import java.util.List;
//import java.util.Set;
//import javax.transaction.Transactional;
//import ma.bservices.beans.Chantier;
//import ma.bservices.beans.Salarie;
//import ma.bservices.beans.Zone;
//import ma.bservices.dao.SalarieDao;
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
//public class ZoneServicesImplTest extends AbstractTransactionalJUnit4SpringContextTests{
//    
//    public ZoneServicesImplTest() {
//    }
//    
//    @Autowired
//    ZoneServices instance;
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
//    @Test
//    public void testUpdate() {
//        System.out.println("update");
//        Zone z =new Zone();
//        int chantier_id = 1;
//        instance.addZone(z, chantier_id);
//        Boolean expResult = true;
//        Boolean result = instance.update(z, chantier_id);
//        assertEquals(expResult, result);
//        instance.delete(z.getIdZone());
//    }
//    
//      /**
//     * Test of find Zone By Salarie method, of class ZoneDAOImpl.
//     */
//    @Test
//    public void testfindBySalarie() {
//        List<Salarie> salaries = new ArrayList<Salarie>();
//        
//        Salarie s = new Salarie();
//        s.setNom("Salarie Test Supp");           
//        Salarie idS=instanceSal.addSalarie(s);
//        
//        System.out.println("testfindBySalarie");
//        Zone zone = new Zone("testfindBySalarie");
//        salaries.add(idS);
//        zone.setSalaries(salaries);
//        instance.addZone(zone,0);
//        
//        
//        List<Zone> result = instance.findBySalarie(idS.getId());
//        int expResult = result.size();
//        assertEquals(expResult, result.size());
//        instance.delete(zone.getIdZone());
//        instanceSal.deleteSalarie(idS);
//    }
//    
//    
//      /**
//     * Test of affect Salarie to Zone method, of class ZoneDAOImpl.
//     */
//    @Test
//    public void testzoneToSalarie() {
//
//        String[] array_zones =new String[1];
//        array_zones[0]="41";
//        Boolean result = null;
//        
//        Zone z1 =new Zone();
//        z1.setLibeleZone("testZoneToSalaries");
//        instance.addZone(z1,135);
//        
//        Salarie s = new Salarie();
//        s.setNom("testzoneToSalarie");   
//        s.setZones_str(array_zones);
//        Salarie idS=instanceSal.addSalarie(s);
//        
//        result = instance.affectZoneToSalarie(z1, idS);
//        
//        System.out.println(" <<<<<<<<<<<<<< RESULT >>>>>>>>>> "+result);     
//        Boolean expResult = true;
//        
//        assertEquals(expResult, result);
//    }
//}
