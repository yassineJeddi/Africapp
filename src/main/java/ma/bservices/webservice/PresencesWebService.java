/**
 *
 */
package ma.bservices.webservice;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import ma.bservices.services.PresenceService;

/**
 * @author root
 *
 */
@WebService(targetNamespace = "http://upsit.tgcc.ma/tgccchantiers")
@SOAPBinding(style = Style.DOCUMENT)
public class PresencesWebService {

//	public String presences(@WebParam(name = "flag")String flag,
//							@WebParam(name = "matricule")String matricule,
//							@WebParam(name = "chantier")String chantier,
//							@WebParam(name = "rubrique") String rubrique,
//							@WebParam(name = "dateDe")String dateDe,
//							@WebParam(name = "dateA")String dateA){
//		
//		String reponse="";
//		PresenceService presenceService=new PresenceService();
//		
//		reponse=presenceService.PresencesQuinzainiers(flag,matricule,chantier,rubrique, dateDe, dateA);		
//		//Endpoint.publish("http://upsit.tgcc.ma/tgccchantiers/presencesWebService", this);
//		return reponse;
//		
//		
//	}
    
    public String presences(@WebParam(name = "params") String params) {

        String reponse = "";
        PresenceService presenceService = new PresenceService();

        reponse = presenceService.PresencesQuinzainiers(params);
        return reponse;

    }

}
