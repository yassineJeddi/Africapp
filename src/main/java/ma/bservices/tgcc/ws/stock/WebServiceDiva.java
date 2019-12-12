package fr.divalto.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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
 *         &lt;element name="action" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="param" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "action",
    "param"
})
@XmlRootElement(name = "WebServiceDiva")
public class WebServiceDiva {

    protected String action;
    protected String param;

    /**
     * Gets the value of the action property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getAction() {
        return action;
    }

    /**
     * Sets the value of the action property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setAction(String value) {
        this.action = value;
    }

    /**
     * Gets the value of the param property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getParam() {
        return param;
    }

    /**
     * Sets the value of the param property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setParam(String value) {
        this.param = value;
    }

}
