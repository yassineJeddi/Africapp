package ma.bservices.tgcc.webService;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceFeature;

public class DivaltoService extends Service {

    
    private final static URL DIVALTOSERVICE_WSDL_LOCATION;
    private final static Logger logger = Logger.getLogger(DivaltoService.class.getName());

    static {
        URL url = null;
        try {
            URL baseUrl;
            baseUrl = ma.bservices.tgcc.webService.DivaltoService.class.getResource(".");
            //  url = new URL(baseUrl, "http://upsit.tgcc.ma:82/webservicediva.asmx?wsdl");
                url = new URL(baseUrl, "http://192.168.0.215:81/WebServiceDiva.asmx?wsdl");
            //      url = new URL(baseUrl, "http://192.168.0.202:81/WebServiceDiva.asmx?wsdl");
        } catch (MalformedURLException e) {
            logger.warning("Failed to create URL for the wsdl Location: 'http://upsit.tgcc.ma:82/webservicediva.asmx?wsdl', retrying as a local file");
            logger.warning(e.getMessage());
        }
        DIVALTOSERVICE_WSDL_LOCATION = url;
    }

    public DivaltoService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public DivaltoService() {
        super(DIVALTOSERVICE_WSDL_LOCATION, new QName("http://www.Divalto.fr/WebService/", "DivaltoService"));
    }

    /**
     *
     * @return returns DivaltoServiceSoap
     */
    @WebEndpoint(name = "DivaltoServiceSoap")
    public DivaltoServiceSoap getDivaltoServiceSoap() {
        return super.getPort(new QName("http://www.Divalto.fr/WebService/", "DivaltoServiceSoap"), DivaltoServiceSoap.class);
    }

    /**
     *
     * @param features A list of {@link javax.xml.ws.WebServiceFeature} to
     * configure on the proxy. Supported features not in the
     * <code>features</code> parameter will have their default values.
     * @return returns DivaltoServiceSoap
     */
    @WebEndpoint(name = "DivaltoServiceSoap")
    public DivaltoServiceSoap getDivaltoServiceSoap(WebServiceFeature... features) {
        return super.getPort(new QName("http://www.Divalto.fr/WebService/", "DivaltoServiceSoap"), DivaltoServiceSoap.class, features);
    }

}
