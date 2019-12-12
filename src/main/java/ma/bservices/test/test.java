/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.test;
 
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

/**
 *
 * @author yassine
 */
public class test {
    public static void main(String args[]) throws Exception {
         Properties props = new Properties();
         String property;
        try { 
              try {

            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            InputStream stream = classLoader.getResourceAsStream("WEB-INF/properties/ws.properties");

            if (stream == null) {

                System.out.println("fichier properties n\'existe pas");

            } else {
                props = new Properties();
                props.load(stream);
            }

//	   props = new Properties();
//	   InputStream is = this.getClass().getResourceAsStream("/accialisconstantes.properties");
//	   props.load(is);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

            
            
            
        System.out.println("1 :::::::::::::::::::::::::> props:"+props);
        
        Properties prop = new Properties();
        ClassLoader loader = Thread.currentThread().getContextClassLoader();           
        InputStream stream = loader.getResourceAsStream("/database.properties");
    
        System.out.println("2 :::::::::::::::::::::::::> stream:"+stream);
        prop.load(stream);
        
        System.out.println("3 :::::::::::::::::::::::::> prop:"+prop);
        
        /*
        
        System.out.println("1 :::::::::::::::::::::::::> ");
        Properties props = new Properties();
        System.out.println("2 :::::::::::::::::::::::::> /properties/ws.properties");
        URL url = ClassLoader.getSystemResource("/properties/ws.properties");
        System.out.println("3 :::::::::::::::::::::::::> url:"+url);
        props.load(url.openStream());
        System.out.println("4 :::::::::::::::::::::::::> ");
        System.out.println(props);
*/
        } catch (Exception e) {
            System.out.println("ERREUR : "+e.getMessage());
        }
    }
}
