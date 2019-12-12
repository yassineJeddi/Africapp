package ma.bservices.constantes;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

public class PropertiesLoader {

    private Properties props;
    private String property;

    /**
     *
     */
    public PropertiesLoader() {

        initialize();

    }

    private void initialize() {

        try {

            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            InputStream stream = classLoader.getResourceAsStream("constantes.properties");

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

    }

    /**
     * Returns the property value from a given key.
     *
     * @param key
     * @return
     */
    public String getProp(String key) {

        property = props.getProperty(key);
        return property;

    }

    /**
     * Lists the property value to the output stream.
     *
     */
    public void getProps() {

        props.list(System.out);

    }

}
