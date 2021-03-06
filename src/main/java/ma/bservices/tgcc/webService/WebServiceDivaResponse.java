package ma.bservices.tgcc.webService;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for anonymous complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 *
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="WebServiceDivaResult" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="retour" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "webServiceDivaResult",
    "retour"
})
@XmlRootElement(name = "WebServiceDivaResponse")
public class WebServiceDivaResponse {

    @XmlElement(name = "WebServiceDivaResult")
    protected int webServiceDivaResult;
    protected String retour;

    /**
     * Gets the value of the webServiceDivaResult property.
     *
     * @return 
     */
    public int getWebServiceDivaResult() {
        return webServiceDivaResult;
    }

    /**
     * Sets the value of the webServiceDivaResult property.
     *
     * @param value
     */
    public void setWebServiceDivaResult(int value) {
        this.webServiceDivaResult = value;
    }

    /**
     * Gets the value of the retour property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getRetour() {
        return retour;
    }

    /**
     * Sets the value of the retour property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setRetour(String value) {
        this.retour = value;
    }

}
