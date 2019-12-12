/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.dao.engin;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant; 
import java.util.Date;
import ma.bservices.tgcc.Entity.ConducteurEngin; 

/**
 *
 * @author yassine.jeddi
 */
public class ConducteurEnginTest {
    public static void main(String[] args){
        ConducteurEngin c = new ConducteurEngin();
        
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	Date date = new Date();
                
                
        c.setActif(Short.parseShort("1"));
        c.setDATE_DEB_AFECT(date);
        c.setDATE_FIN_AFECT(null);
        c.setID_ENGIN(3275);
        c.setMATRICULE("1592");
         
       /* ConducteurEnginDAOImpl cd = new ConducteurEnginDAOImpl(); 
        
         cd.addConducteurEngin(c);
*/
       System.out.println(c.toString());
        
        
    }
}
