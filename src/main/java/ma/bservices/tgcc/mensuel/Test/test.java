/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.mensuel.Test;

import java.util.Date;
import javax.xml.ws.Holder;
import ma.bservices.tgcc.webService.DivaltoService;
import ma.bservices.tgcc.webService.DivaltoServiceSoap;

/**
 *
 * @author a.wattah
 */
public class test {

    public static void main(String[] args) {
        
        
         DivaltoService divaltoService = new DivaltoService();
        Holder<String> hS = new Holder<String>();
        Holder<Integer> hI = new Holder<Integer>();

        DivaltoServiceSoap divaltoServiceSoap = divaltoService.getDivaltoServiceSoap();

        divaltoServiceSoap.webServiceDiva("<ACTION>WSTGCC", "<Task>Get_Mensuel" + "", hI, hS);

        System.out.println("entree : " + hS.value);
        
      
        }
    

}
